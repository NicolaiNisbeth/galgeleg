package com.example.galgeleg;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

public class GameActivity extends AppCompatActivity implements View.OnClickListener {
    private Button btn;
    private ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.game_activity);

        btn = findViewById(R.id.button);
        btn.setOnClickListener(this);

        imageView = findViewById(R.id.imageView);


    }

    @Override
    public void onClick(View v) {
        if (v == btn){
            imageView.setImageResource(R.drawable.drawing2);
        }
    }
}
