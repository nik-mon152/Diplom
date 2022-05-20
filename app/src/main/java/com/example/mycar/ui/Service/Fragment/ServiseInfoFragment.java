package com.example.mycar.ui.Service.Fragment;


import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.mycar.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;

public class ServiseInfoFragment extends Fragment {

    TextView adress, schedule, contacts, site;
    FirebaseFirestore fstore;
    FirebaseAuth fAuth;
    FirebaseUser user;
    String userID;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_info_servise, container, false);


        fstore = FirebaseFirestore.getInstance();
        fAuth = FirebaseAuth.getInstance();

        adress = v.findViewById(R.id.InfoAdress);
        schedule = v.findViewById(R.id.InfoSchedule);
        contacts = v.findViewById(R.id.InfoContacts);
        site = v.findViewById(R.id.InfoSite);

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

        return v;
    }
}