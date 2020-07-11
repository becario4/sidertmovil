package com.sidert.sidertmovil.activities;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.BatteryManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.CheckBox;
import android.widget.TextView;

import com.sidert.sidertmovil.Home;
import com.sidert.sidertmovil.R;
import com.sidert.sidertmovil.database.DBhelper;
import com.sidert.sidertmovil.models.MAmortizacion;
import com.sidert.sidertmovil.models.MAval;
import com.sidert.sidertmovil.models.MCartera;
import com.sidert.sidertmovil.models.MImpresionRes;
import com.sidert.sidertmovil.models.MIntegrante;
import com.sidert.sidertmovil.models.MPago;
import com.sidert.sidertmovil.models.MPrestamoGpoRes;
import com.sidert.sidertmovil.models.MPrestamoRes;
import com.sidert.sidertmovil.utils.Constants;
import com.sidert.sidertmovil.utils.ManagerInterface;
import com.sidert.sidertmovil.utils.Miscellaneous;
import com.sidert.sidertmovil.utils.RetrofitClient;
import com.sidert.sidertmovil.utils.Servicios_Sincronizado;
import com.sidert.sidertmovil.utils.SessionManager;
import com.sidert.sidertmovil.utils.Sincronizar_Catalogos;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.sidert.sidertmovil.utils.Constants.CONTROLLER_API;
import static com.sidert.sidertmovil.utils.Constants.ENVIROMENT;
import static com.sidert.sidertmovil.utils.Constants.FECHA;
import static com.sidert.sidertmovil.utils.Constants.FORMAT_DATE_GNRAL;
import static com.sidert.sidertmovil.utils.Constants.TBL_AMORTIZACIONES;
import static com.sidert.sidertmovil.utils.Constants.TBL_AMORTIZACIONES_T;
import static com.sidert.sidertmovil.utils.Constants.TBL_AVAL;
import static com.sidert.sidertmovil.utils.Constants.TBL_AVAL_T;
import static com.sidert.sidertmovil.utils.Constants.TBL_CARTERA_GPO;
import static com.sidert.sidertmovil.utils.Constants.TBL_CARTERA_GPO_T;
import static com.sidert.sidertmovil.utils.Constants.TBL_CARTERA_IND;
import static com.sidert.sidertmovil.utils.Constants.TBL_CARTERA_IND_T;
import static com.sidert.sidertmovil.utils.Constants.TBL_IMPRESIONES_VENCIDA;
import static com.sidert.sidertmovil.utils.Constants.TBL_IMPRESIONES_VENCIDA_T;
import static com.sidert.sidertmovil.utils.Constants.TBL_IMPRESIONES_VIGENTE;
import static com.sidert.sidertmovil.utils.Constants.TBL_IMPRESIONES_VIGENTE_T;
import static com.sidert.sidertmovil.utils.Constants.TBL_MIEMBROS_GPO;
import static com.sidert.sidertmovil.utils.Constants.TBL_MIEMBROS_GPO_T;
import static com.sidert.sidertmovil.utils.Constants.TBL_PAGOS;
import static com.sidert.sidertmovil.utils.Constants.TBL_PAGOS_T;
import static com.sidert.sidertmovil.utils.Constants.TBL_PRESTAMOS_GPO;
import static com.sidert.sidertmovil.utils.Constants.TBL_PRESTAMOS_GPO_T;
import static com.sidert.sidertmovil.utils.Constants.TBL_PRESTAMOS_IND;
import static com.sidert.sidertmovil.utils.Constants.TBL_PRESTAMOS_IND_T;
import static com.sidert.sidertmovil.utils.Constants.TBL_RESPUESTAS_GPO_T;
import static com.sidert.sidertmovil.utils.Constants.TBL_RESPUESTAS_IND_T;
import static com.sidert.sidertmovil.utils.Constants.TIMESTAMP;

public class DescargaDatos extends AppCompatActivity {

    private Context ctx;

    private CheckBox cbImpresiones;
    private CheckBox cbGeolocalizaciones;
    private CheckBox cbCartera;

    private DBhelper dBhelper;
    private SQLiteDatabase db;

    private SessionManager session;

    private SimpleDateFormat sdf = new SimpleDateFormat(FORMAT_DATE_GNRAL);

    private TextView tvTotal;
    private TextView tvRegistradas;

    public interface PrestamoIndCallbacks{
        void onPrestamoInd(boolean mPrestamosInd);
        void onPrestamoIndFailed(Throwable error);
    }

    public interface PrestamoGpoCallbacks{
        void onPrestamoGpo(boolean mPrestamosGpo);
        void onPrestamoGpoFailed(Throwable error);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_descarga_datos);

        ctx = this;

        dBhelper = new DBhelper(ctx);
        db = dBhelper.getWritableDatabase();

        session = new SessionManager(ctx);

        cbImpresiones = findViewById(R.id.cbImpresiones);
        cbGeolocalizaciones = findViewById(R.id.cbGeolocalizaciones);
        cbCartera = findViewById(R.id.cbCartera);

        tvTotal = findViewById(R.id.tvTotal);
        tvRegistradas = findViewById(R.id.tvRegistradas);

        GetCategoriaTickets();
        GetUltimasImpresiones();
    }

    private void GetCategoriaTickets(){
         Sincronizar_Catalogos sc = new Sincronizar_Catalogos();
         //sc.GetEstados(ctx);
         sc.GetCategoriasTickets(ctx);
         //sc.GetPlazosPrestamo(ctx);
         //Servicios_Sincronizado ss = new Servicios_Sincronizado();
         //ss.GetUltimosRecibos(ctx);
    }

    private void GetUltimasImpresiones (){
        ManagerInterface api = new RetrofitClient().generalRF(CONTROLLER_API, ctx).create(ManagerInterface.class);

        IntentFilter ifilter = new IntentFilter(Intent.ACTION_BATTERY_CHANGED);
        Intent batteryStatus = ctx.registerReceiver(null, ifilter);

        int level = batteryStatus.getIntExtra(BatteryManager.EXTRA_LEVEL, -1);
        int scale = batteryStatus.getIntExtra(BatteryManager.EXTRA_SCALE, -1);

        final float battery = (level / (float)scale)*100;

        Log.e("SerieID",session.getUser().get(0));
        Call<List<MImpresionRes>> call = api.getUltimasImpresiones(session.getUser().get(0),
                                                                    String.valueOf(battery),
                                                                    getString(R.string.app_version),
                                                                    "Bearer "+ session.getUser().get(7));

        call.enqueue(new Callback<List<MImpresionRes>>() {
            @Override
            public void onResponse(Call<List<MImpresionRes>> call, Response<List<MImpresionRes>> response) {
                //cbImpresiones.setChecked(true);
                Log.v("Cantidad","# "+response.code());
                switch (response.code()){
                    case 200:
                        List<MImpresionRes> impresiones = response.body();
                        Log.v("Cantidad impresiones","# "+impresiones.size());
                        for (MImpresionRes item : impresiones){
                            Cursor row;
                            HashMap<Integer, String> data = Miscellaneous.GetNumPrestamo(item.getExternalId());
                            Log.e("TipoCarteta", String.valueOf(item.getTipoCartera()));
                            if (item.getTipoCartera() == -1) {
                                Log.e("Tipo",data.get(0)+"xxxxMisc");
                                if (data.get(0).equals("Vigente")){
                                    if (ENVIROMENT)
                                        row = dBhelper.getRecords(TBL_IMPRESIONES_VIGENTE, " WHERE num_prestamo = ? AND folio = ? AND tipo_impresion = ?", "", new String[]{data.get(1), String.valueOf(item.getFolio()), String.valueOf(item.getTipo())});
                                    else
                                        row = dBhelper.getRecords(TBL_IMPRESIONES_VIGENTE_T, " WHERE num_prestamo = ? AND folio = ? AND tipo_impresion = ?", "", new String[]{data.get(1), String.valueOf(item.getFolio()), String.valueOf(item.getTipo())});

                                    Log.e("Existe", "Vigente " + row.getCount());
                                    if (row.getCount() > 0){
                                        ContentValues cv = new ContentValues();
                                        cv.put("sent_at", item.getSendedAt());
                                        cv.put("estatus", "1");
                                        if (ENVIROMENT)
                                            db.update(TBL_IMPRESIONES_VIGENTE, cv,
                                                "num_prestamo = ? AND folio = ? AND tipo_impresion = ?", new String[]{
                                                        data.get(1), item.getFolio(), item.getTipo()});
                                        else
                                            db.update(TBL_IMPRESIONES_VIGENTE_T, cv,
                                                    "num_prestamo = ? AND folio = ? AND tipo_impresion = ?", new String[]{
                                                            data.get(1), item.getFolio(), item.getTipo()});

                                    }else{
                                        Log.e("......","................");
                                        HashMap<Integer, String> params = new HashMap<>();
                                        Log.e("NumPrestamoIdGestion", String.valueOf(item.getNumPrestamoIdGestion()));
                                        if (item.getNumPrestamoIdGestion().trim().isEmpty())
                                            params.put(0, data.get(1));  //num_prestamo_id_gestion
                                        else
                                            params.put(0, String.valueOf(item.getNumPrestamoIdGestion()));  //num_prestamo_id_gestion
                                        params.put(1, item.getAsesorid());                              //asesor_id
                                        params.put(2, item.getFolio());                                 //folio
                                        params.put(3, item.getTipo());                                  //tipo_impresion
                                        params.put(4, String.valueOf(item.getMontoRealizado()));        //monto
                                        params.put(5, item.getClavecliente());                          //clave_cliente
                                        params.put(6, item.getGeneratedAt());                           //created_at
                                        params.put(7, item.getSendedAt());                              //sent_at
                                        params.put(8, "1");                                             //estatus
                                        params.put(9, data.get(1));                                     //num_prestamo                                              //num_prestamo

                                        dBhelper.saveImpresiones(db, params);
                                    }
                                    row.close();
                                }
                                else if (data.get(0).equals("Vencida")){
                                    if (ENVIROMENT)
                                        row = dBhelper.getRecords(TBL_IMPRESIONES_VENCIDA, " WHERE num_prestamo = ? AND folio = ? AND tipo_impresion = ?", "", new String[]{data.get(1), String.valueOf(item.getFolio()), String.valueOf(item.getTipo())});
                                    else
                                        row = dBhelper.getRecords(TBL_IMPRESIONES_VENCIDA_T, " WHERE num_prestamo = ? AND folio = ? AND tipo_impresion = ?", "", new String[]{data.get(1), String.valueOf(item.getFolio()), String.valueOf(item.getTipo())});

                                    if (row.getCount() > 0){
                                        ContentValues cv = new ContentValues();
                                        cv.put("sent_at", item.getSendedAt());
                                        cv.put("estatus", "1");
                                        db.update((ENVIROMENT)?TBL_IMPRESIONES_VENCIDA:TBL_IMPRESIONES_VENCIDA_T, cv,
                                                "num_prestamo = ? AND folio = ? AND tipo_impresion = ?", new String[]{
                                                        data.get(1), item.getFolio(), item.getTipo()});

                                    }else{
                                        HashMap<Integer, String> params = new HashMap<>();
                                        if (item.getNumPrestamoIdGestion().trim().isEmpty())
                                            params.put(0, data.get(1));  //num_prestamo_id_gestion
                                        else
                                            params.put(0, String.valueOf(item.getNumPrestamoIdGestion()));  //num_prestamo_id_gestion
                                        params.put(1, item.getAsesorid());                              //asesor_id
                                        params.put(2, item.getFolio());                                 //folio
                                        params.put(3, item.getTipo());                                  //tipo_impresion
                                        params.put(4, String.valueOf(item.getMontoRealizado()));        //monto
                                        params.put(5, item.getClavecliente());                          //clave_cliente
                                        params.put(6, item.getGeneratedAt());                           //created_at
                                        params.put(7, item.getSendedAt());                              //sent_at
                                        params.put(8, "1");                                             //estatus
                                        params.put(9, data.get(1));                                     //num_prestamo                                              //num_prestamo

                                        dBhelper.saveImpresionesVencida(db, params);
                                    }
                                    row.close();
                                }
                            }
                            else{
                                if (item.getTipoCartera()  == 0|| item.getTipoCartera() == 1){//VIGENTE
                                    if (ENVIROMENT)
                                        row = dBhelper.getRecords(TBL_IMPRESIONES_VIGENTE, " WHERE num_prestamo = ? AND folio = ? AND tipo_impresion = ?", "", new String[]{data.get(1), item.getFolio(), item.getTipo()});
                                    else
                                        row = dBhelper.getRecords(TBL_IMPRESIONES_VIGENTE_T, " WHERE num_prestamo = ? AND folio = ? AND tipo_impresion = ?", "", new String[]{data.get(1), item.getFolio(), item.getTipo()});

                                    if (row.getCount() > 0){
                                        ContentValues cv = new ContentValues();
                                        cv.put("sent_at", item.getSendedAt());
                                        cv.put("estatus", "1");
                                        db.update((ENVIROMENT)?TBL_IMPRESIONES_VIGENTE:TBL_IMPRESIONES_VIGENTE_T, cv,
                                                "num_prestamo = ? AND folio = ? AND tipo_impresion = ?", new String[]{
                                                        data.get(1), item.getFolio(), item.getTipo()});

                                    }else{
                                        HashMap<Integer, String> params = new HashMap<>();
                                        if (item.getNumPrestamoIdGestion().trim().isEmpty())
                                            params.put(0, data.get(1));  //num_prestamo_id_gestion
                                        else
                                            params.put(0, String.valueOf(item.getNumPrestamoIdGestion()));  //num_prestamo_id_gestion
                                        params.put(1, item.getAsesorid());                              //asesor_id
                                        params.put(2, item.getFolio());                                 //folio
                                        params.put(3, item.getTipo());                                  //tipo_impresion
                                        params.put(4, String.valueOf(item.getMontoRealizado()));        //monto
                                        params.put(5, item.getClavecliente());                          //clave_cliente
                                        params.put(6, item.getGeneratedAt());                           //created_at
                                        params.put(7, item.getSendedAt());                              //sent_at
                                        params.put(8, "1");                                             //estatus
                                        params.put(9, item.getExternalId());                                     //num_prestamo                                              //num_prestamo

                                        dBhelper.saveImpresiones(db, params);
                                    }
                                    row.close();
                                }
                                else if (item.getTipoCartera() == 4){//VENCIDA

                                    Log.e("ValidParams", data.get(1) + " - " + String.valueOf(item.getFolio()) + " - " + String.valueOf(item.getTipo()));

                                    if (ENVIROMENT)
                                        row = dBhelper.getRecords(TBL_IMPRESIONES_VENCIDA, " WHERE num_prestamo = ? AND folio = ? AND tipo_impresion = ?", "", new String[]{data.get(1), String.valueOf(item.getFolio()), String.valueOf(item.getTipo())});
                                    else
                                        row = dBhelper.getRecords(TBL_IMPRESIONES_VENCIDA_T, " WHERE num_prestamo = ? AND folio = ? AND tipo_impresion = ?", "", new String[]{data.get(1), String.valueOf(item.getFolio()), String.valueOf(item.getTipo())});

                                    Log.e("RowImpresion", "as"+row.getCount());
                                    if (row.getCount() > 0){
                                        ContentValues cv = new ContentValues();
                                        cv.put("sent_at", item.getSendedAt());
                                        cv.put("estatus", "1");
                                        db.update((ENVIROMENT)?TBL_IMPRESIONES_VENCIDA:TBL_IMPRESIONES_VENCIDA_T, cv,
                                                "num_prestamo = ? AND folio = ? AND tipo_impresion = ?", new String[]{
                                                        data.get(1), item.getFolio(), item.getTipo()});

                                    }else{
                                        HashMap<Integer, String> params = new HashMap<>();
                                        if (item.getNumPrestamoIdGestion().trim().isEmpty())
                                            params.put(0, data.get(1));  //num_prestamo_id_gestion
                                        else
                                            params.put(0, String.valueOf(item.getNumPrestamoIdGestion()));  //num_prestamo_id_gestion
                                        params.put(1, item.getAsesorid());                              //asesor_id
                                        params.put(2, item.getFolio());                                 //folio
                                        params.put(3, item.getTipo());                                  //tipo_impresion
                                        params.put(4, String.valueOf(item.getMontoRealizado()));        //monto
                                        params.put(5, item.getClavecliente());                          //clave_cliente
                                        params.put(6, item.getGeneratedAt());                           //created_at
                                        params.put(7, item.getSendedAt());                              //sent_at
                                        params.put(8, "1");                                             //estatus
                                        params.put(9, data.get(1));                                     //num_prestamo                                              //num_prestamo

                                        dBhelper.saveImpresionesVencida(db, params);
                                    }
                                    row.close();
                                }
                            }

                        }
                        break;
                }

                //cbGeolocalizaciones.setChecked(true);
                GetCartera();
                //GetGeolocalizaciones();
                cbImpresiones.setChecked(true);
                /*Intent home = new Intent(ctx, Home.class);
                home.putExtra("login", false);
                startActivity(home);
                finish();*/

            }

            @Override
            public void onFailure(Call<List<MImpresionRes>> call, Throwable t) {
                Log.e("error", t.getMessage()+"asdasd");
                Log.e("fail","impresiones");

                //GetGeolocalizaciones();
                GetCartera();
                cbImpresiones.setChecked(true);
                /*Intent home = new Intent(ctx, Home.class);
                home.putExtra("login", false);
                startActivity(home);
                finish();*/
            }
        });
    }

    private void GetCartera(){

        ManagerInterface api = new RetrofitClient().generalRF(Constants.CONTROLLER_MOVIL, ctx).create(ManagerInterface.class);

        Call<List<MCartera>> call = api.getCartera(session.getUser().get(9),"Bearer "+ session.getUser().get(7));

        call.enqueue(new Callback<List<MCartera>>() {
            @Override
            public void onResponse(Call<List<MCartera>> call, Response<List<MCartera>> response) {
                Log.e("Code cartera", String.valueOf(response.code()));
                switch (response.code()){
                    case 200:
                        List<MCartera> cartera = response.body();
                        if (cartera.size() > 0){
                            Cursor row;

                            for (int i = 0; i < cartera.size(); i++){
                                String where = " WHERE id_cartera = ?";
                                String order = "";
                                String[] args =  new String[] {String.valueOf(cartera.get(i).getId())};
                                Log.e("CarteraName", cartera.get(i).getNombre()+" : "+ cartera.get(i).getId());
                                switch (Miscellaneous.GetTipoCartera(cartera.get(i).getCarteraTipo())){
                                    case 1:
                                        if (ENVIROMENT)
                                            row = dBhelper.getRecords(TBL_CARTERA_IND, where, order, args);
                                        else
                                            row = dBhelper.getRecords(TBL_CARTERA_IND_T, where, order, args);

                                        if (row.getCount() == 0){ //Registra la cartera de ind
                                            row.close();
                                            HashMap<Integer, String> values = new HashMap<>();
                                            values.put(0, String.valueOf(cartera.get(i).getId()));              //ID
                                            values.put(1, cartera.get(i).getClave());                           //CLAVE
                                            values.put(2, cartera.get(i).getNombre());                          //NOMBRE
                                            values.put(3, cartera.get(i).getDireccion());                       //DIRECCION
                                            values.put(4, cartera.get(i).getAsesorNombre());                    //ASESOR NOMBRE
                                            values.put(5, cartera.get(i).getSerieId());                         //SERIE ID
                                            values.put(6, (cartera.get(i).getRuta())?"1":"0");                  //IS RUTA
                                            values.put(7, (cartera.get(i).getRuta())?"1":"0");                  //RUTA OBLIGADO
                                            values.put(8, String.valueOf(cartera.get(i).getUsuarioId()));       //USUARIO ID
                                            values.put(9, cartera.get(i).getDia());                             //DIA
                                            values.put(10, cartera.get(i).getNumSolicitud());                   //NUM SOLICITUD
                                            values.put(11, Miscellaneous.ObtenerFecha(TIMESTAMP));              //FECHA CREACION
                                            values.put(12, Miscellaneous.ObtenerFecha(TIMESTAMP));              //FECHA ACTUALIZACION
                                            values.put(13, cartera.get(i).getColonia());                        //COLONIA

                                            values.put(14, (cartera.get(i).getGeoCliente()?"1":"0"));           //GEO CLIENTE
                                            values.put(15, (cartera.get(i).getGeoAval()?"1":"0"));              //GEO AVAL
                                            values.put(16, (cartera.get(i).getGeoNegocio()?"1":"0"));           //GEO NEGOCIO
                                            values.put(17, "0");                                                //CC
                                            values.put(18, "0");                                                //AGF
                                            values.put(19, "");                                                 //CURP

                                            /*values.put(17, (cartera.get(i).getCcInd()?"1":"0"));                //CC
                                            values.put(18, (cartera.get(i).getAgfInd()?"1":"0"));               //AGF
                                            values.put(19, cartera.get(i).getCurp());                           //CURP*/

                                            if (ENVIROMENT)
                                                dBhelper.saveCarteraInd(db, TBL_CARTERA_IND, values);
                                            else
                                                dBhelper.saveCarteraInd(db, TBL_CARTERA_IND_T, values);

                                        }
                                        else{ //Actualiza la cartera de ind
                                            row.close();
                                            ContentValues cv = new ContentValues();
                                            cv.put("nombre", cartera.get(i).getNombre());
                                            cv.put("direccion", cartera.get(i).getDireccion());
                                            cv.put("asesor_nombre", cartera.get(i).getAsesorNombre());
                                            cv.put("serie_id", cartera.get(i).getSerieId());
                                            cv.put("is_ruta", (cartera.get(i).getRuta())?1:0);
                                            cv.put("ruta_obligado", (cartera.get(i).getRuta())?1:0);
                                            cv.put("usuario_id", String.valueOf(cartera.get(i).getUsuarioId()));
                                            cv.put("dia", cartera.get(i).getDia());
                                            cv.put("num_solicitud", cartera.get(i).getNumSolicitud());
                                            cv.put("fecha_actualizado", Miscellaneous.ObtenerFecha(TIMESTAMP));
                                            cv.put("colonia", cartera.get(i).getColonia());
                                            cv.put("geo_cliente", (cartera.get(i).getGeoCliente()?1:0));
                                            cv.put("geo_aval", (cartera.get(i).getGeoAval()?1:0));
                                            cv.put("geo_negocio", (cartera.get(i).getGeoNegocio()?1:0));
                                            cv.put("cc", 0);
                                            cv.put("agf", 0);
                                            cv.put("curp", "");
                                            /*cv.put("cc", (cartera.get(i).getCcInd()?1:0));
                                            cv.put("agf", (cartera.get(i).getAgfInd()?1:0));
                                            cv.put("curp", cartera.get(i).getCurp());*/

                                            if (ENVIROMENT)
                                                db.update(TBL_CARTERA_IND, cv, "id_cartera = ?", new String[]{String.valueOf(cartera.get(i).getId())});
                                            else
                                                db.update(TBL_CARTERA_IND_T, cv, "id_cartera = ?", new String[]{String.valueOf(cartera.get(i).getId())});
                                        }
                                        row.close();
                                        break;
                                    case 2:
                                        if (ENVIROMENT)
                                            row = dBhelper.getRecords(TBL_CARTERA_GPO, where, order, args);
                                        else
                                            row = dBhelper.getRecords(TBL_CARTERA_GPO_T, where, order, args);

                                        if (row.getCount() == 0){ //Registra la cartera de gpo
                                            row.close();
                                            HashMap<Integer, String> values = new HashMap<>();
                                            values.put(0, String.valueOf(cartera.get(i).getId()));              //ID
                                            values.put(1, cartera.get(i).getClave());                           //CLAVE
                                            values.put(2, cartera.get(i).getNombre());                          //NOMBRE
                                            values.put(3, cartera.get(i).getTesorero());                        //TESORERA
                                            values.put(4, cartera.get(i).getDireccion());                       //DIRECCION
                                            values.put(5, cartera.get(i).getAsesorNombre());                    //ASESOR NOMBRE
                                            values.put(6, cartera.get(i).getSerieId());                         //SERIE ID
                                            values.put(7, (cartera.get(i).getRuta())?"1":"0");                  //IS RUTA
                                            values.put(8, (cartera.get(i).getRuta())?"1":"0");                  //RUTA OBLIGADO
                                            values.put(9, String.valueOf(cartera.get(i).getUsuarioId()));       //USUARIO ID
                                            values.put(10, cartera.get(i).getDia());                            //DIA
                                            values.put(11, cartera.get(i).getNumSolicitud());                   //NUM SOLICITUD
                                            values.put(12, Miscellaneous.ObtenerFecha(TIMESTAMP));              //FECHA CREACION
                                            values.put(13, Miscellaneous.ObtenerFecha(TIMESTAMP));              //FECHA ACTUALIZACION
                                            values.put(14, cartera.get(i).getColonia());                        //COLONIA
                                            values.put(15, cartera.get(i).getGeolocalizadas());                 //GEOLOCALIZADAS
                                            values.put(16, "{}");                                               //CC
                                            values.put(17, "{}");                                               //AGF
                                            /*values.put(16, cartera.get(i).getCcGpo());                          //CC
                                            values.put(17, cartera.get(i).getAgfGpo());                         //AGF*/

                                            Log.e("geolocalizadas", "xx"+cartera.get(i).getGeolocalizadas());

                                            dBhelper.saveCarteraGpo(db, TBL_CARTERA_GPO_T, values);
                                        }
                                        else{ //Actualiza la cartera de gpo
                                            row.close();
                                            ContentValues cv = new ContentValues();
                                            cv.put("nombre", cartera.get(i).getNombre());
                                            cv.put("tesorera", cartera.get(i).getTesorero());
                                            cv.put("direccion", cartera.get(i).getDireccion());
                                            cv.put("asesor_nombre", cartera.get(i).getAsesorNombre());
                                            cv.put("serie_id", cartera.get(i).getSerieId());
                                            cv.put("is_ruta", (cartera.get(i).getRuta())?1:0);
                                            cv.put("ruta_obligado", (cartera.get(i).getRuta())?1:0);
                                            cv.put("usuario_id", String.valueOf(cartera.get(i).getUsuarioId()));
                                            cv.put("dia", cartera.get(i).getDia());
                                            cv.put("num_solicitud", cartera.get(i).getNumSolicitud());
                                            cv.put("fecha_actualizado", Miscellaneous.ObtenerFecha(TIMESTAMP));
                                            cv.put("colonia", cartera.get(i).getColonia());
                                            cv.put("geolocalizadas", cartera.get(i).getGeolocalizadas());

                                            if (ENVIROMENT)
                                                db.update(TBL_CARTERA_GPO, cv, "id_cartera = ?", new String[]{String.valueOf(cartera.get(i).getId())});
                                            else
                                                db.update(TBL_CARTERA_GPO_T, cv, "id_cartera = ?", new String[]{String.valueOf(cartera.get(i).getId())});
                                        }
                                        row.close();
                                        break;
                                }//Fin SWITCH
                            } //Fin Ciclo For
                        }//Fin IF
                        GetPrestamos();

                        break;
                    default:
                        Log.e("Mensaje", response.raw().toString());
                        cbCartera.setChecked(true);
                        Intent home = new Intent(ctx, Home.class);
                        home.putExtra("login", false);
                        startActivity(home);
                        finish();
                        break;
                }

            }
            @Override
            public void onFailure(Call<List<MCartera>> call, Throwable t) {
                Log.e("FailCartera", "Fail Cartera"+t.getMessage());
                cbCartera.setChecked(true);
                Intent home = new Intent(ctx, Home.class);
                home.putExtra("login", false);
                startActivity(home);
                finish();

            }
        });
    }

    public void GetPrestamos (){

        final Cursor row;
        String query;
        if (ENVIROMENT)
            query = "SELECT * FROM (SELECT id_cartera, '1' AS tipo FROM " + TBL_CARTERA_IND + " UNION SELECT id_cartera,'2' AS tipo FROM "+TBL_CARTERA_GPO +") AS cartera ";
        else
            query = "SELECT * FROM (SELECT id_cartera,'1' AS tipo FROM " + TBL_CARTERA_IND_T + " UNION SELECT id_cartera,'2' AS tipo FROM "+TBL_CARTERA_GPO_T +") AS cartera ";

        row = db.rawQuery(query, null);

        tvTotal.setText(String.valueOf(row.getCount()));
        final int[] totalClientes = {row.getCount()};
        final int[] clientesActualizados = {0};

        if (row.getCount() > 0){
            row.moveToFirst();

            for (int i = 0; i < row.getCount(); i++){

                switch (row.getInt(1)){
                    case 1:
                        GetPrestamoInd(row.getInt(0), new PrestamoIndCallbacks() {
                            @Override
                            public void onPrestamoInd(boolean mPrestamosInd) {
                                if (mPrestamosInd) {
                                    clientesActualizados[0]++;
                                    QuitarLoading(totalClientes[0], clientesActualizados[0]);
                                }
                            }

                            @Override
                            public void onPrestamoIndFailed(Throwable error) {
                                clientesActualizados[0]++;
                                QuitarLoading(totalClientes[0], clientesActualizados[0]);
                            }
                        });
                        break;
                    case 2:
                        GetPrestmoGpo(row.getInt(0), new PrestamoGpoCallbacks() {
                            @Override
                            public void onPrestamoGpo(boolean mPrestamosGpo) {
                                if (mPrestamosGpo){
                                    clientesActualizados[0]++;
                                    QuitarLoading(totalClientes[0], clientesActualizados[0]);
                                }

                            }

                            @Override
                            public void onPrestamoGpoFailed(Throwable error) {
                                clientesActualizados[0]++;
                                QuitarLoading(totalClientes[0], clientesActualizados[0]);

                            }
                        });
                        break;
                }
                row.moveToNext();
            }
        }
        else{
            row.close();
            QuitarLoading(0,0);
        }
        row.close();

    }

    private void QuitarLoading(int total, int avance){
        tvRegistradas.setText(String.valueOf(avance));
        if (avance == total) {
            cbCartera.setChecked(true);
            Intent home = new Intent(ctx, Home.class);
            home.putExtra("login", false);
            startActivity(home);
            finish();
        }
    }

    private void GetPrestamoInd(final int id, final PrestamoIndCallbacks prestamoCallbacks){
        ManagerInterface api = new RetrofitClient().generalRF(Constants.CONTROLLER_MOVIL, ctx).create(ManagerInterface.class);
        Call<List<MPrestamoRes>> call = api.getPrestamosInd(id,"Bearer "+ session.getUser().get(7));
        call.enqueue(new Callback<List<MPrestamoRes>>() {
            @Override
            public void onResponse(Call<List<MPrestamoRes>> call, Response<List<MPrestamoRes>> response) {
                Log.e("ind","id_carteta: "+id+ " code"+response.code());
                if (response.code() == 200) {
                    List<MPrestamoRes> prestamos = response.body();
                    if (prestamos.size() > 0){

                            Cursor row;

                            Log.e("size antes for", ""+prestamos.size());
                            for (int i = 0; i < prestamos.size(); i++){
                                Log.e("Count i", ""+i);
                                Log.e("id_prestamo", ""+prestamos.get(i).getId());
                                String where = " WHERE id_prestamo = ?";
                                String order = "";
                                String[] args =  new String[] {String.valueOf(prestamos.get(i).getId())};

                                if (ENVIROMENT)
                                    row = dBhelper.getRecords(TBL_PRESTAMOS_IND, where, order, args);
                                else
                                    row = dBhelper.getRecords(TBL_PRESTAMOS_IND_T, where, order, args);

                                if (row.getCount() == 0){ //Registra el prestamo de ind
                                    row.close();
                                    Log.e("Prestamo", "Registra Prestamo");
                                    HashMap<Integer, String> values = new HashMap<>();
                                    values.put(0, String.valueOf(prestamos.get(i).getId()));                    //ID PRESTAMO
                                    values.put(1, String.valueOf(prestamos.get(i).getClienteId()));             //CLIENTE ID
                                    values.put(2, prestamos.get(i).getNumPrestamo());                           //NUM_PRESTAMO
                                    values.put(3, String.valueOf(prestamos.get(i).getNumSolicitud()));          //NUM_SOLICITUD
                                    values.put(4, prestamos.get(i).getFechaEntrega());                          //FECHA_ENTREGA
                                    values.put(5, String.valueOf(prestamos.get(i).getMontoOtorgado()));         //MONTO OTORGADO
                                    values.put(6, String.valueOf(prestamos.get(i).getMontoTotal()));            //MONTO TOTAL
                                    values.put(7, String.valueOf(prestamos.get(i).getMontoAmortizacion()));     //MONTO AMORTIZACION
                                    values.put(8, String.valueOf(prestamos.get(i).getMontoRequerido()));        //MONTO REQUERIDO
                                    values.put(9, String.valueOf(prestamos.get(i).getNumAmortizacion()));       //NUM AMORTIZACION
                                    values.put(10, prestamos.get(i).getFechaEstablecida());                     //FECHA ESTABLECIDA
                                    values.put(11, prestamos.get(i).getTipoCartera());                          //TIPO CARTERA
                                    values.put(12, (prestamos.get(i).getPagada())?"1":"0");                     //PAGADA
                                    values.put(13, Miscellaneous.ObtenerFecha(TIMESTAMP));                      //FECHA CREACION
                                    values.put(14, Miscellaneous.ObtenerFecha(TIMESTAMP));                      //FECHA ACTUALIZACION

                                    Log.e("ParamsPres", values.toString());
                                    Log.e("--","-----------------------------------------------------");
                                    if (ENVIROMENT)
                                        dBhelper.savePrestamosInd(db, TBL_PRESTAMOS_IND, values);
                                    else
                                        dBhelper.savePrestamosInd(db, TBL_PRESTAMOS_IND_T, values);

                                    if (prestamos.get(i).getAval() != null) {
                                        MAval mAval = prestamos.get(i).getAval();
                                        HashMap<Integer, String> values_aval = new HashMap<>();
                                        values_aval.put(0, String.valueOf(prestamos.get(i).getId()));            //ID PRESTAMO
                                        values_aval.put(1, String.valueOf(mAval.getId()));                       //AVAL ID
                                        values_aval.put(2, mAval.getNombre());                                   //NOMBRE
                                        values_aval.put(3, mAval.getParentesco());                               //PARENTESCO
                                        values_aval.put(4, mAval.getDireccion());                                //DIRECCIO
                                        values_aval.put(5, mAval.getTelefono());                                 //TELEFONO
                                        values_aval.put(6, Miscellaneous.ObtenerFecha(TIMESTAMP));               //FECHA CREACION
                                        values_aval.put(7, Miscellaneous.ObtenerFecha(TIMESTAMP));               //FECHA ACTUALIZACION

                                        if (ENVIROMENT)
                                            dBhelper.saveAval(db, TBL_AVAL, values_aval);
                                        else
                                            dBhelper.saveAval(db, TBL_AVAL_T, values_aval);

                                    }

                                    if (prestamos.get(i).getAmortizaciones().size() > 0){
                                        for (int j = 0; j < prestamos.get(i).getAmortizaciones().size(); j++){
                                            MAmortizacion mAmortizacion = prestamos.get(i).getAmortizaciones().get(j);
                                            HashMap<Integer, String> values_amortiz = new HashMap<>();
                                            values_amortiz.put(0, String.valueOf(mAmortizacion.getId()));                                   //ID AMORTIZACION
                                            values_amortiz.put(1, String.valueOf(mAmortizacion.getPrestamoId()));                           //ID PRESTAMOS
                                            values_amortiz.put(2, mAmortizacion.getFecha());                                                //FECHA
                                            values_amortiz.put(3, (mAmortizacion.getFechaPago() != null)?mAmortizacion.getFechaPago():"");  //FECHA PAGO
                                            values_amortiz.put(4, String.valueOf(mAmortizacion.getCapital()));                              //CAPITAL
                                            values_amortiz.put(5, String.valueOf(mAmortizacion.getInteres()));                              //INTERES
                                            values_amortiz.put(6, String.valueOf(mAmortizacion.getIva()));                                  //IVA
                                            values_amortiz.put(7, String.valueOf(mAmortizacion.getComision()));                             //COMISION
                                            values_amortiz.put(8, String.valueOf(mAmortizacion.getTotal()));                                //TOTAL
                                            values_amortiz.put(9, String.valueOf(mAmortizacion.getCapitalPagado()));                        //CAPITAL PAGADO
                                            values_amortiz.put(10, String.valueOf(mAmortizacion.getInteresPagado()));                       //INTERES PAGADO
                                            values_amortiz.put(11, String.valueOf(mAmortizacion.getIvaPagado()));                           //IVA PAGADO
                                            values_amortiz.put(12, String.valueOf(mAmortizacion.getInteresMoratorioPagado()));              //INTERES MORATORIO PAGADO
                                            values_amortiz.put(13, String.valueOf(mAmortizacion.getIvaMoratorioPagado()));                  //IVA_MORATORIO PAGADO
                                            values_amortiz.put(14, String.valueOf(mAmortizacion.getComisionPagada()));                      //COMISION PAGADA
                                            values_amortiz.put(15, String.valueOf(mAmortizacion.getTotalPagado()));                         //TOTAL PAGADO
                                            values_amortiz.put(16, String.valueOf(mAmortizacion.getPagado()));                              //PAGADO
                                            values_amortiz.put(17, String.valueOf(mAmortizacion.getNumero()));                              //NUMERO
                                            values_amortiz.put(18, String.valueOf(mAmortizacion.getDiasAtraso()));                          //DIAS ATRASO
                                            values_amortiz.put(19, Miscellaneous.ObtenerFecha(TIMESTAMP));                                  //FECHA DISPOSITIVO
                                            values_amortiz.put(20, Miscellaneous.ObtenerFecha(TIMESTAMP));                                  //FECHA ACTUALIZADO

                                            if (ENVIROMENT)
                                                dBhelper.saveAmortizaciones(db, TBL_AMORTIZACIONES, values_amortiz);
                                            else
                                                dBhelper.saveAmortizaciones(db, TBL_AMORTIZACIONES_T, values_amortiz);
                                        }
                                    }

                                    if (prestamos.get(i).getPagos() != null && prestamos.get(i).getPagos().size() > 0){
                                        for (int k = 0; k < prestamos.get(i).getPagos().size(); k++){
                                            MPago mPago = prestamos.get(i).getPagos().get(k);
                                            HashMap<Integer, String> values_pago = new HashMap<>();
                                            values_pago.put(0, String.valueOf(prestamos.get(i).getId()));            //ID PRESTAMO
                                            values_pago.put(1, mPago.getFecha());                                    //FECHA
                                            values_pago.put(2, String.valueOf(mPago.getMonto()));                    //MONTO
                                            values_pago.put(3,mPago.getBanco());                                     //BANCO
                                            values_pago.put(4, Miscellaneous.ObtenerFecha(TIMESTAMP));               //FECHA DISPOSITIVO
                                            values_pago.put(5, Miscellaneous.ObtenerFecha(TIMESTAMP));               //FECHA ACTUALIZADO

                                            if (ENVIROMENT)
                                                dBhelper.savePagos(db, TBL_PAGOS, values_pago);
                                            else
                                                dBhelper.savePagos(db, TBL_PAGOS_T, values_pago);
                                        }
                                    }
                                }
                                else{ //Actualiza la cartera de ind
                                    row.moveToFirst();
                                    ContentValues cv = new ContentValues();
                                    cv.put("fecha_entrega", prestamos.get(i).getFechaEntrega());
                                    cv.put("monto_otorgado", String.valueOf(prestamos.get(i).getMontoOtorgado()));
                                    cv.put("monto_total", String.valueOf(prestamos.get(i).getMontoTotal()));
                                    cv.put("monto_amortizacion", String.valueOf(prestamos.get(i).getMontoAmortizacion()));
                                    cv.put("monto_requerido", String.valueOf(prestamos.get(i).getMontoRequerido()));
                                    cv.put("num_amortizacion", String.valueOf(prestamos.get(i).getNumAmortizacion()));
                                    cv.put("fecha_establecida", prestamos.get(i).getFechaEstablecida());
                                    cv.put("tipo_cartera", prestamos.get(i).getTipoCartera());

                                    if (row.getInt(13) == 0)
                                        cv.put("pagada", (prestamos.get(i).getPagada())?"1":"0");
                                    cv.put("fecha_actualizado", Miscellaneous.ObtenerFecha("timestamp"));

                                    db.update((ENVIROMENT)?TBL_PRESTAMOS_IND:TBL_PRESTAMOS_IND_T, cv,
                                            "_id = ?", new String[]{row.getString(0)});

                                    String sql = "SELECT * FROM " + TBL_RESPUESTAS_IND_T + " WHERE id_prestamo = ? AND contacto = ? AND resultado_gestion = ?";
                                    Cursor rowRuta = db.rawQuery(sql, new String[]{String.valueOf(prestamos.get(i).getId()), "SI", "PAGO"});

                                    if (rowRuta.getCount() > 0){
                                        rowRuta.moveToFirst();
                                        int weekFechaEst = 0;
                                        Calendar calFechaEst = Calendar.getInstance();

                                        try {
                                            Date dFechaEstablecida = sdf.parse(Miscellaneous.ObtenerFecha(FECHA.toLowerCase()));
                                            calFechaEst.setTime(dFechaEstablecida);
                                            weekFechaEst = calFechaEst.get(Calendar.WEEK_OF_YEAR);

                                        } catch (ParseException e) {
                                            e.printStackTrace();
                                        }

                                        Log.e("NumeroWeek ", ": "+weekFechaEst);

                                        double sumPago = 0;
                                        for (int r = 0; r < rowRuta.getCount(); r++){
                                            String[] fechaIni = rowRuta.getString(22).split(" ");
                                            Date dFechaEstablecida = null;
                                            try {
                                                dFechaEstablecida = sdf.parse(fechaIni[0]);
                                                calFechaEst.setTime(dFechaEstablecida);

                                                if (calFechaEst.get(Calendar.WEEK_OF_YEAR) == weekFechaEst){
                                                    sumPago += rowRuta.getDouble(15);
                                                }
                                            } catch (ParseException e) {
                                                e.printStackTrace();
                                            }

                                            rowRuta.moveToNext();
                                        }
                                        try {
                                            if (sumPago >= prestamos.get(i).getMontoRequerido()){
                                                ContentValues cvInd = new ContentValues();
                                                cvInd.put("is_ruta", 0);
                                                cvInd.put("ruta_obligado", 0);

                                                db.update(TBL_CARTERA_IND_T, cvInd, "id_cartera = ?", new String[]{row.getString(2)});
                                            }
                                        }catch (NumberFormatException e){

                                        }

                                    }
                                    rowRuta.close();

                                    if (prestamos.get(i).getAval() != null) {

                                        MAval mAval = prestamos.get(i).getAval();
                                        ContentValues cv_aval = new ContentValues();
                                        cv_aval.put("id_aval", String.valueOf(mAval.getId()));                      //AVAL ID
                                        cv_aval.put("nombre", mAval.getNombre());                                   //NOMBRE
                                        cv_aval.put("parentesco", mAval.getParentesco());                           //PARENTESCO
                                        cv_aval.put("direccion", mAval.getDireccion());                             //DIRECCIO
                                        cv_aval.put("telefono", mAval.getTelefono());                               //TELEFONO
                                        cv_aval.put("fecha_actualizado", Miscellaneous.ObtenerFecha(TIMESTAMP));    //FECHA ACTUALIZACION

                                        db.update((ENVIROMENT)?TBL_AVAL:TBL_AVAL_T, cv_aval,
                                                "id_prestamo = ?", new String[]{String.valueOf(prestamos.get(i).getId())});
                                    }

                                    if (prestamos.get(i).getAmortizaciones().size() > 0){
                                        for (int j = 0; j < prestamos.get(i).getAmortizaciones().size(); j++){
                                            MAmortizacion mAmortizacion = prestamos.get(i).getAmortizaciones().get(j);
                                            ContentValues cv_amortiz = new ContentValues();
                                            cv_amortiz.put("fecha", mAmortizacion.getFecha());                                                      //FECHA
                                            cv_amortiz.put("fecha_pago", (mAmortizacion.getFechaPago() != null)?mAmortizacion.getFechaPago():"");   //FECHA PAGO
                                            cv_amortiz.put("capital", String.valueOf(mAmortizacion.getCapital()));                                  //CAPITAL
                                            cv_amortiz.put("interes", String.valueOf(mAmortizacion.getInteres()));                                  //INTERES
                                            cv_amortiz.put("iva", String.valueOf(mAmortizacion.getIva()));                                          //IVA
                                            cv_amortiz.put("comision", String.valueOf(mAmortizacion.getComision()));                                //COMISION
                                            cv_amortiz.put("total", String.valueOf(mAmortizacion.getTotal()));                                      //TOTAL
                                            cv_amortiz.put("capital_pagado", String.valueOf(mAmortizacion.getCapitalPagado()));                     //CAPITAL PAGADO
                                            cv_amortiz.put("interes_pagado", String.valueOf(mAmortizacion.getInteresPagado()));                     //INTERES PAGADO
                                            cv_amortiz.put("iva_pagado", String.valueOf(mAmortizacion.getIvaPagado()));                             //IVA PAGADO
                                            cv_amortiz.put("interes_moratorio_pagado", String.valueOf(mAmortizacion.getInteresMoratorioPagado()));  //INTERES MORATORIO PAGADO
                                            cv_amortiz.put("iva_moratorio_pagado", String.valueOf(mAmortizacion.getIvaMoratorioPagado()));          //IVA_MORATORIO PAGADO
                                            cv_amortiz.put("comision_pagada", String.valueOf(mAmortizacion.getComisionPagada()));                   //COMISION PAGADA
                                            String sqlAmortiz = "SELECT total, total_pagado FROM " + TBL_AMORTIZACIONES_T + " WHERE id_amortizacion = ? AND id_prestamo = ?";
                                            Cursor rowAmortiz = db.rawQuery(sqlAmortiz, new String[]{String.valueOf(mAmortizacion.getId()), String.valueOf(prestamos.get(i).getId())});
                                            if (rowAmortiz.getCount() > 0) {
                                                rowAmortiz.moveToFirst();
                                                if (rowAmortiz.getDouble(0) > rowAmortiz.getDouble(1))
                                                cv_amortiz.put("total_pagado", String.valueOf(mAmortizacion.getTotalPagado()));                         //TOTAL PAGADO
                                                cv_amortiz.put("pagado", String.valueOf(mAmortizacion.getPagado()));                                    //PAGADO
                                                cv_amortiz.put("numero", String.valueOf(mAmortizacion.getNumero()));                                    //NUMERO
                                                cv_amortiz.put("dias_atraso", String.valueOf(mAmortizacion.getDiasAtraso()));                           //DIAS ATRASO
                                            }
                                            rowAmortiz.close();
                                            cv_amortiz.put("fecha_actualizado", Miscellaneous.ObtenerFecha(TIMESTAMP));                             //FECHA ACTUALIZADO

                                            db.update((ENVIROMENT)?TBL_AMORTIZACIONES:TBL_AMORTIZACIONES_T, cv_amortiz,
                                                    "id_amortizacion = ? AND id_prestamo = ?", new String[]{String.valueOf(mAmortizacion.getId()), String.valueOf(prestamos.get(i).getId())});
                                        }
                                    }

                                    if (prestamos.get(i).getPagos() != null && prestamos.get(i).getPagos().size() > 0){
                                        for (int k = 0; k < prestamos.get(i).getPagos().size(); k++){
                                            MPago mPago = prestamos.get(i).getPagos().get(k);
                                            Cursor row_pago = dBhelper.getRecords((ENVIROMENT)?TBL_PAGOS:TBL_PAGOS_T, " WHERE id_prestamo = ? AND fecha = ? AND monto = ? AND banco = ?", "",
                                                    new String[]{String.valueOf(prestamos.get(i).getId()),mPago.getFecha(), String.valueOf(mPago.getMonto()), mPago.getBanco(),});
                                            if (row_pago.getCount() == 0){
                                                HashMap<Integer, String> cv_pago = new HashMap<>();
                                                cv_pago.put(0, String.valueOf(prestamos.get(i).getId()));            //ID PRESTAMO
                                                cv_pago.put(1, mPago.getFecha());                                    //FECHA
                                                cv_pago.put(2, String.valueOf(mPago.getMonto()));                    //MONTO
                                                cv_pago.put(3,mPago.getBanco());                                     //BANCO
                                                cv_pago.put(4, Miscellaneous.ObtenerFecha(TIMESTAMP));               //FECHA DISPOSITIVO
                                                cv_pago.put(5, Miscellaneous.ObtenerFecha(TIMESTAMP));               //FECHA ACTUALIZADO

                                                dBhelper.savePagos(db, (ENVIROMENT)?TBL_PAGOS:TBL_PAGOS_T, cv_pago);
                                            }
                                            row_pago.close();
                                        }
                                    }
                                }
                                row.close();
                            } //Fin Ciclo For
                        }//Fin IF
                    else{
                        ContentValues cv = new ContentValues();
                        cv.put("estatus", "0");
                        db.update(TBL_CARTERA_IND_T, cv, "id_cartera = ?", new String[]{String.valueOf(id)});
                    }
                    if (prestamoCallbacks != null)
                        prestamoCallbacks.onPrestamoInd(true);
                }

            }

            @Override
            public void onFailure(Call<List<MPrestamoRes>> call, Throwable t) {
                if (prestamoCallbacks != null)
                    prestamoCallbacks.onPrestamoIndFailed(t);
            }
        });
    }

    private void GetPrestmoGpo(final int id, final PrestamoGpoCallbacks prestamoGpoCallbacks){
        ManagerInterface api = new RetrofitClient().generalRF(Constants.CONTROLLER_MOVIL, ctx).create(ManagerInterface.class);
        Call<List<MPrestamoGpoRes>> call = api.getPrestamosGpo(id,"Bearer "+ session.getUser().get(7));
        call.enqueue(new Callback<List<MPrestamoGpoRes>>() {
            @Override
            public void onResponse(Call<List<MPrestamoGpoRes>> call, Response<List<MPrestamoGpoRes>> response) {
                Log.e("gpo","id_carteta: "+id+ " code"+response.code());
                if (response.code() == 200){
                    List<MPrestamoGpoRes> prestamos = response.body();
                    if (prestamos.size() > 0){

                        Cursor row;

                        Log.e("size antes for", ""+prestamos.size());
                        for (int i = 0; i < prestamos.size(); i++){
                            Log.e("Count i", ""+i);
                            Log.e("id_prestamo", ""+prestamos.get(i).getId());
                            String where = " WHERE id_prestamo = ?";
                            String order = "";
                            String[] args =  new String[] {String.valueOf(prestamos.get(i).getId())};

                            if (ENVIROMENT)
                                row = dBhelper.getRecords(TBL_PRESTAMOS_GPO, where, order, args);
                            else
                                row = dBhelper.getRecords(TBL_PRESTAMOS_GPO_T, where, order, args);

                            if (row.getCount() == 0){ //Registra el prestamo de gpo
                                Log.e("Prestamo", "Registra Prestamo");
                                HashMap<Integer, String> values = new HashMap<>();
                                values.put(0, String.valueOf(prestamos.get(i).getId()));                    //ID PRESTAMO
                                values.put(1, String.valueOf(prestamos.get(i).getGrupoId()));               //GRUPO ID
                                values.put(2, prestamos.get(i).getNumPrestamo());                           //NUM_PRESTAMO
                                values.put(3, String.valueOf(prestamos.get(i).getNumSolicitud()));          //NUM_SOLICITUD
                                values.put(4, prestamos.get(i).getFechaEntrega());                          //FECHA_ENTREGA
                                values.put(5, String.valueOf(prestamos.get(i).getMontoOtorgado()));         //MONTO OTORGADO
                                values.put(6, String.valueOf(prestamos.get(i).getMontoTotal()));            //MONTO TOTAL
                                values.put(7, String.valueOf(prestamos.get(i).getMontoAmortizacion()));     //MONTO AMORTIZACION
                                values.put(8, String.valueOf(prestamos.get(i).getMontoRequerido()));        //MONTO REQUERIDO
                                values.put(9, String.valueOf(prestamos.get(i).getNumAmortizacion()));       //NUM AMORTIZACION
                                values.put(10, prestamos.get(i).getFechaEstablecida());                     //FECHA ESTABLECIDA
                                values.put(11, prestamos.get(i).getTipoCartera());                          //TIPO CARTERA
                                values.put(12, (prestamos.get(i).getPagada().equals("PAGADA"))?"1":"0");    //PAGADA
                                values.put(13, Miscellaneous.ObtenerFecha(TIMESTAMP));                      //FECHA CREACION
                                values.put(14, Miscellaneous.ObtenerFecha(TIMESTAMP));                      //FECHA ACTUALIZACION

                                if (ENVIROMENT)
                                    dBhelper.savePrestamosGpo(db, TBL_PRESTAMOS_GPO, values);
                                else
                                    dBhelper.savePrestamosGpo(db, TBL_PRESTAMOS_GPO_T, values);

                                if (prestamos.get(i).getIntegrantes() != null) {
                                    for (int l = 0; l < prestamos.get(i).getIntegrantes().size(); l++){
                                        MIntegrante mIntegrante = prestamos.get(i).getIntegrantes().get(l);
                                        Log.e("clave", mIntegrante.getClave()+" -----");
                                        Log.e("prestamoIntegrante", mIntegrante.getPrestamoId()+" -----");
                                        HashMap<Integer, String> values_miembro = new HashMap<>();
                                        values_miembro.put(0, String.valueOf(prestamos.get(i).getId()));            //ID PRESTAMO
                                        values_miembro.put(1, String.valueOf(mIntegrante.getId()));                 //INTEGRANTE ID
                                        values_miembro.put(2, String.valueOf(mIntegrante.getNumSolicitud()));       //NUM SOLICITUD
                                        values_miembro.put(3, String.valueOf(mIntegrante.getGrupoId()));            //GRUPO ID
                                        values_miembro.put(4, mIntegrante.getNombre());                             //NOMBRE
                                        values_miembro.put(5, mIntegrante.getDireccion());                          //DIRECCION
                                        values_miembro.put(6, mIntegrante.getTelCasa());                            //TEL CASA
                                        values_miembro.put(7, mIntegrante.getTelCelular());                         //TEL CELULAR
                                        values_miembro.put(8, mIntegrante.getTipo());                               //TIPO
                                        values_miembro.put(9, mIntegrante.getMontoPrestamo());                      //MONTO PRESTAMO
                                        values_miembro.put(10, mIntegrante.getMontoRequerido());                     //MONTO REQUERIDO
                                        values_miembro.put(11, Miscellaneous.ObtenerFecha(TIMESTAMP));               //FECHA CREACION
                                        values_miembro.put(12, Miscellaneous.ObtenerFecha(TIMESTAMP));               //FECHA ACTUALIZACION
                                        values_miembro.put(13, mIntegrante.getClave());                              //CLAVE
                                        values_miembro.put(14, String.valueOf(mIntegrante.getPrestamoId()));         //ID PRESTAMO
                                        values_miembro.put(15, "");                                                  //CURP
                                        //values_miembro.put(15, mIntegrante.getCurp());                               //CURP
                                        dBhelper.saveMiembros(db, values_miembro);

                                    }
                                }

                                if (prestamos.get(i).getAmortizaciones().size() > 0){
                                    for (int j = 0; j < prestamos.get(i).getAmortizaciones().size(); j++){
                                        MAmortizacion mAmortizacion = prestamos.get(i).getAmortizaciones().get(j);
                                        HashMap<Integer, String> values_amortiz = new HashMap<>();
                                        values_amortiz.put(0, String.valueOf(mAmortizacion.getId()));                      //ID AMORTIZACION
                                        values_amortiz.put(1, String.valueOf(mAmortizacion.getPrestamoId()));              //ID PRESTAMOS
                                        values_amortiz.put(2, mAmortizacion.getFecha());                                   //FECHA
                                        values_amortiz.put(3, (mAmortizacion.getFechaPago() != null)?mAmortizacion.getFechaPago():"");        //FECHA PAGO
                                        values_amortiz.put(4, String.valueOf(mAmortizacion.getCapital()));                 //CAPITAL
                                        values_amortiz.put(5, String.valueOf(mAmortizacion.getInteres()));                 //INTERES
                                        values_amortiz.put(6, String.valueOf(mAmortizacion.getIva()));                     //IVA
                                        values_amortiz.put(7, String.valueOf(mAmortizacion.getComision()));                //COMISION
                                        values_amortiz.put(8, String.valueOf(mAmortizacion.getTotal()));                   //TOTAL
                                        values_amortiz.put(9, String.valueOf(mAmortizacion.getCapitalPagado()));           //CAPITAL PAGADO
                                        values_amortiz.put(10, String.valueOf(mAmortizacion.getInteresPagado()));          //INTERES PAGADO
                                        values_amortiz.put(11, String.valueOf(mAmortizacion.getIvaPagado()));              //IVA PAGADO
                                        values_amortiz.put(12, String.valueOf(mAmortizacion.getInteresMoratorioPagado())); //INTERES MORATORIO PAGADO
                                        values_amortiz.put(13, String.valueOf(mAmortizacion.getIvaMoratorioPagado()));     //IVA_MORATORIO PAGADO
                                        values_amortiz.put(14, String.valueOf(mAmortizacion.getComisionPagada()));         //COMISION PAGADA
                                        values_amortiz.put(15, String.valueOf(mAmortizacion.getTotalPagado()));            //TOTAL PAGADO
                                        values_amortiz.put(16, String.valueOf(mAmortizacion.getPagado()));                 //PAGADO
                                        values_amortiz.put(17, String.valueOf(mAmortizacion.getNumero()));                 //NUMERO
                                        values_amortiz.put(18, String.valueOf(mAmortizacion.getDiasAtraso()));             //DIAS ATRASO
                                        values_amortiz.put(19, Miscellaneous.ObtenerFecha(TIMESTAMP));                     //FECHA DISPOSITIVO
                                        values_amortiz.put(20, Miscellaneous.ObtenerFecha(TIMESTAMP));                     //FECHA ACTUALIZADO

                                        if (ENVIROMENT)
                                            dBhelper.saveAmortizaciones(db, TBL_AMORTIZACIONES, values_amortiz);
                                        else
                                            dBhelper.saveAmortizaciones(db, TBL_AMORTIZACIONES_T, values_amortiz);
                                    }
                                }

                                if (prestamos.get(i).getPagos() != null &&prestamos.get(i).getPagos().size() > 0){
                                    for (int k = 0; k < prestamos.get(i).getPagos().size(); k++){
                                        MPago mPago = prestamos.get(i).getPagos().get(k);
                                        HashMap<Integer, String> values_pago = new HashMap<>();
                                        values_pago.put(0, String.valueOf(prestamos.get(i).getId()));            //ID PRESTAMO
                                        values_pago.put(1, mPago.getFecha());                                    //FECHA
                                        values_pago.put(2, String.valueOf(mPago.getMonto()));                    //MONTO
                                        values_pago.put(3,mPago.getBanco());                                     //BANCO
                                        values_pago.put(4, Miscellaneous.ObtenerFecha(TIMESTAMP));               //FECHA DISPOSITIVO
                                        values_pago.put(5, Miscellaneous.ObtenerFecha(TIMESTAMP));               //FECHA ACTUALIZADO

                                        if (ENVIROMENT)
                                            dBhelper.savePagos(db, TBL_PAGOS, values_pago);
                                        else
                                            dBhelper.savePagos(db, TBL_PAGOS_T, values_pago);
                                    }
                                }
                            }
                            else{ //Actualiza la prestamo gpo
                                row.moveToFirst();
                                ContentValues cv_prestamo = new ContentValues();
                                cv_prestamo.put("fecha_entrega", prestamos.get(i).getFechaEntrega());                          //FECHA_ENTREGA
                                cv_prestamo.put("monto_otorgado", String.valueOf(prestamos.get(i).getMontoOtorgado()));         //MONTO OTORGADO
                                cv_prestamo.put("monto_total", String.valueOf(prestamos.get(i).getMontoTotal()));            //MONTO TOTAL
                                cv_prestamo.put("monto_amortizacion", String.valueOf(prestamos.get(i).getMontoAmortizacion()));     //MONTO AMORTIZACION
                                cv_prestamo.put("monto_requerido", String.valueOf(prestamos.get(i).getMontoRequerido()));        //MONTO REQUERIDO
                                cv_prestamo.put("num_amortizacion", String.valueOf(prestamos.get(i).getNumAmortizacion()));       //NUM AMORTIZACION
                                cv_prestamo.put("fecha_establecida", prestamos.get(i).getFechaEstablecida());                     //FECHA ESTABLECIDA
                                cv_prestamo.put("tipo_cartera", prestamos.get(i).getTipoCartera());                          //TIPO CARTERA
                                if (row.getInt(13) == 0)
                                    cv_prestamo.put("pagada", (prestamos.get(i).getPagada().equals("PAGADA"))?"1":"0");                     //PAGADA
                                cv_prestamo.put("fecha_actualizado", Miscellaneous.ObtenerFecha(TIMESTAMP));                      //FECHA ACTUALIZACION

                                db.update((ENVIROMENT)?TBL_PRESTAMOS_GPO:TBL_PRESTAMOS_GPO_T, cv_prestamo,
                                        "_id = ?", new String[]{row.getString(0)});

                                String sql = "SELECT * FROM " + TBL_RESPUESTAS_GPO_T + " WHERE id_prestamo = ? AND contacto = ? AND resultado_gestion = ?";
                                Cursor rowRuta = db.rawQuery(sql, new String[]{String.valueOf(prestamos.get(i).getId()), "SI", "PAGO"});

                                Log.e("RowRutaTotal", rowRuta.getCount()+" XD");
                                if (rowRuta.getCount() > 0){
                                    rowRuta.moveToFirst();
                                    int weekFechaEst = 0;
                                    Calendar calFechaEst = Calendar.getInstance();

                                    try {
                                        Date dFechaEstablecida = sdf.parse(Miscellaneous.ObtenerFecha(FECHA.toLowerCase()));
                                        calFechaEst.setTime(dFechaEstablecida);
                                        weekFechaEst = calFechaEst.get(Calendar.WEEK_OF_YEAR);

                                    } catch (ParseException e) {
                                        e.printStackTrace();
                                    }

                                    double sumPago = 0;
                                    for (int r = 0; r < rowRuta.getCount(); r++){
                                        String[] fechaIni = rowRuta.getString(22).split(" ");
                                        Date dFechaEstablecida = null;
                                        try {
                                            dFechaEstablecida = sdf.parse(fechaIni[0]);
                                            calFechaEst.setTime(dFechaEstablecida);
                                            if (calFechaEst.get(Calendar.WEEK_OF_YEAR) == weekFechaEst){
                                                sumPago += rowRuta.getDouble(15);
                                            }
                                        } catch (ParseException e) {
                                            e.printStackTrace();
                                        }

                                        rowRuta.moveToNext();
                                    }
                                    try {
                                        if (sumPago >= prestamos.get(i).getMontoRequerido()){
                                            ContentValues cvGpo = new ContentValues();
                                            cvGpo.put("is_ruta", 0);
                                            cvGpo.put("ruta_obligado", 0);

                                            db.update(TBL_CARTERA_GPO_T, cvGpo, "id_cartera = ?", new String[]{String.valueOf(row.getString(2))});
                                        }
                                    }catch (NumberFormatException e){

                                    }
                                }
                                rowRuta.close();

                                if (prestamos.get(i).getIntegrantes() != null) {
                                    for (int l = 0; l < prestamos.get(i).getIntegrantes().size(); l++){
                                        MIntegrante mIntegrante = prestamos.get(i).getIntegrantes().get(l);
                                        ContentValues cv_miembro = new ContentValues();
                                        cv_miembro.put("nombre", mIntegrante.getNombre());                                      //NOMBRE
                                        cv_miembro.put("direccion", mIntegrante.getDireccion());                                //DIRECCION
                                        cv_miembro.put("tel_casa", mIntegrante.getTelCasa());                                   //TEL CASA
                                        cv_miembro.put("tel_celular", mIntegrante.getTelCelular());                             //TEL CELULAR
                                        cv_miembro.put("tipo_integrante", mIntegrante.getTipo());                               //TIPO
                                        cv_miembro.put("monto_prestamo", mIntegrante.getMontoPrestamo());                       //MONTO PRESTAMO
                                        cv_miembro.put("monto_requerido", mIntegrante.getMontoRequerido());                     //MONTO REQUERIDO
                                        cv_miembro.put("fecha_actualizado", Miscellaneous.ObtenerFecha(TIMESTAMP));             //FECHA ACTUALIZACION
                                        cv_miembro.put("clave", mIntegrante.getClave());                                        //CLAVE
                                        cv_miembro.put("id_prestamo_integrante", String.valueOf(mIntegrante.getPrestamoId()));  //PRESTAMO ID
                                        cv_miembro.put("curp", "");                                                             //CURP
                                        //cv_miembro.put("curp", mIntegrante.getCurp());                                          //CURP

                                        db.update((ENVIROMENT)?TBL_MIEMBROS_GPO:TBL_MIEMBROS_GPO_T, cv_miembro,
                                                "id_prestamo = ? AND id_integrante = ?",
                                                new String[]{String.valueOf(prestamos.get(i).getId()), String.valueOf(mIntegrante.getId())});
                                    }
                                }//Termina If de actualizado de integrantes

                                if (prestamos.get(i).getAmortizaciones().size() > 0){
                                    for (int j = 0; j < prestamos.get(i).getAmortizaciones().size(); j++) {
                                        MAmortizacion mAmortizacion = prestamos.get(i).getAmortizaciones().get(j);
                                        ContentValues cv_amortiz = new ContentValues();
                                        cv_amortiz.put("fecha", mAmortizacion.getFecha());                                                //FECHA
                                        cv_amortiz.put("fecha_pago", (mAmortizacion.getFechaPago() != null) ? mAmortizacion.getFechaPago() : "");  //FECHA PAGO
                                        cv_amortiz.put("capital", String.valueOf(mAmortizacion.getCapital()));                              //CAPITAL
                                        cv_amortiz.put("interes", String.valueOf(mAmortizacion.getInteres()));                              //INTERES
                                        cv_amortiz.put("iva", String.valueOf(mAmortizacion.getIva()));                                  //IVA
                                        cv_amortiz.put("comision", String.valueOf(mAmortizacion.getComision()));                             //COMISION
                                        cv_amortiz.put("total", String.valueOf(mAmortizacion.getTotal()));                                //TOTAL
                                        cv_amortiz.put("capital_pagado", String.valueOf(mAmortizacion.getCapitalPagado()));                        //CAPITAL PAGADO
                                        cv_amortiz.put("interes_pagado", String.valueOf(mAmortizacion.getInteresPagado()));                       //INTERES PAGADO
                                        cv_amortiz.put("iva_pagado", String.valueOf(mAmortizacion.getIvaPagado()));                           //IVA PAGADO
                                        cv_amortiz.put("interes_moratorio_pagado", String.valueOf(mAmortizacion.getInteresMoratorioPagado()));              //INTERES MORATORIO PAGADO
                                        cv_amortiz.put("iva_moratorio_pagado", String.valueOf(mAmortizacion.getIvaMoratorioPagado()));                  //IVA_MORATORIO PAGADO
                                        cv_amortiz.put("comision_pagada", String.valueOf(mAmortizacion.getComisionPagada()));                      //COMISION PAGADA
                                        String sqlAmortiz = "SELECT total, total_pagado FROM " + TBL_AMORTIZACIONES_T + " WHERE id_amortizacion = ? AND id_prestamo = ?";
                                        Cursor rowAmortiz = db.rawQuery(sqlAmortiz, new String[]{String.valueOf(mAmortizacion.getId()), String.valueOf(prestamos.get(i).getId())});
                                        if (rowAmortiz.getCount() > 0) {
                                            rowAmortiz.moveToFirst();
                                            if (rowAmortiz.getDouble(0) > rowAmortiz.getDouble(1)){
                                                cv_amortiz.put("total_pagado", String.valueOf(mAmortizacion.getTotalPagado()));                         //TOTAL PAGADO
                                                cv_amortiz.put("pagado", String.valueOf(mAmortizacion.getPagado()));                              //PAGADO
                                                cv_amortiz.put("numero", String.valueOf(mAmortizacion.getNumero()));                              //NUMERO
                                                cv_amortiz.put("dias_atraso", String.valueOf(mAmortizacion.getDiasAtraso()));                          //DIAS ATRASO
                                            }
                                        }
                                        rowAmortiz.close();
                                        cv_amortiz.put("fecha_actualizado", Miscellaneous.ObtenerFecha(TIMESTAMP)); //FECHA ACTUALIZADO

                                        db.update((ENVIROMENT)?TBL_AMORTIZACIONES:TBL_AMORTIZACIONES_T, cv_amortiz,
                                                "id_amortizacion = ? AND id_prestamo = ?", new String[]{String.valueOf(mAmortizacion.getId()), String.valueOf(prestamos.get(i).getId())});
                                    }
                                }//Termina If de Actualizado de amortizaciones

                                if (prestamos.get(i).getPagos() != null &&prestamos.get(i).getPagos().size() > 0){
                                    for (int k = 0; k < prestamos.get(i).getPagos().size(); k++){
                                        MPago mPago = prestamos.get(i).getPagos().get(k);
                                        Log.e("--","................................................");
                                        Log.e("prestamoId", String.valueOf(prestamos.get(i).getId()));
                                        Log.e("fecha", mPago.getFecha());
                                        Log.e("banco", mPago.getBanco());
                                        Log.e("monto", String.valueOf(mPago.getMonto()));
                                        Cursor row_pago = dBhelper.getRecords((ENVIROMENT)?TBL_PAGOS:TBL_PAGOS_T, " WHERE id_prestamo = ? AND fecha = ? AND monto = ? AND banco = ?", "",
                                                new String[]{String.valueOf(prestamos.get(i).getId()),mPago.getFecha(), String.valueOf(mPago.getMonto()),mPago.getBanco()});
                                        Log.e("RowPago",row.getCount()+"asd");
                                        if (row_pago.getCount() == 0){
                                            Log.e("registra","Pago");
                                            HashMap<Integer, String> cv_pago = new HashMap<>();
                                            cv_pago.put(0, String.valueOf(prestamos.get(i).getId()));            //ID PRESTAMO
                                            cv_pago.put(1, mPago.getFecha());                                    //FECHA
                                            cv_pago.put(2, String.valueOf(mPago.getMonto()));                    //MONTO
                                            cv_pago.put(3,mPago.getBanco());                                     //BANCO
                                            cv_pago.put(4, Miscellaneous.ObtenerFecha(TIMESTAMP));               //FECHA DISPOSITIVO
                                            cv_pago.put(5, Miscellaneous.ObtenerFecha(TIMESTAMP));               //FECHA ACTUALIZADO

                                            dBhelper.savePagos(db, (ENVIROMENT)?TBL_PAGOS:TBL_PAGOS_T, cv_pago);
                                        }
                                        row_pago.close();
                                        Log.e("--","...............................................");
                                    }
                                }//Termina If de Actualizar pagos

                            }
                            row.close();
                        } //Fin Ciclo For
                    }//Fin IF
                    else{
                        ContentValues cv = new ContentValues();
                        cv.put("estatus", "0");
                        db.update(TBL_CARTERA_GPO_T, cv, "id_cartera = ?", new String[]{String.valueOf(id)});
                    }
                }

                if (prestamoGpoCallbacks != null)
                    prestamoGpoCallbacks.onPrestamoGpo(true);
            }

            @Override
            public void onFailure(Call<List<MPrestamoGpoRes>> call, Throwable t) {
                if (prestamoGpoCallbacks != null)
                    prestamoGpoCallbacks.onPrestamoGpoFailed(t);
            }
        });
    }

    @Override
    public void onBackPressed() {
        //super.onBackPressed();
    }
}
