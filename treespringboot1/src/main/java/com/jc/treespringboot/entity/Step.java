package com.jc.treespringboot.entity;

import java.util.Date;

public class Step {
    int id ;
    int userId;
    Date date;
    int step;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public int getStep() {
        return step;
    }

    public void setStep(int step) {
        this.step = step;
    }

    @Override
    public String toString() {
        return "Step{" +
                "id=" + id +
                ", userId=" + userId +
                ", date=" + date +
                ", step=" + step +
                '}';
    }
}
