package com.example.mycar.ui.Service.Fragment;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.mycar.R;
import com.example.mycar.ui.Service.AddingService.AddService;
import com.example.mycar.ui.Service.Model.AdapterServise;
import com.example.mycar.ui.Service.Model.AdapterZapravka;
import com.example.mycar.ui.Service.Model.Servise;
import com.example.mycar.ui.Service.Model.Zapravka;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class ServiseTableFragment extends Fragment {

    RecyclerView serviselists;
    List<Servise> serviseList;
    FirebaseFirestore fstore;
    AdapterServise adapterServise;
    FirebaseUser user;
    FirebaseAuth fAuth;
    SwipeRefreshLayout swipeRefreshLayout;
    FloatingActionButton fab;
    TextView textView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_servise_table, container, false);

        fstore = FirebaseFirestore.getInstance();
        fAuth = FirebaseAuth.getInstance();
        user = fAuth.getCurrentUser();
        textView = v.findViewById(R.id.total_serv);

        fab = v.findViewById(R.id.add);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getContext(), AddService.class));

            }
        });

        swipeRefreshLayout = v.findViewById(R.id.update);
        swipeRefreshLayout.setColorSchemeColors(Color.RED, Color.GREEN, Color.BLUE, Color.CYAN);

        serviselists = v.findViewById(R.id.serviselist);
        serviselists.setLayoutManager(new StaggeredGridLayoutManager(1, LinearLayoutManager.VERTICAL));

        serviseList = new ArrayList<>();
        adapterServise = new AdapterServise(serviseList);
        serviselists.setAdapter(adapterServise);

        fstore.collection("Servises").document(user.getUid()).collection("myServises").orderBy("name_work", Query.Direction.DESCENDING).get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()){
                            for (DocumentSnapshot documentSnapshot : task.getResult().getDocuments()){
                                String docId = documentSnapshot.getId();
                                Servise servise = documentSnapshot.toObject(Servise.class);
                                assert servise != null;
                                servise.setDocId(docId);
                                serviseList.add(servise);
                                adapterServise.notifyDataSetChanged();
                                swipeRefreshLayout.setRefreshing(false);
                            }
                            calculateTotal(serviseList);
                        }
                    }
                });

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                fstore.collection("Servises").document(user.getUid()).collection("myServises").get()
                        .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                            @Override
                            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                                serviselists.setLayoutManager(new StaggeredGridLayoutManager(1, LinearLayoutManager.VERTICAL));
                                serviseList = new ArrayList<>();
                                adapterServise = new AdapterServise(serviseList);
                                serviselists.setAdapter(adapterServise);
                                fstore.collection("Servises").document(user.getUid()).collection("myServises").orderBy("name_work", Query.Direction.DESCENDING).get()
                                        .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                            @Override
                                            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                                if (task.isSuccessful()){
                                                    for (DocumentSnapshot documentSnapshot : task.getResult().getDocuments()){
                                                        String docId = documentSnapshot.getId();
                                                        Servise servise = documentSnapshot.toObject(Servise.class);
                                                        assert servise != null;
                                                        servise.setDocId(docId);
                                                        serviseList.add(servise);
                                                        adapterServise.notifyDataSetChanged();
                                                        swipeRefreshLayout.setRefreshing(false);
                                                    }
                                                }
                                            }
                                        });
                                adapterServise.notifyDataSetChanged();
                                swipeRefreshLayout.setRefreshing(false);
                            }
                        });
            }
        });

        return v;
    }

    private void calculateTotal(List<Servise> serviseList) {
        int total = 0;
        for (Servise servise : serviseList){
            total += servise.getPrice_servise();
        }
        textView.setText("Всего: "+total);
    }
}