package com.example.mycar.ui.Notification;

import java.io.Serializable;

public class Notification implements Serializable {
    private String view_work_notif;
    private String work_notif;
    private String adress_notif;
    private String price_notif;
    private int probeg_notif;
    private String comment_notif;
    String docId;

    public Notification(){

    }

    public Notification(String view_work_notif, String work_notif, String adress_notif, String price_notif, int probeg_notif, String comment_notif) {
        this.view_work_notif = view_work_notif;
        this.work_notif = work_notif;
        this.adress_notif = adress_notif;
        this.price_notif = price_notif;
        this.probeg_notif = probeg_notif;
        this.comment_notif = comment_notif;
    }

    public String getView_work_notif() {
        return view_work_notif;
    }

    public void setView_work_notif(String view_work_notif) {
        this.view_work_notif = view_work_notif;
    }

    public String getWork_notif() {
        return work_notif;
    }

    public void setWork_notif(String work_notif) {
        this.work_notif = work_notif;
    }

    public String getAdress_notif() {
        return adress_notif;
    }

    public void setAdress_notif(String adress_notif) {
        this.adress_notif = adress_notif;
    }

    public String getPrice_notif() {
        return price_notif;
    }

    public void setPrice_notif(String price_notif) {
        this.price_notif = price_notif;
    }

    public int getProbeg_notif() {
        return probeg_notif;
    }

    public void setProbeg_notif(int probeg_notif) {
        this.probeg_notif = probeg_notif;
    }

    public String getComment_notif() {
        return comment_notif;
    }

    public void setComment_notif(String comment_notif) {
        this.comment_notif = comment_notif;
    }

    public String getDocId() {
        return docId;
    }

    public void setDocId(String docId) {
        this.docId = docId;
    }
}
