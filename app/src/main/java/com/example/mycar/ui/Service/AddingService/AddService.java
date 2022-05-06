package com.example.mycar.ui.Service.AddingService;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;

import com.example.mycar.R;

public class AddService extends AppCompatActivity {

    AutoCompleteTextView autoCompleteTextView;
    String[] viewWork = {"ТО", "Ремонт", "Замена", "Тюнинг", "Диагностика", "Шиномонтаж"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_service);

        autoCompleteTextView = findViewById(R.id.ViewWork);

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,android.R.layout.select_dialog_singlechoice, viewWork);
//        autoCompleteTextView.setThreshold(1);
        autoCompleteTextView.setAdapter(adapter);
    }
}