package com.example.mycar.ui.Profile;


import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;


import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mycar.Authentication.LoginActivity;
import com.example.mycar.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;


public class ProfileFragment extends Fragment {

    TextView fullname, lastname, email, number, verMsg;
    Button bntLogout, verEmail, resetPasswd;
    FirebaseAuth fAuth;
    FirebaseFirestore fstore;
    FirebaseUser user;
    String userId;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_profile, container, false);


        fullname = v.findViewById(R.id.profileFirsName);
        lastname = v.findViewById(R.id.profileLastName);
        email = v.findViewById(R.id.profileEmail);
        number = v.findViewById(R.id.profilePhone);

        fAuth = FirebaseAuth.getInstance();
        fstore = FirebaseFirestore.getInstance();

        verEmail = v.findViewById(R.id.bntverifyEmail);
        verMsg = v.findViewById(R.id.verifyMsg);

        userId = fAuth.getCurrentUser().getUid();
        user = fAuth.getCurrentUser();

        if(!user.isEmailVerified()){
            verMsg.setVisibility(container.VISIBLE);
            verEmail.setVisibility(container.VISIBLE);

            verEmail.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    user.sendEmailVerification().addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void unused) {
                            Log.d("Сообщение:", "Письмо отправлено");
                            Toast.makeText(v.getContext(),"Письмо с подтверждением почты отправлено!",Toast.LENGTH_SHORT).show();
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Log.d("Сообщение об ошибке", "Письмо не было отправлено " + e.getMessage());
                        }
                    });
                }
            });
        }
        resetPasswd = v.findViewById(R.id.btnChangePasswd);
        resetPasswd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EditText resetPasswd = new EditText(view.getContext());

                AlertDialog.Builder dialogResetPassword = new AlertDialog.Builder(view.getContext());
                dialogResetPassword.setTitle("Хотите сменить пароль?");
                dialogResetPassword.setMessage("Введите новый пароль больше 6 символов");
                dialogResetPassword.setView(resetPasswd);

                dialogResetPassword.setPositiveButton("Да", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        //При заполнении поля с почто будет дана ссылка для смены пароля
                        String passwd = resetPasswd.getText().toString();
                        user.updatePassword(passwd).addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void unused) {
                                Toast.makeText(v.getContext(),"Вы успешно поменяли пароль!",Toast.LENGTH_SHORT).show();
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(v.getContext(),"Произошла ошибка в смене пароля!",Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                });
                dialogResetPassword.setNegativeButton("Нет", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                });
                dialogResetPassword.create().show();
            }
        });

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
                onStop();
                onDestroyView();
                onDestroy();
                onDetach();
            }
        });
        return v;
    }
    @Override
    public void onStop(){
        super.onStop();
    }
    @Override
    public void onDestroyView(){
        super.onDestroyView();
    }
    @Override
    public void onDestroy(){
        super.onDestroy();
    }
    @Override
    public void onDetach(){
        super.onDetach();
    }
}