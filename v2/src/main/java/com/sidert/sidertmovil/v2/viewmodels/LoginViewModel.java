package com.sidert.sidertmovil.v2.viewmodels;

import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.os.Handler;
import android.os.Looper;
import android.text.InputType;
import android.view.View;
import android.view.Window;
import android.widget.Toast;

import com.sidert.sidertmovil.R;
import com.sidert.sidertmovil.utils.Constants;
import com.sidert.sidertmovil.utils.Popups;
import com.sidert.sidertmovil.v2.bussinesmodel.LoginBussinesModel;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import javax.inject.Inject;
import javax.inject.Named;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDialog;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class LoginViewModel
        extends ViewModel {

    private final MutableLiveData<String> username = new MutableLiveData<>();
    private final MutableLiveData<String> password = new MutableLiveData<>();
    private final MutableLiveData<Boolean> showPasswordField = new MutableLiveData<>(false);
    private final MutableLiveData<Integer> inputTypeForPassword = new MutableLiveData<>(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
    private final MutableLiveData<Boolean> successLogin = new MutableLiveData<>(false);
    private final MutableLiveData<Integer> showHelp = new MutableLiveData<>();
    private final MutableLiveData<Integer> showPld = new MutableLiveData<>();
    private final MutableLiveData<Integer> showCovidInfo = new MutableLiveData<>();
    private final MutableLiveData<Boolean> usernameValid = new MutableLiveData<>(false);
    private final MutableLiveData<Boolean> passwordValid = new MutableLiveData<>(true);
    private final MutableLiveData<Boolean> checkPassword = new MutableLiveData<>(false);
    private final MutableLiveData<Boolean> loginButtonEnable = new MutableLiveData<>(false);
    private final AtomicInteger counter;
    private final LoginBussinesModel loginBussinesModel;
    private Toast toast;

    @Inject
    public LoginViewModel(LoginBussinesModel loginBussinesModel, @Named("defaultAsesor") String defaultAsesor) {
        this.counter = new AtomicInteger(0);
        this.loginBussinesModel = loginBussinesModel;
        this.setUsernameValue(defaultAsesor);
    }

    private void setUsernameValue(String defaultUsername) {
        String lastUsernameInSession = loginBussinesModel.getLastUsernameInSession();
        lastUsernameInSession = lastUsernameInSession.trim();
        if (!lastUsernameInSession.isEmpty()) {
            this.username.setValue(lastUsernameInSession);
        } else {
            this.username.setValue(defaultUsername);
        }
    }

    public void updateCounter(View view) {
        int countClick = counter.incrementAndGet();
        Context context = view.getContext();

        if (toast != null) {
            toast.cancel();
        }

        if (countClick == 7) {
            toast = Toast.makeText(context, "Estas a 3 pasos de visualizar la contraseña", Toast.LENGTH_SHORT);
        }
        if (countClick == 8) {
            toast = Toast.makeText(context, "Estas a 2 pasos de visualizar la contraseña", Toast.LENGTH_SHORT);
        }
        if (countClick == 9) {
            toast = Toast.makeText(context, "Estas a 1 pasos de visualizar la contraseña", Toast.LENGTH_SHORT);
        }
        if (countClick == 10) {
            toast = Toast.makeText(context, "Liberando campo de contraseña", Toast.LENGTH_SHORT);
            showPasswordField.setValue(true);
            checkPassword.setValue(true);
            password.setValue(loginBussinesModel.getMacAddress());
        }
        if (countClick == 13) {
            toast = Toast.makeText(context, "Mostrar contraseña", Toast.LENGTH_SHORT);
            inputTypeForPassword.setValue(InputType.TYPE_CLASS_TEXT);
            this.loginBussinesModel.copyMacToClipboard();
        }

        if (countClick == 25) {
            toast = Toast.makeText(context, "Prueba de impresion", Toast.LENGTH_SHORT);
            Thread thread = new Thread(this.loginBussinesModel::doPrint);
            thread.start();
        }

        if (toast != null) {
            toast.show();
            toast = null;
        }
    }

    public void loginToTheApp(View view) {
        this.loginButtonEnable.setValue(true);
        String username = this.getUsernameValue().trim();
        String password = this.getPasswordValue();

        /*Valida que si el usuario es diferente a alguno de los siguientes */
        String[] myUsername = {"ASESOR", "GESTOR", "PROGRAMADORAND", "PROGRAMADOR02", "PROGRAMADORRUFI", "ANALISTAFUNCIONAL"};
        List<String> myUserNameLists = Arrays.asList(myUsername);

        Context context = view.getContext();
        /*Ventana de loading*/
        if (myUserNameLists.contains(username)) {
            this.loginBussinesModel.doLogin(context, username, password, code -> this.loginReferenceByContact(context, code));
        } else {
            this.loginBussinesModel.doLogin(context, username, "", code -> this.loginReferenceByContact(context, code));
        }
    }

    public void requestPermissionsForApplication(AppCompatActivity activity) {
        loginBussinesModel.requestPermissionsForApplication(activity);
    }

    private void loginReferenceByContact(Context context, int code) {
        switch (code) {
            case 5:
                /*Cuando no tenga conexion a internet*/
                showErrorAlert(context, R.string.not_network, Constants.not_network);
                break;
            case 200:
                successLogin.setValue(Boolean.TRUE);
                break;
            case 404:
                /*En caso de que se quiere iniciar con otro usuario no le va permitir o no tenga registrado en DB
                 * mac address y la constraseña*/
                showErrorAlert(context, R.string.error_mac_address);
                break;
            case 400:
                /*En caso de sean incorrectas las contraseñas*/
                showErrorAlert(context, R.string.credenciales_incorrectas);
                break;
            default:
                /*Cuando el servicio no este disponibles que este apagado el servidor o
                 * no tenga los suficientes datos para realizar la peticion*/
                showErrorAlert(context, R.string.servicio_no_disponible);
                break;
        }
    }

    private void showErrorAlert(Context context, int message) {
        showErrorAlert(context, message, Constants.login);
    }

    private void showErrorAlert(Context context, int message, String icon) {

        Handler handler = new Handler(Looper.getMainLooper());
        handler.post(() -> {
            final AlertDialog alertDialog = Popups.showDialogMessage(context, icon, message, R.string.accept, AppCompatDialog::dismiss);
            Window window = alertDialog.getWindow();
            if (window != null) {
                window.requestFeature(Window.FEATURE_NO_TITLE);
                window.setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
                alertDialog.show();
            }
        });

    }

    @Override
    protected void onCleared() {
        super.onCleared();
    }

    public void cleanData() {
        showPasswordField.setValue(Boolean.FALSE);
        inputTypeForPassword.setValue(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
        successLogin.setValue(false);
        showHelp.setValue(0);
        showPld.setValue(0);
        showCovidInfo.setValue(0);
        usernameValid.setValue(false);
        passwordValid.setValue(true);
        checkPassword.setValue(false);
        loginButtonEnable.setValue(false);
        counter.set(0);
    }


    public void showHelp(View ignoredView) {
        this.showHelp.setValue(1);
    }

    public void showDenunciaPld(View ignoredView) {
        this.showPld.setValue(1);
    }

    public void showCovidNotify(View ignoredView) {
        this.showCovidInfo.setValue(1);
    }

    public MutableLiveData<String> getUsername() {
        return username;
    }

    public String getUsernameValue() {
        return username.getValue();
    }

    public MutableLiveData<String> getPassword() {
        return password;
    }

    public String getPasswordValue() {
        return password.getValue();
    }

    public MutableLiveData<Integer> getInputTypeForPassword() {
        return inputTypeForPassword;
    }

    public MutableLiveData<Boolean> getShowPasswordField() {
        return showPasswordField;
    }

    public MutableLiveData<Boolean> getSuccessLogin() {
        return successLogin;
    }

    public MutableLiveData<Integer> getShowHelp() {
        return showHelp;
    }

    public MutableLiveData<Integer> getShowPld() {
        return showPld;
    }

    public MutableLiveData<Integer> getShowCovidInfo() {
        return showCovidInfo;
    }

    public MutableLiveData<Boolean> getUsernameValid() {
        return usernameValid;
    }

    public MutableLiveData<Boolean> getPasswordValid() {
        return passwordValid;
    }

    public MutableLiveData<Boolean> getCheckPassword() {
        return checkPassword;
    }

    public MutableLiveData<Boolean> getLoginButtonEnable() {
        return loginButtonEnable;
    }
}
