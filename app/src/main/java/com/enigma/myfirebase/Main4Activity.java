package com.enigma.myfirebase;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class Main4Activity extends AppCompatActivity {
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main4);
        mAuth = FirebaseAuth.getInstance();
    }

    public void click(View view){
        EditText editText= findViewById(R.id.editText);
        EditText editText2= findViewById(R.id.editText2);
        String email= editText.getText().toString();
        String password= editText2.getText().toString();
        if(!email.equals("") && !password.equals("")){
            if(password.length()>=8){
            mAuth.createUserWithEmailAndPassword(email, password)
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                // Sign in success, update UI with the signed-in user's information
                                Log.d("", "createUserWithEmail:success");
                                FirebaseUser user = mAuth.getCurrentUser();
                                finish();
                            } else {
                                // If sign in fails, display a message to the user.
                                Log.w("", "createUserWithEmail:failure", task.getException());
                                Toast.makeText(Main4Activity.this, "Fallo de autenticación.",
                                        Toast.LENGTH_SHORT).show();
                            }

                            // ...
                        }
                    });}
            else{
                Toast.makeText(Main4Activity.this, "Contraseña debe ser mayor a 8 caracteres",
                        Toast.LENGTH_SHORT).show();
            }
        }
        else{
            Toast.makeText(Main4Activity.this, "Ingrese ambos campos de texto",
                    Toast.LENGTH_SHORT).show();
        }
    }
}
