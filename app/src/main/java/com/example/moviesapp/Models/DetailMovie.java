package com.example.moviesapp.Models;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class DetailMovie implements Parcelable  {

    private String movie_id;
    private String movie_image_url;
    private String movie_title;
    private String movie_runtime;
    private String movie_release_date;
    private String movie_overview;
    private List<Genres> movie_genre;
    @SerializedName("results")
    private List<Video> movie_video;

    public DetailMovie() {
    }

    public DetailMovie(String movie_id, String movie_image_url, String movie_title,
                       String movie_runtime, String movie_release_date, String movie_overview, List<Genres> movie_genre, List<Video> movie_video) {
        this.movie_id = movie_id;
        this.movie_image_url = movie_image_url;
        this.movie_title = movie_title;
        this.movie_runtime = movie_runtime;
        this.movie_release_date = movie_release_date;
        this.movie_overview = movie_overview;
        this.movie_genre = movie_genre;
        this.movie_video = movie_video;
    }

    protected DetailMovie(Parcel in) {
        movie_id = in.readString();
        movie_image_url = in.readString();
        movie_title = in.readString();
        movie_runtime = in.readString();
        movie_release_date = in.readString();
        movie_overview = in.readString();
    }

    public static final Creator<DetailMovie> CREATOR = new Creator<DetailMovie>() {
        @Override
        public DetailMovie createFromParcel(Parcel in) {
            return new DetailMovie(in);
        }

        @Override
        public DetailMovie[] newArray(int size) {
            return new DetailMovie[size];
        }
    };

    public String getMovie_id() {
        return movie_id;
    }

    public void setMovie_id(String movie_id) {
        this.movie_id = movie_id;
    }

    public String getMovie_image_url() {
        return movie_image_url;
    }

    public void setMovie_image_url(String movie_image_url) {
        this.movie_image_url = movie_image_url;
    }

    public String getMovie_title() {
        return movie_title;
    }

    public void setMovie_title(String movie_title) {
        this.movie_title = movie_title;
    }

    public String getMovie_runtime() {
        return movie_runtime;
    }

    public void setMovie_runtime(String movie_runtime) {
        this.movie_runtime = movie_runtime;
    }

    public String getMovie_release_date() {
        return movie_release_date;
    }

    public void setMovie_release_date(String movie_release_date) {
        this.movie_release_date = movie_release_date;
    }

    public String getMovie_overview() {
        return movie_overview;
    }

    public void setMovie_overview(String movie_overview) {
        this.movie_overview = movie_overview;
    }

    public List<Genres> getMovie_genre() {
        return movie_genre;
    }

    public void setMovie_genre(List<Genres> movie_genre) {
        this.movie_genre = movie_genre;
    }

    public List<Video> getMovie_video() {
        return movie_video;
    }

    public void setMovie_video(List<Video> movie_video) {
        this.movie_video = movie_video;
    }

    @Override
    public String toString() {
        return "DetailMovie{" +
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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(movie_id);
        dest.writeString(movie_image_url);
        dest.writeString(movie_title);
        dest.writeString(movie_runtime);
        dest.writeString(movie_release_date);
        dest.writeString(movie_overview);
    }
}
