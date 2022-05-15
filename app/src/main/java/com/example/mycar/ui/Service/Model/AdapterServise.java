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
import com.example.mycar.ui.Service.Details.ServiseDetail;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.orhanobut.dialogplus.DialogPlus;
import com.orhanobut.dialogplus.ViewHolder;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AdapterServise extends RecyclerView.Adapter<AdapterServise.viewholder>{

    Context context;
    List<Servise> serviseList;
    FirebaseFirestore fstore;
    FirebaseUser user;
    FirebaseAuth fAuth;

    public AdapterServise(List<Servise> serviseListist) {
        this.context = context;
        this.serviseList = serviseListist;
        fstore = FirebaseFirestore.getInstance();
        fAuth = FirebaseAuth.getInstance();
        user = fAuth.getCurrentUser();

    }

    @NonNull
    @Override
    public viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.servise_view_layout, parent, false);
        return new viewholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull viewholder holder, @SuppressLint("RecyclerView") int position) {

        holder.view_ServiseList.setText(serviseList.get(position).getView_Servise());
        holder.name_workList.setText(serviseList.get(position).getName_work());
        holder.adressList.setText(serviseList.get(position).getAdress());
        holder.price_serviseList.setText(serviseList.get(position).getPrice_servise() + " ₽");
        holder.probegList.setText(serviseList.get(position).getMileage() + " км");
        holder.commentList.setText(serviseList.get(position).getComment());
        holder.dataList.setText(serviseList.get(position).getData());
        String documentId = serviseList.get(position).getDocId();

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), ServiseDetail.class);
                intent.putExtra("View_Servise", serviseList.get(position).getView_Servise());
                intent.putExtra("Name_work", serviseList.get(position).getName_work());
                intent.putExtra("Adress", serviseList.get(position).getAdress());
                intent.putExtra("Price_servise", serviseList.get(position).getPrice_servise());
                intent.putExtra("Probeg", serviseList.get(position).getMileage());
                intent.putExtra("Comment", serviseList.get(position).getComment());
                intent.putExtra("Data", serviseList.get(position).getData());
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
                        fstore.collection("Servises").document(user.getUid()).collection("myServises").document(documentId)
                                .delete()
                                .addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        if (task.isSuccessful()){
                                            serviseList.remove(position);
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
                        .setContentHolder(new ViewHolder(R.layout.dialog_servise))
                        .setExpanded(true, 1300)
                        .create();

                View contentView = dialogPlus.getHolderView();
                EditText view_Servise = contentView.findViewById(R.id.view_ServiseEdit);
                EditText name_work = contentView.findViewById(R.id.name_workEdit);
                EditText adress = contentView.findViewById(R.id.adressEdit);
                EditText price_servise = contentView.findViewById(R.id.price_serviseEdit);
                EditText probeg = contentView.findViewById(R.id.probegEdit);
                EditText data = contentView.findViewById(R.id.dataEdit);
                EditText comment = contentView.findViewById(R.id.commentEdit);
                Button btnEdd = contentView.findViewById(R.id.btnEdit);


                view_Servise.setText(serviseList.get(position).getView_Servise());
                name_work.setText(serviseList.get(position).getName_work());
                adress.setText(serviseList.get(position).getAdress());
                price_servise.setText(serviseList.get(position).getPrice_servise());
                probeg.setText(serviseList.get(position).getMileage());
                data.setText(serviseList.get(position).getData());
                comment.setText(serviseList.get(position).getComment());


                dialogPlus.show();

                btnEdd.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Map<String, Object> zapravkaEdd = new HashMap<>();
                        zapravkaEdd.put("view_Servise", view_Servise.getText().toString());
                        zapravkaEdd.put("name_work", name_work.getText().toString());
                        zapravkaEdd.put("adress", adress.getText().toString());
                        zapravkaEdd.put("price_servise", price_servise.getText().toString());
                        zapravkaEdd.put("mileage", probeg.getText().toString());
                        zapravkaEdd.put("comment", comment.getText().toString());
                        zapravkaEdd.put("data", data.getText().toString());
                        fstore.collection("Servises").document(user.getUid()).collection("myServises").document(documentId)
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
        return serviseList.size();
    }
    class viewholder extends RecyclerView.ViewHolder{
        TextView view_ServiseList, name_workList, adressList, price_serviseList, probegList, commentList, dataList;
        ImageButton deleteItem, updateItem;
        public viewholder(@NonNull View itemView) {
            super(itemView);
            view_ServiseList = itemView.findViewById(R.id.view_workList);
            name_workList = itemView.findViewById(R.id.name_workList);
            adressList = itemView.findViewById(R.id.adressList);
            price_serviseList = itemView.findViewById(R.id.cummservList);
            probegList = itemView.findViewById(R.id.prodegList);
            commentList = itemView.findViewById(R.id.commentList);
            dataList = itemView.findViewById(R.id.dataList);
            deleteItem = itemView.findViewById(R.id.deleteItem);
            updateItem = itemView.findViewById(R.id.updateItem);
        }
    }
}
