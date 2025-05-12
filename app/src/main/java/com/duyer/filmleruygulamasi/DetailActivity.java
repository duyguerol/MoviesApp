package com.duyer.filmleruygulamasi;

import android.content.DialogInterface;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.duyer.filmleruygulamasi.databinding.ActivityDetailBinding;

public class DetailActivity extends AppCompatActivity {
    private ActivityDetailBinding binding;
    private int filmId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityDetailBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // Get movie details from intent
        filmId = getIntent().getIntExtra("film_id", -1);
        String filmAd = getIntent().getStringExtra("film_ad");
        int filmYil = getIntent().getIntExtra("film_yil", 0);
        double filmImdb = getIntent().getDoubleExtra("film_imdb", 0.0);
        String filmYonetmen = getIntent().getStringExtra("film_yonetmen");
        byte[] filmResim = getIntent().getByteArrayExtra("film_resim");

        // Set toolbar title
        binding.toolbar3.setTitle(filmAd);
        setSupportActionBar(binding.toolbar3);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        // Display movie details
        binding.textViewFilmAd.setText(filmAd);
        binding.textViewFilmYil.setText(String.valueOf(filmYil));
        binding.textViewFilmImdb.setText(String.valueOf(filmImdb));
        binding.textViewFilmYonetmen.setText(filmYonetmen);

        // Display movie image
        if (filmResim != null) {
            binding.imageViewFilmResim.setImageBitmap(BitmapFactory.decodeByteArray(filmResim, 0, filmResim.length));
        }

        // Set back button functionality
        binding.toolbar3.setNavigationOnClickListener(v -> onBackPressed());

        // Set delete button functionality
        binding.buttonDelete.setOnClickListener(v -> showDeleteConfirmationDialog());
    }

    private void showDeleteConfirmationDialog() {
        new AlertDialog.Builder(this)
                .setTitle("Delete Movie")
                .setMessage("Are you sure you want to delete this movie?")
                .setPositiveButton("Yes", (dialog, which) -> deleteMovie())
                .setNegativeButton("No", null)
                .show();
    }

    private void deleteMovie() {
        try {
            SQLiteDatabase database = this.openOrCreateDatabase("Filmler", MODE_PRIVATE, null);
            String deleteQuery = "DELETE FROM filmler WHERE film_id = ?";
            database.execSQL(deleteQuery, new String[]{String.valueOf(filmId)});
            
            Toast.makeText(this, "Movie deleted successfully", Toast.LENGTH_SHORT).show();
            finish(); // Return to previous activity
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(this, "Error deleting movie: " + e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }
} 