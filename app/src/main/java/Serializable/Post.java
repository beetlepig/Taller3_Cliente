package Serializable;

import java.io.Serializable;



public class Post implements Serializable{
    private static final long serialVersionUID = 1L;

    public String nombreUsuario;
    //todavia no se como obtener la fecha, asiganda como 0-0-0-0 temporalmente
    public String fecha;
    public String contenidoPost;
    public byte[] imagen;

    public Post(String name, String fecha, String contenidoPost, byte[] img){
        this.nombreUsuario=name;
        this.fecha=fecha;
        this.contenidoPost=contenidoPost;
        this.imagen=img;
    }

}
