package com.example.baitap;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class ContactAdapter extends BaseAdapter {
    Context context;
    ArrayList<Contact> contacts;

    public ContactAdapter(Context context, ArrayList<Contact> contacts) {
        this.context = context;
        this.contacts = contacts;
    }

    @Override
    public int getCount() {
        return contacts.size();
    }

    @Override
    public Object getItem(int position) {
        return contacts.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;

        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.line_contact, parent, false);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        Contact contactCurrent = (Contact) getItem(position);
        viewHolder.ID.setText(String.valueOf(contactCurrent.getId()));
        viewHolder.Name.setText(contactCurrent.getName());
        viewHolder.PhoneNum.setText(contactCurrent.getPhoneNumber());
        return convertView;
    }

    //class viewHolder tránh giảm giật
    private class ViewHolder{
        TextView ID;
        TextView Name;
        TextView PhoneNum;

        public ViewHolder(View view){
            ID = (TextView) view.findViewById(R.id.tvID);
            Name = (TextView) view.findViewById(R.id.tvName);
            PhoneNum = (TextView) view.findViewById(R.id.tvNum);

        }
    }


}
