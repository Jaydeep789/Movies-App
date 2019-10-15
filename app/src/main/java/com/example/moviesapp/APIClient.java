package com.example.moviesapp;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.util.Log;

import com.example.moviesapp.Models.BasicMovie;
import com.example.moviesapp.Models.BasicTVShow;
import com.example.moviesapp.Models.Cast;
import com.example.moviesapp.Models.DetailMovie;
import com.example.moviesapp.Models.DetailTVShow;
import com.example.moviesapp.Models.Genres;
import com.example.moviesapp.Models.Video;
import com.example.moviesapp.Requests.Responses.BasicMovieResponse;
import com.example.moviesapp.Requests.Responses.BasicTVShowResponse;
import com.example.moviesapp.Requests.Responses.CastResponse;
import com.example.moviesapp.Requests.Responses.DetailMovieResponse;
import com.example.moviesapp.Requests.Responses.DetailTVShowResponse;
import com.example.moviesapp.Requests.Responses.VideoResponse;
import com.example.moviesapp.Requests.ServiceGenerator;
import com.example.moviesapp.Utils.Constants;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

import retrofit2.Call;
import retrofit2.Response;

public class APIClient {

    private static APIClient instance;
    private static final String TAG = "APIClient";

    private MutableLiveData<List<BasicMovie>> basicPopularMovieInfo;
    private MutableLiveData<List<BasicMovie>> basicTopRatedMovieInfo;
    private RetrievePopularMovies retrievePopularMovies;
    private RetrieveTopRatedMovies retrieveTopRatedMovies;

    private MutableLiveData<List<BasicTVShow>> basicPopularTVShowInfo;
    private MutableLiveData<List<BasicTVShow>> basicTopRatedTVShowInfo;
    private RetrievePopularTVShows retrievePopularTVShows;
    private RetrieveTopRatedTVShows retrieveTopRatedTVShows;

    private MutableLiveData<List<BasicMovie>> listOfMoviesFromMovieFragment;
    private RetrieveMoviesForMovieFragment retrieveMoviesForMovieFragment;

    private MutableLiveData<List<BasicTVShow>> listOfTVShowsFromTVShowFragment;
    private RetrieveTVShowsForTVShowFragment retrieveTVShowsForTVShowFragment;

    private RetrieveSearchedMovie retrieveSearchedMovie;
    private RetrieveTVShow retrieveTVShow;

    private MutableLiveData<String> detailMovieData;
    private MutableLiveData<String> detailMovieImageUrl;
    private MutableLiveData<String> detailMovieOverview;
    private MutableLiveData<String> detailMovieReleaseDate;
    private MutableLiveData<String> detailMovieRuntime;
    private MutableLiveData<List<Genres>> detailMovieGenres;
    private RetrieveMovieByID retrieveMovieByID;

    private MutableLiveData<String> detailTVShowData;
    private MutableLiveData<String[]> detailTVShowRuntime;
    private MutableLiveData<String> detailTVShowImageUrl;
    private MutableLiveData<String> detailTVShowEpisodes;
    private MutableLiveData<String> detailTVShowSeason;
    private MutableLiveData<String> detailTVShowOverview;
    private MutableLiveData<String> detailTVShowRelease;
    private MutableLiveData<List<Genres>> detailTVShowGenre;
    private RetrieveTVShowByID retrieveTVShowByID;

    private MutableLiveData<List<Cast>> castListForMovie;
    private MutableLiveData<List<Cast>> castListForTVShow;
    private RetrieveCastForMovie retrieveCastForMovie;
    private RetrieveCastForTVShow retrieveCastForTVShow;

    private MutableLiveData<List<Video>> movieVideos;
    private RetrieveMovieVideos retrieveMovieVideos;

    private MutableLiveData<List<BasicMovie>> movieRecommendations;
    private MutableLiveData<List<BasicTVShow>> tvshowRecommendations;

    private RetrieveSimilarMovies retrieveSimilarMovies;
    private RetrieveSimilarTVShow retrieveSimilarTVShow;


    public static APIClient getInstance() {
        if (instance == null) {
            instance = new APIClient();
        }
        return instance;
    }

    public APIClient() {
        basicPopularMovieInfo = new MutableLiveData<>();
        basicTopRatedMovieInfo = new MutableLiveData<>();
        basicPopularTVShowInfo = new MutableLiveData<>();
        basicTopRatedTVShowInfo = new MutableLiveData<>();

        listOfMoviesFromMovieFragment = new MutableLiveData<>();
        listOfTVShowsFromTVShowFragment = new MutableLiveData<>();

        detailMovieData = new MutableLiveData<>();
        detailMovieImageUrl = new MutableLiveData<>();
        detailMovieOverview = new MutableLiveData<>();
        detailMovieReleaseDate = new MutableLiveData<>();
        detailMovieRuntime = new MutableLiveData<>();
        detailMovieGenres = new MutableLiveData<>();

        detailTVShowData = new MutableLiveData<>();
        detailTVShowEpisodes = new MutableLiveData<>();
        detailTVShowImageUrl = new MutableLiveData<>();
        detailTVShowOverview = new MutableLiveData<>();
        detailTVShowRelease = new MutableLiveData<>();
        detailTVShowRuntime = new MutableLiveData<>();
        detailTVShowSeason = new MutableLiveData<>();
        detailTVShowGenre = new MutableLiveData<>();

        castListForMovie = new MutableLiveData<>();
        castListForTVShow = new MutableLiveData<>();

        movieVideos = new MutableLiveData<>();
        movieRecommendations = new MutableLiveData<>();
        tvshowRecommendations = new MutableLiveData<>();
    }

    /******************************************************************************************/
    /*************************    Discover Fragment Methods     *******************************/
    /******************************************************************************************/

    /*******************************************************************************************************************/
    /***                                         POPULAR MOVIES RESPONSE                                            ****/
    /*******************************************************************************************************************/

    public LiveData<List<BasicMovie>> getBasicPopularMovieInfo() {
        return basicPopularMovieInfo;
    }

    public void searchPopularMovies(int page) {
        if (retrievePopularMovies != null) {
            retrievePopularMovies = null;
        }
        retrievePopularMovies = new RetrievePopularMovies(page);
        final Future handler = AppExecutors.getInstance().getExecutorService().submit(retrievePopularMovies);

        AppExecutors.getInstance().getExecutorService().schedule(new Runnable() {
            @Override
            public void run() {
                //TODO : Let user know its time-out
                handler.cancel(true);
            }
        }, Constants.TIME_OUT, TimeUnit.MILLISECONDS);
    }

    private class RetrievePopularMovies implements Runnable {

        private int page;
        private boolean isCancel;

        public RetrievePopularMovies(int page) {
            this.page = page;
            isCancel = false;
        }

        @Override
        public void run() {
            try {
                Response response = getBasicPopularMovies(page).execute();
                if (isCancel) {
                    return;
                }
                if (response.code() == 200) {
                    List<BasicMovie> list = new ArrayList<>(((BasicMovieResponse) response.body()).getBasicMovieList());
                    if (page == 1) {
                        basicPopularMovieInfo.postValue(list);
                    } else {
                        List<BasicMovie> currentMovieList = basicPopularMovieInfo.getValue();
                        currentMovieList.addAll(list);
                        basicPopularMovieInfo.postValue(currentMovieList);
                    }
                } else {
                    String error = response.errorBody().toString();
                    Log.d(TAG, "run: " + error);
                    basicPopularMovieInfo.postValue(null);
                }

            } catch (IOException e) {
                e.printStackTrace();
                basicPopularMovieInfo.postValue(null);
            }

        }

        private Call<BasicMovieResponse> getBasicPopularMovies(int page) {
            return ServiceGenerator.getMovieAPI().getPopularMovieBasicInfo(
                    Constants.API_KEY,
                    Constants.LANGUAGE,
                    String.valueOf(page)
            );
        }

        private void cancelRequestForBasicPopularMovies() {
            isCancel = true;
        }

    }

    /*******************************************************************************************************************/
    /***                                         POPULAR MOVIES RESPONSE ENDS                                       ****/
    /*******************************************************************************************************************/

    /*******************************************************************************************************************/
    /***                                         TOP RATED MOVIES RESPONSE                                          ****/
    /*******************************************************************************************************************/


    public LiveData<List<BasicMovie>> getBasicTopRatedMovieInfo() {
        return basicTopRatedMovieInfo;
    }

    public void searchTopRatedMovies(int page) {
        if (retrieveTopRatedMovies != null) {
            retrieveTopRatedMovies = null;
        }
        retrieveTopRatedMovies = new RetrieveTopRatedMovies(page);
        final Future handler = AppExecutors.getInstance().getExecutorService().submit(retrieveTopRatedMovies);

        AppExecutors.getInstance().getExecutorService().schedule(new Runnable() {
            @Override
            public void run() {
                //TODO 1: Let user know its time-out

                handler.cancel(true);
            }
        }, Constants.TIME_OUT, TimeUnit.MILLISECONDS);
    }

    private class RetrieveTopRatedMovies implements Runnable {

        private int page;
        private boolean isCancel;

        public RetrieveTopRatedMovies(int page) {
            this.page = page;
            isCancel = false;
        }

        @Override
        public void run() {
            try {
                Response response = getTopRatedMovies(page).execute();
                if (isCancel) {
                    return;
                }
                if (response.code() == 200) {
                    List<BasicMovie> list = new ArrayList<>(((BasicMovieResponse) response.body()).getBasicMovieList());
                    if (page == 1) {
                        basicTopRatedMovieInfo.postValue(list);
                    } else {
                        List<BasicMovie> currentMovieList = basicTopRatedMovieInfo.getValue();
                        currentMovieList.addAll(list);
                        basicTopRatedMovieInfo.postValue(currentMovieList);
                    }
                } else {
                    String error = response.errorBody().toString();
                    Log.d(TAG, "run: " + error);
                    basicTopRatedMovieInfo.postValue(null);
                }
            } catch (IOException e) {
                e.printStackTrace();
                basicTopRatedMovieInfo.postValue(null);
            }
        }

        private Call<BasicMovieResponse> getTopRatedMovies(int page) {
            return ServiceGenerator.getMovieAPI().getTopRatedMovieBasicInfo(
                    Constants.API_KEY,
                    Constants.LANGUAGE,
                    String.valueOf(page)
            );
        }

        private void cancelRequestForTopRatedMovies() {
            isCancel = true;
        }
    }


    /*******************************************************************************************************************/
    /***                                         TOP RATED MOVIES RESPONSE ENDS                                         ****/
    /*******************************************************************************************************************/

    /*******************************************************************************************************************/
    /***                                         POPULAR TV SHOWS RESPONSE                                          ****/
    /*******************************************************************************************************************/

    public LiveData<List<BasicTVShow>> getPopularTVShows() {
        return basicPopularTVShowInfo;
    }

    public void searchPopularTVShows(int page) {
        if (retrievePopularTVShows != null) {
            retrievePopularTVShows = null;
        }
        retrievePopularTVShows = new RetrievePopularTVShows(page);
        final Future handler = AppExecutors.getInstance().getExecutorService().submit(retrievePopularTVShows);

        AppExecutors.getInstance().getExecutorService().schedule(new Runnable() {
            @Override
            public void run() {
                //TODO 2: Let user know its time-out
                handler.cancel(true);
            }
        }, Constants.TIME_OUT, TimeUnit.MILLISECONDS);
    }

    private class RetrievePopularTVShows implements Runnable {

        private int page;
        private boolean isCancel;

        public RetrievePopularTVShows(int page) {
            this.page = page;
            isCancel = false;
        }

        @Override
        public void run() {
            try {
                Response response = getPopularTVShows(page).execute();
                if (isCancel) {
                    return;
                }
                if (response.code() == 200) {
                    List<BasicTVShow> list = new ArrayList<>(((BasicTVShowResponse) response.body()).getBasicTVShowList());
                    if (page == 1) {
                        basicPopularTVShowInfo.postValue(list);
                    } else {
                        List<BasicTVShow> currentTVShowList = basicPopularTVShowInfo.getValue();
                        currentTVShowList.addAll(list);
                        basicPopularTVShowInfo.postValue(currentTVShowList);
                    }
                } else {
                    String error = response.errorBody().toString();
                    basicPopularTVShowInfo.postValue(null);
                }
            } catch (IOException e) {
                e.printStackTrace();
                basicPopularTVShowInfo.postValue(null);
            }

        }

        private Call<BasicTVShowResponse> getPopularTVShows(int page) {
            return ServiceGenerator.getMovieAPI().getPopularTVShowBasicInfo(
                    Constants.API_KEY,
                    Constants.LANGUAGE,
                    String.valueOf(page)
            );
        }

        private void cancelRequest() {
            isCancel = true;
        }
    }


    /*******************************************************************************************************************/
    /***                                         POPULAR TV SHOWS RESPONSE ENDS                                     ****/
    /*******************************************************************************************************************/


    /*******************************************************************************************************************/
    /***                                         TOP RATED TV SHOWS RESPONSE                                        ****/
    /*******************************************************************************************************************/

    public LiveData<List<BasicTVShow>> getTopRatedTVShows() {
        return basicTopRatedTVShowInfo;
    }

    public void searchTopRatedTVShows(int page) {
        if (retrieveTopRatedTVShows != null) {
            retrieveTopRatedTVShows = null;
        }
        retrieveTopRatedTVShows = new RetrieveTopRatedTVShows(page);
        final Future handler = AppExecutors.getInstance().getExecutorService().submit(retrieveTopRatedTVShows);

        AppExecutors.getInstance().getExecutorService().schedule(new Runnable() {
            @Override
            public void run() {
                //TODO 3: Let user know its time-out
                handler.cancel(true);
            }
        }, Constants.TIME_OUT, TimeUnit.MILLISECONDS);
    }

    private class RetrieveTopRatedTVShows implements Runnable {

        private int page;
        private boolean isCancel;

        public RetrieveTopRatedTVShows(int page) {
            this.page = page;
            isCancel = false;
        }

        @Override
        public void run() {
            try {
                Response response = getTopRatedTVShows(page).execute();
                if (isCancel) {
                    return;
                }
                if (response.code() == 200) {
                    List<BasicTVShow> list = new ArrayList<>(((BasicTVShowResponse) response.body()).getBasicTVShowList());
                    if (page == 1) {
                        basicTopRatedTVShowInfo.postValue(list);
                    } else {
                        List<BasicTVShow> currentTVShows = basicTopRatedTVShowInfo.getValue();
                        currentTVShows.addAll(list);
                        basicTopRatedTVShowInfo.postValue(currentTVShows);
                    }
                } else {
                    String error = response.errorBody().toString();
                    basicTopRatedTVShowInfo.postValue(null);
                }
            } catch (IOException e) {
                e.printStackTrace();
                basicTopRatedTVShowInfo.postValue(null);
            }
        }

        private Call<BasicTVShowResponse> getTopRatedTVShows(int page) {
            return ServiceGenerator.getMovieAPI().getTopRatedTVShowBasicInfo(
                    Constants.API_KEY,
                    Constants.LANGUAGE,
                    String.valueOf(page)
            );
        }

        private void cancelRequestForTopRatedTVShows() {
            isCancel = true;
        }
    }


    /*******************************************************************************************************************/
    /***                                         TOP RATED TV SHOWS RESPONSE ENDS                                   ****/
    /*******************************************************************************************************************/


    /******************************************************************************************/
    /*************************    Movies Fragment Methods       *******************************/
    /******************************************************************************************/

    public LiveData<List<BasicMovie>> moviesForMovieFragment() {
        return listOfMoviesFromMovieFragment;
    }

    public void searchMoviesForMovieFragment(int page) {
        if (retrieveMoviesForMovieFragment != null) {
            retrieveMoviesForMovieFragment = null;
        }
        retrieveMoviesForMovieFragment = new RetrieveMoviesForMovieFragment(page);

        final Future handler = AppExecutors.getInstance().getExecutorService().submit(retrieveMoviesForMovieFragment);

        AppExecutors.getInstance().getExecutorService().schedule(new Runnable() {
            @Override
            public void run() {
                //TODO 4: Let user know its time-out
                handler.cancel(true);
            }
        }, Constants.TIME_OUT, TimeUnit.MILLISECONDS);
    }

    private class RetrieveMoviesForMovieFragment implements Runnable {

        private int page;
        private boolean isCancelled;

        public RetrieveMoviesForMovieFragment(int page) {
            this.page = page;
            isCancelled = false;
        }

        @Override
        public void run() {
            try {
                Response response = getMoviesForMovieFragment(page).execute();
                if (isCancelled) {
                    return;
                }
                if (response.code() == 200) {
                    List<BasicMovie> movieList = new ArrayList<>(((BasicMovieResponse) response.body()).getBasicMovieList());
                    if (page == 1) {
                        listOfMoviesFromMovieFragment.postValue(movieList);
                    } else {
                        List<BasicMovie> currentMovieList = listOfMoviesFromMovieFragment.getValue();
                        currentMovieList.addAll(movieList);
                        listOfMoviesFromMovieFragment.postValue(currentMovieList);
                    }
                } else {
                    String error = response.errorBody().toString();
                    Log.d(TAG, "run: " + error);
                    listOfMoviesFromMovieFragment.postValue(null);
                }
            } catch (IOException e) {
                e.printStackTrace();
                listOfMoviesFromMovieFragment.postValue(null);
            }
        }

        private Call<BasicMovieResponse> getMoviesForMovieFragment(int page) {
            return ServiceGenerator.getMovieAPI().getMoviesForMovieFragment(
                    Constants.API_KEY,
                    Constants.LANGUAGE,
                    String.valueOf(page)
            );
        }

        private void cancelRequestForMovieFragment() {
            isCancelled = true;
        }
    }


    /*********************************   Search Movie Response **************************************************/

    public void searchForMovie(int page, String query) {
        if (retrieveSearchedMovie != null) {
            retrieveSearchedMovie = null;
        }
        retrieveSearchedMovie = new RetrieveSearchedMovie(page, query);
        final Future handler = AppExecutors.getInstance().getExecutorService().submit(retrieveSearchedMovie);

        AppExecutors.getInstance().getExecutorService().schedule(new Runnable() {
            @Override
            public void run() {
                //TODO 5: Let user know its time-out
                handler.cancel(true);
            }
        }, Constants.TIME_OUT, TimeUnit.MILLISECONDS);
    }

    private class RetrieveSearchedMovie implements Runnable {

        private int page;
        private String query;
        private boolean isCancel;

        public RetrieveSearchedMovie(int page, String query) {
            this.page = page;
            this.query = query;
            isCancel = false;
        }

        @Override
        public void run() {
            try {
                Response response = getSearchedMovie(page, query).execute();
                if (isCancel) {
                    return;
                }
                if (response.code() == 200) {
                    List<BasicMovie> basicMovieList = new ArrayList<>(((BasicMovieResponse) response.body()).getBasicMovieList());
                    if (page == 1) {
                        listOfMoviesFromMovieFragment.postValue(basicMovieList);
                    } else {
                        List<BasicMovie> currentMovieList = listOfMoviesFromMovieFragment.getValue();
                        currentMovieList.addAll(basicMovieList);
                        listOfMoviesFromMovieFragment.postValue(currentMovieList);
                    }
                } else {
                    String error = response.errorBody().toString();
                    Log.d(TAG, "run: " + error);
                    listOfMoviesFromMovieFragment.postValue(null);
                }
            } catch (IOException e) {
                e.printStackTrace();
                listOfMoviesFromMovieFragment.postValue(null);
            }
        }

        private Call<BasicMovieResponse> getSearchedMovie(int page, String query) {
            return ServiceGenerator.getMovieAPI().getSearchResponseForMovieFragment(
                    Constants.API_KEY,
                    Constants.LANGUAGE,
                    String.valueOf(page),
                    query
            );
        }

        private void requestToCancelSearchMovie() {
            isCancel = true;
        }
    }


    /******************************************************************************************/
    /*************************    TVShows Fragment Methods      *******************************/
    /******************************************************************************************/

    public LiveData<List<BasicTVShow>> tvshowsForTVShowFragment() {
        return listOfTVShowsFromTVShowFragment;
    }

    public void searchTVShowsForTVShowFragment(int page) {
        if (retrieveTVShowsForTVShowFragment != null) {
            retrieveTVShowsForTVShowFragment = null;
        }
        retrieveTVShowsForTVShowFragment = new RetrieveTVShowsForTVShowFragment(page);
        final Future handler = AppExecutors.getInstance().getExecutorService().submit(retrieveTVShowsForTVShowFragment);

        AppExecutors.getInstance().getExecutorService().schedule(new Runnable() {
            @Override
            public void run() {
                //TODO 5: Let user know its time-out
                handler.cancel(true);
            }
        }, Constants.TIME_OUT, TimeUnit.MILLISECONDS);
    }

    private class RetrieveTVShowsForTVShowFragment implements Runnable {

        private int page;
        private boolean isCancelled;

        public RetrieveTVShowsForTVShowFragment(int page) {
            this.page = page;
            isCancelled = false;
        }

        @Override
        public void run() {
            try {
                Response response = getTVShowsForTVShowFragment(page).execute();
                if (isCancelled) {
                    return;
                }
                if (response.code() == 200) {
                    List<BasicTVShow> tvShowList = new ArrayList<>(((BasicTVShowResponse) response.body()).getBasicTVShowList());
                    if (page == 1) {
                        listOfTVShowsFromTVShowFragment.postValue(tvShowList);
                    } else {
                        List<BasicTVShow> currentTVShowList = listOfTVShowsFromTVShowFragment.getValue();
                        currentTVShowList.addAll(tvShowList);
                        listOfTVShowsFromTVShowFragment.postValue(currentTVShowList);
                    }
                } else {
                    String error = response.errorBody().toString();
                    Log.d(TAG, "run: " + error);
                    listOfTVShowsFromTVShowFragment.postValue(null);
                }
            } catch (IOException e) {
                e.printStackTrace();
                listOfTVShowsFromTVShowFragment.postValue(null);
            }

        }

        private Call<BasicTVShowResponse> getTVShowsForTVShowFragment(int page) {
            return ServiceGenerator.getMovieAPI().getTVShowsForTVShowFragment(
                    Constants.API_KEY,
                    Constants.LANGUAGE,
                    String.valueOf(page)
            );
        }

        private void requestToCancelTVShowsForTVShowFragment() {
            isCancelled = true;
        }
    }

    /*********************************   Search TV Show Response **************************************************/

    public void searchTVShowResponse(int page, String query) {
        if (retrieveTVShow != null) {
            retrieveTVShow = null;
        }
        retrieveTVShow = new RetrieveTVShow(page, query);
        final Future handler = AppExecutors.getInstance().getExecutorService().submit(retrieveTVShow);

        AppExecutors.getInstance().getExecutorService().schedule(new Runnable() {
            @Override
            public void run() {
                // TODO 6: Time out
                handler.cancel(true);
            }
        }, Constants.TIME_OUT, TimeUnit.MILLISECONDS);
    }

    private class RetrieveTVShow implements Runnable {

        private int page;
        private String query;
        private boolean isCancel;

        public RetrieveTVShow(int page, String query) {
            this.page = page;
            this.query = query;
            isCancel = false;
        }

        @Override
        public void run() {
            try {
                Response response = getTVShowResponse(page, query).execute();
                if (isCancel) {
                    return;
                }
                if (response.code() == 200) {
                    List<BasicTVShow> basicTVShowList = new ArrayList<>(((BasicTVShowResponse) response.body()).getBasicTVShowList());
                    if (page == 1) {
                        listOfTVShowsFromTVShowFragment.postValue(basicTVShowList);
                    } else {
                        List<BasicTVShow> currentTVShowList = listOfTVShowsFromTVShowFragment.getValue();
                        currentTVShowList.addAll(basicTVShowList);
                        listOfTVShowsFromTVShowFragment.postValue(currentTVShowList);
                    }
                } else {
                    String error = response.errorBody().toString();
                    Log.d(TAG, "run: " + error);
                    listOfTVShowsFromTVShowFragment.postValue(null);
                }
            } catch (IOException e) {
                e.printStackTrace();
                listOfTVShowsFromTVShowFragment.postValue(null);
            }
        }

        private Call<BasicTVShowResponse> getTVShowResponse(int page, String query) {
            return ServiceGenerator.getMovieAPI().getSearchResponseForTVShowFragment(
                    Constants.API_KEY,
                    Constants.LANGUAGE,
                    String.valueOf(page),
                    query
            );
        }

        private void requestToCancelTVShow() {
            isCancel = true;
        }
    }


    /******************************************************************************************/
    /*************************    Movie Activity Detail      *******************************/
    /******************************************************************************************/


    public LiveData<String> getDetailMovieTitle() {
        return detailMovieData;
    }

    public LiveData<String> getDetailMovieImageUrl() {
        return detailMovieImageUrl;
    }

    public LiveData<String> getDetailMovieOverview() {
        return detailMovieOverview;
    }

    public LiveData<String> getDetailMovieRelease() {
        return detailMovieReleaseDate;
    }

    public LiveData<String> getDetailMovieRuntime() {
        return detailMovieRuntime;
    }

    public LiveData<List<Genres>> getDetailMovieGenres() {
        return detailMovieGenres;
    }


    public void searchMovieByID(int id) {
        if (retrieveMovieByID != null) {
            retrieveMovieByID = null;
        }
        retrieveMovieByID = new RetrieveMovieByID(id);
        final Future handler = AppExecutors.getInstance().getExecutorService().submit(retrieveMovieByID);

        AppExecutors.getInstance().getExecutorService().schedule(new Runnable() {
            @Override
            public void run() {
                handler.cancel(true);
            }
        }, Constants.TIME_OUT, TimeUnit.MILLISECONDS);
    }

    private class RetrieveMovieByID implements Runnable {

        private int id;
        private boolean isCancel;

        public RetrieveMovieByID(int id) {
            this.id = id;
            isCancel = false;
        }

        @Override
        public void run() {
            try {
                Response response = getMovieByID(id).execute();
                if (isCancel) {
                    return;
                }
                if (response.code() == 200) {
                    String detailMovieTitle = ((DetailMovieResponse) response.body()).getMovie_title();
                    String detailMovieImage = ((DetailMovieResponse) response.body()).getMovie_image_url();
                    String detailMovieOverviewString = ((DetailMovieResponse) response.body()).getMovie_overview();
                    String detailMovieReleaseString = ((DetailMovieResponse) response.body()).getMovie_release_date();
                    String detailMovieRuntimeString = ((DetailMovieResponse) response.body()).getMovie_runtime();
                    List<Genres> detailMovieGenreList = ((DetailMovieResponse) response.body()).getMovie_genre();

                    detailMovieData.postValue(detailMovieTitle);
                    detailMovieImageUrl.postValue(detailMovieImage);
                    detailMovieOverview.postValue(detailMovieOverviewString);
                    detailMovieReleaseDate.postValue(detailMovieReleaseString);
                    detailMovieRuntime.postValue(detailMovieRuntimeString);
                    detailMovieGenres.postValue(detailMovieGenreList);
                } else {
                    String error = response.errorBody().toString();
                    Log.d(TAG, "run: " + error);
                    detailMovieData.postValue(null);
                    detailMovieImageUrl.postValue(null);
                    detailMovieOverview.postValue(null);
                    detailMovieReleaseDate.postValue(null);
                    detailMovieRuntime.postValue(null);
                    detailMovieGenres.postValue(null);
                }
            } catch (IOException e) {
                e.printStackTrace();
                detailMovieData.postValue(null);
                detailMovieImageUrl.postValue(null);
                detailMovieOverview.postValue(null);
                detailMovieReleaseDate.postValue(null);
                detailMovieRuntime.postValue(null);
                detailMovieGenres.postValue(null);
            }
        }

        private Call<DetailMovieResponse> getMovieByID(int id) {
            return ServiceGenerator.getMovieAPI().getDetailMovieInfoByID(
                    id,
                    Constants.API_KEY,
                    Constants.LANGUAGE
            );
        }

        private void cancelDetailMovieData() {
            isCancel = true;
        }
    }

    /******************************************************************************************/
    /*************************    TVShow Activity Detail      *******************************/
    /******************************************************************************************/

    public LiveData<String> getTVShowDetailName() {
        return detailTVShowData;
    }

    public LiveData<String[]> getTVShowDetailRuntime() {
        return detailTVShowRuntime;
    }

    public LiveData<String> getTVShowDetailImage() {
        return detailTVShowImageUrl;
    }

    public LiveData<String> getTVShowDetailEpisodes() {
        return detailTVShowEpisodes;
    }


    public LiveData<String> getTVShowDetailSeason() {
        return detailTVShowSeason;
    }

    public LiveData<String> getTVShowDetailOverview() {
        return detailTVShowOverview;
    }

    public LiveData<String> getTVShowDetailRelease() {
        return detailTVShowRelease;
    }

    public LiveData<List<Genres>> getTVShowDetailGenres() {
        return detailTVShowGenre;
    }

    public void searchTVShowByID(int id) {
        if (retrieveTVShowByID != null) {
            retrieveTVShowByID = null;
        }
        retrieveTVShowByID = new RetrieveTVShowByID(id);
        final Future handler = AppExecutors.getInstance().getExecutorService().submit(retrieveTVShowByID);

        AppExecutors.getInstance().getExecutorService().schedule(new Runnable() {
            @Override
            public void run() {
                handler.cancel(true);
            }
        }, Constants.TIME_OUT, TimeUnit.MILLISECONDS);
    }

    private class RetrieveTVShowByID implements Runnable {

        private int id;
        private boolean isCancel;

        public RetrieveTVShowByID(int id) {
            this.id = id;
            isCancel = false;
        }

        @Override
        public void run() {
            try {
                Response response = getTVShowByID(id).execute();
                if (isCancel) {
                    return;
                }
                if (response.code() == 200) {
                    String detailTVShowNameString = ((DetailTVShowResponse) response.body()).getDetail_show_name();
                    String[] detailTVShowRuntimeString = ((DetailTVShowResponse) response.body()).getDetail_episode_runtime();
                    String detailTVShowImageString = ((DetailTVShowResponse) response.body()).getDetail_image_url();
                    String detailTVShowEpisodesString = ((DetailTVShowResponse) response.body()).getDetail_no_of_episodes();
                    String detailTVShowSeasonString = ((DetailTVShowResponse) response.body()).getDetail_no_of_seasons();
                    String detailTVShowOverviewString = ((DetailTVShowResponse) response.body()).getDetail_overview();
                    String detailTVShowReleaseString = ((DetailTVShowResponse) response.body()).getDetail_release_date();
                    List<Genres> detailTVShowGenresString = ((DetailTVShowResponse) response.body()).getDetails_genre();

                    detailTVShowData.postValue(detailTVShowNameString);
                    detailTVShowRuntime.postValue(detailTVShowRuntimeString);
                    detailTVShowImageUrl.postValue(detailTVShowImageString);
                    detailTVShowEpisodes.postValue(detailTVShowEpisodesString);
                    detailTVShowSeason.postValue(detailTVShowSeasonString);
                    detailTVShowOverview.postValue(detailTVShowOverviewString);
                    detailTVShowRelease.postValue(detailTVShowReleaseString);
                    detailTVShowGenre.postValue(detailTVShowGenresString);
                } else {
                    String error = response.errorBody().toString();
                    Log.d(TAG, "run: " + error);
                    detailTVShowData.postValue(null);
                    detailTVShowRuntime.postValue(null);
                    detailTVShowImageUrl.postValue(null);
                    detailTVShowEpisodes.postValue(null);
                    detailTVShowSeason.postValue(null);
                    detailTVShowOverview.postValue(null);
                    detailTVShowRelease.postValue(null);
                    detailTVShowGenre.postValue(null);
                }
            } catch (IOException e) {
                e.printStackTrace();
                detailTVShowData.postValue(null);
                detailTVShowRuntime.postValue(null);
                detailTVShowImageUrl.postValue(null);
                detailTVShowEpisodes.postValue(null);
                detailTVShowSeason.postValue(null);
                detailTVShowOverview.postValue(null);
                detailTVShowRelease.postValue(null);
                detailTVShowGenre.postValue(null);
            }
        }

        private Call<DetailTVShowResponse> getTVShowByID(int id) {
            return ServiceGenerator.getMovieAPI().getDetailTVShowInfoByID(
                    id,
                    Constants.API_KEY,
                    Constants.LANGUAGE
            );
        }

        private void cancelDetailTVShowData() {
            isCancel = true;
        }
    }

    /******************************************************************************************/
    /*************************        GET CAST FOR MOVIE        *******************************/
    /******************************************************************************************/

    public LiveData<List<Cast>> getListOfCastForMovie() {
        return castListForMovie;
    }

    public void searchCastForMovie(int id) {
        if (retrieveCastForMovie != null) {
            retrieveCastForMovie = null;
        }
        retrieveCastForMovie = new RetrieveCastForMovie(id);
        final Future handler = AppExecutors.getInstance().getExecutorService().submit(retrieveCastForMovie);

        AppExecutors.getInstance().getExecutorService().schedule(new Runnable() {
            @Override
            public void run() {
                handler.cancel(true);
            }
        }, Constants.TIME_OUT, TimeUnit.MILLISECONDS);
    }

    private class RetrieveCastForMovie implements Runnable {

        private int id;
        private boolean isCancel;

        public RetrieveCastForMovie(int id) {
            this.id = id;
            isCancel = false;
        }

        @Override
        public void run() {
            try {
                Response response = getCastForMovie(id).execute();
                if (isCancel) {
                    return;
                }
                if (response.code() == 200) {
                    List<Cast> castList = new ArrayList<>(((CastResponse) response.body()).getGetListOfCast());
                    castListForMovie.postValue(castList);
                } else {
                    String error = response.errorBody().toString();
                    Log.d(TAG, "run: " + error);
                    castListForMovie.postValue(null);
                }
            } catch (IOException e) {
                e.printStackTrace();
                castListForMovie.postValue(null);
            }

        }

        private Call<CastResponse> getCastForMovie(int id) {
            return ServiceGenerator.getMovieAPI().getCastForMovieByID(
                    id,
                    Constants.API_KEY
            );
        }

        private void cancelRequestForMovieCast() {
            isCancel = true;
        }
    }


    /******************************************************************************************/
    /*************************        GET CAST FOR TV SHOW        *******************************/
    /******************************************************************************************/

    public LiveData<List<Cast>> getListOfCastForTVShow() {
        return castListForTVShow;
    }

    public void searchCastForTVShowByID(int ID) {
        if (retrieveCastForTVShow != null) {
            retrieveCastForTVShow = null;
        }
        retrieveCastForTVShow = new RetrieveCastForTVShow(ID);
        final Future handler = AppExecutors.getInstance().getExecutorService().submit(retrieveCastForTVShow);

        AppExecutors.getInstance().getExecutorService().schedule(new Runnable() {
            @Override
            public void run() {
                handler.cancel(true);
            }
        }, Constants.TIME_OUT, TimeUnit.MILLISECONDS);
    }

    private class RetrieveCastForTVShow implements Runnable {

        private int id;
        private boolean isCancel;

        public RetrieveCastForTVShow(int id) {
            this.id = id;
            isCancel = false;
        }

        @Override
        public void run() {
            try {
                Response response = getCastForTVShow(id).execute();
                if (isCancel) {
                    return;
                }
                if (response.code() == 200) {
                    List<Cast> castListTVShow = new ArrayList<>(((CastResponse) response.body()).getGetListOfCast());
                    castListForTVShow.postValue(castListTVShow);
                } else {
                    String error = response.errorBody().toString();
                    Log.d(TAG, "run: " + error);
                    castListForTVShow.postValue(null);
                }
            } catch (IOException e) {
                e.printStackTrace();
                castListForTVShow.postValue(null);
            }
        }

        private Call<CastResponse> getCastForTVShow(int id) {
            return ServiceGenerator.getMovieAPI().getCastForTVShowByID(
                    id,
                    Constants.API_KEY
            );
        }

        private void cancelRequestForTVShow() {
            isCancel = true;
        }
    }


    /******************************************************************************************/
    /*************************        GET VIDEO FOR MOVIE       *******************************/
    /******************************************************************************************/

    public LiveData<List<Video>> getMovieVideo() {
        return movieVideos;
    }

    public void searchForMovieVideos(int id) {
        if (retrieveMovieVideos != null) {
            retrieveMovieVideos = null;
        }
        retrieveMovieVideos = new RetrieveMovieVideos(id);
        Future handler = AppExecutors.getInstance().getExecutorService().submit(retrieveMovieVideos);

        AppExecutors.getInstance().getExecutorService().schedule(new Runnable() {
            @Override
            public void run() {

            }
        }, Constants.TIME_OUT, TimeUnit.MILLISECONDS);
    }

    private class RetrieveMovieVideos implements Runnable {

        private int id;
        private boolean isCancel;

        public RetrieveMovieVideos(int id) {
            this.id = id;
            isCancel = false;
        }

        @Override
        public void run() {
            try {
                Response response = getMovieVideoAPI(id).execute();
                if (isCancel) {
                    return;
                }
                if (response.code() == 200) {
                    List<Video> videoList = new ArrayList<>(((VideoResponse) response.body()).getVideos());
                    movieVideos.postValue(videoList);
                } else {
                    String error = response.errorBody().toString();
                    Log.d(TAG, "run: " + error);
                    movieVideos.postValue(null);
                }
            } catch (IOException e) {
                e.printStackTrace();
                movieVideos.postValue(null);
            }
        }

        private Call<VideoResponse> getMovieVideoAPI(int id) {
            return ServiceGenerator.getMovieAPI().getMovieVideo(
                    id,
                    Constants.API_KEY,
                    Constants.LANGUAGE
            );
        }

        private void requestToCancelMovieVideo() {
            isCancel = true;
        }
    }

    /******************************************************************************************/
    /*************************        GET SIMILAR MOVIES FOR MOVIE       *******************************/
    /******************************************************************************************/

    public LiveData<List<BasicMovie>> getSimilarMovies() {
        return movieRecommendations;
    }

    public void searchForSimilarMovies(int page, int id) {
        if (retrieveSimilarMovies != null) {
            retrieveSimilarMovies = null;
        }
        retrieveSimilarMovies = new RetrieveSimilarMovies(id, page);
        final Future handler = AppExecutors.getInstance().getExecutorService().submit(retrieveSimilarMovies);

        AppExecutors.getInstance().getExecutorService().schedule(new Runnable() {
            @Override
            public void run() {
                handler.cancel(true);
            }
        }, Constants.TIME_OUT, TimeUnit.MILLISECONDS);
    }

    private class RetrieveSimilarMovies implements Runnable {

        private int id;
        private int page;
        private boolean isCancel;

        public RetrieveSimilarMovies(int id, int page) {
            this.id = id;
            this.page = page;
            isCancel = false;
        }

        @Override
        public void run() {
            try {
                Response response = getSimilarMoviesAPI(id, page).execute();
                if (isCancel) {
                    return;
                }
                if (response.code() == 200) {
                    List<BasicMovie> movieList = new ArrayList<>(((BasicMovieResponse) response.body()).getBasicMovieList());
                    movieRecommendations.postValue(movieList);
                } else {
                    String error = response.errorBody().toString();
                    Log.d(TAG, "run: " + error);
                    movieRecommendations.postValue(null);
                }
            } catch (IOException e) {
                e.printStackTrace();
                movieRecommendations.postValue(null);
            }

        }

        private Call<BasicMovieResponse> getSimilarMoviesAPI(int id, int page) {
            return ServiceGenerator.getMovieAPI().getSimilarMovies(
                    id,
                    Constants.API_KEY,
                    Constants.LANGUAGE,
                    String.valueOf(page)
            );
        }

        private void requestToCancelSimilarMovies() {
            isCancel = true;
        }
    }


    /******************************************************************************************/
    /*************************        GET SIMILAR TV SHOWS FOR TV SHOW   **********************/
    /******************************************************************************************/

    public LiveData<List<BasicTVShow>> getSimilarTVShows() {
        return tvshowRecommendations;
    }

    public void searchSimilarTVShows(int id, int page) {
        if (retrieveSimilarTVShow != null) {
            retrieveSimilarTVShow = null;
        }
        retrieveSimilarTVShow = new RetrieveSimilarTVShow(id, page);
        Future handler = AppExecutors.getInstance().getExecutorService().submit(retrieveSimilarTVShow);

        AppExecutors.getInstance().getExecutorService().schedule(new Runnable() {
            @Override
            public void run() {

            }
        }, Constants.TIME_OUT, TimeUnit.MILLISECONDS);
    }

    private class RetrieveSimilarTVShow implements Runnable {

        private int id, page;
        private boolean isCancel;

        public RetrieveSimilarTVShow(int id, int page) {
            this.id = id;
            this.page = page;
            isCancel = false;
        }

        @Override
        public void run() {
            try {
                Response response = getSimilartTVShowsAPI(id, page).execute();
                if (isCancel) {
                    return;
                }
                if (response.code() == 200) {
                    List<BasicTVShow> basicTVShowList = new ArrayList<>(((BasicTVShowResponse) response.body()).getBasicTVShowList());
                    tvshowRecommendations.postValue(basicTVShowList);
                } else {
                    String error = response.errorBody().toString();
                    Log.d(TAG, "run: " + error);
                    tvshowRecommendations.postValue(null);
                }
            } catch (IOException e) {
                e.printStackTrace();
                tvshowRecommendations.postValue(null);
            }
        }

        private Call<BasicTVShowResponse> getSimilartTVShowsAPI(int id, int page) {
            return ServiceGenerator.getMovieAPI().getSimilarTVShow(
                    id,
                    Constants.API_KEY,
                    Constants.LANGUAGE,
                    String.valueOf(page)
            );
        }

        private void requestToCancelSimilarTVShows() {
            isCancel = true;
        }
    }

}
