package com.example.moviesapp.Models;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.Arrays;
import java.util.List;

public class DetailTVShow implements Parcelable {

    private String detail_show_id;
    private String detail_image_url;
    private String detail_show_name;
    private String detail_release_date;
    private String detail_no_of_seasons;
    private String detail_no_of_episodes;
    private String[] detail_episode_runtime;
    private String detail_overview;
    private List<Genres> details_genre;


    public DetailTVShow(String detail_show_id, String detail_image_url, String detail_show_name, String detail_release_date, String detail_no_of_seasons, String detail_no_of_episodes, String[] detail_episode_runtime, String detail_overview, List<Genres> details_genre) {
        this.detail_show_id = detail_show_id;
        this.detail_image_url = detail_image_url;
        this.detail_show_name = detail_show_name;
        this.detail_release_date = detail_release_date;
        this.detail_no_of_seasons = detail_no_of_seasons;
        this.detail_no_of_episodes = detail_no_of_episodes;
        this.detail_episode_runtime = detail_episode_runtime;
        this.detail_overview = detail_overview;
        this.details_genre = details_genre;
    }

    public DetailTVShow() {
    }

    protected DetailTVShow(Parcel in) {
        detail_show_id = in.readString();
        detail_image_url = in.readString();
        detail_show_name = in.readString();
        detail_release_date = in.readString();
        detail_no_of_seasons = in.readString();
        detail_no_of_episodes = in.readString();
        detail_episode_runtime = in.createStringArray();
        detail_overview = in.readString();
    }

    public static final Creator<DetailTVShow> CREATOR = new Creator<DetailTVShow>() {
        @Override
        public DetailTVShow createFromParcel(Parcel in) {
            return new DetailTVShow(in);
        }

        @Override
        public DetailTVShow[] newArray(int size) {
            return new DetailTVShow[size];
        }
    };

    public String getDetail_show_id() {
        return detail_show_id;
    }

    public void setDetail_show_id(String detail_show_id) {
        this.detail_show_id = detail_show_id;
    }

    public String getDetail_image_url() {
        return detail_image_url;
    }

    public void setDetail_image_url(String detail_image_url) {
        this.detail_image_url = detail_image_url;
    }

    public String getDetail_show_name() {
        return detail_show_name;
    }

    public void setDetail_show_name(String detail_show_name) {
        this.detail_show_name = detail_show_name;
    }

    public String getDetail_release_date() {
        return detail_release_date;
    }

    public void setDetail_release_date(String detail_release_date) {
        this.detail_release_date = detail_release_date;
    }

    public String getDetail_no_of_seasons() {
        return detail_no_of_seasons;
    }

    public void setDetail_no_of_seasons(String detail_no_of_seasons) {
        this.detail_no_of_seasons = detail_no_of_seasons;
    }

    public String getDetail_no_of_episodes() {
        return detail_no_of_episodes;
    }

    public void setDetail_no_of_episodes(String detail_no_of_episodes) {
        this.detail_no_of_episodes = detail_no_of_episodes;
    }

    public String[] getDetail_episode_runtime() {
        return detail_episode_runtime;
    }

    public void setDetail_episode_runtime(String[] detail_episode_runtime) {
        this.detail_episode_runtime = detail_episode_runtime;
    }

    public String getDetail_overview() {
        return detail_overview;
    }

    public void setDetail_overview(String detail_overview) {
        this.detail_overview = detail_overview;
    }

    public List<Genres> getDetails_genre() {
        return details_genre;
    }

    public void setDetails_genre(List<Genres> details_genre) {
        this.details_genre = details_genre;
    }

    @Override
    public String toString() {
        return "DetailTVShow{" +
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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(detail_show_id);
        dest.writeString(detail_image_url);
        dest.writeString(detail_show_name);
        dest.writeString(detail_release_date);
        dest.writeString(detail_no_of_seasons);
        dest.writeString(detail_no_of_episodes);
        dest.writeStringArray(detail_episode_runtime);
        dest.writeString(detail_overview);
    }
}
