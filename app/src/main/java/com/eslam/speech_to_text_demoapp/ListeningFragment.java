package com.eslam.speech_to_text_demoapp;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.speech.RecognizerIntent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import java.lang.reflect.Array;
import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class ListeningFragment extends Fragment implements View.OnClickListener {
    private static final int RECOGNIZER_RESULT  = 1000;

    private ImageButton speech_btn;
    private TextView speech_txt;
    private NavController navController;


    public ListeningFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_listening, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        navController = Navigation.findNavController(view);
        speech_btn = view.findViewById(R.id.speech_btn);
        speech_txt = view.findViewById(R.id.speech_text);

        speech_btn.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.speech_btn:
                _listeningVoice();
                break;
        }
    }

    private void _listeningVoice() {
        Intent speechIntent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        speechIntent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL , RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        speechIntent.putExtra(RecognizerIntent.EXTRA_PROMPT , "Speech To Text");
        startActivityForResult(speechIntent,RECOGNIZER_RESULT);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode){
            case RECOGNIZER_RESULT :
                if (resultCode == Activity.RESULT_OK && data!=null){
                    ArrayList<String> result =data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
                    String msg = result.get(0);
                    String msg_test = "hello world";
                    speech_txt.setText(msg);
                    // Dont forget to edit this
                    if (msg_test.equals("hello world")){
                        Toast.makeText(getContext(), "done", Toast.LENGTH_SHORT).show();
                        navController.navigate(R.id.action_listeningFragment_to_secondFragment);
                    }else {
                        Toast.makeText(getContext(), "failed", Toast.LENGTH_SHORT).show();
                    }
                }
                break;

        }

    }
}
