package com.example.mycar.ui.Notification;

import android.annotation.SuppressLint;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Build;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mycar.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;

import java.util.List;

public class AdapterNotification extends RecyclerView.Adapter<AdapterNotification.viewholder>{

    Context context;
    List<Notification> notificationList;
    FirebaseFirestore fstore;
    FirebaseUser user;
    FirebaseAuth fAuth;
    String userId;
    int prob;


    public AdapterNotification(List<Notification> notificationList) {
        this.context = context;
        this.notificationList = notificationList;
        fstore = FirebaseFirestore.getInstance();
        fAuth = FirebaseAuth.getInstance();
        user = fAuth.getCurrentUser();
        userId = fAuth.getCurrentUser().getUid();

    }

    @NonNull
    @Override
    public viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.notification_view_layout, parent, false);
        return new viewholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull viewholder holder, @SuppressLint("RecyclerView") int position) {
        holder.name_work_notifList.setText(notificationList.get(position).getWork_notif());
        holder.work_notifList.setText(notificationList.get(position).getView_work_notif());
        holder.price_notifList.setText(notificationList.get(position).getPrice_notif() + " ₽");
        holder.adress_notifList.setText(notificationList.get(position).getAdress_notif());
        holder.probeg_notifList.setText(notificationList.get(position).getProbeg_notif() + " км");
        holder.comment_notifList.setText(notificationList.get(position).getComment_notif());
        String documentId = notificationList.get(position).getDocId();

        DocumentReference documentReference = fstore.collection("Cars").document(userId);
        documentReference.addSnapshotListener(new EventListener<DocumentSnapshot>() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onEvent(@Nullable DocumentSnapshot documentSnapshot, @Nullable FirebaseFirestoreException error) {
                if (documentSnapshot != null) {
                    if(documentSnapshot.exists()){
                        prob = documentSnapshot.getLong("Mileage").intValue();

                    }else{
                        Log.d("Сообщение об ошибке", "Ошибка в документе");
                    }
                }
            }
        });

        holder.deleteItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder dialogdelete = new AlertDialog.Builder(v.getContext());
                dialogdelete.setTitle("Удалить информацию?");
                dialogdelete.setPositiveButton("Да", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        fstore.collection("Notification").document(user.getUid()).collection("myNotifications").document(documentId)
                                .delete()
                                .addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        if (task.isSuccessful()){
                                            notificationList.remove(position);
                                            notifyDataSetChanged();
                                        }
                                    }
                                });
                    }
                });
                dialogdelete.setNegativeButton("Нет", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
                dialogdelete.create().show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return notificationList.size();
    }

    class viewholder extends RecyclerView.ViewHolder {
        TextView name_work_notifList, work_notifList, adress_notifList, price_notifList, probeg_notifList, comment_notifList;
        ImageButton deleteItem, updateItem;
        public viewholder(@NonNull View itemView) {
            super(itemView);
            name_work_notifList = itemView.findViewById(R.id.name_work_notifList);
            work_notifList = itemView.findViewById(R.id.view_workList);
            price_notifList = itemView.findViewById(R.id.price_notifList);
            adress_notifList = itemView.findViewById(R.id.adress_notifLis);
            probeg_notifList = itemView.findViewById(R.id.probeg_notifList);
            comment_notifList = itemView.findViewById(R.id.comment_notifList);
            deleteItem = itemView.findViewById(R.id.deleteItem);
        }
    }
}
