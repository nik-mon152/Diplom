package com.example.mycar.ui.Car;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mycar.Authentication.LoginActivity;
import com.example.mycar.R;
import com.example.mycar.ui.Notification.NotificationsFragment;
import com.example.mycar.ui.Profile.EditProfile;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.FirebaseStorage;

public class CarFragment extends Fragment {
    TextView marka, model, age, probeg, fuel;
    ImageView carFoto;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_car, container, false);

        marka = v.findViewById(R.id.Marka);
        model = v.findViewById(R.id.Model);
        age = v.findViewById(R.id.AgeCar);
        probeg = v.findViewById(R.id.Probeg);
        fuel = v.findViewById(R.id.ViewFuelCar);
        carFoto = v.findViewById(R.id.CarFoto);

        if (model.length() == 0 || marka.length() == 0){
            startActivity(new Intent(getActivity(), AddCar.class));
            getActivity().finish();
        }

      return v;
    }
}