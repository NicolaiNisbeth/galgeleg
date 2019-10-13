package com.example.galgeleg;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.util.AttributeSet;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.galgeleg.model.Logic;

public class MyKeyboard extends LinearLayout implements View.OnClickListener {

    private Button qBtn, wBtn, eBtn, rBtn, tBtn, yBtn, uBtn, iBtn, oBtn, pBtn, åBtn,
                    aBtn, sBtn, dBtn, fBtn, gBtn, hBtn, jBtn, kBtn, lBtn, æBtn, øBtn,
                        zBtn, xBtn, cBtn, vBtn, bBtn, nBtn, mBtn;

    private SparseArray<Character> keyValues = new SparseArray<>();
    private Logic logic;
    private EditText editText;
    private ImageView imageView;
    private Activity activity;
    private TextView textView;

    public MyKeyboard(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public MyKeyboard(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context) {
        this.logic = new Logic();
        System.out.println(logic);

        LayoutInflater.from(context).inflate(R.layout.keyboard, this, true);
        qBtn = findViewById(R.id.qBtn); qBtn.setOnClickListener(this); keyValues.put(R.id.qBtn, 'q');
        wBtn = findViewById(R.id.wBtn); wBtn.setOnClickListener(this); keyValues.put(R.id.wBtn, 'w');
        eBtn = findViewById(R.id.eBtn); eBtn.setOnClickListener(this); keyValues.put(R.id.eBtn, 'e');
        rBtn = findViewById(R.id.rBtn); rBtn.setOnClickListener(this); keyValues.put(R.id.rBtn, 'r');
        tBtn = findViewById(R.id.tBtn); tBtn.setOnClickListener(this); keyValues.put(R.id.tBtn, 't');
        yBtn = findViewById(R.id.yBtn); yBtn.setOnClickListener(this); keyValues.put(R.id.yBtn, 'y');
        uBtn = findViewById(R.id.uBtn); uBtn.setOnClickListener(this); keyValues.put(R.id.uBtn, 'u');
        iBtn = findViewById(R.id.iBtn); iBtn.setOnClickListener(this); keyValues.put(R.id.iBtn, 'i');
        oBtn = findViewById(R.id.oBtn); oBtn.setOnClickListener(this); keyValues.put(R.id.oBtn, 'o');
        pBtn = findViewById(R.id.pBtn); pBtn.setOnClickListener(this); keyValues.put(R.id.pBtn, 'p');
        åBtn = findViewById(R.id.åBtn); åBtn.setOnClickListener(this); keyValues.put(R.id.åBtn, 'å');
        aBtn = findViewById(R.id.aBtn); aBtn.setOnClickListener(this); keyValues.put(R.id.aBtn, 'a');
        sBtn = findViewById(R.id.sBtn); sBtn.setOnClickListener(this); keyValues.put(R.id.sBtn, 's');
        dBtn = findViewById(R.id.dBtn); dBtn.setOnClickListener(this); keyValues.put(R.id.dBtn, 'd');
        fBtn = findViewById(R.id.fBtn); fBtn.setOnClickListener(this); keyValues.put(R.id.fBtn, 'f');
        gBtn = findViewById(R.id.gBtn); gBtn.setOnClickListener(this); keyValues.put(R.id.gBtn, 'g');
        hBtn = findViewById(R.id.hBtn); hBtn.setOnClickListener(this); keyValues.put(R.id.hBtn, 'h');
        jBtn = findViewById(R.id.jBtn); jBtn.setOnClickListener(this); keyValues.put(R.id.jBtn, 'j');
        kBtn = findViewById(R.id.kBtn); kBtn.setOnClickListener(this); keyValues.put(R.id.kBtn, 'k');
        lBtn = findViewById(R.id.lBtn); lBtn.setOnClickListener(this); keyValues.put(R.id.lBtn, 'l');
        æBtn = findViewById(R.id.æBtn); æBtn.setOnClickListener(this); keyValues.put(R.id.æBtn, 'æ');
        øBtn = findViewById(R.id.øBtn); øBtn.setOnClickListener(this); keyValues.put(R.id.øBtn, 'ø');
        zBtn = findViewById(R.id.zBtn); zBtn.setOnClickListener(this); keyValues.put(R.id.zBtn, 'z');
        xBtn = findViewById(R.id.xBtn); xBtn.setOnClickListener(this); keyValues.put(R.id.xBtn, 'x');
        cBtn = findViewById(R.id.cBtn); cBtn.setOnClickListener(this); keyValues.put(R.id.cBtn, 'c');
        vBtn = findViewById(R.id.vBtn); vBtn.setOnClickListener(this); keyValues.put(R.id.vBtn, 'v');
        bBtn = findViewById(R.id.bBtn); bBtn.setOnClickListener(this); keyValues.put(R.id.bBtn, 'b');
        nBtn = findViewById(R.id.nBtn); nBtn.setOnClickListener(this); keyValues.put(R.id.nBtn, 'n');
        mBtn = findViewById(R.id.mBtn); mBtn.setOnClickListener(this); keyValues.put(R.id.mBtn, 'm');
    }

    @Override
    public void onClick(View view) {
        char letter = keyValues.get(view.getId());
        logic.guessedLetter(letter);

        crossOutLetter(view.getId());
        editText.setText(logic.getVisibleSentence());

        if (logic.isGameIsWon())
            showPopUp("CONGRATULATIONS", "You won!");
        else {
            setHangingMan(logic.getLives());
            textView.setText("Lives " + logic.getLives());
            if (logic.isGameIsLost()) {
                showPopUp("UNLUCKY", "You lost!\n\nThe answer was: "+logic.printArray(logic.getSolution()));
            }

        }
    }

    private void showPopUp(String title, String msg){
        AlertDialog.Builder winBuild = new AlertDialog.Builder(activity);
        winBuild.setTitle(title);
        winBuild.setMessage(msg);
        winBuild.setPositiveButton("Play Again",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        activity.recreate();
                    }});

        winBuild.setNegativeButton("Exit",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        activity.finish();
                    }});

        winBuild.show();
    }

    private void crossOutLetter(int id) {
        switch (id){
            case R.id.qBtn: qBtn.setBackgroundDrawable(getResources().getDrawable(R.drawable.btn_usedletter)); break;
            case R.id.wBtn: wBtn.setBackgroundDrawable(getResources().getDrawable(R.drawable.btn_usedletter)); break;
            case R.id.eBtn: eBtn.setBackgroundDrawable(getResources().getDrawable(R.drawable.btn_usedletter)); break;
            case R.id.rBtn: rBtn.setBackgroundDrawable(getResources().getDrawable(R.drawable.btn_usedletter)); break;
            case R.id.tBtn: tBtn.setBackgroundDrawable(getResources().getDrawable(R.drawable.btn_usedletter)); break;
            case R.id.yBtn: yBtn.setBackgroundDrawable(getResources().getDrawable(R.drawable.btn_usedletter)); break;
            case R.id.uBtn: uBtn.setBackgroundDrawable(getResources().getDrawable(R.drawable.btn_usedletter)); break;
            case R.id.iBtn: iBtn.setBackgroundDrawable(getResources().getDrawable(R.drawable.btn_usedletter)); break;
            case R.id.oBtn: oBtn.setBackgroundDrawable(getResources().getDrawable(R.drawable.btn_usedletter)); break;
            case R.id.pBtn: pBtn.setBackgroundDrawable(getResources().getDrawable(R.drawable.btn_usedletter)); break;
            case R.id.åBtn: åBtn.setBackgroundDrawable(getResources().getDrawable(R.drawable.btn_usedletter)); break;
            case R.id.aBtn: aBtn.setBackgroundDrawable(getResources().getDrawable(R.drawable.btn_usedletter)); break;
            case R.id.sBtn: sBtn.setBackgroundDrawable(getResources().getDrawable(R.drawable.btn_usedletter)); break;
            case R.id.dBtn: dBtn.setBackgroundDrawable(getResources().getDrawable(R.drawable.btn_usedletter)); break;
            case R.id.fBtn: fBtn.setBackgroundDrawable(getResources().getDrawable(R.drawable.btn_usedletter)); break;
            case R.id.gBtn: gBtn.setBackgroundDrawable(getResources().getDrawable(R.drawable.btn_usedletter)); break;
            case R.id.hBtn: hBtn.setBackgroundDrawable(getResources().getDrawable(R.drawable.btn_usedletter)); break;
            case R.id.jBtn: jBtn.setBackgroundDrawable(getResources().getDrawable(R.drawable.btn_usedletter)); break;
            case R.id.kBtn: kBtn.setBackgroundDrawable(getResources().getDrawable(R.drawable.btn_usedletter)); break;
            case R.id.lBtn: lBtn.setBackgroundDrawable(getResources().getDrawable(R.drawable.btn_usedletter)); break;
            case R.id.æBtn: æBtn.setBackgroundDrawable(getResources().getDrawable(R.drawable.btn_usedletter)); break;
            case R.id.øBtn: øBtn.setBackgroundDrawable(getResources().getDrawable(R.drawable.btn_usedletter)); break;
            case R.id.zBtn: zBtn.setBackgroundDrawable(getResources().getDrawable(R.drawable.btn_usedletter)); break;
            case R.id.xBtn: xBtn.setBackgroundDrawable(getResources().getDrawable(R.drawable.btn_usedletter)); break;
            case R.id.cBtn: cBtn.setBackgroundDrawable(getResources().getDrawable(R.drawable.btn_usedletter)); break;
            case R.id.vBtn: vBtn.setBackgroundDrawable(getResources().getDrawable(R.drawable.btn_usedletter)); break;
            case R.id.bBtn: bBtn.setBackgroundDrawable(getResources().getDrawable(R.drawable.btn_usedletter)); break;
            case R.id.nBtn: nBtn.setBackgroundDrawable(getResources().getDrawable(R.drawable.btn_usedletter)); break;
            case R.id.mBtn: mBtn.setBackgroundDrawable(getResources().getDrawable(R.drawable.btn_usedletter)); break;
        }
    }

    private void setHangingMan(int lives) {
        switch (lives){
            case 5: imageView.setImageResource(R.drawable.head); break;
            case 4: imageView.setImageResource(R.drawable.body); break;
            case 3: imageView.setImageResource(R.drawable.leg1); break;
            case 2: imageView.setImageResource(R.drawable.leg2); break;
            case 1: imageView.setImageResource(R.drawable.arm1); break;
            case 0: imageView.setImageResource(R.drawable.arm2); break;
        }
    }

    public void setEditText(EditText editText){
        this.editText = editText;
    }

    public void setImageView(ImageView imageView){
        this.imageView = imageView;
    }

    public void setActivity(Activity activity){
        this.activity = activity;
    }

    public void setTextView(TextView textView) {this.textView = textView;}

}
