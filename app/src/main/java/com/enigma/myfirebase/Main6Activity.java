package com.enigma.myfirebase;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.concurrent.ExecutionException;

public class Main6Activity extends AppCompatActivity {
    DatabaseReference databaseReference;
    StorageReference storageReference;
    int position;
    String foto;
    String imagen;
    ImageView imageView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main6);
        databaseReference = FirebaseDatabase.getInstance().getReference();
        Intent intent= getIntent();
        position = intent.getIntExtra("position", -1);
        storageReference = FirebaseStorage.getInstance().getReference();
        foto= Main7Activity.productos.get(position).getImagentok();
        imageView = findViewById(R.id.imageView);
        EditText editText1 = findViewById(R.id.editText4);
        EditText editText2 = findViewById(R.id.editText3);
        EditText editText3 = findViewById(R.id.editText5);
        editText1.setText(Main7Activity.productos.get(position).nombre);
        editText2.setText(Main7Activity.productos.get(position).precio);
        editText3.setText(Main7Activity.productos.get(position).descripcion);
        imagen= Main7Activity.productos.get(position).imagen;
        ImageTask imageTask = new ImageTask();
        Bitmap bitmap = null;
        try {
            bitmap = imageTask.execute(Main7Activity.productos.get(position).imagen).get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        imageView.setImageBitmap(bitmap);
    }
    public void execute(final Producto producto, final Producto producto2, final int mode){
        //databaseReference.child(producto.getNombre()).removeValue(); // esto si lo hace
        databaseReference.child("products").orderByChild("nombre").equalTo(producto.getNombre()).addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                if(mode==0){
                    dataSnapshot.getRef().setValue(producto2);
                }
                else{
                    dataSnapshot.getRef().removeValue();
                }
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
    public void update(View view){
        EditText editText1 = findViewById(R.id.editText4);
        EditText editText2 = findViewById(R.id.editText3);
        EditText editText3 = findViewById(R.id.editText5);
        String nombre= editText1.getText().toString();
        String precio= editText2.getText().toString();
        String desc= editText3.getText().toString();
        if(!nombre.equals("") && !precio.equals("") && !desc.equals("") && !imagen.equals("")) {
            Producto producto = new Producto(nombre, precio, desc, imagen, foto);
            execute(Main7Activity.productos.get(position),producto,0);
            finish();
        }

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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu3, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        super.onOptionsItemSelected(item);

        if(item.getItemId()== R.id.delete){
            execute(Main7Activity.productos.get(position),null,1);
            finish();
        }
        return true;
    }
}
