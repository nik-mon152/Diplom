package com.example.mycar.Authentication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
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

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class RegistrationActivity extends AppCompatActivity {

    EditText regFirstName, regLastName, regPatronymic, regData, regEmail, regNumber, regPassword;
    Button btnResgister;
    TextView btnLogin;
    String userID;
    ProgressBar progressBar;
    FirebaseAuth fAuth;
    FirebaseFirestore fStore;
    DatePickerDialog.OnDateSetListener onDateSetListener;
    int year, month, day;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
        getSupportActionBar().hide();

        regFirstName = findViewById(R.id.FirstName);
        regLastName = findViewById(R.id.LastName);
        regPatronymic = findViewById(R.id.patronymic);
        regData = findViewById(R.id.date);
        regEmail = findViewById(R.id.Email);
        regNumber = findViewById(R.id.Number);
        regPassword = findViewById(R.id.Password);
        progressBar = findViewById(R.id.progressBarReg);

        fAuth = FirebaseAuth.getInstance();
        fStore = FirebaseFirestore.getInstance();
        final Calendar calendar = Calendar.getInstance();

        regData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                day = calendar.get(Calendar.DAY_OF_MONTH);
                month = calendar.get(Calendar.MONTH);
                year = calendar.get(Calendar.YEAR);
                DatePickerDialog datePickerDialog = new DatePickerDialog(RegistrationActivity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        month = month + 1;
                        String date = dayOfMonth + "." + month + "." + year;
                        regData.setText(date);
                    }
                }, day, month, year);
                datePickerDialog.show();
            }
        });

        btnResgister = findViewById(R.id.btnResgister);
        btnResgister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String firstName = regFirstName.getText().toString();
                String lastName = regLastName.getText().toString();
                String patronymic = regPatronymic.getText().toString();
                String date = regData.getText().toString();
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
                if(patronymic.isEmpty()){
                    regPatronymic.setError("Введите свое отчество!!!");
                    return;
                }
                if(date.isEmpty()){
                    regData.setError("Выберите дату рождения!!!");
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
                    regNumber.setError("Введите свой номер телефона!!!");
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
                       user.put("FirstName", firstName);
                       user.put("LastName", lastName);
                       user.put("Patronymic", patronymic);
                       user.put("Birthday", date);
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