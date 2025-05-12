package com.duyer.filmleruygulamasi.viewmodel;

import android.app.Application;
import android.graphics.Bitmap;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.duyer.filmleruygulamasi.model.Movie;
import com.duyer.filmleruygulamasi.repository.MovieRepository;

import java.io.ByteArrayOutputStream;
import java.util.List;

public class MovieViewModel extends AndroidViewModel {
    private final MovieRepository repository;
    private final MutableLiveData<List<Movie>> movies = new MutableLiveData<>();
    private final MutableLiveData<Boolean> saveResult = new MutableLiveData<>();
    private final MutableLiveData<Boolean> deleteResult = new MutableLiveData<>();

    public MovieViewModel(@NonNull Application application) {
        super(application);
        repository = new MovieRepository(application);
    }

    public LiveData<List<Movie>> getMovies() {
        return movies;
    }

    public LiveData<Boolean> getSaveResult() {
        return saveResult;
    }

    public LiveData<Boolean> getDeleteResult() {
        return deleteResult;
    }

    public void loadMoviesByCategory(int categoryId) {
        List<Movie> movieList = repository.getMoviesByCategory(categoryId);
        movies.setValue(movieList);
    }

    public void saveMovie(String name, int year, double imdbRating, String director, Bitmap image, int categoryId) {
        try {
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            image.compress(Bitmap.CompressFormat.PNG, 100, outputStream);
            byte[] imageBytes = outputStream.toByteArray();

            Movie movie = new Movie(0, name, year, imdbRating, director, imageBytes, categoryId);
            boolean result = repository.saveMovie(movie);
            saveResult.setValue(result);
        } catch (Exception e) {
            e.printStackTrace();
            saveResult.setValue(false);
        }
    }

    public void deleteMovie(int movieId) {
        boolean result = repository.deleteMovie(movieId);
        deleteResult.setValue(result);
    }
} 