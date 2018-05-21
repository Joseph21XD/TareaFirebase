package com.enigma.myfirebase;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {
    private FirebaseAuth mAuth;


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu2, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        super.onOptionsItemSelected(item);

        if(item.getItemId()== R.id.register){
            Intent intent= new Intent(MainActivity.this,Main4Activity.class);
            startActivity(intent);
        }
        return true;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mAuth = FirebaseAuth.getInstance();


    }

    @Override
    public void onResume() {
        super.onResume();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if(currentUser!=null){
            Intent intent= new Intent(MainActivity.this,Main7Activity.class);
            startActivity(intent);
        }
    }

    public void click(View view){
        EditText editText= findViewById(R.id.editText);
        EditText editText2= findViewById(R.id.editText2);
        String email= editText.getText().toString();
        String password= editText2.getText().toString();
        if(!email.equals("") && !password.equals("")){
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d("LOGIN", "signInWithEmail:"+mAuth.getCurrentUser().getEmail());
                            FirebaseUser user = mAuth.getCurrentUser();
                            Intent intent= new Intent(MainActivity.this,Main7Activity.class);
                            startActivity(intent);
                            //updateUI(user);
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w("lOGIN", "signInWithEmail:failure", task.getException());
                            Toast.makeText(MainActivity.this, "Error de autenticación",
                                    Toast.LENGTH_SHORT).show();
                            //updateUI(null);
                        }

                        // ...
                    }
                });}
        else{
            Toast.makeText(MainActivity.this, "Datos no ingresados",
                    Toast.LENGTH_SHORT).show();
        }
    }

    public void registrar(View view){
        Intent intent= new Intent(MainActivity.this,Main3Activity.class);
        startActivity(intent);
    }
}
