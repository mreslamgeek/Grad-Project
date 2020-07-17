package com.eslam.speech_to_text_demoapp;

import android.gesture.Gesture;
import android.gesture.GestureLibraries;
import android.gesture.GestureLibrary;
import android.gesture.GestureOverlayView;
import android.gesture.Prediction;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import java.util.ArrayList;

public class GesturesDetector extends Fragment implements GestureOverlayView.OnGesturePerformedListener {

    private GestureLibrary gLibrary;
    private GestureOverlayView overlayView;

    public GesturesDetector() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_gestures_detector, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        overlayView = view.findViewById(R.id.overlay);
        gestureSetup();
    }

    private void gestureSetup() {
        gLibrary = GestureLibraries.fromRawResource(getContext(), R.raw.gesture);
        if (!gLibrary.load()) {
            getActivity().finish();
        }
        overlayView.addOnGesturePerformedListener(this);
        overlayView.setGestureStrokeAngleThreshold( 90.0f);

    }

    @Override
    public void onGesturePerformed(GestureOverlayView gestureOverlayView, Gesture gesture) {

        ArrayList<Prediction> predictions =
                gLibrary.recognize(gesture);
        if (predictions.size() > 0 && predictions.get(0).score > 1.0) {

            String action = predictions.get(0).name;

            Toast.makeText(getContext(), action, Toast.LENGTH_SHORT).show();
        }

    }
}