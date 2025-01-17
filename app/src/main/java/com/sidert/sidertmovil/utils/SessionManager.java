package com.sidert.sidertmovil.utils;

import static com.sidert.sidertmovil.utils.Constants.AUTHORITIES;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.concurrent.atomic.AtomicReference;

public class SessionManager extends JSONObject {

    private final String APP_PREF = "com.sidert.sidertmovil";
    private final String MAILBOX = "mailbox";
    private final String DOMINIO = "dominio";
    private final String PUERTO = "puerto";
    private final String DATE = "date";
    private final String ID_CARTERA = "id_cartera";
    private final String SERIE_ID = "serie_id";
    private final String NOMBRE = "nombre";
    private final String AP_PATERNO = "ap_paterno";
    private final String AP_MATERNO = "ap_matenro";
    private final String USER_NAME = "user_name";
    private final String TYPE_USER = "type_user";
    private final String FLAG = "flag";
    private final String COLONIA_P = "colonia_p";
    private final String ASESOR_P = "asesor_p";
    private final String INDIVIDUAL_P = "individual_p";
    private final String GRUPAL_P = "grupal_p";
    private final String CONTADOR_P = "contador_p";
    private final String CLIENTE_GRUPO_P = "cliente_grupo_p";
    private final String COLONIA_C = "colonia_c";
    private final String ASESOR_C = "asesor_c";
    private final String INDIVIDUAL_C = "individual_c";
    private final String GRUPAL_C = "grupal_c";
    private final String CONTADOR_C = "contador_c";
    private final String CLIENTE_GRUPO_C = "cliente_grupo_c";
    private final String ACCESS_TOKEN = "access_token";
    //==================== SUCURSALES ===============================================
    private final String SUCURSALES = "sucursales";
    //==================== FILTROS CARTERA ==========================================
    private final String TIPO_CARTERA_IND_P = "tipo_cartera_ind_p";
    private final String TIPO_CARTERA_GPO_P = "tipo_cartera_gpo_p";
    private final String TIPO_CARTERA_IND_R = "tipo_cartera_ind_r";
    private final String TIPO_CARTERA_GPO_R = "tipo_cartera_gpo_r";
    private final String NOMBRE_CARTERA_P = "nombre_cartera_p";
    private final String NOMBRE_CARTERA_R = "nombre_cartera_r";
    private final String DIA_SEMANA_P = "dia_semana_p";
    private final String DIA_SEMANA_R = "dia_semana_r";
    private final String COLONIA_CARTERA_P = "colonia_cartera_p";
    private final String COLONIA_CARTERA_R = "colonia_cartera_r";
    private final String ASESOR_CARTERA_P = "asesor_cartera_p";
    private final String ASESOR_CARTERA_R = "asesor_cartera_r";
    private final String CONTADOR_CARTERA_P = "contador_cartera_p";
    private final String PARCIAL_CARTERA_P = "parcial_cartera_p";
    private final String CONTADOR_CARTERA_R = "contador_cartera_r";
    private final String PARCIAL_CARTERA_R = "parcial_cartera_r";
    private final String NOMBRE_CARTERA_C = "nombre_cartera_c";
    private final String TIPO_CARTERA_IND_C = "tipo_cartera_ind_c";
    private final String TIPO_CARTERA_GPO_C = "tipo_cartera_gpo_c";
    private final String ESTATUS_FICHA_ENV_C = "estatus_ficha_env_c";
    private final String ESTATUS_FICHA_PEN_C = "estatus_ficha_pen_c";
    private final String CONTADOR_CARTERA_C = "contador_cartera_c";
    private final String MODULOS = "modulos";
    private final String TIPO_REN_IND = "tipo_renovacion_ind";
    private final String TIPO_REN_GPO = "tipo_renovacion_gpo";
    private final String NOMBRE_REN = "nombre_renovacion";
    private final String MENOR_45_REN = "menor45_renovacion";
    private final String CONTADOR_REN = "contador_renovacion";
    private final String TIPO_REN_IND_PRO = "tipo_renovacion_ind_pro";
    private final String TIPO_REN_GPO_PRO = "tipo_renovacion_gpo_pro";
    private final String NOMBRE_REN_PRO = "nombre_renovacion_pro";
    private final String MENOR_45_REN_PRO = "menor45_renovacion_pro";
    private final String CONTADOR_REN_PRO = "contador_renovacion_pro";
    private final String TIPO_REN_IND_COMP = "tipo_renovacion_ind_comp";
    private final String TIPO_REN_GPO_COMP = "tipo_renovacion_gpo_comp";
    private final String NOMBRE_REN_COMP = "nombre_renovacion_comp";
    private final String MENOR_45_REN_COMP = "menor45_renovacion_comp";
    private final String CONTADOR_REN_COMP = "contador_renovacion_comp";
    private final String TIPO_ORI_IND_PRO = "tipo_originacion_ind_pro";
    private final String TIPO_ORI_GPO_PRO = "tipo_originacion_gpo_pro";
    private final String NOMBRE_ORI_PRO = "nombre_originacion_pro";
    private final String MENOR_45_ORI_PRO = "menor45_originacion_pro";
    private final String CONTADOR_ORI_PRO = "contador_originacion_pro";
    private final String TIPO_ORI_IND_COMP = "tipo_originacion_ind_comp";
    private final String TIPO_ORI_GPO_COMP = "tipo_originacion_gpo_comp";
    private final String NOMBRE_ORI_COMP = "nombre_originacion_comp";
    private final String MENOR_45_ORI_COMP = "menor45_originacion_comp";
    private final String CONTADOR_ORI_COMP = "contador_originacion_comp";


    private final String NOMBRE_CIERRE = "nombre_cierre";
    private final String ESTATUS_CONTE_CIERRE = "estatus_conte_cierre";
    private final String ESTATUS_PENDI_CIERRE = "estatus_pendi_cierre";
    private final String CONTADOR_CIERRE = "contador_cierre";

    private final String MAC_ADDRESS = "mac_address";

    private final String NOMBRE_CC_AGF = "nombre_cc_agf";
    private final String TIPO_PRESTAMO_GPO_CC_AGF = "tipo_prestamo_gpo_cc_agf";
    private final String TIPO_PRESTAMO_IND_CC_AGF = "tipo_prestamo_ind_cc_agf";
    private final String CONTADOR_CC_AGF = "contador_cc_agf";

    // Recibos de AGF y CC
    private final String NOMBRE_RECIBO_CC_AGF = "nombre_recibo_cc_agf";
    private final String FOLIO_RECIBO_CC_AGF = "folio_recibo_cc_agf";
    private final String RECIBO_AGF = "recibo_agf";
    private final String RECIBO_CC = "recibo_cc";
    private final String RECIBO_ENVIDA_CC_AGF = "recibo_enviada_cc_agf";
    private final String RECIBO_PENDIENTE_CC_AGF = "recibo_pendiente_cc_agf";
    private final String CONTADOR_RECIBO_CC_AGF = "contador_recibo_cc_agf";

    // Gestiones de AGF y CC
    private final String NOMBRE_GESTION_CC_AGF = "nombre_gestion_cc_agf";
    private final String GESTION_AGF = "gestion_agf";
    private final String GESTION_CC = "gestion_cc";
    private final String GESTION_ENVIDA_CC_AGF = "gestion_enviada_cc_agf";
    private final String GESTION_PENDIENTE_CC_AGF = "gestion_pendiente_cc_agf";
    private final String CONTADOR_GESTION_CC_AGF = "gestion_recibo_cc_agf";

    //FILTROS VERIFICACION DOMICILIARIA
    private final String VER_DOM_CLIENTE_NOMBRE = "ver_dom_cliente_nombre";
    private final String VER_DOM_GRUPO_NOMBRE = "ver_dom_grupo_nombre";
    private final String VER_DOM_FLAG_IND = "ver_dom_flag_ind";
    private final String VER_DOM_FLAG_GRU = "ver_dom_flag_gru";
    private final String CONTADOR_VER_DIS = "contador_ver_dis";

    //FILTROS GESTIONES VERIFICACION DOMICILIARIA
    private final String GES_VER_DOM_CLIENTE_NOMBRE = "ges_ver_dom_cliente_nombre";
    private final String GES_VER_DOM_GRUPO_NOMBRE = "ges_ver_dom_grupo_nombre";
    private final String GES_VER_DOM_FLAG_IND = "ges_ver_dom_flag_ind";
    private final String GES_VER_DOM_FLAG_GRU = "ges_ver_dom_flag_gru";
    private final String GES_CONTADOR_VER_DIS = "ges_contador_ver_dis";

    //PERMISOS
    private final String IMAGE_FORM_GALLERY = "image_from_gallery";

    private final String LAT = "latitude";

    private final String LOG = "longitud";
    private SharedPreferences preferences;
    private SharedPreferences.Editor editor;
    private final Context ctx;

    private static volatile SessionManager instance;

    public static SessionManager getInstance(Context ctx) {
        if (instance == null) {
            synchronized (SessionManager.class) {
                if (instance == null) {
                    instance = new SessionManager(ctx);
                }
            }
        }
        return instance;
    }


    private SessionManager(Context ctx) {
        this.ctx = ctx;
    }

    // =================== Filtros de Geolocalizacion ==============================================
    public void setSucursales(String sucursales) {
        preferences = ctx.getSharedPreferences(APP_PREF, Context.MODE_PRIVATE);
        editor = preferences.edit();
        editor.putString(SUCURSALES, sucursales);
        editor.commit();
    }

    public void setCoordenadas(String latitud, String longitud) {
        preferences = ctx.getSharedPreferences(APP_PREF, Context.MODE_PRIVATE);
        editor = preferences.edit();
        editor.putString(LAT, latitud);
        editor.putString(LOG, longitud);
        editor.commit();
    }

    public String getLatitud() {
        preferences = ctx.getSharedPreferences(APP_PREF, Context.MODE_PRIVATE);
        return preferences.getString(LAT, "0.0");
    }

    public String getLongitud() {
        preferences = ctx.getSharedPreferences(APP_PREF, Context.MODE_PRIVATE);
        return preferences.getString(LOG, "0.0");

    }

    public JSONArray getSucursales() {
        JSONArray sucursales = null;
        try {
            preferences = ctx.getSharedPreferences(APP_PREF, Context.MODE_PRIVATE);
            sucursales = new JSONArray(preferences.getString(SUCURSALES, "[]"));
        } catch (JSONException e) {
            e.printStackTrace();
            sucursales = new JSONArray();
        }

        return sucursales;
    }

    public void setAutorizacionAA(String autorizacion) {
        preferences = ctx.getSharedPreferences(APP_PREF, Context.MODE_PRIVATE);
        editor = preferences.edit();
        editor.putString(AUTHORITIES, autorizacion);
        editor.commit();
    }

    public JSONArray getAutorizacionAAA() {
        JSONArray autorizacion = null;
        try {
            preferences = ctx.getSharedPreferences(APP_PREF, Context.MODE_PRIVATE);
            autorizacion = new JSONArray(preferences.getString(AUTHORITIES, "[]"));
        } catch (JSONException e) {
            e.printStackTrace();
            autorizacion = new JSONArray();
        }

        return autorizacion;
    }

    public ArrayList<String> getAutorizacionList() {
        ArrayList<String> autorizacion = new ArrayList<>();
        preferences = ctx.getSharedPreferences(APP_PREF, Context.MODE_PRIVATE);
        autorizacion.add(preferences.getString(AUTHORITIES, ""));
        return autorizacion;
    }

    public void setSerieId(String serieId) {
        preferences = ctx.getSharedPreferences(APP_PREF, Context.MODE_PRIVATE);
        editor = preferences.edit();
        editor.putString(SERIE_ID, serieId);
        editor.commit();
    }

    public JSONArray getSerieId() {
        JSONArray serieId = null;
        try {
            preferences = ctx.getSharedPreferences(APP_PREF, Context.MODE_PRIVATE);
            serieId = new JSONArray(preferences.getString(SERIE_ID, null));
        } catch (JSONException e) {
            e.printStackTrace();
            serieId = new JSONArray();
        }
        return serieId;
    }


    // =================== Filtros de Geolocalizacion ==============================================
    public void setFiltrosGeoPend(HashMap<String, String> filtros) {
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

    public ArrayList<String> getFiltrosGeoPend() {
        ArrayList<String> filtros = new ArrayList<>();
        preferences = ctx.getSharedPreferences(APP_PREF, Context.MODE_PRIVATE);
        filtros.add(preferences.getString(CLIENTE_GRUPO_P, "")); //0
        filtros.add(preferences.getString(COLONIA_P, ""));        //1
        filtros.add(preferences.getString(ASESOR_P, ""));        //2
        filtros.add(preferences.getString(INDIVIDUAL_P, ""));    //3
        filtros.add(preferences.getString(GRUPAL_P, ""));        //4
        filtros.add(preferences.getString(CONTADOR_P, "0"));     //5
        return filtros;
    }

    public void setFiltrosGeoComp(HashMap<String, String> filtros) {
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

    public ArrayList<String> getFiltrosGeoComp() {
        ArrayList<String> filtros = new ArrayList<>();
        preferences = ctx.getSharedPreferences(APP_PREF, Context.MODE_PRIVATE);
        filtros.add(preferences.getString(CLIENTE_GRUPO_C, ""));
        filtros.add(preferences.getString(COLONIA_C, ""));
        filtros.add(preferences.getString(ASESOR_C, ""));
        filtros.add(preferences.getString(INDIVIDUAL_C, ""));
        filtros.add(preferences.getString(GRUPAL_C, ""));
        filtros.add(preferences.getString(CONTADOR_C, "0"));
        return filtros;
    }

    //==================== Dominio Dinamico ========================================================
    public void setDominio(String dominio) {
        preferences = ctx.getSharedPreferences(APP_PREF, Context.MODE_PRIVATE);
        editor = preferences.edit();
        editor.putString(DOMINIO, dominio);
        editor.commit();
    }

    public String getDominio() {
        preferences = ctx.getSharedPreferences(APP_PREF, Context.MODE_PRIVATE);
        String domain = preferences.getString(DOMINIO, "http://sidert.ddns.net:8084");
        return domain.substring(0, domain.length() - 1);
    }

    //==================== Mac Address ========================================================
    public void setAddress(String macAddress) {
        preferences = ctx.getSharedPreferences(APP_PREF, Context.MODE_PRIVATE);
        editor = preferences.edit();
        editor.putString(MAC_ADDRESS, macAddress);
        editor.commit();
    }

    public String getMacAddress() {
        String macAddress;
        preferences = ctx.getSharedPreferences(APP_PREF, Context.MODE_PRIVATE);
        macAddress = preferences.getString(MAC_ADDRESS, "");
        return macAddress;
    }

    //==================== Filtros de Verificacion Domiciliaria ================================================
    public void setFiltrosVerDom(HashMap<String, String> filtros) {
        preferences = ctx.getSharedPreferences(APP_PREF, Context.MODE_PRIVATE);
        editor = preferences.edit();
        editor.putString(VER_DOM_CLIENTE_NOMBRE, filtros.get(VER_DOM_CLIENTE_NOMBRE));
        editor.putString(VER_DOM_GRUPO_NOMBRE, filtros.get(VER_DOM_GRUPO_NOMBRE));
        editor.putString(VER_DOM_FLAG_IND, filtros.get(VER_DOM_FLAG_IND));
        editor.putString(VER_DOM_FLAG_GRU, filtros.get(VER_DOM_FLAG_GRU));
        editor.putString(CONTADOR_VER_DIS, filtros.get(CONTADOR_VER_DIS));
        editor.commit();
    }

    public ArrayList<String> getFiltrosVerDom() {
        ArrayList<String> filtros = new ArrayList<>();
        preferences = ctx.getSharedPreferences(APP_PREF, Context.MODE_PRIVATE);
        filtros.add(preferences.getString(VER_DOM_CLIENTE_NOMBRE, ""));
        filtros.add(preferences.getString(VER_DOM_GRUPO_NOMBRE, ""));
        filtros.add(preferences.getString(VER_DOM_FLAG_IND, "0"));
        filtros.add(preferences.getString(VER_DOM_FLAG_GRU, "0"));
        filtros.add(preferences.getString(CONTADOR_VER_DIS, "0"));
        return filtros;
    }

    //==================== Filtros de Gestiones Verificacion Domiciliaria ================================================
    public void setFiltrosGesVerDom(HashMap<String, String> filtros) {
        preferences = ctx.getSharedPreferences(APP_PREF, Context.MODE_PRIVATE);
        editor = preferences.edit();
        editor.putString(GES_VER_DOM_CLIENTE_NOMBRE, filtros.get(GES_VER_DOM_CLIENTE_NOMBRE));
        editor.putString(GES_VER_DOM_GRUPO_NOMBRE, filtros.get(GES_VER_DOM_GRUPO_NOMBRE));
        editor.putString(GES_VER_DOM_FLAG_IND, filtros.get(GES_VER_DOM_FLAG_IND));
        editor.putString(GES_VER_DOM_FLAG_GRU, filtros.get(GES_VER_DOM_FLAG_GRU));
        editor.putString(GES_CONTADOR_VER_DIS, filtros.get(GES_CONTADOR_VER_DIS));
        editor.commit();
    }

    public ArrayList<String> getFiltrosGesVerDom() {
        ArrayList<String> filtros = new ArrayList<>();
        preferences = ctx.getSharedPreferences(APP_PREF, Context.MODE_PRIVATE);
        filtros.add(preferences.getString(GES_VER_DOM_CLIENTE_NOMBRE, ""));
        filtros.add(preferences.getString(GES_VER_DOM_GRUPO_NOMBRE, ""));
        filtros.add(preferences.getString(GES_VER_DOM_FLAG_IND, "0"));
        filtros.add(preferences.getString(GES_VER_DOM_FLAG_GRU, "0"));
        filtros.add(preferences.getString(GES_CONTADOR_VER_DIS, "0"));
        return filtros;
    }

    //==================== Filtros de Cierre de Dia ================================================
    public void setFiltrosCierre(HashMap<String, String> filtros) {
        preferences = ctx.getSharedPreferences(APP_PREF, Context.MODE_PRIVATE);
        editor = preferences.edit();
        editor.putString(ESTATUS_CONTE_CIERRE, filtros.get(ESTATUS_CONTE_CIERRE));
        editor.putString(ESTATUS_PENDI_CIERRE, filtros.get(ESTATUS_PENDI_CIERRE));
        editor.putString(NOMBRE_CIERRE, filtros.get(NOMBRE_CIERRE));
        editor.putString(CONTADOR_CIERRE, filtros.get(CONTADOR_CIERRE));
        editor.commit();
    }

    public ArrayList<String> getFiltrosCierre() {
        ArrayList<String> filtros = new ArrayList<>();
        preferences = ctx.getSharedPreferences(APP_PREF, Context.MODE_PRIVATE);
        filtros.add(preferences.getString(ESTATUS_CONTE_CIERRE, "0"));
        filtros.add(preferences.getString(ESTATUS_PENDI_CIERRE, "0"));
        filtros.add(preferences.getString(NOMBRE_CIERRE, ""));
        filtros.add(preferences.getString(CONTADOR_CIERRE, "0"));
        return filtros;
    }

    //===================== Filtros de CC y AGF  ===================================================

    public void setFiltrosCCAGF(HashMap<String, String> filtros) {
        preferences = ctx.getSharedPreferences(APP_PREF, Context.MODE_PRIVATE);
        editor = preferences.edit();
        editor.putString(NOMBRE_CC_AGF, filtros.get(NOMBRE_CC_AGF));
        editor.putString(TIPO_PRESTAMO_GPO_CC_AGF, filtros.get(TIPO_PRESTAMO_GPO_CC_AGF));
        editor.putString(TIPO_PRESTAMO_IND_CC_AGF, filtros.get(TIPO_PRESTAMO_IND_CC_AGF));
        editor.putString(CONTADOR_CC_AGF, filtros.get(CONTADOR_CC_AGF));
        editor.commit();
    }

    public ArrayList<String> getFiltrosCCAGF() {
        ArrayList<String> filtros = new ArrayList<>();
        preferences = ctx.getSharedPreferences(APP_PREF, Context.MODE_PRIVATE);
        filtros.add(preferences.getString(NOMBRE_CC_AGF, ""));
        filtros.add(preferences.getString(TIPO_PRESTAMO_GPO_CC_AGF, "0"));
        filtros.add(preferences.getString(TIPO_PRESTAMO_IND_CC_AGF, "0"));
        filtros.add(preferences.getString(CONTADOR_CC_AGF, "0"));
        return filtros;
    }

    //===================== Filtros de Recibos de CC y AGF  ===================================================

    public void setFiltrosRecibosCCAGF(HashMap<String, String> filtros) {
        preferences = ctx.getSharedPreferences(APP_PREF, Context.MODE_PRIVATE);
        editor = preferences.edit();
        editor.putString(NOMBRE_RECIBO_CC_AGF, filtros.get(NOMBRE_RECIBO_CC_AGF));
        editor.putString(FOLIO_RECIBO_CC_AGF, filtros.get(FOLIO_RECIBO_CC_AGF));
        editor.putString(RECIBO_AGF, filtros.get(RECIBO_AGF));
        editor.putString(RECIBO_CC, filtros.get(RECIBO_CC));
        editor.putString(RECIBO_ENVIDA_CC_AGF, filtros.get(RECIBO_ENVIDA_CC_AGF));
        editor.putString(RECIBO_PENDIENTE_CC_AGF, filtros.get(RECIBO_PENDIENTE_CC_AGF));
        editor.putString(CONTADOR_RECIBO_CC_AGF, filtros.get(CONTADOR_RECIBO_CC_AGF));
        editor.commit();
    }

    public ArrayList<String> getFiltrosRecibosCCAGF() {
        ArrayList<String> filtros = new ArrayList<>();
        preferences = ctx.getSharedPreferences(APP_PREF, Context.MODE_PRIVATE);
        filtros.add(preferences.getString(NOMBRE_RECIBO_CC_AGF, ""));       //0 nombre
        filtros.add(preferences.getString(FOLIO_RECIBO_CC_AGF, ""));        //1 folio
        filtros.add(preferences.getString(RECIBO_AGF, "0"));                //2 agf
        filtros.add(preferences.getString(RECIBO_CC, "0"));                  //3 cc
        filtros.add(preferences.getString(RECIBO_ENVIDA_CC_AGF, "0"));      //4 enviadas
        filtros.add(preferences.getString(RECIBO_PENDIENTE_CC_AGF, "0"));    //5 pendientes
        filtros.add(preferences.getString(CONTADOR_RECIBO_CC_AGF, "0"));    //6 contador
        return filtros;
    }

    //===================== Filtros de Gestiones de CC y AGF  ===================================================

    public void setFiltrosGestionesCCAGF(HashMap<String, String> filtros) {
        preferences = ctx.getSharedPreferences(APP_PREF, Context.MODE_PRIVATE);
        editor = preferences.edit();
        editor.putString(NOMBRE_GESTION_CC_AGF, filtros.get(NOMBRE_GESTION_CC_AGF));
        editor.putString(GESTION_AGF, filtros.get(GESTION_AGF));
        editor.putString(GESTION_CC, filtros.get(GESTION_CC));
        editor.putString(GESTION_ENVIDA_CC_AGF, filtros.get(GESTION_ENVIDA_CC_AGF));
        editor.putString(GESTION_PENDIENTE_CC_AGF, filtros.get(GESTION_PENDIENTE_CC_AGF));
        editor.putString(CONTADOR_GESTION_CC_AGF, filtros.get(CONTADOR_GESTION_CC_AGF));
        editor.commit();
    }

    public ArrayList<String> getFiltrosGestionCCAGF() {
        ArrayList<String> filtros = new ArrayList<>();
        preferences = ctx.getSharedPreferences(APP_PREF, Context.MODE_PRIVATE);
        filtros.add(preferences.getString(NOMBRE_GESTION_CC_AGF, ""));      //0 nombre
        filtros.add(preferences.getString(GESTION_AGF, "0"));               //1 agf
        filtros.add(preferences.getString(GESTION_CC, "0"));                 //2 cc
        filtros.add(preferences.getString(GESTION_ENVIDA_CC_AGF, "0"));     //3 enviada
        filtros.add(preferences.getString(GESTION_PENDIENTE_CC_AGF, "0"));   //4 pendiente
        filtros.add(preferences.getString(CONTADOR_GESTION_CC_AGF, "0"));
        return filtros;
    }

    //==================== Filtros de Cartera ======================================================
    public void setFiltrosCartera(HashMap<String, String> filtros) {
        preferences = ctx.getSharedPreferences(APP_PREF, Context.MODE_PRIVATE);
        editor = preferences.edit();
        editor.putString(TIPO_CARTERA_IND_P, filtros.get(TIPO_CARTERA_IND_P));
        editor.putString(TIPO_CARTERA_GPO_P, filtros.get(TIPO_CARTERA_GPO_P));
        editor.putString(NOMBRE_CARTERA_P, filtros.get(NOMBRE_CARTERA_P));
        editor.putString(DIA_SEMANA_P, filtros.get(DIA_SEMANA_P));
        editor.putString(COLONIA_CARTERA_P, filtros.get(COLONIA_CARTERA_P));
        editor.putString(ASESOR_CARTERA_P, filtros.get(ASESOR_CARTERA_P));
        editor.putString(CONTADOR_CARTERA_P, filtros.get(CONTADOR_CARTERA_P));
        editor.putString(PARCIAL_CARTERA_P, filtros.get(PARCIAL_CARTERA_P));
        editor.commit();
    }

    public ArrayList<String> getFiltrosCartera() {
        ArrayList<String> filtros = new ArrayList<>();
        preferences = ctx.getSharedPreferences(APP_PREF, Context.MODE_PRIVATE);
        filtros.add(preferences.getString(TIPO_CARTERA_IND_P, ""));
        filtros.add(preferences.getString(TIPO_CARTERA_GPO_P, ""));
        filtros.add(preferences.getString(NOMBRE_CARTERA_P, ""));
        filtros.add(preferences.getString(DIA_SEMANA_P, ""));
        filtros.add(preferences.getString(COLONIA_CARTERA_P, ""));
        filtros.add(preferences.getString(ASESOR_CARTERA_P, "0"));
        filtros.add(preferences.getString(CONTADOR_CARTERA_P, "0"));
        filtros.add(preferences.getString(PARCIAL_CARTERA_P, "0"));
        return filtros;
    }

    public void setFiltrosCarteraRuta(HashMap<String, String> filtros) {
        preferences = ctx.getSharedPreferences(APP_PREF, Context.MODE_PRIVATE);
        editor = preferences.edit();
        editor.putString(TIPO_CARTERA_IND_R, filtros.get(TIPO_CARTERA_IND_R));
        editor.putString(TIPO_CARTERA_GPO_R, filtros.get(TIPO_CARTERA_GPO_R));
        editor.putString(NOMBRE_CARTERA_R, filtros.get(NOMBRE_CARTERA_R));
        editor.putString(DIA_SEMANA_R, filtros.get(DIA_SEMANA_R));
        editor.putString(COLONIA_CARTERA_R, filtros.get(COLONIA_CARTERA_R));
        editor.putString(ASESOR_CARTERA_R, filtros.get(ASESOR_CARTERA_R));
        editor.putString(CONTADOR_CARTERA_R, filtros.get(CONTADOR_CARTERA_R));
        editor.putString(PARCIAL_CARTERA_R, filtros.get(PARCIAL_CARTERA_R));
        editor.commit();
    }

    public ArrayList<String> getFiltrosCarteraRuta() {
        ArrayList<String> filtros = new ArrayList<>();
        preferences = ctx.getSharedPreferences(APP_PREF, Context.MODE_PRIVATE);
        filtros.add(preferences.getString(TIPO_CARTERA_IND_R, ""));
        filtros.add(preferences.getString(TIPO_CARTERA_GPO_R, ""));
        filtros.add(preferences.getString(NOMBRE_CARTERA_R, ""));
        filtros.add(preferences.getString(DIA_SEMANA_R, ""));
        filtros.add(preferences.getString(COLONIA_CARTERA_R, ""));
        filtros.add(preferences.getString(ASESOR_CARTERA_R, "0"));
        filtros.add(preferences.getString(CONTADOR_CARTERA_R, "0"));
        filtros.add(preferences.getString(PARCIAL_CARTERA_R, "0"));
        return filtros;
    }

    public void setFiltrosCarteraContestadas(HashMap<String, String> filtros) {
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

    public ArrayList<String> getFiltrosCarteraContestadas() {
        ArrayList<String> filtros = new ArrayList<>();
        preferences = ctx.getSharedPreferences(APP_PREF, Context.MODE_PRIVATE);
        filtros.add(preferences.getString(TIPO_CARTERA_IND_C, ""));  // 0 -> IND
        filtros.add(preferences.getString(TIPO_CARTERA_GPO_C, ""));  // 1 -> GPO
        filtros.add(preferences.getString(ESTATUS_FICHA_ENV_C, ""));  // 2 -> ENV
        filtros.add(preferences.getString(ESTATUS_FICHA_PEN_C, "")); // 3 -> PEN
        filtros.add(preferences.getString(NOMBRE_CARTERA_C, ""));    // 4 -> NOMBRE
        filtros.add(preferences.getString(CONTADOR_CARTERA_C, "0")); // 5 -> CONTADOR
        return filtros;
    }

    // ===================  Filtros de Renovaciones ==============================================================
    public ArrayList<String> getFiltrosDisponiblesRen() {
        ArrayList<String> filtros = new ArrayList<>();
        preferences = ctx.getSharedPreferences(APP_PREF, Context.MODE_PRIVATE);
        filtros.add(preferences.getString(TIPO_REN_IND, "0"));  // 0 -> IND
        filtros.add(preferences.getString(TIPO_REN_GPO, "0"));  // 1 -> GPO
        filtros.add(preferences.getString(NOMBRE_REN, ""));  // 2 -> NOMBRE
        filtros.add(preferences.getString(MENOR_45_REN, "0")); // 3 -> MENOR 45 DIA VENCIMIENTO
        filtros.add(preferences.getString(CONTADOR_REN, "0")); // 4 -> CONTADOR
        return filtros;
    }

    public void setFiltrosDisponiblesRen(HashMap<String, String> filtros) {
        preferences = ctx.getSharedPreferences(APP_PREF, Context.MODE_PRIVATE);
        editor = preferences.edit();
        editor.putString(TIPO_REN_IND, filtros.get(TIPO_REN_IND));
        editor.putString(TIPO_REN_GPO, filtros.get(TIPO_REN_GPO));
        editor.putString(NOMBRE_REN, filtros.get(NOMBRE_REN));
        editor.putString(MENOR_45_REN, filtros.get(MENOR_45_REN));
        editor.putString(CONTADOR_REN, filtros.get(CONTADOR_REN));
        editor.commit();
    }

    public ArrayList<String> getFiltrosEnProcesoRen() {
        ArrayList<String> filtros = new ArrayList<>();
        preferences = ctx.getSharedPreferences(APP_PREF, Context.MODE_PRIVATE);
        filtros.add(preferences.getString(TIPO_REN_IND_PRO, "0"));  // 0 -> IND
        filtros.add(preferences.getString(TIPO_REN_GPO_PRO, "0"));  // 1 -> GPO
        filtros.add(preferences.getString(NOMBRE_REN_PRO, ""));  // 2 -> NOMBRE
        filtros.add(preferences.getString(MENOR_45_REN_PRO, "0")); // 3 -> MENOR 45 DIA VENCIMIENTO
        filtros.add(preferences.getString(CONTADOR_REN_PRO, "0")); // 4 -> CONTADOR
        return filtros;
    }

    public void setFiltrosEnProcesoRen(HashMap<String, String> filtros) {
        preferences = ctx.getSharedPreferences(APP_PREF, Context.MODE_PRIVATE);
        editor = preferences.edit();
        editor.putString(TIPO_REN_IND_PRO, filtros.get(TIPO_REN_IND_PRO));
        editor.putString(TIPO_REN_GPO_PRO, filtros.get(TIPO_REN_GPO_PRO));
        editor.putString(NOMBRE_REN_PRO, filtros.get(NOMBRE_REN_PRO));
        editor.putString(MENOR_45_REN_PRO, filtros.get(MENOR_45_REN_PRO));
        editor.putString(CONTADOR_REN_PRO, filtros.get(CONTADOR_REN_PRO));
        editor.commit();
    }

    public ArrayList<String> getFiltrosCompletadosRen() {
        ArrayList<String> filtros = new ArrayList<>();
        preferences = ctx.getSharedPreferences(APP_PREF, Context.MODE_PRIVATE);
        filtros.add(preferences.getString(TIPO_REN_IND_COMP, "0"));  // 0 -> IND
        filtros.add(preferences.getString(TIPO_REN_GPO_COMP, "0"));  // 1 -> GPO
        filtros.add(preferences.getString(NOMBRE_REN_COMP, ""));  // 2 -> NOMBRE
        filtros.add(preferences.getString(MENOR_45_REN_COMP, "0")); // 3 -> MENOR 45 DIA VENCIMIENTO
        filtros.add(preferences.getString(CONTADOR_REN_COMP, "0")); // 4 -> CONTADOR
        return filtros;
    }

    public void setFiltrosCompletadosRen(HashMap<String, String> filtros) {
        preferences = ctx.getSharedPreferences(APP_PREF, Context.MODE_PRIVATE);
        editor = preferences.edit();
        editor.putString(TIPO_REN_IND_COMP, filtros.get(TIPO_REN_IND_COMP));
        editor.putString(TIPO_REN_GPO_COMP, filtros.get(TIPO_REN_GPO_COMP));
        editor.putString(NOMBRE_REN_COMP, filtros.get(NOMBRE_REN_COMP));
        editor.putString(MENOR_45_REN_COMP, filtros.get(MENOR_45_REN_COMP));
        editor.putString(CONTADOR_REN_COMP, filtros.get(CONTADOR_REN_COMP));
        editor.commit();
    }

    public ArrayList<String> getFiltrosEnProcesoOri() {
        ArrayList<String> filtros = new ArrayList<>();
        preferences = ctx.getSharedPreferences(APP_PREF, Context.MODE_PRIVATE);
        filtros.add(preferences.getString(TIPO_ORI_IND_PRO, "0"));  // 0 -> IND
        filtros.add(preferences.getString(TIPO_ORI_GPO_PRO, "0"));  // 1 -> GPO
        filtros.add(preferences.getString(NOMBRE_ORI_PRO, ""));  // 2 -> NOMBRE
        filtros.add(preferences.getString(MENOR_45_ORI_PRO, "0")); // 3 -> MENOR 45 DIA VENCIMIENTO
        filtros.add(preferences.getString(CONTADOR_ORI_PRO, "0")); // 4 -> CONTADOR
        return filtros;
    }

    public void setFiltrosEnProcesoOri(HashMap<String, String> filtros) {
        preferences = ctx.getSharedPreferences(APP_PREF, Context.MODE_PRIVATE);
        editor = preferences.edit();
        editor.putString(TIPO_ORI_IND_PRO, filtros.get(TIPO_ORI_IND_PRO));
        editor.putString(TIPO_ORI_GPO_PRO, filtros.get(TIPO_ORI_GPO_PRO));
        editor.putString(NOMBRE_ORI_PRO, filtros.get(NOMBRE_ORI_PRO));
        editor.putString(MENOR_45_ORI_PRO, filtros.get(MENOR_45_ORI_PRO));
        editor.putString(CONTADOR_ORI_PRO, filtros.get(CONTADOR_ORI_PRO));
        editor.commit();
    }

    public ArrayList<String> getFiltrosCompletadosOri() {
        ArrayList<String> filtros = new ArrayList<>();
        preferences = ctx.getSharedPreferences(APP_PREF, Context.MODE_PRIVATE);
        filtros.add(preferences.getString(TIPO_ORI_IND_COMP, "0"));  // 0 -> IND
        filtros.add(preferences.getString(TIPO_ORI_GPO_COMP, "0"));  // 1 -> GPO
        filtros.add(preferences.getString(NOMBRE_ORI_COMP, ""));  // 2 -> NOMBRE
        filtros.add(preferences.getString(MENOR_45_ORI_COMP, "0")); // 3 -> MENOR 45 DIA VENCIMIENTO
        filtros.add(preferences.getString(CONTADOR_ORI_COMP, "0")); // 4 -> CONTADOR
        return filtros;
    }

    public void setFiltrosCompletadosOri(HashMap<String, String> filtros) {
        preferences = ctx.getSharedPreferences(APP_PREF, Context.MODE_PRIVATE);
        editor = preferences.edit();
        editor.putString(TIPO_ORI_IND_COMP, filtros.get(TIPO_ORI_IND_COMP));
        editor.putString(TIPO_ORI_GPO_COMP, filtros.get(TIPO_ORI_GPO_COMP));
        editor.putString(NOMBRE_ORI_COMP, filtros.get(NOMBRE_ORI_COMP));
        editor.putString(MENOR_45_ORI_COMP, filtros.get(MENOR_45_ORI_COMP));
        editor.putString(CONTADOR_ORI_COMP, filtros.get(CONTADOR_ORI_COMP));
        editor.commit();
    }

    // ===================  Info User ==============================================================
    public void setUser(String serieID, String nombre, String apPaterno, String apMaterno, String userName, String type_user, boolean flag, String access_token, String modulos, String ID) {
        preferences = ctx.getSharedPreferences(APP_PREF, Context.MODE_PRIVATE);
        editor = preferences.edit();
        editor.putString(SERIE_ID, serieID);           //0
        editor.putString(NOMBRE, nombre);              //1
        editor.putString(AP_PATERNO, apPaterno);       //2
        editor.putString(AP_MATERNO, apMaterno);       //3
        editor.putString(USER_NAME, userName);         //4
        editor.putString(TYPE_USER, type_user);        //5
        editor.putBoolean(FLAG, flag);                 //6 para inicio de sesion
        editor.putString(ACCESS_TOKEN, access_token);  //7
        editor.putString(MODULOS, modulos);            //8
        editor.putString(ID_CARTERA, ID);            //9
        editor.commit();
    }

    public ArrayList<String> getUser() {
        ArrayList<String> user = new ArrayList<>();
        preferences = ctx.getSharedPreferences(APP_PREF, Context.MODE_PRIVATE);
        user.add(preferences.getString(SERIE_ID, null));                       //0
        user.add(preferences.getString(NOMBRE, null));                          //1
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

    public void deleteUser() {
        preferences = ctx.getSharedPreferences(APP_PREF, Context.MODE_PRIVATE);
        editor = preferences.edit();
        editor.remove(SERIE_ID);             //0
        editor.remove(NOMBRE);               //1
        editor.remove(AP_PATERNO);           //2
        editor.remove(AP_MATERNO);           //3
        editor.remove(USER_NAME);            //4
        editor.remove(TYPE_USER);            //5
        editor.putBoolean(FLAG, false);      //6 para inicio de sesion
        editor.remove(ACCESS_TOKEN);         //7
        editor.remove(MODULOS);              //8
        editor.remove(ID_CARTERA);           //9
        editor.commit();
    }

    // ===================  Count Denuncias PLD ==========================================================
    public void setMailBox(String date, String count) {
        preferences = ctx.getSharedPreferences(APP_PREF, Context.MODE_PRIVATE);
        editor = preferences.edit();
        editor.putString(DATE, date);
        editor.putString(MAILBOX, count);
        editor.commit();
    }

    public ArrayList<String> getMailBox() {
        ArrayList<String> launch = new ArrayList<>();
        preferences = ctx.getSharedPreferences(APP_PREF, Context.MODE_PRIVATE);
        launch.add(preferences.getString(DATE, ""));
        launch.add(preferences.getString(MAILBOX, "0"));
        return launch;
    }

    public void deleteMailBox() {
        preferences = ctx.getSharedPreferences(APP_PREF, Context.MODE_PRIVATE);
        editor = preferences.edit();
        editor.remove(MAILBOX);
        editor.remove(DATE);
        editor.apply();
    }

}
