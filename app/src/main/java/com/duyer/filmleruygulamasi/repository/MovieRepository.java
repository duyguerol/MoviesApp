package com.duyer.filmleruygulamasi.repository;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;

import com.duyer.filmleruygulamasi.model.Movie;

import java.util.ArrayList;
import java.util.List;

public class MovieRepository {
    private final Context context;
    private static final String DATABASE_NAME = "Filmler";

    public MovieRepository(Context context) {
        this.context = context;
    }

    public List<Movie> getMoviesByCategory(int categoryId) {
        List<Movie> movies = new ArrayList<>();
        try {
            SQLiteDatabase database = context.openOrCreateDatabase(DATABASE_NAME, Context.MODE_PRIVATE, null);
            createTableIfNotExists(database);

            String query = "SELECT * FROM filmler WHERE kategori_id = ?";
            String[] selectionArgs = new String[]{String.valueOf(categoryId)};
            Cursor cursor = database.rawQuery(query, selectionArgs);

            int idIndex = cursor.getColumnIndex("film_id");
            int nameIndex = cursor.getColumnIndex("film_ad");
            int yearIndex = cursor.getColumnIndex("film_yil");
            int imdbIndex = cursor.getColumnIndex("film_imdb");
            int directorIndex = cursor.getColumnIndex("film_yonetmen");
            int imageIndex = cursor.getColumnIndex("film_resim");
            int categoryIdIndex = cursor.getColumnIndex("kategori_id");

            while (cursor.moveToNext()) {
                Movie movie = new Movie(
                    cursor.getInt(idIndex),
                    cursor.getString(nameIndex),
                    cursor.getInt(yearIndex),
                    cursor.getDouble(imdbIndex),
                    cursor.getString(directorIndex),
                    cursor.getBlob(imageIndex),
                    cursor.getInt(categoryIdIndex)
                );
                movies.add(movie);
            }
            cursor.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return movies;
    }

    public boolean saveMovie(Movie movie) {
        try {
            SQLiteDatabase database = context.openOrCreateDatabase(DATABASE_NAME, Context.MODE_PRIVATE, null);
            createTableIfNotExists(database);

            String sqlString = "INSERT INTO filmler (film_ad, film_yil, film_imdb, film_yonetmen, film_resim, kategori_id) VALUES (?, ?, ?, ?, ?, ?)";
            SQLiteStatement statement = database.compileStatement(sqlString);

            statement.bindString(1, movie.getName());
            statement.bindLong(2, movie.getYear());
            statement.bindDouble(3, movie.getImdbRating());
            statement.bindString(4, movie.getDirector());
            statement.bindBlob(5, movie.getImage());
            statement.bindLong(6, movie.getCategoryId());

            statement.execute();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean deleteMovie(int movieId) {
        try {
            SQLiteDatabase database = context.openOrCreateDatabase(DATABASE_NAME, Context.MODE_PRIVATE, null);
            String deleteQuery = "DELETE FROM filmler WHERE film_id = ?";
            database.execSQL(deleteQuery, new String[]{String.valueOf(movieId)});
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    private void createTableIfNotExists(SQLiteDatabase database) {
        database.execSQL("CREATE TABLE IF NOT EXISTS filmler (" +
                "film_id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "film_ad VARCHAR, " +
                "film_yil INTEGER, " +
                "film_imdb REAL, " +
                "film_yonetmen VARCHAR, " +
                "film_resim BLOB, " +
                "kategori_id INTEGER)");
    }
} 