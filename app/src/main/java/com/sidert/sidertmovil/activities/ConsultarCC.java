package com.sidert.sidertmovil.activities;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.drawable.ColorDrawable;
/*import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;*/
import android.os.Bundle;
//import androidx.appcompat.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
//import android.util.Log;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
//import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.sidert.sidertmovil.R;
import com.sidert.sidertmovil.adapters.adapter_consultas_cc;
import com.sidert.sidertmovil.adapters.adapter_gestiones_cc;
import com.sidert.sidertmovil.database.DBhelper;
import com.sidert.sidertmovil.fragments.dialogs.dialog_date_picker;
import com.sidert.sidertmovil.models.ModeloCatalogoGral;
import com.sidert.sidertmovil.models.circulocredito.CirculoCredito;
import com.sidert.sidertmovil.models.circulocredito.GestionCirculoCredito;
import com.sidert.sidertmovil.models.circulocredito.GestionCirculoCreditoDao;
import com.sidert.sidertmovil.utils.Constants;
import com.sidert.sidertmovil.utils.Miscellaneous;
import com.sidert.sidertmovil.utils.NetworkStatus;
import com.sidert.sidertmovil.utils.Popups;
import com.sidert.sidertmovil.utils.Servicios_Sincronizado;
import com.sidert.sidertmovil.utils.Validator;
import com.sidert.sidertmovil.utils.ValidatorTextView;
import com.sidert.sidertmovil.views.circulocredito.CirculoCreditoActivity;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.sidert.sidertmovil.utils.Constants.CATALOGO;
import static com.sidert.sidertmovil.utils.Constants.COLONIA;
import static com.sidert.sidertmovil.utils.Constants.COLONIAS;
import static com.sidert.sidertmovil.utils.Constants.DATE_CURRENT;
import static com.sidert.sidertmovil.utils.Constants.DAY_CURRENT;
import static com.sidert.sidertmovil.utils.Constants.ESTADOS;
import static com.sidert.sidertmovil.utils.Constants.EXTRA;
import static com.sidert.sidertmovil.utils.Constants.FECHAS_POST;
import static com.sidert.sidertmovil.utils.Constants.FORMAT_DATE_GNRAL;
import static com.sidert.sidertmovil.utils.Constants.IDENTIFIER;
import static com.sidert.sidertmovil.utils.Constants.ITEM;
import static com.sidert.sidertmovil.utils.Constants.MONTH_CURRENT;
import static com.sidert.sidertmovil.utils.Constants.REQUEST_CODE;
import static com.sidert.sidertmovil.utils.Constants.REQUEST_CODE_COLONIA;
import static com.sidert.sidertmovil.utils.Constants.REQUEST_CODE_ESTADO_AVAL;
import static com.sidert.sidertmovil.utils.Constants.REQUEST_CODE_ESTADO_NAC;
import static com.sidert.sidertmovil.utils.Constants.TBL_CONSULTA_CC;
import static com.sidert.sidertmovil.utils.Constants.TBL_RECIBOS_CC;
import static com.sidert.sidertmovil.utils.Constants.TBL_RECUPERACION_RECIBOS_CC;
import static com.sidert.sidertmovil.utils.Constants.TIMESTAMP;
import static com.sidert.sidertmovil.utils.Constants.TITULO;
import static com.sidert.sidertmovil.utils.Constants.YEAR_CURRENT;
import static com.sidert.sidertmovil.utils.Constants.not_network;
import static com.sidert.sidertmovil.utils.Constants.warning;
import static com.sidert.sidertmovil.utils.NameFragments.DIALOGDATEPICKER;

/**Clase para realizar la consulta a CC por el asesor*/
public class ConsultarCC extends AppCompatActivity {
    private FloatingActionButton fbAgregarCC;
    private RecyclerView rvConsultasCC;
    private Context ctx;
    private Toolbar tbMain;
    private DBhelper dBhelper;
    private SQLiteDatabase db;
    private RadioButton rbPendientes;
    private int tipoSeccion = 2;
    private RadioButton rbConsultas;
    private RadioButton rbFallidas;
    private adapter_consultas_cc adapter_cc;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_consultar_c_c);
        ctx = this;
        dBhelper         = new DBhelper(ctx);
        db               = dBhelper.getWritableDatabase();
        tbMain           = findViewById(R.id.tbMain);
        rbPendientes     = findViewById(R.id.rbPendientes);
        rbConsultas      = findViewById(R.id.rbConsultas);
        rbFallidas       = findViewById(R.id.rbFallidas);
        fbAgregarCC  = findViewById(R.id.fbAgregar);
        fbAgregarCC.setOnClickListener(fbAgregarCC_OnClick);
        rvConsultasCC = findViewById(R.id.rvConsultasCC);
        setSupportActionBar(tbMain);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        rbConsultas.setChecked(true);
        rvConsultasCC.setLayoutManager(new LinearLayoutManager(ctx));
        rvConsultasCC.setHasFixedSize(false);
       this.  getConsultas(" WHERE estatus = '200'");
        rbPendientes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                tipoSeccion = 1;
                getConsultas(" WHERE estatus='0'");
            }
        });
        rbConsultas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tipoSeccion = 2;
                getConsultas(" WHERE estatus = '200'");
            }
        });
        rbFallidas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tipoSeccion = 3;
                getConsultas(" WHERE estatus != '200' and estatus!='0'");
            }
        });

    }
    private View.OnClickListener fbAgregarCC_OnClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent formulario_cc = new Intent(getApplicationContext(), FormularioCC.class);
            startActivityForResult  (formulario_cc,0);


        }
    };
    @Override
    protected void onResume() {
        System.out.println("tipo seccion: "+tipoSeccion);
        super.onResume();
        switch (tipoSeccion){
            case 1:      getConsultas(" WHERE estatus='0'");
                break;
            case 2:      getConsultas(" WHERE estatus = '200'");
                break;
            case 3:     getConsultas(" WHERE estatus != '200' and estatus!='0'");
                break;
            default : break;
        }


    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                /**Si el usuario quiere salir de la vista y hay campos llenos se valida si estan vacios cierra la venta*/

                    finish();


                break;

        }

        return super.onOptionsItemSelected(item);
    }
    private void getConsultas( String where){
        String sql = "SELECT * FROM " + TBL_CONSULTA_CC + where+" ORDER BY fecha_termino DESC";
        Cursor row = db.rawQuery(sql, null);
        if (row.getCount() > 0){
            row.moveToFirst();
            List<HashMap<String, String>> consultados = new ArrayList<>();
            for (int i = 0; i < row.getCount(); i++){
                HashMap<String, String> item = new HashMap<>();
                String nombre =row.getString(3) + " " + row.getString(4);

                nombre = nombre.trim() + " " + row.getString(5) + " " + row.getString(6);

                String direccion = row.getString(12)+", "+row.getString(17)+", "+row.getString(13)+", "+row.getString(14)+", "+row.getString(15)+", "+row.getString(16);
                item.put("id", row.getString(0));
                item.put("nombre", nombre.trim());
                item.put("direccion", direccion.trim());
                item.put("producto", row.getString(1));
                item.put("estatus", row.getString(26));
                item.put("preautorizacion", Miscellaneous.validStr(row.getString(25)));
                item.put("errores", Miscellaneous.validStr(row.getString(27)));
                item.put("saldo_actual", Miscellaneous.validStr(row.getString(20)));
                item.put("saldo_vencido", Miscellaneous.validStr(row.getString(21)));
                item.put("peor_pago",Miscellaneous.validStr( row.getString(22)));
                item.put("creditos_abiertos", Miscellaneous.validStr(row.getString(23)));
                item.put("creditos_cerrados", Miscellaneous.validStr(row.getString(24)));
                item.put("fecha_termino", Miscellaneous.validStr(row.getString(18)));
                item.put("fecha_envio", Miscellaneous.validStr(row.getString(19)));
                consultados.add(item);
                row.moveToNext();
            }

            /**Agregar todas las consultas obtenidas de CC*/

            adapter_cc = new adapter_consultas_cc(ctx, consultados, new adapter_consultas_cc.Event() {
                @Override
                public void OnClick(HashMap<String, String> item) {
                    /**Evento al dar click a una cosulta para ver la respuesta de CC*/
                    String mess = "";
                    if ( item.get("estatus").equals("202"))
                    {
                        mess = "Nombre: "+item.get("nombre")+
                                "\n Mensaje: "+item.get("errores")+
                                "\n Fecha Término: "+item.get("fecha_termino")+
                                "\n Fecha Envío: "+item.get("fecha_envio");
                    }
                    else if(item.get("estatus").equals("200"))
                    {
                        mess = "Nombre: "+item.get("nombre")+
                                "\n Preautorización: "+item.get("preautorizacion")+
                                "\n Saldo Actual: "+item.get("saldo_actual")+
                                "\n Saldo Vencido: "+item.get("saldo_vencido")+
                                "\n Créditos Abiertos: "+item.get("creditos_abiertos")+
                                "\n Créditos Cerrados: "+item.get("creditos_cerrados")+
                                "\n Peor Pago: "+item.get("peor_pago")+
                                "\n Fecha Término: "+item.get("fecha_termino")+
                                "\n Fecha Envío: "+item.get("fecha_envio");
                    }
                    else{
                        mess = "Nombre: "+item.get("nombre")+
                                "\n Fecha Término: "+item.get("fecha_termino")+
                                "\n Pendiente de Envío";
                    }
            /*  if(      tipoSeccion==1 ){
                  Intent i_cc = new Intent(getApplicationContext(), FormularioCC.class);
                  HashMap<String, String>  consulta= findById(item.get("id"));
                  i_cc.putExtra("id", consulta.get("id"));
                  i_cc.putExtra("producto_credito", consulta.get("producto_credito"));
                  i_cc.putExtra("monto_solicitado", consulta.get("monto_solicitado"));
                  i_cc.putExtra("primer_nombre", consulta.get("primer_nombre"));
                  i_cc.putExtra("segundo_nombre", consulta.get("segundo_nombre"));
                  i_cc.putExtra("ap_paterno", consulta.get("ap_paterno"));
                  i_cc.putExtra("ap_materno", consulta.get("ap_materno"));
                  i_cc.putExtra("fecha_nac", consulta.get("fecha_nac"));
                  i_cc.putExtra("genero", consulta.get("genero"));
                  i_cc.putExtra("estado_nac", consulta.get("estado_nac"));
                  i_cc.putExtra("curp", consulta.get("curp"));
                  i_cc.putExtra("rfc", consulta.get("rfc"));
                  i_cc.putExtra("direccion", consulta.get("direccion"));
                  i_cc.putExtra("colonia", consulta.get("colonia"));
                  i_cc.putExtra("municipio", consulta.get("municipio"));
                  i_cc.putExtra("ciudad", consulta.get("ciudad"));
                  i_cc.putExtra("estado", consulta.get("estado"));
                  i_cc.putExtra("cp", consulta.get("cp"));
                  startActivity(i_cc);

              }else{*/
                    final AlertDialog info = Popups.showDialogMessage(ctx, warning,
                            mess, R.string.accept, new Popups.DialogMessage() {
                                @Override
                                public void OnClickListener(AlertDialog dialog) {
                                    dialog.dismiss();
                                }
                            });

                    info.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
                    info.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
                    info.show();
              }
            //    }
            });
            rvConsultasCC.setAdapter(adapter_cc);
        }else{
            adapter_cc = new adapter_consultas_cc(ctx,  new ArrayList<>(), new adapter_consultas_cc.Event() {

                @Override
                public void OnClick(HashMap<String, String> item) {

                }
            });
            rvConsultasCC.setAdapter(adapter_cc);
            }
        row.close();

    }
    public    HashMap<String, String> findById(String id){

        HashMap<String, String> item = new HashMap<>();
        String sql = "SELECT cc.* " +
                "FROM " + TBL_CONSULTA_CC + " cc " +
                "WHERE cc._id = ?"
                ;
        Cursor row = db.rawQuery(sql, new String[]{String.valueOf(id)});
        if (row.getCount() > 0){
            row.moveToFirst();
            for (int i = 0; i < row.getCount(); i++){

                item.put("id", row.getString(0));
                item.put("producto_credito", row.getString(1));
                item.put("monto_solicitado", row.getString(2));
                item.put("primer_nombre", row.getString(3));
                item.put("segundo_nombre", row.getString(4));
                item.put("ap_paterno", row.getString(5));
                item.put("ap_materno", row.getString(6));
                item.put("fecha_nac", row.getString(7));
                item.put("genero", row.getString(8));
                item.put("estado_nac", row.getString(9));
                item.put("curp", row.getString(10));
                item.put("rfc", row.getString(11));
                item.put("direccion", row.getString(12));
                item.put("colonia", row.getString(13));
                item.put("municipio", row.getString(14));
                item.put("ciudad", row.getString(15));
                item.put("estado", row.getString(16));
                item.put("cp", row.getString(17));

                row.moveToNext();
            }
        }
        row.close();
        return item;
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        Log.e("RESULT CODE :",""+resultCode+"  REQUEST:"+requestCode);

        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == 0) {
            // A contact was picked.  Here we will just display it
            // to the user.
             System.out.println("TERMINA DE EJECUTARSE");
             Log.e("RESULT CODE :",""+RESULT_OK);

             /*
                can also get the extra sent back through data
                using data.getStringExtra("someKey");
                assuming the extra was a String
             */

        }
    }

}
