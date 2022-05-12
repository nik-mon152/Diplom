package com.example.mycar.ui.Service.Fragment;




import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import com.example.mycar.ui.Service.AddingService.AddZapravka;
import com.example.mycar.ui.Service.Model.Adapter;
import com.example.mycar.ui.Service.Model.Zapravka;
import com.example.mycar.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;


public class ZapravkaFragment extends Fragment {

    RecyclerView zapravkaLists;
    List<Zapravka> zapravkaArrayList;
    FirebaseFirestore fstore;
    Adapter adapter;
    FirebaseUser user;
    FirebaseAuth fAuth;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_zapravka, container, false);

        fstore = FirebaseFirestore.getInstance();
        fAuth = FirebaseAuth.getInstance();
        user = fAuth.getCurrentUser();


        zapravkaLists = v.findViewById(R.id.zapravralist);
        zapravkaLists.setLayoutManager(new StaggeredGridLayoutManager(1, LinearLayoutManager.VERTICAL));
        zapravkaArrayList = new ArrayList<>();
        adapter = new Adapter(zapravkaArrayList);
        zapravkaLists.setAdapter(adapter);
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
                                adapter.notifyDataSetChanged();
                            }
                        }
                    }
                });
        return v;
    }

}