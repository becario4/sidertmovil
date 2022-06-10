package com.sidert.sidertmovil.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteStatement;
import android.util.Log;

import com.sidert.sidertmovil.Home;
import com.sidert.sidertmovil.utils.Constants;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

import static com.sidert.sidertmovil.utils.Constants.*;

public class DBhelper extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "sidert.db";

    public DBhelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL(SidertTables.SidertEntry.CREATE_TABLE_FICHAS_T);
        db.execSQL(SidertTables.SidertEntry.CREATE_TABLE_LOGIN_REPORT_T);
        db.execSQL(SidertTables.SidertEntry.CREATE_TABLE_GEOLOCALIZACION_T);
        db.execSQL(SidertTables.SidertEntry.CREATE_TABLE_ESTADOS);
        db.execSQL(SidertTables.SidertEntry.CREATE_TABLE_MUNICIPIOS);
        db.execSQL(SidertTables.SidertEntry.CREATE_TABLE_COLONIAS);
        db.execSQL(SidertTables.SidertEntry.CREATE_TABLE_SECTORES);
        //db.execSQL(SidertTables.SidertEntry.CREATE_TABLE_IDENTIFICACIONES);
        db.execSQL(SidertTables.SidertEntry.CREATE_TABLE_OCUPACIONES);
        db.execSQL(SidertTables.SidertEntry.CREATE_TABLE_SINCRONIZADO_T);

        db.execSQL(SidertTables.SidertEntry.CREATE_TBL_INTEGRANTES_GPO_T);
        db.execSQL(SidertTables.SidertEntry.CREATE_TBL_TELEFONOS_INTEGRANTE_T);
        db.execSQL(SidertTables.SidertEntry.CREATE_TBL_DOMICILIO_INTEGRANTE_T);
        db.execSQL(SidertTables.SidertEntry.CREATE_TBL_NEGOCIO_INTEGRANTE_T);
        db.execSQL(SidertTables.SidertEntry.CREATE_TBL_CONYUGE_INTEGRANTE_T);
        db.execSQL(SidertTables.SidertEntry.CREATE_TBL_OTROS_DATOS_INTEGRANTE);
        db.execSQL(SidertTables.SidertEntry.CREATE_TBL_CROQUIS_GPO);
        db.execSQL(SidertTables.SidertEntry.CREATE_TBL_POLITICAS_INTEGRANTE);
        db.execSQL(SidertTables.SidertEntry.CREATE_TBL_DOCUMENTOS_INTEGRANTE_T);
        db.execSQL(SidertTables.SidertEntry.CREATE_TBL_CARTERA_IND_T);
        db.execSQL(SidertTables.SidertEntry.CREATE_TBL_CARTERA_GPO_T);
        db.execSQL(SidertTables.SidertEntry.CREATE_TBL_PRESTAMOS_IND_T);
        db.execSQL(SidertTables.SidertEntry.CREATE_TBL_AMORTIZACIONES_T);
        db.execSQL(SidertTables.SidertEntry.CREATE_TBL_PAGOS_IND_T);
        db.execSQL(SidertTables.SidertEntry.CREATE_TBL_PRESTAMOS_GPO_T);
        db.execSQL(SidertTables.SidertEntry.CREATE_TBL_MIEMBROS_T);
        db.execSQL(SidertTables.SidertEntry.CREATE_TBL_MIEMBROS_PAGOS_T);
        db.execSQL(SidertTables.SidertEntry.CREATE_TBL_PAGOS_T);
        db.execSQL(SidertTables.SidertEntry.CREATE_TBL_AVAL_T);
        db.execSQL(SidertTables.SidertEntry.CREATE_TBL_RESPUESTAS_IND_T);
        db.execSQL(SidertTables.SidertEntry.CREATE_TBL_RESPUESTAS_GPO_T);
        db.execSQL(SidertTables.SidertEntry.CREATE_TBL_IMPRESIONES_VIGENTE_T);
        db.execSQL(SidertTables.SidertEntry.CREATE_TBL_IMPRESIONES_VENCIDA_T);
        db.execSQL(SidertTables.SidertEntry.CREATE_TBL_REIMPRESION_VIGENTE_T);
        db.execSQL(SidertTables.SidertEntry.CREATE_TBL_ARQUEO_CAJA_T);
        db.execSQL(SidertTables.SidertEntry.CREATE_TBL_TRACKER_ASESOR_T);
        db.execSQL(SidertTables.SidertEntry.CREATE_TBL_GEO_RESPUESTAS);
        db.execSQL(SidertTables.SidertEntry.CREATE_TBL_RESPUESTAS_IND_V_T);
        db.execSQL(SidertTables.SidertEntry.CREATE_TBL_RESPUESTAS_INTEGRANTE_T);
        db.execSQL(SidertTables.SidertEntry.CREATE_TBL_CIERRE_DIA);
        db.execSQL(SidertTables.SidertEntry.CREATE_TBL_REPORTE_SESIONES);
        db.execSQL(SidertTables.SidertEntry.CREATE_TBL_SUCURSALES);
        db.execSQL(SidertTables.SidertEntry.CREATE_TBL_CODIGOS_OXXO);
        db.execSQL(SidertTables.SidertEntry.CREATE_TBL_CANCEL_GESTIONES);
        db.execSQL(SidertTables.SidertEntry.CREATE_TBL_RECIBOS);
        db.execSQL(SidertTables.SidertEntry.CREATE_TBL_CATEGORIA_TICKETS);
        db.execSQL(SidertTables.SidertEntry.CREATE_TBL_SOPORTE);
        db.execSQL(SidertTables.SidertEntry.CREATE_TBL_PLAZOS_PRESTAMOS);
        db.execSQL(SidertTables.SidertEntry.CREATE_TBL_RESUMENES_GESTION);

        db.execSQL(SidertTables.SidertEntry.CREATE_TBL_SOLICITUDES);
        db.execSQL(SidertTables.SidertEntry.CREATE_TBL_CREDITO_IND);
        db.execSQL(SidertTables.SidertEntry.CREATE_TBL_CLIENTE_IND);
        db.execSQL(SidertTables.SidertEntry.CREATE_TBL_CONYUGE_IND);
        db.execSQL(SidertTables.SidertEntry.CREATE_TBL_ECONOMICOS_IND);
        db.execSQL(SidertTables.SidertEntry.CREATE_TBL_NEGOCIO_IND);
        db.execSQL(SidertTables.SidertEntry.CREATE_TBL_AVAL_IND);
        db.execSQL(SidertTables.SidertEntry.CREATE_TBL_REFERENCIA_IND);
        db.execSQL(SidertTables.SidertEntry.CREATE_TBL_CROQUIS_IND);
        db.execSQL(SidertTables.SidertEntry.CREATE_TBL_POLITICAS_PLD_IND);
        db.execSQL(SidertTables.SidertEntry.CREATE_TBL_DOCUMENTOS);

        db.execSQL(SidertTables.SidertEntry.CREATE_TBL_SOLICITUDES_REN);
        db.execSQL(SidertTables.SidertEntry.CREATE_TBL_CREDITO_IND_REN);
        db.execSQL(SidertTables.SidertEntry.CREATE_TBL_CLIENTE_IND_REN);
        db.execSQL(SidertTables.SidertEntry.CREATE_TBL_CONYUGE_IND_REN);
        db.execSQL(SidertTables.SidertEntry.CREATE_TBL_ECONOMICOS_IND_REN);
        db.execSQL(SidertTables.SidertEntry.CREATE_TBL_NEGOCIO_IND_REN);
        db.execSQL(SidertTables.SidertEntry.CREATE_TBL_AVAL_IND_REN);
        db.execSQL(SidertTables.SidertEntry.CREATE_TBL_REFERENCIA_IND_REN);
        db.execSQL(SidertTables.SidertEntry.CREATE_TBL_CROQUIS_IND_REN);
        db.execSQL(SidertTables.SidertEntry.CREATE_TBL_POLITICAS_PLD_IND_REN);
        db.execSQL(SidertTables.SidertEntry.CREATE_TABLE_DIRECCIONES_REN);
        db.execSQL(SidertTables.SidertEntry.CREATE_TBL_DOCUMENTOS_REN);

        db.execSQL(SidertTables.SidertEntry.CREATE_TBL_DATOS_CREDITO_GPO_REN);
        db.execSQL(SidertTables.SidertEntry.CREATE_TBL_INTEGRANTES_GPO_REN);
        db.execSQL(SidertTables.SidertEntry.CREATE_TBL_TELEFONOS_INTEGRANTE_REN);
        db.execSQL(SidertTables.SidertEntry.CREATE_TBL_DOMICILIO_INTEGRANTE_REN);
        db.execSQL(SidertTables.SidertEntry.CREATE_TBL_NEGOCIO_INTEGRANTE_REN);
        db.execSQL(SidertTables.SidertEntry.CREATE_TBL_CONYUGE_INTEGRANTE_REN);
        db.execSQL(SidertTables.SidertEntry.CREATE_TBL_OTROS_DATOS_INTEGRANTE_REN);
        db.execSQL(SidertTables.SidertEntry.CREATE_TBL_CROQUIS_GPO_REN);
        db.execSQL(SidertTables.SidertEntry.CREATE_TBL_POLITICAS_INTEGRANTE_REN);
        db.execSQL(SidertTables.SidertEntry.CREATE_TBL_DOCUMENTOS_INTEGRANTE_REN);
        db.execSQL(SidertTables.SidertEntry.CREATE_TBL_DATOS_CREDITO_GPO);

        db.execSQL(SidertTables.SidertEntry.CREATE_TBL_DATOS_CREDITO_GPO_AUTO);
        db.execSQL(SidertTables.SidertEntry.CREATE_TBL_INTEGRANTES_GPO_AUTO);
        db.execSQL(SidertTables.SidertEntry.CREATE_TBL_TELEFONOS_INTEGRANTE_AUTO);
        db.execSQL(SidertTables.SidertEntry.CREATE_TBL_DOMICILIO_INTEGRANTE_AUTO);
        db.execSQL(SidertTables.SidertEntry.CREATE_TBL_NEGOCIO_INTEGRANTE_AUTO);
        db.execSQL(SidertTables.SidertEntry.CREATE_TBL_CONYUGE_INTEGRANTE_AUTO);
        db.execSQL(SidertTables.SidertEntry.CREATE_TBL_OTROS_DATOS_INTEGRANTE_AUTO);
        db.execSQL(SidertTables.SidertEntry.CREATE_TBL_CROQUIS_GPO_AUTO);
        db.execSQL(SidertTables.SidertEntry.CREATE_TBL_POLITICAS_INTEGRANTE_AUTO);

        db.execSQL(SidertTables.SidertEntry.CREATE_TBL_SOLICITUDES_AUTO);
        db.execSQL(SidertTables.SidertEntry.CREATE_TBL_CREDITO_IND_AUTO);
        db.execSQL(SidertTables.SidertEntry.CREATE_TBL_CLIENTE_IND_AUTO);
        db.execSQL(SidertTables.SidertEntry.CREATE_TBL_CONYUGE_IND_AUTO);
        db.execSQL(SidertTables.SidertEntry.CREATE_TBL_ECONOMICOS_IND_AUTO);
        db.execSQL(SidertTables.SidertEntry.CREATE_TBL_NEGOCIO_IND_AUTO);
        db.execSQL(SidertTables.SidertEntry.CREATE_TBL_AVAL_IND_AUTO);
        db.execSQL(SidertTables.SidertEntry.CREATE_TBL_REFERENCIA_IND_AUTO);
        db.execSQL(SidertTables.SidertEntry.CREATE_TBL_CROQUIS_IND_AUTO);
        db.execSQL(SidertTables.SidertEntry.CREATE_TBL_POLITICAS_PLD_IND_AUTO);
        db.execSQL(SidertTables.SidertEntry.CREATE_TABLE_DIRECCIONES_AUTO);

        db.execSQL(SidertTables.SidertEntry.CREATE_TABLE_IDENTIFICACIONES_TIPO);
        db.execSQL(SidertTables.SidertEntry.CREATE_TABLE_ESTADOS_CIVILES);
        db.execSQL(SidertTables.SidertEntry.CREATE_TABLE_NIVELES_ESTUDIOS);
        db.execSQL(SidertTables.SidertEntry.CREATE_TABLE_MEDIOS_PAGO_ORIG);
        db.execSQL(SidertTables.SidertEntry.CREATE_TABLE_PARENTESCOS);
        db.execSQL(SidertTables.SidertEntry.CREATE_TABLE_VIVIENDA_TIPOS);
        db.execSQL(SidertTables.SidertEntry.CREATE_TABLE_MEDIOS_CONTACTO);
        db.execSQL(SidertTables.SidertEntry.CREATE_TABLE_DESTINOS_CREDITO);
        db.execSQL(SidertTables.SidertEntry.CREATE_TABLE_DIRECCIONES);
        db.execSQL(SidertTables.SidertEntry.CREATE_TABLE_LOCALIDADES);
        db.execSQL(SidertTables.SidertEntry.CREATE_TBL_PRESTAMOS_TO_RENOVAR);

        db.execSQL(SidertTables.SidertEntry.CREATE_TBL_PRESTAMOS);

        db.execSQL(SidertTables.SidertEntry.CREATE_TBL_RECIBOS_AGF_CC);
        db.execSQL(SidertTables.SidertEntry.CREATE_TBL_RECUPERACION_RECIBOS);
        db.execSQL(SidertTables.SidertEntry.CREATE_TBL_CONSULTA_CC);
        db.execSQL(SidertTables.SidertEntry.CREATE_TBL_RECIBOS_CC);
        db.execSQL(SidertTables.SidertEntry.CREATE_TBL_RECUPERACION_CC);
        db.execSQL(SidertTables.SidertEntry.CREATE_TBL_TELEFONOS_CLIENTE);
        db.execSQL(SidertTables.SidertEntry.CREATE_TABLE_VER_DOM);
        db.execSQL(SidertTables.SidertEntry.CREATE_TABLE_GESTIONES_VER_DOM);

        db.execSQL(SidertTables.SidertEntry.CREATE_TBL_SERVICIOS_SINCRONIZADOS);
        db.execSQL(SidertTables.SidertEntry.CREATE_TBL_DOCUMENTOS_CLIENTES);

        Log.v("CreacionTablas", "se crearon tablas");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        try { db.execSQL(SidertTables.SidertEntry.CREATE_TABLE_GEOLOCALIZACION_T); }
        catch (Exception e) { Log.e("Tablas", "Catch ya existe las tabla GEOLOCALIZACION_T"); }

        try { db.execSQL(SidertTables.SidertEntry.CREATE_TABLE_LOGIN_REPORT_T); }
        catch (Exception e) { Log.e("Tablas", "Catch ya existe las tabla LOGIN_REPORT_T"); }

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

        try { db.execSQL(SidertTables.SidertEntry.CREATE_TABLE_SINCRONIZADO_T); }
        catch (Exception e) { Log.e("Tablas", "Catch ya existe las tabla SINCRONIZADO_T"); }

        try { db.execSQL(SidertTables.SidertEntry.ADD_CREATE_AT_GEO); }
        catch (Exception e) { Log.e("ADD GEO CREATE", "ya contiene la columna"); }

        try { db.execSQL(SidertTables.SidertEntry.ADD_CREATE_AT_GEO_T); }
        catch (Exception e) { Log.e("ADD GEO_T CREATE", "ya contiene la columna"); }

        try { db.execSQL(SidertTables.SidertEntry.ADD_IS_RUTA_T); }
        catch (Exception e) { Log.e("ADD GEO_T CREATE", "ya contiene la columna"); }

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

        try { db.execSQL(SidertTables.SidertEntry.CREATE_TBL_OTROS_DATOS_INTEGRANTE); }
        catch (Exception e) {  Log.e("Tablas", "Catch tabla OTROS_DATOS_INTEGRANTE"); }

        try { db.execSQL(SidertTables.SidertEntry.CREATE_TBL_CROQUIS_GPO); }
        catch (Exception e) {  Log.e("Tablas", "Catch tabla TBL_CROQUIS_GPO"); }

        try { db.execSQL(SidertTables.SidertEntry.CREATE_TBL_POLITICAS_INTEGRANTE); }
        catch (Exception e) {  Log.e("Tablas", "Catch tabla TBL_POLITICAS_INTEGRANTE"); }

        try { db.execSQL(SidertTables.SidertEntry.CREATE_TBL_DOCUMENTOS_INTEGRANTE_T); }
        catch (Exception e) {  Log.e("Tablas", "Catch tabla DOCUMENTOS_INTEGRANTE_T"); }

        try { db.execSQL(SidertTables.SidertEntry.CREATE_TBL_CARTERA_IND_T); }
        catch (Exception e) {  Log.e("Tablas", "Catch tabla CARTERA_IND_T"); }

        try { db.execSQL(SidertTables.SidertEntry.CREATE_TBL_CARTERA_GPO_T); }
        catch (Exception e) {  Log.e("Tablas", "Catch tabla CARTERA_GPO_T"); }

        try { db.execSQL(SidertTables.SidertEntry.CREATE_TBL_PRESTAMOS_IND_T); }
        catch (Exception e) {  Log.e("Tablas", "Catch tabla PRESTAMOS_IND_T"); }

        try { db.execSQL(SidertTables.SidertEntry.CREATE_TBL_AMORTIZACIONES_T); }
        catch (Exception e) {  Log.e("Tablas", "Catch tabla AMORTIZACIONES_T"); }

        try { db.execSQL(SidertTables.SidertEntry.CREATE_TBL_PAGOS_IND_T); }
        catch (Exception e) {  Log.e("Tablas", "Catch tabla PAGOS_IND_T"); }

        try { db.execSQL(SidertTables.SidertEntry.CREATE_TBL_PRESTAMOS_GPO_T); }
        catch (Exception e) {  Log.e("Tablas", "Catch tabla PRESTAMOS_GPO_T"); }

        try { db.execSQL(SidertTables.SidertEntry.CREATE_TBL_MIEMBROS_T); }
        catch (Exception e) {  Log.e("Tablas", "Catch tabla MIEMBROS_T"); }

        try { db.execSQL(SidertTables.SidertEntry.CREATE_TBL_MIEMBROS_PAGOS_T); }
        catch (Exception e) {  Log.e("Tablas", "Catch tabla MIEMBROS_PAGOS_T"); }

        try { db.execSQL(SidertTables.SidertEntry.CREATE_TBL_PAGOS_T); }
        catch (Exception e) {  Log.e("Tablas", "Catch tabla PAGOS_T"); }

        try { db.execSQL(SidertTables.SidertEntry.CREATE_TBL_AVAL_T); }
        catch (Exception e) {  Log.e("Tablas", "Catch tabla AVAL_T"); }

        try { db.execSQL(SidertTables.SidertEntry.CREATE_TBL_RESPUESTAS_IND_T); }
        catch (Exception e) {  Log.e("Tablas", "Catch tabla RESPUESTAS_IND_T"); }

        try { db.execSQL(SidertTables.SidertEntry.CREATE_TBL_RESPUESTAS_GPO_T); }
        catch (Exception e) {  Log.e("Tablas", "Catch tabla RESPUESTAS_GPO_T"); }

        try { db.execSQL(SidertTables.SidertEntry.CREATE_TBL_IMPRESIONES_VIGENTE_T); }
        catch (Exception e) {  Log.e("Tablas", "Catch tabla IMPRESIONES_VIGENTE_T"); }

        try { db.execSQL(SidertTables.SidertEntry.CREATE_TBL_IMPRESIONES_VENCIDA_T); }
        catch (Exception e) {  Log.e("Tablas", "Catch tabla IMPRESIONES_VIGENTE_T"); }

        try { db.execSQL(SidertTables.SidertEntry.CREATE_TBL_REIMPRESION_VIGENTE_T); }
        catch (Exception e) {  Log.e("Tablas", "Catch tabla IMPRESIONES_VIGENTE_T"); }

        try { db.execSQL(SidertTables.SidertEntry.CREATE_TBL_ARQUEO_CAJA_T); }
        catch (Exception e) {  Log.e("Tablas", "Catch tabla ARQUEO_CAJA_T"); }

        try { db.execSQL(SidertTables.SidertEntry.CREATE_TABLE_VER_DOM); }
        catch (Exception e) {  Log.e("Tablas", "Catch tabla CREATE_TABLE_VER_DOM"); }

        try { db.execSQL(SidertTables.SidertEntry.CREATE_TABLE_GESTIONES_VER_DOM); }
        catch (Exception e) {  Log.e("Tablas", "Catch tabla CREATE_TABLE_GESTIONES_VER_DOM"); }

        try { db.execSQL(SidertTables.SidertEntry.CREATE_TBL_SERVICIOS_SINCRONIZADOS); }
        catch (Exception e) {  Log.e("Tablas", "Catch tabla CREATE_TBL_SERVICIOS_SINCRONIZADOS"); }






        try { db.execSQL(SidertTables.SidertEntry.ADD_RES_IMPRESION); }
        catch (Exception e) { Log.e("ADD RES IMPRESION", "ya contiene la columna"); }

        try { db.execSQL(SidertTables.SidertEntry.ADD_RES_IMPRESION_T); }
        catch (Exception e) { Log.e("ADD RES IMPRESION T", "ya contiene la columna"); }

        try { db.execSQL(SidertTables.SidertEntry.ADD_RES_IMPRESION_GPO); }
        catch (Exception e) { Log.e("ADD RES IMPRESION", "ya contiene la columna"); }

        try { db.execSQL(SidertTables.SidertEntry.ADD_RES_IMPRESION_GPO_T); }
        catch (Exception e) { Log.e("ADD RES IMPRESION T", "ya contiene la columna"); }

        try { db.execSQL(SidertTables.SidertEntry.ADD_ARQUEO_CAJA_GPO); }
        catch (Exception e) { Log.e("ADD_ARQUEO_CAJA", "ya contiene la columna"); }

        try { db.execSQL(SidertTables.SidertEntry.ADD_ARQUEO_CAJA_GPO_T); }
        catch (Exception e) { Log.e("ADD ARQUEO_CAJA T", "ya contiene la columna"); }

        try { db.execSQL(SidertTables.SidertEntry.ADD_COLONIA_CARTERA_IND); }
        catch (Exception e) { Log.e("ADD RES IMPRESION", "ya contiene la columna"); }

        try { db.execSQL(SidertTables.SidertEntry.ADD_COLONIA_CARTERA_IND_T); }
        catch (Exception e) { Log.e("ADD RES IMPRESION T", "ya contiene la columna"); }

        try { db.execSQL(SidertTables.SidertEntry.ADD_COLONIA_CARTERA_GPO); }
        catch (Exception e) { Log.e("ADD RES IMPRESION", "ya contiene la columna"); }

        try { db.execSQL(SidertTables.SidertEntry.ADD_COLONIA_CARTERA_GPO_T); }
        catch (Exception e) { Log.e("ADD RES IMPRESION T", "ya contiene la columna"); }

        try { db.execSQL(SidertTables.SidertEntry.ADD_ESTATUS_PAGO_IND_T); }
        catch (Exception e) { Log.e("ADD RES IMPRESION T", "ya contiene la columna"); }

        try { db.execSQL(SidertTables.SidertEntry.ADD_SALDO_CORTE_IND_T); }
        catch (Exception e) { Log.e("ADD RES IMPRESION T", "ya contiene la columna"); }

        try { db.execSQL(SidertTables.SidertEntry.ADD_SALDO_ACTUAL_IND_T); }
        catch (Exception e) { Log.e("ADD RES IMPRESION T", "ya contiene la columna"); }

        try { db.execSQL(SidertTables.SidertEntry.ADD_ESTATUS_PAGO_IND); }
        catch (Exception e) { Log.e("ADD RES IMPRESION T", "ya contiene la columna"); }

        try { db.execSQL(SidertTables.SidertEntry.ADD_SALDO_CORTE_IND); }
        catch (Exception e) { Log.e("ADD RES IMPRESION T", "ya contiene la columna"); }

        try { db.execSQL(SidertTables.SidertEntry.ADD_SALDO_ACTUAL_IND); }
        catch (Exception e) { Log.e("ADD RES IMPRESION T", "ya contiene la columna"); }

        try { db.execSQL(SidertTables.SidertEntry.ADD_ESTATUS_PAGO_GPO_T); }
        catch (Exception e) { Log.e("ADD RES IMPRESION T", "ya contiene la columna"); }

        try { db.execSQL(SidertTables.SidertEntry.ADD_SALDO_CORTE_GPO_T); }
        catch (Exception e) { Log.e("ADD RES IMPRESION T", "ya contiene la columna"); }

        try { db.execSQL(SidertTables.SidertEntry.ADD_SALDO_ACTUAL_GPO_T); }
        catch (Exception e) { Log.e("ADD RES IMPRESION T", "ya contiene la columna"); }

        try { db.execSQL(SidertTables.SidertEntry.ADD_ESTATUS_PAGO_GPO); }
        catch (Exception e) { Log.e("ADD RES IMPRESION T", "ya contiene la columna"); }

        try { db.execSQL(SidertTables.SidertEntry.ADD_SALDO_CORTE_GPO); }
        catch (Exception e) { Log.e("ADD RES IMPRESION T", "ya contiene la columna"); }

        try { db.execSQL(SidertTables.SidertEntry.ADD_SALDO_ACTUAL_GPO); }
        catch (Exception e) { Log.e("ADD RES IMPRESION T", "ya contiene la columna"); }

        try { db.execSQL(SidertTables.SidertEntry.ADD_DIAS_ATRASO_GPO); }
        catch (Exception e) { Log.e("ADD RES IMPRESION T", "ya contiene la columna"); }

        try { db.execSQL(SidertTables.SidertEntry.ADD_DIAS_ATRASO_GPO_T); }
        catch (Exception e) { Log.e("ADD RES IMPRESION T", "ya contiene la columna"); }

        try { db.execSQL(SidertTables.SidertEntry.ADD_DIAS_ATRASO_IND); }
        catch (Exception e) { Log.e("ADD RES IMPRESION T", "ya contiene la columna"); }

        try { db.execSQL(SidertTables.SidertEntry.ADD_NUM_PRESTAMO); }
        catch (Exception e) { Log.e("ADD RES IMPRESION T", "ya contiene la columna"); }

        try { db.execSQL(SidertTables.SidertEntry.ADD_NUM_PRESTAMO_T); }
        catch (Exception e) { Log.e("ADD RES IMPRESION T", "ya contiene la columna"); }

        try { db.execSQL(SidertTables.SidertEntry.ADD_CELULAR_T); }
        catch (Exception e) { Log.e("ADD RES IMPRESION T", "ya contiene la columna"); }

        try { db.execSQL(SidertTables.SidertEntry.ADD_CELULAR_VE_T); }
        catch (Exception e) { Log.e("ADD RES IMPRESION T", "ya contiene la columna"); }

        try { db.execSQL(SidertTables.SidertEntry.ADD_CELULAR_REIMPRESION_T); }
        catch (Exception e) { Log.e("ADD RES IMPRESION T", "ya contiene la columna"); }

        try { db.execSQL(SidertTables.SidertEntry.ADD_DIAS_ATRASO_IND_T); }
        catch (Exception e) { Log.e("ADD RES IMPRESION T", "ya contiene la columna"); }

        try { db.execSQL(SidertTables.SidertEntry.ADD_NUM_PRESTAMO_RIM); }
        catch (Exception e) { Log.e("ADD RES IMPRESION T", "ya contiene la columna"); }

        try{ db.execSQL(SidertTables.SidertEntry.CREATE_TBL_TRACKER_ASESOR_T); }
        catch (Exception e) {}

        try { db.execSQL(SidertTables.SidertEntry.CREATE_TBL_GEO_RESPUESTAS); }
        catch (Exception e) {  Log.e("Tablas", "Catch tabla GEO_RESPUESTAS"); }

        try { db.execSQL(SidertTables.SidertEntry.ADD_CLAVE_INTEGRANTE); }
        catch (Exception e) { Log.e("ADD CLAVE INTEGRANTE", "ya contiene la columna"); }

        try { db.execSQL(SidertTables.SidertEntry.ADD_CLAVE_INTEGRANTE_T); }
        catch (Exception e) { Log.e("ADD CLAVE INTEGRANTE T", "ya contiene la columna"); }

        try { db.execSQL(SidertTables.SidertEntry.ADD_PRESTAMO_ID_INTEGRANTE); }
        catch (Exception e) { Log.e("ADD ID PRESTAMO INTE", "ya contiene la columna"); }
        try { db.execSQL(SidertTables.SidertEntry.ADD_PRESTAMO_ID_INTEGRANTE_T); }
        catch (Exception e) { Log.e("ADD ID PRESTAMO INTE T", "ya contiene la columna"); }
        try { db.execSQL(SidertTables.SidertEntry.ADD_CLAVE_GEO_RESP); }
        catch (Exception e) { Log.e("ADD CLAVE GEO", "ya contiene la columna"); }
        try { db.execSQL(SidertTables.SidertEntry.ADD_GEO_CLIENTE_IND); }
        catch (Exception e) { Log.e("ADD CLAVE GEO", "ya contiene la columna"); }
        try { db.execSQL(SidertTables.SidertEntry.ADD_GEO_AVAL_IND); }
        catch (Exception e) { Log.e("ADD CLAVE GEO", "ya contiene la columna"); }
        try { db.execSQL(SidertTables.SidertEntry.ADD_GEO_NEGOCIO_IND); }
        catch (Exception e) { Log.e("ADD CLAVE GEO", "ya contiene la columna"); }
        try { db.execSQL(SidertTables.SidertEntry.ADD_GEOLOCALIZADAS_GPO); }
        catch (Exception e) { Log.e("ADD CLAVE GEO", "ya contiene la columna"); }
        try{ db.execSQL(SidertTables.SidertEntry.CREATE_TBL_RESPUESTAS_IND_V_T); }
        catch (Exception e) {}
        try{ db.execSQL(SidertTables.SidertEntry.CREATE_TBL_RESPUESTAS_INTEGRANTE_T); }
        catch (Exception e) {}
        try { db.execSQL(SidertTables.SidertEntry.ADD_SERIAL_INDIVIDUAL); }
        catch (Exception e) { Log.e("ADD_SERIE_ID", "ya contiene la columna"); }
        try { db.execSQL(SidertTables.SidertEntry.ADD_SERIAL_INTEGRANTE); }
        catch (Exception e) { Log.e("ADD_SERIE_ID", "ya contiene la columna"); }
        try { db.execSQL(SidertTables.SidertEntry.CREATE_TBL_CIERRE_DIA); }
        catch (Exception e) { Log.e("Tablas", "Catch ya existe las tabla CIERRE DE DIA"); }

        try { db.execSQL(SidertTables.SidertEntry.ADD_NOMBRE_CIERRE_DIA); }
        catch (Exception e) { Log.e("ADD NOMBRE CIERRE", "ya contiene la columna"); }
        try { db.execSQL(SidertTables.SidertEntry.ADD_SERIAL_ID_CIERRE_DIA); }
        catch (Exception e) { Log.e("ADD SERIAL CIERRE", "ya contiene la columna"); }
        try{ db.execSQL(SidertTables.SidertEntry.CREATE_TBL_REPORTE_SESIONES); }
        catch (Exception e) {}
        try{ db.execSQL(SidertTables.SidertEntry.CREATE_TBL_SUCURSALES); }
        catch (Exception e) {}
        try{ db.execSQL(SidertTables.SidertEntry.CREATE_TBL_CODIGOS_OXXO); }
        catch (Exception e) {}
        try { db.execSQL(SidertTables.SidertEntry.ADD_FECHA_VENCIMIENTO_CODIGOS); }
        catch (Exception e) { Log.e("ADD FECHA VENCI", "ya contiene la columna"); }
        try { db.execSQL(SidertTables.SidertEntry.ADD_ESTATUS_CARTERA_IND); }
        catch (Exception e) { Log.e("ADD ESTATUS CAR IND", "ya contiene la columna"); }
        try { db.execSQL(SidertTables.SidertEntry.ADD_ESTATUS_CARTERA_GPO); }
        catch (Exception e) { Log.e("ADD ESTATUS CAR GPO", "ya contiene la columna"); }
        try { db.execSQL(SidertTables.SidertEntry.CREATE_TBL_CANCEL_GESTIONES); }
        catch (Exception e) { Log.e("CANCEL_GESTI", "Catch ya existe las tabla CANCEL GESTIONES"); }
        try { db.execSQL(SidertTables.SidertEntry.CREATE_TBL_RECIBOS); }
        catch (Exception e) {  Log.e("Tablas", "Catch tabla RECIBOS"); }
        try { db.execSQL(SidertTables.SidertEntry.ADD_CURP_RECIBOS); }
        catch (Exception e) { Log.e("ADD CURP", "ya contiene la columna"); }

        try { db.execSQL(SidertTables.SidertEntry.ADD_CC_IND); }
        catch (Exception e) { Log.e("ADD CC_IND", "ya contiene la columna"); }
        try { db.execSQL(SidertTables.SidertEntry.ADD_AGF_IND); }
        catch (Exception e) { Log.e("ADD AGF_IND", "ya contiene la columna"); }
        try { db.execSQL(SidertTables.SidertEntry.ADD_CURP_CARTERA); }
        catch (Exception e) { Log.e("ADD CURP_IND", "ya contiene la columna"); }
        try { db.execSQL(SidertTables.SidertEntry.ADD_CC_GPO); }
        catch (Exception e) { Log.e("ADD CC_GPO", "ya contiene la columna"); }
        try { db.execSQL(SidertTables.SidertEntry.ADD_AGF_GPO); }
        catch (Exception e) { Log.e("ADD AGF_GPO", "ya contiene la columna"); }
        try { db.execSQL(SidertTables.SidertEntry.ADD_CURP_MIEMBROS); }
        catch (Exception e) { Log.e("ADD CURP_MIEM", "ya contiene la columna"); }
        try { db.execSQL(SidertTables.SidertEntry.CREATE_TBL_CATEGORIA_TICKETS); }
        catch (Exception e) { Log.e("CREA TICKETS", "ya existe tickets"); }

        try { db.execSQL(SidertTables.SidertEntry.CREATE_TBL_SOPORTE); }
        catch (Exception e) { Log.e("CREA SOPORTE", "ya existe tickets"); }

        try { db.execSQL(SidertTables.SidertEntry.CREATE_TBL_PLAZOS_PRESTAMOS); }
        catch (Exception e) { Log.e("CREA PLAZOS", "ya existe tickets"); }

        try { db.execSQL(SidertTables.SidertEntry.CREATE_TBL_SOLICITUDES); }
        catch (Exception e) {  Log.e("Tablas", "Catch tabla TBL_SOLICITUDES"); }
        try { db.execSQL(SidertTables.SidertEntry.CREATE_TBL_CREDITO_IND); }
        catch (Exception e) {  Log.e("Tablas", "Catch tabla TBL_CREDITO_IND"); }
        try { db.execSQL(SidertTables.SidertEntry.CREATE_TBL_CLIENTE_IND); }
        catch (Exception e) {  Log.e("Tablas", "Catch tabla TBL_CLIENTE_IND"); }
        try { db.execSQL(SidertTables.SidertEntry.CREATE_TBL_CONYUGE_IND); }
        catch (Exception e) {  Log.e("Tablas", "Catch tabla TBL_CONYUGE_IND"); }
        try { db.execSQL(SidertTables.SidertEntry.CREATE_TBL_ECONOMICOS_IND); }
        catch (Exception e) {  Log.e("Tablas", "Catch tabla TBL_ECONOMICOS_IND"); }
        try { db.execSQL(SidertTables.SidertEntry.CREATE_TBL_NEGOCIO_IND); }
        catch (Exception e) {  Log.e("Tablas", "Catch tabla TBL_NEGOCIO_IND"); }
        try { db.execSQL(SidertTables.SidertEntry.CREATE_TBL_AVAL_IND); }
        catch (Exception e) {  Log.e("Tablas", "Catch tabla TBL_AVAL_IND"); }
        try { db.execSQL(SidertTables.SidertEntry.CREATE_TBL_REFERENCIA_IND); }
        catch (Exception e) {  Log.e("Tablas", "Catch tabla TBL_REFERENCIA_IND"); }
        try { db.execSQL(SidertTables.SidertEntry.CREATE_TBL_CROQUIS_IND); }
        catch (Exception e) {  Log.e("Tablas", "Catch tabla TBL_CROQUIS_IND"); }
        try { db.execSQL(SidertTables.SidertEntry.CREATE_TBL_POLITICAS_PLD_IND); }
        catch (Exception e) {  Log.e("Tablas", "Catch tabla TBL_POLITICAS_PLD_IND"); }
        try { db.execSQL(SidertTables.SidertEntry.CREATE_TBL_DOCUMENTOS); }
        catch (Exception e) {  Log.e("Tablas", "Catch tabla TBL_DOCUMENTOS"); }
        try { db.execSQL(SidertTables.SidertEntry.CREATE_TBL_RESUMENES_GESTION); }
        catch (Exception e) {  Log.e("Tablas", "Catch tabla TBL_RESUMENES_GESTION"); }

        try { db.execSQL(SidertTables.SidertEntry.ADD_ID_SOLICITUD_INTEGRANTE); }
        catch (Exception e) { Log.e("ID_SOLICITUD_INTEGRANTE", "ya contiene la columna"); }

        try { db.execSQL(SidertTables.SidertEntry.CREATE_TBL_DATOS_CREDITO_GPO); }
        catch (Exception e) {  Log.e("Tablas", "Catch tabla DATOS_CREDITO_GPO"); }

        try { db.execSQL(SidertTables.SidertEntry.CREATE_TBL_PRESTAMOS_TO_RENOVAR); }
        catch (Exception e) {  Log.e("Tablas", "Catch tabla PRESTAMOS_TO_RENOVAR"); }

        try { db.execSQL(SidertTables.SidertEntry.CREATE_TABLE_IDENTIFICACIONES_TIPO); }
        catch (Exception e) {  Log.e("Tablas", "Catch tabla TABLE_IDENTIFICACIONES_TIPO"); }
        try { db.execSQL(SidertTables.SidertEntry.CREATE_TABLE_ESTADOS_CIVILES); }
        catch (Exception e) {  Log.e("Tablas", "Catch tabla TABLE_ESTADOS_CIVILES"); }
        try { db.execSQL(SidertTables.SidertEntry.CREATE_TABLE_NIVELES_ESTUDIOS); }
        catch (Exception e) {  Log.e("Tablas", "Catch tabla TABLE_NIVELES_ESTUDIOS"); }
        try { db.execSQL(SidertTables.SidertEntry.CREATE_TABLE_MEDIOS_PAGO_ORIG); }
        catch (Exception e) {  Log.e("Tablas", "Catch tabla TABLE_MEDIOS_PAGO_ORIG"); }
        try { db.execSQL(SidertTables.SidertEntry.CREATE_TABLE_PARENTESCOS); }
        catch (Exception e) {  Log.e("Tablas", "Catch tabla TABLE_PARENTESCOS"); }
        try { db.execSQL(SidertTables.SidertEntry.CREATE_TABLE_VIVIENDA_TIPOS); }
        catch (Exception e) {  Log.e("Tablas", "Catch tabla TABLE_VIVIENDA_TIPOS"); }
        try { db.execSQL(SidertTables.SidertEntry.CREATE_TABLE_MEDIOS_CONTACTO); }
        catch (Exception e) {  Log.e("Tablas", "Catch tabla TABLE_MEDIOS_CONTACTO"); }
        try { db.execSQL(SidertTables.SidertEntry.CREATE_TABLE_DESTINOS_CREDITO); }
        catch (Exception e) {  Log.e("Tablas", "Catch tabla TABLE_DESTINOS_CREDITO"); }
        try { db.execSQL(SidertTables.SidertEntry.CREATE_TABLE_DIRECCIONES); }
        catch (Exception e) {  Log.e("Tablas", "Catch tabla TABLE_DIRECCIONES"); }
        try { db.execSQL(SidertTables.SidertEntry.CREATE_TABLE_LOCALIDADES); }
        catch (Exception e) {  Log.e("Tablas", "Catch tabla TABLE_LOCALIDADES"); }


        try {db.execSQL(SidertTables.SidertEntry.CREATE_TBL_SOLICITUDES_REN);}
        catch (Exception e) {  Log.e("Tablas", "Catch tabla TBL_SOLICITUDES_REN"); }
        try {db.execSQL(SidertTables.SidertEntry.CREATE_TBL_CREDITO_IND_REN);}
        catch (Exception e) {  Log.e("Tablas", "Catch tabla TBL_CREDITO_IND_REN"); }
        try {db.execSQL(SidertTables.SidertEntry.CREATE_TBL_CLIENTE_IND_REN);}
        catch (Exception e) {  Log.e("Tablas", "Catch tabla TBL_CLIENTE_IND_REN"); }
        try {db.execSQL(SidertTables.SidertEntry.CREATE_TBL_CONYUGE_IND_REN);}
        catch (Exception e) {  Log.e("Tablas", "Catch tabla TBL_CONYUGE_IND_REN"); }
        try {db.execSQL(SidertTables.SidertEntry.CREATE_TBL_ECONOMICOS_IND_REN);}
        catch (Exception e) {  Log.e("Tablas", "Catch tabla TBL_ECONOMICOS_IND_REN"); }
        try {db.execSQL(SidertTables.SidertEntry.CREATE_TBL_NEGOCIO_IND_REN);}
        catch (Exception e) {  Log.e("Tablas", "Catch tabla TBL_NEGOCIO_IND_REN"); }
        try {db.execSQL(SidertTables.SidertEntry.CREATE_TBL_AVAL_IND_REN);}
        catch (Exception e) {  Log.e("Tablas", "Catch tabla TBL_AVAL_IND_REN"); }
        try {db.execSQL(SidertTables.SidertEntry.CREATE_TBL_REFERENCIA_IND_REN);}
        catch (Exception e) {  Log.e("Tablas", "Catch tabla TBL_REFERENCIA_IND_REN"); }
        try {db.execSQL(SidertTables.SidertEntry.CREATE_TBL_CROQUIS_IND_REN);}
        catch (Exception e) {  Log.e("Tablas", "Catch tabla TBL_CROQUIS_IND_REN"); }
        try {db.execSQL(SidertTables.SidertEntry.CREATE_TBL_POLITICAS_PLD_IND_REN);}
        catch (Exception e) {  Log.e("Tablas", "Catch tabla TBL_POLITICAS_PLD_IND_REN"); }
        try {db.execSQL(SidertTables.SidertEntry.CREATE_TABLE_DIRECCIONES_REN);}
        catch (Exception e) {  Log.e("Tablas", "Catch tabla TABLE_DIRECCIONES_REN"); }
        try {db.execSQL(SidertTables.SidertEntry.CREATE_TBL_DOCUMENTOS_REN);}
        catch (Exception e) {  Log.e("Tablas", "Catch tabla TBL_DOCUMENTOS_REN"); }

        try{db.execSQL(SidertTables.SidertEntry.CREATE_TBL_DATOS_CREDITO_GPO_REN);}
        catch (Exception e){Log.e("Tablas","Catch tabla TBL_DATOS_CREDITO_GPO_REN");}
        try{db.execSQL(SidertTables.SidertEntry.CREATE_TBL_INTEGRANTES_GPO_REN);}
        catch (Exception e){Log.e("Tablas","Catch tabla TBL_INTEGRANTES_GPO_REN");}
        try{db.execSQL(SidertTables.SidertEntry.CREATE_TBL_TELEFONOS_INTEGRANTE_REN);}
        catch (Exception e){Log.e("Tablas","Catch tabla TELEFONOS_INTEGRANTE_REN");}
        try{db.execSQL(SidertTables.SidertEntry.CREATE_TBL_DOMICILIO_INTEGRANTE_REN);}
        catch (Exception e){Log.e("Tablas","Catch tabla TBL_DOMICILIO_INTEGRANTE_REN");}
        try{db.execSQL(SidertTables.SidertEntry.CREATE_TBL_NEGOCIO_INTEGRANTE_REN);}
        catch (Exception e){Log.e("Tablas","Catch tabla TBL_NEGOCIO_INTEGRANTE_REN");}
        try{db.execSQL(SidertTables.SidertEntry.CREATE_TBL_CONYUGE_INTEGRANTE_REN);}
        catch (Exception e){Log.e("Tablas","Catch tabla TBL_CONYUGE_INTEGRANTE_REN");}
        try{db.execSQL(SidertTables.SidertEntry.CREATE_TBL_OTROS_DATOS_INTEGRANTE_REN);}
        catch (Exception e){Log.e("Tablas","Catch tabla TBL_OTROS_DATOS_INTEGRANTE_REN");}
        try{db.execSQL(SidertTables.SidertEntry.CREATE_TBL_CROQUIS_GPO_REN);}
        catch (Exception e){Log.e("Tablas","Catch tabla TBL_CROQUIS_GPO_REN");}
        try{db.execSQL(SidertTables.SidertEntry.CREATE_TBL_POLITICAS_INTEGRANTE_REN);}
        catch (Exception e){Log.e("Tablas","Catch tabla TBL_POLITICAS_INTEGRANTE_REN");}
        try{db.execSQL(SidertTables.SidertEntry.CREATE_TBL_DOCUMENTOS_INTEGRANTE_REN);}
        catch (Exception e){Log.e("Tablas","Catch tabla TBL_DOCUMENTOS_INTEGRANTE_REN");}

        try {db.execSQL(SidertTables.SidertEntry.CREATE_TBL_SOLICITUDES_AUTO);}
        catch (Exception e) {  Log.e("Tablas", "Catch tabla TBL_SOLICITUDES_REN"); }
        try {db.execSQL(SidertTables.SidertEntry.CREATE_TBL_CREDITO_IND_AUTO);}
        catch (Exception e) {  Log.e("Tablas", "Catch tabla TBL_CREDITO_IND_REN"); }
        try {db.execSQL(SidertTables.SidertEntry.CREATE_TBL_CLIENTE_IND_AUTO);}
        catch (Exception e) {  Log.e("Tablas", "Catch tabla TBL_CLIENTE_IND_REN"); }
        try {db.execSQL(SidertTables.SidertEntry.CREATE_TBL_CONYUGE_IND_AUTO);}
        catch (Exception e) {  Log.e("Tablas", "Catch tabla TBL_CONYUGE_IND_REN"); }
        try {db.execSQL(SidertTables.SidertEntry.CREATE_TBL_ECONOMICOS_IND_AUTO);}
        catch (Exception e) {  Log.e("Tablas", "Catch tabla TBL_ECONOMICOS_IND_REN"); }
        try {db.execSQL(SidertTables.SidertEntry.CREATE_TBL_NEGOCIO_IND_AUTO);}
        catch (Exception e) {  Log.e("Tablas", "Catch tabla TBL_NEGOCIO_IND_REN"); }
        try {db.execSQL(SidertTables.SidertEntry.CREATE_TBL_AVAL_IND_AUTO);}
        catch (Exception e) {  Log.e("Tablas", "Catch tabla TBL_AVAL_IND_REN"); }
        try {db.execSQL(SidertTables.SidertEntry.CREATE_TBL_REFERENCIA_IND_AUTO);}
        catch (Exception e) {  Log.e("Tablas", "Catch tabla TBL_REFERENCIA_IND_REN"); }
        try {db.execSQL(SidertTables.SidertEntry.CREATE_TBL_CROQUIS_IND_AUTO);}
        catch (Exception e) {  Log.e("Tablas", "Catch tabla TBL_CROQUIS_IND_REN"); }
        try {db.execSQL(SidertTables.SidertEntry.CREATE_TBL_POLITICAS_PLD_IND_AUTO);}
        catch (Exception e) {  Log.e("Tablas", "Catch tabla TBL_POLITICAS_PLD_IND_REN"); }
        try {db.execSQL(SidertTables.SidertEntry.CREATE_TABLE_DIRECCIONES_AUTO);}
        catch (Exception e) {  Log.e("Tablas", "Catch tabla TABLE_DIRECCIONES_REN"); }

        try {db.execSQL(SidertTables.SidertEntry.CREATE_TBL_DATOS_CREDITO_GPO_AUTO);}
        catch (Exception e) {  Log.e("Tablas", "Catch tabla BL_DATOS_CREDITO_GPO_AUTO"); }
        try {db.execSQL(SidertTables.SidertEntry.CREATE_TBL_INTEGRANTES_GPO_AUTO);}
        catch (Exception e) {  Log.e("Tablas", "Catch tabla TBL_INTEGRANTES_GPO_AUTO"); }
        try {db.execSQL(SidertTables.SidertEntry.CREATE_TBL_TELEFONOS_INTEGRANTE_AUTO);}
        catch (Exception e) {  Log.e("Tablas", "Catch tabla TBL_TELEFONOS_INTEGRANTE_AUTO"); }
        try {db.execSQL(SidertTables.SidertEntry.CREATE_TBL_DOMICILIO_INTEGRANTE_AUTO);}
        catch (Exception e) {  Log.e("Tablas", "Catch tabla TBL_DOMICILIO_INTEGRANTE_AUTO"); }
        try {db.execSQL(SidertTables.SidertEntry.CREATE_TBL_NEGOCIO_INTEGRANTE_AUTO);}
        catch (Exception e) {  Log.e("Tablas", "Catch tabla TBL_NEGOCIO_INTEGRANTE_AUTO"); }
        try {db.execSQL(SidertTables.SidertEntry.CREATE_TBL_CONYUGE_INTEGRANTE_AUTO);}
        catch (Exception e) {  Log.e("Tablas", "Catch tabla TBL_CONYUGE_INTEGRANTE_AUTO"); }
        try {db.execSQL(SidertTables.SidertEntry.CREATE_TBL_OTROS_DATOS_INTEGRANTE_AUTO);}
        catch (Exception e) {  Log.e("Tablas", "Catch tabla TBL_OTROS_DATOS_INTEGRANTE_AUTO"); }
        try {db.execSQL(SidertTables.SidertEntry.CREATE_TBL_CROQUIS_GPO_AUTO);}
        catch (Exception e) {  Log.e("Tablas", "Catch tabla TBL_CROQUIS_GPO_AUTO"); }
        try {db.execSQL(SidertTables.SidertEntry.CREATE_TBL_POLITICAS_INTEGRANTE_AUTO);}
        catch (Exception e) {  Log.e("Tablas", "Catch tabla TBL_POLITICAS_INTEGRANTE_AUTO"); }

        try{db.execSQL(SidertTables.SidertEntry.CREATE_TBL_PRESTAMOS);}
        catch (Exception e){Log.e("Tablas","Catch tabla TBL_PRESTAMOS");}
        try{db.execSQL(SidertTables.SidertEntry.CREATE_TBL_RECIBOS_AGF_CC);}
        catch (Exception e){Log.e("Tablas","Catch tabla TBL_RECIBOS_AGF_CC");}
        try { db.execSQL(SidertTables.SidertEntry.CREATE_TBL_RECUPERACION_RECIBOS); }
        catch (Exception e) { Log.e("Tablas", "Catch ya existe la tabla RECUPERACION_RECIBOS"); }

        try { db.execSQL(SidertTables.SidertEntry.CREATE_TBL_CONSULTA_CC); }
        catch (Exception e) { Log.e("Tablas", "Catch ya existe la tabla TBL_CONSULTA_CC"); }
        try { db.execSQL(SidertTables.SidertEntry.CREATE_TBL_RECIBOS_CC); }
        catch (Exception e) { Log.e("Tablas", "Catch ya existe la tabla TBL_RECIBOS_CC"); }
        try { db.execSQL(SidertTables.SidertEntry.CREATE_TBL_RECUPERACION_CC); }
        catch (Exception e) { Log.e("Tablas", "Catch ya existe la tabla TBL_RECUPERACION_CC"); }

        try { db.execSQL(SidertTables.SidertEntry.CREATE_TBL_TELEFONOS_CLIENTE); }
        catch (Exception e) { Log.e("Tablas", "Catch ya existe la tabla TBL_TELEFONOS_CLIENTE"); }

        try { db.execSQL(SidertTables.SidertEntry.ADD_DIAS_ATRASO_CARTERA_IND); }
        catch (Exception e) { Log.e("ADD ATRASO IND", "ya contiene la columna"); }
        try { db.execSQL(SidertTables.SidertEntry.ADD_DIAS_ATRASO_CARTERA_GPO); }
        catch (Exception e) { Log.e("ADD ATRASO GPO", "ya contiene la columna"); }
        try { db.execSQL(SidertTables.SidertEntry.ADD_DEPENDIENTES_ECONOMICOS_INTEGRANTE); }
        catch (Exception e) { Log.e("ADD DEPENDIENTES INT", "ya contiene la columna"); }

        try { db.execSQL(SidertTables.SidertEntry.ADD_TIPO_PRESTAMO); }
        catch (Exception e) { Log.e("ADD_TIPO_PRESTAMO", "ya contiene la columna"); }

        try { db.execSQL(SidertTables.SidertEntry.ADD_GRUPO_ID); }
        catch (Exception e) { Log.e("ADD_GRUPO_ID", "ya contiene la columna"); }

        try { db.execSQL(SidertTables.SidertEntry.ADD_COMENTARIO_NEGOCIO_IND); }
        catch (Exception e) { Log.e("COMENT_NEGOCIO_IND", "ya contiene la columna"); }

        try { db.execSQL(SidertTables.SidertEntry.ADD_COMENTARIO_REFERENCIA_IND); }
        catch (Exception e) { Log.e("COMENT_REFE_IND", "ya contiene la columna"); }

        try { db.execSQL(SidertTables.SidertEntry.ADD_COMENTARIO_CROQUIS_IND); }
        catch (Exception e) { Log.e("COMENT_CROQUIS_IND", "ya contiene la columna"); }

        try { db.execSQL(SidertTables.SidertEntry.ADD_COMENTARIO_NEGOCIO_IND_REN); }
        catch (Exception e) { Log.e("COMENT_NEGOCIO_IND", "ya contiene la columna"); }

        try { db.execSQL(SidertTables.SidertEntry.ADD_COMENTARIO_REFERENCIA_IND_REN); }
        catch (Exception e) { Log.e("COMENT_REFE_IND", "ya contiene la columna"); }

        try { db.execSQL(SidertTables.SidertEntry.ADD_COMENTARIO_CROQUIS_IND_REN); }
        catch (Exception e) { Log.e("COMENT_CROQUIS_IND", "ya contiene la columna"); }

        try { db.execSQL(SidertTables.SidertEntry.ADD_MONTO_PRESTAMOS); }
        catch (Exception e) { Log.e("MONTO_PRESTAMO", "ya contiene la columna"); }

        try { db.execSQL(SidertTables.SidertEntry.ADD_MONTO_AUTORIZADO); }
        catch (Exception e) { Log.e("MONTO_AUTORIZADO", "ya contiene la columna"); }

        try { db.execSQL(SidertTables.SidertEntry.ADD_MONTO_AUTORIZADO_GPO); }
        catch (Exception e) { Log.e("MONTO_AUTORIZADO_GPO", "ya contiene la columna"); }

        try { db.execSQL(SidertTables.SidertEntry.ADD_COSTO_CONSULTA); }
        catch (Exception e) { Log.e("COSTO_CONSULTA", "ya contiene la columna"); }

        try { db.execSQL(SidertTables.SidertEntry.ADD_PLAZO_AGF_CC); }
        catch (Exception e) { Log.e("ADD_PLAZO_AGF_CC", "ya contiene la columna"); }

        try { db.execSQL(SidertTables.SidertEntry.ADD_TOTAL_INTEGRANTES_AGF); }
        catch (Exception e) { Log.e("ADD_TOTAL_INTEGRANTES", "ya contiene la columna"); }

        try { db.execSQL(SidertTables.SidertEntry.ADD_TOTAL_INT_MANUAL_AGF); }
        catch (Exception e) { Log.e("ADD_TOTAL_INT_MANUAL", "ya contiene la columna"); }

        try { db.execSQL(SidertTables.SidertEntry.ADD_FECHA_ENTREGA_PRESTAMOS_AV); }
        catch (Exception e) { Log.e("ADD_FCHA_EGA_PMOS_AV", "ya contiene la columna"); }

        try { db.execSQL(SidertTables.SidertEntry.ADD_ESTATUS_PRESTAMO_PRESTAMOS_AV); }
        catch (Exception e) { Log.e("ADD_EST_PMO_PRMOS_AV", "ya contiene la columna"); }

        try { db.execSQL(SidertTables.SidertEntry.ADD_LOCATED_AT_DIRECCIONES); }
        catch (Exception e) { Log.e("ADD_LOCATEDAT_DIR", "ya contiene la columna"); }

        try { db.execSQL(SidertTables.SidertEntry.ADD_LOCATED_AT_DIRECCIONES_AUTO); }
        catch (Exception e) { Log.e("ADD_LOCATEDAT_DIR_AUTO", "ya contiene la columna"); }

        try { db.execSQL(SidertTables.SidertEntry.ADD_LOCATED_AT_DIRECCIONES_RENOV); }
        catch (Exception e) { Log.e("ADD_LOCATEDAT_DIR_RENOV", "ya contiene la columna"); }

        try { db.execSQL(SidertTables.SidertEntry.ADD_LATITUD_CLIENTE); }
        catch (Exception e) { Log.e("ADD_LATITUD_CLI", "ya contiene la columna"); }

        try { db.execSQL(SidertTables.SidertEntry.ADD_LONGITUD_CLIENTE); }
        catch (Exception e) { Log.e("ADD_LONGITUD_CLI", "ya contiene la columna"); }

        try { db.execSQL(SidertTables.SidertEntry.ADD_LOCATED_AT_CLIENTE); }
        catch (Exception e) { Log.e("ADD_LOCATEDAT_CLI", "ya contiene la columna"); }

        try { db.execSQL(SidertTables.SidertEntry.ADD_LATITUD_CLIENTE_AUTO); }
        catch (Exception e) { Log.e("ADD_LATITUD_CLI_AUTO", "ya contiene la columna"); }

        try { db.execSQL(SidertTables.SidertEntry.ADD_LONGITUD_CLIENTE_AUTO); }
        catch (Exception e) { Log.e("ADD_LONGITUD_CLI_AUTO", "ya contiene la columna"); }

        try { db.execSQL(SidertTables.SidertEntry.ADD_LOCATED_AT_CLIENTE_AUTO); }
        catch (Exception e) { Log.e("ADD_LOCATEDAT_CLI_AUTO", "ya contiene la columna"); }

        try { db.execSQL(SidertTables.SidertEntry.ADD_LATITUD_CLIENTE_RENOV); }
        catch (Exception e) { Log.e("ADD_LATITUD_CLIE_REN", "ya contiene la columna"); }

        try { db.execSQL(SidertTables.SidertEntry.ADD_LONGITUD_CLIENTE_RENOV); }
        catch (Exception e) { Log.e("ADD_LONGITUD_CLIE_REN", "ya contiene la columna"); }

        try { db.execSQL(SidertTables.SidertEntry.ADD_LOCATED_AT_CLIENTE_RENOV); }
        catch (Exception e) { Log.e("ADD_LOCATED_AT_CLIE_REN", "ya contiene la columna"); }

        try { db.execSQL(SidertTables.SidertEntry.ADD_LATITUD_AVAL); }
        catch (Exception e) { Log.e("ADD_LATITUD_AVAL", "ya contiene la columna"); }

        try { db.execSQL(SidertTables.SidertEntry.ADD_LONGITUD_AVAL); }
        catch (Exception e) { Log.e("ADD_LONGITUD_AVAL", "ya contiene la columna"); }

        try { db.execSQL(SidertTables.SidertEntry.ADD_LOCATED_AT_AVAL); }
        catch (Exception e) { Log.e("ADD_LOCATED_AT_AVAL", "ya contiene la columna"); }

        try { db.execSQL(SidertTables.SidertEntry.ADD_LATITUD_AVAL_AUTO); }
        catch (Exception e) { Log.e("ADD_LATITUD_AVAL_AUTO", "ya contiene la columna"); }

        try { db.execSQL(SidertTables.SidertEntry.ADD_LONGITUD_AVAL_AUTO); }
        catch (Exception e) { Log.e("ADD_LONGITUD_AVAL_AUTO", "ya contiene la columna"); }

        try { db.execSQL(SidertTables.SidertEntry.ADD_LOCATED_AT_AVAL_AUTO); }
        catch (Exception e) { Log.e("ADD_LOCATED_AT_AVAL_AU", "ya contiene la columna"); }

        try { db.execSQL(SidertTables.SidertEntry.ADD_LATITUD_AVAL_RENOV); }
        catch (Exception e) { Log.e("ADD_LATITUD_AVAL_RENOV", "ya contiene la columna"); }

        try { db.execSQL(SidertTables.SidertEntry.ADD_LONGITUD_AVAL_RENOV); }
        catch (Exception e) { Log.e("ADD_LONGITUD_AVAL_RENOV", "ya contiene la columna"); }

        try { db.execSQL(SidertTables.SidertEntry.ADD_LOCATED_AT_AVAL_RENOV); }
        catch (Exception e) { Log.e("ADD_LOCATED_AT_AVAL_REN", "ya contiene la columna"); }

        try { db.execSQL(SidertTables.SidertEntry.ADD_LOCATED_AT_NEG_INT); }
        catch (Exception e) { Log.e("ADD_LOCATED_AT_NEG_INT", "ya contiene la columna"); }

        try { db.execSQL(SidertTables.SidertEntry.ADD_LOCATED_AT_DOM_INT); }
        catch (Exception e) { Log.e("ADD_LOCATED_AT_NEG_INT", "ya contiene la columna"); }

        try { db.execSQL(SidertTables.SidertEntry.ADD_LATITUD_OD_INT); }
        catch (Exception e) { Log.e("ADD_LATITUD_OD_INT", "ya contiene la columna"); }

        try { db.execSQL(SidertTables.SidertEntry.ADD_LONGITUD_OD_INT); }
        catch (Exception e) { Log.e("ADD_LONGITUD_OD_INT", "ya contiene la columna"); }

        try { db.execSQL(SidertTables.SidertEntry.ADD_LOCATED_AT_OD_INT); }
        catch (Exception e) { Log.e("ADD_LOCATED_AT_OD_INT", "ya contiene la columna"); }

        try { db.execSQL(SidertTables.SidertEntry.ADD_LATITUD_OD_INT_REN); }
        catch (Exception e) { Log.e("ADD_LATITUD_OD_INT_REN", "ya contiene la columna"); }

        try { db.execSQL(SidertTables.SidertEntry.ADD_LONGITUD_OD_INT_REN); }
        catch (Exception e) { Log.e("ADD_LONGITUD_OD_INT_REN", "ya contiene la columna"); }

        try { db.execSQL(SidertTables.SidertEntry.ADD_LOCATED_AT_OD_INT_REN); }
        catch (Exception e) { Log.e("ADD_LOCATED_AT_ODINTREN", "ya contiene la columna"); }

        try { db.execSQL(SidertTables.SidertEntry.ADD_LOCATED_AT_NEG_INT_REN); }
        catch (Exception e) { Log.e("ADD_LOCATED_AT_NEGINREN", "ya contiene la columna"); }

        try { db.execSQL(SidertTables.SidertEntry.ADD_LOCATED_AT_DOM_INT_REN); }
        catch (Exception e) { Log.e("ADD_LOCATED_AT_DOMINREN", "ya contiene la columna"); }

        try { db.execSQL(SidertTables.SidertEntry.ADD_LATITUD_OD_INT_AUTO); }
        catch (Exception e) { Log.e("ADD_LATITUD_OD_INT_AUTO", "ya contiene la columna"); }

        try { db.execSQL(SidertTables.SidertEntry.ADD_LONGITUD_OD_INT_AUTO); }
        catch (Exception e) { Log.e("ADD_LONGITUD_OD_INTAUTO", "ya contiene la columna"); }

        try { db.execSQL(SidertTables.SidertEntry.ADD_LOCATED_AT_OD_INT_AUTO); }
        catch (Exception e) { Log.e("ADD_LOCATED_AT_ODINAUTO", "ya contiene la columna"); }

        try { db.execSQL(SidertTables.SidertEntry.ADD_LOCATED_AT_DOM_INT_AUTO); }
        catch (Exception e) { Log.e("ADD_LOCATED_AT_DOMINAU", "ya contiene la columna"); }

        try { db.execSQL(SidertTables.SidertEntry.ADD_LOCATED_AT_NEG_INT_AUTO); }
        catch (Exception e) { Log.e("ADD_LOCATED_AT_NEGINTAU", "ya contiene la columna"); }

        try { db.execSQL(SidertTables.SidertEntry.ADD_UBIC_DC_NEG_IND); }
        catch (Exception e) { Log.e("ADD_UBIC_DC_NEG_IND", "ya contiene la columna"); }

        try { db.execSQL(SidertTables.SidertEntry.ADD_UBIC_DC_NEG_IND_REN); }
        catch (Exception e) { Log.e("ADD_UBIC_DC_NEG_IND_REN", "ya contiene la columna"); }

        try { db.execSQL(SidertTables.SidertEntry.ADD_UBIC_DC_NEG_IND_AUT); }
        catch (Exception e) { Log.e("ADD_UBIC_DC_NEG_IND_AUT", "ya contiene la columna"); }

        try { db.execSQL(SidertTables.SidertEntry.ADD_TIENE_F_OD_INT); }
        catch (Exception e) { Log.e("ADD_TIENE_F_OD_INT", "ya contiene la columna"); }

        try { db.execSQL(SidertTables.SidertEntry.ADD_NOM_QF_OD_INT); }
        catch (Exception e) { Log.e("ADD_NOM_QF_OD_INT", "ya contiene la columna"); }

        try { db.execSQL(SidertTables.SidertEntry.ADD_TIENE_F_OD_INT_REN); }
        catch (Exception e) { Log.e("ADD_TIENE_F_OD_INT_REN", "ya contiene la columna"); }

        try { db.execSQL(SidertTables.SidertEntry.ADD_NOM_QF_OD_INT_REN); }
        catch (Exception e) { Log.e("ADD_NOM_QF_OD_INT_REN", "ya contiene la columna"); }

        try { db.execSQL(SidertTables.SidertEntry.ADD_TIENE_F_OD_INT_AUT); }
        catch (Exception e) { Log.e("ADD_TIENE_F_OD_INT_AUT", "ya contiene la columna"); }

        try { db.execSQL(SidertTables.SidertEntry.ADD_NOM_QF_OD_INT_AUT); }
        catch (Exception e) { Log.e("ADD_NOM_QF_OD_INT_AUT", "ya contiene la columna"); }

        try { db.execSQL(SidertTables.SidertEntry.ADD_TIENE_NEG_NEG_INT); }
        catch (Exception e) { Log.e("ADD_TIENE_NEG_NEG_INT", "ya contiene la columna"); }

        try { db.execSQL(SidertTables.SidertEntry.ADD_UBIC_DC_NEG_INT); }
        catch (Exception e) { Log.e("ADD_UBIC_DC_NEG_INT", "ya contiene la columna"); }

        try { db.execSQL(SidertTables.SidertEntry.ADD_TIENE_NEG_NEG_INT_REN); }
        catch (Exception e) { Log.e("ADD_TIENE_N_NEG_INT_REN", "ya contiene la columna"); }

        try { db.execSQL(SidertTables.SidertEntry.ADD_UBIC_DC_NEG_INT_REN); }
        catch (Exception e) { Log.e("ADD_UBIC_DC_NEG_INT_REN", "ya contiene la columna"); }

        try { db.execSQL(SidertTables.SidertEntry.ADD_TIENE_NEG_NEG_INT_AUT); }
        catch (Exception e) { Log.e("ADD_TIENE_N_NEG_INT_AUT", "ya contiene la columna"); }

        try { db.execSQL(SidertTables.SidertEntry.ADD_UBIC_DC_NEG_INT_AUT); }
        catch (Exception e) { Log.e("ADD_UBIC_DC_NEG_INT_AUT", "ya contiene la columna"); }

        try { db.execSQL(SidertTables.SidertEntry.ADD_CICLO_INT_GPO_REN); }
        catch (Exception e) { Log.e("ADD_CICLO_INT_GPO_REN", "ya contiene la columna"); }

        try { db.execSQL(SidertTables.SidertEntry.ADD_MONTO_ANT_INT_GPO_REN); }
        catch (Exception e) { Log.e("ADD_MTO_ANT_INT_GPO_REN", "ya contiene la columna"); }

        try { db.execSQL(SidertTables.SidertEntry.ADD_GRUPO_ID_VER_DOM); }
        catch (Exception e) { Log.e("ADD_GRUPO_ID_VER_DOM", "ya contiene la columna"); }

        try { db.execSQL(SidertTables.SidertEntry.ADD_GRUPO_NOM_VER_DOM); }
        catch (Exception e) { Log.e("ADD_GRUPO_NOM_VER_DOM", "ya contiene la columna"); }

        try { db.execSQL(SidertTables.SidertEntry.ADD_NO_SOL_VER_DOM); }
        catch (Exception e) { Log.e("ADD_NO_SOL_VER_DOM", "ya contiene la columna"); }

        try { db.execSQL(SidertTables.SidertEntry.ADD_MONTO_REFINANCIAR_IND); }
        catch (Exception e) { Log.e("ADD_MONTO_REFINANCIAR_IND", "ya contiene la columna"); }

        try { db.execSQL(SidertTables.SidertEntry.ADD_CARACT_DOM_CRO_IND); }
        catch (Exception e) { Log.e("ADD_CARACT_DOM_CRO_IND", "ya contiene la columna"); }

        try { db.execSQL(SidertTables.SidertEntry.ADD_INE_SELF_DOC_IND); }
        catch (Exception e) { Log.e("ADD_INE_SELF_DOC_IND", "ya contiene la columna"); }

        try { db.execSQL(SidertTables.SidertEntry.ADD_COM_GAR_DOC_IND); }
        catch (Exception e) { Log.e("ADD_COM_GAR_DOC_IND", "ya contiene la columna"); }

        try { db.execSQL(SidertTables.SidertEntry.ADD_INE_FRON_AVAL_DOC_IND); }
        catch (Exception e) { Log.e("ADD_INE_FRON_AVAL_DOC_IND", "ya contiene la columna"); }

        try { db.execSQL(SidertTables.SidertEntry.ADD_INE_REV_AVAL_DOC_IND); }
        catch (Exception e) { Log.e("ADD_INE_REV_AVAL_DOC_IND", "ya contiene la columna"); }

        try { db.execSQL(SidertTables.SidertEntry.ADD_CURP_AVAL_DOC_IND); }
        catch (Exception e) { Log.e("ADD_CURP_AVAL_DOC_IND", "ya contiene la columna"); }

        try { db.execSQL(SidertTables.SidertEntry.ADD_COM_DOM_AVAL_DOC_IND); }
        catch (Exception e) { Log.e("ADD_COM_DOM_AVAL_DOC_IND", "ya contiene la columna"); }

        try { db.execSQL(SidertTables.SidertEntry.ADD_MONTO_REFINANCIAR_IND_REN); }
        catch (Exception e) { Log.e("ADD_MONTO_REFINANCIAR_IND_REN", "ya contiene la columna"); }

        try { db.execSQL(SidertTables.SidertEntry.ADD_CARACT_DOM_CRO_IND_REN); }
        catch (Exception e) { Log.e("ADD_CARACT_DOM_CRO_IND_REN", "ya contiene la columna"); }

        try { db.execSQL(SidertTables.SidertEntry.ADD_INE_SELF_DOC_IND_REN); }
        catch (Exception e) { Log.e("ADD_INE_SELF_DOC_IND_REN", "ya contiene la columna"); }

        try { db.execSQL(SidertTables.SidertEntry.ADD_COM_GAR_DOC_IND_REN); }
        catch (Exception e) { Log.e("ADD_COM_GAR_DOC_IND_REN", "ya contiene la columna"); }

        try { db.execSQL(SidertTables.SidertEntry.ADD_INE_FRON_AVAL_DOC_IND_REN); }
        catch (Exception e) { Log.e("ADD_INE_FRON_AVAL_DOC_IND_REN", "ya contiene la columna"); }

        try { db.execSQL(SidertTables.SidertEntry.ADD_INE_REV_AVAL_DOC_IND_REN); }
        catch (Exception e) { Log.e("ADD_INE_REV_AVAL_DOC_IND_REN", "ya contiene la columna"); }

        try { db.execSQL(SidertTables.SidertEntry.ADD_CURP_AVAL_DOC_IND_REN); }
        catch (Exception e) { Log.e("ADD_CURP_AVAL_DOC_IND_REN", "ya contiene la columna"); }

        try { db.execSQL(SidertTables.SidertEntry.ADD_COM_DOM_AVAL_DOC_IND_REN); }
        catch (Exception e) { Log.e("ADD_COM_DOM_AVAL_DOC_IND_REN", "ya contiene la columna"); }

        try { db.execSQL(SidertTables.SidertEntry.ADD_MONTO_REFIN_INT); }
        catch (Exception e) { Log.e("ADD_MONTO_REFIN_INT", "ya contiene la columna"); }

        try { db.execSQL(SidertTables.SidertEntry.ADD_CARAC_DOM_INT); }
        catch (Exception e) { Log.e("ADD_CARAC_DOM_INT", "ya contiene la columna"); }

        try { db.execSQL(SidertTables.SidertEntry.ADD_INE_SELFIE_DOC_INT); }
        catch (Exception e) { Log.e("ADD_INE_SELFIE_DOC_INT", "ya contiene la columna"); }

        try { db.execSQL(SidertTables.SidertEntry.ADD_MONTO_REFIN_INT_REN); }
        catch (Exception e) { Log.e("ADD_MONTO_REFIN_INT_REN", "ya contiene la columna"); }

        try { db.execSQL(SidertTables.SidertEntry.ADD_CARAC_DOM_INT_REN); }
        catch (Exception e) { Log.e("ADD_CARAC_DOM_INT_REN", "ya contiene la columna"); }

        try { db.execSQL(SidertTables.SidertEntry.ADD_INE_SELFIE_DOC_INT_REN); }
        catch (Exception e) { Log.e("ADD_INE_SELFIE_DOC_INT_REN", "ya contiene la columna"); }

        try { db.execSQL(SidertTables.SidertEntry.ADD_PRESTAMO_ID_SOL_REN); }
        catch (Exception e) { Log.e("ADD_PRESTAMO_ID_SOL_REN", "ya contiene la columna"); }

        try { db.execSQL(SidertTables.SidertEntry.CREATE_TBL_DOCUMENTOS_CLIENTES); }
        catch (Exception e) { Log.e("CREATE_TBL_DOCUMENTOS_CLIENTES", "ya existe la tabla DOCUMENTOS_CLIENTES"); }
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

    public void saveCategoriaTickets(SQLiteDatabase db, HashMap<Integer, String> params) {
        db.beginTransaction();

        String sql = "INSERT INTO " + TICKETS + "(" +
                "ticket_id, " +
                "nombre, " +
                "prioridad, " +
                "estatus) VALUES(?, ?, ?, ?)";
        SQLiteStatement pInsert = db.compileStatement(sql);
        pInsert.bindLong(1, Long.parseLong(params.get(0)));     //1  TICKET_ID
        pInsert.bindString(2, params.get(1));                   //2  NOMBRE
        pInsert.bindLong(3, Long.parseLong(params.get(2)));     //3  PRIORIDAD
        pInsert.bindString(4, params.get(3));                   //4  ESTATUS


        pInsert.execute();

        db.setTransactionSuccessful();
        db.endTransaction();
    }

    public void savePrestamosToRenovar(SQLiteDatabase db, HashMap<Integer, String> params) {
        db.beginTransaction();

        String sql = "INSERT INTO " + TBL_PRESTAMOS_TO_RENOVAR + "(" +
                "asesor_id, " +
                "prestamo_id, " +
                "cliente_id, " +
                "cliente_nombre, " +
                "no_prestamo, " +
                "fecha_vencimiento, " +
                "num_pagos, " +
                "descargado, " +
                "tipo_prestamo, " +
                "grupo_id) VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        SQLiteStatement pInsert = db.compileStatement(sql);
        pInsert.bindString(1, params.get(0));                   //1  ASESOR_ID
        pInsert.bindLong(2, Long.parseLong(params.get(1)));     //2  PRESTAMO_ID
        pInsert.bindLong(3, Long.parseLong(params.get(2)));     //3  CLIENTE_ID
        pInsert.bindString(4, params.get(3));                   //4  CLIENTE_NOMBRE
        pInsert.bindString(5, params.get(4));                   //5  NO_PRESTAMO
        pInsert.bindString(6, params.get(5));                   //6  FECHA_VECIMIENTO
        pInsert.bindLong(7, Long.parseLong(params.get(6)));     //7  NUM_PAGOS
        pInsert.bindLong(8, Long.parseLong(params.get(7)));     //8  DESCARGADOS
        pInsert.bindLong(9, Long.parseLong(params.get(8)));     //9  TIPO_PRESTAMO
        pInsert.bindString(10, params.get(9));                  //10 GRUPO_ID

        pInsert.execute();

        db.setTransactionSuccessful();
        db.endTransaction();
    }

    public void saveTelefonosCli(SQLiteDatabase db, HashMap<Integer, String> params) {
        db.beginTransaction();

        String sql = "INSERT INTO " + TBL_TELEFONOS_CLIENTE + "(" +
                "id_prestamo, " +
                "tel_casa, " +
                "tel_celular) VALUES(?, ?, ?)";
        SQLiteStatement pInsert = db.compileStatement(sql);
        pInsert.bindString(1, params.get(0));                   //1  ID_PRESTAMO
        pInsert.bindString(2, params.get(1));                   //2  TEL_CASA
        pInsert.bindString(3, params.get(2));                   //3  TEL_CELULAR

        pInsert.execute();

        db.setTransactionSuccessful();
        db.endTransaction();
    }

    public void saveRecibosAgfCc(SQLiteDatabase db, HashMap<Integer, String> params) {
        db.beginTransaction();

        String sql = "INSERT INTO " + TBL_RECIBOS_AGF_CC + "(" +
                "grupo_id, " +
                "num_solicitud, " +
                "monto, " +
                "folio, " +
                "tipo_recibo, " +
                "tipo_impresion, " +
                "fecha_impresion, " +
                "fecha_envio, " +
                "estatus, " +
                "nombre, " +
                "plazo) VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        SQLiteStatement pInsert = db.compileStatement(sql);
        pInsert.bindString(1, params.get(0));                   //1  GRUPO ID
        pInsert.bindString(2, params.get(1));                   //2  NUM SOLICITUD
        pInsert.bindString(3, params.get(2));                   //3  MONTO
        pInsert.bindString(4, params.get(3));                   //4  FOLIO
        pInsert.bindString(5, params.get(4));                   //5  TIPO RECIBO
        pInsert.bindString(6, params.get(5));                   //6  TIPO IMPRESION
        pInsert.bindString(7, params.get(6));                   //7  FECHA IMPRESION
        pInsert.bindString(8, params.get(7));                   //8  FECHA ENVIO
        pInsert.bindLong(9, Long.parseLong(params.get(8)));     //9  ESTATUS
        pInsert.bindString(10, params.get(9));                  //10  NOMBRE
        pInsert.bindString(11, params.get(10));                  //11  PLAZO

        pInsert.execute();

        db.setTransactionSuccessful();
        db.endTransaction();
    }

    public void savePrestamos(SQLiteDatabase db, HashMap<Integer, String> params) {
        db.beginTransaction();

        String sql = "INSERT INTO " + TBL_PRESTAMOS + "(" +
                "nombre_grupo, " +
                "grupo_id, " +
                "cliente_id, " +
                "num_solicitud, " +
                "periodicidad, " +
                "num_pagos, " +
                "estado_nacimiento, " +
                "genero, " +
                "nombre_cliente, " +
                "fecha_nacimiento, " +
                "edad, " +
                "monto) VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        SQLiteStatement pInsert = db.compileStatement(sql);
        pInsert.bindString(1, params.get(0));                   //1  NOMBRE GRUPO
        pInsert.bindString(2, params.get(1));                   //2  GRUPO ID
        pInsert.bindString(3, params.get(2));                   //3  CLIENTE ID
        pInsert.bindString(4, params.get(3));                   //4  NUM SOLICITUD
        pInsert.bindString(5, params.get(4));                   //5  PERIODICIDAD
        pInsert.bindString(6, params.get(5));                   //6  NUM PAGOS
        pInsert.bindString(7, params.get(6));                   //7  ESTADO NACIMIENTO
        pInsert.bindString(8, params.get(7));                   //8  GENERO
        pInsert.bindString(9, params.get(8));                   //9  NOMBRE CLIENTE
        pInsert.bindString(10, params.get(9));                  //10 FECHA NACIMIENTO
        pInsert.bindString(11, params.get(10));                 //11 EDAD
        pInsert.bindString(12, params.get(11));                 //12 MONTO

        pInsert.execute();

        db.setTransactionSuccessful();
        db.endTransaction();
    }

    public void saveRecibosCC(SQLiteDatabase db, HashMap<Integer, String> params) {
        db.beginTransaction();

        String sql = "INSERT INTO " + TBL_RECIBOS_CC + "(" +
                "tipo_credito, " +
                "nombre_uno, " +
                "curp, " +
                "nombre_dos, " +
                "total_integrantes, " +
                "monto, " +
                "tipo_impresion, " +
                "folio, " +
                "fecha_impresion, " +
                "fecha_envio, " +
                "estatus) VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        SQLiteStatement pInsert = db.compileStatement(sql);
        pInsert.bindString(1, params.get(0));                   //1  TIPO CREDITO
        pInsert.bindString(2, params.get(1));                   //2  NOMBRE UNO
        pInsert.bindString(3, params.get(2));                   //3  CURP
        pInsert.bindString(4, params.get(3));                   //4  NOMBRE DOS
        pInsert.bindLong(5, Long.parseLong(params.get(4)));     //5  TOTAL INTEGRANTES
        pInsert.bindString(6, params.get(5));                   //6  MONTO
        pInsert.bindString(7, params.get(6));                   //7  TIPO IMPRESION
        pInsert.bindString(8, params.get(7));                   //8  FOLIO
        pInsert.bindString(9, params.get(8));                   //9  FECHS IMPRESION
        pInsert.bindString(10, params.get(9));                  //10 FECHA ENVIO
        pInsert.bindLong(11, Long.parseLong(params.get(10)));   //11 ESTATUS

        pInsert.execute();

        db.setTransactionSuccessful();
        db.endTransaction();
    }

    public Long saveRecuperacionCC(SQLiteDatabase db, HashMap<Integer, String> params) {
        db.beginTransaction();

        String sql = "INSERT INTO " + TBL_RECUPERACION_RECIBOS_CC + "(" +
                "tipo_credito, " +
                "nombre_uno, " +
                "curp, " +
                "nombre_dos, " +
                "integrantes, " +
                "monto, " +
                "medio_pago, " +
                "imprimir_recibo, " +
                "folio, "+
                "evidencia, " +
                "tipo_imagen, " +
                "fecha_termino, " +
                "fecha_envio, " +
                "estatus, " +
                "costo_consulta) VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        SQLiteStatement pInsert = db.compileStatement(sql);
        pInsert.bindLong(1, Long.parseLong(params.get(0)));     //1  TIPO CREDITO
        pInsert.bindString(2, params.get(1));                   //2  NOMBRE UNO
        pInsert.bindString(3, params.get(2));                   //3  CURP
        pInsert.bindString(4, params.get(3));                   //4  NOMBRE DOS
        pInsert.bindLong(5, Long.parseLong(params.get(4)));     //5  INTEGRANTES
        pInsert.bindString(6, params.get(5));                   //6  MONTO
        pInsert.bindString(7, params.get(6));                   //7  MEDIO PAGO
        pInsert.bindString(8, params.get(7));                   //8  IMPRIMIR RECIBO
        pInsert.bindLong(9, Long.parseLong(params.get(8)));     //9  FOLIO
        pInsert.bindString(10, params.get(9));                  //10 EVIDENCIA
        pInsert.bindString(11, params.get(10));                 //11 TIPO IMAGEN
        pInsert.bindString(12, params.get(11));                 //12 FECHA TERMINO
        pInsert.bindString(13, params.get(12));                 //13 FECHA ENVIO
        pInsert.bindLong(14, Long.parseLong(params.get(13)));   //14 ESTATUS
        pInsert.bindString(15, params.get(14));                 //15 COSTO_CONSULTA

        Long id = pInsert.executeInsert();

        db.setTransactionSuccessful();
        db.endTransaction();

        return id;
    }

    public long saveConsultaCC(SQLiteDatabase db, HashMap<Integer, String> params) {
        db.beginTransaction();
        String sql = "INSERT INTO " + TBL_CONSULTA_CC + "(" +
                "producto_credito," +
                "monto_solicitado,"+
                "primer_nombre,"+
                "segundo_nombre," +
                "ap_paterno,"+
                "ap_materno,"+
                "fecha_nac,"+
                "genero,"+
                "estado_nac,"+
                "curp,"+
                "rfc,"+
                "direccion,"+
                "colonia,"+
                "municipio,"+
                "ciudad,"+
                "estado,"+
                "cp,"+
                "fecha_termino,"+
                "fecha_envio,"+
                "estatus,"+
                "errores) VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
        SQLiteStatement pInsert = db.compileStatement(sql);
        pInsert.bindString(1, params.get(0));                   //1  PRODUCTO
        pInsert.bindString(2, params.get(1));                   //2  MONTO_SOLICITADO
        pInsert.bindString(3, params.get(2));                   //3  PRIMER_NOMBRE
        pInsert.bindString(4, params.get(3));                   //4  SEGUNDO_NOMBRE
        pInsert.bindString(5, params.get(4));                   //5  AP_PATERNO
        pInsert.bindString(6, params.get(5));                   //6  AP_MATERNO
        pInsert.bindString(7, params.get(6));                   //7  FECHA_NAC
        pInsert.bindString(8, params.get(7));                   //8  GENERO
        pInsert.bindString(9, params.get(8));                   //9  ESTADO_NAC
        pInsert.bindString(10, params.get(9));                  //10 CURP
        pInsert.bindString(11, params.get(10));                 //11 RFC
        pInsert.bindString(12, params.get(11));                 //12 DIRECCION
        pInsert.bindString(13, params.get(12));                 //13 COLONIA
        pInsert.bindString(14, params.get(13));                 //14 MUNICIPIO
        pInsert.bindString(15, params.get(14));                 //15 CIUDAD
        pInsert.bindString(16, params.get(15));                 //16 ESTADO
        pInsert.bindString(17, params.get(16));                 //17 CP

        pInsert.bindString(18, params.get(17));                 //18 FECHA_TERMINO
        pInsert.bindString(19, params.get(18));                 //19 FECHA_ENVIO
        pInsert.bindLong(20, Long.parseLong(params.get(19)));   //20 ESTATUS
        pInsert.bindString(21, params.get(20));                 //20 ERRORES

        long id = pInsert.executeInsert();

        db.setTransactionSuccessful();
        db.endTransaction();

        return id;
    }

    public void saveLocalidades(SQLiteDatabase db, HashMap<Integer, String> params) {
        db.beginTransaction();

        String sql = "INSERT INTO " + LOCALIDADES + "(" +
                "id_localidad, " +
                "nombre, " +
                "id_municipio) VALUES(?, ?, ?)";
        SQLiteStatement pInsert = db.compileStatement(sql);
        pInsert.bindLong(1, Long.parseLong(params.get(0)));     //1  ID_LOCALIDAD
        pInsert.bindString(2, params.get(1));                   //2  NOMBRE
        pInsert.bindLong(3, Long.parseLong(params.get(2)));     //3  ID_MUNICIPIO


        pInsert.execute();

        db.setTransactionSuccessful();
        db.endTransaction();
    }

    public void savePlazosPrestamo(SQLiteDatabase db, HashMap<Integer, String> params) {
        db.beginTransaction();

        String sql = "INSERT INTO " + TBL_PLAZOS_PRESTAMOS + "(" +
                "id_plazo_prestamo, " +
                "nombre, " +
                "numero_plazos, " +
                "estatus) VALUES(?, ?, ?, ?)";
        SQLiteStatement pInsert = db.compileStatement(sql);
        pInsert.bindLong(1, Long.parseLong(params.get(0)));     //1  ID_PLAZO_PRESTAMO
        pInsert.bindString(2, params.get(1));                   //2  NOMBRE
        pInsert.bindLong(3, Long.parseLong(params.get(2)));     //3  NUMERO_PLAZO
        pInsert.bindLong(4, Long.parseLong(params.get(3)));     //4  ESTATUS


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

    public void saveCatalogos(String tbl, SQLiteDatabase db, HashMap<Integer, String> params) {
        db.beginTransaction();
        String sql = "INSERT INTO " + tbl + "(" +
               "id, " +
               "nombre) VALUES(?, ?)";
        SQLiteStatement pInsert = db.compileStatement(sql);
        pInsert.bindLong(1, Long.parseLong(params.get(0)));     //ID       1
        pInsert.bindString(2, params.get(1));                   //NOMBRE   2

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

    public Long saveDirecciones(SQLiteDatabase db, HashMap<Integer, String> params, int tipo) {
        db.beginTransaction();

        String tbl = TBL_DIRECCIONES;
        switch (tipo){
            case 2:
                tbl = TBL_DIRECCIONES_REN;
                break;
            case 3:
                tbl = TBL_DIRECCIONES_AUTO;
                break;
        }

        String sql = "INSERT INTO " + tbl + " (" +
                "tipo_direccion, " +
                "latitud, " +
                "longitud, " +
                "calle, " +
                "num_exterior, " +
                "num_interior, " +
                "lote, " +
                "manzana, " +
                "cp, " +
                "colonia, " +
                "ciudad, " +
                "localidad, " +
                "municipio, " +
                "estado) VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        SQLiteStatement pInsert = db.compileStatement(sql);
        pInsert.bindString(1, params.get(0));                   //TIPO DIRECCION             1
        pInsert.bindString(2, params.get(1));                   //LATITUD                    2
        pInsert.bindString(3, params.get(2));                   //LONGITUD                   3
        pInsert.bindString(4, params.get(3));                   //CALLE                      4
        pInsert.bindString(5, params.get(4));                   //NUM_EXTERIOR               5
        pInsert.bindString(6, params.get(5));                   //NUM_INTERIOR               6
        pInsert.bindString(7, params.get(6));                   //LOTE                       7
        pInsert.bindString(8, params.get(7));                   //MANZANA                    8
        pInsert.bindString(9, params.get(8));                   //CP                         9
        pInsert.bindString(10, params.get(9));                  //COLONIA                   10
        pInsert.bindString(11, params.get(10));                 //CIUDAD                    11
        pInsert.bindString(12, params.get(11));                 //LOCALIDAD                 12
        pInsert.bindString(13, params.get(12));                 //MUNUCIPIO                 13
        pInsert.bindString(14, params.get(13));                 //ESTADO                    14

        Long id = pInsert.executeInsert();

        db.setTransactionSuccessful();
        db.endTransaction();

        return id;
    }

    public void saveRecibos(SQLiteDatabase db, HashMap<Integer, String> params) {
        db.beginTransaction();
        String sql = "INSERT INTO " + TBL_RECIBOS + "(" +
                "prestamo_id, " +
                "asesor_id, " +
                "tipo_recibo, " +
                "tipo_impresion, " +
                "folio, " +
                "monto, " +
                "clave, " +
                "nombre, " +
                "ap_paterno, " +
                "ap_materno, " +
                "fecha_impreso, " +
                "fecha_envio, " +
                "estatus, " +
                "curp) VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        SQLiteStatement pInsert = db.compileStatement(sql);
        pInsert.bindString(1, params.get(0));                   //PRESTAMO_ID         1
        pInsert.bindString(2, params.get(1));                   //ASESOR_ID           2
        pInsert.bindString(3, params.get(2));                   //TIPO_RECIBO         3
        pInsert.bindString(4, params.get(3));                   //TIPO_IMPRESION      4
        pInsert.bindString(5, params.get(4));                   //FOLIO               5
        pInsert.bindString(6, params.get(5));                   //MONTO               6
        pInsert.bindString(7, params.get(6));                   //CLAVE               7
        pInsert.bindString(8, params.get(7));                   //NOMBRE              8
        pInsert.bindString(9, params.get(8));                   //AP_PATERNO          9
        pInsert.bindString(10, params.get(9));                  //AP_MATERNO         10
        pInsert.bindString(11, params.get(10));                 //FECHA_IMPRESO      11
        pInsert.bindString(12, params.get(11));                 //FECHA_ENVIO        12
        pInsert.bindLong(13, Integer.parseInt(params.get(12))); //ESTATUS            13
        pInsert.bindString(14, params.get(13));                 //CURP               14

        pInsert.execute();

        db.setTransactionSuccessful();
        db.endTransaction();
    }

    public void saveGeoRespuestas(SQLiteDatabase db, HashMap<Integer, String> params) {
        db.beginTransaction();
        String tbl = "geo";
        String sql = "INSERT INTO " + tbl + "(" +
                "id_cartera, " +
                "num_solicitud, " +
                "tipo_ficha, " +
                "tipo_geolocalizacion, " +
                "id_integrante, " +
                "nombre, " +
                "direccion, " +
                "latitud, " +
                "longitud, " +
                "direccion_capturada, " +
                "codigo_barras, " +
                "fachada, " +
                "comentario, " +
                "fecha_fin_geo, " +
                "fecha_envio_geo, " +
                "estatus, " +
                "clave) VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        SQLiteStatement pInsert = db.compileStatement(sql);
        pInsert.bindString(1, params.get(0));                           //ID_CARTERA                 1
        pInsert.bindString(2, params.get(1));                           //NUM_SOLICITUD              2
        pInsert.bindLong(3, Integer.parseInt(params.get(2)));           //TIPO_FICHA                 3
        pInsert.bindString(4, params.get(3));                           //TIPO_GEOLOCALIZACION       4
        pInsert.bindString(5, params.get(4));                           //ID_INTEGRANTE              5
        pInsert.bindString(6, params.get(5));                           //NOMBRE                     6
        pInsert.bindString(7, params.get(6));                           //DIRECCION                  7
        pInsert.bindString(8, params.get(7));                           //LATITUD                    8
        pInsert.bindString(9, params.get(8));                           //LONGITUD                   9
        pInsert.bindString(10, params.get(9));                          //DIRECCION_CAPTURADA       10
        pInsert.bindString(11, params.get(10));                         //CODIGO_BARRAS             11
        pInsert.bindString(12, params.get(11));                         //FACHADA                   12
        pInsert.bindString(13, params.get(12));                         //COMENTARIO                13
        pInsert.bindString(14, params.get(13));                         //FECHA_FIN_GEO             14
        pInsert.bindString(15, params.get(14));                         //FECHA_ENVIO_GEO           15
        pInsert.bindLong(16, Integer.parseInt(params.get(15)));         //ESTATUS                   16
        pInsert.bindString(17, params.get(16));                         //CLAVE                     17

        pInsert.execute();

        db.setTransactionSuccessful();
        db.endTransaction();
    }

    public void saveReimpresionVigente(SQLiteDatabase db, HashMap<Integer, String> params) {
        db.beginTransaction();
        String tbl = TBL_REIMPRESION_VIGENTE_T;

        String sql = "INSERT INTO " + tbl + "(" +
                "num_prestamo_id_gestion, " +
                "tipo_reimpresion, " +
                "folio, " +
                "monto, " +
                "clv_cliente, " +
                "asesor_id, " +
                "serie_id, " +
                "create_at, " +
                "sent_at, " +
                "estatus, " +
                "num_prestamo, " +
                "celular) VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        SQLiteStatement pInsert = db.compileStatement(sql);
        pInsert.bindString(1, params.get(0));                   //1  NUM_PRESTAMO_ID_GESTION
        pInsert.bindString(2, params.get(1));                   //2  TIPO_IMPRESION
        pInsert.bindLong(3, Long.parseLong(params.get(2)));     //3  FOLIO
        pInsert.bindString(4, params.get(3));                   //4  MONTO
        pInsert.bindString(5, params.get(4));                   //5  CLV_CLIENTE
        pInsert.bindString(6, params.get(5));                   //6  ASESOR_ID
        pInsert.bindString(7, params.get(6));                   //7  SERIE_ID
        pInsert.bindString(8, params.get(7));                   //8  FECHA IMPRESION
        pInsert.bindString(9, params.get(8));                   //9  FECHA_ENVIO
        pInsert.bindLong(10, Integer.parseInt(params.get(9)));  //10 ESTATUS
        pInsert.bindString(11, params.get(10));                 //11 NUM PRESTAMO
        pInsert.bindString(12, params.get(11));                 //11 CELULAR

        pInsert.execute();

        db.setTransactionSuccessful();
        db.endTransaction();
    }

    public Long saveCancelGestiones(SQLiteDatabase db, HashMap<Integer, String> params) {
        db.beginTransaction();
        String tbl = TBL_CANCELACIONES;

        String sql = "INSERT INTO " + tbl + "(" +
                "id_respuesta, " +
                "id_solicitud, " +
                "tipo_gestion, " +
                "tipo_prestamo, " +
                "comentario_asesor, " +
                "comentario_admin, " +
                "estatus, " +
                "fecha_solicitud, " +
                "fecha_aplicacion) VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?)";
        SQLiteStatement pInsert = db.compileStatement(sql);
        pInsert.bindString(1, params.get(0));                   //1  ID_RESPUESTA
        pInsert.bindString(2, params.get(1));                   //2  ID_SOLICITUD
        pInsert.bindLong(3, Integer.parseInt(params.get(2)));   //3  TIPO_GESTION (1=individual, 2 = grupal)
        pInsert.bindString(4, params.get(3));                   //4  TIPO_PRESTAMO
        pInsert.bindString(5, params.get(4));                   //5  COMENTARIO_ASESOR
        pInsert.bindString(6, params.get(5));                   //6  COMENTARIO_ADMIN
        pInsert.bindString(7, params.get(6));                   //7  ESTATUS
        pInsert.bindString(8, params.get(7));                   //8  FECHA_SOLICITUD
        pInsert.bindString(9, params.get(8));                   //9  FECHA_APLICACION

        Long id = pInsert.executeInsert();

        db.setTransactionSuccessful();
        db.endTransaction();

        return id;
    }

    public void saveCierreDia(SQLiteDatabase db, HashMap<Integer, String> params) {
        db.beginTransaction();
        String tbl = TBL_CIERRE_DIA_T;

        String sql = "INSERT INTO " + tbl + "(" +
                "asesor_id, " +
                "id_respuesta, " +
                "num_prestamo, " +
                "clave_cliente, " +
                "medio_pago, " +
                "monto_depositado, " +
                "evidencia, " +
                "tipo_cierre, " +
                "tipo_prestamo, " +
                "fecha_inicio, " +
                "fecha_fin, " +
                "fecha_envio, " +
                "estatus, " +
                "nombre, " +
                "serial_id) VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        SQLiteStatement pInsert = db.compileStatement(sql);
        pInsert.bindString(1, params.get(0));                   //1  ASESOR_ID
        pInsert.bindString(2, params.get(1));                   //2  ID_RESPUESTA
        pInsert.bindString(3, params.get(2));                   //3  NUM_PRESTAMO
        pInsert.bindString(4, params.get(3));                   //4  CLAVE_CLIENTE
        pInsert.bindString(5, params.get(4));                   //5  MEDIO_PAGO
        pInsert.bindString(6, params.get(5));                   //6  MONTO_DEPOSITADO
        pInsert.bindString(7, params.get(6));                   //7  EVIDENCIA
        pInsert.bindString(8, params.get(7));                   //8  TIPO_CIERRE (1=individual, 2 = grupal)
        pInsert.bindString(9, params.get(8));                   //9  TIPO_PRESTAMO
        pInsert.bindString(10, params.get(9));                  //10 FECHA_INICIO
        pInsert.bindString(11, params.get(10));                 //11 FECHA_FIN
        pInsert.bindString(12, params.get(11));                 //12 FECHA_ENVIO
        pInsert.bindLong(13, Integer.parseInt(params.get(12))); //13 ESTATUS
        pInsert.bindString(14, params.get(13));                 //14 NOMBRE
        pInsert.bindString(15, params.get(14));                 //15 SERIAL ID

        pInsert.execute();

        db.setTransactionSuccessful();
        db.endTransaction();
    }

    public void saveImpresiones(SQLiteDatabase db, HashMap<Integer, String> params) {
        String tbl = TBL_IMPRESIONES_VIGENTE_T;
        db.beginTransaction();
        String sql = "INSERT INTO " + tbl + "(" +
                "num_prestamo_id_gestion, " +
                "asesor_id, " +
                "folio, " +
                "tipo_impresion, " +
                "monto, " +
                "clave_cliente, " +
                "create_at, " +
                "sent_at, " +
                "estatus, " +
                "num_prestamo, " +
                "celular) VALUES(?,?,?,?,?,?,?,?,?,?,?)";
        SQLiteStatement pInsert = db.compileStatement(sql);
        pInsert.bindString(1, params.get(0));                   //NUM PRESTAMO + ID_GESTION    1
        pInsert.bindString(2, params.get(1));                   //ASESOR ID                    2
        pInsert.bindLong(3, Long.parseLong(params.get(2)));     //FOLIO                        3
        pInsert.bindString(4, params.get(3));                   //TIPO IMPRESION               4
        pInsert.bindString(5, params.get(4));                   //MONTO                        5
        pInsert.bindString(6, params.get(5));                   //CLAVE CLIENTE                6
        pInsert.bindString(7, params.get(6));                   //FECHA IMPRESION              7
        pInsert.bindString(8, params.get(7));                   //FECHA ENVIO                  8
        pInsert.bindLong(9, Integer.parseInt(params.get(8)));   //ESTATUS                      9
        pInsert.bindString(10, params.get(9));                  //NUM PRESTAMO                 10
        pInsert.bindString(11, params.get(10));                 //CELULAR                      11

        pInsert.execute();

        db.setTransactionSuccessful();
        db.endTransaction();
    }

    public void saveImpresionesVencida(SQLiteDatabase db, HashMap<Integer, String> params) {
        String tbl = TBL_IMPRESIONES_VENCIDA_T;
        db.beginTransaction();
        String sql = "INSERT INTO " + tbl + "(" +
                "num_prestamo_id_gestion, " +
                "asesor_id, " +
                "folio, " +
                "tipo_impresion, " +
                "monto, " +
                "clave_cliente, " +
                "create_at, " +
                "sent_at, " +
                "estatus, " +
                "num_prestamo, " +
                "celular) VALUES(?,?,?,?,?,?,?,?,?,?,?)";
        SQLiteStatement pInsert = db.compileStatement(sql);
        pInsert.bindString(1, params.get(0));                   //NUM PRESTAMO + ID_GESTION    1
        pInsert.bindString(2, params.get(1));                   //ASESOR ID                    2
        pInsert.bindLong(3, Long.parseLong(params.get(2)));     //FOLIO                        3
        pInsert.bindString(4, params.get(3));                   //TIPO IMPRESION               4
        pInsert.bindString(5, params.get(4));                   //MONTO                        5
        pInsert.bindString(6, params.get(5));                   //CLAVE CLIENTE                6
        pInsert.bindString(7, params.get(6));                   //FECHA IMPRESION              7
        pInsert.bindString(8, params.get(7));                   //FECHA ENVIO                  8
        pInsert.bindLong(9, Integer.parseInt(params.get(8)));   //ESTATUS                      9
        pInsert.bindString(10, params.get(9));                  //NUM PRESTAMO                 10
        pInsert.bindString(11, params.get(10));                 //CELULAR                      11

        pInsert.execute();

        db.setTransactionSuccessful();
        db.endTransaction();
    }

    public void saveTrackerAsesor(SQLiteDatabase db, HashMap<Integer, String> params) {
        Log.e("AQUI", "GUARDANDO TRACKER");
        String tbl = TBL_TRACKER_ASESOR_T;
        db.beginTransaction();
        String sql = "INSERT INTO " + tbl + "(" +
                "asesor_id, " +
                "serie_id, " +
                "nombre, " +
                "latitud, " +
                "longitud, " +
                "bateria, " +
                "created_at, " +
                "sended_at, " +
                "estatus) VALUES(?,?,?,?,?,?,?,?,?)";
        SQLiteStatement pInsert = db.compileStatement(sql);
        pInsert.bindString(1, params.get(0));                   //ASESOR_ID    1
        pInsert.bindString(2, params.get(1));                   //SERIE ID     2
        pInsert.bindString(3, params.get(2));                   //NOMBRE       3
        pInsert.bindString(4, params.get(3));                   //LATITUD      4
        pInsert.bindString(5, params.get(4));                   //LONGITUD     5
        pInsert.bindString(6, params.get(5));                   //BATERIA      6
        pInsert.bindString(7, params.get(6));                   //CREATED AT   7
        pInsert.bindString(8, params.get(7));                   //SENDED AT    8
        pInsert.bindString(9, params.get(8));                   //ESTATUS      9

        pInsert.execute();

        db.setTransactionSuccessful();
        db.endTransaction();
    }

    public void saveResumenGestion(SQLiteDatabase db, HashMap<Integer, String> params) {

        db.beginTransaction();
        String sql = "INSERT INTO " + TBL_RESUMENES_GESTION + "(" +
                "id_respuesta, " +
                "nombre_resumen, " +
                "nombre_cliente, " +
                "tipo_gestion) VALUES(?,?,?,?)";
        SQLiteStatement pInsert = db.compileStatement(sql);
        pInsert.bindString(1, params.get(0));                   //ID_RESPUESTA    1
        pInsert.bindString(2, params.get(1));                   //NOMBRE_RESUMEN  2
        pInsert.bindString(3, params.get(2));                   //NOMBRE_CLIENTE  3
        pInsert.bindString(4, params.get(3));                   //TIPO_GESTION    4

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

    public Long saveSolicitudes(SQLiteDatabase db, HashMap<Integer, String> params, int tipo) {
        db.beginTransaction();

        String tbl = (tipo == 1)?TBL_SOLICITUDES:TBL_SOLICITUDES_REN;

        String sql = "";
        SQLiteStatement pInsert;

        if(tipo != 1)
        {
            sql = "INSERT INTO " + tbl + " (" +
                    "vol_solicitud, " +
                    "usuario_id, " +
                    "tipo_solicitud, " +
                    "id_originacion, " +
                    "nombre, " +
                    "fecha_inicio, " +
                    "fecha_termino, " +
                    "fecha_envio, " +
                    "fecha_dispositivo, " +
                    "fecha_guardado, " +
                    "estatus," +
                    "prestamo_id) " +
                    "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
            pInsert = db.compileStatement(sql);
            pInsert.bindString(1, params.get(0));                       //VOL SOLICITUD
            pInsert.bindLong(2, Long.parseLong(params.get(1)));         //USUARIO ID
            pInsert.bindLong(3, Long.parseLong(params.get(2)));         //TIPO SOLICITUD
            pInsert.bindLong(4, Long.parseLong(params.get(3)));         //ID ORIGINACION
            pInsert.bindString(5, params.get(4));                       //NOMBRE
            pInsert.bindString(6, params.get(5));                       //FECHA INICIO
            pInsert.bindString(7, params.get(6));                       //FECHA TERMINO
            pInsert.bindString(8, params.get(7));                       //FECHA ENVIO
            pInsert.bindString(9, params.get(8));                       //FECHA DISPOSITIVO
            pInsert.bindString(10, params.get(9));                      //FECHA CREADO
            pInsert.bindLong(11, Long.parseLong(params.get(10)));       //ESTATUS
            pInsert.bindLong(12, Long.parseLong(params.get(11)));       //PRESTAMO ID
        }
        else
        {
            sql = "INSERT INTO " + tbl + " (" +
                    "vol_solicitud, " +
                    "usuario_id, " +
                    "tipo_solicitud, " +
                    "id_originacion, " +
                    "nombre, " +
                    "fecha_inicio, " +
                    "fecha_termino, " +
                    "fecha_envio, " +
                    "fecha_dispositivo, " +
                    "fecha_guardado, " +
                    "estatus)" +
                    "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
            pInsert = db.compileStatement(sql);
            pInsert.bindString(1, params.get(0));                       //VOL SOLICITUD
            pInsert.bindLong(2, Long.parseLong(params.get(1)));         //USUARIO ID
            pInsert.bindLong(3, Long.parseLong(params.get(2)));         //TIPO SOLICITUD
            pInsert.bindLong(4, Long.parseLong(params.get(3)));         //ID ORIGINACION
            pInsert.bindString(5, params.get(4));                       //NOMBRE
            pInsert.bindString(6, params.get(5));                       //FECHA INICIO
            pInsert.bindString(7, params.get(6));                       //FECHA TERMINO
            pInsert.bindString(8, params.get(7));                       //FECHA ENVIO
            pInsert.bindString(9, params.get(8));                       //FECHA DISPOSITIVO
            pInsert.bindString(10, params.get(9));                      //FECHA CREADO
            pInsert.bindLong(11, Long.parseLong(params.get(10)));       //ESTATUS
        }

        Long id = pInsert.executeInsert();

        db.setTransactionSuccessful();
        db.endTransaction();
        Log.e("Registra", "Solicitud");
        return id;
    }

    public Long saveSolicitudesAuto(SQLiteDatabase db, HashMap<Integer, String> params) {
        db.beginTransaction();

        String sql = "INSERT INTO " + TBL_SOLICITUDES_AUTO + " (" +
                "solicitud, " +
                "asesor, " +
                "tipo_solicitud, " +
                "id_originacion, " +
                "nombre, " +
                "fecha_envio, " +
                "estatus) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?)";
        SQLiteStatement pInsert = db.compileStatement(sql);
        pInsert.bindString(1, params.get(0));         //SOLICITUD INDIVIDUAL | GRUPAL
        pInsert.bindString(2, params.get(1));         //NOMBRE ASESOR
        pInsert.bindString(3, params.get(2));         //TIPO_SOLICITUD RENOVACION | ORIGINACIOM
        pInsert.bindLong(4, Long.parseLong(params.get(3)));  //ID ORIGINACION
        pInsert.bindString(5, params.get(4));                //NOMBRE CLIENTE
        pInsert.bindString(6, params.get(5));                //FECHA GUARDADO
        pInsert.bindLong(7, Long.parseLong(params.get(6))); //ESTATUS
        Long id = pInsert.executeInsert();

        db.setTransactionSuccessful();
        db.endTransaction();
        Log.e("Registra", "Solicitud");
        return id;
    }

    public Long saveArqueoCaja(SQLiteDatabase db, HashMap<Integer, String> params) {
        db.beginTransaction();
        String tbl = TBL_ARQUEO_CAJA_T;

        String sql = "INSERT INTO " + tbl + " (" +
                "id_gestion, " +
                "mil, " +
                "quinientos, " +
                "doscientos, " +
                "cien, " +
                "cincuenta, " +
                "veinte, " +
                "diez, " +
                "cinco, " +
                "dos, " +
                "peso, " +
                "c_cincuenta, " +
                "c_veinte, " +
                "c_diez) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        SQLiteStatement pInsert = db.compileStatement(sql);
        pInsert.bindString(1, params.get(0));                       //ID GESTION
        pInsert.bindString(2, params.get(1));                       //MIL
        pInsert.bindString(3, params.get(2));                       //QUINIENTOS
        pInsert.bindString(4, params.get(3));                       //DOSCIENTOS
        pInsert.bindString(5, params.get(4));                       //CIEN
        pInsert.bindString(6, params.get(5));                       //CINCUENTA
        pInsert.bindString(7, params.get(6));                       //VEINTE
        pInsert.bindString(8, params.get(7));                       //DIEZ
        pInsert.bindString(9, params.get(8));                       //CINCO
        pInsert.bindString(10, params.get(9));                      //DOS
        pInsert.bindString(11, params.get(10));                     //PESO
        pInsert.bindString(12, params.get(11));                     //C CINCUENTA
        pInsert.bindString(13, params.get(12));                     //C VEINTE
        pInsert.bindString(14, params.get(13));                     //C DIEZ
        Long id = pInsert.executeInsert();

        db.setTransactionSuccessful();
        db.endTransaction();
        return id;
    }

    public void saveDatosCredito(SQLiteDatabase db, HashMap<Integer, String> params){
        db.beginTransaction();
        String sql = "INSERT INTO " + TBL_CREDITO_IND + " (" +
                "id_solicitud, " +
                "plazo, " +
                "periodicidad, " +
                "fecha_desembolso, " +
                "dia_desembolso, " +
                "hora_visita, " +
                "monto_prestamo, " +
                "destino, " +
                "clasificacion_riesgo, " +
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
        Log.e("Registro", "Credito Ind");
    }

    public void saveDatosCreditoRen(SQLiteDatabase db, HashMap<Integer, String> params){
        db.beginTransaction();
        String sql = "INSERT INTO " + TBL_CREDITO_IND_REN + " (" +
                "id_solicitud, " +
                "plazo, " +
                "periodicidad, " +
                "fecha_desembolso, " +
                "dia_desembolso, " +
                "hora_visita, " +
                "monto_prestamo, " +
                "ciclo, " +
                "credito_anterior, " +
                "comportamiento_pago, " +
                "num_cliente, " +
                "observaciones, " +
                "destino, " +
                "clasificacion_riesgo, " +
                "estatus_completado) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
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
        pInsert.bindLong(15, Long.parseLong(params.get(14)));
        pInsert.executeInsert();

        db.setTransactionSuccessful();
        db.endTransaction();
        Log.e("Registro", "Credito Ind");
    }

    public void saveDatosCreditoAuto(SQLiteDatabase db, HashMap<Integer, String> params){
        db.beginTransaction();
        String sql = "INSERT INTO " + TBL_CREDITO_IND_AUTO + " (" +
                "id_solicitud, " +
                "plazo, " +
                "periodicidad, " +
                "fecha_desembolso, " +
                "dia_desembolso, " +
                "hora_visita, " +
                "monto_prestamo, " +
                "ciclo, " +
                "credito_anterior, " +
                "comportamiento_pago, " +
                "num_cliente, " +
                "observaciones, " +
                "destino, " +
                "clasificacion_riesgo, " +
                "estatus_completado, " +
                "monto_autorizado) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
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
        pInsert.bindLong(15, Long.parseLong(params.get(14)));
        pInsert.bindString(16, params.get(15));
        pInsert.executeInsert();

        db.setTransactionSuccessful();
        db.endTransaction();
        Log.e("Registro", "Credito Ind");
    }

    public Long saveDatosPersonales (SQLiteDatabase db, HashMap<Integer, String> params, int tipo){
        db.beginTransaction();

        String tbl = TBL_CLIENTE_IND;
        switch (tipo){
            case 2:
                tbl = TBL_CLIENTE_IND_REN;
                break;
            case 3:
                tbl = TBL_CLIENTE_IND_AUTO;
                break;
        }

        String sql = "INSERT INTO " + tbl + " (" +
                "id_solicitud, " + "nombre, " + "paterno, " + "materno, " +
                "fecha_nacimiento, " + "edad, " + "genero, " +"estado_nacimiento, " +
                "rfc, " + "curp, " + "curp_digito_veri, " +
                "ocupacion, " + "actividad_economica, " + "tipo_identificacion, " +
                "no_identificacion, " + "nivel_estudio, " + "estado_civil, " + "bienes, " +
                "tipo_vivienda, " + "parentesco, " + "otro_tipo_vivienda, " + "direccion_id, " +
                "tel_casa, " + "tel_celular, " + "tel_mensajes, " +
                "tel_trabajo, " +  "tiempo_vivir_sitio, " +  "dependientes, " +
                "medio_contacto, " + "estado_cuenta, " +  "email, " +  "foto_fachada, " +  "ref_domiciliaria, " +
                "firma, " +  "estatus_rechazo, " +  "comentario_rechazo, " + "estatus_completado) " +
                "VALUES (?,?,?,?,?,?,?,?,?,?, ?,?,?,?,?,?,?,?,?,?, ?,?,?,?,?,?,?,?,?,?, ?,?,?,?,?,?,?)";
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
        pInsert.bindLong(18, Long.parseLong(params.get(17)));
        pInsert.bindString(19, params.get(18));
        pInsert.bindString(20, params.get(19));
        pInsert.bindString(21, params.get(20));
        pInsert.bindString(22, params.get(21));
        pInsert.bindString(23, params.get(22));
        pInsert.bindString(24, params.get(23));
        pInsert.bindString(25, params.get(24));
        pInsert.bindString(26, params.get(25));
        pInsert.bindLong(27, Long.parseLong(params.get(26)));
        pInsert.bindString(28, params.get(27));
        pInsert.bindString(29, params.get(28));
        pInsert.bindString(30, params.get(29));
        pInsert.bindString(31, params.get(30));
        pInsert.bindString(32, params.get(31));
        pInsert.bindString(33, params.get(32));
        pInsert.bindString(34, params.get(33));
        pInsert.bindLong(35, Long.parseLong(params.get(34)));
        pInsert.bindString(36, params.get(35));
        pInsert.bindLong(37, Long.parseLong(params.get(36)));
        Long id = pInsert.executeInsert();

        db.setTransactionSuccessful();
        db.endTransaction();

        Log.e("Registra", "Datos del cliente");
        return id;
    }

    public void saveDatosConyuge(SQLiteDatabase db, HashMap<Integer, String> params, int tipo){
        db.beginTransaction();
        String tbl = TBL_CONYUGE_IND;
        switch (tipo){
            case 2:
                tbl = TBL_CONYUGE_IND_REN;
                break;
            case 3:
                tbl = TBL_CONYUGE_IND_AUTO;
                break;
        }
        String sql = "INSERT INTO " + tbl + " (" +
                "id_solicitud, " +
                "nombre, " +
                "paterno, " +
                "materno, " +
                "nacionalidad, " +
                "ocupacion, " +
                "direccion_id, " +
                "ing_mensual, " +
                "gasto_mensual, " +
                "tel_casa, " +
                "tel_celular, " +
                "estatus_completado) " +
                "VALUES (?,?,?,?,?,?,?,?,?,?,?,?)";
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
        pInsert.bindLong(12, Long.parseLong(params.get(11)));
        pInsert.executeInsert();

        db.setTransactionSuccessful();
        db.endTransaction();
        Log.e("Registra", "Conyuge");
    }

    public void saveCodigosOxxo(SQLiteDatabase db, HashMap<Integer, String> params){
        db.beginTransaction();
        String sql = "INSERT INTO " + TBL_CODIGOS_OXXO + " (" +
                "id, " +
                "id_asesor, " +
                "num_prestamo, " +
                "fecha_amortiz, " +
                "monto_amortiz, " +
                "nombre_pdf, " +
                "created_at, " +
                "fecha_vencimiento) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        SQLiteStatement pInsert = db.compileStatement(sql);
        pInsert.bindString(1, params.get(0));
        pInsert.bindString(2, params.get(1));
        pInsert.bindString(3, params.get(2));
        pInsert.bindString(4, params.get(3));
        pInsert.bindString(5, params.get(4));
        pInsert.bindString(6, params.get(5));
        pInsert.bindString(7, params.get(6));
        pInsert.bindString(8, params.get(7));
        pInsert.executeInsert();

        db.setTransactionSuccessful();
        db.endTransaction();
    }

    public void saveDatosEconomicos(SQLiteDatabase db, HashMap<Integer, String> params, int tipo){
        db.beginTransaction();
        String tbl = TBL_ECONOMICOS_IND;
        switch (tipo){
            case 2:
                tbl = TBL_ECONOMICOS_IND_REN;
                break;
            case 3:
                tbl = TBL_ECONOMICOS_IND_AUTO;
                break;
        }
        String sql = "INSERT INTO " + tbl + " (" +
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
        Log.e("Registra", "Datos economicos");
    }

    public void saveDatosNegocio (SQLiteDatabase db, HashMap<Integer, String> params, int tipo){
        db.beginTransaction();
        String tbl = TBL_NEGOCIO_IND;
        switch (tipo){
            case 2:
                tbl = TBL_NEGOCIO_IND_REN;
                break;
            case 3:
                tbl = TBL_NEGOCIO_IND_AUTO;
                break;
        }

        String sql = "INSERT INTO " + tbl + " (" +
                "id_solicitud, " + "nombre, " + "direccion_id, " + "ocupacion, " + "actividad_economica, " +
                "destino_credito, " + "otro_destino, " + "antiguedad, "  + "ing_mensual, " + "ing_otros, " +
                "gasto_semanal, " + "gasto_agua, " + "gasto_luz, " + "gasto_telefono, " +
                "gasto_renta, " + "gasto_otros, " + "capacidad_pago, " + "medio_pago, " +
                "otro_medio_pago, " + "monto_maximo, " + "num_operacion_mensuales, " +
                "num_operacion_efectivo, " + "dias_venta, " +
                "foto_fachada, " + "ref_domiciliaria, " + "estatus_completado, " + "comentario_rechazo) " +
                "VALUES (?,?,?,?,?,?,?,?,?,?, ?,?,?,?,?,?,?,?,?,?, ?,?,?,?,?,?,?)";
        SQLiteStatement pInsert = db.compileStatement(sql);
        pInsert.bindLong(1, Long.parseLong(params.get(0)));
        pInsert.bindString(2, params.get(1));
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
        pInsert.bindString(17, params.get(16));
        pInsert.bindString(18, params.get(17));
        pInsert.bindString(19, params.get(18));
        pInsert.bindString(20, params.get(19));
        pInsert.bindLong(21, Long.parseLong(params.get(20)));
        pInsert.bindLong(22, Long.parseLong(params.get(21)));
        pInsert.bindString(23, params.get(22));
        pInsert.bindString(24, params.get(23));
        pInsert.bindString(25, params.get(24));
        pInsert.bindLong(26, Long.parseLong(params.get(25)));
        pInsert.bindString(27, params.get(26));
        pInsert.executeInsert();

        db.setTransactionSuccessful();
        db.endTransaction();
        Log.e("Registra", "Negocio");
    }

    public void saveDatosAval (SQLiteDatabase db, HashMap<Integer, String> params, int tipo){
        db.beginTransaction();
        String tbl = TBL_AVAL_IND;
        switch (tipo){
            case 2:
                tbl = TBL_AVAL_IND_REN;
                break;
            case 3:
                tbl = TBL_AVAL_IND_AUTO;
                break;
        }
        String sql = "INSERT INTO " + tbl + " (" +
                "id_solicitud, " +
                "nombre, " +
                "paterno, " +
                "materno, " +
                "fecha_nacimiento, " +
                "edad, " +
                "genero, " +
                "estado_nacimiento, " +
                "rfc, " +
                "curp, " +
                "curp_digito, " +
                "parentesco_cliente, " +
                "tipo_identificacion, " +
                "no_identificacion, " +
                "ocupacion, " +
                "actividad_economica, " +
                "destino_credito, " +
                "otro_destino, " +
                "direccion_id, " +
                "tipo_vivienda, " +
                "nombre_titular, " +
                "parentesco, " +
                "caracteristicas_domicilio, " +
                "antigueda, " +
                "tiene_negocio, " +
                "nombre_negocio, " +
                "ing_mensual, " +
                "ing_otros, " +
                "gasto_semanal, " +
                "gasto_agua, " +
                "gasto_luz, " +
                "gasto_telefono, " +
                "gasto_renta, " +
                "gasto_otros, " +
                "capacidad_pagos, "+
                "medio_pago, "+
                "otro_medio_pago, "+
                "monto_maximo, " +
                "horario_localizacion, " +
                "activos_observables, " +
                "tel_casa, " +
                "tel_celular, " +
                "tel_mensajes, " +
                "tel_trabajo, " +
                "email, " +
                "foto_fachada, " +
                "ref_domiciliaria, " +
                "firma, " +
                "estatus_rechazo, " +
                "comentario_rechazo, "  +
                "estatus_completado) " +
                "VALUES (?,?,?,?,?,?,?,?,?,?, ?,?,?,?,?,?,?,?,?,?, ?,?,?,?,?,?,?,?,?,?, ?,?,?,?,?,?,?,?,?,?, ?,?,?,?,?,?,?,?,?,?, ?)";
        SQLiteStatement pInsert = db.compileStatement(sql);
        pInsert.bindLong(1, Long.parseLong(params.get(0)));     //ID SOLICITUD
        pInsert.bindString(2, params.get(1));                   //NOMBRE
        pInsert.bindString(3, params.get(2));                   //PATERNO
        pInsert.bindString(4, params.get(3));                   //MATERNO
        pInsert.bindString(5, params.get(4));                   //FECHA NACIMIENTO
        pInsert.bindString(6, params.get(5));                   //EDAD
        pInsert.bindLong(7, Long.parseLong(params.get(6)));     //GENERO
        pInsert.bindString(8, params.get(7));                   //ESTADO NACIMIENTO
        pInsert.bindString(9, params.get(8));                   //RFC
        pInsert.bindString(10, params.get(9));                  //CURP
        pInsert.bindString(11, params.get(10));                 //CURP DIGITO
        pInsert.bindString(12, params.get(11));                 //PARENTESCO
        pInsert.bindString(13, params.get(12));                 //TIPO IDENTIFICACION
        pInsert.bindString(14, params.get(13));                 //NO IDENTIFICACION
        pInsert.bindString(15, params.get(14));                 //OCUPACION
        pInsert.bindString(16, params.get(15));                 //ACTIVIDAD ECONOMICA
        pInsert.bindString(17, params.get(16));                 //DESTINO CREDITO
        pInsert.bindString(18, params.get(17));                 //OTRO DESTINO CREDITO
        pInsert.bindString(19, params.get(18));                 //DIRECCION ID
        pInsert.bindString(20, params.get(19));                 //TIPO VIVIENDA
        pInsert.bindString(21, params.get(20));                 //NOMBRE TITULAR
        pInsert.bindString(22, params.get(21));                 //PARENTESCO
        pInsert.bindString(23, params.get(22));                 //CARACTERISTICAS DOMICILIO
        pInsert.bindLong(24, Long.parseLong(params.get(23)));   //ANTIGUEDAD
        pInsert.bindLong(25, Long.parseLong(params.get(24)));   //TIENE NEGOCIO
        pInsert.bindString(26, params.get(25));                 //NOMBRE DEL NEGOCIO
        pInsert.bindString(27, params.get(26));                 //ING MENSUAL
        pInsert.bindString(28, params.get(27));                 //ING OTROS
        pInsert.bindString(29, params.get(28));                 //GASTO MENSUAL
        pInsert.bindString(30, params.get(29));                 //GASTO AGUA
        pInsert.bindString(31, params.get(30));                 //GASTO LUZ
        pInsert.bindString(32, params.get(31));                 //GASTO TELEFONO
        pInsert.bindString(33, params.get(32));                 //GASTO RENTA
        pInsert.bindString(34, params.get(33));                 //GASTO OTROS
        pInsert.bindString(35, params.get(34));                 //CAPACIDAD PAGOS
        pInsert.bindString(36, params.get(35));                 //MEDIOS PAGO
        pInsert.bindString(37, params.get(36));                 //OTROS MEDIO PAGO
        pInsert.bindString(38, params.get(37));                 //MONTO MAXIMO
        pInsert.bindString(39, params.get(38));                 //HORARIO LOCALIZACION
        pInsert.bindString(40, params.get(39));                 //ACTIVOS OBSERVABLES
        pInsert.bindString(41, params.get(40));                 //TEL CASA
        pInsert.bindString(42, params.get(41));                 //TEL CELULAR
        pInsert.bindString(43, params.get(42));                 //TEL MENSAJES
        pInsert.bindString(44, params.get(43));                 //TEL TRABAJO
        pInsert.bindString(45, params.get(44));                 //EMAIL
        pInsert.bindString(46, params.get(45));                 //FOTO FACHADA
        pInsert.bindString(47, params.get(46));                 //REF DOMICILIARIA
        pInsert.bindString(48, params.get(47));                 //FIRMA
        pInsert.bindLong(49, Long.parseLong(params.get(48)));   //ESTATUS RECHADO
        pInsert.bindString(50, params.get(49));                 //COMENTARIO RECHAZO
        pInsert.bindLong(51, Long.parseLong(params.get(50)));   //ESTATUS COMPLETADO
        pInsert.executeInsert();

        db.setTransactionSuccessful();
        db.endTransaction();
        Log.e("Registra", "Aval");
    }

    public void saveReferencia (SQLiteDatabase db, HashMap<Integer, String> params, int tipo){
        db.beginTransaction();
        String tbl = TBL_REFERENCIA_IND;
        switch (tipo){
            case 2:
                tbl = TBL_REFERENCIA_IND_REN;
                break;
            case 3:
                tbl = TBL_REFERENCIA_IND_AUTO;
                break;
        }

        String sql = "INSERT INTO " + tbl + " (" +
                "id_solicitud, " +
                "nombre, " +
                "paterno, " +
                "materno, " +
                "fecha_nacimiento, " +
                "direccion_id, " +
                "tel_celular, " +
                "estatus_completado, " +
                "comentario_rechazo) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
        SQLiteStatement pInsert = db.compileStatement(sql);
        pInsert.bindLong(1, Long.parseLong(params.get(0)));
        pInsert.bindString(2, params.get(1));
        pInsert.bindString(3, params.get(2));
        pInsert.bindString(4, params.get(3));
        pInsert.bindString(5, params.get(4));
        pInsert.bindString(6, params.get(5));
        pInsert.bindString(7, params.get(6));
        pInsert.bindLong(8, Long.parseLong(params.get(7)));
        pInsert.bindString(9, params.get(8));
        pInsert.executeInsert();

        db.setTransactionSuccessful();
        db.endTransaction();
        Log.e("Registra", "Referencia");
    }

    public void saveCroquisInd (SQLiteDatabase db, HashMap<Integer, String> params, int tipo){
        db.beginTransaction();
        String tbl = TBL_CROQUIS_IND;
        switch (tipo){
            case 2:
                tbl = TBL_CROQUIS_IND_REN;
                break;
            case 3:
                tbl = TBL_CROQUIS_IND_AUTO;
                break;
        }
        String sql = "INSERT INTO " + tbl + " (" +
                "id_solicitud, " +
                "calle_principal, " +
                "lateral_uno, " +
                "lateral_dos, " +
                "calle_trasera, " +
                "referencias, " +
                "estatus_completado, " +
                "comentario_rechazo) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        SQLiteStatement pInsert = db.compileStatement(sql);
        pInsert.bindLong(1, Long.parseLong(params.get(0)));
        pInsert.bindString(2, params.get(1));
        pInsert.bindString(3, params.get(2));
        pInsert.bindString(4, params.get(3));
        pInsert.bindString(5, params.get(4));
        pInsert.bindString(6, params.get(5));
        pInsert.bindLong(7, Long.parseLong(params.get(6)));
        pInsert.bindString(8, params.get(7));
        pInsert.executeInsert();

        db.setTransactionSuccessful();
        db.endTransaction();
        Log.e("Registra", "Croquis");
    }

    public void saveCroquisGpo (SQLiteDatabase db, HashMap<Integer, String> params, int tipo){
        db.beginTransaction();
        String tbl = TBL_CROQUIS_GPO;
        switch (tipo){
            case 2:
                tbl = TBL_CROQUIS_GPO_REN;
                break;
            case 3:
                tbl = TBL_CROQUIS_GPO_AUTO;
                break;
        }
        String sql = "INSERT INTO " + tbl + " (" +
                "id_integrante, " +
                "calle_principal, " +
                "lateral_uno, " +
                "lateral_dos, " +
                "calle_trasera, " +
                "referencias, " +
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
        Log.e("Registra", "Croquis");
    }

    public void savePoliticasInd (SQLiteDatabase db, HashMap<Integer, String> params, int tipo){
        db.beginTransaction();
        String tbl = TBL_POLITICAS_PLD_IND;
        switch (tipo){
            case 2:
                tbl = TBL_POLITICAS_PLD_IND_REN;
                break;
            case 3:
                tbl = TBL_POLITICAS_PLD_IND_AUTO;
                break;
        }
        String sql = "INSERT INTO " + tbl + " (" +
                "id_solicitud, " +
                "propietario_real, " +
                "proveedor_recursos, " +
                "persona_politica, " +
                "estatus_completado) " +
                "VALUES (?, ?, ?, ?, ?)";
        SQLiteStatement pInsert = db.compileStatement(sql);
        pInsert.bindLong(1, Long.parseLong(params.get(0)));
        pInsert.bindLong(2, Long.parseLong(params.get(1)));
        pInsert.bindLong(3, Long.parseLong(params.get(2)));
        pInsert.bindLong(4, Long.parseLong(params.get(3)));
        pInsert.bindLong(5, Long.parseLong(params.get(4)));
        pInsert.executeInsert();

        db.setTransactionSuccessful();
        db.endTransaction();
        Log.e("Registra", "Politicas");
    }

    public void savePoliticasIntegrante (SQLiteDatabase db, HashMap<Integer, String> params, int tipo){
        db.beginTransaction();
        String tbl = TBL_POLITICAS_PLD_INTEGRANTE;
        switch (tipo){
            case 2:
                tbl = TBL_POLITICAS_PLD_INTEGRANTE_REN;
                break;
            case 3:
                tbl = TBL_POLITICAS_PLD_INTEGRANTE_AUTO;
                break;
        }
        String sql = "INSERT INTO " + tbl + " (" +
                "id_integrante, " +
                "propietario_real, " +
                "proveedor_recursos, " +
                "persona_politica, " +
                "estatus_completado) " +
                "VALUES (?, ?, ?, ?, ?)";
        SQLiteStatement pInsert = db.compileStatement(sql);
        pInsert.bindLong(1, Long.parseLong(params.get(0)));
        pInsert.bindLong(2, Long.parseLong(params.get(1)));
        pInsert.bindLong(3, Long.parseLong(params.get(2)));
        pInsert.bindLong(4, Long.parseLong(params.get(3)));
        pInsert.bindLong(5, Long.parseLong(params.get(4)));
        pInsert.executeInsert();

        db.setTransactionSuccessful();
        db.endTransaction();
    }

    public Long saveDatosCreditoGpo(SQLiteDatabase db, HashMap<Integer, String> params, int tipo){
        db.beginTransaction();
        String tbl = (tipo == 1)?TBL_CREDITO_GPO:TBL_CREDITO_GPO_REN;
        String sql = "INSERT INTO " + tbl + " (" +
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
        pInsert.bindLong(1, Long.parseLong(params.get(0)));         //ID_SOLICITUD
        pInsert.bindString(2, params.get(1));                       //NOMBRE GRUPO
        pInsert.bindString(3, params.get(2));                       //PLAZO
        pInsert.bindString(4, params.get(3));                       //PERIODICIDAD
        pInsert.bindString(5, params.get(4));                       //FECHA_DESEMBOLSO
        pInsert.bindString(6, params.get(5));                       //DIA_DESEMBOLSO
        pInsert.bindString(7, params.get(6));                       //HORA_VISITA
        pInsert.bindLong(8, Long.parseLong(params.get(7)));         //ESTATUS
        Long id = pInsert.executeInsert();

        db.setTransactionSuccessful();
        db.endTransaction();
        return id;
    }

    public Long saveDatosCreditoGpoRen(SQLiteDatabase db, HashMap<Integer, String> params, Integer tipo){
        db.beginTransaction();
        String tbl = (tipo == 1)?TBL_CREDITO_GPO_REN:TBL_CREDITO_GPO_AUTO;
        String sql = "INSERT INTO " + tbl + " (" +
                "id_solicitud, " +
                "nombre_grupo, " +
                "plazo, " +
                "periodicidad, " +
                "fecha_desembolso, " +
                "dia_desembolso, " +
                "hora_visita, " +
                "estatus_completado, " +
                "observaciones, " +
                "ciclo, " +
                "grupo_id) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        SQLiteStatement pInsert = db.compileStatement(sql);
        pInsert.bindLong(1, Long.parseLong(params.get(0)));         //ID_SOLICITUD
        pInsert.bindString(2, params.get(1));                       //NOMBRE GRUPO
        pInsert.bindString(3, params.get(2));                       //PLAZO
        pInsert.bindString(4, params.get(3));                       //PERIODICIDAD
        pInsert.bindString(5, params.get(4));                       //FECHA_DESEMBOLSO
        pInsert.bindString(6, params.get(5));                       //DIA_DESEMBOLSO
        pInsert.bindString(7, params.get(6));                       //HORA_VISITA
        pInsert.bindLong(8, Long.parseLong(params.get(7)));         //ESTATUS
        pInsert.bindString(9, params.get(8));                       //OBSERVACIONES
        pInsert.bindString(10, params.get(9));                      //CICLO
        pInsert.bindString(11, params.get(10));                     //GRUPO_ID
        Long id = pInsert.executeInsert();

        db.setTransactionSuccessful();
        db.endTransaction();
        return id;
    }

    public Long saveIntegrantesGpo (SQLiteDatabase db, HashMap<Integer, String> params, int tipo){
        db.beginTransaction();
        String tbl = TBL_INTEGRANTES_GPO;
        switch (tipo){
            case 2:
                tbl = TBL_INTEGRANTES_GPO_REN;
                break;
            case 3:
                tbl = TBL_INTEGRANTES_GPO_AUTO;
                break;
        }
        String sql = "INSERT INTO " + tbl + " (" +
                "id_credito, " +
                "cargo, " +
                "nombre, " +
                "paterno, " +
                "materno, " +
                "fecha_nacimiento, " +
                "edad, " +
                "genero, " +
                "estado_nacimiento, " +
                "rfc, " +
                "curp, " +
                "curp_digito_veri, " +
                "tipo_identificacion, " +
                "no_identificacion, " +
                "nivel_estudio, " +
                "ocupacion, " +
                "estado_civil, " +
                "bienes, " +
                "estatus_rechazo, " +
                "comentario_rechazo, " +
                "estatus_completado, " +
                "id_solicitud_integrante) " +
                "VALUES (?,?,?,?,?,?,?,?,?,?, ?,?,?,?,?,?,?,?,?,?, ?,?)";

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
        pInsert.bindString(17, params.get(16));
        pInsert.bindLong(18, Long.parseLong(params.get(17)));
        pInsert.bindLong(19, Long.parseLong(params.get(18)));
        pInsert.bindString(20, params.get(19));
        pInsert.bindLong(21, Long.parseLong(params.get(20)));
        pInsert.bindLong(22, Long.parseLong(params.get(21)));

        long id = pInsert.executeInsert();

        db.setTransactionSuccessful();
        db.endTransaction();
        return id;
    }

    public Long saveIntegrantesGpoRen (SQLiteDatabase db, HashMap<Integer, String> params){
        db.beginTransaction();
        String sql = "INSERT INTO " + TBL_INTEGRANTES_GPO_REN + " (" +
                "id_credito, " +
                "cargo, " +
                "nombre, " +
                "paterno, " +
                "materno, " +
                "fecha_nacimiento, " +
                "edad, " +
                "genero, " +
                "estado_nacimiento, " +
                "rfc, " +
                "curp, " +
                "curp_digito_veri, " +
                "tipo_identificacion, " +
                "no_identificacion, " +
                "nivel_estudio, " +
                "ocupacion, " +
                "estado_civil, " +
                "bienes, " +
                "estatus_rechazo, " +
                "comentario_rechazo, " +
                "estatus_completado, " +
                "id_solicitud_integrante, " +
                "is_nuevo, " +
                "cliente_id," +
                "ciclo," +
                "monto_prestamo_anterior" +
                ") " +
                "VALUES (?,?,?,?,?,?,?,?,?,?, ?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";

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
        pInsert.bindString(17, params.get(16));
        pInsert.bindLong(18, Long.parseLong(params.get(17)));
        pInsert.bindLong(19, Long.parseLong(params.get(18)));
        pInsert.bindString(20, params.get(19));
        pInsert.bindLong(21, Long.parseLong(params.get(20)));
        pInsert.bindLong(22, Long.parseLong(params.get(21)));
        pInsert.bindLong(23, Long.parseLong(params.get(22)));
        pInsert.bindString(24, params.get(23));
        pInsert.bindLong(25, Integer.parseInt(params.get(24)));
        pInsert.bindString(26, params.get(25));

        long id = pInsert.executeInsert();

        db.setTransactionSuccessful();
        db.endTransaction();
        return id;
    }

    public void saveCargoIntegranteGpoRen (SQLiteDatabase db, Integer id_cargo, Integer id_integrante){
        ContentValues cv = new ContentValues();

        cv.put("cargo", id_cargo);

        db.update(TBL_INTEGRANTES_GPO_REN, cv, "id = ?", new String[]{String.valueOf(id_integrante)});
    }

    public void saveDatosTelefonicos (SQLiteDatabase db, HashMap<Integer, String> params, int tipo){
        db.beginTransaction();
        String tbl = TBL_TELEFONOS_INTEGRANTE;
        switch (tipo){
            case 2:
                tbl = TBL_TELEFONOS_INTEGRANTE_REN;
                break;
            case 3:
                tbl = TBL_TELEFONOS_INTEGRANTE_AUTO;
                break;
        }
        String sql = "INSERT INTO " + tbl + " (" +
                "id_integrante, " +
                "tel_casa, " +
                "tel_celular, " +
                "tel_mensaje, " +
                "tel_trabajo, " +
                "estatus_completado) " +
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

    public void saveDatosDomicilio (SQLiteDatabase db, HashMap<Integer, String> params, int tipo){
        db.beginTransaction();
        String tbl = TBL_DOMICILIO_INTEGRANTE;
        switch (tipo){
            case 2:
                tbl = TBL_DOMICILIO_INTEGRANTE_REN;
                break;
            case 3:
                tbl = TBL_DOMICILIO_INTEGRANTE_AUTO;
                break;
        }
        String sql = "INSERT INTO " + tbl + " (" +
                "id_integrante, " +
                "latitud, " +
                "longitud, " +
                "calle, " +
                "no_exterior, " +
                "no_interior, " +
                "manzana, " +
                "lote, " +
                "cp, " +
                "colonia, " +
                "ciudad, " +
                "localidad, " +
                "municipio, " +
                "estado, " +
                "tipo_vivienda, " +
                "parentesco, " +
                "otro_tipo_vivienda, " +
                "tiempo_vivir_sitio, " +
                "foto_fachada, " +
                "ref_domiciliaria, " +
                "estatus_completado, " +
                "dependientes) " +
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
        pInsert.bindString(20, params.get(19));
        pInsert.bindLong(21, Long.parseLong(params.get(20)));
        pInsert.bindString(22, params.get(21));

        pInsert.executeInsert();

        db.setTransactionSuccessful();
        db.endTransaction();
    }

    public void saveDatosNegocioGpo (SQLiteDatabase db, HashMap<Integer, String> params, int tipo){
        db.beginTransaction();
        String tbl = TBL_NEGOCIO_INTEGRANTE;
        switch (tipo){
            case 2:
                tbl = TBL_NEGOCIO_INTEGRANTE_REN;
                break;
            case 3:
                tbl = TBL_NEGOCIO_INTEGRANTE_AUTO;
                break;
        }
        String sql = "INSERT INTO " + tbl + " (" +
                "id_integrante, " +
                "nombre, " +
                "latitud, " +
                "longitud, " +
                "calle, " +
                "no_exterior, " +
                "no_interior, " +
                "manzana, " +
                "lote, " +
                "cp, " +
                "colonia, " +
                "ciudad, " +
                "localidad, " +
                "municipio, " +
                "estado, " +
                "destino_credito, " +
                "otro_destino_credito, " +
                "ocupacion, " +
                "actividad_economica, " +
                "antiguedad, " +
                "ing_mensual, " +
                "ing_otros, " +
                "gasto_semanal, " +
                "capacidad_pago, " +
                "monto_maximo, " +
                "medios_pago, " +
                "otro_medio_pago, " +
                "num_ope_mensuales, " +
                "num_ope_mensuales_efectivo, " +
                "foto_fachada, " +
                "ref_domiciliaria, " +
                "estatus_rechazo, " +
                "comentario_rechazo, " +
                "estatus_completado) " +
                "VALUES (?,?,?,?,?,?,?,?,?,?, ?,?,?,?,?,?,?,?,?,?, ?,?,?,?,?,?,?,?,?,?, ?,?,?,?)";

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
        pInsert.bindLong(32, Long.parseLong(params.get(31)));
        pInsert.bindString(33, params.get(32));
        pInsert.bindLong(34, Long.parseLong(params.get(33)));

        pInsert.executeInsert();

        db.setTransactionSuccessful();
        db.endTransaction();
    }

    public void saveDatosConyugeGpo (SQLiteDatabase db, HashMap<Integer, String> params, int tipo){
        db.beginTransaction();
        String tbl = TBL_CONYUGE_INTEGRANTE;
        switch (tipo){
            case 2:
                tbl = TBL_CONYUGE_INTEGRANTE_REN;
                break;
            case 3:
                tbl = TBL_CONYUGE_INTEGRANTE_AUTO;
                break;
        }
        String sql = "INSERT INTO " + tbl + " (" +
                "id_integrante, " +
                "nombre, " +
                "paterno, " +
                "materno, " +
                "nacionalidad, " +
                "ocupacion, " +
                "calle, " +
                "no_exterior, " +
                "no_interior, " +
                "manzana, " +
                "lote, " +
                "cp, " +
                "colonia, " +
                "ciudad, " +
                "localidad, " +
                "municipio, " +
                "estado, " +
                "ingresos_mensual, "+
                "gasto_mensual, " +
                "tel_celular, " +
                "tel_trabajo, " +
                "estatus_completado) " +
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
        pInsert.bindString(20, params.get(19));
        pInsert.bindString(21, params.get(20));
        pInsert.bindLong(22, Long.parseLong(params.get(21)));

        pInsert.executeInsert();

        db.setTransactionSuccessful();
        db.endTransaction();
    }

    public void saveDatosOtrosGpo (SQLiteDatabase db, HashMap<Integer, String> params, int tipo){
        db.beginTransaction();
        String tbl = (tipo == 1)?TBL_OTROS_DATOS_INTEGRANTE:TBL_OTROS_DATOS_INTEGRANTE_REN;

        String sql = "INSERT INTO " + tbl + " (" +
                "id_integrante, " +
                "clasificacion_riesgo, " +
                "medio_contacto, " +
                "email, " +
                "estado_cuenta, " +
                "estatus_integrante, " +
                "monto_solicitado, " +
                "casa_reunion, " +
                "firma, " +
                "estatus_completado) " +
                "VALUES (?,?,?,?,?,?,?,?,?,?)";

        SQLiteStatement pInsert = db.compileStatement(sql);
        pInsert.bindLong(1, Long.parseLong(params.get(0)));
        pInsert.bindString(2, params.get(1));
        pInsert.bindString(3, params.get(2));
        pInsert.bindString(4, params.get(3));
        pInsert.bindString(5, params.get(4));
        pInsert.bindLong(6, Long.parseLong(params.get(5)));
        pInsert.bindString(7, params.get(6));
        pInsert.bindLong(8, Long.parseLong(params.get(7)));
        pInsert.bindString(9, params.get(8));
        pInsert.bindLong(10, Long.parseLong(params.get(9)));

        pInsert.executeInsert();

        db.setTransactionSuccessful();
        db.endTransaction();
    }

    public void saveDatosOtrosGpoAuto (SQLiteDatabase db, HashMap<Integer, String> params){
        db.beginTransaction();
        String tbl = TBL_OTROS_DATOS_INTEGRANTE_AUTO;

        String sql = "INSERT INTO " + tbl + " (" +
                "id_integrante, " +
                "clasificacion_riesgo, " +
                "medio_contacto, " +
                "email, " +
                "estado_cuenta, " +
                "estatus_integrante, " +
                "monto_solicitado, " +
                "casa_reunion, " +
                "firma, " +
                "estatus_completado, " +
                "monto_autorizado) " +
                "VALUES (?,?,?,?,?,?,?,?,?,?,?)";

        SQLiteStatement pInsert = db.compileStatement(sql);
        pInsert.bindLong(1, Long.parseLong(params.get(0)));
        pInsert.bindString(2, params.get(1));
        pInsert.bindString(3, params.get(2));
        pInsert.bindString(4, params.get(3));
        pInsert.bindString(5, params.get(4));
        pInsert.bindLong(6, Long.parseLong(params.get(5)));
        pInsert.bindString(7, params.get(6));
        pInsert.bindLong(8, Long.parseLong(params.get(7)));
        pInsert.bindString(9, params.get(8));
        pInsert.bindLong(10, Long.parseLong(params.get(9)));
        pInsert.bindString(11, params.get(10));

        pInsert.executeInsert();

        db.setTransactionSuccessful();
        db.endTransaction();
    }

    public void saveDocumentosClientes (SQLiteDatabase db,HashMap<Integer, String> params, int tipo){
        db.beginTransaction();
        String tbl = (tipo == 1)?TBL_DOCUMENTOS:TBL_DOCUMENTOS_REN;
        String sql = "INSERT INTO " + tbl + " (" +
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

    public void saveDocumentosIntegrante (SQLiteDatabase db, HashMap<Integer, String> params, int tipo){
        db.beginTransaction();
        String tbl = (tipo == 1)?TBL_DOCUMENTOS_INTEGRANTE:TBL_DOCUMENTOS_INTEGRANTE_REN;
        String sql = "INSERT INTO " + tbl + " (" +
                "id_integrante, " +
                "ine_frontal, " +
                "ine_reverso, " +
                "curp, " +
                "comprobante, " +
                "estatus_completado) " +
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

    public Long saveReporteSesiones(SQLiteDatabase db, HashMap<Integer, String> params){
        db.beginTransaction();
        String sql = "INSERT INTO " + TBL_REPORTE_SESIONES + " (" +
                "id, " +
                "serie_id, " +
                "nombre_asesor, " +
                "usuario, " +
                "sucursal, " +
                "sucursalid, " +
                "horariologin, " +
                "nivelbateria, " +
                "versionapp, " +
                "primeragestion, " +
                "ultimagestion, " +
                "totalgestiones) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        SQLiteStatement pInsert = db.compileStatement(sql);
        pInsert.bindLong(1, Long.parseLong(params.get(0)));    //(1) id
        pInsert.bindString(2, params.get(1));                  //(2) serie_id
        pInsert.bindString(3, params.get(2));                  //(3) nombre_asesore
        pInsert.bindString(4, params.get(3));                  //(4) usuario
        pInsert.bindString(5, params.get(4));                  //(5) sucursal
        pInsert.bindString(6, params.get(5));                  //(6) sucursalid
        pInsert.bindString(7, params.get(6));                  //(7) horariologin
        pInsert.bindString(8, params.get(7));                  //(8) nivelbateria
        pInsert.bindString(9, params.get(8));                  //(9) versionapp
        pInsert.bindString(10, params.get(9));                 //(10) primeragestion
        pInsert.bindString(11, params.get(10));                //(11) ultimagestion
        pInsert.bindString(12, params.get(11));                //(12) totalgestiones

        Long id = pInsert.executeInsert();

        db.setTransactionSuccessful();
        db.endTransaction();
        return id;
    }

    public Long saveSucursales(SQLiteDatabase db, HashMap<Integer, String> params){
        db.beginTransaction();
        String sql = "INSERT INTO " + TBL_SUCURSALES + " (" +
                "id, " +
                "nombre, " +
                "region_id, " +
                "centrocosto_id) " +
                "VALUES (?, ?, ?, ?)";
        SQLiteStatement pInsert = db.compileStatement(sql);
        pInsert.bindLong(1, Long.parseLong(params.get(0)));    //(1) id
        pInsert.bindString(2, params.get(1));                  //(2) nombre
        pInsert.bindLong(3, Long.parseLong(params.get(2)));    //(3) region_id
        pInsert.bindLong(4, Long.parseLong(params.get(3)));    //(4) centrocosto_id

        Long id = pInsert.executeInsert();

        db.setTransactionSuccessful();
        db.endTransaction();
        return id;
    }

    public Long saveCarteraInd(SQLiteDatabase db, String table_name, HashMap<Integer, String> params){
        db.beginTransaction();
        String sql = "INSERT INTO " + table_name + " (" +
                "id_cartera, " +
                "clave, " +
                "nombre, " +
                "direccion, " +
                "asesor_nombre, " +
                "serie_id, " +
                "is_ruta, " +
                "ruta_obligado, " +
                "usuario_id, " +
                "dia, " +
                "num_solicitud, " +
                "fecha_dispositivo, " +
                "fecha_actualizado, " +
                "colonia, " +
                "geo_cliente, " +
                "geo_aval, " +
                "geo_negocio, " +
                "cc, " +
                "agf, " +
                "curp, " +
                "dias_atraso) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        SQLiteStatement pInsert = db.compileStatement(sql);
        pInsert.bindString(1, params.get(0));                  //(1) id_cartera
        pInsert.bindString(2, params.get(1));                  //(2) clave
        pInsert.bindString(3, params.get(2));                  //(3) nombre
        pInsert.bindString(4, params.get(3));                  //(4) direccion
        pInsert.bindString(5, params.get(4));                  //(5) asesor_nombre
        pInsert.bindString(6, params.get(5));                  //(6) serie_id
        pInsert.bindLong(7, Long.parseLong(params.get(6)));    //(7) is_ruta
        pInsert.bindLong(8, Long.parseLong(params.get(7)));    //(8) ruta_obligado
        pInsert.bindString(9, params.get(8));                  //(9) usuario_id
        pInsert.bindString(10, params.get(9));                 //(10) dia
        pInsert.bindString(11, params.get(10));                //(11) num_solicitud
        pInsert.bindString(12, params.get(11));                //(12) fecha_dispositivo
        pInsert.bindString(13, params.get(12));                //(13) fecha_actualizado
        pInsert.bindString(14, params.get(13));                //(14) colonia
        pInsert.bindLong(15, Long.parseLong(params.get(14)));  //(15) geo_cliente
        pInsert.bindLong(16, Long.parseLong(params.get(15)));  //(16) geo_aval
        pInsert.bindLong(17, Long.parseLong(params.get(16)));  //(17) geo_negocio
        pInsert.bindString(18, params.get(17));                //(18) cc
        pInsert.bindString(19, params.get(18));                //(19) agf
        pInsert.bindString(20, params.get(19));                //(20) curp
        pInsert.bindString(21, params.get(20));                //(21) curp

        Long id = pInsert.executeInsert();

        db.setTransactionSuccessful();
        db.endTransaction();
        return id;
    }

    public Long savePrestamosInd(SQLiteDatabase db, String table_name, HashMap<Integer, String> params){
        db.beginTransaction();
        String sql = "INSERT INTO " + table_name + " (" +
                "id_prestamo, " +
                "id_cliente, " +
                "num_prestamo, " +
                "num_solicitud, " +
                "fecha_entrega, " +
                "monto_otorgado, " +
                "monto_total, " +
                "monto_amortizacion, " +
                "monto_requerido, " +
                "num_amortizacion, " +
                "fecha_establecida, " +
                "tipo_cartera, " +
                "pagada, " +
                "fecha_dispositivo, " +
                "fecha_actualizado) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ? )";
        SQLiteStatement pInsert = db.compileStatement(sql);
        pInsert.bindString(1, params.get(0));                                                   //(1) id_prestamo
        pInsert.bindString(2, params.get(1));                                                   //(2) id_cliente
        pInsert.bindString(3, params.get(2));                                                   //(3) num_prestamo
        pInsert.bindString(4, params.get(3));                                                   //(4) num_solicitud
        pInsert.bindString(5, params.get(4));                                                   //(5) fecha_entrada
        pInsert.bindString(6, params.get(5));                                                   //(6) monto_otorgado
        pInsert.bindString(7, params.get(6));                                                   //(7) monto_total
        pInsert.bindString(8, params.get(7));                                                   //(8) monto_amortizacion
        pInsert.bindString(9, params.get(8));                                                   //(9) monto_requerido
        pInsert.bindLong(10, Long.parseLong(params.get(9)));                                    //(10) num_amortizacion
        pInsert.bindString(11, params.get(10));                                                 //(11) fecha_establecida
        pInsert.bindString(12, params.get(11));                                                 //(12) tipo_cartera
        pInsert.bindLong(13, Long.parseLong(params.get(12)));                                   //(13) pagada
        pInsert.bindString(14, params.get(13));                                                 //(14) fecha_dispositivo
        pInsert.bindString(15, params.get(14));                                                 //(15) fecha_actualizado
        Long id = pInsert.executeInsert();

        db.setTransactionSuccessful();
        db.endTransaction();
        return id;
    }

    public Long savePrestamosGpo(SQLiteDatabase db, String table_name, HashMap<Integer, String> params){
        db.beginTransaction();
        String sql = "INSERT INTO " + table_name + " (" +
                "id_prestamo, " +
                "id_grupo, " +
                "num_prestamo, " +
                "num_solicitud, " +
                "fecha_entrega, " +
                "monto_otorgado, " +
                "monto_total, " +
                "monto_amortizacion, " +
                "monto_requerido, " +
                "num_amortizacion, " +
                "fecha_establecida, " +
                "tipo_cartera, " +
                "pagada, " +
                "fecha_dispositivo, " +
                "fecha_actualizado) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?,  ?, ?, ?, ?, ? )";
        SQLiteStatement pInsert = db.compileStatement(sql);
        pInsert.bindString(1, params.get(0));                                                   //(1) id_prestamo
        pInsert.bindString(2, params.get(1));                                                   //(2) id_grupo
        pInsert.bindString(3, params.get(2));                                                   //(3) num_prestamo
        pInsert.bindString(4, params.get(3));                                                   //(4) num_solicitud
        pInsert.bindString(5, params.get(4));                                                   //(5) fecha_entrada
        pInsert.bindString(6, params.get(5));                                                   //(6) monto_otorgado
        pInsert.bindString(7, params.get(6));                                                   //(7) monto_total
        pInsert.bindString(8, params.get(7));                                                   //(8) monto_amortizacion
        pInsert.bindString(9, params.get(8));                                                   //(9) monto_requerido
        pInsert.bindLong(10, Long.parseLong(params.get(9)));                                    //(10) num_amortizacion
        pInsert.bindString(11, params.get(10));                                                 //(11) fecha_establecida
        pInsert.bindString(12, params.get(11));                                                 //(12) tipo_cartera
        pInsert.bindLong(13, Long.parseLong(params.get(12)));                                   //(13) pagada
        pInsert.bindString(14, params.get(13));                                                 //(14) fecha_dispositivo
        pInsert.bindString(15, params.get(14));                                                 //(15) fecha_actualizado
        Long id = pInsert.executeInsert();

        db.setTransactionSuccessful();
        db.endTransaction();
        return id;
    }

    public Long saveAmortizaciones(SQLiteDatabase db, String table_name, HashMap<Integer, String> params){
        db.beginTransaction();
        String sql = "INSERT INTO " + table_name + " (" +
                "id_amortizacion, " +
                "id_prestamo, " +
                "fecha, " +
                "fecha_pago, " +
                "capital, " +
                "interes, " +
                "iva, " +
                "comision, " +
                "total, " +
                "capital_pagado, " +
                "interes_pagado, " +
                "iva_pagado, " +
                "interes_moratorio_pagado, " +
                "iva_moratorio_pagado, " +
                "comision_pagada, " +
                "total_pagado, " +
                "pagado, " +
                "numero, " +
                "dias_atraso, " +
                "fecha_dispositivo, " +
                "fecha_actualizado) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?,  ?, ?, ?, ?, ?, ?, ?, ?, ?, ?,  ? )";
        SQLiteStatement pInsert = db.compileStatement(sql);
        pInsert.bindString(1, params.get(0));                                                   //(1) id_amortizacion
        pInsert.bindString(2, params.get(1));                                                   //(2) id_prestamo
        pInsert.bindString(3, params.get(2));                                                   //(3) fecha
        pInsert.bindString(4, params.get(3));                                                   //(4) fecha_pago
        pInsert.bindString(5, params.get(4));                                                   //(5) capital
        pInsert.bindString(6, params.get(5));                                                   //(6) interes
        pInsert.bindString(7, params.get(6));                                                   //(7) iva
        pInsert.bindString(8, params.get(7));                                                   //(8) comision
        pInsert.bindString(9, params.get(8));                                                   //(9) total
        pInsert.bindString(10, params.get(9));                                                  //(10) capital_pagado
        pInsert.bindString(11, params.get(10));                                                 //(11) interes_pagado
        pInsert.bindString(12, params.get(11));                                                 //(12) iva_pagado
        pInsert.bindString(13, params.get(12));                                                 //(13) interes_moratorio_pagado
        pInsert.bindString(14, params.get(13));                                                 //(14) iva_moratorio_pagado
        pInsert.bindString(15, params.get(14));                                                 //(15) comision_pagada
        pInsert.bindString(16, params.get(15));                                                 //(16) total_pagado
        pInsert.bindString(17, params.get(16));                                                 //(17) pagado
        pInsert.bindLong(18, Long.parseLong(params.get(17)));                                   //(18) numero
        pInsert.bindString(19, params.get(18));                                                 //(19) dias_atraso
        pInsert.bindString(20, params.get(19));                                                 //(20) fecha_dispositivo
        pInsert.bindString(21, params.get(20));                                                 //(21) fecha_actualizado
        Long id = pInsert.executeInsert();

        db.setTransactionSuccessful();
        db.endTransaction();
        return id;
    }

    public Long saveRespuestasInd(SQLiteDatabase db, String table_name, HashMap<Integer, String> params){
        db.beginTransaction();
        String sql = "INSERT INTO " + table_name + " (" +
                "id_prestamo, " +
                "latitud, " +
                "longitud, " +
                "contacto, " +
                "motivo_aclaracion, " +
                "comentario, " +
                "actualizar_telefono, " +
                "nuevo_telefono, " +
                "resultado_gestion, " +
                "motivo_no_pago, " +
                "fecha_fallecimiento, " +
                "medio_pago, " +
                "fecha_pago, " +
                "pagara_requerido, " +
                "pago_realizado, " +
                "imprimir_recibo, " +
                "folio, " +
                "evidencia, " +
                "tipo_imagen, " +
                "gerente, " +
                "firma, " +
                "fecha_inicio, " +
                "fecha_fin, " +
                "fecha_envio, " +
                "estatus, " +
                "res_impresion, " +
                "estatus_pago, " +
                "saldo_corte, " +
                "saldo_actual, " +
                "dias_atraso) " +
                "VALUES (?,?,?,?,?,?,?,?,?,?,  ?,?,?,?,?,?,?,?,?,?,  ?,?,?,?,?,?,?,?,?,? )";
        SQLiteStatement pInsert = db.compileStatement(sql);
        pInsert.bindString(1, params.get(0));                                                   //(1) id_prestamo
        pInsert.bindString(2, params.get(1));                                                   //(2) latitud
        pInsert.bindString(3, params.get(2));                                                   //(3) longitud
        pInsert.bindString(4, params.get(3));                                                   //(4) contacto
        pInsert.bindString(5, params.get(4));                                                   //(5) motivo_aclaracion
        pInsert.bindString(6, params.get(5));                                                   //(6) comentario
        pInsert.bindString(7, params.get(6));                                                   //(7) actualizar_telefono
        pInsert.bindString(8, params.get(7));                                                   //(8) nuevo_telefono
        pInsert.bindString(9, params.get(8));                                                   //(9) resultado_gestion
        pInsert.bindString(10, params.get(9));                                                  //(10) motivo_no_pago
        pInsert.bindString(11, params.get(10));                                                 //(11) fecha_fallecimiento
        pInsert.bindString(12, params.get(11));                                                 //(12) medio_pago
        pInsert.bindString(13, params.get(12));                                                 //(13) fecha_pago
        pInsert.bindString(14, params.get(13));                                                 //(14) pagara_requerido
        pInsert.bindString(15, params.get(14));                                                 //(15) pago_realizado
        pInsert.bindString(16, params.get(15));                                                 //(16) imprimir_recibo
        pInsert.bindString(17, params.get(16));                                                 //(17) folio
        pInsert.bindString(18, params.get(17));                                                 //(18) evidencia
        pInsert.bindString(19, params.get(18));                                                 //(19) tipo_imagen
        pInsert.bindString(20, params.get(19));                                                 //(20) gerente
        pInsert.bindString(21, params.get(20));                                                 //(21) firma
        pInsert.bindString(22, params.get(21));                                                 //(22) fecha_inicio
        pInsert.bindString(23, params.get(22));                                                 //(23) fecha_fin
        pInsert.bindString(24, params.get(23));                                                 //(24) fecha_envio
        pInsert.bindString(25, params.get(24));                                                 //(25) estatus
        pInsert.bindString(26, params.get(25));                                                 //(26) res_impresion
        pInsert.bindString(27, params.get(26));                                                 //(27) estatus_pago
        pInsert.bindString(28, params.get(27));                                                 //(28) saldo_corte
        pInsert.bindString(29, params.get(28));                                                 //(29) saldo_actual
        pInsert.bindString(30, params.get(29));                                                 //(30) dias_atraso
        Long id = pInsert.executeInsert();

        db.setTransactionSuccessful();
        db.endTransaction();
        return id;
    }

    public Long saveRespuestasGpo(SQLiteDatabase db, HashMap<Integer, String> params){
        db.beginTransaction();
        String tbl =  TBL_RESPUESTAS_GPO_T;

        String sql = "INSERT INTO " + tbl + " (" +
                "id_prestamo, " +
                "latitud, " +
                "longitud, " +
                "contacto, " +
                "motivo_aclaracion, " +
                "comentario, " +
                "actualizar_telefono, " +
                "nuevo_telefono, " +
                "resultado_gestion, " +
                "motivo_no_pago, " +
                "fecha_fallecimiento, " +
                "medio_pago, " +
                "fecha_pago, " +
                "detalle_ficha, " +
                "pago_realizado, " +
                "imprimir_recibo, " +
                "folio, " +
                "evidencia, " +
                "tipo_imagen, " +
                "gerente, " +
                "firma, " +
                "fecha_inicio, " +
                "fecha_fin, " +
                "fecha_envio, " +
                "estatus, " +
                "res_impresion, " +
                "arqueo_caja, " +
                "estatus_pago, " +
                "saldo_corte, " +
                "saldo_actual, " +
                "dias_atraso) " +
                "VALUES (?,?,?,?,?,?,?,?,?,?,  ?,?,?,?,?,?,?,?,?,?,  ?,?,?,?,?,?,?,?,?,?, ?)";
        SQLiteStatement pInsert = db.compileStatement(sql);
        pInsert.bindString(1, params.get(0));                                                   //(1) id_prestamo
        pInsert.bindString(2, params.get(1));                                                   //(2) latitud
        pInsert.bindString(3, params.get(2));                                                   //(3) longitud
        pInsert.bindString(4, params.get(3));                                                   //(4) contacto
        pInsert.bindString(5, params.get(4));                                                   //(5) motivo_aclaracion
        pInsert.bindString(6, params.get(5));                                                   //(6) comentario
        pInsert.bindString(7, params.get(6));                                                   //(7) actualizar_telefono
        pInsert.bindString(8, params.get(7));                                                   //(8) nuevo_telefono
        pInsert.bindString(9, params.get(8));                                                   //(9) resultado_gestion
        pInsert.bindString(10, params.get(9));                                                  //(10) motivo_no_pago
        pInsert.bindString(11, params.get(10));                                                 //(11) fecha_fallecimiento
        pInsert.bindString(12, params.get(11));                                                 //(12) medio_pago
        pInsert.bindString(13, params.get(12));                                                 //(13) fecha_pago
        pInsert.bindString(14, params.get(13));                                                 //(14) detalle_ficha
        pInsert.bindString(15, params.get(14));                                                 //(15) pago_realizado
        pInsert.bindString(16, params.get(15));                                                 //(16) imprimir_recibo
        pInsert.bindString(17, params.get(16));                                                 //(17) folio
        pInsert.bindString(18, params.get(17));                                                 //(18) evidencia
        pInsert.bindString(19, params.get(18));                                                 //(19) tipo_imagen
        pInsert.bindString(20, params.get(19));                                                 //(20) gerente
        pInsert.bindString(21, params.get(20));                                                 //(21) firma
        pInsert.bindString(22, params.get(21));                                                 //(22) fecha_inicio
        pInsert.bindString(23, params.get(22));                                                 //(23) fecha_fin
        pInsert.bindString(24, params.get(23));                                                 //(24) fecha_envio
        pInsert.bindString(25, params.get(24));                                                 //(25) estatus
        pInsert.bindString(26, params.get(25));                                                 //(26) res_impresion
        pInsert.bindString(27, params.get(26));                                                 //(27) arqueo_caja
        pInsert.bindString(28, params.get(27));                                                 //(28) estatus_pago
        pInsert.bindString(29, params.get(28));                                                 //(29) saldo_corte
        pInsert.bindString(30, params.get(29));                                                 //(30) saldo_actual
        pInsert.bindString(31, params.get(30));                                                 //(31) dias_atraso
        Long id = pInsert.executeInsert();

        db.setTransactionSuccessful();
        db.endTransaction();
        return id;
    }

    public Long saveRespuestasVencidasInd(SQLiteDatabase db, HashMap<Integer, String> params){
        String tbl;
        tbl = TBL_RESPUESTAS_IND_V_T;
        db.beginTransaction();
        Log.e("Inserta", "individual vencida");
        String sql = "INSERT INTO " + tbl + " (" +
                "id_prestamo, " +
                "latitud, " +
                "longitud, " +
                "contacto, " +
                "comentario, " +
                "actualizar_telefono, " +
                "nuevo_telefono, " +
                "resultado_gestion, " +
                "motivo_no_pago, " +
                "fecha_fallecimiento, " +
                "fecha_monto_promesa," +
                "monto_promesa," +
                "medio_pago, " +
                "fecha_pago, " +
                "pagara_requerido, " +
                "pago_realizado, " +
                "imprimir_recibo, " +
                "folio, " +
                "evidencia, " +
                "tipo_imagen, " +
                "gerente, " +
                "firma, " +
                "fecha_inicio, " +
                "fecha_fin, " +
                "fecha_envio, " +
                "estatus, " +
                "res_impresion, " +
                "estatus_pago, " +
                "saldo_corte, " +
                "saldo_actual, " +
                "dias_atraso, " +
                "serial_id) " +
                "VALUES (?,?,?,?,?,?,?,?,?,?,  ?,?,?,?,?,?,?,?,?,?,  ?,?,?,?,?,?,?,?,?,?, ?,?)";
        SQLiteStatement pInsert = db.compileStatement(sql);
        pInsert.bindString(1, params.get(0));                                                   //(1) id_prestamo
        pInsert.bindString(2, params.get(1));                                                   //(2) latitud
        pInsert.bindString(3, params.get(2));                                                   //(3) longitud
        pInsert.bindString(4, params.get(3));                                                   //(4) contacto
        pInsert.bindString(5, params.get(4));                                                   //(5) comentario
        pInsert.bindString(6, params.get(5));                                                   //(6) actualizar_telefono
        pInsert.bindString(7, params.get(6));                                                   //(7) nuevo_telefono
        pInsert.bindString(8, params.get(7));                                                   //(8) resultado_gestion
        pInsert.bindString(9, params.get(8));                                                   //(9) motivo_no_pago
        pInsert.bindString(10, params.get(9));                                                  //(10) fecha_fallecimiento
        pInsert.bindString(11, params.get(10));                                                 //(11) fecha_monto_promesa
        pInsert.bindString(12, params.get(11));                                                 //(12) monto_promesa
        pInsert.bindString(13, params.get(12));                                                 //(13) medio_pago
        pInsert.bindString(14, params.get(13));                                                 //(14) fecha_pago
        pInsert.bindString(15, params.get(14));                                                 //(15) pagara_requerido
        pInsert.bindString(16, params.get(15));                                                 //(16) pago_realizado
        pInsert.bindString(17, params.get(16));                                                 //(17) imprimir_recibo
        pInsert.bindString(18, params.get(17));                                                 //(18) folio
        pInsert.bindString(19, params.get(18));                                                 //(19) evidencia
        pInsert.bindString(20, params.get(19));                                                 //(20) tipo_imagen
        pInsert.bindString(21, params.get(20));                                                 //(21) gerente
        pInsert.bindString(22, params.get(21));                                                 //(22) firma
        pInsert.bindString(23, params.get(22));                                                 //(23) fecha_inicio
        pInsert.bindString(24, params.get(23));                                                 //(24) fecha_fin
        pInsert.bindString(25, params.get(24));                                                 //(25) fecha_envio
        pInsert.bindString(26, params.get(25));                                                 //(26) estatus
        pInsert.bindString(27, params.get(26));                                                 //(27) res_impresion
        pInsert.bindString(28, params.get(27));                                                 //(28) estatus_pago
        pInsert.bindString(29, params.get(28));                                                 //(29) saldo_corte
        pInsert.bindString(30, params.get(29));                                                 //(30) saldo_actual
        pInsert.bindString(31, params.get(30));                                                 //(31) dias_atraso
        pInsert.bindString(32, params.get(31));                                                 //(32) serial_id
        Long id = pInsert.executeInsert();

        db.setTransactionSuccessful();
        db.endTransaction();
        Log.e("id_vencida", String.valueOf(id));
        return id;
    }

    public Long saveRespuestasVencidasInt(SQLiteDatabase db, HashMap<Integer, String> params){
        String tbl;
        tbl = TBL_RESPUESTAS_INTEGRANTE_T;
        db.beginTransaction();
        String sql = "INSERT INTO " + tbl + " (" +
                "id_prestamo, " +
                "id_integrante, " +
                "latitud, " +
                "longitud, " +
                "contacto, " +
                "comentario, " +
                "actualizar_telefono, " +
                "nuevo_telefono, " +
                "resultado_gestion, " +
                "motivo_no_pago, " +
                "fecha_fallecimiento, " +
                "fecha_monto_promesa," +
                "monto_promesa," +
                "medio_pago, " +
                "fecha_pago, " +
                "pagara_requerido, " +
                "pago_realizado, " +
                "imprimir_recibo, " +
                "folio, " +
                "evidencia, " +
                "tipo_imagen, " +
                "gerente, " +
                "firma, " +
                "fecha_inicio, " +
                "fecha_fin, " +
                "fecha_envio, " +
                "estatus, " +
                "res_impresion, " +
                "estatus_pago, " +
                "saldo_corte, " +
                "saldo_actual, " +
                "dias_atraso, " +
                "serial_id) " +
                "VALUES (?,?,?,?,?,?,?,?,?,?,  ?,?,?,?,?,?,?,?,?,?,  ?,?,?,?,?,?,?,?,?,?, ?,?,?)";
        SQLiteStatement pInsert = db.compileStatement(sql);
        pInsert.bindString(1, params.get(0));                                                   //(1) id_prestamo
        pInsert.bindString(2, params.get(1));                                                   //(2) id_integrante
        pInsert.bindString(3, params.get(2));                                                   //(3) latitud
        pInsert.bindString(4, params.get(3));                                                   //(4) longitud
        pInsert.bindString(5, params.get(4));                                                   //(5) contacto
        pInsert.bindString(6, params.get(5));                                                   //(6) comentario
        pInsert.bindString(7, params.get(6));                                                   //(7) actualizar_telefono
        pInsert.bindString(8, params.get(7));                                                   //(8) nuevo_telefono
        pInsert.bindString(9, params.get(8));                                                   //(9) resultado_gestion
        pInsert.bindString(10, params.get(9));                                                  //(10) motivo_no_pago
        pInsert.bindString(11, params.get(10));                                                 //(11) fecha_fallecimiento
        pInsert.bindString(12, params.get(11));                                                 //(12) fecha_monto_promesa
        pInsert.bindString(13, params.get(12));                                                 //(13) monto_promesa
        pInsert.bindString(14, params.get(13));                                                 //(14) medio_pago
        pInsert.bindString(15, params.get(14));                                                 //(15) fecha_pago
        pInsert.bindString(16, params.get(15));                                                 //(16) pagara_requerido
        pInsert.bindString(17, params.get(16));                                                 //(17) pago_realizado
        pInsert.bindString(18, params.get(17));                                                 //(18) imprimir_recibo
        pInsert.bindString(19, params.get(18));                                                 //(19) folio
        pInsert.bindString(20, params.get(19));                                                 //(20) evidencia
        pInsert.bindString(21, params.get(20));                                                 //(21) tipo_imagen
        pInsert.bindString(22, params.get(21));                                                 //(22) gerente
        pInsert.bindString(23, params.get(22));                                                 //(23) firma
        pInsert.bindString(24, params.get(23));                                                 //(24) fecha_inicio
        pInsert.bindString(25, params.get(24));                                                 //(25) fecha_fin
        pInsert.bindString(26, params.get(25));                                                 //(26) fecha_envio
        pInsert.bindString(27, params.get(26));                                                 //(27) estatus
        pInsert.bindString(28, params.get(27));                                                 //(28) res_impresion
        pInsert.bindString(29, params.get(28));                                                 //(29) estatus_pago
        pInsert.bindString(30, params.get(29));                                                 //(30) saldo_corte
        pInsert.bindString(31, params.get(30));                                                 //(31) saldo_actual
        pInsert.bindString(32, params.get(31));                                                 //(32) dias_atraso
        pInsert.bindString(33, params.get(32));                                                 //(33) serial_id
        Long id = pInsert.executeInsert();

        db.setTransactionSuccessful();
        db.endTransaction();
        return id;
    }

    public Long savePagos(SQLiteDatabase db, String table_name, HashMap<Integer, String> params){
        db.beginTransaction();
        String sql = "INSERT INTO " + table_name + " (" +
                "id_prestamo, " +
                "fecha, " +
                "monto, " +
                "banco, " +
                "fecha_dispositivo, " +
                "fecha_actualizado) " +
                "VALUES (?, ?, ?, ?, ?, ?)";
        SQLiteStatement pInsert = db.compileStatement(sql);
        pInsert.bindString(1, params.get(0));                                                   //(1) id_prestamo
        pInsert.bindString(2, params.get(1));                                                   //(2) fecha
        pInsert.bindString(3, params.get(2));                                                   //(3) monto
        pInsert.bindString(4, params.get(3));                                                   //(4) banco
        pInsert.bindString(5, params.get(4));                                                   //(5) fecha_dispositivo
        pInsert.bindString(6, params.get(5));                                                   //(6) fecha_actualizado
        Long id = pInsert.executeInsert();

        db.setTransactionSuccessful();
        db.endTransaction();
        return id;
    }

    public Long saveAval(SQLiteDatabase db, String table_name, HashMap<Integer, String> params){
        db.beginTransaction();
        String sql = "INSERT INTO " + table_name + " (" +
                "id_prestamo, " +
                "id_aval, " +
                "nombre, " +
                "parentesco, " +
                "direccion, " +
                "telefono, " +
                "fecha_dispositivo, " +
                "fecha_actualizado) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        SQLiteStatement pInsert = db.compileStatement(sql);
        pInsert.bindString(1, params.get(0));                                                   //(1) id_prestamo
        pInsert.bindString(2, params.get(1));                                                   //(2) id_aval
        pInsert.bindString(3, params.get(2));                                                   //(3) nombre
        pInsert.bindString(4, params.get(3));                                                   //(4) parentesco
        pInsert.bindString(5, params.get(4));                                                   //(5) direccion
        pInsert.bindString(6, params.get(5));                                                   //(6) telefono
        pInsert.bindString(7, params.get(6));                                                   //(7) fecha_dispositivo
        pInsert.bindString(8, params.get(7));                                                   //(8) fecha_actualizado
        Long id = pInsert.executeInsert();

        db.setTransactionSuccessful();
        db.endTransaction();
        return id;
    }

    public Long saveMiembros(SQLiteDatabase db, HashMap<Integer, String> params){
        db.beginTransaction();
        String tbl = TBL_MIEMBROS_GPO_T;

        String sql = "INSERT INTO " + tbl + " (" +
                "id_prestamo, " +
                "id_integrante, " +
                "num_solicitud, " +
                "id_grupo, " +
                "nombre, " +
                "direccion, " +
                "tel_casa, " +
                "tel_celular, " +
                "tipo_integrante, " +
                "monto_prestamo, " +
                "monto_requerido, " +
                "fecha_dispositivo, " +
                "fecha_actualizado, " +
                "clave, " +
                "id_prestamo_integrante, " +
                "curp) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        SQLiteStatement pInsert = db.compileStatement(sql);
        pInsert.bindString(1, params.get(0));                                                   //(1) id_prestamo
        pInsert.bindString(2, params.get(1));                                                   //(2) id_integrante
        pInsert.bindString(3, params.get(2));                                                   //(3) num_solicitud
        pInsert.bindString(4, params.get(3));                                                   //(4) id_grupo
        pInsert.bindString(5, params.get(4));                                                   //(5) nombre
        pInsert.bindString(6, params.get(5));                                                   //(6) direccion
        pInsert.bindString(7, params.get(6));                                                   //(7) tel_casa
        pInsert.bindString(8, params.get(7));                                                   //(8) tel_celular
        pInsert.bindString(9, params.get(8));                                                   //(9) tipo_integrante
        pInsert.bindString(10, params.get(9));                                                  //(10) monto_prestamo
        pInsert.bindString(11, params.get(10));                                                 //(11) monto_requerido
        pInsert.bindString(12, params.get(11));                                                 //(12) fecha_dispositivo
        pInsert.bindString(13, params.get(12));                                                 //(13) fecha_actualizado
        pInsert.bindString(14, params.get(13));                                                 //(14) clave
        pInsert.bindString(15, params.get(14));                                                 //(15) id_prestamo_integrante
        pInsert.bindString(16, params.get(15));                                                 //(16) curp
        Long id = pInsert.executeInsert();

        db.setTransactionSuccessful();
        db.endTransaction();
        return id;
    }

    public Long saveMiembrosPagos(SQLiteDatabase db, HashMap<Integer, String> params){
        db.beginTransaction();
        String tbl = TBL_MIEMBROS_PAGOS_T;

        String sql = "INSERT INTO " + tbl + " (" +
                "id_integrante, " +
                "id_prestamo, " +
                "id_gestion, " +
                "nombre, " +
                "monto_requerido, " +
                "pago_realizado, " +
                "solidario, " +
                "adelanto, " +
                "pago_requerido) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
        SQLiteStatement pInsert = db.compileStatement(sql);
        pInsert.bindString(1, params.get(0));                                                   //(1) id_integrante
        pInsert.bindString(2, params.get(1));                                                   //(2) id_prestamo
        pInsert.bindString(3, params.get(2));                                                   //(3) id_gestion
        pInsert.bindString(4, params.get(3));                                                   //(4) nombre
        pInsert.bindString(5, params.get(4));                                                   //(5) monto_requerido
        pInsert.bindString(6, params.get(5));                                                   //(6) pago_realizado
        pInsert.bindString(7, params.get(6));                                                   //(7) adelanto
        pInsert.bindString(8, params.get(7));                                                   //(8) solidario
        pInsert.bindString(9, params.get(8));                                                   //(9) pago_requerido
        Long id = pInsert.executeInsert();

        db.setTransactionSuccessful();
        db.endTransaction();
        return id;
    }

    public Long saveCarteraGpo(SQLiteDatabase db, String table_name, HashMap<Integer, String> params){
        db.beginTransaction();
        String sql = "INSERT INTO " + table_name + " (" +
                "id_cartera, " +
                "clave, " +
                "nombre, " +
                "tesorera, " +
                "direccion, " +
                "asesor_nombre, " +
                "serie_id, " +
                "is_ruta, " +
                "ruta_obligado, " +
                "usuario_id, " +
                "dia, " +
                "num_solicitud, " +
                "fecha_dispositivo, " +
                "fecha_actualizado, " +
                "colonia, " +
                "geolocalizadas, " +
                "cc, " +
                "agf, " +
                "dias_atraso) " +
                "VALUES (?,?,?,?,?,?,?,?,?,?, ?,?,?,?,?,?,?,?,?)";
        SQLiteStatement pInsert = db.compileStatement(sql);
        pInsert.bindString(1, params.get(0));                                                   //(1) id_cartera
        pInsert.bindString(2, params.get(1));                                                   //(2) clave
        pInsert.bindString(3, params.get(2));                                                   //(3) nombre
        pInsert.bindString(4, params.get(3));                                                   //(4) tesorera
        pInsert.bindString(5, params.get(4));                                                   //(5) direccion
        pInsert.bindString(6, params.get(5));                                                   //(6) nombre_asesor
        pInsert.bindString(7, params.get(6));                                                   //(7) serie_id
        pInsert.bindLong(8, Long.parseLong(params.get(7)));                                     //(8) is_ruta
        pInsert.bindLong(9, Long.parseLong(params.get(8)));                                     //(9) ruta_olbigado
        pInsert.bindString(10, params.get(9));                                                  //(10) usuario_id
        pInsert.bindString(11, params.get(10));                                                 //(11) dia
        pInsert.bindString(12, params.get(11));                                                 //(12) num_solicitud
        pInsert.bindString(13, params.get(12));                                                 //(13) fecha_dispositivo
        pInsert.bindString(14, params.get(13));                                                 //(14) fecha_actualizado
        pInsert.bindString(15, params.get(14));                                                 //(15) colonia
        pInsert.bindString(16, params.get(15));                                                 //(16) geolocalizadas
        pInsert.bindString(17, params.get(16));                                                 //(17) cc
        pInsert.bindString(18, params.get(17));                                                 //(18) agf
        pInsert.bindString(19, params.get(18));                                                 //(19) dias_atraso
        Long id = pInsert.executeInsert();

        db.setTransactionSuccessful();
        db.endTransaction();
        return id;
    }

    public Long saveSoporte(SQLiteDatabase db, HashMap<Integer, String> params){
        db.beginTransaction();
        String sql = "INSERT INTO " + TBL_SOPORTE + " (" +
                "categoria_id, " +
                "tipo_ficha, " +
                "nombre, " +
                "grupo_id, " +
                "cliente_id, " +
                "num_solicitud, " +
                "folio_impresion, " +
                "comentario, " +
                "folio_solicitud, " +
                "turno, " +
                "comentario_admin, " +
                "fecha_solicitud, " +
                "fecha_envio, " +
                "estatus_ticket, " +
                "estatus_envio) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        SQLiteStatement pInsert = db.compileStatement(sql);
        pInsert.bindLong(1, Long.parseLong(params.get(0)));                                   //(1) categoria
        pInsert.bindLong(2, Long.parseLong(params.get(1)));                                   //(2) tipo_ficha
        pInsert.bindString(3, params.get(2));                                                 //(3) nombre
        pInsert.bindLong(4, Long.parseLong(params.get(3)));                                   //(4) grupo_id
        pInsert.bindLong(5, Long.parseLong(params.get(4)));                                   //(5) cliente_id
        pInsert.bindLong(6, Long.parseLong(params.get(5)));                                   //(6) num_solicitud
        pInsert.bindLong(7, Long.parseLong(params.get(6)));                                   //(7) folio_impresion
        pInsert.bindString(8, params.get(7));                                                 //(8) comentario
        pInsert.bindString(9, params.get(8));                                                 //(9) folio_solicitud
        pInsert.bindLong(10, Long.parseLong(params.get(9)));                                  //(10) turno
        pInsert.bindString(11, params.get(10));                                               //(11) comentario_admin
        pInsert.bindString(12, params.get(11));                                               //(12) fecha_solicitud
        pInsert.bindString(13, params.get(12));                                               //(13) fecha_envio
        pInsert.bindString(14, params.get(13));                                               //(14) estatus_ticket
        pInsert.bindLong(15, Long.parseLong(params.get(14)));                                 //(15) estatus_envio

        Long id = pInsert.executeInsert();

        db.setTransactionSuccessful();
        db.endTransaction();
        return id;
    }

    public Cursor getRecords(String table, String where, String order, String[] args){
        SQLiteDatabase db = this.getReadableDatabase();
        Log.v("SQL", "SELECT * FROM " + table + where + order);
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
        String query = "SELECT cre.*, cli.*, dcli.*, cony.*, dcon.*, eco.*, neg.*, dneg.*, aval.*, daval.*, refe.*, doc.*, soli.estatus, soli.fecha_inicio, soli.fecha_termino, soli.id_originacion FROM "+TBL_SOLICITUDES+" AS soli " +
                    "INNER JOIN " + TBL_CREDITO_IND + " AS cre ON soli.id_solicitud = cre.id_solicitud " +
                    "INNER JOIN " + TBL_CLIENTE_IND + " AS cli ON soli.id_solicitud = cli.id_solicitud " +
                    "INNER JOIN " + TBL_DIRECCIONES + " AS dcli ON cli.direccion_id = dcli.id_direccion AND dcli.tipo_direccion = 'CLIENTE' " +
                    "INNER JOIN " + TBL_CONYUGE_IND + " AS cony ON soli.id_solicitud = cony.id_solicitud " +
                    "INNER JOIN " + TBL_DIRECCIONES + " AS dcon ON cony.direccion_id = dcon.id_direccion AND dcon.tipo_direccion = 'CONYUGE' " +
                    "INNER JOIN " + TBL_ECONOMICOS_IND + " AS eco ON soli.id_solicitud = eco.id_solicitud " +
                    "INNER JOIN " + TBL_NEGOCIO_IND + " AS neg ON soli.id_solicitud = neg.id_solicitud " +
                    "INNER JOIN " + TBL_DIRECCIONES + " AS dneg ON neg.direccion_id = dneg.id_direccion AND dneg.tipo_direccion = 'NEGOCIO' " +
                    "INNER JOIN " + TBL_AVAL_IND + " AS aval ON soli.id_solicitud = aval.id_solicitud " +
                    "INNER JOIN " + TBL_DIRECCIONES + " AS daval ON aval.direccion_id = daval.id_direccion AND daval.tipo_direccion = 'AVAL' " +
                    "INNER JOIN " + TBL_REFERENCIA_IND + " AS refe ON soli.id_solicitud = refe.id_solicitud " +
                    "INNER JOIN " + TBL_DOCUMENTOS + " AS doc ON soli.id_solicitud = doc.id_solicitud " +
                    "WHERE soli.id_solicitud = "+id_solicitud + ((completado)?" AND soli.estatus = 1":"");

        Cursor row =  db.rawQuery( query, null);
        return row;
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

    public Cursor getCargoGrupo (String id_credito, int tipo){
        String tbl = (tipo == 1)?TBL_INTEGRANTES_GPO:TBL_INTEGRANTES_GPO_REN;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res =  db.rawQuery( "SELECT DISTINCT (cargo) from "+ tbl +" WHERE id_credito = " + id_credito, null);

        return res;
    }

    public Cursor customSelect (String table, String select, String where, String order, String[] args){
        SQLiteDatabase db = this.getReadableDatabase();
        Log.v("SQLXXX", "SELECT " + select + " FROM " + table + where + order);
        Cursor res =  db.rawQuery( "SELECT " + select + " FROM " + table + where + order, args );
        return res;
    }

}
