package com.duyer;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.duyer.filmleruygulamasi.FilmlerActivity;
import com.duyer.filmleruygulamasi.R;
import com.duyer.filmleruygulamasi.databinding.ActivitySaveBinding;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;

public class SaveActivity extends AppCompatActivity {
    private ActivitySaveBinding binding;
    private Bitmap selectedImage;
    private int selectedCategoryId;
    private String selectedCategoryName;

    private final ActivityResultLauncher<Intent> imagePickerLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            result -> {
                if (result.getResultCode() == RESULT_OK && result.getData() != null) {
                    try {
                        Uri imageUri = result.getData().getData();
                        selectedImage = MediaStore.Images.Media.getBitmap(this.getContentResolver(), imageUri);
                        binding.imageView2.setImageBitmap(selectedImage);
                    } catch (IOException e) {
                        e.printStackTrace();
                        Toast.makeText(this, "Error loading image", Toast.LENGTH_SHORT).show();
                    }
                }
            });

    private final ActivityResultLauncher<String> requestPermissionLauncher =
            registerForActivityResult(new ActivityResultContracts.RequestPermission(), isGranted -> {
                if (isGranted) {
                    // Permission granted, launch image picker
                    launchImagePicker();
                } else {
                    // Permission denied
                    Toast.makeText(this, "Permission required to select images", Toast.LENGTH_SHORT).show();
                }
            });

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySaveBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        selectedCategoryId = getIntent().getIntExtra("kategori_id", -1);
        selectedCategoryName = getIntent().getStringExtra("kategori_ad");

        if (selectedCategoryId == -1 || selectedCategoryName == null) {
            Toast.makeText(this, "Error: No category selected", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }

        binding.buttonsave.setOnClickListener(this::save);
        binding.imageView2.setOnClickListener(v -> checkPermissionAndPickImage());
    }

    private void checkPermissionAndPickImage() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            // Android 13 and above - use READ_MEDIA_IMAGES
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_MEDIA_IMAGES) 
                    == PackageManager.PERMISSION_GRANTED) {
                launchImagePicker();
            } else {
                requestPermissionLauncher.launch(Manifest.permission.READ_MEDIA_IMAGES);
            }
        } else {
            // Below Android 13 - use READ_EXTERNAL_STORAGE
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) 
                    == PackageManager.PERMISSION_GRANTED) {
                launchImagePicker();
            } else {
                requestPermissionLauncher.launch(Manifest.permission.READ_EXTERNAL_STORAGE);
            }
        }
    }

    private void launchImagePicker() {
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        imagePickerLauncher.launch(intent);
    }

    private void save(View view) {
        String filmAd = binding.editTextad.getText().toString();
        String filmYil = binding.editTextyil.getText().toString();
        String filmImdb = binding.editTextimdb.getText().toString();
        String filmYonetmen = binding.editTextyonetmen.getText().toString();

        if (filmAd.isEmpty() || filmYil.isEmpty() || filmImdb.isEmpty() || filmYonetmen.isEmpty() || selectedImage == null) {
            Toast.makeText(this, "Please fill all fields and select an image", Toast.LENGTH_SHORT).show();
            return;
        }

        try {
            SQLiteDatabase database = this.openOrCreateDatabase("Filmler", MODE_PRIVATE, null);
            database.execSQL("CREATE TABLE IF NOT EXISTS filmler (film_id INTEGER PRIMARY KEY AUTOINCREMENT, film_ad VARCHAR, film_yil INTEGER, film_imdb REAL, film_yonetmen VARCHAR, film_resim BLOB, kategori_id INTEGER)");

            String sqlString = "INSERT INTO filmler (film_ad, film_yil, film_imdb, film_yonetmen, film_resim, kategori_id) VALUES (?, ?, ?, ?, ?, ?)";
            SQLiteStatement statement = database.compileStatement(sqlString);

            statement.bindString(1, filmAd);
            statement.bindString(2, filmYil);
            statement.bindString(3, filmImdb);
            statement.bindString(4, filmYonetmen);

            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            selectedImage.compress(Bitmap.CompressFormat.PNG, 100, outputStream);
            byte[] imageBytes = outputStream.toByteArray();
            statement.bindBlob(5, imageBytes);
            statement.bindLong(6, selectedCategoryId);

            statement.execute();

            Toast.makeText(this, "Movie saved successfully to " + selectedCategoryName, Toast.LENGTH_SHORT).show();

            Intent intent = new Intent(SaveActivity.this, FilmlerActivity.class);
            intent.putExtra("kategori_id", selectedCategoryId);
            intent.putExtra("kategori_ad", selectedCategoryName);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
            finish();

        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(this, "Error saving movie: " + e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }
}
