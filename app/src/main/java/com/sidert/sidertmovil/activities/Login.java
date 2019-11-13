package com.sidert.sidertmovil.activities;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Environment;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.sidert.sidertmovil.Home;
import com.sidert.sidertmovil.R;
import com.sidert.sidertmovil.fragments.dialogs.dialog_mailbox;
import com.sidert.sidertmovil.fragments.dialogs.dialog_message;
import com.sidert.sidertmovil.utils.Constants;
import com.sidert.sidertmovil.utils.NameFragments;
import com.sidert.sidertmovil.utils.Popups;
import com.sidert.sidertmovil.utils.SessionManager;
import com.sidert.sidertmovil.utils.Validator;

import java.io.File;

public class Login extends AppCompatActivity {

    private Context ctx;
    private Context context;
    private EditText etUser;
    private EditText etPassword;
    private Button btnLogin;
    private CardView cvDenunciarPLD;
    private Validator validator;
    private SessionManager session;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (Constants.ENVIROMENT)
            getWindow().setFlags(WindowManager.LayoutParams.FLAG_SECURE, WindowManager.LayoutParams.FLAG_SECURE);
        setContentView(R.layout.activity_login);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        ctx             = getApplicationContext();
        context         = this;
        session         = new SessionManager(ctx);
        etUser          = findViewById(R.id.etUser);
        etPassword      = findViewById(R.id.etPassword);
        btnLogin        = findViewById(R.id.btnLogin);
        cvDenunciarPLD  = findViewById(R.id.cvDenciarPLD);

        //etUser.setText("administrador");
        etUser.setText("asesor951");
        validator = new Validator();

        btnLogin.setOnClickListener(btnLogin_OnClick);

        cvDenunciarPLD.setOnClickListener(cvDenunciarPLD_OnClick);

        etUser.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
                InputMethodManager inputManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                inputManager.hideSoftInputFromWindow(etUser.getWindowToken(), 0);
                return true;
            }
        });
    }

    private View.OnClickListener btnLogin_OnClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            doLogin();
            if(!validator.validate(etUser, new String[] {validator.REQUIRED}) &&
                    !validator.validate(etPassword, new String[] {validator.REQUIRED})) {
                doLogin();
            }
        }
    };

    private View.OnClickListener cvDenunciarPLD_OnClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            dialog_mailbox complaint = new dialog_mailbox();
            complaint.show(getSupportFragmentManager(), NameFragments.DIALOGMAILBOX);
        }
    };

    private void doLogin (){
        if (true){
            Intent home = new Intent(this, Home.class);
            switch (etUser.getText().toString().trim()){
                case "asesor951":
                    if(isExternalStorageWritable()){
                        String nombreDirectorioPrivado = "Files";
                        crearDirectorioPrivado(ctx, nombreDirectorioPrivado);
                    }
                    session.setUser("1", "Operador", "0");
                    startActivity(home);
                    finish();
                    break;
                case "administrador":
                    session.setUser("2","Administrador","1");
                    startActivity(home);
                    finish();
                    break;
                default:
                    final AlertDialog success = Popups.showDialogMessage(context, Constants.login,
                            R.string.error_login, R.string.accept, new Popups.DialogMessage() {
                                @Override
                                public void OnClickListener(AlertDialog dialog) {
                                    dialog.dismiss();
                                }
                            });
                    success.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
                    success.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
                    success.show();
                    break;
            }
        }
        else{
            final AlertDialog success = Popups.showDialogMessage(ctx, Constants.login,
                    R.string.error_login, R.string.accept, new Popups.DialogMessage() {
                        @Override
                        public void OnClickListener(AlertDialog dialog) {
                            dialog.dismiss();
                        }
                    });
            success.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
            success.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
            success.show();
        }
    }

    public File crearDirectorioPrivado(Context context, String nombreDirectorio) {
        //Crear directorio privado en la carpeta Pictures.
        File directorio =new File(
                context.getExternalFilesDir(Environment.DIRECTORY_PICTURES),
                nombreDirectorio);

        //Muestro un mensaje en el logcat si no se creo la carpeta por algun motivo
        if (!directorio.mkdirs())
            Log.e("SIDER.MOVIL", "Error: No se creo el directorio privado");

        return directorio;
    }

    private boolean isExternalStorageWritable() {
        String state = Environment.getExternalStorageState();
        return Environment.MEDIA_MOUNTED.equals(state);
    }

}
