package com.example.mycar.ui.Service.AddingService;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mycar.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class EditZapravka extends AppCompatActivity {
    Button editZapravka;
    Intent zapravkaEdit;
    TextView  count, cumm, price, probeg, comment, data;
    Spinner fuelView;
    private String[] viewFuelStr = {"АИ-92", "АИ-95", "АИ-98", "АИ-100", "АИ-95+", "АИ-92+", "Дизель", "Метан", "Пропан"};
    FirebaseFirestore fstore;
    FirebaseUser user;
    FirebaseAuth fAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_zapravka);
        setTitle("Изменение данных");

        ArrayAdapter<String> viewFuelStrAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, viewFuelStr);
        viewFuelStrAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        fuelView = findViewById(R.id.viewFuel);
        fuelView.setAdapter(viewFuelStrAdapter);

        fstore = FirebaseFirestore.getInstance();
        fAuth = FirebaseAuth.getInstance();
        user = fAuth.getCurrentUser();

        count = findViewById(R.id.countEdit);
        cumm = findViewById(R.id.cummEdit);
        price = findViewById(R.id.Price);
        probeg = findViewById(R.id.ProbegEdit);
        comment = findViewById(R.id.commentEdit);
        data = findViewById(R.id.dataEdit);
        editZapravka = findViewById(R.id.EditZaprav);

        zapravkaEdit = getIntent();
        String countT = zapravkaEdit.getStringExtra("Cumm");
        String cummT = zapravkaEdit.getStringExtra("Count");
        String priceT = zapravkaEdit.getStringExtra("Price");
        String probegT = zapravkaEdit.getStringExtra("Probeg");
        String commentT = zapravkaEdit.getStringExtra("Comment");
        String dataT = zapravkaEdit.getStringExtra("Data");

        count.setText(countT);
        cumm.setText(cummT);
        price.setText(priceT);
        probeg.setText(probegT);
        comment.setText(commentT);
        data.setText(dataT);

        editZapravka.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
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
                DocumentReference documentReference = fstore.collection("Zapravki").document(user.getUid()).collection("myZapravki").document(zapravkaEdit.getStringExtra("documentId"));
                Map<String, Object> zapravka = new HashMap<>();
                zapravka.put("view_Fuel", addfuel);
                zapravka.put("fuel_quantity", addcol);
                zapravka.put("refueling_amount", addcumm);
                zapravka.put("price_liter", addprice);
                zapravka.put("mileage", addprobeg);
                zapravka.put("comment", addcomment);
                zapravka.put("data", adddata);
                documentReference.update(zapravka).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()){

                        }
                    }
                });


//                    @Override
//                    public void onSuccess(Void unused) {
//                        Toast.makeText(getApplicationContext(),"Данные изменены!",Toast.LENGTH_SHORT).show();
//
//                        onBackPressed();
//                        finish();
//                    }
                }
        });
    }
}