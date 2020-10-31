package com.sidert.sidertmovil.utils;

import com.sidert.sidertmovil.R;

public class WebServicesRoutes {

    //public final static String DOMAIN = "http://sidert.ddns.net:"; // Dominio
    //public final static String DOMAIN = "http://172.24.16.48:"; // Dominio producción con VPN
    //public final static String DOMAIN = "http://192.168.172.169:"; // Dominio local IP Francisco

    //public final static String PORT = "81"; //Puerto de producción sidert.ddns
    //public final static String PORT = "82"; //Puerto de desarrollo sidert.ddns
    //public final static String PORT = "83";   //Puerto de producción sidert.ddns
    //public final static String PORT = "8080"; //Puerto para VPN

    public final static String CONTROLLER_DENUNCIAS     = "/serviciosidert/Api.svc/"; //Controlador
    public final static String CONTROLLER_LOGIN         = "/login/"; //Controlador login
    public final static String CONTROLLER_FICHAS        = "/api/fichas/"; //Controlador para fichas
    public final static String CONTROLLER_CATALOGOS     = "/api/catalogos/"; //Controlador para catalogos
    public final static String CONTROLLER_CODIGOS       = "/api/movil/codigosoxxo/"; //Controlador para catalogos
    public final static String CONTROLLER_API           = "/api/"; //Controlador
    public final static String CONTROLLER_SOLICITUDES   = "/api/solicitudes/creditos/"; //Controlador para solicitudes
    public final static String CONTROLLER_MOVIL         = "/api/movil/"; //Controlador para movil
    public final static String IMAGES_GEOLOCALIZACION   = "uploads/img/";
    public final static String PDF_CODIGOS_OXXO         = "uploads/codigos_oxxo/";
    public final static String CONTROLLER_RECIBOS       = "/api/movil/recibos/"; //Controlador para recibos
    public final static String CONTROLLER_SOPORTE       = "/api/movil/soporte/"; //Controlador para soporte
    public final static String CONTROLLER_APK           = "/api/movil/apk/"; //Controlador para apk


    //public final static String BASE_URL = DOMAIN + PORT;

    //Servicios
    public final static String WS_LOGIN                     = "token"; //Servicio para inicio de sesion
    //public final static String WS_LOGIN_movil               = "token"; //Servicio para inicio de sesion para movil
    public final static String WS_GET_CARTERA               = "carteras"; //Servicio para obtener la cartera
    public final static String WS_GET_PRESTAMOS_AGF_CC      = "prestamos"; //Servicio para obtener prestamos para AGF y CC
    public final static String WS_GET_PRESTAMO_RENOVAR      = "ultimoprestamoind"; //Servicio para obtener datos del cliente para renovacion
    public final static String WS_GET_PRESTAMO_RENOVAR_GPO  = "ultimoprestamogpo"; //Servicio para obtener datos del grupo para renovacion
    public final static String WS_GET_TICKETS               = "getTickets"; //Servicio para obtener la cartera
    public final static String WS_GET_ULTIMAS_IMPRESIONES   = "ultimosRegistro"; //Servicio para obtener los ultimos registros de impresiones
    public final static String WS_POST_IMPRESIONES          = "impresion"; //Servicio para registrar las impresiones
    public final static String WS_POST_TRACKER              = "tracker"; //Servicio para registrar la ubicacion del dispositivo cada 10 minutos
    public final static String WS_GET_TRACKER_ASESOR        = "getTrackerAsesor"; //Servicio para para obtener la ratua del asesor
    public final static String WS_GET_SUCURSALES            = "getSucursales"; //Servicio para para obtener mis sucursales son sus asesores correspondientes
    public final static String WS_GET_PRESTAMOS_IND         = "prestamos/cliente/{id_cliente}"; //Servicio para obtener los prestamos individuales
    public final static String WS_GET_PRESTAMOS_GPO         = "prestamos/grupo/{id_grupo}"; //Servicio para obtener los prestamos grupales
    public final static String WS_POST_SAVE_AGF             = "saveAgf"; //Servicio para guardar los recibos de apoyo a gastos funerarios
    public final static String WS_MAILBOX                   = "Denuncia"; //Servicio para buzón de quejas o sugerencias
    public final static String WS_SYNCHRONIZEBD             = "UltimosRegistrosLog"; //Servicio para obtener los registros de impresiones y actualizar su BD local
    public final static String WS_GET_GEOLOCALIZACIONES     = "GetGeolocalizadas"; //Servicio para obtener las fichas de geolocalización
    public final static String WS_GET_LOG_ASESORES          = "logAsesores"; //Servicio para obtener el inicio de sesion de los asesores
    public final static String WS_GET_GENERAR_CODIGO_OXXO   = "generarCodigo"; //Servicio para obtener el inicio de sesion de los asesores
    public final static String WS_GET_MIS_SUCURSALES        = "GetSucursalesByUsuario/{usuarioId}"; //Servicio para obtener mis sucursales
    public final static String WS_SAVE_GEO                  = "SaveGeolocalizacion"; //Guarda la informacipon capturada de GEOLOCALIZACION
    public final static String WS_POST_ORIGINACION_IND      = "individuales"; //Guarda la solicitud de credito individual (originacion)
    public final static String WS_POST_ORIGINACION_GPO      = "grupales"; //Guarda la solicitud de credito grupal (originacion)
    public final static String WS_POST_CIERRE_DIA           = "SaveCierreDia"; //Guarda el cierre de día
    public final static String WS_POST_RESPUESTA_GESTION    = "gestiones"; //Guarda la respuesta gestion de recuperaciones
    //public final static String WS_GET_DOWNLOAD_APK          = "uploads/apks/sidert_movil.apk"; //Descarga archivo de apk
    public final static String WS_GET_DOWNLOAD_APK          = "downloadApk"; //Valida la contraseña para descarga archivo de apk
    public final static String WS_GET_SETTINGS_APP          = "settings"; //Valida la contraseña para activar configuraciones de la fecha y hora
    public final static String WS_GET_PRESTAMOS_RENOVAR     = "prestamostorenovar"; //Obtiene los prestamos listos para renovar
    public final static String WS_POST_SOLICITUD_CANCELAR   = "solicitudCancelarGestion"; //Servicio para cancelar solicitud de una gestion
    public final static String WS_GET_GESTIONES_CANCELADAS  = "getGestionesCanceladas"; //Servicio para obtener las gestiones canceladas por el administrador
    public final static String WS_POST_RECIBO               = "guardarRecibo"; //Servicio para registrar los recibos CC yAGF
    public final static String WS_POST_DEVICE_TOKEN         = "deviceToken"; //Servicio para registrar el device token para las notificaciones push
    public final static String WS_POST_SOPORTE              = "guardarTicket"; //Servicio para registrar los levantamientos de soporte
    public final static String WS_GET_ULTIMOS_RECIBOS       = "getUltimoRecibo"; //Servicio para obtener los ultimos folio de recibos entre CC y AGF
    public final static String WS_GET_GESTIONES             = "getUltimasGestiones"; //Servicio para obtener las ultimas gestiones de la semana actual
    public final static String WS_GET_SOLIC_RECHAZO_IND     = "individuales_rechazos"; //Obtiene un listado de las solicitudes para obtener las secciones de la solicitud y su estatus
    public final static String WS_GET_SOLIC_RECHAZO_GPO     = "grupales_rechazos"; //Obtiene un listado de las solicitudes de los integrantes del grupo que fueron rechazados

    //Catálogos
    public final static String WS_GET_ESTADOS               = "GetEstados"; //Obtiene el catálogo de estados de México
    public final static String WS_GET_MUNICIPIOS            = "GetMunicipios"; //Obtiene el catálogo de municipios
    public final static String WS_GET_COLONIAS              = "GetColonias"; //Obtiene el catálogo de colonias
    public final static String WS_GET_OCUPACIONES           = "GetOcupaciones"; //Obtiene el catálogo de las ocupaciones
    public final static String WS_GET_SECTORES              = "GetSectores"; //Obtiene el catálogo de sectores (actividades económicas)
    public final static String WS_GET_IDENTIFICACIONES      = "GetIdentificacionTipos"; //Obtiene el catálogo de tipos de identificacion (INE/IFE, Pasaporte...)
    public final static String WS_GET_VIVIENDA_TIPOS        = "GetViviendaTipos"; //Obtiene el catálogo de tipos de vivienda
    public final static String WS_GET_MEDIOS_CONTACTO       = "GetMediosContacto"; //Obtiene el catálogo de los medios de contacto
    public final static String WS_GET_DESTINOS_CREDITO      = "GetDestinosCredito"; //Obtiene el catálogo de los destinos de credito
    public final static String WS_GET_CATEGORIA_TICKETS     = "GetCategoriaTickets"; //Obtiene el catálogo de las categorias de levantamiento de tickets
    public final static String WS_GET_PLAZOS_PRESTAMOS      = "GetPlazos"; //Obtiene el catálogo de los plazos de los prestamos
    public final static String WS_GET_ESTADOS_CIVILES       = "GetEstadosCiviles"; //Obtiene el catálogo de los estados civiles
    public final static String WS_GET_NIVELES_ESTUDIOS      = "GetNivelesEstudios"; //Obtiene el catálogo de los niveles de estudios
    public final static String WS_GET_MEDIOS_PAGO_ORIG      = "GetMediosPagoOriginacion"; //Obtiene el catálogo de los medios de pagos de originación
    public final static String WS_GET_PARENTESCOS           = "GetParentescos"; //Obtiene el catálogo de los parentescos



}
