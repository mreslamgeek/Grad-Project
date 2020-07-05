package com.eslam.speech_to_text_demoapp;

import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.telephony.SubscriptionInfo;
import android.telephony.SubscriptionManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import java.util.List;

public class ShowNumberFragment extends Fragment implements View.OnClickListener {

    private static final int PERMISSION_REQUEST_CODE = 1;
    private TextView number_phone;
    private Button number_phone_show;

    public ShowNumberFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_show_number, container, false);

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        number_phone = view.findViewById(R.id.phone_number_txt);
        number_phone_show = view.findViewById(R.id.show_number_btn);
        number_phone_show.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.show_number_btn:
                getPhone();
                break;
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case PERMISSION_REQUEST_CODE:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    getPhone();
                } else {
                    Toast.makeText(getActivity(), "Permission Denied. We can't get phone number.", Toast.LENGTH_LONG).show();
                }
                break;
        }
    }

    private void getPhone() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            checkPermission("READ_PHONE_STATE");
            List<SubscriptionInfo> subscription = SubscriptionManager.from(getContext()).getActiveSubscriptionInfoList();
            for (int i = 0; i < subscription.size(); i++) {
                SubscriptionInfo info = subscription.get(i);
                number_phone.setText(info.getNumber());
            }
        }

    }

    private void checkPermission(String permission) {
        if (Build.VERSION.SDK_INT >= 23) {
            int result = ContextCompat.checkSelfPermission(getActivity(), permission);
            if (result == PackageManager.PERMISSION_GRANTED) {
                return;
            } else {
                requestPermission(permission);
            }
        }
    }

    private void requestPermission(String permission) {
        if (ActivityCompat.shouldShowRequestPermissionRationale(getActivity(), permission)) {
            Toast.makeText(getContext(), "Phone state permission allows us to get phone number. Please allow it for additional functionality.", Toast.LENGTH_LONG).show();
        }
        ActivityCompat.requestPermissions(getActivity(), new String[]{permission}, PERMISSION_REQUEST_CODE);
    }

}
