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
import com.example.moviesapp.Models.BasicTVShow;
import com.example.moviesapp.OnClickListeners.OnItemClick;
import com.example.moviesapp.R;
import com.example.moviesapp.Utils.Constants;

import java.util.List;

public class BasicTopRatedTVShowsAdapter extends RecyclerView.Adapter<BasicTopRatedTVShowsAdapter.TopRatedTVShowsViewHolder> {

    private List<BasicTVShow> basicTopRatedTVShowsList;
    private Context context;
    private OnItemClick onItemClick;

    public BasicTopRatedTVShowsAdapter(OnItemClick onItemClick) {
        this.onItemClick = onItemClick;
    }

    @NonNull
    @Override
    public TopRatedTVShowsViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.all_list_items, viewGroup, false);
        return new TopRatedTVShowsViewHolder(view, onItemClick);
    }

    @Override
    public void onBindViewHolder(@NonNull TopRatedTVShowsViewHolder topRatedTVShowsViewHolder, int i) {

        BasicTVShow basicTVShow = basicTopRatedTVShowsList.get(i);

        //topRatedTVShowsViewHolder.textView.setText(basicTVShow.getOriginal_name());
        topRatedTVShowsViewHolder.bind(basicTVShow);
    }


    @Override
    public int getItemCount() {
        if (basicTopRatedTVShowsList != null) {
            return basicTopRatedTVShowsList.size();
        }
        return 0;
    }

    public class TopRatedTVShowsViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private ImageView imageView;
        private TextView textView;
        private OnItemClick onItemClick;

        public TopRatedTVShowsViewHolder(@NonNull View itemView, OnItemClick onItemClick) {
            super(itemView);

            this.onItemClick = onItemClick;
            imageView = itemView.findViewById(R.id.image);
            textView = itemView.findViewById(R.id.text);

            itemView.setOnClickListener(this);
        }

        public void bind(BasicTVShow basicTVShow) {
            Glide.with(itemView).load(Constants.IMAGE_BASE + basicTVShow.getImage_url())
                    .apply(RequestOptions.placeholderOf(R.drawable.studio_icon_stable)).into(imageView);
        }

        @Override
        public void onClick(View v) {
            onItemClick.onTopRatedTVShowClick(getAdapterPosition());
        }
    }

    public void setTopRatedTVShows(List<BasicTVShow> list) {
        basicTopRatedTVShowsList = list;
        notifyDataSetChanged();
    }

    public BasicTVShow getSelectedTopRatedTVShow(int position) {
        if (basicTopRatedTVShowsList != null) {
            if (basicTopRatedTVShowsList.size() > 0) {
                return basicTopRatedTVShowsList.get(position);
            }
        }
        return null;
    }
}
