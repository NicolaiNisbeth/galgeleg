package com.example.galgeleg;

import android.content.Context;
import android.util.AttributeSet;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.widget.LinearLayout;

import com.example.galgeleg.activity.Game;

import java.util.HashMap;

public class Keyboard extends LinearLayout {
    private SparseArray<String> btnToLetter;
    private HashMap<String, Integer> letterToBtn;

    public Keyboard(Context context){
        super(context);
    }

    public Keyboard(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public Keyboard(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initBtns(context);
    }

    private void initBtns(Context context) {
        LayoutInflater.from(context).inflate(R.layout.keyboard, this, true);
        Game game = (Game) super.getContext();

        int btnID;
        btnToLetter = new SparseArray<>();
        letterToBtn = new HashMap<>();

        for (char i = 'a'; i <= 'z'; i++) {
            btnID = getResources().getIdentifier(i+"Btn", "id", context.getPackageName());
            findViewById(btnID).setOnClickListener(game);
            btnToLetter.put(btnID, ""+i);
            letterToBtn.put(""+i, btnID);
        }

        // æ
        btnID = R.id.æBtn;
        findViewById(btnID).setOnClickListener(game);
        btnToLetter.put(btnID, "æ");
        letterToBtn.put("æ", btnID);

        // ø
        btnID = R.id.øBtn;
        findViewById(btnID).setOnClickListener(game);
        btnToLetter.put(btnID, "ø");
        letterToBtn.put("ø", btnID);

        // å
        btnID = R.id.åBtn;
        findViewById(btnID).setOnClickListener(game);
        btnToLetter.put(btnID, "å");
        letterToBtn.put("å", btnID);
    }

    public String getLetter(int id){
        return btnToLetter.get(id);
    }

    public int getLetterID(String letter){
        return letterToBtn.get(letter);
    }
}
