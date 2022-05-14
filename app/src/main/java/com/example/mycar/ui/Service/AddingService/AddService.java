package com.example.mycar.ui.Service.AddingService;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.mycar.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class AddService extends AppCompatActivity {

    EditText work, adress, price, probeg, comment, data;
    Spinner spinner;
    String[] viewWork = {"ТО", "Ремонт", "Замена", "Тюнинг", "Диагностика", "Шиномонтаж"};
    Button addServise;
    FirebaseFirestore fstore;
    FirebaseUser user;
    FirebaseAuth fAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_service);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setTitle("Сервис");

        fstore = FirebaseFirestore.getInstance();
        fAuth = FirebaseAuth.getInstance();
        user = fAuth.getCurrentUser();

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, viewWork);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner = findViewById(R.id.ViewWork);
        spinner.setAdapter(adapter);
        Date currentDate = new Date();
        DateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy");
        String dateText = dateFormat.format(currentDate);

        work = findViewById(R.id.NameWork);
        adress = findViewById(R.id.InfoAdress);
        probeg = findViewById(R.id.servProbeg);
        comment = findViewById(R.id.servComment);
        price = findViewById(R.id.priceServise);
        data = findViewById(R.id.textDate);
        data.setText(dateText);
        addServise = findViewById(R.id.addServ);

        addServise.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String addViewWork = spinner.getSelectedItem().toString();
                String addNameWork = work.getText().toString();
                String addAdress = adress.getText().toString();
                String addprobeg = probeg.getText().toString();
                String addprice = price.getText().toString();
                String adddata = data.getText().toString();
                String addcomment = comment.getText().toString();

                if(addNameWork.isEmpty()){
                    work.setError("Введите данные в поле!!!");
                    return;
                }
                if(addAdress.isEmpty()){
                    adress.setError("Введите данные в поле!!!");
                    return;
                }
                if(addprobeg.isEmpty()){
                    probeg.setError("Введите данные в поле!!!");
                    return;
                }
                if(addprice.isEmpty()){
                    price.setError("Введите данные в поле!!!");
                    return;
                }
                if(adddata.isEmpty()){
                    data.setError("Введите данные в поле!!!");
                    return;
                }
                if(addcomment.isEmpty()){
                    comment.setError("Введите данные в поле!!!");
                    return;
                }

                Map<String, Object> service = new HashMap<>();
                service.put("view_Fuel", addViewWork);
                service.put("name_work", addNameWork);
                service.put("addAdress", addAdress);
                service.put("price_servise", addprice);
                service.put("mileage", addprobeg);
                service.put("comment", addcomment);
                service.put("data", adddata);
                fstore.collection("Servises").document(user.getUid()).collection("myServises")
                        .add(service)
                        .addOnCompleteListener(new OnCompleteListener<DocumentReference>() {
                            @Override
                            public void onComplete(@NonNull Task<DocumentReference> task) {
                                Toast.makeText(AddService.this, "Данные о сервисе добавлены! Обновите страницу чтобы добавить информацию", Toast.LENGTH_SHORT).show();
                                onBackPressed();
                                finish();
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(getApplicationContext(),"Ошибка в добавлении данных!",Toast.LENGTH_SHORT).show();
                    }
                });
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