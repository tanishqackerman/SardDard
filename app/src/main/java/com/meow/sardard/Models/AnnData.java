package com.meow.sardard.Models;

public class AnnData {

    String title, msg, date;

    public AnnData() {

    }

    public AnnData(String title, String msg, String date) {
        this.title = title;
        this.msg = msg;
        this.date = date;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
