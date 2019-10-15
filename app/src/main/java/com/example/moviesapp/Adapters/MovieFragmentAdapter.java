package com.example.moviesapp.Adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.moviesapp.Models.BasicMovie;
import com.example.moviesapp.OnClickListeners.OnItemClick;
import com.example.moviesapp.OnClickListeners.OnMovieItemClick;
import com.example.moviesapp.R;
import com.example.moviesapp.Utils.Constants;

import java.util.List;

public class MovieFragmentAdapter extends RecyclerView.Adapter<MovieFragmentAdapter.MovieFragmentViewHolder> {

    private List<BasicMovie> list;
    private Context context;
    private OnMovieItemClick onMovieItemClick;

    public MovieFragmentAdapter(OnMovieItemClick onMovieItemClick) {
        this.onMovieItemClick = onMovieItemClick;
    }

    @NonNull
    @Override
    public MovieFragmentViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.all_list_items1, viewGroup, false);
        return new MovieFragmentViewHolder(view, onMovieItemClick);
    }

    @Override
    public void onBindViewHolder(@NonNull MovieFragmentViewHolder movieFragmentViewHolder, int i) {

        BasicMovie basicMovie = list.get(i);

        movieFragmentViewHolder.textView.setText(basicMovie.getMovie_name());
        movieFragmentViewHolder.bind(basicMovie);
    }

    @Override
    public int getItemCount() {
        if (list != null) {
            return list.size();
        }
        return 0;
    }

    public void setMovies(List<BasicMovie> movieList){
        list = movieList;
        notifyDataSetChanged();
    }

    public BasicMovie getSelectedMovie(int position){
        if (list != null){
            if (list.size() > 0){
                return list.get(position);
            }
        }
        return null;
    }

    public class MovieFragmentViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private ImageView imageView;
        private TextView textView;
        private OnMovieItemClick onMovieItemClick;

        public MovieFragmentViewHolder(@NonNull View itemView, OnMovieItemClick onMovieItemClick) {
            super(itemView);

            this.onMovieItemClick = onMovieItemClick;
            imageView = itemView.findViewById(R.id.image);
            textView = itemView.findViewById(R.id.text);

            itemView.setOnClickListener(this);
        }

        public void bind(BasicMovie basicMovie){
            Glide.with(itemView).load(Constants.IMAGE_BASE + basicMovie.getImage_url())
                    .apply(RequestOptions.placeholderOf(R.drawable.studio_icon_stable)).into(imageView);
        }

        @Override
        public void onClick(View v) {
            onMovieItemClick.onMoviesFragmentClick(getAdapterPosition());
        }
    }
}
