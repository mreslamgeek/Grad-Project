package com.eslam.speech_to_text_demoapp;

import android.Manifest;
import android.app.Activity;
import android.content.ContentResolver;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import java.util.ArrayList;


public class ReadFragment extends Fragment {

     ListView listView;
    private static final int PERMISSION_REQUEST_READ_CONTACTS=100;
    ArrayList<String> smList;
    private static final String TAG= MainActivity.class.getSimpleName();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_read, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        listView = view.findViewById(R.id.list);
        int permissionOnCheck= ContextCompat.checkSelfPermission(getContext(), Manifest.permission.READ_SMS);

        if (permissionOnCheck== PackageManager.PERMISSION_GRANTED)
        {
            showContacts();
        }
        else {
            ActivityCompat.requestPermissions(getActivity(),new String[]{Manifest.permission.READ_SMS},PERMISSION_REQUEST_READ_CONTACTS);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if(requestCode==PERMISSION_REQUEST_READ_CONTACTS){
            if (grantResults[0]==PackageManager.PERMISSION_GRANTED) {
                showContacts();
            }
            else {
                Toast.makeText(getContext(), "Until you grant the permission, we cannot display name", Toast.LENGTH_SHORT).show();
            }
        }
    }

    public void showContacts(){
        Uri inboxUri =Uri.parse("content://sms/inbox");
        smList=new ArrayList<>();
        ContentResolver contentResolver= getActivity().getContentResolver();
        Cursor cursor=contentResolver.query(inboxUri,null,null,null,null);
        while (cursor.moveToNext()){
            String number= cursor.getString(cursor.getColumnIndexOrThrow("address")).toString();
            String body= cursor.getString(cursor.getColumnIndexOrThrow("body")).toString();
            smList.add("NumBer:"+number+ "\n" +  "Body:"+ body);
            Log.d(TAG,"showContacts: number:"+number +"body: "+body);
        }
        cursor.close();
        ArrayAdapter<String> adapter=new ArrayAdapter<String>(getContext(), android.R.layout.simple_list_item_1,smList);
        listView.setAdapter(adapter);

    }
}
