package com.eslam.speech_to_text_demoapp;

import android.Manifest;
import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.BatteryManager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

public class BatteryFragment extends Fragment {

    private TextView battery , charging ,plugged;
    IntentFilter filter = new IntentFilter("android.provider.Telephony.PowerConnectionReceiver");

    private BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            
            int level =intent.getIntExtra(BatteryManager.EXTRA_LEVEL,0);
            battery.setText(String.valueOf(level)+"%");

            setChargingStatus( intent);

            int chargePlug = intent.getIntExtra(BatteryManager.EXTRA_PLUGGED, -1);
            boolean usbCharge = chargePlug ==BatteryManager.BATTERY_PLUGGED_USB;
            boolean acCharge = chargePlug == BatteryManager.BATTERY_PLUGGED_AC;

            plugged.setText(String.valueOf(chargePlug));
        }
    };

    private void setChargingStatus(Intent intent) {

        int status = intent.getIntExtra("status", -1);

        switch (status) {
            case BatteryManager.BATTERY_STATUS_UNKNOWN:
                charging.setText("UnKnown");
                break;
            case BatteryManager.BATTERY_STATUS_CHARGING:
                charging.setText("Charging");
                break;
            case BatteryManager.BATTERY_STATUS_DISCHARGING:
                charging.setText("DISCHARGING");
                break;
            case BatteryManager.BATTERY_STATUS_NOT_CHARGING:
                charging.setText("Not Charging");
                break;
            case BatteryManager.BATTERY_STATUS_FULL:
                charging.setText("Full");
                break;
            default:
                charging.setText("Null");

        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        battery = view.findViewById(R.id.txt_level);
        charging = view.findViewById(R.id.txt_charge);
        plugged = view.findViewById(R.id.txt_plugged);

       // LoadBatteryInfo();

        //getActivity().registerReceiver(broadcastReceiver,filter);
        getActivity().registerReceiver(this.broadcastReceiver,new IntentFilter(Intent.ACTION_BATTERY_CHANGED));


    }
   /* private void LoadBatteryInfo() {

        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(Intent.ACTION_BATTERY_CHANGED);
        getActivity().registerReceiver(broadcastReceiver, intentFilter);

         getActivity().registerReceiver(this.broadcastReceiver,new IntentFilter(Intent.ACTION_BATTERY_CHANGED));


    }*/
}
