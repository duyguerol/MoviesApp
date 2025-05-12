package com.duyer;

import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.duyer.filmleruygulamasi.DetailActivity;
import com.duyer.filmleruygulamasi.databinding.FilmCardBinding;

import java.util.ArrayList;

public class FilmlerAdapter extends RecyclerView.Adapter<FilmlerAdapter.FilmlerHolder> {
    private final ArrayList<Filmler> filmlerArrayList;
    private final Context context;

    public FilmlerAdapter(Context context, ArrayList<Filmler> filmlerArrayList) {
        this.context = context;
        this.filmlerArrayList = filmlerArrayList;
    }

    @NonNull
    @Override
    public FilmlerHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        FilmCardBinding binding = FilmCardBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new FilmlerHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull FilmlerHolder holder, int position) {
        Filmler film = filmlerArrayList.get(position);
        holder.binding.textViewFilmAd.setText(film.getFilm_ad());

        // Set movie image
        if (film.getFilm_resim() != null) {
            holder.binding.imageViewFilmResim.setImageBitmap(BitmapFactory.decodeByteArray(film.getFilm_resim(), 0, film.getFilm_resim().length));
        }

        // Set click listener for detail view
        holder.itemView.setOnClickListener(v -> {
            Intent intent = new Intent(context, DetailActivity.class);
            intent.putExtra("film_id", film.getFilm_id());
            intent.putExtra("film_ad", film.getFilm_ad());
            intent.putExtra("film_yil", film.getFilm_yil());
            intent.putExtra("film_imdb", film.getFilm_imdb());
            intent.putExtra("film_yonetmen", film.getFilm_yonetmen());
            intent.putExtra("film_resim", film.getFilm_resim());
            intent.putExtra("kategori_id", film.getKategori_id());
            context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return filmlerArrayList.size();
    }

    public static class FilmlerHolder extends RecyclerView.ViewHolder {
        private final FilmCardBinding binding;

        public FilmlerHolder(FilmCardBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}








