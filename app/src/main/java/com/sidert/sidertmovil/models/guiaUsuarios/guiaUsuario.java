package com.sidert.sidertmovil.models.guiaUsuarios;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

import okhttp3.MultipartBody;

public class guiaUsuario implements Serializable {
    @SerializedName("file")
    @Expose
    private String file;

    @SerializedName("size")
    @Expose
    private String size;

    @SerializedName("download")
    @Expose
    private String archivo;

    public String getNombre() {
        return file;
    }

    public void setNombre(String nombre) {
        this.file = nombre;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public String getArchivo() {
        return archivo;
    }

    public void setArchivo(String archivo) {
        this.archivo = archivo;
    }
}
