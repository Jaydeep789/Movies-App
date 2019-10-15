package com.example.moviesapp.Fragments;


import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.moviesapp.Activities.MovieActivity;
import com.example.moviesapp.Adapters.MovieFragmentAdapter;
import com.example.moviesapp.Models.BasicMovie;
import com.example.moviesapp.OnClickListeners.OnMovieItemClick;
import com.example.moviesapp.R;
import com.example.moviesapp.ViewModel.MainViewModel;

import java.util.List;

import static android.support.constraint.Constraints.TAG;

/**
 * A simple {@link Fragment} subclass.
 */
public class MoviesFragment extends Fragment implements OnMovieItemClick {

    private MainViewModel mainViewModel1;
    private RecyclerView recyclerView_MovieFragment;
    private MovieFragmentAdapter movieFragmentAdapter;
    private SearchView searchView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_movies, container, false);

        Toolbar toolbar = view.findViewById(R.id.toolbar);
        toolbar.setTitle("Movies");

        mainViewModel1 = ViewModelProviders.of(MoviesFragment.this).get(MainViewModel.class);
        recyclerView_MovieFragment = view.findViewById(R.id.recycler_movies);
        searchView = view.findViewById(R.id.searchView);

        initRecyclerView();
        priorCalls();
        observers();
        initSearchView();

        return view;
    }

    private void priorCalls() {
        mainViewModel1.requestMovieForMovieFragment(1);
    }

    private void initRecyclerView() {
        recyclerView_MovieFragment.setHasFixedSize(true);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(), 2);
        recyclerView_MovieFragment.setLayoutManager(gridLayoutManager);

        movieFragmentAdapter = new MovieFragmentAdapter(this);
        recyclerView_MovieFragment.setAdapter(movieFragmentAdapter);

        recyclerView_MovieFragment.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                if (!recyclerView_MovieFragment.canScrollVertically(1)) {
                    if (!searchView.getQuery().toString().equals("")){
                        mainViewModel1.requestForNextPageOfSearchForMovie();
                    }else {
                        mainViewModel1.requestNextPageMoviesForMovieFragment();
                    }
                }
            }
        });
    }

    private void initSearchView(){
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                mainViewModel1.searchForMovie(1,s);
                searchView.clearFocus();
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                return false;
            }
        });
    }


    private void observers() {
        mainViewModel1.getMoviesForMovieFragment().observe(MoviesFragment.this, new Observer<List<BasicMovie>>() {
            @Override
            public void onChanged(@Nullable List<BasicMovie> basicMovies) {
                movieFragmentAdapter.setMovies(basicMovies);
            }
        });
    }

    @Override
    public void onMoviesFragmentClick(int position) {
        Intent intent = new Intent(getContext(), MovieActivity.class);
        intent.putExtra("movie",movieFragmentAdapter.getSelectedMovie(position));
        getContext().startActivity(intent);
    }

}
