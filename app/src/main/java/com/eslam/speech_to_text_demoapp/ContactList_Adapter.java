package com.eslam.speech_to_text_demoapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class ContactList_Adapter extends RecyclerView.Adapter<ContactList_Adapter.ContactViewHolder> {
    Context mContext;
    List<ContactModel> contactModelList;

    public ContactList_Adapter(Context mContext, List<ContactModel> contactModelList) {
        this.mContext = mContext;
        this.contactModelList = contactModelList;
    }

    @NonNull
    @Override
    public ContactList_Adapter.ContactViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.single_item_contact_list, parent, false);
        return new ContactViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ContactList_Adapter.ContactViewHolder holder, int position) {
        holder.contact_name.setText(contactModelList.get(position).getName());
        holder.contact_phone.setText(contactModelList.get(position).getPhoneNumber());

    }

    @Override
    public int getItemCount() {
        if (contactModelList == null) {
            return 0;
        } else {
            return contactModelList.size();
        }
    }

    public class ContactViewHolder extends RecyclerView.ViewHolder {
        TextView contact_name, contact_phone;
        ImageView contact_photo;
        ImageButton call_btn;

        public ContactViewHolder(@NonNull View itemView) {
            super(itemView);

            contact_name = itemView.findViewById(R.id.single_item_name);
            contact_phone = itemView.findViewById(R.id.single_item_phonenumber);
            contact_photo = itemView.findViewById(R.id.single_item_photo);
            call_btn = itemView.findViewById(R.id.single_item_call_btn);

        }
    }
}
