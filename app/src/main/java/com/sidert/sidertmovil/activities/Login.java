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
import com.sidert.sidertmovil.R;
import com.sidert.sidertmovil.database.DBhelper;
import com.sidert.sidertmovil.database.SidertTables;
import com.sidert.sidertmovil.fragments.dialogs.dialog_contrasena_root;
import com.sidert.sidertmovil.fragments.dialogs.dialog_mailbox;
import com.sidert.sidertmovil.models.LoginResponse;
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

import org.json.JSONArray;
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
import static com.sidert.sidertmovil.utils.Constants.MAC_ADDRESSES;
import static com.sidert.sidertmovil.utils.Constants.MATERNO;
import static com.sidert.sidertmovil.utils.Constants.MODULOS;
import static com.sidert.sidertmovil.utils.Constants.NOMBRE_EMPLEADO;
import static com.sidert.sidertmovil.utils.Constants.PATERNO;
import static com.sidert.sidertmovil.utils.Constants.SERIE_ID;
import static com.sidert.sidertmovil.utils.Constants.SUCURSALES;

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
                etPassword.setVisibility(View.VISIBLE);
                dialog_contrasena_root dialogRoot = new dialog_contrasena_root();
                dialogRoot.show(getSupportFragmentManager(), NameFragments.DIALOGDATEPICKER);
            }

        }
    };

    private View.OnClickListener btnLogin_OnClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if(!validator.validate(etUser, new String[] {validator.REQUIRED})) {
                if (!etUser.getText().toString().trim().equals("ASESOR") &&
                !etUser.getText().toString().trim().equals("GESTOR") &&
                !etUser.getText().toString().trim().equals("PROGRAMADORAND") &&
                !etUser.getText().toString().trim().equals("PROGRAMADOR02") &&
                !etUser.getText().toString().trim().equals("PROGRAMADORRUFI") &&
                !etUser.getText().toString().trim().equals("ANALISTAFUNCIONAL"))
                    doLogin();
                else{
                    if (!validator.validate(etPassword, new String[]{validator.REQUIRED})) {
                        doLogin();
                    }
                    else{
                        Toast.makeText(ctx, "Ingrese la contraseña", Toast.LENGTH_SHORT).show();
                    }
                }
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
                                                 session.getMacAddress(),
                                                 etPassword.getText().toString().trim(),
                                                 Miscellaneous.authorization("androidapp", "m1*cR0w4V3-s"));
            /*Call<LoginResponse> call = api.login(etUser.getText().toString().trim(),
                    "password",
                    etPassword.getText().toString().trim(),
                    Miscellaneous.authorization("androidapp", "m1*cR0w4V3-s"));*/
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

                                /*boolean isLogin = false;
                                Log.e("MAC_ADDRESSES", String.valueOf(json_info.has(MAC_ADDRESSES)));
                                if (json_info.has(MAC_ADDRESSES)){
                                    JSONArray macAddresses = json_info.getJSONArray(MAC_ADDRESSES);
                                    for(int i = 0 ; i < macAddresses.length(); i++){
                                        JSONObject item = macAddresses.getJSONObject(i);
                                        if (session.getMacAddress().equals(item.getString("mac_address"))){
                                            isLogin = true;
                                            break;
                                        }
                                    }
                                }*/

                                /*if (true || json_info.getInt("id") == 134 || json_info.getInt("id") == 119 ||
                                json_info.getInt("id") == 1 || json_info.getInt("id") == 123 ||
                                json_info.getInt("id") == 135 || json_info.getInt("id") == 157 ||
                                json_info.getInt("id") == 200 || json_info.getInt("id") == 368) {*/
                                    Log.e("JsonInfo", json_info.toString());
                                    HashMap<Integer, String> params = new HashMap<>();
                                    params.put(0, json_info.getString(SERIE_ID));
                                    params.put(1, json_info.getString(NOMBRE_EMPLEADO) + " " +
                                            json_info.getString(PATERNO) + " " +
                                            json_info.getString(MATERNO));
                                    params.put(2, Miscellaneous.ObtenerFecha("timestamp"));
                                    params.put(3, "");
                                    params.put(4, "1");

                                    dBhelper.saveRecordLogin(db, SidertTables.SidertEntry.TABLE_LOGIN_REPORT_T, params);

                                    HashMap<Integer, String> params_sincro = new HashMap<>();
                                    params_sincro.put(0, json_info.getString(SERIE_ID));
                                    params_sincro.put(1, Miscellaneous.ObtenerFecha("timestamp"));


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

                                    //session.setSucursales(json_info.getString(SUCURSALES));

                                    Calendar c = Calendar.getInstance();
                                    AlarmManager manager = (AlarmManager) ctx.getSystemService(ctx.ALARM_SERVICE);
                                    Intent myIntent;
                                    PendingIntent pendingIntent;
                                    myIntent = new Intent(ctx, AlarmaTrackerReciver.class);
                                    myIntent.putExtra("logueo", "start");
                                    pendingIntent = PendingIntent.getBroadcast(ctx, CANCEL_TRACKER_ID, myIntent, 0);
                                    manager.setRepeating(AlarmManager.RTC_WAKEUP, c.getTimeInMillis(), 1000 * 60 * 15, pendingIntent);

                                    new MyFireBaseInstanceIDService(ctx);

                                    Intent home = new Intent(context, DescargaDatos.class);
                                    home.putExtra("login", true);
                                    startActivity(home);
                                    finish();
                                /*}
                                else{
                                    dialog_mess = Popups.showDialogMessage(context, Constants.login,
                                            R.string.error_mac_address, R.string.accept, new Popups.DialogMessage() {
                                                @Override

                                                public void OnClickListener(AlertDialog dialog) {
                                                    dialog.dismiss();
                                                }
                                            });
                                    dialog_mess.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
                                    dialog_mess.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
                                    dialog_mess.show();
                                }*/

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }

                            break;
                        case 404:
                            dialog_mess = Popups.showDialogMessage(context, Constants.login,
                                    R.string.error_mac_address, R.string.accept, new Popups.DialogMessage() {
                                        @Override

                                        public void OnClickListener(AlertDialog dialog) {
                                            dialog.dismiss();
                                        }
                                    });
                            dialog_mess.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
                            dialog_mess.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
                            dialog_mess.show();
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
                    Log.e("error", "fail: "+t.getMessage());
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
