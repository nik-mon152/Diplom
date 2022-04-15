package com.example.mycar.ui.Profile;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.mycar.Authentication.LoginActivity;
import com.example.mycar.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;


public class ProfileFragment extends Fragment {

    TextView fullname, lastname, email, number;
    Button bntLogout;
    FirebaseAuth fAuth;
    FirebaseFirestore fstore;
    String userId;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_profile, null);


        fullname = v.findViewById(R.id.profileFirsName);
        lastname = v.findViewById(R.id.profileLastName);
        email = v.findViewById(R.id.profileEmail);
        number = v.findViewById(R.id.profilePhone);

        fAuth = FirebaseAuth.getInstance();
        fstore = FirebaseFirestore.getInstance();

        userId = fAuth.getCurrentUser().getUid();

        DocumentReference documentReference = fstore.collection("Users").document(userId);
        documentReference.addSnapshotListener(new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot documentSnapshot, @Nullable FirebaseFirestoreException error) {
                fullname.setText(documentSnapshot.getString("FirsName"));
                lastname.setText(documentSnapshot.getString("LastName"));
                email.setText(documentSnapshot.getString("Email"));
                number.setText(documentSnapshot.getString("Number"));
            }
        });

        bntLogout = v.findViewById(R.id.btnLogout);
        bntLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fAuth.signOut();
                startActivity(new Intent(getActivity(), LoginActivity.class));
                onDestroy();
            }
        });
        return v;
    }

}