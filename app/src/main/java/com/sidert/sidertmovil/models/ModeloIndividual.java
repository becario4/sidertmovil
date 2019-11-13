package com.sidert.sidertmovil.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class ModeloIndividual implements Serializable {

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

    @SerializedName("Cliente")
    @Expose
    private Cliente cliente;

    @SerializedName("Aval")
    @Expose
    private Aval aval;

    @SerializedName("Prestamo")
    @Expose
    private Prestamo prestamo;

    @SerializedName("ReporteAnaliticoOmega")
    @Expose
    private List<ReporteAnaliticoOmega> reporteAnaliticoOmega = null;

    @SerializedName("TablaPagosCliente")
    @Expose
    private List<TablaPagosCliente> tablaPagosCliente = null;

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

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public Aval getAval() {
        return aval;
    }

    public void setAval(Aval aval) {
        this.aval = aval;
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

    public List<TablaPagosCliente> getTablaPagosCliente() {
        return tablaPagosCliente;
    }

    public void setTablaPagosCliente(List<TablaPagosCliente> tablaPagosCliente) {
        this.tablaPagosCliente = tablaPagosCliente;
    }

    public static class Cliente implements Serializable {

        @SerializedName("NumeroCliente")
        @Expose
        private String numeroCliente;
        @SerializedName("Nombre")
        @Expose
        private String nombre;
        @SerializedName("TelCelular")
        @Expose
        private String telCelular;
        @SerializedName("TelDomicilio")
        @Expose
        private String telDomicilio;
        @SerializedName("Direccion")
        @Expose
        private Direccion direccion;

        public String getNumeroCliente() {
            return numeroCliente;
        }

        public void setNumeroCliente(String numeroCliente) {
            this.numeroCliente = numeroCliente;
        }

        public String getNombre() {
            return nombre;
        }

        public void setNombre(String nombre) {
            this.nombre = nombre;
        }

        public String getTelCelular() {
            return telCelular;
        }

        public void setTelCelular(String telCelular) {
            this.telCelular = telCelular;
        }

        public String getTelDomicilio() {
            return telDomicilio;
        }

        public void setTelDomicilio(String telDomicilio) {
            this.telDomicilio = telDomicilio;
        }

        public Direccion getDireccion() {
            return direccion;
        }

        public void setDireccion(Direccion direccion) {
            this.direccion = direccion;
        }

    }

    public static class Direccion implements Serializable {

        @SerializedName("Calle")
        @Expose
        private String calle;
        @SerializedName("Ciudad")
        @Expose
        private String ciudad;
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
        @SerializedName("latitude")
        @Expose
        private String latitude;
        @SerializedName("longitude")
        @Expose
        private String longitude;

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

    }

    public static class Aval implements Serializable {

        @SerializedName("NombreCompletoAval")
        @Expose
        private String nombreCompletoAval;
        @SerializedName("TelefonoAval")
        @Expose
        private String telefonoAval;
        @SerializedName("AddressAval")
        @Expose
        private String addressAval;
        @SerializedName("ParentescoAval")
        @Expose
        private String parentescoAval;

        public String getNombreCompletoAval() {
            return nombreCompletoAval;
        }

        public void setNombreCompletoAval(String nombreCompletoAval) {
            this.nombreCompletoAval = nombreCompletoAval;
        }

        public String getTelefonoAval() {
            return telefonoAval;
        }

        public void setTelefonoAval(String telefonoAval) {
            this.telefonoAval = telefonoAval;
        }

        public String getAddressAval() {
            return addressAval;
        }

        public void setAddressAval(String addressAval) {
            this.addressAval = addressAval;
        }

        public String getParentescoAval() {
            return parentescoAval;
        }

        public void setParentescoAval(String parentescoAval) {
            this.parentescoAval = parentescoAval;
        }

    }

    public static class Prestamo implements Serializable {

        @SerializedName("NumeroDePrestamo")
        @Expose
        private String numeroDePrestamo;
        @SerializedName("FechaDelCreditoOtorgado")
        @Expose
        private String fechaDelCreditoOtorgado;
        @SerializedName("MontoTotalPrestamo")
        @Expose
        private Double montoTotalPrestamo;
        @SerializedName("MontoPrestamo")
        @Expose
        private Double montoPrestamo;
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
        @SerializedName("FechaPagoEstablecida")
        @Expose
        private String fechaPagoEstablecida;
        @SerializedName("HoraPagoEstablecida")
        @Expose
        private String horaPagoEstablecida;
        @SerializedName("SaldoActual")
        @Expose
        private Double saldoActual;
        @SerializedName("SumaDePagos")
        @Expose
        private Double sumaDePagos;
        @SerializedName("DiasAtraso")
        @Expose
        private Integer diasAtraso;
        @SerializedName("Frecuencia")
        @Expose
        private String frecuencia;
        @SerializedName("DiaSemana")
        @Expose
        private String diaSemana;
        @SerializedName("PerteneceGarantia")
        @Expose
        private String perteneceGarantia;
        @SerializedName("CuentaConGarantia")
        @Expose
        private String cuentaConGarantia;
        @SerializedName("TipoGarantia")
        @Expose
        private String tipoGarantia;

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

        public Double getMontoTotalPrestamo() {
            return montoTotalPrestamo;
        }

        public void setMontoTotalPrestamo(Double montoTotalPrestamo) {
            this.montoTotalPrestamo = montoTotalPrestamo;
        }

        public Double getMontoPrestamo() {
            return montoPrestamo;
        }

        public void setMontoPrestamo(Double montoPrestamo) {
            this.montoPrestamo = montoPrestamo;
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

        public Double getSaldoActual() {
            return saldoActual;
        }

        public void setSaldoActual(Double saldoActual) {
            this.saldoActual = saldoActual;
        }

        public Double getSumaDePagos() {
            return sumaDePagos;
        }

        public void setSumaDePagos(Double sumaDePagos) {
            this.sumaDePagos = sumaDePagos;
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

        public String getPerteneceGarantia() {
            return perteneceGarantia;
        }

        public void setPerteneceGarantia(String perteneceGarantia) {
            this.perteneceGarantia = perteneceGarantia;
        }

        public String getCuentaConGarantia() {
            return cuentaConGarantia;
        }

        public void setCuentaConGarantia(String cuentaConGarantia) {
            this.cuentaConGarantia = cuentaConGarantia;
        }

        public String getTipoGarantia() {
            return tipoGarantia;
        }

        public void setTipoGarantia(String tipoGarantia) {
            this.tipoGarantia = tipoGarantia;
        }

    }

    public static class ReporteAnaliticoOmega implements Serializable {

        @SerializedName("No")
        @Expose
        private Integer no;
        @SerializedName("FechaAmortizacion")
        @Expose
        private String fechaAmortizacion;
        @SerializedName("FechaPago")
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

    public static class TablaPagosCliente implements Serializable {

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
