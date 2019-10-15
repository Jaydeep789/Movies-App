package com.example.moviesapp.Requests.Responses;

import com.example.moviesapp.Models.Video;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class VideoResponse {

    @SerializedName("results")
    @Expose
    private List<Video> videos;

    public List<Video> getVideos() {
        return videos;
    }

    @Override
    public String toString() {
        return "VideoResponse{" +
                "videos=" + videos +
                '}';
    }
}
