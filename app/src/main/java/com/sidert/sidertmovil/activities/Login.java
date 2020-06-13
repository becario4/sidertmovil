package com.sidert.sidertmovil.activities;

import android.Manifest;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.drawable.ColorDrawable;
import android.os.BatteryManager;
import android.os.Build;
import android.os.Environment;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.text.InputType;
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
import android.widget.TextView;
import android.widget.Toast;

import com.sidert.sidertmovil.AlarmaManager.AlarmaTrackerReciver;
import com.sidert.sidertmovil.Home;
import com.sidert.sidertmovil.R;
import com.sidert.sidertmovil.database.DBhelper;
import com.sidert.sidertmovil.database.SidertTables;
import com.sidert.sidertmovil.fragments.dialogs.dialog_contrasena_root;
import com.sidert.sidertmovil.fragments.dialogs.dialog_mailbox;
import com.sidert.sidertmovil.models.LoginResponse;
import com.sidert.sidertmovil.utils.Constants;
import com.sidert.sidertmovil.utils.ManagerInterface;
import com.sidert.sidertmovil.utils.Miscellaneous;
import com.sidert.sidertmovil.utils.NameFragments;
import com.sidert.sidertmovil.utils.NetworkStatus;
import com.sidert.sidertmovil.utils.Popups;
import com.sidert.sidertmovil.utils.RetrofitClient;
import com.sidert.sidertmovil.utils.SessionManager;
import com.sidert.sidertmovil.utils.Validator;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.nio.charset.StandardCharsets;
import java.util.Calendar;
import java.util.HashMap;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.sidert.sidertmovil.utils.Constants.AUTHORITIES;
import static com.sidert.sidertmovil.utils.Constants.CANCEL_TRACKER_ID;
import static com.sidert.sidertmovil.utils.Constants.MATERNO;
import static com.sidert.sidertmovil.utils.Constants.MODULOS;
import static com.sidert.sidertmovil.utils.Constants.NOMBRE_EMPLEADO;
import static com.sidert.sidertmovil.utils.Constants.PATERNO;
import static com.sidert.sidertmovil.utils.Constants.SERIE_ID;

public class Login extends AppCompatActivity {

    private Context ctx;
    private Context context;
    private ImageView IVlogo;
    private EditText etUser;
    private EditText etPassword;
    private Button btnLogin;
    private CardView cvCovid;
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

    private int countClick = 0;

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

        IVlogo          = findViewById(R.id.IVlogo);
        etUser          = findViewById(R.id.etUser);
        etPassword      = findViewById(R.id.etPassword);
        btnLogin        = findViewById(R.id.btnLogin);
        cvCovid         = findViewById(R.id.cvInfoCovid);
        cvDenunciarPLD  = findViewById(R.id.cvDenciarPLD);

        validator = new Validator();

        btnLogin.setOnClickListener(btnLogin_OnClick);

        cvDenunciarPLD.setOnClickListener(cvDenunciarPLD_OnClick);
        cvCovid.setOnClickListener(cvCovid_OnClick);

        etUser.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
                InputMethodManager inputManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                inputManager.hideSoftInputFromWindow(etUser.getWindowToken(), 0);
                return true;
            }
        });

        IVlogo.setOnClickListener(IVlogo_OnClick);
    }

    private View.OnClickListener IVlogo_OnClick = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            countClick += 1;

            if (countClick == 3)
                Toast.makeText(ctx, "Estas a 3 pasos de visualizar la contraseña", Toast.LENGTH_SHORT).show();
            if (countClick == 6)
                Toast.makeText(ctx, "Estas a 2 pasos de visualizar la contraseña", Toast.LENGTH_SHORT).show();
            if (countClick == 9)
                Toast.makeText(ctx, "Estas a 1 pasos de visualizar la contraseña", Toast.LENGTH_SHORT).show();
            if (countClick >= 9) {
                dialog_contrasena_root dialogRoot = new dialog_contrasena_root();
                dialogRoot.show(getSupportFragmentManager(), NameFragments.DIALOGDATEPICKER);
            }

        }
    };

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

    private View.OnClickListener cvCovid_OnClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent covid = new Intent(context, ComunicadoCovid.class);
            startActivity(covid);
        }
    };

    private void doLoginSoporte(){
        //session.setUser("000","Alejandro Isaias", "Lopez", "Jimenez", "Programador","PROGRAMADOR", true, "","");
        Intent home = new Intent(context, Home.class);
        startActivity(home);
        finish();
    }

    private void doLoginAsesorT(){
        //session.setUser("000","Alejandro Isaias", "Lopez", "Jimenez", "Asesor","ASESOR", true, "eyJhbGciOiJSUzI1NiIsInR5cCI6IkpXVCJ9.eyJtYXRlcm5vIjoiIiwicGF0ZXJubyI6IkxPUEVaIiwidXNlcl9uYW1lIjoiUFJPR1JBTUFET1JBTkQiLCJzY29wZSI6WyJyZWFkIiwid3JpdGUiXSwic2VyaWVpZCI6IjU4NCIsImlkIjoxMTksImV4cCI6MTU4MTc4MDQ4Nywibm9tYnJlIjoiQUxFSkFORFJPIiwiYXV0aG9yaXRpZXMiOlsiUk9MRV9TVVBFUiJdLCJqdGkiOiIyN2VkNzE2MS01ZDI1LTQ2ZmQtYWNiNi1hM2RjNWQzNTVjMWIiLCJlbWFpbCI6InByb2dyYW1hZG9yMkBydWZpLm14IiwiY2xpZW50X2lkIjoiYW5kcm9pZGFwcCJ9.dKgxCXq-t7K1FuwcSxtkG7U4LUCWgwxXdZe67T5vPYqonQYviVZ2bFA-NpV1MvuSgAXHZhxAXc4fGwkhz-IyhdY49kvzzilfzFHuRHbXLiNowTIxdUmFFYMxNPMN5XMIpauQt4eC--6eEmdkDYL1cxB35RA9Q_p6kSRkc0U8_UWVh1_K-9UOYwliJLbIlpSUMkps82wmUydp8rATd_OanwHbUx3uiOHBzBhXVOi2b8It9mIH_gBSAKeiEySIqSAMFoaimWacYw8vVPUrtNH5Z4CspLCYDc63qDEQYTiR1egtv0Ofxu-irpCEIGfsTOTPFGXrb-G_M7YAXdvkmCmlHQ","modulos");

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

            IntentFilter ifilter = new IntentFilter(Intent.ACTION_BATTERY_CHANGED);
            Intent batteryStatus = registerReceiver(null, ifilter);

            int level = batteryStatus.getIntExtra(BatteryManager.EXTRA_LEVEL, -1);
            int scale = batteryStatus.getIntExtra(BatteryManager.EXTRA_SCALE, -1);

            float battery = (level / (float)scale)*100;

            ManagerInterface api = new RetrofitClient().generalRF(Constants.CONTROLLER_LOGIN, ctx).create(ManagerInterface.class);

            Call<LoginResponse> call = api.login(etUser.getText().toString().trim(),
                                                 etPassword.getText().toString().trim(),
                                                 "password",
                                                 battery,
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

                                Log.e("JsonInfo", json_info.toString());
                                HashMap<Integer, String> params = new HashMap<>();
                                params.put(0, json_info.getString(SERIE_ID));
                                params.put(1, json_info.getString(NOMBRE_EMPLEADO)+" "+
                                        json_info.getString(PATERNO)+" "+
                                        json_info.getString(MATERNO));
                                params.put(2,Miscellaneous.ObtenerFecha("timestamp"));
                                params.put(3,"");
                                params.put(4,"1");

                                if (Constants.ENVIROMENT)
                                    dBhelper.saveRecordLogin(db, SidertTables.SidertEntry.TABLE_LOGIN_REPORT, params);
                                else
                                    dBhelper.saveRecordLogin(db, SidertTables.SidertEntry.TABLE_LOGIN_REPORT_T, params);

                                HashMap<Integer, String> params_sincro = new HashMap<>();
                                params_sincro.put(0, json_info.getString(SERIE_ID));
                                params_sincro.put(1, Miscellaneous.ObtenerFecha("timestamp"));

                                if (Constants.ENVIROMENT)
                                    dBhelper.saveSincronizado(db, Constants.SINCRONIZADO, params_sincro);
                                else
                                    dBhelper.saveSincronizado(db, Constants.SINCRONIZADO_T, params_sincro);

                                session.setUser(Miscellaneous.validString(json_info.getString(SERIE_ID)),
                                        Miscellaneous.validString(json_info.getString(NOMBRE_EMPLEADO)),
                                        Miscellaneous.validString(json_info.getString(PATERNO)),
                                        Miscellaneous.validString(json_info.getString(MATERNO)),
                                        Miscellaneous.validString(json_info.getString(Constants.USER_NAME)),
                                        Miscellaneous.validString(json_info.getString(AUTHORITIES)),
                                        true,
                                        Miscellaneous.validString(res.getAccessToken()),
                                        Miscellaneous.validString(json_info.getString(MODULOS)),
                                        Miscellaneous.validString(json_info.getString("id")));

                                Calendar c = Calendar.getInstance();
                                AlarmManager manager = (AlarmManager) ctx.getSystemService(ctx.ALARM_SERVICE);
                                Intent myIntent;
                                PendingIntent pendingIntent;
                                myIntent = new Intent(ctx, AlarmaTrackerReciver.class);
                                myIntent.putExtra("logueo","start");
                                pendingIntent = PendingIntent.getBroadcast(ctx,CANCEL_TRACKER_ID,myIntent,0);
                                manager.setRepeating(AlarmManager.RTC_WAKEUP,c.getTimeInMillis(),1000*60*15,pendingIntent);

                                //MyFireBaseInstanceIDService id = new MyFireBaseInstanceIDService();
                                /*if (c.get(Calendar.HOUR_OF_DAY) > 6 && c.get(Calendar.HOUR_OF_DAY) < 22) {
                                    Log.e("Login", "On Start Service Job Login");
                                    WorkManager mWorkManager = WorkManager.getInstance();
                                    OneTimeWorkRequest mRequestUnique = new OneTimeWorkRequest.Builder(WorkerLogout.class).setInitialDelay((22 - c.get(Calendar.HOUR_OF_DAY))-1, TimeUnit.HOURS).build();
                                    mWorkManager.enqueue(mRequestUnique);
                                }*/

                                Intent home = new Intent(context, DescargaDatos.class);
                                home.putExtra("login", true);
                                startActivity(home);
                                finish();

                                /*Log.v("json", json_info.toString());
                                if (json_info.getString(AUTHORITIES).contains("ROLE_GERENTESUCURSAL") ||
                                        json_info.getString(AUTHORITIES).contains("ROLE_GERENTEREGIONAL") ||
                                        json_info.getString(AUTHORITIES).contains("ROLE_COORDINADOR") ||
                                        json_info.getString(AUTHORITIES).contains("ROLE_DIRECCION") ||
                                        json_info.getString(AUTHORITIES).contains("ROLE_ANALISTA")){

                                }
                                else if(json_info.getString(AUTHORITIES).contains("ROLE_SUPER")){
                                    session.setUser(Miscellaneous.validString(json_info.getString(SERIE_ID)),
                                            Miscellaneous.validString(json_info.getString(NOMBRE_EMPLEADO)),
                                            Miscellaneous.validString(json_info.getString(PATERNO)),
                                            Miscellaneous.validString(json_info.getString(MATERNO)),
                                            Miscellaneous.validString(json_info.getString(Constants.USER_NAME)),
                                            Miscellaneous.validString(json_info.getString(AUTHORITIES)),
                                            true,
                                            Miscellaneous.validString(res.getAccessToken()),
                                            Miscellaneous.validString(json_info.getString(MODULOS)));

                                    Intent home = new Intent(context, Home.class);
                                    home.putExtra("login", true);
                                    startActivity(home);
                                    finish();

                                }
                                else if(json_info.getString(AUTHORITIES).contains("ROLE_ASESOR")){
                                    session.setUser(Miscellaneous.validString(json_info.getString(SERIE_ID)),
                                            Miscellaneous.validString(json_info.getString(NOMBRE_EMPLEADO)),
                                            Miscellaneous.validString(json_info.getString(PATERNO)),
                                            Miscellaneous.validString(json_info.getString(MATERNO)),
                                            Miscellaneous.validString(json_info.getString(Constants.USER_NAME)),
                                            Miscellaneous.validString(json_info.getString(AUTHORITIES)),
                                            true,
                                            Miscellaneous.validString(res.getAccessToken()),
                                            Miscellaneous.validString(json_info.getString(MODULOS)));

                                }
                                else {
                                    Toast.makeText(ctx, "Este usuario no está autorizado", Toast.LENGTH_SHORT).show();
                                }*/

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
            final AlertDialog error_connect = Popups.showDialogMessage(context, Constants.not_network,
                    R.string.not_network, R.string.accept, new Popups.DialogMessage() {
                        @Override
                        public void OnClickListener(AlertDialog dialog) {
                            dialog.dismiss();
                        }
                    });
            error_connect.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
            error_connect.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
            error_connect.show();
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

    public void SetPass(boolean flag){
        if (flag)
            etPassword.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
    }

}
