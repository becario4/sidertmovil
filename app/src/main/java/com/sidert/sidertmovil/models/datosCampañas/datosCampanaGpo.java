package com.sidert.sidertmovil.models.datosCampañas;

import android.database.Cursor;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.sidert.sidertmovil.models.BaseModel;
import com.sidert.sidertmovil.models.IFillModel;

import java.io.Serializable;

public class datosCampanaGpo extends BaseModel implements Serializable, IFillModel {


    public static final String  TBL               = "tbl_datos_credito_campana_gpo";
    public static final String  COL_ID_SOLICITUD  = "id_solicitud";
    public static final String  COL_ID_ORIGINACION= "id_originacion";
    public static final String  COL_ID_CAMPANA       = "id_campana";
    public static final String  COL_TIPO_CAMPANA  = "tipo_campana";
    public static final String  COL_NOMBRE_REFIERO= "nombre_refiero";


    private Integer _id;

    @SerializedName(COL_ID_SOLICITUD)
    @Expose
    private Integer id_solicitud;

    @SerializedName(COL_ID_ORIGINACION)
    @Expose
    private Integer id_originacion;

    @SerializedName(COL_ID_CAMPANA)
    @Expose
    private Integer id_campana;

    @SerializedName(COL_TIPO_CAMPANA)
    @Expose
    private String tipo_campana;

    @SerializedName(COL_NOMBRE_REFIERO)
    @Expose
    private String nombre_refiero;


    public Integer getId_solicitud() {
        return id_solicitud;
    }

    public void setId_solicitud(Integer id_solicitud) {
        this.id_solicitud = id_solicitud;
    }

    public Integer getId_originacion() {
        return id_originacion;
    }

    public void setId_originacion(Integer id_originacion) {
        this.id_originacion = id_originacion;
    }

    public Integer getId_campana() {
        return id_campana;
    }

    public void setId_campana(Integer id_campana) {
        this.id_campana = id_campana;
    }

    public String getTipo_campana() {
        return tipo_campana;
    }

    public void setTipo_campana(String tipo_campana) {
        this.tipo_campana = tipo_campana;
    }

    public String getNombre_refiero() {
        return nombre_refiero;
    }

    public void setNombre_refiero(String nombre_refiero) {
        this.nombre_refiero = nombre_refiero;
    }

    @Override
    public void fill(Cursor row){
        this.row = row;

        id_solicitud    = getInt(COL_ID_SOLICITUD);
        id_originacion  = getInt(COL_ID_ORIGINACION);
        id_campana      = getInt(COL_ID_CAMPANA);
        tipo_campana    = getString(COL_TIPO_CAMPANA);
        nombre_refiero  = getString(COL_NOMBRE_REFIERO);
    }






}
