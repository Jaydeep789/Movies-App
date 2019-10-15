package com.example.moviesapp.Models;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class BasicTVShow implements Parcelable {

    @SerializedName("id")
    private String show_id;

    @SerializedName("poster_path")
    private String image_url;

    @SerializedName("original_name")
    private String original_name;

    public BasicTVShow(String show_id, String image_url, String original_name) {
        this.show_id = show_id;
        this.image_url = image_url;
        this.original_name = original_name;
    }

    public BasicTVShow() {
    }

    protected BasicTVShow(Parcel in) {
        show_id = in.readString();
        image_url = in.readString();
        original_name = in.readString();
    }

    public static final Creator<BasicTVShow> CREATOR = new Creator<BasicTVShow>() {
        @Override
        public BasicTVShow createFromParcel(Parcel in) {
            return new BasicTVShow(in);
        }

        @Override
        public BasicTVShow[] newArray(int size) {
            return new BasicTVShow[size];
        }
    };

    public String getShow_id() {
        return show_id;
    }

    public void setShow_id(String show_id) {
        this.show_id = show_id;
    }

    public String getImage_url() {
        return image_url;
    }

    public void setImage_url(String image_url) {
        this.image_url = image_url;
    }

    public String getOriginal_name() {
        return original_name;
    }

    public void setOriginal_name(String original_name) {
        this.original_name = original_name;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(show_id);
        dest.writeString(image_url);
        dest.writeString(original_name);
    }

    @Override
    public String toString() {
        return "BasicTVShow{" +
                "show_id='" + show_id + '\'' +
                ", image_url='" + image_url + '\'' +
                ", original_name='" + original_name + '\'' +
                '}';
    }
}
