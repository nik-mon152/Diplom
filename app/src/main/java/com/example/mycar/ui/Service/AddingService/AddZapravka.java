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

public class AddZapravka extends AppCompatActivity {

    EditText count, cumm, price, probeg, comment, data;
    Spinner fuelView;
    Button addZapravka;
    private String[] viewFuelStr = {"АИ-92", "АИ-95", "АИ-98", "АИ-100", "АИ-95+", "АИ-92+", "Дизель", "Метан", "Пропан"};
    FirebaseFirestore fstore;
    FirebaseUser user;
    FirebaseAuth fAuth;
    String userId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_zapravka);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setTitle("Заправка");

        fstore = FirebaseFirestore.getInstance();
        fAuth = FirebaseAuth.getInstance();
        user = fAuth.getCurrentUser();
        userId = fAuth.getCurrentUser().getUid();
        Calendar calendar = Calendar.getInstance();

        ArrayAdapter<String> viewFuelStrAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, viewFuelStr);
        viewFuelStrAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        fuelView = findViewById(R.id.viewFuelAdd);
        fuelView.setAdapter(viewFuelStrAdapter);
        fuelView.setPrompt("Выберите вид топлива");

        Date currentDate = new Date();
        DateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy");
        String dateText = dateFormat.format(currentDate);

        count = findViewById(R.id.countAdd);
        cumm = findViewById(R.id.cummAdd);
        price = findViewById(R.id.priceAdd);
        probeg = findViewById(R.id.probegAdd);
        comment = findViewById(R.id.commentAdd);
        data = findViewById(R.id.dataAdd);
        data.setText(dateText);

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
                new DatePickerDialog(AddZapravka.this, date, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH),
                        calendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });


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


        TextWatcher textWatcher = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (!count.getText().toString().isEmpty() && !cumm.getText().toString().isEmpty()) {
                    int temp1 = Integer.parseInt(count.getText().toString());
                    double temp2 = Integer.parseInt(cumm.getText().toString());
                    price.setText(String.valueOf(temp2 / temp1));
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        };
        count.addTextChangedListener(textWatcher);
        cumm.addTextChangedListener(textWatcher);

        addZapravka = findViewById(R.id.addZaprav);
        addZapravka.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(probeg.getText().toString().isEmpty()){
                    probeg.setError("Введите пробег");
                    return;
                }
                if(cumm.getText().toString().isEmpty()){
                    cumm.setError("Введите стоимость заправки");
                    return;
                }

                String addfuel = fuelView.getSelectedItem().toString();
                String addcol = count.getText().toString();
                int addcumm = Integer.parseInt(cumm.getText().toString());
                String addprice = price.getText().toString();
                int addprobeg =Integer.parseInt(probeg.getText().toString());
                String addcomment = comment.getText().toString();
                String adddata = data.getText().toString();

                if(addcol.isEmpty()){
                    count.setError("Введите количество топлива");
                    return;
                }
                if(addcumm == 0){
                    cumm.setError("Введите стоимость заправки");
                    return;
                }
                if(adddata.isEmpty()){
                    data.setError("Введите дату");
                    return;
                }
                Map<String, Object> zapravka = new HashMap<>();
                zapravka.put("view_Fuel", addfuel);
                zapravka.put("fuel_quantity", addcol);
                zapravka.put("refueling_amount", addcumm);
                zapravka.put("price_liter", addprice);
                zapravka.put("mileage", addprobeg);
                zapravka.put("comment", addcomment);
                zapravka.put("data", adddata);
                fstore.collection("Zapravki").document(user.getUid()).collection("myZapravki")
                        .add(zapravka)
                        .addOnCompleteListener(new OnCompleteListener<DocumentReference>() {
                            @Override
                            public void onComplete(@NonNull Task<DocumentReference> task) {
                                Toast.makeText(AddZapravka.this, "Данные о заправке добавлены! Обновите страницу чтобы добавить информацию", Toast.LENGTH_SHORT).show();
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
            onBackPressed();
        }
        return super.onOptionsItemSelected(item);
    }
}