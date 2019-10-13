package com.example.galgeleg;

import android.os.Bundle;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;


public class GameActivity extends AppCompatActivity {
    private ImageView imageView;
    private MyKeyboard keyboard;
    private EditText editText;
    private TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.game_activity);

        imageView = findViewById(R.id.imageView);
        editText = findViewById(R.id.editText);
        keyboard = findViewById(R.id.keyboard);
        textView = findViewById(R.id.lives);

        keyboard.setEditText(editText);
        keyboard.setImageView(imageView);
        keyboard.setTextView(textView);
        keyboard.setActivity(this);
    }
}