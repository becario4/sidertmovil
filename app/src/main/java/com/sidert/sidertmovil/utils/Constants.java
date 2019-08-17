package com.sidert.sidertmovil.utils;

import android.os.Environment;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Constants {

    public final static boolean ENVIROMENT          = false;

    // ACTION BAR CONSTANTS
    public final static String TBtitle   = "title";
    public final static String TBhasBack = "hasBack";

    public final static String RECOVERY         = "Recuperacion";
    public final static String COLLECTION       = "Cobranza";
    public final static String WALLET_EXPIRED   = "CarteraVencida";

    public final static String ASESSOR          = "Asesor";
    public final static String MANNAGER           = "Gestor";

    public final static String ERROR            = "Error";

    public final static String FICHA            = "ficha";

    public final static String FORMAT_DATE_GNRAL = "yyyy-MM-dd";

    public final static String negative_payment         = "Negación de pago";
    public final static String outdate_information      = "Información Desfasada";
    public final static String death                    = "Fallecimiento";
    public final static String other                    = "Otro";

    public final static String bank                    = "Banco";
    public final static String oxxo                    = "Oxxo";
    public final static String cash                    = "Efectivo";
    public final static String sidert                  = "SIDERT";

    public final static String photo                    = "Fotografía";
    public final static String galery                  = "Galería";

    public final static String KEY                     = "key";
    public final static String VALUE                   = "value";

    public final static String PARAMS                   = "params";
    public final static String CONDITIONALS             = "conditionals";

    public final static String WHERE                    = "where";
    public final static String ORDER                    = "order";

    public final static String MESSAGE                  = "message";
    public final static String RESPONSE                 = "response";
    public final static String CODE                     = "code";

    public final static String uri_signature            = "uri_signature";

    public final static String type                     = "type";
    public final static String client_code              = "clave_cliente";

    //Iconos para colocar el dialog_message
    public final static String not_network              = "not_network";
    public final static String success                  = "success";
    public final static String logout                   = "logout";


    // Keys para servicios
    public final static String ADVISER_ID             = "asesorid";
    public final static String DATE                   = "fecha";
    public final static String ISSUE                  = "asunto";
    public final static String REASON                 = "motivo";

    //======================  TAGS DE FICHAS  ===================================================
    public final static String ORDER_ID = "id";
    public final static String TYPE = "type";
    public final static String ASSIGN_DATE = "assignDate";
    public final static String EXPIRATION_DATE = "expirationDate";
    public final static String CANCELLATION_DATE = "cancellationDate";
    public final static String CLIENTE = "Cliente";
    public final static String NUMERO_CLIENTE = "NumeroCliente";
    public final static String NOMBRE = "Nombre";
    public final static String TEL_CELULAR = "TelCelular";
    public final static String TEL_DOMICILIO = "TelDomicilio";
    public final static String DIRECCION = "Direccion";
    public final static String CALLE = "Calle";
    public final static String CIUDAD = "Ciudad";
    public final static String CODIGO_POSTAL = "CodigoPostal";
    public final static String COLONIA = "Colonia";
    public final static String MUNICIPIO = "Municipio";
    public final static String ESTADO = "Estado";
    public final static String LATITUDE = "latitude";
    public final static String LONGITUD = "longitude";
    public final static String AVAL = "Aval";
    public final static String NOMBRE_COMPLETO_AVAL = "NombreCompletoAval";
    public final static String TELEFONO_AVAL = "TelefonoAval";
    public final static String ADDRESS_AVAL = "AddressAval";
    public final static String PARENTESCO_AVAL= "ParentescoAval";
    public final static String PRESTAMO = "Prestamo";
    public final static String NUMERO_DE_PRESTAMO = "NumeroDePrestamo";
    public final static String FECHA_CREDITO_OTORGADO = "FechaDelCreditoOtorgado";
    public final static String MONTO_TOTAL_PRESTAMO = "MontoTotalPrestamo";
    public final static String MONTO_PRESTAMO = "MontoPrestamo";
    public final static String PAGO_SEMANAL = "PagoSemanal";
    public final static String PAGO_REALIZADO = "PagoRealizado";
    public final static String NUMERO_AMORTIZACION = "NumeroAmortizacion";
    public final static String MONTO_AMORTIZACION = "MontoAmortizacion";
    public final static String FECHA_PAGO_ESTABLECIDA = "FechaPagoEstablecida";
    public final static String HORA_PAGO_ESTABLECIDA = "HoraPagoEstablecida";
    public final static String SALDO_ACTUAL = "SaldoActual";
    public final static String SUMA_DE_PAGOS = "SumaDePagos";
    public final static String DIAS_ATRASO = "DiasAtraso";
    public final static String FRECUENCIA = "Frecuencia";
    public final static String DIA_SEMANA = "DiaSemana";
    public final static String PERTENECE_GARANTIA = "PerteneceGarantia";
    public final static String CUENTA_CON_GARANTIA = "CuentaConGarantia";
    public final static String TIPO_GARANTIA = "TipoGarantia";
    public final static String REPORTE_ANALITICO_OMEGA = "ReporteAnaliticoOmega";
    public final static String NO = "No";
    public final static String FECHA_AMORTIZACION = "FechaAmortizacion";
    public final static String FECHA_PAGO = "FechaPago";
    public final static String ESTATUS = "Estatus";
    public final static String DIAS = "Dias";
    public final static String TABLA_PAGOS_CLIENTE = "TablaPagosCliente";
    public final static String FECHA = "Fecha";
    public final static String PAGO = "Pago";
    public final static String BANCO = "Banco";

    //===========================   TIPOS FICHAS  ==================================================
    public final static String RECUPERACION_INDIVIDUAL = "RecuperacionIndividual";
    public final static String RECUPERACION_GRUPAL = "RecuperacionGpo";

    public final static int LIMIT_COMPLAINTS          = 2;

    public static final String PATH                   = Environment.getExternalStorageDirectory().getAbsolutePath();

    //=========================  Nombre de tablas  =================================================
    public final static String LOG_ASESSOR          = "log_impressions_asessor";
    public final static String LOG_MANAGER          = "log_impressions_manager";



}
