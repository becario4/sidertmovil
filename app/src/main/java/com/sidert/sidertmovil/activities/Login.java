package com.sidert.sidertmovil.activities;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.sidert.sidertmovil.Home;
import com.sidert.sidertmovil.R;
import com.sidert.sidertmovil.fragments.dialogs.dialog_message;
import com.sidert.sidertmovil.utils.Constants;
import com.sidert.sidertmovil.utils.NameFragments;
import com.sidert.sidertmovil.utils.Validator;

public class Login extends AppCompatActivity {

    private Context ctx;
    private EditText etUser;
    private EditText etPassword;
    private Button btnLogin;
    private Validator validator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_SECURE, WindowManager.LayoutParams.FLAG_SECURE);
        setContentView(R.layout.activity_login);
        ctx = getApplicationContext();
        etUser  = findViewById(R.id.etUser);
        etPassword  = findViewById(R.id.etPassword);
        btnLogin    = findViewById(R.id.btnLogin);

        validator = new Validator();

        btnLogin.setOnClickListener(btnLogin_OnClick);

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

    private void doLogin (){
        if (true){
        //if (etUser.getText().toString().equals("sidertt/alejandro") && etPassword.getText().toString().equals("4l3j4ndr0")){
            Intent home = new Intent(this, Home.class);
            startActivity(home);
            finish();
        }
        else{
            dialog_message popup_message = new dialog_message();
            Bundle b = new Bundle();
            b.putString(Constants.message,"Usuario y/o contraseña incorrecta, intente de nuevo.");
            popup_message.setArguments(b);
            popup_message.show(getSupportFragmentManager(), NameFragments.DIALOGMESSAGE);
        }
    }
}
