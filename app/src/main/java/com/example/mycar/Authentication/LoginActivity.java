package com.example.mycar.Authentication;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.mycar.MainActivity;
import com.example.mycar.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LoginActivity extends AppCompatActivity {

    EditText logEmail, logPassword;
    TextView userRegister, forgotPasswordtext;
    Button userLogin;
    ProgressBar progressBar;
    FirebaseAuth fAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        getSupportActionBar().hide();

        fAuth = FirebaseAuth.getInstance();
        progressBar = findViewById(R.id.progressBarLog);
        logEmail = findViewById(R.id.Email);
        logPassword = findViewById(R.id.Password);

        if(fAuth.getCurrentUser() != null){
            startActivity(new Intent(getApplicationContext(), MainActivity.class));
            finish();
        }

        userRegister = findViewById(R.id.tvRegister);
        userRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(LoginActivity.this, RegistrationActivity.class));
            }
        });

        forgotPasswordtext = findViewById(R.id.forgotPassword);
        forgotPasswordtext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EditText resetEmail = new EditText(view.getContext());
                resetEmail.setMaxLines(1);
                AlertDialog.Builder dialogResetPassword = new AlertDialog.Builder(view.getContext());
                dialogResetPassword.setTitle("???????????? ?????????????? ?????????????");
                dialogResetPassword.setMessage("?????????????? ?????????? ?? ?????????????? ???? ????????????????????????????????");
                dialogResetPassword.setView(resetEmail);

                dialogResetPassword.setPositiveButton("????", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        //?????? ???????????????????? ???????? ?? ?????????? ?????????? ???????? ???????????? ?????? ?????????? ????????????
                        String email = resetEmail.getText().toString();
                        fAuth.sendPasswordResetEmail(email).addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void unused) {
                                Toast.makeText(LoginActivity.this,"???????????? ???? ?????????????????? ???????????? ???????????????????? ?????? ???? ??????????",Toast.LENGTH_SHORT).show();

                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(LoginActivity.this,"???????????? ?? ???????????????? ???????????? ???? ??????????" + e.getMessage(),Toast.LENGTH_SHORT).show();

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

        userLogin = findViewById(R.id.btnlogin);
        userLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String email = logEmail.getText().toString().trim();
                String password = logPassword.getText().toString().trim();

                if(email.isEmpty()){
                    logEmail.setError("?????????????? ??????????!!!");
                    return;
                }
                if(password.isEmpty()){
                    logPassword.setError("?????????????? ???????? ????????????!!!");
                    return;
                }else if(password.length() < 6){
                    logPassword.setError("???????????? ???????????? ?????????????????? ???????????? 6 ????????????????");
                    return;
                }

                progressBar.setVisibility(View.VISIBLE);

                fAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            Toast.makeText(LoginActivity.this,"???? ?????????????? ?????????? ?? ???????? ??????????????!",Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(LoginActivity.this, MainActivity.class));
                            finish();
                        }else {
                            Toast.makeText(LoginActivity.this,"???????????????? ?????????????????? ????????????!"+
                                    task.getException().getMessage(),Toast.LENGTH_SHORT).show();
                            progressBar.setVisibility(View.GONE);
                        }
                    }
                });
            }
        });
    }
}