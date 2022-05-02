package com.example.mycar.ui.Car;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.mycar.R;

public class AddCar extends AppCompatActivity {

    TextView marka, model, age, probeg, fuel;
    ImageView carFoto;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_car);
        setTitle("Добавление автомобиля");

        marka = findViewById(R.id.AddMarka);
        model = findViewById(R.id.AddModel);
        age = findViewById(R.id.AddAgeCar);
        probeg = findViewById(R.id.Probeg);
        fuel = findViewById(R.id.ViewFuelCar);
        carFoto = findViewById(R.id.CarFoto);
    }
}