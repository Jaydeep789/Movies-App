package com.example.moviesapp.Adapters;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.moviesapp.Models.BasicTVShow;
import com.example.moviesapp.OnClickListeners.OnTVShowItemClick;
import com.example.moviesapp.R;
import com.example.moviesapp.Utils.Constants;

import java.util.List;

public class TVShowActivityAdapter extends RecyclerView.Adapter<TVShowActivityAdapter.MyViewTVHolder> {

    private List<BasicTVShow> basicTVShows;
    private OnTVShowItemClick onTVShowItemClick;

    public TVShowActivityAdapter(OnTVShowItemClick onTVShowItemClick) {
        this.onTVShowItemClick = onTVShowItemClick;
    }

    @NonNull
    @Override
    public MyViewTVHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.all_list_items1, viewGroup, false);
        return new MyViewTVHolder(view, onTVShowItemClick);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewTVHolder myViewTVHolder, int i) {
        BasicTVShow basicTVShow = basicTVShows.get(i);

        myViewTVHolder.textView.setText(basicTVShow.getOriginal_name());
        myViewTVHolder.bind(basicTVShow);
    }

    @Override
    public int getItemCount() {
        if (basicTVShows != null) {
            return basicTVShows.size();
        }
        return 0;
    }

    public void setTVShows(List<BasicTVShow> basicTVShowList) {
        basicTVShows = basicTVShowList;
        notifyDataSetChanged();
    }

    public BasicTVShow getSelectedTVShow(int position) {
        if (basicTVShows != null) {
            if (basicTVShows.size() > 0) {
                return basicTVShows.get(position);
            }
        }
        return null;
    }

    public class MyViewTVHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private ImageView imageView;
        private TextView textView;
        private OnTVShowItemClick onTVShowItemClick;

        public MyViewTVHolder(@NonNull View itemView, OnTVShowItemClick onTVShowItemClick) {
            super(itemView);

            this.onTVShowItemClick = onTVShowItemClick;
            imageView = itemView.findViewById(R.id.image);
            textView = itemView.findViewById(R.id.text);

            itemView.setOnClickListener(this);
        }

        public void bind(BasicTVShow basicTVShow) {
            Glide.with(itemView).load(Constants.IMAGE_BASE + basicTVShow.getImage_url()).into(imageView);
        }

        @Override
        public void onClick(View v) {
            onTVShowItemClick.OnTVShowFragmentClick(getAdapterPosition());
        }
    }
}
