package com.example.moviesapp.Requests.Responses;

import com.example.moviesapp.Models.DetailMovie;
import com.example.moviesapp.Models.Genres;
import com.example.moviesapp.Models.Video;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class DetailMovieResponse {

    @SerializedName("id")
    @Expose
    private String movie_id;

    @SerializedName("poster_path")
    @Expose
    private String movie_image_url;

    @SerializedName("title")
    @Expose
    private String movie_title;

    @SerializedName("runtime")
    @Expose
    private String movie_runtime;

    @SerializedName("release_date")
    @Expose
    private String movie_release_date;

    @SerializedName("overview")
    @Expose
    private String movie_overview;

    @SerializedName("genres")
    @Expose
    private List<Genres> movie_genre;

    @SerializedName("videos")
    @Expose
    private List<Video> movie_video;

    public String getMovie_id() {
        return movie_id;
    }

    public String getMovie_image_url() {
        return movie_image_url;
    }

    public String getMovie_title() {
        return movie_title;
    }

    public String getMovie_runtime() {
        return movie_runtime;
    }

    public String getMovie_release_date() {
        return movie_release_date;
    }

    public String getMovie_overview() {
        return movie_overview;
    }

    public List<Genres> getMovie_genre() {
        return movie_genre;
    }

    public List<Video> getMovie_video() {
        return movie_video;
    }

    @Override
    public String toString() {
        return "DetailMovieResponse{" +
                "movie_id='" + movie_id + '\'' +
                ", movie_image_url='" + movie_image_url + '\'' +
                ", movie_title='" + movie_title + '\'' +
                ", movie_runtime='" + movie_runtime + '\'' +
                ", movie_release_date='" + movie_release_date + '\'' +
                ", movie_overview='" + movie_overview + '\'' +
                ", movie_genre=" + movie_genre +
                ", movie_video=" + movie_video +
                '}';
    }
}
