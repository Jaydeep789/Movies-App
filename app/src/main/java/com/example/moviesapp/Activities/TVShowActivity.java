package com.example.moviesapp.Activities;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.moviesapp.Adapters.TVShowActivityAdapter;
import com.example.moviesapp.Models.BasicTVShow;
import com.example.moviesapp.Models.Cast;
import com.example.moviesapp.Models.DetailTVShow;
import com.example.moviesapp.Models.Genres;
import com.example.moviesapp.OnClickListeners.OnTVShowItemClick;
import com.example.moviesapp.R;
import com.example.moviesapp.Utils.Constants;
import com.example.moviesapp.ViewModel.TVShowViewModel;

import java.util.List;

public class TVShowActivity extends AppCompatActivity implements OnTVShowItemClick {

    private static final String TAG = "TVShowActivity";

    private TVShowViewModel tvShowViewModel;
    private ImageView tvshow_image;
    private TextView tvshow_name, tvshow_overview, tvshow_runtime, tvshow_seasons, tvshow_episodes, tvshow_genres, tvshow_cast;

    private RecyclerView recyclerView;
    private TVShowActivityAdapter tvShowActivityAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tvshow);

        tvShowViewModel = ViewModelProviders.of(this).get(TVShowViewModel.class);

        initData();
        initRecycler();
        getIntentData();
        observers();
    }

    private void getIntentData() {
        if (getIntent().hasExtra("tvshow")) {
            BasicTVShow basicTVShow = getIntent().getParcelableExtra("tvshow");
            tvShowViewModel.searchTVShowByID(Integer.parseInt(basicTVShow.getShow_id()));
            tvShowViewModel.searchTVShowCastByID(Integer.parseInt(basicTVShow.getShow_id()));
            tvShowViewModel.searchTVShowForTVShow(Integer.parseInt(basicTVShow.getShow_id()), 1);
        }
    }

    private void initData() {
        tvshow_image = findViewById(R.id.tvshow_image);
        tvshow_name = findViewById(R.id.tvshow_name);
        tvshow_overview = findViewById(R.id.tvshow_overview);
        tvshow_runtime = findViewById(R.id.tvshow_runtime);
        tvshow_seasons = findViewById(R.id.tvshow_seasons);
        tvshow_episodes = findViewById(R.id.tvshow_episodes);
        tvshow_genres = findViewById(R.id.tvshow_genres);
        tvshow_cast = findViewById(R.id.tvshow_cast);

    }

    private void initRecycler() {
        recyclerView = findViewById(R.id.recycler_similar_movies);
        recyclerView.setHasFixedSize(true);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 2);
        recyclerView.setLayoutManager(gridLayoutManager);

        tvShowActivityAdapter = new TVShowActivityAdapter(this);
        recyclerView.setAdapter(tvShowActivityAdapter);
    }

    private void observers() {
        tvShowViewModel.getTVShowDetails().observe(this, new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                setTVShowData(s);
            }
        });

        tvShowViewModel.getTVShowDetailEpisodes().observe(this, new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                setTVShowEpisodes(s);
            }
        });

        tvShowViewModel.getTVShowDetailGenres().observe(this, new Observer<List<Genres>>() {
            @Override
            public void onChanged(@Nullable List<Genres> genres) {
                if (genres != null) {
                    setTVShowGenre(genres);
                    Log.d(TAG, "onChanged: " + genres.toString());
                }

            }
        });

        tvShowViewModel.getTVShowDetailImage().observe(this, new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                setTVShowImage(s);
            }
        });

        tvShowViewModel.getTVShowDetailOverview().observe(this, new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                setTVShowOverview(s);
            }
        });

        tvShowViewModel.getTVShowDetailRuntime().observe(this, new Observer<String[]>() {
            @Override
            public void onChanged(@Nullable String[] strings) {
                setTVShowRuntime(strings);
            }
        });

        tvShowViewModel.getTVShowDetailSeason().observe(this, new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                setTVShowSeason(s);
            }
        });

        tvShowViewModel.getListOfCastForTVShow().observe(this, new Observer<List<Cast>>() {
            @Override
            public void onChanged(@Nullable List<Cast> casts) {
                if (casts != null) {
                    setCast(casts);
                    Log.d(TAG, "onChanged: " + casts.toString());
                }
            }
        });

        tvShowViewModel.getSimilarTVShows().observe(this, new Observer<List<BasicTVShow>>() {
            @Override
            public void onChanged(@Nullable List<BasicTVShow> basicTVShows) {
                tvShowActivityAdapter.setTVShows(basicTVShows);
            }
        });
    }

    private void setTVShowData(String s) {
        tvshow_name.setText(s);
    }

    private void setCast(List<Cast> castListTVShow) {
        String names = "";
        for (Cast cast : castListTVShow) {
            names += cast.getName() + ", ";
        }
        tvshow_cast.setText(names);
    }

    private void setTVShowRuntime(String[] data) {
        String time = "";
        for (String string : data) {
            time += string + " mins, ";
        }
        tvshow_runtime.setText(time);
    }

    private void setTVShowImage(String data) {
        String image = Constants.IMAGE_BASE + data;
        Glide.with(this).load(image).apply(RequestOptions.placeholderOf(R.drawable.studio_icon_stable)).into(tvshow_image);
    }

    private void setTVShowEpisodes(String data) {
        tvshow_episodes.setText(data);
    }

    private void setTVShowSeason(String data) {
        tvshow_seasons.setText(data);
    }

    private void setTVShowOverview(String data) {
        tvshow_overview.setText(data);
    }

    private void setTVShowGenre(List<Genres> genresList) {
        String names = "";
        for (Genres genres : genresList) {
            names += genres.getName() + ", ";
        }
        tvshow_genres.setText(names);
    }

    @Override
    public void OnTVShowFragmentClick(int position) {
        Intent intent = new Intent(this, TVShowActivity.class);
        intent.putExtra("tvshow", tvShowActivityAdapter.getSelectedTVShow(position));
        startActivity(intent);
    }
}
