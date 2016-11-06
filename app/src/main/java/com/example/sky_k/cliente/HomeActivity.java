package com.example.sky_k.cliente;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.Observer;
import java.util.Vector;

import Serializable.Post;

public class HomeActivity extends AppCompatActivity implements Observer{
private ArrayList<Post> postes;
    RecyclerView rv;
    LinearLayoutManager llm;
    Adaptador ad;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        postes= new ArrayList<>();
        Comunicacion.getInstance().addObserver(this);
        pedirPostes();

    }




    private void pedirPostes(){
        Comunicacion.getInstance().enviar("post_req");
    }

    @Override
    public void update(Observable o, Object arg) {
       if(arg instanceof ArrayList){
           postes= (ArrayList<Post>) arg;

           System.out.println("recibidoPosts");
           System.out.println(postes.size());
           System.out.println(postes.get(0).contenidoPost);
           runOnUiThread(new Runnable() {
               @Override
               public void run() {
                   rv= (RecyclerView) findViewById(R.id.Lista);
                   llm= new LinearLayoutManager(HomeActivity.this);
                   rv.setLayoutManager(llm);
                   ad=new Adaptador(postes);
                   rv.setAdapter(ad);
               }
           });


       }
    }
}
