package com.sidert.sidertmovil.utils;

public class WebServicesRoutes {

    //public final static String DOMAIN = "http://sidert.ddns.net:"; // Dominio
    public final static String DOMAIN = "http://172.24.16.48:"; // Dominio producción con VPN
    //public final static String DOMAIN = "http://192.168.100.24:"; // Dominio local IP Francisco

    //public final static String PORT = "81"; //Puerto de producción
    //public final static String PORT = "82"; //Puerto de desarrollo
    //public final static String PORT = "83";
    public final static String PORT = "8080"; //Puerto de local

    //public final static String CONTROLLER = "/serviciosidert/Api.svc/"; //Controlador
    public final static String CONTROLLER_LOGIN = "/oauth/"; //Controlador login
    public final static String CONTROLLER_FICHAS = "/api/fichas/"; //Controlador para fichas
    public final static String CONTROLLER_CATALOGOS = "/api/catalogos/"; //Controlador para catalogos
    public final static String IMAGES_GEOLOCALIZACION = "uploads/img/";

    public final static String BASE_URL = DOMAIN + PORT;

    //Servicios
    public final static String WS_LOGIN                     = "token"; //Servicio para inicio de sesion
    public final static String WS_MAILBOX                   = "Denuncia"; //Servicio para buzón de quejas o sugerencias
    public final static String WS_SYNCHRONIZEBD             = "UltimosRegistrosLog"; //Servicio para obtener los registros de impresiones y actualizar su BD local
    public final static String WS_GET_GEOLOCALIZACIONES     = "GetGeolocalizaciones"; //Servicio para inicio de sesion
    public final static String WS_SAVE_GEO                  = "UpdateGeolocalizacion"; //Guarda la informacipon capturada de GEOLOCALIZACION

    //Catálogos
    public final static String WS_GET_ESTADOS               = "GetEstados"; //Obtiene el catálogo de estados de México
    public final static String WS_GET_MUNICIPIOS            = "GetMunicipios"; //Obtiene el catálogo de municipios
    public final static String WS_GET_COLONIAS              = "GetColonias"; //Obtiene el catálogo de colonias
    public final static String WS_GET_OCUPACIONES           = "GetOcupaciones"; //Obtiene el catálogo de las ocupaciones
    public final static String WS_GET_SECTORES              = "GetSectores"; //Obtiene el catálogo de sectores (actividades económicas)

}
