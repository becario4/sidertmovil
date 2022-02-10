package com.sidert.sidertmovil.views.apoyogastosfunerarios;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
/*import android.support.design.widget.FloatingActionButton;
import androidx.appcompat.app.AppCompatActivity;*/
import android.os.Bundle;
/*import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import androidx.appcompat.widget.Toolbar;*/
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.orhanobut.dialogplus.DialogPlus;
import com.orhanobut.dialogplus.OnClickListener;
import com.orhanobut.dialogplus.ViewHolder;
import com.sidert.sidertmovil.R;
import com.sidert.sidertmovil.activities.GenerarCurp;
import com.sidert.sidertmovil.models.apoyogastosfunerarios.Gestion;
import com.sidert.sidertmovil.models.apoyogastosfunerarios.GestionDao;
import com.sidert.sidertmovil.models.apoyogastosfunerarios.Prestamo;
import com.sidert.sidertmovil.models.apoyogastosfunerarios.PrestamoDao;
import com.sidert.sidertmovil.models.apoyogastosfunerarios.Recibo;
import com.sidert.sidertmovil.models.apoyogastosfunerarios.ReciboDao;
import com.sidert.sidertmovil.utils.SessionManager;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.List;

import static com.sidert.sidertmovil.utils.Constants.TIPO;

public class ApoyoGastosFunerariosActivity extends AppCompatActivity {
    private Context ctx;

    private RecyclerView rvClientes;

    private RadioButton rbPrestamos;
    private RadioButton rbRecibos;
    private RadioButton rbGestiones;

    private SessionManager session;

    private Toolbar tbMain;

    //Prestamos
    private CheckBox cbInd;
    private CheckBox cbGpo;
    private AutoCompleteTextView aetNombre;
    private ArrayAdapter<String> adapterNombre;
    private int cont_filtros = 0;
    public TextView tvContFiltros;
    private String[] dataNombre;

    //Recibos
    private CheckBox cbEnv;
    private CheckBox cbPen;
    private AutoCompleteTextView aetNombreRec;
    private AutoCompleteTextView aetFolio;
    private ArrayAdapter<String> adapterNombreRec;
    private int cont_filtros_recibo = 0;

    private String[] dataNombreRec;

    //Gestiones
    private CheckBox cbEnvGes;
    private CheckBox cbPenGes;
    private AutoCompleteTextView aetNombreGes;
    private ArrayAdapter<String> adapterNombreGes;
    private int cont_filtros_ges = 0;
    private String[] dataNombreGes;

    private int tipoSeccion = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_circulo_credito);

        ctx = this;

        tbMain       = findViewById(R.id.TBmain);
        session      = new SessionManager(ctx);
        rbPrestamos  = findViewById(R.id.rbPrestamos);
        rbRecibos    = findViewById(R.id.rbRecibos);
        rbGestiones  = findViewById(R.id.rbGestiones);
        rvClientes = findViewById(R.id.rvClienteCC);

        rvClientes.setLayoutManager(new LinearLayoutManager(ctx));
        rvClientes.setHasFixedSize(false);

        setSupportActionBar(tbMain);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        setTitle("Solicitud de Soporte");

        invalidateOptionsMenu();

        rbPrestamos.setChecked(true);

        rbPrestamos.setOnClickListener(v -> {
            tipoSeccion = 1;
            setupBadge();
            getPrestamos();
        });

        rbRecibos.setOnClickListener(v -> {
            tipoSeccion = 2;
            setupBadge();
            getRecibos();
        });

        rbGestiones.setOnClickListener(v -> {
            tipoSeccion = 3;
            setupBadge();
            getGestiones();
        });
    }

    private void getPrestamos()
    {
        PrestamoDao prestamoDao = new PrestamoDao(ctx);

        String nombreGrupo       = session.getFiltrosCCAGF().get(0);
        String nombreCliente     = session.getFiltrosCCAGF().get(0);
        String banderaIndividual = session.getFiltrosCCAGF().get(1);
        String banderaGrupal     = session.getFiltrosCCAGF().get(2);
        String[] filters         = new String[]{nombreGrupo, nombreCliente, banderaIndividual, banderaGrupal};
        List<Prestamo> prestamos = prestamoDao.findAllByCustomFilters(filters);

        rvClientes.setAdapter(null);

        if(prestamos.size() > 0)
        {
            PrestamosAdapter adapter = new PrestamosAdapter(ctx, prestamos, prestamo -> {
                ReciboDao reciboDao = new ReciboDao(ctx);
                Recibo recibo;

                if(prestamo.getGrupoId() == 1)
                {
                    recibo = reciboDao.findByNombreAndNumSolicitudAndTipoImpresion(prestamo.getNombreCliente(0), String.valueOf(prestamo.getNumSolicitud()), "O");
                }
                else
                {
                    recibo = reciboDao.findByGrupoIdAndNumSolicitudAndTipoImpresion(String.valueOf(prestamo.getGrupoId()), String.valueOf(prestamo.getNumSolicitud()), "O");
                }

                if(recibo == null)
                {
                    recibo = new Recibo();
                    recibo.setGrupoId(String.valueOf(prestamo.getGrupoId()));
                    recibo.setNumSolicitud(String.valueOf(prestamo.getNumSolicitud()));
                    recibo.setTipoRecibo("AGF");

                    if(prestamo.getGrupoId() > 1)
                    {
                        recibo.setNombre(prestamo.getNombreGrupo());
                        recibo.setNombreFirma(prestamo.getTesorero());
                        recibo.setNumIntegrantes(prestamo.getNumIntegrantes());
                    }
                    else {
                        recibo.setNombre(prestamo.getNombreCliente().substring(1));
                        recibo.setNombreFirma(prestamo.getNombreCliente().substring(1));
                        recibo.setNumIntegrantes((Integer.parseInt(prestamo.getEdad()) < 75 && Double.parseDouble(prestamo.getMonto()) <= 29000)?1:0);
                    }

                    recibo.setPlazo(prestamo.getPlazo());
                    recibo.setMonto(String.valueOf((recibo.getPlazo() * getCostoUnitario()) * recibo.getNumIntegrantes()));
                }
                else
                {
                    //SE RECUPERA EL NUMERO ORIGINAL DE INTEGRANTES YA QUE NO SE GUARDA EN EL RECIBO SI SELECCIONAR INTEGRANTES MANUALES
                    if(prestamo.getGrupoId() > 1)
                    {
                        recibo.setNombreFirma(prestamo.getTesorero());
                    }
                    else {
                        recibo.setNombreFirma(prestamo.getNombreCliente().substring(1));
                    }

                    recibo.setNumIntegrantes(prestamo.getNumIntegrantes());
                }

                if(recibo.getNumIntegrantes() > 0)
                {
                    Intent intentRecibo = new Intent(ctx, ReciboActivity.class);
                    intentRecibo.putExtra("recibo", recibo);
                    intentRecibo.putExtra("res_impresion", 0);
                    intentRecibo.putExtra("is_reeimpresion", false);
                    intentRecibo.putExtra("cliente_id", prestamo.getClienteId());
                    intentRecibo.putExtra("costo_unitario", getCostoUnitario());
                    intentRecibo.putExtra("fecha_entrega", prestamo.getFechaEntrega());
                    intentRecibo.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

                    startActivity(intentRecibo);
                }
                else if(Integer.parseInt(prestamo.getEdad()) >= 75)
                {
                    Toast.makeText(ctx, "Esta persona tiene mas de 75 años", Toast.LENGTH_SHORT).show();
                }
                else if(Double.parseDouble(prestamo.getMonto()) > 29000)
                {
                    Toast.makeText(ctx, "El monto del prestamo es mayor a 29,000", Toast.LENGTH_SHORT).show();
                }
                else{
                    Toast.makeText(ctx, "No se hace cobro de apoyo a gastos funerarios", Toast.LENGTH_SHORT).show();
                }
            });

            rvClientes.setAdapter(adapter);
        }
    }

    private void getRecibos()
    {
        ReciboDao reciboDao = new ReciboDao(ctx);

        String nombre       = session.getFiltrosRecibosCCAGF().get(0);
        String folioRecibo  = session.getFiltrosRecibosCCAGF().get(1);
        String folio        = session.getFiltrosRecibosCCAGF().get(1);
        String enviadas     = session.getFiltrosRecibosCCAGF().get(4);
        String pendientes   = session.getFiltrosRecibosCCAGF().get(5);

        String[] filters = new String[]{nombre, folioRecibo, folio, enviadas, pendientes};
        List<Recibo> recibos = reciboDao.findAllByCustomFilters(filters);
        rvClientes.setAdapter(null);

        if(recibos.size() > 0)
        {
            RecibosAdapter adapter = new RecibosAdapter(ctx, recibos);
            rvClientes.setAdapter(adapter);
        }
    }

    private void getGestiones()
    {
        GestionDao gestionDao = new GestionDao(ctx);

        String nombre = session.getFiltrosGestionCCAGF().get(0);
        String enviados = session.getFiltrosGestionCCAGF().get(3);
        String pendientes = session.getFiltrosGestionCCAGF().get(4);
        String[] filters = new String[]{nombre, enviados, pendientes};
        List<Gestion> gestiones = gestionDao.findAllByCustomFilters(filters);

        rvClientes.setAdapter(null);

        if (gestiones.size() > 0)
        {
            GestionesAdapter adapter = new GestionesAdapter(ctx, gestiones);
            rvClientes.setAdapter(adapter);
        }
    }

    private void GetClientes (){
        PrestamoDao prestamoDao = new PrestamoDao(ctx);
        List<String> nombres = prestamoDao.showNombres();

        if(nombres.size() > 0)
        {
            dataNombre = new String[nombres.size()];

            for(int i = 0; i < nombres.size(); i++)
            {
                dataNombre[i] = nombres.get(i);
            }

        }
        else {
            dataNombre = new String[1];
            dataNombre[0] = "";
        }

        adapterNombre = new ArrayAdapter<>(ctx, R.layout.custom_list_item, R.id.text_view_list_item, dataNombre);
    }

    private void GetClientesRecibos(){
        ReciboDao reciboDao = new ReciboDao(ctx);
        List<String> nombres = reciboDao.showNombres();

        if(nombres.size() > 0)
        {
            dataNombreRec = new String[nombres.size()];

            for(int i = 0; i < nombres.size(); i++)
            {
                dataNombreRec[i] = nombres.get(i);
            }

        }
        else {
            dataNombreRec = new String[1];
            dataNombreRec[0] = "";
        }

        adapterNombreRec = new ArrayAdapter<>(ctx, R.layout.custom_list_item, R.id.text_view_list_item, dataNombreRec);
    }

    private void GetClientesGestiones(){
        GestionDao gestionDao = new GestionDao(ctx);
        List<String> nombres = gestionDao.showNombres();

        if(nombres.size() > 0)
        {
            dataNombreGes = new String[nombres.size()];

            for(int i = 0; i < nombres.size(); i++)
            {
                dataNombreGes[i] = nombres.get(i);
            }

        }
        else {
            dataNombreGes = new String[1];
            dataNombreGes[0] = "";
        }

        adapterNombreGes = new ArrayAdapter<>(ctx, R.layout.custom_list_item, R.id.text_view_list_item, dataNombreGes);
    }

    @SuppressLint("ClickableViewAccessibility")
    private void Filtros (){
        int view = 0;
        int sizeH = 0;

        switch (tipoSeccion){
            case 1:
                sizeH = 600;
                view = R.layout.sheet_dialog_filtros_prestamos_cc_agf;
                break;
            case 2:
                sizeH = 730;
                view = R.layout.sheet_dialog_filtros_recibos_cc_agf;
                break;
            case 3:
                sizeH = 610;
                view = R.layout.sheet_dialog_filtros_gestiones_cc_agf;
                break;
        }

        DialogPlus filtros_dg = DialogPlus.newDialog(ctx)
                .setContentHolder(new ViewHolder(view))
                .setGravity(Gravity.TOP)
                .setPadding(20,40,20,10)
                .setOnClickListener(new OnClickListener() {
                    @Override
                    public void onClick(DialogPlus dialog, View view) {
                        String where                    = "";
                        cont_filtros                    = 0;
                        cont_filtros_recibo             = 0;
                        cont_filtros_ges                = 0;
                        HashMap<String, String> filtros = new HashMap<>();
                        InputMethodManager imm          = (InputMethodManager)ctx.getSystemService(Context.INPUT_METHOD_SERVICE);

                        switch (view.getId()) {
                            case R.id.btnFiltrar:
                                switch (tipoSeccion){
                                    case 1:
                                        if (!aetNombre.getText().toString().trim().isEmpty()){
                                            filtros.put("nombre_cc_agf",aetNombre.getText().toString().trim());
                                            cont_filtros += 1;
                                        }
                                        else filtros.put("nombre_cc_agf","");

                                        if (cbInd.isChecked() && cbGpo.isChecked()){
                                            filtros.put("tipo_prestamo_ind_cc_agf","1");
                                            filtros.put("tipo_prestamo_gpo_cc_agf","1");
                                            cont_filtros += 2;
                                        }
                                        else if (cbInd.isChecked()){
                                            filtros.put("tipo_prestamo_ind_cc_agf","1");
                                            filtros.put("tipo_prestamo_gpo_cc_agf","0");
                                            cont_filtros += 1;
                                        }
                                        else if (cbGpo.isChecked()){
                                            filtros.put("tipo_prestamo_ind_cc_agf","0");
                                            filtros.put("tipo_prestamo_gpo_cc_agf","1");
                                            cont_filtros += 1;
                                        }else {
                                            filtros.put("tipo_prestamo_ind_cc_agf","0");
                                            filtros.put("tipo_prestamo_gpo_cc_agf","0");
                                        }

                                        filtros.put("contador_cc_agf", String.valueOf(cont_filtros));
                                        session.setFiltrosCCAGF(filtros);

                                        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);

                                        getPrestamos();

                                        break;
                                    case 2:
                                        if (!aetNombreRec.getText().toString().trim().isEmpty()){
                                            filtros.put("nombre_recibo_cc_agf",aetNombreRec.getText().toString().trim());
                                            cont_filtros_recibo += 1;
                                        }
                                        else{
                                            filtros.put("nombre_recibo_cc_agf","");
                                        }

                                        if (!aetFolio.getText().toString().trim().isEmpty()){
                                            filtros.put("folio_recibo_cc_agf",aetFolio.getText().toString().trim());
                                            cont_filtros_recibo += 1;
                                        }
                                        else{
                                            filtros.put("folio_recibo_cc_agf","");
                                        }

                                        filtros.put("recibo_agf","0");
                                        filtros.put("recibo_cc","0");

                                        if (cbEnv.isChecked() && cbPen.isChecked()){
                                            filtros.put("recibo_enviada_cc_agf","1");
                                            filtros.put("recibo_pendiente_cc_agf","1");
                                            cont_filtros_recibo += 2;
                                        }
                                        else if (cbEnv.isChecked()){
                                            filtros.put("recibo_enviada_cc_agf","1");
                                            filtros.put("recibo_pendiente_cc_agf","0");
                                            cont_filtros_recibo += 1;
                                        }
                                        else if (cbPen.isChecked()){
                                            filtros.put("recibo_enviada_cc_agf","0");
                                            filtros.put("recibo_pendiente_cc_agf","1");
                                            cont_filtros_recibo += 1;
                                        }else {
                                            filtros.put("recibo_enviada_cc_agf","0");
                                            filtros.put("recibo_pendiente_cc_agf","0");
                                        }

                                        filtros.put("contador_recibo_cc_agf", String.valueOf(cont_filtros_recibo));
                                        session.setFiltrosRecibosCCAGF(filtros);

                                        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);

                                        getRecibos();

                                        break;
                                    case 3:
                                        if (!aetNombreGes.getText().toString().trim().isEmpty()){
                                            filtros.put("nombre_gestion_cc_agf",aetNombreGes.getText().toString().trim());
                                            cont_filtros_ges += 1;
                                        }
                                        else{
                                            filtros.put("nombre_gestion_cc_agf","");
                                        }

                                        filtros.put("gestion_agf","0");
                                        filtros.put("gestion_cc","0");

                                        if (cbEnvGes.isChecked() && cbPenGes.isChecked()){
                                            filtros.put("gestion_enviada_cc_agf","1");
                                            filtros.put("gestion_pendiente_cc_agf","1");
                                            cont_filtros_ges += 2;
                                        }
                                        else if (cbEnvGes.isChecked()){
                                            filtros.put("gestion_enviada_cc_agf","1");
                                            filtros.put("gestion_pendiente_cc_agf","0");
                                            cont_filtros_ges += 1;
                                        }
                                        else if (cbPenGes.isChecked()){
                                            filtros.put("gestion_enviada_cc_agf","0");
                                            filtros.put("gestion_pendiente_cc_agf","1");
                                            cont_filtros_ges += 1;
                                        }else {
                                            filtros.put("gestion_enviada_cc_agf","0");
                                            filtros.put("gestion_pendiente_cc_agf","0");
                                        }

                                        filtros.put("gestion_recibo_cc_agf", String.valueOf(cont_filtros_ges));
                                        session.setFiltrosGestionesCCAGF(filtros);

                                        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);

                                        getGestiones();

                                        break;
                                }

                                dialog.dismiss();

                                break;
                            case R.id.btnBorrar:
                                switch (tipoSeccion){
                                    case 1:
                                        cbInd.setChecked(false);
                                        cbGpo.setChecked(false);
                                        aetNombre.setText("");

                                        cont_filtros = 0;
                                        filtros = new HashMap<>();
                                        filtros.put("nombre_cc_agf","");
                                        filtros.put("tipo_prestamo_ind_cc_agf","0");
                                        filtros.put("tipo_prestamo_gpo_cc_agf","0");
                                        filtros.put("contador_cc_agf", "0");
                                        session.setFiltrosCCAGF(filtros);

                                        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);

                                        getPrestamos();

                                        aetNombre.setAdapter(adapterNombre);
                                        break;
                                    case 2:
                                        aetNombreRec.setText("");
                                        cbEnv.setChecked(false);
                                        cbPen.setChecked(false);
                                        aetNombreRec.setAdapter(adapterNombreRec);
                                        aetFolio.setText("");

                                        cont_filtros_recibo = 0;
                                        filtros = new HashMap<>();
                                        filtros.put("nombre_recibo_cc_agf","");
                                        filtros.put("folio_recibo_cc_agf","");
                                        filtros.put("recibo_agf","0");
                                        filtros.put("recibo_cc","0");
                                        filtros.put("recibo_enviada_cc_agf","0");
                                        filtros.put("recibo_pendiente_cc_agf","0");
                                        filtros.put("contador_recibo_cc_agf", "0");
                                        session.setFiltrosRecibosCCAGF(filtros);

                                        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);

                                        getRecibos();

                                        break;
                                    case 3:
                                        aetNombreGes.setText("");
                                        cbEnvGes.setChecked(false);
                                        cbPenGes.setChecked(false);
                                        aetNombreGes.setAdapter(adapterNombreGes);

                                        cont_filtros_ges = 0;
                                        filtros = new HashMap<>();
                                        filtros.put("nombre_recibo_cc_agf","");
                                        filtros.put("folio_recibo_cc_agf","");
                                        filtros.put("recibo_agf","0");
                                        filtros.put("recibo_cc","0");
                                        filtros.put("recibo_enviada_cc_agf","0");
                                        filtros.put("recibo_pendiente_cc_agf","0");
                                        filtros.put("contador_recibo_cc_agf", "0");
                                        session.setFiltrosGestionesCCAGF(filtros);

                                        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);

                                        getGestiones();

                                        break;
                                }
                                break;
                        }
                        setupBadge();

                    }
                })
                .setExpanded(true, sizeH)
                .create();

        switch (tipoSeccion){
            case 1:
                aetNombre   = filtros_dg.getHolderView().findViewById(R.id.aetNombre);
                cbInd       = filtros_dg.getHolderView().findViewById(R.id.cbInd);
                cbGpo       = filtros_dg.getHolderView().findViewById(R.id.cbGpo);

                aetNombre.setAdapter(adapterNombre);

                aetNombre.setOnTouchListener(new View.OnTouchListener() {
                    @SuppressLint("ClickableViewAccessibility")
                    @Override
                    public boolean onTouch(View v, MotionEvent event) {
                        aetNombre.showDropDown();
                        return false;
                    }
                });

                if (!session.getFiltrosCCAGF().get(0).isEmpty()){
                    aetNombre.setText(session.getFiltrosCCAGF().get(0));
                }

                if (session.getFiltrosCCAGF().get(1).equals("1")){
                    cbGpo.setChecked(true);
                }

                if (session.getFiltrosCCAGF().get(2).equals("1")){
                    cbInd.setChecked(true);
                }

                break;
            case 2:
                aetNombreRec = filtros_dg.getHolderView().findViewById(R.id.aetNombreRecibo);
                aetFolio     = filtros_dg.getHolderView().findViewById(R.id.aetFolio);
                cbEnv        = filtros_dg.getHolderView().findViewById(R.id.cbEnv);
                cbPen        = filtros_dg.getHolderView().findViewById(R.id.cbPen);

                aetNombreRec.setAdapter(adapterNombreRec);

                aetNombreRec.setOnTouchListener(new View.OnTouchListener() {
                    @SuppressLint("ClickableViewAccessibility")
                    @Override
                    public boolean onTouch(View v, MotionEvent event) {
                        aetNombreRec.showDropDown();
                        return false;
                    }
                });

                if (!session.getFiltrosRecibosCCAGF().get(0).isEmpty()){
                    aetNombreRec.setText(session.getFiltrosRecibosCCAGF().get(0));
                }

                if (!session.getFiltrosRecibosCCAGF().get(1).isEmpty()){
                    aetFolio.setText(session.getFiltrosRecibosCCAGF().get(1));
                }

                if (session.getFiltrosRecibosCCAGF().get(4).equals("1")){
                    cbEnv.setChecked(true);
                }

                if (session.getFiltrosRecibosCCAGF().get(5).equals("1")){
                    cbPen.setChecked(true);
                }

                break;
            case 3:
                aetNombreGes   = filtros_dg.getHolderView().findViewById(R.id.aetNombreGes);
                cbEnvGes       = filtros_dg.getHolderView().findViewById(R.id.cbEnvGes);
                cbPenGes       = filtros_dg.getHolderView().findViewById(R.id.cbPenGes);

                aetNombreGes.setAdapter(adapterNombreGes);

                aetNombreGes.setOnTouchListener(new View.OnTouchListener() {
                    @SuppressLint("ClickableViewAccessibility")
                    @Override
                    public boolean onTouch(View v, MotionEvent event) {
                        aetNombreGes.showDropDown();
                        return false;
                    }
                });

                if (!session.getFiltrosGestionCCAGF().get(0).isEmpty()){
                    aetNombreGes.setText(session.getFiltrosGestionCCAGF().get(0));
                }

                if (session.getFiltrosGestionCCAGF().get(3).equals("1")){
                    cbEnvGes.setChecked(true);
                }

                if (session.getFiltrosGestionCCAGF().get(4).equals("1")){
                    cbPenGes.setChecked(true);
                }

                break;
        }

        filtros_dg.show();
    }

    private void setupBadge() {
        switch (tipoSeccion){
            case 2:
                if (tvContFiltros != null) {
                    tvContFiltros.setText(String.valueOf(session.getFiltrosRecibosCCAGF().get(6)));
                    tvContFiltros.setVisibility(View.VISIBLE);
                }
                break;
            case 3:
                if (tvContFiltros != null) {
                    tvContFiltros.setText(String.valueOf(session.getFiltrosGestionCCAGF().get(5)));
                    tvContFiltros.setVisibility(View.VISIBLE);
                }
                break;
            default:
                if (tvContFiltros != null) {
                    tvContFiltros.setText(String.valueOf(session.getFiltrosCCAGF().get(3)));
                    tvContFiltros.setVisibility(View.VISIBLE);
                }
                break;
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_filtro, menu);

        final MenuItem menuItem = menu.findItem(R.id.nvFiltro);
        View actionView = menuItem.getActionView();
        tvContFiltros = actionView.findViewById(R.id.filtro_bagde);
        actionView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onOptionsItemSelected(menuItem);
            }
        });

        setupBadge();
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;
            case R.id.nvFiltro:
                Filtros();
                break;
            case R.id.nvInfo:
                Intent intentResumen = new Intent(ctx, ResumenActivity.class);
                startActivity(intentResumen);
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onResume() {
        super.onResume();
        GetClientes();
        GetClientesRecibos();
        GetClientesGestiones();

        rbPrestamos.setChecked(true);

        getPrestamos();
    }

    private Double getCostoUnitario()
    {
        Double costoUnitario = 15.00;

        for(int i = 0; i < session.getSucursales().length(); i++)
        {
            try {
                JSONObject sucursales = session.getSucursales().getJSONObject(i);
                if (sucursales.getString("nombre").equals("2.2 MECAPALAPA") || sucursales.getString("nombre").equals("2.3 LA MESA")) {
                    costoUnitario = 12.5;
                    break;
                }

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        return costoUnitario;
    }
}
