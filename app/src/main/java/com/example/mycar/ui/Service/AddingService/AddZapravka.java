package com.example.mycar.ui.Service.AddingService;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.mycar.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class AddZapravka extends AppCompatActivity {

    EditText count, cumm, price, probeg, comment, data;
    Spinner fuelView;
    Button addZapravka;
    private String[] viewFuelStr = {"АИ-92", "АИ-95", "АИ-98", "АИ-100", "АИ-95+", "АИ-92+", "Дизель", "Метан", "Пропан"};
    FirebaseFirestore fstore;
    FirebaseUser user;
    FirebaseAuth fAuth;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_zapravka);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setTitle("Заправка");

        fstore = FirebaseFirestore.getInstance();
        fAuth = FirebaseAuth.getInstance();
        user = fAuth.getCurrentUser();


        ArrayAdapter<String> viewFuelStrAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, viewFuelStr);
        viewFuelStrAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        fuelView = findViewById(R.id.viewFuel);
        fuelView.setAdapter(viewFuelStrAdapter);
        fuelView.setPrompt("Выберите вид топлива");

        Date currentDate = new Date();
        DateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy");
        String dateText = dateFormat.format(currentDate);


        count = findViewById(R.id.countEdit);
        cumm = findViewById(R.id.cummEdit);
        price = findViewById(R.id.Price);
        probeg = findViewById(R.id.ProbegEdit);
        comment = findViewById(R.id.commentEdit);
        data = findViewById(R.id.dataEdit);
        data.setText(dateText);


        TextWatcher textWatcher = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (!count.getText().toString().isEmpty() && !cumm.getText().toString().isEmpty() && !cumm.getText().toString().isEmpty()) {
                    int temp1 = Integer.parseInt(count.getText().toString());
                    int temp2 = Integer.parseInt(cumm.getText().toString());

                    price.setText(String.valueOf(temp2 / temp1));
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        };
        count.addTextChangedListener(textWatcher);
        cumm.addTextChangedListener(textWatcher);

        addZapravka = findViewById(R.id.EditZaprav);
        addZapravka.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String addfuel = fuelView.getSelectedItem().toString();
                String addcol = count.getText().toString();
                String addcumm = cumm.getText().toString();
                String addprice = price.getText().toString();
                String addprobeg = probeg.getText().toString();
                String addcomment = comment.getText().toString();
                String adddata = data.getText().toString();

                if(addcol.isEmpty()){
                    count.setError("Введите данные в поле!!!");
                    return;
                }
                if(addcumm.isEmpty()){
                    cumm.setError("Введите данные в поле!!!");
                    return;
                }
                if(addcomment.isEmpty()){
                    comment.setError("Введите данные в поле!!!");
                    return;
                }
                DocumentReference documentReference = fstore.collection("Zapravki").document(user.getUid()).collection("myZapravki").document();
                Map<String, Object> zapravka = new HashMap<>();
                zapravka.put("view_Fuel", addfuel);
                zapravka.put("fuel_quantity", addcol);
                zapravka.put("refueling_amount", addcumm);
                zapravka.put("price_liter", addprice);
                zapravka.put("mileage", addprobeg);
                zapravka.put("comment", addcomment);
                zapravka.put("data", adddata);
            documentReference.set(zapravka).addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void unused) {
                    Toast.makeText(getApplicationContext(),"Данные о заправке добавлены!",Toast.LENGTH_SHORT).show();
                    onBackPressed();
                    finish();
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(getApplicationContext(),"Ошибка в добавлении данных!",Toast.LENGTH_SHORT).show();
                    Log.d("Сообщение об ошибке ", "Данные не добавлены " + e.getMessage());
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