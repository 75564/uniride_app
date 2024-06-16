package com.example.myregister;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class BusDriverLoginActivity extends AppCompatActivity {

    TextInputEditText editTextEmail, editTextPassword;
    Button login;
    TextView register;
    FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bus_driver_login);

        editTextEmail = findViewById(R.id.email);
        editTextPassword = findViewById(R.id.password);
        login = findViewById(R.id.login);
        register = findViewById(R.id.register);

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email, password;
                email = String.valueOf(editTextEmail.getText());
                password = String.valueOf(editTextPassword.getText());

                if (TextUtils.isEmpty(email)) {
                    Toast.makeText(BusDriverLoginActivity.this, "Enter Email", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (TextUtils.isEmpty(password)) {
                    Toast.makeText(BusDriverLoginActivity.this, "Enter Password", Toast.LENGTH_SHORT).show();
                    return;
                }

                firebaseAuth.signInWithEmailAndPassword(email, password)
                        .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    // User is authenticated, now check their type
                                    String userId = firebaseAuth.getCurrentUser().getUid();
                                    DatabaseReference userRef = FirebaseDatabase.getInstance().getReference().child("Users").child(userId);
                                    userRef.addValueEventListener(new ValueEventListener() {
                                        @Override
                                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                            if (dataSnapshot.exists()) {
                                                String userType = dataSnapshot.child("userType").getValue().toString();
                                                if (userType.equals("busDriver")) {
                                                    // User is a bus driver, allow the login
                                                    Toast.makeText(BusDriverLoginActivity.this, "Login Successfully", Toast.LENGTH_SHORT).show();
                                                    Intent intent = new Intent(BusDriverLoginActivity.this, Home.class);
                                                    startActivity(intent);
                                                    finish();
                                                } else {
                                                    // User is not a bus driver, deny the login
                                                    Toast.makeText(BusDriverLoginActivity.this, "Authentication Failed", Toast.LENGTH_SHORT).show();
                                                    firebaseAuth.signOut();
                                                }
                                            }
                                        }

                                        @Override
                                        public void onCancelled(@NonNull DatabaseError databaseError) {
                                            // Handle error
                                        }
                                    });
                                } else {
                                    Toast.makeText(BusDriverLoginActivity.this, "Authentication Failed", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
            }
        });

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(BusDriverLoginActivity.this, BusDriverRegisterActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }
}