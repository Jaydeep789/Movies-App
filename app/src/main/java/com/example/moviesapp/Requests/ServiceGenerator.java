package com.example.moviesapp.Requests;

import com.example.moviesapp.Utils.Constants;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ServiceGenerator {

    private static Retrofit.Builder builder = new Retrofit.Builder()
            .baseUrl(Constants.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create());

    private static Retrofit retrofit = builder.build();

    private static MovieAPI movieAPI = retrofit.create(MovieAPI.class);

    public static MovieAPI getMovieAPI() {
        return movieAPI;
    }
}
