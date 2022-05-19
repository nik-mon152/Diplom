package com.example.mycar.ui.Service.Details;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Handler;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;

import com.example.mycar.R;
import com.example.mycar.ui.Service.Model.Servise;
import com.example.mycar.ui.Service.Model.Zapravka;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Objects;

public class ServiseDetail extends AppCompatActivity {

    EditText work, adress, price, probeg, comment, data, view_work;
    Button addServise;
    Servise servise = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_servise_detail);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        setTitle("Просмотр данных");

        final Object object = getIntent().getSerializableExtra("detail_servise");
        if (object instanceof Servise){
            servise = (Servise) object;
        }

        view_work = findViewById(R.id.ViewWork);
        work = findViewById(R.id.NameWork);
        adress = findViewById(R.id.InfoAdress);
        probeg = findViewById(R.id.servProbeg);
        comment = findViewById(R.id.servComment);
        price = findViewById(R.id.priceServise);
        data = findViewById(R.id.textDate);
        addServise = findViewById(R.id.addServ);

        if (servise != null){
            view_work.setText(servise.getView_Servise());
            work.setText(servise.getName_work());
            adress.setText(servise.getAdress());
            probeg.setText(servise.getMileage() + "");
            comment.setText(servise.getComment());
            price.setText(servise.getPrice_servise() + "");
            data.setText(servise.getData());
        }
    }
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();

        if (id == androidx.appcompat.R.id.action_bar){

            Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                public void run() {
                    onBackPressed();
                }
            }, 10000);
        }else if (id == android.R.id.home){
            finish();
        }
        return super.onOptionsItemSelected(item);
    }
}