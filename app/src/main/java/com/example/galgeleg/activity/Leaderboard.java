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
    private String[] names, scores;
    private ListElemAdapter elemAdapter = new ListElemAdapter();
    private RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.leaderboard_activity);


        names = PreferenceReader.readSharedSetting(getBaseContext(), "NAMES", "NO_NAMES").replaceAll("\\W+"," ").trim().split(" ");
        scores = PreferenceReader.readSharedSetting(getBaseContext(), "SCORES", "0").replaceAll("\\W+"," ").trim().split(" ");

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
            if (scores[i].equals("0") || names[i].equals("NO_NAMES")) return;

            holder.ranking.setText("" + (i + 1));
            holder.name.setText(names[i]);
            holder.score.setText(scores[i]);

        }

        @Override
        public int getItemCount() {
            return scores.length;
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
