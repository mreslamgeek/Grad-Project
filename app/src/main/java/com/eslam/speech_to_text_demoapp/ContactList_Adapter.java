package com.eslam.speech_to_text_demoapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class ContactList_Adapter extends RecyclerView.Adapter<ContactList_Adapter.ContactViewHolder> {
    Context mContext;
    List<ContactModel> contactModelList;
    private SelectUser selectUser;

    public ContactList_Adapter(Context mContext, List<ContactModel> contactModelList, SelectUser selectUser) {
        this.mContext = mContext;
        this.contactModelList = contactModelList;
        this.selectUser = selectUser;
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

    public interface SelectUser {
        void selectedUser(ContactModel contactModel);

    }

    public class ContactViewHolder extends RecyclerView.ViewHolder {
        TextView contact_name, contact_phone;

        public ContactViewHolder(@NonNull View itemView) {
            super(itemView);

            contact_name = itemView.findViewById(R.id.single_item_name);
            contact_phone = itemView.findViewById(R.id.single_item_phonenumber);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    selectUser.selectedUser(contactModelList.get(getAdapterPosition()));
                }
            });

        }
    }
}
