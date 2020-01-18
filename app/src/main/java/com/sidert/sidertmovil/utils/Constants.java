package com.sidert.sidertmovil.utils;

import android.os.Environment;

public class Constants {

    public final static boolean ENVIROMENT          = true;

    // CONTROLADORES
    public final static String CONTROLLER_LOGIN       = "login";
    public final static String CONTROLLER_FICHAS      = "fichas";
    public final static String CONTROLLER_CATALOGOS   = "catalogos";

    // ACTION BAR CONSTANTS
    public final static String TBtitle   = "title";
    public final static String TBhasBack = "hasBack";

    public final static String RECOVERY         = "Recuperacion";
    public final static String COLLECTION       = "Cobranza";
    public final static String WALLET_EXPIRED   = "CarteraVencida";

    public final static String ASESSOR          = "Asesor";
    public final static String MANNAGER           = "Gestor";

    public final static String ERROR            = "Error";

    public final static String FICHA            = "ficha";

    public final static String FORMAT_DATE_GNRAL        = "yyyy-MM-dd";
    public final static String FORMAT_TIMESTAMP         = "yyyy-MM-dd HH:mm:ss";

    public final static String negative_payment         = "Negación de pago";
    public final static String outdate_information      = "Información Desfasada";
    public final static String death                    = "Fallecimiento";
    public final static String other                    = "Otro";

    public final static String bank                    = "Banco";
    public final static String oxxo                    = "Oxxo";
    public final static String cash                    = "Efectivo";
    public final static String sidert                  = "SIDERT";

    public final static String photo                    = "Fotografía";
    public final static String galery                  = "Galería";

    public final static String KEY                     = "key";
    public final static String VALUE                   = "value";

    public final static String PARAMS                   = "params";
    public final static String CONDITIONALS             = "conditionals";

    public final static String WHERE                    = "where";
    public final static String ORDER                    = "order";

    public final static String MESSAGE                  = "message";
    public final static String RESPONSE                 = "response";
    public final static String CODE                     = "code";

    public final static String RES_PRINT                = "res_print";

    public final static String uri_signature            = "uri_signature";

    public final static String type                     = "type";
    public final static String client_code              = "clave_cliente";

    public final static String EFECTIVO                 = "Efectivo";
    public final static String EXTERNAL_ID              = "external_id";

    //Iconos para colocar el dialog_message
    public final static String not_network              = "not_network";
    public final static String success                  = "success";
    public final static String face_happy               = "face_happy";
    public final static String face_dissatisfied        = "face_dissatisfied";
    public final static String print_off                = "print_off";
    public final static String money                    = "money";
    public final static String logout                   = "logout";
    public final static String firma                    = "firma";
    public final static String question                 = "question";
    public final static String warning                  = "warning";
    public final static String login                    = "login";
    public final static String camara                   = "camara";


    // Keys para servicios

    public final static String ADVISER_ID             = "asesorid";
    public final static String DATE                   = "fecha";
    public final static String ISSUE                  = "asunto";
    public final static String REASON                 = "motivo";
    public final static String FOLIO                  = "folio";
    public final static String SERIE_ID               = "serieid";
    public final static String NOMBRE_EMPLEADO        = "nombre";
    public final static String PATERNO                = "paterno";
    public final static String MATERNO                = "materno";
    public final static String USER_NAME              = "user_name";
    public final static String EMAIL                  = "email";
    public final static String AUTHORITIES            = "authorities";


    //======================  TAGS DE FICHAS  ===================================================
    public final static String ORDER_ID                     = "id";
    public final static String TYPE                         = "type";
    public final static String ASSIGN_DATE                  = "assignDate";
    public final static String EXPIRATION_DATE              = "expirationDate";
    public final static String CANCELLATION_DATE            = "cancellationDate";
    public final static String CLIENTE                      = "Cliente";
    public final static String GRUPO                        = "Grupo";
    public final static String GRUPAL                       = "Grupal";
    public final static String INDIVIDUAL                   = "Individual";
    public final static String NUMERO_CLIENTE               = "NumeroCliente";
    public final static String NOMBRE                       = "Nombre";
    public final static String TEL_CELULAR                  = "TelCelular";
    public final static String TELEFONO_CELULAR             = "TelefonoCelular";
    public final static String TELEFONO_DOMICILIO           = "TelefonoDomicilio";
    public final static String TEL_DOMICILIO                = "TelDomicilio";
    public final static String DIRECCION                    = "Direccion";
    public final static String CALLE                        = "Calle";
    public final static String CIUDAD                       = "Ciudad";
    public final static String CODIGO_POSTAL                = "CodigoPostal";
    public final static String COLONIA                      = "Colonia";
    public final static String MUNICIPIO                    = "Municipio";
    public final static String ESTADO                       = "Estado";
    public final static String LATITUDE                     = "latitude";
    public final static String LONGITUDE                    = "longitude";
    public final static String AVAL                         = "Aval";
    public final static String NOMBRE_COMPLETO_AVAL         = "NombreCompletoAval";
    public final static String TELEFONO_AVAL                = "TelefonoAval";
    public final static String ADDRESS_AVAL                 = "AddressAval";
    public final static String PARENTESCO_AVAL              = "ParentescoAval";
    public final static String PRESTAMO                     = "Prestamo";
    public final static String NUMERO_DE_PRESTAMO           = "NumeroDePrestamo";
    public final static String FECHA_CREDITO_OTORGADO       = "FechaDelCreditoOtorgado";
    public final static String MONTO_TOTAL_PRESTAMO         = "MontoTotalPrestamo";
    public final static String MONTO_PRESTAMO               = "MontoPrestamo";
    public final static String PAGO_SEMANAL                 = "PagoSemanal";
    public final static String CLAVE_CLIENTE                = "ClaveCliente";
    public final static String PAGO_REALIZADO               = "PagoRealizado";
    public final static String NUMERO_AMORTIZACION          = "NumeroAmortizacion";
    public final static String MONTO_AMORTIZACION           = "MontoAmortizacion";
    public final static String FECHA_PAGO_ESTABLECIDA       = "FechaPagoEstablecida";
    public final static String HORA_PAGO_ESTABLECIDA        = "HoraPagoEstablecida";
    public final static String SALDO_ACTUAL                 = "SaldoActual";
    public final static String SUMA_DE_PAGOS                = "SumaDePagos";
    public final static String DIAS_ATRASO                  = "DiasAtraso";
    public final static String FRECUENCIA                   = "Frecuencia";
    public final static String DIA_SEMANA                   = "DiaSemana";
    public final static String PERTENECE_GARANTIA           = "PerteneceGarantia";
    public final static String CUENTA_CON_GARANTIA          = "CuentaConGarantia";
    public final static String TIPO_GARANTIA                = "TipoGarantia";
    public final static String REPORTE_ANALITICO_OMEGA      = "ReporteAnaliticoOmega";
    public final static String NO                           = "No";
    public final static String FECHA_AMORTIZACION           = "FechaAmortizacion";
    public final static String FECHA_PAGO                   = "FechaPago";
    public final static String ESTATUS                      = "Estatus";
    public final static String DIAS                         = "Dias";
    public final static String TABLA_PAGOS_CLIENTE          = "TablaPagosCliente";
    public final static String FECHA                        = "Fecha";
    public final static String PAGO                         = "Pago";
    public final static String BANCO                        = "Banco";
    public final static String CLAVE_GRUPO                  = "ClaveGrupo";
    public final static String NOMBRE_GRUPO                 = "NombreGrupo";
    public final static String TOTAL_INTEGRANTES            = "TotalIntegrantes";
    public final static String INTEGRANTES_GRUPO            = "IntegrantesDelGrupo";
    public final static String MONTO                        = "Monto";
    public final static String PAGO_SEMANAL_INT             = "PagoSemanalInt";
    public final static String TESORERA                     = "Tesorera";
    public final static String ADDRESS                      = "Address";
    public final static String PRESIDENTA                   = "Presidenta";
    public final static String NOMBRE_PRESIDENTA            = "NombrePresidenta";
    public final static String ADDRESS_PRESIDENTA           = "AddressPresidenta";
    public final static String TEL_CELULAR_PRESIDENTA       = "TelCelularPresidenta";
    public final static String TEL_DOMICILIO_PRESIDENTA     = "TelCelularPresidenta";
    public final static String SECRETARIA                   = "Secretaria";
    public final static String NOMBRE_SECRETARIA            = "NombreSecretaria";
    public final static String ADDRESS_SECRETARIA           = "AddressSercretaria";
    public final static String TEL_CELULAR_SECRETARIA       = "TelCelularSecretaria";
    public final static String TEL_DOMICILIO_SECRETARIA     = "TelCelularSecretaria";
    public final static String TABLA_PAGOS_GRUPO            = "TablaPagosGrupo";
    public final static String FECHA_AMORTIZACION_GPO       = "Fecha_Amortizacion";
    public final static String FECHA_PAGO_GPO               = "Fecha_Pago";
    public final static String SAVE                         = "Guardar";
    public final static String PAGO_REQUERIDO               = "PagoRequerido";
    public final static String FIRMA_IMAGE                  = "FirmaImage";

    public final static String CODEBARS                     = "code_bars";

    public final static String NUM_SOLICITUD                = "num_solicitud";
    public final static String MODULO                       = "modulo";
    public final static String _ID                          = "_id";
    public final static String IMAGEN                       = "imagen";
    public final static String INTEGRANTE_TIPO              = "integrante_tipo";

    public final static String PICTURE                      = "picture";

    //==========================  TIPO INTEGRANTE  =================================================
    public final static String TIPO_CLIENTE                 = "CLIENTE";
    public final static String TIPO_NEGOCIO                 = "NEGOCIO";
    public final static String TIPO_AVAL                    = "AVAL";
    public final static String TIPO_PRESIDENTE              = "PRESIDENTE";
    public final static String TIPO_TESORERO                = "TESORERO";
    public final static String TIPO_SECRETARIO              = "SECRETARIO";


    //========================== PARAMS REQUESTS  ==================================================
    public final static String USERNAME                  = "username";
    public final static String PASSWORD                  = "password";
    public final static String GRANT_TYPE                = "grant_type";

    //==========================   REQUEST CODE   ==================================================
    public final static int REQUEST_CODE_FIRMA                     = 456;
    public final static int REQUEST_CODE_IMPRESORA                 = 963;
    public final static int REQUEST_CODE_INTEGRANTES_GPO           = 951;
    public final static int REQUEST_CODE_RESUMEN_INTEGRANTES_GPO   = 834;
    public final static int REQUEST_CODE_GALERIA                   = 852;
    public final static int REQUEST_CODE_CAMARA_TICKET             = 753;
    public final static int REQUEST_CODE_CAMARA_FACHADA            = 493;
    public final static int REQUEST_CODE_LLAMADA                   = 123;
    public final static int REQUEST_CODE_ARQUEO_CAJA               = 369;
    public final static int REQUEST_CODE_ACTIVITY                  = 749;
    public final static int REQUEST_CODE_PREVIEW                   = 864;
    public final static int REQUEST_CODE_CODEBARS                  = 462;
    public final static int REQUEST_CODE_ADD_INTEGRANTE            = 764;

    //===========================  ID JOB SCHEDULE  ================================================
    public final static int ID_JOB_LOGOUT                  = 324;
    public final static int ID_JOB_SINCRONIZADO            = 753;

    //===========================   TIPOS FICHAS  ==================================================
    public final static String RECUPERACION_IND         = "RecuperacionIndividual";
    public final static String RECUPERACION_GPO         = "RecuperacionGrupal";
    public final static String COBRANZA_IND             = "CobranzaVencidaIndividual";
    public final static String COBRANZA_GPO             = "CobranzaVencidaGrupal";
    public final static String CARTERA_VENCIDA_IND      = "CarteraVencidaIndividual";
    public final static String CARTERA_VENCIDA_GPO      = "CarteraVencidaGrupal";

    public final static int LIMIT_COMPLAINTS          = 2;

    public static final String PATH                   = Environment.getExternalStorageDirectory().getAbsolutePath();
    public static final String ROOT_PATH              = Environment.getExternalStorageDirectory().getAbsolutePath()+"/Android/data/com.sidert.sidertmovil/files/Pictures/Files/";

    //=========================  Nombre de tablas  =================================================
    public final static String LOG_ASESSOR          = "log_impressions_asessor";
    public final static String LOG_MANAGER          = "log_impressions_manager";
    public final static String LOG_ASESSOR_T        = "log_impresiones_asesor_test";
    public final static String LOG_MANAGER_T        = "log_impresiones_gestor_test";
    public final static String IND                  = "individuals";
    public final static String GPO                  = "groups";
    public final static String FICHAS               = "fichas";
    public final static String GEOLOCALIZACION      = "geolocalizacion";
    public final static String LOGIN_REPORT         = "login_report";
    //public final static String IND_VE               = "individuals_ven";
    //public final static String GPO_VE               = "groups_ven"
    public final static String SINCRONIZADO         = "sincronizado";
    public final static String IND_T                = "individuals_t";
    public final static String GPO_T                = "groups_t";
    public final static String FICHAS_T             = "fichas_t";
    public final static String GEOLOCALIZACION_T    = "geolocalizacion_t";
    public final static String LOGIN_REPORT_T       = "login_report_t";
    //public final static String IND_VE_T             = "individuals_ven_t";
    //public final static String GPO_VE_T             = "groups_ven_t";
    public final static String SINCRONIZADO_T       = "sincronizado_t";

    public final static String STATUS_FICHAS       = "status_fichas";
    public final static String ESTADOS             = "estados";
    public final static String MUNICIPIOS          = "municipios";
    public final static String COLONIAS            = "colonias";

    //====================   TAGS  PARA  JSON  DE  RESPUESTA  DE  GESTION  =========================
    public final static String LATITUD                  = "Latitud";
    public final static String LONGITUD                 = "Longitud";
    public final static String CONTACTO                 = "Contacto";
    public final static String MOTIVO_ACLARACION        = "MotivoAclaracion";
    public final static String COMENTARIO               = "Comentario";
    public final static String GERENTE                  = "Gerente";
    public final static String FIRMA                    = "Firma";
    public final static String FACHADA                  = "Fachada";
    public final static String EVIDENCIA                = "Evidencia";
    public final static String ACTUALIZAR_TELEFONO      = "ActualizarTelefono";
    public final static String NUEVO_TELEFONO           = "Nuevo_telefono";
    public final static String RESULTADO_PAGO           = "ResultadoPago";
    public final static String POS_MEDIO_PAGO           = "PosMedioPago";
    public final static String MEDIO_PAGO               = "MedioPago";
    public final static String FECHA_DEPOSITO           = "FechaDeposito";
    public final static String SALDO_CORTE              = "SaldoCorte";
    public final static String MONTO_REQUERIDO          = "MontoRequerido";
    public final static String FOLIO_TICKET             = "FolioTicket";
    public final static String POS_MOTIVO_NO_PAGO       = "PosMotivoNoPago";
    public final static String MOTIVO_NO_PAGO           = "MotivoNoPago";
    public final static String FECHA_DEFUNCION          = "FechaDefuncion";
    public final static String RESUMEN_INTEGRANTES      = "ResumenInegrantes";
    public final static String INTEGRANTES              = "Integrantes";
    public final static String MOTIVO_NO_CONTACTO       = "MotivoNoContacto";
    public final static String RESPUESTA_GESTION        = "RespuestaGestion";
    public final static String IMPRESORA                = "Impresora";
    public final static String DETALLE_FICHA            = "DetalleFicha";
    public final static String TERMINADO                = "Terminado";
    public final static String EDITABLE                 = "Editable";
    public final static String TIPO                     = "Tipo";





}
