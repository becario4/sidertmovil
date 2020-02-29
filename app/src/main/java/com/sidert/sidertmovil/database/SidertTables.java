package com.sidert.sidertmovil.database;

import android.provider.BaseColumns;

import com.sidert.sidertmovil.utils.Constants;
import com.sidert.sidertmovil.utils.Miscellaneous;

public class SidertTables {

    public static class SidertEntry implements BaseColumns {

        // == TABLE
        public static final String TABLE_NAME_LOG_ASESSOR  = Constants.LOG_ASESSOR;        //Tabla de Recuperación y Cobranza
        public static final String TABLE_NAME_LOG_MANAGER  = Constants.LOG_MANAGER;        //Tabla de Cartera Vencida
        public static final String TABLE_IND               = Constants.IND;                //Tabla para formularios individual vigente (RI, CI)
        public static final String TABLE_GPO               = Constants.GPO;                //Tabla para formularios grupales vigente (RG, CG)
        public static final String TABLE_FICHAS            = Constants.FICHAS;             //Tabla para todos los formularios RI, RG, CI, CG, CVI, CVG
        //public static final String TABLE_IND_VE          = Constants.IND_VE;             //Tabla para formularios individual vencida (CVI)
        //public static final String TABLE_GPO_VE          = Constants.GPO_VE;             //Tabla para formularios grupales vencida (CVG)
        public static final String TABLE_GEOLOCALIZACION   = Constants.GEOLOCALIZACION;    //Tabla para formularios de golocaliacion
        public static final String TABLE_LOGIN_REPORT      = Constants.LOGIN_REPORT;       //Tabla para timestamp de inicio de sesión
        public static final String TABLE_SINCRONIZADO      = Constants.SINCRONIZADO;       //Tabla para registrar el timestamp de fecha y hora de ultima sincronizacion
        public static final String TABLE_SOLICITUDES_IND   = Constants.SOLICITUDES_IND;    //Tabla para registrar las solicitudes de crédito

        //======================= TABLES TEST  =====================================================
        public static final String TABLE_LOG_ASESSOR_T     = Constants.LOG_ASESSOR_T;      //Tabla de Recuperación y Cobranza Pruebas
        public static final String TABLE_LOG_MANAGER_T     = Constants.LOG_MANAGER_T;      //Tabla de Cartera Vencida Pruebas
        public static final String TABLE_IND_T             = Constants.IND_T;              //Tabla para formularios individual vigente (RI, CI) Pruebas
        public static final String TABLE_GPO_T             = Constants.GPO_T;              //Tabla para formularios grupales vigente (RG, CG) Pruebas
        public static final String TABLE_FICHAS_T          = Constants.FICHAS_T;           //Tabla para todos los formularios RI, RG, CI, CG, CVI, CVG para pruebas
        //public static final String TABLE_IND_VE_T        = Constants.IND_VE_T;           //Tabla para formularios individual vencida (CVI) Pruebas
        //public static final String TABLE_GPO_VE_T        = Constants.GPO_VE_T;           //Tabla para formularios grupales vencida (CVG) Pruebas
        public static final String TABLE_GEOLOCALIZACION_T = Constants.GEOLOCALIZACION_T;  //Tabla para formularios de golocaliacion Pruebas
        public static final String TABLE_LOGIN_REPORT_T    = Constants.LOGIN_REPORT_T;     //Tabla para timestamp de inicio de sesión Pruebas
        public static final String TABLE_SINCRONIZADO_T    = Constants.SINCRONIZADO_T;     //Tabla para registrar el timestamp de fecha y hora de ultima sincronizacion Pruebas
        public static final String TABLE_SOLICITUDES_IND_T = Constants.SOLICITUDES_IND_T;  //Tabla para registrar las solicitudes de crédito Pruebas

        // ====================== TABLAS GENERALES  ========================================
        public static final String TABLE_STATUS_FICHAS = Constants.STATUS_FICHAS;
        public static final String TABLE_ESTADOS       = Constants.ESTADOS;
        public static final String TABLE_MUNICIPIOS    = Constants.MUNICIPIOS;
        public static final String TABLE_COLONIAS      = Constants.COLONIAS;
        public static final String TABLE_OCUPACIONES   = Constants.OCUPACIONES;
        public static final String TABLE_SECTORES      = Constants.SECTORES;
        public static final String TABLE_IDENTIFICACIONES      = Constants.IDENTIFICACIONES;

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

        static final String CREATE_TBL_DOCUMENTOS_CLIENTES_T = "CREATE TABLE documentos_t (" +
                "id_documento INTEGER PRIMARY KEY AUTOINCREMENT," +
                "id_cliente INTEGER," +
                "nombre TEXT," +
                "tipo_documento INTEGER," +
                "estatus INTEGER)";




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
    }
}
