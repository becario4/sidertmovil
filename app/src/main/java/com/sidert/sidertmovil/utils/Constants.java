package com.sidert.sidertmovil.utils;

import android.os.Environment;

import retrofit2.http.PUT;

public class Constants {

    public final static boolean ENVIROMENT = false;

    // CONTROLADORES
    public final static String CONTROLLER_LOGIN = "login";
    public final static String CONTROLLER_FICHAS = "fichas";
    public final static String CONTROLLER_CATALOGOS = "catalogos";
    public final static String CONTROLLER_SOLICITUDES = "solicitudes";
    public final static String CONTROLLER_MOVIL = "movil";
    public final static String CONTROLLER_DENUNCIAS = "denuncias";
    public final static String CONTROLLER_API = "api";
    public final static String CONTROLLER_CODIGOS = "codigos";
    public final static String CONTROLLER_RECIBOS = "recibos";
    public final static String CONTROLLER_SOPORTE = "soporte";
    public final static String CONTROLLER_APK = "apk";
    public final static String CONTROLLER_CAMAPANAS = "campanas";

    // ACTION BAR CONSTANTS
    public final static String TBtitle = "title";
    public final static String TBhasBack = "hasBack";

    public final static String RECOVERY = "Recuperacion";
    public final static String COLLECTION = "Cobranza";
    public final static String WALLET_EXPIRED = "CarteraVencida";

    public final static String ASESSOR = "Asesor";
    public final static String MANNAGER = "Gestor";

    public final static String ERROR = "Error";

    public final static String FICHA = "ficha";

    public final static String TIMESTAMP = "timestamp";

    public final static String FORMAT_DATE = "dd-MM-yyyy";
    public final static String FORMAT_DATE_GNRAL = "yyyy-MM-dd";
    public final static String FORMAT_TIMESTAMP = "yyyy-MM-dd HH:mm:ss";

    public final static String negative_payment = "Negación de pago";
    public final static String outdate_information = "Información Desfasada";
    public final static String death = "Fallecimiento";
    public final static String other = "Otro";

    public final static String bank = "Banco";
    public final static String oxxo = "Oxxo";
    public final static String cash = "Efectivo";
    public final static String sidert = "SIDERT";

    public final static String photo = "Fotografía";
    public final static String galery = "Galería";

    public final static String KEY = "key";
    public final static String VALUE = "value";

    public final static String PARAMS = "params";
    public final static String CONDITIONALS = "conditionals";

    public final static String WHERE = "where";
    public final static String ORDER = "order";

    public final static String MESSAGE = "message";
    public final static String RESPONSE = "response";
    public final static String CODE = "code";

    public final static String SCREEN_SHOT = "response";

    public final static String RES_PRINT = "res_print";

    public final static String uri_signature = "uri_signature";

    public final static String type = "type";
    public final static String client_code = "clave_cliente";

    public final static String EFECTIVO = "Efectivo";
    public final static String EXTERNAL_ID = "external_id";

    //Iconos para colocar el dialog_message
    public final static String not_network = "not_network";
    public final static String success = "success";
    public final static String face_happy = "face_happy";
    public final static String face_dissatisfied = "face_dissatisfied";
    public final static String print_off = "print_off";
    public final static String money = "money";
    public final static String logout = "logout";
    public final static String firma = "firma";
    public final static String question = "question";
    public final static String warning = "warning";
    public final static String login = "login";
    public final static String camara = "camara";

    // Keys para servicios

    public final static String ADVISER_ID = "asesorid";
    public final static String DATE = "fecha";
    public final static String ISSUE = "asunto";
    public final static String REASON = "motivo";
    public final static String FOLIO = "folio";
    public final static String SERIE_ID = "serieid";
    public final static String NOMBRE_EMPLEADO = "nombre";
    public final static String PATERNO = "paterno";
    public final static String MATERNO = "materno";
    public final static String USER_NAME = "user_name";
    public final static String EMAIL = "email";
    public final static String AUTHORITIES = "authorities";
    public final static String MODULOS = "modulos";
    public final static String SUCURSALES = "sucursales";
    public final static String MAC_ADDRESSES = "mac_addresses";
    public final static String MAC_ADDRESS = "macAddress";

    //====================== ACTION PUSH NOTIFICATIONS  =========================================
    public final static String BORRAR_CACHE = "BORRAR CACHE";
    public final static String ACTUALIZAR_CARTERA = "ACTUALIZAR CARTERA";
    public final static String REMOVER_CARTERA = "REMOVER CARTERA";
    public final static String UBICACION_ACTUAL = "UBICACION ACTUAL";
    public final static String FECHA_HORA = "FECHA Y HORA";
    public final static String ACCION = "Accion";


    //======================  TAGS DE FICHAS  ===================================================
    public final static String ORDER_ID = "id";
    public final static String ID_CARTERA = "id_cartera";
    public final static String TYPE = "type";
    public final static String ASSIGN_DATE = "assignDate";
    public final static String EXPIRATION_DATE = "expirationDate";
    public final static String CANCELLATION_DATE = "cancellationDate";
    public final static String CLIENTE = "Cliente";
    public final static String GRUPO = "Grupo";
    public final static String GRUPAL = "Grupal";
    public final static String INDIVIDUAL = "Individual";
    public final static String NUMERO_CLIENTE = "NumeroCliente";
    public final static String NOMBRE = "Nombre";
    public final static String TEL_CELULAR = "TelCelular";
    public final static String TELEFONO_CELULAR = "TelefonoCelular";
    public final static String TELEFONO_DOMICILIO = "TelefonoDomicilio";
    public final static String TEL_DOMICILIO = "TelDomicilio";
    public final static String DIRECCION = "Direccion";
    public final static String CALLE = "Calle";
    public final static String TIPO_SOLICITUD = "TipoSolicitud";
    public final static String CIUDAD = "Ciudad";
    public final static String CODIGO_POSTAL = "CodigoPostal";
    public final static String COLONIA = "Colonia";
    public final static String MUNICIPIO = "Municipio";
    public final static String ESTADO = "Estado";
    public final static String LATITUDE = "latitude";
    public final static String LONGITUDE = "longitude";
    public final static String AVAL = "Aval";
    public final static String NOMBRE_COMPLETO_AVAL = "NombreCompletoAval";
    public final static String TELEFONO_AVAL = "TelefonoAval";
    public final static String ADDRESS_AVAL = "AddressAval";
    public final static String PARENTESCO_AVAL = "ParentescoAval";
    public final static String PRESTAMO = "Prestamo";
    public final static String NUMERO_DE_PRESTAMO = "NumeroDePrestamo";
    public final static String FECHA_CREDITO_OTORGADO = "FechaDelCreditoOtorgado";
    public final static String MONTO_TOTAL_PRESTAMO = "MontoTotalPrestamo";
    public final static String MONTO_PRESTAMO = "MontoPrestamo";
    public final static String PAGO_SEMANAL = "PagoSemanal";
    public final static String CLAVE_CLIENTE = "ClaveCliente";
    public final static String PAGO_REALIZADO = "PagoRealizado";
    public final static String NUMERO_AMORTIZACION = "NumeroAmortizacion";
    public final static String MONTO_AMORTIZACION = "MontoAmortizacion";
    public final static String CLAVE = "Clave";
    public final static String FECHA_PAGO_ESTABLECIDA = "FechaPagoEstablecida";
    public final static String HORA_PAGO_ESTABLECIDA = "HoraPagoEstablecida";
    public final static String SALDO_ACTUAL = "SaldoActual";
    public final static String FECHA_FIN = "FechaFin";
    public final static String SUMA_DE_PAGOS = "SumaDePagos";
    public final static String DIAS_ATRASO = "DiasAtraso";
    public final static String FRECUENCIA = "Frecuencia";
    public final static String DIA_SEMANA = "DiaSemana";
    public final static String PERTENECE_GARANTIA = "PerteneceGarantia";
    public final static String CUENTA_CON_GARANTIA = "CuentaConGarantia";
    public final static String TIPO_GARANTIA = "TipoGarantia";
    public final static String REPORTE_ANALITICO_OMEGA = "ReporteAnaliticoOmega";
    public final static String NO = "No";
    public final static String FECHA_AMORTIZACION = "FechaAmortizacion";
    public final static String FECHA_PAGO = "FechaPago";
    public final static String ESTATUS = "Estatus";
    public final static String ID = "id";
    public final static String DIAS = "Dias";
    public final static String TABLA_PAGOS_CLIENTE = "TablaPagosCliente";
    public final static String FECHA = "Fecha";
    public final static String PAGO = "Pago";
    public final static String BANCO = "Banco";
    public final static String CLAVE_GRUPO = "ClaveGrupo";
    public final static String NOMBRE_GRUPO = "NombreGrupo";
    public final static String TOTAL_INTEGRANTES = "TotalIntegrantes";
    public final static String INTEGRANTES_GRUPO = "IntegrantesDelGrupo";
    public final static String MONTO = "Monto";
    public final static String PAGO_SEMANAL_INT = "PagoSemanalInt";
    public final static String TESORERA = "Tesorera";
    public final static String ADDRESS = "Address";
    public final static String PRESIDENTA = "Presidenta";
    public final static String NOMBRE_PRESIDENTA = "NombrePresidenta";
    public final static String ADDRESS_PRESIDENTA = "AddressPresidenta";
    public final static String TEL_CELULAR_PRESIDENTA = "TelCelularPresidenta";
    public final static String TEL_DOMICILIO_PRESIDENTA = "TelCelularPresidenta";
    public final static String SECRETARIA = "Secretaria";
    public final static String NOMBRE_SECRETARIA = "NombreSecretaria";
    public final static String ADDRESS_SECRETARIA = "AddressSercretaria";
    public final static String TEL_CELULAR_SECRETARIA = "TelCelularSecretaria";
    public final static String TEL_DOMICILIO_SECRETARIA = "TelCelularSecretaria";
    public final static String TABLA_PAGOS_GRUPO = "TablaPagosGrupo";
    public final static String FECHA_AMORTIZACION_GPO = "Fecha_Amortizacion";
    public final static String FECHA_PAGO_GPO = "Fecha_Pago";
    public final static String SAVE = "Guardar";
    public final static String PAGO_REQUERIDO = "PagoRequerido";
    public final static String FIRMA_IMAGE = "FirmaImage";
    public final static String TIPO_IMAGEN = "TipoImagen";

    public final static String ID_CREDITO = "id_credito";

    public final static String FECHA_INI = "Fecha_Ini";

    public final static String CODEBARS = "code_bars";

    public final static String NUM_SOLICITUD = "num_solicitud";
    public final static String ID_INTEGRANTE = "id_integrante";
    public final static String MODULO = "modulo";
    public final static String _ID = "_id";
    public final static String IMAGEN = "imagen";
    public final static String INTEGRANTE_TIPO = "integrante_tipo";

    public final static String PICTURE = "picture";

    public final static String ID_RESPUESTA = "id_respuesta";
    public final static String CANCELACION = "cancelacion";

    //==========================  TIPO INTEGRANTE  =================================================
    public final static String TIPO_CLIENTE = "CLIENTE";
    public final static String TIPO_NEGOCIO = "NEGOCIO";
    public final static String TIPO_AVAL = "AVAL";
    public final static String TIPO_PRESIDENTE = "PRESIDENTE";
    public final static String TIPO_TESORERO = "TESORERO";
    public final static String TIPO_SECRETARIO = "SECRETARIO";


    //========================== PARAMS REQUESTS  ==================================================
    public final static String USERNAME = "username";
    public final static String PASSWORD = "password";
    public final static String GRANT_TYPE = "grant_type";
    public final static String BATTERY = "battery";

    //==========================   REQUEST CODE   ==================================================
    public final static int REQUEST_CODE_FIRMA = 456;
    public final static int REQUEST_CODE_DISPOSITIVO = 962;
    public final static int REQUEST_CODE_IMPRESORA = 963;
    public final static int REQUEST_CODE_INTEGRANTES_GPO = 951;
    public final static int REQUEST_CODE_RESUMEN_INTEGRANTES_GPO = 834;
    public final static int REQUEST_CODE_GALERIA = 852;
    public final static int REQUEST_CODE_CAMARA_TICKET = 753;
    public final static int REQUEST_CODE_CAMARA_FACHADA = 493;
    public final static int REQUEST_CODE_LLAMADA = 123;
    public final static int REQUEST_CODE_ARQUEO_CAJA = 369;
    public final static int REQUEST_CODE_ACTIVITY = 749;
    public final static int REQUEST_CODE_PREVIEW = 864;
    public final static int REQUEST_CODE_CODEBARS = 462;
    public final static int REQUEST_CODE_ADD_INTEGRANTE = 764;
    public final static int REQUEST_CODE_ESTADO_NAC = 468;
    public final static int REQUEST_CODE_OCUPACION_CLIE = 346;
    public final static int REQUEST_CODE_OCUPACION_NEG = 664;
    public final static int REQUEST_CODE_OCUPACION_CONY = 673;
    public final static int REQUEST_CODE_ACTIVIDAD_NEG = 412;
    public final static int REQUEST_CODE_ESTADO_NAC_AVAL = 348;
    public final static int REQUEST_CODE_OCUPACION_AVAL = 671;
    public final static int REQUEST_CODE_ESTADO_AVAL = 846;
    public final static int REQUEST_CODE_COLONIA_CLIE = 934;
    public final static int REQUEST_CODE_LOCALIDAD_CLIE = 848;
    public final static int REQUEST_CODE_LOCALIDAD_CONY = 615;
    public final static int REQUEST_CODE_LOCALIDAD_NEG = 516;
    public final static int REQUEST_CODE_LOCALIDAD_AVAL = 734;
    public final static int REQUEST_CODE_LOCALIDAD_REF = 559;
    public final static int REQUEST_CODE_COLONIA_CONY = 446;
    public final static int REQUEST_CODE_COLONIA_AVAL = 168;
    public final static int REQUEST_CODE_COLONIA_NEG = 336;
    public final static int REQUEST_CODE_COLONIA_REF = 794;
    public final static int REQUEST_CODE_COLONIA = 641;
    public final static int REQUEST_CODE_CAMARA_FACHADA_CLI = 698;
    public final static int REQUEST_CODE_CAMARA_FACHADA_NEG = 598;
    public final static int REQUEST_CODE_CAMARA_FACHADA_AVAL = 994;
    public final static int REQUEST_CODE_FIRMA_AVAL = 863;
    public final static int REQUEST_CODE_FIRMA_CLI = 862;
    public final static int REQUEST_CODE_FIRMA_ASESOR = 861;
    public final static int REQUEST_CODE_FOTO_INE_SELFIE = 801;
    public final static int REQUEST_CODE_FOTO_INE_FRONTAL = 752;
    public final static int REQUEST_CODE_FOTO_INE_REVERSO = 798;
    public final static int REQUEST_CODE_FOTO_CURP = 768;
    public final static int REQUEST_CODE_FOTO_COMPROBATE = 715;
    public final static int REQUEST_CODE_FOTO_COMPROBATE_GARANTIA = 802;
    public final static int REQUEST_CODE_MIEMBRO_GEO = 668;
    public final static int REQUEST_CODE_CAMERA_CIERRE_DIA = 416;
    public final static int REQUEST_CODE_SOPORTE = 685;
    public final static int REQUEST_CODE_CALLE = 648;
    public final static int REQUEST_CODE_FOTO_INE_FRONTAL_AVAL = 803;
    public final static int REQUEST_CODE_FOTO_INE_REVERSO_AVAL = 804;
    public final static int REQUEST_CODE_FOTO_CURP_AVAL = 805;
    public final static int REQUEST_CODE_FOTO_COMPROBANTE_AVAL = 806;
    public final static int CANCEL_TRACKER_ID = 300;
    public final static int REQUEST_CODE_CAMAPANAS = 333;
    public final static int REQUEST_CODE_CARTERAEN = 222;

    //=========================== PARÁMETROS FECHAS  ===============================================
    public final static String DAY_CURRENT = "day_current";
    public final static String MONTH_CURRENT = "month_current";
    public final static String YEAR_CURRENT = "year_current";
    public final static String DATE_CURRENT = "date_current";
    public final static String FECHAS_POST = "fechas_post";
    public final static String IDENTIFIER = "identifier";

    //===========================  ID JOB SCHEDULE  ================================================
    public final static int ID_JOB_LOGOUT = 324;
    public final static int ID_JOB_SINCRONIZADO = 753;

    //===========================   TIPOS FICHAS  ==================================================
    public final static String RECUPERACION_IND = "RecuperacionIndividual";
    public final static String RECUPERACION_GPO = "RecuperacionGrupal";
    public final static String COBRANZA_IND = "CobranzaVencidaIndividual";
    public final static String COBRANZA_GPO = "CobranzaVencidaGrupal";
    public final static String CARTERA_VENCIDA_IND = "CarteraVencidaIndividual";
    public final static String CARTERA_VENCIDA_GPO = "CarteraVencidaGrupal";

    public final static int LIMIT_COMPLAINTS = 2;//maximo de peticiones de denuncias PLD

    public static final String PATH = Environment.getExternalStorageDirectory().getAbsolutePath();
    public static final String ROOT_PATH = Environment.getExternalStorageDirectory().getAbsolutePath() + "/Android/data/com.sidert.sidertmovil.v2/files/Pictures/Files/";

    //=========================  Nombre de tablas  =================================================
    public final static String LOG_ASESSOR = "log_impressions_asessor";
    public final static String LOG_MANAGER = "log_impressions_manager";
    public final static String LOG_ASESSOR_T = "log_impresiones_asesor_test";
    public final static String LOG_MANAGER_T = "log_impresiones_gestor_test";
    public final static String IND = "individuals";
    public final static String GPO = "groups";
    public final static String FICHAS = "fichas";
    public final static String GEOLOCALIZACION = "geolocalizacion";
    public final static String LOGIN_REPORT = "login_report";
    //public final static String IND_VE               = "individuals_ven";
    //public final static String GPO_VE               = "groups_ven"
    public final static String SINCRONIZADO = "sincronizado";
    public final static String SOLICITUDES_IND = "solicitudes_ind";
    public final static String TBL_RECIBOS_VIGENTE = "tbl_recibos_vigente";
    public final static String TBL_RECIBOS_VENCIDA = "tbl_recibos_vencida";
    public final static String TBL_REIMPRESION = "tbl_reimpresion";
    public final static String RECIBOS_CIRCULO_CREDITO = "recibos_circulo_credito";
    public final static String TBL_CENTRO_COSTO = "tbl_centro_costo";

    public final static String IND_T = "individuals_t";
    public final static String GPO_T = "groups_t";
    public final static String FICHAS_T = "fichas_t";
    public final static String GEOLOCALIZACION_T = "geolocalizacion_t";
    public final static String LOGIN_REPORT_T = "login_report_t";
    //public final static String IND_VE_T             = "individuals_ven_t";
    //public final static String GPO_VE_T             = "groups_ven_t";
    public final static String SINCRONIZADO_T = "sincronizado_t";
    public final static String SOLICITUDES_IND_T = "solicitudes_ind_t";
    public final static String TBL_RECIBOS_VIGENTE_T = "tbl_recibos_vigente_t";
    public final static String TBL_RECIBOS_VENCIDA_T = "tbl_recibos_vencida_t";
    public final static String TBL_REIMPRESION_T = "tbl_reimpresion_t";
    public final static String TBL_CARTERA_IND_T = "cartera_individual_t";
    public final static String TBL_CARTERA_GPO_T = "cartera_grupo_t";
    public final static String TBL_CARTERA_IND = "cartera_individual";
    public final static String TBL_CARTERA_GPO = "cartera_grupo";
    public final static String TBL_PRESTAMOS_IND_T = "tbl_prestamos_ind_t";
    public final static String TBL_PRESTAMOS_IND = "tbl_prestamos_ind";
    public final static String TBL_AMORTIZACIONES = "tbl_amortizaciones";
    public final static String TBL_AMORTIZACIONES_T = "tbl_amortizaciones_t";
    public final static String TBL_PAGOS_IND_T = "tbl_pagos_ind_t";
    public final static String TBL_PAGOS_IND = "tbl_pagos_ind";
    public final static String TBL_RECIBOS = "tbl_recibos";
    public final static String TBL_SOPORTE = "tbl_soporte";
    public final static String TBL_REPORTE_SESIONES = "tbl_reporte_sesiones";
    public final static String TBL_SUCURSALES = "tbl_sucursales";
    public final static String TBL_CODIGOS_OXXO = "tbl_codigos_oxxo";
    public final static String TBL_RESUMENES_GESTION = "tbl_resumenes_gestion";
    public final static String TBL_PRESTAMOS_TO_RENOVAR = "tbl_prestamos_to_renovar";
    //public final static String SOLICITUDES              = "solicitudes";
    //public final static String SOLICITUDES_T            = "solicitudes_t";

    public final static String TBL_SOLICITUDES = "tbl_solicitudes";
    public final static String TBL_DIRECCIONES = "tbl_direccion";

    public final static String TBL_CREDITO_IND = "tbl_credito_ind";
    public final static String TBL_CLIENTE_IND = "tbl_cliente_ind";
    public final static String TBL_CONYUGE_IND = "tbl_conyuge_ind";
    public final static String TBL_ECONOMICOS_IND = "tbl_economicos_ind";
    public final static String TBL_NEGOCIO_IND = "tbl_negocio_ind";
    public final static String TBL_AVAL_IND = "tbl_aval_ind";
    public final static String TBL_REFERENCIA_IND = "tbl_referencia_ind";
    public final static String TBL_CROQUIS_IND = "tbl_croquis_ind";
    public final static String TBL_POLITICAS_PLD_IND = "politicas_pld_ind";
    public final static String TBL_DOCUMENTOS = "tbl_documentos";
    public final static String TBL_CREDITO_GPO = "tbl_credito_gpo";
    public final static String TBL_INTEGRANTES_GPO = "tbl_integrantes_gpo";
    public final static String TBL_TELEFONOS_INTEGRANTE = "tbl_telefonos_integrante";
    public final static String TBL_DOMICILIO_INTEGRANTE = "tbl_domicilio_integrante";
    public final static String TBL_NEGOCIO_INTEGRANTE = "tbl_negocio_integrante";
    public final static String TBL_CONYUGE_INTEGRANTE = "tbl_conyuge_integrante";
    public final static String TBL_OTROS_DATOS_INTEGRANTE = "tbl_otros_datos_integrante";
    public final static String TBL_CROQUIS_GPO = "tbl_croquis_gpo";
    public final static String TBL_DATOS_BENEFICIARIO = "tbl_datos_beneficiario_ind";
    public final static String TBL_DATOS_BENEFICIARIO_REN = "tbl_datos_beneficiario_ind_ren";
    public final static String TBL_DATOS_BENEFICIARIO_GPO = "tbl_datos_beneficiario_gpo";
    public final static String TBL_DATOS_BENEFICIARIO_GPO_REN = "tbl_datos_beneficiario_gpo_ren";
    public final static String TBL_POLITICAS_PLD_INTEGRANTE = "tbl_politicas_pld_integrante";
    public final static String TBL_DOCUMENTOS_INTEGRANTE = "documentos_integrante";
    public final static String TBL_CATALOGOS_CAMPANAS = "tbl_catalogo_campanas";
    public final static String TBL_SUCURSALES_LOCALIDADES = "tbl_sucursales_localidades";
    public final static String TBL_DATOS_CREDITO_CAMPANA = "tbl_datos_credito_campana";
    public final static String TBL_DATOS_CREDITO_CAMPANA_GPO = "tbl_datos_credito_campana_gpo";
    public final static String TBL_DATOS_CREDITO_CAMPANA_GPO_REN = "tbl_datos_credito_campana_gpo_ren";
    public final static String TBL_DATOS_ENTREGA_CARTERA = "tbl_datos_entrega_cartera";

    public final static String DATOS_CREDITO_GPO = "datos_credito_gpo";
    public final static String DATOS_CREDITO_GPO_T = "datos_credito_gpo_t";
    public final static String DATOS_INTEGRANTES_GPO = "datos_integrantes_gpo";
    public final static String DATOS_INTEGRANTES_GPO_T = "datos_integrantes_gpo_t";
    public final static String TELEFONOS_INTEGRANTE = "telefonos_integrante";
    public final static String TELEFONOS_INTEGRANTE_T = "telefonos_integrante_t";
    public final static String DOMICILIO_INTEGRANTE = "domicilio_integrante";
    public final static String DOMICILIO_INTEGRANTE_T = "domicilio_integrante_t";
    public final static String NEGOCIO_INTEGRANTE = "negocio_integrante";
    public final static String NEGOCIO_INTEGRANTE_T = "negocio_integrante_t";
    public final static String CONYUGE_INTEGRANTE = "conyuge_integrante";
    public final static String CONYUGE_INTEGRANTE_T = "conyuge_integrante_t";
    public final static String OTROS_DATOS_INTEGRANTE = "otros_datos_integrante";
    public final static String OTROS_DATOS_INTEGRANTE_T = "otros_datos_integrante_t";
    public final static String DOCUMENTOS_INTEGRANTE = "documentos_integrante";
    public final static String DOCUMENTOS_INTEGRANTE_T = "documentos_integrante_t";
    public final static String RECIBOS_CIRCULO_CREDITO_T = "recibos_circulo_credito_t";
    public final static String TBL_RESPUESTAS_IND_T = "respuestas_ind_t";
    public final static String TBL_RESPUESTAS_IND_V_T = "respuestas_ind_v_t";
    public final static String TBL_RESPUESTAS_INTEGRANTE_T = "respuestas_integrante_v_t";
    public final static String TBL_RESPUESTAS_IND = "respuestas_ind";
    public final static String TBL_RESPUESTAS_GPO_T = "respuestas_gpo_t";
    public final static String TBL_RESPUESTAS_GPO_V_T = "respuestas_gpo_v_t";
    public final static String TBL_RESPUESTAS_GPO = "respuestas_gpo";

    public final static String TBL_PLAZOS_PRESTAMOS = "tbl_plazos_prestamos";

    public final static String TBL_TELEFONOS_CLIENTE = "tbl_telefonos_cliente";


    public final static String TBL_CIERRE_DIA_T = "tbl_cierre_dia_t";

    public final static String TBL_IMPRESIONES_VIGENTE_T = "tbl_impresiones_vigente_t";
    public final static String TBL_IMPRESIONES_VIGENTE = "tbl_impresiones_vigente";
    public final static String TBL_IMPRESIONES_VENCIDA_T = "tbl_impresiones_vencida_t";
    public final static String TBL_IMPRESIONES_VENCIDA = "tbl_impresiones_vencida";
    public final static String TBL_REIMPRESION_VIGENTE_T = "tbl_reimpresion_vigente_t";
    public final static String TBL_REIMPRESION_VIGENTE = "tbl_reimpresion_vigente";

    public final static String TBL_PRESTAMOS_GPO = "tbl_prestamos_gpo";
    public final static String TBL_PRESTAMOS_GPO_T = "tbl_prestamos_gpo_t";
    public final static String TBL_MIEMBROS_GPO = "tbl_miembros_gpo";
    public final static String TBL_MIEMBROS_GPO_T = "tbl_miembros_gpo_t";
    public final static String TBL_MIEMBROS_PAGOS = "tbl_miembros_pagos";
    public final static String TBL_MIEMBROS_PAGOS_T = "tbl_miembros_pagos_t";
    public final static String TBL_PAGOS = "tbl_pagos";
    public final static String TBL_PAGOS_T = "tbl_pagos_t";
    public final static String TBL_ARQUEO_CAJA_T = "tbl_arqueo_caja_t";
    public final static String TBL_ARQUEO_CAJA = "tbl_arqueo_caja";
    public final static String TBL_TRACKER_ASESOR_T = "tbl_tracker_asesor_t";
    public final static String TBL_TRACKER_ASESOR = "tbl_tracker_asesor";

    public final static String TBL_GEO_RESPUESTAS_T = "geo";

    public final static String TBL_AVAL_T = "tbl_aval_t";
    public final static String TBL_AVAL = "tbl_aval";

    public static final String TBL_CANCELACIONES = "tbl_cancelaciones";

    public final static String TBL_SOLICITUDES_AUTO = "tbl_solicitudes_auto";
    public final static String TBL_CREDITO_IND_AUTO = "tbl_credito_ind_auto";
    public final static String TBL_CLIENTE_IND_AUTO = "tbl_cliente_ind_auto";
    public final static String TBL_CONYUGE_IND_AUTO = "tbl_conyuge_ind_auto";
    public final static String TBL_ECONOMICOS_IND_AUTO = "tbl_economicos_ind_auto";
    public final static String TBL_NEGOCIO_IND_AUTO = "tbl_negocio_ind_auto";
    public final static String TBL_AVAL_IND_AUTO = "tbl_aval_auto";
    public final static String TBL_REFERENCIA_IND_AUTO = "tbl_referencia_ind_auto";
    public final static String TBL_CROQUIS_IND_AUTO = "tbl_croquis_ind_auto";
    public final static String TBL_POLITICAS_PLD_IND_AUTO = "tbl_politicas_pld_ind_auto";
    public final static String TBL_DIRECCIONES_AUTO = "tbl_direcciones_auto";

    public final static String TBL_CREDITO_GPO_AUTO = "tbl_credito_gpo_auto";
    public final static String TBL_INTEGRANTES_GPO_AUTO = "tbl_integrantes_gpo_auto";
    public final static String TBL_TELEFONOS_INTEGRANTE_AUTO = "tbl_telefonos_integrante_auto";
    public final static String TBL_DOMICILIO_INTEGRANTE_AUTO = "tbl_domicilio_integrante_auto";
    public final static String TBL_NEGOCIO_INTEGRANTE_AUTO = "tbl_negocio_integrante_auto";
    public final static String TBL_CONYUGE_INTEGRANTE_AUTO = "tbl_conyuge_integrante_auto";
    public final static String TBL_OTROS_DATOS_INTEGRANTE_AUTO = "tbl_otros_datos_integrante_auto";
    public final static String TBL_CROQUIS_GPO_AUTO = "tbl_croquis_gpo_auto";
    public final static String TBL_POLITICAS_PLD_INTEGRANTE_AUTO = "tbl_politicas_pld_integrante_auto";

    public final static String TBL_SOLICITUDES_REN = "tbl_solicitudes_ren";
    public final static String TBL_CREDITO_IND_REN = "tbl_credito_ind_ren";
    public final static String TBL_CLIENTE_IND_REN = "tbl_cliente_ind_ren";
    public final static String TBL_CONYUGE_IND_REN = "tbl_conyuge_ind_ren";
    public final static String TBL_ECONOMICOS_IND_REN = "tbl_economicos_ind_ren";
    public final static String TBL_NEGOCIO_IND_REN = "tbl_negocio_ind_ren";
    public final static String TBL_AVAL_IND_REN = "tbl_aval_ren";
    public final static String TBL_REFERENCIA_IND_REN = "tbl_referencia_ind_ren";
    public final static String TBL_CROQUIS_IND_REN = "tbl_croquis_ind_ren";
    public final static String TBL_POLITICAS_PLD_IND_REN = "tbl_politicas_pld_ind_ren";
    public final static String TBL_DIRECCIONES_REN = "tbl_direcciones_ren";
    public final static String TBL_DOCUMENTOS_REN = "tbl_documentos_ren";

    public final static String TBL_CREDITO_GPO_REN = "tbl_credito_gpo_ren";
    public final static String TBL_INTEGRANTES_GPO_REN = "tbl_integrantes_gpo_ren";
    public final static String TBL_TELEFONOS_INTEGRANTE_REN = "tbl_telefonos_integrante_ren";
    public final static String TBL_DOMICILIO_INTEGRANTE_REN = "tbl_domicilio_integrante_ren";
    public final static String TBL_NEGOCIO_INTEGRANTE_REN = "tbl_negocio_integrante_ren";
    public final static String TBL_CONYUGE_INTEGRANTE_REN = "tbl_conyuge_integrante_ren";
    public final static String TBL_OTROS_DATOS_INTEGRANTE_REN = "tbl_otros_datos_integrante_ren";
    public final static String TBL_CROQUIS_GPO_REN = "tbl_croquis_gpo_ren";
    public final static String TBL_POLITICAS_PLD_INTEGRANTE_REN = "tbl_politicas_pld_integrante_ren";
    public final static String TBL_DOCUMENTOS_INTEGRANTE_REN = "tbl_documentos_integrante_ren";

    public final static String TBL_PRESTAMOS = "tbl_prestamos";

    public final static String TBL_RECIBOS_AGF_CC = "tbl_recibos_agf_cc";
    public final static String TBL_RECUPERACION_RECIBOS = "tbl_recuperacion_recibos";

    public final static String TBL_CONSULTA_CC = "tbl_consulta_cc";
    public final static String TBL_RECIBOS_CC = "tbl_recibos_cc";
    public final static String TBL_RECUPERACION_RECIBOS_CC = "tbl_recuperacion_recibos_cc";

    public final static String TBL_SERVICIOS_SINCRONIZADOS = "tbl_servicios_sincronizados";
    public final static String TBL_DOCUMENTOS_CLIENTES = "tbl_documentos_clientes";

    public final static String TBL_VERIFICACIONES_DOMICILIARIAS = "tbl_verificaciones_domiciliarias";
    public final static String TBL_GESTIONES_VER_DOM = "tbl_gestiones_ver_dom";

    public final static String STATUS_FICHAS = "status_fichas";
    public final static String ESTADOS = "estados";
    public final static String MUNICIPIOS = "municipios";
    public final static String LOCALIDADES = "localidades";
    public final static String COLONIAS = "cat_colonias";
    public final static String OCUPACIONES = "ocupaciones";
    public final static String SECTORES = "sectores";
    public final static String IDENTIFICACIONES = "identificaciones";
    public final static String TICKETS = "tickets";
    public final static String CAMPANAS = "campanas";
    public final static String CARTERAEN = "carteraEn";
    public final static String SUCLOCALIDADES = "suc_localidades";

    public final static String TBL_IDENTIFICACIONES_TIPO = "tbl_identificacion_tipo";
    public final static String TBL_VIVIENDA_TIPOS = "tbl_vivienda_tipos";
    public final static String TBL_DESTINOS_CREDITO = "tbl_destinos_credito";
    public final static String TBL_ESTADOS_CIVILES = "tbl_estados_civiles";
    public final static String TBL_NIVELES_ESTUDIOS = "tbl_niveles_estudios";
    public final static String TBL_MEDIOS_PAGO_ORI = "tbl_medios_pago_ori";
    public final static String TBL_PARENTESCOS = "tbl_parentescos";
    public final static String TBL_MEDIOS_CONTACTO = "tbl_medios_contacto";

    //====================   TAGS  PARA  JSON  DE  RESPUESTA  DE  GESTION  =========================
    public final static String RESUMEN = "Resumen";
    public final static String UBICACION = "Ubicacion";
    public final static String LATITUD = "Latitud";
    public final static String LONGITUD = "Longitud";
    public final static String CONTACTO = "Contacto";
    public final static String MOTIVO_ACLARACION = "MotivoAclaracion";
    public final static String COMENTARIO = "Comentario";
    public final static String GERENTE = "Gerente";
    public final static String FIRMA = "Firma";
    public final static String FACHADA = "Fachada";
    public final static String EVIDENCIA = "Evidencia";
    public final static String ACTUALIZAR_TELEFONO = "ActualizarTelefono";
    public final static String NUEVO_TELEFONO = "Nuevo_telefono";
    public final static String RESULTADO_PAGO = "ResultadoPago";
    public final static String POS_MEDIO_PAGO = "PosMedioPago";
    public final static String MEDIO_PAGO = "MedioPago";
    public final static String FECHA_DEPOSITO = "FechaDeposito";
    public final static String SALDO_CORTE = "SaldoCorte";
    public final static String MONTO_REQUERIDO = "MontoRequerido";
    public final static String FOLIO_TICKET = "FolioTicket";
    public final static String POS_MOTIVO_NO_PAGO = "PosMotivoNoPago";
    public final static String MOTIVO_NO_PAGO = "MotivoNoPago";
    public final static String FECHA_DEFUNCION = "FechaDefuncion";
    public final static String FECHA_PROMESA_PAGO = "FechaPromesaPago";
    public final static String MONTO_PROMESA = "MontoPromesa";
    public final static String RESUMEN_INTEGRANTES = "ResumenInegrantes";
    public final static String INTEGRANTES = "Integrantes";
    public final static String MOTIVO_NO_CONTACTO = "MotivoNoContacto";
    public final static String RESPUESTA_GESTION = "RespuestaGestion";
    public final static String IMPRESORA = "Impresora";
    public final static String DETALLE_FICHA = "DetalleFicha";
    public final static String TERMINADO = "Terminado";
    public final static String EDITABLE = "Editable";
    public final static String TIPO = "Tipo";
    public final static String ID_PRESTAMO = "id_prestamo";
    public final static String TIPO_PRESTAMO = "tipo_prestamo";
    public final static String TIPO_GESTION = "tipo_gestion";
    public final static String ID_GESTION = "id_gestion";
    public final static String TOTAL = "total";
    public final static String TBL_NAME = "tbl_name";
    public final static String TIPO_CARTERA = "tipo_cartera";
    public final static String FICHA_TIPO = "ficha_tipo";
    public final static String PRESTAMO_ID = "prestamo_id";
    public final static String FECHA_DISPOSITIVO = "fecha_dispositivo";
    public final static String FECHA_RESPUESTA = "fecha_respuesta";
    public final static String FECHA_INI_GEO = "fecha_ini_geo";
    public final static String FECHA_FIN_GEO = "fecha_fin_geo";
    public final static String FECHA_ENVIO = "fecha_envio";
    public final static String ID_CARTERAEN = "id_tipocartera";

    public final static String TITULO = "titulo";
    public final static String CATALOGO = "catalogo";
    public final static String ITEM = "item";
    public final static String TICKET_CC = "ticket_cc";
    public final static String REQUEST_CODE = "request_code";
    public final static String EXTRA = "extra";

    //======================= KYES PARA ORIGINACION  =======================================
    public final static String ID_SOLICITUD = "id_solicitud";
    public final static String ES_RENOVACION = "es_renovacion";
    public final static String K_PLAZO = "plazo";
    public final static String K_PERIODICIDAD = "periodicidad";
    public final static String K_FECHA_DESEMBOLSO = "fecha_desembolso";
    public final static String K_HORA_VISITA = "hora_visita";
    public final static String K_MONTO_PRESTAMO = "monto_prestamo";
    public final static String K_MONTO_LETRA = "monto_letra";
    public final static String K_ID_CAMPANA = "id_campana";
    public final static String K_DESTINO_PRESTAMO = "destino_prestamo";
    public final static String K_CLASIFICACION_RIESGO = "clasificacion_riesgo";
    public final static String K_COMPORTAMIENTO_PAGO = "comportamiento_pago";
    public final static String K_OBSERVACIONES = "observaciones";
    public final static String K_TIPO_SOLICITUD = "tipo_solicitud";
    public final static String K_CLIENTE_ID = "cliente_id";
    public final static String K_GRUPO_ID = "grupo_id";
    public final static String K_ESTATUS_INTEGRANTE = "estatus_integrante";
    public final static String K_FECHA_INICIO = "fecha_inicio";
    public final static String K_FECHA_ENVIO = "fecha_envio";
    public final static String K_MONTO_REFINANCIAR = "monto_refinanciar";
    public final static String K_SOLICITUD_ID = "solicitud_id";
    public final static String K_FECHA_TERMINO = "fecha_termino";
    public final static String K_SOLICITANTE = "solicitante";
    public final static String K_OTROS_DATOS = "otros_datos";
    public final static String K_CASA_REUNION = "casa_reunion";
    public final static String K_CARGO = "cargo";
    public final static String K_NOMBRE = "nombre";
    public final static String K_TOTAL_INTEGRANTES = "total_integrantes";
    public final static String K_NOMBRE_GRUPO = "nombre_grupo";
    public final static String K_PATERNO = "paterno";
    public final static String K_MATERNO = "materno";
    public final static String K_FECHA_NACIMIENTO = "fecha_nacimiento";
    public final static String K_EDAD = "edad";
    public final static String K_GENERO = "genero";
    public final static String K_ESTADO_NACIMIENTO = "estado_nacimiento";
    public final static String K_RFC = "rfc";
    public final static String K_CURP = "curp";
    public final static String K_PARENTESCO_SOLICITANTE = "parentesco_solicitante";
    public final static String K_OCUPACION = "ocupacion";
    public final static String K_NACIONALIDAD = "nacionalidad";
    public final static String K_ACTIVIDAD_ECONOMICA = "actividad_economica";
    public final static String K_DESTINO_CREDITO = "destino_credito";
    public final static String K_OTRO_DESTINO_CREDITO = "otro_destino_credito";
    public final static String K_IDENTIFICACION_TIPO = "identificacion_tipo";
    public final static String K_NO_IDENTIFICACION = "no_identificacion";
    public final static String K_NIVEL_ESTUDIO = "nivel_estudio";
    public final static String K_ESTADO_CIVIL = "estado_civil";
    public final static String K_BIENES = "bienes";
    public final static String K_TIPO_VIVIENDA = "tipo_vivienda";
    public final static String K_PARENTESCO = "parentesco";
    public final static String K_PARENTESCO_TITULAR = "parentesco_titular";
    public final static String K_OTRO_TIPO_VIVIENDA = "otro_tipo_vivienda";
    public final static String K_CARACTERISTICAS_DOMICILIO = "caracteristicas_domicilio";
    public final static String K_TIENE_NEGOCIO = "tiene_negocio";
    public final static String K_NOMBRE_NEGOCIO = "nombre_negocio";
    public final static String K_LATITUD = "latitud";
    public final static String K_LONGITUD = "longitud";
    public final static String K_CALLE = "calle";
    public final static String K_NO_EXTERIOR = "no_exterior";
    public final static String K_NO_INTERIOR = "no_interior";
    public final static String K_NO_MANZANA = "no_manzana";
    public final static String K_NO_LOTE = "no_lote";
    public final static String K_CODIGO_POSTAL = "codigo_postal";
    public final static String K_COLONIA = "colonia";
    public final static String K_CIUDAD = "ciudad";
    public final static String K_LOCALIDAD = "localidad";
    public final static String K_MUNICIPIO = "municipio";
    public final static String K_ESTADO = "estado";
    public final static String K_TEL_CASA = "tel_casa";
    public final static String K_TEL_CELULAR = "tel_celular";
    public final static String K_TEL_MENSAJE = "tel_mensaje";
    public final static String K_TEL_TRABAJO = "tel_trabajo";
    public final static String K_TIEMPO_VIVIR_SITIO = "tiempo_vivir_sitio";
    public final static String K_DEPENDIENTES_ECONOMICO = "dependientes_economico";
    public final static String K_MEDIO_CONTACTO = "medio_contacto";
    public final static String K_ESTADO_CUENTA = "estado_cuenta";
    public final static String K_EMAIL = "email";
    public final static String K_FOTO_FACHADA = "foto_fachada";
    public final static String K_REFERENCIA_DOMICILIARIA = "referencia_domiciliaria";
    public final static String K_FIRMA = "firma";
    public final static String K_LOCATED_AT = "located_at";
    public final static String K_AVAL_LOCATED_AT = "aval_located_at";
    public final static String K_AVAL_LATITUD = "aval_latitud";
    public final static String K_AVAL_LONGITUD = "aval_longitud";
    public final static String K_SOL_LOCATED_AT = "solicitante_located_at";
    public final static String K_SOL_LATITUD = "solicitante_latitud";
    public final static String K_SOL_LONGITUD = "solicitante_longitud";
    public final static String K_TIENE_FIRMA = "tiene_firma";
    public final static String K_NOMBRE_QUIEN_FIRMA = "nombre_quien_firma";


    public final static String K_SOLICITANTE_CONYUGE = "solicitante_conyugue";

    public final static String K_SOLICITANTE_DATOS_ECONOMICOS = "solicitante_datos_economicos";
    public final static String K_PROPIEDADES = "propiedades";
    public final static String K_VALOR_APROXIMADO = "valor_aproximado";
    public final static String K_UBICACION = "ubicacion";
    public final static String K_INGRESO = "ingreso";

    public final static String K_SOLICITANTE_NEGOCIO = "solicitante_negocio";
    public final static String K_ANTIGUEDAD = "antiguedad";
    public final static String K_INGRESO_MENSUAL = "ingreso_mensual";
    public final static String K_GASTO_MENSUAL = "gasto_mensual";
    public final static String K_INGRESOS_OTROS = "ingresos_otros";
    public final static String K_GASTO_SEMANAL = "gasto_semanal";
    public final static String K_GASTO_AGUA = "gasto_agua";
    public final static String K_GASTO_LUZ = "gasto_luz";
    public final static String K_GASTO_TELEFONO = "gasto_telefono";
    public final static String K_GASTO_RENTA = "gasto_renta";
    public final static String K_GASTO_OTROS = "gasto_otros";
    public final static String K_CAPACIDAD_PAGO = "capacidad_pago";
    public final static String K_MEDIOS_PAGOS = "medios_pago";
    public final static String K_OTRO_MEDIOS_PAGOS = "otro_medio_pago";
    public final static String K_MONTO_MAXIMO = "monto_maximo";
    public final static String K_NUM_OPERACIONES_MENSUAL = "num_operaciones_mensuales";
    public final static String K_NUM_OPERACIONES_MENSUAL_EFECTIVO = "num_operaciones_mensuales_efectivo";
    public final static String K_DIAS_VENTA = "dias_venta";
    public final static String K_REFERENCIA_NEGOCIO = "referencia_negocio";

    public final static String K_SOLICITANTE_AVAL = "solicitante_aval";
    public final static String K_NOMBRE_TITULAR = "nombre_titular";
    public final static String K_HORA_LOCALIZACION = "hora_localizacion";
    public final static String K_ACTIVOS_OBSERVABLES = "activos_observables";

    public final static String K_SOLICITANTE_REFERENCIA = "solicitante_referencia";

    public final static String K_CALLE_ENFRENTE = "calle_enfrente";
    public final static String K_CALLE_LATERAL_IZQ = "lateral_izquierda";
    public final static String K_CALLE_LATERAL_DER = "lateral_derecha";
    public final static String K_CALLE_ATRAS = "calle_atras";
    public final static String K_REFERENCIAS = "referencias";
    public final static String K_SOLICITANTE_CROQUIS = "solicitante_croquis";

    public final static String K_PROPIETARIO = "propietario";
    public final static String K_PROVEEDOR_RECURSOS = "proveedor_recursos";
    public final static String K_POLITICAMENTE_EXP = "politicamente_expuesto";
    public final static String K_SOLICITANTE_POLITICAS = "solicitante_politicas";


    public final static String K_IDENTIFICACION_SELFIE = "identificacion_selfie";
    public final static String K_SOLICITANTE_DOCUMENTOS = "solicitante_documentos";
    public final static String K_IDENTIFICACION_FRONTAL = "identificacion_frontal";
    public final static String K_IDENTIFICACION_REVERSO = "identificacion_reverso";
    public final static String K_COMPROBANTE_DOMICILIO = "comprobante_domicilio";
    public final static String K_CODIGO_BARRAS = "codigo_barras";
    public final static String K_FIRMA_ASESOR = "firma_asesor";
    public final static String K_COMPROBANTE_GARANTIA = "comprobante_garantia";
    public final static String K_IDENTIFICACION_FRONTAL_AVAL = "identificacion_frontal_aval";
    public final static String K_IDENTIFICACION_REVERSO_AVAL = "identificacion_reverso_aval";
    public final static String K_CURP_AVAL = "curp_aval";
    public final static String K_COMPROBANTE_DOMICILIO_AVAL = "comprobante_domicilio_aval";

    //================================== KEYS FOR BENEFICIARIO ====================================//


    public final static String K_SOLICITANTE_BENEFICIARIO = "solicitante_beneficiario";
    public final static String K_ID_ORIGINACION = "id_originacion";
    public final static String K_ID_CLIENTE = "id_cliente";
    public final static String K_ID_GRUPO = "id_grupo";
    public final static String K_NOMBRE_BENEFICIARIO = "nombre";
    public final static String K_PATERNO_BENEFICIARIO = "paterno";
    public final static String K_MATERNO_BENEFICIARIO = "materno";
    public final static String K_PARENTESCO_BENEFICIARIO = "parentesco";
    public final static String K_SERIE_ID_BENEFICIARIO = "serieid";

}
