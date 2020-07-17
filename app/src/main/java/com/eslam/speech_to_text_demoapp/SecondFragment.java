package com.eslam.speech_to_text_demoapp;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class SecondFragment extends Fragment implements View.OnClickListener {

    //    private TextToSpeech mTTS;
    private EditText words;

    private TextView charge_Code;

    private Button _charge_btn;
    private TextView phone_number;

    public SecondFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_second, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

/*        mTTS = new TextToSpeech(view.getContext(), new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int i) {
                if (i == TextToSpeech.SUCCESS) {
                    int result = mTTS.setLanguage(Locale.CANADA);
                    if (result == TextToSpeech.LANG_MISSING_DATA
                            || result == TextToSpeech.LANG_NOT_SUPPORTED) {
                        Log.e("TTS", "Language not supported");
                    } else {

                    }
                }
            }
        });*/
        phone_number = view.findViewById(R.id.txt_phone_number);

        words = view.findViewById(R.id.et_text);
        charge_Code = view.findViewById(R.id.txt_charge_code);

        _charge_btn = view.findViewById(R.id.btn_text_to_voice);
        _charge_btn.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_text_to_voice:
                _getData();
                break;

        }

    }

    private void _getData() {

        //Must Modify entered number to be just 14 integer number
        String chargeCode = words.getText().toString();
        TelephonyManager manager = (TelephonyManager) getActivity().getSystemService(Context.TELEPHONY_SERVICE);
        String network_name = manager.getNetworkOperatorName().toLowerCase();
        phone_number.setText(network_name);

        Uri valid_code = null;
        chargeCode.trim();

        if (network_name.contains("orange")) {
//            valid_code = Uri.parse (Uri.decode("#")+"102"+Uri.decode("*") + chargeCode + Uri.decode("#"));



//            valid_code = Uri.decode("#")+"102"+Uri.decode("*")+Uri.parse(chargeCode)+Uri.decode("#");
        } else if (network_name.contains("vodafone")) {
//            valid_code = ("*858*" + chargeCode + "#");
        } else if (network_name.contains("etisalat")) {
//            valid_code = ("*655*" + chargeCode + "#");
        } else if (network_name.contains("we")) {
//            valid_code = ("*555*" + chargeCode + "#");
        } else {
            charge_Code.setText("Cannot Find Network");

        }

//        Toast.makeText(getContext(), valid_code.toString(), Toast.LENGTH_SHORT).show();
        charge_Code.setText(valid_code.toString());
//        ((MainActivity) getActivity())._Transfer_Text_To_Voice(charge_Code.getText().toString());
//        ((MainActivity) getActivity()).makePhoneCall(valid_code);


        //Must Modify to make call to charging number and return respond

    }

/*

    //onDestroy Text_To_Speech Engine
    @Override
    public void onDestroy() {

        if (mTTS != null) {
            mTTS.stop();
            mTTS.shutdown();
        }
        super.onDestroy();
    }
*/

/*
        //Display SIM-Network Provider
        private void show_carrier_name() {
        TelephonyManager tm = (TelephonyManager) getActivity().getSystemService(Context.TELEPHONY_SERVICE);
        String network_name = tm.getNetworkOperatorName();
        NetworkName = network_name;
        phone_number.setText(network_name);

    }*/

/*
   //Method To Speak Toast
        private void _Transfer_Text_To_Voice(String speakText) {
        mTTS.setPitch(0.7f);
        mTTS.setSpeechRate(0.7f);

        mTTS.speak(speakText, TextToSpeech.QUEUE_FLUSH, null);
    }*/
}
