package com.eslam.speech_to_text_demoapp;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

public class SendFragment extends Fragment implements View.OnClickListener {

     Button btnSend;
     EditText etPhoneNo, etMsg;
    private static final int MY_PERMISSION_REQUEST_SMS =0;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_send, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        btnSend = view.findViewById(R.id.btnSend);
        etMsg = view.findViewById(R.id.txtMsg);
        etPhoneNo = view.findViewById(R.id.txtPhoneNo);
        btnSend.setOnClickListener(this);
    }


    private void MYMessage() {
        String myNumber=etPhoneNo.getText().toString().trim();
        String myMsg=etMsg.getText().toString().trim();

        if (myNumber == null || myNumber.equals("") || myMsg == null || myMsg.equals("")){
            Toast.makeText(getContext(), "Field cannot be empty", Toast.LENGTH_SHORT).show();
        }
        else {

            if (TextUtils.isDigitsOnly(myNumber)){
                SmsManager smsManager=SmsManager.getDefault();
                smsManager.sendTextMessage(myNumber,null,myMsg,null,null);
                Toast.makeText(getContext(), "Message sent", Toast.LENGTH_SHORT).show();
            }
            else{
                Toast.makeText(getContext(), "Please Enter numbers only", Toast.LENGTH_SHORT).show();
            }

        }

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode){
            case  MY_PERMISSION_REQUEST_SMS:
                if (grantResults.length >= 0 && grantResults[0]==PackageManager.PERMISSION_GRANTED){
                    MYMessage();
                }
                else {
                    Toast.makeText(getContext(), "You Don't have permission" + "to make the action", Toast.LENGTH_SHORT).show();
                }
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btnSend:
                int permissionOnCheck = ContextCompat.checkSelfPermission(getContext(), Manifest.permission.SEND_SMS);
                if (permissionOnCheck == PackageManager.PERMISSION_GRANTED) {
                    MYMessage();
                }
                else {
                    ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.SEND_SMS},MY_PERMISSION_REQUEST_SMS);
                }
                break;
        }
    }
}

