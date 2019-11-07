package com.example.galgeleg.activity;

import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.galgeleg.R;

import java.util.ArrayList;
import java.util.Arrays;

public class Leaderboard extends AppCompatActivity {

    // se nedenstående
    // https://drive.google.com/file/d/1wt2wq0yFZFPqFirYwvGXbWDBEEvfdh_G/view
    // https://www.youtube.com/watch?v=_ziMhIYjy0E&feature=youtu.be


    String[] playerNames = {"TheShackledOne", "FNC Bwipo", "JUGKlNG", "MSF Decay", "Saim Sejoing", "baxsxasd",
    "Mldk1ng", "Teddy0", "Iziio", "Diablo v2", "Agurin", "Dîamondprox", "EU funny game", "the inescapable",
    "inspiredd", "v0PnXqLxFmlfcD83s", "Chrisberg", "SK Selfmade", "Ssutres"};

    private ArrayList<String> playerNamesList = new ArrayList<>(Arrays.asList(playerNames));
    private ListElemAdapter elemAdapter = new ListElemAdapter();
    private RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.leaderboard_activity);

        recyclerView = findViewById(R.id.recyclerview);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(elemAdapter);
    }

    class ListElemAdapter extends RecyclerView.Adapter<ListElemViewholder> {
        @NonNull
        @Override
        public ListElemViewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View listElementViews = getLayoutInflater().inflate(R.layout.list_elements, parent, false);
            return new ListElemViewholder(listElementViews);
        }

        @Override
        public void onBindViewHolder(@NonNull ListElemViewholder holder, int position) {
            holder.ranking.setText("" + (position + 1));
            holder.name.setText(playerNamesList.get(position));
            holder.score.setText("" + (position + 1 * 2));
        }

        @Override
        public int getItemCount() {
            return playerNamesList.size();
        }
    }

    class ListElemViewholder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private TextView ranking, name, score;

        public ListElemViewholder(View listElementViews) {
            super(listElementViews);
            ranking = listElementViews.findViewById(R.id.list_elem_ranking);
            name = listElementViews.findViewById(R.id.list_elem_name);
            score = listElementViews.findViewById(R.id.list_elem_score);
        }

        @Override
        public void onClick(View v) { }
    }
}
