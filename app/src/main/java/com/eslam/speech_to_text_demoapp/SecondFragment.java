package com.eslam.speech_to_text_demoapp;

import android.content.Context;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import java.util.Locale;

public class SecondFragment extends Fragment implements View.OnClickListener {

    private TextToSpeech mTTS;
    private EditText words;
    private Button _TTS_Confuser;
    private TextView carrier_Name, charge_Code;

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

        mTTS = new TextToSpeech(view.getContext(), new TextToSpeech.OnInitListener() {
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
        });

        words = view.findViewById(R.id.et_text);
        _TTS_Confuser = view.findViewById(R.id.btn_text_to_voice);
        _TTS_Confuser.setOnClickListener(this);
        carrier_Name = view.findViewById(R.id.txt_carrier_name);
        charge_Code = view.findViewById(R.id.txt_charge_code);


    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_text_to_voice:
                //_Transfer_Text_To_Voice();
                _getData();
                break;


        }

    }

    private void _getData() {

        //Must Modify entered number to be just 14 integer number
        String charge_code = words.getText().toString();
        TelephonyManager manager = (TelephonyManager) getActivity().getSystemService(Context.TELEPHONY_SERVICE);
        String CarrierName = manager.getNetworkOperatorName().toLowerCase();
        carrier_Name.setText(CarrierName);


        if (CarrierName.contains("orange")) {
            charge_Code.setText("#102*" + charge_code + "#");
        } else if (CarrierName.contains("vodafone")) {
            charge_Code.setText("*858*" + charge_code + "#");
        } else if (CarrierName.contains("etisalat")) {
            charge_Code.setText("*655*" + charge_code + "#");
        } else if (CarrierName.contains("we")) {
            charge_Code.setText("*555*" + charge_code + "#");
        } else {
            charge_Code.setText("Cannot Find Network");

        }

        //Must Modify to make call to charging number and return respond


    }

    @Override
    public void onDestroy() {

        if (mTTS != null) {
            mTTS.stop();
            mTTS.shutdown();
        }
        super.onDestroy();
    }

    private void _Transfer_Text_To_Voice() {
        String message = words.getText().toString();
        mTTS.setPitch(0.8f);
        mTTS.setSpeechRate(0.5f);
        mTTS.speak(message, TextToSpeech.QUEUE_FLUSH, null);
    }
}
