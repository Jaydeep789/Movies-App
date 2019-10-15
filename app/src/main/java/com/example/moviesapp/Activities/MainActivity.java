package com.example.moviesapp.Activities;

import android.app.ProgressDialog;
import android.support.annotation.NonNull;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;

import com.example.moviesapp.Fragments.DiscoverFragment;
import com.example.moviesapp.Fragments.MoviesFragment;
import com.example.moviesapp.Fragments.TVShowsFragment;
import com.example.moviesapp.R;

public class MainActivity extends AppCompatActivity {

    private BottomNavigationView bottomNavigationView;
    private AppBarLayout appBarLayout;
    private Fragment selected = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        appBarLayout = findViewById(R.id.App_bar);
        bottomNavigationView = findViewById(R.id.bottom_navigationview);
        bottomNavigationView.setOnNavigationItemSelectedListener(itemListener);

        getSupportFragmentManager().beginTransaction().replace(R.id.container, new DiscoverFragment()).commit();
    }

    BottomNavigationView.OnNavigationItemSelectedListener itemListener = new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {

            switch (menuItem.getItemId()) {

                case R.id.discover:
                    selected = new DiscoverFragment();
                    break;

                case R.id.movies:
                    selected = new MoviesFragment();
                    break;

                case R.id.tv_shows:
                    selected = new TVShowsFragment();
                    break;
            }

            if (selected != null) {
                getSupportFragmentManager().beginTransaction().replace(R.id.container, selected).commit();
            }

            return true;
        }
    };
}
