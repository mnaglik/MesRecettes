package com.example.mesrecettes;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

public class AdapterListUsers extends BaseAdapter {
    private Context context;
    private List<Users> listUsers;
    private LayoutInflater inflater;

    public AdapterListUsers(Context context, List<Users> listUsers) {
        this.context = context;
        this.listUsers = listUsers;
        this.inflater = LayoutInflater.from(context);
    }


    @Override
    public int getCount() {
        return listUsers.size();
    }

    @Override
    public Object getItem(int position) {
        return listUsers.get(position);
    }

    @Override
    public long getItemId(int position) {
        return listUsers.get(position).getId();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup viewGroup) {
        convertView = inflater.inflate(R.layout.layout_user,null);

        Users user = (Users)getItem(position);

        TextView txtId = convertView.findViewById(R.id.txtId);
        txtId.setText(""+user.getId());

        TextView txtIdentifiant = convertView.findViewById(R.id.txtIdentifiant);
        txtIdentifiant.setText(""+user.getIdentifiant().toString());

        TextView txtPassword = convertView.findViewById(R.id.txtPassword);
        txtPassword.setText(""+user.getPassword().toString());

        TextView txtLevel = convertView.findViewById(R.id.txtLevel);
        txtLevel.setText(""+user.getLevel().toString());



        return convertView;
    }
}
