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

public class BasicPopularMoviesAdapter extends RecyclerView.Adapter<BasicPopularMoviesAdapter.MyViewHolder> {

    private List<BasicMovie> basicPopularMovieList;
    private Context context;
    private OnItemClick onItemClick;

    public BasicPopularMoviesAdapter(OnItemClick onItemClick) {
        this.onItemClick = onItemClick;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.all_list_items, viewGroup, false);
        return new MyViewHolder(view, onItemClick);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder myViewHolder, int i) {

        BasicMovie basicMovie = basicPopularMovieList.get(i);

       // myViewHolder.text.setText(basicMovie.getMovie_name());
        String image = Constants.IMAGE_BASE + basicMovie.getImage_url();
        myViewHolder.bind(basicMovie);
    }

    @Override
    public int getItemCount() {
        if (basicPopularMovieList != null) {
            return basicPopularMovieList.size();
        }
        return 0;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private ImageView image;
        private TextView text;
        private OnItemClick onItemClick;

        public MyViewHolder(@NonNull View itemView, OnItemClick onItemClick) {
            super(itemView);
            this.onItemClick = onItemClick;
            image = itemView.findViewById(R.id.image);
            text = itemView.findViewById(R.id.text);

            itemView.setOnClickListener(this);
        }

        public void bind(BasicMovie basicMovie){
            Glide.with(itemView).load(Constants.IMAGE_BASE + basicMovie.getImage_url())
                    .apply(RequestOptions.placeholderOf(R.drawable.studio_icon_stable)).into(image);
        }

        @Override
        public void onClick(View v) {
            onItemClick.onPopularMovieClick(getAdapterPosition());
        }
    }

    public void setPopularMovies(List<BasicMovie> popularMovies) {
        basicPopularMovieList = popularMovies;
        notifyDataSetChanged();
    }

    public BasicMovie getSelectedMovie(int position) {
        if (basicPopularMovieList != null) {
            if (basicPopularMovieList.size() > 0) {
                return basicPopularMovieList.get(position);
            }
        }
        return null;
    }
}
