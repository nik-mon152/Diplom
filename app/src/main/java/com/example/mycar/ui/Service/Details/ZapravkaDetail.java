package com.example.mycar.ui.Service.Details;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.MenuItem;
import android.widget.TextView;

import com.example.mycar.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Objects;

public class ZapravkaDetail extends AppCompatActivity {

    Intent zapravkaDetail;
    TextView fuelView, count, cumm, price, probeg, comment, data;
    FirebaseFirestore fstore;
    FirebaseUser user;
    FirebaseAuth fAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_zapravka_detail);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        setTitle("Просмотр данных");

        fuelView = findViewById(R.id.viewFuelAdd);
        count = findViewById(R.id.countAdd);
        cumm = findViewById(R.id.cummAdd);
        price = findViewById(R.id.priceAdd);
        probeg = findViewById(R.id.probegAdd);
        comment = findViewById(R.id.commentAdd);
        data = findViewById(R.id.dataAdd);


        fstore = FirebaseFirestore.getInstance();
        fAuth = FirebaseAuth.getInstance();
        user = fAuth.getCurrentUser();


        zapravkaDetail = getIntent();

        fuelView.setText(zapravkaDetail.getStringExtra("Fuel"));
        count.setText(zapravkaDetail.getStringExtra("Cumm"));
        cumm.setText(zapravkaDetail.getStringExtra("Litr"));
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