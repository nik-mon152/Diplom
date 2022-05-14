package com.example.mycar.ui.Service.Model;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;


import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mycar.R;
import com.example.mycar.ui.Service.Details.ZapravkaDetail;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.orhanobut.dialogplus.DialogPlus;
import com.orhanobut.dialogplus.ViewHolder;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AdapterZapravka extends RecyclerView.Adapter<AdapterZapravka.viewholder> {

    Context context;
    List<Zapravka> zapravkaArrayList;
    FirebaseFirestore fstore;
    FirebaseUser user;
    FirebaseAuth fAuth;


    public AdapterZapravka(List<Zapravka> zapravkaArrayList) {
        this.context = context;
        this.zapravkaArrayList = zapravkaArrayList;
        fstore = FirebaseFirestore.getInstance();
        fAuth = FirebaseAuth.getInstance();
        user = fAuth.getCurrentUser();

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


        holder.deleteItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder dialogdelete = new AlertDialog.Builder(v.getContext());
                dialogdelete.setTitle("Удалить информацию?");
                dialogdelete.setPositiveButton("Да", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        fstore.collection("Zapravki").document(user.getUid()).collection("myZapravki").document(documentId)
                                .delete()
                                .addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        if (task.isSuccessful()){
                                            zapravkaArrayList.remove(position);
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
        holder.updateItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final DialogPlus dialogPlus = DialogPlus.newDialog(holder.itemView.getContext())
                        .setContentHolder(new ViewHolder(R.layout.dialog_context))
                        .setExpanded(true, 1300)
                        .create();

                View contentView = dialogPlus.getHolderView();
                EditText viewFuelEdd = contentView.findViewById(R.id.viewFuelAdd);
                EditText countEdd = contentView.findViewById(R.id.countAdd);
                EditText cummEdd = contentView.findViewById(R.id.cummAdd);
                EditText priceEdd = contentView.findViewById(R.id.priceAdd);
                EditText probegEdd = contentView.findViewById(R.id.probegAdd);
                EditText dataEdd = contentView.findViewById(R.id.dataAdd);
                EditText commentEdd = contentView.findViewById(R.id.commentAdd);
                Button btnEdd = contentView.findViewById(R.id.btnEdit);


                viewFuelEdd.setText(zapravkaArrayList.get(position).getView_Fuel());
                countEdd.setText(zapravkaArrayList.get(position).getFuel_quantity());
                cummEdd.setText(zapravkaArrayList.get(position).getRefueling_amount());
                priceEdd.setText(zapravkaArrayList.get(position).getPrice_liter());
                probegEdd.setText(zapravkaArrayList.get(position).getMileage());
                dataEdd.setText(zapravkaArrayList.get(position).getData());
                commentEdd.setText(zapravkaArrayList.get(position).getComment());


                dialogPlus.show();

                btnEdd.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Map<String, Object> zapravkaEdd = new HashMap<>();
                        zapravkaEdd.put("view_Fuel", viewFuelEdd.getText().toString());
                        zapravkaEdd.put("fuel_quantity", countEdd.getText().toString());
                        zapravkaEdd.put("refueling_amount", cummEdd.getText().toString());
                        zapravkaEdd.put("price_liter", priceEdd.getText().toString());
                        zapravkaEdd.put("mileage", probegEdd.getText().toString());
                        zapravkaEdd.put("comment", commentEdd.getText().toString());
                        zapravkaEdd.put("data", dataEdd.getText().toString());
                        fstore.collection("Zapravki").document(user.getUid()).collection("myZapravki").document(documentId)
                                .update(zapravkaEdd)
                                .addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void unused) {
                                        Toast.makeText(v.getContext(), "Обновите страницу чтобы применить изменения", Toast.LENGTH_SHORT).show();
                                        notifyDataSetChanged();
                                        dialogPlus.dismiss();
                                    }
                                });
                    }
                });
            }
        });
    }

    @Override
    public int getItemCount() {
        return zapravkaArrayList.size();
    }

    class viewholder extends RecyclerView.ViewHolder{
        TextView fuelList, cummList, litrList, priceList, probegList, commentList, dataList;
        FloatingActionButton add;
        ImageButton deleteItem, updateItem;
        public viewholder(@NonNull View itemView) {
            super(itemView);
            fuelList = itemView.findViewById(R.id.fuelList);
            cummList = itemView.findViewById(R.id.cummList);
            litrList = itemView.findViewById(R.id.litrList);
            priceList = itemView.findViewById(R.id.priceList);
            probegList = itemView.findViewById(R.id.prodegList);
            commentList = itemView.findViewById(R.id.commentList);
            dataList = itemView.findViewById(R.id.dataList);
            deleteItem = itemView.findViewById(R.id.deleteItem);
            add = itemView.findViewById(R.id.add);
            updateItem = itemView.findViewById(R.id.updateItem);
        }
    }
}
