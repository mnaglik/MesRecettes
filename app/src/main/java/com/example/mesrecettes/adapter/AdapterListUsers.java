package com.example.mesrecettes.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.mesrecettes.R;
import com.example.mesrecettes.model.Users;

import java.util.List;

public class AdapterListUsers extends BaseAdapter { //activité qui permet l'affichage de la liste des users
    private Context context;
    private List<Users> listUsers;
    private LayoutInflater inflater;

    public AdapterListUsers(Context context, List<Users> listUsers) {
        this.context = context;
        this.listUsers = listUsers;
        this.inflater = LayoutInflater.from(context);
    }

//methode qui permet de savoir combien de users il ya a dans la table
    @Override
    public int getCount() {
        return listUsers.size();
    }

    //permet de recupere un user en fonction de sa position dans la liste
    @Override
    public Object getItem(int position) {
        return listUsers.get(position);
    }

    //permet de recupere l'id du user a la position donnée
    @Override
    public long getItemId(int position) {
        return listUsers.get(position).getId();
    }

    //permet d'inserer les user à la vue.
    @Override
    public View getView(int position, View convertView, ViewGroup viewGroup) {
        convertView = inflater.inflate(R.layout.layout_user,null);

        Users user = (Users)getItem(position);

        TextView txtId = convertView.findViewById(R.id.txtId);
        txtId.setText(""+user.getId());

        TextView txtIdentifiant = convertView.findViewById(R.id.txtnom);
        txtIdentifiant.setText(""+user.getIdentifiant().toString());

        TextView txtPassword = convertView.findViewById(R.id.txtcategorie);
        txtPassword.setText(""+user.getPassword().toString());

        TextView txtLevel = convertView.findViewById(R.id.txtLevel);
        txtLevel.setText(""+user.getLevel().toString());



        return convertView;
    }
}
