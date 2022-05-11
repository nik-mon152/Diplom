package com.example.mycar.ui.Service.Details;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.mycar.R;
import com.google.firebase.firestore.FirebaseFirestore;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Objects;

public class ZapravkaDetail extends AppCompatActivity {

    Button editZapravka;
    Intent zapravkaDetail;
    TextView fuelView, count, cumm, price, probeg, comment, data;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_zapravka_detail);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        setTitle("Просмотр данных");




        fuelView = findViewById(R.id.viewFuel);
        count = findViewById(R.id.Count);
        cumm = findViewById(R.id.Cumm);
        price = findViewById(R.id.Price);
        probeg = findViewById(R.id.ProbegZapr);
        comment = findViewById(R.id.Comment);
        data = findViewById(R.id.data);


        zapravkaDetail = getIntent();

        fuelView.setText(zapravkaDetail.getStringExtra("Fuel"));
        count.setText(zapravkaDetail.getStringExtra("Litr"));
        cumm.setText(zapravkaDetail.getStringExtra("Cumm"));
        price.setText(zapravkaDetail.getStringExtra("Price"));
        probeg.setText(zapravkaDetail.getStringExtra("Probeg"));
        comment.setText(zapravkaDetail.getStringExtra("Comment"));
        data.setText(zapravkaDetail.getStringExtra("Data"));

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