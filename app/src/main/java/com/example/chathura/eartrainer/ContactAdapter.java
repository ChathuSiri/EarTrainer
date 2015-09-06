package com.example.chathura.eartrainer;

/**
 * Created by Chathura on 8/22/2015.
 */
import android.content.ClipData;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

public class ContactAdapter extends RecyclerView.Adapter<ContactAdapter.ContactViewHolder> {

    private List<ContactInfo> contactList;

    public ContactAdapter(List<ContactInfo> contactList) {
        this.contactList = contactList;
    }


    @Override
    public int getItemCount() {
        return contactList.size();
    }

    @Override
    public void onBindViewHolder(ContactViewHolder contactViewHolder, int i) {
        ContactInfo ci = contactList.get(i);

        contactViewHolder.title.setText(ci.getTitle());
        contactViewHolder.image.setImageResource(ci.getImage());
        contactViewHolder.description.setText(ci.getDescription());

        //viewHolder.currentItem = items.get(i);

    }

    @Override
    public ContactViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View itemView = LayoutInflater.
                from(viewGroup.getContext()).
                inflate(R.layout.my_card_view, viewGroup, false);
        return new ContactViewHolder(itemView);
    }

    public static class ContactViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public ImageView image;
        public TextView title;
        public TextView description;


        public ContactViewHolder(View itemView) {
            super(itemView);
            image = (ImageView)itemView.findViewById(R.id.image);
            title = (TextView)itemView.findViewById(R.id.title);
            description = (TextView)itemView.findViewById(R.id.description);

            itemView.setOnClickListener(this);

        }
        @Override
        public void onClick(View v) {
            int num = this.getAdapterPosition();
            Toast.makeText(v.getContext(),num+"is clicked",Toast.LENGTH_LONG).show();



        }
    }
}


