package com.example.galgeleg.fragment;

import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.galgeleg.R;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link Guessor.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link Guessor#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Guessor extends Fragment {

    private OnFragmentInteractionListener listener;
    private TextView editUsername;

    public Guessor() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment Guessor.
     */
    // TODO: Rename and change types and number of parameters
    public static Guessor newInstance() {
        return new Guessor();
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
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            String usernameInput = editUsername.getText().toString().trim();
            listener.onGuessorInteraction(usernameInput.isEmpty());
        }

        @Override
        public void afterTextChanged(Editable s) {

        }
    };




    @Override
    public void onAttach(Context context) {
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

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        void onGuessorInteraction(boolean uri);
    }
}
