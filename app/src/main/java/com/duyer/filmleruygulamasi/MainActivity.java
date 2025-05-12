package com.duyer.filmleruygulamasi;

import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.duyer.Kategoriler;
import com.duyer.KategorilerAdapter;
import com.duyer.filmleruygulamasi.databinding.ActivityMainBinding;

import java.io.IOException;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private ActivityMainBinding binding;
    private ArrayList<Kategoriler> kategoriList;
    private KategorilerAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        kategoriList = new ArrayList<>();

        binding.toolbar.setTitle("Kategoriler");
        setSupportActionBar(binding.toolbar);

        binding.kategoriRv.setHasFixedSize(true);
        binding.kategoriRv.setLayoutManager(new LinearLayoutManager(this));

        try {
            SQLiteDatabase database = this.openOrCreateDatabase("Kategoriler", MODE_PRIVATE, null);
            database.execSQL("CREATE TABLE IF NOT EXISTS kategoriler (kategori_id INTEGER PRIMARY KEY AUTOINCREMENT, kategori_ad VARCHAR)");

            // Sadece ilk kez çalıştır
            SharedPreferences prefs = getSharedPreferences("com.ornek.kategoriler", MODE_PRIVATE);
            boolean ilkAcilis = prefs.getBoolean("ilkAcilis", true);

            if (ilkAcilis) {
                database.execSQL("DELETE FROM kategoriler"); // Önlem amaçlı temizle
                database.execSQL("INSERT INTO kategoriler (kategori_ad) VALUES ('Dram')");
                database.execSQL("INSERT INTO kategoriler (kategori_ad) VALUES ('Komedi')");
                database.execSQL("INSERT INTO kategoriler (kategori_ad) VALUES ('Bilim Kurgu')");
                database.execSQL("INSERT INTO kategoriler (kategori_ad) VALUES ('Fantastik')");
                database.execSQL("INSERT INTO kategoriler (kategori_ad) VALUES ('Gerilim')");
                database.execSQL("INSERT INTO kategoriler (kategori_ad) VALUES ('Aksiyon')");
                database.execSQL("INSERT INTO kategoriler (kategori_ad) VALUES ('Korku')");
                database.execSQL("INSERT INTO kategoriler (kategori_ad) VALUES ('Romantik')");
                database.execSQL("INSERT INTO kategoriler (kategori_ad) VALUES ('Spor')");
                database.execSQL("INSERT INTO kategoriler (kategori_ad) VALUES ('Macera')");
                database.execSQL("INSERT INTO kategoriler (kategori_ad) VALUES ('Gizem')");
                database.execSQL("INSERT INTO kategoriler (kategori_ad) VALUES ('Animasyon')");
                database.execSQL("INSERT INTO kategoriler (kategori_ad) VALUES ('Savaş')");
                database.execSQL("INSERT INTO kategoriler (kategori_ad) VALUES ('Aile')");
                database.execSQL("INSERT INTO kategoriler (kategori_ad) VALUES ('Tarih')");
                database.execSQL("INSERT INTO kategoriler (kategori_ad) VALUES ('Müzikal')");
                database.execSQL("INSERT INTO kategoriler (kategori_ad) VALUES ('Doğa')");
                database.execSQL("INSERT INTO kategoriler (kategori_ad) VALUES ('Biyografi')");
                database.execSQL("INSERT INTO kategoriler (kategori_ad) VALUES ('Belgesel')");
                database.execSQL("INSERT INTO kategoriler (kategori_ad) VALUES ('Western')");
                database.execSQL("INSERT INTO kategoriler (kategori_ad) VALUES ('Spor')");
                database.execSQL("INSERT INTO kategoriler (kategori_ad) VALUES ('Kısa Film')");
                database.execSQL("INSERT INTO kategoriler (kategori_ad) VALUES ('Anime')");
                database.execSQL("INSERT INTO kategoriler (kategori_ad) VALUES ('Çocuk')");
                database.execSQL("INSERT INTO kategoriler (kategori_ad) VALUES ('Sanat Filmi')");

                prefs.edit().putBoolean("ilkAcilis", false).apply();
            }

            Cursor cursor = database.rawQuery("SELECT * FROM kategoriler", null);
            int kategori_idIx = cursor.getColumnIndex("kategori_id");
            int kategori_adIx = cursor.getColumnIndex("kategori_ad");

            while (cursor.moveToNext()) {
                int id = cursor.getInt(kategori_idIx);
                String ad = cursor.getString(kategori_adIx);
                kategoriList.add(new Kategoriler(id, ad));
            }
            cursor.close();

            adapter = new KategorilerAdapter(this,kategoriList);
            binding.kategoriRv.setAdapter(adapter);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
