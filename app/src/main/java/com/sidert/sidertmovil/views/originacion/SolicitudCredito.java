package com.sidert.sidertmovil.views.originacion;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.drawable.ColorDrawable;
//import android.support.design.widget.FloatingActionButton;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;

import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
//import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.orhanobut.dialogplus.DialogPlus;
import com.orhanobut.dialogplus.OnClickListener;
import com.orhanobut.dialogplus.ViewHolder;
import com.sidert.sidertmovil.R;
import com.sidert.sidertmovil.adapters.adapter_originacion;
import com.sidert.sidertmovil.database.DBhelper;
import com.sidert.sidertmovil.models.solicitudes.Solicitud;
import com.sidert.sidertmovil.models.solicitudes.SolicitudDao;
import com.sidert.sidertmovil.models.solicitudes.solicitudind.originacion.Cliente;
import com.sidert.sidertmovil.models.solicitudes.solicitudind.originacion.ClienteDao;
import com.sidert.sidertmovil.utils.Constants;
import com.sidert.sidertmovil.utils.Popups;
import com.sidert.sidertmovil.utils.SessionManager;
import com.sidert.sidertmovil.views.apoyogastosfunerarios.ResumenActivity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;


import static com.sidert.sidertmovil.utils.Constants.TBL_AVAL_IND;
import static com.sidert.sidertmovil.utils.Constants.TBL_CLIENTE_IND;
import static com.sidert.sidertmovil.utils.Constants.TBL_CONYUGE_IND;
import static com.sidert.sidertmovil.utils.Constants.TBL_CONYUGE_INTEGRANTE;
import static com.sidert.sidertmovil.utils.Constants.TBL_CREDITO_GPO;
import static com.sidert.sidertmovil.utils.Constants.TBL_CREDITO_IND;
import static com.sidert.sidertmovil.utils.Constants.TBL_CROQUIS_GPO;
import static com.sidert.sidertmovil.utils.Constants.TBL_CROQUIS_IND;
import static com.sidert.sidertmovil.utils.Constants.TBL_DOCUMENTOS;
import static com.sidert.sidertmovil.utils.Constants.TBL_DOCUMENTOS_INTEGRANTE;
import static com.sidert.sidertmovil.utils.Constants.TBL_DOMICILIO_INTEGRANTE;
import static com.sidert.sidertmovil.utils.Constants.TBL_ECONOMICOS_IND;
import static com.sidert.sidertmovil.utils.Constants.TBL_INTEGRANTES_GPO;
import static com.sidert.sidertmovil.utils.Constants.TBL_NEGOCIO_IND;
import static com.sidert.sidertmovil.utils.Constants.TBL_NEGOCIO_INTEGRANTE;
import static com.sidert.sidertmovil.utils.Constants.TBL_OTROS_DATOS_INTEGRANTE;
import static com.sidert.sidertmovil.utils.Constants.TBL_POLITICAS_PLD_IND;
import static com.sidert.sidertmovil.utils.Constants.TBL_POLITICAS_PLD_INTEGRANTE;
import static com.sidert.sidertmovil.utils.Constants.TBL_REFERENCIA_IND;
import static com.sidert.sidertmovil.utils.Constants.TBL_SOLICITUDES;
import static com.sidert.sidertmovil.utils.Constants.TBL_TELEFONOS_INTEGRANTE;

public class SolicitudCredito extends AppCompatActivity {

    private Context ctx;
    private Context context;

    private FloatingActionButton fbAgregar;

    private adapter_originacion adapter;
    private RecyclerView rvOriginacion;

    private Toolbar tbMain;

    private RadioButton rbEnProceso;
    private RadioButton rbCompletados;

    private SessionManager session;

    private int seccion = 2;
    private int cont_proceso = 0;
    private int cont_completados = 0;

    private LinearLayout llGpo;
    private LinearLayout llInd;

    private DBhelper dBhelper;
    private SQLiteDatabase db;

    private Paint p = new Paint();

    private boolean fabExpanded = false;

    private CheckBox cbInd;
    private CheckBox cbGpo;
    private CheckBox cbMenor45;
    private AutoCompleteTextView aetNombre;
    private ArrayAdapter<String> adapterNombre;
    public TextView tvContFiltros;
    private String[] dataNombre;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_solicitud_credito);
        ctx = getApplicationContext();
        context = this;

        tbMain       = findViewById(R.id.TBmain);
        session      = SessionManager.getInstance(ctx);

        dBhelper = DBhelper.getInstance(ctx);
        db = dBhelper.getWritableDatabase();

        rvOriginacion = findViewById(R.id.rvOriginacion);
        rbEnProceso   = findViewById(R.id.rbEnProceso);
        rbCompletados = findViewById(R.id.rbCompletados);

        rvOriginacion.setLayoutManager(new LinearLayoutManager(ctx));
        rvOriginacion.setHasFixedSize(false);

        fbAgregar = findViewById(R.id.fbAgregar);
        llGpo = findViewById(R.id.llGpo);
        llInd = findViewById(R.id.llInd);

        setSupportActionBar(tbMain);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        invalidateOptionsMenu();

        rbEnProceso.setChecked(true);

        rbEnProceso.setOnClickListener(v -> {
            seccion = 2;
            setupBadge();
            GetClientesEnProceso();
            getEnProceso();
        });

        rbCompletados.setOnClickListener(v -> {
            seccion = 3;
            setupBadge();
            GetClientesCompletados();
            getCompletados();
        });

        fbAgregar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (fabExpanded){
                    closeSubMenusFab();
                } else {
                    openSubMenusFab();
                }
            }
        });

        llGpo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                llGpo.setEnabled(false);
                Intent i_solicitud_gpo = new Intent(ctx, SolicitudCreditoGpo.class);
                i_solicitud_gpo.putExtra("is_new",true);
                i_solicitud_gpo.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(i_solicitud_gpo);
            }
        });

        llInd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                llInd.setEnabled(false);
                Intent i_solicitud_ind = new Intent(ctx, SolicitudCreditoInd.class);
                i_solicitud_ind.putExtra("is_new", true);
                i_solicitud_ind.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(i_solicitud_ind);
            }
        });
    }

    private void closeSubMenusFab(){
        llGpo.setVisibility(View.INVISIBLE);
        llInd.setVisibility(View.INVISIBLE);
        fbAgregar.setImageResource(R.drawable.ic_add_black);
        fabExpanded = false;
    }

    //Opens FAB submenus
    private void openSubMenusFab(){
        llGpo.setVisibility(View.VISIBLE);
        llInd.setVisibility(View.VISIBLE);

        fbAgregar.setImageResource(R.drawable.ic_close_black);
        fabExpanded = true;
    }

    private void initSwipe() {
        ItemTouchHelper.SimpleCallback simpleCallback = new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.RIGHT|ItemTouchHelper.LEFT) {
            @Override
            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
                int position = viewHolder.getAdapterPosition();
                if (direction == ItemTouchHelper.RIGHT){
                    BorrarSolicitud(Integer.parseInt(adapter.getItem(position).get(2)),adapter.getItem(position).get(0), position);
                } else {
                    BorrarSolicitud(Integer.parseInt(adapter.getItem(position).get(2)),adapter.getItem(position).get(0), position);
                }
            }

            @Override
            public void onChildDraw(Canvas c, RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive) {
                Bitmap icon;
                if(actionState == ItemTouchHelper.ACTION_STATE_SWIPE){
                    View itemView = viewHolder.itemView;
                    float height  = (float) itemView.getBottom() - (float) itemView.getTop();
                    float width   = height /4;
                    if(dX > 0){
                        p.setColor(Color.RED);
                        RectF background = new RectF((float) itemView.getLeft(), (float) itemView.getTop(), dX,(float) itemView.getBottom());
                        c.drawRect(background,p);
                        icon = BitmapFactory.decodeResource(getResources(), R.drawable.ic_delete_forever);
                        RectF icon_dest = new RectF((float) itemView.getLeft() + width ,(float) itemView.getTop() + width,(float) itemView.getLeft()+ 3*width,(float)itemView.getBottom() - width);
                        c.drawBitmap(icon,null,icon_dest,p);
                    } else {
                        p.setColor(Color.RED);
                        RectF background = new RectF((float) itemView.getRight() + dX, (float) itemView.getTop(),(float) itemView.getRight(), (float) itemView.getBottom());
                        c.drawRect(background,p);
                        icon = BitmapFactory.decodeResource(getResources(), R.drawable.ic_delete_forever);
                        RectF icon_dest = new RectF((float) itemView.getRight() - 3*width ,(float) itemView.getTop() + width,(float) itemView.getRight() - width,(float)itemView.getBottom() - width);
                        c.drawBitmap(icon,null,icon_dest,p);
                    }
                }
                super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);
            }
        };
        ItemTouchHelper helper = new ItemTouchHelper(simpleCallback);
        helper.attachToRecyclerView(rvOriginacion);
    }

    private void BorrarSolicitud (final int tipo_solicitud, final String id_solcitud, final int position){
        AlertDialog borrar_soli_dlg = Popups.showDialogConfirm(context, Constants.question,
                R.string.borrar_solicitud, R.string.yes, new Popups.DialogMessage() {
                    @Override
                    public void OnClickListener(AlertDialog dialog) {
                        AlertDialog confirm_borrar_soli_dlg = Popups.showDialogConfirm(context, Constants.question,
                                R.string.confirm_borrar_solicitud, R.string.yes, new Popups.DialogMessage() {
                                    @Override
                                    public void OnClickListener(AlertDialog dialog) {
                                        if (tipo_solicitud == 1) {
                                            db.delete(TBL_SOLICITUDES, "id_solicitud = ?", new String[]{id_solcitud});
                                            db.delete(TBL_CREDITO_IND, "id_solicitud = ?", new String[]{id_solcitud});
                                            db.delete(TBL_CLIENTE_IND, "id_solicitud = ?", new String[]{id_solcitud});
                                            db.delete(TBL_CONYUGE_IND, "id_solicitud = ?", new String[]{id_solcitud});
                                            db.delete(TBL_ECONOMICOS_IND, "id_solicitud = ?", new String[]{id_solcitud});
                                            db.delete(TBL_NEGOCIO_IND, "id_solicitud = ?", new String[]{id_solcitud});
                                            db.delete(TBL_AVAL_IND, "id_solicitud = ?", new String[]{id_solcitud});
                                            db.delete(TBL_REFERENCIA_IND, "id_solicitud = ?", new String[]{id_solcitud});
                                            db.delete(TBL_CROQUIS_IND, "id_solicitud = ?", new String[]{id_solcitud});
                                            db.delete(TBL_POLITICAS_PLD_IND, "id_solicitud = ?", new String[]{id_solcitud});
                                            db.delete(TBL_DOCUMENTOS, "id_solicitud = ?", new String[]{id_solcitud});

                                        }
                                        else {
                                            Cursor row_credito = dBhelper.getRecords(TBL_CREDITO_GPO, " WHERE id_solicitud = ?", "", new String[]{id_solcitud});
                                            row_credito.moveToFirst();
                                            Cursor row_integrantes = dBhelper.getRecords(TBL_INTEGRANTES_GPO, " WHERE id_credito = ?", "", new String[]{row_credito.getString(0)});
                                            row_integrantes.moveToFirst();
                                            for (int i = 0; i < row_integrantes.getCount(); i++){
                                                db.delete(TBL_TELEFONOS_INTEGRANTE, "id_integrante = ?", new String[]{row_integrantes.getString(0)});
                                                db.delete(TBL_DOMICILIO_INTEGRANTE, "id_integrante = ?", new String[]{row_integrantes.getString(0)});
                                                db.delete(TBL_NEGOCIO_INTEGRANTE, "id_integrante = ?", new String[]{row_integrantes.getString(0)});
                                                db.delete(TBL_CONYUGE_INTEGRANTE, "id_integrante = ?", new String[]{row_integrantes.getString(0)});
                                                db.delete(TBL_OTROS_DATOS_INTEGRANTE, "id_integrante = ?", new String[]{row_integrantes.getString(0)});
                                                db.delete(TBL_CROQUIS_GPO, "id_integrante = ?", new String[]{row_integrantes.getString(0)});
                                                db.delete(TBL_POLITICAS_PLD_INTEGRANTE, "id_integrante = ?", new String[]{row_integrantes.getString(0)});
                                                db.delete(TBL_DOCUMENTOS_INTEGRANTE, "id_integrante = ?", new String[]{row_integrantes.getString(0)});
                                                row_integrantes.moveToNext();
                                            }
                                            row_integrantes.close();

                                            db.delete(TBL_INTEGRANTES_GPO, "id_credito = ?", new String[]{row_credito.getString(0)});
                                            db.delete(TBL_CREDITO_GPO, "id = ?", new String[]{row_credito.getString(0)});
                                            row_credito.close();
                                            db.delete(TBL_SOLICITUDES, "id_solicitud = ?", new String[]{id_solcitud});
                                        }
                                        adapter.removeItem(position);
                                        dialog.dismiss();

                                    }
                                }, R.string.no, new Popups.DialogMessage() {
                                    @Override
                                    public void OnClickListener(AlertDialog dialog) {
                                        adapter.notifyDataSetChanged();
                                        dialog.dismiss();
                                    }
                                });
                        Objects.requireNonNull(confirm_borrar_soli_dlg.getWindow()).requestFeature(Window.FEATURE_NO_TITLE);
                        confirm_borrar_soli_dlg.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
                        confirm_borrar_soli_dlg.show();
                        dialog.dismiss();

                    }
                }, R.string.no, new Popups.DialogMessage() {
                    @Override
                    public void OnClickListener(AlertDialog dialog) {
                        adapter.notifyDataSetChanged();
                        dialog.dismiss();
                    }
                });
        Objects.requireNonNull(borrar_soli_dlg.getWindow()).requestFeature(Window.FEATURE_NO_TITLE);
        borrar_soli_dlg.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        borrar_soli_dlg.show();
    }

    private void GetClientesEnProceso(){
        SolicitudDao solicitudDao = new SolicitudDao(ctx);
        List<String> nombres = solicitudDao.showNombres(1);

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

    private void GetClientesCompletados(){
        SolicitudDao solicitudDao = new SolicitudDao(ctx);
        List<String> nombres = solicitudDao.showNombres(3);

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

    private void getEnProceso(){
        SolicitudDao solicitudDao = new SolicitudDao(ctx);

        String banderaIndividuales = session.getFiltrosEnProcesoOri().get(0);
        String banderaGrupales     = session.getFiltrosEnProcesoOri().get(1);
        String nombre              = session.getFiltrosEnProcesoOri().get(2);
        String banderaMenor45      = session.getFiltrosEnProcesoOri().get(3);

        List<Solicitud> solicitudes = solicitudDao.findAllByFilters(
                banderaMenor45,
                banderaGrupales,
                banderaIndividuales,
                nombre,
                1
        );

        rvOriginacion.setAdapter(null);

        if(solicitudes.size() > 0)
        {
            fillList(solicitudes);
        }
    }

    private void getCompletados(){
        SolicitudDao solicitudDao = new SolicitudDao(ctx);

        String banderaIndividuales = session.getFiltrosCompletadosOri().get(0);
        String banderaGrupales     = session.getFiltrosCompletadosOri().get(1);
        String nombre              = session.getFiltrosCompletadosOri().get(2);
        String banderaMenor45      = session.getFiltrosCompletadosOri().get(3);

        List<Solicitud> solicitudes = solicitudDao.findAllByFilters(
                banderaMenor45,
                banderaGrupales,
                banderaIndividuales,
                nombre,
                3
        );

        rvOriginacion.setAdapter(null);

        if(solicitudes.size() > 0)
        {
            fillList(solicitudes);
        }
    }

    private void fillList(List<Solicitud> solicitudes)
    {
        ClienteDao clienteDao = new ClienteDao(ctx);

        ArrayList<HashMap<Integer,String>> data = new ArrayList<>();
        for(Solicitud solicitud : solicitudes)
        {
            Cliente cliente = null;

            if(solicitud.getTipoSolicitud() == 1) cliente = clienteDao.findByIdSolicitud(solicitud.getIdSolicitud());

            HashMap<Integer, String> item = new HashMap();

            item.put(0, String.valueOf(solicitud.getIdSolicitud()));
            item.put(1, solicitud.getNombre());
            item.put(2, String.valueOf(solicitud.getTipoSolicitud()));
            item.put(3, String.valueOf(solicitud.getEstatus()));
            item.put(4, solicitud.getFechaTermino());
            item.put(5, solicitud.getFechaGuardado());
            item.put(6, String.valueOf(solicitud.getIdOriginacion()));
            if(cliente != null) item.put(7, cliente.getComentarioRechazo());//pendiente

            data.add(item);
        }

        adapter = new adapter_originacion(ctx, data, new adapter_originacion.Event() {
            @Override
            public void FichaOnClick(HashMap<Integer, String> item) {
                Intent i_solicitud;
                ProgressDialog dialog = ProgressDialog.show(SolicitudCredito.this, "",
                        "Cargando la información por favor espere...", true);
                dialog.setCancelable(false);
                dialog.show();
                switch (Integer.parseInt(item.get(2))){
                    case 1:
                        Log.e("Clic", "individual");
                        i_solicitud = new Intent(ctx, SolicitudCreditoInd.class);
                        i_solicitud.putExtra("is_new", false);
                        i_solicitud.putExtra("id_solicitud", item.get(0));
                        i_solicitud.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(i_solicitud);
                        dialog.dismiss();
                        break;
                    case 2:
                        i_solicitud = new Intent(ctx, SolicitudCreditoGpo.class);
                        i_solicitud.putExtra("is_new", false);
                        i_solicitud.putExtra("id_solicitud", item.get(0));
                        i_solicitud.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        Log.e("ID_SOLICITUD", item.get(0));
                        startActivity(i_solicitud);
                        dialog.dismiss();
                        break;
                }

            }
        });

        rvOriginacion.setAdapter(adapter);
        initSwipe();
    }

    @SuppressLint("ClickableViewAccessibility")
    private void Filtros () {
        int view = 0;

        int sizeH = 900;
        Activity activity = this;
        if (activity != null) {
            View decorateView = activity.getWindow().getDecorView();
            sizeH = (int) (decorateView.getHeight() / 2.0);
        }


        switch (seccion) {
            case 2:
                view = R.layout.sheet_dialog_filtros_proceso_ori;
                break;
            case 3:
                view = R.layout.sheet_dialog_filtros_completados_ori;
                break;
        }

        DialogPlus filtros_dg = DialogPlus.newDialog(context)
                .setContentHolder(new ViewHolder(view))
                .setGravity(Gravity.TOP)
                .setPadding(20,40,20,10)
                .setOnClickListener(new OnClickListener() {
                    @Override
                    public void onClick(DialogPlus dialog, View view) {
                        cont_proceso = 0;
                        cont_completados = 0;
                        HashMap<String, String> filtros = new HashMap<>();
                        InputMethodManager imm = (InputMethodManager) ctx.getSystemService(Context.INPUT_METHOD_SERVICE);

                        int id = view.getId();
                        if (id == R.id.btnFiltrar) {
                            switch (seccion) {
                                case 2:
                                    if (!aetNombre.getText().toString().trim().isEmpty()) {
                                        filtros.put("nombre_originacion_pro", aetNombre.getText().toString().trim());
                                        cont_proceso += 1;
                                    } else filtros.put("nombre_originacion_pro", "");

                                    if (cbInd.isChecked() && cbGpo.isChecked()) {
                                        filtros.put("tipo_originacion_ind_pro", "1");
                                        filtros.put("tipo_originacion_gpo_pro", "1");
                                        cont_proceso += 2;
                                    } else if (cbInd.isChecked()) {
                                        filtros.put("tipo_originacion_ind_pro", "1");
                                        filtros.put("tipo_originacion_gpo_pro", "0");
                                        cont_proceso += 1;
                                    } else if (cbGpo.isChecked()) {
                                        filtros.put("tipo_originacion_ind_pro", "0");
                                        filtros.put("tipo_originacion_gpo_pro", "1");
                                        cont_proceso += 1;
                                    } else {
                                        filtros.put("tipo_originacion_ind_pro", "0");
                                        filtros.put("tipo_originacion_gpo_pro", "0");
                                    }

                                    if (cbMenor45.isChecked()) {
                                        filtros.put("menor45_originacion_pro", "1");
                                        cont_proceso += 1;
                                    } else filtros.put("menor45_originacion_pro", "0");

                                    filtros.put("contador_originacion_pro", String.valueOf(cont_proceso));
                                    session.setFiltrosEnProcesoOri(filtros);

                                    imm.hideSoftInputFromWindow(view.getWindowToken(), 0);

                                    getEnProceso();

                                    break;
                                case 3:
                                    if (!aetNombre.getText().toString().trim().isEmpty()) {
                                        filtros.put("nombre_originacion_comp", aetNombre.getText().toString().trim());
                                        cont_completados += 1;
                                    } else filtros.put("nombre_originacion_comp", "");

                                    if (cbInd.isChecked() && cbGpo.isChecked()) {
                                        filtros.put("tipo_originacion_ind_comp", "1");
                                        filtros.put("tipo_originacion_gpo_comp", "1");
                                        cont_completados += 2;
                                    } else if (cbInd.isChecked()) {
                                        filtros.put("tipo_originacion_ind_comp", "1");
                                        filtros.put("tipo_originacion_gpo_comp", "0");
                                        cont_completados += 1;
                                    } else if (cbGpo.isChecked()) {
                                        filtros.put("tipo_originacion_ind_comp", "0");
                                        filtros.put("tipo_originacion_gpo_comp", "1");
                                        cont_completados += 1;
                                    } else {
                                        filtros.put("tipo_originacion_ind_comp", "0");
                                        filtros.put("tipo_originacion_gpo_comp", "0");
                                    }

                                    if (cbMenor45.isChecked()) {
                                        filtros.put("menor45_originacion_comp", "1");
                                        cont_completados += 1;
                                    } else filtros.put("menor45_originacion_comp", "0");

                                    filtros.put("contador_originacion_comp", String.valueOf(cont_completados));
                                    session.setFiltrosCompletadosOri(filtros);

                                    imm.hideSoftInputFromWindow(view.getWindowToken(), 0);

                                    getCompletados();

                                    break;
                            }
                            dialog.dismiss();
                        } else if (id == R.id.btnBorrar) {
                            switch (seccion) {
                                case 2:
                                    cbInd.setChecked(false);
                                    cbGpo.setChecked(false);
                                    cbMenor45.setChecked(false);
                                    aetNombre.setText("");
                                    aetNombre.setAdapter(adapterNombre);

                                    cont_proceso = 0;
                                    filtros = new HashMap<>();
                                    filtros.put("nombre_originacion_pro", "");
                                    filtros.put("tipo_originacion_ind_pro", "0");
                                    filtros.put("tipo_originacion_gpo_pro", "0");
                                    filtros.put("menor45_originacion_pro", "0");
                                    filtros.put("contador_originacion_pro", "0");
                                    session.setFiltrosEnProcesoOri(filtros);

                                    imm.hideSoftInputFromWindow(view.getWindowToken(), 0);

                                    getEnProceso();

                                    break;
                                case 3:
                                    cbInd.setChecked(false);
                                    cbGpo.setChecked(false);
                                    cbMenor45.setChecked(false);
                                    aetNombre.setText("");
                                    aetNombre.setAdapter(adapterNombre);

                                    cont_completados = 0;
                                    filtros = new HashMap<>();
                                    filtros.put("nombre_originacion_comp", "");
                                    filtros.put("tipo_originacion_ind_comp", "0");
                                    filtros.put("tipo_originacion_gpo_comp", "0");
                                    filtros.put("menor45_originacion_comp", "0");
                                    filtros.put("contador_originacion_comp", "0");
                                    session.setFiltrosCompletadosOri(filtros);

                                    imm.hideSoftInputFromWindow(view.getWindowToken(), 0);

                                    getCompletados();

                                    break;
                            }
                        }

                        setupBadge();

                    }
                })
                .setExpanded(true, sizeH)
                .create();

        switch (seccion){
            case 2:
                aetNombre   = filtros_dg.getHolderView().findViewById(R.id.aetNombre);
                cbInd       = filtros_dg.getHolderView().findViewById(R.id.cbInd);
                cbGpo       = filtros_dg.getHolderView().findViewById(R.id.cbGpo);
                cbMenor45   = filtros_dg.getHolderView().findViewById(R.id.cbMenor45);
                //cbMenor45.setChecked(true);

                aetNombre.setAdapter(adapterNombre);

                aetNombre.setOnTouchListener((v, event) -> {
                    aetNombre.showDropDown();
                    return false;
                });

                if (!session.getFiltrosEnProcesoOri().get(2).isEmpty()){
                    aetNombre.setText(session.getFiltrosEnProcesoOri().get(2));
                }

                if (session.getFiltrosEnProcesoOri().get(1).equals("1")){
                    cbGpo.setChecked(true);
                }

                if (session.getFiltrosEnProcesoOri().get(0).equals("1")){
                    cbInd.setChecked(true);
                }

                if (session.getFiltrosEnProcesoOri().get(3).equals("1")){
                    cbMenor45.setChecked(true);
                }

                break;
            case 3:
                aetNombre   = filtros_dg.getHolderView().findViewById(R.id.aetNombre);
                cbInd       = filtros_dg.getHolderView().findViewById(R.id.cbInd);
                cbGpo       = filtros_dg.getHolderView().findViewById(R.id.cbGpo);
                cbMenor45   = filtros_dg.getHolderView().findViewById(R.id.cbMenor45);
                //cbMenor45.setChecked(true);

                aetNombre.setAdapter(adapterNombre);

                aetNombre.setOnTouchListener((v, event) -> {
                    aetNombre.showDropDown();
                    return false;
                });

                if (!session.getFiltrosCompletadosOri().get(2).isEmpty()){
                    aetNombre.setText(session.getFiltrosCompletadosOri().get(2));
                }

                if (session.getFiltrosCompletadosOri().get(1).equals("1")){
                    cbGpo.setChecked(true);
                }

                if (session.getFiltrosCompletadosOri().get(0).equals("1")){
                    cbInd.setChecked(true);
                }

                if (session.getFiltrosCompletadosOri().get(3).equals("1")){
                    cbMenor45.setChecked(true);
                }

                break;
        }

        filtros_dg.show();

    }

    private void setupBadge(){
        switch (seccion){
            case 3:
                if (tvContFiltros != null) {
                    tvContFiltros.setText(String.valueOf(session.getFiltrosCompletadosOri().get(4)));
                    tvContFiltros.setVisibility(View.VISIBLE);
                }
                break;
            default:
                if (tvContFiltros != null) {
                    tvContFiltros.setText(String.valueOf(session.getFiltrosEnProcesoOri().get(4)));
                    tvContFiltros.setVisibility(View.VISIBLE);
                }
                break;
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_filtro, menu);

        final MenuItem menuItemInfo = menu.findItem(R.id.nvInfo);
        menuItemInfo.setVisible(false);

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
        int itemId = item.getItemId();
        if (itemId == android.R.id.home) {
            finish();
        } else if (itemId == R.id.nvFiltro) {
            Filtros();
        } else if (itemId == R.id.nvInfo) {
            Intent intentResumen = new Intent(ctx, ResumenActivity.class);
            startActivity(intentResumen);
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onResume() {
        super.onResume();

        switch(seccion) {
            case 2:
                rbEnProceso.setChecked(true);
                setupBadge();
                GetClientesEnProceso();
                getEnProceso();
                break;
            case 3:
                rbCompletados.setChecked(true);
                setupBadge();
                GetClientesCompletados();
                getCompletados();
                break;
        }

        llGpo.setEnabled(true);
        llInd.setEnabled(true);

    }
}
