package com.example.sky_k.cliente;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 1130613425 on 21/10/2015.
 */
public class AdaptadorUsuarios extends ArrayAdapter<Usuario> {

    public AdaptadorUsuarios(Context context, ArrayList<Usuario> users) {
        super(context, 0, users);
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Get the data item for this position
        Usuario user = getItem(position);
        // Check if an existing view is being reused, otherwise inflate the view
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.modelo_lista_usuario_mensaje, parent, false);
        }
        // Lookup view for data population
        TextView tvNombreUsuario = (TextView) convertView.findViewById(R.id.tv_txt_user_model);
        TextView tvMensajeUsuario = (TextView) convertView.findViewById(R.id.tv_txt_msn_model);
        // Populate the data into the template view using the data object
        tvNombreUsuario.setText(user.getNombre());
        tvMensajeUsuario.setText(user.getMensaje());
        // Return the completed view to render on screen
        return convertView;
    }
}
