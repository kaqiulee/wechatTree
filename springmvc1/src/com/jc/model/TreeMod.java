package com.jc.model;

import java.util.Date;

public class TreeMod extends CommonHeader{
    private Integer userId;
    private String name;
    private Integer life;
    private Double latitude;
    private Double longitude;
    private Date time;

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getLife() {
        return life;
    }

    public void setLife(Integer life) {
        this.life = life;
    }

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }

    @Override
    public String toString() {
        return "TreeMod{" +
                "userId=" + userId +
                ", name='" + name + '\'' +
                ", life=" + life +
                ", latitude='" + latitude + '\'' +
                ", longitude='" + longitude + '\'' +
                ", time=" + time +
                '}';
    }
}