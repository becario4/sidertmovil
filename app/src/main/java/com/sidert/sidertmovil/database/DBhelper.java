package com.sidert.sidertmovil.database;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteStatement;
import android.util.Log;
import android.widget.Toast;

import com.sidert.sidertmovil.utils.Constants;
import com.sidert.sidertmovil.utils.Miscellaneous;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

import static com.sidert.sidertmovil.utils.Constants.DATOS_AVAL_IND;
import static com.sidert.sidertmovil.utils.Constants.DATOS_AVAL_IND_T;
import static com.sidert.sidertmovil.utils.Constants.DATOS_CLIENTE_IND;
import static com.sidert.sidertmovil.utils.Constants.DATOS_CLIENTE_IND_T;
import static com.sidert.sidertmovil.utils.Constants.DATOS_CONYUGE_IND;
import static com.sidert.sidertmovil.utils.Constants.DATOS_CONYUGE_IND_T;
import static com.sidert.sidertmovil.utils.Constants.DATOS_CREDITO_IND;
import static com.sidert.sidertmovil.utils.Constants.DATOS_CREDITO_IND_T;
import static com.sidert.sidertmovil.utils.Constants.DATOS_ECONOMICOS_IND;
import static com.sidert.sidertmovil.utils.Constants.DATOS_ECONOMICOS_IND_T;
import static com.sidert.sidertmovil.utils.Constants.DATOS_NEGOCIO_IND;
import static com.sidert.sidertmovil.utils.Constants.DATOS_NEGOCIO_IND_T;
import static com.sidert.sidertmovil.utils.Constants.DATOS_REFERENCIA_IND;
import static com.sidert.sidertmovil.utils.Constants.DATOS_REFERENCIA_IND_T;
import static com.sidert.sidertmovil.utils.Constants.DOCUMENTOS;
import static com.sidert.sidertmovil.utils.Constants.DOCUMENTOS_T;
import static com.sidert.sidertmovil.utils.Constants.ENVIROMENT;

public class DBhelper extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "sidert.db";

    public DBhelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        /*db.execSQL(SidertTables.SidertEntry.CREATE_TABLE_ASESSORS);
        db.execSQL(SidertTables.SidertEntry.CREATE_TABLE_GESTORS);
        db.execSQL(SidertTables.SidertEntry.CREATE_TABLE_ASESSORS_T);
        db.execSQL(SidertTables.SidertEntry.CREATE_TABLE_GESTORS_T);*/
        db.execSQL(SidertTables.SidertEntry.CREATE_TABLE_VIGENTE);
        db.execSQL(SidertTables.SidertEntry.CREATE_TABLE_VENCIDA);
        db.execSQL(SidertTables.SidertEntry.CREATE_TABLE_REIMPRESION);
        db.execSQL(SidertTables.SidertEntry.CREATE_TABLE_FICHAS);
        db.execSQL(SidertTables.SidertEntry.CREATE_TABLE_FICHAS_T);
        db.execSQL(SidertTables.SidertEntry.CREATE_TABLE_LOGIN_REPORT);
        db.execSQL(SidertTables.SidertEntry.CREATE_TABLE_LOGIN_REPORT_T);
        db.execSQL(SidertTables.SidertEntry.CREATE_TABLE_GEOLOCALIZACION);
        db.execSQL(SidertTables.SidertEntry.CREATE_TABLE_GEOLOCALIZACION_T);
        db.execSQL(SidertTables.SidertEntry.CREATE_TABLE_ESTADOS);
        db.execSQL(SidertTables.SidertEntry.CREATE_TABLE_MUNICIPIOS);
        db.execSQL(SidertTables.SidertEntry.CREATE_TABLE_COLONIAS);
        db.execSQL(SidertTables.SidertEntry.CREATE_TABLE_SECTORES);
        //db.execSQL(SidertTables.SidertEntry.CREATE_TABLE_IDENTIFICACIONES);
        db.execSQL(SidertTables.SidertEntry.CREATE_TABLE_OCUPACIONES);
        db.execSQL(SidertTables.SidertEntry.CREATE_TABLE_SINCRONIZADO);
        db.execSQL(SidertTables.SidertEntry.CREATE_TABLE_SINCRONIZADO_T);
        db.execSQL(SidertTables.SidertEntry.CREATE_TABLE_SOLICITUDES_T);
        db.execSQL(SidertTables.SidertEntry.CREATE_TABLE_DATOS_CREDITO_IND_T);
        db.execSQL(SidertTables.SidertEntry.CREATE_TABLE_CLIENTE_IND_T);
        db.execSQL(SidertTables.SidertEntry.CREATE_TABLE_CONYUGE_IND_T);
        db.execSQL(SidertTables.SidertEntry.CREATE_TABLE_ECONOMICOS_IND_T);
        db.execSQL(SidertTables.SidertEntry.CREATE_TABLE_NEGOCIO_IND_T);
        db.execSQL(SidertTables.SidertEntry.CREATE_TABLE_AVAL_IND_T);
        db.execSQL(SidertTables.SidertEntry.CREATE_TABLE_REFERENCIA_IND_T);
        db.execSQL(SidertTables.SidertEntry.CREATE_TBL_DATOS_CREDITO_GPO_T);
        db.execSQL(SidertTables.SidertEntry.CREATE_TBL_INTEGRANTES_GPO_T);
        db.execSQL(SidertTables.SidertEntry.CREATE_TBL_TELEFONOS_INTEGRANTE_T);
        db.execSQL(SidertTables.SidertEntry.CREATE_TBL_DOMICILIO_INTEGRANTE_T);
        db.execSQL(SidertTables.SidertEntry.CREATE_TBL_NEGOCIO_INTEGRANTE_T);
        db.execSQL(SidertTables.SidertEntry.CREATE_TBL_CONYUGE_INTEGRANTE_T);
        db.execSQL(SidertTables.SidertEntry.CREATE_TBL_OTROS_DATOS_INTEGRANTE_T);
        db.execSQL(SidertTables.SidertEntry.CREATE_TBL_DOCUMENTOS_CLIENTES_T);
        db.execSQL(SidertTables.SidertEntry.CREATE_TBL_DOCUMENTOS_INTEGRANTE_T);
        db.execSQL(SidertTables.SidertEntry.CREATE_TABLE_VIGENTE_T);
        db.execSQL(SidertTables.SidertEntry.CREATE_TABLE_VENCIDA_T);
        db.execSQL(SidertTables.SidertEntry.CREATE_TABLE_REIMPRESION_T);
        db.execSQL(SidertTables.SidertEntry.CREATE_TABLE_RECIBOS_CIRCULO_CREDITO_T);

        Log.v("CreacionTablas", "se crearon tablas");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        //db.execSQL("DROP TABLE IF EXISTS " + SidertTables.SidertEntry.TABLE_FICHAS_T);
        //db.execSQL(SidertTables.SidertEntry.CREATE_TABLE_FICHAS_T);
        //db.execSQL("DROP TABLE IF EXISTS datos_cliente_ind_t");

        try { db.execSQL(SidertTables.SidertEntry.CREATE_TABLE_VIGENTE); }
        catch (Exception e) { Log.e("Tablas", "Catch ya existe las tabla VIGENTE"); }

        try { db.execSQL(SidertTables.SidertEntry.CREATE_TABLE_VENCIDA); }
        catch (Exception e) { Log.e("Tablas", "Catch ya existe las tabla VENCIDA"); }

        try { db.execSQL(SidertTables.SidertEntry.CREATE_TABLE_REIMPRESION); }
        catch (Exception e) { Log.e("Tablas", "Catch ya existe las tabla REIMPRESION"); }

        try { db.execSQL(SidertTables.SidertEntry.CREATE_TABLE_VIGENTE_T); }
        catch (Exception e) { Log.e("Tablas", "Catch ya existe las tabla VIGENTE_T"); }

        try { db.execSQL(SidertTables.SidertEntry.CREATE_TABLE_VENCIDA_T); }
        catch (Exception e) { Log.e("Tablas", "Catch ya existe las tabla VENCIDA_T"); }

        try { db.execSQL(SidertTables.SidertEntry.CREATE_TABLE_REIMPRESION_T); }
        catch (Exception e) { Log.e("Tablas", "Catch ya existe las tabla REIMPRESION_T"); }

        try { db.execSQL(SidertTables.SidertEntry.CREATE_TABLE_GEOLOCALIZACION); }
        catch (Exception e) { Log.e("Tablas", "Catch ya existe las tabla GEOLOCALIZACION"); }

        try { db.execSQL(SidertTables.SidertEntry.CREATE_TABLE_GEOLOCALIZACION_T); }
        catch (Exception e) { Log.e("Tablas", "Catch ya existe las tabla GEOLOCALIZACION_T"); }

        try { db.execSQL(SidertTables.SidertEntry.CREATE_TABLE_LOGIN_REPORT); }
        catch (Exception e) { Log.e("Tablas", "Catch ya existe las tabla LOGIN_REPORT"); }

        try { db.execSQL(SidertTables.SidertEntry.CREATE_TABLE_LOGIN_REPORT_T); }
        catch (Exception e) { Log.e("Tablas", "Catch ya existe las tabla LOGIN_REPORT_T"); }

        try { db.execSQL(SidertTables.SidertEntry.CREATE_TABLE_FICHAS); }
        catch (Exception e) { Log.e("Tablas", "Catch ya existe las tabla FICHAS"); }

        try { db.execSQL(SidertTables.SidertEntry.CREATE_TABLE_FICHAS_T); }
        catch (Exception e) { Log.e("Tablas", "Catch ya existe las tabla FICHAS_T"); }

        try { db.execSQL(SidertTables.SidertEntry.CREATE_TABLE_ESTADOS); }
        catch (Exception e) { Log.e("Tablas", "Catch ya existe las tabla ESTADOS"); }

        try { db.execSQL(SidertTables.SidertEntry.CREATE_TABLE_MUNICIPIOS); }
        catch (Exception e) { Log.e("Tablas", "Catch ya existe las tabla MUNICIPIOS"); }

        try { db.execSQL(SidertTables.SidertEntry.CREATE_TABLE_COLONIAS); }
        catch (Exception e) { Log.e("Tablas", "Catch ya existe las tabla COLONIAS"); }

        try { db.execSQL(SidertTables.SidertEntry.CREATE_TABLE_SECTORES); }
        catch (Exception e) { Log.e("Tablas", "Catch ya existe las tabla SECTORES"); }

        //try { db.execSQL(SidertTables.SidertEntry.CREATE_TABLE_IDENTIFICACIONES); }
        //catch (Exception e) { Log.e("Tablas", "Catch ya existe las tabla IDENTIFICACIONES"); }

        try { db.execSQL(SidertTables.SidertEntry.CREATE_TABLE_OCUPACIONES); }
        catch (Exception e) { Log.e("Tablas", "Catch ya existe las tabla OCUPACIONES"); }

        try { db.execSQL(SidertTables.SidertEntry.CREATE_TABLE_SINCRONIZADO); }
        catch (Exception e) { Log.e("Tablas", "Catch ya existe las tabla SINCRONIZADO"); }

        try { db.execSQL(SidertTables.SidertEntry.CREATE_TABLE_SINCRONIZADO_T); }
        catch (Exception e) { Log.e("Tablas", "Catch ya existe las tabla SINCRONIZADO_T"); }

        try { db.execSQL(SidertTables.SidertEntry.ADD_CREATE_AT_GEO); }
        catch (Exception e) { Log.e("ADD GEO CREATE", "ya contiene la columna"); }

        try { db.execSQL(SidertTables.SidertEntry.ADD_CREATE_AT_GEO_T); }
        catch (Exception e) { Log.e("ADD GEO_T CREATE", "ya contiene la columna"); }

        try { db.execSQL(SidertTables.SidertEntry.ADD_IS_RUTA_T); }
        catch (Exception e) { Log.e("ADD GEO_T CREATE", "ya contiene la columna"); }

        try { db.execSQL(SidertTables.SidertEntry.CREATE_TABLE_SOLICITUDES_T); }
        catch (Exception e) { Log.e("Tablas", "Catch ya existe las tabla SOLICITUDES_T"); }

        try { db.execSQL(SidertTables.SidertEntry.CREATE_TABLE_DATOS_CREDITO_IND_T); }
        catch (Exception e) {  Log.e("Tablas", "Catch tabla DATOS_CREDITO_IND_T"); }

        try { db.execSQL(SidertTables.SidertEntry.CREATE_TABLE_CLIENTE_IND_T); }
        catch (Exception e) {  Log.e("Tablas", "Catch tabla CLIENTE_IND_T"); }

        try { db.execSQL(SidertTables.SidertEntry.CREATE_TABLE_CONYUGE_IND_T); }
        catch (Exception e) {  Log.e("Tablas", "Catch tabla CONYUGE_IND_T"); }

        try { db.execSQL(SidertTables.SidertEntry.CREATE_TABLE_ECONOMICOS_IND_T); }
        catch (Exception e) {  Log.e("Tablas", "Catch tabla ECONOMICOS_IND_T"); }

        try { db.execSQL(SidertTables.SidertEntry.CREATE_TABLE_NEGOCIO_IND_T); }
        catch (Exception e) {  Log.e("Tablas", "Catch tabla NEGOCIO_IND_T"); }

        try { db.execSQL(SidertTables.SidertEntry.CREATE_TABLE_AVAL_IND_T); }
        catch (Exception e) {  Log.e("Tablas", "Catch tabla AVAL_IND_T"); }

        try { db.execSQL(SidertTables.SidertEntry.CREATE_TABLE_REFERENCIA_IND_T); }
        catch (Exception e) {  Log.e("Tablas", "Catch tabla REFERENCIA_IND_T"); }

        try { db.execSQL(SidertTables.SidertEntry.CREATE_TBL_DATOS_CREDITO_GPO_T); }
        catch (Exception e) {  Log.e("Tablas", "Catch tabla DATOS_CREDITO_GPO_T"); }

        try { db.execSQL(SidertTables.SidertEntry.CREATE_TBL_INTEGRANTES_GPO_T); }
        catch (Exception e) {  Log.e("Tablas", "Catch tabla INTEGRANTES_GPO_T"); }

        try { db.execSQL(SidertTables.SidertEntry.CREATE_TBL_TELEFONOS_INTEGRANTE_T); }
        catch (Exception e) {  Log.e("Tablas", "Catch tabla TELEFONOS_INTEGRANTE_T"); }

        try { db.execSQL(SidertTables.SidertEntry.CREATE_TBL_DOMICILIO_INTEGRANTE_T); }
        catch (Exception e) {  Log.e("Tablas", "Catch tabla DOMICILIO_INTEGRANTE_T"); }

        try { db.execSQL(SidertTables.SidertEntry.CREATE_TBL_NEGOCIO_INTEGRANTE_T); }
        catch (Exception e) {  Log.e("Tablas", "Catch tabla NEGOCIO_INTEGRANTE_T"); }

        try { db.execSQL(SidertTables.SidertEntry.CREATE_TBL_CONYUGE_INTEGRANTE_T); }
        catch (Exception e) {  Log.e("Tablas", "Catch tabla CONYUGE_INTEGRANTE_T"); }

        try { db.execSQL(SidertTables.SidertEntry.CREATE_TBL_OTROS_DATOS_INTEGRANTE_T); }
        catch (Exception e) {  Log.e("Tablas", "Catch tabla OTROS_DATOS_INTEGRANTE_T"); }

        try { db.execSQL(SidertTables.SidertEntry.CREATE_TBL_DOCUMENTOS_CLIENTES_T); }
        catch (Exception e) {  Log.e("Tablas", "Catch tabla DOCUMENTOS_CLIENTES_T"); }

        try { db.execSQL(SidertTables.SidertEntry.CREATE_TBL_DOCUMENTOS_INTEGRANTE_T); }
        catch (Exception e) {  Log.e("Tablas", "Catch tabla DOCUMENTOS_INTEGRANTE_T"); }

        try{ db.execSQL(SidertTables.SidertEntry.CREATE_TABLE_RECIBOS_CIRCULO_CREDITO_T); }
        catch (Exception e) {}

    }

    public void saveRecibosVigente(SQLiteDatabase db, String table, HashMap<Integer, String> params){
        db.beginTransaction();
        String sql = "INSERT INTO " + table + "(" +
                SidertTables.SidertEntry.ASSESOR_ID + ", " +
                SidertTables.SidertEntry.FOLIO + ", " +
                SidertTables.SidertEntry.TYPE_IMPRESSION + ", " +
                SidertTables.SidertEntry.AMOUNT + ", " +
                SidertTables.SidertEntry.EXTERNAL_ID + ", " +
                SidertTables.SidertEntry.ERRORS + ", " +
                SidertTables.SidertEntry.GENERATED_AT + ", " +
                SidertTables.SidertEntry.SENT_AT + ", " +
                SidertTables.SidertEntry.STATUS + ", " +
                SidertTables.SidertEntry.CLV_CLIENTE + ") VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        SQLiteStatement pInsert = db.compileStatement(sql);
        pInsert.bindString(1, params.get(0));
        pInsert.bindLong(2, Long.parseLong(params.get(1)));
        pInsert.bindString(3, params.get(2));
        pInsert.bindString(4, params.get(3));
        pInsert.bindString(5, params.get(4));
        pInsert.bindString(6, params.get(5));
        pInsert.bindString(7, params.get(6));
        pInsert.bindString(8, params.get(7));
        pInsert.bindString(9, params.get(8));
        pInsert.bindString(10, params.get(9));
        pInsert.execute();

        db.setTransactionSuccessful();
        db.endTransaction();
    }

    public void saveRecibosVencida(SQLiteDatabase db, String table_name, HashMap<Integer, String> params){
        db.beginTransaction();
        String sql = "INSERT INTO " + table_name + "(" +
                SidertTables.SidertEntry.ASSESOR_ID + ", " +
                SidertTables.SidertEntry.FOLIO + ", " +
                SidertTables.SidertEntry.TYPE_IMPRESSION + ", " +
                SidertTables.SidertEntry.AMOUNT + ", " +
                SidertTables.SidertEntry.EXTERNAL_ID + ", " +
                SidertTables.SidertEntry.ERRORS + ", " +
                SidertTables.SidertEntry.GENERATED_AT + ", " +
                SidertTables.SidertEntry.SENT_AT + ", " +
                SidertTables.SidertEntry.STATUS + ", " +
                SidertTables.SidertEntry.CLV_CLIENTE + ") VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        SQLiteStatement pInsert = db.compileStatement(sql);
        pInsert.bindString(1, params.get(0));
        pInsert.bindLong(2, Long.parseLong(params.get(1)));
        pInsert.bindString(3, params.get(2));
        pInsert.bindString(4, params.get(3));
        pInsert.bindString(5, params.get(4));
        pInsert.bindString(6, params.get(5));
        pInsert.bindString(7, params.get(6));
        pInsert.bindString(8, params.get(7));
        pInsert.bindString(9, params.get(8));
        pInsert.bindString(10, params.get(9));
        pInsert.execute();

        db.setTransactionSuccessful();
        db.endTransaction();
    }

    public void saveReimpresion(SQLiteDatabase db, String table_name, HashMap<Integer, String> params){
        db.beginTransaction();
        String sql = "INSERT INTO " + table_name + "(" +
                SidertTables.SidertEntry.EXTERNAL_ID + ", " +
                SidertTables.SidertEntry.ASSESOR_ID + ", " +
                SidertTables.SidertEntry.SERIE_ID + ", " +
                SidertTables.SidertEntry.CLV_IMPRESION + ", " +
                SidertTables.SidertEntry.GENERATED_AT + ", " +
                SidertTables.SidertEntry.SENT_AT + ", " +
                SidertTables.SidertEntry.TYPE_IMPRESSION + ", " +
                SidertTables.SidertEntry.AMOUNT + ", " +
                SidertTables.SidertEntry.NUEVO_FOLIO + ", " +
                SidertTables.SidertEntry.FOLIO_ANTERIOR + ", " +
                SidertTables.SidertEntry.INCIDENCIA + ", " +
                SidertTables.SidertEntry.CLV_CLIENTE + ", " +
                SidertTables.SidertEntry.STATUS + ") VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        SQLiteStatement pInsert = db.compileStatement(sql);
        pInsert.bindString(1, params.get(0));
        pInsert.bindString(2, params.get(1));
        pInsert.bindString(3, params.get(2));
        pInsert.bindString(4, params.get(3));
        pInsert.bindString(5,params.get(4));
        pInsert.bindString(6,params.get(5));
        pInsert.bindString(7,params.get(6));
        pInsert.bindString(8, params.get(7));
        pInsert.bindLong(9, Long.parseLong(params.get(8)));
        pInsert.bindLong(10, Long.parseLong(params.get(9)));
        pInsert.bindString(11, params.get(10));
        pInsert.bindString(12, params.get(11));
        pInsert.bindLong(13, Long.parseLong(params.get(12)));
        pInsert.execute();

        db.setTransactionSuccessful();
        db.endTransaction();
        Log.v("Tag-Guardar","Termina de guardar");
    }

    public void saveEstados(SQLiteDatabase db, HashMap<Integer, String> params) {
        db.beginTransaction();
        String sql = "INSERT INTO " + SidertTables.SidertEntry.TABLE_ESTADOS + "(" +
                SidertTables.SidertEntry.ESTADO_ID + ", " +
                SidertTables.SidertEntry.ESTADO_NOMBRE + ", " +
                SidertTables.SidertEntry.PAIS_ID + ") VALUES(?, ?, ?)";
        SQLiteStatement pInsert = db.compileStatement(sql);
        pInsert.bindLong(1, Long.parseLong(params.get(0)));     //ESTADO ID       1
        pInsert.bindString(2, params.get(1));                   //NOMBRE ESTADO   2
        pInsert.bindLong(3, Long.parseLong(params.get(2)));     //PAIS ID         3

        pInsert.execute();

        db.setTransactionSuccessful();
        db.endTransaction();
    }

    public void saveMunicipios(SQLiteDatabase db, HashMap<Integer, String> params) {
        db.beginTransaction();
        String sql = "INSERT INTO " + SidertTables.SidertEntry.TABLE_MUNICIPIOS + "(" +
                SidertTables.SidertEntry.MUNICIPIO_ID + ", " +
                SidertTables.SidertEntry.MUNICIPIO_NOMBRE + ", " +
                SidertTables.SidertEntry.ESTADO_ID + ") VALUES(?, ?, ?)";
        SQLiteStatement pInsert = db.compileStatement(sql);
        pInsert.bindLong(1, Long.parseLong(params.get(0)));     //MUNICIPIO ID       1
        pInsert.bindString(2, params.get(1));                   //NOMBRE MUNICIPIO   2
        pInsert.bindLong(3, Long.parseLong(params.get(2)));     //ESTADO ID          3

        pInsert.execute();

        db.setTransactionSuccessful();
        db.endTransaction();
    }

    public void saveColonias(SQLiteDatabase db, HashMap<Integer, String> params) {
        db.beginTransaction();
        String sql = "INSERT INTO " + SidertTables.SidertEntry.TABLE_COLONIAS + "(" +
                SidertTables.SidertEntry.COLONIA_ID + ", " +
                SidertTables.SidertEntry.COLONIA_NOMBRE + ", " +
                SidertTables.SidertEntry.CP + ", " +
                SidertTables.SidertEntry.MUNICIPIO_ID + ") VALUES(?, ?, ?, ?)";
        SQLiteStatement pInsert = db.compileStatement(sql);
        pInsert.bindLong(1, Long.parseLong(params.get(0)));     //COLONIA ID       1
        pInsert.bindString(2, params.get(1));                   //COLONIA NOMBRE   2
        pInsert.bindLong(3, Long.parseLong(params.get(2)));     //CODIGO POSTAL    3
        pInsert.bindLong(4, Long.parseLong(params.get(3)));     //MUNICIPIO ID     4

        pInsert.execute();

        db.setTransactionSuccessful();
        db.endTransaction();
    }

    public void saveSectores(SQLiteDatabase db, HashMap<Integer, String> params) {
        db.beginTransaction();
        String sql = "INSERT INTO " + SidertTables.SidertEntry.TABLE_SECTORES + "(" +
                SidertTables.SidertEntry.SECTOR_ID + ", " +
                SidertTables.SidertEntry.SECTOR_NOMBRE + ", " +
                SidertTables.SidertEntry.NIVEL_RIESGO + ") VALUES(?, ?, ?)";
        SQLiteStatement pInsert = db.compileStatement(sql);
        pInsert.bindLong(1, Long.parseLong(params.get(0)));     //SECTOR ID       1
        pInsert.bindString(2, params.get(1));                   //SECTOR NOMBRE   2
        pInsert.bindLong(3, Long.parseLong(params.get(2)));     //NIVEL RIESGO    3

        pInsert.execute();

        db.setTransactionSuccessful();
        db.endTransaction();
    }

    public void saveIdentificaciones(SQLiteDatabase db, HashMap<Integer, String> params) {
        db.beginTransaction();
        String sql = "INSERT INTO " + SidertTables.SidertEntry.TABLE_IDENTIFICACIONES + "(" +
                SidertTables.SidertEntry.IDENTIFICACION_ID + ", " +
                SidertTables.SidertEntry.IDENTIFICACION_NOMBRE + ") VALUES(?, ?)";
        SQLiteStatement pInsert = db.compileStatement(sql);
        pInsert.bindLong(1, Long.parseLong(params.get(0)));     //IDENTIFICACIOn ID       1
        pInsert.bindString(2, params.get(1));                   //IDENTIFICACION NOMBRE   2

        pInsert.execute();

        db.setTransactionSuccessful();
        db.endTransaction();
    }

    public void saveOcupaciones(SQLiteDatabase db, HashMap<Integer, String> params) {
        db.beginTransaction();
        String sql = "INSERT INTO " + SidertTables.SidertEntry.TABLE_OCUPACIONES + "(" +
                SidertTables.SidertEntry.OCUPACION_ID + ", " +
                SidertTables.SidertEntry.OCUPACION_NOMBRE + ", " +
                SidertTables.SidertEntry.OCUPACION_CLAVE + ", " +
                SidertTables.SidertEntry.NIVEL_RIESGO + ", " +
                SidertTables.SidertEntry.SECTOR_ID + ") VALUES(?, ?, ?, ?, ?)";
        SQLiteStatement pInsert = db.compileStatement(sql);
        pInsert.bindLong(1, Long.parseLong(params.get(0)));     //OCUPACION ID       1
        pInsert.bindString(2, params.get(1));                   //OCUPACION NOMBRE   2
        pInsert.bindString(3, params.get(2));                   //OCUPACION CLAVE    3
        pInsert.bindLong(4, Long.parseLong(params.get(3)));     //NIVEL RIESGO       4
        pInsert.bindLong(5, Long.parseLong(params.get(4)));     //SECTOR ID          5

        pInsert.execute();

        db.setTransactionSuccessful();
        db.endTransaction();
    }

    public void saveRecordsFichas(SQLiteDatabase db, String table_name, HashMap<Integer, String> params) {
        db.beginTransaction();
        String sql = "INSERT INTO " + table_name + "(" +
                SidertTables.SidertEntry.ASESOR_ID + ", " +
                SidertTables.SidertEntry.EXTERNAL_ID + ", " +
                SidertTables.SidertEntry.FORM + ", " +
                SidertTables.SidertEntry.FECHA_ASIGNACION + ", " +
                SidertTables.SidertEntry.DIA_SEMANA + ", " +
                SidertTables.SidertEntry.FECHA_PAGO + ", " +
                SidertTables.SidertEntry.PRESTAMO_OBJ + ", " +
                SidertTables.SidertEntry.NOMBRE + ", " +
                SidertTables.SidertEntry.CLAVE + ", " +
                SidertTables.SidertEntry.DATOS_OBJ + ", " +
                SidertTables.SidertEntry.AVAL_OBJ + ", " +
                SidertTables.SidertEntry.PRESIDENTA_OBJ + ", " +
                SidertTables.SidertEntry.TESORERA_OBJ + ", " +
                SidertTables.SidertEntry.SECRETARIA_OBJ + ", " +
                SidertTables.SidertEntry.REPORTE_OMEGA_OBJ + ", " +
                SidertTables.SidertEntry.TABLA_PAGOS_OBJ + ", " +
                SidertTables.SidertEntry.IMPRESION + ", " +
                SidertTables.SidertEntry.FECHA_INI + ", " +
                SidertTables.SidertEntry.FECHA_TER + ", " +
                SidertTables.SidertEntry.FECHA_ENV + ", " +
                SidertTables.SidertEntry.FECHA_CON + ", " +
                SidertTables.SidertEntry.RESPUESTA + ", " +
                SidertTables.SidertEntry.STATUS + ", " +
                SidertTables.SidertEntry.TIMESTAMP + ") VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        SQLiteStatement pInsert = db.compileStatement(sql);
        pInsert.bindString(1, params.get(0));                   //ASESOR ID             1
        pInsert.bindString(2, params.get(1));                   //EXTERNAL ID           2
        pInsert.bindString(3, params.get(2));                   //NOMBRE FORMULARIO     3
        pInsert.bindString(4, params.get(3));                   //FECHA ASIGNACION      4
        pInsert.bindString(5, params.get(4));                   //DIA SEMANA            5
        pInsert.bindString(6, params.get(5));                   //FECHA PAGO            6
        pInsert.bindString(7, params.get(6));                   //PRESTAMO OBJ          7
        pInsert.bindString(8, params.get(7));                   //NOMBRE                8
        pInsert.bindString(9, params.get(8));                   //CLAVE                 9
        pInsert.bindString(10, params.get(9));                  //DATOS OBJ             10
        pInsert.bindString(11, params.get(10));                 //AVAL OBJ              11
        pInsert.bindString(12, params.get(11));                 //PRESIDENTA OBJ        12
        pInsert.bindString(13, params.get(12));                 //TESORERA OBJ          13
        pInsert.bindString(14, params.get(13));                 //SECRETARIA OBJ        14
        pInsert.bindString(15, params.get(14));                 //REPORTE OMEGA OBJ     15
        pInsert.bindString(16, params.get(15));                 //TABLA PAGOS OBJ       16
        pInsert.bindLong(17, Integer.parseInt(params.get(16))); //IMPRESION             17
        pInsert.bindString(18, params.get(17));                 //FECHA INICIO          18
        pInsert.bindString(19, params.get(18));                 //FECHA TERMINADO       19
        pInsert.bindString(20, params.get(19));                 //FECHA ENVIADO         20
        pInsert.bindString(21, params.get(20));                 //FECHA CONFIRMADO      21
        pInsert.bindString(22, params.get(21));                 //RESPUESTA             22
        pInsert.bindLong(23, Integer.parseInt(params.get(22))); //ESTATUS               23
        pInsert.bindString(24, params.get(23));                 //TIMESTAMP             24
        pInsert.execute();

        Log.v("nombreSave", params.get(7));
        db.setTransactionSuccessful();
        db.endTransaction();

    }

    public void saveRecordsGeo(SQLiteDatabase db, String table_name, HashMap<Integer, String> params) {
        db.beginTransaction();
        String sql = "INSERT INTO " + table_name + "(" +
                SidertTables.SidertEntry.FICHA_ID + "," +
                SidertTables.SidertEntry.ASESOR_NOMBRE + "," +
                SidertTables.SidertEntry.TIPO_FICHA + "," +
                SidertTables.SidertEntry.NOMBRE + "," +
                SidertTables.SidertEntry.NUM_SOLICITUD + "," +
                SidertTables.SidertEntry.CLIE_GPO_ID + "," +
                SidertTables.SidertEntry.CLIE_GPO_CLV + "," +
                SidertTables.SidertEntry.DIRECCION + "," +
                SidertTables.SidertEntry.COLONIA + "," +
                SidertTables.SidertEntry.FECHA_ENT + "," +
                SidertTables.SidertEntry.FECHA_VEN + "," +
                SidertTables.SidertEntry.DATA + "," +
                SidertTables.SidertEntry.RES_UNO + "," +
                SidertTables.SidertEntry.RES_DOS + "," +
                SidertTables.SidertEntry.RES_TRES + "," +
                SidertTables.SidertEntry.FECHA_ENV_UNO + "," +
                SidertTables.SidertEntry.FECHA_ENV_DOS + "," +
                SidertTables.SidertEntry.FECHA_ENV_TRES + "," +
                SidertTables.SidertEntry.FECHA_ENV + "," +
                SidertTables.SidertEntry.FECHA_TER + "," +
                SidertTables.SidertEntry.STATUS + "," +
                SidertTables.SidertEntry.CREATE_AT + ") VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        SQLiteStatement pInsert = db.compileStatement(sql);
        pInsert.bindString(1, params.get(0));                   //FICHA ID                                1
        pInsert.bindString(2, params.get(1));                   //NOMBRE ASESOR                           2
        pInsert.bindLong(3, Long.parseLong(params.get(2)));     //TIPO FICHA (1 = IND | 2 = GPO)          3
        pInsert.bindString(4, params.get(3));                   //NOMBRE (CLIENTE O GRUPO)                4
        pInsert.bindString(5, params.get(4));                   //NUMERO SOLICITUD                        5
        pInsert.bindString(6, params.get(5));                   //CLIENTE O GRUPO ID                      6
        pInsert.bindString(7, params.get(6));                   //CLIENTE O GRUPO CLAVE                   7
        pInsert.bindString(8, params.get(7));                   //DIRECCION                               8
        pInsert.bindString(9, params.get(8));                   //COLONIA                                 9
        pInsert.bindString(10, params.get(9));                  //FECHA ENTREGA                          10
        pInsert.bindString(11, params.get(10));                 //FECHA VENCIMIENTO                      11
        pInsert.bindString(12, params.get(11));                 //DATA                                   12
        pInsert.bindString(13, params.get(12));                 //RESPUESTA UNO (CLIENTE|PRESIDENTE)     13
        pInsert.bindString(14, params.get(13));                 //RESPUESTA DOS (NEGOCIO|TESORERO)       14
        pInsert.bindString(15, params.get(14));                 //RESPUESTA TRES (AVAL|SECRETARIO)       15
        pInsert.bindString(16, params.get(15));                 //FECHA ENVIO UNO (CLIENTE|PRESIDENTE)   16
        pInsert.bindString(17, params.get(16));                 //FECHA ENVIO DOS (NEGOCIO|TESORERO)     17
        pInsert.bindString(18, params.get(17));                 //FECHA ENVIO TRES (AVAL|SECRETARIO)     18
        pInsert.bindString(19, params.get(18));                 //FECHA DE ENVIO                         19
        pInsert.bindString(20, params.get(19));                 //FECHA DE TERMINO                       20
        pInsert.bindLong(21, Long.parseLong(params.get(20)));   //ESTATUS DE FICHA                       21
        pInsert.bindString(22, params.get(21));                 //FECHA DE CREADO                        22
        pInsert.execute();

        db.setTransactionSuccessful();
        db.endTransaction();

    }

    public void saveRecordLogin(SQLiteDatabase db, String table_name, HashMap<Integer, String> params) {
        db.beginTransaction();
        String sql = "INSERT INTO " + table_name + "(" +
                SidertTables.SidertEntry.SERIE_ID + ", " +
                SidertTables.SidertEntry.NOMBRE + ", " +
                SidertTables.SidertEntry.LOGIN_TIMESTAMP + ", " +
                SidertTables.SidertEntry.FECHA_ENV + ", " +
                SidertTables.SidertEntry.STATUS + ") VALUES(?, ?, ?, ?, ?)";
        SQLiteStatement pInsert = db.compileStatement(sql);
        pInsert.bindString(1, params.get(0));                   //SERIE ID                 1
        pInsert.bindString(2, params.get(1));                   //NOMBRE                   2
        pInsert.bindString(3, params.get(2));                   //TIMESTAMP INICIO SESION  3
        pInsert.bindString(4, params.get(3));                   //FECHA ENVIO              4
        pInsert.bindLong(5, Integer.parseInt(params.get(4)));   //ESTATUS                  5

        pInsert.execute();

        db.setTransactionSuccessful();
        db.endTransaction();
    }

    public void saveTicketCC(SQLiteDatabase db, String table_name, HashMap<Integer, String> params) {
        db.beginTransaction();
        String sql = "INSERT INTO " + table_name + "(" +
                "asesor_id, " +
                "tipo_impresion, " +
                "nombre_cliente, " +
                "folio, " +
                "fecha_impresion, " +
                "fecha_envio, " +
                "estatus) VALUES(?, ?, ?, ?, ?, ?, ?)";
        SQLiteStatement pInsert = db.compileStatement(sql);
        pInsert.bindString(1, params.get(0));                   //SERIE ID        1
        pInsert.bindString(2, params.get(1));                   //TIPO_IMPRESION  2
        pInsert.bindString(3, params.get(2));                   //NOMBRE CLIENTE  3
        pInsert.bindLong(4, Long.parseLong(params.get(3)));     //FOLIO           4
        pInsert.bindString(5, params.get(4));                   //FECHA IMPRESION 5
        pInsert.bindString(6, params.get(5));                   //FECHA ENVIO     6
        pInsert.bindLong(7, Integer.parseInt(params.get(6)));   //ESTATUS         7

        pInsert.execute();

        db.setTransactionSuccessful();
        db.endTransaction();
    }

    public void saveSincronizado(SQLiteDatabase db, String table_name, HashMap<Integer, String> params) {
        db.beginTransaction();
        String sql = "INSERT INTO " + table_name + "(" +
                SidertTables.SidertEntry.SERIE_ID + ", " +
                SidertTables.SidertEntry.TIMESTAMP + ") VALUES(?, ?)";
        SQLiteStatement pInsert = db.compileStatement(sql);
        pInsert.bindString(1, params.get(0));                   //SERIE ID                 1
        pInsert.bindString(2, params.get(1));                   //TIMESTAMP                2

        pInsert.execute();

        db.setTransactionSuccessful();
        db.endTransaction();
    }

    public Long saveSolicitudes(SQLiteDatabase db, String table_name, HashMap<Integer, String> params) {
        db.beginTransaction();
        String sql = "INSERT INTO " + table_name + " (" +
                "serie_id, " +
                "tipo_solicitud, " +
                "estatus, " +
                "id_originacion, " +
                "nombre, " +
                "fecha_inicio, " +
                "fecha_termino, " +
                "fecha_envio, " +
                "fecha_dispositivo, " +
                "fecha_guardado) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        SQLiteStatement pInsert = db.compileStatement(sql);
        pInsert.bindString(1, params.get(0));                       //SERIE_ID
        pInsert.bindLong(2, Long.parseLong(params.get(1)));         //TIPO SOLICITUD
        pInsert.bindLong(3, Long.parseLong(params.get(2)));         //ESTATUS
        pInsert.bindLong(4, Long.parseLong(params.get(3)));         //ID ORIGINACION
        pInsert.bindString(5, params.get(4));                       //NOMBRE
        pInsert.bindString(6, params.get(5));                       //FECHA INICIO
        pInsert.bindString(7, params.get(6));                       //FECHA TERMINO
        pInsert.bindString(8, params.get(7));                       //FECHA ENVIO
        pInsert.bindString(9, params.get(8));                       //FECHA DISPOSITIVO
        pInsert.bindString(10, params.get(9));                      //FECHA CREADO
        Long id = pInsert.executeInsert();

        db.setTransactionSuccessful();
        db.endTransaction();
        return id;
    }

    public void saveDatosCredito(SQLiteDatabase db, String table_name, HashMap<Integer, String> params){
        db.beginTransaction();
        String sql = "INSERT INTO " + table_name + " (" +
                "id_solicitud, " +
                "plazo, " +
                "periodicidad, " +
                "fecha_desembolso, " +
                "dia_desembolso, " +
                "hora_visita, " +
                "monto_prestamo, " +
                "destino, " +
                "estatus_completado) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
        SQLiteStatement pInsert = db.compileStatement(sql);
        pInsert.bindLong(1, Long.parseLong(params.get(0)));
        pInsert.bindString(2, params.get(1));
        pInsert.bindString(3, params.get(2));
        pInsert.bindString(4, params.get(3));
        pInsert.bindString(5, params.get(4));
        pInsert.bindString(6, params.get(5));
        pInsert.bindString(7, params.get(6));
        pInsert.bindString(8, params.get(7));
        pInsert.bindLong(9, Long.parseLong(params.get(8)));
        pInsert.executeInsert();

        db.setTransactionSuccessful();
        db.endTransaction();
    }

    public Long saveDatosPersonales (SQLiteDatabase db, String table_name, HashMap<Integer, String> params){
        db.beginTransaction();
        String sql = "INSERT INTO " + table_name + " (" +
                "id_solicitud, " + "nombre, " + "paterno, " + "materno, " +
                "fecha_nacimiento, " + "edad, " + "genero, " +"estado_nacimiento, " +
                "rfc, " + "homoclave, " + "curp, " + "curp_digito_veri, " +
                "ocupacion, " + "actividad_economica, " + "tipo_identificacion, " +
                "no_identificacion, " + "nivel_estudio, " + "estado_civil, " + "bienes, " +
                "tipo_vivienda, " + "parentesco, " + "otro_tipo_vivienda, " + "latitud, " +
                "longitud, " + "calle, " + "no_exterior, " + "no_interior, " + "manzana, " +
                "lote, " + "cp, " + "colonia, " + "tel_casa, " + "tel_celular, " + "tel_mensajes, " +
                "tel_trabajo, " +  "tiempo_vivir_sitio, " +  "dependientes, " +
                "medio_contacto, " +  "email, " +  "foto_fachada, " +  "ref_domiciliaria, " +
                "firma, " +  "estatus_rechazo, " +  "comentario_rechazo, " + "estatus_completado) " +
                "VALUES (?,?,?,?,?,?,?,?,?,?, ?,?,?,?,?,?,?,?,?,?, ?,?,?,?,?,?,?,?,?,?, ?,?,?,?,?,?,?,?,?,?, ?,?,?,?,?)";
        SQLiteStatement pInsert = db.compileStatement(sql);
        pInsert.bindLong(1, Long.parseLong(params.get(0)));
        pInsert.bindString(2, params.get(1));
        pInsert.bindString(3, params.get(2));
        pInsert.bindString(4, params.get(3));
        pInsert.bindString(5, params.get(4));
        pInsert.bindString(6, params.get(5));
        pInsert.bindLong(7, Long.parseLong(params.get(6)));
        pInsert.bindString(8, params.get(7));
        pInsert.bindString(9, params.get(8));
        pInsert.bindString(10, params.get(9));
        pInsert.bindString(11, params.get(10));
        pInsert.bindString(12, params.get(11));
        pInsert.bindString(13, params.get(12));
        pInsert.bindString(14, params.get(13));
        pInsert.bindString(15, params.get(14));
        pInsert.bindString(16, params.get(15));
        pInsert.bindString(17, params.get(16));
        pInsert.bindString(18, params.get(17));
        pInsert.bindLong(19, Long.parseLong(params.get(18)));
        pInsert.bindString(20, params.get(19));
        pInsert.bindString(21, params.get(20));
        pInsert.bindString(22, params.get(21));
        pInsert.bindString(23, params.get(22));
        pInsert.bindString(24, params.get(23));
        pInsert.bindString(25, params.get(24));
        pInsert.bindString(26, params.get(25));
        pInsert.bindString(27, params.get(26));
        pInsert.bindString(28, params.get(27));
        pInsert.bindString(29, params.get(28));
        pInsert.bindString(30, params.get(29));
        pInsert.bindString(31, params.get(30));
        pInsert.bindString(32, params.get(31));
        pInsert.bindString(33, params.get(32));
        pInsert.bindString(34, params.get(33));
        pInsert.bindString(35, params.get(34));
        pInsert.bindLong(36, Long.parseLong(params.get(35)));
        pInsert.bindString(37, params.get(36));
        pInsert.bindString(38, params.get(37));
        pInsert.bindString(39, params.get(38));
        pInsert.bindString(40, params.get(39));
        pInsert.bindString(41, params.get(40));
        pInsert.bindString(42, params.get(41));
        pInsert.bindLong(43, Long.parseLong(params.get(42)));
        pInsert.bindString(44, params.get(43));
        pInsert.bindLong(45, Long.parseLong(params.get(44)));
        Long id = pInsert.executeInsert();

        db.setTransactionSuccessful();
        db.endTransaction();

        return id;
    }

    public void saveDatosConyuge(SQLiteDatabase db, String table_name, HashMap<Integer, String> params){
        db.beginTransaction();
        String sql = "INSERT INTO " + table_name + " (" +
                "id_solicitud, " +
                "nombre, " +
                "paterno, " +
                "materno, " +
                "ocupacion, " +
                "tel_celular, " +
                "estatus_completado) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?)";
        SQLiteStatement pInsert = db.compileStatement(sql);
        pInsert.bindLong(1, Long.parseLong(params.get(0)));
        pInsert.bindString(2, params.get(1));
        pInsert.bindString(3, params.get(2));
        pInsert.bindString(4, params.get(3));
        pInsert.bindString(5, params.get(4));
        pInsert.bindString(6, params.get(5));
        pInsert.bindLong(7, Long.parseLong(params.get(6)));
        pInsert.executeInsert();

        db.setTransactionSuccessful();
        db.endTransaction();
    }

    public void saveDatosEconomicos(SQLiteDatabase db, String table_name, HashMap<Integer, String> params){
        db.beginTransaction();
        String sql = "INSERT INTO " + table_name + " (" +
                "id_solicitud, " +
                "propiedades, " +
                "valor_aproximado, " +
                "ubicacion, " +
                "ingreso, " +
                "estatus_completado) " +
                "VALUES (?, ?, ?, ?, ?, ?)";
        SQLiteStatement pInsert = db.compileStatement(sql);
        pInsert.bindLong(1, Long.parseLong(params.get(0)));
        pInsert.bindString(2, params.get(1));
        pInsert.bindString(3, params.get(2));
        pInsert.bindString(4, params.get(3));
        pInsert.bindString(5, params.get(4));
        pInsert.bindLong(6, Long.parseLong(params.get(5)));
        pInsert.executeInsert();

        db.setTransactionSuccessful();
        db.endTransaction();
    }

    public void saveDatosNegocio (SQLiteDatabase db, String table_name, HashMap<Integer, String> params){
        db.beginTransaction();
        String sql = "INSERT INTO " + table_name + " (" +
                "id_solicitud, " + "nombre, " + "latitud, " + "longitud, " +
                "calle, " + "no_exterior, " + "no_interior, " + "manzana, " +
                "lote, " + "cp, " + "colonia, " + "actividad_economica, " +
                "actividad_economica, " + "ing_mensual, " + "ing_otros, " +
                "gasto_semanal, " + "gasto_agua, " + "gasto_luz, " + "gasto_telefono, " +
                "gasto_renta, " + "gasto_otros, " + "capacidad_pago, " + "dias_venta, " +
                "foto_fachada, " + "ref_domiciliaria, " + "estatus_completado) " +
                "VALUES (?,?,?,?,?,?,?,?,?,?, ?,?,?,?,?,?,?,?,?,?, ?,?,?,?,?,?)";
        SQLiteStatement pInsert = db.compileStatement(sql);
        pInsert.bindLong(1, Long.parseLong(params.get(0)));
        pInsert.bindString(2, params.get(1));
        pInsert.bindString(3, params.get(2));
        pInsert.bindString(4, params.get(3));
        pInsert.bindString(5, params.get(4));
        pInsert.bindString(6, params.get(5));
        pInsert.bindString(7, params.get(6));
        pInsert.bindString(8, params.get(7));
        pInsert.bindString(9, params.get(8));
        pInsert.bindString(10, params.get(9));
        pInsert.bindString(11, params.get(10));
        pInsert.bindString(12, params.get(11));
        pInsert.bindLong(13, Long.parseLong(params.get(12)));
        pInsert.bindString(14, params.get(13));
        pInsert.bindString(15, params.get(14));
        pInsert.bindString(16, params.get(15));
        pInsert.bindString(17, params.get(16));
        pInsert.bindString(18, params.get(17));
        pInsert.bindString(19, params.get(18));
        pInsert.bindString(20, params.get(19));
        pInsert.bindString(21, params.get(20));
        pInsert.bindString(22, params.get(21));
        pInsert.bindString(23, params.get(22));
        pInsert.bindString(24, params.get(23));
        pInsert.bindString(25, params.get(24));
        pInsert.bindLong(26, Long.parseLong(params.get(25)));
        pInsert.executeInsert();

        db.setTransactionSuccessful();
        db.endTransaction();
    }

    public void saveDatosAval (SQLiteDatabase db, String table_name, HashMap<Integer, String> params){
        db.beginTransaction();
        String sql = "INSERT INTO " + table_name + " (" +
                "id_solicitud, " + "nombre, " + "paterno, " + "materno, " +
                "fecha_nacimiento, " + "edad, " + "genero, " + "estado_nacimiento, " + "rfc, " +
                "homoclave, " + "curp, " + "curp_digito_veri, " + "tipo_identificacion, " +
                "no_identificacion, " +  "ocupacion, " + "actividad_economica, " +
                "latitud, " + "longitud, " + "calle, " + "no_exterior, " + "no_interior, " +
                "manzana, " + "lote, " + "cp, " + "colonia, " + "tipo_vivienda, " + "nombre_titular, " +
                "parentesco, " + "ing_mensual, " + "ing_otros, " + "gasto_semanal, " + "gasto_agua, " +
                "gasto_luz, " + "gasto_telefono, " + "gasto_renta, " + "gasto_otros, " +
                "horario_localizacion, " + "antiguedad, " + "tel_casa, " + "tel_celular, " +
                "foto_fachada, " + "ref_domiciliaria, " + "firma, " + "estatus_rechazo, " +
                "comentario_rechazo, "  + "estatus_completado) " +
                "VALUES (?,?,?,?,?,?,?,?,?,?, ?,?,?,?,?,?,?,?,?,?, ?,?,?,?,?,?,?,?,?,?, ?,?,?,?,?,?,?,?,?,?, ?,?,?,?,?,?)";
        SQLiteStatement pInsert = db.compileStatement(sql);
        pInsert.bindLong(1, Long.parseLong(params.get(0)));
        pInsert.bindString(2, params.get(1));
        pInsert.bindString(3, params.get(2));
        pInsert.bindString(4, params.get(3));
        pInsert.bindString(5, params.get(4));
        pInsert.bindString(6, params.get(5));
        pInsert.bindLong(7, Long.parseLong(params.get(6)));
        pInsert.bindString(8, params.get(7));
        pInsert.bindString(9, params.get(8));
        pInsert.bindString(10, params.get(9));
        pInsert.bindString(11, params.get(10));
        pInsert.bindString(12, params.get(11));
        pInsert.bindString(13, params.get(12));
        pInsert.bindString(14, params.get(13));
        pInsert.bindString(15, params.get(14));
        pInsert.bindString(16, params.get(15));
        pInsert.bindString(17, params.get(16));
        pInsert.bindString(18, params.get(17));
        pInsert.bindString(19, params.get(18));
        pInsert.bindString(20, params.get(19));
        pInsert.bindString(21, params.get(20));
        pInsert.bindString(22, params.get(21));
        pInsert.bindString(23, params.get(22));
        pInsert.bindString(24, params.get(23));
        pInsert.bindString(25, params.get(24));
        pInsert.bindString(26, params.get(25));
        pInsert.bindString(27, params.get(26));
        pInsert.bindString(28, params.get(27));
        pInsert.bindString(29, params.get(28));
        pInsert.bindString(30, params.get(29));
        pInsert.bindString(31, params.get(30));
        pInsert.bindString(32, params.get(31));
        pInsert.bindString(33, params.get(32));
        pInsert.bindString(34, params.get(33));
        pInsert.bindString(35, params.get(34));
        pInsert.bindString(36, params.get(35));
        pInsert.bindString(37, params.get(36));
        pInsert.bindLong(38, Long.parseLong(params.get(37)));
        pInsert.bindString(39, params.get(38));
        pInsert.bindString(40, params.get(39));
        pInsert.bindString(41, params.get(40));
        pInsert.bindString(42, params.get(41));
        pInsert.bindString(43, params.get(42));
        pInsert.bindLong(44, Long.parseLong(params.get(43)));
        pInsert.bindString(45, params.get(44));
        pInsert.bindLong(46, Long.parseLong(params.get(45)));
        pInsert.executeInsert();

        db.setTransactionSuccessful();
        db.endTransaction();
    }

    public void saveReferencia (SQLiteDatabase db, String table_name, HashMap<Integer, String> params){
        db.beginTransaction();
        String sql = "INSERT INTO " + table_name + " (" +
                "id_solicitud, " +
                "nombre, " +
                "paterno, " +
                "materno, " +
                "calle, " +
                "cp, " +
                "colonia, " +
                "municipio, " +
                "tel_celular, " +
                "estatus_completado) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        SQLiteStatement pInsert = db.compileStatement(sql);
        pInsert.bindLong(1, Long.parseLong(params.get(0)));
        pInsert.bindString(2, params.get(1));
        pInsert.bindString(3, params.get(2));
        pInsert.bindString(4, params.get(3));
        pInsert.bindString(5, params.get(4));
        pInsert.bindString(6, params.get(5));
        pInsert.bindString(7, params.get(6));
        pInsert.bindString(8, params.get(7));
        pInsert.bindString(9, params.get(8));
        pInsert.bindLong(10, Long.parseLong(params.get(9)));
        pInsert.executeInsert();

        db.setTransactionSuccessful();
        db.endTransaction();
    }

    public Long saveDatosCreditoGpo(SQLiteDatabase db, String table_name, HashMap<Integer, String> params){
        db.beginTransaction();
        String sql = "INSERT INTO " + table_name + " (" +
                "id_solicitud, " +
                "nombre_grupo, " +
                "plazo, " +
                "periodicidad, " +
                "fecha_desembolso, " +
                "dia_desembolso, " +
                "hora_visita, " +
                "estatus_completado) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        SQLiteStatement pInsert = db.compileStatement(sql);
        pInsert.bindLong(1, Long.parseLong(params.get(0)));
        pInsert.bindString(2, params.get(1));
        pInsert.bindString(3, params.get(2));
        pInsert.bindString(4, params.get(3));
        pInsert.bindString(5, params.get(4));
        pInsert.bindString(6, params.get(5));
        pInsert.bindString(7, params.get(6));
        pInsert.bindLong(8, Long.parseLong(params.get(7)));
        Long id = pInsert.executeInsert();

        db.setTransactionSuccessful();
        db.endTransaction();
        return id;
    }

    public Long saveIntegrantesGpo (SQLiteDatabase db, String table_name, HashMap<Integer, String> params){
        db.beginTransaction();
        String sql = "INSERT INTO " + table_name + " (" +
                "id_credito, " +  "cargo, " +  "nombre, " +  "paterno, " +  "materno, " +
                "fecha_nacimiento, " + "edad, " + "genero, " + "estado_nacimiento, " +
                "rfc, " + "curp, " + "curp_digito_veri, " + "tipo_identificacion, " +
                "no_identificacion, " + "nivel_estudio, " + "estado_civil, " + "bienes, " +
                "estatus_rechazo, " + "comentario_rechazo, " + "estatus_completado) " +
                "VALUES (?,?,?,?,?,?,?,?,?,?, ?,?,?,?,?,?,?,?,?,?)";

        SQLiteStatement pInsert = db.compileStatement(sql);
        pInsert.bindLong(1, Long.parseLong(params.get(0)));
        pInsert.bindLong(2, Long.parseLong(params.get(1)));
        pInsert.bindString(3, params.get(2));
        pInsert.bindString(4, params.get(3));
        pInsert.bindString(5, params.get(4));
        pInsert.bindString(6, params.get(5));
        pInsert.bindString(7, params.get(6));
        pInsert.bindLong(8, Long.parseLong(params.get(7)));
        pInsert.bindString(9, params.get(8));
        pInsert.bindString(10, params.get(9));
        pInsert.bindString(11, params.get(10));
        pInsert.bindString(12, params.get(11));
        pInsert.bindString(13, params.get(12));
        pInsert.bindString(14, params.get(13));
        pInsert.bindString(15, params.get(14));
        pInsert.bindString(16, params.get(15));
        pInsert.bindLong(17, Long.parseLong(params.get(16)));
        pInsert.bindLong(18, Long.parseLong(params.get(17)));
        pInsert.bindString(19, params.get(18));
        pInsert.bindLong(20, Long.parseLong(params.get(19)));

        long id = pInsert.executeInsert();

        db.setTransactionSuccessful();
        db.endTransaction();
        return id;
    }

    public void saveDatosTelefonicos (SQLiteDatabase db, String table_name, HashMap<Integer, String> params){
        db.beginTransaction();
        String sql = "INSERT INTO " + table_name + " (" +
                "id_integrante, " + "tel_casa, " + "tel_celular, " +
                "tel_mensaje, " + "estatus_completado) " +
                "VALUES (?,?,?,?,?)";

        SQLiteStatement pInsert = db.compileStatement(sql);
        pInsert.bindLong(1, Long.parseLong(params.get(0)));
        pInsert.bindString(2, params.get(1));
        pInsert.bindString(3, params.get(2));
        pInsert.bindString(4, params.get(3));
        pInsert.bindLong(5, Long.parseLong(params.get(4)));

        pInsert.executeInsert();

        db.setTransactionSuccessful();
        db.endTransaction();
    }

    public void saveDatosDomicilio (SQLiteDatabase db, String table_name, HashMap<Integer, String> params){
        db.beginTransaction();
        String sql = "INSERT INTO " + table_name + " (" +
                "id_integrante, " + "latitud, " + "longitud, " + "calle, " + "no_exterior, " +
                "no_interior, " + "manzana, " + "lote, " + "cp, " + "colonia, " + "tipo_vivienda, " +
                "parentesco, " + "otro_tipo_vivienda, " + "tiempo_vivir_sitio, " + "foto_fachada, " + "ref_domiciliaria, " +
                "estatus_completado) " +
                "VALUES (?,?,?,?,?,?,?,?,?,?, ?,?,?,?,?,?,?)";

        SQLiteStatement pInsert = db.compileStatement(sql);
        pInsert.bindLong(1, Long.parseLong(params.get(0)));
        pInsert.bindString(2, params.get(1));
        pInsert.bindString(3, params.get(2));
        pInsert.bindString(4, params.get(3));
        pInsert.bindString(5, params.get(4));
        pInsert.bindString(6, params.get(5));
        pInsert.bindString(7, params.get(6));
        pInsert.bindString(8, params.get(7));
        pInsert.bindString(9, params.get(8));
        pInsert.bindString(10, params.get(9));
        pInsert.bindString(11, params.get(10));
        pInsert.bindString(12, params.get(11));
        pInsert.bindString(13, params.get(12));
        pInsert.bindString(14, params.get(13));
        pInsert.bindString(15, params.get(14));
        pInsert.bindString(16, params.get(15));
        pInsert.bindLong(17, Long.parseLong(params.get(16)));

        pInsert.executeInsert();

        db.setTransactionSuccessful();
        db.endTransaction();
    }

    public void saveDatosNegocioGpo (SQLiteDatabase db, String table_name, HashMap<Integer, String> params){
        db.beginTransaction();
        String sql = "INSERT INTO " + table_name + " (" +
                "id_integrante, " + "nombre, " + "latitud, " + "longitud, " + "calle, " + "no_exterior, " +
                "no_interior, " + "manzana, " + "lote, " + "cp, " + "colonia, " +
                "actividad_economica, " + "antiguedad, " + "ing_mensual, " + "ing_otros, " +
                "gasto_semanal, " + "capacidad_pago, " + "foto_fachada, " + "ref_domiciliaria, " +
                "estatus_rechazo, " + "comentario_rechazo, " + "estatus_completado) " +
                "VALUES (?,?,?,?,?,?,?,?,?,?, ?,?,?,?,?,?,?,?,?,?, ?,?)";

        SQLiteStatement pInsert = db.compileStatement(sql);
        pInsert.bindLong(1, Long.parseLong(params.get(0)));
        pInsert.bindString(2, params.get(1));
        pInsert.bindString(3, params.get(2));
        pInsert.bindString(4, params.get(3));
        pInsert.bindString(5, params.get(4));
        pInsert.bindString(6, params.get(5));
        pInsert.bindString(7, params.get(6));
        pInsert.bindString(8, params.get(7));
        pInsert.bindString(9, params.get(8));
        pInsert.bindString(10, params.get(9));
        pInsert.bindString(11, params.get(10));
        pInsert.bindString(12, params.get(11));
        pInsert.bindString(13, params.get(12));
        pInsert.bindString(14, params.get(13));
        pInsert.bindString(15, params.get(14));
        pInsert.bindString(16, params.get(15));
        pInsert.bindString(17, params.get(16));
        pInsert.bindString(18, params.get(17));
        pInsert.bindString(19, params.get(18));
        pInsert.bindLong(20, Long.parseLong(params.get(19)));
        pInsert.bindString(21, params.get(20));
        pInsert.bindLong(22, Long.parseLong(params.get(21)));

        pInsert.executeInsert();

        db.setTransactionSuccessful();
        db.endTransaction();
    }

    public void saveDatosConyugeGpo (SQLiteDatabase db, String table_name, HashMap<Integer, String> params){
        db.beginTransaction();
        String sql = "INSERT INTO " + table_name + " (" +
                "id_integrante, " + "nombre, " + "paterno, " + "materno, " + "ocupacion, " +
                "tel_celular, " + "ingresos, " + "estatus_completado) " +
                "VALUES (?,?,?,?,?,?,?,?)";

        SQLiteStatement pInsert = db.compileStatement(sql);
        pInsert.bindLong(1, Long.parseLong(params.get(0)));
        pInsert.bindString(2, params.get(1));
        pInsert.bindString(3, params.get(2));
        pInsert.bindString(4, params.get(3));
        pInsert.bindString(5, params.get(4));
        pInsert.bindString(6, params.get(5));
        pInsert.bindString(7, params.get(6));
        pInsert.bindLong(8, Long.parseLong(params.get(7)));

        pInsert.executeInsert();

        db.setTransactionSuccessful();
        db.endTransaction();
    }

    public void saveDatosOtrosGpo (SQLiteDatabase db, String table_name, HashMap<Integer, String> params){
        db.beginTransaction();
        String sql = "INSERT INTO " + table_name + " (" +
                "id_integrante, " + "medio_contacto, " + "email, " + "estatus_integrante, " +
                "monto_solicitado, " + "casa_reunion, " + "firma, " + "estatus_completado) " +
                "VALUES (?,?,?,?,?,?,?,?)";

        SQLiteStatement pInsert = db.compileStatement(sql);
        pInsert.bindLong(1, Long.parseLong(params.get(0)));
        pInsert.bindString(2, params.get(1));
        pInsert.bindString(3, params.get(2));
        pInsert.bindLong(4, Long.parseLong(params.get(3)));
        pInsert.bindString(5, params.get(4));
        pInsert.bindLong(6, Long.parseLong(params.get(5)));
        pInsert.bindString(7, params.get(6));
        pInsert.bindLong(8, Long.parseLong(params.get(7)));

        pInsert.executeInsert();

        db.setTransactionSuccessful();
        db.endTransaction();
    }

    public void saveDocumentosClientes (SQLiteDatabase db, String table_name, HashMap<Integer, String> params){
        db.beginTransaction();
        String sql = "INSERT INTO " + table_name + " (" +
                "id_solicitud, " + "ine_frontal, " + "ine_reverso, " + "curp, " +
                "comprobante, " + "codigo_barras, " + "firma_asesor, " + "estatus_completado) " +
                "VALUES (?,?,?,?,?,?,?,?)";

        SQLiteStatement pInsert = db.compileStatement(sql);
        pInsert.bindLong(1, Long.parseLong(params.get(0)));
        pInsert.bindString(2, params.get(1));
        pInsert.bindString(3, params.get(2));
        pInsert.bindString(4, params.get(3));
        pInsert.bindString(5, params.get(4));
        pInsert.bindString(6, params.get(5));
        pInsert.bindString(7, params.get(6));
        pInsert.bindLong(8, Long.parseLong(params.get(7)));

        pInsert.executeInsert();

        db.setTransactionSuccessful();
        db.endTransaction();
    }

    public void saveDocumentosIntegrante (SQLiteDatabase db, String table_name, HashMap<Integer, String> params){
        db.beginTransaction();
        String sql = "INSERT INTO " + table_name + " (" +
                "id_integrante, " + "ine_frontal, " + "ine_reverso, " +
                "curp, " + "comprobante, " + "estatus_completado) " +
                "VALUES (?,?,?,?,?,?)";

        SQLiteStatement pInsert = db.compileStatement(sql);
        pInsert.bindLong(1, Long.parseLong(params.get(0)));
        pInsert.bindString(2, params.get(1));
        pInsert.bindString(3, params.get(2));
        pInsert.bindString(4, params.get(3));
        pInsert.bindString(5, params.get(4));
        pInsert.bindLong(6, Long.parseLong(params.get(5)));

        pInsert.executeInsert();

        db.setTransactionSuccessful();
        db.endTransaction();
    }

    //---------------------------------------------------------------------------------------------------------------

    public void saveRecordsInd (SQLiteDatabase db, String table_name, HashMap<Integer, String> params){
        db.beginTransaction();
        String sql = "INSERT INTO " +table_name + "(" +
                SidertTables.SidertEntry.ASESOR_ID + ", " +
                SidertTables.SidertEntry.EXTERNAL_ID + ", " +
                SidertTables.SidertEntry.FORM + ", " +
                SidertTables.SidertEntry.FECHA_ASIGNACION + ", " +
                SidertTables.SidertEntry.NOMBRE_CLIENTE + ", " +
                SidertTables.SidertEntry.CLIENTE_OBJ + ", " +
                SidertTables.SidertEntry.AVAL_OBJ + ", " +
                SidertTables.SidertEntry.PRESTAMO_OBJ + ", " +
                SidertTables.SidertEntry.REPORTE_OMEGA_OBJ + ", " +
                SidertTables.SidertEntry.TABLA_PAGOS_OBJ + ", " +
                SidertTables.SidertEntry.DIA_SEMANA + ", " +
                SidertTables.SidertEntry.FECHA_PAGO + ", " +
                SidertTables.SidertEntry.IMPRESION + ", " +
                SidertTables.SidertEntry.FECHA_TER + ", " +
                SidertTables.SidertEntry.FECHA_ENV + ", " +
                SidertTables.SidertEntry.FECHA_CON + ", " +
                SidertTables.SidertEntry.STATUS + ", " +
                SidertTables.SidertEntry.TIMESTAMP + ") VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        SQLiteStatement pInsert = db.compileStatement(sql);
        pInsert.bindString(1, params.get(0));                   //ASESOR ID
        pInsert.bindString(2, params.get(1));                   //EXTERNAL ID
        pInsert.bindString(3, params.get(2));                   //NOMBRE FORMULARIO
        pInsert.bindString(4, params.get(3));                   //FECHA ASIGNACION
        pInsert.bindString(5, params.get(4));                   //NOMBRE CLIENTE
        pInsert.bindString(6, params.get(5));                   //CLIENTE OBJ
        pInsert.bindString(7, params.get(6));                   //AVAL OBJ
        pInsert.bindString(8, params.get(7));                   //PRESTAMO OBJ
        pInsert.bindString(9, params.get(8));                   //REPORTE OMEGA OBJ
        pInsert.bindString(10, params.get(9));                   //TABLA PAGOS OBJ
        pInsert.bindString(11, params.get(10));                  //DIA SEMANA
        pInsert.bindLong(12, Integer.parseInt(params.get(11))); //IMPRESION
        pInsert.bindString(13, params.get(12));                 //FECHA TERMINADO
        pInsert.bindString(14, params.get(13));                 //FECHA ENVIADO
        pInsert.bindString(15, params.get(14));                 //FECHA CON
        pInsert.bindLong(16, Integer.parseInt(params.get(15))); //STATUS
        pInsert.bindString(17, params.get(16));                 //TIMESTAMP
        pInsert.execute();

        db.setTransactionSuccessful();
        db.endTransaction();
    }

    public void saveRecordsGpo (SQLiteDatabase db, String table_name, HashMap<Integer, String> params){
        db.beginTransaction();
        String sql = "INSERT INTO " +table_name + "(" +
                SidertTables.SidertEntry.ASESOR_ID + ", " +
                SidertTables.SidertEntry.EXTERNAL_ID + ", " +
                SidertTables.SidertEntry.FORM + ", " +
                SidertTables.SidertEntry.FECHA_ASIGNACION + ", " +
                SidertTables.SidertEntry.NOMBRE_GPO + ", " +
                SidertTables.SidertEntry.CLAVE + ", " +
                SidertTables.SidertEntry.INTEGRANTES_OBJ + ", " +
                SidertTables.SidertEntry.TOTAL_INTEGRANTES + ", " +
                SidertTables.SidertEntry.PRESIDENTA_OBJ + ", " +
                SidertTables.SidertEntry.SECRETARIA_OBJ + ", " +
                SidertTables.SidertEntry.PRESTAMO_OBJ + ", " +
                SidertTables.SidertEntry.REPORTE_OMEGA_OBJ + ", " +
                SidertTables.SidertEntry.TABLA_PAGOS_OBJ + ", " +
                SidertTables.SidertEntry.DIA_SEMANA + ", " +
                SidertTables.SidertEntry.FECHA_PAGO + ", " +
                SidertTables.SidertEntry.STATUS + ", " +
                SidertTables.SidertEntry.FECHA_TER + ", " +
                SidertTables.SidertEntry.FECHA_ENV + ", " +
                SidertTables.SidertEntry.FECHA_CON + ", " +
                SidertTables.SidertEntry.TIMESTAMP + ") VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        SQLiteStatement pInsert = db.compileStatement(sql);
        pInsert.bindString(1, params.get(0));                   //ASESOR ID
        pInsert.bindString(2, params.get(1));                   //EXTERNAL ID
        pInsert.bindString(3, params.get(2));                   //NOMBRE FORMULARIO
        pInsert.bindString(4, params.get(3));                   //FECHA ASIGNACION
        pInsert.bindString(5, params.get(4));                   //NOMBRE GPO
        pInsert.bindString(6, params.get(5));                   //CLAVE
        pInsert.bindString(7, params.get(6));                   //INTEGRANTES OBJ
        pInsert.bindLong(8, Integer.parseInt(params.get(7)));   //TOTAL INTEGRANTES
        pInsert.bindString(9, params.get(8));                   //PRESIDENTA OBJ
        pInsert.bindString(10, params.get(9));                   //SECRETARIO OBJ
        pInsert.bindString(11, params.get(10));                  //PRESTAMO_OBJ
        pInsert.bindString(12, params.get(11));                 //REPORTE OMAEGAJ OBJ
        pInsert.bindString(13, params.get(12));                 //TABLA DE PAGOS
        pInsert.bindString(14, params.get(13));                 //DIA SEMAMA
        pInsert.bindString(15, params.get(14));                 //FECHA PAGO
        pInsert.bindLong(16, Integer.parseInt(params.get(15))); //ESTATUS
        pInsert.bindString(17, params.get(16));                 //FECHA_TER;
        pInsert.bindString(18, params.get(17));                 //FECHA_ENV
        pInsert.bindString(19, params.get(18));                 //FECHA_CON
        pInsert.bindString(20, params.get(19));                 //TIMESTAMP
        pInsert.execute();

        db.setTransactionSuccessful();
        db.endTransaction();
    }

    public void saveRecordsImpressionsLog(SQLiteDatabase db, String table_name, HashMap<Integer, String> params){
        db.beginTransaction();
        String sql = "INSERT INTO " +table_name + "(" +
                SidertTables.SidertEntry.FOLIO + ", " +
                SidertTables.SidertEntry.ASSESOR_ID + ", " +
                SidertTables.SidertEntry.EXTERNAL_ID + ", " +
                SidertTables.SidertEntry.AMOUNT + ", " +
                SidertTables.SidertEntry.TYPE_IMPRESSION + ", " +
                SidertTables.SidertEntry.ERRORS + ", " +
                SidertTables.SidertEntry.GENERATED_AT + ", " +
                SidertTables.SidertEntry.SENT_AT + ", " +
                SidertTables.SidertEntry.STATUS + ") VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?)";
        SQLiteStatement pInsert = db.compileStatement(sql);
        pInsert.bindLong(1, Integer.parseInt(params.get(0)));   //FOLIO
        pInsert.bindString(2, params.get(1));                   //ID ASESOR
        pInsert.bindString(3, params.get(2));                   //ID ORDEN
        pInsert.bindString(4, params.get(3));                   //MONTO
        pInsert.bindString(5, params.get(4));                   //TIPO IMPRESION
        pInsert.bindString(6, params.get(5));                   //ERRORES
        pInsert.bindString(7, params.get(6));                   //FECHA IMPRESO
        pInsert.bindString(8, params.get(7));                   //FECHA ENVIADO
        pInsert.bindLong(9, Integer.parseInt(params.get(8)));   //ESTADO 0=Enviado, 1=NoEnviado
        pInsert.execute();

        db.setTransactionSuccessful();
        db.endTransaction();
    }

    public Cursor getRecords(String table, String where, String order, String[] args){
        SQLiteDatabase db = this.getReadableDatabase();
        //Log.v("SQL", "SELECT * FROM " + table + where + order);
        Cursor res =  db.rawQuery( "SELECT * FROM " + table + where + order, args );
        return res;
    }

    public Cursor getDireccionByCP (String cp){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res =  db.rawQuery( "SELECT e.estado_id ,e.estado_nombre, e.pais_id, m.municipio_id, m.municipio_nombre, m.estado_id, c.colonia_id, c.colonia_nombre, c.cp, c.municipio_id FROM cat_colonias AS c INNER JOIN municipios AS m ON m.municipio_id = c.municipio_id INNER JOIN estados AS e ON e.estado_id = m.estado_id WHERE c.cp = "+cp, null);
        return res;
    }

    public Cursor getOriginacionInd (String id_solicitud, boolean completado){
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "";
        if (ENVIROMENT){
            query = "SELECT cre.*, cli.*, cony.*, eco.*, neg.*, aval.*, refe.*, doc.*, soli.estatus FROM solicitudes_t AS soli " +
                    "INNER JOIN " + DATOS_CREDITO_IND + " AS cre ON soli.id_solicitud = cre.id_solicitud " +
                    "INNER JOIN " + DATOS_CLIENTE_IND + " AS cli ON soli.id_solicitud = cli.id_solicitud " +
                    "INNER JOIN " + DATOS_CONYUGE_IND + " AS cony ON soli.id_solicitud = cony.id_solicitud " +
                    "INNER JOIN " + DATOS_ECONOMICOS_IND + " AS eco ON soli.id_solicitud = eco.id_solicitud " +
                    "INNER JOIN " + DATOS_NEGOCIO_IND + " AS neg ON soli.id_solicitud = neg.id_solicitud " +
                    "INNER JOIN " + DATOS_AVAL_IND + " AS aval ON soli.id_solicitud = aval.id_solicitud " +
                    "INNER JOIN " + DATOS_REFERENCIA_IND + " AS refe ON soli.id_solicitud = refe.id_solicitud " +
                    "INNER JOIN " + DOCUMENTOS + " AS doc ON soli.id_solicitud = doc.id_solicitud " +
                    "WHERE soli.id_solicitud = "+id_solicitud + ((completado)?" AND soli.estatus = 0":"");
        }
        else{
            query = "SELECT cre.*, cli.*, cony.*, eco.*, neg.*, aval.*, refe.*, doc.*, soli.estatus FROM solicitudes_t AS soli " +
                    "INNER JOIN " + DATOS_CREDITO_IND_T + " AS cre ON soli.id_solicitud = cre.id_solicitud " +
                    "INNER JOIN " + DATOS_CLIENTE_IND_T + " AS cli ON soli.id_solicitud = cli.id_solicitud " +
                    "INNER JOIN " + DATOS_CONYUGE_IND_T + " AS cony ON soli.id_solicitud = cony.id_solicitud " +
                    "INNER JOIN " + DATOS_ECONOMICOS_IND_T + " AS eco ON soli.id_solicitud = eco.id_solicitud " +
                    "INNER JOIN " + DATOS_NEGOCIO_IND_T + " AS neg ON soli.id_solicitud = neg.id_solicitud " +
                    "INNER JOIN " + DATOS_AVAL_IND_T + " AS aval ON soli.id_solicitud = aval.id_solicitud " +
                    "INNER JOIN " + DATOS_REFERENCIA_IND_T + " AS refe ON soli.id_solicitud = refe.id_solicitud " +
                    "INNER JOIN " + DOCUMENTOS_T + " AS doc ON soli.id_solicitud = doc.id_solicitud " +
                    "WHERE soli.id_solicitud = "+id_solicitud + ((completado)?" AND soli.estatus = 0":"");
        }

        Cursor res =  db.rawQuery( query, null);
        return res;
    }

    public Cursor getOriginacionCreditoGpo (String id_solicitud){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res =  db.rawQuery( "SELECT soli.*, cre.* FROM solicitudes_t AS soli " +
                "INNER JOIN datos_credito_gpo_t AS cre ON soli.id_solicitud = cre.id_solicitud " +
                "WHERE soli.id_solicitud = "+id_solicitud, null);
        return res;
    }

    public Cursor getIntegranteOri (String id_credito, String id_integrante){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res =  db.rawQuery( "SELECT i.*, t.*, d.*, n.*, c.*, o.*, docu.* FROM datos_integrantes_gpo_t AS i " +
                "INNER JOIN telefonos_integrante_t AS t ON t.id_integrante = i.id " +
                "INNER JOIN domicilio_integrante_t AS d ON d.id_integrante = i.id " +
                "INNER JOIN negocio_integrante_t AS n ON n.id_integrante = i.id " +
                "INNER JOIN conyuge_integrante_t AS c ON c.id_integrante = i.id " +
                "INNER JOIN otros_datos_integrante_t AS o ON o.id_integrante = i.id " +
                "INNER JOIN documentos_integrante_t AS docu ON docu.id_integrante = i.id " +
                "WHERE i.id_credito = " + id_credito + " AND i.id = " + id_integrante + " ORDER BY i.nombre ASC", null);
        return res;
    }

    public Cursor getCargoGrupo (String id_credito){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res =  db.rawQuery( "SELECT DISTINCT (cargo) from datos_integrantes_gpo_t WHERE id_credito = " + id_credito, null);

        return res;
    }

    public Cursor getComite (String id_credito){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res =  db.rawQuery( "SELECT DISTINCT (cargo) from datos_integrantes_gpo_t WHERE id_credito = " + id_credito + " AND cargo <> 4", null);

        return res;
    }

    public Cursor customSelect (String table, String select, String where, String order, String[] args){
        SQLiteDatabase db = this.getReadableDatabase();
        //Log.v("SQL", "SELECT * FROM " + table + where + order);
        Cursor res =  db.rawQuery( "SELECT " + select + " FROM " + table + where + order, args );
        return res;
    }

    public int updateRecords(Context ctx, String table, JSONObject params) throws JSONException {
        DBhelper objBD = new DBhelper(ctx);
        SQLiteDatabase db = objBD.getWritableDatabase();
        ContentValues values = new ContentValues();
        String conditionals = "";

        JSONArray jsonParams =(JSONArray) params.get(Constants.PARAMS);
        JSONArray jsonCon =(JSONArray) params.get(Constants.CONDITIONALS);

        for (int i = 0; i < jsonParams.length(); i++){
            JSONObject item = jsonParams.getJSONObject(i);
            Log.v("values", item.getString(Constants.KEY) + ":" + item.getString(Constants.VALUE));
            values.put(item.getString(Constants.KEY),item.getString(Constants.VALUE));
        }

        for (int j = 0; j < jsonCon.length(); j++){
            JSONObject item = jsonCon.getJSONObject(j);
            Log.v("condi", item.getString(Constants.KEY) + ":" + item.getString(Constants.VALUE));
            conditionals += item.getString(Constants.KEY) + "='"+item.getString(Constants.VALUE)+"'";
        }

        return db.update(table, values, conditionals, null);
    }
}
