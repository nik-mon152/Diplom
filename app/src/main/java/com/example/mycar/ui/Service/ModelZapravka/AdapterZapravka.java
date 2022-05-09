package com.example.mycar.ui.Service.ModelZapravka;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mycar.R;

import java.util.List;

public class AdapterZapravka extends RecyclerView.Adapter<AdapterZapravka.ViewHolder> {
    List<String> fuels;
    List<String> cumms;
    List<String> litrs;
    List<String> prices;
    List<String> probegs;
    List<String> comments;
    List<String> dates;

    public AdapterZapravka(List<String> fuel, List<String> cumm, List<String> litr,
                           List<String> price, List<String> probeg, List<String> comment, List<String> date){
        this.fuels = fuel;
        this.cumms = cumm;
        this.litrs = litr;
        this.prices = price;
        this.probegs = probeg;
        this.comments = comment;
        this.dates = date;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.zapravka_view_layout, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.fuelList.setText(fuels.get(position));
        holder.cummList.setText(cumms.get(position));
        holder.litrList.setText(litrs.get(position));
        holder.priceList.setText(prices.get(position));
        holder.probegList.setText(probegs.get(position));
        holder.commentList.setText(comments.get(position));
        holder.dataList.setText(dates.get(position));
        holder.view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(view.getContext(), "click item", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return fuels.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView fuelList, cummList, litrList, priceList, probegList, commentList, dataList;
        View view;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            fuelList = itemView.findViewById(R.id.fuelList);
            cummList = itemView.findViewById(R.id.cummList);
            litrList = itemView.findViewById(R.id.litrList);
            priceList = itemView.findViewById(R.id.priceList);
            probegList = itemView.findViewById(R.id.prodegList);
            commentList = itemView.findViewById(R.id.commentList);
            dataList = itemView.findViewById(R.id.dataList);
            view = itemView;

        }
    }
}
