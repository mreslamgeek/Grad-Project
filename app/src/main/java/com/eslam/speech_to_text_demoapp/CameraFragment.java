    package com.eslam.speech_to_text_demoapp;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.service.media.MediaBrowserService;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class CameraFragment extends Fragment implements View.OnClickListener {

    private Button btn_photo, btn_vedio;
    private ImageView imageView;
    private static final int REQUEST_IMAGE_CAPTURE = 101;
    private   static final int REQUEST_VIDEO_CAPTURE = 100;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_camera, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        imageView=view.findViewById(R.id.imgView);
        btn_vedio=view.findViewById(R.id.btn_video);
        btn_photo=view.findViewById(R.id.btn_photo);
        btn_photo.setOnClickListener(this);
        btn_vedio.setOnClickListener(this);

    }



    @Override
    public void onClick(View view) {
        Intent takeVideoIntent = new Intent(MediaStore.ACTION_VIDEO_CAPTURE);
        Intent imageTakeIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);


     /*   if (takeVideoIntent.resolveActivity(getContext().getPackageManager()) != null) {
            startActivityForResult(takeVideoIntent, REQUEST_VIDEO_CAPTURE);

        }
         else if (imageTakeIntent.resolveActivity(getContext().getPackageManager()) != null) {
            startActivityForResult(imageTakeIntent, REQUEST_IMAGE_CAPTURE);

        }

    }*/
        switch (view.getId()) {
            case R.id.btn_video:
                if (takeVideoIntent.resolveActivity(getContext().getPackageManager()) != null) {
                    startActivityForResult(takeVideoIntent, REQUEST_VIDEO_CAPTURE);
                }
                break;
            case R.id.btn_photo:
                if (imageTakeIntent.resolveActivity(getContext().getPackageManager()) != null) {
                    startActivityForResult(imageTakeIntent, REQUEST_IMAGE_CAPTURE);

                }
                break;

        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {

        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == android.app.Activity.RESULT_OK) {
            Bundle extras = data.getExtras();
            Bitmap imageBitmap = (Bitmap) extras.get("data");
            imageView.setImageBitmap(imageBitmap);
        }
    }
}



