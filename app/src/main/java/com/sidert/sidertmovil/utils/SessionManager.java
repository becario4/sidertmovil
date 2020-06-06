package com.sidert.sidertmovil.utils;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

import java.util.ArrayList;
import java.util.HashMap;

public class SessionManager {

    private final String APP_PREF       = "com.sidert.sidertmovil";
    private final String MAILBOX        = "mailbox";
    private final String DOMINIO        = "dominio";
    private final String PUERTO         = "puerto";
    private final String DATE           = "date";
    private final String ID_CARTERA     = "id_cartera";
    private final String SERIE_ID       = "serie_id";
    private final String NOMBRE         = "nombre";
    private final String AP_PATERNO     = "ap_paterno";
    private final String AP_MATERNO     = "ap_matenro";
    private final String USER_NAME      = "user_name";
    private final String TYPE_USER      = "type_user";
    private final String FLAG           = "flag";
    private final String COLONIA_P        = "colonia_p";
    private final String ASESOR_P         = "asesor_p";
    private final String INDIVIDUAL_P     = "individual_p";
    private final String GRUPAL_P         = "grupal_p";
    private final String CONTADOR_P       = "contador_p";
    private final String CLIENTE_GRUPO_P  = "cliente_grupo_p";
    private final String COLONIA_C        = "colonia_c";
    private final String ASESOR_C         = "asesor_c";
    private final String INDIVIDUAL_C     = "individual_c";
    private final String GRUPAL_C         = "grupal_c";
    private final String CONTADOR_C       = "contador_c";
    private final String CLIENTE_GRUPO_C  = "cliente_grupo_c";
    private final String ACCESS_TOKEN   = "access_token";
    //==================== FILTROS CARTERA ==========================================
    private final String TIPO_CARTERA_IND_P = "tipo_cartera_ind_p";
    private final String TIPO_CARTERA_GPO_P = "tipo_cartera_gpo_p";
    private final String TIPO_CARTERA_IND_R = "tipo_cartera_ind_r";
    private final String TIPO_CARTERA_GPO_R = "tipo_cartera_gpo_r";
    private final String NOMBRE_CARTERA_P   = "nombre_cartera_p";
    private final String NOMBRE_CARTERA_R   = "nombre_cartera_r";
    private final String DIA_SEMANA_P       = "dia_semana_p";
    private final String DIA_SEMANA_R       = "dia_semana_r";
    private final String COLONIA_CARTERA_P  = "colonia_cartera_p";
    private final String COLONIA_CARTERA_R  = "colonia_cartera_r";
    private final String ASESOR_CARTERA_P   = "asesor_cartera_p";
    private final String ASESOR_CARTERA_R   = "asesor_cartera_r";
    private final String CONTADOR_CARTERA_P = "contador_cartera_p";
    private final String CONTADOR_CARTERA_R = "contador_cartera_r";
    private final String NOMBRE_CARTERA_C   = "nombre_cartera_c";
    private final String TIPO_CARTERA_IND_C = "tipo_cartera_ind_c";
    private final String TIPO_CARTERA_GPO_C = "tipo_cartera_gpo_c";
    private final String ESTATUS_FICHA_ENV_C = "estatus_ficha_env_c";
    private final String ESTATUS_FICHA_PEN_C = "estatus_ficha_pen_c";
    private final String CONTADOR_CARTERA_C = "contador_cartera_c";
    private final String MODULOS            = "modulos";


    private final String NOMBRE_CIERRE          = "nombre_cierre";
    private final String ESTATUS_CONTE_CIERRE   = "estatus_conte_cierre";
    private final String ESTATUS_PENDI_CIERRE   = "estatus_pendi_cierre";
    private final String CONTADOR_CIERRE        = "contador_cierre";

    private SharedPreferences preferences;
    private SharedPreferences.Editor editor;
    private Context ctx;


    public SessionManager(Context ctx) {
        this.ctx = ctx;
    }

    // =================== Filtros de Geolocalizacion ==============================================
    public void setFiltrosGeoPend (HashMap<String, String> filtros){
        preferences = ctx.getSharedPreferences(APP_PREF, Context.MODE_PRIVATE);
        editor = preferences.edit();
        editor.putString(CLIENTE_GRUPO_P, filtros.get("nombre_p"));
        editor.putString(COLONIA_P, filtros.get("colonia_p"));
        editor.putString(ASESOR_P, filtros.get("asesor_p"));
        editor.putString(INDIVIDUAL_P, filtros.get("individual_p"));
        editor.putString(GRUPAL_P, filtros.get("grupal_p"));
        editor.putString(CONTADOR_P, filtros.get("contador_p"));
        editor.commit();
    }

    public ArrayList<String> getFiltrosGeoPend (){
        ArrayList<String> filtros = new ArrayList<>();
        preferences = ctx.getSharedPreferences(APP_PREF, Context.MODE_PRIVATE);
        filtros.add(preferences.getString(CLIENTE_GRUPO_P, "")); //0
        filtros.add(preferences.getString(COLONIA_P,""));        //1
        filtros.add(preferences.getString(ASESOR_P, ""));        //2
        filtros.add(preferences.getString(INDIVIDUAL_P, ""));    //3
        filtros.add(preferences.getString(GRUPAL_P, ""));        //4
        filtros.add(preferences.getString(CONTADOR_P, "0"));     //5
        return filtros;
    }

    public void setFiltrosGeoComp (HashMap<String, String> filtros){
        preferences = ctx.getSharedPreferences(APP_PREF, Context.MODE_PRIVATE);
        editor = preferences.edit();
        editor.putString(CLIENTE_GRUPO_C, filtros.get("nombre_c"));
        editor.putString(COLONIA_C, filtros.get("colonia_c"));
        editor.putString(ASESOR_C, filtros.get("asesor_c"));
        editor.putString(INDIVIDUAL_C, filtros.get("individual_c"));
        editor.putString(GRUPAL_C, filtros.get("grupal_c"));
        editor.putString(CONTADOR_C, filtros.get("contador_c"));
        editor.commit();
    }

    public ArrayList<String> getFiltrosGeoComp (){
        ArrayList<String> filtros = new ArrayList<>();
        preferences = ctx.getSharedPreferences(APP_PREF, Context.MODE_PRIVATE);
        filtros.add(preferences.getString(CLIENTE_GRUPO_C, ""));
        filtros.add(preferences.getString(COLONIA_C,""));
        filtros.add(preferences.getString(ASESOR_C, ""));
        filtros.add(preferences.getString(INDIVIDUAL_C, ""));
        filtros.add(preferences.getString(GRUPAL_C, ""));
        filtros.add(preferences.getString(CONTADOR_C, "0"));
        return filtros;
    }

    //==================== Dominio Dinamico ========================================================
    public void setDominio (String dominio, String puerto){
        preferences = ctx.getSharedPreferences(APP_PREF, Context.MODE_PRIVATE);
        editor = preferences.edit();
        editor.putString(DOMINIO, dominio);
        editor.putString(PUERTO, puerto);
        editor.commit();
    }

    public ArrayList<String> getDominio (){
        ArrayList<String> baseUrl = new ArrayList<>();
        preferences = ctx.getSharedPreferences(APP_PREF, Context.MODE_PRIVATE);
        baseUrl.add(preferences.getString(DOMINIO, "http://sidert.ddns.net:"));
        baseUrl.add(preferences.getString(PUERTO,"83"));

        return baseUrl;
    }

    //==================== Filtros de Cierre de Dia ================================================
    public void setFiltrosCierre (HashMap<String, String> filtros){
        preferences = ctx.getSharedPreferences(APP_PREF, Context.MODE_PRIVATE);
        editor = preferences.edit();
        editor.putString(ESTATUS_CONTE_CIERRE, filtros.get(ESTATUS_CONTE_CIERRE));
        editor.putString(ESTATUS_PENDI_CIERRE, filtros.get(ESTATUS_PENDI_CIERRE));
        editor.putString(NOMBRE_CIERRE, filtros.get(NOMBRE_CIERRE));
        editor.putString(CONTADOR_CIERRE, filtros.get(CONTADOR_CIERRE));
        editor.commit();
    }

    public ArrayList<String> getFiltrosCierre (){
        ArrayList<String> filtros = new ArrayList<>();
        preferences = ctx.getSharedPreferences(APP_PREF, Context.MODE_PRIVATE);
        filtros.add(preferences.getString(ESTATUS_CONTE_CIERRE, "0"));
        filtros.add(preferences.getString(ESTATUS_PENDI_CIERRE, "0"));
        filtros.add(preferences.getString(NOMBRE_CIERRE,""));
        filtros.add(preferences.getString(CONTADOR_CIERRE, "0"));
        return filtros;
    }

    //==================== Filtros de Cartera ======================================================
    public void setFiltrosCartera (HashMap<String, String> filtros){
        preferences = ctx.getSharedPreferences(APP_PREF, Context.MODE_PRIVATE);
        editor = preferences.edit();
        editor.putString(TIPO_CARTERA_IND_P, filtros.get(TIPO_CARTERA_IND_P));
        editor.putString(TIPO_CARTERA_GPO_P, filtros.get(TIPO_CARTERA_GPO_P));
        editor.putString(NOMBRE_CARTERA_P, filtros.get(NOMBRE_CARTERA_P));
        editor.putString(DIA_SEMANA_P, filtros.get(DIA_SEMANA_P));
        editor.putString(COLONIA_CARTERA_P, filtros.get(COLONIA_CARTERA_P));
        editor.putString(ASESOR_CARTERA_P, filtros.get(ASESOR_CARTERA_P));
        editor.putString(CONTADOR_CARTERA_P, filtros.get(CONTADOR_CARTERA_P));
        editor.commit();
    }

    public ArrayList<String> getFiltrosCartera (){
        ArrayList<String> filtros = new ArrayList<>();
        preferences = ctx.getSharedPreferences(APP_PREF, Context.MODE_PRIVATE);
        filtros.add(preferences.getString(TIPO_CARTERA_IND_P, ""));
        filtros.add(preferences.getString(TIPO_CARTERA_GPO_P, ""));
        filtros.add(preferences.getString(NOMBRE_CARTERA_P,""));
        filtros.add(preferences.getString(DIA_SEMANA_P, ""));
        filtros.add(preferences.getString(COLONIA_CARTERA_P, ""));
        filtros.add(preferences.getString(ASESOR_CARTERA_P, "0"));
        filtros.add(preferences.getString(CONTADOR_CARTERA_P, "0"));
        return filtros;
    }

    public void setFiltrosCarteraRuta (HashMap<String, String> filtros){
        preferences = ctx.getSharedPreferences(APP_PREF, Context.MODE_PRIVATE);
        editor = preferences.edit();
        editor.putString(TIPO_CARTERA_IND_R, filtros.get(TIPO_CARTERA_IND_R));
        editor.putString(TIPO_CARTERA_GPO_R, filtros.get(TIPO_CARTERA_GPO_R));
        editor.putString(NOMBRE_CARTERA_R, filtros.get(NOMBRE_CARTERA_R));
        editor.putString(DIA_SEMANA_R, filtros.get(DIA_SEMANA_R));
        editor.putString(COLONIA_CARTERA_R, filtros.get(COLONIA_CARTERA_R));
        editor.putString(ASESOR_CARTERA_R, filtros.get(ASESOR_CARTERA_R));
        editor.putString(CONTADOR_CARTERA_R, filtros.get(CONTADOR_CARTERA_R));
        editor.commit();
    }

    public ArrayList<String> getFiltrosCarteraRuta (){
        ArrayList<String> filtros = new ArrayList<>();
        preferences = ctx.getSharedPreferences(APP_PREF, Context.MODE_PRIVATE);
        filtros.add(preferences.getString(TIPO_CARTERA_IND_R, ""));
        filtros.add(preferences.getString(TIPO_CARTERA_GPO_R, ""));
        filtros.add(preferences.getString(NOMBRE_CARTERA_R,""));
        filtros.add(preferences.getString(DIA_SEMANA_R, ""));
        filtros.add(preferences.getString(COLONIA_CARTERA_R, ""));
        filtros.add(preferences.getString(ASESOR_CARTERA_R, "0"));
        filtros.add(preferences.getString(CONTADOR_CARTERA_R, "0"));
        return filtros;
    }

    public void setFiltrosCarteraContestadas (HashMap<String, String> filtros){
        preferences = ctx.getSharedPreferences(APP_PREF, Context.MODE_PRIVATE);
        editor = preferences.edit();
        editor.putString(TIPO_CARTERA_IND_C, filtros.get(TIPO_CARTERA_IND_C));
        editor.putString(TIPO_CARTERA_GPO_C, filtros.get(TIPO_CARTERA_GPO_C));
        editor.putString(ESTATUS_FICHA_ENV_C, filtros.get(ESTATUS_FICHA_ENV_C));
        editor.putString(ESTATUS_FICHA_PEN_C, filtros.get(ESTATUS_FICHA_PEN_C));
        editor.putString(NOMBRE_CARTERA_C, filtros.get(NOMBRE_CARTERA_C));
        editor.putString(CONTADOR_CARTERA_C, filtros.get(CONTADOR_CARTERA_C));
        editor.commit();
    }

    public ArrayList<String> getFiltrosCarteraContestadas (){
        ArrayList<String> filtros = new ArrayList<>();
        preferences = ctx.getSharedPreferences(APP_PREF, Context.MODE_PRIVATE);
        filtros.add(preferences.getString(TIPO_CARTERA_IND_C, ""));  // 0 -> IND
        filtros.add(preferences.getString(TIPO_CARTERA_GPO_C, ""));  // 1 -> GPO
        filtros.add(preferences.getString(ESTATUS_FICHA_ENV_C,""));  // 2 -> ENV
        filtros.add(preferences.getString(ESTATUS_FICHA_PEN_C, "")); // 3 -> PEN
        filtros.add(preferences.getString(NOMBRE_CARTERA_C, ""));    // 4 -> NOMBRE
        filtros.add(preferences.getString(CONTADOR_CARTERA_C, "0")); // 5 -> CONTADOR
        return filtros;
    }

    // ===================  Info User ==============================================================
    public void setUser (String serieID, String nombre, String apPaterno, String apMaterno, String userName, String type_user, boolean flag, String access_token, String modulos, String ID){
        preferences = ctx.getSharedPreferences(APP_PREF, Context.MODE_PRIVATE);
        editor = preferences.edit();
        editor.putString(SERIE_ID, serieID);           //0
        editor.putString(NOMBRE, nombre);              //1
        editor.putString(AP_PATERNO, apPaterno);       //2
        editor.putString(AP_MATERNO, apMaterno);       //3
        editor.putString(USER_NAME, userName);         //4
        editor.putString(TYPE_USER, type_user);        //5
        editor.putBoolean(FLAG, flag);                 //6
        editor.putString(ACCESS_TOKEN, access_token);  //7
        editor.putString(MODULOS, modulos);            //8
        editor.putString(ID_CARTERA, ID);            //9
        editor.commit();
    }

    public ArrayList<String> getUser (){
        ArrayList<String> user = new ArrayList<>();
        preferences = ctx.getSharedPreferences(APP_PREF, Context.MODE_PRIVATE);
        user.add(preferences.getString(SERIE_ID, null));                       //0
        user.add(preferences.getString(NOMBRE,null));                          //1
        user.add(preferences.getString(AP_PATERNO, null));                     //2
        user.add(preferences.getString(AP_MATERNO, null));                     //3
        user.add(preferences.getString(USER_NAME, null));                      //4
        user.add(preferences.getString(TYPE_USER, null));                      //5
        user.add(String.valueOf(preferences.getBoolean(FLAG, false)));         //6
        user.add(preferences.getString(ACCESS_TOKEN, null));                   //7
        user.add(preferences.getString(MODULOS, null));                        //8
        user.add(preferences.getString(ID_CARTERA, null));                     //9
        return user;
    }

    public void deleteUser (){
        preferences = ctx.getSharedPreferences(APP_PREF, Context.MODE_PRIVATE);
        editor = preferences.edit();
        editor.remove(SERIE_ID);
        editor.remove(NOMBRE);
        editor.remove(AP_PATERNO);
        editor.remove(AP_MATERNO);
        editor.remove(USER_NAME);
        editor.remove(TYPE_USER);
        editor.remove(FLAG);
        editor.remove(ACCESS_TOKEN);
        editor.remove(MODULOS);
        editor.remove(ID_CARTERA);
        editor.apply();
    }

    // ===================  Count MailBox ==========================================================
    public void setMailBox (String date, String count){
        preferences = ctx.getSharedPreferences(APP_PREF, Context.MODE_PRIVATE);
        editor = preferences.edit();
        editor.putString(DATE, date);
        editor.putString(MAILBOX, count);
        editor.commit();
    }

    public ArrayList<String> getMailBox (){
        ArrayList<String> launch = new ArrayList<>();
        preferences = ctx.getSharedPreferences(APP_PREF, Context.MODE_PRIVATE);
        launch.add(preferences.getString(DATE, ""));
        launch.add(preferences.getString(MAILBOX,"0"));
        return launch;
    }

    public void deleteMailBox (){
        preferences = ctx.getSharedPreferences(APP_PREF, Context.MODE_PRIVATE);
        editor = preferences.edit();
        editor.remove(MAILBOX);
        editor.remove(DATE);
        editor.apply();
    }

}
