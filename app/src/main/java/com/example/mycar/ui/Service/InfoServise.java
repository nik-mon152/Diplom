package com.example.mycar.ui.Service;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.TextView;

import com.example.mycar.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;

public class InfoServise extends AppCompatActivity {

    TextView adress, schedule, contacts, site;
    FirebaseFirestore fstore;
    FirebaseAuth fAuth;
    FirebaseUser user;
    String userID;

    @SuppressLint("WrongViewCast")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info_servise);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setTitle("Информация о сервисе");

        fstore = FirebaseFirestore.getInstance();
        fAuth = FirebaseAuth.getInstance();
        user = fAuth.getCurrentUser();
        userID = fAuth.getCurrentUser().getUid();

        adress = findViewById(R.id.InfoAdress);
        schedule = findViewById(R.id.InfoSchedule);
        contacts = findViewById(R.id.InfoContacts);
        site = findViewById(R.id.InfoSite);

        DocumentReference documentReference = fstore.collection("Servise").document("ServiseInfo");
        documentReference.addSnapshotListener(new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot documentSnapshot, @Nullable FirebaseFirestoreException error) {
                if (documentSnapshot != null) {
                    if(documentSnapshot.exists()){
                        adress.setText(documentSnapshot.getString("Adress"));
                        schedule.setText(documentSnapshot.getString("Schedule"));
                        contacts.setText(documentSnapshot.getString("Contacts"));
                        site.setText(documentSnapshot.getString("Site"));
                    }else{
                        Log.d("Сообщение об ошибке", "Ошибка в документе");
                    }
                }
            }
        });
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