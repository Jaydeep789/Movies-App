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
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.moviesapp.Activities.TVShowActivity;
import com.example.moviesapp.Adapters.TVShowFragmentAdapter;
import com.example.moviesapp.Models.BasicTVShow;
import com.example.moviesapp.OnClickListeners.OnTVShowItemClick;
import com.example.moviesapp.R;
import com.example.moviesapp.ViewModel.MainViewModel;

import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class TVShowsFragment extends Fragment implements OnTVShowItemClick {

    private MainViewModel mainViewModel;
    private RecyclerView recyclerView_tvshows;
    private TVShowFragmentAdapter tvShowFragmentAdapter;
    private SearchView searchView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_tvshows, container, false);
        mainViewModel = ViewModelProviders.of(TVShowsFragment.this).get(MainViewModel.class);
        recyclerView_tvshows = view.findViewById(R.id.recycler);
        searchView = view.findViewById(R.id.searchViewTVShow);

        priorCalls();
        initRecyclerView();
        observers();
        initSearchView();

        return view;
    }

    private void priorCalls() {
        mainViewModel.requestTVShowsForTVShowFragment(1);
    }

    private void initSearchView() {
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                mainViewModel.searchForTVShow(1, s);
                searchView.clearFocus();
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                return false;
            }
        });
    }

    private void initRecyclerView() {
        recyclerView_tvshows.setHasFixedSize(true);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(), 2);
        recyclerView_tvshows.setLayoutManager(gridLayoutManager);

        tvShowFragmentAdapter = new TVShowFragmentAdapter(this);
        recyclerView_tvshows.setAdapter(tvShowFragmentAdapter);

        recyclerView_tvshows.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                if (!recyclerView_tvshows.canScrollVertically(1)) {
                    if (!searchView.getQuery().toString().equals("")) {
                        mainViewModel.requestForNextPageOfSearchForTVShow();
                    } else {
                        mainViewModel.requestNextPageForTVShowsFragment();
                    }
                }
            }
        });
    }

    private void observers() {
        mainViewModel.getTVShowsForTVShowFragment().observe(TVShowsFragment.this, new Observer<List<BasicTVShow>>() {
            @Override
            public void onChanged(@Nullable List<BasicTVShow> basicTVShows) {
                tvShowFragmentAdapter.setTVShows(basicTVShows);
            }
        });
    }

    @Override
    public void OnTVShowFragmentClick(int position) {
        Intent intent = new Intent(getContext(), TVShowActivity.class);
        intent.putExtra("tvshow",tvShowFragmentAdapter.getSelectedTVShow(position));
        getContext().startActivity(intent);
    }
}
