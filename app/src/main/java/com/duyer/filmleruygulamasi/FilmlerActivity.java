package com.duyer.filmleruygulamasi;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import com.duyer.filmleruygulamasi.adapter.MovieAdapter;
import com.duyer.filmleruygulamasi.databinding.ActivityFilmlerBinding;
import com.duyer.filmleruygulamasi.viewmodel.MovieViewModel;

public class FilmlerActivity extends AppCompatActivity {
    private ActivityFilmlerBinding binding;
    private MovieAdapter adapter;
    private MovieViewModel viewModel;
    private int kategoriId;
    private String kategoriAd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityFilmlerBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        kategoriId = getIntent().getIntExtra("kategori_id", -1);
        kategoriAd = getIntent().getStringExtra("kategori_ad");

        if (kategoriId == -1 || kategoriAd == null) {
            finish();
            return;
        }

        binding.toolbar2.setTitle(kategoriAd + " Movies");
        setSupportActionBar(binding.toolbar2);

        // Setup RecyclerView
        binding.filmlerRv.setHasFixedSize(true);
        binding.filmlerRv.setLayoutManager(new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL));

        adapter = new MovieAdapter(this);
        binding.filmlerRv.setAdapter(adapter);

        // Initialize ViewModel
        viewModel = new ViewModelProvider(this).get(MovieViewModel.class);
        
        // Observe movies
        viewModel.getMovies().observe(this, movies -> {
            adapter.setMovies(movies);
            adapter.notifyDataSetChanged();
        });

        // Load movies
        viewModel.loadMoviesByCategory(kategoriId);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.tasarim_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.menu_add) {
            Intent intent = new Intent(FilmlerActivity.this, SaveActivity.class);
            intent.putExtra("kategori_id", kategoriId);
            intent.putExtra("kategori_ad", kategoriAd);
            startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onResume() {
        super.onResume();
        viewModel.loadMoviesByCategory(kategoriId);
    }
}