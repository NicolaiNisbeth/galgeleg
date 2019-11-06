package com.example.galgeleg;

import android.content.Context;
import android.util.AttributeSet;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.widget.LinearLayout;

import com.example.galgeleg.activity.Game;

public class Keyboard extends LinearLayout {
    private SparseArray<String> btnToLetter;

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
        for (char i = 'a'; i <= 'z'; i++) {
            btnID = getResources().getIdentifier(i+"Btn", "id", context.getPackageName());
            findViewById(btnID).setOnClickListener(game);
            btnToLetter.put(btnID, ""+i);
        }

        // æ
        btnID = R.id.æBtn;
        findViewById(btnID).setOnClickListener(game);
        btnToLetter.put(btnID, "æ");

        // ø
        btnID = R.id.øBtn;
        findViewById(btnID).setOnClickListener(game);
        btnToLetter.put(btnID, "ø");

        // å
        btnID = R.id.åBtn;
        findViewById(btnID).setOnClickListener(game);
        btnToLetter.put(btnID, "å");
    }

    public String getLetter(int id){
        return btnToLetter.get(id);
    }
}
