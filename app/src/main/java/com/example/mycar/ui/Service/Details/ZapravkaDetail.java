package com.example.mycar.ui.Service.Details;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mycar.R;
import com.example.mycar.ui.Service.AddingService.EditZapravka;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Objects;

public class ZapravkaDetail extends AppCompatActivity {

    Button editZapravka, deleteZapravka;
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

        fuelView = findViewById(R.id.viewFuel);
        count = findViewById(R.id.countEdit);
        cumm = findViewById(R.id.cummEdit);
        price = findViewById(R.id.Price);
        probeg = findViewById(R.id.ProbegEdit);
        comment = findViewById(R.id.commentEdit);
        data = findViewById(R.id.dataEdit);
        editZapravka = findViewById(R.id.EditZaprav);
        deleteZapravka = findViewById(R.id.deleteZapravka);

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
        String docId = zapravkaDetail.getStringExtra("documentId");

        editZapravka.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ZapravkaDetail.this, EditZapravka.class);
                intent.putExtra("Count", zapravkaDetail.getStringExtra("Litr"));
                intent.putExtra("Cumm", zapravkaDetail.getStringExtra("Cumm"));
                intent.putExtra("Price", zapravkaDetail.getStringExtra("Price"));
                intent.putExtra("Probeg", zapravkaDetail.getStringExtra("Probeg"));
                intent.putExtra("Comment", zapravkaDetail.getStringExtra("Comment"));
                intent.putExtra("Data", zapravkaDetail.getStringExtra("Data"));
                intent.putExtra("documentId", zapravkaDetail.getStringExtra("documentId"));
                startActivity(intent);
            }
        });
        deleteZapravka.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DocumentReference documentReference = fstore.collection("Zapravki").document(user.getUid()).collection("myZapravki").document(docId);
                documentReference.delete().addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        Toast.makeText(ZapravkaDetail.this, "Вы удалили запись", Toast.LENGTH_SHORT).show();
                        onBackPressed();
                        finish();
                    }
                });
            }
        });

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