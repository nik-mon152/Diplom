package com.example.mycar.ui.Car;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.mycar.R;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

public class CarFragment extends Fragment {
    TextView markaModel, age, probeg, fuel;
    ImageView carFoto;
    Button addCar;
    FirebaseAuth fAuth;
    FirebaseFirestore fstore;
    FirebaseUser user;
    String userId;
    StorageReference storageReference;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_car, container, false);

        markaModel = v.findViewById(R.id.MarkaModel);
        age = v.findViewById(R.id.AgeCar);
        probeg = v.findViewById(R.id.Probeg);
        fuel = v.findViewById(R.id.ViewFuelCar);
        carFoto = v.findViewById(R.id.CarFoto);
        addCar = v.findViewById(R.id.btnAddCar);

        // Firebase
        fAuth = FirebaseAuth.getInstance();
        fstore = FirebaseFirestore.getInstance();
        storageReference = FirebaseStorage.getInstance().getReference();
        userId = fAuth.getCurrentUser().getUid();
        user = fAuth.getCurrentUser();

        //Получение фото транспорта
        StorageReference profileRef = storageReference.child("cars/" + fAuth.getCurrentUser().getUid() + "/car.jpg");
        profileRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                Picasso.get().load(uri).into(carFoto);
            }
        });

        DocumentReference documentReference = fstore.collection("Cars").document(userId);
        documentReference.addSnapshotListener(new EventListener<DocumentSnapshot>() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onEvent(@Nullable DocumentSnapshot documentSnapshot, @Nullable FirebaseFirestoreException error) {
                if (documentSnapshot != null) {
                    if(documentSnapshot.exists()){
                        markaModel.setText("Тс: " + documentSnapshot.getString("Marka") + " " + documentSnapshot.getString("Model"));
                        age.setText("Год выпуска: " + documentSnapshot.getString("Year of issue") + " год");
                        probeg.setText("Пробег: " + documentSnapshot.getLong("Mileage").intValue() + " км.");
                        fuel.setText("Вид топлива: " + documentSnapshot.getString("Type of fuel"));
                    }else{
                        Log.d("Сообщение об ошибке", "Ошибка в документе");
                    }
                }
            }
        });
        addCar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(v.getContext(), AddCar.class));
            }
        });
      return v;
    }
}