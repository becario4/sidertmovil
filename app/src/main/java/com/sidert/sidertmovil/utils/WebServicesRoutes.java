package com.sidert.sidertmovil.utils;

public class WebServicesRoutes {

    //public final static String DOMAIN = "http://sidert.ddns.net:"; // Dominio
    public final static String DOMAIN = "http://172.24.16.48:"; // Dominio local

    //public final static String PORT = "81"; //Puerto de producción
    //public final static String PORT = "82"; //Puerto de desarrollo
    public final static String PORT = "8080"; //Puerto de local

    //public final static String CONTROLLER = "/serviciosidert/Api.svc/"; //Controlador
    public final static String CONTROLLER = "/oauth/"; //Controlador local

    public final static String BASE_URL = DOMAIN + PORT + CONTROLLER;

    //Servicios
    public final static String WS_LOGIN                     = "token"; //Servicio para inicio de sesion
    public final static String WS_MAILBOX                   = "Denuncia"; //Servicio para buzón de quejas o sugerencias
    public final static String WS_SYNCHRONIZEBD             = "UltimosRegistrosLog"; //Servicio para obtener los registros de impresiones y actualizar su BD local
    public final static String WS_GET_GEOLOCALIZACIONES     = "GetGeolocalizaciones"; //Servicio para inicio de sesion
    public final static String WS_SAVE_GEO                  = "UpdateGeolocalizacion"; //Guarda la informacipon capturada de GEOLOCALIZACION

}
