package com.sidert.sidertmovil.activities;

import android.content.Context;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.sidert.sidertmovil.R;
import com.sidert.sidertmovil.utils.Constants;

import java.util.Objects;
import java.util.Optional;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import uk.co.senab.photoview.PhotoViewAttacher;

public class VerImagen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ver_imagen);

        Context ctx = this;

        Toolbar tBmain = findViewById(R.id.TBmain);
        ImageView ivVerImagen = findViewById(R.id.ivVerImagen);

        setSupportActionBar(tBmain);
        Bundle data = getIntent().getExtras();

        ActionBar supportActionBar = getSupportActionBar();

        Optional.ofNullable(supportActionBar)
                .ifPresent(actionBar -> {
                    actionBar.setDisplayHomeAsUpEnabled(true);
                    actionBar.setDisplayShowHomeEnabled(true);
                    setTitle("");
                });

        Optional.ofNullable(data)
                .filter(Objects::nonNull)
                .map(bundle -> bundle.get(Constants.IMAGEN))
                .ifPresent(imageData -> {
                    Glide.with(ctx).load(imageData).into(ivVerImagen);
                    PhotoViewAttacher photo = new PhotoViewAttacher(ivVerImagen);
                    photo.update();
                });
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }
}
