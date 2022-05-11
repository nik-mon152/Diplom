package com.example.mycar.ui.Service.ModelZapravka;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;


import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mycar.R;
import com.example.mycar.ui.Service.Details.ZapravkaDetail;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.List;

public class Adapter extends RecyclerView.Adapter<Adapter.viewholder> {

    Context context;
    List<Zapravka> zapravkaArrayList;
    FirebaseFirestore fstore;

    public Adapter(List<Zapravka> zapravkaArrayList) {
        this.context = context;
        this.zapravkaArrayList = zapravkaArrayList;
    }

    @NonNull
    @Override
    public viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.zapravka_view_layout, parent, false);
        return new viewholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull viewholder holder, @SuppressLint("RecyclerView") int position) {

        holder.fuelList.setText(zapravkaArrayList.get(position).getView_Fuel());
        holder.cummList.setText(zapravkaArrayList.get(position).getRefueling_amount() + " ₽");
        holder.litrList.setText(zapravkaArrayList.get(position).getFuel_quantity() + " л");
        holder.priceList.setText(zapravkaArrayList.get(position).getPrice_liter() + " ₽");
        holder.probegList.setText(zapravkaArrayList.get(position).getMileage() + " км");
        holder.commentList.setText(zapravkaArrayList.get(position).getComment());
        holder.dataList.setText(zapravkaArrayList.get(position).getData());
        String documentId = zapravkaArrayList.get(position).getDocId();

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), ZapravkaDetail.class);
                        intent.putExtra("Fuel", zapravkaArrayList.get(position).getView_Fuel());
                        intent.putExtra("Cumm", zapravkaArrayList.get(position).getFuel_quantity());
                        intent.putExtra("Litr", zapravkaArrayList.get(position).getRefueling_amount());
                        intent.putExtra("Price", zapravkaArrayList.get(position).getPrice_liter());
                        intent.putExtra("Probeg", zapravkaArrayList.get(position).getMileage());
                        intent.putExtra("Comment", zapravkaArrayList.get(position).getComment());
                        intent.putExtra("Data", zapravkaArrayList.get(position).getData());
                        intent.putExtra("documentId", documentId);
                        v.getContext().startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return zapravkaArrayList.size();
    }

    class viewholder extends RecyclerView.ViewHolder{
        TextView fuelList, cummList, litrList, priceList, probegList, commentList, dataList;
        public viewholder(@NonNull View itemView) {
            super(itemView);
            fuelList = itemView.findViewById(R.id.fuelList);
            cummList = itemView.findViewById(R.id.cummList);
            litrList = itemView.findViewById(R.id.litrList);
            priceList = itemView.findViewById(R.id.priceList);
            probegList = itemView.findViewById(R.id.prodegList);
            commentList = itemView.findViewById(R.id.commentList);
            dataList = itemView.findViewById(R.id.dataList);
        }
    }
}
