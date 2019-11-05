package com.example.galgeleg;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.util.AttributeSet;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

public class Keyboard extends LinearLayout implements View.OnClickListener {
    public static Keyboard instance;
    private SparseArray<Character> btnToLetter;
    private GameActivity g;

    // TODO: fix every screen rotation calls constructor!
    public Keyboard(Context context){
        super(context);
    }

    public Keyboard(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public Keyboard(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.g = (GameActivity) super.getContext();
        this.btnToLetter = new SparseArray<>();
        initBtns(context);
    }

    public static Keyboard getInstance(Context context){
        if (instance == null) instance = new Keyboard(context);

        return instance;
    }

    private void initBtns(Context context) {
        LayoutInflater.from(context).inflate(R.layout.keyboard, this, true);
        int btnID;
        for (char i = 'a'; i <= 'z'; i++) {
            btnID = getResources().getIdentifier(i+"Btn", "id", context.getPackageName());
            findViewById(btnID).setOnClickListener(this);
            btnToLetter.put(btnID, i);
        }
        // æ
        btnID = R.id.æBtn;
        findViewById(btnID).setOnClickListener(this);
        btnToLetter.put(btnID, 'æ');

        // ø
        btnID = R.id.øBtn;
        findViewById(btnID).setOnClickListener(this);
        btnToLetter.put(btnID, 'ø');

        // å
        btnID = R.id.åBtn;
        findViewById(btnID).setOnClickListener(this);
        btnToLetter.put(btnID, 'å');
    }

    @Override
    public void onClick(View view) {
        char letter = btnToLetter.get(view.getId());

        g.logic.guessedLetter(letter);

        crossOutLetter(view.getId());
        g.hiddenWord.setText(g.logic.getVisibleSentence());


        // TODO: redirect to end game activity
        if (g.logic.gameIsWon())
            showPopUp("CONGRATULATIONS", "You won!");
        else {
            setHangingMan(g.logic.getLives());
            g.lives.setText("Lives " + g.logic.getLives());
            if (g.logic.gameIsLost()) {
                showPopUp("UNLUCKY", "You lost!\n\nThe answer was: "+g.logic.printArray(g.logic.getSolution()));
            }

        }
    }

    public void crossOutLetter(int id) {
        Button b = findViewById(id);
        b.setBackgroundDrawable(getResources().getDrawable(R.drawable.btn_usedletter));
    }

    public void setHangingMan(int lives) {
        switch (lives){
            case 5: g.imageView.setImageResource(R.drawable.head); break;
            case 4: g.imageView.setImageResource(R.drawable.body); break;
            case 3: g.imageView.setImageResource(R.drawable.leg1); break;
            case 2: g.imageView.setImageResource(R.drawable.leg2); break;
            case 1: g.imageView.setImageResource(R.drawable.arm1); break;
            case 0: g.imageView.setImageResource(R.drawable.arm2); break;
        }
    }

    // TODO implement activity to showcase transfer of data
    private void showPopUp(String title, String msg){
        AlertDialog.Builder endBuild = new AlertDialog.Builder(super.getContext());
        endBuild.setTitle(title);
        endBuild.setMessage(msg);
        endBuild.setPositiveButton("Play Again",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        g.logic.restart();
                        g.finish();
                        g.startActivity(g.getIntent());
                        //g.recreate(); // doesn't reload initial start string on screen until btn pressed
                    }});

        endBuild.setNegativeButton("Exit",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        g.logic.restart();
                        g.finish();
                    }});
        endBuild.show();
    }
}
