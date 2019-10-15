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
import com.example.moviesapp.R;
import com.example.moviesapp.Utils.Constants;

import java.util.List;

public class BasicTopRatedMoviesAdapter extends RecyclerView.Adapter<BasicTopRatedMoviesAdapter.TopRatedMoviesViewHolder> {

    private List<BasicMovie> basicTopRatedMovieList;
    private Context context;
    private OnItemClick onItemClick;

    public BasicTopRatedMoviesAdapter(OnItemClick onItemClick) {
        this.onItemClick = onItemClick;
    }

    @NonNull
    @Override
    public TopRatedMoviesViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.all_list_items, viewGroup, false);
        return new TopRatedMoviesViewHolder(view, onItemClick);
    }

    @Override
    public void onBindViewHolder(@NonNull TopRatedMoviesViewHolder topRatedMoviesViewHolder, int i) {

        BasicMovie basicMovie = basicTopRatedMovieList.get(i);

        //topRatedMoviesViewHolder.text.setText(basicMovie.getMovie_name());
        topRatedMoviesViewHolder.bind(basicMovie);
    }

    @Override
    public int getItemCount() {
        if (basicTopRatedMovieList != null) {
            return basicTopRatedMovieList.size();
        }
        return 0;
    }

    public class TopRatedMoviesViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private ImageView imageView;
        private TextView text;
        private OnItemClick onItemClick;

        public TopRatedMoviesViewHolder(@NonNull View itemView, OnItemClick onItemClick) {
            super(itemView);

            this.onItemClick = onItemClick;
            imageView = itemView.findViewById(R.id.image);
            text = itemView.findViewById(R.id.text);

            itemView.setOnClickListener(this);

        }

        public void bind(BasicMovie basicMovie){
            Glide.with(itemView).load(Constants.IMAGE_BASE + basicMovie.getImage_url())
                    .apply(RequestOptions.placeholderOf(R.drawable.studio_icon_stable)).into(imageView);
        }

        @Override
        public void onClick(View v) {
            onItemClick.onTopRatedMovieClick(getAdapterPosition());
        }
    }

    public void setTopRatedMovies(List<BasicMovie> list) {
        basicTopRatedMovieList = list;
        notifyDataSetChanged();
    }

    public BasicMovie getSelectedTopRatedMovie(int position){
        if (basicTopRatedMovieList != null){
            if (basicTopRatedMovieList.size() > 0){
                return basicTopRatedMovieList.get(position);
            }
        }
        return null;
    }

}
