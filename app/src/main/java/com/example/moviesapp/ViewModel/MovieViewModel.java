package com.example.moviesapp.ViewModel;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;

import com.example.moviesapp.Models.BasicMovie;
import com.example.moviesapp.Models.Cast;
import com.example.moviesapp.Models.DetailMovie;
import com.example.moviesapp.Models.Genres;
import com.example.moviesapp.Models.Video;
import com.example.moviesapp.Repository.Repository;

import java.util.List;

public class MovieViewModel extends ViewModel {

    private Repository repository;

    public MovieViewModel() {
        repository = Repository.getInstance();
    }

    /*** Get Detail Movie Data   *****/

    public LiveData<String > getDetailMovieData() {
        return repository.getDetailMovieData();
    }

    public LiveData<String> getDetailMovieImageUrl() {
        return repository.getDetailMovieImageUrl();
    }

    public LiveData<String> getDetailMovieOverview() {
        return repository.getDetailMovieOverview();
    }

    public LiveData<String> getDetailMovieRelease() {
        return repository.getDetailMovieRelease();
    }

    public LiveData<String> getDetailMovieRuntime() {
        return repository.getDetailMovieRuntime();
    }

    public LiveData<List<Video>> getDetailMovieVideo() {
        return repository.getDetailMovieVideo();
    }

    public LiveData<List<Genres>> getDetailMovieGenres() {
        return repository.getDetailMovieGenres();
    }

    /*** Search Movie Data By ID   *****/
    public void searchMovieByID(int ID){
        repository.searchMovieByID(ID);
    }


    /*** Get Detail Movie Cast Data   *****/
    public LiveData<List<Cast>> getListOfCastForMovie() {
        return repository.getListOfCastForMovie();
    }


    /*** Search Movie Data By ID   *****/
    public void searchMovieCastByID(int ID){
        repository.searchMovieCastByID(ID);
    }

    public void searchMovieVideosByID(int id){
        repository.searchMovieVideosByID(id);
    }

    /******************************************************************************************/
    /*************************    Similar Movies for movie      ***********************/
    /******************************************************************************************/

    public LiveData<List<BasicMovie>> getSimilarMovies() {
        return repository.getSimilarMovies();
    }

    public void searchSimilarMoviesForMovie(int id, int page){
        repository.searchSimilarMoviesForMovie(id, page);
    }


    /******************************************************************************************/
    /*************************    Similar TVShow for tvshow      ***********************/
    /******************************************************************************************/



}
