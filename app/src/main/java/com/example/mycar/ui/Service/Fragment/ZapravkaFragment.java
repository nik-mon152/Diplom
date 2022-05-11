package com.example.mycar.ui.Service.Fragment;



import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import com.example.mycar.ui.Service.ModelZapravka.Adapter;
import com.example.mycar.ui.Service.ModelZapravka.Zapravka;
import com.example.mycar.R;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;


public class ZapravkaFragment extends Fragment {

    RecyclerView zapravkaLists;
    ArrayList<Zapravka> zapravkaArrayList;
    FirebaseFirestore fstore;
    Adapter adapter;
    FirebaseUser user;
    FirebaseAuth fAuth;
//    FirestoreRecyclerAdapter<Zapravka, ZapravkaViewHolder> zapravkaAdapter;




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
        fstore.collection("Zapravki").document(user.getUid()).collection("myZapravki").get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        List<DocumentSnapshot> list = queryDocumentSnapshots.getDocuments();
                        for (DocumentSnapshot d:list){
                            Zapravka object = d.toObject(Zapravka.class);
                            zapravkaArrayList.add(object);
                        }
                        adapter.notifyDataSetChanged();
                    }
                });
        return v;
    }


}