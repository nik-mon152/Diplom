package com.example.mycar.ui.Service.Model;

import java.io.Serializable;

public class Servise implements Serializable {
    private String view_Servise;
    private String name_work;
    private String adress;
    private int price_servise;
    private int mileage;
    private String comment;
    private String data;
    String docId;

    public Servise(){
    }

    public Servise(String view_Servise, String name_work, String adress, int price_servise, int mileage, String comment, String data){
        this.view_Servise = view_Servise;
        this.name_work = name_work;
        this.adress = adress;
        this.price_servise = price_servise;
        this.mileage = mileage;
        this.comment = comment;
        this.data = data;
    }

    public String getView_Servise() {
        return view_Servise;
    }

    public void setView_Servise(String view_Servise) {
        this.view_Servise = view_Servise;
    }

    public String getName_work() {
        return name_work;
    }

    public void setName_work(String name_work) {
        this.name_work = name_work;
    }

    public String getAdress() {
        return adress;
    }

    public void setAdress(String adress) {
        this.adress = adress;
    }

    public int getPrice_servise() {
        return price_servise;
    }

    public void setPrice_servise(int price_servise) {
        this.price_servise = price_servise;
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

    public String getDocId() {
        return docId;
    }

    public void setDocId(String docId) {
        this.docId = docId;
    }
}
