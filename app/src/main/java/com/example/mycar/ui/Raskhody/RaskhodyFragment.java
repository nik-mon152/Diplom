package com.example.mycar.ui.Raskhody;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.mycar.R;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;

import java.util.ArrayList;

public class RaskhodyFragment extends Fragment {

    PieChart pieChart;
    FirebaseAuth fAuth;
    FirebaseFirestore fstore;
    String userId;
    TextView servise, zapravki;
    int fbservise, fbzapravki;
    int[] colorArray = new int[]{Color.BLUE, Color.RED};

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_raskhody, container, false);

        fAuth = FirebaseAuth.getInstance();
        fstore = FirebaseFirestore.getInstance();
        userId = fAuth.getCurrentUser().getUid();
        servise = v.findViewById(R.id.textServise);
        zapravki = v.findViewById(R.id.textZapravki);


        fstore.collection("Expenses").document(userId).addSnapshotListener(new EventListener<DocumentSnapshot>() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onEvent(@Nullable DocumentSnapshot documentSnapshot, @Nullable FirebaseFirestoreException error) {
                if (documentSnapshot != null) {
                    if(documentSnapshot.exists()){
                        servise.setText("Сервисы: " + documentSnapshot.getLong("total_servise").intValue() + "₽");
                        zapravki.setText("Заправки: " + documentSnapshot.getLong("total_zapravka").intValue() + "₽");
                        fbservise = documentSnapshot.getLong("total_servise").intValue();
                        fbzapravki = documentSnapshot.getLong("total_zapravka").intValue();
                    }else{
                        Log.d("Сообщение об ошибке", "Ошибка в документе");
                    }
                }
                pieChart = v.findViewById(R.id.pie_chart);
                ArrayList<PieEntry> pieEntries = new ArrayList<>();
                pieEntries.add(new PieEntry(fbservise,"Сервисы"));
                pieEntries.add(new PieEntry(fbzapravki,"Заправки"));

                PieDataSet pieDataSet = new PieDataSet(pieEntries, "");
                pieDataSet.setColors(ColorTemplate.COLORFUL_COLORS);
                pieDataSet.setValueTextColor(Color.BLACK);
                pieDataSet.setValueTextSize(16f);

                PieData pieData = new PieData(pieDataSet);
                pieChart.setData(pieData);
                pieChart.getDescription().setEnabled(false);
                pieChart.setCenterText("Расходы");
                pieChart.animate();
            }

        });
        return v;
    }
}
