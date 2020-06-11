package com.Sidiq.moviecataloc.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.Sidiq.moviecataloc.BuildConfig;
import com.Sidiq.moviecataloc.R;
import com.Sidiq.moviecataloc.model.MoviePopuler;
import com.bumptech.glide.Glide;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class RecylerPopulerAdapter extends RecyclerView.Adapter<RecylerPopulerAdapter.ViewHolder> {

    private List<MoviePopuler> mListItem;
    private static final String TAG = "RecylerPopulerAdapter";
    private Context context;
    private final static String IMAGE_BASE_URL = BuildConfig.IMAGE_BASE_URL;
    private List<MoviePopuler> moviePopulers;

    public RecylerPopulerAdapter(Context context, List<MoviePopuler> moviePopulers) {
        this.context = context;
        this.moviePopulers = moviePopulers;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_movie, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        String poster_url = IMAGE_BASE_URL + "w185" + moviePopulers.get(position).getPosterPath();
        Glide.with(context.getApplicationContext())
                .load(poster_url)
                .error(R.drawable.ic_launcher_foreground)
                .into(holder.imageView);
        holder.titleMovie.setText(moviePopulers.get(position).getTitle());
    }

    @Override
    public int getItemCount() {
        return moviePopulers.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.image_card_movie)
        ImageView imageView;
        @BindView(R.id.title_movie)
        TextView titleMovie;
        @BindView(R.id.cardViewMovie)
        CardView cardView;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

//
}



