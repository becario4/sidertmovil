package com.sidert.sidertmovil;

import android.content.Intent;
//import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.sidert.sidertmovil.activities.Login;
import com.sidert.sidertmovil.utils.SessionManager;

/**
Clase para valida si se requiere inicio de sesion o pasa directo a la cartera
*/
public class MainActivity extends AppCompatActivity {

    private SessionManager session;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        session = SessionManager.getInstance(this);

        Intent i;

        /**Si en datos de sesion de usuario es diferente de null y FLAG = true pasa a la vista de cartera **/
        if (session.getUser().get(0) != null && session.getUser().get(6).equals("true")) {
            i = new Intent(getApplication(), Home.class);
            startActivity(i);
            finish();
        }
        else{/**Cuando no hay datos de sesion guardados o haya cerrado sesion o su inicio
            de sesion sea menor al dia de hoy le pedirá Inicio de sesion*/
            i = new Intent(getApplication(), Login.class);
            startActivity(i);
            finish();
        }
    }

    @Override
    protected void onDestroy()
    {
        super.onDestroy();


    }

}
