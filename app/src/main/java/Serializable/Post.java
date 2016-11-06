package Serializable;

import java.io.Serializable;



public class Post implements Serializable{
    private static final long serialVersionUID = 1L;

    public String nombreUsuario;
    //todavia no se como obtener la fecha, asiganda como 0-0-0-0 temporalmente
    public String fecha;
    public String contenidoPost;
    public byte[] imagen;
    public String nameImage;
    public String imgURL;
    public Post(String name, String fecha, String contenidoPost, byte[] img,String nameImage){
        this.nombreUsuario=name;
        this.fecha=fecha;
        this.contenidoPost=contenidoPost;
        this.imagen=img;
        this.nameImage=nameImage;
    }

    public Post(String name, String fecha, String contenidoPost, String rutaImg){
        this.nombreUsuario=name;
        this.fecha=fecha;
        this.contenidoPost=contenidoPost;
        this.imgURL=rutaImg;
    }



}
