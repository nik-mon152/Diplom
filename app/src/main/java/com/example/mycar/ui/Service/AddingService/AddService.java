package com.example.mycar.ui.Service.AddingService;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

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

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class AddService extends AppCompatActivity {

    EditText work, adress, price, probeg, comment, data;
    Spinner spinner;
    String[] viewWork = {"ТО", "Ремонт", "Замена", "Тюнинг", "Диагностика", "Шиномонтаж"};
    Button addServise;
    FirebaseFirestore fstore;
    FirebaseUser user;
    FirebaseAuth fAuth;
    String userId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_service);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setTitle("Сервис");

        fstore = FirebaseFirestore.getInstance();
        fAuth = FirebaseAuth.getInstance();
        user = fAuth.getCurrentUser();
        userId = fAuth.getCurrentUser().getUid();
        Calendar calendar = Calendar.getInstance();

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

        DocumentReference documentReference = fstore.collection("Cars").document(userId);
        documentReference.addSnapshotListener(new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot documentSnapshot, @Nullable FirebaseFirestoreException error) {
                if (documentSnapshot != null) {
                    if(documentSnapshot.exists()){
                        probeg.setText(documentSnapshot.getLong("Mileage").intValue() + "");
                    }else{
                        Log.d("Сообщение об ошибке", "Ошибка в документе");
                    }
                }
            }
        });

        DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                calendar.set(Calendar.YEAR, year);
                calendar.set(Calendar.MONTH, month);
                calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);

                updateCalendar();
            }
            private void updateCalendar() {
                String format = "dd.MM.yyyy";
                SimpleDateFormat sdf = new SimpleDateFormat(format, Locale.ENGLISH);
                data.setText(sdf.format(calendar.getTime()));
            }
        };
        data.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new DatePickerDialog(AddService.this, date, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH),
                        calendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        TextWatcher tw = new TextWatcher() {
            private String current = "";
            private String ddmmyyyy = "DDMMYYYY";
            private Calendar cal = Calendar.getInstance();
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (!s.toString().equals(current)) {
                    String clean = s.toString().replaceAll("[^\\d.]|\\.", "");
                    String cleanC = current.replaceAll("[^\\d.]|\\.", "");

                    int cl = clean.length();
                    int sel = cl;
                    for (int i = 2; i <= cl && i < 6; i += 2) {
                        sel++;
                    }
                    if (clean.equals(cleanC)) sel--;

                    if (clean.length() < 8){
                        clean = clean + ddmmyyyy.substring(clean.length());
                    }else{
                        int day  = Integer.parseInt(clean.substring(0,2));
                        int mon  = Integer.parseInt(clean.substring(2,4));
                        int year = Integer.parseInt(clean.substring(4,8));

                        mon = mon < 1 ? 1 : mon > 12 ? 12 : mon;
                        cal.set(Calendar.MONTH, mon-1);
                        year = (year<1900)?1900:(year>2100)?2100:year;
                        cal.set(Calendar.YEAR, year);

                        day = (day > cal.getActualMaximum(Calendar.DATE))? cal.getActualMaximum(Calendar.DATE):day;
                        clean = String.format("%02d%02d%02d",day, mon, year);
                    }

                    clean = String.format("%s.%s.%s", clean.substring(0, 2),
                            clean.substring(2, 4),
                            clean.substring(4, 8));

                    sel = sel < 0 ? 0 : sel;
                    current = clean;
                    data.setText(current);
                    data.setSelection(sel < current.length() ? sel : current.length());
                }

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        };
        data.addTextChangedListener(tw);

        addServise.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(probeg.getText().toString().isEmpty()){
                    probeg.setError("Введите пробег");
                    return;
                }
                if(price.getText().toString().isEmpty()){
                    price.setError("Введите стоимость сервиса");
                    return;
                }
                String addViewWork = spinner.getSelectedItem().toString();
                String addNameWork = work.getText().toString();
                String addAdress = adress.getText().toString();
                int addprobeg = Integer.parseInt(probeg.getText().toString());
                int addprice = Integer.parseInt(price.getText().toString());
                String adddata = data.getText().toString();
                String addcomment = comment.getText().toString();

                if(addNameWork.isEmpty()){
                    work.setError("Введите вид работ");
                    return;
                }
                if(addAdress.isEmpty()){
                    adress.setError("Введите адрес сервиса");
                    return;
                }
                if(addprobeg == 0){
                    probeg.setError("Введите пробег");
                    return;
                }
                if(addprice == 0){
                    price.setError("Введите стоимость сервиса");
                    return;
                }
                if(adddata.isEmpty()){
                    data.setError("Введите дату");
                    return;
                }
                Map<String, Object> service = new HashMap<>();
                service.put("view_Servise", addViewWork);
                service.put("name_work", addNameWork);
                service.put("adress", addAdress);
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