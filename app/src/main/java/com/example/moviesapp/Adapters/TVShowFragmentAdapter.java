package com.example.moviesapp.Adapters;

import android.content.Context;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.moviesapp.Fragments.TVShowsFragment;
import com.example.moviesapp.Models.BasicTVShow;
import com.example.moviesapp.OnClickListeners.OnTVShowItemClick;
import com.example.moviesapp.R;
import com.example.moviesapp.Utils.Constants;

import java.util.List;

public class TVShowFragmentAdapter extends RecyclerView.Adapter<TVShowFragmentAdapter.TVShowFragmentViewHolder> {

    private List<BasicTVShow> basicTVShowList;
    private Context context;
    private OnTVShowItemClick onTVShowItemClick;

    public TVShowFragmentAdapter(OnTVShowItemClick onTVShowItemClick) {
        this.onTVShowItemClick = onTVShowItemClick;
    }

    @NonNull
    @Override
    public TVShowFragmentViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.all_list_items1, viewGroup, false);
        return new TVShowFragmentViewHolder(view, onTVShowItemClick);
    }

    @Override
    public void onBindViewHolder(@NonNull TVShowFragmentViewHolder tvShowFragmentViewHolder, int i) {

        BasicTVShow basicTVShow = basicTVShowList.get(i);

        tvShowFragmentViewHolder.textView.setText(basicTVShow.getOriginal_name());
        tvShowFragmentViewHolder.bind(basicTVShow);
    }

    @Override
    public int getItemCount() {
        if (basicTVShowList != null) {
            return basicTVShowList.size();
        }
        return 0;
    }

    public void setTVShows(List<BasicTVShow> tvShows) {
        basicTVShowList = tvShows;
        notifyDataSetChanged();
    }

    public BasicTVShow getSelectedTVShow(int position) {
        if (basicTVShowList != null) {
            if (basicTVShowList.size() > 0) {
                return basicTVShowList.get(position);
            }
        }
        return null;
    }

    public class TVShowFragmentViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private ImageView imageView;
        private TextView textView;
        private OnTVShowItemClick onTVShowItemClick;

        public TVShowFragmentViewHolder(@NonNull View itemView, OnTVShowItemClick onTVShowItemClick) {
            super(itemView);

            this.onTVShowItemClick = onTVShowItemClick;
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
            onTVShowItemClick.OnTVShowFragmentClick(getAdapterPosition());
        }
    }
}
