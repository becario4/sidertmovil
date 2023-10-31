package com.sidert.sidertmovil.v2.ui;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.location.LocationManager;
import android.os.Bundle;
import android.provider.Settings;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;

import com.sidert.sidertmovil.activities.ComunicadoCovid;
import com.sidert.sidertmovil.fragments.dialogs.dialog_mailbox;
import com.sidert.sidertmovil.utils.NameFragments;
import com.sidert.sidertmovil.v2.databinding.ActivityLoginBinding;
import com.sidert.sidertmovil.v2.viewmodels.LoginViewModel;
import com.sidert.sidertmovil.views.pdfreader.PdfReaderActivity;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;

public class LoginActivity
        extends MVVMBaseActivity<ActivityLoginBinding, LoginViewModel> {

    @Override
    public void setDatabinding() {
        this.databinding = ActivityLoginBinding.inflate(this.getLayoutInflater());
    }

    @Override
    public void setViewmodelClass() {
        this.viewmodelClass = LoginViewModel.class;
    }

    @Override
    public void bindViewAndViewmodel() {
        this.databinding.setLoginViewModel(viewmodel);
    }

    @Override
    public void onBuildView(@Nullable Bundle savedInstanceState) {
        super.onBuildView(savedInstanceState);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        LocationManager locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
        this.viewmodel.requestPermissionsForApplication(this);
        /*Valida que el GPS esté activo*/
        if (!locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
            alertNoGps();
        }
    }

    public void bind() {
        Context context = this.getApplicationContext();

        viewmodel.getSuccessLogin().observe(this, success -> {
            if (success) {
                /*Abre la vista de descarga de informacion como cartera y catalogos*/
                this.viewmodel.cleanData();
                Intent home = new Intent(context, DescargaDatosActivity.class);
                home.putExtra("login", true);
                home.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(home);
                finish();
            }
        });

        viewmodel.getShowHelp().observe(this, show -> {
            if (show == 1) {
                Intent help = new Intent(context, PdfReaderActivity.class);
                startActivity(help);
            }
        });

        viewmodel.getShowPld().observe(this, show -> {
            if (show == 1) {
                dialog_mailbox complaint = new dialog_mailbox();
                complaint.show(getSupportFragmentManager(), NameFragments.DIALOGMAILBOX);
            }

        });

        viewmodel.getShowCovidInfo().observe(this, show -> {
            if (show == 1) {
                Intent covid = new Intent(context, ComunicadoCovid.class);
                startActivity(covid);
            }
        });

        viewmodel.getUsername().observe(this, value -> {
            boolean validationUsername = (value != null) && (!value.trim().isEmpty());
            viewmodel.getUsernameValid().setValue(validationUsername);
        });

        viewmodel.getPassword().observe(this, value -> {
            Boolean flag = viewmodel.getCheckPassword().getValue();
            if (flag != null && flag) {
                boolean validationPassword = (value != null) && (!value.trim().isEmpty());
                viewmodel.getPasswordValid().setValue(validationPassword);
            }
        });

        viewmodel.getUsernameValid().observe(this, value -> {
            Boolean passwordValid = viewmodel.getPasswordValid().getValue();
            boolean _passwordValid = passwordValid != null && passwordValid;
            viewmodel.getLoginButtonEnable().setValue(value && _passwordValid);
        });

        viewmodel.getPasswordValid().observe(this, value -> {
            Boolean usernameValid = viewmodel.getUsernameValid().getValue();
            boolean _usernameValid = usernameValid != null && usernameValid;
            viewmodel.getLoginButtonEnable().setValue(value && _usernameValid);
        });

        databinding.etUser.setOnEditorActionListener((textView, actionId, event) -> {
            InputMethodManager inputManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            inputManager.hideSoftInputFromWindow(textView.getWindowToken(), 0);
            return true;
        });
    }

    /**
     * Alerta para habilitar el GPS desde configuraciones
     */
    private void alertNoGps() {
        Context context = this.getApplicationContext();
        Toast.makeText(context, "El sistema de GPS se encuentra desactivado, favor de ACTIVARLO!!!", Toast.LENGTH_LONG).show();
        ActivityResultLauncher<Intent> lunchActivity = this.registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    if (result.getResultCode() == Activity.RESULT_CANCELED) {
                        alertNoGps();
                    }
                }
        );
        lunchActivity.launch(new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS));
    }

}
