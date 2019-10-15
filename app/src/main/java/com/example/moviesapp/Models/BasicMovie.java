package com.example.moviesapp.Models;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class BasicMovie implements Parcelable {

    @SerializedName("id")
    private String movie_id;

    @SerializedName("poster_path")
    private String image_url;

    @SerializedName("title")
    private String movie_name;

    public BasicMovie(String movie_id, String image_url, String movie_name) {
        this.movie_id = movie_id;
        this.image_url = image_url;
        this.movie_name = movie_name;
    }

    public BasicMovie() {
    }

    protected BasicMovie(Parcel in) {
        movie_id = in.readString();
        image_url = in.readString();
        movie_name = in.readString();
    }

    public static final Creator<BasicMovie> CREATOR = new Creator<BasicMovie>() {
        @Override
        public BasicMovie createFromParcel(Parcel in) {
            return new BasicMovie(in);
        }

        @Override
        public BasicMovie[] newArray(int size) {
            return new BasicMovie[size];
        }
    };

    public String getMovie_id() {
        return movie_id;
    }

    public void setMovie_id(String movie_id) {
        this.movie_id = movie_id;
    }

    public String getImage_url() {
        return image_url;
    }

    public void setImage_url(String image_url) {
        this.image_url = image_url;
    }

    public String getMovie_name() {
        return movie_name;
    }

    public void setMovie_name(String movie_name) {
        this.movie_name = movie_name;
    }

    @Override
    public String toString() {
        return "BasicMovie{" +
                "movie_id='" + movie_id + '\'' +
                ", image_url='" + image_url + '\'' +
                ", movie_name='" + movie_name + '\'' +
                '}';
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(movie_id);
        dest.writeString(image_url);
        dest.writeString(movie_name);
    }
}
