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
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;

public class BusDriverRegisterActivity extends AppCompatActivity {

    TextInputEditText editTextEmail, editTextPassword;
    Button register;
    TextView login;
    FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference usersRef = database.getReference("Users");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bus_driver_register);

        editTextEmail = findViewById(R.id.email);
        editTextPassword = findViewById(R.id.password);
        register = findViewById(R.id.register);
        login = findViewById(R.id.login);

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email, password;
                email = String.valueOf(editTextEmail.getText());
                password = String.valueOf(editTextPassword.getText());

                if (TextUtils.isEmpty(email)) {
                    Toast.makeText(BusDriverRegisterActivity.this, "Enter Email", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (TextUtils.isEmpty(password)) {
                    Toast.makeText(BusDriverRegisterActivity.this, "Enter Password", Toast.LENGTH_SHORT).show();
                    return;
                }

                firebaseAuth.createUserWithEmailAndPassword(email, password)
                        .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    // User is registered, now store their type
                                    String userId = firebaseAuth.getCurrentUser().getUid();
                                    DatabaseReference userRef = usersRef.child(userId);
                                    Map<String, Object> userMap = new HashMap<>();
                                    userMap.put("userType", "busDriver");
                                    userRef.updateChildren(userMap);
                                    Toast.makeText(BusDriverRegisterActivity.this, "Registration Successfully", Toast.LENGTH_SHORT).show();
                                    Intent intent = new Intent(BusDriverRegisterActivity.this, Home.class);
                                    startActivity(intent);
                                    finish();
                                } else {
                                    Toast.makeText(BusDriverRegisterActivity.this, "Registration Failed", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
            }
        });

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(BusDriverRegisterActivity.this, BusDriverLoginActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }
}