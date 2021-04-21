package com.sidert.sidertmovil.activities;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.os.Environment;
/*import androidx.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;*/
import android.os.Bundle;
//import androidx.appcompat.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;

import com.lvrenyang.io.BTPrinting;
import com.lvrenyang.io.Page;
import com.lvrenyang.io.Pos;
import com.lvrenyang.io.base.IOCallBack;
import com.sewoo.port.android.BluetoothPort;
import com.sewoo.request.android.RequestHandler;
import com.sidert.sidertmovil.R;
import com.sidert.sidertmovil.database.DBhelper;
import com.sidert.sidertmovil.models.MFormatoRecibo;
import com.sidert.sidertmovil.models.MTicketCC;
import com.sidert.sidertmovil.models.RecibosAgfCC;
import com.sidert.sidertmovil.utils.Constants;
import com.sidert.sidertmovil.utils.Miscellaneous;
import com.sidert.sidertmovil.utils.Popups;
import com.sidert.sidertmovil.utils.PrintRecibos;
import com.sidert.sidertmovil.utils.Prints;
import com.sidert.sidertmovil.utils.SessionManager;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Objects;
import java.util.Vector;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static com.sidert.sidertmovil.utils.Constants.FOLIO;
import static com.sidert.sidertmovil.utils.Constants.TBL_RECIBOS_AGF_CC;
import static com.sidert.sidertmovil.utils.Constants.TBL_RECIBOS_CC;
import static com.sidert.sidertmovil.utils.Constants.TICKET_CC;

/**Clase donde se ve un preview del recibo de AGF y CC*/
public class FormatoRecibos extends AppCompatActivity implements IOCallBack {

    private static final int REQUEST_ENABLE_BT = 2;
    FormatoRecibos mFormatoRecibos;
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

    Context ctx;

    private String ban = "";
    private Thread hThread;


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

    //private MFormatoRecibo mRecibo;
    //private MTicketCC mTicket;
    private RecibosAgfCC ticket;

    private DBhelper dBhelper;
    private SQLiteDatabase db;

    private int resImpresion = 0;
    private boolean isReeimpresion = false;

    private String nombre ="";
    private String nomFirma = "";
    private String monto = "0.0";
    private String tipo = "";
    private int meses = 0;
    private JSONObject obj;

    //IMPRESORA GOOJPRT
    Page mPage = new Page();
    Pos mPos = new Pos();
    BTPrinting mBt = new BTPrinting();
    ExecutorService es = Executors.newScheduledThreadPool(30);

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_formato_recibos);

        ctx = this;
        mFormatoRecibos = this;
        dBhelper = new DBhelper(ctx);
        db = dBhelper.getWritableDatabase();

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

        /**SE obtienen los datos que se mandaron de la vista anterior*/
        nombre      = getIntent().getStringExtra("nombre");
        nomFirma    = getIntent().getStringExtra("nombre_firma");
        monto       = getIntent().getStringExtra("monto");
        meses       = getIntent().getIntExtra("meses",0);
        tipo        = getIntent().getStringExtra("tipo");

        /**Se inicializa el objeto que se va imprimir*/
        ticket = new RecibosAgfCC();

        //Log.e("Integrantes", String.valueOf(getIntent().getIntExtra("integrantes", 0))+" total");
        try {
            /**Este json se agrego porque para CC cambia un poco la logica y para no afectar lo que ya estaba de CC se agrego el json*/
            obj = new JSONObject();
            obj.put("monto", String.valueOf(monto));
            obj.put("curp", getIntent().getStringExtra("curp"));
            obj.put("total_integrantes", getIntent().getIntExtra("integrantes", 0));
            obj.put("nombre_uno", nombre); /**Puede ser EL nombre del ciente(individual)*/
            obj.put("nombre_dos", nomFirma);/**Puede ser el nombre aval(indivudual) o el nombre del representante(grupal) */
            obj.put("tipo_credito", getIntent().getIntExtra("tipo_credito", 0));


            /**Se comienza a crear el objecto de impresion*/
            if (tipo.equals("AGF"))
                ticket.setNombreFirma(nomFirma);
            else{
                if (getIntent().getIntExtra("tipo_credito", 0) == 1)
                    ticket.setNombreFirma(nombre);
                else
                    ticket.setNombreFirma(nomFirma);
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }

        /**Se agregan mas datos al objecto*/
        ticket.setGrupoId(getIntent().getStringExtra("grupo_id"));
        ticket.setNumSolicitud(getIntent().getStringExtra("num_solicitud"));
        ticket.setNombre(nombre);
        ticket.setTipoImpresion("O");
        ticket.setMonto(monto);
        ticket.setTipoRecibo(tipo);
        ticket.setCurp(getIntent().getStringExtra("curp"));
        ticket.setTotalIntegrantes(getIntent().getIntExtra("integrantes", 0));
        ticket.setResImpresion(0);
        ticket.setReeimpresion(false);

        resImpresion = getIntent().getIntExtra("res_impresion", 0);
        isReeimpresion = getIntent().getBooleanExtra("is_reeimpresion", false);

        tvTipoImpresion.setText("Original");
        tvTipoFirma.setText("Firma Asesor");
        tvNombreFirma.setText(session.getUser().get(1));

        tvTimestamp.setText(Miscellaneous.ObtenerFecha("timestamp"));

        tvNombreCliente.setText(nombre);


        /**Se valida si ya se ha realizado alguna impresion para ocultar o mostrar botones de impresion Orginal o Copia*/
        if (ticket.getTipoImpresion().equals("O")){ // si es original
            tvTipoImpresion.setText("Original");
            tvTipoFirma.setText("Firma Asesor");
            tvNombreFirma.setText(session.getUser().get(1));
        }
        else{ // si es copia
            btnOriginal.setVisibility(View.GONE);
            btnCopia.setVisibility(View.VISIBLE);
            tvTipoImpresion.setText("Copia");
            tvTipoFirma.setText("Firma Cliente");
            tvNombreFirma.setText(nomFirma);
        }

        tvTimestamp.setText(Miscellaneous.ObtenerFecha("timestamp"));

        tvNombreCliente.setText(nombre);

        /**Se valida el tipo de recibo para colocar el titulo en el preview*/
        switch (tipo){
            case "CC": //Formato circulo de credito
                tvTipoRecibo.setText("CIRCULO DE CREDITO");
                //tvCantidadLetra.setText("Diecisiete pesos 50/100 M.N.");
                break;
            case "AGF": //Formato de gastos funerarios
                tvTipoRecibo.setText("APOYO PARA GASTOS FUNERARIOS");

                break;
        }

        /**Se valida si el monto si contiene decimales para establecer el monto en letra*/
        tvPago.setText(Miscellaneous.moneyFormat(monto));
        if (monto.contains(".5"))
            tvCantidadLetra.setText(Miscellaneous.cantidadLetra(monto) + " pesos 50/100 M.N.");
        else
            tvCantidadLetra.setText(Miscellaneous.cantidadLetra(monto) + " pesos 00/100 M.N.");

        /**El getIntent es para saber si se mandaron datos a la clase para continuar con la configuracion de bluetooh*/
        if (this.getIntent() != null)
        {
            /**Funcion para leer un archivo (BTprinter) que se encuentra dentro de la
             * carpeta 'temp' que esta en la raiz de almacenamiento ese archivo contiene la
             * direccion MAC de la impresora*/
            loadSettingFile();
            bluetoothSetup();
        }
        else
        {
            finish();
        }
        /**Obtiene el listado de todos los dispositivos vinculados*/
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1);
        addPairedDevices();

        /**Evento de click para impresiones*/
        btnOriginal.setOnClickListener(btnOriginal_OnClick);
        btnCopia.setOnClickListener(btnCopia_OnClick);
        btnOriginalRe.setOnClickListener(btnOriginalRe_OnClick);
        btnCopiaRe.setOnClickListener(btnCopiaRe_OnClick);

        mPage.Set(mBt);
        mPos.Set(mBt);
        mBt.SetCallBack(this);
    }

    /**Evento de click para imprimir de tipo original*/
    private View.OnClickListener btnOriginal_OnClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            try {
                if(bluetoothAdapter.getRemoteDevice(address_print).getName().contains("MTP"))
                {
                    Log.e("IMPRESORA MTP", "ENTRE AQUI");
                    es.submit(new TaskOpen(mBt, address_print, mFormatoRecibos));
                    //es.submit(new TaskPrint(mPage));
                    es.submit(new TaskPrint(mPos));
                }
                else{
                    new connTask().execute(bluetoothAdapter.getRemoteDevice(address_print),"O");
                }
            }catch (Exception e){
                e.printStackTrace();
            }
        }
    };

    /**Evento de click para imprimir de tipo copia*/
    private View.OnClickListener btnCopia_OnClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            try {
                ticket.setTipoImpresion("C");
                new connTask().execute(bluetoothAdapter.getRemoteDevice(address_print),"C");
            }catch (Exception e){
                e.printStackTrace();
            }
        }
    };

    /**Evento de click para imprimir de tipo reimpresion original*/
    private View.OnClickListener btnOriginalRe_OnClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            ticket.setTipoImpresion("O");
            isReeimpresion = true;
            ticket.setReeimpresion(true);
            new connTask().execute(bluetoothAdapter.getRemoteDevice(address_print),"O");
        }
    };

    /**Evento de click para imprimir de tipo reimpresion copia*/
    private View.OnClickListener btnCopiaRe_OnClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            isReeimpresion = true;
            ticket.setReeimpresion(true);
            ticket.setTipoImpresion("C");
            new connTask().execute(bluetoothAdapter.getRemoteDevice(address_print),"C");
        }
    };

    @Override
    public void OnOpen() {

    }

    @Override
    public void OnOpenFailed() {

    }

    @Override
    public void OnClose() {

    }

    public class TaskOpen implements Runnable
    {
        BTPrinting bt = null;
        String address = null;
        Context context = null;

        public TaskOpen(BTPrinting bt, String address, Context context)
        {
            this.bt = bt;
            this.address = address;
            this.context = context;
        }

        @Override
        public void run() {
            // TODO Auto-generated method stub
            bt.Open(address,context);
        }
    }

    public class TaskPrint implements Runnable
    {
        //Page page = null;
        Pos pos = null;

        /*
        public TaskPrint(Page page)
        {
            this.page = page;
        }*/

        public TaskPrint(Pos pos)
        {
            this.pos = pos;
        }

        @Override
        public void run() {
            final int bPrintResult = Prints.PrintTicket(getApplicationContext(), pos, 384, false, false, true, 1, 1, 0, ticket, obj);
            //final int bPrintResult = Prints.PrintTicket(getApplicationContext(), page, 384, 800);
            final boolean bIsOpened = pos.GetIO().IsOpened();

            mFormatoRecibos.runOnUiThread(() -> Toast.makeText(ctx.getApplicationContext(), (bPrintResult == 0) ? "SUCCESS" : "ERROR" + " " + Prints.ResultCodeToString(bPrintResult), Toast.LENGTH_SHORT).show());
        }
    }

    // Bluetooth Connection Task.
    /**Tarea asincrona para realizar la impresiones cada vez que se presiona alguna boton
     * se verifica si tiene conexion con la impreso por aquello que se halla desemparejado o este apagada la impresora*/
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
                /**Se realiza la conexion de la impresora con la direccion mac registrada en el archivo BTprinter
                 * Si no hubo ningun problema de conexion(emparejamiento) se retorna el valor 0 = success
                 * en caso de que no se logre conectar entra al catch y retorna el valor -1 = error*/
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

        /**Este proceso va despues de la conexion a la impresora ya cuando retorna el valor*/
        @Override
        protected void onPostExecute(Integer result)
        {
            /**Se valida el valor retornado para ver el estatus de conexion si fue success comienza a imprimir*/
            if(result == 0)	// Connection success.
            {
                RequestHandler rh = new RequestHandler();
                hThread = new Thread(rh);
                hThread.start();

                /**Instancia para realizar la impresion*/
                PrintRecibos print = new PrintRecibos();
                /**Contructor que recibe los datos de la impresion*/
                print.WriteTicket(ctx, ticket, obj);
                try{
                    /**Se valida que no sea una reimpresion*/
                    if (!isReeimpresion) {
                        /**Si fue una impresion original se oculta el boton de original y se muestra el de la copia*/
                        if (ticket.getTipoImpresion().equals("O")) {
                            resImpresion = 1; // El 1 representa que solo se imprimio original
                            bluetoothPort.disconnect();
                            tvTipoImpresion.setText("Copia");
                            tvTipoFirma.setText("Firma Cliente");
                            tvNombreFirma.setText(nomFirma);
                            btnOriginal.setVisibility(View.GONE);
                            btnCopia.setVisibility(View.VISIBLE);
                            btnCopia.setBackgroundResource(R.drawable.btn_rounded_blue);
                            btnCopia.setEnabled(true);
                        } else {
                            /**Si fue una impresion copia se oculta el boton de la copia y se muestran botones de reimpresion original y copia*/
                            resImpresion = 2; //El 2 representa que ya se realizaron impresiones de original y copia
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
                /**Muestra un mensaje que no se puedo conectar con la impresora*/
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

            loading.dismiss();
        }
    }

    /**Tarea asincrona para emparejamiento con la impresora*/
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
            /**Se realiza la conexion de la impresora con la direccion mac registrada en el archivo BTprinter
             * Si no hubo ningun problema de conexion(emparejamiento) se retorna el valor 0 = success
             * en caso de que no se logre conectar entra al catch y retorna el valor -1 = error*/
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

        /**Este proceso va despues de la conexion a la impresora ya cuando retorna el valor*/
        @Override
        protected void onPostExecute(Integer result)
        {
            /**Se valida el valor retornado para ver el estatus de conexion si fue success
             * busca si existe una impresion con los datos que quiere imprimir es para saber
             * si ya se imprimir por ejemplo la original o ya se imprimieron ambas y quiere
             * hacer reimpresiones*/
            if(result == 0)	// Connection success.
            {
                RequestHandler rh = new RequestHandler();
                hThread = new Thread(rh);
                hThread.start();

                String sql = "";
                Cursor row = null;
                /**Valida el tipo de impresion si es AGF en caso contrario busca en CC*/
                if (ticket.getTipoRecibo().equals("AGF"))
                {
                    sql = "SELECT * FROM " + TBL_RECIBOS_AGF_CC + " WHERE grupo_id = ? AND num_solicitud = ? AND tipo_recibo = ? AND nombre = ?";
                    row = db.rawQuery(sql, new String[]{ticket.getGrupoId(), ticket.getNumSolicitud(), ticket.getTipoRecibo(), ticket.getNombre()});
                }
                else
                {
                    Log.e("PARAMS", ticket.getTipoRecibo() +" : "+ ticket.getCurp());
                    sql = "SELECT * FROM " + TBL_RECIBOS_CC + " WHERE tipo_credito = ? AND curp = ?";
                    try {
                        row = db.rawQuery(sql, new String[]{String.valueOf(obj.getInt("tipo_credito")), ticket.getCurp()});
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }

                /**Valida cuantas impresiones se han realizado con la informacion actual del objecto*/
                Log.e("CountXXX", row.getCount()+" TTTT");
                if (row != null && row.getCount() < 3) {/**Si no hay impresiones o son menores a 3 registros mostrara botones de original o copia*/
                    switch (row.getCount()) {
                        case 0:/**Cuando no hay ninguna impresion*/
                            btnOriginal.setVisibility(View.VISIBLE);
                            btnOriginal.setEnabled(true);
                            btnOriginal.setBackgroundResource(R.drawable.btn_rounded_blue);
                            break;
                        case 1:/**Cuando ya se realizo una impresion original*/
                            btnOriginal.setVisibility(View.GONE);
                            btnOriginal.setEnabled(false);
                            btnOriginal.setBackgroundResource(R.drawable.btn_disable);

                            btnCopia.setVisibility(View.VISIBLE);
                            btnCopia.setEnabled(true);
                            btnCopia.setBackgroundResource(R.drawable.btn_rounded_blue);
                            break;
                        case 2:/**Cuando ya se imprimio la copia*/
                            btnCopia.setVisibility(View.GONE);
                            btnOriginal.setVisibility(View.GONE);
                            btnCopiaRe.setVisibility(View.VISIBLE);
                            btnOriginalRe.setVisibility(View.VISIBLE);
                            break;
                    }
                }
                /**Cuando hay mas de 2 registros de impresion con la misma informacion del objecto actual mostrara botones de reimpresion*/
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
                /**En caso de que no puedo conecrar con la impresra muestra un mensaje de confirmacion se quiere salir o volver a conectar*/
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
    /**Configuracion de bluetooh para saber si esta activo */
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

    /**Funcion para obtener todos los dispositivos emparejados y realizar las conexion para buscar con cual conecta*/
    private void addPairedDevices()
    {
        try {
            BluetoothDevice pairedDevice;
            for (BluetoothDevice bluetoothDevice : (bluetoothAdapter.getBondedDevices())) {
                pairedDevice = bluetoothDevice;

                //if (bluetoothPort.isValidAddress(pairedDevice.getAddress())) {
                    //Log.e("remoteDevices", remoteDevices.toString());
                    Log.e("pairedDevice", pairedDevice.toString());
                    //Log.e("paired", pairedDevice.toString());

                    if(pairedDevice.getName().contains("MTP"))
                    {
                        remoteDevices.add(pairedDevice);
                        adapter.add(pairedDevice.getName() + "\n[" + pairedDevice.getAddress() + "] [Paired]");
                        address_print = pairedDevice.toString();

                        String sql = "";
                        Cursor row = null;
                        /**Valida el tipo de impresion si es AGF en caso contrario busca en CC*/
                        if (ticket.getTipoRecibo().equals("AGF"))
                        {
                            sql = "SELECT * FROM " + TBL_RECIBOS_AGF_CC + " WHERE grupo_id = ? AND num_solicitud = ? AND tipo_recibo = ? AND nombre = ?";
                            row = db.rawQuery(sql, new String[]{ticket.getGrupoId(), ticket.getNumSolicitud(), ticket.getTipoRecibo(), ticket.getNombre()});
                        }
                        else
                        {
                            Log.e("PARAMS", ticket.getTipoRecibo() +" : "+ ticket.getCurp());
                            sql = "SELECT * FROM " + TBL_RECIBOS_CC + " WHERE tipo_credito = ? AND curp = ?";
                            try {
                                row = db.rawQuery(sql, new String[]{String.valueOf(obj.getInt("tipo_credito")), ticket.getCurp()});
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }

                        /**Valida cuantas impresiones se han realizado con la informacion actual del objecto*/
                        Log.e("CountXXX", row.getCount()+" TTTT");
                        if (row != null && row.getCount() < 3) {/**Si no hay impresiones o son menores a 3 registros mostrara botones de original o copia*/
                            switch (row.getCount()) {
                                case 0:/**Cuando no hay ninguna impresion*/
                                    btnOriginal.setVisibility(View.VISIBLE);
                                    btnOriginal.setEnabled(true);
                                    btnOriginal.setBackgroundResource(R.drawable.btn_rounded_blue);
                                    break;
                                case 1:/**Cuando ya se realizo una impresion original*/
                                    btnOriginal.setVisibility(View.GONE);
                                    btnOriginal.setEnabled(false);
                                    btnOriginal.setBackgroundResource(R.drawable.btn_disable);

                                    btnCopia.setVisibility(View.VISIBLE);
                                    btnCopia.setEnabled(true);
                                    btnCopia.setBackgroundResource(R.drawable.btn_rounded_blue);
                                    break;
                                case 2:/**Cuando ya se imprimio la copia*/
                                    btnCopia.setVisibility(View.GONE);
                                    btnOriginal.setVisibility(View.GONE);
                                    btnCopiaRe.setVisibility(View.VISIBLE);
                                    btnOriginalRe.setVisibility(View.VISIBLE);
                                    break;
                            }
                        }
                        /**Cuando hay mas de 2 registros de impresion con la misma informacion del objecto actual mostrara botones de reimpresion*/
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

                        break;
                    }
                    else {
                        if (bluetoothPort.isValidAddress(pairedDevice.getAddress())) {
                            remoteDevices.add(pairedDevice);
                            adapter.add(pairedDevice.getName() + "\n[" + pairedDevice.getAddress() + "] [Paired]");
                            address_print = pairedDevice.toString();

                            new pairBluetoothTask().execute(bluetoothAdapter.getRemoteDevice(pairedDevice.toString()));

                            break;
                        }
                    }

                //}
            }
        }catch (Exception e){
            e.printStackTrace();
            Log.e("expcion", e.getMessage());
        }
    }

    // clear device data used list.
    private void clearBtDevData()
    {
        remoteDevices = new Vector<>();
    }

    /**
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

    /**En dado caso que se logró conectar con un dispositivo se actualiza
     * el archivo de BTprint colocando la mac address */
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

    /**
     * Generación de la respuesta para la actividad para identificar que
     * impresiones se han realizado
     *
     * @param success para saber si se realizó impresión
     * @param resultPrint que tipo de impresión se ha ejecutado 0= ninguna impresion, 1=Impresion Original, 2= Impresion Original y Copia
     * @param resultMess mensaje para usuario que tipo de impresión ha realizado
     *
     * @return folio: en caso de que se realizaron impresiones
     * @return mensaje
     * */
    public void sendResponse(boolean success, int resultPrint, String resultMess){
        saveSettingFile();
        String sql = "";
        Cursor row = null;
        int folio = 0;

        if (ticket.getTipoRecibo().equals("AGF")) {
            sql = "SELECT * FROM " + TBL_RECIBOS_AGF_CC + " WHERE grupo_id = ? AND num_solicitud = ? AND tipo_recibo = ?";
            row = db.rawQuery(sql, new String[]{ticket.getGrupoId(), ticket.getNumSolicitud(), ticket.getTipoRecibo()});
            if (row.getCount() > 0){
                row.moveToFirst();
                folio = row.getInt(4);
            }
            row.close();
        }
        else{
            String tipoCredito = "";
            String curp = "";
            String nombre = "";

            try {
                tipoCredito = obj.getString("tipo_credito");
                curp = obj.getString("curp");
                nombre = obj.getString("nombre_dos");
            } catch (JSONException e) {
                e.printStackTrace();
            }

            Log.e("SendResponse", tipoCredito + "  "+ curp);
            sql = "SELECT * FROM " + TBL_RECIBOS_CC + " WHERE tipo_credito = ? AND curp = ?";
            row = db.rawQuery(sql, new String[]{tipoCredito, curp});
            if (row.getCount() > 0){
                row.moveToFirst();
                folio = row.getInt(8);
            }
            row.close();
        }

        row.moveToFirst();
        resultPrint = row.getCount();
        if (row.getCount() == 1)
            resultPrint = 1;
        else if (row.getCount() >= 2)
            resultPrint = 2;

        switch (resultPrint) {
            case 0:
                resultMess = ctx.getResources().getString(R.string.not_print);
                break;
            case 1:
                resultMess = ctx.getResources().getString(R.string.print_original);
                break;
            case 2:
                resultMess = ctx.getResources().getString(R.string.print_original_copy);
                break;
        }

        Intent intent = new Intent();
        if(success){
            intent.putExtra(Constants.MESSAGE, resultMess);
            intent.putExtra(Constants.RES_PRINT, resultPrint);
            try {
                intent.putExtra(Constants.FOLIO, folio);
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
                switch (resImpresion) {
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
                sendResponse(true, resImpresion, message);
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

    }

    /**funcion del evento del boton back del dispositivo
     * validando el resImpresion que impresiones se han realizado*/
    @Override
    public void onBackPressed() {
        String message;
        switch (resImpresion) {
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
        sendResponse(true, resImpresion, message);
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
