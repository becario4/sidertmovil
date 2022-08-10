package com.sidert.sidertmovil.models.solicitudes.solicitudind.renovacion;

import static com.sidert.sidertmovil.utils.Constants.ROOT_PATH;

import android.database.Cursor;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.sidert.sidertmovil.models.BaseModel;
import com.sidert.sidertmovil.models.IFillModel;

import java.io.File;
import java.io.Serializable;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

public class NegocioRen extends BaseModel implements Serializable, IFillModel {
    public final static String TBL                            = "tbl_negocio_ind_ren";
    protected static final String COL_ID_ECONOMICO            = "id_negocio";
    protected static final String COL_ID_SOLICITUD            = "id_solicitud";
    protected static final String COL_NOMBRE                  = "nombre";
    protected static final String COL_DIRECCION_ID            = "direccion_id";
    protected static final String COL_OCUPACION               = "ocupacion";
    protected static final String COL_ACTIVIDAD_ECONOMICA     = "actividad_economica";
    protected static final String COL_DESTINO_CREDITO         = "destino_credito";
    protected static final String COL_OTRO_DESTINO            = "otro_destino";
    protected static final String COL_ANTIGUEDAD              = "antiguedad";
    protected static final String COL_ING_MENSUAL             = "ing_mensual";
    protected static final String COL_ING_OTROS               = "ing_otros";
    protected static final String COL_GASTO_SEMANAL           = "gasto_semanal";
    protected static final String COL_GASTO_AGUA              = "gasto_agua";
    protected static final String COL_GASTO_LUZ               = "gasto_luz";
    protected static final String COL_GASTO_TELEFONO          = "gasto_telefono";
    protected static final String COL_GASTO_RENTA             = "gasto_renta";
    protected static final String COL_GASTO_OTROS             = "gasto_otros";
    protected static final String COL_CAPACIDAD_PAGO          = "capacidad_pago";
    protected static final String COL_MEDIO_PAGO              = "medio_pago";
    protected static final String COL_OTRO_MEDIO_PAGO         = "otro_medio_pago";
    protected static final String COL_MONTO_MAXIMO            = "monto_maximo";
    protected static final String COL_NUM_OPERACION_MENSUALES = "num_operacion_mensuales";
    protected static final String COL_NUM_OPERACION_EFECTIVO  = "num_operacion_efectivo";
    protected static final String COL_DIAS_VENTA              = "dias_venta";
    protected static final String COL_FOTO_FACHADA            = "foto_fachada";
    protected static final String COL_REF_DOMICILIARIA        = "ref_domiciliaria";
    protected static final String COL_ESTATUS_COMPLETADO      = "estatus_completado";
    protected static final String COL_COMENTARIO_RECHAZO      = "comentario_rechazo";
    protected static final String COL_UBICADO_EN_DIM_CLIE     = "ubicado_en_dom_cli";

    @SerializedName(COL_ID_ECONOMICO)
    @Expose
    private Integer idNegocio;

    @SerializedName(COL_ID_SOLICITUD)
    @Expose
    private Integer idSolicitud;

    @SerializedName(COL_NOMBRE)
    @Expose
    private String nombre;

    @SerializedName(COL_DIRECCION_ID)
    @Expose
    private String direccionId;

    @SerializedName(COL_OCUPACION)
    @Expose
    private String ocupacion;

    @SerializedName(COL_ACTIVIDAD_ECONOMICA)
    @Expose
    private String actividadEconomica;

    @SerializedName(COL_DESTINO_CREDITO)
    @Expose
    private String destinoCredito;

    @SerializedName(COL_OTRO_DESTINO)
    @Expose
    private String otroDestino;

    @SerializedName(COL_ANTIGUEDAD)
    @Expose
    private Integer antiguedad;

    @SerializedName(COL_ING_MENSUAL)
    @Expose
    private String ingMensual;

    @SerializedName(COL_ING_OTROS)
    @Expose
    private String ingOtros;

    @SerializedName(COL_GASTO_SEMANAL)
    @Expose
    private String gastoSemanal;

    @SerializedName(COL_GASTO_AGUA)
    @Expose
    private String gastoAgua;

    @SerializedName(COL_GASTO_LUZ)
    @Expose
    private String gastoLuz;

    @SerializedName(COL_GASTO_TELEFONO)
    @Expose
    private String gastoTelefono;

    @SerializedName(COL_GASTO_RENTA)
    @Expose
    private String gastoRenta;

    @SerializedName(COL_GASTO_OTROS)
    @Expose
    private String gastoOtros;

    @SerializedName(COL_CAPACIDAD_PAGO)
    @Expose
    private String capacidadPago;

    @SerializedName(COL_MEDIO_PAGO)
    @Expose
    private String medioPago;

    @SerializedName(COL_OTRO_MEDIO_PAGO)
    @Expose
    private String otroMedioPago;

    @SerializedName(COL_MONTO_MAXIMO)
    @Expose
    private String montoMaximo;

    @SerializedName(COL_NUM_OPERACION_MENSUALES)
    @Expose
    private Integer numOperacionMensuales;

    @SerializedName(COL_NUM_OPERACION_EFECTIVO)
    @Expose
    private Integer numOperacionEfectivo;

    @SerializedName(COL_DIAS_VENTA)
    @Expose
    private String diasVenta;

    @SerializedName(COL_FOTO_FACHADA)
    @Expose
    private String fotoFachada;

    @SerializedName(COL_REF_DOMICILIARIA)
    @Expose
    private String refDomiciliaria;

    @SerializedName(COL_ESTATUS_COMPLETADO)
    @Expose
    private Integer estatusCompletado;

    @SerializedName(COL_COMENTARIO_RECHAZO)
    @Expose
    private String comentarioRechazo;

    @SerializedName(COL_UBICADO_EN_DIM_CLIE)
    @Expose
    private String ubicadoEnDomCli;

    public Integer getIdNegocio() {
        return idNegocio;
    }

    public void setIdNegocio(Integer idNegocio) {
        this.idNegocio = idNegocio;
    }

    public Integer getIdSolicitud() {
        return idSolicitud;
    }

    public void setIdSolicitud(Integer idSolicitud) {
        this.idSolicitud = idSolicitud;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDireccionId() {
        return direccionId;
    }

    public void setDireccionId(String direccionId) {
        this.direccionId = direccionId;
    }

    public String getOcupacion() {
        return ocupacion;
    }

    public void setOcupacion(String ocupacion) {
        this.ocupacion = ocupacion;
    }

    public String getActividadEconomica() {
        return actividadEconomica;
    }

    public void setActividadEconomica(String actividadEconomica) {
        this.actividadEconomica = actividadEconomica;
    }

    public String getDestinoCredito() {
        return destinoCredito;
    }

    public void setDestinoCredito(String destinoCredito) {
        this.destinoCredito = destinoCredito;
    }

    public String getOtroDestino() {
        return otroDestino;
    }

    public void setOtroDestino(String otroDestino) {
        this.otroDestino = otroDestino;
    }

    public Integer getAntiguedad() {
        return antiguedad;
    }

    public void setAntiguedad(Integer antiguedad) {
        this.antiguedad = antiguedad;
    }

    public String getIngMensual() {
        return ingMensual;
    }

    public void setIngMensual(String ingMensual) {
        this.ingMensual = ingMensual;
    }

    public String getIngOtros() {
        return ingOtros;
    }

    public void setIngOtros(String ingOtros) {
        this.ingOtros = ingOtros;
    }

    public String getGastoSemanal() {
        return gastoSemanal;
    }

    public void setGastoSemanal(String gastoSemanal) {
        this.gastoSemanal = gastoSemanal;
    }

    public String getGastoAgua() {
        return gastoAgua;
    }

    public void setGastoAgua(String gastoAgua) {
        this.gastoAgua = gastoAgua;
    }

    public String getGastoLuz() {
        return gastoLuz;
    }

    public void setGastoLuz(String gastoLuz) {
        this.gastoLuz = gastoLuz;
    }

    public String getGastoTelefono() {
        return gastoTelefono;
    }

    public void setGastoTelefono(String gastoTelefono) {
        this.gastoTelefono = gastoTelefono;
    }

    public String getGastoRenta() {
        return gastoRenta;
    }

    public void setGastoRenta(String gastoRenta) {
        this.gastoRenta = gastoRenta;
    }

    public String getGastoOtros() {
        return gastoOtros;
    }

    public void setGastoOtros(String gastoOtros) {
        this.gastoOtros = gastoOtros;
    }

    public String getCapacidadPago() {
        return capacidadPago;
    }

    public void setCapacidadPago(String capacidadPago) {
        this.capacidadPago = capacidadPago;
    }

    public String getMedioPago() {
        return medioPago;
    }

    public void setMedioPago(String medioPago) {
        this.medioPago = medioPago;
    }

    public String getOtroMedioPago() {
        return otroMedioPago;
    }

    public void setOtroMedioPago(String otroMedioPago) {
        this.otroMedioPago = otroMedioPago;
    }

    public String getMontoMaximo() {
        return montoMaximo;
    }

    public void setMontoMaximo(String montoMaximo) {
        this.montoMaximo = montoMaximo;
    }

    public Integer getNumOperacionMensuales() {
        return numOperacionMensuales;
    }

    public void setNumOperacionMensuales(Integer numOperacionMensuales) {
        this.numOperacionMensuales = numOperacionMensuales;
    }

    public Integer getNumOperacionEfectivo() {
        return numOperacionEfectivo;
    }

    public void setNumOperacionEfectivo(Integer numOperacionEfectivo) {
        this.numOperacionEfectivo = numOperacionEfectivo;
    }

    public String getDiasVenta() {
        return diasVenta;
    }

    public void setDiasVenta(String diasVenta) {
        this.diasVenta = diasVenta;
    }

    public String getFotoFachada() {
        return fotoFachada;
    }

    public void setFotoFachada(String fotoFachada) {
        this.fotoFachada = fotoFachada;
    }

    public String getRefDomiciliaria() {
        return refDomiciliaria;
    }

    public void setRefDomiciliaria(String refDomiciliaria) {
        this.refDomiciliaria = refDomiciliaria;
    }

    public Integer getEstatusCompletado() {
        return estatusCompletado;
    }

    public void setEstatusCompletado(Integer estatusCompletado) {
        this.estatusCompletado = estatusCompletado;
    }

    public String getComentarioRechazo() {
        return comentarioRechazo;
    }

    public void setComentarioRechazo(String comentarioRechazo) {
        this.comentarioRechazo = comentarioRechazo;
    }

    public String getUbicadoEnDomCli() {
        return ubicadoEnDomCli;
    }

    public void setUbicadoEnDomCli(String ubicadoEnDomCli) {
        this.ubicadoEnDomCli = ubicadoEnDomCli;
    }

    public MultipartBody.Part getFachadaMBPart() {
        MultipartBody.Part fachada_negocio = null;
        File image_fachada_negocio = null;

        if(fotoFachada != null && !fotoFachada.equals("")) image_fachada_negocio = new File(ROOT_PATH + "Fachada/" + fotoFachada);

        if (image_fachada_negocio != null) {
            RequestBody imageBodyFachadaNeg = RequestBody.create(MediaType.parse("image/*"), image_fachada_negocio);
            fachada_negocio = MultipartBody.Part.createFormData("fachada_negocio", image_fachada_negocio.getName(), imageBodyFachadaNeg);
        }

        return fachada_negocio;
    }

    @Override
    public void fill(Cursor row) {
        this.row = row;

        idNegocio             = getInt(COL_ID_ECONOMICO);
        idSolicitud           = getInt(COL_ID_SOLICITUD);
        nombre                = getString(COL_NOMBRE);
        direccionId           = getString(COL_DIRECCION_ID);
        ocupacion             = getString(COL_OCUPACION);
        actividadEconomica    = getString(COL_ACTIVIDAD_ECONOMICA);
        destinoCredito        = getString(COL_DESTINO_CREDITO);
        otroDestino           = getString(COL_OTRO_DESTINO);
        antiguedad            = getInt(COL_ANTIGUEDAD);
        ingMensual            = getString(COL_ING_MENSUAL);
        ingOtros              = getString(COL_ING_OTROS);
        gastoSemanal          = getString(COL_GASTO_SEMANAL);
        gastoAgua             = getString(COL_GASTO_AGUA);
        gastoLuz              = getString(COL_GASTO_LUZ);
        gastoTelefono         = getString(COL_GASTO_TELEFONO);
        gastoRenta            = getString(COL_GASTO_RENTA);
        gastoOtros            = getString(COL_GASTO_OTROS);
        capacidadPago         = getString(COL_CAPACIDAD_PAGO);
        medioPago             = getString(COL_MEDIO_PAGO);
        otroMedioPago         = getString(COL_OTRO_MEDIO_PAGO);
        montoMaximo           = getString(COL_MONTO_MAXIMO);
        numOperacionMensuales = getInt(COL_NUM_OPERACION_MENSUALES);
        numOperacionEfectivo  = getInt(COL_NUM_OPERACION_EFECTIVO);
        diasVenta             = getString(COL_DIAS_VENTA);
        fotoFachada           = getString(COL_FOTO_FACHADA);
        refDomiciliaria       = getString(COL_REF_DOMICILIARIA);
        estatusCompletado     = getInt(COL_ESTATUS_COMPLETADO);
        comentarioRechazo     = getString(COL_COMENTARIO_RECHAZO);
        ubicadoEnDomCli       = getString(COL_UBICADO_EN_DIM_CLIE);
    }
}
