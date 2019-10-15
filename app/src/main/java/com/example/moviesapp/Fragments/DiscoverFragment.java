package com.example.moviesapp.Fragments;


import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.example.moviesapp.Activities.MovieActivity;
import com.example.moviesapp.Activities.TVShowActivity;
import com.example.moviesapp.Adapters.BasicPopularMoviesAdapter;
import com.example.moviesapp.Adapters.BasicPopularTVShowsAdapter;
import com.example.moviesapp.Adapters.BasicTopRatedMoviesAdapter;
import com.example.moviesapp.Adapters.BasicTopRatedTVShowsAdapter;
import com.example.moviesapp.ViewModel.MainViewModel;
import com.example.moviesapp.Models.BasicMovie;
import com.example.moviesapp.Models.BasicTVShow;
import com.example.moviesapp.OnClickListeners.OnItemClick;
import com.example.moviesapp.R;

import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class DiscoverFragment extends Fragment implements OnItemClick {

    private RecyclerView recyclerView_top_movies, recyclerView_popular_movies, recyclerView_top_tv_shows, recyclerView_popular_tv_shows;
    private BasicPopularMoviesAdapter basicPopularMoviesAdapter;
    private BasicTopRatedMoviesAdapter basicTopRatedMoviesAdapter;
    private BasicPopularTVShowsAdapter basicPopularTVShowsAdapter;
    private BasicTopRatedTVShowsAdapter basicTopRatedTVShowsAdapter;
    private MainViewModel mainViewModel;
    private ProgressBar progressBar;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_discover, container, false);

        progressBar = view.findViewById(R.id.progress_circular);
        Toolbar toolbar = view.findViewById(R.id.toolbar);
        toolbar.setTitle("Movies");

        recyclerView_popular_movies = view.findViewById(R.id.popular_movies_recycler);
        recyclerView_top_movies = view.findViewById(R.id.top_rated_movies_recycler);
        recyclerView_popular_tv_shows = view.findViewById(R.id.popular_tv_shows_recycler);
        recyclerView_top_tv_shows = view.findViewById(R.id.top_rated_tv_shows_recycler);

        mainViewModel = ViewModelProviders.of(DiscoverFragment.this).get(MainViewModel.class);

        initRecycler();
        observer();
        priorCalls();

        return view;
    }

    private void priorCalls() {
        mainViewModel.requestPopularMovies(1);
        mainViewModel.requestTopRatedMovies(1);
        mainViewModel.requestPopularTVShows(1);
        mainViewModel.requestTopRatedTVShows(1);
        progressBar.setVisibility(View.GONE);
    }

    private void observer() {
        mainViewModel.getBasicPopularMovies().observe(DiscoverFragment.this, new Observer<List<BasicMovie>>() {
            @Override
            public void onChanged(@Nullable List<BasicMovie> basicMovies) {
                basicPopularMoviesAdapter.setPopularMovies(basicMovies);
            }
        });

        mainViewModel.getBasicTopRatedMovies().observe(DiscoverFragment.this, new Observer<List<BasicMovie>>() {
            @Override
            public void onChanged(@Nullable List<BasicMovie> basicMovies) {
                basicTopRatedMoviesAdapter.setTopRatedMovies(basicMovies);
            }
        });

        mainViewModel.getBasicPopularTVShows().observe(DiscoverFragment.this, new Observer<List<BasicTVShow>>() {
            @Override
            public void onChanged(@Nullable List<BasicTVShow> basicTVShows) {
                basicPopularTVShowsAdapter.setPopularTVShows(basicTVShows);
            }
        });

        mainViewModel.getBasicTopRatedTVShows().observe(DiscoverFragment.this, new Observer<List<BasicTVShow>>() {
            @Override
            public void onChanged(@Nullable List<BasicTVShow> basicTVShows) {
                basicTopRatedTVShowsAdapter.setTopRatedTVShows(basicTVShows);
            }
        });
    }

    private void initRecycler() {
        /*************  Basic Popular Movies ****************/
        recyclerView_popular_movies.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        recyclerView_popular_movies.setLayoutManager(linearLayoutManager);

        basicPopularMoviesAdapter = new BasicPopularMoviesAdapter(this);
        recyclerView_popular_movies.setAdapter(basicPopularMoviesAdapter);

        /*****************   Basic Top Rated Movies   ***************/

        recyclerView_top_movies.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager1 = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        recyclerView_top_movies.setLayoutManager(linearLayoutManager1);

        basicTopRatedMoviesAdapter = new BasicTopRatedMoviesAdapter(this);
        recyclerView_top_movies.setAdapter(basicTopRatedMoviesAdapter);

        /*****************   Basic Popular TV Shows    **************/

        recyclerView_popular_tv_shows.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager2 = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        recyclerView_popular_tv_shows.setLayoutManager(linearLayoutManager2);

        basicPopularTVShowsAdapter = new BasicPopularTVShowsAdapter(this);
        recyclerView_popular_tv_shows.setAdapter(basicPopularTVShowsAdapter);

        /*****************   Basic Top Rated TV Shows   **************/

        recyclerView_top_tv_shows.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager3 = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        recyclerView_top_tv_shows.setLayoutManager(linearLayoutManager3);

        basicTopRatedTVShowsAdapter = new BasicTopRatedTVShowsAdapter(this);
        recyclerView_top_tv_shows.setAdapter(basicTopRatedTVShowsAdapter);


        recyclerView_popular_movies.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                if (!recyclerView_popular_movies.canScrollHorizontally(1)) {
                    mainViewModel.requestNextPagePopularMovies();
                }
            }
        });

        recyclerView_top_movies.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                if (!recyclerView_top_movies.canScrollHorizontally(1)) {
                    mainViewModel.requestNextPageTopRatedMovies();
                }
            }
        });

        recyclerView_popular_tv_shows.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                if (!recyclerView_popular_tv_shows.canScrollHorizontally(1)) {
                    mainViewModel.requestNextPagePopularTVShows();
                }
            }
        });

        recyclerView_top_tv_shows.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                if (!recyclerView_top_tv_shows.canScrollHorizontally(1)) {
                    mainViewModel.requestNextPageTopRatedTVShows();
                }
            }
        });
    }

    @Override
    public void onPopularMovieClick(int position) {
        Intent intent = new Intent(getContext(), MovieActivity.class);
        intent.putExtra("movie", basicPopularMoviesAdapter.getSelectedMovie(position));
        getContext().startActivity(intent);
    }

    @Override
    public void onTopRatedMovieClick(int position) {
        Intent intent = new Intent(getContext(), MovieActivity.class);
        intent.putExtra("movie", basicTopRatedMoviesAdapter.getSelectedTopRatedMovie(position));
        getContext().startActivity(intent);
    }

    @Override
    public void onPopularTVShowClick(int position) {
        Intent intent = new Intent(getContext(), TVShowActivity.class);
        intent.putExtra("tvshow", basicPopularTVShowsAdapter.getSelectedPopularTVShow(position));
        getContext().startActivity(intent);
    }

    @Override
    public void onTopRatedTVShowClick(int position) {
        Intent intent = new Intent(getContext(), TVShowActivity.class);
        intent.putExtra("tvshow", basicTopRatedTVShowsAdapter.getSelectedTopRatedTVShow(position));
        getContext().startActivity(intent);
    }
}
