package com.sidert.sidertmovil.activities;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
//import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
/*import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.appcompat.widget.Toolbar;*/
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.sidert.sidertmovil.R;
import com.sidert.sidertmovil.adapters.adapter_gestionadas_ind;
import com.sidert.sidertmovil.database.DBhelper;
import com.sidert.sidertmovil.models.MGestionada;
import com.sidert.sidertmovil.utils.Miscellaneous;
import com.sidert.sidertmovil.utils.SessionManager;

import java.util.ArrayList;

import static com.sidert.sidertmovil.utils.Constants.ACTUALIZAR_TELEFONO;
import static com.sidert.sidertmovil.utils.Constants.CANCELACION;
import static com.sidert.sidertmovil.utils.Constants.CLIENTE;
import static com.sidert.sidertmovil.utils.Constants.COMENTARIO;
import static com.sidert.sidertmovil.utils.Constants.CONTACTO;
import static com.sidert.sidertmovil.utils.Constants.ESTATUS;
import static com.sidert.sidertmovil.utils.Constants.EVIDENCIA;
import static com.sidert.sidertmovil.utils.Constants.FECHA_DEFUNCION;
import static com.sidert.sidertmovil.utils.Constants.FECHA_DEPOSITO;
import static com.sidert.sidertmovil.utils.Constants.FECHA_PROMESA_PAGO;
import static com.sidert.sidertmovil.utils.Constants.FIRMA;
import static com.sidert.sidertmovil.utils.Constants.FOLIO_TICKET;
import static com.sidert.sidertmovil.utils.Constants.GERENTE;
import static com.sidert.sidertmovil.utils.Constants.ID_PRESTAMO;
import static com.sidert.sidertmovil.utils.Constants.ID_RESPUESTA;
import static com.sidert.sidertmovil.utils.Constants.IMPRESORA;
import static com.sidert.sidertmovil.utils.Constants.LATITUD;
import static com.sidert.sidertmovil.utils.Constants.LONGITUD;
import static com.sidert.sidertmovil.utils.Constants.MEDIO_PAGO;
import static com.sidert.sidertmovil.utils.Constants.MONTO_PROMESA;
import static com.sidert.sidertmovil.utils.Constants.MOTIVO_ACLARACION;
import static com.sidert.sidertmovil.utils.Constants.MOTIVO_NO_PAGO;
import static com.sidert.sidertmovil.utils.Constants.NUEVO_TELEFONO;
import static com.sidert.sidertmovil.utils.Constants.PAGO_REALIZADO;
import static com.sidert.sidertmovil.utils.Constants.PAGO_REQUERIDO;
import static com.sidert.sidertmovil.utils.Constants.PARAMS;
import static com.sidert.sidertmovil.utils.Constants.RESULTADO_PAGO;
import static com.sidert.sidertmovil.utils.Constants.RESUMEN;
import static com.sidert.sidertmovil.utils.Constants.TBL_CANCELACIONES;
import static com.sidert.sidertmovil.utils.Constants.TBL_NAME;
import static com.sidert.sidertmovil.utils.Constants.TBL_RESUMENES_GESTION;
import static com.sidert.sidertmovil.utils.Constants.TIPO_CARTERA;
import static com.sidert.sidertmovil.utils.Constants.TIPO_GESTION;
import static com.sidert.sidertmovil.utils.Constants.TIPO_IMAGEN;
import static com.sidert.sidertmovil.utils.Constants.TIPO_PRESTAMO;

/**Vista para la obtencion de gestiones realizadas (VIGENTE, COBRANZA, VENCIDA) de individual o grupal*/
public class Gestionadas extends AppCompatActivity {

    private Context ctx;
    private Toolbar tbMain;

    private RecyclerView rvGestionadas;

    private adapter_gestionadas_ind adapter;

    private DBhelper dBhelper;
    private SQLiteDatabase db;

    private String id_prestamo = "0";

    private String tbl = "tbl_name";
    private String tbl_cartera = "tbl_cartera";

    private String tipo_prestamo = "";
    private int tipo_gestion  = 0;

    private SessionManager session;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gestiondas);

        ctx = this;

        session = SessionManager.getInstance(ctx);

        dBhelper = DBhelper.getInstance(ctx);
        db = dBhelper.getWritableDatabase();

        tbMain = findViewById(R.id.TBmain);

        rvGestionadas = findViewById(R.id.rvGestionadas);

        rvGestionadas.setLayoutManager(new LinearLayoutManager(ctx));
        rvGestionadas.setHasFixedSize(false);

        /**Obtencionde datos que se pasaron entre clases*/
        id_prestamo = getIntent().getStringExtra(ID_PRESTAMO);
        tbl = getIntent().getStringExtra(TBL_NAME);
        tbl_cartera = getIntent().getStringExtra(TIPO_CARTERA);
        tipo_prestamo = getIntent().getStringExtra(TIPO_PRESTAMO);
        tipo_gestion = getIntent().getIntExtra(TIPO_GESTION, 0);

    }

    /**Funcion para obtener las respuestas de gestion completadas*/
    private void init (){
        Cursor row = null;
        Log.e("Tabla", tbl);
        /**Se prepara la consulta para obtener las respuestas dependiendo al nombre de tabla
         * que se mando para saber si buscan en TBL_RESPUESTAS_IND_T, TBL_RESPUESTAS_GPO_T
         * TBL_RESPUESTAS_IND_V_T o TBL_RESPUESTAS_INTEGRANTES y se busca por estatus
         * que son lo que se colocan "1","2","3" que corresponden a
         * pendiente de envio, enviadas, canceladas respectivamente el 3 no se ocupa porque
         * aun no se libera la actualizacion para cancelar respuestas pero no afecta que se mande
         * el estatus 3*/
        String sql = "SELECT r.*, COALESCE(c.estatus, '') AS estatus_cancel, COALESCE(c.comentario_admin, '') AS comentario_cancel FROM " + tbl + " r LEFT JOIN " + TBL_CANCELACIONES + " AS c ON r._id = c.id_respuesta AND c.tipo_gestion = ? WHERE r.id_prestamo = ? AND r.estatus  IN (?,?,?) ORDER BY r.fecha_inicio DESC";
        row = db.rawQuery(sql, new String[]{String.valueOf(tipo_gestion),id_prestamo, "1","2","3"});

        if (row.getCount() > 0){
            row.moveToFirst();
            ArrayList<MGestionada> data = new ArrayList<>();
            for(int i = 0; i < row.getCount(); i++){
                MGestionada gestionadas = new MGestionada();
                gestionadas.setIdGestion(row.getString(0));
                /**Se valida el tipo de gestion(1=individual,2=grupal) y tipo de prestamo(vigente,vencida)
                 * por lo de las posiciones de las columnas*/
                if (tipo_gestion == 2 && tipo_prestamo.equals("VENCIDA"))
                    gestionadas.setFechaGestion(row.getString(24));
                else {
                    gestionadas.setFechaGestion(row.getString(23));
                }

                String contacto = "";
                if (tipo_gestion == 2 && tipo_prestamo.equals("VENCIDA"))
                    contacto = row.getString(5);
                else
                    contacto = row.getString(4);

                switch (contacto){
                    case "SI":
                        gestionadas.setContacto("SI CONTACTO");
                        if (tipo_prestamo.equals("VIGENTE") || tipo_gestion == 2 && tipo_prestamo.equals("VENCIDA"))
                            gestionadas.setResultado(row.getString(9));
                        else
                            gestionadas.setResultado(row.getString(8));
                        if (tipo_prestamo.equals("VIGENTE") && row.getString(9).equals("PAGO") || tipo_prestamo.equals("VENCIDA") && row.getString(8).equals("PAGO")) {
                            if (tipo_prestamo.equals("VIGENTE")) {
                                gestionadas.setComentarioBanco("MEDIO PAGO: " + row.getString(12));
                                gestionadas.setMonto(Miscellaneous.moneyFormat(row.getString(15)));
                            }
                            else if(tipo_gestion == 1 && tipo_prestamo.equals("VENCIDA")){
                                gestionadas.setComentarioBanco("MEDIO PAGO: " + row.getString(13));
                                gestionadas.setMonto(Miscellaneous.moneyFormat(row.getString(16)));
                            }
                            else if(tipo_gestion == 2 && tipo_prestamo.equals("VENCIDA")){
                                gestionadas.setComentarioBanco("MEDIO PAGO: " + row.getString(14));
                                gestionadas.setMonto(Miscellaneous.moneyFormat(row.getString(17)));
                            }

                        }
                        else {
                            if (tipo_prestamo.equals("VIGENTE") || tipo_gestion == 2 && tipo_prestamo.equals("VENCIDA"))
                                gestionadas.setComentarioBanco("COMENTARIO: " + row.getString(6));
                            else
                                gestionadas.setComentarioBanco("COMENTARIO: " + row.getString(5));
                            gestionadas.setMonto("");
                        }
                        break;
                    case "NO":
                        gestionadas.setContacto("NO CONTACTO");
                        if (tipo_prestamo.equals("VIGENTE") || tipo_gestion == 2 && tipo_prestamo.equals("VENCIDA")) {
                            gestionadas.setComentarioBanco("COMENTARIO: " + row.getString(6));
                            gestionadas.setResultado(row.getString(9));
                        }
                        else {
                            gestionadas.setComentarioBanco("COMENTARIO: " + row.getString(5));
                            gestionadas.setResultado(row.getString(8));
                        }

                        gestionadas.setMonto("");
                        break;
                    case "ACLARACION":
                        gestionadas.setContacto("ACLARACION");
                        if (tipo_prestamo.equals("VIGENTE"))
                            gestionadas.setComentarioBanco("MOTIVO: " + row.getString(5));
                        gestionadas.setResultado("");
                        gestionadas.setMonto("");
                        break;
                }

                if (tipo_gestion == 1 && tipo_prestamo.equals("VIGENTE")) {
                    gestionadas.setEstatisGestion(row.getString(25));
                    Log.e("EstatusCancel", row.getString(31));
                    gestionadas.setEstatusCancel(row.getString(31));
                    gestionadas.setComentario(row.getString(32));
                }
                else if (tipo_gestion == 2 && tipo_prestamo.equals("VIGENTE")) {
                    gestionadas.setEstatisGestion(row.getString(25));
                    gestionadas.setEstatusCancel(row.getString(32));
                    gestionadas.setComentario(row.getString(33));
                }
                else if (tipo_gestion == 1 && tipo_prestamo.equals("VENCIDA")) {
                    gestionadas.setEstatisGestion(row.getString(26));
                    gestionadas.setEstatusCancel(row.getString(33));
                    gestionadas.setComentario(row.getString(34));
                }
                else if (tipo_gestion == 2 && tipo_prestamo.equals("VENCIDA")) {
                    gestionadas.setEstatisGestion(row.getString(27));
                    gestionadas.setEstatusCancel(row.getString(34));
                    gestionadas.setComentario(row.getString(35));
                }

                data.add(gestionadas);
                Log.e("Gestionadas", Miscellaneous.ConvertToJson(gestionadas));
                row.moveToNext();
            }

            adapter = new adapter_gestionadas_ind(ctx, data, new adapter_gestionadas_ind.Event() {

                @Override
                public void GestionadaClick(MGestionada item) {
                    /**Evento al dar tap a una gestiones para poder ver un resumen de su gestion*/
                    Cursor row;
                    Log.e("id_ges", item.getIdGestion());
                    String sql = "SELECT r.*, COALESCE(rg.nombre_resumen, '') AS nombre_resumen, COALESCE(rg.nombre_cliente, '') AS nombre_cliente FROM " + tbl + " AS r LEFT JOIN "+TBL_RESUMENES_GESTION+" AS rg ON r._id = rg.id_respuesta AND rg.tipo_gestion = '"+tipo_gestion+"' WHERE r._id = ?";
                    row = db.rawQuery(sql, new String[]{item.getIdGestion()});
                    //row = dBhelper.getRecords(tbl, " WHERE _id = ?", "", new String[]{item.getIdGestion()});

                    if (row.getCount() > 0) {
                        row.moveToFirst();
                        Bundle b = new Bundle();
                        b.putString(ESTATUS, item.getEstatisGestion());
                        b.putString(CANCELACION, item.getEstatusCancel());
                        b.putString(ID_RESPUESTA, row.getString(0));
                        b.putString(TIPO_GESTION, String.valueOf(tipo_gestion));
                        b.putString(TIPO_PRESTAMO, tipo_prestamo);
                        String contacto = "";
                        if (tipo_gestion == 2 && tipo_prestamo.equals("VENCIDA")) {
                            b.putDouble(LATITUD, row.getDouble(3));
                            b.putDouble(LONGITUD, row.getDouble(4));
                            b.putString(CONTACTO, row.getString(5));
                            contacto = row.getString(5);
                        }
                        else{
                            b.putDouble(LATITUD, row.getDouble(2));
                            b.putDouble(LONGITUD, row.getDouble(3));
                            b.putString(CONTACTO, row.getString(4));
                            contacto = row.getString(4);
                        }
                        switch (contacto) {
                            case "SI":
                                b.putString(ACTUALIZAR_TELEFONO, row.getString(7));
                                if (row.getString(7).equals("SI"))
                                    b.putString(NUEVO_TELEFONO, row.getString(8));

                                String medio_pago = "";
                                if (tipo_prestamo.equals("VIGENTE") || tipo_gestion == 2 && tipo_prestamo.equals("VENCIDA")) {
                                    b.putString(RESULTADO_PAGO, row.getString(9));
                                    medio_pago = row.getString(9);
                                } else if (tipo_gestion == 1 && tipo_prestamo.equals("VENCIDA")){
                                    b.putString(RESULTADO_PAGO, row.getString(8));
                                    medio_pago = row.getString(8);
                                }

                                switch (medio_pago) {
                                    case "PAGO":
                                        if (tipo_prestamo.equals("VIGENTE")) {
                                            b.putString(MEDIO_PAGO, row.getString(12));
                                            b.putString(FECHA_DEPOSITO, row.getString(13));
                                            b.putString(PAGO_REQUERIDO, row.getString(14));
                                            b.putString(PAGO_REALIZADO, row.getString(15));
                                            if (row.getString(12).equals("EFECTIVO") || row.getString(12).equals("SIDERT")) {
                                                b.putString(IMPRESORA, row.getString(16));
                                                b.putString(FOLIO_TICKET, row.getString(17));
                                            }
                                        }
                                        else if (tipo_gestion == 1 && tipo_prestamo.equals("VENCIDA")){
                                            b.putString(MEDIO_PAGO, row.getString(13));
                                            b.putString(FECHA_DEPOSITO, row.getString(14));
                                            b.putString(PAGO_REQUERIDO, row.getString(15));
                                            b.putString(PAGO_REALIZADO, row.getString(16));
                                            if (row.getString(13).equals("EFECTIVO") || row.getString(13).equals("SIDERT")) {
                                                b.putString(IMPRESORA, row.getString(17));
                                                b.putString(FOLIO_TICKET, row.getString(18));
                                            }
                                        }
                                        else if (tipo_gestion == 2 && tipo_prestamo.equals("VENCIDA")){
                                            b.putString(MEDIO_PAGO, row.getString(14));
                                            b.putString(FECHA_DEPOSITO, row.getString(15));
                                            b.putString(PAGO_REQUERIDO, row.getString(16));
                                            b.putString(PAGO_REALIZADO, row.getString(17));
                                            if (row.getString(14).equals("EFECTIVO") || row.getString(17).equals("SIDERT")) {
                                                b.putString(IMPRESORA, row.getString(18));
                                                b.putString(FOLIO_TICKET, row.getString(19));
                                            }
                                        }
                                        break;
                                    case "NO PAGO":
                                        if (tipo_prestamo.equals("VIGENTE") || tipo_gestion == 2 && tipo_prestamo.equals("VENCIDA")) {
                                            b.putString(MOTIVO_NO_PAGO, row.getString(10));
                                            if (row.getString(10).equals("FALLECIMIENTO"))
                                                b.putString(FECHA_DEFUNCION, row.getString(11));
                                            else if (row.getString(9).equals("PROMESA DE PAGO")){
                                                b.putString(FECHA_PROMESA_PAGO, row.getString(12));
                                                b.putString(MONTO_PROMESA, row.getString(13).trim().replace(",",""));
                                            }
                                            b.putString(COMENTARIO, row.getString(6));
                                        } else {
                                            b.putString(MOTIVO_NO_PAGO, row.getString(9));
                                            if (row.getString(9).equals("FALLECIMIENTO"))
                                                b.putString(FECHA_DEFUNCION, row.getString(10));
                                            else if (row.getString(9).equals("PROMESA DE PAGO")){
                                                b.putString(FECHA_PROMESA_PAGO, row.getString(11));
                                                b.putString(MONTO_PROMESA, row.getString(12).trim().replace(",",""));
                                            }

                                            b.putString(COMENTARIO, row.getString(5));
                                        }
                                        break;
                                }
                                break;
                            case "NO":
                                if (tipo_prestamo.equals("VIGENTE") || tipo_gestion == 2 && tipo_prestamo.equals("VENCIDA"))
                                    b.putString(COMENTARIO, row.getString(6));
                                else
                                    b.putString(COMENTARIO, row.getString(5));
                                break;
                            case "ACLARACION":
                                if (tipo_prestamo.equals("VIGENTE")) {
                                    b.putString(MOTIVO_ACLARACION, row.getString(5));
                                    b.putString(COMENTARIO, row.getString(6));
                                } else if (tipo_gestion == 1 && tipo_prestamo.equals("VENCIDA"))
                                    b.putString(COMENTARIO, row.getString(5));
                                else if (tipo_gestion == 2 && tipo_prestamo.equals("VENCIDA"))
                                    b.putString(COMENTARIO, row.getString(6));
                                break;
                        }

                        if (tipo_prestamo.equals("VIGENTE")){
                            b.putString(EVIDENCIA, row.getString(18));
                            b.putString(TIPO_IMAGEN, row.getString(19));
                            b.putString(GERENTE, row.getString(20));
                            if (row.getString(20).equals("SI"))
                                b.putString(FIRMA, row.getString(21));

                            if (tipo_gestion == 1) {
                                b.putString(RESUMEN, row.getString(31));
                                b.putString(CLIENTE, row.getString(32));
                            }
                            else{
                                b.putString(RESUMEN, row.getString(32));
                                b.putString(CLIENTE, row.getString(33));
                            }
                        }
                        else if (tipo_gestion == 1 && tipo_prestamo.equals("VENCIDA")){
                            b.putString(EVIDENCIA, row.getString(19));
                            b.putString(TIPO_IMAGEN, row.getString(20));
                            b.putString(GERENTE, row.getString(21));
                            if (row.getString(21).equals("SI"))
                                b.putString(FIRMA, row.getString(22));
                            b.putString(RESUMEN, row.getString(33));
                            b.putString(CLIENTE, row.getString(34));
                        }
                        else if (tipo_gestion == 2 && tipo_prestamo.equals("VENCIDA")){
                            b.putString(EVIDENCIA, row.getString(20));
                            b.putString(TIPO_IMAGEN, row.getString(21));
                            b.putString(GERENTE, row.getString(22));
                            if (row.getString(22).equals("SI"))
                                b.putString(FIRMA, row.getString(23));
                            b.putString(RESUMEN, row.getString(34));
                            b.putString(CLIENTE, row.getString(35));
                        }

                        Log.e("SIDERTMOVIL", b.toString());

                        /**Una vez cargado todos los datos a mandar a la vista para el resumen se lanza la actividad
                         * VisteGestion.class que mostrará los datos*/
                        Intent i_preview = new Intent(ctx, VistaGestion.class);
                        i_preview.putExtra(PARAMS,b);
                        startActivity(i_preview);
                    }
                }

                @Override
                public void SendClickLong(MGestionada item) {
                    /**Evento de un tap largo para envio de gestion porque está en pendiente de envio
                     * ya no se ocupa porque ya no está la funcion*/
                    //Toast.makeText(ctx, "Enviando Gestion", Toast.LENGTH_SHORT).show();

                }
            });

            rvGestionadas.setAdapter(adapter);
        }
    }



    @Override
    protected void onResume() {
        super.onResume();
        init();
    }
}
