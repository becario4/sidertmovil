package com.sidert.sidertmovil.utils;

public class DatosCompartidos {

    private static DatosCompartidos instance = null;

    private Long id_solicitud;

    private Integer id_solicitud_integrante;

    private Integer cliente_id;

    private Long credito_id;

    protected DatosCompartidos(){}

    public static DatosCompartidos getInstance(){
        if(instance == null){
            instance = new DatosCompartidos();
        }
        return instance;
    }

    public Long getId_solicitud(){
        return id_solicitud;
    }

    public void setId_solicitud(Long id_solicitud){
        this.id_solicitud = id_solicitud;
    }

    public Integer getId_solicitud_integrante(){return id_solicitud_integrante;}

    public void setId_solicitud_integrante(Integer id_solicitud_integrante){this.id_solicitud_integrante = id_solicitud_integrante;}

    public Integer getCliente_id(){return cliente_id;}

    public void setCliente_id(Integer cliente_id){this.cliente_id = cliente_id;}

    public Long getCredito_id(){return credito_id;}

    public void setCredito_id(Long credito_id){this.credito_id = credito_id;}

}
