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

public class BasicPopularTVShowsAdapter extends RecyclerView.Adapter<BasicPopularTVShowsAdapter.PopularTVShowViewHolder> {

    private List<BasicTVShow> basicPopularTVShowList;
    private Context context;
    private OnItemClick onItemClick;

    public BasicPopularTVShowsAdapter(OnItemClick onItemClick) {
        this.onItemClick = onItemClick;
    }

    @NonNull
    @Override
    public PopularTVShowViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.all_list_items, viewGroup, false);
        return new PopularTVShowViewHolder(view, onItemClick);
    }

    @Override
    public void onBindViewHolder(@NonNull PopularTVShowViewHolder popularTVShowViewHolder, int i) {

        BasicTVShow basicTVShow = basicPopularTVShowList.get(i);

       // popularTVShowViewHolder.textView.setText(basicTVShow.getOriginal_name());
        popularTVShowViewHolder.bind(basicTVShow);

    }

    @Override
    public int getItemCount() {
        if (basicPopularTVShowList != null){
            return basicPopularTVShowList.size();
        }
        return 0;
    }

    public class PopularTVShowViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private ImageView imageView;
        private TextView textView;
        private OnItemClick onItemClick;

        public PopularTVShowViewHolder(@NonNull View itemView, OnItemClick onItemClick) {
            super(itemView);

            this.onItemClick = onItemClick;
            imageView = itemView.findViewById(R.id.image);
            textView = itemView.findViewById(R.id.text);

            itemView.setOnClickListener(this);
        }

        public void bind(BasicTVShow basicTVShow){
            Glide.with(itemView).load(Constants.IMAGE_BASE + basicTVShow.getImage_url())
                    .apply(RequestOptions.placeholderOf(R.drawable.studio_icon_stable)).into(imageView);
        }

        @Override
        public void onClick(View v) {
            onItemClick.onPopularTVShowClick(getAdapterPosition());
        }
    }

    public void setPopularTVShows(List<BasicTVShow> list){
        basicPopularTVShowList = list;
        notifyDataSetChanged();
    }

    public BasicTVShow getSelectedPopularTVShow(int position){
        if (basicPopularTVShowList != null){
            if (basicPopularTVShowList.size() > 0){
                return basicPopularTVShowList.get(position);
            }
        }
        return null;
    }
}
