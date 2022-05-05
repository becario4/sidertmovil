package com.sidert.sidertmovil.activities;

import android.Manifest;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.location.LocationManager;
import android.os.BatteryManager;
import android.os.Build;
/*import android.support.v4.app.ActivityCompat;
import androidx.appcompat.app.AppCompatActivity;*/
import android.os.Bundle;
import android.util.Log;
import android.widget.CheckBox;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

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
import com.sidert.sidertmovil.models.apoyogastosfunerarios.ApoyoGastosFunerariosResponse;
import com.sidert.sidertmovil.models.cartera.amortizaciones.Amortizacion;
import com.sidert.sidertmovil.models.cartera.amortizaciones.AmortizacionDao;
import com.sidert.sidertmovil.models.documentosclientes.DocumentoCliente;
import com.sidert.sidertmovil.models.documentosclientes.DocumentoClienteDao;
import com.sidert.sidertmovil.services.expedientes.DocumentoClienteService;
import com.sidert.sidertmovil.utils.Constants;
import com.sidert.sidertmovil.utils.ManagerInterface;
import com.sidert.sidertmovil.utils.Miscellaneous;
import com.sidert.sidertmovil.utils.MyCurrentListener;
import com.sidert.sidertmovil.utils.NetworkStatus;
import com.sidert.sidertmovil.utils.RetrofitClient;
import com.sidert.sidertmovil.utils.Servicios_Sincronizado;
import com.sidert.sidertmovil.utils.SessionManager;
import com.sidert.sidertmovil.utils.Sincronizar_Catalogos;

import org.json.JSONObject;

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
import static com.sidert.sidertmovil.utils.Constants.TBL_PRESTAMOS_IND_T;
import static com.sidert.sidertmovil.utils.Constants.TBL_RESPUESTAS_GPO_T;
import static com.sidert.sidertmovil.utils.Constants.TBL_RESPUESTAS_IND_T;
import static com.sidert.sidertmovil.utils.Constants.TBL_TELEFONOS_CLIENTE;
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

    /**Interfaz para descarga de prestamos Individuales*/
    public interface PrestamoIndCallbacks {
        void onPrestamoInd(boolean mPrestamosInd);

        void onPrestamoIndFailed(Throwable error);
    }

    /**Interfaz para descarga de prestamos Grupales*/
    public interface PrestamoGpoCallbacks {
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

        /**Funcion para descargar algunos catalogos o inclusive informacion de prestamos o recibos*/
        GetInformacion();

        /**Funcion para obtener el ultimo folio de impresion vigente o vencida*/
        GetUltimasImpresiones();
    }

    private void GetInformacion() {
        /**Se descargan catalgos que se ocuparan para originacion y renovacion*/
        Sincronizar_Catalogos sc = new Sincronizar_Catalogos();
        sc.GetCategoriasTickets(ctx);
        sc.GetEstados(ctx);

        sc.GetSectores(ctx);
        sc.GetOcupaciones(ctx);
        sc.GetMediosPagoOri(ctx);
        sc.GetNivelesEstudios(ctx);
        sc.GetEstadosCiviles(ctx);
        //sc.GetMediosPagoOri(ctx);
        sc.GetParentesco(ctx);
        sc.GetTipoIdentificacion(ctx);
        sc.GetViviendaTipos(ctx);
        sc.GetMediosContacto(ctx);
        sc.GetDestinosCredito(ctx);
        sc.GetPlazosPrestamo(ctx);

        /**Se descarga informacion como prestamos a renovar, ultimos folios de CC y AGF
         * o prestamos autorizados para autorizar monto*/
        Servicios_Sincronizado ss = new Servicios_Sincronizado();

        ss.SendImpresionesVi(ctx, false);
        ss.SendReimpresionesVi(ctx, false);

        /**Descarga los prestamos autorizados y vigentes para realizar cobros AGF*/
        ss.GetPrestamos(ctx, false);
        /**Descarga el ultimo folio de AGF*/
        ss.GetUltimosRecibos(ctx);
        /**Descarga el ultimo folio de CC*/
        ss.GetUltimosRecibosCC(ctx);

        /**Descarga los prestamos a renovar*/
        ss.GetPrestamosToRenovar(ctx);
        /**Descarga Solicituades Individuales que fueron rechazadas por la admin*/
        ss.GetSolicitudesRechazadasInd(ctx, false);
        /**Descarga Solicituades Grupales que fueron rechazadas por la admin*/
        ss.GetSolicitudesRechazadasGpo(ctx, false);
        /**Descarga los prestamos que fueron autorizados por la admin solo para
         * autorizar el monto*/
        ss.GetPrestamosAutorizados(ctx, false);

        ss.GetGestionesVerDom(ctx, false);
    }

    /**funcion para Descargar el ultimo folio de vigente y vencida
     * ademas como agregar el nivel de bateria y la ubicacion*/
    private void GetUltimasImpresiones() {
        /**Interfaz para la peticion de ultimos recibos*/
        final ManagerInterface api = new RetrofitClient().generalRF(CONTROLLER_API, ctx).create(ManagerInterface.class);

        /**Se obtiene el nivel de bateria*/
        IntentFilter ifilter = new IntentFilter(Intent.ACTION_BATTERY_CHANGED);
        Intent batteryStatus = ctx.registerReceiver(null, ifilter);
        int level = batteryStatus.getIntExtra(BatteryManager.EXTRA_LEVEL, -1);
        int scale = batteryStatus.getIntExtra(BatteryManager.EXTRA_SCALE, -1);
        /**Se calcula el nivel de bateria*/
        final float battery = (level / (float) scale) * 100;
        Log.e("ENTRE AQUI", "ANTES DE LOCATION");
        /**Se obtiene la ubicacion del dispositivo*/
        LocationManager locationManager = (LocationManager) ctx.getSystemService(Context.LOCATION_SERVICE);

        Log.e("ENTRE AQUI", "DESPUES DE LOCATION");
        MyCurrentListener locationListener = new MyCurrentListener(new MyCurrentListener.evento() {
            @Override
            public void onComplete(String latitud, String longitud) {
                Log.e("ENTRE AQUI", latitud);
                /**En caso de obtener la ubicaion se prepara la peticion con los datos
                 * de nivel de bateria, ubicacion, version de la app el serie id*/
                Call<List<MImpresionRes>> call;
                if (!latitud.isEmpty() && !longitud.isEmpty()) {
                    Log.e("ENTRE AQUI", "datos vacios");
                    call = api.getUltimasImpresiones(session.getUser().get(0),
                            String.valueOf(battery),
                            getString(R.string.app_version),
                            latitud,
                            longitud,
                            "Bearer " + session.getUser().get(7));
                } else {
                    Log.e("ENTRE AQUI", "con datos");
                    /**En caso de no obtener la ubicaion se prepara la peticion con los datos
                     * de version de la app el serie id*/
                    call = api.getUltimasImpresiones(session.getUser().get(0),
                            String.valueOf(battery),
                            getString(R.string.app_version),
                            "",
                            "",
                            "Bearer " + session.getUser().get(7));
                }

                /**Se realiza la peticion para obtener el ultimo recibo de vigente y vencida,
                 * ademas guarda version de app, bateria y ubicacion*/
                call.enqueue(new Callback<List<MImpresionRes>>() {
                    @Override
                    public void onResponse(Call<List<MImpresionRes>> call, Response<List<MImpresionRes>> response) {
                        /**Lee el codigo de respuesta de peticion*/
                        switch (response.code()) {
                            case 200:
                                /**Se obtiene el ultimo folio registrado en DB*/
                                List<MImpresionRes> impresiones = response.body();

                                for (MImpresionRes item : impresiones) {
                                    Cursor row;
                                    /**Obtiene el tipo de impresion(Vigente/Vencida) y el numero de prestamo*/
                                    HashMap<Integer, String> data = Miscellaneous.GetNumPrestamo(item.getExternalId());

                                    Log.e("TipoCartera", String.valueOf(item.getTipoCartera()));
                                    if (item.getTipoCartera() == -1) {/**Si el tipo Cartera fue Vigente*/

                                        if (data.get(0).equals("Vigente")) {
                                            /**Se busca esa impresion si ya se tiene guarda en el movil*/
                                            Log.e("nPre folio tipo_imp", data.get(1) + " " + String.valueOf(item.getFolio()) + " " + String.valueOf(item.getTipo()));
                                            row = dBhelper.getRecords(TBL_IMPRESIONES_VIGENTE_T, " WHERE num_prestamo = ?  AND folio = ? AND tipo_impresion = ?", "", new String[]{data.get(1), String.valueOf(item.getFolio()), String.valueOf(item.getTipo())});

                                            /**En caso de existir solo actualizar fecha de envio y estatus*/
                                            if (row.getCount() > 0) {
                                                ContentValues cv = new ContentValues();
                                                cv.put("sent_at", item.getSendedAt());
                                                cv.put("estatus", "1");
                                                db.update(TBL_IMPRESIONES_VIGENTE_T, cv,
                                                        "num_prestamo = ? AND folio = ? AND tipo_impresion = ?", new String[]{
                                                                data.get(1), item.getFolio(), item.getTipo()});

                                            } else {
                                                /**En caso de que NO exista registrar la impresion*/

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
                                                Log.e("NumPrestamo", data.get(1));
                                                params.put(9, data.get(1));                                     //num_prestamo
                                                params.put(10, "");                                             //celular
                                                /**Guardar la impresion en vigente*/
                                                dBhelper.saveImpresiones(db, params);
                                            }
                                            row.close();
                                        }
                                        /**En caso de ser una impresion de vencida*/
                                        else if (data.get(0).equals("Vencida")) {

                                            /**Buscar si existe esa impresion en la tabla de vencida*/
                                            Log.e("nPre folio tipo_imp", data.get(1) + " " + String.valueOf(item.getFolio()) + " " + String.valueOf(item.getTipo()));
                                            row = dBhelper.getRecords(TBL_IMPRESIONES_VENCIDA_T, " WHERE num_prestamo = ?  AND folio = ? AND tipo_impresion = ?", "", new String[]{data.get(1), String.valueOf(item.getFolio()), String.valueOf(item.getTipo())});

                                            /**En caso de existir esa impresion*/
                                            if (row.getCount() > 0) {
                                                /**Actualizar la fecha de envio y el estatus de la impresion*/
                                                ContentValues cv = new ContentValues();
                                                cv.put("sent_at", item.getSendedAt());
                                                cv.put("estatus", "1");
                                                db.update(TBL_IMPRESIONES_VENCIDA_T, cv,
                                                        "num_prestamo = ? AND folio = ? AND tipo_impresion = ?", new String[]{
                                                                data.get(1), item.getFolio(), item.getTipo()});

                                            } else {
                                                /**En caso de No existir registrar la impresion*/
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
                                                Log.e("NumPrestamo", data.get(1));
                                                params.put(9, data.get(1));                                     //num_prestamo
                                                params.put(10, "");                                             //celular
                                                /**Guarda la impresion de vencida en la tabla de vencida*/
                                                dBhelper.saveImpresionesVencida(db, params);
                                            }
                                            row.close();
                                        }
                                    } else {
                                        /**Si tipo cartera en vigente o cobranza*/
                                        if (item.getTipoCartera() == 0 || item.getTipoCartera() == 1) {//VIGENTE

                                            /**Busco la impresion en la tabla de vigente*/
                                            Log.e("nPre folio tipo_imp", data.get(1) + " " + String.valueOf(item.getFolio()) + " " + String.valueOf(item.getTipo()));
                                            row = dBhelper.getRecords(TBL_IMPRESIONES_VIGENTE_T, " WHERE num_prestamo = ? AND folio = ? AND tipo_impresion = ?", "", new String[]{data.get(1), item.getFolio(), item.getTipo()});

                                            /**En caso de existir la impresion en la tabla*/
                                            Log.e("CountRow", String.valueOf(row.getCount()) + " asda");
                                            if (row.getCount() > 0) {
                                                /**Actualiza fecha de envio y estatus de la impresion*/
                                                ContentValues cv = new ContentValues();
                                                cv.put("sent_at", item.getSendedAt());
                                                cv.put("estatus", "1");
                                                db.update(TBL_IMPRESIONES_VIGENTE_T, cv,
                                                        "num_prestamo = ? AND folio = ? AND tipo_impresion = ?", new String[]{
                                                                data.get(1), item.getFolio(), item.getTipo()});

                                            } else {
                                                /**En caso de NO existir registrar la impresion en Vigente*/
                                                HashMap<Integer, String> params = new HashMap<>();
                                                if (item.getNumPrestamoIdGestion().trim().isEmpty())
                                                    params.put(0, data.get(1));  //num_prestamo_id_gestion
                                                else
                                                    params.put(0, String.valueOf(item.getNumPrestamoIdGestion()));  //num_prestamo_id_gestion
                                                params.put(1, item.getAsesorid());                              //asesor_id
                                                params.put(2, item.getFolio());                                 //folioco
                                                params.put(3, item.getTipo());                                  //tipo_impresion
                                                params.put(4, String.valueOf(item.getMontoRealizado()));        //monto
                                                params.put(5, item.getClavecliente());                          //clave_cliente
                                                params.put(6, item.getGeneratedAt());                           //created_at
                                                params.put(7, item.getSendedAt());                              //sent_at
                                                params.put(8, "1");                                             //estatus
                                                params.put(9, data.get(1));                                     //num_prestamo
                                                params.put(10, "");                                             //celular
                                                /**Guarda la impresion en la tabla de vigente*/
                                                dBhelper.saveImpresiones(db, params);
                                            }
                                            row.close();
                                        }
                                        /**Si tipo cartera es Vencida*/
                                        else if (item.getTipoCartera() == 4) {//VENCIDA

                                            /**Busca la impresion en la tabla de vencida*/
                                            row = dBhelper.getRecords(TBL_IMPRESIONES_VENCIDA_T, " WHERE num_prestamo = ?  AND folio = ? AND tipo_impresion = ?", "", new String[]{data.get(1), String.valueOf(item.getFolio()), String.valueOf(item.getTipo())});

                                            /**En caso de existir en la tabla*/
                                            if (row.getCount() > 0) {
                                                /**Actualiza los campos de fecha de envio y estatus de la impresion*/
                                                ContentValues cv = new ContentValues();
                                                cv.put("sent_at", item.getSendedAt());
                                                cv.put("estatus", "1");
                                                db.update(TBL_IMPRESIONES_VENCIDA_T, cv,
                                                        "num_prestamo = ? AND folio = ? AND tipo_impresion = ?", new String[]{
                                                                data.get(1), item.getFolio(), item.getTipo()});

                                            } else {
                                                /**En caso de NO existir registrar la impresion en la tabla de vencida*/
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
                                                params.put(9, data.get(1));                                     //num_prestamo
                                                params.put(10, "");                                             //celular
                                                /**Guarda la impresion en la tabla de vencida*/
                                                dBhelper.saveImpresionesVencida(db, params);
                                            }
                                            row.close();
                                        }
                                    }

                                }
                                break;
                        }

                        /**Funcion para obtener ahora la cartera del asesor*/
                        GetCartera();
                        /**Se marca la casilla de impresiones para saber que ya termino ese proceso*/
                        cbImpresiones.setChecked(true);
                    }

                    @Override
                    public void onFailure(Call<List<MImpresionRes>> call, Throwable t) {
                        /**En caso de fallar la peticion se pasa al siguiente proceso
                         * que es descargar la cartera*/
                        Log.e("error", t.getMessage() + "asdasd");

                        /**Funcion para obtener ahora la cartera del asesor*/
                        GetCartera();
                        /**Se marca la casilla de impresiones para saber que ya termino ese proceso*/
                        cbImpresiones.setChecked(true);
                    }
                });

            }
        });

        if (ActivityCompat.checkSelfPermission(ctx, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(ctx, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

        }

        String provider;

        /**Se valida el modelo del dispositivo para saber con que proveedor de GPS va a realizar la peticion*/
        if (Build.MODEL.contains("Redmi")){ /**Si es para REDMI*/
            provider = LocationManager.NETWORK_PROVIDER;
            //provider = LocationManager.GPS_PROVIDER;
        }
        else {/**Si es para SAMSUNG*/
            /**Si tiene conexion a internet*/
            if (NetworkStatus.haveNetworkConnection(ctx))
                provider = LocationManager.NETWORK_PROVIDER;
            else
                provider = LocationManager.GPS_PROVIDER;
        }

        /**Se realiza la consulta para obtener la ubicacion del dispositivo*/
        locationManager.requestSingleUpdate(provider, locationListener, null);

    }

    /**Funcion para obtener la cartera que tiene asignada el asesor/gestor*/
    private void GetCartera(){
        /**Interfaz para relizar la consulta de la cartera*/
        ManagerInterface api = new RetrofitClient().generalRF(Constants.CONTROLLER_MOVIL, ctx).create(ManagerInterface.class);

        /**Se prepara la peticion colocando el id del usuario*/
        Call<List<MCartera>> call = api.getCartera(session.getUser().get(9),"Bearer "+ session.getUser().get(7));

        /**Se realiza la peticion de obtencion solo de la cartera*/
        call.enqueue(new Callback<List<MCartera>>() {
            @Override
            public void onResponse(Call<List<MCartera>> call, Response<List<MCartera>> response) {
                Log.e("Code cartera", String.valueOf(response.code()));
                /**Se valida el codigo de respuesta*/
                switch (response.code()){
                    case 200:
                        /**Obtiene un listado de la cartera asignado*/
                        List<MCartera> cartera = response.body();
                        if (cartera.size() > 0){
                            Cursor row;

                            /**Recorre toda la cartera*/
                            for (int i = 0; i < cartera.size(); i++){
                                String where = " WHERE id_cartera = ?";
                                String order = "";
                                String[] args =  new String[] {String.valueOf(cartera.get(i).getId())};
                                /**Se valida el tipo de cartera Individual o grupal*/
                                switch (Miscellaneous.GetTipoCartera(cartera.get(i).getCarteraTipo())){
                                    case 1:/**Individual*/

                                        /**Se busca si existe la cartera registrada en movil*/
                                        row = dBhelper.getRecords(TBL_CARTERA_IND_T, where, order, args);

                                        /**En caso de no existir registra la cartera*/
                                        if (row.getCount() == 0){ //Registra la cartera de ind
                                            row.close();
                                            /**Registra la cartera*/
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
                                            values.put(20, String.valueOf(cartera.get(i).getDiasAtraso()));     //DIAS ATRASO
                                            /*values.put(17, (cartera.get(i).getCcInd()?"1":"0"));                //CC
                                            values.put(18, (cartera.get(i).getAgfInd()?"1":"0"));               //AGF
                                            values.put(19, cartera.get(i).getCurp());                           //CURP*/

                                            dBhelper.saveCarteraInd(db, TBL_CARTERA_IND_T, values);

                                        }
                                        else{ //Actualiza la cartera de ind
                                            /**Actualiza los datos de la cartera porque ya existe*/
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
                                            cv.put("dias_atraso", String.valueOf(cartera.get(i).getDiasAtraso()));
                                            /*cv.put("cc", (cartera.get(i).getCcInd()?1:0));
                                            cv.put("agf", (cartera.get(i).getAgfInd()?1:0));
                                            cv.put("curp", cartera.get(i).getCurp());*/

                                            db.update(TBL_CARTERA_IND_T, cv, "id_cartera = ?", new String[]{String.valueOf(cartera.get(i).getId())});
                                        }

                                        row.close();
                                        break;
                                    case 2:/**Grupal*/

                                        /**Busca si existe la cartera en el movil*/
                                        row = dBhelper.getRecords(TBL_CARTERA_GPO_T, where, order, args);

                                        /**En caso de no existir registra la cartera*/
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
                                            values.put(18, String.valueOf(cartera.get(i).getDiasAtraso()));     //DIAS_ATRASO
                                            /*values.put(16, cartera.get(i).getCcGpo());                          //CC
                                            values.put(17, cartera.get(i).getAgfGpo());                         //AGF*/

                                            dBhelper.saveCarteraGpo(db, TBL_CARTERA_GPO_T, values);
                                        }
                                        else{ //Actualiza la cartera de gpo
                                            /**Actualiza la cartera por que existe en el movil*/
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
                                            cv.put("dias_atraso", String.valueOf(cartera.get(i).getDiasAtraso()));

                                            db.update(TBL_CARTERA_GPO_T, cv, "id_cartera = ?", new String[]{String.valueOf(cartera.get(i).getId())});
                                        }
                                        row.close();
                                        break;
                                }//Fin SWITCH
                            } //Fin Ciclo For
                        }//Fin IF
                        /**Pasa al siguiente proceso que es obtener prestamos*/
                        GetPrestamos(0, -1);

                        break;
                    default:
                        /**Al no recibir cartera pasa directo a la vista de Cartera*/
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

                /**Al no recibir cartera pasa directo a la vista de Cartera*/
                cbCartera.setChecked(true);
                Intent home = new Intent(ctx, Home.class);
                home.putExtra("login", false);
                startActivity(home);
                finish();

            }
        });
    }

    /**Funcion para obtener los prestamos de acuerdo a la cartera que tiene registrada en el movil*/
    public void GetPrestamos(int iOffSet, int iTotal){

        final Cursor row;
        String query;
        int iTotalRows;

        if(iTotal < 0)
        {
            query = "SELECT * FROM (SELECT id_cartera,'1' AS tipo FROM " + TBL_CARTERA_IND_T + " UNION SELECT id_cartera,'2' AS tipo FROM "+TBL_CARTERA_GPO_T +") AS cartera order by id_cartera, tipo";
            Cursor rowTotal = db.rawQuery(query, null);
            iTotalRows = rowTotal.getCount();
            rowTotal.close();

            tvTotal.setText(String.valueOf(iTotalRows));
        }
        else {
            iTotalRows = iTotal;
        }

        /**Obtiene el listado de cartera registrada tanto individuales y grupales*/
        query = "SELECT * FROM (SELECT id_cartera,'1' AS tipo FROM " + TBL_CARTERA_IND_T + " UNION SELECT id_cartera,'2' AS tipo FROM "+TBL_CARTERA_GPO_T +") AS cartera order by id_cartera, tipo limit 1 offset " + iOffSet;
        row = db.rawQuery(query, null);

        /**Si obtiene resultado de cartera*/
        if(iTotalRows > 0) {
            if (row.getCount() > 0) {
                row.moveToFirst();

                /**Recorre el listado de la cartera*/
                for (int i = 0; i < row.getCount(); i++) {
                    int iTipoCartera = row.getInt(1);
                    int iPrestamoId = row.getInt(0);
                    row.close();

                    /**Valida el tipo de cartera Individual|Grupal*/
                    switch (iTipoCartera) {
                        case 1:/**Individual*/
                            /**Realiza la consulta del prestamo individual de la cartera*/
                            GetPrestamoInd(iPrestamoId, new PrestamoIndCallbacks() {
                                @Override
                                public void onPrestamoInd(boolean mPrestamosInd) {
                                    if (mPrestamosInd) {
                                        GetPrestamos(iOffSet + 1, iTotalRows);
                                        QuitarLoading(iTotalRows, iOffSet + 1);
                                    }
                                }

                                @Override
                                public void onPrestamoIndFailed(Throwable error) {
                                    GetPrestamos(iOffSet + 1, iTotalRows);
                                    QuitarLoading(iTotalRows, iOffSet + 1);
                                }
                            });
                            break;
                        case 2:/**Grupal*/
                            /**Busca el prestamos grupal de la cartera*/
                            GetPrestmoGpo(iPrestamoId, new PrestamoGpoCallbacks() {
                                @Override
                                public void onPrestamoGpo(boolean mPrestamosGpo) {
                                    if (mPrestamosGpo) {
                                        GetPrestamos(iOffSet + 1, iTotalRows);
                                        QuitarLoading(iTotalRows, iOffSet + 1);
                                    }

                                }

                                @Override
                                public void onPrestamoGpoFailed(Throwable error) {
                                    GetPrestamos(iOffSet + 1, iTotalRows);
                                    QuitarLoading(iTotalRows, iOffSet + 1);

                                }
                            });
                            break;
                    }
                }
            }
        }
        else{
            QuitarLoading(0,0);
        }
    }

    /**Funcion para obtener prestamos individuales*/
    private void GetPrestamoInd(final int id, final PrestamoIndCallbacks prestamoCallbacks){
        /**Interfaz para consultar prestamos*/
        ManagerInterface api = new RetrofitClient().generalRF(Constants.CONTROLLER_MOVIL, ctx).create(ManagerInterface.class);
        /**Se prepara la peticion para obtener los prestamos colocando el id de la cartera*/
        Call<List<MPrestamoRes>> call = api.getPrestamosInd(id,"Bearer "+ session.getUser().get(7));
        /**Se realiza la peticion para obtener los prestamos*/
        call.enqueue(new Callback<List<MPrestamoRes>>() {
            @Override
            public void onResponse(Call<List<MPrestamoRes>> call, Response<List<MPrestamoRes>> response) {
                Log.e("ind","id_carteta: "+id+ " code"+response.code());
                /**Se valida el codigo de respuesta*/
                if (response.code() == 200) {
                    /**Se obtiene el listado de prestamos*/
                    List<MPrestamoRes> prestamos = response.body();

                    if (prestamos.size() > 0){
                        /**Se actualiza el estatus de la cartera para que se visualice */
                        ContentValues cv_cartera = new ContentValues();
                        cv_cartera.put("estatus", "1");
                        db.update(TBL_CARTERA_IND_T, cv_cartera, "id_cartera = ?", new String[]{String.valueOf(id)});

                            Cursor row;
                            /**Se hace el recorrido de los prestamos obtenidos de la consulta*/
                            for (int i = 0; i < prestamos.size(); i++){
                                String where = " WHERE id_prestamo = ?";
                                String order = "";
                                String[] args =  new String[] {String.valueOf(prestamos.get(i).getId())};
                                /**Se busca el prestamo si se encuentre registrado en el movil*/
                                row = dBhelper.getRecords(TBL_PRESTAMOS_IND_T, where, order, args);

                                /**Si no exisite en el dispositivo se registra*/
                                if (row.getCount() == 0)
                                { //Registra el prestamo de ind
                                    row.close();
                                    /**Registra los datos del prestamo individual*/
                                    HashMap<Integer, String> values = new HashMap<>();
                                    values.put(0, String.valueOf(prestamos.get(i).getId()));                    //ID PRESTAMO
                                    values.put(1, String.valueOf(prestamos.get(i).getClienteId()));             //CLIENTE ID
                                    values.put(2, prestamos.get(i).getNumPrestamo());                           //NUM_PRESTAMO
                                    values.put(3, String.valueOf(prestamos.get(i).getNumSolicitud()));          //NUM_SOLICITUD
                                    values.put(4, prestamos.get(i).getFechaEntrega());                          //FECHA_ENTREGA
                                    values.put(5, String.valueOf(Miscellaneous.validDbl(prestamos.get(i).getMontoOtorgado())));         //MONTO OTORGADO
                                    values.put(6, String.valueOf(Miscellaneous.validDbl(prestamos.get(i).getMontoTotal())));            //MONTO TOTAL
                                    values.put(7, String.valueOf(Miscellaneous.validDbl(prestamos.get(i).getMontoAmortizacion())));     //MONTO AMORTIZACION
                                    values.put(8, String.valueOf(Miscellaneous.validDbl(prestamos.get(i).getMontoRequerido())));        //MONTO REQUERIDO
                                    values.put(9, String.valueOf(Miscellaneous.validInt(prestamos.get(i).getNumAmortizacion())));       //NUM AMORTIZACION
                                    values.put(10, Miscellaneous.validStr(prestamos.get(i).getFechaEstablecida()));                     //FECHA ESTABLECIDA
                                    values.put(11, prestamos.get(i).getTipoCartera());                          //TIPO CARTERA
                                    values.put(12, (prestamos.get(i).getPagada().equals("PAGADA"))?"1":"0");    //PAGADA
                                    values.put(13, Miscellaneous.ObtenerFecha(TIMESTAMP));                      //FECHA CREACION
                                    values.put(14, Miscellaneous.ObtenerFecha(TIMESTAMP));                      //FECHA ACTUALIZACION

                                    dBhelper.savePrestamosInd(db, TBL_PRESTAMOS_IND_T, values);

                                    /**Si de los datos del prestamo vienen datos del aval se registran datos del aval*/
                                    if (prestamos.get(i).getAval() != null) {
                                        /**Se registran datos del aval*/
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

                                        dBhelper.saveAval(db, TBL_AVAL_T, values_aval);

                                    }

                                    /**Si tiene listado de amortizaciones se registran las amortizaciones*/
                                    if (prestamos.get(i).getAmortizaciones().size() > 0){
                                        /**Se hace recorrido del listado de amortizaciones*/
                                        for (int j = 0; j < prestamos.get(i).getAmortizaciones().size(); j++){
                                            /**Registra el listado de amortizaciones del prestamo*/
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

                                            dBhelper.saveAmortizaciones(db, TBL_AMORTIZACIONES_T, values_amortiz);
                                        }
                                    }

                                    /**Si tiene listado de pagos se registran*/
                                    if (prestamos.get(i).getPagos() != null && prestamos.get(i).getPagos().size() > 0){
                                        /**Se hace el recorrido de pagos para hacer los registros  de pagos*/
                                        for (int k = 0; k < prestamos.get(i).getPagos().size(); k++){
                                            /**Se hace el registro de los pagos realizados*/
                                            MPago mPago = prestamos.get(i).getPagos().get(k);
                                            HashMap<Integer, String> values_pago = new HashMap<>();
                                            values_pago.put(0, String.valueOf(prestamos.get(i).getId()));            //ID PRESTAMO
                                            values_pago.put(1, mPago.getFecha());                                    //FECHA
                                            values_pago.put(2, String.valueOf(mPago.getMonto()));                    //MONTO
                                            values_pago.put(3, Miscellaneous.validStr(mPago.getBanco()));            //BANCO
                                            values_pago.put(4, Miscellaneous.ObtenerFecha(TIMESTAMP));               //FECHA DISPOSITIVO
                                            values_pago.put(5, Miscellaneous.ObtenerFecha(TIMESTAMP));               //FECHA ACTUALIZADO

                                            dBhelper.savePagos(db, TBL_PAGOS_T, values_pago);
                                        }
                                    }

                                    /**Registra los datos de telefono de casa y celular del cliente ligado al prestamo*/
                                    HashMap<Integer, String> values_tel = new HashMap<>();
                                    values_tel.put(0, String.valueOf(prestamos.get(i).getId()));                    //ID_PRESTAMO
                                    values_tel.put(1, Miscellaneous.validStr(prestamos.get(i).getTelCasa()));       //TEL_CASA
                                    values_tel.put(2, Miscellaneous.validStr(prestamos.get(i).getTelCelular()));    //TEL_CELULAR
                                    dBhelper.saveTelefonosCli(db, values_tel);
                                }
                                /**En caso de existir el prestamo en el movil actualizar los datos*/
                                else{ //Actualiza la prestamo de ind
                                    row.moveToFirst();
                                    /**Actualiza datos del prestamo*/
                                    ContentValues cv = new ContentValues();
                                    cv.put("fecha_entrega", prestamos.get(i).getFechaEntrega());
                                    cv.put("monto_otorgado", String.valueOf(prestamos.get(i).getMontoOtorgado()));
                                    cv.put("monto_total", String.valueOf(prestamos.get(i).getMontoTotal()));
                                    cv.put("monto_amortizacion", String.valueOf(prestamos.get(i).getMontoAmortizacion()));
                                    cv.put("monto_requerido", String.valueOf(prestamos.get(i).getMontoRequerido()));
                                    cv.put("num_amortizacion", String.valueOf(prestamos.get(i).getNumAmortizacion()));
                                    cv.put("fecha_establecida", prestamos.get(i).getFechaEstablecida());
                                    cv.put("tipo_cartera", prestamos.get(i).getTipoCartera());

                                    /**Si ya estaba pagado ese prestamo se valida con la nueva informacion obtenida
                                     * para saber si hay que volverlo activo o sigue igual a pagado*/
                                    if (row.getInt(13) == 0)
                                        cv.put("pagada", (prestamos.get(i).getPagada().equals("PAGADA"))?"1":"0");
                                    cv.put("fecha_actualizado", Miscellaneous.ObtenerFecha(TIMESTAMP));

                                    db.update(TBL_PRESTAMOS_IND_T, cv, "_id = ?", new String[]{row.getString(0)});

                                    /**Busca todas aquella respuestas correspondientes al prestamos y que fueron de PAGO
                                     * para saber si se quita de ruta porque ya se gestionó*/
                                    String sql = "SELECT * FROM " + TBL_RESPUESTAS_IND_T + " WHERE id_prestamo = ? AND contacto = ? AND resultado_gestion = ?";
                                    Cursor rowRuta = db.rawQuery(sql, new String[]{String.valueOf(prestamos.get(i).getId()), "SI", "PAGO"});

                                    if (rowRuta.getCount() > 0){
                                        rowRuta.moveToFirst();
                                        int weekFechaEst = 0;
                                        Calendar calFechaEst = Calendar.getInstance();

                                        try {
                                            /**Obtiene el valor del numero de semana del año del dia actual*/
                                            Date dFechaEstablecida = sdf.parse(Miscellaneous.ObtenerFecha(FECHA.toLowerCase()));
                                            calFechaEst.setTime(dFechaEstablecida);
                                            weekFechaEst = calFechaEst.get(Calendar.WEEK_OF_YEAR);

                                        } catch (ParseException e) {
                                            e.printStackTrace();
                                        }

                                        double sumPago = 0;
                                        for (int r = 0; r < rowRuta.getCount(); r++){
                                            /**Obtiene la fecha de inicio de gestion*/
                                            String[] fechaFinGes = rowRuta.getString(23).split(" ");
                                            Date dFechaEstablecida = null;
                                            try {
                                                /**Obtiene el valor del numero de semana del año de la fecha cuando inicio la gestion*/
                                                dFechaEstablecida = sdf.parse(fechaFinGes[0]);
                                                calFechaEst.setTime(dFechaEstablecida);

                                                /**Valida si corresponden a la misma semana*/
                                                if (calFechaEst.get(Calendar.WEEK_OF_YEAR) == weekFechaEst){
                                                    sumPago += rowRuta.getDouble(15);
                                                }
                                            } catch (ParseException e) {
                                                e.printStackTrace();
                                            }

                                            rowRuta.moveToNext();
                                        }
                                        try {
                                            /**Si los pagos realizados son mayores al monto requerido se remueve de ruta*/
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

                                    /**Si se obtienen datos del aval*/
                                    if (prestamos.get(i).getAval() != null) {
                                        /**Actualiza datos del aval*/
                                        MAval mAval = prestamos.get(i).getAval();
                                        ContentValues cv_aval = new ContentValues();
                                        cv_aval.put("id_aval", String.valueOf(mAval.getId()));                      //AVAL ID
                                        cv_aval.put("nombre", mAval.getNombre());                                   //NOMBRE
                                        cv_aval.put("parentesco", mAval.getParentesco());                           //PARENTESCO
                                        cv_aval.put("direccion", mAval.getDireccion());                             //DIRECCIO
                                        cv_aval.put("telefono", mAval.getTelefono());                               //TELEFONO
                                        cv_aval.put("fecha_actualizado", Miscellaneous.ObtenerFecha(TIMESTAMP));    //FECHA ACTUALIZACION

                                        db.update(TBL_AVAL_T, cv_aval, "id_prestamo = ?", new String[]{String.valueOf(prestamos.get(i).getId())});
                                    }

                                    /**Si se obtiene datos de amortizaciones se actualizan datos*/
                                    if (prestamos.get(i).getAmortizaciones().size() > 0){
                                        MAmortizacion mAmortizacionExiste = prestamos.get(i).getAmortizaciones().get(0);
                                        AmortizacionDao amortizacionDao = new AmortizacionDao(ctx);
                                        Amortizacion amortizacionExiste = amortizacionDao.findByIdAmortizacion(String.valueOf(mAmortizacionExiste.getId()));

                                        Log.e("AQUI", "EXISTO EN PRESTAMOS");

                                        if(amortizacionExiste != null)
                                        {

                                            Log.e("AQUI AMORTIZACION EXISTO", amortizacionExiste.getIdAmortizacion());

                                            /**Se realiza recorrido para el listado de amortizaciones*/
                                            for (int j = 0; j < prestamos.get(i).getAmortizaciones().size(); j++){
                                                /**Actualiza los datos de amortizacion del prestamo*/
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
                                                    if (rowAmortiz.getDouble(0) > rowAmortiz.getDouble(1)) {
                                                        cv_amortiz.put("total_pagado", String.valueOf(mAmortizacion.getTotalPagado()));                         //TOTAL PAGADO
                                                        cv_amortiz.put("pagado", String.valueOf(mAmortizacion.getPagado()));                                    //PAGADO
                                                        cv_amortiz.put("numero", String.valueOf(mAmortizacion.getNumero()));                                    //NUMERO
                                                        cv_amortiz.put("dias_atraso", String.valueOf(mAmortizacion.getDiasAtraso()));                           //DIAS ATRASO
                                                    }
                                                }
                                                rowAmortiz.close();
                                                cv_amortiz.put("fecha_actualizado", Miscellaneous.ObtenerFecha(TIMESTAMP));                             //FECHA ACTUALIZADO

                                                db.update(TBL_AMORTIZACIONES_T, cv_amortiz, "id_amortizacion = ? AND id_prestamo = ?", new String[]{String.valueOf(mAmortizacion.getId()), String.valueOf(prestamos.get(i).getId())});
                                            }
                                        }
                                        else
                                        {
                                            Log.e("AQUI AMORTIZACION NO EXISTO", String.valueOf(prestamos.get(i).getId()));

                                            amortizacionDao.deleteByIdPrestamo(String.valueOf(prestamos.get(i).getId()));

                                            for (int j = 0; j < prestamos.get(i).getAmortizaciones().size(); j++) {
                                                MAmortizacion mAmortizacion = prestamos.get(i).getAmortizaciones().get(j);

                                                Amortizacion amortizacion = new Amortizacion();
                                                amortizacion.setIdAmortizacion(String.valueOf(mAmortizacion.getId()));
                                                amortizacion.setIdPrestamo(String.valueOf(mAmortizacion.getPrestamoId()));
                                                amortizacion.setFecha(mAmortizacion.getFecha());
                                                amortizacion.setFechaPago((mAmortizacion.getFechaPago() != null)?mAmortizacion.getFechaPago():"");
                                                amortizacion.setCapital(String.valueOf(mAmortizacion.getCapital()));
                                                amortizacion.setInteres(String.valueOf(mAmortizacion.getInteres()));
                                                amortizacion.setIva(String.valueOf(mAmortizacion.getIva()));
                                                amortizacion.setComision(String.valueOf(mAmortizacion.getComision()));
                                                amortizacion.setTotal(String.valueOf(mAmortizacion.getTotal()));
                                                amortizacion.setCapitalPagado(String.valueOf(mAmortizacion.getCapitalPagado()));
                                                amortizacion.setInteresPagado(String.valueOf(mAmortizacion.getInteresPagado()));
                                                amortizacion.setIvaPagado(String.valueOf(mAmortizacion.getIvaPagado()));
                                                amortizacion.setInteresMoratorioPagado(String.valueOf(mAmortizacion.getInteresMoratorioPagado()));
                                                amortizacion.setIvaMoratorioPagado(String.valueOf(mAmortizacion.getIvaMoratorioPagado()));
                                                amortizacion.setComisionPagada(String.valueOf(mAmortizacion.getComisionPagada()));
                                                amortizacion.setTotalPagado(String.valueOf(mAmortizacion.getTotalPagado()));
                                                amortizacion.setPagado(String.valueOf(mAmortizacion.getPagado()));
                                                amortizacion.setNumero(String.valueOf(mAmortizacion.getNumero()));
                                                amortizacion.setDiasAtraso(String.valueOf(mAmortizacion.getDiasAtraso()));
                                                amortizacion.setFechaDispositivo(Miscellaneous.ObtenerFecha(TIMESTAMP));
                                                amortizacion.setFechaActualizado(Miscellaneous.ObtenerFecha(TIMESTAMP));

                                                amortizacionDao.store(amortizacion);
                                            }
                                        }

                                    }

                                    /**Si se obtienen pagos  del prestamo se actualizan datos*/
                                    if (prestamos.get(i).getPagos() != null && prestamos.get(i).getPagos().size() > 0){
                                        for (int k = 0; k < prestamos.get(i).getPagos().size(); k++){
                                            MPago mPago = prestamos.get(i).getPagos().get(k);
                                            Cursor row_pago = dBhelper.getRecords(TBL_PAGOS_T, " WHERE id_prestamo = ? AND fecha = ? AND monto = ? AND banco = ?", "",
                                                    new String[]{String.valueOf(prestamos.get(i).getId()),mPago.getFecha(), String.valueOf(mPago.getMonto()), mPago.getBanco(),});
                                            if (row_pago.getCount() == 0){
                                                /**Se crea registro por si es un pago nuevo*/
                                                HashMap<Integer, String> cv_pago = new HashMap<>();
                                                cv_pago.put(0, String.valueOf(prestamos.get(i).getId()));            //ID PRESTAMO
                                                cv_pago.put(1, mPago.getFecha());                                    //FECHA
                                                cv_pago.put(2, String.valueOf(mPago.getMonto()));                    //MONTO
                                                cv_pago.put(3, Miscellaneous.validStr(mPago.getBanco()));            //BANCO
                                                cv_pago.put(4, Miscellaneous.ObtenerFecha(TIMESTAMP));               //FECHA DISPOSITIVO
                                                cv_pago.put(5, Miscellaneous.ObtenerFecha(TIMESTAMP));               //FECHA ACTUALIZADO

                                                dBhelper.savePagos(db, TBL_PAGOS_T, cv_pago);
                                            }
                                            row_pago.close();
                                        }
                                    }

                                    /**Actualiza datos de telefono celular del prestamo*/
                                    ContentValues cv_telefonos = new ContentValues();
                                    cv_telefonos.put("tel_casa", Miscellaneous.validStr(prestamos.get(i).getTelCasa()));
                                    cv_telefonos.put("tel_celular", Miscellaneous.validStr(prestamos.get(i).getTelCelular()));
                                    db.update(TBL_TELEFONOS_CLIENTE, cv_telefonos, "id_prestamo = ?", new String[]{String.valueOf(prestamos.get(i).getId())});
                                }
                                row.close();

                                //BUSCAR SI SE TIENE LOS DOCUMENTOS DE EXPEDIENTES
                                /*DocumentoClienteDao documentoClienteDao = new DocumentoClienteDao(ctx);
                                List<DocumentoCliente> documentosClientes = documentoClienteDao.findAllByPrestamoId(prestamos.get(i).getId());

                                if(documentosClientes.size() == 0)
                                {
                                    DocumentoClienteService api = new RetrofitClient().generalRF(Constants.CONTROLLER_MOVIL, ctx).create(DocumentoClienteService.class);
                                    Call<List<DocumentoCliente>> callDocumentoCliente = api.show(prestamos.get(i).getId(),"Bearer "+ session.getUser().get(7));
                                    callDocumentoCliente.enqueue(new Callback<List<DocumentoCliente>>() {
                                         @Override
                                         public void onResponse(Call<List<DocumentoCliente>> call, Response<List<DocumentoCliente>> response) {
                                             Log.e("ind", "id_carteta: " + id + " code" + response.code());
                                             if (response.code() == 200) {

                                                 List<DocumentoCliente> documentosClientesResponse = response.body();

                                                 for(DocumentoCliente dc : documentosClientesResponse)
                                                 {
                                                     DocumentoCliente documentoCliente = new DocumentoCliente();

                                                     documentoCliente.setArchivoBase64(dc.getArchivoBase64());
                                                     documentoCliente.setClavecliente(dc.getClavecliente());
                                                     documentoCliente.setClienteId(dc.getClienteId());
                                                     documentoCliente.setFecha(dc.getFecha());
                                                     documentoCliente.setGrupoId(dc.getGrupoId());
                                                     //documentoCliente.setId(dc.getId());
                                                     documentoCliente.setNumSolicitud(dc.getNumSolicitud());
                                                     documentoCliente.setPrestamoId(dc.getPrestamoId());
                                                     documentoCliente.setTipo(dc.getTipo());

                                                     documentoClienteDao.store(documentoCliente);
                                                 }
                                             }
                                         }

                                        @Override
                                        public void onFailure(Call<List<DocumentoCliente>> call, Throwable t) {
                                             Log.e("Error", "DocumentoCliente" + t.getMessage());
                                        }
                                    });
                                }
                                */


                            } //Fin Ciclo For
                        }//Fin IF
                    else{
                        /**En caso de que no se obtuvieron datos de prestamos se cambia el estatus
                         * de la cartera para que no se muestre*/
                        ContentValues cv = new ContentValues();
                        cv.put("estatus", "0");
                        db.update(TBL_CARTERA_IND_T, cv, "id_cartera = ?", new String[]{String.valueOf(id)});
                    }
                    if (prestamoCallbacks != null)
                        prestamoCallbacks.onPrestamoInd(true);
                }
                else{
                    Log.e("ERROR AQUI", response.message());
                }
            }

            @Override
            public void onFailure(Call<List<MPrestamoRes>> call, Throwable t) {
                if (prestamoCallbacks != null)
                    prestamoCallbacks.onPrestamoIndFailed(t);
            }
        });
    }

    /**Funcion para obtener los datos del prestamo de grupales*/
    private void GetPrestmoGpo(final int id, final PrestamoGpoCallbacks prestamoGpoCallbacks){
        /**Interfaz para consultar prestamos grupales*/
        ManagerInterface api = new RetrofitClient().generalRF(Constants.CONTROLLER_MOVIL, ctx).create(ManagerInterface.class);
        /**Se prepara la peticion colocando el id de la cartera */
        Call<List<MPrestamoGpoRes>> call = api.getPrestamosGpo(id,"Bearer "+ session.getUser().get(7));
        /**Se realiza la peticion para obtener los datos del prestamo grupal*/
        call.enqueue(new Callback<List<MPrestamoGpoRes>>() {
            @Override
            public void onResponse(Call<List<MPrestamoGpoRes>> call, Response<List<MPrestamoGpoRes>> response) {
                /**Se valida si la peticion se realizó con éxito*/
                if (response.code() == 200){
                    /**Se obtiene el listado de prestamo*/
                    List<MPrestamoGpoRes> prestamos = response.body();
                    if (prestamos.size() > 0){

                        Log.e("----","---------------------------------------------");
                        Log.e("IdCartera", "----> "+id);
                        Log.e("----","---------------------------------------------");
                        /**Actualiza el estatus de la cartera para que se siga visualizando*/
                        ContentValues cv_cartera = new ContentValues();
                        cv_cartera.put("estatus", "1");
                        db.update(TBL_CARTERA_GPO_T, cv_cartera, "id_cartera = ?", new String[]{String.valueOf(id)});

                        Cursor row;

                        /**Se recorre el listado de prestamos grupales*/
                        for (int i = 0; i < prestamos.size(); i++){
                            String where = " WHERE id_prestamo = ?";
                            String order = "";
                            String[] args =  new String[] {String.valueOf(prestamos.get(i).getId())};

                            /**Se busca el prestamo para saber si existe en el movil*/
                            row = dBhelper.getRecords(TBL_PRESTAMOS_GPO_T, where, order, args);

                            /**En caso de no existir en el movil*/
                            if (row.getCount() == 0){ //Registra el prestamo de gpo
                                /**Se registra el prestamo en el movil*/
                                HashMap<Integer, String> values = new HashMap<>();
                                values.put(0, String.valueOf(prestamos.get(i).getId()));                    //ID PRESTAMO
                                values.put(1, String.valueOf(prestamos.get(i).getGrupoId()));               //GRUPO ID
                                values.put(2, prestamos.get(i).getNumPrestamo());                           //NUM_PRESTAMO
                                values.put(3, String.valueOf(prestamos.get(i).getNumSolicitud()));          //NUM_SOLICITUD
                                values.put(4, prestamos.get(i).getFechaEntrega());                          //FECHA_ENTREGA
                                values.put(5, String.valueOf(Miscellaneous.validDbl(prestamos.get(i).getMontoOtorgado())));         //MONTO OTORGADO
                                values.put(6, String.valueOf(Miscellaneous.validDbl(prestamos.get(i).getMontoTotal())));            //MONTO TOTAL
                                values.put(7, String.valueOf(Miscellaneous.validDbl(prestamos.get(i).getMontoAmortizacion())));     //MONTO AMORTIZACION
                                values.put(8, String.valueOf(Miscellaneous.validDbl(prestamos.get(i).getMontoRequerido())));        //MONTO REQUERIDO
                                values.put(9, String.valueOf(Miscellaneous.validInt(prestamos.get(i).getNumAmortizacion())));       //NUM AMORTIZACION
                                values.put(10, Miscellaneous.validStr(prestamos.get(i).getFechaEstablecida()));                     //FECHA ESTABLECIDA
                                values.put(11, prestamos.get(i).getTipoCartera());                          //TIPO CARTERA
                                values.put(12, (prestamos.get(i).getPagada().equals("PAGADA"))?"1":"0");    //PAGADA
                                values.put(13, Miscellaneous.ObtenerFecha(TIMESTAMP));                      //FECHA CREACION
                                values.put(14, Miscellaneous.ObtenerFecha(TIMESTAMP));                      //FECHA ACTUALIZACION

                                dBhelper.savePrestamosGpo(db, TBL_PRESTAMOS_GPO_T, values);

                                /**Si se obtienen datos de integrantes*/
                                if (prestamos.get(i).getIntegrantes() != null) {
                                    /**Se recorre el listado de integrantes para ser registrados*/
                                    for (int l = 0; l < prestamos.get(i).getIntegrantes().size(); l++){
                                        /**Se registra los datos del integrante*/
                                        MIntegrante mIntegrante = prestamos.get(i).getIntegrantes().get(l);
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

                                /**Si se tienen datos de amortizaciones obtenidos en la peticion*/
                                if (prestamos.get(i).getAmortizaciones().size() > 0){
                                    /**Se recorre el listado de amortizaciones*/
                                    for (int j = 0; j < prestamos.get(i).getAmortizaciones().size(); j++){
                                        /**Se registran los datos de las amortizaciones*/
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

                                        dBhelper.saveAmortizaciones(db, TBL_AMORTIZACIONES_T, values_amortiz);
                                    }
                                }

                                /**Si se tienen datos de pagos obtenidos en la peticion*/
                                if (prestamos.get(i).getPagos() != null &&prestamos.get(i).getPagos().size() > 0){
                                    /**Se recorre el listado de pagos*/
                                    for (int k = 0; k < prestamos.get(i).getPagos().size(); k++){
                                        /**Registra los pagos */
                                        MPago mPago = prestamos.get(i).getPagos().get(k);
                                        HashMap<Integer, String> values_pago = new HashMap<>();
                                        values_pago.put(0, String.valueOf(prestamos.get(i).getId()));            //ID PRESTAMO
                                        values_pago.put(1, mPago.getFecha());                                    //FECHA
                                        values_pago.put(2, String.valueOf(mPago.getMonto()));                    //MONTO
                                        values_pago.put(3,mPago.getBanco());                                     //BANCO
                                        values_pago.put(4, Miscellaneous.ObtenerFecha(TIMESTAMP));               //FECHA DISPOSITIVO
                                        values_pago.put(5, Miscellaneous.ObtenerFecha(TIMESTAMP));               //FECHA ACTUALIZADO

                                        dBhelper.savePagos(db, TBL_PAGOS_T, values_pago);
                                    }
                                }
                            }
                            else{ //Actualiza la prestamo gpo
                                /**Actualiza los datos del prestamo*/
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

                                db.update(TBL_PRESTAMOS_GPO_T, cv_prestamo, "_id = ?", new String[]{row.getString(0)});

                                /**Se buscan las respuestas de grupo que correspondan al prestamo y que sean de pago para
                                 * saber si se remueven de ruta o no*/
                                String sql = "SELECT * FROM " + TBL_RESPUESTAS_GPO_T + " WHERE id_prestamo = ? AND contacto = ? AND resultado_gestion = ?";
                                Cursor rowRuta = db.rawQuery(sql, new String[]{String.valueOf(prestamos.get(i).getId()), "SI", "PAGO"});

                                if (rowRuta.getCount() > 0){
                                    rowRuta.moveToFirst();
                                    int weekFechaEst = 0;
                                    Calendar calFechaEst = Calendar.getInstance();

                                    try {
                                        /**Obtiene el numero de la semana del año del dia actual*/
                                        Date dFechaEstablecida = sdf.parse(Miscellaneous.ObtenerFecha(FECHA.toLowerCase()));
                                        calFechaEst.setTime(dFechaEstablecida);
                                        weekFechaEst = calFechaEst.get(Calendar.WEEK_OF_YEAR);

                                    } catch (ParseException e) {
                                        e.printStackTrace();
                                    }

                                    double sumPago = 0;

                                    for (int r = 0; r < rowRuta.getCount(); r++){
                                        /**Se obtiene la fecha de fin gestion de la respuesta*/
                                        String[] fechaFinGes = rowRuta.getString(23).split(" ");
                                        Date dFechaEstablecida = null;
                                        try {
                                            /**Obtiene el numero de semana del año con la fecha de fin gestion*/
                                            dFechaEstablecida = sdf.parse(fechaFinGes[0]);
                                            calFechaEst.setTime(dFechaEstablecida);
                                            /**Se compara el numero de semana de la gestion con el numero de la semana actual*/
                                            if (calFechaEst.get(Calendar.WEEK_OF_YEAR) == weekFechaEst){
                                                sumPago += rowRuta.getDouble(15);
                                            }
                                        } catch (ParseException e) {
                                            e.printStackTrace();
                                        }

                                        rowRuta.moveToNext();
                                    }
                                    try {
                                        /**Si la suma de los pagos de las recuperaciones es mayor o igual a lo requerido se quita de ruta*/
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

                                /**Actualiza datos de los integrantes*/
                                if (prestamos.get(i).getIntegrantes() != null) {
                                    for (int l = 0; l < prestamos.get(i).getIntegrantes().size(); l++){
                                        /**Actualzia los datos del integrante*/
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

                                        db.update(TBL_MIEMBROS_GPO_T, cv_miembro, "id_prestamo = ? AND id_integrante = ?",
                                                new String[]{String.valueOf(prestamos.get(i).getId()), String.valueOf(mIntegrante.getId())});
                                    }
                                }//Termina If de actualizado de integrantes

                                /**Actualiza los datos de las amortizaciones*/
                                if (prestamos.get(i).getAmortizaciones().size() > 0){
                                    MAmortizacion mAmortizacionExiste = prestamos.get(i).getAmortizaciones().get(0);
                                    AmortizacionDao amortizacionDao = new AmortizacionDao(ctx);
                                    Amortizacion amortizacionExiste = amortizacionDao.findByIdAmortizacion(String.valueOf(mAmortizacionExiste.getId()));

                                    if(amortizacionExiste != null)
                                    {
                                        for (int j = 0; j < prestamos.get(i).getAmortizaciones().size(); j++) {
                                            MAmortizacion mAmortizacion = prestamos.get(i).getAmortizaciones().get(j);
                                            ContentValues cv_amortiz = new ContentValues();
                                            cv_amortiz.put("fecha", mAmortizacion.getFecha());                                                //FECHA
                                            cv_amortiz.put("fecha_pago", (mAmortizacion.getFechaPago() != null) ? mAmortizacion.getFechaPago() : "");  //FECHA PAGO
                                            cv_amortiz.put("capital", String.valueOf(mAmortizacion.getCapital()));                            //CAPITAL
                                            cv_amortiz.put("interes", String.valueOf(mAmortizacion.getInteres()));                            //INTERES
                                            cv_amortiz.put("iva", String.valueOf(mAmortizacion.getIva()));                                    //IVA
                                            cv_amortiz.put("comision", String.valueOf(mAmortizacion.getComision()));                          //COMISION
                                            cv_amortiz.put("total", String.valueOf(mAmortizacion.getTotal()));                                //TOTAL
                                            cv_amortiz.put("capital_pagado", String.valueOf(mAmortizacion.getCapitalPagado()));               //CAPITAL PAGADO
                                            cv_amortiz.put("interes_pagado", String.valueOf(mAmortizacion.getInteresPagado()));               //INTERES PAGADO
                                            cv_amortiz.put("iva_pagado", String.valueOf(mAmortizacion.getIvaPagado()));                       //IVA PAGADO
                                            cv_amortiz.put("interes_moratorio_pagado", String.valueOf(mAmortizacion.getInteresMoratorioPagado()));//INTERES MORATORIO PAGADO
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

                                            db.update(TBL_AMORTIZACIONES_T, cv_amortiz, "id_amortizacion = ? AND id_prestamo = ?", new String[]{String.valueOf(mAmortizacion.getId()), String.valueOf(prestamos.get(i).getId())});
                                        }
                                    }
                                    else
                                    {
                                        amortizacionDao.deleteByIdPrestamo(String.valueOf(prestamos.get(i).getId()));

                                        for (int j = 0; j < prestamos.get(i).getAmortizaciones().size(); j++) {
                                            MAmortizacion mAmortizacion = prestamos.get(i).getAmortizaciones().get(j);

                                            Amortizacion amortizacion = new Amortizacion();
                                            amortizacion.setIdAmortizacion(String.valueOf(mAmortizacion.getId()));
                                            amortizacion.setIdPrestamo(String.valueOf(mAmortizacion.getPrestamoId()));
                                            amortizacion.setFecha(mAmortizacion.getFecha());
                                            amortizacion.setFechaPago((mAmortizacion.getFechaPago() != null)?mAmortizacion.getFechaPago():"");
                                            amortizacion.setCapital(String.valueOf(mAmortizacion.getCapital()));
                                            amortizacion.setInteres(String.valueOf(mAmortizacion.getInteres()));
                                            amortizacion.setIva(String.valueOf(mAmortizacion.getIva()));
                                            amortizacion.setComision(String.valueOf(mAmortizacion.getComision()));
                                            amortizacion.setTotal(String.valueOf(mAmortizacion.getTotal()));
                                            amortizacion.setCapitalPagado(String.valueOf(mAmortizacion.getCapitalPagado()));
                                            amortizacion.setInteresPagado(String.valueOf(mAmortizacion.getInteresPagado()));
                                            amortizacion.setIvaPagado(String.valueOf(mAmortizacion.getIvaPagado()));
                                            amortizacion.setInteresMoratorioPagado(String.valueOf(mAmortizacion.getInteresMoratorioPagado()));
                                            amortizacion.setIvaMoratorioPagado(String.valueOf(mAmortizacion.getIvaMoratorioPagado()));
                                            amortizacion.setComisionPagada(String.valueOf(mAmortizacion.getComisionPagada()));
                                            amortizacion.setTotalPagado(String.valueOf(mAmortizacion.getTotalPagado()));
                                            amortizacion.setPagado(String.valueOf(mAmortizacion.getPagado()));
                                            amortizacion.setNumero(String.valueOf(mAmortizacion.getNumero()));
                                            amortizacion.setDiasAtraso(String.valueOf(mAmortizacion.getDiasAtraso()));
                                            amortizacion.setFechaDispositivo(Miscellaneous.ObtenerFecha(TIMESTAMP));
                                            amortizacion.setFechaActualizado(Miscellaneous.ObtenerFecha(TIMESTAMP));

                                            amortizacionDao.store(amortizacion);
                                        }
                                    }
                                }//Termina If de Actualizado de amortizaciones

                                /**Registra nuevos pagos si es que no existen en el movil*/
                                if (prestamos.get(i).getPagos() != null &&prestamos.get(i).getPagos().size() > 0){
                                    for (int k = 0; k < prestamos.get(i).getPagos().size(); k++){
                                        MPago mPago = prestamos.get(i).getPagos().get(k);

                                        Cursor row_pago = dBhelper.getRecords(TBL_PAGOS_T, " WHERE id_prestamo = ? AND fecha = ? AND monto = ? AND banco = ?", "",
                                                new String[]{String.valueOf(prestamos.get(i).getId()),mPago.getFecha(), String.valueOf(mPago.getMonto()),mPago.getBanco()});
                                        if (row_pago.getCount() == 0){
                                            HashMap<Integer, String> cv_pago = new HashMap<>();
                                            cv_pago.put(0, String.valueOf(prestamos.get(i).getId()));            //ID PRESTAMO
                                            cv_pago.put(1, mPago.getFecha());                                    //FECHA
                                            cv_pago.put(2, String.valueOf(mPago.getMonto()));                    //MONTO
                                            cv_pago.put(3,mPago.getBanco());                                     //BANCO
                                            cv_pago.put(4, Miscellaneous.ObtenerFecha(TIMESTAMP));               //FECHA DISPOSITIVO
                                            cv_pago.put(5, Miscellaneous.ObtenerFecha(TIMESTAMP));               //FECHA ACTUALIZADO

                                            dBhelper.savePagos(db, TBL_PAGOS_T, cv_pago);
                                        }
                                        row_pago.close();
                                    }
                                }//Termina If de Actualizar pagos

                            }
                            row.close();

                            //BUSCAR SI SE TIENE LOS DOCUMENTOS DE EXPEDIENTES
                            /*DocumentoClienteDao documentoClienteDao = new DocumentoClienteDao(ctx);
                            List<DocumentoCliente> documentosClientes = documentoClienteDao.findAllByPrestamoId(prestamos.get(i).getId());

                            if(documentosClientes.size() == 0)
                            {
                                DocumentoClienteService api = new RetrofitClient().generalRF(Constants.CONTROLLER_MOVIL, ctx).create(DocumentoClienteService.class);
                                Call<List<DocumentoCliente>> callDocumentoCliente = api.show(prestamos.get(i).getId(),"Bearer "+ session.getUser().get(7));
                                callDocumentoCliente.enqueue(new Callback<List<DocumentoCliente>>() {
                                    @Override
                                    public void onResponse(Call<List<DocumentoCliente>> call, Response<List<DocumentoCliente>> response) {
                                        Log.e("ind", "id_carteta: " + id + " code" + response.code());
                                        if (response.code() == 200) {

                                            List<DocumentoCliente> documentosClientesResponse = response.body();

                                            for(DocumentoCliente dc : documentosClientesResponse)
                                            {
                                                DocumentoCliente documentoCliente = new DocumentoCliente();

                                                documentoCliente.setArchivoBase64(dc.getArchivoBase64());
                                                documentoCliente.setClavecliente(dc.getClavecliente());
                                                documentoCliente.setClienteId(dc.getClienteId());
                                                documentoCliente.setFecha(dc.getFecha());
                                                documentoCliente.setGrupoId(dc.getGrupoId());
                                                //documentoCliente.setId(dc.getId());
                                                documentoCliente.setNumSolicitud(dc.getNumSolicitud());
                                                documentoCliente.setPrestamoId(dc.getPrestamoId());
                                                documentoCliente.setTipo(dc.getTipo());

                                                documentoClienteDao.store(documentoCliente);
                                            }
                                        }
                                    }

                                    @Override
                                    public void onFailure(Call<List<DocumentoCliente>> call, Throwable t) {
                                        Log.e("Error", "DocumentoCliente" + t.getMessage());
                                    }
                                });
                            }
                            */
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

    /**Funcion para pasar a la vista de cartera y terminar el proceso de descarga de datos*/
    private void QuitarLoading(int total, int avance){
        tvRegistradas.setText(String.valueOf(avance));
        if (avance == total) {
            SessionManager session = new SessionManager(ctx);

            /**Procesos para obtener las respuestas realizadas de la semana actual*/
            Servicios_Sincronizado ss = new Servicios_Sincronizado();
            if (session.getUser().get(5).contains("ROLE_GESTOR"))
                ss.GetGestionadas(ctx, "VENCIDA", false);
            else if (session.getUser().get(5).contains("ROLE_ASESOR"))
                ss.GetGestionadas(ctx, "VIGENTE", false);

            cbCartera.setChecked(true);
            /**Pasa a la vista de cartera*/
            Intent home = new Intent(ctx, Home.class);
            home.putExtra("login", false);
            startActivity(home);
            finish();
        }
    }

    @Override
    public void onBackPressed() {
        //super.onBackPressed();
    }
}
