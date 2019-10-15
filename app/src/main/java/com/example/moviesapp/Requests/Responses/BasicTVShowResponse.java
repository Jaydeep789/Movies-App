package com.example.moviesapp.Requests.Responses;

import com.example.moviesapp.Models.BasicTVShow;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class BasicTVShowResponse {

    @SerializedName("total_results")
    @Expose
    private int count;

    @SerializedName("results")
    @Expose
    private List<BasicTVShow> basicTVShowList;

    public int getCount() {
        return count;
    }

    public List<BasicTVShow> getBasicTVShowList() {
        return basicTVShowList;
    }

    @Override
    public String toString() {
        return "BasicTVShowResponse{" +
                "count=" + count +
                ", basicTVShowList=" + basicTVShowList +
                '}';
    }
}
