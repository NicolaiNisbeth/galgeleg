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
import com.example.galgeleg.util.PreferenceUtil;

public class Leaderboard extends AppCompatActivity {
    private ListElemAdapter elemAdapter = new ListElemAdapter();
    private RecyclerView recyclerView;
    private String[] names, scores;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.leaderboard_activity);

        names = PreferenceUtil.readSharedSetting(getBaseContext(), getString(R.string.names_pref), getString(R.string.default_no_names_pref))
                .replaceAll("\\W+"," ")
                .trim()
                .split(" ");

        scores = PreferenceUtil.readSharedSetting(getBaseContext(), getString(R.string.scores_pref), getString(R.string.default_no_scores_pref))
                .replaceAll("\\W+"," ")
                .trim()
                .split(" ");

        recyclerView = findViewById(R.id.recyclerview);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(elemAdapter);
    }

    class ListElemAdapter extends RecyclerView.Adapter<ListElemViewHolder> {
        @NonNull
        @Override
        public ListElemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View listElementViews = getLayoutInflater().inflate(R.layout.list_scores, parent, false);
            return new ListElemViewHolder(listElementViews);
        }

        @Override
        public void onBindViewHolder(@NonNull ListElemViewHolder holder, int i) {
            if (scores[i].equals(getString(R.string.default_no_scores_pref))
                || names[i].equals(getString(R.string.default_no_names_pref))) return;

            holder.ranking.setText("" + (i + 1));
            holder.name.setText(names[i]);
            holder.score.setText(scores[i]);
        }

        @Override
        public int getItemCount() {
            return scores.length;
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public int getItemViewType(int position) {
            return position;
        }
    }

    class ListElemViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private TextView ranking, name, score;

        public ListElemViewHolder(View listElementViews) {
            super(listElementViews);
            ranking = listElementViews.findViewById(R.id.list_elem_ranking);
            name = listElementViews.findViewById(R.id.list_elem_name);
            score = listElementViews.findViewById(R.id.list_elem_score);
        }

        @Override
        public void onClick(View v) { }
    }
}
