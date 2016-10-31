package com.example.sky_k.cliente;

/**
 * Created by 1130613425 on 21/10/2015.
 */
public class Usuario {

    private String nombre;
    private String mensaje;

    public Usuario(String nombre, String mensaje){
        this.nombre = nombre;
        this.mensaje =  mensaje;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getMensaje() {
        return mensaje;
    }
}
