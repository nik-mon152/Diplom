package com.example.mycar.ui.Notification;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.mycar.R;
import com.example.mycar.ui.Service.AddingService.AddZapravka;
import com.example.mycar.ui.Service.Model.AdapterZapravka;
import com.example.mycar.ui.Service.Model.Servise;
import com.example.mycar.ui.Service.Model.Zapravka;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class NotificationsFragment extends Fragment {

    FloatingActionButton fab;
    TextView textView;
    SwipeRefreshLayout swipeRefreshLayout;
    FirebaseUser user;
    FirebaseAuth fAuth;
    FirebaseFirestore fstore;
    String userId;
    int probeg_car;
    RecyclerView notificationLists;
    List<Notification> notificationList;
    AdapterNotification adapterNotification;
    public static Context context;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_notifications, container, false);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            NotificationChannel channel = new NotificationChannel("Mych", "Mychal1", NotificationManager.IMPORTANCE_HIGH);
            NotificationManager manager = getActivity().getSystemService(NotificationManager.class);
            manager.createNotificationChannel(channel);
        }

        fstore = FirebaseFirestore.getInstance();
        fAuth = FirebaseAuth.getInstance();
        user = fAuth.getCurrentUser();
        userId = fAuth.getCurrentUser().getUid();
        context = getContext();

        textView = v.findViewById(R.id.notif_probeg);

        DocumentReference documentReference = fstore.collection("Cars").document(userId);
        documentReference.addSnapshotListener(new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot documentSnapshot, @Nullable FirebaseFirestoreException error) {
                if (documentSnapshot != null) {
                    if(documentSnapshot.exists()){
                        textView.setText("Ваш текуший пробег: " + documentSnapshot.getLong("Mileage").intValue() + "км");
                        probeg_car = documentSnapshot.getLong("Mileage").intValue();
                    }else{
                        Log.d("Сообщение об ошибке", "Ошибка в документе");
                    }
                }
            }
        });

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
                            notification(notificationList);
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

    public void notification(List<Notification> notificationList) {
        int probeg = 0;
        for (Notification notification : notificationList){
            probeg = notification.getProbeg_notif();
        }
        if(probeg_car >= probeg && probeg != 0){
            NotificationCompat.Builder builder = new NotificationCompat.Builder(getActivity(), "Mych");
            builder.setSmallIcon(R.mipmap.ic_launcher);
            builder.setContentTitle("My car");
            builder.setContentText("Напоминание о записи");
            builder.setAutoCancel(true);
            builder.setAutoCancel(true);

            NotificationManagerCompat managerCompat = NotificationManagerCompat.from(getActivity());
            managerCompat.notify(1, builder.build());
        }
    }
}