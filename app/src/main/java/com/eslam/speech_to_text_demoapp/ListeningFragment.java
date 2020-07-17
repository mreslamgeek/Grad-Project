package com.eslam.speech_to_text_demoapp;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.gesture.Gesture;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.speech.RecognitionListener;
import android.speech.RecognizerIntent;
import android.speech.SpeechRecognizer;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.airbnb.lottie.LottieAnimationView;

import java.util.ArrayList;
import java.util.Locale;

/**
 * A simple {@link Fragment} subclass.
 */
public class ListeningFragment extends Fragment implements View.OnTouchListener {
    private static final int RECOGNIZER_RESULT = 1000;
    LottieAnimationView micBtn;
    SpeechRecognizer mSpeechRecognizer;
    Intent mSpeechRecognizerIntent;
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

        checkPermission();
        navController = Navigation.findNavController(view);
        speech_txt = view.findViewById(R.id.speech_text);
        micBtn = view.findViewById(R.id._micAnimationView);

        micBtn.setOnTouchListener(this);
        mSpeechRecognizerIntent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        mSpeechRecognizerIntent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,
                RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        mSpeechRecognizerIntent.putExtra(RecognizerIntent.EXTRA_LANGUAGE,
                Locale.getDefault());
        mSpeechRecognizerIntent.putExtra(RecognizerIntent.EXTRA_PARTIAL_RESULTS, true);

        mSpeechRecognizer = SpeechRecognizer.createSpeechRecognizer(getContext());
        mSpeechRecognizer.setRecognitionListener(new RecognitionListener() {
            @Override
            public void onReadyForSpeech(Bundle bundle) {

            }

            @Override
            public void onBeginningOfSpeech() {

            }

            @Override
            public void onRmsChanged(float v) {

            }

            @Override
            public void onBufferReceived(byte[] bytes) {

            }

            @Override
            public void onEndOfSpeech() {

            }

            @Override
            public void onError(int i) {

            }

            @Override
            public void onResults(Bundle bundle) {
                //getting all the matches
                ArrayList<String> matches = bundle
                        .getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION);

                //displaying the first match
                if (matches != null) {
                    String inputDataString = matches.get(0).toLowerCase();
                    speech_txt.setText(inputDataString);
                    actionChecker(inputDataString);
                }
            }

            @Override
            public void onPartialResults(Bundle bundle) {
                ArrayList<String> matches = bundle
                        .getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION);
                String word = matches.get(0);
                speech_txt.setText(word);

            }

            @Override
            public void onEvent(int i, Bundle bundle) {

            }
        });

    }

    @Override
    public boolean onTouch(View view, MotionEvent motionEvent) {
        if (view.getId() == R.id._micAnimationView) {
            int action = motionEvent.getAction();
            switch (action) {
                case MotionEvent.ACTION_DOWN: // Pressed
                    micBtn.setSpeed(2.0f);
                    micBtn.playAnimation();
//                    speech_txt.setText("Listening...");
                    mSpeechRecognizer.startListening(mSpeechRecognizerIntent);
                    return true;

                case MotionEvent.ACTION_UP: // Released
                    mSpeechRecognizer.stopListening();
                    mSpeechRecognizer.cancel();

                    micBtn.setProgress(0);
                    micBtn.cancelAnimation();

                    return true;

            }
        }
        return false;

    }

    private void checkPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (!(ContextCompat.checkSelfPermission(getContext(), Manifest.permission.RECORD_AUDIO) == PackageManager.PERMISSION_GRANTED)) {
                Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS,
                        Uri.parse("package:" + getContext().getPackageName()));
                startActivity(intent);
                getActivity().finish();
            }
        }
    }

    private void actionChecker(String command) {

        if (command.equals("hello my assistant")) {
            Toast.makeText(getContext(), "done", Toast.LENGTH_SHORT).show();
            navController.navigate(R.id.action_listeningFragment_to_secondFragment);

        } else if (command.equals("read sms")) {
            navController.navigate(R.id.action_listeningFragment_to_readFragment);

        } else if (command.equals("send sms")) {
            navController.navigate(R.id.action_listeningFragment_to_sendFragment);
        } else if (command.contains("camera")) {
//            navController.navigate(R.id.action_listeningFragment_to_cameraFragment2);
            ((MainActivity) getActivity())._takePicture();

        } else if (command.equals("battery")) {
            navController.navigate(R.id.action_listeningFragment_to_batteryFragment);

        } else if (command.contains("show phone number")) {
            navController.navigate(R.id.action_listeningFragment_to_showNumberFragment);
        } else if (command.contains("call")) {
            navController.navigate(R.id.action_listeningFragment_to_callingFragment);
        } else if (command.contains("contacts") || command.contains("contact")) {
            navController.navigate(R.id.action_listeningFragment_to_contacts_List);
        } else if (command.contains("charge") || command.contains("balance")) {
            navController.navigate(R.id.action_listeningFragment_to_numberDetectorFragment);
        } else if (command.contains("detect") || (command.contains("gesture") || command.contains("gestures"))) {
//            navController.navigate(R.id.action_listeningFragment_to_gesturesDetector);
            MyGestureDialog gestureDialog = new MyGestureDialog();
            gestureDialog.setCancelable(false);


            gestureDialog.show(getActivity().getSupportFragmentManager(),"Gesture Dialog");
        } else {
            Toast.makeText(getContext(), "failed", Toast.LENGTH_SHORT).show();

        }

        mSpeechRecognizer.stopListening();
        mSpeechRecognizer.cancel();
        micBtn.setProgress(0);
        micBtn.cancelAnimation();
    }

    private void _listeningVoice() {
        Intent speechIntent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        speechIntent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        speechIntent.putExtra(RecognizerIntent.EXTRA_PROMPT, "Speech To Text");
        startActivityForResult(speechIntent, RECOGNIZER_RESULT);
    }

   /* @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode) {
            case RECOGNIZER_RESULT:
                if (resultCode == Activity.RESULT_OK && data != null) {
                    ArrayList<String> result = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
                    String msg = result.get(0).toLowerCase();

                    //String msg_test = "hello world";

                    speech_txt.setText(msg);
                    // Dont forget to edit this
                    if (msg.equals("hello world")) {
                        Toast.makeText(getContext(), "done", Toast.LENGTH_SHORT).show();
                        navController.navigate(R.id.action_listeningFragment_to_secondFragment);

                    } else if (msg.equals("read sms")) {
                        navController.navigate(R.id.action_listeningFragment_to_readFragment);

                    } else if (msg.equals("send sms")) {
                        navController.navigate(R.id.action_listeningFragment_to_sendFragment);
                    } else if (msg.equals("open camera")) {
                        navController.navigate(R.id.action_listeningFragment_to_cameraFragment2);
                    } else if (msg.equals("battery")) {
                        navController.navigate(R.id.action_listeningFragment_to_batteryFragment);

                    } else if (msg.contains("show phone number")) {
                        navController.navigate(R.id.action_listeningFragment_to_showNumberFragment);
                    } else if (msg.contains("call")) {
                        navController.navigate(R.id.action_listeningFragment_to_callingFragment);
                    } else if (msg.contains("contacts")) {
                        navController.navigate(R.id.action_listeningFragment_to_contacts_List);

                    } else {
                        Toast.makeText(getContext(), "failed", Toast.LENGTH_SHORT).show();

                    }

                }
                break;

        }

    }*/

}
