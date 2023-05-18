package com.sidert.sidertmovil.fragments;

import static com.sidert.sidertmovil.utils.Constants.CONTROLLER_MOVIL;
import static com.sidert.sidertmovil.utils.Constants.CONTROLLER_SOPORTE;
import static com.sidert.sidertmovil.utils.Constants.SUCURSALES;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import android.se.omapi.Session;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.JsonArray;
import com.sidert.sidertmovil.Home;
import com.sidert.sidertmovil.R;
import com.sidert.sidertmovil.utils.RetrofitClient;
import com.sidert.sidertmovil.utils.funCalculadora;
import com.sidert.sidertmovil.database.DBhelper;
import com.sidert.sidertmovil.models.Calculadora;
import com.sidert.sidertmovil.models.MSucursal;
import com.sidert.sidertmovil.services.calculadoraApi.CalculadoraApiService;
import com.sidert.sidertmovil.utils.Constants;
import com.sidert.sidertmovil.utils.NetworkStatus;
import com.sidert.sidertmovil.utils.Popups;
import com.sidert.sidertmovil.utils.SessionManager;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class calculadoraPrestamo extends Fragment {
    private Context ctx;
    private Home boostrap;
    private funCalculadora caf = new funCalculadora();
    private SessionManager session;
    private String[] sucursales;
    private DBhelper dBhelper;
    private SQLiteDatabase db;

    //Tipo de cliente y prestamo
    public String[] _clienteCalcu;
    public String[] _prestamoCalcu;
    //Nuevos Individuales y Grupales
    public String[] _periodoIndCalcu;
    public String[] _plazoIndCalcu;
    public String[] _periodoGpoCalcu;
    public String[] _plazoGpoCalcu;
    //Renovados Individuales y grupales
    public String[] _periodoRenovados;
    public String[] _plazoSemanal;
    public String[] _plazoCatorcenal;
    public String[] _plazoQuincenal;
    public String[] _plazoMensual;


    public TextView tipoClienteCalcu;
    public TextView tipoPrestamoCalcu;
    public EditText montoPrestamoCalcu;

    //Opciones Nuevos: Individuales y grupales
    public TextView tipoPeriodoIndCalcu;
    public TextView tipoPlazoIndCalcu;
    public TextView tipoPeriodoGpoCalcu;
    public TextView tipoPlazoGpoCalcu;

    //Opciones Renovados: Individuales - grupales
    public TextView tipoPeriodoRenovadosA;
    public TextView plazoSemanalRenovados;
    public TextView plazosCatorcenalRenovados;
    public TextView plazosQuincenalRenovados;
    public TextView plazosMensualRenovados;

    //Resultados
    public TextView suucursalCosto;
    public TextView periodoPago;
    public TextView resultadoPrestamo;
    public Button calcularPrestamosSimulado;


    //Linear layout Nuevos - Individuales y grupales
    private LinearLayout llPeriodoClienteNuevoInd;
    private LinearLayout llPeriodoNuevoGrupo;
    private LinearLayout llClientePlazoInd;
    private LinearLayout llPlazoNuevoGrupo;

    private LinearLayout llPeriodoClientesRenovados;
    private LinearLayout llPlazoSemanalRenovado;
    private LinearLayout llPlazoCatorcenalRenovado;
    private LinearLayout llPlazoQuincenalRenovado;
    private LinearLayout llPlazoMensualRenovado;


    //Resultados
    private LinearLayout llMontoPrestamo;
    private LinearLayout llBotonCalcularPrestamo;
    private LinearLayout llResultadosCalculo;



    Calculadora calcu = new Calculadora();
    private Object List;
    ProgressDialog progressDialog;

    //AGREGAR UN MIN 1000 Y MAX 1,000,000  DE LAS CANTIDADES
    //AGREGAR QUE LOS MONTOS SEAN ENTEROS

    @SuppressLint("ResourceType")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_calculadora_prestamo, container, false);
        ctx     = getContext();

        boostrap    = (Home)getActivity() ;
        boostrap.setTitle("Calculadora Prestamos");
        boostrap.invalidateOptionsMenu();

        session = new SessionManager(ctx);

        //getSucursalIdA();

        //Casteo de los arreglos
        _clienteCalcu = getResources().getStringArray(R.array.tipo_cliente);
        _prestamoCalcu = getResources().getStringArray(R.array.tipo_clientePrestamo);
        //Casteo arrelgos Nuevo - Individuales y grupales
        _periodoIndCalcu = getResources().getStringArray(R.array.tipo_periodicidad_nuevo);
        _plazoIndCalcu = getResources().getStringArray(R.array.plazo_individual_nuevo);
        _periodoGpoCalcu = getResources().getStringArray(R.array.tipo_periodicidad_nuevo);
        _plazoGpoCalcu = getResources().getStringArray(R.array.tipo_plazoGrup_nuevo);

        //Casteo arreglos renovados - individuales y arreglos
        _periodoRenovados = getResources().getStringArray(R.array.periodicidad_cliente_renovado);
        _plazoSemanal = getResources().getStringArray(R.array.plazo_cliente_renovado_semanal);
        _plazoCatorcenal = getResources().getStringArray(R.array.plazo_cliente_renovado_catorcenal);
        _plazoQuincenal = getResources().getStringArray(R.array.plazo_cliente_renovado_quincenal);
        _plazoMensual = getResources().getStringArray(R.array.plazo_cliente_renovado_mensual);

        //Casteo de los textView
        tipoClienteCalcu = v.findViewById(R.id.txtOpcionCliente2);
        tipoPrestamoCalcu = v.findViewById(R.id.txtOpcionPrestamo2);
        montoPrestamoCalcu = v.findViewById(R.id.txtMontoPrestamo2);

        //Casteo de los textView clientes nuevos - Individuales y grupales
        suucursalCosto = v.findViewById(R.id.txtSucursalCosto);
        SessionManager sen = new SessionManager(ctx);
        try {
            getSucursalA(sen);
        } catch (JSONException e) {
            e.printStackTrace();
        }


        //suucursalCosto.setText(sen.getSucursales().toString());
        //MSucursal m = new MSucursal();
         //String datos = getSucursalIdA((List<MSucursal>) m);
        //suucursalCosto.setText(datos);


        tipoPeriodoIndCalcu = v.findViewById(R.id.txtOpcionPeriodo);
        tipoPlazoIndCalcu = v.findViewById(R.id.txtOpcionPlazo2);
        tipoPeriodoGpoCalcu = v.findViewById(R.id.txtOpcionPeriodoGrupoNew);
        tipoPlazoGpoCalcu = v.findViewById(R.id.txtOpcionPlazoNewGrupo);

        //Casteo de los textView clientes renovados individuales y grupales
        tipoPeriodoRenovadosA = v.findViewById(R.id.txtOpcionPeriodoRenovado);
        plazoSemanalRenovados = v.findViewById(R.id.txtOpcionPlazoRenovadoSemanal);
        plazosCatorcenalRenovados = v.findViewById(R.id.txtOpcionPlazoCatorcenalRenovado);
        plazosQuincenalRenovados = v.findViewById(R.id.txtOpcionPlazoQuincenalRenovado);
        plazosMensualRenovados = v.findViewById(R.id.txtOpcionPlazoMensualRenovados);

        //Casteo de los textViews resultados
        periodoPago = v.findViewById(R.id.txtPeriodoPago);
        resultadoPrestamo = v.findViewById(R.id.txtTotalPago);
        calcularPrestamosSimulado = v.findViewById(R.id.btnCalcular);
        suucursalCosto = v.findViewById(R.id.txtSucursalCosto);

        llMontoPrestamo = v.findViewById(R.id.llMontoPrestamo2);
        //Casteo de los layout clientes nuevos - individuales y grupales
        llPeriodoClienteNuevoInd = v.findViewById(R.id.llPeriodoNuevoInd2);
        llClientePlazoInd = v.findViewById(R.id.llClienteNuevoPlazoInd);
        llPeriodoNuevoGrupo = v.findViewById(R.id.llPeriodoNuevoGrupo);
        llPlazoNuevoGrupo = v.findViewById(R.id.llPlazoNuevoGrupo);

        //Casteo de los layout clientes renovados - individuales y grupales
        llPeriodoClientesRenovados = v.findViewById(R.id.llPeriodoRenovado);
        llPlazoSemanalRenovado = v.findViewById(R.id.llPlazoSemanalRenovado);
        llPlazoCatorcenalRenovado = v.findViewById(R.id.llPlazoCatorcenalRenovado);
        llPlazoQuincenalRenovado = v.findViewById(R.id.llPlazoQuincenalRenovado);
        llPlazoMensualRenovado = v.findViewById(R.id.llPlazoMensualRenovado);

        //Casteo de los layout resultados
        llBotonCalcularPrestamo = v.findViewById(R.id.llbotonCalcular);
        llResultadosCalculo = v.findViewById(R.id.llResultados);

        //Invocando el click lister tipo prestamo y cliente
        tipoClienteCalcu.setOnClickListener(tipoCliente_OnClick);
        tipoPrestamoCalcu.setOnClickListener(tipoPrestamoNuevos_Click);

        //Invocando el click lister clientes nuevos y grupales - periodo - plazos
        tipoPeriodoIndCalcu.setOnClickListener(tipoPeriodoInd_Click);
        tipoPlazoIndCalcu.setOnClickListener(tipoPlazoInd_OnClick);
        tipoPeriodoGpoCalcu.setOnClickListener(tipoPeriodoGpoCalcu_OnClick);
        tipoPlazoGpoCalcu.setOnClickListener(tipoPlazoGpoCalcu_OnClick);

        //Invocando el click lister clientes renovado - periodo - plazos
        tipoPeriodoRenovadosA.setOnClickListener(tipoPeriodoRenovados_OnClick);
        plazoSemanalRenovados.setOnClickListener(tipoSemanalRenovados_OnClick);
        plazosCatorcenalRenovados.setOnClickListener(tipoCatorcenal_OnClick);
        plazosQuincenalRenovados.setOnClickListener(tipoQuincenal_OnClick);
        plazosMensualRenovados.setOnClickListener(tipoMensual_OnClick);

        //Resultados click Listener
        calcularPrestamosSimulado.setOnClickListener(calcularPrestamo_OnClick);

        return v;
    }

    public View.OnClickListener tipoCliente_OnClick = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            AlertDialog.Builder builder = new AlertDialog.Builder(ctx);
            builder.setTitle(R.string.selected_option)
                    .setItems(R.array.tipo_cliente, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int position) {
                            tipoClienteCalcu.setError(null);
                            tipoClienteCalcu.setText(_clienteCalcu[position]);
                            tipoPrestamoNuevos_Click.onClick(tipoClienteCalcu);

                        }
                    });
            builder.create();
            builder.show();
        }
    };

    public View.OnClickListener tipoPrestamoNuevos_Click = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            AlertDialog.Builder builder = new AlertDialog.Builder(ctx);
            builder.setTitle(R.string.selected_option)
                    .setItems(R.array.tipo_clientePrestamo, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int position) {
                            tipoPrestamoCalcu.setError(null);
                            tipoPrestamoCalcu.setText(_prestamoCalcu[position]);

                            if(tipoClienteCalcu.getText().equals("NUEVO")){
                                llPeriodoClientesRenovados.setVisibility(View.GONE);
                                llPlazoSemanalRenovado.setVisibility(View.GONE);
                                llPlazoCatorcenalRenovado.setVisibility(View.GONE);
                                llPlazoQuincenalRenovado.setVisibility(View.GONE);
                                llPlazoMensualRenovado.setVisibility(View.GONE);
                                //llPeriodoClientesRenovados.setVisibility(View.VISIBLE);


                                if(tipoPrestamoCalcu.getText().equals("INDIVIDUALES")){
                                    limpiarTextosA(tipoPrestamoCalcu.getText().toString());
                                    llMontoPrestamo.setVisibility(View.VISIBLE);
                                    llPeriodoClienteNuevoInd.setVisibility(View.VISIBLE);
                                    llClientePlazoInd.setVisibility(View.VISIBLE);
                                    if(tipoPlazoIndCalcu.getText().toString()!=null){
                                        llBotonCalcularPrestamo.setVisibility(View.VISIBLE);
                                    }else
                                        llBotonCalcularPrestamo.setVisibility(View.GONE);
                                    //llBotonCalcularPrestamo.setVisibility(View.VISIBLE);

                                    llPlazoNuevoGrupo.setVisibility(View.GONE);
                                    llPeriodoNuevoGrupo.setVisibility(View.GONE);
                                }else if(tipoPrestamoCalcu.getText().equals("GRUPALES")) {
                                    limpiarTextosA(tipoPrestamoCalcu.getText().toString());
                                    llMontoPrestamo.setVisibility(View.VISIBLE);
                                    llPeriodoNuevoGrupo.setVisibility(View.VISIBLE);
                                    llPlazoNuevoGrupo.setVisibility(View.VISIBLE);
                                    if(tipoPlazoGpoCalcu.getText().toString()!=null){
                                        llBotonCalcularPrestamo.setVisibility(View.VISIBLE);
                                    }else
                                        llBotonCalcularPrestamo.setVisibility(View.GONE);
                                    //llBotonCalcularPrestamo.setVisibility(View.VISIBLE);

                                    llPeriodoClienteNuevoInd.setVisibility(View.GONE);
                                    llClientePlazoInd.setVisibility(View.GONE);
                                }
                            }else if(tipoClienteCalcu.getText().equals("RENOVADO")){
                                    limpiarTextosRenovados();
                                    llMontoPrestamo.setVisibility(View.VISIBLE);
                                    llPeriodoClientesRenovados.setVisibility(View.VISIBLE);
                                if(tipoPlazoGpoCalcu.getText().toString()!=null){
                                    llBotonCalcularPrestamo.setVisibility(View.VISIBLE);
                                }else
                                    llBotonCalcularPrestamo.setVisibility(View.GONE);

                                    llPlazoNuevoGrupo.setVisibility(View.GONE);
                                    llPeriodoNuevoGrupo.setVisibility(View.GONE);
                                    llPeriodoClienteNuevoInd.setVisibility(View.GONE);
                                    llClientePlazoInd.setVisibility(View.GONE);
                            }
                        }

                    });
            builder.create();
            builder.show();
        }
    };

    public View.OnClickListener tipoPeriodoInd_Click = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            AlertDialog.Builder builder = new AlertDialog.Builder(ctx);
            builder.setTitle(R.string.selected_option)
                    .setItems(R.array.tipo_periodicidad_nuevo, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int position) {
                            tipoPeriodoIndCalcu.setError(null);
                            tipoPeriodoIndCalcu.setText(_periodoIndCalcu[position]);
                            caf.getPeriodicidadA(tipoPeriodoIndCalcu.getText().toString());
                        }
                    });
            builder.create();
            builder.show();
        }
    };

    public View.OnClickListener tipoPlazoInd_OnClick = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            AlertDialog.Builder builder = new AlertDialog.Builder(ctx);
            builder.setTitle(R.string.selected_option)
                    .setItems(R.array.plazo_individual_nuevo, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int position) {
                            tipoPlazoIndCalcu.setError(null);
                            tipoPlazoIndCalcu.setText(_plazoIndCalcu[position]);
                        }
                    });
            builder.create();
            builder.show();
        }
    };

    public View.OnClickListener tipoPeriodoGpoCalcu_OnClick = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            AlertDialog.Builder builder = new AlertDialog.Builder(ctx);
            builder.setTitle(R.string.selected_option)
                    .setItems(R.array.tipo_periodicidad_nuevo, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int position) {
                            tipoPeriodoGpoCalcu.setError(null);
                            tipoPeriodoGpoCalcu.setText(_periodoGpoCalcu[position]);
                            caf.getPeriodicidadA(tipoPeriodoGpoCalcu.getText().toString());
                        }
                    });
            builder.create();
            builder.show();
        }
    };

    public View.OnClickListener tipoPlazoGpoCalcu_OnClick = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            AlertDialog.Builder builder = new AlertDialog.Builder(ctx);
            builder.setTitle(R.string.selected_option)
                    .setItems(R.array.tipo_plazoGrup_nuevo, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int position) {
                            tipoPlazoGpoCalcu.setError(null);
                            tipoPlazoGpoCalcu.setText(_plazoGpoCalcu[position]);
                        }
                    });
            builder.create();
            builder.show();
        }
    };

    public View.OnClickListener tipoPeriodoRenovados_OnClick = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            AlertDialog.Builder builder = new AlertDialog.Builder(ctx);
            builder.setTitle(R.string.selected_option)
                    .setItems(R.array.periodicidad_cliente_renovado, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int position) {
                            tipoPeriodoRenovadosA.setError(null);
                            tipoPeriodoRenovadosA.setText(_periodoRenovados[position]);

                            if(tipoPeriodoRenovadosA.getText().equals("SEMANAL")){
                                llPlazoSemanalRenovado.setVisibility(View.VISIBLE);
                                limpiarTextosRenovadosA(tipoPeriodoRenovadosA.getText().toString());
                                llPlazoCatorcenalRenovado.setVisibility(View.GONE);
                                llPlazoQuincenalRenovado.setVisibility(View.GONE);
                                llPlazoMensualRenovado.setVisibility(View.GONE);
                            }else if(tipoPeriodoRenovadosA.getText().equals("CATORCENAL")){
                                llPlazoCatorcenalRenovado.setVisibility(View.VISIBLE);
                                limpiarTextosRenovadosA(tipoPeriodoRenovadosA.getText().toString());
                                llPlazoSemanalRenovado.setVisibility(View.GONE);
                                llPlazoQuincenalRenovado.setVisibility(View.GONE);
                                llPlazoMensualRenovado.setVisibility(View.GONE);
                            }else if(tipoPeriodoRenovadosA.getText().equals("QUINCENAL")){
                                llPlazoQuincenalRenovado.setVisibility(View.VISIBLE);
                                llPlazoCatorcenalRenovado.setVisibility(View.GONE);
                                limpiarTextosRenovadosA(tipoPeriodoRenovadosA.getText().toString());
                                llPlazoSemanalRenovado.setVisibility(View.GONE);
                                llPlazoMensualRenovado.setVisibility(View.GONE);
                            }else if(tipoPeriodoRenovadosA.getText().equals("MENSUAL")){
                                llPlazoMensualRenovado.setVisibility(View.VISIBLE);
                                limpiarTextosRenovadosA(tipoPeriodoRenovadosA.getText().toString());
                                llPlazoSemanalRenovado.setVisibility(View.GONE);
                                llPlazoCatorcenalRenovado.setVisibility(View.GONE);
                                llPlazoQuincenalRenovado.setVisibility(View.GONE);
                            }
                        }
                    });
            builder.create();
            builder.show();
        }
    };

    public View.OnClickListener tipoSemanalRenovados_OnClick = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            AlertDialog.Builder builder = new AlertDialog.Builder(ctx);
            builder.setTitle(R.string.selected_option)
                    .setItems(R.array.plazo_cliente_renovado_semanal, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int position) {
                            plazoSemanalRenovados.setText(null);
                            plazoSemanalRenovados.setText(_plazoSemanal[position]);
                        }
                    });
            builder.create();
            builder.show();
        }
    };

    public View.OnClickListener tipoCatorcenal_OnClick = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            AlertDialog.Builder builder = new AlertDialog.Builder(ctx);
            builder.setTitle(R.string.selected_option)
                    .setItems(R.array.plazo_cliente_renovado_catorcenal, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int position) {
                            plazosCatorcenalRenovados.setText(null);
                            plazosCatorcenalRenovados.setText(_plazoCatorcenal[position]);
                        }
                    });
            builder.create();
            builder.show();
        }
    };

    public View.OnClickListener tipoQuincenal_OnClick = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            AlertDialog.Builder builder = new AlertDialog.Builder(ctx);
            builder.setTitle(R.string.selected_option)
                    .setItems(R.array.plazo_cliente_renovado_quincenal, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int positon) {
                            plazosQuincenalRenovados.setText(null);
                            plazosQuincenalRenovados.setText(_plazoQuincenal[positon]);
                        }
                    });
            builder.create();
            builder.show();
        }
    };

    public View.OnClickListener tipoMensual_OnClick = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            AlertDialog.Builder builder = new AlertDialog.Builder(ctx);
            builder.setTitle(R.string.selected_option)
                    .setItems(R.array.plazo_cliente_renovado_mensual, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int positon) {
                            plazosMensualRenovados.setText(null);
                            plazosMensualRenovados.setText(_plazoMensual[positon]);
                        }
                    });
            builder.create();
            builder.show();
        }
    };

    public View.OnClickListener calcularPrestamo_OnClick = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            progressDialog = new ProgressDialog(ctx);
            progressDialog.setTitle("¡Calculando!");
            progressDialog.setMessage("Obteniendo presupuesto, por favor espere...");
            progressDialog.setProgressStyle(progressDialog.STYLE_SPINNER);
            progressDialog.show();
            progressDialog.setCancelable(false);
            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        Thread.sleep(1200);
                        if (caf.moneyFormatA(montoPrestamoCalcu.getText().toString()) != null && !(montoPrestamoCalcu.getText().toString().isEmpty())) {
                            if (tipoClienteCalcu.getText().equals("NUEVO")) {
                                consultarPrestamoNuevos();
                                progressDialog.dismiss();
                                llResultadosCalculo.setVisibility(View.VISIBLE);
                            } else if (tipoClienteCalcu.getText().equals("RENOVADO")) {
                                consultarPrestamoRenovados();
                                progressDialog.dismiss();
                                llResultadosCalculo.setVisibility(View.VISIBLE);
                            }
                        } else {
                            montoPrestamoCalcu.setError("Los montos ingresados deben ser multiplos de 1000.");
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    progressDialog.dismiss();
                }
            }).start();
            llResultadosCalculo.setVisibility(View.VISIBLE);
        }
    };

        public void consultarPrestamoNuevos() {
            try {
                if (tipoClienteCalcu.getText().equals("NUEVO") && tipoPrestamoCalcu.getText().equals("INDIVIDUALES")) {
                    int idSucursal = Integer.parseInt(suucursalCosto.getText().toString());
                    final String idProducto = caf.GetTipoProductoA(tipoPrestamoCalcu.getText().toString());
                    final String tipoCliente = caf.GetTipoCliente(tipoClienteCalcu.getText().toString());
                    final String monto = caf.moneyFormatA(montoPrestamoCalcu.getText().toString());
                    int periodo = caf.getPeriodicidadA(tipoPeriodoIndCalcu.getText().toString());
                    int plazo = caf.getAmortizaciones(tipoPeriodoIndCalcu.getText().toString(), tipoPlazoIndCalcu.getText().toString());

                    ServicioWebCalculos(idSucursal, idProducto, tipoCliente, monto, plazo, periodo);

                } else if (tipoClienteCalcu.getText().equals("NUEVO") && tipoPrestamoCalcu.getText().equals("GRUPALES")) {
                    int idSucursal = Integer.parseInt(suucursalCosto.getText().toString());
                    final String idProducto = caf.GetTipoProductoA(tipoPrestamoCalcu.getText().toString());
                    final String tipoCliente = caf.GetTipoCliente(tipoClienteCalcu.getText().toString());
                    final String monto = caf.moneyFormatA(montoPrestamoCalcu.getText().toString());
                    int periodo = caf.getPeriodicidadA(tipoPeriodoGpoCalcu.getText().toString());
                    int plazo = caf.getAmortizaciones(tipoPeriodoGpoCalcu.getText().toString(), tipoPlazoGpoCalcu.getText().toString());
                    ServicioWebCalculos(idSucursal, idProducto, tipoCliente, monto, plazo, periodo);

                } else {
                    Toast.makeText(ctx, "SOLO CLIENTES NUEVOS", Toast.LENGTH_SHORT).show();
                }
            } catch (Exception e) {
                Toast.makeText(ctx, "Error:" + e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        }

        //PASAR EL PARAMETRO AL TEXTVIW, UNA VEZ MOSTRADO OBTENER EL COSTO SUCURSAL
        public void getSucursalA(SessionManager sen) throws JSONException {
            JSONArray jsonArray = sen.getSucursales();

            for (int i = 0; i < jsonArray.length(); i++) {

                String data;
                JSONObject ide = jsonArray.getJSONObject(Integer.parseInt(String.valueOf(i)));
                data = ide.getString("centrocosto_id");
                String objetos = null;
                objetos = jsonArray.get(i).toString();

                if (objetos.equals("centrocosto_id") || objetos != null) {
                    suucursalCosto.setText(data);
                } else
                    suucursalCosto.setText("NEL PRRO");
            }
        }


        public void consultarPrestamoRenovados() {

            try {
                int idSucursal = Integer.parseInt(suucursalCosto.getText().toString());
                final String idProducto = caf.GetTipoProductoA(tipoPrestamoCalcu.getText().toString());
                final String tipoCliente = caf.GetTipoCliente(tipoClienteCalcu.getText().toString());
                final String monto = caf.moneyFormatA(montoPrestamoCalcu.getText().toString());
                int periodo = caf.getPeriodicidadA(tipoPeriodoRenovadosA.getText().toString());
                int plazo = 0;
                if (tipoPeriodoRenovadosA.getText().equals("SEMANAL")) {
                    plazo = caf.getAmortizacionesRenovados(tipoPeriodoRenovadosA.getText().toString(), plazoSemanalRenovados.getText().toString());
                } else if (tipoPeriodoRenovadosA.getText().equals("CATORCENAL")) {
                    plazo = caf.getAmortizacionesRenovados(tipoPeriodoRenovadosA.getText().toString(), plazosCatorcenalRenovados.getText().toString());
                } else if (tipoPeriodoRenovadosA.getText().equals("QUINCENAL")) {
                    plazo = caf.getAmortizacionesRenovados(tipoPeriodoRenovadosA.getText().toString(), plazosQuincenalRenovados.getText().toString());
                } else if (tipoPeriodoRenovadosA.getText().equals("MENSUAL")) {
                    plazo = caf.getAmortizacionesRenovados(tipoPeriodoRenovadosA.getText().toString(), plazosMensualRenovados.getText().toString());
                }
                ServicioWebCalculos(idSucursal, idProducto, tipoCliente, monto, plazo, periodo);
            } catch (Exception e) {
                //Toast.makeText(ctx, "Error:" + e.getMessage(),Toast.LENGTH_SHORT).show();
            }
        }

        public void ServicioWebCalculos(int idSucursal, String idProducto, String tipoCliente, String monto, int plazo, int periodo) {
           /*String base_url = "http://sidert.ddns.net:83/api/movil/";

            final OkHttpClient okHttpClient = new OkHttpClient.Builder()
                    .connectTimeout(120, TimeUnit.SECONDS)
                    .readTimeout(120, TimeUnit.SECONDS)
                    .build();

            Retrofit retrofit = null;

            retrofit = new Retrofit.Builder()
                    .addConverterFactory(GsonConverterFactory.create())
                    .baseUrl(base_url)
                    .client(okHttpClient)
                    .build();*/

            CalculadoraApiService api = new RetrofitClient().generalRF(CONTROLLER_MOVIL, ctx).create(CalculadoraApiService.class);
            //"Bearer "+ session.getUser().get(7)
            Call<Calculadora> call = api.setCalcularPresupuesto(idSucursal, idProducto, tipoCliente, monto, plazo, periodo);
            call.enqueue(new Callback<Calculadora>() {
                @Override
                public void onResponse(Call<Calculadora> call, Response<Calculadora> response) {
                    if (response.isSuccessful()) {
                        //Code

                        //mostrarRestulados(response.body().toString());
                        List<Calculadora> calcuList = Collections.singletonList(response.body());
                        for (Calculadora calcuA : calcuList) {
                            periodoPago.setText("$" + calcuA.getPeriodoPago().toString());
                            resultadoPrestamo.setText("$" + calcuA.getMontoPago().toString());
                        }
                        //periodoPago.setText(response.body().toString().trim());
                        Toast.makeText(ctx, "¡Calculo obtenido!", Toast.LENGTH_SHORT).show();
                    }else{
                        Toast.makeText(ctx, "Error, vuelve a intentarlo", Toast.LENGTH_SHORT).show();
                    }

                }

                @Override
                public void onFailure(Call<Calculadora> call, Throwable t) {
                    Toast.makeText(ctx, "Error, vuelve a intentarlo", Toast.LENGTH_SHORT).show();
                }
            });
        }
        //Agregar los metodos para limpiar los distintos tipos de textos
        //Limpia todos los textos

        public void limpiarTextosA(String x) {

            if (x == "INDIVIDUALES") {
                montoPrestamoCalcu.setText("");
                tipoPeriodoGpoCalcu.setText("");
                tipoPlazoGpoCalcu.setText("");
                tipoPeriodoRenovadosA.setText("");
                plazoSemanalRenovados.setText("");
                plazosCatorcenalRenovados.setText("");
                plazosQuincenalRenovados.setText("");
                plazosMensualRenovados.setText("");
                periodoPago.setText("");
                resultadoPrestamo.setText("");
                llResultadosCalculo.setVisibility(View.GONE);
            } else if (x == "GRUPALES") {
                montoPrestamoCalcu.setText("");
                tipoPeriodoIndCalcu.setText("");
                tipoPlazoIndCalcu.setText("");
                tipoPeriodoRenovadosA.setText("");
                plazoSemanalRenovados.setText("");
                plazosCatorcenalRenovados.setText("");
                plazosQuincenalRenovados.setText("");
                plazosMensualRenovados.setText("");
                periodoPago.setText("");
                resultadoPrestamo.setText("");
                llResultadosCalculo.setVisibility(View.GONE);
            }
        }

        public void limpiarTextosRenovadosA(String a) {
            if (a == "SEMANAL") {
                montoPrestamoCalcu.setText("");
                plazosCatorcenalRenovados.setText("");
                plazosQuincenalRenovados.setText("");
                plazosMensualRenovados.setText("");
                periodoPago.setText("");
                resultadoPrestamo.setText("");
                llResultadosCalculo.setVisibility(View.GONE);
            } else if (a == "CATORCENAL") {
                montoPrestamoCalcu.setText("");
                plazoSemanalRenovados.setText("");
                plazosQuincenalRenovados.setText("");
                plazosMensualRenovados.setText("");
                periodoPago.setText("");
                resultadoPrestamo.setText("");
                llResultadosCalculo.setVisibility(View.GONE);

            } else if (a == "QUINCENAL") {
                plazoSemanalRenovados.setText("");
                plazosCatorcenalRenovados.setText("");
                plazosMensualRenovados.setText("");
                periodoPago.setText("");
                resultadoPrestamo.setText("");
                llResultadosCalculo.setVisibility(View.GONE);

            } else if (a == "MENSUAL") {
                plazoSemanalRenovados.setText("");
                plazosCatorcenalRenovados.setText("");
                plazosQuincenalRenovados.setText("");
                periodoPago.setText("");
                resultadoPrestamo.setText("");
                llResultadosCalculo.setVisibility(View.GONE);
            }
        }

        public void limpiarTextosRenovados() {
            montoPrestamoCalcu.setText("");
            tipoPeriodoIndCalcu.setText("");
            tipoPlazoIndCalcu.setText("");
            tipoPeriodoGpoCalcu.setText("");
            tipoPlazoGpoCalcu.setText("");
            tipoPeriodoRenovadosA.setText("");
            plazoSemanalRenovados.setText("");
            plazosCatorcenalRenovados.setText("");
            plazosQuincenalRenovados.setText("");
            plazosMensualRenovados.setText("");
            periodoPago.setText("");
            resultadoPrestamo.setText("");
        }
}
