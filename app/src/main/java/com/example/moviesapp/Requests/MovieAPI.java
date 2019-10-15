package com.example.moviesapp.Requests;

import com.example.moviesapp.Models.BasicMovie;
import com.example.moviesapp.Models.BasicTVShow;
import com.example.moviesapp.Requests.Responses.BasicMovieResponse;
import com.example.moviesapp.Requests.Responses.BasicTVShowResponse;
import com.example.moviesapp.Requests.Responses.CastResponse;
import com.example.moviesapp.Requests.Responses.DetailMovieResponse;
import com.example.moviesapp.Requests.Responses.DetailTVShowResponse;
import com.example.moviesapp.Requests.Responses.VideoResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface MovieAPI {

    /*******************************************************************/
    /***                    MOVIE RESPONSES                         ****/
    /*******************************************************************/
    @GET("movie/popular")
    Call<BasicMovieResponse> getPopularMovieBasicInfo(
            @Query("api_key") String key,
            @Query("language") String language,
            @Query("page") String page
    );

    @GET("movie/top_rated")
    Call<BasicMovieResponse> getTopRatedMovieBasicInfo(
            @Query("api_key") String key,
            @Query("language") String language,
            @Query("page") String page
    );

    @GET("movie/{movie_id}")
    Call<DetailMovieResponse> getDetailMovieInfoByID(
            @Path("movie_id") int id,
            @Query("api_key") String key,
            @Query("language") String language
    );


    /******************************************************************/
    /******                TV SHOW RESPONSES                     ******/
    /******************************************************************/

    @GET("tv/popular")
    Call<BasicTVShowResponse> getPopularTVShowBasicInfo(
            @Query("api_key") String key,
            @Query("language") String language,
            @Query("page") String page
    );

    @GET("tv/top_rated")
    Call<BasicTVShowResponse> getTopRatedTVShowBasicInfo(
            @Query("api_key") String key,
            @Query("language") String language,
            @Query("page") String page
    );

    @GET("tv/{tv_id}")
    Call<DetailTVShowResponse> getDetailTVShowInfoByID(
            @Path("tv_id") int id,
            @Query("api_key") String key,
            @Query("language") String language
    );

    /**********************************************************************************/
    /******                  MOVIE RESPONSES FOR MOVIE FRAGMENT                  ******/
    /**********************************************************************************/

    @GET("movie/now_playing")
    Call<BasicMovieResponse> getMoviesForMovieFragment(
            @Query("api_key") String key,
            @Query("language") String language,
            @Query("page") String page
    );

    @GET("search/movie")
    Call<BasicMovieResponse> getSearchResponseForMovieFragment(
            @Query("api_key") String key,
            @Query("language") String language,
            @Query("page") String page,
            @Query("query") String query
    );

    /**********************************************************************************/
    /******                  TVSHOW RESPONSES FOR TV SHOW FRAGMENT               ******/
    /**********************************************************************************/

    @GET("tv/on_the_air")
    Call<BasicTVShowResponse> getTVShowsForTVShowFragment(
            @Query("api_key") String key,
            @Query("language") String language,
            @Query("page") String page
    );

    @GET("search/tv")
    Call<BasicTVShowResponse> getSearchResponseForTVShowFragment(
            @Query("api_key") String key,
            @Query("language") String language,
            @Query("page") String page,
            @Query("query") String query
    );

    /**********************************************************************************/
    /******                            CAST RESPONSE                             ******/
    /**********************************************************************************/

    @GET("movie/{movie_id}/credits")
    Call<CastResponse> getCastForMovieByID(
            @Path("movie_id") int id,
            @Query("api_key") String key
    );

    @GET("tv/{tv_id}/credits")
    Call<CastResponse> getCastForTVShowByID(
            @Path("tv_id") int id,
            @Query("api_key") String key
    );

    /**********************************************************************************/
    /******                            VIDEOS RESPONSE                             ******/
    /**********************************************************************************/


    @GET("movie/{movie_id}/videos")
    Call<VideoResponse> getMovieVideo(
            @Path("movie_id") int id,
            @Query("api_key") String key,
            @Query("language") String language
    );


    /**********************************************************************************/
    /******                     MOVIE RECOMMENDATION RESPONSE                    ******/
    /**********************************************************************************/

    @GET("movie/{movie_id}/similar")
    Call<BasicMovieResponse> getSimilarMovies(
            @Path("movie_id") int id,
            @Query("api_key") String key,
            @Query("language") String language,
            @Query("page") String page
    );

    /**********************************************************************************/
    /******                     MOVIE RECOMMENDATION RESPONSE                    ******/
    /**********************************************************************************/

    @GET("tv/{tv_id}/similar")
    Call<BasicTVShowResponse> getSimilarTVShow(
            @Path("tv_id") int id,
            @Query("api_key") String key,
            @Query("language") String language,
            @Query("page") String page
    );

}
