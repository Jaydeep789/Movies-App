package com.example.moviesapp.Adapters;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.moviesapp.Models.BasicMovie;
import com.example.moviesapp.OnClickListeners.OnMovieItemClick;
import com.example.moviesapp.R;
import com.example.moviesapp.Utils.Constants;

import java.util.List;

public class MovieActivityAdapter extends RecyclerView.Adapter<MovieActivityAdapter.MyViewHolder> {

    private List<BasicMovie> basicMovies;
    private OnMovieItemClick onMovieItemClick;

    public MovieActivityAdapter(OnMovieItemClick onMovieItemClick) {
        this.onMovieItemClick = onMovieItemClick;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.all_list_items1, viewGroup, false);
        return new MyViewHolder(view, onMovieItemClick);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder myViewHolder, int i) {

        BasicMovie basicMovie = basicMovies.get(i);

        myViewHolder.textView.setText(basicMovie.getMovie_name());
        myViewHolder.bind(basicMovie);

    }

    @Override
    public int getItemCount() {
        if (basicMovies != null) {
            return basicMovies.size();
        }
        return 0;
    }

    public void setMoviesList(List<BasicMovie> list) {
        basicMovies = list;
        notifyDataSetChanged();
    }

    public BasicMovie getSelectedItem(int position) {
        if (basicMovies != null) {
            if (basicMovies.size() > 0) {
                return basicMovies.get(position);
            }
        }
        return null;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private ImageView imageView;
        private TextView textView;
        private OnMovieItemClick onMovieItemClick;

        public MyViewHolder(@NonNull View itemView, OnMovieItemClick onMovieItemClick) {
            super(itemView);

            this.onMovieItemClick = onMovieItemClick;
            imageView = itemView.findViewById(R.id.image);
            textView = itemView.findViewById(R.id.text);

            itemView.setOnClickListener(this);
        }

        public void bind(BasicMovie basicMovie) {
            Glide.with(itemView).load(Constants.IMAGE_BASE + basicMovie.getImage_url()).into(imageView);
        }

        @Override
        public void onClick(View v) {
            onMovieItemClick.onMoviesFragmentClick(getAdapterPosition());
        }
    }
}
