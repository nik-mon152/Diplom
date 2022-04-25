package com.example.mycar.ui.Profile;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.mycar.MainActivity;
import com.example.mycar.R;

public class EditProfile extends AppCompatActivity {

    EditText profileName, profileLastName, profileEmail, profileNumber;
    Button save, cancel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setTitle("Изменение профиля");
        //getSupportActionBar().hide();

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


        save = findViewById(R.id.btnSave);

    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();

        if (id == androidx.appcompat.R.id.action_bar){
            return true;
        }else if (id == android.R.id.home){
            finish();
        }
        return super.onOptionsItemSelected(item);
    }
}