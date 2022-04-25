package com.example.mycar.Authentication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mycar.R;
import com.example.mycar.SplashScreen;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class RegistrationActivity extends AppCompatActivity {

    EditText regFirstName, regLastName, regEmail, regNumber, regPassword;
    Button btnResgister;
    TextView btnLogin;
    String userID;
    ProgressBar progressBar;
    FirebaseAuth fAuth;
    FirebaseFirestore fStore;

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
        progressBar = findViewById(R.id.progressBarReg);

        fAuth = FirebaseAuth.getInstance();
        fStore = FirebaseFirestore.getInstance();
//        if(fAuth.getCurrentUser() != null){
//            startActivity(new Intent(getApplicationContext(), RegistrationActivity.class));
//            finish();
//        }

        btnResgister = findViewById(R.id.btnResgister);
        btnResgister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String firstName = regFirstName.getText().toString();
                String lastName = regLastName.getText().toString();
                String email = regEmail.getText().toString().trim();
                String number = regNumber.getText().toString();
                String password = regPassword.getText().toString().trim();
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
                    regPassword.setError("Введите пароль!!!");
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

                       FirebaseUser users = fAuth.getCurrentUser();
                       users.sendEmailVerification().addOnSuccessListener(new OnSuccessListener<Void>() {
                           @Override
                           public void onSuccess(Void unused) {
                               Log.d("Сообщение:", "Письмо отправлено");
                               Toast.makeText(RegistrationActivity.this,"Письмо с подтверждением почты отправлено!",Toast.LENGTH_SHORT).show();
                           }
                       }).addOnFailureListener(new OnFailureListener() {
                           @Override
                           public void onFailure(@NonNull Exception e) {
                               Log.d("Сообщение об ошибке", "Письмо не было отправлено " + e.getMessage());
                           }
                       });


                       Toast.makeText(RegistrationActivity.this,"Регистрация прошла успешно!",Toast.LENGTH_SHORT).show();
                       userID = fAuth.getCurrentUser().getUid();
                       DocumentReference documentReference = fStore.collection("Users").document(userID);
                       Map<String, Object> user = new HashMap<>();
                       user.put("FirsName", firstName);
                       user.put("LastName", lastName);
                       user.put("Email", email);
                       user.put("Number", number);
                       documentReference.set(user).addOnSuccessListener(new OnSuccessListener<Void>() {
                           @Override
                           public void onSuccess(Void aVoid) {
                               Log.d("Сообщение", "Пользователь создан c уникальным кодом:" + userID);
                           }
                       }).addOnFailureListener(new OnFailureListener() {
                           @Override
                           public void onFailure(@NonNull Exception e) {
                               Log.d("Сообщение об ошибке", "Пользователь не был создан " + e.getMessage());
                           }
                       });
                       startActivity(new Intent(getApplicationContext(),LoginActivity.class));
                   }else {
                       Toast.makeText(RegistrationActivity.this,"Ошибка в регистрации!"+ task.getException().getMessage(),Toast.LENGTH_SHORT).show();
                       progressBar.setVisibility(View.GONE);
                   }
                    }
                });
            }
        });
        btnLogin = findViewById(R.id.tvUserLogin);
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), LoginActivity.class));
                finish();
            }
        });
    }
}