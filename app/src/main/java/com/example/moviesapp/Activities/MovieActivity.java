package com.example.moviesapp.Activities;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.moviesapp.Adapters.MovieActivityAdapter;
import com.example.moviesapp.Models.BasicMovie;
import com.example.moviesapp.Models.Cast;
import com.example.moviesapp.Models.DetailMovie;
import com.example.moviesapp.Models.Genres;
import com.example.moviesapp.Models.Video;
import com.example.moviesapp.OnClickListeners.OnMovieItemClick;
import com.example.moviesapp.R;
import com.example.moviesapp.Utils.Constants;
import com.example.moviesapp.ViewModel.MovieViewModel;

import java.util.List;

public class MovieActivity extends AppCompatActivity implements OnMovieItemClick {

    private static final String TAG = "MovieActivity";
    private List<Cast> castListMovie;
    private String youtube = "";
    private RecyclerView recyclerView;
    private MovieActivityAdapter movieActivityAdapter;


    private MovieViewModel movieViewModel;
    private ImageView movie_image;
    private TextView movie_name, movie_overview, movie_runtime, movie_release, movie_trailer, movie_genre, movie_cast;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie);

        movieViewModel = ViewModelProviders.of(MovieActivity.this).get(MovieViewModel.class);

        initViews();
        initRecycler();
        getIntentData();
        observers();
    }

    private void initViews() {
        movie_image = findViewById(R.id.movie_image);
        movie_name = findViewById(R.id.movie_name);
        movie_overview = findViewById(R.id.movie_overview);
        movie_runtime = findViewById(R.id.movie_runtime);
        movie_release = findViewById(R.id.movie_release);
        movie_trailer = findViewById(R.id.movie_trailer);
        movie_genre = findViewById(R.id.movie_genres);
        movie_cast = findViewById(R.id.movie_cast);
    }

    private void initRecycler() {
        recyclerView = findViewById(R.id.recycler_recommendation_movie);
        recyclerView.setHasFixedSize(true);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 2, LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(gridLayoutManager);

        movieActivityAdapter = new MovieActivityAdapter(this);
        recyclerView.setAdapter(movieActivityAdapter);
    }

    private void getIntentData() {
        if (getIntent().hasExtra("movie")) {
            BasicMovie basicMovie = getIntent().getParcelableExtra("movie");
            movieViewModel.searchMovieByID(Integer.parseInt(basicMovie.getMovie_id()));
            movieViewModel.searchMovieCastByID(Integer.parseInt(basicMovie.getMovie_id()));
            movieViewModel.searchMovieVideosByID(Integer.parseInt(basicMovie.getMovie_id()));
            movieViewModel.searchSimilarMoviesForMovie(Integer.parseInt(basicMovie.getMovie_id()), 1);
        }
    }

    private void observers() {
        movieViewModel.getDetailMovieData().observe(this, new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                setMovieTitle(s);
            }
        });

        movieViewModel.getDetailMovieImageUrl().observe(this, new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                setMovieImage(s);
            }
        });

        movieViewModel.getDetailMovieOverview().observe(this, new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                setMovieOverview(s);
            }
        });

        movieViewModel.getDetailMovieRelease().observe(this, new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                setMovieReleaseDate(s);
            }
        });

        movieViewModel.getDetailMovieRuntime().observe(this, new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                setMovieRuntime(s);
            }
        });

        movieViewModel.getDetailMovieGenres().observe(this, new Observer<List<Genres>>() {
            @Override
            public void onChanged(@Nullable List<Genres> genres) {
                if (genres != null) {
                    setMovieGenres(genres);
                    Log.d(TAG, "onChanged: " + genres.toString());
                }
            }
        });

        movieViewModel.getDetailMovieVideo().observe(this, new Observer<List<Video>>() {
            @Override
            public void onChanged(@Nullable List<Video> videos) {
                if (videos != null) {
                    Log.d(TAG, "onChanged: " + videos.toString());
                    setMovieVideo(videos);
                }
            }
        });

        movieViewModel.getListOfCastForMovie().observe(this, new Observer<List<Cast>>() {
            @Override
            public void onChanged(@Nullable List<Cast> casts) {
                if (casts != null) {
                    setMovieCasts(casts);
                    Log.d(TAG, "onChanged: " + casts.toString());
                }
            }
        });

        movieViewModel.getSimilarMovies().observe(this, new Observer<List<BasicMovie>>() {
            @Override
            public void onChanged(@Nullable List<BasicMovie> movieList) {
                if (movieList != null) {
                    movieActivityAdapter.setMoviesList(movieList);
                }
            }
        });


    }

    private void setMovieTitle(String data) {
        movie_name.setText(data);
    }

    private void setMovieImage(String data) {
        String image = Constants.IMAGE_BASE + data;
        Glide.with(this).load(image).apply(RequestOptions.placeholderOf(R.drawable.studio_icon_stable)).into(movie_image);
    }

    private void setMovieOverview(String data) {
        movie_overview.setText(data);
    }

    private void setMovieReleaseDate(String data) {
        movie_release.setText(data);
    }

    private void setMovieRuntime(String data) {
        movie_runtime.setText(data);
    }

    private void setMovieVideo(final List<Video> video) {

        movie_trailer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (video != null) {
                    for (Video video1 : video) {
                        if (video1.getType().equals("Trailer")) {
                            String key = video1.getKey();
                            youtube = "https://www.youtube.com/watch?v=" + key;
                        }
                    }
                    openTrailer(youtube);
                } else {
                    youtube = "";
                    openTrailer(youtube);
                }
            }
        });
    }

    private void openTrailer(String youtube) {
        if (!youtube.equals("")) {
            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(youtube));
            startActivity(intent);
        } else {
            Toast.makeText(this, "Video preview not available", Toast.LENGTH_SHORT).show();
        }

    }

    private void setMovieGenres(List<Genres> genres) {
        String names = "";
        for (Genres genres1 : genres) {
            names += genres1.getName() + ", ";
        }
        movie_genre.setText(names);
    }

    private void setMovieCasts(List<Cast> castList) {
        String cast = "";
        for (Cast cast1 : castList) {
            cast += cast1.getName() + ", ";
        }
        movie_cast.setText(cast);

    }

    @Override
    public void onMoviesFragmentClick(int position) {
        Intent intent = new Intent(this, MovieActivity.class);
        intent.putExtra("movie", movieActivityAdapter.getSelectedItem(position));
        startActivity(intent);
    }
}