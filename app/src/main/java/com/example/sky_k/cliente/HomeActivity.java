package com.example.sky_k.cliente;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;
import java.util.Vector;

import Serializable.Post;

public class HomeActivity extends AppCompatActivity implements Observer{
private ArrayList<Post> postes;
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
           

       }
    }
}
