package com.sidert.sidertmovil.utils;

public class WebServicesRoutes {

    public final static String DOMAIN = "http://sidert.ddns.net:"; // Dominio
    //public final static String DOMAIN = "http://172.24.16.48:"; // Dominio producción con VPN
    //public final static String DOMAIN = "http://192.168.0.125:"; // Dominio local IP Francisco

    //public final static String PORT = "81"; //Puerto de producción sidert.ddns
    //public final static String PORT = "82"; //Puerto de desarrollo sidert.ddns
    public final static String PORT = "83";   //Puerto de producción sidert.ddns
    //public final static String PORT = "8080"; //Puerto para VPN

    public final static String CONTROLLER_DENUNCIAS     = "/serviciosidert/Api.svc/"; //Controlador
    public final static String CONTROLLER_LOGIN         = "/oauth/"; //Controlador login
    public final static String CONTROLLER_FICHAS        = "/api/fichas/"; //Controlador para fichas
    public final static String CONTROLLER_CATALOGOS     = "/api/catalogos/"; //Controlador para catalogos
    public final static String CONTROLLER_IMPRESIONES   = "/api/"; //Controlador para impresiones
    public final static String CONTROLLER_SOLICITUDES   = "/api/solicitudes/creditos/"; //Controlador para solicitudes
    public final static String CONTROLLER_MOVIL         = "/api/movil/"; //Controlador para movil
    public final static String IMAGES_GEOLOCALIZACION   = "uploads/img/";


    public final static String BASE_URL = DOMAIN + PORT;

    //Servicios
    public final static String WS_LOGIN                     = "token"; //Servicio para inicio de sesion
    public final static String WS_GET_CARTERA               = "carteras"; //Servicio para obtener la cartera
    public final static String WS_GET_ULTIMAS_IMPRESIONES   = "ultimosRegistro"; //Servicio para obtener los ultimos registros de impresiones
    public final static String WS_POST_IMPRESIONES          = "impresion"; //Servicio para registrar las impresiones
    public final static String WS_POST_TRACKER              = "tracker"; //Servicio para registrar la ubicacion del dispositivo cada 10 minutos
    public final static String WS_GET_TRACKER_ASESOR        = "getTrackerAsesor"; //Servicio para para obtener la ratua del asesor
    public final static String WS_GET_SUCURSALES            = "getSucursales"; //Servicio para para obtener mis sucursales son sus asesores correspondientes
    public final static String WS_GET_PRESTAMOS_IND         = "prestamos/cliente/{id_cliente}"; //Servicio para obtener los prestamos individuales
    public final static String WS_GET_PRESTAMOS_GPO         = "prestamos/grupo/{id_grupo}"; //Servicio para obtener los prestamos grupales
    public final static String WS_MAILBOX                   = "Denuncia"; //Servicio para buzón de quejas o sugerencias
    public final static String WS_SYNCHRONIZEBD             = "UltimosRegistrosLog"; //Servicio para obtener los registros de impresiones y actualizar su BD local
    public final static String WS_GET_GEOLOCALIZACIONES     = "GetGeolocalizadas"; //Servicio para obtener las fichas de geolocalización
    public final static String WS_SAVE_GEO                  = "SaveGeolocalizacion"; //Guarda la informacipon capturada de GEOLOCALIZACION
    public final static String WS_POST_ORIGINACION_IND      = "individuales"; //Guarda la solicitud de credito individual (originacion)
    public final static String WS_POST_RESPUESTA_GESTION    = "gestiones"; //Guarda la respuesta gestion de recuperaciones

    //Catálogos
    public final static String WS_GET_ESTADOS               = "GetEstados"; //Obtiene el catálogo de estados de México
    public final static String WS_GET_MUNICIPIOS            = "GetMunicipios"; //Obtiene el catálogo de municipios
    public final static String WS_GET_COLONIAS              = "GetColonias"; //Obtiene el catálogo de colonias
    public final static String WS_GET_OCUPACIONES           = "GetOcupaciones"; //Obtiene el catálogo de las ocupaciones
    public final static String WS_GET_SECTORES              = "GetSectores"; //Obtiene el catálogo de sectores (actividades económicas)
    public final static String WS_GET_IDENTIFICACIONES      = "GetIdentificacionTipos"; //Obtiene el catálogo de tipos de identificacion (INE/IFE, Pasaporte...)


}
