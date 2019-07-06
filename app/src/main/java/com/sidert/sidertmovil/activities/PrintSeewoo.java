package com.sidert.sidertmovil.activities;

import android.Manifest;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.sewoo.port.android.BluetoothPort;
import com.sewoo.request.android.RequestHandler;
import com.sidert.sidertmovil.R;
import com.sidert.sidertmovil.database.DBhelper;
import com.sidert.sidertmovil.models.OrderModel;
import com.sidert.sidertmovil.utils.Constants;
import com.sidert.sidertmovil.utils.Miscellaneous;
import com.sidert.sidertmovil.utils.Popups;
import com.sidert.sidertmovil.utils.PrintTicket;
import com.sidert.sidertmovil.utils.SessionManager;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.text.NumberFormat;
import java.util.Iterator;
import java.util.Locale;
import java.util.Vector;

import static com.sidert.sidertmovil.activities.Signature.verifyStoragePermissions;

public class PrintSeewoo extends AppCompatActivity {

    private static final int REQUEST_ENABLE_BT = 2;

    private BluetoothPort bluetoothPort;
    private BluetoothAdapter bluetoothAdapter;
    private Vector<BluetoothDevice> remoteDevices;
    ArrayAdapter<String> adapter;

    private static final String dir = Environment.getExternalStorageDirectory().getAbsolutePath() + "//temp";
    private static final String fileName = dir + "//BTPrinter";
    private String lastConnAddr;

    private String ban = "";
    private Thread hThread;
    private Context ctx;

    private Toolbar tbMain;

    private TextView tvNumLoan;
    private TextView tvNum;
    private TextView tvNumClient;
    private TextView tvName;
    private TextView tvNameClient;
    private TextView tvTotalLoan;
    private TextView tvAmountLoan;
    private TextView tvPaymentRequired;
    private TextView tvPaymentMade;
    private TextView tvSignature;
    private TextView tvNameSignature;

    private LinearLayout llPaymentRequired;

    private Button btnPrintOriginal;
    private Button btnPrintCopy;

    private String address_print = "";

    //Base de datos
    DBhelper dBhelper;
    SQLiteDatabase db;

    //Persistencia de datos
    private SessionManager session;

    private boolean canExitApp = false;

    private String currentTime = "";

    // Storage Permissions
    private static final int REQUEST_EXTERNAL_STORAGE = 1;
    private static String[] PERMISSIONS_STORAGE = {
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE
    };

    private OrderModel item;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (Constants.ENVIROMENT)
            getWindow().setFlags(WindowManager.LayoutParams.FLAG_SECURE, WindowManager.LayoutParams.FLAG_SECURE);
        verifyStoragePermissions(this);
        setContentView(R.layout.activity_print_seewoo);
        ctx = this;
        session = new SessionManager(ctx);
        dBhelper = new DBhelper(ctx);

        tbMain              = findViewById(R.id.tbMain);

        tvNumLoan           = findViewById(R.id.tvNumLoan);
        tvNum               = findViewById(R.id.tvNum);
        tvNumClient         = findViewById(R.id.tvNumClient);
        tvName              = findViewById(R.id.tvName);
        tvNameClient        = findViewById(R.id.tvNameClient);
        tvTotalLoan         = findViewById(R.id.tvTotalLoan);
        tvAmountLoan        = findViewById(R.id.tvAmountLoan);
        tvPaymentRequired   = findViewById(R.id.tvPaymentRequired);
        tvPaymentMade       = findViewById(R.id.tvPaymentMade);
        tvSignature         = findViewById(R.id.tvSignature);
        tvNameSignature     = findViewById(R.id.tvNameSignature);

        llPaymentRequired   = findViewById(R.id.llPaymentRequired);

        btnPrintOriginal    = findViewById(R.id.btnPrintOriginal);
        btnPrintCopy        = findViewById(R.id.btnPrintCopy);

        setSupportActionBar(tbMain);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        setTitle(getApplicationContext().getString(R.string.print_title));


        item = (OrderModel) getIntent().getSerializableExtra("order");

        if (item.getResultPrint().equals("0.0")){
            if (item.getExternalID().contains("ri") || item.getExternalID().contains("ci")){
                tvSignature.setText(ctx.getResources().getString(R.string.client_signature));
                tvNameSignature.setText(item.getNameClient());
            }
            else if (item.getExternalID().contains("rg") || item.getExternalID().contains("cg")){
                tvSignature.setText(ctx.getResources().getString(R.string.treasurer_signature));
                tvNameSignature.setText(item.getNameClient());
            }
            else{
                tvSignature.setText(ctx.getResources().getString(R.string.manager_signature));
                tvNameSignature.setText(item.getNameAsessor());
            }
        }
        else if (item.getResultPrint().equals("1.0")){
            if (item.getExternalID().contains("ri") ||
                    item.getExternalID().contains("ci") ||
                    item.getExternalID().contains("rg") ||
                    item.getExternalID().contains("cg")){
                tvSignature.setText(ctx.getResources().getString(R.string.asessor_signature));
                tvNameSignature.setText(item.getNameAsessor());
            }
            else{
                tvSignature.setText("");
                tvNameSignature.setText(ctx.getResources().getString(R.string.name_signature));
            }
        }
        else{
            Toast.makeText(ctx, ctx.getResources().getString(R.string.print_original_copy), Toast.LENGTH_SHORT).show();
        }

        tvNumLoan.setText(item.getNumLoan());
        tvNumClient.setText(item.getNumClient());
        tvNameClient.setText(item.getNameClient());
        tvAmountLoan.setText(Miscellaneous.moneyFormat(String.valueOf(item.getAmountLoan())));
        tvPaymentRequired.setText(Miscellaneous.moneyFormat(String.valueOf(item.getAmountRequired())));
        tvPaymentMade.setText(Miscellaneous.moneyFormat(String.valueOf(item.getAmountMade())));

        if (Miscellaneous.getIndorGpo(item.getExternalID()) == 1){
            tvNum.setText(ctx.getResources().getString(R.string.client_number)+":");
            tvName.setText(ctx.getResources().getString(R.string.client_name)+":");
            tvTotalLoan.setText(ctx.getResources().getString(R.string.loan_amount)+":");
            llPaymentRequired.setVisibility(View.VISIBLE);
        }
        else if (Miscellaneous.getIndorGpo(item.getExternalID()) == 2){
            tvNum.setText(ctx.getResources().getString(R.string.group_number)+":");
            tvName.setText(ctx.getResources().getString(R.string.group_name)+":");
            tvTotalLoan.setText(ctx.getResources().getString(R.string.amount_total_loan_group)+":");
            if (item.getExternalID().contains("cvg")){
                llPaymentRequired.setVisibility(View.GONE);
            }
        }
        else {
            Toast.makeText(ctx, ctx.getResources().getString(R.string.error_contact_TI), Toast.LENGTH_SHORT).show();
        }

        String receivedText = "";

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

        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1);
        addPairedDevices();

        btnPrintOriginal.setOnClickListener(btnPrintOriginal_OnClick);
        btnPrintCopy.setOnClickListener(btnPrintCopy_OnClick);

    }

    private View.OnClickListener btnPrintOriginal_OnClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            try {
                Log.v("AddressImpresora",address_print);
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
                new connTask().execute(bluetoothAdapter.getRemoteDevice(address_print),"C");
            }catch (Exception e){
                e.printStackTrace();
            }
        }
    };

    // Bluetooth Connection Task.
    public class connTask extends AsyncTask<Object, Void, Integer>
    {
        final AlertDialog loading = Popups.showLoadingDialog(ctx,ctx.getResources().getString(R.string.please_wait), ctx.getResources().getString(R.string.loading_info));

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
            Integer retVal = null;
            try
            {
                Log.v("Tag-Address",params[0].toString());
                ban = (String) params[1];
                bluetoothPort.connect((BluetoothDevice)params[0],true);

                lastConnAddr = ((BluetoothDevice)params[0]).getAddress();
                retVal = new Integer(0);
            }
            catch (IOException e)
            {
                retVal = new Integer(-1);
            }
            return retVal;
        }

        @Override
        protected void onPostExecute(Integer result)
        {
            loading.dismiss();
            if(result.intValue() == 0)	// Connection success.
            {
                RequestHandler rh = new RequestHandler();
                hThread = new Thread(rh);
                hThread.start();

                //PrintTicket print = new PrintTicket();
                //print.WriteTicket();
                if (ban.equals("O")){
                    btnPrintOriginal.setVisibility(View.GONE);
                    btnPrintCopy.setVisibility(View.VISIBLE);
                    btnPrintCopy.setBackgroundResource(R.drawable.btn_rounded_blue);

                }
            }
            else	// Connection failed.
            {
                final AlertDialog errorPrint = Popups.showDialogMessage(ctx, Constants.not_network, ctx.getResources().getString(R.string.error_connect_print), ctx.getResources().getString(R.string.accept), new Popups.DialogMessage() {
                    @Override
                    public void OnClickListener(AlertDialog dialog) {
                        dialog.dismiss();
                    }
                }, null, null);
                errorPrint.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
                errorPrint.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
                errorPrint.show();
            }
        }
    }

    // add paired device to list
    public class pairBluetoothTask extends AsyncTask<BluetoothDevice, Void, Integer>
    {
        final AlertDialog loading = Popups.showLoadingDialog(ctx,ctx.getResources().getString(R.string.please_wait), ctx.getResources().getString(R.string.loading_info));

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
            Integer retVal = null;
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
                    case "0.0":
                        btnPrintOriginal.setVisibility(View.VISIBLE);
                        btnPrintOriginal.setEnabled(true);
                        btnPrintOriginal.setBackgroundResource(R.drawable.btn_rounded_blue);
                        break;
                    case "1.0":
                        btnPrintOriginal.setVisibility(View.GONE);
                        btnPrintOriginal.setEnabled(false);
                        btnPrintOriginal.setBackgroundResource(R.drawable.btn_disable);

                        btnPrintCopy.setVisibility(View.VISIBLE);
                        btnPrintCopy.setEnabled(true);
                        btnPrintCopy.setBackgroundResource(R.drawable.btn_rounded_blue);
                        break;
                    case "2.0":
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
                final AlertDialog errorConnect = Popups.showDialogMessage(ctx, Constants.not_network, ctx.getResources().getString(R.string.error_connect_print), ctx.getResources().getString(R.string.connect), new Popups.DialogMessage() {
                    @Override
                    public void OnClickListener(AlertDialog dialog) {
                        new pairBluetoothTask().execute(bluetoothAdapter.getRemoteDevice(address_print));
                        dialog.dismiss();
                    }
                }, ctx.getResources().getString(R.string.cancel), new Popups.DialogMessage() {
                    @Override
                    public void OnClickListener(AlertDialog dialog) {
                        dialog.dismiss();
                        sendResponse(false,"0.0", ctx.getResources().getString(R.string.error_process_info));
                    }
                });
                errorConnect.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
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
            clearBtDevData();
            btnPrintCopy.setEnabled(false);
            //activarBtImprimir(true,formiikData.getInputFields().getResImpresion());
        }else {
            btnPrintOriginal.setEnabled(true);
            btnPrintCopy.setEnabled(false);
            //activarBtImprimir(true,formiikData.getInputFields().getResImpresion());
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
            Iterator<BluetoothDevice> iter = (bluetoothAdapter.getBondedDevices()).iterator();
            while (iter.hasNext()) {
                pairedDevice = iter.next();
                if (bluetoothPort.isValidAddress(pairedDevice.getAddress())) {
                    Log.e("remoteDevices", remoteDevices.toString());
                    Log.e("pairedDevice", pairedDevice.toString());

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
        remoteDevices = new Vector<BluetoothDevice>();
    }

    /*
    * Obtiene la ADDRESS de la impresora de un archivo .txt llamado BTPrinter
    * dentro de la carpeta temp y la coloca en el EditText
    * */
    private void loadSettingFile()
    {
        int rin = 0;
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
            File tempDir = new File(dir);
            if(!tempDir.exists())
            {
                tempDir.mkdir();
            }
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
    public void sendResponse(boolean success, String resultPrint, String resultMess){
        saveSettingFile();
        Intent intent = new Intent();
        if(success){
            intent.putExtra("message", resultMess);
            intent.putExtra("res_print", resultPrint);
            setResult(RESULT_OK, intent);
        }else{
            intent.putExtra("message", resultMess);
            intent.putExtra("res_print", resultPrint);
            setResult(RESULT_OK, intent);
        }
        finish();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                String message = "";
                switch ("2.0") {
                    case "0.0":
                        message = ctx.getResources().getString(R.string.not_print);
                        break;
                    case "1.0":
                        message = ctx.getResources().getString(R.string.print_original);
                        break;
                    case "2.0":
                        message = ctx.getResources().getString(R.string.print_original_copy);
                        break;
                }
                sendResponse(true, "0.0", message);
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        String message = "";
        switch ("2.0") {
            case "0.0":
                message = ctx.getResources().getString(R.string.not_print);
                break;
            case "1.0":
                message = ctx.getResources().getString(R.string.print_original);
                break;
            case "2.0":
                message = ctx.getResources().getString(R.string.print_original_copy);
                break;
        }
        sendResponse(true, "0.0", message);
    }

}