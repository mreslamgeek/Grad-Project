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
import androidx.fragment.app.DialogFragment;

import java.util.ArrayList;

public class MyGestureDialog extends DialogFragment implements GestureOverlayView.OnGesturePerformedListener {
    private GestureLibrary gLibrary;
    private GestureOverlayView overlayView;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.layout_gesture_dialog, container, false);

        overlayView = view.findViewById(R.id.overlay_gesture);
        gestureSetup();

        return view;
    }

    private void gestureSetup() {
        gLibrary = GestureLibraries.fromRawResource(getContext(), R.raw.gesture);
        if (!gLibrary.load()) {
            getActivity().finish();
        }
        overlayView.addOnGesturePerformedListener(this);
        overlayView.setGestureStrokeAngleThreshold(90.0f);

    }

    @Override
    public void onGesturePerformed(GestureOverlayView gestureOverlayView, Gesture gesture) {
        ArrayList<Prediction> predictions =
                gLibrary.recognize(gesture);
        if (predictions.size() > 0 && predictions.get(0).score > 1.0) {

            String action = predictions.get(0).name;

            //make check here to see wht gesture detect and perform action

                Toast.makeText(getContext(), action, Toast.LENGTH_SHORT).show();
                dismiss();

        }
    }
}
