package com.example.galgeleg.fragment;

import android.content.Context;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.galgeleg.Logic;
import com.example.galgeleg.R;
import com.example.galgeleg.activity.Leaderboard;
import com.example.galgeleg.activity.Menu;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link Selector.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link Selector#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Selector extends Fragment {
    private OnFragmentInteractionListener listener;
    private String[] words;
    private RecyclerView recyclerView;
    private ListElemAdapter elemAdapter = new ListElemAdapter();
    int selectedPosition = 0;


    public Selector() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment Selector.
     */
    // TODO: Rename and change types and number of parameters
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

        words = Menu.liveData.getValue().getWordLibrary().toArray(new String[0]);
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
}
