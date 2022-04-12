package com.example.mycar;

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

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class RegistrationActivity extends AppCompatActivity {

    EditText regFirstName, regLastName, regEmail, regNumber, regPassword;
    Button btnResgister;
    TextView btnLogin;
    ProgressBar progressBar;
    FirebaseAuth fAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
        getSupportActionBar().hide();

        regFirstName = findViewById(R.id.FirstName);
        regLastName = findViewById(R.id.LastName);
        regEmail = findViewById(R.id.Email);
        regNumber = findViewById(R.id.Number);
        regPassword = findViewById(R.id.Password);
        progressBar = findViewById(R.id.progressBarLog);

        fAuth = FirebaseAuth.getInstance();
        if(fAuth.getCurrentUser() != null){
            startActivity(new Intent(getApplicationContext(),LoginActivity.class));
            finish();
        }

        btnResgister = findViewById(R.id.btnResgister);
        btnResgister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String firstName = regFirstName.getText().toString();
                String lastName = regLastName.getText().toString();
                String email = regEmail.getText().toString().trim();
                String number = regNumber.getText().toString();
                String password = regPassword.getText().toString();
                String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";

                if(firstName.isEmpty()){
                    regFirstName.setError("Введите свое полное имя!!!");
                    return;
                }
                if(lastName.isEmpty()){
                    regLastName.setError("Введите свою фамилию!!!");
                    return;
                }
                if(email.isEmpty()){
                    regEmail.setError("Введите почту!!!");
                    return;
                }
                if(email.matches(emailPattern)){
                    Toast.makeText(getApplicationContext(),"valid email address",Toast.LENGTH_SHORT).show();
                }else{
                    regEmail.setError("Почта введена неккоретно!!!");
                    return;
                }
                if(number.isEmpty()){
                    regNumber.setError("Введите свой номером телефона!!!");
                    return;
                }else if(number.length() < 11){
                    regNumber.setError("Некорректно введен номер!!!");
                    return;
                }
                if(password.isEmpty()){
                    regPassword.setError("Введите свой номером телефона!!!");
                    return;
                }else if(password.length() < 6){
                    regPassword.setError("Пароль должен содержать больше 6 символов");
                    return;
                }

                progressBar.setVisibility(View.VISIBLE);
                //регистрация пользователя
                fAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                   if(task.isSuccessful()){
                       Toast.makeText(RegistrationActivity.this,"Регистрация прошла успешно!",Toast.LENGTH_SHORT).show();
                       startActivity(new Intent(getApplicationContext(),LoginActivity.class));
                   }else {
                       Toast.makeText(RegistrationActivity.this,"Ошибка в регистрации!"+ task.getException().getMessage(),Toast.LENGTH_SHORT).show();
                   }
                    }
                });
            }
        });
        btnLogin = findViewById(R.id.tvUserLogin);
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(RegistrationActivity.this, LoginActivity.class));
            }
        });
    }
}