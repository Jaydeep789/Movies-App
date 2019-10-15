package com.example.moviesapp.ViewModel;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;

import com.example.moviesapp.Models.BasicTVShow;
import com.example.moviesapp.Models.Cast;
import com.example.moviesapp.Models.DetailTVShow;
import com.example.moviesapp.Models.Genres;
import com.example.moviesapp.Repository.Repository;

import java.util.List;

public class TVShowViewModel extends ViewModel {

    private Repository repository;

    public TVShowViewModel() {
        repository = Repository.getInstance();
    }

    public LiveData<String> getTVShowDetails() {
        return repository.getTVShowDetails();
    }

    public LiveData<String[]> getTVShowDetailRuntime() {
        return repository.getTVShowDetailRuntime();
    }

    public LiveData<String> getTVShowDetailImage() {
        return repository.getTVShowDetailImage();
    }

    public LiveData<String> getTVShowDetailEpisodes() {
        return repository.getTVShowDetailEpisodes();
    }


    public LiveData<String> getTVShowDetailSeason() {
        return repository.getTVShowDetailSeason();
    }

    public LiveData<String> getTVShowDetailOverview() {
        return repository.getTVShowDetailOverview();
    }

    public LiveData<String> getTVShowDetailRelease() {
        return repository.getTVShowDetailRelease();
    }

    public LiveData<List<Genres>> getTVShowDetailGenres() {
        return repository.getTVShowDetailGenres();
    }

    public void searchTVShowByID(int ID){
        repository.searchTVShowByID(ID);
    }


    public LiveData<List<Cast>> getListOfCastForTVShow() {
        return repository.getListOfCastForTVShow();
    }

    public void searchTVShowCastByID(int ID){
        repository.searchTVShowCastByID(ID);
    }


    /******************************************************************************************/
    /*************************    Similar TVShow for tvshow      ***********************/
    /******************************************************************************************/

    public LiveData<List<BasicTVShow>> getSimilarTVShows() {
        return repository.getSimilarTVShows();
    }

    public void searchTVShowForTVShow(int id, int page){
        repository.searchTVShowForTVShow(id, page);
    }
}
