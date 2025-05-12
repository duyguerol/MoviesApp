package com.duyer.filmleruygulamasi.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.duyer.filmleruygulamasi.DetailActivity;
import com.duyer.filmleruygulamasi.databinding.FilmCardBinding;
import com.duyer.filmleruygulamasi.model.Movie;

import java.util.ArrayList;
import java.util.List;

public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.MovieViewHolder> {
    private final Context context;
    private List<Movie> movies = new ArrayList<>();

    public MovieAdapter(Context context) {
        this.context = context;
    }

    public void setMovies(List<Movie> movies) {
        this.movies = movies;
    }

    @NonNull
    @Override
    public MovieViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        FilmCardBinding binding = FilmCardBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new MovieViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull MovieViewHolder holder, int position) {
        Movie movie = movies.get(position);
        holder.binding.textViewFilmAd.setText(movie.getName());

        if (movie.getImage() != null) {
            holder.binding.imageViewFilmResim.setImageBitmap(
                BitmapFactory.decodeByteArray(movie.getImage(), 0, movie.getImage().length)
            );
        }

        holder.itemView.setOnClickListener(v -> {
            Intent intent = new Intent(context, DetailActivity.class);
            intent.putExtra("film_id", movie.getId());
            intent.putExtra("film_ad", movie.getName());
            intent.putExtra("film_yil", movie.getYear());
            intent.putExtra("film_imdb", movie.getImdbRating());
            intent.putExtra("film_yonetmen", movie.getDirector());
            intent.putExtra("film_resim", movie.getImage());
            intent.putExtra("kategori_id", movie.getCategoryId());
            context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return movies.size();
    }

    static class MovieViewHolder extends RecyclerView.ViewHolder {
        private final FilmCardBinding binding;

        public MovieViewHolder(FilmCardBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
} 