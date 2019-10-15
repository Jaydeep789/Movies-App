package com.example.moviesapp.Models;

import com.google.gson.annotations.SerializedName;

public class Video {

    private String key;

    private String type;

    public Video(String key, String type) {
        this.key = key;
        this.type = type;
    }

    public Video() {
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return "Video{" +
                "key='" + key + '\'' +
                ", type='" + type + '\'' +
                '}';
    }
}
