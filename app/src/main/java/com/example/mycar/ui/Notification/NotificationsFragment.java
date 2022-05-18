package com.example.mycar.ui.Notification;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.mycar.R;
import com.example.mycar.ui.Service.AddingService.AddZapravka;
import com.example.mycar.ui.Service.Model.AdapterZapravka;
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

public class NotificationsFragment extends Fragment {

    FloatingActionButton fab;
    SwipeRefreshLayout swipeRefreshLayout;
    FirebaseUser user;
    FirebaseAuth fAuth;
    FirebaseFirestore fstore;
    RecyclerView notificationLists;
    List<Notification> notificationList;
    AdapterNotification adapterNotification;
    public static Context context;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_notifications, container, false);

        fstore = FirebaseFirestore.getInstance();
        fAuth = FirebaseAuth.getInstance();
        user = fAuth.getCurrentUser();
        context = getContext();

        fab = v.findViewById(R.id.add);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getContext(), NotificationAdd.class));

            }
        });

        swipeRefreshLayout = v.findViewById(R.id.update);
        swipeRefreshLayout.setColorSchemeColors(Color.RED, Color.GREEN, Color.BLUE, Color.CYAN);

        notificationLists = v.findViewById(R.id.notificationlist);
        notificationLists.setLayoutManager(new StaggeredGridLayoutManager(1, LinearLayoutManager.VERTICAL));

        notificationList = new ArrayList<>();
        adapterNotification = new AdapterNotification(notificationList);
        notificationLists.setAdapter(adapterNotification);
        fstore.collection("Notification").document(user.getUid()).collection("myNotifications").orderBy("work_notif", Query.Direction.DESCENDING).get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()){
                            for (DocumentSnapshot documentSnapshot : task.getResult().getDocuments()){
                                String docId = documentSnapshot.getId();
                                Notification notification = documentSnapshot.toObject(Notification.class);
                                assert notification != null;
                                notification.setDocId(docId);
                                notificationList.add(notification);
                                adapterNotification.notifyDataSetChanged();
                                swipeRefreshLayout.setRefreshing(false);
                            }
                        }
                    }
                });
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                fstore.collection("Notification").document(user.getUid()).collection("myNotifications").get()
                        .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                            @Override
                            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                                notificationLists.setLayoutManager(new StaggeredGridLayoutManager(1, LinearLayoutManager.VERTICAL));
                                notificationList = new ArrayList<>();
                                adapterNotification = new AdapterNotification(notificationList);
                                notificationLists.setAdapter(adapterNotification);
                                fstore.collection("Notification").document(user.getUid()).collection("myNotifications").orderBy("work_notif", Query.Direction.DESCENDING).get()
                                        .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                            @Override
                                            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                                if (task.isSuccessful()){
                                                    for (DocumentSnapshot documentSnapshot : task.getResult().getDocuments()){
                                                        String docId = documentSnapshot.getId();
                                                        Notification notification = documentSnapshot.toObject(Notification.class);
                                                        assert notification != null;
                                                        notification.setDocId(docId);
                                                        notificationList.add(notification);
                                                        adapterNotification.notifyDataSetChanged();
                                                        swipeRefreshLayout.setRefreshing(false);
                                                    }
                                                }
                                            }
                                        });
                                adapterNotification.notifyDataSetChanged();
                                swipeRefreshLayout.setRefreshing(false);
                            }
                        });
            }
        });

        return v;
    }
}