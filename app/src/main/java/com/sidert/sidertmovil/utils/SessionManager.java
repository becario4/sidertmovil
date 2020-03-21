package com.sidert.sidertmovil.utils;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

import java.util.ArrayList;
import java.util.HashMap;

public class SessionManager {

    private final String APP_PREF       = "com.sidert.sidertmovil";
    private final String MAILBOX        = "mailbox";
    private final String DATE           = "date";
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
        filtros.add(preferences.getString(CLIENTE_GRUPO_P, ""));
        filtros.add(preferences.getString(COLONIA_P,""));
        filtros.add(preferences.getString(ASESOR_P, ""));
        filtros.add(preferences.getString(INDIVIDUAL_P, ""));
        filtros.add(preferences.getString(GRUPAL_P, ""));
        filtros.add(preferences.getString(CONTADOR_P, "0"));
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

    // ===================  Info User ==============================================================
    public void setUser (String serieID, String nombre, String apPaterno, String apMaterno, String userName, String type_user, boolean flag, String access_token){
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
        return user;
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
