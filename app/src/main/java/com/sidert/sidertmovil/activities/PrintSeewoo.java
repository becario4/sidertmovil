package com.sidert.sidertmovil.activities;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.sewoo.port.android.BluetoothPort;
import com.sewoo.request.android.RequestHandler;
import com.sidert.sidertmovil.R;
import com.sidert.sidertmovil.database.DBhelper;
import com.sidert.sidertmovil.models.MImpresion;
import com.sidert.sidertmovil.models.MReimpresion;
import com.sidert.sidertmovil.utils.Constants;
import com.sidert.sidertmovil.utils.Miscellaneous;
import com.sidert.sidertmovil.utils.Popups;
import com.sidert.sidertmovil.utils.PrintTicket;
import com.sidert.sidertmovil.utils.ReprintTicket;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Objects;
import java.util.Vector;

import static com.sidert.sidertmovil.utils.Constants.ENVIROMENT;
import static com.sidert.sidertmovil.utils.Constants.FOLIO;
import static com.sidert.sidertmovil.utils.Constants.TBL_IMPRESIONES_VENCIDA;
import static com.sidert.sidertmovil.utils.Constants.TBL_IMPRESIONES_VENCIDA_T;
import static com.sidert.sidertmovil.utils.Constants.TBL_IMPRESIONES_VIGENTE;
import static com.sidert.sidertmovil.utils.Constants.TBL_IMPRESIONES_VIGENTE_T;
import static com.sidert.sidertmovil.utils.Constants.TBL_RESPUESTAS_GPO;
import static com.sidert.sidertmovil.utils.Constants.TBL_RESPUESTAS_GPO_T;
import static com.sidert.sidertmovil.utils.Constants.TBL_RESPUESTAS_IND;
import static com.sidert.sidertmovil.utils.Constants.TBL_RESPUESTAS_IND_T;
import static com.sidert.sidertmovil.utils.Constants.TBL_RESPUESTAS_IND_V_T;
import static com.sidert.sidertmovil.utils.Constants.TBL_RESPUESTAS_INTEGRANTE_T;

public class PrintSeewoo extends AppCompatActivity {

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

    private Button btnPrintOriginal;
    private Button btnPrintCopy;

    private String address_print = "";

    private MImpresion item;

    private DBhelper dBhelper;
    private SQLiteDatabase db;

    private LinearLayout llReimpreion;
    private Button btnReprintOriginal;
    private Button btnReprintCopia;

    private String DBRecibosVigente = "DBRecibosVigente.csv";
    private String DBRecibosVigente_t = "DBRecibosVigente_t.csv";

    private MReimpresion mReimpresion;


    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_print_seewoo);
        ctx = this;

        dBhelper = new DBhelper(ctx);
        db = dBhelper.getWritableDatabase();

        Toolbar tbMain              = findViewById(R.id.tbMain);

        TextView tvNumLoan           = findViewById(R.id.tvNumLoan);
        TextView tvNum               = findViewById(R.id.tvNum);
        TextView tvNumClient         = findViewById(R.id.tvNumClient);
        TextView tvName              = findViewById(R.id.tvName);
        TextView tvNameClient        = findViewById(R.id.tvNameClient);
        TextView tvTotalLoan         = findViewById(R.id.tvTotalLoan);
        TextView tvAmountLoan        = findViewById(R.id.tvAmountLoan);
        TextView tvPaymentRequired   = findViewById(R.id.tvPaymentRequired);
        TextView tvPaymentMade       = findViewById(R.id.tvPaymentMade);
        TextView tvSignature         = findViewById(R.id.tvSignature);
        TextView tvNameSignature     = findViewById(R.id.tvNameSignature);

        llReimpreion                 = findViewById(R.id.llReimpresion);
        btnReprintOriginal           = findViewById(R.id.btnReprintOriginal);
        btnReprintCopia              = findViewById(R.id.btnReprintCopia);

        LinearLayout llPaymentRequired   = findViewById(R.id.llPaymentRequired);

        btnPrintOriginal    = findViewById(R.id.btnPrintOriginal);
        btnPrintCopy        = findViewById(R.id.btnPrintCopy);

        setSupportActionBar(tbMain);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        setTitle(getApplicationContext().getString(R.string.print_title));

        item = (MImpresion) getIntent().getSerializableExtra("order");

        Log.e("ResImpresion", item.getResultPrint()+" as");
        if (item.getResultPrint() == 0){
            if (item.getTipoPrestamo().equals("VIGENTE") || item.getTipoPrestamo().equals("COBRANZA"))
                tvSignature.setText("Firma Asesor:");
            else if (item.getTipoPrestamo().contains("VENCIDA"))
                tvSignature.setText("Firma Gestor:");
            tvNameSignature.setText(item.getNombreAsesor());
        }
        else if (item.getResultPrint() == 1){
            if (item.getTipoPrestamo().equals("VIGENTE") || item.getTipoPrestamo().equals("COBRANZA")){
                if (item.getTipoGestion().equals("INDIVIDUAL")){
                    tvSignature.setText("Firma Cliente:");
                    tvNameSignature.setText(item.getNombre());
                }
                else if (item.getTipoGestion().equals("GRUPAL")){
                    tvSignature.setText("Firma Tesorera:");
                    tvNameSignature.setText(item.getNombreFirma());
                }
            }
            else if (item.getTipoPrestamo().contains("VENCIDA")){
                tvSignature.setText("Firma Cliente:");
                tvNameSignature.setText(item.getNombre());
            }
        }
        else{
            llReimpreion.setVisibility(View.VISIBLE);
            mReimpresion = new MReimpresion();
            Cursor row = dBhelper.getRecords((item.getTipoPrestamo().contains("VENCIDA"))?TBL_IMPRESIONES_VENCIDA_T:TBL_IMPRESIONES_VIGENTE_T, " WHERE num_prestamo_id_gestion = ? AND tipo_impresion = ?", "", new String[]{item.getNumeroPrestamo()+"-"+item.getIdGestion(), "O"});

            Log.e("CountRecibos", "XXX: "+row.getCount());
            if (row.getCount() > 0){
                row.moveToFirst();
                mReimpresion.setIdPrestamo(item.getIdPrestamo());
                mReimpresion.setIdGestion(item.getIdGestion());
                mReimpresion.setMontoPrestamo(item.getMontoPrestamo());
                mReimpresion.setFolio(row.getString(3));
                mReimpresion.setMonto(row.getString(5));
                mReimpresion.setClaveCliente(item.getClaveCliente());
                mReimpresion.setNumeroPrestamo(item.getNumeroPrestamo());
                mReimpresion.setNumeroCliente(item.getNumeroCliente());
                mReimpresion.setNombre(item.getNombre());
                mReimpresion.setPagoRequerido(item.getPagoRequerido());
                mReimpresion.setNombreAsesor(item.getNombreAsesor());
                mReimpresion.setAsesorId(item.getAsesorId());
                mReimpresion.setTipoPrestamo(item.getTipoPrestamo());
                mReimpresion.setTipoGestion(item.getTipoGestion());
                mReimpresion.setNombreFirma(item.getNombreFirma());
            }
            row.close();


            Toast.makeText(ctx, ctx.getResources().getString(R.string.print_original_copy), Toast.LENGTH_SHORT).show();
        }

        tvNumLoan.setText(item.getNumeroPrestamo());
        tvNumClient.setText(item.getNumeroCliente());
        tvNameClient.setText(item.getNombre());

        tvAmountLoan.setText(Miscellaneous.moneyFormat(String.valueOf(item.getMontoPrestamo())));
        tvPaymentRequired.setText(Miscellaneous.moneyFormat(String.valueOf(item.getPagoRequerido())));
        tvPaymentMade.setText(Miscellaneous.moneyFormat(String.valueOf(item.getMonto())));

        if (item.getTipoGestion().equals("INDIVIDUAL")){
            tvNum.setText(ctx.getResources().getString(R.string.client_number)+":");
            tvName.setText(ctx.getResources().getString(R.string.client_name)+":");
            tvTotalLoan.setText(ctx.getResources().getString(R.string.loan_amount)+":");
            llPaymentRequired.setVisibility(View.VISIBLE);
            if (item.getTipoPrestamo().contains("VENCIDA")){
                llPaymentRequired.setVisibility(View.GONE);
            }
        }
        else if (item.getTipoGestion().equals("GRUPAL")){
            tvNum.setText(ctx.getResources().getString(R.string.group_number)+":");
            tvName.setText(ctx.getResources().getString(R.string.group_name)+":");
            tvTotalLoan.setText(ctx.getResources().getString(R.string.amount_total_loan_group)+":");
            llPaymentRequired.setVisibility(View.VISIBLE);
            if (item.getTipoPrestamo().contains("VENCIDA")){
                llPaymentRequired.setVisibility(View.GONE);
            }
        }
        else {
            Toast.makeText(ctx, ctx.getResources().getString(R.string.error_contact_TI), Toast.LENGTH_SHORT).show();
        }

        //Valida si se está obteniendo datos y que de dentro de los datos contenga la key = 'formiikdata'
        //if (this.getIntent() != null && this.getIntent().hasExtra("Data"))
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

        btnPrintOriginal.setOnClickListener(btnPrintOriginal_OnClick);
        btnPrintCopy.setOnClickListener(btnPrintCopy_OnClick);

        btnReprintOriginal.setOnClickListener(btnReprintOriginal_OnClick);
        btnReprintCopia.setOnClickListener(btnReprintCopia_OnClick);

    }

    private View.OnClickListener btnPrintOriginal_OnClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            try {

                item.setResultPrint(0);
                new connTask().execute(bluetoothAdapter.getRemoteDevice(address_print),"O");
            }catch (Exception e){
                e.printStackTrace();
            }
        }
    };

    private View.OnClickListener btnPrintCopy_OnClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            try {
                item.setResultPrint(1);
                new connTask().execute(bluetoothAdapter.getRemoteDevice(address_print),"C");
            }catch (Exception e){
                e.printStackTrace();
            }
        }
    };

    private View.OnClickListener btnReprintOriginal_OnClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            try {
                mReimpresion.setTipo_impresion("O");
                new connTaskReprint().execute(bluetoothAdapter.getRemoteDevice(address_print),"O");
            }catch (Exception e){
                e.printStackTrace();
            }

        }
    };

    private View.OnClickListener btnReprintCopia_OnClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            try {
                mReimpresion.setTipo_impresion("C");
                new connTaskReprint().execute(bluetoothAdapter.getRemoteDevice(address_print),"C");
            }catch (Exception e){
                e.printStackTrace();
            }
        }
    };

    // Bluetooth Connection Task.
    public class connTaskReprint extends AsyncTask<Object, Void, Integer>
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

                ReprintTicket Reprint = new ReprintTicket();
                Reprint.WriteTicket(ctx, mReimpresion);
                try {
                    bluetoothPort.disconnect();
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

                PrintTicket print = new PrintTicket();
                print.WriteTicket(ctx, item);
                try {
                if (ban.equals("O")){
                    bluetoothPort.disconnect();
                    item.setResultPrint(1);
                    ContentValues cv = new ContentValues();
                    cv.put("res_impresion", 1);
                    if (ENVIROMENT) {
                        if (item.getTipoGestion().equals("INDIVIDUAL"))
                            db.update(TBL_RESPUESTAS_IND, cv, "id_prestamo = ? AND _id = ?", new String[]{item.getIdPrestamo(), item.getIdGestion()});
                        else
                            db.update(TBL_RESPUESTAS_GPO, cv, "id_prestamo = ? AND _id = ?", new String[]{item.getIdPrestamo(), item.getIdGestion()});
                    }
                    else {
                        if (item.getTipoGestion().equals("INDIVIDUAL")) {
                            if (item.getTipoPrestamo().equals("VIGENTE") || item.getTipoPrestamo().equals("COBRANZA"))
                                db.update(TBL_RESPUESTAS_IND_T, cv, "id_prestamo = ? AND _id = ?", new String[]{item.getIdPrestamo(), item.getIdGestion()});
                            else
                                db.update(TBL_RESPUESTAS_IND_V_T, cv, "id_prestamo = ? AND _id = ?", new String[]{item.getIdPrestamo(), item.getIdGestion()});
                        }
                        else {
                            Log.e("TipoPres", item.getTipoPrestamo());
                            if (item.getTipoPrestamo().equals("VIGENTE") || item.getTipoPrestamo().equals("COBRANZA")) {
                                Log.e("ACtualiza","Respuesta");
                                db.update(TBL_RESPUESTAS_GPO_T, cv, "id_prestamo = ? AND _id = ?", new String[]{item.getIdPrestamo(), item.getIdGestion()});
                            }
                            else
                                db.update(TBL_RESPUESTAS_INTEGRANTE_T, cv, "id_prestamo = ? AND _id = ?", new String[]{item.getIdPrestamo(), item.getIdGestion()});
                        }
                    }
                    btnPrintOriginal.setVisibility(View.GONE);
                    btnPrintCopy.setVisibility(View.VISIBLE);
                    btnPrintCopy.setBackgroundResource(R.drawable.btn_rounded_blue);
                    btnPrintCopy.setEnabled(true);
                }
                else{
                    ContentValues cv = new ContentValues();
                    cv.put("res_impresion", 2);
                    if (ENVIROMENT) {
                        if (item.getTipoGestion().equals("INDIVIDUAL"))
                            db.update(TBL_RESPUESTAS_IND, cv, "id_prestamo = ? AND _id = ?", new String[]{item.getIdPrestamo(), item.getIdGestion()});
                        else
                            db.update(TBL_RESPUESTAS_GPO, cv, "id_prestamo = ? AND _id = ?", new String[]{item.getIdPrestamo(), item.getIdGestion()});
                    }
                    else {
                        if (item.getTipoGestion().equals("INDIVIDUAL")) {
                            if (item.getTipoPrestamo().equals("VIGENTE") || item.getTipoPrestamo().equals("COBRANZA"))
                                db.update(TBL_RESPUESTAS_IND_T, cv, "id_prestamo = ? AND _id = ?", new String[]{item.getIdPrestamo(), item.getIdGestion()});
                            else
                                db.update(TBL_RESPUESTAS_IND_V_T, cv, "id_prestamo = ? AND _id = ?", new String[]{item.getIdPrestamo(), item.getIdGestion()});
                        }
                        else {
                            if (item.getTipoPrestamo().equals("VIGENTE") || item.getTipoPrestamo().equals("COBRANZA"))
                                db.update(TBL_RESPUESTAS_GPO_T, cv, "id_prestamo = ? AND _id = ?", new String[]{item.getIdPrestamo(), item.getIdGestion()});
                            else
                                db.update(TBL_RESPUESTAS_INTEGRANTE_T, cv, "id_prestamo = ? AND _id = ?", new String[]{item.getIdPrestamo(), item.getIdGestion()});
                        }
                    }

                    btnPrintCopy.setVisibility(View.GONE);
                    llReimpreion.setVisibility(View.VISIBLE);
                    bluetoothPort.disconnect();
                    item.setResultPrint(2);

                    Cursor row = dBhelper.getRecords((item.getTipoPrestamo().contains("VENCIDA"))?TBL_IMPRESIONES_VENCIDA_T:TBL_IMPRESIONES_VIGENTE_T, " WHERE num_prestamo_id_gestion = ?", "", new String[]{item.getNumeroPrestamo()+"-"+item.getIdGestion()});
                    row.moveToFirst();
                    mReimpresion = new MReimpresion();
                    mReimpresion.setIdPrestamo(item.getIdPrestamo());
                    mReimpresion.setIdGestion(item.getIdGestion());
                    mReimpresion.setMontoPrestamo(item.getMontoPrestamo());
                    mReimpresion.setFolio(row.getString(3));
                    mReimpresion.setMonto(row.getString(5));
                    mReimpresion.setClaveCliente(item.getClaveCliente());
                    mReimpresion.setNumeroPrestamo(item.getNumeroPrestamo());
                    mReimpresion.setNumeroCliente(item.getNumeroCliente());
                    mReimpresion.setNombre(item.getNombre());
                    mReimpresion.setNombreGrupo(item.getNombreGrupo());
                    mReimpresion.setPagoRequerido(item.getPagoRequerido());
                    mReimpresion.setNombreAsesor(item.getNombreAsesor());
                    mReimpresion.setAsesorId(item.getAsesorId());
                    mReimpresion.setTipoPrestamo(item.getTipoPrestamo());
                    mReimpresion.setTipoGestion(item.getTipoGestion());
                    mReimpresion.setNombreFirma(item.getNombreFirma());

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
                switch (item.getResultPrint()){
                    case 0:
                        btnPrintOriginal.setVisibility(View.VISIBLE);
                        btnPrintOriginal.setEnabled(true);
                        btnPrintOriginal.setBackgroundResource(R.drawable.btn_rounded_blue);
                        break;
                    case 1:
                        btnPrintOriginal.setVisibility(View.GONE);
                        btnPrintOriginal.setEnabled(false);
                        btnPrintOriginal.setBackgroundResource(R.drawable.btn_disable);

                        btnPrintCopy.setVisibility(View.VISIBLE);
                        btnPrintCopy.setEnabled(true);
                        btnPrintCopy.setBackgroundResource(R.drawable.btn_rounded_blue);
                        break;
                    case 2:
                        btnPrintCopy.setVisibility(View.GONE);
                        btnPrintOriginal.setVisibility(View.GONE);
                        break;
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
            btnPrintCopy.setEnabled(false);
        }else {
            Log.e("bluetooth", "No Conectado");
            btnPrintOriginal.setEnabled(true);
            btnPrintCopy.setEnabled(false);
            clearBtDevData();
            if (bluetoothAdapter == null) {
                // Device does not support Bluetooth
                return;
            }
            if (!bluetoothAdapter.isEnabled()) {
                Intent enableBtIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
                startActivityForResult(enableBtIntent, REQUEST_ENABLE_BT);
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
            Cursor row = dBhelper.getRecords((item.getTipoPrestamo().contains("VENCIDA"))?TBL_IMPRESIONES_VENCIDA_T:TBL_IMPRESIONES_VIGENTE_T, " WHERE num_prestamo_id_gestion = ?", "", new String[]{this.item.getNumeroPrestamo()+"-"+this.item.getIdGestion()});
            row.moveToFirst();

            intent.putExtra(Constants.MESSAGE, resultMess);
            intent.putExtra(Constants.RES_PRINT, resultPrint);
            try {
                intent.putExtra(Constants.FOLIO, row.getInt(3));
            }
            catch (Exception e){
                intent.putExtra(FOLIO, 0);
            }

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
                switch (this.item.getResultPrint()) {
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
                sendResponse(true, this.item.getResultPrint(), message);
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
        boolean success = false;
        switch (this.item.getResultPrint()) {
            case 0:
                message = ctx.getResources().getString(R.string.not_print);
                break;
            case 1:
                message = ctx.getResources().getString(R.string.print_original);
                success = true;
                break;
            case 2:
                message = ctx.getResources().getString(R.string.print_original_copy);
                success = true;
                break;
            default:
                message = ctx.getResources().getString(R.string.not_print);
                break;
        }
        sendResponse(success, this.item.getResultPrint(), message);
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
