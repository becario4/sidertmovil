package com.sidert.sidertmovil.database;

import android.provider.BaseColumns;

import com.sidert.sidertmovil.utils.Miscellaneous;

import static com.sidert.sidertmovil.utils.Constants.COLONIAS;
import static com.sidert.sidertmovil.utils.Constants.ESTADOS;
import static com.sidert.sidertmovil.utils.Constants.FICHAS;
import static com.sidert.sidertmovil.utils.Constants.FICHAS_T;
import static com.sidert.sidertmovil.utils.Constants.GEOLOCALIZACION;
import static com.sidert.sidertmovil.utils.Constants.GEOLOCALIZACION_T;
import static com.sidert.sidertmovil.utils.Constants.GPO;
import static com.sidert.sidertmovil.utils.Constants.GPO_T;
import static com.sidert.sidertmovil.utils.Constants.IDENTIFICACIONES;
import static com.sidert.sidertmovil.utils.Constants.IND;
import static com.sidert.sidertmovil.utils.Constants.IND_T;
import static com.sidert.sidertmovil.utils.Constants.LOGIN_REPORT;
import static com.sidert.sidertmovil.utils.Constants.LOGIN_REPORT_T;
import static com.sidert.sidertmovil.utils.Constants.LOG_ASESSOR;
import static com.sidert.sidertmovil.utils.Constants.LOG_ASESSOR_T;
import static com.sidert.sidertmovil.utils.Constants.LOG_MANAGER;
import static com.sidert.sidertmovil.utils.Constants.LOG_MANAGER_T;
import static com.sidert.sidertmovil.utils.Constants.MUNICIPIOS;
import static com.sidert.sidertmovil.utils.Constants.OCUPACIONES;
import static com.sidert.sidertmovil.utils.Constants.SECTORES;
import static com.sidert.sidertmovil.utils.Constants.SINCRONIZADO;
import static com.sidert.sidertmovil.utils.Constants.SINCRONIZADO_T;
import static com.sidert.sidertmovil.utils.Constants.SOLICITUDES_IND;
import static com.sidert.sidertmovil.utils.Constants.SOLICITUDES_IND_T;
import static com.sidert.sidertmovil.utils.Constants.STATUS_FICHAS;
import static com.sidert.sidertmovil.utils.Constants.TBL_AMORTIZACIONES;
import static com.sidert.sidertmovil.utils.Constants.TBL_AMORTIZACIONES_T;
import static com.sidert.sidertmovil.utils.Constants.TBL_ARQUEO_CAJA;
import static com.sidert.sidertmovil.utils.Constants.TBL_ARQUEO_CAJA_T;
import static com.sidert.sidertmovil.utils.Constants.TBL_AVAL;
import static com.sidert.sidertmovil.utils.Constants.TBL_AVAL_T;
import static com.sidert.sidertmovil.utils.Constants.TBL_CANCELACIONES;
import static com.sidert.sidertmovil.utils.Constants.TBL_CARTERA_GPO;
import static com.sidert.sidertmovil.utils.Constants.TBL_CARTERA_GPO_T;
import static com.sidert.sidertmovil.utils.Constants.TBL_CARTERA_IND;
import static com.sidert.sidertmovil.utils.Constants.TBL_CARTERA_IND_T;
import static com.sidert.sidertmovil.utils.Constants.TBL_CIERRE_DIA_T;
import static com.sidert.sidertmovil.utils.Constants.TBL_CODIGOS_OXXO;
import static com.sidert.sidertmovil.utils.Constants.TBL_GEO_RESPUESTAS_T;
import static com.sidert.sidertmovil.utils.Constants.TBL_IMPRESIONES_VENCIDA;
import static com.sidert.sidertmovil.utils.Constants.TBL_IMPRESIONES_VENCIDA_T;
import static com.sidert.sidertmovil.utils.Constants.TBL_IMPRESIONES_VIGENTE;
import static com.sidert.sidertmovil.utils.Constants.TBL_IMPRESIONES_VIGENTE_T;
import static com.sidert.sidertmovil.utils.Constants.TBL_MIEMBROS_GPO;
import static com.sidert.sidertmovil.utils.Constants.TBL_MIEMBROS_GPO_T;
import static com.sidert.sidertmovil.utils.Constants.TBL_MIEMBROS_PAGOS;
import static com.sidert.sidertmovil.utils.Constants.TBL_MIEMBROS_PAGOS_T;
import static com.sidert.sidertmovil.utils.Constants.TBL_PAGOS;
import static com.sidert.sidertmovil.utils.Constants.TBL_PAGOS_IND;
import static com.sidert.sidertmovil.utils.Constants.TBL_PAGOS_IND_T;
import static com.sidert.sidertmovil.utils.Constants.TBL_PAGOS_T;
import static com.sidert.sidertmovil.utils.Constants.TBL_PRESTAMOS_GPO;
import static com.sidert.sidertmovil.utils.Constants.TBL_PRESTAMOS_GPO_T;
import static com.sidert.sidertmovil.utils.Constants.TBL_PRESTAMOS_IND;
import static com.sidert.sidertmovil.utils.Constants.TBL_PRESTAMOS_IND_T;
import static com.sidert.sidertmovil.utils.Constants.TBL_RECIBOS;
import static com.sidert.sidertmovil.utils.Constants.TBL_RECIBOS_VENCIDA;
import static com.sidert.sidertmovil.utils.Constants.TBL_RECIBOS_VENCIDA_T;
import static com.sidert.sidertmovil.utils.Constants.TBL_RECIBOS_VIGENTE;
import static com.sidert.sidertmovil.utils.Constants.TBL_RECIBOS_VIGENTE_T;
import static com.sidert.sidertmovil.utils.Constants.TBL_REIMPRESION;
import static com.sidert.sidertmovil.utils.Constants.TBL_REIMPRESION_T;
import static com.sidert.sidertmovil.utils.Constants.TBL_REIMPRESION_VIGENTE;
import static com.sidert.sidertmovil.utils.Constants.TBL_REIMPRESION_VIGENTE_T;
import static com.sidert.sidertmovil.utils.Constants.TBL_REPORTE_SESIONES;
import static com.sidert.sidertmovil.utils.Constants.TBL_RESPUESTAS_GPO;
import static com.sidert.sidertmovil.utils.Constants.TBL_RESPUESTAS_GPO_T;
import static com.sidert.sidertmovil.utils.Constants.TBL_RESPUESTAS_GPO_V_T;
import static com.sidert.sidertmovil.utils.Constants.TBL_RESPUESTAS_IND;
import static com.sidert.sidertmovil.utils.Constants.TBL_RESPUESTAS_IND_T;
import static com.sidert.sidertmovil.utils.Constants.TBL_RESPUESTAS_IND_V_T;
import static com.sidert.sidertmovil.utils.Constants.TBL_RESPUESTAS_INTEGRANTE_T;
import static com.sidert.sidertmovil.utils.Constants.TBL_SUCURSALES;
import static com.sidert.sidertmovil.utils.Constants.TBL_TRACKER_ASESOR;
import static com.sidert.sidertmovil.utils.Constants.TBL_TRACKER_ASESOR_T;

public class SidertTables {

    public static class SidertEntry implements BaseColumns {

        // == TABLE
        public static final String TABLE_NAME_LOG_ASESSOR  = LOG_ASESSOR;        //Tabla de Recuperación y Cobranza
        public static final String TABLE_NAME_LOG_MANAGER  = LOG_MANAGER;        //Tabla de Cartera Vencida
        public static final String TABLE_IND               = IND;                //Tabla para formularios individual vigente (RI, CI)
        public static final String TABLE_GPO               = GPO;                //Tabla para formularios grupales vigente (RG, CG)
        public static final String TABLE_FICHAS            = FICHAS;             //Tabla para todos los formularios RI, RG, CI, CG, CVI, CVG
        //public static final String TABLE_IND_VE          = IND_VE;             //Tabla para formularios individual vencida (CVI)
        //public static final String TABLE_GPO_VE          = GPO_VE;             //Tabla para formularios grupales vencida (CVG)
        public static final String TABLE_GEOLOCALIZACION   = GEOLOCALIZACION;    //Tabla para formularios de golocaliacion
        public static final String TABLE_LOGIN_REPORT      = LOGIN_REPORT;       //Tabla para timestamp de inicio de sesión
        public static final String TABLE_SINCRONIZADO      = SINCRONIZADO;       //Tabla para registrar el timestamp de fecha y hora de ultima sincronizacion
        public static final String TABLE_SOLICITUDES_IND   = SOLICITUDES_IND;    //Tabla para registrar las solicitudes de crédito
        public static final String TABLE_RECIBOS_VIGENTE   = TBL_RECIBOS_VIGENTE;
        public static final String TABLE_RECIBOS_VENCIDA   = TBL_RECIBOS_VENCIDA;
        public static final String TABLE_REIMPRESION       = TBL_REIMPRESION;

        //======================= TABLES TEST  =====================================================
        public static final String TABLE_LOG_ASESSOR_T     = LOG_ASESSOR_T;      //Tabla de Recuperación y Cobranza Pruebas
        public static final String TABLE_LOG_MANAGER_T     = LOG_MANAGER_T;      //Tabla de Cartera Vencida Pruebas
        public static final String TABLE_IND_T             = IND_T;              //Tabla para formularios individual vigente (RI, CI) Pruebas
        public static final String TABLE_GPO_T             = GPO_T;              //Tabla para formularios grupales vigente (RG, CG) Pruebas
        public static final String TABLE_FICHAS_T          = FICHAS_T;           //Tabla para todos los formularios RI, RG, CI, CG, CVI, CVG para pruebas
        //public static final String TABLE_IND_VE_T        = IND_VE_T;           //Tabla para formularios individual vencida (CVI) Pruebas
        //public static final String TABLE_GPO_VE_T        = GPO_VE_T;           //Tabla para formularios grupales vencida (CVG) Pruebas
        public static final String TABLE_GEOLOCALIZACION_T = GEOLOCALIZACION_T;  //Tabla para formularios de golocaliacion Pruebas
        public static final String TABLE_LOGIN_REPORT_T    = LOGIN_REPORT_T;     //Tabla para timestamp de inicio de sesión Pruebas
        public static final String TABLE_SINCRONIZADO_T    = SINCRONIZADO_T;     //Tabla para registrar el timestamp de fecha y hora de ultima sincronizacion Pruebas
        public static final String TABLE_SOLICITUDES_IND_T = SOLICITUDES_IND_T;  //Tabla para registrar las solicitudes de crédito Pruebas
        public static final String TABLE_RECIBOS_VIGENTE_T = TBL_RECIBOS_VIGENTE_T;
        public static final String TABLE_RECIBOS_VENCIDA_T = TBL_RECIBOS_VENCIDA_T;
        public static final String TABLE_REIMPRESION_T     = TBL_REIMPRESION_T;


        // ====================== TABLAS GENERALES  ========================================
        public static final String TABLE_STATUS_FICHAS      = STATUS_FICHAS;
        public static final String TABLE_ESTADOS            = ESTADOS;
        public static final String TABLE_MUNICIPIOS         = MUNICIPIOS;
        public static final String TABLE_COLONIAS           = COLONIAS;
        public static final String TABLE_OCUPACIONES        = OCUPACIONES;
        public static final String TABLE_SECTORES           = SECTORES;
        public static final String TABLE_IDENTIFICACIONES   = IDENTIFICACIONES;

        // == COLUMNS IMPRESSIONS LOG
        public static final String ASESOR_ID            = "asesor_id";
        public static final String FOLIO                = "folio";
        public static final String ASSESOR_ID           = "assesor_id";
        public static final String ASESOR_NOMBRE        = "asesor_nombre";
        public static final String EXTERNAL_ID          = "external_id";
        public static final String AMOUNT               = "amount";
        public static final String TYPE_IMPRESSION      = "type_impression";
        public static final String ERRORS               = "errors";
        public static final String GENERATED_AT         = "generated_at";
        public static final String SENT_AT              = "sent_at";
        public static final String STATUS               = "status";
        public static final String FORM                 = "form";
        public static final String FECHA_ASIGNACION     = "fecha_asig";
        public static final String NOMBRE_CLIENTE       = "nombre_cliente";
        public static final String NOMBRE               = "nombre";
        public static final String CLIENTE_OBJ          = "cliente_obj";
        public static final String AVAL_OBJ             = "aval_obj";
        public static final String PRESTAMO_OBJ         = "prestamo_obj";
        public static final String REPORTE_OMEGA_OBJ    = "reporte_omega_obj";
        public static final String TABLA_PAGOS_OBJ      = "tabla_pagos_obj";
        public static final String DIA_SEMANA           = "dia_semana";
        public static final String FECHA_PAGO           = "fecha_pago";
        public static final String FECHA_INI            = "fecha_ini";
        public static final String FECHA_TER            = "fecha_ter";
        public static final String FECHA_ENV            = "fecha_env";
        public static final String FECHA_CON            = "fecha_con";
        public static final String TIMESTAMP            = "timestamp";
        public static final String NOMBRE_GPO           = "nombre_gpo";
        public static final String CLAVE                = "clave";
        public static final String INTEGRANTES_OBJ      = "integrantes_obj";
        public static final String TOTAL_INTEGRANTES    = "TOTAL_INTEGRANTES";
        public static final String PRESIDENTA_OBJ        = "presidenta_obj";
        public static final String TESORERA_OBJ         = "tesorera_obj";
        public static final String SECRETARIA_OBJ       = "secretaria_obj";
        public static final String DATOS_OBJ            = "datos_obj";
        public static final String IMPRESION            = "impresion";
        public static final String RESPUESTA            = "respuesta";
        public static final String TIPO_FORM            = "tipo_form";
        public static final String NUM_SOLICITUD        = "num_solicitud";
        public static final String DATA                 = "data";
        public static final String FECHA_ENT            = "fecha_ent";
        public static final String FECHA_VEN            = "fecha_ven";
        public static final String DIRECCION            = "direccion";
        public static final String COLONIA              = "colonia";
        public static final String RES_UNO              = "res_uno";
        public static final String RES_DOS              = "res_dos";
        public static final String RES_TRES             = "res_tres";
        public static final String FECHA_ENV_UNO        = "fecha_env_uno";
        public static final String FECHA_ENV_DOS        = "fecha_env_dos";
        public static final String FECHA_ENV_TRES       = "fecha_env_tres";
        public static final String TIPO_FICHA           = "tipo_ficha";
        public static final String SERIE_ID             = "serie_id";
        public static final String LOGIN_TIMESTAMP      = "login_timestamp";
        public static final String FICHA_ID             = "ficha_id";
        public static final String CLIE_GPO_ID          = "clie_gpo_id";
        public static final String CLIE_GPO_CLV         = "clie_gpo_clv";
        public static final String TIPO                 = "tipo";
        public static final String ESTADO_ID            = "estado_id";
        public static final String ESTADO_NOMBRE        = "estado_nombre";
        public static final String PAIS_ID              = "pais_id";
        public static final String MUNICIPIO_ID         = "municipio_id";
        public static final String MUNICIPIO_NOMBRE     = "municipio_nombre";
        public static final String COLONIA_NOMBRE       = "colonia_nombre";
        public static final String COLONIA_ID           = "colonia_id";
        public static final String OCUPACION_ID         = "ocupacion_id";
        public static final String OCUPACION_NOMBRE     = "ocupacion_nombre";
        public static final String OCUPACION_CLAVE      = "ocupacion_clave";
        public static final String NIVEL_RIESGO         = "nivel_riesgo";
        public static final String SECTOR_ID            = "sector_id";
        public static final String SECTOR_NOMBRE        = "sector_nombre";
        public static final String CP                   = "cp";
        public static final String CREDITO              = "credito";
        public static final String PERSONALES           = "personales";
        public static final String CONYUGE              = "conyuge";
        public static final String ECONOMICOS           = "economicos";
        public static final String NEGOCIO              = "negocio";
        public static final String AVAL                 = "aval";
        public static final String REFERENCIA           = "referencia";
        public static final String CREATE_AT            = "create_at";
        public static final String IDENTIFICACION_ID    = "identificacion_id";
        public static final String IDENTIFICACION_NOMBRE = "identificacion_nombre";
        public static final String CLV_CLIENTE           = "clv_cliente";
        public static final String CLV_IMPRESION         = "clv_impresion";
        public static final String NUEVO_FOLIO           = "nuevo_folio";
        public static final String FOLIO_ANTERIOR        = "folio_anterior";
        public static final String INCIDENCIA            = "incidencia";


        // == QUERIES CREATE
        static final String CREATE_TABLE_ASESSORS   = "CREATE TABLE " + TABLE_NAME_LOG_ASESSOR + "("
                + SidertEntry._ID               + " INTEGER PRIMARY KEY,"
                + SidertEntry.FOLIO             + " INTEGER,"
                + SidertEntry.ASSESOR_ID        + " TEXT,"
                + SidertEntry.EXTERNAL_ID       + " TEXT,"
                + SidertEntry.AMOUNT            + " TEXT,"
                + SidertEntry.TYPE_IMPRESSION   + " TEXT,"
                + SidertEntry.ERRORS            + " TEXT,"
                + SidertEntry.GENERATED_AT      + " TEXT,"
                + SidertEntry.SENT_AT           + " TEXT,"
                + SidertEntry.STATUS            + " INTEGER)";

        static final String CREATE_TABLE_GESTORS   = "CREATE TABLE " + TABLE_NAME_LOG_MANAGER + "("
                + SidertEntry._ID               + " INTEGER PRIMARY KEY,"
                + SidertEntry.FOLIO             + " INTEGER,"
                + SidertEntry.ASSESOR_ID        + " TEXT,"
                + SidertEntry.EXTERNAL_ID       + " TEXT,"
                + SidertEntry.AMOUNT            + " TEXT,"
                + SidertEntry.TYPE_IMPRESSION   + " TEXT,"
                + SidertEntry.ERRORS            + " TEXT,"
                + SidertEntry.GENERATED_AT      + " TEXT,"
                + SidertEntry.SENT_AT           + " TEXT,"
                + SidertEntry.STATUS            + " INTEGER)";

        static final String CREATE_TABLE_FICHAS = "CREATE TABLE " + TABLE_FICHAS + "("
                + SidertEntry._ID               + " INTEGER PRIMARY KEY,"
                + SidertEntry.ASESOR_ID         + " TEXT,"
                + SidertEntry.EXTERNAL_ID       + " TEXT,"
                + SidertEntry.FORM              + " TEXT,"
                + SidertEntry.FECHA_ASIGNACION  + " TEXT,"
                + SidertEntry.DIA_SEMANA        + " TEXT,"
                + SidertEntry.FECHA_PAGO        + " TEXT,"
                + SidertEntry.PRESTAMO_OBJ      + " TEXT,"
                + SidertEntry.NOMBRE            + " TEXT,"
                + SidertEntry.CLAVE             + " TEXT,"
                + SidertEntry.DATOS_OBJ         + " TEXT,"
                + SidertEntry.AVAL_OBJ          + " TEXT,"
                + SidertEntry.PRESIDENTA_OBJ    + " TEXT,"
                + SidertEntry.TESORERA_OBJ      + " TEXT,"
                + SidertEntry.SECRETARIA_OBJ    + " TEXT,"
                + SidertEntry.REPORTE_OMEGA_OBJ + " TEXT,"
                + SidertEntry.TABLA_PAGOS_OBJ   + " TEXT,"
                + SidertEntry.IMPRESION         + " INTEGER,"
                + SidertEntry.FECHA_INI         + " TEXT,"
                + SidertEntry.FECHA_TER         + " TEXT,"
                + SidertEntry.FECHA_ENV         + " TEXT,"
                + SidertEntry.FECHA_CON         + " TEXT,"
                + SidertEntry.RESPUESTA         + " TEXT,"
                + SidertEntry.STATUS            + " INTEGER,"
                + SidertEntry.TIMESTAMP         + " TEXT)";

        static final String CREATE_TABLE_GEOLOCALIZACION = "CREATE TABLE " + TABLE_GEOLOCALIZACION + "("
                + SidertEntry._ID                + " INTEGER PRIMARY KEY,"
                + SidertEntry.FICHA_ID           + " TEXT,"
                + SidertEntry.ASESOR_NOMBRE      + " TEXT,"
                + SidertEntry.TIPO_FICHA         + " INTEGER,"
                + SidertEntry.NOMBRE             + " TEXT,"
                + SidertEntry.NUM_SOLICITUD      + " TEXT,"
                + SidertEntry.CLIE_GPO_ID        + " TEXT,"
                + SidertEntry.CLIE_GPO_CLV       + " TEXT,"
                + SidertEntry.DIRECCION          + " TEXT,"
                + SidertEntry.COLONIA            + " TEXT,"
                + SidertEntry.FECHA_ENT          + " TEXT,"
                + SidertEntry.FECHA_VEN          + " TEXT,"
                + SidertEntry.DATA               + " TEXT,"
                + SidertEntry.RES_UNO            + " TEXT,"
                + SidertEntry.RES_DOS            + " TEXT,"
                + SidertEntry.RES_TRES           + " TEXT,"
                + SidertEntry.FECHA_ENV_UNO      + " TEXT,"
                + SidertEntry.FECHA_ENV_DOS      + " TEXT,"
                + SidertEntry.FECHA_ENV_TRES     + " TEXT,"
                + SidertEntry.FECHA_ENV          + " TEXT,"
                + SidertEntry.FECHA_TER          + " TEXT,"
                + SidertEntry.STATUS             + " INTEGER,"
                + SidertEntry.CREATE_AT          + " TEXT)";

        static final String CREATE_TABLE_LOGIN_REPORT   = "CREATE TABLE " + TABLE_LOGIN_REPORT + "("
                + SidertEntry._ID               + " INTEGER PRIMARY KEY,"
                + SidertEntry.SERIE_ID          + " TEXT,"
                + SidertEntry.NOMBRE            + " TEXT,"
                + SidertEntry.LOGIN_TIMESTAMP   + " TEXT,"
                + SidertEntry.FECHA_ENV         + " TEXT,"
                + SidertEntry.STATUS            + " INTEGER)";

        static final String CREATE_TABLE_SINCRONIZADO   = "CREATE TABLE " + TABLE_SINCRONIZADO + "("
                + SidertEntry._ID               + " INTEGER PRIMARY KEY,"
                + SidertEntry.SERIE_ID          + " TEXT,"
                + SidertEntry.TIMESTAMP         + " TEXT)";

        static final String CREATE_TABLE_SOLICITUDES_IND   = "CREATE TABLE " + TABLE_SOLICITUDES_IND + "("
                + SidertEntry._ID               + " INTEGER PRIMARY KEY,"
                + SidertEntry.SERIE_ID          + " TEXT,"
                + SidertEntry.CREDITO           + " TEXT,"
                + SidertEntry.PERSONALES        + " TEXT,"
                + SidertEntry.CONYUGE           + " TEXT,"
                + SidertEntry.ECONOMICOS        + " TEXT,"
                + SidertEntry.NEGOCIO           + " TEXT,"
                + SidertEntry.AVAL              + " TEXT,"
                + SidertEntry.REFERENCIA        + " TEXT,"
                + SidertEntry.FECHA_INI         + " TEXT,"
                + SidertEntry.FECHA_TER         + " TEXT,"
                + SidertEntry.FECHA_ENV         + " TEXT,"
                + SidertEntry.STATUS            + " INTEGER)";

        static final String CREATE_TABLE_VIGENTE   = "CREATE TABLE " + TABLE_RECIBOS_VIGENTE + "("
                + SidertEntry._ID + " INTEGER PRIMARY KEY,"
                + SidertEntry.ASSESOR_ID + " TEXT,"
                + SidertEntry.FOLIO + " INTEGER,"
                + SidertEntry.TYPE_IMPRESSION + " TEXT,"
                + SidertEntry.AMOUNT + " TEXT,"
                + SidertEntry.EXTERNAL_ID + " TEXT,"
                + SidertEntry.ERRORS + " TEXT,"
                + SidertEntry.GENERATED_AT + " TEXT,"
                + SidertEntry.SENT_AT + " TEXT,"
                + SidertEntry.STATUS + " TEXT,"
                + SidertEntry.CLV_CLIENTE + " TEXT)";

        static final String CREATE_TABLE_VENCIDA   = "CREATE TABLE " + TABLE_RECIBOS_VENCIDA + "("
                + SidertEntry._ID + " INTEGER PRIMARY KEY,"
                + SidertEntry.ASSESOR_ID + " TEXT,"
                + SidertEntry.FOLIO + " INTEGER,"
                + SidertEntry.TYPE_IMPRESSION + " TEXT,"
                + SidertEntry.AMOUNT + " TEXT,"
                + SidertEntry.EXTERNAL_ID + " TEXT,"
                + SidertEntry.ERRORS + " TEXT,"
                + SidertEntry.GENERATED_AT + " TEXT,"
                + SidertEntry.SENT_AT + " TEXT,"
                + SidertEntry.STATUS + " TEXT,"
                + SidertEntry.CLV_CLIENTE + " TEXT)";

        public static final String CREATE_TABLE_REIMPRESION   = "CREATE TABLE " + TABLE_REIMPRESION + "("
                + SidertEntry._ID + " INTEGER PRIMARY KEY,"
                + SidertEntry.EXTERNAL_ID + " TEXT,"
                + SidertEntry.ASSESOR_ID + " TEXT,"
                + SidertEntry.SERIE_ID + " TEXT,"
                + SidertEntry.CLV_IMPRESION + " TEXT,"
                + SidertEntry.GENERATED_AT + " TEXT,"
                + SidertEntry.SENT_AT + " TEXT,"
                + SidertEntry.TYPE_IMPRESSION + " TEXT,"
                + SidertEntry.AMOUNT + " TEXT,"
                + SidertEntry.NUEVO_FOLIO + " INTEGER,"
                + SidertEntry.FOLIO_ANTERIOR + " INTEGER,"
                + SidertEntry.INCIDENCIA + " TEXT,"
                + SidertEntry.CLV_CLIENTE + " TEXT,"
                + SidertEntry.STATUS + " INTEGER)";

        public static final String CREATE_TBL_CARTERA_IND = "CREATE TABLE " + TBL_CARTERA_IND + "(" +
                "_id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "id_cartera TEXT," +
                "clave TEXT," +
                "nombre TEXT," +
                "direccion TEXT," +
                "asesor_nombre TEXT," +
                "serie_id TEXT," +
                "is_ruta INTEGER," +
                "ruta_obligado INTEGER," +
                "usuario_id TEXT," +
                "dia TEXT," +
                "num_solicitud TEXT," +
                "fecha_dispositivo TEXT," +
                "fecha_actualizado TEXT," +
                "colonia TEXT)";

        public static final String CREATE_TBL_CARTERA_GPO = "CREATE TABLE " + TBL_CARTERA_GPO + "(" +
                "_id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "id_cartera TEXT," +
                "clave TEXT," +
                "nombre TEXT," +
                "tesorera TEXT," +
                "direccion TEXT," +
                "asesor_nombre TEXT," +
                "serie_id TEXT," +
                "is_ruta INTEGER," +
                "ruta_obligado INTEGER," +
                "usuario_id TEXT," +
                "dia TEXT," +
                "num_solicitud TEXT," +
                "fecha_dispositivo TEXT," +
                "fecha_actualizado TEXT," +
                "colonia TEXT)";

        public static final String CREATE_TBL_PRESTAMOS_IND = "CREATE TABLE " + TBL_PRESTAMOS_IND + " (" +
                "_id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "id_prestamo TEXT," +
                "id_cliente TEXT," +
                "num_prestamo TEXT," +
                "num_solicitud TEXT," +
                "fecha_entrega TEXT," +
                "monto_otorgado TEXT," +
                "monto_total TEXT," +
                "monto_amortizacion TEXT," +
                "monto_requerido TEXT," +
                "num_amortizacion INTEGER," +
                "fecha_establecida TEXT," +
                "tipo_cartera TEXT," +
                "pagada INTEGER," +
                "fecha_dispositivo TEXT," +
                "fecha_actualizado TEXT)";

        public static final String CREATE_TBL_AMORTIZACIONES = "CREATE TABLE " + TBL_AMORTIZACIONES +" (" +
                "_id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "id_amortizacion TEXT," +
                "id_prestamo TEXT," +
                "fecha TEXT," +
                "fecha_pago TEXT," +
                "capital TEXT," +
                "interes TEXT," +
                "iva TEXT," +
                "comision TEXT," +
                "total TEXT," +
                "capital_pagado TEXT," +
                "interes_pagado TEXT," +
                "iva_pagado TEXT," +
                "interes_moratorio_pagado TEXT," +
                "iva_moratorio_pagado TEXT," +
                "comision_pagada TEXT," +
                "total_pagado TEXT," +
                "pagado TEXT," +
                "numero INTEGER," +
                "dias_atraso TEXT," +
                "fecha_dispositivo TEXT," +
                "fecha_actualizado TEXT)";

        public static final String CREATE_TBL_PAGOS_IND = "CREATE TABLE " + TBL_PAGOS_IND + " (" +
                "_id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "id_prestamo TEXT," +
                "fecha TEXT," +
                "monto TEXT," +
                "banco TEXT," +
                "fecha_dispositivo TEXT," +
                "fecha_actualizado TEXT)";
        
        public static final String CREATE_TBL_AVAL =  "CREATE TABLE " + TBL_AVAL + "(" +
                "_id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "id_prestamo TEXT," +
                "id_aval TEXT," +
                "nombre TEXT," +
                "parentesco TEXT," +
                "direccion TEXT," +
                "telefono TEXT," +
                "fecha_dispositivo TEXT," +
                "ficha_actualizado TEXT)";

        public static final String CREATE_TBL_RESPUESTAS_IND = "CREATE TABLE " + TBL_RESPUESTAS_IND + " (" +
                "_id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "id_prestamo TEXT," +
                "latitud TEXT," +
                "longitud TEXT," +
                "contacto TEXT," +
                "motivo_aclaracion TEXT," +
                "comentario TEXT," +
                "actualizar_telefono TEXT," +
                "nuevo_telefono TEXT," +
                "resultado_gestion TEXT," +
                "motivo_no_pago TEXT," +
                "fecha_fallecimiento TEXT," +
                "medio_pago TEXT," +
                "fecha_pago TEXT," +
                "pagara_requerido TEXT," +
                "pago_realizado TEXT," +
                "imprimir_recibo TEXT," +
                "folio TEXT," +
                "evidencia TEXT," +
                "tipo_imagen TEXT," +
                "gerente TEXT," +
                "firma TEXT," +
                "fecha_inicio TEXT," +
                "fecha_fin TEXT," +
                "fecha_envio TEXT," +
                "estatus TEXT," +
                "res_impresion TEXT DEFAULT \"0\")";

        public static final String CREATE_TBL_RESPUESTAS_GPO = "CREATE TABLE " + TBL_RESPUESTAS_GPO + " (" +
                "_id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "id_prestamo TEXT," +
                "latitud TEXT," +
                "longitud TEXT," +
                "contacto TEXT," +
                "motivo_aclaracion TEXT," +
                "comentario TEXT," +
                "actualizar_telefono TEXT," +
                "nuevo_telefono TEXT," +
                "resultado_gestion TEXT," +
                "motivo_no_pago TEXT," +
                "fecha_fallecimiento TEXT," +
                "medio_pago TEXT," +
                "fecha_pago TEXT," +
                "detalle_ficha TEXT," +
                "pago_realizado TEXT," +
                "imprimir_recibo TEXT," +
                "folio TEXT," +
                "evidencia TEXT," +
                "tipo_imagen TEXT," +
                "gerente TEXT," +
                "firma TEXT," +
                "fecha_inicio TEXT," +
                "fecha_fin TEXT," +
                "fecha_envio TEXT," +
                "estatus TEXT," +
                "res_impresion TEXT DEFAULT '0'," +
                "arqueo_caja TEXT DEFAULT '0'," +
                "estatus_pago TEXT DEFAULT '',"+
                "saldo_corde TEXT DEFAULT '',"+
                "saldo_actual TEXT DEFAULT '')";

        public static final String CREATE_TBL_PRESTAMOS_GPO = "CREATE TABLE " + TBL_PRESTAMOS_GPO + " (" +
                "_id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "id_prestamo TEXT," +
                "id_grupo TEXT," +
                "num_prestamo TEXT," +
                "num_solicitud TEXT," +
                "fecha_entrega TEXT," +
                "monto_otorgado TEXT," +
                "monto_total TEXT," +
                "monto_amortizacion TEXT," +
                "monto_requerido TEXT," +
                "num_amortizacion INTEGER," +
                "fecha_establecida TEXT," +
                "tipo_cartera TEXT," +
                "pagada INTEGER," +
                "fecha_dispositivo TEXT," +
                "fecha_actualizado TEXT)";

        public static final String CREATE_TBL_MIEMBROS = "CREATE TABLE " + TBL_MIEMBROS_GPO + " (" +
                "_id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "id_prestamo TEXT," +
                "id_integrante TEXT," +
                "num_solicitud TEXT," +
                "id_grupo TEXT," +
                "nombre TEXT," +
                "direccion TEXT," +
                "tel_casa TEXT," +
                "tel_celular TEXT," +
                "tipo_integrante TEXT," +
                "monto_prestamo TEXT," +
                "monto_requerido TEXT," +
                "fecha_dispositivo TEXT," +
                "fecha_actualizado TEXT," +
                "clave TEXT," +
                "id_prestamo_integrante TEXT)";

        public static final String CREATE_TBL_PAGOS = "CREATE TABLE " + TBL_PAGOS + " (" +
                "_id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "id_prestamo TEXT," +
                "fecha TEXT," +
                "monto TEXT," +
                "banco TEXT," +
                "fecha_dispositivo TEXT," +
                "fecha_actualizado TEXT)";

        public static final String CREATE_TBL_MIEMBROS_PAGOS = "CREATE TABLE " + TBL_MIEMBROS_PAGOS + " (" +
                "_id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "id_integrante TEXT," +
                "id_prestamo TEXT," +
                "id_gestion TEXT," +
                "nombre TEXT," +
                "monto_requerido TEXT," +
                "pago_realizado TEXT," +
                "adelanto TEXT," +
                "solidario TEXT," +
                "pago_requerido TEXT)";

        static final String CREATE_TBL_IMPRESIONES_VIGENTE = "CREATE TABLE " + TBL_IMPRESIONES_VIGENTE + " (" +
                "_id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "num_prestamo_id_gestion TEXT," +
                "asesor_id TEXT," +
                "folio INTEGER," +
                "tipo_impresion TEXT," +
                "monto TEXT," +
                "clave_cliente TEXT," +
                "create_at TEXT," +
                "sent_at TEXT," +
                "estatus INTEGER)";

        public static final String CREATE_TBL_IMPRESIONES_VENCIDA = "CREATE TABLE " + TBL_IMPRESIONES_VENCIDA + " (" +
                "_id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "num_prestamo_id_gestion TEXT," +
                "asesor_id TEXT," +
                "folio INTEGER," +
                "tipo_impresion TEXT," +
                "monto TEXT," +
                "clave_cliente TEXT," +
                "create_at TEXT," +
                "sent_at TEXT," +
                "estatus INTEGER," +
                "num_prestamo TEXT)";

        static final String CREATE_TBL_REIMPRESION_VIGENTE = "CREATE TABLE " + TBL_REIMPRESION_VIGENTE + " (" +
                "_id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "num_prestamo_id_gestion TEXT," +
                "tipo_reimpresion TEXT," +
                "folio INTEGER," +
                "monto TEXT," +
                "clv_cliente TEXT," +
                "asesor_id TEXT," +
                "serie_id TEXT," +
                "create_at TEXT," +
                "sent_at TEXT," +
                "estatus INTEGER)";

        static final String CREATE_TBL_ARQUEO_CAJA = "CREATE TABLE " + TBL_ARQUEO_CAJA + " (" +
                "_id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "id_gestion TEXT," +
                "mil TEXT," +
                "quinientos TEXT," +
                "doscientos TEXT," +
                "cien TEXT," +
                "cincuenta TEXT," +
                "veinte TEXT," +
                "diez TEXT," +
                "cinco TEXT," +
                "dos TEXT," +
                "peso TEXT," +
                "c_cincuenta TEXT," +
                "c_veinte TEXT," +
                "c_diez TEXT)";

        static final String CREATE_TBL_TRACKER_ASESOR = "CREATE TABLE " + TBL_TRACKER_ASESOR + " (" +
                "_id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "asesor_id TEXT," +
                "serie_id TEXT," +
                "nombre TEXT," +
                "latitud TEXT," +
                "longitud TEXT," +
                "bateria TEXT," +
                "created_at TEXT," +
                "sended_at TEXT," +
                "estatus TEXT)";

        //=============================== TABLES TEST  ===============================================
        static final String CREATE_TABLE_ASESSORS_T = "CREATE TABLE " + TABLE_LOG_ASESSOR_T + "("
                + SidertEntry._ID               + " INTEGER PRIMARY KEY,"
                + SidertEntry.FOLIO             + " INTEGER,"
                + SidertEntry.ASSESOR_ID        + " TEXT,"
                + SidertEntry.EXTERNAL_ID       + " TEXT,"
                + SidertEntry.AMOUNT            + " TEXT,"
                + SidertEntry.TYPE_IMPRESSION   + " TEXT,"
                + SidertEntry.ERRORS            + " TEXT,"
                + SidertEntry.GENERATED_AT      + " TEXT,"
                + SidertEntry.SENT_AT           + " TEXT,"
                + SidertEntry.STATUS            + " INTEGER)";

        static final String CREATE_TABLE_GESTORS_T = "CREATE TABLE " + TABLE_LOG_MANAGER_T + "("
                + SidertEntry._ID               + " INTEGER PRIMARY KEY,"
                + SidertEntry.FOLIO             + " INTEGER,"
                + SidertEntry.ASSESOR_ID        + " TEXT,"
                + SidertEntry.EXTERNAL_ID       + " TEXT,"
                + SidertEntry.AMOUNT            + " TEXT,"
                + SidertEntry.TYPE_IMPRESSION   + " TEXT,"
                + SidertEntry.ERRORS            + " TEXT,"
                + SidertEntry.GENERATED_AT      + " TEXT,"
                + SidertEntry.SENT_AT           + " TEXT,"
                + SidertEntry.STATUS            + " INTEGER)";

        static final String CREATE_TABLE_FICHAS_T = "CREATE TABLE " + TABLE_FICHAS_T + "("
                + SidertEntry._ID               + " INTEGER PRIMARY KEY,"   //0
                + SidertEntry.ASESOR_ID         + " TEXT,"                  //1
                + SidertEntry.EXTERNAL_ID       + " TEXT,"                  //2
                + SidertEntry.FORM              + " TEXT,"                  //3
                + SidertEntry.FECHA_ASIGNACION  + " TEXT,"                  //4
                + SidertEntry.DIA_SEMANA        + " TEXT,"                  //5
                + SidertEntry.FECHA_PAGO        + " TEXT,"                  //6
                + SidertEntry.PRESTAMO_OBJ      + " TEXT,"                  //7
                + SidertEntry.NOMBRE            + " TEXT,"                  //8
                + SidertEntry.CLAVE             + " TEXT,"                  //9
                + SidertEntry.DATOS_OBJ         + " TEXT,"                  //10
                + SidertEntry.AVAL_OBJ          + " TEXT,"                  //11
                + SidertEntry.PRESIDENTA_OBJ    + " TEXT,"                  //12
                + SidertEntry.TESORERA_OBJ      + " TEXT,"                  //13
                + SidertEntry.SECRETARIA_OBJ    + " TEXT,"                  //14
                + SidertEntry.REPORTE_OMEGA_OBJ + " TEXT,"                  //15
                + SidertEntry.TABLA_PAGOS_OBJ   + " TEXT,"                  //16
                + SidertEntry.IMPRESION         + " INTEGER,"               //17
                + SidertEntry.FECHA_INI         + " TEXT,"                  //18
                + SidertEntry.FECHA_TER         + " TEXT,"                  //19
                + SidertEntry.FECHA_ENV         + " TEXT,"                  //20
                + SidertEntry.FECHA_CON         + " TEXT,"                  //21
                + SidertEntry.RESPUESTA         + " TEXT,"                  //22
                + SidertEntry.STATUS            + " INTEGER,"               //23
                + SidertEntry.TIMESTAMP         + " TEXT)";                 //24

        static final String CREATE_TABLE_GEOLOCALIZACION_T = "CREATE TABLE " + TABLE_GEOLOCALIZACION_T + "("
                + SidertEntry._ID                + " INTEGER PRIMARY KEY,"
                + SidertEntry.FICHA_ID           + " TEXT,"
                + SidertEntry.ASESOR_NOMBRE      + " TEXT,"
                + SidertEntry.TIPO_FICHA         + " INTEGER,"
                + SidertEntry.NOMBRE             + " TEXT,"
                + SidertEntry.NUM_SOLICITUD      + " TEXT,"
                + SidertEntry.CLIE_GPO_ID        + " TEXT,"
                + SidertEntry.CLIE_GPO_CLV       + " TEXT,"
                + SidertEntry.DIRECCION          + " TEXT,"
                + SidertEntry.COLONIA            + " TEXT,"
                + SidertEntry.FECHA_ENT          + " TEXT,"
                + SidertEntry.FECHA_VEN          + " TEXT,"
                + SidertEntry.DATA               + " TEXT,"
                + SidertEntry.RES_UNO            + " TEXT,"
                + SidertEntry.RES_DOS            + " TEXT,"
                + SidertEntry.RES_TRES           + " TEXT,"
                + SidertEntry.FECHA_ENV_UNO      + " TEXT,"
                + SidertEntry.FECHA_ENV_DOS      + " TEXT,"
                + SidertEntry.FECHA_ENV_TRES     + " TEXT,"
                + SidertEntry.FECHA_ENV          + " TEXT,"
                + SidertEntry.FECHA_TER          + " TEXT,"
                + SidertEntry.STATUS             + " INTEGER,"
                + SidertEntry.CREATE_AT          + " TEXT)";

        static final String CREATE_TABLE_LOGIN_REPORT_T   = "CREATE TABLE " + TABLE_LOGIN_REPORT_T + "("
                + SidertEntry._ID               + " INTEGER PRIMARY KEY,"
                + SidertEntry.SERIE_ID          + " TEXT,"
                + SidertEntry.NOMBRE            + " TEXT,"
                + SidertEntry.LOGIN_TIMESTAMP   + " TEXT,"
                + SidertEntry.FECHA_ENV         + " TEXT,"
                + SidertEntry.STATUS            + " INTEGER)";

        static final String CREATE_TABLE_SOLICITUDES_IND_T   = "CREATE TABLE " + TABLE_SOLICITUDES_IND_T + "("
                + SidertEntry._ID               + " INTEGER PRIMARY KEY,"
                + SidertEntry.SERIE_ID          + " TEXT,"
                + SidertEntry.CREDITO           + " TEXT,"
                + SidertEntry.PERSONALES        + " TEXT,"
                + SidertEntry.CONYUGE           + " TEXT,"
                + SidertEntry.ECONOMICOS        + " TEXT,"
                + SidertEntry.NEGOCIO           + " TEXT,"
                + SidertEntry.AVAL              + " TEXT,"
                + SidertEntry.REFERENCIA        + " TEXT,"
                + SidertEntry.FECHA_INI         + " TEXT,"
                + SidertEntry.FECHA_TER         + " TEXT,"
                + SidertEntry.FECHA_ENV         + " TEXT,"
                + SidertEntry.STATUS            + " INTEGER)";

        static final String CREATE_TABLE_SINCRONIZADO_T   = "CREATE TABLE " + TABLE_SINCRONIZADO_T + "("
                + SidertEntry._ID               + " INTEGER PRIMARY KEY,"
                + SidertEntry.SERIE_ID          + " TEXT,"
                + SidertEntry.TIMESTAMP         + " TEXT)";

        static final String CREATE_TABLE_SOLICITUDES_T = "CREATE TABLE solicitudes_t ( " +
                "id_solicitud INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "serie_id TEXT, " +
                "tipo_solicitud INTEGER," +
                "estatus INTEGER, " +
                "id_originacion INTEGER, " +
                "nombre TEXT, " +
                "fecha_inicio TEXT, " +
                "fecha_termino TEXT, " +
                "fecha_envio TEXT, " +
                "fecha_dispositivo TEXT, " +
                "fecha_guardado TEXT)";

        static final String CREATE_TABLE_DATOS_CREDITO_IND_T = "CREATE TABLE datos_credito_ind_t (" +
                "id_credito INTEGER PRIMARY KEY AUTOINCREMENT," +
                "id_solicitud INTEGER," +
                "plazo TEXT," +
                "periodicidad TEXT," +
                "fecha_desembolso TEXT," +
                "dia_desembolso TEXT," +
                "hora_visita TEXT," +
                "monto_prestamo TEXT," +
                "destino TEXT," +
                "estatus_completado INTEGER)";

        static final String CREATE_TABLE_CLIENTE_IND_T = "CREATE TABLE datos_cliente_ind_t (" +
                "id_cliente INTEGER PRIMARY KEY AUTOINCREMENT," +
                "id_solicitud INTEGER," +
                "nombre TEXT," +
                "paterno TEXT," +
                "materno TEXT," +
                "fecha_nacimiento TEXT," +
                "edad TEXT," +
                "genero INTEGER," +
                "estado_nacimiento TEXT," +
                "rfc TEXT," +
                "homoclave TEXT," +
                "curp TEXT," +
                "curp_digito_veri TEXT," +
                "ocupacion TEXT," +
                "actividad_economica TEXT," +
                "tipo_identificacion TEXT," +
                "no_identificacion TEXT," +
                "nivel_estudio TEXT," +
                "estado_civil TEXT," +
                "bienes INTEGER," +
                "tipo_vivienda TEXT," +
                "parentesco TEXT," +
                "otro_tipo_vivienda TEXT," +
                "latitud TEXT," +
                "longitud TEXT," +
                "calle TEXT," +
                "no_exterior TEXT," +
                "no_interior TEXT," +
                "manzana TEXT," +
                "lote TEXT," +
                "cp TEXT," +
                "colonia TEXT," +
                "tel_casa TEXT," +
                "tel_celular TEXT," +
                "tel_mensajes TEXT," +
                "tel_trabajo TEXT," +
                "tiempo_vivir_sitio INTEGER," +
                "dependientes TEXT," +
                "medio_contacto TEXT," +
                "email TEXT," +
                "foto_fachada TEXT," +
                "ref_domiciliaria TEXT," +
                "firma TEXT," +
                "estatus_rechazo INTEGER," +
                "comentario_rechazo TEXT," +
                "estatus_completado INTEGER)";
        
        static final String CREATE_TABLE_CONYUGE_IND_T = "CREATE TABLE datos_conyuge_ind_t (" +
                "id_conyuge INTEGER PRIMARY KEY AUTOINCREMENT," +
                "id_solicitud INTEGER," +
                "nombre TEXT," +
                "paterno TEXT," +
                "materno TEXT," +
                "ocupacion TEXT," +
                "tel_celular TEXT," +
                "estatus_completado INTEGER)";

        static final String CREATE_TABLE_ECONOMICOS_IND_T = "CREATE TABLE datos_economicos_ind_t (" +
                "id_economico INTEGER PRIMARY KEY AUTOINCREMENT," +
                "id_solicitud INTEGER," +
                "propiedades TEXT," +
                "valor_aproximado TEXT," +
                "ubicacion TEXT," +
                "ingreso TEXT," +
                "estatus_completado INTEGER)";

        static final String CREATE_TABLE_NEGOCIO_IND_T = "CREATE TABLE datos_negocio_ind_t (" +
                "id_negocio INTEGER PRIMARY KEY AUTOINCREMENT," +
                "id_solicitud INTEGER," +
                "nombre TEXT," +
                "latitud TEXT," +
                "longitud TEXT," +
                "calle TEXT," +
                "no_exterior TEXT," +
                "no_interior TEXT," +
                "manzana TEXT," +
                "lote TEXT," +
                "cp TEXT," +
                "colonia TEXT," +
                "actividad_economica TEXT," +
                "antiguedad INTEGER," +
                "ing_mensual TEXT," +
                "ing_otros TEXT," +
                "gasto_semanal TEXT," +
                "gasto_agua TEXT," +
                "gasto_luz TEXT," +
                "gasto_telefono TEXT," +
                "gasto_renta TEXT," +
                "gasto_otros TEXT," +
                "capacidad_pago TEXT," +
                "dias_venta TEXT," +
                "foto_fachada TEXT," +
                "ref_domiciliaria TEXT," +
                "estatus_completado INTEGER)";
        
        static final String CREATE_TABLE_AVAL_IND_T = "CREATE TABLE datos_aval_ind_t (" +
                "id_aval INTEGER PRIMARY KEY AUTOINCREMENT," +
                "id_solicitud INTEGER," +
                "nombre TEXT," +
                "paterno TEXT," +
                "materno TEXT," +
                "fecha_nacimiento TEXT," +
                "edad TEXT," +
                "genero INTEGER," +
                "estado_nacimiento TEXT," +
                "rfc TEXT," +
                "homoclave TEXT," +
                "curp TEXT," +
                "curp_digito_veri TEXT," +
                "tipo_identificacion TEXT," +
                "no_identificacion TEXT," +
                "ocupacion TEXT," +
                "actividad_economica TEXT," +
                "latitud TEXT," +
                "longitud TEXT," +
                "calle TEXT," +
                "no_exterior TEXT," +
                "no_interior TEXT," +
                "manzana TEXT," +
                "lote TEXT," +
                "cp TEXT," +
                "colonia TEXT," +
                "tipo_vivienda TEXT," +
                "nombre_titular TEXT," +
                "parentesco TEXT," +
                "ing_mensual TEXT," +
                "ing_otros TEXT," +
                "gasto_semanal TEXT," +
                "gasto_agua TEXT," +
                "gasto_luz TEXT," +
                "gasto_telefono TEXT," +
                "gasto_renta TEXT," +
                "gasto_otros TEXT," +
                "horario_localizacion TEXT," +
                "antiguedad INTEGER," +
                "tel_casa TEXT," +
                "tel_celular TEXT," +
                "foto_fachada TEXT," +
                "ref_domiciliaria TEXT," +
                "firma TEXT," +
                "estatus_rechazo INTEGER," +
                "comentario_rechazo TEXT," +
                "estatus_completado INTEGER)";
        
        static final String CREATE_TABLE_REFERENCIA_IND_T = "CREATE TABLE datos_referencia_ind_t (" +
                "id_referencia INTEGER PRIMARY KEY AUTOINCREMENT," +
                "id_solicitud INTEGER," +
                "nombre TEXT," +
                "paterno TEXT," +
                "materno TEXT," +
                "calle TEXT," +
                "cp TEXT," +
                "colonia TEXT," +
                "municipio TEXT," +
                "tel_celular TEXT," +
                "estatus_completado INTEGER)";

        static final String CREATE_TBL_DOCUMENTOS_CLIENTES_T = "CREATE TABLE documentos_t (" +
                "id_documento INTEGER PRIMARY KEY AUTOINCREMENT," +
                "id_solicitud INTEGER," +
                "ine_frontal TEXT," +
                "ine_reverso TEXT," +
                "curp TEXT," +
                "comprobante TEXT," +
                "codigo_barras TEXT," +
                "firma_asesor TEXT," +
                "estatus_completado INTEGER)";

        static final String CREATE_TBL_DATOS_CREDITO_GPO_T = "CREATE TABLE datos_credito_gpo_t (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "id_solicitud INTEGER," +
                "nombre_grupo TEXT," +
                "plazo TEXT," +
                "periodicidad TEXT," +
                "fecha_desembolso TEXT," +
                "dia_desembolso TEXT," +
                "hora_visita TEXT," +
                "estatus_completado INTEGER)";
        
        static final String CREATE_TBL_INTEGRANTES_GPO_T = "CREATE TABLE datos_integrantes_gpo_t (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "id_credito INTEGER," +
                "cargo INTEGER," +
                "nombre TEXT," +
                "paterno TEXT," +
                "materno TEXT," +
                "fecha_nacimiento TEXT," +
                "edad TEXT," +
                "genero INTEGER," +
                "estado_nacimiento TEXT," +
                "rfc TEXT," +
                "curp TEXT," +
                "curp_digito_veri TEXT," +
                "tipo_identificacion TEXT," +
                "no_identificacion TEXT," +
                "nivel_estudio TEXT," +
                "estado_civil TEXT," +
                "bienes INTEGER," +
                "estatus_rechazo INTEGER," +
                "comentario_rechazo TEXT," +
                "estatus_completado INTEGER)";
        
        static final String CREATE_TBL_TELEFONOS_INTEGRANTE_T = "CREATE TABLE telefonos_integrante_t (" +
                "id_telefonico INTEGER PRIMARY KEY AUTOINCREMENT," +
                "id_integrante INTEGER," +
                "tel_casa TEXT," +
                "tel_celular TEXT," +
                "tel_mensaje TEXT," +
                "estatus_completado INTEGER)";

        static final String CREATE_TBL_DOMICILIO_INTEGRANTE_T = "CREATE TABLE domicilio_integrante_t (" +
                "id_domicilio INTEGER PRIMARY KEY AUTOINCREMENT," +
                "id_integrante INTEGER," +
                "latitud TEXT," +
                "longitud TEXT," +
                "calle TEXT," +
                "no_exterior TEXT," +
                "no_interior TEXT," +
                "manzana TEXT," +
                "lote TEXT," +
                "cp TEXT," +
                "colonia TEXT," +
                "tipo_vivienda TEXT," +
                "parentesco TEXT," +
                "otro_tipo_vivienda TEXT," +
                "tiempo_vivir_sitio TEXT," +
                "foto_fachada TEXT," +
                "ref_domiciliaria TEXT," +
                "estatus_completado INTEGER)";
        
        static final String CREATE_TBL_NEGOCIO_INTEGRANTE_T = "CREATE TABLE negocio_integrante_t (" +
                "id_negocio INTEGER PRIMARY KEY AUTOINCREMENT," +
                "id_integrante INTEGER," +
                "nombre TEXT," +
                "latitud TEXT," +
                "longitud TEXT," +
                "calle TEXT," +
                "no_exterior TEXT," +
                "no_interior TEXT," +
                "manzana TEXT," +
                "lote TEXT," +
                "cp TEXT," +
                "colonia TEXT," +
                "actividad_economica TEXT," +
                "antiguedad TEXT," +
                "ing_mensual TEXT," +
                "ing_otros TEXT," +
                "gasto_semanal TEXT," +
                "capacidad_pago TEXT," +
                "foto_fachada TEXT," +
                "ref_domiciliaria TEXT," +
                "estatus_rechazo INTEGER," +
                "comentario_rechazo TEXT," +
                "estatus_completado INTEGER)";

        static final String CREATE_TBL_CONYUGE_INTEGRANTE_T = "CREATE TABLE conyuge_integrante_t (" +
                "id_conyuge INTEGER PRIMARY KEY AUTOINCREMENT," +
                "id_integrante INTEGER," +
                "nombre TEXT," +
                "paterno TEXT," +
                "materno TEXT," +
                "ocupacion TEXT," +
                "tel_celular TEXT," +
                "ingresos TEXT," +
                "estatus_completado INTEGER)";
                
        static final String CREATE_TBL_OTROS_DATOS_INTEGRANTE_T = "CREATE TABLE otros_datos_integrante_t (" +
                "id_otro INTEGER PRIMARY KEY AUTOINCREMENT," +
                "id_integrante INTEGER," +
                "medio_contacto TEXT," +
                "email TEXT," +
                "estatus_integrante INTEGER," +
                "monto_solicitado TEXT," +
                "casa_reunion INTEGER," +
                "firma TEXT," +
                "estatus_completado INTEGER)";

        static final String CREATE_TBL_DOCUMENTOS_INTEGRANTE_T = "CREATE TABLE documentos_integrante_t (" +
                "id_documento INTEGER PRIMARY KEY AUTOINCREMENT," +
                "id_integrante INTEGER," +
                "ine_frontal TEXT," +
                "ine_reverso TEXT," +
                "curp TEXT," +
                "comprobante TEXT," +
                "estatus_completado INTEGER)";

        static final String CREATE_TABLE_VIGENTE_T   = "CREATE TABLE " + TABLE_RECIBOS_VIGENTE_T + "("
                + SidertEntry._ID + " INTEGER PRIMARY KEY,"
                + SidertEntry.ASSESOR_ID + " TEXT,"
                + SidertEntry.FOLIO + " INTEGER,"
                + SidertEntry.TYPE_IMPRESSION + " TEXT,"
                + SidertEntry.AMOUNT + " TEXT,"
                + SidertEntry.EXTERNAL_ID + " TEXT,"
                + SidertEntry.ERRORS + " TEXT,"
                + SidertEntry.GENERATED_AT + " TEXT,"
                + SidertEntry.SENT_AT + " TEXT,"
                + SidertEntry.STATUS + " TEXT,"
                + SidertEntry.CLV_CLIENTE + " TEXT)";

        static final String CREATE_TABLE_VENCIDA_T   = "CREATE TABLE " + TABLE_RECIBOS_VENCIDA_T + "("
                + SidertEntry._ID + " INTEGER PRIMARY KEY,"
                + SidertEntry.ASSESOR_ID + " TEXT,"
                + SidertEntry.FOLIO + " INTEGER,"
                + SidertEntry.TYPE_IMPRESSION + " TEXT,"
                + SidertEntry.AMOUNT + " TEXT,"
                + SidertEntry.EXTERNAL_ID + " TEXT,"
                + SidertEntry.ERRORS + " TEXT,"
                + SidertEntry.GENERATED_AT + " TEXT,"
                + SidertEntry.SENT_AT + " TEXT,"
                + SidertEntry.STATUS + " TEXT,"
                + SidertEntry.CLV_CLIENTE + " TEXT)";

        public static final String CREATE_TABLE_REIMPRESION_T   = "CREATE TABLE " + TABLE_REIMPRESION_T + "("
                + SidertEntry._ID + " INTEGER PRIMARY KEY,"
                + SidertEntry.EXTERNAL_ID + " TEXT,"
                + SidertEntry.ASSESOR_ID + " TEXT,"
                + SidertEntry.SERIE_ID + " TEXT,"
                + SidertEntry.CLV_IMPRESION + " TEXT,"
                + SidertEntry.GENERATED_AT + " TEXT,"
                + SidertEntry.SENT_AT + " TEXT,"
                + SidertEntry.TYPE_IMPRESSION + " TEXT,"
                + SidertEntry.AMOUNT + " TEXT,"
                + SidertEntry.NUEVO_FOLIO + " INTEGER,"
                + SidertEntry.FOLIO_ANTERIOR + " INTEGER,"
                + SidertEntry.INCIDENCIA + " TEXT,"
                + SidertEntry.CLV_CLIENTE + " TEXT,"
                + SidertEntry.STATUS + " INTEGER)";
        
        public static final String CREATE_TABLE_RECIBOS_CIRCULO_CREDITO_T = "CREATE TABLE recibos_circulo_credito_t (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "asesor_id TEXT," +
                "tipo_impresion TEXT," +
                "nombre_cliente TEXT," +
                "folio INTEGER," +
                "fecha_impresion TEXT," +
                "fecha_envio TEXT," +
                "estatus INTEGER)";

        public static final String CREATE_TBL_RECIBOS = "CREATE TABLE "+TBL_RECIBOS+" (" +
                "_id INTEGER PRIMARY KEY AUTOINCREMENT,"+
	            "prestamo_id TEXT DEFAULT '',"+
                "asesor_id TEXT,"+
                "tipo_recibo TEXT," +
                "tipo_impresion TEXT," +
                "folio TEXT,"+
                "monto TEXT,"+
                "clave TEXT DEFAULT '',"+
                "nombre TEXT,"+
                "ap_paterno	TEXT,"+
                "ap_materno	TEXT,"+
                "fecha_impreso	TEXT,"+
                "fecha_envio TEXT,"+
                "estatus INTEGER);";
        
        public static final String CREATE_TBL_CARTERA_IND_T = "CREATE TABLE " + TBL_CARTERA_IND_T + " (" +
                "_id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "id_cartera TEXT," +
                "clave TEXT," +
                "nombre TEXT," +
                "direccion TEXT," +
                "asesor_nombre TEXT," +
                "serie_id TEXT," +
                "is_ruta INTEGER," +
                "ruta_obligado INTEGER," +
                "usuario_id TEXT," +
                "dia TEXT," +
                "num_solicitud TEXT," +
                "fecha_dispositivo TEXT," +
                "fecha_actualizado TEXT," +
                "colonia TEXT," +
                "geo_cliente INTEGER," +
                "geo_aval INTEGER," +
                "geo_negocio INTEGER," +
                "estatus TEXT DEFAULT '1')";

        public static final String CREATE_TBL_CARTERA_GPO_T = "CREATE TABLE " + TBL_CARTERA_GPO_T + " (" +
                "_id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "id_cartera TEXT," +
                "clave TEXT," +
                "nombre TEXT," +
                "tesorera TEXT," +
                "direccion TEXT," +
                "asesor_nombre TEXT," +
                "serie_id TEXT," +
                "is_ruta INTEGER," +
                "ruta_obligado INTEGER," +
                "usuario_id TEXT," +
                "dia TEXT," +
                "num_solicitud TEXT," +
                "fecha_dispositivo TEXT," +
                "fecha_actualizado TEXT," +
                "colonia TEXT," +
                "geolocalizadas TEXT," +
                "estatus TEXT DEFAULT '1')";
        
        public static final String CREATE_TBL_PRESTAMOS_IND_T = "CREATE TABLE " + TBL_PRESTAMOS_IND_T + " (" +
                "_id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "id_prestamo TEXT," +
                "id_cliente TEXT," +
                "num_prestamo TEXT," +
                "num_solicitud TEXT," +
                "fecha_entrega TEXT," +
                "monto_otorgado TEXT," +
                "monto_total TEXT," +
                "monto_amortizacion TEXT," +
                "monto_requerido TEXT," +
                "num_amortizacion INTEGER," +
                "fecha_establecida TEXT," +
                "tipo_cartera TEXT," +
                "pagada INTEGER," +
                "fecha_dispositivo TEXT," +
                "fecha_actualizado TEXT)";
        
        public static final String CREATE_TBL_AMORTIZACIONES_T = "CREATE TABLE " + TBL_AMORTIZACIONES_T +" (" +
                "_id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "id_amortizacion TEXT," +
                "id_prestamo TEXT," +
                "fecha TEXT," +
                "fecha_pago TEXT," +
                "capital TEXT," +
                "interes TEXT," +
                "iva TEXT," +
                "comision TEXT," +
                "total TEXT," +
                "capital_pagado TEXT," +
                "interes_pagado TEXT," +
                "iva_pagado TEXT," +
                "interes_moratorio_pagado TEXT," +
                "iva_moratorio_pagado TEXT," +
                "comision_pagada TEXT," +
                "total_pagado TEXT," +
                "pagado TEXT," +
                "numero INTEGER," +
                "dias_atraso TEXT," +
                "fecha_dispositivo TEXT," +
                "fecha_actualizado TEXT)";
        
        public static final String CREATE_TBL_PAGOS_IND_T = "CREATE TABLE " + TBL_PAGOS_IND_T + " (" +
                "_id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "id_prestamo TEXT," +
                "fecha TEXT," +
                "monto TEXT," +
                "banco TEXT," +
                "fecha_dispositivo TEXT," +
                "fecha_actualizado TEXT)";

        public static final String CREATE_TBL_AVAL_T =  "CREATE TABLE " + TBL_AVAL_T + "(" +
                "_id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "id_prestamo TEXT," +
                "id_aval TEXT," +
                "nombre TEXT," +
                "parentesco TEXT," +
                "direccion TEXT," +
                "telefono TEXT," +
                "fecha_dispositivo TEXT," +
                "fecha_actualizado TEXT)";
        
        public static final String CREATE_TBL_RESPUESTAS_IND_T = "CREATE TABLE " + TBL_RESPUESTAS_IND_T + " (" +
                "_id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "id_prestamo TEXT," +
                "latitud TEXT," +
                "longitud TEXT," +
                "contacto TEXT," +
                "motivo_aclaracion TEXT," +
                "comentario TEXT," +
                "actualizar_telefono TEXT," +
                "nuevo_telefono TEXT," +
                "resultado_gestion TEXT," +
                "motivo_no_pago TEXT," +
                "fecha_fallecimiento TEXT," +
                "medio_pago TEXT," +
                "fecha_pago TEXT," +
                "pagara_requerido TEXT," +
                "pago_realizado TEXT," +
                "imprimir_recibo TEXT," +
                "folio TEXT," +
                "evidencia TEXT," +
                "tipo_imagen TEXT," +
                "gerente TEXT," +
                "firma TEXT," +
                "fecha_inicio TEXT," +
                "fecha_fin TEXT," +
                "fecha_envio TEXT," +
                "estatus TEXT," +
                "res_impresion TEXT DEFAULT '0',"+
                "estatus_pago TEXT DEFAULT '',"+
                "saldo_corte TEXT DEFAULT '',"+
                "saldo_actual TEXT DEFAULT '',"+
                "dias_atraso TEXT DEFAULT '0')";

        public static final String CREATE_TBL_PRESTAMOS_GPO_T = "CREATE TABLE " + TBL_PRESTAMOS_GPO_T + " (" +
                "_id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "id_prestamo TEXT," +
                "id_grupo TEXT," +
                "num_prestamo TEXT," +
                "num_solicitud TEXT," +
                "fecha_entrega TEXT," +
                "monto_otorgado TEXT," +
                "monto_total TEXT," +
                "monto_amortizacion TEXT," +
                "monto_requerido TEXT," +
                "num_amortizacion INTEGER," +
                "fecha_establecida TEXT," +
                "tipo_cartera TEXT," +
                "pagada INTEGER," +
                "fecha_dispositivo TEXT," +
                "fecha_actualizado TEXT)";
        
        public static final String CREATE_TBL_MIEMBROS_T = "CREATE TABLE " + TBL_MIEMBROS_GPO_T + " (" +
                "_id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "id_prestamo TEXT," +
                "id_integrante TEXT," +
                "num_solicitud TEXT," +
                "id_grupo TEXT," +
                "nombre TEXT," +
                "direccion TEXT," +
                "tel_casa TEXT," +
                "tel_celular TEXT," +
                "tipo_integrante TEXT," +
                "monto_prestamo TEXT," +
                "monto_requerido TEXT," +
                "fecha_dispositivo TEXT," +
                "fecha_actualizado TEXT," +
                "clave TEXT," +
                "id_prestamo_integrante TEXT)";

        public static final String CREATE_TBL_PAGOS_T = "CREATE TABLE " + TBL_PAGOS_T + " (" +
                "_id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "id_prestamo TEXT," +
                "fecha TEXT," +
                "monto TEXT," +
                "banco TEXT," +
                "fecha_dispositivo TEXT," +
                "fecha_actualizado TEXT)";
        
        public static final String CREATE_TBL_RESPUESTAS_GPO_T = "CREATE TABLE " + TBL_RESPUESTAS_GPO_T + " (" +
                "_id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "id_prestamo TEXT," +
                "latitud TEXT," +
                "longitud TEXT," +
                "contacto TEXT," +
                "motivo_aclaracion TEXT," +
                "comentario TEXT," +
                "actualizar_telefono TEXT," +
                "nuevo_telefono TEXT," +
                "resultado_gestion TEXT," +
                "motivo_no_pago TEXT," +
                "fecha_fallecimiento TEXT," +
                "medio_pago TEXT," +
                "fecha_pago TEXT," +
                "detalle_ficha TEXT," +
                "pago_realizado TEXT," +
                "imprimir_recibo TEXT," +
                "folio TEXT," +
                "evidencia TEXT," +
                "tipo_imagen TEXT," +
                "gerente TEXT," +
                "firma TEXT," +
                "fecha_inicio TEXT," +
                "fecha_fin TEXT," +
                "fecha_envio TEXT," +
                "estatus TEXT," +
                "res_impresion TEXT DEFAULT '0'," +
                "arqueo_caja TEXT DEFAULT '0'," +
                "estatus_pago TEXT DEFAULT '',"+
                "saldo_corte TEXT DEFAULT '',"+
                "saldo_actual TEXT DEFAULT '',"+
                "dias_atraso TEXT DEFAULT '0')";

        public static final String CREATE_TBL_MIEMBROS_PAGOS_T = "CREATE TABLE " + TBL_MIEMBROS_PAGOS_T + " (" +
                "_id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "id_integrante TEXT," +
                "id_prestamo TEXT," +
                "id_gestion TEXT," +
                "nombre TEXT," +
                "monto_requerido TEXT," +
                "pago_realizado TEXT," +
                "adelanto TEXT," +
                "solidario TEXT," +
                "pago_requerido TEXT)";

        
        public static final String CREATE_TBL_IMPRESIONES_VIGENTE_T = "CREATE TABLE " + TBL_IMPRESIONES_VIGENTE_T + " (" +
                "_id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "num_prestamo_id_gestion TEXT," +
                "asesor_id TEXT," +
                "folio INTEGER," +
                "tipo_impresion TEXT," +
                "monto TEXT," +
                "clave_cliente TEXT," +
                "create_at TEXT," +
                "sent_at TEXT," +
                "estatus INTEGER," +
                "num_prestamo TEXT)";

        public static final String CREATE_TBL_IMPRESIONES_VENCIDA_T = "CREATE TABLE " + TBL_IMPRESIONES_VENCIDA_T + " (" +
                "_id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "num_prestamo_id_gestion TEXT," +
                "asesor_id TEXT," +
                "folio INTEGER," +
                "tipo_impresion TEXT," +
                "monto TEXT," +
                "clave_cliente TEXT," +
                "create_at TEXT," +
                "sent_at TEXT," +
                "estatus INTEGER," +
                "num_prestamo TEXT)";

        static final String CREATE_TBL_REIMPRESION_VIGENTE_T = "CREATE TABLE " + TBL_REIMPRESION_VIGENTE_T + " (" +
                "_id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "num_prestamo_id_gestion TEXT," +
                "tipo_reimpresion TEXT," +
                "folio INTEGER," +
                "monto TEXT," +
                "clv_cliente TEXT," +
                "asesor_id TEXT," +
                "serie_id TEXT," +
                "create_at TEXT," +
                "sent_at TEXT," +
                "estatus INTEGER," +
                "num_prestamo INTEGER)";
        
        static final String CREATE_TBL_ARQUEO_CAJA_T = "CREATE TABLE " + TBL_ARQUEO_CAJA_T + " (" +
                "_id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "id_gestion TEXT," +
                "mil TEXT," +
                "quinientos TEXT," +
                "doscientos TEXT," +
                "cien TEXT," +
                "cincuenta TEXT," +
                "veinte TEXT," +
                "diez TEXT," +
                "cinco TEXT," +
                "dos TEXT," +
                "peso TEXT," +
                "c_cincuenta TEXT," +
                "c_veinte TEXT," +
                "c_diez TEXT)";
        
        static final String CREATE_TBL_TRACKER_ASESOR_T = "CREATE TABLE " + TBL_TRACKER_ASESOR_T + " (" +
                "_id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "asesor_id TEXT," +
                "serie_id TEXT," +
                "nombre TEXT," +
                "latitud TEXT," +
                "longitud TEXT," +
                "bateria TEXT," +
                "created_at TEXT," +
                "sended_at TEXT," +
                "estatus TEXT)";

        static final String CREATE_TBL_GEO_RESPUESTAS = "CREATE TABLE geo (" +
                "_id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "id_cartera TEXT," +
                "num_solicitud TEXT," +
                "tipo_ficha INTEGER," +
                "tipo_geolocalizacion TEXT," +
                "id_integrante TEXT," +
                "nombre TEXT," +
                "direccion TEXT," +
                "latitud TEXT," +
                "longitud TEXT," +
                "direccion_capturada TEXT," +
                "codigo_barras TEXT," +
                "fachada TEXT," +
                "comentario TEXT," +
                "fecha_fin_geo TEXT," +
                "fecha_envio_geo TEXT," +
                "estatus INTEGER," +
                "clave TEXT)";

        public static final String CREATE_TBL_RESPUESTAS_IND_V_T = "CREATE TABLE " + TBL_RESPUESTAS_IND_V_T + " (" +
                "_id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "id_prestamo TEXT," +
                "latitud TEXT," +
                "longitud TEXT," +
                "contacto TEXT," +
                "comentario TEXT," +
                "actualizar_telefono TEXT," +
                "nuevo_telefono TEXT," +
                "resultado_gestion TEXT," +
                "motivo_no_pago TEXT," +
                "fecha_fallecimiento TEXT," +
                "fecha_monto_promesa TEXT," +
                "monto_promesa TEXT," +
                "medio_pago TEXT," +
                "fecha_pago TEXT," +
                "pagara_requerido TEXT," +
                "pago_realizado TEXT," +
                "imprimir_recibo TEXT," +
                "folio TEXT," +
                "evidencia TEXT," +
                "tipo_imagen TEXT," +
                "gerente TEXT," +
                "firma TEXT," +
                "fecha_inicio TEXT," +
                "fecha_fin TEXT," +
                "fecha_envio TEXT," +
                "estatus TEXT," +
                "res_impresion TEXT DEFAULT '0',"+
                "estatus_pago TEXT DEFAULT '',"+
                "saldo_corte TEXT DEFAULT '',"+
                "saldo_actual TEXT DEFAULT '',"+
                "dias_atraso TEXT DEFAULT '0',"+
                "serial_id TEXT DEFAULT '0')";

        public static final String CREATE_TBL_RESPUESTAS_INTEGRANTE_T = "CREATE TABLE " + TBL_RESPUESTAS_INTEGRANTE_T + " (" +
                "_id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "id_prestamo TEXT," +
                "id_integrante TEXT," +
                "latitud TEXT," +
                "longitud TEXT," +
                "contacto TEXT," +
                "comentario TEXT," +
                "actualizar_telefono TEXT," +
                "nuevo_telefono TEXT," +
                "resultado_gestion TEXT," +
                "motivo_no_pago TEXT," +
                "fecha_fallecimiento TEXT," +
                "fecha_monto_promesa TEXT," +
                "monto_promesa TEXT," +
                "medio_pago TEXT," +
                "fecha_pago TEXT," +
                "pagara_requerido TEXT," +
                "pago_realizado TEXT," +
                "imprimir_recibo TEXT," +
                "folio TEXT," +
                "evidencia TEXT," +
                "tipo_imagen TEXT," +
                "gerente TEXT," +
                "firma TEXT," +
                "fecha_inicio TEXT," +
                "fecha_fin TEXT," +
                "fecha_envio TEXT," +
                "estatus TEXT," +
                "res_impresion TEXT DEFAULT '0',"+
                "estatus_pago TEXT DEFAULT '',"+
                "saldo_corte TEXT DEFAULT '',"+
                "saldo_actual TEXT DEFAULT '',"+
                "dias_atraso TEXT DEFAULT '0',"+
                "serial_id TEXT DEFAULT '0')";

        public static final String CREATE_TBL_CIERRE_DIA = "CREATE TABLE " + TBL_CIERRE_DIA_T + " (" +
                "_id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "asesor_id TEXT," +
                "id_respuesta TEXT," +
                "num_prestamo TEXT," +
                "clave_cliente TEXT," +
                "medio_pago TEXT," +
                "monto_depositado TEXT," +
                "evidencia TEXT," +
                "tipo_cierre TEXT," +
                "tipo_prestamo TEXT," +
                "fecha_inicio TEXT," +
                "fecha_fin TEXT," +
                "fecha_envio TEXT," +
                "estatus INTEGER," +
                "nombre TEXT," +
                "serial_id TEXT)";

        public static final String CREATE_TBL_REPORTE_SESIONES = "CREATE TABLE " + TBL_REPORTE_SESIONES + " (" +
                "_id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "id INTEGER," +
                "serie_id TEXT," +
                "nombre_asesor TEXT," +
                "usuario TEXT," +
                "sucursal TEXT," +
                "sucursalid TEXT," +
                "horariologin TEXT," +
                "nivelbateria TEXT," +
                "versionapp TEXT," +
                "primeragestion TEXT," +
                "ultimagestion TEXT," +
                "totalgestiones TEXT)";

        public static final String CREATE_TBL_SUCURSALES = "CREATE TABLE " + TBL_SUCURSALES + " (" +
                "_id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "id INTEGER," +
                "nombre TEXT," +
                "region_id INTEGER," +
                "centrocosto_id INTEGER)";
        
        public static final String CREATE_TBL_CODIGOS_OXXO = "CREATE TABLE " + TBL_CODIGOS_OXXO + " (" +
                "_id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "id TEXT," +
                "id_asesor TEXT," +
                "num_prestamo TEXT," +
                "fecha_amortiz TEXT," +
                "monto_amortiz TEXT," +
                "nombre_pdf TEXT," +
                "created_at TEXT," +
                "fecha_vencimiento TEXT)";
        
        public static final String CREATE_TBL_CANCEL_GESTIONES = "CREATE TABLE " + TBL_CANCELACIONES + " (" +
                "_id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "id_respuesta TEXT," +
                "id_solicitud TEXT," +
                "tipo_gestion INTEGER," +
                "tipo_prestamo TEXT," +
                "comentario_asesor TEXT," +
                "comentario_admin TEXT," +
                "estatus TEXT," +
                "fecha_solicitud TEXT," +
                "fecha_aplicacion TEXT)";

        //================  TABLAS GENERALES  ===================================

        static final String CREATE_TABLE_STATUS_FICHAS = "CREATE TABLE " + TABLE_STATUS_FICHAS + "("
                + SidertEntry._ID               + " INTEGER PRIMARY KEY,"
                + SidertEntry.STATUS            + " INTEGER,"
                + SidertEntry.NOMBRE            + " TEXT)";

        static final String CREATE_TABLE_ESTADOS = "CREATE TABLE " + TABLE_ESTADOS + "("
                + SidertEntry._ID               + " INTEGER PRIMARY KEY,"
                + SidertEntry.ESTADO_ID         + " INTEGER,"
                + SidertEntry.ESTADO_NOMBRE     + " TEXT,"
                + SidertEntry.PAIS_ID           + " INTEGER)";

        static final String CREATE_TABLE_MUNICIPIOS = "CREATE TABLE " + TABLE_MUNICIPIOS + "("
                + SidertEntry._ID               + " INTEGER PRIMARY KEY,"
                + SidertEntry.MUNICIPIO_ID      + " INTEGER,"
                + SidertEntry.MUNICIPIO_NOMBRE  + " TEXT,"
                + SidertEntry.ESTADO_ID         + " INTEGER)";

        static final String CREATE_TABLE_COLONIAS = "CREATE TABLE " + TABLE_COLONIAS + "("
                + SidertEntry._ID               + " INTEGER PRIMARY KEY,"
                + SidertEntry.COLONIA_ID        + " INTEGER,"
                + SidertEntry.COLONIA_NOMBRE    + " TEXT,"
                + SidertEntry.CP                + " INTEGER,"
                + SidertEntry.MUNICIPIO_ID      + " INTEGER)";

        static final String CREATE_TABLE_OCUPACIONES = "CREATE TABLE " + TABLE_OCUPACIONES + "("
                + SidertEntry._ID               + " INTEGER PRIMARY KEY,"
                + SidertEntry.OCUPACION_ID      + " INTEGER,"
                + SidertEntry.OCUPACION_NOMBRE  + " TEXT,"
                + SidertEntry.OCUPACION_CLAVE   + " TEXT,"
                + SidertEntry.NIVEL_RIESGO      + " INTEGER,"
                + SidertEntry.SECTOR_ID         + " INTEGER)";

        static final String CREATE_TABLE_SECTORES = "CREATE TABLE " + TABLE_SECTORES + "("
                + SidertEntry._ID            + " INTEGER PRIMARY KEY,"
                + SidertEntry.SECTOR_ID      + " INTEGER,"
                + SidertEntry.SECTOR_NOMBRE  + " TEXT,"
                + SidertEntry.NIVEL_RIESGO   + " INTEGER)";

        static final String CREATE_TABLE_IDENTIFICACIONES = "CREATE TABLE " + TABLE_IDENTIFICACIONES + "("
                + SidertEntry._ID                    + " INTEGER PRIMARY KEY,"
                + SidertEntry.IDENTIFICACION_ID      + " INTEGER,"
                + SidertEntry.IDENTIFICACION_NOMBRE  + " TEXT)";

        static final String ADD_CREATE_AT_GEO = "ALTER TABLE " + TABLE_GEOLOCALIZACION +
                                                " ADD COLUMN " + SidertEntry.CREATE_AT +
                                                " TEXT DEFAULT '" + Miscellaneous.ObtenerFecha("timestamp")+"'";

        static final String ADD_CREATE_AT_GEO_T = "ALTER TABLE " + TABLE_GEOLOCALIZACION_T +
                                                  " ADD COLUMN " + SidertEntry.CREATE_AT +
                                                  " TEXT DEFAULT '" + Miscellaneous.ObtenerFecha("timestamp")+"'";

        static final String ADD_IS_RUTA_T = "ALTER TABLE " + FICHAS_T +
                " ADD COLUMN is_ruta" +
                " INTEGER DEFAULT 0";

        static final String ADD_RES_IMPRESION_T = "ALTER TABLE " + TBL_RESPUESTAS_IND_T +
                " ADD COLUMN res_impresion" +
                " TEXT DEFAULT \"0\"";

        static final String ADD_RES_IMPRESION = "ALTER TABLE " + TBL_RESPUESTAS_IND +
                " ADD COLUMN res_impresion" +
                " TEXT DEFAULT \"0\"";

        static final String ADD_RES_IMPRESION_GPO_T = "ALTER TABLE " + TBL_RESPUESTAS_GPO_T +
                " ADD COLUMN res_impresion" +
                " TEXT DEFAULT \"0\"";

        static final String ADD_RES_IMPRESION_GPO = "ALTER TABLE " + TBL_RESPUESTAS_GPO +
                " ADD COLUMN res_impresion" +
                " TEXT DEFAULT \"0\"";

        static final String ADD_ARQUEO_CAJA_GPO_T = "ALTER TABLE " + TBL_RESPUESTAS_GPO_T +
                " ADD COLUMN arqueo_caja" +
                " TEXT DEFAULT \"0\"";

        static final String ADD_ARQUEO_CAJA_GPO = "ALTER TABLE " + TBL_RESPUESTAS_GPO +
                " ADD COLUMN arqueo_caja" +
                " TEXT DEFAULT \"0\"";

        static final String ADD_COLONIA_CARTERA_IND = "ALTER TABLE " + TBL_CARTERA_IND +
                " ADD COLUMN colonia" +
                " TEXT DEFAULT ''";

        static final String ADD_COLONIA_CARTERA_IND_T = "ALTER TABLE " + TBL_CARTERA_IND_T +
                " ADD COLUMN colonia" +
                " TEXT DEFAULT ''";

        static final String ADD_COLONIA_CARTERA_GPO = "ALTER TABLE " + TBL_CARTERA_GPO +
                " ADD COLUMN colonia" +
                " TEXT DEFAULT ''";

        static final String ADD_COLONIA_CARTERA_GPO_T = "ALTER TABLE " + TBL_CARTERA_GPO_T +
                " ADD COLUMN colonia" +
                " TEXT DEFAULT ''";

        static final String ADD_ESTATUS_PAGO_IND_T = "ALTER TABLE " + TBL_RESPUESTAS_IND_T +
                " ADD COLUMN estatus_pago" +
                " TEXT DEFAULT ''";

        static final String ADD_SALDO_CORTE_IND_T = "ALTER TABLE " + TBL_RESPUESTAS_IND_T +
                " ADD COLUMN saldo_corte" +
                " TEXT DEFAULT ''";

        static final String ADD_SALDO_ACTUAL_IND_T = "ALTER TABLE " + TBL_RESPUESTAS_IND_T +
                " ADD COLUMN saldo_actual" +
                " TEXT DEFAULT ''";

        static final String ADD_ESTATUS_PAGO_IND = "ALTER TABLE " + TBL_RESPUESTAS_IND +
                " ADD COLUMN estatus_pago" +
                " TEXT DEFAULT ''";

        static final String ADD_SALDO_CORTE_IND = "ALTER TABLE " + TBL_RESPUESTAS_IND +
                " ADD COLUMN saldo_corte" +
                " TEXT DEFAULT ''";

        static final String ADD_SALDO_ACTUAL_IND = "ALTER TABLE " + TBL_RESPUESTAS_IND +
                " ADD COLUMN saldo_actual" +
                " TEXT DEFAULT ''";

        static final String ADD_ESTATUS_PAGO_GPO_T = "ALTER TABLE " + TBL_RESPUESTAS_GPO_T +
                " ADD COLUMN estatus_pago" +
                " TEXT DEFAULT ''";

        static final String ADD_SALDO_CORTE_GPO_T = "ALTER TABLE " + TBL_RESPUESTAS_GPO_T +
                " ADD COLUMN saldo_corte" +
                " TEXT DEFAULT ''";

        static final String ADD_SALDO_ACTUAL_GPO_T = "ALTER TABLE " + TBL_RESPUESTAS_GPO_T +
                " ADD COLUMN saldo_actual" +
                " TEXT DEFAULT ''";

        static final String ADD_ESTATUS_PAGO_GPO = "ALTER TABLE " + TBL_RESPUESTAS_GPO +
                " ADD COLUMN estatus_pago" +
                " TEXT DEFAULT ''";

        static final String ADD_SALDO_CORTE_GPO = "ALTER TABLE " + TBL_RESPUESTAS_GPO +
                " ADD COLUMN saldo_corte" +
                " TEXT DEFAULT ''";

        static final String ADD_SALDO_ACTUAL_GPO = "ALTER TABLE " + TBL_RESPUESTAS_GPO +
                " ADD COLUMN saldo_actual" +
                " TEXT DEFAULT ''";

        static final String ADD_DIAS_ATRASO_IND_T = "ALTER TABLE " + TBL_RESPUESTAS_IND_T +
                " ADD COLUMN dias_atraso" +
                " TEXT DEFAULT \"0\"";

        static final String ADD_DIAS_ATRASO_IND = "ALTER TABLE " + TBL_RESPUESTAS_IND +
                " ADD COLUMN dias_atraso" +
                " TEXT DEFAULT \"0\"";

        static final String ADD_DIAS_ATRASO_GPO = "ALTER TABLE " + TBL_RESPUESTAS_GPO +
                " ADD COLUMN dias_atraso" +
                " TEXT DEFAULT \"0\"";

        static final String ADD_DIAS_ATRASO_GPO_T = "ALTER TABLE " + TBL_RESPUESTAS_GPO_T +
                " ADD COLUMN dias_atraso" +
                " TEXT DEFAULT \"0\"";

        static final String ADD_NUM_PRESTAMO_T = "ALTER TABLE " + TBL_IMPRESIONES_VIGENTE_T +
                " ADD COLUMN num_prestamo" +
                " TEXT DEFAULT '0'";

        static final String ADD_NUM_PRESTAMO = "ALTER TABLE " + TBL_IMPRESIONES_VIGENTE +
                " ADD COLUMN num_prestamo" +
                " TEXT DEFAULT '0'";

        static final String ADD_NUM_PRESTAMO_RIM = "ALTER TABLE " + TBL_REIMPRESION_VIGENTE_T +
                " ADD COLUMN num_prestamo" +
                " TEXT DEFAULT '0'";

        static final String ADD_CLAVE_INTEGRANTE_T = "ALTER TABLE " + TBL_MIEMBROS_GPO_T +
                " ADD COLUMN clave" +
                " TEXT DEFAULT '0'";

        static final String ADD_CLAVE_INTEGRANTE = "ALTER TABLE " + TBL_MIEMBROS_GPO +
                " ADD COLUMN clave" +
                " TEXT DEFAULT '0'";

        static final String ADD_PRESTAMO_ID_INTEGRANTE_T = "ALTER TABLE " + TBL_MIEMBROS_GPO_T +
                " ADD COLUMN id_prestamo_integrante" +
                " TEXT DEFAULT '0'";

        static final String ADD_PRESTAMO_ID_INTEGRANTE = "ALTER TABLE " + TBL_MIEMBROS_GPO +
                " ADD COLUMN id_prestamo_integrante" +
                " TEXT DEFAULT '0'";

        static final String ADD_CLAVE_GEO_RESP = "ALTER TABLE " + TBL_GEO_RESPUESTAS_T +
                " ADD COLUMN clave" +
                " TEXT DEFAULT '0'";

        static final String ADD_GEO_CLIENTE_IND = "ALTER TABLE " + TBL_CARTERA_IND_T +
                " ADD COLUMN geo_cliente" +
                " INTEGER DEFAULT '0'";

        static final String ADD_GEO_AVAL_IND = "ALTER TABLE " + TBL_CARTERA_IND_T +
                " ADD COLUMN geo_aval" +
                " INTEGER DEFAULT '0'";

        static final String ADD_GEO_NEGOCIO_IND = "ALTER TABLE " + TBL_CARTERA_IND_T +
                " ADD COLUMN geo_negocio" +
                " INTEGER DEFAULT '0'";

        static final String ADD_GEOLOCALIZADAS_GPO = "ALTER TABLE " + TBL_CARTERA_GPO_T +
                " ADD COLUMN geolocalizadas" +
                " TEXT DEFAULT ''";

        static final String ADD_SERIAL_INDIVIDUAL = "ALTER TABLE " + TBL_RESPUESTAS_IND_V_T +
                " ADD COLUMN serial_id" +
                " TEXT DEFAULT '0'";

        static final String ADD_SERIAL_INTEGRANTE = "ALTER TABLE " + TBL_RESPUESTAS_INTEGRANTE_T +
                " ADD COLUMN serial_id" +
                " TEXT DEFAULT '0'";

        static final String ADD_NOMBRE_CIERRE_DIA = "ALTER TABLE " + TBL_CIERRE_DIA_T +
                " ADD COLUMN nombre" +
                " TEXT DEFAULT ''";

        static final String ADD_SERIAL_ID_CIERRE_DIA = "ALTER TABLE " + TBL_CIERRE_DIA_T +
                " ADD COLUMN serial_id" +
                " TEXT DEFAULT ''";

        static final String ADD_FECHA_VENCIMIENTO_CODIGOS = "ALTER TABLE " + TBL_CODIGOS_OXXO +
                " ADD COLUMN fecha_vencimiento" +
                " TEXT DEFAULT ''";

        static final String ADD_ESTATUS_CARTERA_IND = "ALTER TABLE " + TBL_CARTERA_IND_T +
                " ADD COLUMN estatus" +
                " TEXT DEFAULT '1'";

        static final String ADD_ESTATUS_CARTERA_GPO = "ALTER TABLE " + TBL_CARTERA_GPO_T +
                " ADD COLUMN estatus" +
                " TEXT DEFAULT '1'";

    }
}
