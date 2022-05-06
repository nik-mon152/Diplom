package com.example.mycar.ui.Service.AddingService;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.MenuItem;
import android.widget.EditText;

import com.example.mycar.R;

public class AddZapravka extends AppCompatActivity {

    EditText count, cumm, price, probeg, comment;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_zapravka);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setTitle("Заправка");


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