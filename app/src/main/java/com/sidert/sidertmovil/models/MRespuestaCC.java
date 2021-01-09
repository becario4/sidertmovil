package com.sidert.sidertmovil.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class MRespuestaCC implements Serializable {

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
    private String declaracionesConsumidor;
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
    @SerializedName("errores")
    @Expose
    private List<Error> errores = null;

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

    public String getDeclaracionesConsumidor() {
        return declaracionesConsumidor;
    }

    public void setDeclaracionesConsumidor(String declaracionesConsumidor) {
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

    public List<Error> getErrores() {
        return errores;
    }

    public void setErrores(List<Error> errores) {
        this.errores = errores;
    }

    public class Persona implements Serializable {

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
        private String cURP;
        @SerializedName("numeroSeguridadSocial")
        @Expose
        private Object numeroSeguridadSocial;
        @SerializedName("nacionalidad")
        @Expose
        private String nacionalidad;
        @SerializedName("residencia")
        @Expose
        private Integer residencia;
        @SerializedName("estadoCivil")
        @Expose
        private Object estadoCivil;
        @SerializedName("sexo")
        @Expose
        private Object sexo;
        @SerializedName("claveElectorIFE")
        @Expose
        private Object claveElectorIFE;
        @SerializedName("numeroDependientes")
        @Expose
        private Integer numeroDependientes;
        @SerializedName("fechaDefuncion")
        @Expose
        private Object fechaDefuncion;

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

        public String getCURP() {
            return cURP;
        }

        public void setCURP(String cURP) {
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

        public Integer getResidencia() {
            return residencia;
        }

        public void setResidencia(Integer residencia) {
            this.residencia = residencia;
        }

        public Object getEstadoCivil() {
            return estadoCivil;
        }

        public void setEstadoCivil(Object estadoCivil) {
            this.estadoCivil = estadoCivil;
        }

        public Object getSexo() {
            return sexo;
        }

        public void setSexo(Object sexo) {
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

    public class Empleo implements Serializable{

        @SerializedName("nombreEmpresa")
        @Expose
        private String nombreEmpresa;
        @SerializedName("direccion")
        @Expose
        private Object direccion;
        @SerializedName("coloniaPoblacion")
        @Expose
        private Object coloniaPoblacion;
        @SerializedName("delegacionMunicipio")
        @Expose
        private Object delegacionMunicipio;
        @SerializedName("ciudad")
        @Expose
        private Object ciudad;
        @SerializedName("estado")
        @Expose
        private Object estado;
        @SerializedName("CP")
        @Expose
        private String cP;
        @SerializedName("numeroTelefono")
        @Expose
        private Object numeroTelefono;
        @SerializedName("extension")
        @Expose
        private String extension;
        @SerializedName("fax")
        @Expose
        private Object fax;
        @SerializedName("puesto")
        @Expose
        private Object puesto;
        @SerializedName("fechaContratacion")
        @Expose
        private Object fechaContratacion;
        @SerializedName("claveMoneda")
        @Expose
        private String claveMoneda;
        @SerializedName("salarioMensual")
        @Expose
        private Integer salarioMensual;
        @SerializedName("fechaUltimoDiaEmpleo")
        @Expose
        private String fechaUltimoDiaEmpleo;
        @SerializedName("fechaVerificacionEmpleo")
        @Expose
        private Object fechaVerificacionEmpleo;

        public String getNombreEmpresa() {
            return nombreEmpresa;
        }

        public void setNombreEmpresa(String nombreEmpresa) {
            this.nombreEmpresa = nombreEmpresa;
        }

        public Object getDireccion() {
            return direccion;
        }

        public void setDireccion(Object direccion) {
            this.direccion = direccion;
        }

        public Object getColoniaPoblacion() {
            return coloniaPoblacion;
        }

        public void setColoniaPoblacion(Object coloniaPoblacion) {
            this.coloniaPoblacion = coloniaPoblacion;
        }

        public Object getDelegacionMunicipio() {
            return delegacionMunicipio;
        }

        public void setDelegacionMunicipio(Object delegacionMunicipio) {
            this.delegacionMunicipio = delegacionMunicipio;
        }

        public Object getCiudad() {
            return ciudad;
        }

        public void setCiudad(Object ciudad) {
            this.ciudad = ciudad;
        }

        public Object getEstado() {
            return estado;
        }

        public void setEstado(Object estado) {
            this.estado = estado;
        }

        public String getCP() {
            return cP;
        }

        public void setCP(String cP) {
            this.cP = cP;
        }

        public Object getNumeroTelefono() {
            return numeroTelefono;
        }

        public void setNumeroTelefono(Object numeroTelefono) {
            this.numeroTelefono = numeroTelefono;
        }

        public String getExtension() {
            return extension;
        }

        public void setExtension(String extension) {
            this.extension = extension;
        }

        public Object getFax() {
            return fax;
        }

        public void setFax(Object fax) {
            this.fax = fax;
        }

        public Object getPuesto() {
            return puesto;
        }

        public void setPuesto(Object puesto) {
            this.puesto = puesto;
        }

        public Object getFechaContratacion() {
            return fechaContratacion;
        }

        public void setFechaContratacion(Object fechaContratacion) {
            this.fechaContratacion = fechaContratacion;
        }

        public String getClaveMoneda() {
            return claveMoneda;
        }

        public void setClaveMoneda(String claveMoneda) {
            this.claveMoneda = claveMoneda;
        }

        public Integer getSalarioMensual() {
            return salarioMensual;
        }

        public void setSalarioMensual(Integer salarioMensual) {
            this.salarioMensual = salarioMensual;
        }

        public String getFechaUltimoDiaEmpleo() {
            return fechaUltimoDiaEmpleo;
        }

        public void setFechaUltimoDiaEmpleo(String fechaUltimoDiaEmpleo) {
            this.fechaUltimoDiaEmpleo = fechaUltimoDiaEmpleo;
        }

        public Object getFechaVerificacionEmpleo() {
            return fechaVerificacionEmpleo;
        }

        public void setFechaVerificacionEmpleo(Object fechaVerificacionEmpleo) {
            this.fechaVerificacionEmpleo = fechaVerificacionEmpleo;
        }

    }

    public class Domicilio implements Serializable{

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
        private String tipoAsentamiento;
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

        public String getTipoAsentamiento() {
            return tipoAsentamiento;
        }

        public void setTipoAsentamiento(String tipoAsentamiento) {
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

    }

    public class Consulta implements Serializable{

        @SerializedName("fechaConsulta")
        @Expose
        private String fechaConsulta;
        @SerializedName("claveOtorgante")
        @Expose
        private Object claveOtorgante;
        @SerializedName("nombreOtorgante")
        @Expose
        private String nombreOtorgante;
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
        private Integer importeCredito;
        @SerializedName("tipoResponsabilidad")
        @Expose
        private Object tipoResponsabilidad;

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

        public Integer getImporteCredito() {
            return importeCredito;
        }

        public void setImporteCredito(Integer importeCredito) {
            this.importeCredito = importeCredito;
        }

        public Object getTipoResponsabilidad() {
            return tipoResponsabilidad;
        }

        public void setTipoResponsabilidad(Object tipoResponsabilidad) {
            this.tipoResponsabilidad = tipoResponsabilidad;
        }

    }

    public class Credito implements Serializable{

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
        private Object cuentaActual;
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
        private Object valorActivoValuacion;
        @SerializedName("numeroPagos")
        @Expose
        private Integer numeroPagos;
        @SerializedName("frecuenciaPagos")
        @Expose
        private String frecuenciaPagos;
        @SerializedName("montoPagar")
        @Expose
        private Integer montoPagar;
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
        private Integer creditoMaximo;
        @SerializedName("saldoActual")
        @Expose
        private Integer saldoActual;
        @SerializedName("limiteCredito")
        @Expose
        private Integer limiteCredito;
        @SerializedName("saldoVencido")
        @Expose
        private Integer saldoVencido;
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
        private Object clavePrevencion;
        @SerializedName("totalPagosReportados")
        @Expose
        private Object totalPagosReportados;
        @SerializedName("peorAtraso")
        @Expose
        private Object peorAtraso;
        @SerializedName("fechaPeorAtraso")
        @Expose
        private Object fechaPeorAtraso;
        @SerializedName("saldoVencidoPeorAtraso")
        @Expose
        private Integer saldoVencidoPeorAtraso;
        @SerializedName("montoUltimoPago")
        @Expose
        private Object montoUltimoPago;
        @SerializedName("CAN")
        @Expose
        private Object cAN;

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

        public Object getCuentaActual() {
            return cuentaActual;
        }

        public void setCuentaActual(Object cuentaActual) {
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

        public Object getValorActivoValuacion() {
            return valorActivoValuacion;
        }

        public void setValorActivoValuacion(Object valorActivoValuacion) {
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

        public Integer getMontoPagar() {
            return montoPagar;
        }

        public void setMontoPagar(Integer montoPagar) {
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

        public Integer getCreditoMaximo() {
            return creditoMaximo;
        }

        public void setCreditoMaximo(Integer creditoMaximo) {
            this.creditoMaximo = creditoMaximo;
        }

        public Integer getSaldoActual() {
            return saldoActual;
        }

        public void setSaldoActual(Integer saldoActual) {
            this.saldoActual = saldoActual;
        }

        public Integer getLimiteCredito() {
            return limiteCredito;
        }

        public void setLimiteCredito(Integer limiteCredito) {
            this.limiteCredito = limiteCredito;
        }

        public Integer getSaldoVencido() {
            return saldoVencido;
        }

        public void setSaldoVencido(Integer saldoVencido) {
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

        public Object getClavePrevencion() {
            return clavePrevencion;
        }

        public void setClavePrevencion(Object clavePrevencion) {
            this.clavePrevencion = clavePrevencion;
        }

        public Object getTotalPagosReportados() {
            return totalPagosReportados;
        }

        public void setTotalPagosReportados(Object totalPagosReportados) {
            this.totalPagosReportados = totalPagosReportados;
        }

        public Object getPeorAtraso() {
            return peorAtraso;
        }

        public void setPeorAtraso(Object peorAtraso) {
            this.peorAtraso = peorAtraso;
        }

        public Object getFechaPeorAtraso() {
            return fechaPeorAtraso;
        }

        public void setFechaPeorAtraso(Object fechaPeorAtraso) {
            this.fechaPeorAtraso = fechaPeorAtraso;
        }

        public Integer getSaldoVencidoPeorAtraso() {
            return saldoVencidoPeorAtraso;
        }

        public void setSaldoVencidoPeorAtraso(Integer saldoVencidoPeorAtraso) {
            this.saldoVencidoPeorAtraso = saldoVencidoPeorAtraso;
        }

        public Object getMontoUltimoPago() {
            return montoUltimoPago;
        }

        public void setMontoUltimoPago(Object montoUltimoPago) {
            this.montoUltimoPago = montoUltimoPago;
        }

        public Object getCAN() {
            return cAN;
        }

        public void setCAN(Object cAN) {
            this.cAN = cAN;
        }

    }

    public class Error implements Serializable {

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

}
