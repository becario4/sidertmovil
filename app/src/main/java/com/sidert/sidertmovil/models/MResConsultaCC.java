package com.sidert.sidertmovil.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class MResConsultaCC implements Serializable {
    @SerializedName("error4")
    @Expose
    private String error4;
    @SerializedName("ok")
    @Expose
    private Ok ok;

    public Ok getOk() {
        return ok;
    }

    public void setOk(Ok ok) {
        this.ok = ok;
    }

    public String getError4() {
        return error4;
    }

    public void setError4(String error4) {
        this.error4 = error4;
    }

    public class Ok implements Serializable{
        @SerializedName("res")
        @Expose
        private Respuesta res;
        @SerializedName("err")
        @Expose
        private Errores err;
        @SerializedName("id")
        @Expose
        private Long id;
        @SerializedName("sucursal")
        @Expose
        private Long sucursal;
        @SerializedName("usuario_id")
        @Expose
        private Long usuario_id;
        @SerializedName("apellido_paterno")
        @Expose
        private String apellido_paterno;
        @SerializedName("producto")
        @Expose
        private String producto;
        @SerializedName("apellido_materno")
        @Expose
        private String apellido_materno;
        @SerializedName("primer_nombre")
        @Expose
        private String primer_nombre;
        @SerializedName("segundo_nombre")
        @Expose
        private String segundo_nombre;
        @SerializedName("fecha_nacimiento")
        @Expose
        private String fecha_nacimiento;
        @SerializedName("rfc")
        @Expose
        private String rfc;
        @SerializedName("curp")
        @Expose
        private String curp;
        @SerializedName("nacionalidad")
        @Expose
        private String nacionalidad;
        @SerializedName("pdf")
        @Expose
        private String pdf;
        @SerializedName("json")
        @Expose
        private String json;
        @SerializedName("direccion")
        @Expose
        private String direccion;
        @SerializedName("colonia_poblacion")
        @Expose
        private String colonia_poblacion;
        @SerializedName("delegacion_municipio")
        @Expose
        private String delegacion_municipio;
        @SerializedName("ciudad")
        @Expose
        private String ciudad;
        @SerializedName("estado")
        @Expose
        private String estado;
        @SerializedName("cp")
        @Expose
        private String cp;
        @SerializedName("preautorizacion")
        @Expose
        private String preautorizacion;
        @SerializedName("autorizacion")
        @Expose
        private String autorizacion;
        @SerializedName("monto_solicitado")
        @Expose
        private Double monto_solicitado;
        @SerializedName("usuario_autorizado_id")
        @Expose
        private Long usuario_autorizado_id;
        @SerializedName("prospecto")
        @Expose
        private String prospecto;
        @SerializedName("origen")
        @Expose
        private String origen;
        @SerializedName("folio")
        @Expose
        private String folio;
        @SerializedName("asesor_id")
        @Expose
        private Long  asesor_id;
        @SerializedName("imagen")
        @Expose
        private String imagen;
        @SerializedName("bandera_imagen")
        @Expose
        private Boolean bandera_imagen;


        public Long getId() {
            return id;
        }

        public void setId(Long id) {
            this.id = id;
        }

        public Long getSucursal() {
            return sucursal;
        }

        public void setSucursal(Long sucursal) {
            this.sucursal = sucursal;
        }

        public Long getUsuario_id() {
            return usuario_id;
        }

        public void setUsuario_id(Long usuario_id) {
            this.usuario_id = usuario_id;
        }

        public String getApellido_paterno() {
            return apellido_paterno;
        }

        public void setApellido_paterno(String apellido_paterno) {
            this.apellido_paterno = apellido_paterno;
        }

        public String getProducto() {
            return producto;
        }

        public void setProducto(String producto) {
            this.producto = producto;
        }

        public String getApellido_materno() {
            return apellido_materno;
        }

        public void setApellido_materno(String apellido_materno) {
            this.apellido_materno = apellido_materno;
        }

        public String getPrimer_nombre() {
            return primer_nombre;
        }

        public void setPrimer_nombre(String primer_nombre) {
            this.primer_nombre = primer_nombre;
        }

        public String getSegundo_nombre() {
            return segundo_nombre;
        }

        public void setSegundo_nombre(String segundo_nombre) {
            this.segundo_nombre = segundo_nombre;
        }

        public String getFecha_nacimiento() {
            return fecha_nacimiento;
        }

        public void setFecha_nacimiento(String fecha_nacimiento) {
            this.fecha_nacimiento = fecha_nacimiento;
        }

        public String getRfc() {
            return rfc;
        }

        public void setRfc(String rfc) {
            this.rfc = rfc;
        }

        public String getCurp() {
            return curp;
        }

        public void setCurp(String curp) {
            this.curp = curp;
        }

        public String getNacionalidad() {
            return nacionalidad;
        }

        public void setNacionalidad(String nacionalidad) {
            this.nacionalidad = nacionalidad;
        }

        public String getPdf() {
            return pdf;
        }

        public void setPdf(String pdf) {
            this.pdf = pdf;
        }

        public String getJson() {
            return json;
        }

        public void setJson(String json) {
            this.json = json;
        }

        public String getDireccion() {
            return direccion;
        }

        public void setDireccion(String direccion) {
            this.direccion = direccion;
        }

        public String getColonia_poblacion() {
            return colonia_poblacion;
        }

        public void setColonia_poblacion(String colonia_poblacion) {
            this.colonia_poblacion = colonia_poblacion;
        }

        public String getDelegacion_municipio() {
            return delegacion_municipio;
        }

        public void setDelegacion_municipio(String delegacion_municipio) {
            this.delegacion_municipio = delegacion_municipio;
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

        public String getCp() {
            return cp;
        }

        public void setCp(String cp) {
            this.cp = cp;
        }

        public String getPreautorizacion() {
            return preautorizacion;
        }

        public void setPreautorizacion(String preautorizacion) {
            this.preautorizacion = preautorizacion;
        }

        public String getAutorizacion() {
            return autorizacion;
        }

        public void setAutorizacion(String autorizacion) {
            this.autorizacion = autorizacion;
        }

        public Double getMonto_solicitado() {
            return monto_solicitado;
        }

        public void setMonto_solicitado(Double monto_solicitado) {
            this.monto_solicitado = monto_solicitado;
        }

        public Long getUsuario_autorizado_id() {
            return usuario_autorizado_id;
        }

        public void setUsuario_autorizado_id(Long usuario_autorizado_id) {
            this.usuario_autorizado_id = usuario_autorizado_id;
        }

        public String getProspecto() {
            return prospecto;
        }

        public void setProspecto(String prospecto) {
            this.prospecto = prospecto;
        }

        public String getOrigen() {
            return origen;
        }

        public void setOrigen(String origen) {
            this.origen = origen;
        }

        public String getFolio() {
            return folio;
        }

        public void setFolio(String folio) {
            this.folio = folio;
        }

        public Long getAsesor_id() {
            return asesor_id;
        }

        public void setAsesor_id(Long asesor_id) {
            this.asesor_id = asesor_id;
        }

        public String getImagen() {
            return imagen;
        }

        public void setImagen(String imagen) {
            this.imagen = imagen;
        }

        public Boolean getBandera_imagen() {
            return bandera_imagen;
        }

        public void setBandera_imagen(Boolean bandera_imagen) {
            this.bandera_imagen = bandera_imagen;
        }

        public Respuesta getRes() {
            return res;
        }

        public void setRes(Respuesta res) {
            this.res = res;
        }

        public Errores getErr() {
            return err;
        }

        public void setErr(Errores err) {
            this.err = err;
        }
    }
    public class Detail {
        public String errorcode;
        public String getErrorcode() {
            return errorcode;
        }

        public void setErrorcode(String errorcode) {
            this.errorcode = errorcode;
        }
    }
    public class Fault {
        public String faultstring;
        public Detail detail;
        public Detail getDetail() {
            return detail;
        }
        public void setDetail(Detail detail) {
            this.detail = detail;
        }
        public String getFaultstring() {
            return faultstring;
        }
        public void setFaultstring(String faultstring) {
            this.faultstring = faultstring;
        }
    }
    public class Errores  implements Serializable{
        @SerializedName("incidentId")
        @Expose
        public String incidentId;
        @SerializedName("hostName")
        @Expose
        public String hostName;
        @SerializedName("errorCode")
        @Expose
        public String errorCode;
        @SerializedName("description")
        @Expose
        public String description;
        @SerializedName("timeUtc")
        @Expose
        public String timeUtc;
        @SerializedName("clientIp")
        @Expose
        public String clientIp;
        @SerializedName("proxyId")
        @Expose
        public String proxyId;
        @SerializedName("proxyIp")
        @Expose
        public String proxyIp;
        @SerializedName("fault")
        @Expose
        public Fault fault=null;
        @SerializedName("errores")
        @Expose
        private List<Error> errores = null;

        public String getHostName() {
            return hostName;
        }
        public void setHostName(String hostName) {
            this.hostName = hostName;
        }
        public String getErrorCode() {
            return errorCode;
        }
        public void setErrorCode(String errorCode) {
            this.errorCode = errorCode;
        }
        public String getDescription() {
            return description;
        }
        public void setDescription(String description) {
            this.description = description;
        }
        public String getTimeUtc() {
            return timeUtc;
        }

        public void setTimeUtc(String timeUtc) {
            this.timeUtc = timeUtc;
        }
        public String getClientIp() {
            return clientIp;
        }
        public void setClientIp(String clientIp) {
            this.clientIp = clientIp;
        }
        public String getProxyId() {
            return proxyId;
        }
        public void setProxyId(String proxyId) {
            this.proxyId = proxyId;
        }
        public String getProxyIp() {
            return proxyIp;
        }

        public void setProxyIp(String proxyIp) {
            this.proxyIp = proxyIp;
        }
        public String getIncidentId() {
            return incidentId;
        }
        public void setIncidentId(String incidentId) {
            this.incidentId = incidentId;
        }
        public Errores errores(List<Error> errores) {
            this.errores = errores;
            return this;
        }

        public Errores addErroresItem(Error erroresItem) {
            if (this.errores == null) {
                    this.errores = new ArrayList<Error>();
            }
            this.errores.add(erroresItem);
            return this;
        }

        /**
         * Get errores
         * @return errores
         **/
        // @ApiModelProperty(value = "")
        public List<Error> getErrores() {
            return errores;
        }

        public void setErrores(List<Error> errores) {
            this.errores = errores;
        }






        @Override
        public String toString() {
            StringBuilder sb = new StringBuilder();
            sb.append("class Errores {\n");

            sb.append("    errores: ").append(toIndentedString(errores)).append("\n");
            sb.append("}");
            return sb.toString();
        }

        /**
         * Convert the given object to string with each line indented by 4 spaces
         * (except the first line).
         */
        private String toIndentedString(java.lang.Object o) {
            if (o == null) {
                return "null";
            }
            return o.toString().replace("\n", "\n    ");
        }

    }
    public class Respuesta implements Serializable{

    @SerializedName("folio")
    @Expose
    private String folio;
    @SerializedName("folioConsulta")
    @Expose
    private String folioConsulta;
    @SerializedName("folioConsultaOtorgante")
    @Expose
    private String folioConsultaOtorgante;
    @SerializedName("claveOtorgante")
    @Expose
    private String claveOtorgante;
    @SerializedName("declaracionesConsumidor")
    @Expose
    private Object declaracionesConsumidor;
    @SerializedName("persona")
    @Expose
    private Persona persona;
    @SerializedName("consultas")
    @Expose
    private List<Consulta> consultas = null;
    @SerializedName("creditos")
    @Expose
    private List<Credito> creditos = null;
    @SerializedName("domicilios")
    @Expose
    private List<Domicilio> domicilios = null;
    @SerializedName("empleos")
    @Expose
    private List<Empleo> empleos = null;
    @SerializedName("mensajes")
    @Expose
    private List<Mensaje> mensajes = null;

    public String getFolio() {
        return folio;
    }

    public void setFolio(String folio) {
        this.folio = folio;
    }

    public String getFolioConsulta() {
        return folioConsulta;
    }

    public void setFolioConsulta(String folioConsulta) {
        this.folioConsulta = folioConsulta;
    }

    public String getFolioConsultaOtorgante() {
        return folioConsultaOtorgante;
    }

    public void setFolioConsultaOtorgante(String folioConsultaOtorgante) {
        this.folioConsultaOtorgante = folioConsultaOtorgante;
    }

    public String getClaveOtorgante() {
        return claveOtorgante;
    }

    public void setClaveOtorgante(String claveOtorgante) {
        this.claveOtorgante = claveOtorgante;
    }

    public Object getDeclaracionesConsumidor() {
        return declaracionesConsumidor;
    }

    public void setDeclaracionesConsumidor(Object declaracionesConsumidor) {
        this.declaracionesConsumidor = declaracionesConsumidor;
    }

    public Persona getPersona() {
        return persona;
    }

    public void setPersona(Persona persona) {
        this.persona = persona;
    }

    public List<Consulta> getConsultas() {
        return consultas;
    }

    public void setConsultas(List<Consulta> consultas) {
        this.consultas = consultas;
    }

    public List<Credito> getCreditos() {
        return creditos;
    }

    public void setCreditos(List<Credito> creditos) {
        this.creditos = creditos;
    }

    public List<Domicilio> getDomicilios() {
        return domicilios;
    }

    public void setDomicilios(List<Domicilio> domicilios) {
        this.domicilios = domicilios;
    }

    public List<Empleo> getEmpleos() {
        return empleos;
    }

    public void setEmpleos(List<Empleo> empleos) {
        this.empleos = empleos;
    }

    public List<Mensaje> getMensajes() {
        return mensajes;
    }

    public void setMensajes(List<Mensaje> mensajes) {
        this.mensajes = mensajes;
    }

}
    public class Consulta implements Serializable {

        @SerializedName("fechaConsulta")
        @Expose
        private String fechaConsulta;
        @SerializedName("claveOtorgante")
        @Expose
        private Object claveOtorgante;
        @SerializedName("nombreOtorgante")
        @Expose
        private String nombreOtorgante;
        @SerializedName("direccionOtorgante")
        @Expose
        private Object direccionOtorgante;
        @SerializedName("telefonoOtorgante")
        @Expose
        private String telefonoOtorgante;
        @SerializedName("tipoCredito")
        @Expose
        private String tipoCredito;
        @SerializedName("claveUnidadMonetaria")
        @Expose
        private String claveUnidadMonetaria;
        @SerializedName("importeCredito")
        @Expose
        private Double importeCredito;
        @SerializedName("tipoResponsabilidad")
        @Expose
        private Object tipoResponsabilidad;
        @SerializedName("idDomicilio")
        @Expose
        private Object idDomicilio;
        @SerializedName("servicios")
        @Expose
        private Object servicios;

        public String getFechaConsulta() {
            return fechaConsulta;
        }

        public void setFechaConsulta(String fechaConsulta) {
            this.fechaConsulta = fechaConsulta;
        }

        public Object getClaveOtorgante() {
            return claveOtorgante;
        }

        public void setClaveOtorgante(Object claveOtorgante) {
            this.claveOtorgante = claveOtorgante;
        }

        public String getNombreOtorgante() {
            return nombreOtorgante;
        }

        public void setNombreOtorgante(String nombreOtorgante) {
            this.nombreOtorgante = nombreOtorgante;
        }

        public Object getDireccionOtorgante() {
            return direccionOtorgante;
        }

        public void setDireccionOtorgante(Object direccionOtorgante) {
            this.direccionOtorgante = direccionOtorgante;
        }

        public String getTelefonoOtorgante() {
            return telefonoOtorgante;
        }

        public void setTelefonoOtorgante(String telefonoOtorgante) {
            this.telefonoOtorgante = telefonoOtorgante;
        }

        public String getTipoCredito() {
            return tipoCredito;
        }

        public void setTipoCredito(String tipoCredito) {
            this.tipoCredito = tipoCredito;
        }

        public String getClaveUnidadMonetaria() {
            return claveUnidadMonetaria;
        }

        public void setClaveUnidadMonetaria(String claveUnidadMonetaria) {
            this.claveUnidadMonetaria = claveUnidadMonetaria;
        }

        public Double getImporteCredito() {
            return importeCredito;
        }

        public void setImporteCredito(Double importeCredito) {
            this.importeCredito = importeCredito;
        }

        public Object getTipoResponsabilidad() {
            return tipoResponsabilidad;
        }

        public void setTipoResponsabilidad(Object tipoResponsabilidad) {
            this.tipoResponsabilidad = tipoResponsabilidad;
        }

        public Object getIdDomicilio() {
            return idDomicilio;
        }

        public void setIdDomicilio(Object idDomicilio) {
            this.idDomicilio = idDomicilio;
        }

        public Object getServicios() {
            return servicios;
        }

        public void setServicios(Object servicios) {
            this.servicios = servicios;
        }

    }
    public class Domicilio implements Serializable{

        @SerializedName("cp")
        @Expose
        private Integer cp;
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
        private Integer cP;
        @SerializedName("fechaResidencia")
        @Expose
        private String fechaResidencia;
        @SerializedName("numeroTelefono")
        @Expose
        private Object numeroTelefono;
        @SerializedName("tipoDomicilio")
        @Expose
        private Object tipoDomicilio;
        @SerializedName("tipoAsentamiento")
        @Expose
        private Object tipoAsentamiento;
        @SerializedName("fechaRegistroDomicilio")
        @Expose
        private String fechaRegistroDomicilio;
        @SerializedName("tipoAltaDomicilio")
        @Expose
        private Object tipoAltaDomicilio;
        @SerializedName("numeroOtorgantesCarga")
        @Expose
        private Object numeroOtorgantesCarga;
        @SerializedName("numeroOtorgantesConsulta")
        @Expose
        private Object numeroOtorgantesConsulta;
        @SerializedName("idDomicilio")
        @Expose
        private Object idDomicilio;

        public Integer getCp() {
            return cp;
        }

        public void setCp(Integer cp) {
            this.cp = cp;
        }

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

        public Integer getCP() {
            return cP;
        }

        public void setCP(Integer cP) {
            this.cP = cP;
        }

        public String getFechaResidencia() {
            return fechaResidencia;
        }

        public void setFechaResidencia(String fechaResidencia) {
            this.fechaResidencia = fechaResidencia;
        }

        public Object getNumeroTelefono() {
            return numeroTelefono;
        }

        public void setNumeroTelefono(Object numeroTelefono) {
            this.numeroTelefono = numeroTelefono;
        }

        public Object getTipoDomicilio() {
            return tipoDomicilio;
        }

        public void setTipoDomicilio(Object tipoDomicilio) {
            this.tipoDomicilio = tipoDomicilio;
        }

        public Object getTipoAsentamiento() {
            return tipoAsentamiento;
        }

        public void setTipoAsentamiento(Object tipoAsentamiento) {
            this.tipoAsentamiento = tipoAsentamiento;
        }

        public String getFechaRegistroDomicilio() {
            return fechaRegistroDomicilio;
        }

        public void setFechaRegistroDomicilio(String fechaRegistroDomicilio) {
            this.fechaRegistroDomicilio = fechaRegistroDomicilio;
        }

        public Object getTipoAltaDomicilio() {
            return tipoAltaDomicilio;
        }

        public void setTipoAltaDomicilio(Object tipoAltaDomicilio) {
            this.tipoAltaDomicilio = tipoAltaDomicilio;
        }

        public Object getNumeroOtorgantesCarga() {
            return numeroOtorgantesCarga;
        }

        public void setNumeroOtorgantesCarga(Object numeroOtorgantesCarga) {
            this.numeroOtorgantesCarga = numeroOtorgantesCarga;
        }

        public Object getNumeroOtorgantesConsulta() {
            return numeroOtorgantesConsulta;
        }

        public void setNumeroOtorgantesConsulta(Object numeroOtorgantesConsulta) {
            this.numeroOtorgantesConsulta = numeroOtorgantesConsulta;
        }

        public Object getIdDomicilio() {
            return idDomicilio;
        }

        public void setIdDomicilio(Object idDomicilio) {
            this.idDomicilio = idDomicilio;
        }

    }
    public class Empleo implements Serializable{

        @SerializedName("cp")
        @Expose
        private Integer cp;
        @SerializedName("nombreEmpresa")
        @Expose
        private String nombreEmpresa;
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
        private Integer cP;
        @SerializedName("numeroTelefono")
        @Expose
        private String numeroTelefono;
        @SerializedName("extension")
        @Expose
        private String extension;
        @SerializedName("fax")
        @Expose
        private String fax;
        @SerializedName("puesto")
        @Expose
        private String puesto;
        @SerializedName("fechaContratacion")
        @Expose
        private String fechaContratacion;
        @SerializedName("claveMoneda")
        @Expose
        private String claveMoneda;
        @SerializedName("salarioMensual")
        @Expose
        private Double salarioMensual;
        @SerializedName("fechaUltimoDiaEmpleo")
        @Expose
        private String fechaUltimoDiaEmpleo;
        @SerializedName("fechaVerificacionEmpleo")
        @Expose
        private String fechaVerificacionEmpleo;

        public Integer getCp() {
            return cp;
        }

        public void setCp(Integer cp) {
            this.cp = cp;
        }

        public String getNombreEmpresa() {
            return nombreEmpresa;
        }

        public void setNombreEmpresa(String nombreEmpresa) {
            this.nombreEmpresa = nombreEmpresa;
        }

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

        public Integer getCP() {
            return cP;
        }

        public void setCP(Integer cP) {
            this.cP = cP;
        }

        public String getNumeroTelefono() {
            return numeroTelefono;
        }

        public void setNumeroTelefono(String numeroTelefono) {
            this.numeroTelefono = numeroTelefono;
        }

        public String getExtension() {
            return extension;
        }

        public void setExtension(String extension) {
            this.extension = extension;
        }

        public String getFax() {
            return fax;
        }

        public void setFax(String fax) {
            this.fax = fax;
        }

        public String getPuesto() {
            return puesto;
        }

        public void setPuesto(String puesto) {
            this.puesto = puesto;
        }

        public String getFechaContratacion() {
            return fechaContratacion;
        }

        public void setFechaContratacion(String fechaContratacion) {
            this.fechaContratacion = fechaContratacion;
        }

        public String getClaveMoneda() {
            return claveMoneda;
        }

        public void setClaveMoneda(String claveMoneda) {
            this.claveMoneda = claveMoneda;
        }

        public Double getSalarioMensual() {
            return salarioMensual;
        }

        public void setSalarioMensual(Double salarioMensual) {
            this.salarioMensual = salarioMensual;
        }

        public String getFechaUltimoDiaEmpleo() {
            return fechaUltimoDiaEmpleo;
        }

        public void setFechaUltimoDiaEmpleo(String fechaUltimoDiaEmpleo) {
            this.fechaUltimoDiaEmpleo = fechaUltimoDiaEmpleo;
        }

        public String getFechaVerificacionEmpleo() {
            return fechaVerificacionEmpleo;
        }

        public void setFechaVerificacionEmpleo(String fechaVerificacionEmpleo) {
            this.fechaVerificacionEmpleo = fechaVerificacionEmpleo;
        }

    }
    public class Mensaje implements Serializable{

        @SerializedName("tipoMensaje")
        @Expose
        private Integer tipoMensaje;
        @SerializedName("leyenda")
        @Expose
        private String leyenda;

        public Integer getTipoMensaje() {
            return tipoMensaje;
        }

        public void setTipoMensaje(Integer tipoMensaje) {
            this.tipoMensaje = tipoMensaje;
        }

        public String getLeyenda() {
            return leyenda;
        }

        public void setLeyenda(String leyenda) {
            this.leyenda = leyenda;
        }

    }
    public class Persona implements Serializable{

        @SerializedName("curp")
        @Expose
        private Object curp;
        @SerializedName("rfc")
        @Expose
        private String rfc;
        @SerializedName("apellidoPaterno")
        @Expose
        private String apellidoPaterno;
        @SerializedName("apellidoMaterno")
        @Expose
        private String apellidoMaterno;
        @SerializedName("apellidoAdicional")
        @Expose
        private Object apellidoAdicional;
        @SerializedName("nombres")
        @Expose
        private String nombres;
        @SerializedName("fechaNacimiento")
        @Expose
        private String fechaNacimiento;
        @SerializedName("RFC")
        @Expose
        private String rFC;
        @SerializedName("CURP")
        @Expose
        private Object cURP;
        @SerializedName("numeroSeguridadSocial")
        @Expose
        private Object numeroSeguridadSocial;
        @SerializedName("nacionalidad")
        @Expose
        private String nacionalidad;
        @SerializedName("residencia")
        @Expose
        private String residencia;
        @SerializedName("estadoCivil")
        @Expose
        private String estadoCivil;
        @SerializedName("sexo")
        @Expose
        private String sexo;
        @SerializedName("claveElectorIFE")
        @Expose
        private Object claveElectorIFE;
        @SerializedName("numeroDependientes")
        @Expose
        private Integer numeroDependientes;
        @SerializedName("fechaDefuncion")
        @Expose
        private Object fechaDefuncion;

        public Object getCurp() {
            return curp;
        }

        public void setCurp(Object curp) {
            this.curp = curp;
        }

        public String getRfc() {
            return rfc;
        }

        public void setRfc(String rfc) {
            this.rfc = rfc;
        }

        public String getApellidoPaterno() {
            return apellidoPaterno;
        }

        public void setApellidoPaterno(String apellidoPaterno) {
            this.apellidoPaterno = apellidoPaterno;
        }

        public String getApellidoMaterno() {
            return apellidoMaterno;
        }

        public void setApellidoMaterno(String apellidoMaterno) {
            this.apellidoMaterno = apellidoMaterno;
        }

        public Object getApellidoAdicional() {
            return apellidoAdicional;
        }

        public void setApellidoAdicional(Object apellidoAdicional) {
            this.apellidoAdicional = apellidoAdicional;
        }

        public String getNombres() {
            return nombres;
        }

        public void setNombres(String nombres) {
            this.nombres = nombres;
        }

        public String getFechaNacimiento() {
            return fechaNacimiento;
        }

        public void setFechaNacimiento(String fechaNacimiento) {
            this.fechaNacimiento = fechaNacimiento;
        }

        public String getRFC() {
            return rFC;
        }

        public void setRFC(String rFC) {
            this.rFC = rFC;
        }

        public Object getCURP() {
            return cURP;
        }

        public void setCURP(Object cURP) {
            this.cURP = cURP;
        }

        public Object getNumeroSeguridadSocial() {
            return numeroSeguridadSocial;
        }

        public void setNumeroSeguridadSocial(Object numeroSeguridadSocial) {
            this.numeroSeguridadSocial = numeroSeguridadSocial;
        }

        public String getNacionalidad() {
            return nacionalidad;
        }

        public void setNacionalidad(String nacionalidad) {
            this.nacionalidad = nacionalidad;
        }

        public String getResidencia() {
            return residencia;
        }

        public void setResidencia(String residencia) {
            this.residencia = residencia;
        }

        public String getEstadoCivil() {
            return estadoCivil;
        }

        public void setEstadoCivil(String estadoCivil) {
            this.estadoCivil = estadoCivil;
        }

        public String getSexo() {
            return sexo;
        }

        public void setSexo(String sexo) {
            this.sexo = sexo;
        }

        public Object getClaveElectorIFE() {
            return claveElectorIFE;
        }

        public void setClaveElectorIFE(Object claveElectorIFE) {
            this.claveElectorIFE = claveElectorIFE;
        }

        public Integer getNumeroDependientes() {
            return numeroDependientes;
        }

        public void setNumeroDependientes(Integer numeroDependientes) {
            this.numeroDependientes = numeroDependientes;
        }

        public Object getFechaDefuncion() {
            return fechaDefuncion;
        }

        public void setFechaDefuncion(Object fechaDefuncion) {
            this.fechaDefuncion = fechaDefuncion;
        }

    }
    public class Errore implements Serializable{

        @SerializedName("codigo")
        @Expose
        private String codigo;
        @SerializedName("mensaje")
        @Expose
        private String mensaje;

        public String getCodigo() {
            return codigo;
        }

        public void setCodigo(String codigo) {
            this.codigo = codigo;
        }

        public String getMensaje() {
            return mensaje;
        }

        public void setMensaje(String mensaje) {
            this.mensaje = mensaje;
        }

    }
    public class Error implements Serializable{

        @SerializedName("codigo")
        @Expose
        private String codigo = null;
        @SerializedName("mensaje")
        @Expose
        private String mensaje = null;

        public String getCodigo() {
            return codigo;
        }

        public void setCodigo(String codigo) {
            this.codigo = codigo;
        }

        public String getMensaje() {
            return mensaje;
        }

        public void setMensaje(String mensaje) {
            this.mensaje = mensaje;
        }
    }
    public class Credito implements Serializable{

        @SerializedName("can")
        @Expose
        private Object can;
        @SerializedName("fechaActualizacion")
        @Expose
        private String fechaActualizacion;
        @SerializedName("registroImpugnado")
        @Expose
        private Integer registroImpugnado;
        @SerializedName("claveOtorgante")
        @Expose
        private Object claveOtorgante;
        @SerializedName("nombreOtorgante")
        @Expose
        private String nombreOtorgante;
        @SerializedName("cuentaActual")
        @Expose
        private String cuentaActual;
        @SerializedName("tipoResponsabilidad")
        @Expose
        private String tipoResponsabilidad;
        @SerializedName("tipoCuenta")
        @Expose
        private String tipoCuenta;
        @SerializedName("tipoCredito")
        @Expose
        private String tipoCredito;
        @SerializedName("claveUnidadMonetaria")
        @Expose
        private String claveUnidadMonetaria;
        @SerializedName("valorActivoValuacion")
        @Expose
        private Integer valorActivoValuacion;
        @SerializedName("numeroPagos")
        @Expose
        private Integer numeroPagos;
        @SerializedName("frecuenciaPagos")
        @Expose
        private String frecuenciaPagos;
        @SerializedName("montoPagar")
        @Expose
        private Double montoPagar;
        @SerializedName("fechaAperturaCuenta")
        @Expose
        private String fechaAperturaCuenta;
        @SerializedName("fechaUltimoPago")
        @Expose
        private String fechaUltimoPago;
        @SerializedName("fechaUltimaCompra")
        @Expose
        private String fechaUltimaCompra;
        @SerializedName("fechaCierreCuenta")
        @Expose
        private Object fechaCierreCuenta;
        @SerializedName("fechaReporte")
        @Expose
        private String fechaReporte;
        @SerializedName("ultimaFechaSaldoCero")
        @Expose
        private Object ultimaFechaSaldoCero;
        @SerializedName("garantia")
        @Expose
        private Object garantia;
        @SerializedName("creditoMaximo")
        @Expose
        private Double creditoMaximo;
        @SerializedName("saldoActual")
        @Expose
        private Double saldoActual;
        @SerializedName("limiteCredito")
        @Expose
        private Double limiteCredito;
        @SerializedName("saldoVencido")
        @Expose
        private Double saldoVencido;
        @SerializedName("numeroPagosVencidos")
        @Expose
        private Integer numeroPagosVencidos;
        @SerializedName("pagoActual")
        @Expose
        private String pagoActual;
        @SerializedName("historicoPagos")
        @Expose
        private String historicoPagos;
        @SerializedName("fechaRecienteHistoricoPagos")
        @Expose
        private Object fechaRecienteHistoricoPagos;
        @SerializedName("fechaAntiguaHistoricoPagos")
        @Expose
        private Object fechaAntiguaHistoricoPagos;
        @SerializedName("clavePrevencion")
        @Expose
        private String clavePrevencion;
        @SerializedName("totalPagosReportados")
        @Expose
        private Integer totalPagosReportados;
        @SerializedName("peorAtraso")
        @Expose
        private Double peorAtraso;
        @SerializedName("fechaPeorAtraso")
        @Expose
        private String fechaPeorAtraso;
        @SerializedName("saldoVencidoPeorAtraso")
        @Expose
        private Double saldoVencidoPeorAtraso;
        @SerializedName("montoUltimoPago")
        @Expose
        private Object montoUltimoPago;
        @SerializedName("idDomicilio")
        @Expose
        private Object idDomicilio;
        @SerializedName("servicios")
        @Expose
        private Object servicios;
        @SerializedName("CAN")
        @Expose
        private Object cAN;

        public Object getCan() {
            return can;
        }

        public void setCan(Object can) {
            this.can = can;
        }

        public String getFechaActualizacion() {
            return fechaActualizacion;
        }

        public void setFechaActualizacion(String fechaActualizacion) {
            this.fechaActualizacion = fechaActualizacion;
        }

        public Integer getRegistroImpugnado() {
            return registroImpugnado;
        }

        public void setRegistroImpugnado(Integer registroImpugnado) {
            this.registroImpugnado = registroImpugnado;
        }

        public Object getClaveOtorgante() {
            return claveOtorgante;
        }

        public void setClaveOtorgante(Object claveOtorgante) {
            this.claveOtorgante = claveOtorgante;
        }

        public String getNombreOtorgante() {
            return nombreOtorgante;
        }

        public void setNombreOtorgante(String nombreOtorgante) {
            this.nombreOtorgante = nombreOtorgante;
        }

        public String getCuentaActual() {
            return cuentaActual;
        }

        public void setCuentaActual(String cuentaActual) {
            this.cuentaActual = cuentaActual;
        }

        public String getTipoResponsabilidad() {
            return tipoResponsabilidad;
        }

        public void setTipoResponsabilidad(String tipoResponsabilidad) {
            this.tipoResponsabilidad = tipoResponsabilidad;
        }

        public String getTipoCuenta() {
            return tipoCuenta;
        }

        public void setTipoCuenta(String tipoCuenta) {
            this.tipoCuenta = tipoCuenta;
        }

        public String getTipoCredito() {
            return tipoCredito;
        }

        public void setTipoCredito(String tipoCredito) {
            this.tipoCredito = tipoCredito;
        }

        public String getClaveUnidadMonetaria() {
            return claveUnidadMonetaria;
        }

        public void setClaveUnidadMonetaria(String claveUnidadMonetaria) {
            this.claveUnidadMonetaria = claveUnidadMonetaria;
        }

        public Integer getValorActivoValuacion() {
            return valorActivoValuacion;
        }

        public void setValorActivoValuacion(Integer valorActivoValuacion) {
            this.valorActivoValuacion = valorActivoValuacion;
        }

        public Integer getNumeroPagos() {
            return numeroPagos;
        }

        public void setNumeroPagos(Integer numeroPagos) {
            this.numeroPagos = numeroPagos;
        }

        public String getFrecuenciaPagos() {
            return frecuenciaPagos;
        }

        public void setFrecuenciaPagos(String frecuenciaPagos) {
            this.frecuenciaPagos = frecuenciaPagos;
        }

        public Double getMontoPagar() {
            return montoPagar;
        }

        public void setMontoPagar(Double montoPagar) {
            this.montoPagar = montoPagar;
        }

        public String getFechaAperturaCuenta() {
            return fechaAperturaCuenta;
        }

        public void setFechaAperturaCuenta(String fechaAperturaCuenta) {
            this.fechaAperturaCuenta = fechaAperturaCuenta;
        }

        public String getFechaUltimoPago() {
            return fechaUltimoPago;
        }

        public void setFechaUltimoPago(String fechaUltimoPago) {
            this.fechaUltimoPago = fechaUltimoPago;
        }

        public String getFechaUltimaCompra() {
            return fechaUltimaCompra;
        }

        public void setFechaUltimaCompra(String fechaUltimaCompra) {
            this.fechaUltimaCompra = fechaUltimaCompra;
        }

        public Object getFechaCierreCuenta() {
            return fechaCierreCuenta;
        }

        public void setFechaCierreCuenta(Object fechaCierreCuenta) {
            this.fechaCierreCuenta = fechaCierreCuenta;
        }

        public String getFechaReporte() {
            return fechaReporte;
        }

        public void setFechaReporte(String fechaReporte) {
            this.fechaReporte = fechaReporte;
        }

        public Object getUltimaFechaSaldoCero() {
            return ultimaFechaSaldoCero;
        }

        public void setUltimaFechaSaldoCero(Object ultimaFechaSaldoCero) {
            this.ultimaFechaSaldoCero = ultimaFechaSaldoCero;
        }

        public Object getGarantia() {
            return garantia;
        }

        public void setGarantia(Object garantia) {
            this.garantia = garantia;
        }

        public Double getCreditoMaximo() {
            return creditoMaximo;
        }

        public void setCreditoMaximo(Double creditoMaximo) {
            this.creditoMaximo = creditoMaximo;
        }

        public Double getSaldoActual() {
            return saldoActual;
        }

        public void setSaldoActual(Double saldoActual) {
            this.saldoActual = saldoActual;
        }

        public Double getLimiteCredito() {
            return limiteCredito;
        }

        public void setLimiteCredito(Double limiteCredito) {
            this.limiteCredito = limiteCredito;
        }

        public Double getSaldoVencido() {
            return saldoVencido;
        }

        public void setSaldoVencido(Double saldoVencido) {
            this.saldoVencido = saldoVencido;
        }

        public Integer getNumeroPagosVencidos() {
            return numeroPagosVencidos;
        }

        public void setNumeroPagosVencidos(Integer numeroPagosVencidos) {
            this.numeroPagosVencidos = numeroPagosVencidos;
        }

        public String getPagoActual() {
            return pagoActual;
        }

        public void setPagoActual(String pagoActual) {
            this.pagoActual = pagoActual;
        }

        public String getHistoricoPagos() {
            return historicoPagos;
        }

        public void setHistoricoPagos(String historicoPagos) {
            this.historicoPagos = historicoPagos;
        }

        public Object getFechaRecienteHistoricoPagos() {
            return fechaRecienteHistoricoPagos;
        }

        public void setFechaRecienteHistoricoPagos(Object fechaRecienteHistoricoPagos) {
            this.fechaRecienteHistoricoPagos = fechaRecienteHistoricoPagos;
        }

        public Object getFechaAntiguaHistoricoPagos() {
            return fechaAntiguaHistoricoPagos;
        }

        public void setFechaAntiguaHistoricoPagos(Object fechaAntiguaHistoricoPagos) {
            this.fechaAntiguaHistoricoPagos = fechaAntiguaHistoricoPagos;
        }

        public String getClavePrevencion() {
            return clavePrevencion;
        }

        public void setClavePrevencion(String clavePrevencion) {
            this.clavePrevencion = clavePrevencion;
        }

        public Integer getTotalPagosReportados() {
            return totalPagosReportados;
        }

        public void setTotalPagosReportados(Integer totalPagosReportados) {
            this.totalPagosReportados = totalPagosReportados;
        }

        public Double getPeorAtraso() {
            return peorAtraso;
        }

        public void setPeorAtraso(Double peorAtraso) {
            this.peorAtraso = peorAtraso;
        }

        public String getFechaPeorAtraso() {
            return fechaPeorAtraso;
        }

        public void setFechaPeorAtraso(String fechaPeorAtraso) {
            this.fechaPeorAtraso = fechaPeorAtraso;
        }

        public Double getSaldoVencidoPeorAtraso() {
            return saldoVencidoPeorAtraso;
        }

        public void setSaldoVencidoPeorAtraso(Double saldoVencidoPeorAtraso) {
            this.saldoVencidoPeorAtraso = saldoVencidoPeorAtraso;
        }

        public Object getMontoUltimoPago() {
            return montoUltimoPago;
        }

        public void setMontoUltimoPago(Object montoUltimoPago) {
            this.montoUltimoPago = montoUltimoPago;
        }

        public Object getIdDomicilio() {
            return idDomicilio;
        }

        public void setIdDomicilio(Object idDomicilio) {
            this.idDomicilio = idDomicilio;
        }

        public Object getServicios() {
            return servicios;
        }

        public void setServicios(Object servicios) {
            this.servicios = servicios;
        }

        public Object getcAN() {
            return cAN;
        }

        public void setcAN(Object cAN) {
            this.cAN = cAN;
        }
    }
}
