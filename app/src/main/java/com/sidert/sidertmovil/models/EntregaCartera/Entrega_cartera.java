package com.sidert.sidertmovil.models.EntregaCartera;

import android.database.Cursor;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.sidert.sidertmovil.models.BaseDao;
import com.sidert.sidertmovil.models.BaseModel;
import com.sidert.sidertmovil.models.IFillModel;

import java.io.Serializable;

public class Entrega_cartera extends BaseModel implements Serializable, IFillModel {

    public static final String TBL              = "tbl_datos_entrega_cartera";
    public static final String COL_ID_ENTREGACARTERA = "id_tipocartera";
    public static final String COL_TIPO_ENTREGACARTERA = "tipo_EntregaCartera";
    public static final String COL_ESTATUS             = "estatus";

    private Integer id;

    @SerializedName("id_tipocartera")
    @Expose
    private Integer id_EntregaCartera;

    @SerializedName("tipo_EntregaCartera")
    @Expose
    private String tipo_EntregaCartera;

    @SerializedName("estatus")
    @Expose
    private Integer estatus;

    public Integer getId_EntregaCartera() {
        return id_EntregaCartera;
    }

    public void setId_EntregaCartera(Integer id_EntregaCartera) {
        this.id_EntregaCartera = id_EntregaCartera;
    }

    public String getTipo_EntregaCartera() {
        return tipo_EntregaCartera;
    }

    public void setTipo_EntregaCartera(String tipo_EntregaCartera) {
        this.tipo_EntregaCartera = tipo_EntregaCartera;
    }

    public Integer getEstatus() {
        return estatus;
    }

    public void setEstatus(Integer estatus) {
        this.estatus = estatus;
    }

    @Override
    public void fill(Cursor row){
        this.row = row;

        id_EntregaCartera = getInt(COL_ID_ENTREGACARTERA);
        tipo_EntregaCartera = getString(COL_TIPO_ENTREGACARTERA);
        estatus = getInt(COL_ESTATUS);

    }

}
