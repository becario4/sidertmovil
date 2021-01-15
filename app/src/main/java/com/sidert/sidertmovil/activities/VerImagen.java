package com.sidert.sidertmovil.activities;

import android.content.Context;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.appcompat.widget.Toolbar;
import android.view.MenuItem;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.sidert.sidertmovil.R;
import com.sidert.sidertmovil.utils.Constants;

import uk.co.senab.photoview.PhotoViewAttacher;

public class VerImagen extends AppCompatActivity {

    private Context ctx;

    private Toolbar TBmain;
    private ImageView ivVerImagen;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ver_imagen);

        ctx             = this;

        TBmain          = findViewById(R.id.TBmain);
        ivVerImagen     = findViewById(R.id.ivVerImagen);

        setSupportActionBar(TBmain);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        setTitle("");

        Bundle data = getIntent().getExtras();

        Glide.with(ctx).load(data.getByteArray(Constants.IMAGEN)).into(ivVerImagen);

        PhotoViewAttacher photo = new PhotoViewAttacher(ivVerImagen);
        photo.update();

    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
