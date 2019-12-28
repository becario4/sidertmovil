package com.sidert.sidertmovil.database;

import android.provider.BaseColumns;

import com.sidert.sidertmovil.utils.Constants;

public class SidertTables {

    public static class SidertEntry implements BaseColumns {

        // == TABLE
        public static final String TABLE_NAME_LOG_ASESSOR    = Constants.LOG_ASESSOR; //Tabla de Recuperación y Cobranza
        public static final String TABLE_NAME_LOG_MANAGER    = Constants.LOG_MANAGER; //Tabla de Cartera Vencida
        public static final String TABLE_IND     = Constants.IND;                     //Tabla para formularios individual vigente (RI, CI)
        public static final String TABLE_GPO     = Constants.GPO;                     //Tabla para formularios grupales vigente (RG, CG)
        public static final String TABLE_FICHAS     = Constants.FICHAS;               //Tabla para todos los formularios RI, RG, CI, CG, CVI, CVG
        //public static final String TABLE_IND_VE     = Constants.IND_VE;             //Tabla para formularios individual vencida (CVI)
        //public static final String TABLE_GPO_VE     = Constants.GPO_VE;             //Tabla para formularios grupales vencida (CVG)
        public static final String TABLE_GEOLOCALIZACION = Constants.GEOLOCALIZACION; //Tabla para formularios de golocaliacion
        public static final String TABLE_LOGIN_REPORT     = Constants.LOGIN_REPORT;   //Tabla para timestamp de inicio de sesión

        //======================= TABLES TEST  =====================================================
        public static final String TABLE_LOG_ASESSOR_T    = Constants.LOG_ASESSOR_T; //Tabla de Recuperación y Cobranza Pruebas
        public static final String TABLE_LOG_MANAGER_T    = Constants.LOG_MANAGER_T; //Tabla de Cartera Vencida Pruebas
        public static final String TABLE_IND_T     = Constants.IND_T;                //Tabla para formularios individual vigente (RI, CI) Pruebas
        public static final String TABLE_GPO_T     = Constants.GPO_T;                //Tabla para formularios grupales vigente (RG, CG) Pruebas
        public static final String TABLE_FICHAS_T     = Constants.FICHAS_T;          //Tabla para todos los formularios RI, RG, CI, CG, CVI, CVG para pruebas
        //public static final String TABLE_IND_VE_T     = Constants.IND_VE_T;        //Tabla para formularios individual vencida (CVI) Pruebas
        //public static final String TABLE_GPO_VE_T     = Constants.GPO_VE_T;        //Tabla para formularios grupales vencida (CVG) Pruebas
        public static final String TABLE_GEOLOCALIZACION_T = Constants.GEOLOCALIZACION_T; //Tabla para formularios de golocaliacion Pruebas
        public static final String TABLE_LOGIN_REPORT_T     = Constants.LOGIN_REPORT_T;   //Tabla para timestamp de inicio de sesión Pruebas

        // ====================== TABLAS GENERALES  ========================================
        public static final String TABLE_STATUS_FICHAS = Constants.STATUS_FICHAS;


        // == COLUMNS IMPRESSIONS LOG
        public static final String ASESOR_ID            = "asesor_id";
        public static final String FOLIO                = "folio";
        public static final String ASSESOR_ID           = "assesor_id";
        public static final String ASESOR_NOMBRE       = "asesor_nombre";
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
                + SidertEntry.STATUS             + " INTEGER)";

        static final String CREATE_TABLE_LOGIN_REPORT   = "CREATE TABLE " + TABLE_LOGIN_REPORT + "("
                + SidertEntry._ID               + " INTEGER PRIMARY KEY,"
                + SidertEntry.SERIE_ID          + " TEXT,"
                + SidertEntry.NOMBRE            + " TEXT,"
                + SidertEntry.LOGIN_TIMESTAMP   + " TEXT,"
                + SidertEntry.FECHA_ENV         + " TEXT,"
                + SidertEntry.STATUS            + " INTEGER)";

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
                + SidertEntry.STATUS             + " INTEGER)";

        static final String CREATE_TABLE_LOGIN_REPORT_T   = "CREATE TABLE " + TABLE_LOGIN_REPORT_T + "("
                + SidertEntry._ID               + " INTEGER PRIMARY KEY,"
                + SidertEntry.SERIE_ID          + " TEXT,"
                + SidertEntry.NOMBRE            + " TEXT,"
                + SidertEntry.LOGIN_TIMESTAMP   + " TEXT,"
                + SidertEntry.FECHA_ENV         + " TEXT,"
                + SidertEntry.STATUS            + " INTEGER)";


        //================  TABLAS GENERALES  ===================================

        static final String CREATE_TABLE_STATUS_FICHAS = "CREATE TABLE " + TABLE_STATUS_FICHAS + "("
                + SidertEntry._ID               + " INTEGER PRIMARY KEY,"
                + SidertEntry.STATUS            + " INTEGER,"
                + SidertEntry.NOMBRE            + " TEXT)";
    }
}
