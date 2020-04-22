package com.sidert.sidertmovil.activities;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.sewoo.port.android.BluetoothPort;
import com.sewoo.request.android.RequestHandler;
import com.sidert.sidertmovil.R;
import com.sidert.sidertmovil.models.MFormatoRecibo;
import com.sidert.sidertmovil.models.MTicketCC;
import com.sidert.sidertmovil.utils.Constants;
import com.sidert.sidertmovil.utils.Miscellaneous;
import com.sidert.sidertmovil.utils.Popups;
import com.sidert.sidertmovil.utils.PrintRecibos;
import com.sidert.sidertmovil.utils.PrintTicket;
import com.sidert.sidertmovil.utils.SessionManager;

import org.w3c.dom.Text;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Objects;
import java.util.Vector;

import static com.sidert.sidertmovil.utils.Constants.ITEM;
import static com.sidert.sidertmovil.utils.Constants.TICKET_CC;

public class FormatoRecibos extends AppCompatActivity {

    private static final int REQUEST_ENABLE_BT = 2;

    private BluetoothPort bluetoothPort;
    private BluetoothAdapter bluetoothAdapter;
    private Vector<BluetoothDevice> remoteDevices;
    ArrayAdapter<String> adapter;

    private static final int REQUEST_EXTERNAL_STORAGE = 1;
    private static final int REQUEST_READ_EXTERNAL_STORAGE = 2;
    private static String[] PERMISSIONS_STORAGE = {Manifest.permission.WRITE_EXTERNAL_STORAGE};
    private static String[] PERMISSIONS_READ_STORAGE = {Manifest.permission.READ_EXTERNAL_STORAGE};

    private static final String dir = Environment.getExternalStorageDirectory().getAbsolutePath() + "//temp";
    private static final String fileName = dir + "//BTPrinter";
    private String lastConnAddr;

    private String ban = "";
    private Thread hThread;
    private Context ctx;

    private Button btnOriginal;
    private Button btnCopia;
    private Button btnOriginalRe;
    private Button btnCopiaRe;

    private String address_print = "";

    private SessionManager session;

    private TextView tvTipoImpresion;
    private TextView tvTipoRecibo;
    private TextView tvTimestamp;
    private TextView tvNombreCliente;
    private TextView tvPago;
    private TextView tvCantidadLetra;
    private TextView tvTipoFirma;
    private TextView tvNombreFirma;

    private MFormatoRecibo mRecibo;
    private MTicketCC mTicket;


    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_formato_recibos);

        ctx = this;

        session = new SessionManager(ctx);

        Toolbar tbMain              = findViewById(R.id.tbMain);

        tvTipoImpresion     = findViewById(R.id.tvTipoImpresion);
        tvTipoRecibo        = findViewById(R.id.tvTipoRecibo);
        tvTimestamp         = findViewById(R.id.tvTimestamp);
        tvNombreCliente     = findViewById(R.id.tvNombreCliente);
        tvPago              = findViewById(R.id.tvPago);
        tvCantidadLetra     = findViewById(R.id.tvCantidadLetra);
        tvTipoFirma         = findViewById(R.id.tvTipoFirma);
        tvNombreFirma       = findViewById(R.id.tvNombreFirma);

        btnOriginal = findViewById(R.id.btnOriginal);
        btnCopia = findViewById(R.id.btnCopia);
        btnOriginalRe = findViewById(R.id.btnOriginalRe);
        btnCopiaRe = findViewById(R.id.btnCopiaRe);

        setSupportActionBar(tbMain);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        setTitle(getApplicationContext().getString(R.string.print_title));

        int tipo_recibo = 0;

            mRecibo = (MFormatoRecibo) getIntent().getSerializableExtra(TICKET_CC);
            tipo_recibo = mRecibo.getTipoRecibo();
            if (mRecibo.isTipoImpresion()){ // si es original
                tvTipoImpresion.setText("Original");
                tvTipoFirma.setText("Firma Asesor");
                tvNombreFirma.setText(session.getUser().get(1));
            }
            else{ // si es copia
                tvTipoImpresion.setText("Copia");
                tvTipoFirma.setText("Firma Cliente");
                tvNombreFirma.setText(mRecibo.getNombreCliente());
            }

            tvTimestamp.setText(Miscellaneous.ObtenerFecha("timestamp"));

            tvNombreCliente.setText(mRecibo.getNombreCliente());



        switch (tipo_recibo){
            case 1: //Formato circulo de credito
                tvTipoRecibo.setText("CIRCULO DE CREDITO");
                tvPago.setText(Miscellaneous.moneyFormat("17.5"));
                tvCantidadLetra.setText("Diecisiete pesos 50/100 M.N.");
                break;
            case 2: //Formato de gastos funerarios
                break;
        }

        if (this.getIntent() != null)
        {
            loadSettingFile();
            bluetoothSetup();
        }
        else
        {
            finish();
        }

        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1);
        addPairedDevices();

        btnOriginal.setOnClickListener(btnOriginal_OnClick);
        btnCopia.setOnClickListener(btnCopia_OnClick);
        btnOriginalRe.setOnClickListener(btnOriginalRe_OnClick);
        btnCopiaRe.setOnClickListener(btnCopiaRe_OnClick);
    }

    private View.OnClickListener btnOriginal_OnClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            try {
                new connTask().execute(bluetoothAdapter.getRemoteDevice(address_print),"O");
            }catch (Exception e){
                e.printStackTrace();
            }
        }
    };

    private View.OnClickListener btnCopia_OnClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            try {
                mRecibo.setTipoImpresion(false);
                new connTask().execute(bluetoothAdapter.getRemoteDevice(address_print),"C");
            }catch (Exception e){
                e.printStackTrace();
            }
        }
    };

    private View.OnClickListener btnOriginalRe_OnClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            mRecibo.setTipoImpresion(true);
            mRecibo.setReeimpresion(true);
            new connTask().execute(bluetoothAdapter.getRemoteDevice(address_print),"O");
        }
    };

    private View.OnClickListener btnCopiaRe_OnClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            mRecibo.setTipoImpresion(false);
            mRecibo.setReeimpresion(true);
            new connTask().execute(bluetoothAdapter.getRemoteDevice(address_print),"C");
        }
    };

    // Bluetooth Connection Task.
    public class connTask extends AsyncTask<Object, Void, Integer>
    {
        final AlertDialog loading = Popups.showLoadingDialog(ctx,R.string.please_wait, R.string.loading_info);

        @Override
        protected void onPreExecute()
        {
            loading.setCancelable(false);
            loading.show();
            super.onPreExecute();
        }

        @Override
        protected Integer doInBackground(Object... params)
        {
            int  retVal;
            try
            {
                ban = (String) params[1];
                bluetoothPort.connect((BluetoothDevice)params[0],true);

                lastConnAddr = ((BluetoothDevice)params[0]).getAddress();
                retVal = 0;
            }
            catch (IOException e)
            {
                retVal = -1;
            }
            return retVal;
        }

        @Override
        protected void onPostExecute(Integer result)
        {
            loading.dismiss();
            if(result == 0)	// Connection success.
            {
                RequestHandler rh = new RequestHandler();
                hThread = new Thread(rh);
                hThread.start();

                PrintRecibos print = new PrintRecibos();
                print.WriteTicket(ctx, mRecibo);
                try{
                if (!mRecibo.isReeimpresion()) {
                    if (mRecibo.isTipoImpresion()) {
                        bluetoothPort.disconnect();
                        tvTipoImpresion.setText("Copia");
                        tvTipoFirma.setText("Firma Cliente");
                        tvNombreFirma.setText(mRecibo.getNombreCliente());
                        btnOriginal.setVisibility(View.GONE);
                        btnCopia.setVisibility(View.VISIBLE);
                        btnCopia.setBackgroundResource(R.drawable.btn_rounded_blue);
                        btnCopia.setEnabled(true);
                    } else {
                        bluetoothPort.disconnect();
                        btnCopia.setVisibility(View.GONE);
                        btnCopiaRe.setVisibility(View.VISIBLE);
                        btnOriginalRe.setVisibility(View.VISIBLE);
                    }
                }
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

            }
            else	// Connection failed.
            {
                final AlertDialog errorPrint = Popups.showDialogMessage(ctx, Constants.print_off,
                        R.string.error_connect_print, R.string.accept, new Popups.DialogMessage() {
                            @Override
                            public void OnClickListener(AlertDialog dialog) {
                                dialog.dismiss();
                            }
                        });
                Objects.requireNonNull(errorPrint.getWindow()).requestFeature(Window.FEATURE_NO_TITLE);
                errorPrint.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
                errorPrint.show();
                try {
                    bluetoothPort.disconnect();
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    // add paired device to list
    public class pairBluetoothTask extends AsyncTask<BluetoothDevice, Void, Integer>
    {
        final AlertDialog loading = Popups.showLoadingDialog(ctx,R.string.please_wait, R.string.loading_info);

        @Override
        protected void onPreExecute()
        {
            loading.setCancelable(false);
            loading.show();
            super.onPreExecute();
        }

        @Override
        protected Integer doInBackground(BluetoothDevice... params)
        {
            int retVal;
            try
            {
                bluetoothPort.connect(params[0],true);

                lastConnAddr = params[0].getAddress();
                retVal = 0;
                saveSettingFile();
            }
            catch (IOException e)
            {
                retVal = -1;
            }

            Log.e("retVal", ""+retVal);

            return retVal;
        }

        @Override
        protected void onPostExecute(Integer result)
        {
            if(result == 0)	// Connection success.
            {
                RequestHandler rh = new RequestHandler();
                hThread = new Thread(rh);
                hThread.start();
                if (!mRecibo.isReeimpresion()) {
                    switch (mRecibo.getResImpresion()) {
                        case 0:
                            btnOriginal.setVisibility(View.VISIBLE);
                            btnOriginal.setEnabled(true);
                            btnOriginal.setBackgroundResource(R.drawable.btn_rounded_blue);
                            break;
                        case 1:
                            btnOriginal.setVisibility(View.GONE);
                            btnOriginal.setEnabled(false);
                            btnOriginal.setBackgroundResource(R.drawable.btn_disable);

                            btnCopia.setVisibility(View.VISIBLE);
                            btnCopia.setEnabled(true);
                            btnCopia.setBackgroundResource(R.drawable.btn_rounded_blue);
                            break;
                        case 2:
                            btnCopia.setVisibility(View.GONE);
                            btnOriginal.setVisibility(View.GONE);
                            break;
                    }
                }
                else{
                    btnCopiaRe.setVisibility(View.VISIBLE);
                    btnOriginalRe.setVisibility(View.VISIBLE);
                }

                try {
                    bluetoothPort.disconnect();

                } catch (IOException e) {
                    e.printStackTrace();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            else{
                final AlertDialog errorConnect = Popups.showDialogConfirm(ctx, Constants.print_off,
                        R.string.error_connect_print, R.string.connect, new Popups.DialogMessage() {
                            @Override
                            public void OnClickListener(AlertDialog dialog) {
                                try {
                                    bluetoothPort.disconnect();
                                } catch (IOException e) {
                                    e.printStackTrace();
                                } catch (InterruptedException e) {
                                    e.printStackTrace();
                                }
                                new pairBluetoothTask().execute(bluetoothAdapter.getRemoteDevice(address_print));
                                dialog.dismiss();
                            }
                        }, R.string.cancel, new Popups.DialogMessage() {
                            @Override
                            public void OnClickListener(AlertDialog dialog) {
                                dialog.dismiss();
                                sendResponse(false,0, ctx.getResources().getString(R.string.error_process_info));
                            }
                        });
                Objects.requireNonNull(errorConnect.getWindow()).requestFeature(Window.FEATURE_NO_TITLE);
                errorConnect.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
                errorConnect.show();
            }
            if(loading.isShowing())
                loading.dismiss();
        }
    }

    // Set up Bluetooth.
    private void bluetoothSetup()
    {
        bluetoothPort = BluetoothPort.getInstance();
        bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        if(bluetoothPort.isConnected()){
            Log.e("bluetooth", "Conectado");
            clearBtDevData();
            btnCopia.setEnabled(false);
        }else {
            Log.e("bluetooth", "No Conectado");
            btnOriginal.setEnabled(true);
            btnCopia.setEnabled(false);
            clearBtDevData();
            if (bluetoothAdapter == null) {
                // Device does not support Bluetooth
                return;
            }
            if (!bluetoothAdapter.isEnabled()) {
                Log.e("xx","---------------------------");
                Intent enableBtIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
                startActivityForResult(enableBtIntent, REQUEST_ENABLE_BT);
            }
            else{
                Log.e("xxxxx","-------------- " + bluetoothAdapter.isEnabled());
            }
        }
    }

    private void addPairedDevices()
    {
        try {
            BluetoothDevice pairedDevice;
            for (BluetoothDevice bluetoothDevice : (bluetoothAdapter.getBondedDevices())) {
                pairedDevice = bluetoothDevice;
                if (bluetoothPort.isValidAddress(pairedDevice.getAddress())) {
                    //Log.e("remoteDevices", remoteDevices.toString());
                    //Log.e("pairedDevice", pairedDevice.toString());
                    remoteDevices.add(pairedDevice);
                    adapter.add(pairedDevice.getName() + "\n[" + pairedDevice.getAddress() + "] [Paired]");
                    address_print = pairedDevice.toString();
                    Log.e("paired", pairedDevice.toString());
                    new pairBluetoothTask().execute(bluetoothAdapter.getRemoteDevice(pairedDevice.toString()));
                }
            }
        }catch (Exception e){
            e.printStackTrace();
            //Log.e("expcion", e.getMessage());
        }
    }

    // clear device data used list.
    private void clearBtDevData()
    {
        remoteDevices = new Vector<>();
    }

    /*
     * Obtiene la ADDRESS de la impresora de un archivo llamado BTPrinter
     * dentro de la carpeta temp y lo coloca en el EditText
     * */
    private void loadSettingFile()
    {
        int rin;
        char [] buf = new char[128];
        try
        {
            FileReader fReader = new FileReader(fileName);
            rin = fReader.read(buf);
            if(rin > 0)
            {
                lastConnAddr = new String(buf,0,rin);
                address_print = lastConnAddr;

                Log.e("Address", lastConnAddr);

            }
            fReader.close();
        }
        catch (FileNotFoundException e)
        {
            e.printStackTrace();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }

    //Guarda la ultima ADDRESS emparejada en el archivo BTPrinter
    private void saveSettingFile()
    {
        try
        {
            FileWriter fWriter = new FileWriter(fileName);
            if(lastConnAddr != null)
                fWriter.write(lastConnAddr);
            fWriter.close();
        }
        catch (FileNotFoundException e)
        {
            e.printStackTrace();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }

    /*
     * Generación de la respuesta para la actividad para identificar que
     * impresiones se han realizado
     *
     * @param success para saber si se realizó impresión
     * @param resultPrint que tipo de impresión se ha ejecutado
     * @param resultMess mensaje para usuario que tipo de impresión ha realizado
     * */
    public void sendResponse(boolean success, int resultPrint, String resultMess){
        saveSettingFile();
        Intent intent = new Intent();
        if(success){
            intent.putExtra(Constants.MESSAGE, resultMess);
            intent.putExtra(Constants.RES_PRINT, resultPrint);
            intent.putExtra(Constants.FOLIO, 1);
            setResult(RESULT_OK, intent);
        }else{
            intent.putExtra(Constants.MESSAGE, resultMess);
            intent.putExtra(Constants.RES_PRINT, resultPrint);
            setResult(RESULT_OK, intent);
        }
        finish();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                String message = "";
                switch (this.mRecibo.getResImpresion()) {
                    case 0:
                        message = ctx.getResources().getString(R.string.not_print);
                        break;
                    case 1:
                        message = ctx.getResources().getString(R.string.print_original);
                        break;
                    case 2:
                        message = ctx.getResources().getString(R.string.print_original_copy);
                        break;
                }
                sendResponse(true, 2, message);
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

    }

    @Override
    public void onBackPressed() {
        String message;
        switch (this.mRecibo.getResImpresion()) {
            case 0:
                message = ctx.getResources().getString(R.string.not_print);
                break;
            case 1:
                message = ctx.getResources().getString(R.string.print_original);
                break;
            case 2:
                message = ctx.getResources().getString(R.string.print_original_copy);
                break;
            default:
                message = ctx.getResources().getString(R.string.not_print);
                break;
        }
        sendResponse(true, this.mRecibo.getResImpresion(), message);
    }

    /**
     * Checks if the app has permission to write to device storage
     * <p/>
     * If the app does not has permission then the user will be prompted to grant permissions
     *
     * @param activity the activity from which permissions are checked
     */
    public static void verifyStoragePermissions(Activity activity) {
        // Check if we have write permission
        int permission = ActivityCompat.checkSelfPermission(activity, Manifest.permission.WRITE_EXTERNAL_STORAGE);

        if (permission != PackageManager.PERMISSION_GRANTED) {
            // We don't have permission so prompt the user
            ActivityCompat.requestPermissions(
                    activity,
                    PERMISSIONS_STORAGE,
                    REQUEST_EXTERNAL_STORAGE
            );
        }
    }
}
