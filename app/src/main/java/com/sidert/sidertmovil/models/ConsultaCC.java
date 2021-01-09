package com.sidert.sidertmovil.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class ConsultaCC implements Serializable {

    @SerializedName("sucursal")
    @Expose
    private Long sucursal;

    @SerializedName("apellidoPaterno")
    @Expose
    private String apPaterno;

    @SerializedName("apellidoMaterno")
    @Expose
    private String apMaterno;

    @SerializedName("primerNombre")
    @Expose
    private String primerNombre;

    @SerializedName("monto_solicitado")
    @Expose
    private Integer montoSolicitado;

    @SerializedName("producto")
    @Expose
    private String producto;

    @SerializedName("usuario_id")
    @Expose
    private Integer usuarioId;

    @SerializedName("origen")
    @Expose
    private String origen;

    @SerializedName("segundoNombre")
    @Expose
    private String segundoNombre;

    @SerializedName("fechaNacimiento")
    @Expose
    private String fechaNac;

    @SerializedName("CURP")
    @Expose
    private String curp;

    @SerializedName("RFC")
    @Expose
    private String rfc;

    @SerializedName("nacionalidad")
    @Expose
    private String nacionalidad;

    @SerializedName("residencia")
    @Expose
    private CatResidencia catResidencia;

    @SerializedName("estadocivil")
    @Expose
    private CatEstadoCivil catEstadoCivil;

    @SerializedName("sexo")
    @Expose
    private CatSexo catSexo;

    @SerializedName("domicilio")
    @Expose
    private DomicilioPeticion domPeticion;

    public Long getSucursal() {
        return sucursal;
    }

    public void setSucursal(Long sucursal) {
        this.sucursal = sucursal;
    }

    public String getApPaterno() {
        return apPaterno;
    }

    public void setApPaterno(String apPaterno) {
        this.apPaterno = apPaterno;
    }

    public String getApMaterno() {
        return apMaterno;
    }

    public void setApMaterno(String apMaterno) {
        this.apMaterno = apMaterno;
    }

    public String getPrimerNombre() {
        return primerNombre;
    }

    public void setPrimerNombre(String primerNombre) {
        this.primerNombre = primerNombre;
    }

    public Integer getMontoSolicitado() {
        return montoSolicitado;
    }

    public void setMontoSolicitado(Integer montoSolicitado) {
        this.montoSolicitado = montoSolicitado;
    }

    public String getProducto() {
        return producto;
    }

    public void setProducto(String producto) {
        this.producto = producto;
    }

    public Integer getUsuarioId() {
        return usuarioId;
    }

    public void setUsuarioId(Integer usuarioId) {
        this.usuarioId = usuarioId;
    }

    public String getOrigen() {
        return origen;
    }

    public void setOrigen(String origen) {
        this.origen = origen;
    }

    public String getSegundoNombre() {
        return segundoNombre;
    }

    public void setSegundoNombre(String segundoNombre) {
        this.segundoNombre = segundoNombre;
    }

    public String getFechaNac() {
        return fechaNac;
    }

    public void setFechaNac(String fechaNac) {
        this.fechaNac = fechaNac;
    }

    public String getCurp() {
        return curp;
    }

    public void setCurp(String curp) {
        this.curp = curp;
    }

    public String getRfc() {
        return rfc;
    }

    public void setRfc(String rfc) {
        this.rfc = rfc;
    }

    public String getNacionalidad() {
        return nacionalidad;
    }

    public void setNacionalidad(String nacionalidad) {
        this.nacionalidad = nacionalidad;
    }

    public CatResidencia getCatResidencia() {
        return catResidencia;
    }

    public void setCatResidencia(CatResidencia catResidencia) {
        this.catResidencia = catResidencia;
    }

    public CatEstadoCivil getCatEstadoCivil() {
        return catEstadoCivil;
    }

    public void setCatEstadoCivil(CatEstadoCivil catEstadoCivil) {
        this.catEstadoCivil = catEstadoCivil;
    }

    public CatSexo getCatSexo() {
        return catSexo;
    }

    public void setCatSexo(CatSexo catSexo) {
        this.catSexo = catSexo;
    }

    public DomicilioPeticion getDomPeticion() {
        return domPeticion;
    }

    public void setDomPeticion(DomicilioPeticion domPeticion) {
        this.domPeticion = domPeticion;
    }

    public class CatResidencia implements Serializable{

    }

    public class CatEstadoCivil implements Serializable{

    }

    public class CatSexo implements Serializable{

    }

    public static class DomicilioPeticion implements Serializable{

        @SerializedName("direccion")
        @Expose
        private String direccion;

        @SerializedName("coloniaPoblacion")
        @Expose
        private String coloniaPoblacion;

        @SerializedName("delegacionMunicipio")
        @Expose
        private String delegacionMunicipio;

        @SerializedName("ciudad")
        @Expose
        private String ciudad;

        @SerializedName("estado")
        @Expose
        private String estado;

        @SerializedName("CP")
        @Expose
        private String cP;

        public String getDireccion() {
            return direccion;
        }

        public void setDireccion(String direccion) {
            this.direccion = direccion;
        }

        public String getColoniaPoblacion() {
            return coloniaPoblacion;
        }

        public void setColoniaPoblacion(String coloniaPoblacion) {
            this.coloniaPoblacion = coloniaPoblacion;
        }

        public String getDelegacionMunicipio() {
            return delegacionMunicipio;
        }

        public void setDelegacionMunicipio(String delegacionMunicipio) {
            this.delegacionMunicipio = delegacionMunicipio;
        }

        public String getCiudad() {
            return ciudad;
        }

        public void setCiudad(String ciudad) {
            this.ciudad = ciudad;
        }

        public String getEstado() {
            return estado;
        }

        public void setEstado(String estado) {
            this.estado = estado;
        }

        public String getCP() {
            return cP;
        }

        public void setCP(String cP) {
            this.cP = cP;
        }
    }
}
