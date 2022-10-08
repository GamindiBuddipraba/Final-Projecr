package com.codebysl.greenlanka;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class ComplainAdapter extends BaseAdapter {

    private Context mContext;

    private ArrayList<String> comp_desc = new ArrayList<String>();
    private ArrayList<String> contact = new ArrayList<String>();



    public ComplainAdapter(Context  context,ArrayList<String> comp_desc, ArrayList<String> contact
    )
    {
        this.mContext = context;
        this.comp_desc= comp_desc;
        this.contact = contact;
    }
    @Override
    public int getCount() {
        return comp_desc.size();
    }
    @Override
    public Object getItem(int position) {
        return null;
    }
    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final ComplainAdapter.viewHolder holder;
        LayoutInflater layoutInflater;
        if (convertView == null) {
            layoutInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = layoutInflater.inflate(R.layout.complain_view_layout, null);
            holder = new ComplainAdapter.viewHolder();
            holder.complain = (TextView) convertView.findViewById(R.id.tv_comp_desc);
            holder.contactNo = (TextView) convertView.findViewById(R.id.tv_comp_contact);
            convertView.setTag(holder);
        } else {
            holder = (ComplainAdapter.viewHolder) convertView.getTag();
        }

        holder.complain.setText(comp_desc.get(position));
        holder.contactNo.setText(contact.get(position));
        return convertView;

    }
    public class viewHolder {

        TextView complain;
        TextView contactNo;
    }
}
