package com.example.galgeleg.fragment;

import android.content.Context;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.galgeleg.Logic;
import com.example.galgeleg.R;
import com.example.galgeleg.activity.PlayerSetup;
import com.example.galgeleg.util.PreferenceUtil;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.net.URL;
import java.util.HashSet;
import java.util.Set;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link Selector.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link Selector#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Selector extends Fragment implements View.OnClickListener {
    private OnFragmentInteractionListener listener;
    private String[] words;
    private RecyclerView recyclerView;
    private ListElemAdapter elemAdapter = new ListElemAdapter();
    private int selectedPosition = 0;
    private ProgressBar progressBar;
    private FloatingActionButton floatingActionButton;

    public Selector() {
        // Required empty public constructor
    }

    public static Selector newInstance() {
        return new Selector();
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
        // When reading from wordLibrary in Logic, the order of words differ from the order in cache
        // therefore to maintain the same order I have to read from Preferences.
        String data = PreferenceUtil.readSharedSetting(getContext(), "WORDS", "noValues");
        words = data.replaceAll("\\W+"," ").trim().split(" ");

        if (data.equals("noValues"))
            words = PlayerSetup.liveData.getValue().getWordLibrary().toArray(new String[0]);


        return v;
    }

    @Override
    public void onAttach(Context context) {
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
        /*
        // TODO spinning in the background
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                progressBar.setVisibility(View.GONE);
            }
        }, 6000);

         */
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        void recyclerViewListClicked(View v, int position);
    }

    class ListElemAdapter extends RecyclerView.Adapter<Selector.ListElemViewHolder> {


        @NonNull
        @Override
        public ListElemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View listElementViews = getLayoutInflater().inflate(R.layout.list_words, parent, false);
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
            Logic logic = PlayerSetup.liveData.getValue();
            Set<String> wordsFromDR = null;

            try {
                wordsFromDR = logic.hentOrdFraDr();
                logic.getWordLibrary().clear();
                logic.getWordLibrary().addAll(wordsFromDR);
                PreferenceUtil.saveSharedSetting(getContext(), "WORDS", String.valueOf(wordsFromDR));

            } catch (Exception e) {
                e.printStackTrace();
            }

            PlayerSetup.liveData.postValue(logic);
            return wordsFromDR;
        }

        // TODO: Error handling in case of no connection

        @Override
        protected void onPostExecute(Set<String> words) {
            System.out.println("Downloaded: " + words);
            if (words != null){
                Selector.this.words = words.toArray(new String[0]);
                elemAdapter.notifyDataSetChanged();
                Toast.makeText(getContext(), "Words are downloaded", Toast.LENGTH_SHORT).show();
            }
            else {
                Toast.makeText(getContext(), "Unable to fetch from DR!", Toast.LENGTH_LONG).show();
            }
            floatingActionButton.setVisibility(View.VISIBLE);
            progressBar.setVisibility(View.GONE);
        }
    }
}
