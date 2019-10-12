package com.example.galgeleg;

import android.os.Bundle;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;


public class GameActivity extends AppCompatActivity {
    private ImageView imageView;
    private MyKeyboard keyboard;
    private EditText editText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.game_activity);

        imageView = findViewById(R.id.imageView);
        editText = findViewById(R.id.editText);
        keyboard = findViewById(R.id.keyboard);

        keyboard.setEditText(editText);
        keyboard.setImageView(imageView);
        keyboard.setActivity(this);
    }
}