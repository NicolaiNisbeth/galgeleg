package com.example.galgeleg.player_setup;

import android.content.Context;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.galgeleg.game.GameLogic;
import com.example.galgeleg.R;
import com.example.galgeleg.util.PreferenceUtil;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.net.URL;
import java.util.Set;

public class SelectorFragment extends Fragment implements View.OnClickListener {
    private ListElemAdapter elemAdapter = new ListElemAdapter();
    private FloatingActionButton floatingActionButton;
    private OnFragmentInteractionListener listener;
    private RecyclerView recyclerView;
    private int selectedPosition = 0;
    private ProgressBar progressBar;
    private String[] words;
    private GameLogic gameLogic;

    public SelectorFragment() { }
    public static SelectorFragment newInstance() {
        return new SelectorFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_selector, container, false);

        recyclerView = v.findViewById(R.id.wordRecycler);
        recyclerView.setLayoutManager(new LinearLayoutManager(v.getContext()));
        recyclerView.setAdapter(elemAdapter);

        floatingActionButton = v.findViewById(R.id.floating_action_btn);
        floatingActionButton.setOnClickListener(this);

        progressBar = v.findViewById(R.id.progress_bar);
        progressBar.setVisibility(View.INVISIBLE);
        gameLogic = PlayerSetupActivity.liveData.getValue();


        // When reading from wordLibrary in GameLogic, the order of words differ from the order in cache
        // therefore to maintain the same order I have to read from Preferences.
        String data = PreferenceUtil.readSharedSetting(getContext(), getString(R.string.words_pref), getString(R.string.default_no_words_pref));
        words = data.replaceAll("\\W+"," ").trim().split(" ");

        if (data.equals(getString(R.string.default_no_words_pref)))
            words = gameLogic.getWordLibrary().toArray(new String[0]);

        return v;
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            listener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        listener = null;
    }

    @Override
    public void onClick(View v) {
        floatingActionButton.setVisibility(View.INVISIBLE);
        new loadWordsFromDR().execute(); // start async call to DR
        progressBar.setVisibility(View.VISIBLE);
    }

    class ListElemAdapter extends RecyclerView.Adapter<SelectorFragment.ListElemViewHolder> {
        @NonNull
        @Override
        public ListElemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View listElementViews = getLayoutInflater().inflate(R.layout.row_words, parent, false);
            return new ListElemViewHolder(listElementViews);
        }

        @Override
        public void onBindViewHolder(@NonNull ListElemViewHolder holder, int i) {
            holder.word.setText(words[i]);
            holder.word.setAllCaps(true);
            holder.word.setTextSize(17);
            holder.itemView.setBackgroundColor(selectedPosition == i ? Color.LTGRAY : Color.TRANSPARENT);
        }

        @Override
        public int getItemCount() {
            return words.length;
        }
    }

    // https://stackoverflow.com/questions/27194044/how-to-properly-highlight-selected-item-on-recyclerview
    class ListElemViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private TextView word;
        public ListElemViewHolder(View listElementViews) {
            super(listElementViews);
            word = listElementViews.findViewById(R.id.list_elem_word);
            listElementViews.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            if (getAdapterPosition() == RecyclerView.NO_POSITION) return;

            elemAdapter.notifyItemChanged(selectedPosition);
            selectedPosition = getAdapterPosition();
            elemAdapter.notifyItemChanged(selectedPosition);
            listener.recyclerViewListClicked(v, this.getLayoutPosition());
        }
    }

    private class loadWordsFromDR extends AsyncTask<URL, Integer, Set<String>> {

        protected Set<String> doInBackground(URL... urls) {
            Set<String> wordsFromDR = null;

            try {
                wordsFromDR = gameLogic.hentOrdFraDr();
                gameLogic.getWordLibrary().clear();
                gameLogic.getWordLibrary().addAll(wordsFromDR);
                PreferenceUtil.saveSharedSetting(getContext(), getString(R.string.words_pref), String.valueOf(wordsFromDR));
            } catch (Exception e) {
                e.printStackTrace();
            }

            PlayerSetupActivity.liveData.postValue(gameLogic);
            return wordsFromDR;
        }

        @Override
        protected void onPostExecute(Set<String> words) {
            System.out.println("Downloaded: " + words);

            if (words != null){
                SelectorFragment.this.words = words.toArray(new String[0]);
                elemAdapter.notifyDataSetChanged();
                Toast.makeText(getContext(), R.string.download_succesful_msg, Toast.LENGTH_SHORT).show();
            }
            else {
                Toast.makeText(getContext(), R.string.download_failed, Toast.LENGTH_LONG).show();
            }

            floatingActionButton.setVisibility(View.VISIBLE);
            progressBar.setVisibility(View.GONE);
        }
    }

    public interface OnFragmentInteractionListener {
        void recyclerViewListClicked(View v, int position);
    }
}