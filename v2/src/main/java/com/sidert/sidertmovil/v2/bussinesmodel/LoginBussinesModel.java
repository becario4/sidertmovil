package com.sidert.sidertmovil.v2.bussinesmodel;

import android.Manifest;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.location.Location;
import android.location.LocationManager;
import android.os.Build;
import android.os.Environment;
import android.util.Base64;
import android.widget.Toast;

import com.sewoo.jpos.command.ESCPOS;
import com.sewoo.jpos.printer.ESCPOSPrinter;
import com.sewoo.jpos.printer.LKPrint;
import com.sewoo.port.android.BluetoothPort;
import com.sewoo.request.android.RequestHandler;
import com.sidert.sidertmovil.AlarmaManager.AlarmaTrackerReciver;
import com.sidert.sidertmovil.models.LoginResponse;
import com.sidert.sidertmovil.utils.Constants;
import com.sidert.sidertmovil.utils.Miscellaneous;
import com.sidert.sidertmovil.utils.NetworkStatus;
import com.sidert.sidertmovil.utils.SessionManager;
import com.sidert.sidertmovil.v2.R;
import com.sidert.sidertmovil.v2.SidertMovilApplication;
import com.sidert.sidertmovil.v2.domain.daos.LoginReportDao;
import com.sidert.sidertmovil.v2.domain.daos.SincronizadoDao;
import com.sidert.sidertmovil.v2.domain.entities.LoginReport;
import com.sidert.sidertmovil.v2.domain.entities.Sincronizado;
import com.sidert.sidertmovil.v2.remote.auth.LoginRemoteAuth;
import com.sidert.sidertmovil.v2.utils.ExecutorUtil;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeoutException;

import javax.inject.Inject;
import javax.inject.Named;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.internal.EverythingIsNonNull;
import timber.log.Timber;

import static android.content.Context.ALARM_SERVICE;
import static com.sidert.sidertmovil.utils.Constants.AUTHORITIES;
import static com.sidert.sidertmovil.utils.Constants.CANCEL_TRACKER_ID;
import static com.sidert.sidertmovil.utils.Constants.MATERNO;
import static com.sidert.sidertmovil.utils.Constants.MODULOS;
import static com.sidert.sidertmovil.utils.Constants.NOMBRE_EMPLEADO;
import static com.sidert.sidertmovil.utils.Constants.PATERNO;
import static com.sidert.sidertmovil.utils.Constants.SERIE_ID;
import static com.sidert.sidertmovil.utils.Constants.SUCURSALES;

public class LoginBussinesModel extends BaseBussinesModel {

    private static final String TAG = LoginBussinesModel.class.getSimpleName();
    private final LoginReportDao loginReportDao;
    private final SincronizadoDao sincronizacionDao;
    private final LoginRemoteAuth loginRemoteAuth;
    private final String androidRestAuth;
    private final String androidServiceUrl;
    private final BluetoothAdapter bluetoothAdapter;
    private final BluetoothPort bluetoothPort;
    private Thread btThread;

    @Inject
    public LoginBussinesModel(
            SidertMovilApplication sidertMovilApplication,
            ExecutorUtil executorUtil,
            SessionManager sessionManager,
            LoginReportDao loginReportDao,
            SincronizadoDao sincronizacionDao,
            @Named("androidRestAuth") String androidRestAuth,
            @Named("baseUrl") String androidServiceUrl,
            LoginRemoteAuth loginRemoteAuth,
            BluetoothAdapter bluetoothAdapter,
            BluetoothPort bluetoothPort
    ) {
        super(sidertMovilApplication, executorUtil, sessionManager);
        this.loginReportDao = loginReportDao;
        this.sincronizacionDao = sincronizacionDao;
        this.androidRestAuth = androidRestAuth;
        this.loginRemoteAuth = loginRemoteAuth;
        this.androidServiceUrl = androidServiceUrl;
        this.bluetoothAdapter = bluetoothAdapter;
        this.bluetoothPort = bluetoothPort;
    }

    public void doPrint() {
        bluetoothSetup();
        final char ESC = ESCPOS.ESC;
        final char LF = ESCPOS.LF;
        Timber.tag(TAG).i("Imprimir");
        if (bluetoothAdapter.isEnabled()) {
            try {
                Bitmap bm = BitmapFactory.decodeResource(sidertMovilApplication.getResources(), R.drawable.logo_impresion);
                ESCPOSPrinter escposPrinter = new ESCPOSPrinter();
                escposPrinter.printBitmap(bm, LKPrint.LK_ALIGNMENT_CENTER);
                escposPrinter.printNormal(ESC + "|cA" + ESC + "|bC" + ESC + "|2C" + "Receipt" + LF + LF);
                escposPrinter.printNormal(ESC + "|rATEL (123)-456-7890\n\n\n");
                escposPrinter.printNormal(ESC + "|cAThank you!!!\n");
                escposPrinter.printNormal("Spicy Chiken Nugget(6pz)  $10.00\n");
                escposPrinter.printNormal("Hamburger                 $20.00\n");
                escposPrinter.printNormal("Pizza                     $30.00\n");
                escposPrinter.printNormal("Lemons                    $40.00\n");
                escposPrinter.printNormal("Drink                     $50.00\n");
                escposPrinter.printNormal("Excluded tax             $150.00\n");
                escposPrinter.printNormal(ESC + "|uCTax(5%)                    $7.50\n");
                escposPrinter.printNormal(ESC + "|bC" + ESC + "|2CTotal    $157.50\n\n");
                escposPrinter.printNormal("Payment                  $200.00\n");
                escposPrinter.printNormal("Change                    $42.50\n\n");
                escposPrinter.printBarCode("{Babc456789012", LKPrint.LK_BCS_Code128, 40, 2, LKPrint.LK_ALIGNMENT_CENTER, LKPrint.LK_HRI_TEXT_BELOW); // Print Barcode
                escposPrinter.printNormal("" + LF + LF + LF);
            } catch (IOException e) {
                Timber.tag(TAG).e(e);
            }
        }
    }

    private void bluetoothSetup() {

        int version = Build.VERSION.SDK_INT;
        String blueetoothPermisson00;
        String blueetoothPermisson01;

        if (version <= 30) {
            blueetoothPermisson00 = Manifest.permission.BLUETOOTH;
            blueetoothPermisson01 = Manifest.permission.BLUETOOTH_ADMIN;
        } else {
            blueetoothPermisson00 = Manifest.permission.BLUETOOTH_SCAN;
            blueetoothPermisson01 = Manifest.permission.BLUETOOTH_CONNECT;
        }


        if ((ActivityCompat.checkSelfPermission(sidertMovilApplication, blueetoothPermisson00) != PackageManager.PERMISSION_GRANTED) &&
                (ActivityCompat.checkSelfPermission(sidertMovilApplication, blueetoothPermisson01) != PackageManager.PERMISSION_GRANTED)) {
            return;
        }

        BluetoothDevice bluetoothDevice = null;
        for (BluetoothDevice pairedDevice : bluetoothAdapter.getBondedDevices()) {
            if (bluetoothPort.isValidAddress(pairedDevice.getAddress())) {
                bluetoothDevice = pairedDevice;
                break;
            }
        }

        if (bluetoothDevice != null) {
            Timber.tag(TAG).i("Iniciando conexion de bluetooth");
            BroadcastReceiver connectDevice = new BroadcastReceiver() {
                @Override
                public void onReceive(Context context, Intent intent) {
                    String action = intent.getAction();

                    if (BluetoothDevice.ACTION_ACL_CONNECTED.equals(action)) {
                        Toast.makeText(sidertMovilApplication, "BlueTooth Connect", Toast.LENGTH_SHORT).show();
                    } else if (BluetoothDevice.ACTION_ACL_DISCONNECTED.equals(action)) {
                        try {
                            if (bluetoothPort.isConnected())
                                bluetoothPort.disconnect();
                        } catch (IOException | InterruptedException e) {
                            Timber.tag(TAG).e(e, "");
                        }
                        if ((btThread != null) && (btThread.isAlive())) {
                            btThread.interrupt();
                            btThread = null;
                        }
                        Toast.makeText(sidertMovilApplication, "BlueTooth Disconnect", Toast.LENGTH_SHORT).show();
                    }
                }
            };
            try {
                this.sidertMovilApplication.registerReceiver(connectDevice, new IntentFilter(BluetoothDevice.ACTION_ACL_CONNECTED));
                this.sidertMovilApplication.registerReceiver(connectDevice, new IntentFilter(BluetoothDevice.ACTION_ACL_DISCONNECTED));
                bluetoothPort.connect(bluetoothDevice);
                RequestHandler rh = new RequestHandler();
                btThread = new Thread(rh);
                btThread.start();
            } catch (IOException e) {
                Timber.tag(TAG).e(e, "Error with connection");
            }
        }
    }

    public String getLastUsernameInSession() {
        try {
            return this.executorUtil.runTaskInThread(loginReportDao::findOneOrderByCreateAtDesc)
                    .map(LoginReport::getUsername)
                    .orElse("");
        } catch (ExecutionException | InterruptedException | TimeoutException e) {
            return "";
        }
    }

    public void doLogin(Context context, String username, String password, Contract contract) {

        try {
            executorUtil.runTaskInThread(() -> {
                if (NetworkStatus.haveNetworkConnection(context)) {
                    String mac = sessionManager.getMacAddress();
                    loginRemoteAuth.loginToken(
                            username,
                            mac,
                            password.trim(),
                            androidRestAuth
                    ).enqueue(new Callback<LoginResponse>() {
                        @Override
                        public void onResponse(@EverythingIsNonNull Call<LoginResponse> call, @EverythingIsNonNull Response<LoginResponse> response) {
                            if (response.isSuccessful()) {
                                LoginResponse res = response.body();

                                if (isExternalStorageWritable()) {
                                    String nameDir = "Files";
                                    crearDirectorioPrivado(context, nameDir);
                                }

                                assert res != null;
                                String accessToken = Miscellaneous.validStr(res.getAccessToken());

                                byte[] data = Base64.decode(accessToken.replace(".", ";").split(";")[1], Base64.DEFAULT);

                                try {
                                    JSONObject payload = new JSONObject(new String(data, StandardCharsets.UTF_8));
                                    Timber.tag(TAG).i("JSON INFO: %s", payload.toString());

                                    String serieId = Miscellaneous.validStr(payload.getString(SERIE_ID));
                                    String nombre = Miscellaneous.validStr(payload.getString(NOMBRE_EMPLEADO));
                                    String paterno = payload.getString(PATERNO);
                                    String materno = payload.getString(MATERNO);
                                    String nombreCompleto = nombre + " " + paterno + " " + materno;

                                    String username = Miscellaneous.validStr(payload.getString(Constants.USER_NAME));
                                    String autorizaciones = Miscellaneous.validStr(payload.getString(AUTHORITIES));
                                    String modulos = Miscellaneous.validStr(payload.getString(MODULOS));
                                    String id = Miscellaneous.validStr(payload.getString("id"));
                                    String sucursales = payload.getString(SUCURSALES);

                                    /*Guarda un registro para el log de inicio de sesion*/
                                    LocalDateTime current = LocalDateTime.now();

                                    LoginReport loginReport = new LoginReport();
                                    loginReport.setUsername(username);
                                    loginReport.setSerieId(serieId);
                                    loginReport.setNombre(nombreCompleto);
                                    loginReport.setLoginTimestamp(DateTimeFormatter.ISO_DATE_TIME.format(current));
                                    loginReport.setFechaEnv("");
                                    loginReport.setStatus(1);
                                    executorUtil.runTaskInThread(() -> loginReportDao.insert(loginReport));

                                    Sincronizado sincronizacion = new Sincronizado();
                                    sincronizacion.setTimestamp(DateTimeFormatter.ISO_DATE_TIME.format(current));
                                    sincronizacion.setSerieId(payload.getString(SERIE_ID));
                                    executorUtil.runTaskInThread(() -> sincronizacionDao.insert(sincronizacion));

                                    sessionManager.setUser(
                                            serieId,            //SerieID
                                            nombre,             //Nombre
                                            paterno,            //Ap_paterno
                                            materno,            //Ap_materno
                                            username,           //Username
                                            autorizaciones,     //Autorizaciones
                                            true,               //Bandera para inicio de sesion
                                            accessToken,        //AccessToken
                                            modulos,            //Modulos Permitidos
                                            id                  //UserId que se ocupara para peticiones
                                    );

                                    sessionManager.setSucursales(sucursales);
                                    sessionManager.setAutorizacionAA(autorizaciones);
                                    sessionManager.getAutorizacionAAA();
                                    sessionManager.setDominio(androidServiceUrl);

                                    Timber.tag(TAG).i("SUCURSALES---------> %s", sessionManager.getSucursales().toString());

                                    /*Se crea una alarma que para cada 15 minutos este
                                     * realizando peticiones para envio o descarga de informacion*/
                                    Calendar c = Calendar.getInstance();
                                    AlarmManager manager = (AlarmManager) context.getSystemService(ALARM_SERVICE);
                                    Intent myIntent = new Intent(context, AlarmaTrackerReciver.class);
                                    myIntent.putExtra("logueo", "start");
                                    PendingIntent pendingIntent = PendingIntent.getBroadcast(context, CANCEL_TRACKER_ID, myIntent, PendingIntent.FLAG_IMMUTABLE);
                                    manager.setRepeating(AlarmManager.RTC_WAKEUP, c.getTimeInMillis(), 1000 * 60 * 15, pendingIntent);
                                    contract.loginResult(response.code());
                                } catch (JSONException |
                                         ExecutionException |
                                         InterruptedException |
                                         TimeoutException exception) {
                                    Timber.tag(TAG).e(exception, "Error in login method");
                                    contract.loginResult(6);
                                }
                            } else {
                                String bodyResponse = response.toString();
                                Timber.tag(TAG).i(bodyResponse);
                                contract.loginResult(404);
                            }
                        }

                        @Override
                        public void onFailure(Call<LoginResponse> call, Throwable t) {
                            Timber.tag(TAG).e(t.toString());
                            Timber.tag(TAG).e("fail: %s", t.getMessage());
                            contract.loginResult(6);
                        }
                    });
                } else {
                    contract.loginResult(5);
                }
            });
        } catch (ExecutionException | InterruptedException e) {
            Timber.tag(TAG).e(e);
        }

    }

    private boolean isExternalStorageWritable() {
        String state = Environment.getExternalStorageState();
        return Environment.MEDIA_MOUNTED.equals(state);
    }

    private void crearDirectorioPrivado(Context context, String nombreDirectorio) {
        //Crear directorio privado en la carpeta Pictures.
        File directorio = new File(
                context.getExternalFilesDir(Environment.DIRECTORY_PICTURES),
                nombreDirectorio
        );

        //Muestro un mensaje en el logcat si no se creo la carpeta por algun motivo
        if (!directorio.mkdirs())
            Timber.tag(TAG).e("Error: No se creo el directorio privado");
    }

    public String getMacAddress() {
        return sessionManager.getMacAddress();
    }

    public void requestPermissionsForApplication(final AppCompatActivity activity) {
        if (activity == null) return;

        int version = Build.VERSION.SDK_INT;

        Timber.tag(this.getClass().getName()).i("Android version: %s", version);

        List<String> permissionsRequested = new ArrayList<>();
        permissionsRequested.add(Manifest.permission.ACCESS_FINE_LOCATION);
        permissionsRequested.add(Manifest.permission.ACCESS_COARSE_LOCATION);
        permissionsRequested.add(Manifest.permission.BLUETOOTH);
        permissionsRequested.add(Manifest.permission.BLUETOOTH_ADMIN);
        permissionsRequested.add(Manifest.permission.CAMERA);
        permissionsRequested.add(Manifest.permission.WRITE_EXTERNAL_STORAGE);
        permissionsRequested.add(Manifest.permission.READ_EXTERNAL_STORAGE);
        permissionsRequested.add(Manifest.permission.CALL_PHONE);
        permissionsRequested.add(Manifest.permission.READ_PHONE_STATE);
        permissionsRequested.add(Manifest.permission.SEND_SMS);

        boolean blueetoothPermisson00 = ActivityCompat.checkSelfPermission(activity, Manifest.permission.BLUETOOTH) != PackageManager.PERMISSION_GRANTED;
        boolean blueetoothPermisson01 = ActivityCompat.checkSelfPermission(activity, Manifest.permission.BLUETOOTH_ADMIN) != PackageManager.PERMISSION_GRANTED;
        boolean fineLocationPermisson = ActivityCompat.checkSelfPermission(activity, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED;
        boolean coarseLocationPermisson = ActivityCompat.checkSelfPermission(activity, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED;
        boolean cameraPermisson = ActivityCompat.checkSelfPermission(activity, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED;
        boolean writeExternalStoragePermisson = ActivityCompat.checkSelfPermission(activity, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED;
        boolean readExternalStoragePermisson = ActivityCompat.checkSelfPermission(activity, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED;
        boolean callphonePermisson = ActivityCompat.checkSelfPermission(activity, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED;
        boolean readPhoneStatePermisson = ActivityCompat.checkSelfPermission(activity, Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED;
        boolean sendMsmPermisson = ActivityCompat.checkSelfPermission(activity, Manifest.permission.SEND_SMS) != PackageManager.PERMISSION_GRANTED;

        boolean allPermissionFlag = blueetoothPermisson00 || blueetoothPermisson01 || fineLocationPermisson || coarseLocationPermisson ||
                cameraPermisson || writeExternalStoragePermisson || readExternalStoragePermisson || callphonePermisson ||
                readPhoneStatePermisson || sendMsmPermisson;

        if (version > 30) {
            allPermissionFlag |= ActivityCompat.checkSelfPermission(activity, Manifest.permission.BLUETOOTH_SCAN) != PackageManager.PERMISSION_GRANTED;
            allPermissionFlag |= ActivityCompat.checkSelfPermission(activity, Manifest.permission.BLUETOOTH_CONNECT) != PackageManager.PERMISSION_GRANTED;
            permissionsRequested.add(Manifest.permission.BLUETOOTH_CONNECT);
            permissionsRequested.add(Manifest.permission.BLUETOOTH_SCAN);
        }

        if (allPermissionFlag) {
            ActivityResultLauncher<String[]> permissionRequest = activity.registerForActivityResult(new ActivityResultContracts
                    .RequestMultiplePermissions(), result -> {
                bluetoothPermissionAction(result);
                locationPermissionAction(activity, result);
            });

            String[] permissionArray = new String[permissionsRequested.size()];
            permissionRequest.launch(permissionsRequested.toArray(permissionArray));
        }
    }

    private void bluetoothPermissionAction(Map<String, Boolean> result) {

        int version = Build.VERSION.SDK_INT;
        String blueetoothPermisson00;
        String blueetoothPermisson01;

        if (version <= 30) {
            blueetoothPermisson00 = Manifest.permission.BLUETOOTH;
            blueetoothPermisson01 = Manifest.permission.BLUETOOTH_ADMIN;
        } else {
            blueetoothPermisson00 = Manifest.permission.BLUETOOTH_SCAN;
            blueetoothPermisson01 = Manifest.permission.BLUETOOTH_CONNECT;
        }

        Boolean bluethootConnectGranted = result.getOrDefault(blueetoothPermisson00, false);
        Boolean bluethootAdminGranted = result.getOrDefault(blueetoothPermisson01, false);
        if ((bluethootConnectGranted != null && bluethootConnectGranted) && (bluethootAdminGranted != null && bluethootAdminGranted)) {
            Timber.tag(TAG).i("Ya tenemos permisos para el Bluetooth");
        } else {
            Timber.tag(TAG).i("No tenemos permisos para el Bluetooth");
        }
    }

    private void locationPermissionAction(final AppCompatActivity activity, Map<String, Boolean> result) {
        String provider;
        if (Build.MODEL.contains("Redmi")) {
            provider = LocationManager.NETWORK_PROVIDER;
        } else {
            if (NetworkStatus.haveNetworkConnection(activity)) {
                provider = LocationManager.NETWORK_PROVIDER;
            } else {
                provider = LocationManager.GPS_PROVIDER;

            }
        }

        Boolean fineLocationGranted = result.getOrDefault(Manifest.permission.ACCESS_FINE_LOCATION, false);
        Boolean coarseLocationGranted = result.getOrDefault(Manifest.permission.ACCESS_COARSE_LOCATION, false);
        if (fineLocationGranted != null && fineLocationGranted) {
            saveLocation(activity, provider);
        } else if (coarseLocationGranted != null && coarseLocationGranted) {
            saveLocation(activity, provider);
        }
    }

    private void saveLocation(final AppCompatActivity activity, final String provider) {
        LocationManager locationManager = (LocationManager) activity.getSystemService(Context.LOCATION_SERVICE);
        if (ActivityCompat.checkSelfPermission(activity, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(activity, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        Location location = locationManager.getLastKnownLocation(provider);
        if (location != null) {
            String latitud = Double.toString(location.getLatitude());
            String longitud = Double.toString(location.getLongitude());
            sessionManager.setCoordenadas(latitud, longitud);
        }
    }

    public void copyMacToClipboard() {
        String mac = this.getMacAddress();
        ClipboardManager clipboard = (ClipboardManager) this.sidertMovilApplication.getSystemService(Context.CLIPBOARD_SERVICE);
        ClipData clip = ClipData.newPlainText("mac", mac);
        clipboard.setPrimaryClip(clip);
    }


    @FunctionalInterface
    public interface Contract {
        void loginResult(int code);
    }

}
