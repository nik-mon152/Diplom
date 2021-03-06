package com.example.mycar.ui.Service.Model;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.text.Editable;
import android.text.TextWatcher;
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
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.orhanobut.dialogplus.DialogPlus;
import com.orhanobut.dialogplus.ViewHolder;

import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AdapterZapravka extends RecyclerView.Adapter<AdapterZapravka.viewholder> {

    Context context;
    List<Zapravka> zapravkaList;
    FirebaseFirestore fstore;
    FirebaseUser user;
    FirebaseAuth fAuth;


    public AdapterZapravka(List<Zapravka> zapravkaArrayList) {
        this.context = context;
        this.zapravkaList = zapravkaArrayList;
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

        holder.fuelList.setText(zapravkaList.get(position).getView_Fuel());
        holder.cummList.setText(zapravkaList.get(position).getRefueling_amount() + " ???");
        holder.litrList.setText(zapravkaList.get(position).getFuel_quantity() + " ??");
        holder.priceList.setText(zapravkaList.get(position).getPrice_liter() + " ???");
        holder.probegList.setText(zapravkaList.get(position).getMileage() + " ????");
        holder.commentList.setText(zapravkaList.get(position).getComment());
        holder.dataList.setText(zapravkaList.get(position).getData());
        String documentId = zapravkaList.get(position).getDocId();

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), ZapravkaDetail.class);
                intent.putExtra("detail_zaprav", zapravkaList.get(position));
                v.getContext().startActivity(intent);
            }
        });


        holder.deleteItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder dialogdelete = new AlertDialog.Builder(v.getContext());
                dialogdelete.setTitle("?????????????? ?????????????????????");
                dialogdelete.setPositiveButton("????", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        fstore.collection("Zapravki").document(user.getUid()).collection("myZapravki").document(documentId)
                                .delete()
                                .addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        if (task.isSuccessful()){
                                            zapravkaList.remove(position);
                                            notifyDataSetChanged();
                                        }
                                    }
                                });
                    }
                });
                dialogdelete.setNegativeButton("??????", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
                dialogdelete.create().show();
            }
        });
        holder.updateItem.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onClick(View v) {
                final DialogPlus dialogPlus = DialogPlus.newDialog(holder.itemView.getContext())
                        .setContentHolder(new ViewHolder(R.layout.dialog_zapravka))
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

                TextWatcher tw = new TextWatcher() {
                    private String current = "";
                    private String ddmmyyyy = "DDMMYYYY";
                    private Calendar cal = Calendar.getInstance();
                    @Override
                    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                    }

                    @Override
                    public void onTextChanged(CharSequence s, int start, int before, int count) {
                        if (!s.toString().equals(current)) {
                            String clean = s.toString().replaceAll("[^\\d.]|\\.", "");
                            String cleanC = current.replaceAll("[^\\d.]|\\.", "");

                            int cl = clean.length();
                            int sel = cl;
                            for (int i = 2; i <= cl && i < 6; i += 2) {
                                sel++;
                            }
                            if (clean.equals(cleanC)) sel--;

                            if (clean.length() < 8){
                                clean = clean + ddmmyyyy.substring(clean.length());
                            }else{
                                int day  = Integer.parseInt(clean.substring(0,2));
                                int mon  = Integer.parseInt(clean.substring(2,4));
                                int year = Integer.parseInt(clean.substring(4,8));

                                mon = mon < 1 ? 1 : mon > 12 ? 12 : mon;
                                cal.set(Calendar.MONTH, mon-1);
                                year = (year<1900)?1900:(year>2100)?2100:year;
                                cal.set(Calendar.YEAR, year);

                                day = (day > cal.getActualMaximum(Calendar.DATE))? cal.getActualMaximum(Calendar.DATE):day;
                                clean = String.format("%02d%02d%02d",day, mon, year);
                            }

                            clean = String.format("%s.%s.%s", clean.substring(0, 2),
                                    clean.substring(2, 4),
                                    clean.substring(4, 8));

                            sel = sel < 0 ? 0 : sel;
                            current = clean;
                            dataEdd.setText(current);
                            dataEdd.setSelection(sel < current.length() ? sel : current.length());
                        }

                    }

                    @Override
                    public void afterTextChanged(Editable s) {

                    }
                };
                dataEdd.addTextChangedListener(tw);


                viewFuelEdd.setText(zapravkaList.get(position).getView_Fuel());
                countEdd.setText(zapravkaList.get(position).getFuel_quantity());
                cummEdd.setText(Integer.toString(zapravkaList.get(position).getRefueling_amount()));
                priceEdd.setText(zapravkaList.get(position).getPrice_liter());
                probegEdd.setText(Integer.toString(zapravkaList.get(position).getMileage()));
                dataEdd.setText(zapravkaList.get(position).getData());
                commentEdd.setText(zapravkaList.get(position).getComment());


                dialogPlus.show();

                btnEdd.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(cummEdd.getText().toString().isEmpty()){
                            cummEdd.setError("?????????????? ?????????????????? ????????????????");
                            return;
                        }
                        if(probegEdd.getText().toString().isEmpty()){
                            probegEdd.setError("?????????????? ????????????");
                            return;
                        }
                        String fuel = viewFuelEdd.getText().toString();
                        String count = countEdd.getText().toString();
                        int cumm = Integer.parseInt(cummEdd.getText().toString());
                        String price = priceEdd.getText().toString();
                        int probeg = Integer.parseInt(cummEdd.getText().toString());
                        String coment = commentEdd.getText().toString();
                        String data = dataEdd.getText().toString();

                        if(fuel.isEmpty()){
                            viewFuelEdd.setError("?????????????? ?????? ??????????????");
                            return;
                        }
                        if(count.isEmpty()){
                            countEdd.setError("?????????????? ???????????????????? ??????????????");
                            return;
                        }
                        if(cumm == 0){
                            cummEdd.setError("?????????????? ?????????????????? ????????????????");
                            return;
                        }
                        if(price.isEmpty()){
                            priceEdd.setError("?????????????? ???????? ???????????? ??????!!!");
                            return;
                        }
                        if(probeg == 0){
                            probegEdd.setError("?????????????? ????????????");
                            return;
                        }
                        if(coment.isEmpty()){
                            commentEdd.setError("?????????????? ????????????????????");
                            return;
                        }
                        if(data.isEmpty()){
                            dataEdd.setError("?????????????? ???????? ????????????????");
                            return;
                        }

                        Map<String, Object> zapravkaEdd = new HashMap<>();
                        zapravkaEdd.put("view_Fuel", fuel);
                        zapravkaEdd.put("fuel_quantity", count);
                        zapravkaEdd.put("refueling_amount", cumm);
                        zapravkaEdd.put("price_liter", price);
                        zapravkaEdd.put("mileage", probeg);
                        zapravkaEdd.put("comment", coment);
                        zapravkaEdd.put("data", data);
                        fstore.collection("Zapravki").document(user.getUid()).collection("myZapravki").document(documentId)
                                .update(zapravkaEdd)
                                .addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void unused) {
                                        Toast.makeText(v.getContext(), "???????????????? ???????????????? ?????????? ?????????????????? ??????????????????", Toast.LENGTH_SHORT).show();
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
        return zapravkaList.size();
    }

    class viewholder extends RecyclerView.ViewHolder{
        TextView fuelList, cummList, litrList, priceList, probegList, commentList, dataList;
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
            updateItem = itemView.findViewById(R.id.updateItem);
        }
    }
}
