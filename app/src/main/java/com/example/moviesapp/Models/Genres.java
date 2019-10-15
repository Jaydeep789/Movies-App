package com.example.moviesapp.Models;

import com.google.gson.annotations.SerializedName;

public class Genres {

    @SerializedName("name")
    private String name;

    public Genres(String name) {
        this.name = name;
    }

    public Genres() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Genres{" +
                "name='" + name + '\'' +
                '}';
    }
}
