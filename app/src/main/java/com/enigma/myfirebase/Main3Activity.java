package com.enigma.myfirebase;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.IOException;
import java.util.Random;
import java.util.concurrent.ExecutionException;

public class Main3Activity extends AppCompatActivity {

    StorageReference storageReference;
    static final String AB = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
    static Random rnd = new Random();
    String foto;
    String imagen;
    ProgressDialog progressDialog;
    ImageView imageView;
    DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main3);
        storageReference = FirebaseStorage.getInstance().getReference();
        foto= getRandom(10);
        progressDialog = new ProgressDialog(this);
        imageView= findViewById(R.id.imageView);
    }

    public void uploadPhoto(View view){
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");
        startActivityForResult(intent, 1);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode== 1 && resultCode== RESULT_OK){
            Uri uri = data.getData();
            StorageReference filePath = storageReference.child("fotos").child(foto);
            filePath.putFile(uri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    Uri descarga = taskSnapshot.getDownloadUrl();
                    imagen= descarga.toString();
                    Log.d("FOTO", descarga.toString());
                    ImageTask imageTask = new ImageTask();
                    try {
                        Bitmap bitmap =  imageTask.execute(descarga.toString()).get();
                        imageView.setImageBitmap(bitmap);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    } catch (ExecutionException e) {
                        e.printStackTrace();
                    }
                    imageView.setBackground(null);

                    Toast.makeText(getApplicationContext(),"Imagen ingresada",Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

    public String getRandom(int len){
        StringBuilder sb = new StringBuilder( len );
        for( int i = 0; i < len; i++ )
            sb.append( AB.charAt( rnd.nextInt(AB.length()) ) );
        return sb.toString();
    }

    public void click(View view){
        EditText editText1 = findViewById(R.id.editText4);
        EditText editText2 = findViewById(R.id.editText3);
        EditText editText3 = findViewById(R.id.editText5);
        String nombre= editText1.getText().toString();
        String precio= editText2.getText().toString();
        String desc= editText3.getText().toString();
        if(!nombre.equals("") && !precio.equals("") && !desc.equals("") && !imagen.equals("")) {
            Producto producto = new Producto(nombre, precio, desc, imagen, foto);
            DatabaseReference reference = FirebaseDatabase.getInstance().getReference();
            databaseReference = reference.child("products");
            String id = databaseReference.push().getKey();
            databaseReference.child(id).setValue(producto).addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void aVoid) {
                    Toast.makeText(getApplicationContext(),"Producto ingresado",Toast.LENGTH_SHORT).show();
                    finish();
                }
            });

        }
    }
}
