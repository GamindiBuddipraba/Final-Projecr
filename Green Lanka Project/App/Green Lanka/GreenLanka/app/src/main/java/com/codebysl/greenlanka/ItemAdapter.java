package com.codebysl.greenlanka;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;

public class ItemAdapter extends BaseAdapter {

    private Context mContext;

    private ArrayList<String> item_type = new ArrayList<String>();
    private ArrayList<String> item_desc = new ArrayList<String>();
    private ArrayList<String> item_contact = new ArrayList<String>();
    private ArrayList<String> item_address = new ArrayList<String>();
    private ArrayList<String> item_lat = new ArrayList<String>();
    private ArrayList<String> item_long = new ArrayList<String>();
    private ArrayList<String> status = new ArrayList<String>();
    private ArrayList<String>key1 = new ArrayList<String>();
    private ArrayList<String>key2 = new ArrayList<String>();




    public ItemAdapter(Context  context,ArrayList<String> item_type, ArrayList<String> item_desc, ArrayList<String> item_contact, ArrayList<String> item_address, ArrayList<String> item_lat, ArrayList<String> item_long ,ArrayList<String> status,ArrayList<String> key1,ArrayList<String> key2
    )
    {
        this.mContext = context;
        this.item_type= item_type;
        this.item_desc = item_desc;
        this.item_contact = item_contact;
        this.item_address = item_address;
        this.item_lat = item_lat;
        this.item_long = item_long;
        this.status = status;
        this.key1 = key1;
        this.key2 = key2;

    }
    @Override
    public int getCount() {
        return item_type.size();
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
        final ItemAdapter.viewHolder holder;
        LayoutInflater layoutInflater;
        if (convertView == null) {
            layoutInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

            if(status.get(position).equals("0")){
                convertView = layoutInflater.inflate(R.layout.item_view_layout, null);
            }else{
                convertView = layoutInflater.inflate(R.layout.item_view_layout_2, null);
            }
            holder = new ItemAdapter.viewHolder();

            holder.type = (TextView) convertView.findViewById(R.id.tv_item_type);
            holder.desc = (TextView) convertView.findViewById(R.id.tv_description);
            holder.contact = (TextView) convertView.findViewById(R.id.tv_cno);
            holder.address = (TextView) convertView.findViewById(R.id.tv_address);
            holder.lat = (TextView) convertView.findViewById(R.id.et_lat);
            holder.longti = (TextView) convertView.findViewById(R.id.et_long);
            holder.textkey1 = (TextView) convertView.findViewById(R.id.et_key1);
            holder.textkey2 = (TextView) convertView.findViewById(R.id.et_key2);
            convertView.setTag(holder);
        } else {
            holder = (ItemAdapter.viewHolder) convertView.getTag();
        }

        holder.type.setText(item_type.get(position));
        holder.desc.setText(item_desc.get(position));
        holder.contact.setText(item_contact.get(position));
        holder.address.setText(item_address.get(position));
        holder.lat.setText(item_lat.get(position));
        holder.longti.setText(item_long.get(position));
        holder.textkey1.setText(key1.get(position));
        holder.textkey2.setText(key2.get(position));
        return convertView;



    }
    public class viewHolder {

        TextView type;
        TextView desc;
        TextView contact;
        TextView address;
        TextView lat;
        TextView longti;
        TextView textkey1;
        TextView textkey2;
    }
}
