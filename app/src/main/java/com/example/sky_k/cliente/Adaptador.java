package com.example.sky_k.cliente;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.ExifInterface;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import Serializable.Post;

/**
 * Created by sky_k on 05/11/2016.
 */

public class Adaptador extends RecyclerView.Adapter<Adaptador.PostViewHolder> {
List<Post> listaPosts;

    public Adaptador(List<Post> listaPosts) {
        this.listaPosts = listaPosts;
    }

    @Override
    public PostViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(parent.getContext()).inflate(R.layout.row_item,parent,false);
        PostViewHolder holder= new PostViewHolder(v);

        return holder;
    }

    @Override
    public void onBindViewHolder(PostViewHolder holder, int position) {
     holder.img.setImageBitmap(BitmapFactory.decodeByteArray(listaPosts.get(position).imagen,0,listaPosts.get(position).imagen.length));
     holder.name.setText(listaPosts.get(position).nombreUsuario);
        holder.descripcion.setText(listaPosts.get(position).contenidoPost);
    }

    @Override
    public int getItemCount() {
        return listaPosts.size();
    }

    public static class PostViewHolder extends RecyclerView.ViewHolder{
        ImageView img;
        TextView name;
        TextView descripcion;
        public PostViewHolder(View itemView) {
            super(itemView);
            img= (ImageView) itemView.findViewById(R.id.img_post);
            name= (TextView) itemView.findViewById(R.id.name_user_post);
            descripcion= (TextView) itemView.findViewById(R.id.txt_descripcion);
        }
    }
}
