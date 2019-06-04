package com.sidert.sidertmovil.utils;

public class WebServicesRoutes {

    //public final static String PORT = "81"; //Puerto de producción
    public final static String PORT = "82"; //Puerto de desarrollo

    public final static String DOMAIN = "http://sidert.zapto.org:"; // Dominio

    public final static String CONTROLLER = "/serviciosidert/Api.svc/"; //Controlador

    public final static String BASE_URL = DOMAIN + PORT + CONTROLLER;

    public final static String WS_MAILBOX = "Denuncia"; //Servicio para buzón de quejas o sugerencias

}
