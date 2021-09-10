package com.sidert.sidertmovil.views.verificaciondomiciliaria;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
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
import android.widget.RadioButton;
import android.widget.TextView;

import com.orhanobut.dialogplus.DialogPlus;
import com.orhanobut.dialogplus.OnClickListener;
import com.orhanobut.dialogplus.ViewHolder;
import com.sidert.sidertmovil.R;
import com.sidert.sidertmovil.models.verificacionesdomiciliarias.VerificacionDomiciliaria;
import com.sidert.sidertmovil.models.verificacionesdomiciliarias.VerificacionDomiciliariaDao;
import com.sidert.sidertmovil.utils.SessionManager;
import com.sidert.sidertmovil.views.apoyogastosfunerarios.ResumenActivity;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.List;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class VerificacionDomiciliariaActivity extends AppCompatActivity {
    private Context ctx;
    private SessionManager session;
    private int iTipoSeccion = 1;

    VerificacionDomiciliariaDao verificacionDao;

    //UI
    private RadioButton rbDisponibles;
    private RadioButton rbGestionados;
    private RecyclerView rvPreview;
    public TextView tvContFiltros;
    private Toolbar tbMain;

    //VERIFICACIONES DISPONILES
    private CheckBox cbIndVerDis;
    private CheckBox cbGpoVerDis;
    private AutoCompleteTextView acNombreVerDis;
    private ArrayAdapter<String> adapterNombresVerDis;
    private int cont_filtros_ver_dis = 0;
    private String[] nombresVerDis;

    //VERIFICACIONES GESTIONADAS
    private CheckBox cbIndVerGes;
    private CheckBox cbGpoVerGes;
    private AutoCompleteTextView acNombreVerGes;
    private ArrayAdapter<String> adapterNombresVerGes;
    private int cont_filtros_ver_ges = 0;
    private String[] nombresVerGes;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.view_verificacion_domiciliaria);

        ctx = this;
        session = new SessionManager(ctx);

        verificacionDao = new VerificacionDomiciliariaDao(ctx);

        tbMain = findViewById(R.id.tbMain);
        rbDisponibles = findViewById(R.id.rbDisponibles);
        rbGestionados = findViewById(R.id.rbGestionados);
        rvPreview = findViewById(R.id.rvPreview);

        rvPreview.setLayoutManager(new LinearLayoutManager(ctx));
        rvPreview.setHasFixedSize(false);

        setSupportActionBar(tbMain);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        invalidateOptionsMenu();

        rbDisponibles.setChecked(true);

        rbDisponibles.setOnClickListener(v -> {
            iTipoSeccion = 1;
            setupBadge();
            getDisponibles();
        });

        rbGestionados.setOnClickListener(v -> {
            iTipoSeccion = 2;
            setupBadge();
            getGestionados();
        });

    }

    protected void getDisponibles()
    {
        String nombreCliente = session.getFiltrosVerDom().get(0);
        String nombreGrupo = session.getFiltrosVerDom().get(1);
        String banderaIndividual = session.getFiltrosVerDom().get(2);
        String banderaGrupal     = session.getFiltrosVerDom().get(3);
        String[] filters         = new String[]{nombreGrupo, nombreCliente, banderaIndividual, banderaGrupal};
        List<VerificacionDomiciliaria> verificaciones = verificacionDao.findDisponibles(filters);

        rvPreview.setAdapter(null);

        if(verificaciones.size() > 0)
        {
            VerificacionDomiciliariaAdapter adapter = new VerificacionDomiciliariaAdapter(ctx, verificaciones, verificacion -> {
                Intent intentVerDom = new Intent(ctx, GestionVerificacionDomiciliariaActivity.class);
                intentVerDom.putExtra("ver_dom", verificacion);
                intentVerDom.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intentVerDom);
            });

            rvPreview.setAdapter(adapter);
        }
    }

    protected void getGestionados()
    {
        String nombreCliente = session.getFiltrosVerDom().get(0);
        String nombreGrupo = session.getFiltrosVerDom().get(1);
        String banderaIndividual = session.getFiltrosVerDom().get(2);
        String banderaGrupal     = session.getFiltrosVerDom().get(3);
        String[] filters         = new String[]{nombreGrupo, nombreCliente, banderaIndividual, banderaGrupal};
        List<VerificacionDomiciliaria> verificaciones = verificacionDao.findGestionadas(filters);

        rvPreview.setAdapter(null);

        if(verificaciones.size() > 0)
        {
            VerificacionDomiciliariaAdapter adapter = new VerificacionDomiciliariaAdapter(ctx, verificaciones, verificacion -> {
                Intent intentVerDom = new Intent(ctx, GestionVerificacionDomiciliariaActivity.class);
                intentVerDom.putExtra("ver_dom", verificacion);
                intentVerDom.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intentVerDom);
            });

            rvPreview.setAdapter(adapter);
        }
    }

    private void getClientesDisponibles()
    {
        List<String> nombres = verificacionDao.showNombresDisponibles();

        if(nombres.size() > 0)
        {
            nombresVerDis = new String[nombres.size()];

            for(int i = 0; i < nombres.size(); i++)
            {
                nombresVerDis[i] = nombres.get(i);
            }

        }
        else {
            nombresVerDis = new String[1];
            nombresVerDis[0] = "";
        }

        adapterNombresVerDis = new ArrayAdapter<>(ctx, R.layout.custom_list_item, R.id.text_view_list_item, nombresVerDis);
    }

    private void getClientesGestionados()
    {
        List<String> nombres = verificacionDao.showNombresGestionados();

        if(nombres.size() > 0)
        {
            nombresVerGes = new String[nombres.size()];

            for(int i = 0; i < nombres.size(); i++)
            {
                nombresVerGes[i] = nombres.get(i);
            }

        }
        else {
            nombresVerGes = new String[1];
            nombresVerGes[0] = "";
        }

        adapterNombresVerGes = new ArrayAdapter<>(ctx, R.layout.custom_list_item, R.id.text_view_list_item, nombresVerGes);
    }

    @SuppressLint("ClickableViewAccessibility")
    private void Filtros (){
        int view = 0;
        int sizeH = 0;

        switch (iTipoSeccion){
            case 1:
                sizeH = 600;
                view = R.layout.sheet_dialog_filtros_verificaciones_domiciliarias;
                break;
            case 2:
                sizeH = 600;
                view = R.layout.sheet_dialog_filtros_gestiones_ver_dom;
                break;
        }

        DialogPlus filtros_dg = DialogPlus.newDialog(ctx)
            .setContentHolder(new ViewHolder(view))
            .setGravity(Gravity.TOP)
            .setPadding(20,40,20,10)
            .setOnClickListener(new OnClickListener() {

                @Override
                public void onClick(DialogPlus dialog, View view) {
                    String where             = "";
                    cont_filtros_ver_dis = 0;
                    cont_filtros_ver_ges   = 0;
                    HashMap<String, String> filtros = new HashMap<>();
                    InputMethodManager imm = (InputMethodManager)ctx.getSystemService(Context.INPUT_METHOD_SERVICE);

                    switch (view.getId()) {
                        case R.id.btnFiltrar:
                            switch (iTipoSeccion){
                                case 1:
                                    if (!acNombreVerDis.getText().toString().trim().isEmpty()){
                                        filtros.put("ver_dom_cliente_nombre", acNombreVerDis.getText().toString().trim());
                                        filtros.put("ver_dom_grupo_nombre", acNombreVerDis.getText().toString().trim());
                                        cont_filtros_ver_dis += 1;
                                    }
                                    else {
                                        filtros.put("ver_dom_cliente_nombre", "");
                                        filtros.put("ver_dom_grupo_nombre", "");
                                    }

                                    if (cbGpoVerDis.isChecked() && cbIndVerDis.isChecked()){
                                        filtros.put("ver_dom_flag_ind","1");
                                        filtros.put("ver_dom_flag_gru","1");
                                        cont_filtros_ver_dis += 2;
                                    }
                                    else if (cbIndVerDis.isChecked()){
                                        filtros.put("ver_dom_flag_ind","1");
                                        filtros.put("ver_dom_flag_gru","0");
                                        cont_filtros_ver_dis += 1;
                                    }
                                    else if (cbGpoVerDis.isChecked()){
                                        filtros.put("ver_dom_flag_ind","0");
                                        filtros.put("ver_dom_flag_gru","1");
                                        cont_filtros_ver_dis += 1;
                                    }else {
                                        filtros.put("ver_dom_flag_ind","0");
                                        filtros.put("ver_dom_flag_gru","0");
                                    }

                                    filtros.put("contador_ver_dis", String.valueOf(cont_filtros_ver_dis));
                                    session.setFiltrosVerDom(filtros);

                                    imm.hideSoftInputFromWindow(view.getWindowToken(), 0);

                                    getDisponibles();

                                    break;
                                case 2:
                                    if (!acNombreVerGes.getText().toString().trim().isEmpty()){
                                        filtros.put("ges_ver_dom_cliente_nombre", acNombreVerGes.getText().toString().trim());
                                        filtros.put("ges_ver_dom_grupo_nombre", acNombreVerGes.getText().toString().trim());
                                        cont_filtros_ver_ges += 1;
                                    }
                                    else {
                                        filtros.put("ges_ver_dom_cliente_nombre", "");
                                        filtros.put("ges_ver_dom_grupo_nombre", "");
                                    }

                                    if (cbGpoVerGes.isChecked() && cbIndVerGes.isChecked()){
                                        filtros.put("ges_ver_dom_flag_ind","1");
                                        filtros.put("ges_ver_dom_flag_gru","1");
                                        cont_filtros_ver_ges += 2;
                                    }
                                    else if (cbIndVerGes.isChecked()){
                                        filtros.put("ges_ver_dom_flag_ind","1");
                                        filtros.put("ges_ver_dom_flag_gru","0");
                                        cont_filtros_ver_ges += 1;
                                    }
                                    else if (cbGpoVerGes.isChecked()){
                                        filtros.put("ges_ver_dom_flag_ind","0");
                                        filtros.put("ges_ver_dom_flag_gru","1");
                                        cont_filtros_ver_ges += 1;
                                    }else {
                                        filtros.put("ges_ver_dom_flag_ind","0");
                                        filtros.put("ges_ver_dom_flag_gru","0");
                                    }

                                    filtros.put("ges_contador_ver_dis", String.valueOf(cont_filtros_ver_ges));
                                    session.setFiltrosGesVerDom(filtros);

                                    imm.hideSoftInputFromWindow(view.getWindowToken(), 0);

                                    getDisponibles();

                                    break;
                            }

                            dialog.dismiss();
                            break;

                        case R.id.btnBorrar:
                                switch (iTipoSeccion){
                                    case 1:
                                        cbIndVerDis.setChecked(false);
                                        cbGpoVerDis.setChecked(false);
                                        acNombreVerDis.setText("");
                                        acNombreVerDis.setAdapter(adapterNombresVerDis);
                                        cont_filtros_ver_dis = 0;

                                        filtros = new HashMap<>();
                                        filtros.put("ver_dom_cliente_nombre","");
                                        filtros.put("ver_dom_grupo_nombre","");
                                        filtros.put("ver_dom_flag_ind","0");
                                        filtros.put("ver_dom_flag_gru", "0");
                                        filtros.put("contador_ver_dis", "0");
                                        session.setFiltrosVerDom(filtros);

                                        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);

                                        getDisponibles();
                                        acNombreVerDis.setAdapter(adapterNombresVerDis);

                                        break;
                                    case 2:
                                        cbIndVerGes.setChecked(false);
                                        cbGpoVerGes.setChecked(false);
                                        acNombreVerGes.setText("");
                                        acNombreVerGes.setAdapter(adapterNombresVerGes);
                                        cont_filtros_ver_ges = 0;

                                        filtros = new HashMap<>();
                                        filtros.put("ges_ver_dom_cliente_nombre","");
                                        filtros.put("ges_ver_dom_grupo_nombre","");
                                        filtros.put("ges_ver_dom_flag_ind","0");
                                        filtros.put("ges_ver_dom_flag_gru", "0");
                                        filtros.put("ges_contador_ver_dis", "0");
                                        session.setFiltrosGesVerDom(filtros);

                                        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);

                                        getGestionados();

                                        break;
                                }
                                break;
                        }
                        setupBadge();
                    }
            })
            .setExpanded(true, sizeH)
            .create();

        switch (iTipoSeccion){
            case 1:
                acNombreVerDis = filtros_dg.getHolderView().findViewById(R.id.aetNombre);
                cbIndVerDis    = filtros_dg.getHolderView().findViewById(R.id.cbInd);
                cbGpoVerDis    = filtros_dg.getHolderView().findViewById(R.id.cbGpo);

                acNombreVerDis.setAdapter(adapterNombresVerDis);

                acNombreVerDis.setOnTouchListener(new View.OnTouchListener() {
                    @SuppressLint("ClickableViewAccessibility")
                    @Override
                    public boolean onTouch(View v, MotionEvent event) {
                        acNombreVerDis.showDropDown();
                        return false;
                    }
                });

                if (!session.getFiltrosVerDom().get(0).isEmpty()){
                    acNombreVerDis.setText(session.getFiltrosVerDom().get(0));
                }

                if (session.getFiltrosVerDom().get(2).equals("1")){
                    cbIndVerDis.setChecked(true);
                }

                if (session.getFiltrosVerDom().get(3).equals("1")){
                    cbGpoVerDis.setChecked(true);
                }

                break;
            case 2:
                acNombreVerDis = filtros_dg.getHolderView().findViewById(R.id.aetNombre);
                cbIndVerDis    = filtros_dg.getHolderView().findViewById(R.id.cbInd);
                cbGpoVerDis    = filtros_dg.getHolderView().findViewById(R.id.cbGpo);

                acNombreVerGes.setAdapter(adapterNombresVerGes);

                acNombreVerGes.setOnTouchListener(new View.OnTouchListener() {
                    @SuppressLint("ClickableViewAccessibility")
                    @Override
                    public boolean onTouch(View v, MotionEvent event) {
                        acNombreVerGes.showDropDown();
                        return false;
                    }
                });

                if (!session.getFiltrosGesVerDom().get(0).isEmpty()){
                    acNombreVerGes.setText(session.getFiltrosGesVerDom().get(0));
                }

                if (session.getFiltrosGesVerDom().get(2).equals("1")){
                    cbIndVerGes.setChecked(true);
                }

                if (session.getFiltrosGesVerDom().get(3).equals("1")){
                    cbGpoVerGes.setChecked(true);
                }

                break;
        }

        filtros_dg.show();

    }

    private void setupBadge() {
        switch (iTipoSeccion){
            case 1:
                if (tvContFiltros != null) {
                    tvContFiltros.setText(String.valueOf(session.getFiltrosVerDom().get(4)));
                    tvContFiltros.setVisibility(View.VISIBLE);
                }
                break;
            case 2:
                if (tvContFiltros != null) {
                    tvContFiltros.setText(String.valueOf(session.getFiltrosGesVerDom().get(4)));
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
        getClientesDisponibles();
        getClientesGestionados();

        rbDisponibles.setChecked(true);

        getDisponibles();
    }

}
