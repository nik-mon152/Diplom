package com.example.mycar.ui.Service.Fragment;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.example.mycar.ui.Service.AddingService.AddZapravka;
import com.example.mycar.ui.Service.Model.AdapterZapravka;
import com.example.mycar.ui.Service.Model.Zapravka;
import com.example.mycar.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;
import com.orhanobut.dialogplus.DialogPlus;
import com.orhanobut.dialogplus.ViewHolder;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class ZapravkaFragment extends Fragment{

    RecyclerView zapravkaLists;
    List<Zapravka> zapravkaArrayList;
    FirebaseFirestore fstore;
    AdapterZapravka adapterZapravka;
    FirebaseUser user;
    FirebaseAuth fAuth;
    SwipeRefreshLayout swipeRefreshLayout;
    FloatingActionButton fab;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_zapravka, container, false);

        fstore = FirebaseFirestore.getInstance();
        fAuth = FirebaseAuth.getInstance();
        user = fAuth.getCurrentUser();

        fab = v.findViewById(R.id.add);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getContext(), AddZapravka.class));
            }
        });

        swipeRefreshLayout = v.findViewById(R.id.update);
        swipeRefreshLayout.setColorSchemeColors(Color.RED, Color.GREEN, Color.BLUE, Color.CYAN);

        zapravkaLists = v.findViewById(R.id.zapravralist);
        zapravkaLists.setLayoutManager(new StaggeredGridLayoutManager(1, LinearLayoutManager.VERTICAL));

        zapravkaArrayList = new ArrayList<>();
        adapterZapravka = new AdapterZapravka(zapravkaArrayList);
        zapravkaLists.setAdapter(adapterZapravka);
        fstore.collection("Zapravki").document(user.getUid()).collection("myZapravki").orderBy("view_Fuel", Query.Direction.DESCENDING).get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()){
                            for (DocumentSnapshot documentSnapshot : task.getResult().getDocuments()){
                                String docId = documentSnapshot.getId();
                                Zapravka zapravka = documentSnapshot.toObject(Zapravka.class);
                                assert zapravka != null;
                                zapravka.setDocId(docId);
                                zapravkaArrayList.add(zapravka);
                                adapterZapravka.notifyDataSetChanged();
                                swipeRefreshLayout.setRefreshing(false);
                            }
                        }
                    }
                });

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                fstore.collection("Zapravki").document(user.getUid()).collection("myZapravki").get()
                        .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                            @Override
                            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                                zapravkaLists.setLayoutManager(new StaggeredGridLayoutManager(1, LinearLayoutManager.VERTICAL));
                                zapravkaArrayList = new ArrayList<>();
                                adapterZapravka = new AdapterZapravka(zapravkaArrayList);
                                zapravkaLists.setAdapter(adapterZapravka);
                                fstore.collection("Zapravki").document(user.getUid()).collection("myZapravki").orderBy("view_Fuel", Query.Direction.DESCENDING).get()
                                        .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                            @Override
                                            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                                if (task.isSuccessful()){
                                                    for (DocumentSnapshot documentSnapshot : task.getResult().getDocuments()){
                                                        String docId = documentSnapshot.getId();
                                                        Zapravka zapravka = documentSnapshot.toObject(Zapravka.class);
                                                        assert zapravka != null;
                                                        zapravka.setDocId(docId);
                                                        zapravkaArrayList.add(zapravka);
                                                        adapterZapravka.notifyDataSetChanged();
                                                        swipeRefreshLayout.setRefreshing(false);
                                                    }
                                                }
                                            }
                                        });
                                adapterZapravka.notifyDataSetChanged();
                                swipeRefreshLayout.setRefreshing(false);
                            }
                        });
            }
        });
        return v;
    }

}