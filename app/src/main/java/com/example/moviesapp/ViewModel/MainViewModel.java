package com.example.moviesapp.ViewModel;

import android.arch.lifecycle.LiveData;

import com.example.moviesapp.Models.BasicMovie;
import com.example.moviesapp.Models.BasicTVShow;
import com.example.moviesapp.Repository.Repository;

import java.util.List;

public class MainViewModel extends android.arch.lifecycle.ViewModel {

    private Repository repository;

    public MainViewModel() {
        repository = Repository.getInstance();
    }

    /******************************************************************************************/
    /*************************    Discover Fragment Methods     *******************************/
    /******************************************************************************************/

    public LiveData<List<BasicMovie>> getBasicPopularMovies(){
        return repository.getPopularMovies();
    }

    public LiveData<List<BasicMovie>> getBasicTopRatedMovies(){
        return repository.getTopRatedMovies();
    }

    public LiveData<List<BasicTVShow>> getBasicPopularTVShows(){
        return repository.getPopularTVShows();
    }

    public LiveData<List<BasicTVShow>> getBasicTopRatedTVShows(){
        return repository.getTopRatedTVShows();
    }

    public void requestPopularMovies(int page){
        repository.requestPopularMovies(page);
    }

    public void requestNextPagePopularMovies(){
        repository.requestNextPagePopularMovies();
    }

    public void requestTopRatedMovies(int page){
        repository.requestTopRatedMovies(page);
    }

    public void requestNextPageTopRatedMovies(){
        repository.requestNextPageTopRatedMovies();
    }

    public void requestPopularTVShows(int page){
        repository.requestPopularTVShows(page);
    }

    public void requestNextPagePopularTVShows(){
        repository.requestNextPagePopularTVShows();
    }

    public void requestTopRatedTVShows(int page){
        repository.requestTopRatedTVShows(page);
    }

    public void requestNextPageTopRatedTVShows(){
        repository.requestNextPageTopRatedTVShows();
    }


    /******************************************************************************************/
    /*************************      Movies Fragment Methods     *******************************/
    /******************************************************************************************/

    public LiveData<List<BasicMovie>> getMoviesForMovieFragment(){
        return repository.getMoviesForMovieFragment();
    }

    public void requestMovieForMovieFragment(int page){
        repository.requestMovieForMovieFragment(page);
    }

    public void requestNextPageMoviesForMovieFragment(){
        repository.requestNextPageMoviesForMovieFragment();
    }

    public void searchForMovie(int page, String query){
        repository.searchForMovie(page,query);
    }

    public void requestForNextPageOfSearchForMovie(){
        repository.requestForNextPageOfSearchForMovie();
    }


    /******************************************************************************************/
    /*************************    TVShows Fragment Methods      *******************************/
    /******************************************************************************************/

    public LiveData<List<BasicTVShow>> getTVShowsForTVShowFragment(){
        return repository.getTVShowsForTVShowFragment();
    }

    public void requestTVShowsForTVShowFragment(int page){
        repository.requestTVShowsForTVShowFragment(page);
    }

    public void requestNextPageForTVShowsFragment(){
        repository.requestNextPageForTVShowsFragment();
    }

    public void searchForTVShow(int page, String query){
        repository.searchForTVShow(page,query);
    }

    public void requestForNextPageOfSearchForTVShow(){
        repository.requestForNextPageOfSearchForTVShow();
    }
}
