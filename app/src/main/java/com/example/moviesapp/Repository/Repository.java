package com.example.moviesapp.Repository;

import android.arch.lifecycle.LiveData;

import com.example.moviesapp.APIClient;
import com.example.moviesapp.Models.BasicMovie;
import com.example.moviesapp.Models.BasicTVShow;
import com.example.moviesapp.Models.Cast;
import com.example.moviesapp.Models.DetailMovie;
import com.example.moviesapp.Models.DetailTVShow;
import com.example.moviesapp.Models.Genres;
import com.example.moviesapp.Models.Video;

import java.util.List;

public class Repository {

    private static Repository instance;
    private APIClient apiClient;
    private int mPagePopularMovies, mPagePopularTVShows, mPageTopRatedMovies, mPageTopRatedTVShows, mPageForMovieFragment, mPageForTVShowFragment;
    private String searchMovieName, searchTVShowName;
    private int searchMoviePage, searchTVShowPage;


    public static Repository getInstance() {
        if (instance == null) {
            instance = new Repository();
        }
        return instance;
    }

    public Repository() {
        apiClient = APIClient.getInstance();
    }

    /******************************************************************************************/
    /*************************    Discover Fragment Methods     *******************************/
    /******************************************************************************************/

    public LiveData<List<BasicMovie>> getPopularMovies() {
        return apiClient.getBasicPopularMovieInfo();
    }

    public LiveData<List<BasicMovie>> getTopRatedMovies() {
        return apiClient.getBasicTopRatedMovieInfo();
    }

    public LiveData<List<BasicTVShow>> getPopularTVShows() {
        return apiClient.getPopularTVShows();
    }

    public LiveData<List<BasicTVShow>> getTopRatedTVShows() {
        return apiClient.getTopRatedTVShows();
    }

    public void requestPopularMovies(int page) {
        mPagePopularMovies = page;
        apiClient.searchPopularMovies(page);
    }

    public void requestNextPagePopularMovies() {
        requestPopularMovies(mPagePopularMovies + 1);
    }

    public void requestTopRatedMovies(int page) {
        mPageTopRatedMovies = page;
        apiClient.searchTopRatedMovies(page);
    }

    public void requestNextPageTopRatedMovies() {
        requestTopRatedMovies(mPageTopRatedMovies + 1);
    }

    public void requestPopularTVShows(int page) {
        mPagePopularTVShows = page;
        apiClient.searchPopularTVShows(page);
    }

    public void requestNextPagePopularTVShows() {
        requestPopularTVShows(mPagePopularTVShows + 1);
    }

    public void requestTopRatedTVShows(int page) {
        mPageTopRatedTVShows = page;
        apiClient.searchTopRatedTVShows(page);
    }

    public void requestNextPageTopRatedTVShows() {
        requestTopRatedTVShows(mPageTopRatedTVShows + 1);
    }


    /******************************************************************************************/
    /*************************    Movies Fragment Methods       *******************************/
    /******************************************************************************************/

    public LiveData<List<BasicMovie>> getMoviesForMovieFragment() {
        return apiClient.moviesForMovieFragment();
    }

    public void requestMovieForMovieFragment(int page) {
        mPageForMovieFragment = page;
        apiClient.searchMoviesForMovieFragment(page);
    }

    public void requestNextPageMoviesForMovieFragment() {
        requestMovieForMovieFragment(mPageForMovieFragment + 1);
    }

    public void searchForMovie(int page, String query) {
        searchMoviePage = page;
        searchMovieName = query;
        apiClient.searchForMovie(page, query);
    }

    public void requestForNextPageOfSearchForMovie() {
        searchForMovie(searchMoviePage + 1, searchMovieName);
    }

    /******************************************************************************************/
    /*************************    TVShows Fragment Methods      *******************************/
    /******************************************************************************************/

    public LiveData<List<BasicTVShow>> getTVShowsForTVShowFragment() {
        return apiClient.tvshowsForTVShowFragment();
    }

    public void requestTVShowsForTVShowFragment(int page) {
        mPageForTVShowFragment = page;
        apiClient.searchTVShowsForTVShowFragment(page);
    }

    public void requestNextPageForTVShowsFragment() {
        requestTVShowsForTVShowFragment(mPageForTVShowFragment + 1);
    }

    public void searchForTVShow(int page, String query) {
        searchTVShowPage = page;
        searchTVShowName = query;
        apiClient.searchTVShowResponse(page, query);
    }

    public void requestForNextPageOfSearchForTVShow() {
        searchForTVShow(searchTVShowPage + 1, searchTVShowName);
    }


    /******************************************************************************************/
    /*************************    Movie Detail Activity Methods      **************************/
    /******************************************************************************************/


    public LiveData<String> getDetailMovieData() {
        return apiClient.getDetailMovieTitle();
    }

    public void searchMovieByID(int ID) {
        apiClient.searchMovieByID(ID);
    }

    public LiveData<String> getDetailMovieImageUrl() {
        return apiClient.getDetailMovieImageUrl();
    }

    public LiveData<String> getDetailMovieOverview() {
        return apiClient.getDetailMovieOverview();
    }

    public LiveData<String> getDetailMovieRelease() {
        return apiClient.getDetailMovieRelease();
    }

    public LiveData<String> getDetailMovieRuntime() {
        return apiClient.getDetailMovieRuntime();
    }

    public LiveData<List<Video>> getDetailMovieVideo() {
        return apiClient.getMovieVideo();
    }

    public void searchMovieVideosByID(int id){
        apiClient.searchForMovieVideos(id);
    }

    public LiveData<List<Genres>> getDetailMovieGenres() {
        return apiClient.getDetailMovieGenres();
    }


    /******************************************************************************************/
    /*************************    TVShows Details Activity Methods      ***********************/
    /******************************************************************************************/

    public LiveData<String > getTVShowDetails() {
        return apiClient.getTVShowDetailName();
    }

    public void searchTVShowByID(int ID) {
        apiClient.searchTVShowByID(ID);
    }

    public LiveData<String[]> getTVShowDetailRuntime() {
        return apiClient.getTVShowDetailRuntime();
    }

    public LiveData<String> getTVShowDetailImage() {
        return apiClient.getTVShowDetailImage();
    }

    public LiveData<String> getTVShowDetailEpisodes() {
        return apiClient.getTVShowDetailEpisodes();
    }


    public LiveData<String> getTVShowDetailSeason() {
        return apiClient.getTVShowDetailSeason();
    }

    public LiveData<String> getTVShowDetailOverview() {
        return apiClient.getTVShowDetailOverview();
    }

    public LiveData<String> getTVShowDetailRelease() {
        return apiClient.getTVShowDetailRelease();
    }

    public LiveData<List<Genres>> getTVShowDetailGenres() {
        return apiClient.getTVShowDetailGenres();
    }

    /******************************************************************************************/
    /*************************    Movie Cast Methods      ***********************/
    /******************************************************************************************/


    public LiveData<List<Cast>> getListOfCastForMovie() {
        return apiClient.getListOfCastForMovie();
    }

    public void searchMovieCastByID(int ID) {
        apiClient.searchCastForMovie(ID);
    }


    /******************************************************************************************/
    /*************************    TVShow Cast Methods      ***********************/
    /******************************************************************************************/


    public LiveData<List<Cast>> getListOfCastForTVShow() {
        return apiClient.getListOfCastForTVShow();
    }

    public void searchTVShowCastByID(int ID){
        apiClient.searchCastForTVShowByID(ID);
    }

    /******************************************************************************************/
    /*************************    Similar Movies for movie      ***********************/
    /******************************************************************************************/

    public LiveData<List<BasicMovie>> getSimilarMovies() {
        return apiClient.getSimilarMovies();
    }

    public void searchSimilarMoviesForMovie(int id, int page){
        apiClient.searchForSimilarMovies(page,id);
    }


    /******************************************************************************************/
    /*************************    Similar TVShow for tvshow      ***********************/
    /******************************************************************************************/

    public LiveData<List<BasicTVShow>> getSimilarTVShows() {
        return apiClient.getSimilarTVShows();
    }

    public void searchTVShowForTVShow(int id, int page){
        apiClient.searchSimilarTVShows(id, page);
    }
}
