package com.example.mycar.ui.Car;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.mycar.R;

public class AddCar extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_car);
        setTitle("Добавление автомобиля");
    }
}