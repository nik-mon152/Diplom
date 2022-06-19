package com.example.mycar.ui.Notification;


import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.example.mycar.MainActivity;
import com.example.mycar.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;

import java.util.HashMap;
import java.util.Map;

public class NotificationAdd extends AppCompatActivity {

    String[] viewWork = {"ТО", "Ремонт", "Замена", "Тюнинг", "Диагностика", "Шиномонтаж"};
    Spinner spinner;
    FirebaseAuth fAuth;
    FirebaseFirestore fstore;
    FirebaseUser user;
    String userId;
    EditText work, adressn, cumm, probegn, commentn;
    Button add;
    NotificationManagerCompat notificationManagerCompat;
    Notification notification;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification_add);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setTitle("Уведомление");

        fstore = FirebaseFirestore.getInstance();
        fAuth = FirebaseAuth.getInstance();
        user = fAuth.getCurrentUser();
        userId = fAuth.getCurrentUser().getUid();


        work = findViewById(R.id.nameWorkNotif);
        adressn = findViewById(R.id.AdressNotif);
        cumm = findViewById(R.id.priceNotif);
        probegn = findViewById(R.id.notifprobeg);
        commentn = findViewById(R.id.notifComment);

        DocumentReference documentReference = fstore.collection("Cars").document(userId);
        documentReference.addSnapshotListener(new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot documentSnapshot, @Nullable FirebaseFirestoreException error) {
                if (documentSnapshot != null) {
                    if(documentSnapshot.exists()){
                        probegn.setText(documentSnapshot.getLong("Mileage").intValue() + "");
                    }else{
                        Log.d("Сообщение об ошибке", "Ошибка в документе");
                    }
                }
            }
        });
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, viewWork);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner = findViewById(R.id.ViewWorkNotif);
        spinner.setAdapter(adapter);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            NotificationChannel channel = new NotificationChannel("Mych", "Mychal1", NotificationManager.IMPORTANCE_HIGH);
            NotificationManager manager = getSystemService(NotificationManager.class);
            manager.createNotificationChannel(channel);
        }



        add = findViewById(R.id.addNotif);
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(probegn.getText().toString().isEmpty()){
                    probegn.setError("Введите пробег");
                    return;
                }

                String viewWorkn = spinner.getSelectedItem().toString();
                String name = work.getText().toString();
                String adress = adressn.getText().toString();
                String price = cumm.getText().toString();
                int probeg = Integer.parseInt(probegn.getText().toString());
                String comment = commentn.getText().toString();

                if(name.isEmpty()){
                    work.setError("Введите название");
                    return;
                }
                if(adress.isEmpty()){
                    adressn.setError("Введите адрес");
                    return;
                }
                if(price.isEmpty()){
                    cumm.setError("Введите сумму");
                    return;
                }
                if(probeg == 0){
                    probegn.setError("Введите пробег");
                    return;
                }
                Map<String, Object> notif = new HashMap<>();
                notif.put("view_work_notif", viewWorkn);
                notif.put("work_notif", name);
                notif.put("adress_notif", adress);
                notif.put("price_notif", price);
                notif.put("probeg_notif",probeg);
                notif.put("comment_notif", comment);
                fstore.collection("Notification").document(user.getUid()).collection("myNotifications")
                        .add(notif)
                        .addOnCompleteListener(new OnCompleteListener<DocumentReference>() {
                            @Override
                            public void onComplete(@NonNull Task<DocumentReference> task) {
                                Toast.makeText(NotificationAdd.this, "Данные о заправке добавлены! Обновите страницу чтобы добавить информацию", Toast.LENGTH_SHORT).show();
                                onBackPressed();
                                finish();
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(getApplicationContext(),"Ошибка в добавлении данных!",Toast.LENGTH_SHORT).show();
                    }
                });
                Intent intent = new Intent(NotificationAdd.this, MainActivity.class);
                NotificationCompat.Builder builder = new NotificationCompat.Builder(NotificationAdd.this, "Mych");
                builder.setSmallIcon(R.mipmap.ic_launcher);
                builder.setContentTitle("My car");
                builder.setContentText("Вы создали уведомление!");
                builder.setAutoCancel(true);
                builder.setAutoCancel(true);

                NotificationManagerCompat managerCompat = NotificationManagerCompat.from(NotificationAdd.this);
                managerCompat.notify(1, builder.build());
            }

        });
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();

        if (id == androidx.appcompat.R.id.action_bar){
            return true;
        }else if (id == android.R.id.home){
            finish();
        }
        return super.onOptionsItemSelected(item);
    }
}