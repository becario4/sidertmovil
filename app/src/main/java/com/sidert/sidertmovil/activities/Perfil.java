package com.sidert.sidertmovil.activities;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
//import android.support.design.widget.FloatingActionButton;
import androidx.appcompat.app.AppCompatActivity;
//import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;

import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.sidert.sidertmovil.R;
import com.sidert.sidertmovil.utils.SessionManager;

import de.hdodenhof.circleimageview.CircleImageView;

public class Perfil extends AppCompatActivity {

    private Context ctx;
    private Toolbar TBmain;
    private FloatingActionButton fabPicture;
    private CircleImageView civAvatar;
    private final int WR_PERMS = 3453;
    private EditText etNombre;
    private EditText etEmail;
    private EditText etCelular;
    private EditText etPuesto;

    private SessionManager session;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_perfil);
        ctx             = getApplicationContext();
        session         = SessionManager.getInstance(ctx);
        TBmain          = findViewById(R.id.TBmain);
        fabPicture      = findViewById(R.id.fabPicture);
        civAvatar       = findViewById(R.id.civAvatar);
        etNombre        = findViewById(R.id.etNombre);
        etEmail         = findViewById(R.id.etEmail);
        etCelular       = findViewById(R.id.etCelular);
        etPuesto        = findViewById(R.id.etPuesto);

        setSupportActionBar(TBmain);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        //setTitle(getApplicationContext().getString(R.string.edit_profile));
        setTitle("Perfil");

        etNombre.setText(session.getUser().get(1)+" "+session.getUser().get(2)+" "+session.getUser().get(3));
        etEmail.setText(session.getUser().get(7));

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

            }
        }
    };

    @Override
    public void onActivityResult(final int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode){

        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        //inflater.inflate(R.menu.menu_save, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int itemId = item.getItemId();
        if (itemId == android.R.id.home) {
            finish();
        } else if (itemId == R.id.save) {
            Toast.makeText(ctx, "Error, Estamos trabajando", Toast.LENGTH_SHORT).show();
        }
        return super.onOptionsItemSelected(item);
    }
}
