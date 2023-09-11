package com.sidert.sidertmovil.v2.domain.entities;

import java.io.Serializable;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "tbl_recuperacion_recibos")
public final class RecuperacionRecibos implements Serializable {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "_id")
    private Long id;
    @ColumnInfo(name = "grupo_id")
    private String grupoId;
    @ColumnInfo(name = "num_solicitud")
    private String numSolicitud;
    @ColumnInfo(name = "medio_pago")
    private String medioPago;
    @ColumnInfo(name = "evidencia")
    private String evidencia;
    @ColumnInfo(name = "tipo_imagen")
    private String tipoImagen;
    @ColumnInfo(name = "fecha_termino")
    private String fechaTermino;
    @ColumnInfo(name = "fecha_envio")
    private String fechaEnvio;
    @ColumnInfo(name = "tipo")
    private String tipo;
    @ColumnInfo(name = "nombre")
    private String nombre;
    @ColumnInfo(name = "estatus")
    private Integer estatus;
    @ColumnInfo(name = "monto")
    private String monto;
    @ColumnInfo(name = "imprimir_recibo")
    private String imprimirRecibo;
    @ColumnInfo(name = "folio_manual")
    private String folioManual;
    @ColumnInfo(name = "cliente_id")
    private String clienteId;
    @ColumnInfo(name = "total_integrantes")
    private Integer totalIntegrantes;
    @ColumnInfo(name = "total_integrantes_manual")
    private Integer totalIntegrantesManual;


// Getters & Setters

    public Long getId(){
        return this.id;
    }

    public void setId(Long id){
        this.id = id;
    }
    public String getGrupoId(){
        return this.grupoId;
    }

    public void setGrupoId(String grupoId){
        this.grupoId = grupoId;
    }
    public String getNumSolicitud(){
        return this.numSolicitud;
    }

    public void setNumSolicitud(String numSolicitud){
        this.numSolicitud = numSolicitud;
    }
    public String getMedioPago(){
        return this.medioPago;
    }

    public void setMedioPago(String medioPago){
        this.medioPago = medioPago;
    }
    public String getEvidencia(){
        return this.evidencia;
    }

    public void setEvidencia(String evidencia){
        this.evidencia = evidencia;
    }
    public String getTipoImagen(){
        return this.tipoImagen;
    }

    public void setTipoImagen(String tipoImagen){
        this.tipoImagen = tipoImagen;
    }
    public String getFechaTermino(){
        return this.fechaTermino;
    }

    public void setFechaTermino(String fechaTermino){
        this.fechaTermino = fechaTermino;
    }
    public String getFechaEnvio(){
        return this.fechaEnvio;
    }

    public void setFechaEnvio(String fechaEnvio){
        this.fechaEnvio = fechaEnvio;
    }
    public String getTipo(){
        return this.tipo;
    }

    public void setTipo(String tipo){
        this.tipo = tipo;
    }
    public String getNombre(){
        return this.nombre;
    }

    public void setNombre(String nombre){
        this.nombre = nombre;
    }
    public Integer getEstatus(){
        return this.estatus;
    }

    public void setEstatus(Integer estatus){
        this.estatus = estatus;
    }
    public String getMonto(){
        return this.monto;
    }

    public void setMonto(String monto){
        this.monto = monto;
    }
    public String getImprimirRecibo(){
        return this.imprimirRecibo;
    }

    public void setImprimirRecibo(String imprimirRecibo){
        this.imprimirRecibo = imprimirRecibo;
    }
    public String getFolioManual(){
        return this.folioManual;
    }

    public void setFolioManual(String folioManual){
        this.folioManual = folioManual;
    }
    public String getClienteId(){
        return this.clienteId;
    }

    public void setClienteId(String clienteId){
        this.clienteId = clienteId;
    }
    public Integer getTotalIntegrantes(){
        return this.totalIntegrantes;
    }

    public void setTotalIntegrantes(Integer totalIntegrantes){
        this.totalIntegrantes = totalIntegrantes;
    }
    public Integer getTotalIntegrantesManual(){
        return this.totalIntegrantesManual;
    }

    public void setTotalIntegrantesManual(Integer totalIntegrantesManual){
        this.totalIntegrantesManual = totalIntegrantesManual;
    }

}