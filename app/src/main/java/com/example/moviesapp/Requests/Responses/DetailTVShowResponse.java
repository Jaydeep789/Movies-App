package com.example.moviesapp.Requests.Responses;

import com.example.moviesapp.Models.DetailTVShow;
import com.example.moviesapp.Models.Genres;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.Arrays;
import java.util.List;

public class DetailTVShowResponse {

    @SerializedName("id")
    private String detail_show_id;

    @SerializedName("poster_path")
    private String detail_image_url;

    @SerializedName("original_name")
    private String detail_show_name;

    @SerializedName("first_air_date")
    private String detail_release_date;

    @SerializedName("number_of_seasons")
    private String detail_no_of_seasons;

    @SerializedName("number_of_episodes")
    private String detail_no_of_episodes;

    @SerializedName("episode_run_time")
    private String[] detail_episode_runtime;

    @SerializedName("overview")
    private String detail_overview;

    @SerializedName("genres")
    private List<Genres> details_genre;

    public String getDetail_show_id() {
        return detail_show_id;
    }

    public String getDetail_image_url() {
        return detail_image_url;
    }

    public String getDetail_show_name() {
        return detail_show_name;
    }

    public String getDetail_release_date() {
        return detail_release_date;
    }

    public String getDetail_no_of_seasons() {
        return detail_no_of_seasons;
    }

    public String getDetail_no_of_episodes() {
        return detail_no_of_episodes;
    }

    public String[] getDetail_episode_runtime() {
        return detail_episode_runtime;
    }

    public String getDetail_overview() {
        return detail_overview;
    }

    public List<Genres> getDetails_genre() {
        return details_genre;
    }

    @Override
    public String toString() {
        return "DetailTVShowResponse{" +
                "detail_show_id='" + detail_show_id + '\'' +
                ", detail_image_url='" + detail_image_url + '\'' +
                ", detail_show_name='" + detail_show_name + '\'' +
                ", detail_release_date='" + detail_release_date + '\'' +
                ", detail_no_of_seasons='" + detail_no_of_seasons + '\'' +
                ", detail_no_of_episodes='" + detail_no_of_episodes + '\'' +
                ", detail_episode_runtime=" + Arrays.toString(detail_episode_runtime) +
                ", detail_overview='" + detail_overview + '\'' +
                ", details_genre=" + details_genre +
                '}';
    }
}
