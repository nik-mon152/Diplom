package com.example.mycar.ui.Profile;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.EditText;

import com.example.mycar.R;

public class EditProfile extends AppCompatActivity {

    EditText profileName, profileLastName, profileEmail, profileNumber;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);
        getSupportActionBar().hide();

        Intent data = getIntent();
        String name = data.getStringExtra("Имя");
        String lastName = data.getStringExtra("Фамилия");
        String email = data.getStringExtra("Почта");
        String number = data.getStringExtra("Номер телефона");

        profileName = findViewById(R.id.textName);
        profileLastName = findViewById(R.id.textLastName);
        profileEmail = findViewById(R.id.textEmail);
        profileNumber = findViewById(R.id.textNumber);

        profileName.setText(name);
        profileLastName.setText(lastName);
        profileEmail.setText(email);
        profileNumber.setText(number);
    }
}