package com.example.mycar.ui.Service.AddingService;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

import com.example.mycar.R;

public class AddZapravka extends AppCompatActivity {

    EditText count, cumm, price, probeg, comment;
    Spinner fuelView;
    private String[] viewFuelStr = {"АИ-92", "АИ-95", "АИ-98", "АИ-100", "АИ-95+", "АИ-92+", "Дизель", "Метан", "Пропан"};


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_zapravka);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setTitle("Заправка");

        ArrayAdapter<String> viewFuelStrAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, viewFuelStr);
        viewFuelStrAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        fuelView = findViewById(R.id.viewFuel);
        fuelView.setAdapter(viewFuelStrAdapter);
        fuelView.setPrompt("Выберите вид топлива");


        count = findViewById(R.id.Count);
        cumm = findViewById(R.id.Cumm);
        price = findViewById(R.id.Price);
        probeg = findViewById(R.id.ProbegZapr);
        comment = findViewById(R.id.Comment);


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