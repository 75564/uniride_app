package com.example.myregister;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class ChooseUserActivity extends AppCompatActivity {

    Button chooseStudent, chooseBusDriver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_user);

        chooseStudent = findViewById(R.id.choose_student);
        chooseBusDriver = findViewById(R.id.choose_bus_driver);

        chooseStudent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Navigate to StudentRegisterActivity or StudentLoginActivity
                Intent intent = new Intent(ChooseUserActivity.this, Register.class);
                startActivity(intent);
            }
        });

        chooseBusDriver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Navigate to BusDriverRegisterActivity or BusDriverLoginActivity
                Intent intent = new Intent(ChooseUserActivity.this, BusDriverRegisterActivity.class);
                startActivity(intent);
            }
        });
    }
}