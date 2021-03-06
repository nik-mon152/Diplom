package com.example.mycar.ui.Service.Model;

import java.io.Serializable;

public class Zapravka implements Serializable {
    private String view_Fuel;
    private String fuel_quantity;
    private int refueling_amount;
    private String price_liter;
    private int mileage;
    private String comment;
    private String data;
    String docId;


    public Zapravka(){
    }

    public Zapravka(String view_Fuel, String fuel_quantity, int refueling_amount, String price_liter, int mileage, String comment, String data) {
        this.view_Fuel = view_Fuel;
        this.fuel_quantity = fuel_quantity;
        this.refueling_amount = refueling_amount;
        this.price_liter = price_liter;
        this.mileage = mileage;
        this.comment = comment;
        this.data = data;
    }

    public String getDocId() {
        return docId;
    }

    public void setDocId(String docId) {
        this.docId = docId;
    }

    public String getView_Fuel() {
        return view_Fuel;
    }

    public void setView_Fuel(String view_Fuel) {
        this.view_Fuel = view_Fuel;
    }

    public String getFuel_quantity() {
        return fuel_quantity;
    }

    public void setFuel_quantity(String fuel_quantity) {
        this.fuel_quantity = fuel_quantity;
    }

    public int getRefueling_amount() {
        return refueling_amount;
    }

    public void setRefueling_amount(int refueling_amount) {
        this.refueling_amount = refueling_amount;
    }

    public String getPrice_liter() {
        return price_liter;
    }

    public void setPrice_liter(String price_liter) {
        this.price_liter = price_liter;
    }

    public int getMileage() {
        return mileage;
    }

    public void setMileage(int mileage) {
        this.mileage = mileage;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }
}

