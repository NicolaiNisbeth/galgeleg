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
import com.example.galgeleg.util.PreferenceReader;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Map;

public class Leaderboard extends AppCompatActivity {

    // se nedenstående
    // https://drive.google.com/file/d/1wt2wq0yFZFPqFirYwvGXbWDBEEvfdh_G/view
    // https://www.youtube.com/watch?v=_ziMhIYjy0E&feature=youtu.be


    /*
    String[] playerNames = {"TheShackledOne", "FNC Bwipo", "JUGKlNG", "MSF Decay", "Saim Sejoing", "baxsxasd",
    "Mldk1ng", "Teddy0", "Iziio", "Diablo v2", "Agurin", "Dîamondprox", "EU funny game", "the inescapable",
    "inspiredd", "v0PnXqLxFmlfcD83s", "Chrisberg", "SK Selfmade", "Ssutres", "YOY N0 SEE"};
        */

    private String[] names = new String[20], scores = new String[20];
    //private ArrayList<String> playerNamesList = new ArrayList<>(Arrays.asList(playerNames));
    private ListElemAdapter elemAdapter = new ListElemAdapter();
    private RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.leaderboard_activity);


        //names = PreferenceReader.readSharedSetting(getBaseContext(), "NAMES", "NO_NAMES").replaceAll("\\W+"," ").trim().split(" ");
        //scores = PreferenceReader.readSharedSetting(getBaseContext(), "SCORES", "NO_SCORES").replaceAll("\\W+"," ").trim().split(" ");

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
        public void onBindViewHolder(@NonNull ListElemViewholder holder, int i) {
            // TODO: read data from preference manager

            holder.ranking.setText("" + (i + 1));
            holder.name.setText(names[i]);
            holder.score.setText(scores[i]);

        }

        @Override
        public int getItemCount() {
            return names.length;
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
