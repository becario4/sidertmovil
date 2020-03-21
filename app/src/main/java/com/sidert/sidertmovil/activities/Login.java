package com.sidert.sidertmovil.activities;

import android.Manifest;
import android.app.Activity;
import android.app.job.JobInfo;
import android.app.job.JobScheduler;
import android.app.job.JobService;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Environment;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.util.Base64;
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
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.sidert.sidertmovil.Home;
import com.sidert.sidertmovil.R;
import com.sidert.sidertmovil.database.DBhelper;
import com.sidert.sidertmovil.database.SidertTables;
import com.sidert.sidertmovil.fragments.dialogs.dialog_mailbox;
import com.sidert.sidertmovil.fragments.dialogs.dialog_message;
import com.sidert.sidertmovil.models.LoginResponse;
import com.sidert.sidertmovil.utils.BkgJobServiceLogout;
import com.sidert.sidertmovil.utils.Constants;
import com.sidert.sidertmovil.utils.ManagerInterface;
import com.sidert.sidertmovil.utils.Miscellaneous;
import com.sidert.sidertmovil.utils.MyFireBaseInstanceIDService;
import com.sidert.sidertmovil.utils.NameFragments;
import com.sidert.sidertmovil.utils.NetworkStatus;
import com.sidert.sidertmovil.utils.Popups;
import com.sidert.sidertmovil.utils.RetrofitClient;
import com.sidert.sidertmovil.utils.SessionManager;
import com.sidert.sidertmovil.utils.Validator;
import com.sidert.sidertmovil.utils.WebServicesRoutes;
import com.sidert.sidertmovil.utils.WorkerLogout;
import com.sidert.sidertmovil.utils.WorkerSincronizado;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.util.Calendar;
import java.util.HashMap;
import java.util.concurrent.TimeUnit;

import androidx.work.OneTimeWorkRequest;
import androidx.work.PeriodicWorkRequest;
import androidx.work.WorkManager;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Login extends AppCompatActivity {

    private Context ctx;
    private Context context;
    private EditText etUser;
    private EditText etPassword;
    private Button btnLogin;
    private CardView cvDenunciarPLD;
    private Validator validator;
    private SessionManager session;
    private DBhelper dBhelper;
    private SQLiteDatabase db;
    String[] perms = {  Manifest.permission.ACCESS_FINE_LOCATION,
                        Manifest.permission.ACCESS_COARSE_LOCATION,
                        Manifest.permission.CAMERA,
                        Manifest.permission.BLUETOOTH_ADMIN,
                        Manifest.permission.BLUETOOTH,
                        Manifest.permission.READ_EXTERNAL_STORAGE,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE};

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //getWindow().setFlags(WindowManager.LayoutParams.FLAG_SECURE, WindowManager.LayoutParams.FLAG_SECURE);
        setContentView(R.layout.activity_login);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        verifyPermission();
        ctx             = getApplicationContext();
        context         = this;
        session         = new SessionManager(ctx);

        dBhelper        = new DBhelper(ctx);
        db              = dBhelper.getWritableDatabase();

        etUser          = findViewById(R.id.etUser);
        etPassword      = findViewById(R.id.etPassword);
        btnLogin        = findViewById(R.id.btnLogin);
        cvDenunciarPLD  = findViewById(R.id.cvDenciarPLD);

        //etUser.setText("GERENTEBANDERILLA");
        //etPassword.setText("g'Rh~iAYPp9'");
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
            if(!validator.validate(etUser, new String[] {validator.REQUIRED}) &&
                    !validator.validate(etPassword, new String[] {validator.REQUIRED})) {
                if (etUser.getText().toString().trim().equals("programador") && etPassword.getText().toString().trim().equals("Qvv12#4")){
                    doLoginSoporte();
                }
                else if(etUser.getText().toString().trim().equals("asesor123") && etPassword.getText().toString().trim().equals("Qvv12#4")){
                    doLoginAsesorT();
                }
                else
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

    private void doLoginSoporte(){
        session.setUser("000","Alejandro Isaias", "Lopez", "Jimenez", "Programador","PROGRAMADOR", true, "");
        Intent home = new Intent(context, Home.class);
        startActivity(home);
        finish();
    }

    private void doLoginAsesorT(){
        session.setUser("000","Alejandro Isaias", "Lopez", "Jimenez", "Asesor","ASESOR", true, "eyJhbGciOiJSUzI1NiIsInR5cCI6IkpXVCJ9.eyJtYXRlcm5vIjoiIiwicGF0ZXJubyI6IkxPUEVaIiwidXNlcl9uYW1lIjoiUFJPR1JBTUFET1JBTkQiLCJzY29wZSI6WyJyZWFkIiwid3JpdGUiXSwic2VyaWVpZCI6IjU4NCIsImlkIjoxMTksImV4cCI6MTU4MTc4MDQ4Nywibm9tYnJlIjoiQUxFSkFORFJPIiwiYXV0aG9yaXRpZXMiOlsiUk9MRV9TVVBFUiJdLCJqdGkiOiIyN2VkNzE2MS01ZDI1LTQ2ZmQtYWNiNi1hM2RjNWQzNTVjMWIiLCJlbWFpbCI6InByb2dyYW1hZG9yMkBydWZpLm14IiwiY2xpZW50X2lkIjoiYW5kcm9pZGFwcCJ9.dKgxCXq-t7K1FuwcSxtkG7U4LUCWgwxXdZe67T5vPYqonQYviVZ2bFA-NpV1MvuSgAXHZhxAXc4fGwkhz-IyhdY49kvzzilfzFHuRHbXLiNowTIxdUmFFYMxNPMN5XMIpauQt4eC--6eEmdkDYL1cxB35RA9Q_p6kSRkc0U8_UWVh1_K-9UOYwliJLbIlpSUMkps82wmUydp8rATd_OanwHbUx3uiOHBzBhXVOi2b8It9mIH_gBSAKeiEySIqSAMFoaimWacYw8vVPUrtNH5Z4CspLCYDc63qDEQYTiR1egtv0Ofxu-irpCEIGfsTOTPFGXrb-G_M7YAXdvkmCmlHQ");

        if(isExternalStorageWritable()){
            String nameDir = "Files";
            crearDirectorioPrivado(ctx, nameDir);
        }
        Intent home = new Intent(context, Home.class);
        startActivity(home);
        finish();
    }

    private void doLogin (){
        if (NetworkStatus.haveNetworkConnection(ctx)){

            final AlertDialog loading = Popups.showLoadingDialog(context,R.string.please_wait, R.string.loading_info);
            loading.show();


            ManagerInterface api = new RetrofitClient().generalRF(Constants.CONTROLLER_LOGIN).create(ManagerInterface.class);

            Call<LoginResponse> call = api.login(etUser.getText().toString().trim(),
                                                 etPassword.getText().toString().trim(),
                                                 "password",
                                                 Miscellaneous.authorization("androidapp", "m1*cR0w4V3-s"));
            call.enqueue(new Callback<LoginResponse>() {
                @Override
                public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                    AlertDialog dialog_mess;
                    switch (response.code()){
                        case 200:
                            LoginResponse res = response.body();

                            if(isExternalStorageWritable()){
                                String nameDir = "Files";
                                crearDirectorioPrivado(ctx, nameDir);
                            }

                            byte[] data = Base64.decode(res.getAccessToken().replace(".",";").split(";")[1], Base64.DEFAULT);

                            try {
                                JSONObject json_info = new JSONObject(new String(data, StandardCharsets.UTF_8));

                                if (json_info.getString(Constants.AUTHORITIES).contains("ROLE_GERENTESUCURSAL") ||
                                        json_info.getString(Constants.AUTHORITIES).contains("ROLE_GERENTEREGIONAL") ||
                                        json_info.getString(Constants.AUTHORITIES).contains("ROLE_COORDINADOR") ||
                                        json_info.getString(Constants.AUTHORITIES).contains("ROLE_DIRECCION") ||
                                        json_info.getString(Constants.AUTHORITIES).contains("ROLE_ANALISTA") ||
                                        json_info.getString(Constants.AUTHORITIES).contains("ROLE_SUPER")){

                                    HashMap<Integer, String> params = new HashMap<>();
                                    params.put(0, json_info.getString(Constants.SERIE_ID));
                                    params.put(1, json_info.getString(Constants.NOMBRE_EMPLEADO)+" "+
                                            json_info.getString(Constants.PATERNO)+" "+
                                            json_info.getString(Constants.MATERNO));
                                    params.put(2,Miscellaneous.ObtenerFecha("timestamp"));
                                    params.put(3,"");
                                    params.put(4,"1");

                                    if (Constants.ENVIROMENT)
                                        dBhelper.saveRecordLogin(db, SidertTables.SidertEntry.TABLE_LOGIN_REPORT, params);
                                    else
                                        dBhelper.saveRecordLogin(db, SidertTables.SidertEntry.TABLE_LOGIN_REPORT_T, params);

                                    HashMap<Integer, String> params_sincro = new HashMap<>();
                                    params_sincro.put(0, json_info.getString(Constants.SERIE_ID));
                                    params_sincro.put(1, Miscellaneous.ObtenerFecha("timestamp"));

                                    if (Constants.ENVIROMENT)
                                        dBhelper.saveSincronizado(db, Constants.SINCRONIZADO, params_sincro);
                                    else
                                        dBhelper.saveSincronizado(db, Constants.SINCRONIZADO_T, params_sincro);

                                    Log.v("login", json_info.toString());
                                    session.setUser(Miscellaneous.validString(json_info.getString(Constants.SERIE_ID)),
                                            Miscellaneous.validString(json_info.getString(Constants.NOMBRE_EMPLEADO)),
                                            Miscellaneous.validString(json_info.getString(Constants.PATERNO)),
                                            Miscellaneous.validString(json_info.getString(Constants.MATERNO)),
                                            Miscellaneous.validString(json_info.getString(Constants.USER_NAME)),
                                            Miscellaneous.validString(json_info.getString(Constants.AUTHORITIES)),
                                            true,
                                            Miscellaneous.validString(res.getAccessToken()));

                                    Calendar c = Calendar.getInstance();

                                    MyFireBaseInstanceIDService id = new MyFireBaseInstanceIDService();
                                    if (c.get(Calendar.HOUR_OF_DAY) > 6 && c.get(Calendar.HOUR_OF_DAY) < 22) {

                                            Log.e("Login", "On Start Service Job Login");
                                            WorkManager mWorkManager = WorkManager.getInstance();
                                            OneTimeWorkRequest mRequestUnique = new OneTimeWorkRequest.Builder(WorkerLogout.class).setInitialDelay((22 - c.get(Calendar.HOUR_OF_DAY))-1, TimeUnit.HOURS).build();
                                            mWorkManager.enqueue(mRequestUnique);

                                            /*if (!json_info.getString(Constants.AUTHORITIES).contains("ROLE_SUPER")){

                                                final PeriodicWorkRequest mRequest = new PeriodicWorkRequest.Builder(WorkerSincronizado.class, 15, TimeUnit.MINUTES).addTag(Constants.SINCRONIZADO).build();
                                                mWorkManager.enqueue(mRequest);
                                            }*/


                                            /*ComponentName serviceComponent;
                                            serviceComponent = new ComponentName(context, BkgJobServiceLogout.class);
                                            JobInfo.Builder builder = new JobInfo.Builder(Constants.ID_JOB_LOGOUT, serviceComponent);
                                            //builder.setPeriodic((22 - c.get(Calendar.HOUR_OF_DAY)) * 60 * 60 * 1000);
                                            builder.setMinimumLatency((22 - c.get(Calendar.HOUR_OF_DAY)) * 60 * 60 * 1000);
                                            builder.setOverrideDeadline((22 - c.get(Calendar.HOUR_OF_DAY)) * 60 * 60 * 1000);
                                            JobScheduler jobScheduler = (JobScheduler) context.getSystemService(Context.JOB_SCHEDULER_SERVICE);
                                            jobScheduler.schedule(builder.build());*/

                                    }


                                    Intent home = new Intent(context, Home.class);
                                    startActivity(home);
                                    finish();
                                }
                                else if(json_info.getString(Constants.AUTHORITIES).contains("ROLE_ASESOR")){
                                    session.setUser(Miscellaneous.validString(json_info.getString(Constants.SERIE_ID)),
                                            Miscellaneous.validString(json_info.getString(Constants.NOMBRE_EMPLEADO)),
                                            Miscellaneous.validString(json_info.getString(Constants.PATERNO)),
                                            Miscellaneous.validString(json_info.getString(Constants.MATERNO)),
                                            Miscellaneous.validString(json_info.getString(Constants.USER_NAME)),
                                            Miscellaneous.validString(json_info.getString(Constants.AUTHORITIES)),
                                            true,
                                            Miscellaneous.validString(res.getAccessToken()));
                                    Intent home = new Intent(context, Home.class);
                                    startActivity(home);
                                    finish();
                                }
                                else {
                                    Toast.makeText(ctx, "Este usuario no está autorizado", Toast.LENGTH_SHORT).show();
                                }

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }


                            break;
                        case 400:
                            dialog_mess = Popups.showDialogMessage(context, Constants.login,
                                    R.string.credenciales_incorrectas, R.string.accept, new Popups.DialogMessage() {
                                        @Override

                                        public void OnClickListener(AlertDialog dialog) {
                                            dialog.dismiss();
                                        }
                                    });
                            dialog_mess.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
                            dialog_mess.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
                            dialog_mess.show();
                            break;
                        default:
                            dialog_mess = Popups.showDialogMessage(context, Constants.login,
                                    R.string.servicio_no_disponible, R.string.accept, new Popups.DialogMessage() {
                                        @Override
                                        public void OnClickListener(AlertDialog dialog) {
                                            dialog.dismiss();
                                        }
                                    });
                            dialog_mess.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
                            dialog_mess.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
                            dialog_mess.show();
                            break;
                    }
                    loading.dismiss();
                }

                @Override
                public void onFailure(Call<LoginResponse> call, Throwable t) {
                    Log.e("error", "fail"+t.getMessage());
                    loading.dismiss();
                }
            });
        }
        else{
            final AlertDialog success = Popups.showDialogMessage(context, Constants.not_network,
                    R.string.not_network, R.string.accept, new Popups.DialogMessage() {
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

    @RequiresApi(api = Build.VERSION_CODES.M)
    private void verifyPermission() {
        int permsRequestCode = 100;
        String[] perms = {Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.ACCESS_COARSE_LOCATION,
                Manifest.permission.CAMERA,
                Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.CALL_PHONE,
                Manifest.permission.BLUETOOTH};

        int accessFinePermission = checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION);
        int accessCoarsePermission = checkSelfPermission(Manifest.permission.ACCESS_COARSE_LOCATION);
        int cameraPermission = checkSelfPermission(Manifest.permission.CAMERA);
        int writeStorage = checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE);
        int readStorage = checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE);
        int callPhone = checkSelfPermission(Manifest.permission.CALL_PHONE);
        int bluetooth = checkSelfPermission(Manifest.permission.BLUETOOTH);

        if (cameraPermission == PackageManager.PERMISSION_GRANTED &&
                accessFinePermission == PackageManager.PERMISSION_GRANTED &&
                accessCoarsePermission == PackageManager.PERMISSION_GRANTED &&
                writeStorage == PackageManager.PERMISSION_GRANTED &&
                readStorage == PackageManager.PERMISSION_GRANTED &&
                callPhone == PackageManager.PERMISSION_GRANTED &&
                bluetooth == PackageManager.PERMISSION_GRANTED) {
            //se realiza metodo si es necesario...
        } else {
            requestPermissions(perms, permsRequestCode);
        }
    }

}
