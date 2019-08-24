package com.sidert.sidertmovil.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class ModeloGrupal implements Serializable {

    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("type")
    @Expose
    private String type;
    @SerializedName("assignDate")
    @Expose
    private String assignDate;
    @SerializedName("expirationDate")
    @Expose
    private String expirationDate;
    @SerializedName("cancellationDate")
    @Expose
    private String cancellationDate;
    @SerializedName("Grupo")
    @Expose
    private Grupo grupo;
    @SerializedName("Tesorera")
    @Expose
    private Tesorera tesorera;
    @SerializedName("Presidenta")
    @Expose
    private Presidenta presidenta;
    @SerializedName("Secretaria")
    @Expose
    private Secretaria secretaria;
    @SerializedName("Prestamo")
    @Expose
    private Prestamo prestamo;
    @SerializedName("ReporteAnaliticoOmega")
    @Expose
    private List<ReporteAnaliticoOmega> reporteAnaliticoOmega = null;
    @SerializedName("TablaPagosGrupo")
    @Expose
    private List<TablaPagosGrupo> tablaPagosGrupo = null;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getAssignDate() {
        return assignDate;
    }

    public void setAssignDate(String assignDate) {
        this.assignDate = assignDate;
    }

    public String getExpirationDate() {
        return expirationDate;
    }

    public void setExpirationDate(String expirationDate) {
        this.expirationDate = expirationDate;
    }

    public String getCancellationDate() {
        return cancellationDate;
    }

    public void setCancellationDate(String cancellationDate) {
        this.cancellationDate = cancellationDate;
    }

    public Grupo getGrupo() {
        return grupo;
    }

    public void setGrupo(Grupo grupo) {
        this.grupo = grupo;
    }

    public Tesorera getTesorera() {
        return tesorera;
    }

    public void setTesorera(Tesorera tesorera) {
        this.tesorera = tesorera;
    }

    public Presidenta getPresidenta() {
        return presidenta;
    }

    public void setPresidenta(Presidenta presidenta) {
        this.presidenta = presidenta;
    }

    public Secretaria getSecretaria() {
        return secretaria;
    }

    public void setSecretaria(Secretaria secretaria) {
        this.secretaria = secretaria;
    }

    public Prestamo getPrestamo() {
        return prestamo;
    }

    public void setPrestamo(Prestamo prestamo) {
        this.prestamo = prestamo;
    }

    public List<ReporteAnaliticoOmega> getReporteAnaliticoOmega() {
        return reporteAnaliticoOmega;
    }

    public void setReporteAnaliticoOmega(List<ReporteAnaliticoOmega> reporteAnaliticoOmega) {
        this.reporteAnaliticoOmega = reporteAnaliticoOmega;
    }

    public List<TablaPagosGrupo> getTablaPagosGrupo() {
        return tablaPagosGrupo;
    }

    public void setTablaPagosGrupo(List<TablaPagosGrupo> tablaPagosGrupo) {
        this.tablaPagosGrupo = tablaPagosGrupo;
    }

    public static class Grupo implements Serializable {

        @SerializedName("ClaveGrupo")
        @Expose
        private String claveGrupo;
        @SerializedName("NombreGrupo")
        @Expose
        private String nombreGrupo;
        @SerializedName("IntegrantesDelGrupo")
        @Expose
        private List<IntegrantesDelGrupo> integrantesDelGrupo = null;
        @SerializedName("TotalIntegrantes")
        @Expose
        private Integer totalIntegrantes;

        public String getClaveGrupo() {
            return claveGrupo;
        }

        public void setClaveGrupo(String claveGrupo) {
            this.claveGrupo = claveGrupo;
        }

        public String getNombreGrupo() {
            return nombreGrupo;
        }

        public void setNombreGrupo(String nombreGrupo) {
            this.nombreGrupo = nombreGrupo;
        }

        public List<IntegrantesDelGrupo> getIntegrantesDelGrupo() {
            return integrantesDelGrupo;
        }

        public void setIntegrantesDelGrupo(List<IntegrantesDelGrupo> integrantesDelGrupo) {
            this.integrantesDelGrupo = integrantesDelGrupo;
        }

        public Integer getTotalIntegrantes() {
            return totalIntegrantes;
        }

        public void setTotalIntegrantes(Integer totalIntegrantes) {
            this.totalIntegrantes = totalIntegrantes;
        }

    }

    public static class Tesorera implements Serializable {

        @SerializedName("Nombre")
        @Expose
        private String nombre;
        @SerializedName("TelefonoCelular")
        @Expose
        private String telefonoCelular;
        @SerializedName("TelefonoDomicilio")
        @Expose
        private String telefonoDomicilio;
        @SerializedName("Direccion")
        @Expose
        private Direccion direccion;

        public String getNombre() {
            return nombre;
        }

        public void setNombre(String nombre) {
            this.nombre = nombre;
        }

        public String getTelefonoCelular() {
            return telefonoCelular;
        }

        public void setTelefonoCelular(String telefonoCelular) {
            this.telefonoCelular = telefonoCelular;
        }

        public String getTelefonoDomicilio() {
            return telefonoDomicilio;
        }

        public void setTelefonoDomicilio(String telefonoDomicilio) {
            this.telefonoDomicilio = telefonoDomicilio;
        }

        public Direccion getDireccion() {
            return direccion;
        }

        public void setDireccion(Direccion direccion) {
            this.direccion = direccion;
        }

    }

    public static class Presidenta implements Serializable {

        @SerializedName("NombrePresidenta")
        @Expose
        private String nombrePresidenta;
        @SerializedName("AddressPresidenta")
        @Expose
        private String addressPresidenta;
        @SerializedName("TelCelularPresidenta")
        @Expose
        private String telCelularPresidenta;
        @SerializedName("TelDomicilioPresidenta")
        @Expose
        private String telDomicilioPresidenta;

        public String getNombrePresidenta() {
            return nombrePresidenta;
        }

        public void setNombrePresidenta(String nombrePresidenta) {
            this.nombrePresidenta = nombrePresidenta;
        }

        public String getAddressPresidenta() {
            return addressPresidenta;
        }

        public void setAddressPresidenta(String addressPresidenta) {
            this.addressPresidenta = addressPresidenta;
        }

        public String getTelCelularPresidenta() {
            return telCelularPresidenta;
        }

        public void setTelCelularPresidenta(String telCelularPresidenta) {
            this.telCelularPresidenta = telCelularPresidenta;
        }

        public String getTelDomicilioPresidenta() {
            return telDomicilioPresidenta;
        }

        public void setTelDomicilioPresidenta(String telDomicilioPresidenta) {
            this.telDomicilioPresidenta = telDomicilioPresidenta;
        }

    }

    public static class Prestamo implements Serializable {

        @SerializedName("NumeroDePrestamo")
        @Expose
        private String numeroDePrestamo;
        @SerializedName("FechaDelCreditoOtorgado")
        @Expose
        private String fechaDelCreditoOtorgado;
        @SerializedName("FechaPagoEstablecida")
        @Expose
        private String fechaPagoEstablecida;
        @SerializedName("HoraPagoEstablecida")
        @Expose
        private String horaPagoEstablecida;
        @SerializedName("MontoPrestamo")
        @Expose
        private Double montoPrestamo;
        @SerializedName("MontoTotalPrestamo")
        @Expose
        private Double montoTotalPrestamo;
        @SerializedName("PagoSemanal")
        @Expose
        private Double pagoSemanal;
        @SerializedName("PagoRealizado")
        @Expose
        private Double pagoRealizado;
        @SerializedName("NumeroAmortizacion")
        @Expose
        private Integer numeroAmortizacion;
        @SerializedName("MontoAmortizacion")
        @Expose
        private Double montoAmortizacion;
        @SerializedName("SumaDePagos")
        @Expose
        private Double sumaDePagos;
        @SerializedName("SaldoActual")
        @Expose
        private Double saldoActual;
        @SerializedName("DiasAtraso")
        @Expose
        private Integer diasAtraso;
        @SerializedName("Frecuencia")
        @Expose
        private String frecuencia;
        @SerializedName("DiaSemana")
        @Expose
        private String diaSemana;

        public String getNumeroDePrestamo() {
            return numeroDePrestamo;
        }

        public void setNumeroDePrestamo(String numeroDePrestamo) {
            this.numeroDePrestamo = numeroDePrestamo;
        }

        public String getFechaDelCreditoOtorgado() {
            return fechaDelCreditoOtorgado;
        }

        public void setFechaDelCreditoOtorgado(String fechaDelCreditoOtorgado) {
            this.fechaDelCreditoOtorgado = fechaDelCreditoOtorgado;
        }

        public String getFechaPagoEstablecida() {
            return fechaPagoEstablecida;
        }

        public void setFechaPagoEstablecida(String fechaPagoEstablecida) {
            this.fechaPagoEstablecida = fechaPagoEstablecida;
        }

        public String getHoraPagoEstablecida() {
            return horaPagoEstablecida;
        }

        public void setHoraPagoEstablecida(String horaPagoEstablecida) {
            this.horaPagoEstablecida = horaPagoEstablecida;
        }

        public Double getMontoPrestamo() {
            return montoPrestamo;
        }

        public void setMontoPrestamo(Double montoPrestamo) {
            this.montoPrestamo = montoPrestamo;
        }

        public Double getMontoTotalPrestamo() {
            return montoTotalPrestamo;
        }

        public void setMontoTotalPrestamo(Double montoTotalPrestamo) {
            this.montoTotalPrestamo = montoTotalPrestamo;
        }

        public Double getPagoSemanal() {
            return pagoSemanal;
        }

        public void setPagoSemanal(Double pagoSemanal) {
            this.pagoSemanal = pagoSemanal;
        }

        public Double getPagoRealizado() {
            return pagoRealizado;
        }

        public void setPagoRealizado(Double pagoRealizado) {
            this.pagoRealizado = pagoRealizado;
        }

        public Integer getNumeroAmortizacion() {
            return numeroAmortizacion;
        }

        public void setNumeroAmortizacion(Integer numeroAmortizacion) {
            this.numeroAmortizacion = numeroAmortizacion;
        }

        public Double getMontoAmortizacion() {
            return montoAmortizacion;
        }

        public void setMontoAmortizacion(Double montoAmortizacion) {
            this.montoAmortizacion = montoAmortizacion;
        }

        public Double getSumaDePagos() {
            return sumaDePagos;
        }

        public void setSumaDePagos(Double sumaDePagos) {
            this.sumaDePagos = sumaDePagos;
        }

        public Double getSaldoActual() {
            return saldoActual;
        }

        public void setSaldoActual(Double saldoActual) {
            this.saldoActual = saldoActual;
        }

        public Integer getDiasAtraso() {
            return diasAtraso;
        }

        public void setDiasAtraso(Integer diasAtraso) {
            this.diasAtraso = diasAtraso;
        }

        public String getFrecuencia() {
            return frecuencia;
        }

        public void setFrecuencia(String frecuencia) {
            this.frecuencia = frecuencia;
        }

        public String getDiaSemana() {
            return diaSemana;
        }

        public void setDiaSemana(String diaSemana) {
            this.diaSemana = diaSemana;
        }

    }

    public static class Secretaria implements Serializable {

        @SerializedName("NombreSecretaria")
        @Expose
        private String nombreSecretaria;
        @SerializedName("AddressSercretaria")
        @Expose
        private String addressSercretaria;
        @SerializedName("TelCelularSecretaria")
        @Expose
        private String telCelularSecretaria;
        @SerializedName("TelDomicilioSecretaria")
        @Expose
        private String telDomicilioSecretaria;

        public String getNombreSecretaria() {
            return nombreSecretaria;
        }

        public void setNombreSecretaria(String nombreSecretaria) {
            this.nombreSecretaria = nombreSecretaria;
        }

        public String getAddressSercretaria() {
            return addressSercretaria;
        }

        public void setAddressSercretaria(String addressSercretaria) {
            this.addressSercretaria = addressSercretaria;
        }

        public String getTelCelularSecretaria() {
            return telCelularSecretaria;
        }

        public void setTelCelularSecretaria(String telCelularSecretaria) {
            this.telCelularSecretaria = telCelularSecretaria;
        }

        public String getTelDomicilioSecretaria() {
            return telDomicilioSecretaria;
        }

        public void setTelDomicilioSecretaria(String telDomicilioSecretaria) {
            this.telDomicilioSecretaria = telDomicilioSecretaria;
        }

    }

    public static class IntegrantesDelGrupo implements Serializable {

        @SerializedName("Nombre")
        @Expose
        private String nombre;
        @SerializedName("Monto")
        @Expose
        private Double monto;
        @SerializedName("PagoSemanalInt")
        @Expose
        private Double pagoSemanalInt;
        @SerializedName("PagoRealizado")
        @Expose
        private Double pagoRealizado;

        private Double pagoSolidario;
        private Double pagoAdelanto;

        private boolean isPagoCompleto;
        private boolean isGuardado;

        public String getNombre() {
            return nombre;
        }

        public void setNombre(String nombre) {
            this.nombre = nombre;
        }

        public Double getMonto() {
            return monto;
        }

        public void setMonto(Double monto) {
            this.monto = monto;
        }

        public Double getPagoSemanalInt() {
            return pagoSemanalInt;
        }

        public void setPagoSemanalInt(Double pagoSemanalInt) {
            this.pagoSemanalInt = pagoSemanalInt;
        }

        public Double getPagoRealizado() {
            return pagoRealizado;
        }

        public void setPagoRealizado(Double pagoRealizado) {
            this.pagoRealizado = pagoRealizado;
        }

        public Double getPagoSolidario() {
            return pagoSolidario;
        }

        public void setPagoSolidario(Double pagoSolidario) {
            this.pagoSolidario = pagoSolidario;
        }

        public Double getPagoAdelanto() {
            return pagoAdelanto;
        }

        public void setPagoAdelanto(Double pagoAdelanto) {
            this.pagoAdelanto = pagoAdelanto;
        }

        public boolean isPagoCompleto() {
            return isPagoCompleto;
        }

        public void setPagoCompleto(boolean pagoCompleto) {
            isPagoCompleto = pagoCompleto;
        }

        public boolean isGuardado() {
            return isGuardado;
        }

        public void setGuardado(boolean guardado) {
            isGuardado = guardado;
        }
    }

    public static class Direccion implements Serializable {

        @SerializedName("Address")
        @Expose
        private String address;
        @SerializedName("Calle")
        @Expose
        private String calle;
        @SerializedName("Ciudad")
        @Expose
        private String ciudad;
        @SerializedName("latitude")
        @Expose
        private String latitude;
        @SerializedName("longitude")
        @Expose
        private String longitude;
        @SerializedName("CodigoPostal")
        @Expose
        private String codigoPostal;
        @SerializedName("Colonia")
        @Expose
        private String colonia;
        @SerializedName("Municipio")
        @Expose
        private String municipio;
        @SerializedName("Estado")
        @Expose
        private String estado;

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
        }

        public String getCalle() {
            return calle;
        }

        public void setCalle(String calle) {
            this.calle = calle;
        }

        public String getCiudad() {
            return ciudad;
        }

        public void setCiudad(String ciudad) {
            this.ciudad = ciudad;
        }

        public String getLatitude() {
            return latitude;
        }

        public void setLatitude(String latitude) {
            this.latitude = latitude;
        }

        public String getLongitude() {
            return longitude;
        }

        public void setLongitude(String longitude) {
            this.longitude = longitude;
        }

        public String getCodigoPostal() {
            return codigoPostal;
        }

        public void setCodigoPostal(String codigoPostal) {
            this.codigoPostal = codigoPostal;
        }

        public String getColonia() {
            return colonia;
        }

        public void setColonia(String colonia) {
            this.colonia = colonia;
        }

        public String getMunicipio() {
            return municipio;
        }

        public void setMunicipio(String municipio) {
            this.municipio = municipio;
        }

        public String getEstado() {
            return estado;
        }

        public void setEstado(String estado) {
            this.estado = estado;
        }

    }

    public static class ReporteAnaliticoOmega implements Serializable {

        @SerializedName("No")
        @Expose
        private Integer no;
        @SerializedName("Fecha_Amortizacion")
        @Expose
        private String fechaAmortizacion;
        @SerializedName("Fecha_Pago")
        @Expose
        private String fechaPago;
        @SerializedName("Estatus")
        @Expose
        private String estatus;
        @SerializedName("Dias")
        @Expose
        private Integer dias;

        public Integer getNo() {
            return no;
        }

        public void setNo(Integer no) {
            this.no = no;
        }

        public String getFechaAmortizacion() {
            return fechaAmortizacion;
        }

        public void setFechaAmortizacion(String fechaAmortizacion) {
            this.fechaAmortizacion = fechaAmortizacion;
        }

        public String getFechaPago() {
            return fechaPago;
        }

        public void setFechaPago(String fechaPago) {
            this.fechaPago = fechaPago;
        }

        public String getEstatus() {
            return estatus;
        }

        public void setEstatus(String estatus) {
            this.estatus = estatus;
        }

        public Integer getDias() {
            return dias;
        }

        public void setDias(Integer dias) {
            this.dias = dias;
        }

    }

    public static class TablaPagosGrupo implements Serializable {

        @SerializedName("Fecha")
        @Expose
        private String fecha;
        @SerializedName("Pago")
        @Expose
        private Double pago;
        @SerializedName("Banco")
        @Expose
        private String banco;

        public String getFecha() {
            return fecha;
        }

        public void setFecha(String fecha) {
            this.fecha = fecha;
        }

        public Double getPago() {
            return pago;
        }

        public void setPago(Double pago) {
            this.pago = pago;
        }

        public String getBanco() {
            return banco;
        }

        public void setBanco(String banco) {
            this.banco = banco;
        }

    }

}
