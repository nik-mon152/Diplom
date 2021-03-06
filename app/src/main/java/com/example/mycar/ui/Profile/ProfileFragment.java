package com.example.mycar.ui.Profile;



import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

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
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;


public class ProfileFragment extends Fragment {

    TextView fio, birthday, email, number, verMsg;
    Button bntLogout, verEmail, resetPasswd, changeProfile;
    ImageView profileImage;
    FirebaseAuth fAuth;
    FirebaseFirestore fstore;
    FirebaseUser user;
    String userId;
    StorageReference storageReference;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_profile, container, false);

        fio = v.findViewById(R.id.profleFIO);
        birthday = v.findViewById(R.id.birthday);
        email = v.findViewById(R.id.profileEmail);
        number = v.findViewById(R.id.profilePhone);



        // Firebase
        fAuth = FirebaseAuth.getInstance();
        fstore = FirebaseFirestore.getInstance();
        storageReference = FirebaseStorage.getInstance().getReference();

        profileImage = v.findViewById(R.id.profileImage);
        StorageReference profileRef = storageReference.child("users/" + fAuth.getCurrentUser().getUid() + "/profile.jpg");
        profileRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                Picasso.get().load(uri).into(profileImage);
            }
        });

        verEmail = v.findViewById(R.id.bntverifyEmail);
        verMsg = v.findViewById(R.id.verifyMsg);

        userId = fAuth.getCurrentUser().getUid();
        user = fAuth.getCurrentUser();

        resetPasswd = v.findViewById(R.id.btnChangePasswd);
        changeProfile = v.findViewById(R.id.btnChangeProfile);

        if(!user.isEmailVerified()){
            verMsg.setVisibility(v.VISIBLE);
            verEmail.setVisibility(v.VISIBLE);
            resetPasswd.setVisibility(v.INVISIBLE);
            changeProfile.setVisibility(v.INVISIBLE);


            verEmail.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    user.sendEmailVerification().addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void unused) {
                            Log.d("??????????????????:", "???????????? ????????????????????");
                            Toast.makeText(v.getContext(),"???????????? ?? ???????????????????????????? ?????????? ????????????????????!",Toast.LENGTH_SHORT).show();
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Log.d("?????????????????? ???? ????????????", "???????????? ???? ???????? ???????????????????? " + e.getMessage());
                        }
                    });
                }
            });
        }

        resetPasswd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EditText resetPasswd = new EditText(view.getContext());

                AlertDialog.Builder dialogResetPassword = new AlertDialog.Builder(view.getContext());
                dialogResetPassword.setTitle("???????????? ?????????????? ?????????????");
                dialogResetPassword.setMessage("?????????????? ?????????? ???????????? ???????????? 6 ????????????????");
                dialogResetPassword.setView(resetPasswd);

                dialogResetPassword.setPositiveButton("????", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        //?????? ???????????????????? ???????? ?? ???????????? ?????????? ???????? ???????????? ?????? ?????????? ????????????
                        String passwd = resetPasswd.getText().toString();
                        user.updatePassword(passwd).addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void unused) {
                                Toast.makeText(v.getContext(),"???? ?????????????? ???????????????? ????????????!",Toast.LENGTH_SHORT).show();
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(v.getContext(),"?????????????????? ???????????? ?? ?????????? ????????????!",Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                });
                dialogResetPassword.setNegativeButton("??????", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                });
                dialogResetPassword.create().show();
            }
        });

        DocumentReference documentReference = fstore.collection("Users").document(userId);
        documentReference.addSnapshotListener(new EventListener<DocumentSnapshot>() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onEvent(@Nullable DocumentSnapshot documentSnapshot, @Nullable FirebaseFirestoreException error) {
                if (documentSnapshot != null) {
                    if(documentSnapshot.exists()){
                        fio.setText(documentSnapshot.getString("LastName") + " " + documentSnapshot.getString("FirstName") + " " + documentSnapshot.getString("Patronymic"));
                        birthday.setText(documentSnapshot.getString("Birthday"));
                        email.setText(documentSnapshot.getString("Email"));
                        number.setText(documentSnapshot.getString("Number"));
                    }else{
                        Log.d("?????????????????? ???? ????????????", "???????????? ?? ??????????????????");
                    }
                }
            }
        });

        bntLogout = v.findViewById(R.id.btnLogout);
        bntLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fAuth.signOut();
                startActivity(new Intent(getActivity(), LoginActivity.class));
                getActivity().finish();
            }
        });

        changeProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(v.getContext(), EditProfile.class);
                intent.putExtra("??????????", email.getText().toString());
                intent.putExtra("?????????? ????????????????", number.getText().toString());
                startActivity(intent);
            }
        });
        return v;
    }
}