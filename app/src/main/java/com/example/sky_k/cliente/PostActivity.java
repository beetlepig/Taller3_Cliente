package com.example.sky_k.cliente;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.os.PersistableBundle;
import android.provider.MediaStore;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URI;
import java.util.jar.Manifest;

import Serializable.Post;

public class PostActivity extends AppCompatActivity {
    Button btnImg;
    Button btnSendPost;
    EditText contenidoPost;
    private String APP_DIRECTORY = "Intrigue/";
    private String MEDIA_DIRECTORY= APP_DIRECTORY + "Pictures";
   // private String TEMPORAL_PICTURE_NAME = "temporal.jpg";
    private final int PERMISOS= 100;
    private final int PHOTO_CODE = 200;
    private final int SELECT_PICTURE = 300;
    private final CharSequence[] OPCIONES= {"Capturar Fotografia","Elegir de la Galeria","Cancelar"};
    private AlertDialog.Builder ventanaDialogo;
    private String mPath;
    private String name;

    Bitmap img;
    byte[] imgSer;
    File imangesita;
    String nombreImagen;
    private RelativeLayout rLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post);
        Intent lanzador = getIntent();
        name = lanzador.getStringExtra("user");

        btnImg= (Button) findViewById(R.id.btn_LoadImage);
        btnSendPost= (Button) findViewById(R.id.btn_SendPost);
        rLayout= (RelativeLayout) findViewById(R.id.activity_post);
        contenidoPost= (EditText) findViewById(R.id.text_Post);

        if(PermisosAceptados()){
            btnImg.setEnabled(true);
        }else {
            btnImg.setEnabled(false);
        }

        btnSendPost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(img!=null && contenidoPost.toString()!=null){
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            byte[] b = new byte[(int) imangesita.length()];
                            try {
                                FileInputStream fileInputStream = new FileInputStream(imangesita);
                                fileInputStream.read(b);
                                fileInputStream.close();
                                imgSer= b;
                                nombreImagen= imangesita.getName();


                            }catch (Exception e){
                                e.printStackTrace();
                            }



                            Post postAEnviar= new Post(name,"0-0-0-0",contenidoPost.getText().toString(),imgSer, nombreImagen);
                            Comunicacion.getInstance().enviarObjeto(postAEnviar);
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    Toast.makeText(PostActivity.this,"PostEnviado",Toast.LENGTH_LONG).show();
                                }
                            });

                        }
                    }).start();



                }else{
                    Toast.makeText(PostActivity.this,"Selecciona una imagen e ingresa una descripcion",Toast.LENGTH_LONG).show();
                }

            }
        });

        btnImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               ventanaDialogo= new AlertDialog.Builder(PostActivity.this);
                ventanaDialogo.setTitle("Agregar Una Imagen");
                ventanaDialogo.setItems(OPCIONES, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int SelectionNumber) {
                        switch (SelectionNumber){
                            case 0:
                               AbrirCamara();



                                break;

                            case 1:
                                Intent intento=  new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI);
                                intento.setType("image/*");
                                startActivityForResult(intento.createChooser(intento, "Selecionar Aplicacion"),SELECT_PICTURE);
                                break;

                            case 2:
                                dialog.dismiss();

                                break;
                        }
                    }
                });

                ventanaDialogo.show();
            }
        });
    }

    private void AbrirCamara() {
        File file = new File(Environment.getDataDirectory(), MEDIA_DIRECTORY);
        boolean isDirectoryCreated = file.exists();

        if(!isDirectoryCreated)
            isDirectoryCreated = file.mkdirs();

        if(isDirectoryCreated){
            Long timestamp = System.currentTimeMillis() / 1000;
            String imageName = timestamp.toString() + ".jpg";

            mPath = Environment.getDataDirectory() + File.separator + MEDIA_DIRECTORY
                    + File.separator + imageName;

            File newFile = new File(mPath);

            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(newFile));
            startActivityForResult(intent, PHOTO_CODE);
        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(resultCode == RESULT_OK){
            switch (requestCode){
                case PHOTO_CODE:
                    MediaScannerConnection.scanFile(this, new String[]{mPath}, null, new MediaScannerConnection.OnScanCompletedListener() {
                        @Override
                        public void onScanCompleted(String path, Uri uri) {
                            Log.i("Escaneado Papu", "Scanned " + path + ":");
                            Log.i("InternalStorage", "-> Uri = " + uri);
                        }
                    });

                    img= BitmapFactory.decodeFile(mPath);


                    System.out.println("bitmap Guardado");
                    break;

                case SELECT_PICTURE:
                      Uri imagenDeGaleria= data.getData();
                      imangesita=new File( getRealPathFromURI(PostActivity.this, imagenDeGaleria));
                    System.out.println(imangesita.getPath());
                    try {
                        img= MediaStore.Images.Media.getBitmap(this.getContentResolver(),imagenDeGaleria);

                        System.out.println("bitmap Guardado");
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                    break;
            }

        }
    }

    private boolean PermisosAceptados() {
        if(Build.VERSION.SDK_INT<Build.VERSION_CODES.M){
            return true;
        }

        if((checkSelfPermission(android.Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) && (checkSelfPermission(android.Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED)){
           return true;
        }

        if((shouldShowRequestPermissionRationale(android.Manifest.permission.WRITE_EXTERNAL_STORAGE)) || (shouldShowRequestPermissionRationale(android.Manifest.permission.CAMERA))){
            Snackbar.make(rLayout, "Los permisos son necesarios para poder usar la aplicación",
                    Snackbar.LENGTH_INDEFINITE).setAction(android.R.string.ok, new View.OnClickListener() {

                @RequiresApi(api = Build.VERSION_CODES.M)
                @Override
                public void onClick(View v) {
                    requestPermissions(new String[]{android.Manifest.permission.WRITE_EXTERNAL_STORAGE, android.Manifest.permission.CAMERA}, PERMISOS);
                }
            }).show();
        }else {
            requestPermissions(new String[]{android.Manifest.permission.WRITE_EXTERNAL_STORAGE, android.Manifest.permission.CAMERA}, PERMISOS);
        }
        return false;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if(requestCode== PERMISOS){
            if(grantResults.length==2 && grantResults[0]== PackageManager.PERMISSION_GRANTED && grantResults[1]== PackageManager.PERMISSION_GRANTED){
                Toast.makeText(PostActivity.this,"Permisos Aceptados", Toast.LENGTH_SHORT).show();
                btnImg.setEnabled(true);
            }

        }else{
            mostrarExplicacion();
        }
    }

    private void mostrarExplicacion() {
        AlertDialog.Builder dialogo= new AlertDialog.Builder(PostActivity.this);
        dialogo.setTitle("Permisos Necesarios");
        dialogo.setMessage("Es necesario que aceptes los permisos para que la aplicación funcione correctamente");
        dialogo.setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        dialogo.show();
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString("PathImagen", mPath);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        mPath= savedInstanceState.getString("PathImagen");
    }


    public String getRealPathFromURI(Context context, Uri contentUri) {
        Cursor cursor = null;
        try {
            String[] proj = { MediaStore.Images.Media.DATA };
            cursor = context.getContentResolver().query(contentUri,  proj, null, null, null);
            int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
            cursor.moveToFirst();
            return cursor.getString(column_index);
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
    }


}
