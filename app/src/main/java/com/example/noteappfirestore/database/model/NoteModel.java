package com.example.noteappfirestore.database.model;

import com.google.firebase.firestore.ServerTimestamp;

import com.google.firebase.firestore.ServerTimestamp;

import java.io.Serializable;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;


public class NoteModel implements Serializable {
    private String id, image_url,title , des;
    @ServerTimestamp
    Date time , date;
    public String formatDate(){
        SimpleDateFormat  simpleDateFormat = new SimpleDateFormat("E, dd/MMMM/yyyy ");
        return  simpleDateFormat.format(date);
    }

    public String formatTime(){
        SimpleDateFormat  simpleDateFormat = new SimpleDateFormat("HH:mm:ss a ");
        return  simpleDateFormat.format(time);
    }

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getDes() {
        return des;
    }

    public void setDes(String des) {
        this.des = des;
    }

    public NoteModel() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getImage_url() {
        return image_url;
    }

    public void setImage_url(String image_url) {
        this.image_url = image_url;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
