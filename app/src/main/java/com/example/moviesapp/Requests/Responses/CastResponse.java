package com.example.moviesapp.Requests.Responses;

import com.example.moviesapp.Models.Cast;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class CastResponse {

    @SerializedName("cast")
    @Expose
    private List<Cast> getListOfCast;

    public List<Cast> getGetListOfCast() {
        return getListOfCast;
    }

    @Override
    public String toString() {
        return "CastResponse{" +
                "getListOfCast=" + getListOfCast +
                '}';
    }
}
