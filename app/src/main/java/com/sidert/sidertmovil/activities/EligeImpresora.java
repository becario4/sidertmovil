package com.sidert.sidertmovil.activities;

import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.lvrenyang.io.BTPrinting;
import com.lvrenyang.io.Page;
import com.lvrenyang.io.Pos;
import com.lvrenyang.io.base.IOCallBack;
import com.sidert.sidertmovil.R;
import com.sidert.sidertmovil.adapters.adapter_dispositivos;
import com.sidert.sidertmovil.database.DBhelper;
import com.sidert.sidertmovil.models.ItemDispositivo;
import com.sidert.sidertmovil.utils.TaskOpen;
import java.util.ArrayList;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class EligeImpresora extends AppCompatActivity implements IOCallBack {
    private Context ctx;
    private RecyclerView recycler;
    private ArrayList<ItemDispositivo> dispositivos;
    private adapter_dispositivos adapterDispositivos;
    private BluetoothAdapter bluetoothAdapter;
    ExecutorService es = Executors.newScheduledThreadPool(30);
    //IMPRESORA GOOJPRT
    BTPrinting mBt = new BTPrinting();
    Page mPage = new Page();
    Pos mPos = new Pos();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        ctx=this;
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_elige_impresora);
        recycler = (RecyclerView) findViewById(R.id.recycler_dispositivos);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        recycler.setLayoutManager(layoutManager);
        recycler.setHasFixedSize(true);
        dispositivos = new ArrayList<ItemDispositivo>();
        bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        if(bluetoothAdapter != null){
            if(!bluetoothAdapter.isEnabled()){// si no está activado
                // Mandamos a activarlo
                Intent habilitarBluIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
                startActivityForResult(habilitarBluIntent, 243);
            }else{
                leerDispositivos();

            }
        }else{
            Toast.makeText(this, "El dispositivo no soporta Bluetooth", Toast.LENGTH_SHORT).show();
        }
        mPage.Set(mBt);
        mPos.Set(mBt);
        mBt.SetCallBack(this);
}
private  void leerDispositivos(){
    // Obtenemos la lista de dispositivos sincronizados
    Set<BluetoothDevice> dispositivosSync = bluetoothAdapter.getBondedDevices();

    // Si hay dispositivos sincronizados
    if(dispositivosSync.size() > 0){
        // Llenamos el array de dispositivos para pasarlo al adapter
        for(BluetoothDevice dispositivo : dispositivosSync){
            dispositivos.add(new ItemDispositivo(dispositivo.getName(),  dispositivo.getAddress()));
        }
    }
    adapterDispositivos = new adapter_dispositivos(new EscuchadorClick(), dispositivos);
    recycler.setAdapter(adapterDispositivos);
}

    @Override
    public void OnOpen() {
        Toast.makeText(this, "Conexión Establecida", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void OnOpenFailed() {
        Toast.makeText(this, "Conexion Fallida", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void OnClose() {
        Toast.makeText(this, "Se ha Cerrado la Conexión", Toast.LENGTH_SHORT).show();
    }

    private class EscuchadorClick implements adapter_dispositivos.MiListenerClick{

        @Override
        public void clickItem(View itemView, int posicion) {
            // Mandamos la direccion al onActivityResult de la actividad que lanzo esta
            Bundle bundle = new Bundle();
            bundle.putString("DireccionDispositivo", adapterDispositivos.getDispositivos().get(posicion).getDireccion());
            bundle.putString("NombreDispositivo", adapterDispositivos.getDispositivos().get(posicion).getNombre());
            // Sincronizar con el dispositivo
            es.submit(new TaskOpen(mBt,  adapterDispositivos.getDispositivos().get(posicion).getDireccion(),ctx));
            //es.submit(new TaskPrint(mPage));


          //  Intent enableBtIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
          //  startActivityForResult(enableBtIntent, 2);
           // Intent intentPaAtras = new Intent();
            //intentPaAtras.putExtras(bundle);
           // setResult(Activity.RESULT_OK, intentPaAtras);
         //   finish();
        }
    }



    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==243 && resultCode==-1){
            leerDispositivos();
        }else{
            Toast.makeText(this, "No se pudo activar el bluetooth del dispositivo", Toast.LENGTH_SHORT).show();
        }

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
}
