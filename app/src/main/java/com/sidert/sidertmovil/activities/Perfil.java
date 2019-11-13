package com.sidert.sidertmovil.activities;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.sidert.sidertmovil.R;
import com.theartofdev.edmodo.cropper.CropImage;

import java.io.File;

import de.hdodenhof.circleimageview.CircleImageView;

public class Perfil extends AppCompatActivity {

    private Context ctx;
    private Toolbar TBmain;
    private FloatingActionButton fabPicture;
    private CircleImageView civAvatar;
    private final int WR_PERMS = 3453;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_perfil);
        ctx             = getApplicationContext();
        TBmain          = findViewById(R.id.TBmain);
        fabPicture      = findViewById(R.id.fabPicture);
        civAvatar       = findViewById(R.id.civAvatar);

        setSupportActionBar(TBmain);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        setTitle(getApplicationContext().getString(R.string.edit_profile));

        fabPicture.setOnClickListener(fabPicture_OnClick);
    }

    private View.OnClickListener fabPicture_OnClick = new View.OnClickListener() {
        @SuppressLint("NewApi")
        @Override
        public void onClick(View v) {
            if (ContextCompat.checkSelfPermission(ctx, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED
                    || ContextCompat.checkSelfPermission(ctx,Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED){
                requestPermissions(new String[] {Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE}, WR_PERMS);
            } else {
                CropImage.activity().
                        setOutputCompressQuality(50).
                        start(Perfil.this);
            }
        }
    };

    @Override
    public void onActivityResult(final int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode){
            case CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE:
                if (data != null){
                    CropImage.ActivityResult result = CropImage.getActivityResult(data);
                    final Uri selectedImage = result.getUri();
                    final File foto = new File(selectedImage.getPath());
                    Glide.with(ctx).load(foto).into(civAvatar);
                }
                break;
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_save, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;
            case R.id.save:
                Toast.makeText(ctx,"Error, Estamos trabajando",Toast.LENGTH_SHORT).show();
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}