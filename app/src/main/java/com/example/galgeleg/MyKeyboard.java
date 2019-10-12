package com.example.galgeleg;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.inputmethod.InputConnection;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.example.galgeleg.model.Logic;

public class MyKeyboard extends LinearLayout implements View.OnClickListener {

    private Button qBtn, wBtn, eBtn, rBtn, tBtn, yBtn, uBtn, iBtn, oBtn, pBtn, åBtn,
                    aBtn, sBtn, dBtn, fBtn, gBtn, hBtn, jBtn, kBtn, lBtn, æBtn, øBtn,
                        zBtn, xBtn, cBtn, vBtn, bBtn, nBtn, mBtn;



    private SparseArray<String> keyValues = new SparseArray<>();
    private InputConnection inputConnection;
    private Logic logic;
    private EditText editText;
    private ImageView imageView;

    public MyKeyboard(Context context) {
        this(context, null, 0);
    }

    public MyKeyboard(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public MyKeyboard(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
        this.logic = new Logic();
        System.out.println(logic);
    }

    private void init(Context context) {
        LayoutInflater.from(context).inflate(R.layout.keyboard, this, true);
        qBtn = findViewById(R.id.qBtn); qBtn.setOnClickListener(this); keyValues.put(R.id.qBtn, "q");
        wBtn = findViewById(R.id.wBtn); wBtn.setOnClickListener(this); keyValues.put(R.id.wBtn, "w");
        eBtn = findViewById(R.id.eQtn); eBtn.setOnClickListener(this); keyValues.put(R.id.eQtn, "e");
        rBtn = findViewById(R.id.rQtn); rBtn.setOnClickListener(this); keyValues.put(R.id.rQtn, "r");
        tBtn = findViewById(R.id.tBtn); tBtn.setOnClickListener(this); keyValues.put(R.id.tBtn, "t");
        yBtn = findViewById(R.id.yBtn); yBtn.setOnClickListener(this); keyValues.put(R.id.yBtn, "y");
        uBtn = findViewById(R.id.uBtn); uBtn.setOnClickListener(this); keyValues.put(R.id.uBtn, "u");
        iBtn = findViewById(R.id.iBtn); iBtn.setOnClickListener(this); keyValues.put(R.id.iBtn, "i");
        oBtn = findViewById(R.id.oBtn); oBtn.setOnClickListener(this); keyValues.put(R.id.oBtn, "o");
        pBtn = findViewById(R.id.pBtn); pBtn.setOnClickListener(this); keyValues.put(R.id.pBtn, "p");
        åBtn = findViewById(R.id.åBtn); åBtn.setOnClickListener(this); keyValues.put(R.id.åBtn, "å");
        aBtn = findViewById(R.id.aBtn); aBtn.setOnClickListener(this); keyValues.put(R.id.aBtn, "a");
        sBtn = findViewById(R.id.sBtn); sBtn.setOnClickListener(this); keyValues.put(R.id.sBtn, "s");
        dBtn = findViewById(R.id.dBtn); dBtn.setOnClickListener(this); keyValues.put(R.id.dBtn, "d");
        fBtn = findViewById(R.id.fBtn); fBtn.setOnClickListener(this); keyValues.put(R.id.fBtn, "f");
        gBtn = findViewById(R.id.gBtn); gBtn.setOnClickListener(this); keyValues.put(R.id.gBtn, "g");
        hBtn = findViewById(R.id.hBtn); hBtn.setOnClickListener(this); keyValues.put(R.id.hBtn, "h");
        jBtn = findViewById(R.id.jBtn); jBtn.setOnClickListener(this); keyValues.put(R.id.jBtn, "j");
        kBtn = findViewById(R.id.kBtn); kBtn.setOnClickListener(this); keyValues.put(R.id.kBtn, "k");
        lBtn = findViewById(R.id.lBtn); lBtn.setOnClickListener(this); keyValues.put(R.id.lBtn, "l");
        æBtn = findViewById(R.id.æBtn); æBtn.setOnClickListener(this); keyValues.put(R.id.æBtn, "æ");
        øBtn = findViewById(R.id.øBtn); øBtn.setOnClickListener(this); keyValues.put(R.id.øBtn, "ø");
        zBtn = findViewById(R.id.zBtn); zBtn.setOnClickListener(this); keyValues.put(R.id.zBtn, "z");
        xBtn = findViewById(R.id.xBtn); xBtn.setOnClickListener(this); keyValues.put(R.id.xBtn, "x");
        cBtn = findViewById(R.id.cBtn); cBtn.setOnClickListener(this); keyValues.put(R.id.cBtn, "c");
        vBtn = findViewById(R.id.vBtn); vBtn.setOnClickListener(this); keyValues.put(R.id.vBtn, "v");
        bBtn = findViewById(R.id.bBtn); bBtn.setOnClickListener(this); keyValues.put(R.id.bBtn, "b");
        nBtn = findViewById(R.id.nBtn); nBtn.setOnClickListener(this); keyValues.put(R.id.nBtn, "n");
        mBtn = findViewById(R.id.mBtn); mBtn.setOnClickListener(this); keyValues.put(R.id.mBtn, "m");
    }

    @Override
    public void onClick(View view) {

        char value = keyValues.get(view.getId()).charAt(0);

        editText.setText("");
        logic.guessedLetter(value);
        editText.setText(logic.getVisibleSentence());
        if (!logic.isPreviousGuessWasCorrect()){
            setHangingMan(logic.getWrongGuess());
        }

        if (logic.isGameIsWon())
            System.out.println("du har vundet");

        if (logic.isGameIsLost()) {
            System.out.println("du har tabt");
        }

    }

    private void setHangingMan(int wrongGuess) {
        switch (wrongGuess){
            case 1: imageView.setImageResource(R.drawable.drawing2); break;
            case 2: imageView.setImageResource(R.drawable.drawing3); break;
            case 3: imageView.setImageResource(R.drawable.drawing4); break;
            case 4: imageView.setImageResource(R.drawable.drawing5); break;
            case 5: imageView.setImageResource(R.drawable.drawing6); break;
            case 6: imageView.setImageResource(R.drawable.drawing7); break;
        }

    }

    public void setInputConnection(InputConnection ic) {
        inputConnection = ic;
    }

    public void setEditText(EditText editText){
        this.editText = editText;
    }

    public void setImageView(ImageView imageView){
        this.imageView = imageView;
    }

}
