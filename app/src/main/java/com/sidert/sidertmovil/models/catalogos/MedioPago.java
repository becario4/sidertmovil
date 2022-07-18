package com.sidert.sidertmovil.models.catalogos;

import android.database.Cursor;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.sidert.sidertmovil.models.BaseModel;
import com.sidert.sidertmovil.models.IFillModel;

import java.io.Serializable;

import static com.sidert.sidertmovil.utils.Constants.K_MEDIOS_PAGOS;

public class MedioPago extends BaseModel implements Serializable, IFillModel {
    public final static String TBL = "tbl_medios_pago_ori";
    protected static final String COL_LOCAL_ID = "_id";
    protected static final String COL_ID = "id";
    protected static final String COL_NOMBRE = "nombre";

    @SerializedName(COL_LOCAL_ID)
    @Expose
    private Integer localId;

    @SerializedName(COL_ID)
    @Expose
    private Integer id;

    @SerializedName(COL_NOMBRE)
    @Expose
    private String nombre;

    public Integer getLocalId() {
        return localId;
    }

    public void setLocalId(Integer localId) {
        this.localId = localId;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public static String getIdMediosPago(String mediosPago)
    {
        String aux = "";

        if (mediosPago.trim().isEmpty()){
            String[] medios = mediosPago.split(",");

            if (medios.length > 0){
                for (int m = 0; m < medios.length; m++){
                    if (m == 0)
                        aux = "'"+medios[m].trim()+"'";
                    else
                        aux += ","+"'"+medios[m].trim()+"'";
                }
            }
        }

        return aux;
    }

    @Override
    public void fill(Cursor row) {
        this.row = row;

        localId = getInt(COL_LOCAL_ID);
        id      = getInt(COL_ID);
        nombre  = getString(COL_NOMBRE);
    }
}