package com.example.mycar.Authentication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mycar.MainActivity;
import com.example.mycar.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LoginActivity extends AppCompatActivity {

    EditText logEmail, logPassword;
    TextView userRegister;
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

        userRegister = findViewById(R.id.tvRegister);
        userRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), RegistrationActivity.class));
            }
        });
        userLogin = findViewById(R.id.btnlogin);
        userLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String email = logEmail.getText().toString().trim();
                String password = logPassword.getText().toString().trim();

                if(email.isEmpty()){
                    logEmail.setError("Введите почту!!!");
                    return;
                }
                if(password.isEmpty()){
                    logPassword.setError("Введите свой пароль!!!");
                    return;
                }else if(password.length() < 6){
                    logPassword.setError("Пароль должен содержать больше 6 символов");
                    return;
                }

                progressBar.setVisibility(View.VISIBLE);

                fAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            Toast.makeText(LoginActivity.this,"Вы успешно вошли в свой аккаунт!",Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(LoginActivity.this, MainActivity.class));
                        }else {
                            Toast.makeText(LoginActivity.this,"Неверные введенные данные!"+ task.getException().getMessage(),Toast.LENGTH_SHORT).show();
                            progressBar.setVisibility(View.GONE);
                        }
                    }
                });
            }
        });
    }
}