package com.example.galgeleg.fragment;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.example.galgeleg.R;

public class Guesser extends Fragment {

    private OnFragmentInteractionListener listener;
    private TextView editUsername;
    private String usernameInput;

    public Guesser() { }
    public static Guesser newInstance() {
        return new Guesser();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_guessor, container, false);

        editUsername = v.findViewById(R.id.editUsername);
        editUsername.addTextChangedListener(playerSetupWatcher);
        return v;
    }

    private TextWatcher playerSetupWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) { }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            usernameInput = editUsername.getText().toString().trim();
            listener.playBtnActivator(usernameInput.isEmpty());
            listener.getUsernameFromFrag(usernameInput);
        }

        @Override
        public void afterTextChanged(Editable s) { }
    };

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            listener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        listener = null;
    }

    public interface OnFragmentInteractionListener {
        void playBtnActivator(boolean uri);
        void getUsernameFromFrag(String username);
    }
}
