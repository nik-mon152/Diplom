package com.example.mycar.ui.Service.Details;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.MenuItem;
import android.widget.TextView;

import com.example.mycar.R;
import com.example.mycar.ui.Service.Model.Zapravka;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Objects;

public class ZapravkaDetail extends AppCompatActivity {

    TextView fuelView, count, cumm, price, probeg, comment, data;
    FirebaseFirestore fstore;
    FirebaseUser user;
    FirebaseAuth fAuth;
    Zapravka zapravka = null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_zapravka_detail);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        setTitle("Просмотр данных");

        final Object object = getIntent().getSerializableExtra("detail_zaprav");
        if (object instanceof Zapravka){
            zapravka = (Zapravka) object;
        }

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

         if (zapravka != null){
             fuelView.setText(zapravka.getView_Fuel());
             count.setText(zapravka.getFuel_quantity());
             cumm.setText(zapravka.getRefueling_amount() + "");
             price.setText(zapravka.getPrice_liter());
             probeg.setText(zapravka.getMileage() + "");
             comment.setText(zapravka.getComment());
             data.setText(zapravka.getData());
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