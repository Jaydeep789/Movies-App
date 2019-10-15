package com.example.moviesapp.Requests.Responses;

import com.example.moviesapp.Models.BasicMovie;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class BasicMovieResponse {

    @SerializedName("total_results")
    @Expose
    private int count;

    @SerializedName("results")
    @Expose
    private List<BasicMovie> basicMovieList;

    public int getCount() {
        return count;
    }

    public List<BasicMovie> getBasicMovieList() {
        return basicMovieList;
    }

    @Override
    public String toString() {
        return "BasicMovieResponse{" +
                "count=" + count +
                ", basicMovieList=" + basicMovieList +
                '}';
    }
}
