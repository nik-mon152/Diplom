package com.example.mycar.ui.Notification;

import android.os.Bundle;
import android.os.Handler;
import android.view.MenuItem;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.mycar.R;

import java.util.Objects;

public class NotificationDetail extends AppCompatActivity {

    EditText work, work_view, adressn, cumm, probegn, commentn;
    Notification notification = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        setContentView(R.layout.activity_notification_detail);
        setTitle("Просмотр данных");

        work_view = findViewById(R.id.ViewWorkNot);
        work = findViewById(R.id.nameWorkNotif);
        adressn = findViewById(R.id.AdressNotif);
        cumm = findViewById(R.id.priceNotif);
        probegn = findViewById(R.id.notifprobeg);
        commentn = findViewById(R.id.notifComment);

        final Object object = getIntent().getSerializableExtra("notification");
        if (object instanceof Notification){
            notification = (Notification) object;
        }

        if (notification != null){
            work_view.setText(notification.getView_work_notif());
            work.setText(notification.getWork_notif());
            adressn.setText(notification.getAdress_notif());
            probegn.setText(notification.getProbeg_notif() + "");
            commentn.setText(notification.getComment_notif());
            cumm.setText(notification.getPrice_notif() + "");

        }
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();

        if (id == androidx.appcompat.R.id.action_bar){

            Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                public void run() {
                    onBackPressed();
                }
            }, 10000);
        }else if (id == android.R.id.home){
            finish();
        }
        return super.onOptionsItemSelected(item);
    }
}