package com.sidert.sidertmovil.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class MRenovacionGrupal implements Serializable {

    @SerializedName("prestamo")
    @Expose
    private Prestamo prestamo;
    @SerializedName("integrantes")
    @Expose
    private List<Integrante> integrantes = null;

    public Prestamo getPrestamo() {
        return prestamo;
    }

    public void setPrestamo(Prestamo prestamo) {
        this.prestamo = prestamo;
    }

    public List<Integrante> getIntegrantes() {
        return integrantes;
    }

    public void setIntegrantes(List<Integrante> integrantes) {
        this.integrantes = integrantes;
    }

    public class Prestamo {

        @SerializedName("prestamod_id")
        @Expose
        private Integer prestamodId;
        @SerializedName("no_amortizaciones")
        @Expose
        private Integer noAmortizaciones;
        @SerializedName("periodo_dias")
        @Expose
        private Integer periodoDias;
        @SerializedName("fecha_entrega")
        @Expose
        private String fechaEntrega;
        @SerializedName("monto")
        @Expose
        private Double monto;
        @SerializedName("cliente_id")
        @Expose
        private Integer clienteId;
        @SerializedName("nivel_riesgo")
        @Expose
        private Integer nivelRiesgo;
        @SerializedName("num_ciclo")
        @Expose
        private Integer numCiclo;

        public Integer getPrestamodId() {
            return prestamodId;
        }

        public void setPrestamodId(Integer prestamodId) {
            this.prestamodId = prestamodId;
        }

        public Integer getNoAmortizaciones() {
            return noAmortizaciones;
        }

        public void setNoAmortizaciones(Integer noAmortizaciones) {
            this.noAmortizaciones = noAmortizaciones;
        }

        public Integer getPeriodoDias() {
            return periodoDias;
        }

        public void setPeriodoDias(Integer periodoDias) {
            this.periodoDias = periodoDias;
        }

        public String getFechaEntrega() {
            return fechaEntrega;
        }

        public void setFechaEntrega(String fechaEntrega) {
            this.fechaEntrega = fechaEntrega;
        }

        public Double getMonto() {
            return monto;
        }

        public void setMonto(Double monto) {
            this.monto = monto;
        }

        public Integer getClienteId() {
            return clienteId;
        }

        public void setClienteId(Integer clienteId) {
            this.clienteId = clienteId;
        }

        public Integer getNivelRiesgo() {
            return nivelRiesgo;
        }

        public void setNivelRiesgo(Integer nivelRiesgo) {
            this.nivelRiesgo = nivelRiesgo;
        }

        public Integer getNumCiclo() {
            return numCiclo;
        }

        public void setNumCiclo(Integer numCiclo) {
            this.numCiclo = numCiclo;
        }

    }

    public class Integrante {

        @SerializedName("cliente_id")
        @Expose
        private Long clienteId;
        @SerializedName("cliente")
        @Expose
        private Cliente cliente;
        @SerializedName("conyuge")
        @Expose
        private Conyuge conyuge;
        @SerializedName("negocio")
        @Expose
        private Negocio negocio;

        @SerializedName("ciclo")
        @Expose
        private Integer ciclo;

        @SerializedName("monto_prestamo_anterior")
        @Expose
        private String montoPrestamoAnterior;

        public String getMontoPrestamoAnterior() {
            return montoPrestamoAnterior;
        }

        public void setMontoPrestamoAnterior(String montoPrestamoAnterior) {
            this.montoPrestamoAnterior = montoPrestamoAnterior;
        }

        public Integer getCiclo() {
            return ciclo;
        }

        public void setCiclo(Integer ciclo) {
            this.ciclo = ciclo;
        }

        public Long getClienteId() {
            return clienteId;
        }

        public void setClienteId(Long clienteId) {
            this.clienteId = clienteId;
        }

        public Cliente getCliente() {
            return cliente;
        }

        public void setCliente(Cliente cliente) {
            this.cliente = cliente;
        }

        public Conyuge getConyuge() {
            return conyuge;
        }

        public void setConyuge(Conyuge conyuge) {
            this.conyuge = conyuge;
        }

        public Negocio getNegocio() {
            return negocio;
        }

        public void setNegocio(Negocio negocio) {
            this.negocio = negocio;
        }

    }

    public class Cliente {

        @SerializedName("nombre")
        @Expose
        private String nombre;
        @SerializedName("paterno")
        @Expose
        private String paterno;
        @SerializedName("materno")
        @Expose
        private String materno;
        @SerializedName("fecha_nacimiento")
        @Expose
        private String fechaNacimiento;
        @SerializedName("edad")
        @Expose
        private Integer edad;
        @SerializedName("genero")
        @Expose
        private Integer genero;
        @SerializedName("estado_nacimiento")
        @Expose
        private Integer estadoNacimiento;
        @SerializedName("rfc")
        @Expose
        private String rfc;
        @SerializedName("curp")
        @Expose
        private String curp;
        @SerializedName("ocupacion_id")
        @Expose
        private Integer ocupacionId;
        @SerializedName("sector_id")
        @Expose
        private Integer sectorId;
        @SerializedName("no_identificacion")
        @Expose
        private String noIdentificacion;
        @SerializedName("estudio_nivel_id")
        @Expose
        private Integer estudioNivelId;
        @SerializedName("estado_civil_id")
        @Expose
        private Integer estadoCivilId;
        @SerializedName("regimen_bien_id")
        @Expose
        private Integer regimenBienId;
        @SerializedName("vivienda_tipo_id")
        @Expose
        private Integer viviendaTipoId;
        @SerializedName("identificacion_tipo_id")
        @Expose
        private Integer identificacionTipoId;
        @SerializedName("parentesco_id")
        @Expose
        private Integer parentescoId;
        @SerializedName("otro_tipo_vivienda")
        @Expose
        private String otroTipoVivienda;
        @SerializedName("latitud")
        @Expose
        private String latitud;
        @SerializedName("longitud")
        @Expose
        private String longitud;
        @SerializedName("calle")
        @Expose
        private String calle;
        @SerializedName("no_exterior")
        @Expose
        private String noExterior;
        @SerializedName("no_interior")
        @Expose
        private String noInterior;
        @SerializedName("no_manzana")
        @Expose
        private String noManzana;
        @SerializedName("no_lote")
        @Expose
        private String noLote;
        @SerializedName("codigo_postal")
        @Expose
        private Integer codigoPostal;
        @SerializedName("colonia_id")
        @Expose
        private Integer coloniaId;
        @SerializedName("municipio_id")
        @Expose
        private Integer municipioId;
        @SerializedName("localidad_id")
        @Expose
        private Integer localidadId;
        @SerializedName("ciudad")
        @Expose
        private String ciudad;
        @SerializedName("estado_id")
        @Expose
        private Integer estadoId;
        @SerializedName("referencia")
        @Expose
        private String referencia;
        @SerializedName("tel_casa")
        @Expose
        private String telCasa;
        @SerializedName("tel_celular")
        @Expose
        private String telCelular;
        @SerializedName("tel_mensaje")
        @Expose
        private String telMensaje;
        @SerializedName("tel_trabajo")
        @Expose
        private Object telTrabajo;
        @SerializedName("tiempo_vivir_sitio")
        @Expose
        private String tiempoVivirSitio;
        @SerializedName("dependientes_economico")
        @Expose
        private Integer dependientesEconomico;
        @SerializedName("medio_contacto_id")
        @Expose
        private Integer medioContactoId;
        @SerializedName("estado_cuenta")
        @Expose
        private String estadoCuenta;
        @SerializedName("email")
        @Expose
        private String email;
        @SerializedName("tipo_integrante")
        @Expose
        private Integer tipoIntegrante;

        public String getNombre() {
            return nombre;
        }

        public void setNombre(String nombre) {
            this.nombre = nombre;
        }

        public String getPaterno() {
            return paterno;
        }

        public void setPaterno(String paterno) {
            this.paterno = paterno;
        }

        public String getMaterno() {
            return materno;
        }

        public void setMaterno(String materno) {
            this.materno = materno;
        }

        public String getFechaNacimiento() {
            return fechaNacimiento;
        }

        public void setFechaNacimiento(String fechaNacimiento) {
            this.fechaNacimiento = fechaNacimiento;
        }

        public Integer getEdad() {
            return edad;
        }

        public void setEdad(Integer edad) {
            this.edad = edad;
        }

        public Integer getGenero() {
            return genero;
        }

        public void setGenero(Integer genero) {
            this.genero = genero;
        }

        public Integer getEstadoNacimiento() {
            return estadoNacimiento;
        }

        public void setEstadoNacimiento(Integer estadoNacimiento) {
            this.estadoNacimiento = estadoNacimiento;
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

        public Integer getOcupacionId() {
            return ocupacionId;
        }

        public void setOcupacionId(Integer ocupacionId) {
            this.ocupacionId = ocupacionId;
        }

        public Integer getSectorId() {
            return sectorId;
        }

        public void setSectorId(Integer sectorId) {
            this.sectorId = sectorId;
        }

        public String getNoIdentificacion() {
            return noIdentificacion;
        }

        public void setNoIdentificacion(String noIdentificacion) {
            this.noIdentificacion = noIdentificacion;
        }

        public Integer getEstudioNivelId() {
            return estudioNivelId;
        }

        public void setEstudioNivelId(Integer estudioNivelId) {
            this.estudioNivelId = estudioNivelId;
        }

        public Integer getEstadoCivilId() {
            return estadoCivilId;
        }

        public void setEstadoCivilId(Integer estadoCivilId) {
            this.estadoCivilId = estadoCivilId;
        }

        public Integer getRegimenBienId() {
            return regimenBienId;
        }

        public void setRegimenBienId(Integer regimenBienId) {
            this.regimenBienId = regimenBienId;
        }

        public Integer getViviendaTipoId() {
            return viviendaTipoId;
        }

        public void setViviendaTipoId(Integer viviendaTipoId) {
            this.viviendaTipoId = viviendaTipoId;
        }

        public Integer getIdentificacionTipoId() {
            return identificacionTipoId;
        }

        public void setIdentificacionTipoId(Integer identificacionTipoId) {
            this.identificacionTipoId = identificacionTipoId;
        }

        public Integer getParentescoId() {
            return parentescoId;
        }

        public void setParentescoId(Integer parentescoId) {
            this.parentescoId = parentescoId;
        }

        public String getOtroTipoVivienda() {
            return otroTipoVivienda;
        }

        public void setOtroTipoVivienda(String otroTipoVivienda) {
            this.otroTipoVivienda = otroTipoVivienda;
        }

        public String getLatitud() {
            return latitud;
        }

        public void setLatitud(String latitud) {
            this.latitud = latitud;
        }

        public String getLongitud() {
            return longitud;
        }

        public void setLongitud(String longitud) {
            this.longitud = longitud;
        }

        public String getCalle() {
            return calle;
        }

        public void setCalle(String calle) {
            this.calle = calle;
        }

        public String getNoExterior() {
            return noExterior;
        }

        public void setNoExterior(String noExterior) {
            this.noExterior = noExterior;
        }

        public String getNoInterior() {
            return noInterior;
        }

        public void setNoInterior(String noInterior) {
            this.noInterior = noInterior;
        }

        public String getNoManzana() {
            return noManzana;
        }

        public void setNoManzana(String noManzana) {
            this.noManzana = noManzana;
        }

        public String getNoLote() {
            return noLote;
        }

        public void setNoLote(String noLote) {
            this.noLote = noLote;
        }

        public Integer getCodigoPostal() {
            return codigoPostal;
        }

        public void setCodigoPostal(Integer codigoPostal) {
            this.codigoPostal = codigoPostal;
        }

        public Integer getColoniaId() {
            return coloniaId;
        }

        public void setColoniaId(Integer coloniaId) {
            this.coloniaId = coloniaId;
        }

        public Integer getMunicipioId() {
            return municipioId;
        }

        public void setMunicipioId(Integer municipioId) {
            this.municipioId = municipioId;
        }

        public Integer getLocalidadId() {
            return localidadId;
        }

        public void setLocalidadId(Integer localidadId) {
            this.localidadId = localidadId;
        }

        public String getCiudad() {
            return ciudad;
        }

        public void setCiudad(String ciudad) {
            this.ciudad = ciudad;
        }

        public Integer getEstadoId() {
            return estadoId;
        }

        public void setEstadoId(Integer estadoId) {
            this.estadoId = estadoId;
        }

        public String getReferencia() {
            return referencia;
        }

        public void setReferencia(String referencia) {
            this.referencia = referencia;
        }

        public String getTelCasa() {
            return telCasa;
        }

        public void setTelCasa(String telCasa) {
            this.telCasa = telCasa;
        }

        public String getTelCelular() {
            return telCelular;
        }

        public void setTelCelular(String telCelular) {
            this.telCelular = telCelular;
        }

        public String getTelMensaje() {
            return telMensaje;
        }

        public void setTelMensaje(String telMensaje) {
            this.telMensaje = telMensaje;
        }

        public Object getTelTrabajo() {
            return telTrabajo;
        }

        public void setTelTrabajo(Object telTrabajo) {
            this.telTrabajo = telTrabajo;
        }

        public String getTiempoVivirSitio() {
            return tiempoVivirSitio;
        }

        public void setTiempoVivirSitio(String tiempoVivirSitio) {
            this.tiempoVivirSitio = tiempoVivirSitio;
        }

        public Integer getDependientesEconomico() {
            return dependientesEconomico;
        }

        public void setDependientesEconomico(Integer dependientesEconomico) {
            this.dependientesEconomico = dependientesEconomico;
        }

        public Integer getMedioContactoId() {
            return medioContactoId;
        }

        public void setMedioContactoId(Integer medioContactoId) {
            this.medioContactoId = medioContactoId;
        }

        public String getEstadoCuenta() {
            return estadoCuenta;
        }

        public void setEstadoCuenta(String estadoCuenta) {
            this.estadoCuenta = estadoCuenta;
        }

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public Integer getTipoIntegrante() {
            return tipoIntegrante;
        }

        public void setTipoIntegrante(Integer tipoIntegrante) {
            this.tipoIntegrante = tipoIntegrante;
        }

    }

    public class Conyuge {

        @SerializedName("nombre")
        @Expose
        private String nombre;
        @SerializedName("paterno")
        @Expose
        private String paterno;
        @SerializedName("materno")
        @Expose
        private String materno;
        @SerializedName("nacionalidad")
        @Expose
        private String nacionalidad;
        @SerializedName("ocupacion_id")
        @Expose
        private Integer ocupacionId;
        @SerializedName("latitud")
        @Expose
        private String latitud;
        @SerializedName("longitud")
        @Expose
        private String longitud;
        @SerializedName("calle")
        @Expose
        private String calle;
        @SerializedName("no_exterior")
        @Expose
        private String noExterior;
        @SerializedName("no_interior")
        @Expose
        private String noInterior;
        @SerializedName("no_manzana")
        @Expose
        private String noManzana;
        @SerializedName("no_lote")
        @Expose
        private String noLote;
        @SerializedName("codigo_postal")
        @Expose
        private Integer codigoPostal;
        @SerializedName("colonia_id")
        @Expose
        private Integer coloniaId;
        @SerializedName("municipio_id")
        @Expose
        private Integer municipioId;
        @SerializedName("localidad_id")
        @Expose
        private Integer localidadId;
        @SerializedName("ciudad")
        @Expose
        private String ciudad;
        @SerializedName("estado_id")
        @Expose
        private Integer estadoId;
        @SerializedName("referencia")
        @Expose
        private String referencia;
        @SerializedName("ingreso_mensual")
        @Expose
        private Double ingresoMensual;
        @SerializedName("gasto_mensual")
        @Expose
        private Double gastoMensual;
        @SerializedName("tel_casa")
        @Expose
        private String telCasa;
        @SerializedName("tel_celular")
        @Expose
        private String telCelular;

        public String getNombre() {
            return nombre;
        }

        public void setNombre(String nombre) {
            this.nombre = nombre;
        }

        public String getPaterno() {
            return paterno;
        }

        public void setPaterno(String paterno) {
            this.paterno = paterno;
        }

        public String getMaterno() {
            return materno;
        }

        public void setMaterno(String materno) {
            this.materno = materno;
        }

        public String getNacionalidad() {
            return nacionalidad;
        }

        public void setNacionalidad(String nacionalidad) {
            this.nacionalidad = nacionalidad;
        }

        public Integer getOcupacionId() {
            return ocupacionId;
        }

        public void setOcupacionId(Integer ocupacionId) {
            this.ocupacionId = ocupacionId;
        }

        public String getLatitud() {
            return latitud;
        }

        public void setLatitud(String latitud) {
            this.latitud = latitud;
        }

        public String getLongitud() {
            return longitud;
        }

        public void setLongitud(String longitud) {
            this.longitud = longitud;
        }

        public String getCalle() {
            return calle;
        }

        public void setCalle(String calle) {
            this.calle = calle;
        }

        public String getNoExterior() {
            return noExterior;
        }

        public void setNoExterior(String noExterior) {
            this.noExterior = noExterior;
        }

        public String getNoInterior() {
            return noInterior;
        }

        public void setNoInterior(String noInterior) {
            this.noInterior = noInterior;
        }

        public String getNoManzana() {
            return noManzana;
        }

        public void setNoManzana(String noManzana) {
            this.noManzana = noManzana;
        }

        public String getNoLote() {
            return noLote;
        }

        public void setNoLote(String noLote) {
            this.noLote = noLote;
        }

        public Integer getCodigoPostal() {
            return codigoPostal;
        }

        public void setCodigoPostal(Integer codigoPostal) {
            this.codigoPostal = codigoPostal;
        }

        public Integer getColoniaId() {
            return coloniaId;
        }

        public void setColoniaId(Integer coloniaId) {
            this.coloniaId = coloniaId;
        }

        public Integer getMunicipioId() {
            return municipioId;
        }

        public void setMunicipioId(Integer municipioId) {
            this.municipioId = municipioId;
        }

        public Integer getLocalidadId() {
            return localidadId;
        }

        public void setLocalidadId(Integer localidadId) {
            this.localidadId = localidadId;
        }

        public String getCiudad() {
            return ciudad;
        }

        public void setCiudad(String ciudad) {
            this.ciudad = ciudad;
        }

        public Integer getEstadoId() {
            return estadoId;
        }

        public void setEstadoId(Integer estadoId) {
            this.estadoId = estadoId;
        }

        public String getReferencia() {
            return referencia;
        }

        public void setReferencia(String referencia) {
            this.referencia = referencia;
        }

        public Double getIngresoMensual() {
            return ingresoMensual;
        }

        public void setIngresoMensual(Double ingresoMensual) {
            this.ingresoMensual = ingresoMensual;
        }

        public Double getGastoMensual() {
            return gastoMensual;
        }

        public void setGastoMensual(Double gastoMensual) {
            this.gastoMensual = gastoMensual;
        }

        public String getTelCasa() {
            return telCasa;
        }

        public void setTelCasa(String telCasa) {
            this.telCasa = telCasa;
        }

        public String getTelCelular() {
            return telCelular;
        }

        public void setTelCelular(String telCelular) {
            this.telCelular = telCelular;
        }

    }

    public class Negocio {

        @SerializedName("referencia")
        @Expose
        private String referencia;
        @SerializedName("nombre")
        @Expose
        private String nombre;
        @SerializedName("ocupacion_id")
        @Expose
        private Integer ocupacionId;
        @SerializedName("sector_id")
        @Expose
        private Integer sectorId;
        @SerializedName("destino_credito_id")
        @Expose
        private Integer destinoCreditoId;
        @SerializedName("otro_destino_credito")
        @Expose
        private String otroDestinoCredito;
        @SerializedName("antiguedad")
        @Expose
        private Integer antiguedad;
        @SerializedName("medios_pagos_ids")
        @Expose
        private String mediosPagosIds;
        @SerializedName("otro_medio_pago")
        @Expose
        private String otroMedioPago;
        @SerializedName("num_operaciones_mensuales")
        @Expose
        private Integer numOperacionesMensuales;
        @SerializedName("num_operaciones_mensuales_efectivo")
        @Expose
        private Integer numOperacionesMensualesEfectivo;
        @SerializedName("dias_venta")
        @Expose
        private String diasVenta;
        @SerializedName("latitud")
        @Expose
        private String latitud;
        @SerializedName("longitud")
        @Expose
        private String longitud;
        @SerializedName("calle")
        @Expose
        private String calle;
        @SerializedName("no_exterior")
        @Expose
        private String noExterior;
        @SerializedName("no_interior")
        @Expose
        private String noInterior;
        @SerializedName("no_manzana")
        @Expose
        private String noManzana;
        @SerializedName("no_lote")
        @Expose
        private String noLote;
        @SerializedName("codigo_postal")
        @Expose
        private Integer codigoPostal;
        @SerializedName("colonia_id")
        @Expose
        private Integer coloniaId;
        @SerializedName("municipio_id")
        @Expose
        private Integer municipioId;
        @SerializedName("localidad_id")
        @Expose
        private Integer localidadId;
        @SerializedName("ciudad")
        @Expose
        private String ciudad;
        @SerializedName("estado_id")
        @Expose
        private Integer estadoId;
        @SerializedName("ingreso_mensual")
        @Expose
        private Double ingresoMensual;
        @SerializedName("ingresos_otros")
        @Expose
        private Double ingresosOtros;
        @SerializedName("gasto_mensual")
        @Expose
        private Double gastoMensual;
        @SerializedName("gasto_agua")
        @Expose
        private Double gastoAgua;
        @SerializedName("gasto_luz")
        @Expose
        private Double gastoLuz;
        @SerializedName("gasto_telefono")
        @Expose
        private Double gastoTelefono;
        @SerializedName("gasto_renta")
        @Expose
        private Double gastoRenta;
        @SerializedName("gasto_otros")
        @Expose
        private Double gastoOtros;
        @SerializedName("capacidad_pago")
        @Expose
        private Double capacidadPago;
        @SerializedName("monto_maximo")
        @Expose
        private Double montoMaximo;

        public String getReferencia() {
            return referencia;
        }

        public void setReferencia(String referencia) {
            this.referencia = referencia;
        }

        public String getNombre() {
            return nombre;
        }

        public void setNombre(String nombre) {
            this.nombre = nombre;
        }

        public Integer getOcupacionId() {
            return ocupacionId;
        }

        public void setOcupacionId(Integer ocupacionId) {
            this.ocupacionId = ocupacionId;
        }

        public Integer getSectorId() {
            return sectorId;
        }

        public void setSectorId(Integer sectorId) {
            this.sectorId = sectorId;
        }

        public Integer getDestinoCreditoId() {
            return destinoCreditoId;
        }

        public void setDestinoCreditoId(Integer destinoCreditoId) {
            this.destinoCreditoId = destinoCreditoId;
        }

        public String getOtroDestinoCredito() {
            return otroDestinoCredito;
        }

        public void setOtroDestinoCredito(String otroDestinoCredito) {
            this.otroDestinoCredito = otroDestinoCredito;
        }

        public Integer getAntiguedad() {
            return antiguedad;
        }

        public void setAntiguedad(Integer antiguedad) {
            this.antiguedad = antiguedad;
        }

        public String getMediosPagosIds() {
            return mediosPagosIds;
        }

        public void setMediosPagosIds(String mediosPagosIds) {
            this.mediosPagosIds = mediosPagosIds;
        }

        public String getOtroMedioPago() {
            return otroMedioPago;
        }

        public void setOtroMedioPago(String otroMedioPago) {
            this.otroMedioPago = otroMedioPago;
        }

        public Integer getNumOperacionesMensuales() {
            return numOperacionesMensuales;
        }

        public void setNumOperacionesMensuales(Integer numOperacionesMensuales) {
            this.numOperacionesMensuales = numOperacionesMensuales;
        }

        public Integer getNumOperacionesMensualesEfectivo() {
            return numOperacionesMensualesEfectivo;
        }

        public void setNumOperacionesMensualesEfectivo(Integer numOperacionesMensualesEfectivo) {
            this.numOperacionesMensualesEfectivo = numOperacionesMensualesEfectivo;
        }

        public String getDiasVenta() {
            return diasVenta;
        }

        public void setDiasVenta(String diasVenta) {
            this.diasVenta = diasVenta;
        }

        public String getLatitud() {
            return latitud;
        }

        public void setLatitud(String latitud) {
            this.latitud = latitud;
        }

        public String getLongitud() {
            return longitud;
        }

        public void setLongitud(String longitud) {
            this.longitud = longitud;
        }

        public String getCalle() {
            return calle;
        }

        public void setCalle(String calle) {
            this.calle = calle;
        }

        public String getNoExterior() {
            return noExterior;
        }

        public void setNoExterior(String noExterior) {
            this.noExterior = noExterior;
        }

        public String getNoInterior() {
            return noInterior;
        }

        public void setNoInterior(String noInterior) {
            this.noInterior = noInterior;
        }

        public String getNoManzana() {
            return noManzana;
        }

        public void setNoManzana(String noManzana) {
            this.noManzana = noManzana;
        }

        public String getNoLote() {
            return noLote;
        }

        public void setNoLote(String noLote) {
            this.noLote = noLote;
        }

        public Integer getCodigoPostal() {
            return codigoPostal;
        }

        public void setCodigoPostal(Integer codigoPostal) {
            this.codigoPostal = codigoPostal;
        }

        public Integer getColoniaId() {
            return coloniaId;
        }

        public void setColoniaId(Integer coloniaId) {
            this.coloniaId = coloniaId;
        }

        public Integer getMunicipioId() {
            return municipioId;
        }

        public void setMunicipioId(Integer municipioId) {
            this.municipioId = municipioId;
        }

        public Integer getLocalidadId() {
            return localidadId;
        }

        public void setLocalidadId(Integer localidadId) {
            this.localidadId = localidadId;
        }

        public String getCiudad() {
            return ciudad;
        }

        public void setCiudad(String ciudad) {
            this.ciudad = ciudad;
        }

        public Integer getEstadoId() {
            return estadoId;
        }

        public void setEstadoId(Integer estadoId) {
            this.estadoId = estadoId;
        }

        public Double getIngresoMensual() {
            return ingresoMensual;
        }

        public void setIngresoMensual(Double ingresoMensual) {
            this.ingresoMensual = ingresoMensual;
        }

        public Double getIngresosOtros() {
            return ingresosOtros;
        }

        public void setIngresosOtros(Double ingresosOtros) {
            this.ingresosOtros = ingresosOtros;
        }

        public Double getGastoMensual() {
            return gastoMensual;
        }

        public void setGastoMensual(Double gastoMensual) {
            this.gastoMensual = gastoMensual;
        }

        public Double getGastoAgua() {
            return gastoAgua;
        }

        public void setGastoAgua(Double gastoAgua) {
            this.gastoAgua = gastoAgua;
        }

        public Double getGastoLuz() {
            return gastoLuz;
        }

        public void setGastoLuz(Double gastoLuz) {
            this.gastoLuz = gastoLuz;
        }

        public Double getGastoTelefono() {
            return gastoTelefono;
        }

        public void setGastoTelefono(Double gastoTelefono) {
            this.gastoTelefono = gastoTelefono;
        }

        public Double getGastoRenta() {
            return gastoRenta;
        }

        public void setGastoRenta(Double gastoRenta) {
            this.gastoRenta = gastoRenta;
        }

        public Double getGastoOtros() {
            return gastoOtros;
        }

        public void setGastoOtros(Double gastoOtros) {
            this.gastoOtros = gastoOtros;
        }

        public Double getCapacidadPago() {
            return capacidadPago;
        }

        public void setCapacidadPago(Double capacidadPago) {
            this.capacidadPago = capacidadPago;
        }

        public Double getMontoMaximo() {
            return montoMaximo;
        }

        public void setMontoMaximo(Double montoMaximo) {
            this.montoMaximo = montoMaximo;
        }

    }

}
