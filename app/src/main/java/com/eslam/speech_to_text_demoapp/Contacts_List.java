package com.eslam.speech_to_text_demoapp;

import android.Manifest;
import android.content.ContentResolver;
import android.database.Cursor;
import android.graphics.Canvas;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.karumi.dexter.Dexter;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.single.PermissionListener;

import java.util.ArrayList;

import it.xabaras.android.recyclerview.swipedecorator.RecyclerViewSwipeDecorator;

public class Contacts_List extends Fragment implements ContactList_Adapter.SelectUser {
    //    List<ContactModel> contactList;
    private ArrayList<ContactModel> contactList = new ArrayList<>();

    private RecyclerView recyclerView;
    private ProgressBar progressBar;
    private ContactList_Adapter adapter;

    private Animation fadeInAnim;
    private Animation fadeOutAnim;


    ItemTouchHelper.SimpleCallback simpleCallback = new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
        @Override
        public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
            return false;
        }

        @Override
        public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
            int position = viewHolder.getAdapterPosition();
            String number = contactList.get(position).getPhoneNumber();


            switch (direction) {
                case ItemTouchHelper.RIGHT:
                  /*  Toast.makeText(getContext(), "Send Sms" + position, Toast.LENGTH_SHORT).show();
                    String number = contactList.get(position).getPhoneNumber();
                    number = MainActivity.charRemoveAt(number, 0);*/
                    ((MainActivity) getActivity())._SendMessage(number.trim(), "Love U");

                    adapter.notifyDataSetChanged();
                    break;
                case ItemTouchHelper.LEFT:
                    Toast.makeText(getContext(), "Make a call" + position, Toast.LENGTH_SHORT).show();
                    adapter.notifyDataSetChanged();
                    ((MainActivity) getActivity()).makePhoneCall(number.trim());

                    break;
            }

        }

        @Override
        public void onChildDraw(@NonNull Canvas c, @NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive) {
            new RecyclerViewSwipeDecorator.Builder(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive)
                    .addSwipeLeftBackgroundColor(ContextCompat.getColor(getContext(), R.color.callBackground))
                    .addSwipeLeftActionIcon(R.drawable.call_ico)
                    .addSwipeLeftLabel("Call")
    
                    .addSwipeRightBackgroundColor(ContextCompat.getColor(getContext(),R.color.smsBackground))
                    .addSwipeRightActionIcon(R.drawable.sms_ico)
                    .addSwipeRightLabel("SMS")

                    .create()
                    .decorate();
            super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);

        }
    };

    public Contacts_List() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_contacts__list, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        fadeInAnim = AnimationUtils.loadAnimation(getContext(), R.anim.fade_in);
        fadeOutAnim = AnimationUtils.loadAnimation(getContext(), R.anim.fade_out);
        recyclerView = view.findViewById(R.id.list_contact);
        progressBar = view.findViewById(R.id.list_contact_progress);

        adapter = new ContactList_Adapter(getContext(), contactList , this);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(adapter);
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(simpleCallback);
        itemTouchHelper.attachToRecyclerView(recyclerView);

        recyclerView.startAnimation(fadeInAnim);
        progressBar.startAnimation(fadeOutAnim);
//        getContacts();

        Dexter.withContext(getContext())
                .withPermission(Manifest.permission.READ_CONTACTS)
                .withListener(new PermissionListener() {
                    @Override
                    public void onPermissionGranted(PermissionGrantedResponse permissionGrantedResponse) {
                        if (permissionGrantedResponse.getPermissionName().equals(Manifest.permission.READ_CONTACTS)) {
                            recyclerView.startAnimation(fadeInAnim);
                            progressBar.startAnimation(fadeOutAnim);
                            getContacts();
                        }

                    }

                    @Override
                    public void onPermissionDenied(PermissionDeniedResponse permissionDeniedResponse) {
                        Toast.makeText(getContext(), "Permission Denied", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onPermissionRationaleShouldBeShown(PermissionRequest permissionRequest, PermissionToken permissionToken) {
                        permissionToken.continuePermissionRequest();
                    }
                }).check();
    }

    private void getContacts() {
        ContentResolver contentResolver = getActivity().getContentResolver();

        Cursor phones = contentResolver.query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                null, null, null, null);
        while (phones.moveToNext()) {
            String name = phones.getString(phones.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME));
            String phone__number = phones.getString(phones.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
//            String imgUri = phones.getString(phones.getColumnIndex(ContactsContract.CommonDataKinds.Phone.PHOTO_URI));

            contactList.add(new ContactModel(name, phone__number));
            adapter.notifyDataSetChanged();
        }

    }

    @Override
    public void selectedUser(ContactModel contactModel) {

        ((MainActivity)getActivity())._Transfer_Text_To_Voice(contactModel.getName());
    }
}

