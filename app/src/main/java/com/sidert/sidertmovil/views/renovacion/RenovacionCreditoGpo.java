package com.sidert.sidertmovil.views.renovacion;

import android.content.ContentValues;
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

import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.appcompat.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.gson.Gson;
import com.sidert.sidertmovil.R;
import com.sidert.sidertmovil.adapters.adapter_originacion;
import com.sidert.sidertmovil.database.DBhelper;
import com.sidert.sidertmovil.fragments.dialogs.dialog_renovacion_gpo;
import com.sidert.sidertmovil.fragments.dialogs.dialog_sending_solicitud_grupal;
import com.sidert.sidertmovil.models.ApiResponse;
import com.sidert.sidertmovil.models.MSolicitudRechazoGpo;
import com.sidert.sidertmovil.models.permiso.PermisoResponse;
import com.sidert.sidertmovil.models.solicitudes.SolicitudRen;
import com.sidert.sidertmovil.models.solicitudes.SolicitudRenDao;
import com.sidert.sidertmovil.models.solicitudes.solicitudgpo.SolicitudDetalleEstatusGpo;
import com.sidert.sidertmovil.models.solicitudes.solicitudgpo.renovacion.ConyugueIntegranteRen;
import com.sidert.sidertmovil.models.solicitudes.solicitudgpo.renovacion.ConyugueIntegranteRenDao;
import com.sidert.sidertmovil.models.solicitudes.solicitudgpo.renovacion.CreditoGpoRen;
import com.sidert.sidertmovil.models.solicitudes.solicitudgpo.renovacion.CreditoGpoRenDao;
import com.sidert.sidertmovil.models.solicitudes.solicitudgpo.renovacion.CroquisIntegranteRen;
import com.sidert.sidertmovil.models.solicitudes.solicitudgpo.renovacion.CroquisIntegranteRenDao;
import com.sidert.sidertmovil.models.solicitudes.solicitudgpo.renovacion.DocumentoIntegranteRen;
import com.sidert.sidertmovil.models.solicitudes.solicitudgpo.renovacion.DocumentoIntegranteRenDao;
import com.sidert.sidertmovil.models.solicitudes.solicitudgpo.renovacion.DomicilioIntegranteRen;
import com.sidert.sidertmovil.models.solicitudes.solicitudgpo.renovacion.DomicilioIntegranteRenDao;
import com.sidert.sidertmovil.models.solicitudes.solicitudgpo.renovacion.IntegranteGpoRen;
import com.sidert.sidertmovil.models.solicitudes.solicitudgpo.renovacion.IntegranteGpoRenDao;
import com.sidert.sidertmovil.models.solicitudes.solicitudgpo.renovacion.NegocioIntegranteRen;
import com.sidert.sidertmovil.models.solicitudes.solicitudgpo.renovacion.NegocioIntegranteRenDao;
import com.sidert.sidertmovil.models.solicitudes.solicitudgpo.renovacion.OtrosDatosIntegranteRen;
import com.sidert.sidertmovil.models.solicitudes.solicitudgpo.renovacion.OtrosDatosIntegranteRenDao;
import com.sidert.sidertmovil.models.solicitudes.solicitudgpo.renovacion.PoliticaPldIntegranteRen;
import com.sidert.sidertmovil.models.solicitudes.solicitudgpo.renovacion.PoliticaPldIntegranteRenDao;
import com.sidert.sidertmovil.models.solicitudes.solicitudgpo.renovacion.TelefonoIntegranteRen;
import com.sidert.sidertmovil.models.solicitudes.solicitudgpo.renovacion.TelefonoIntegranteRenDao;
import com.sidert.sidertmovil.models.solicitudes.solicitudind.originacion.Beneficiario;
import com.sidert.sidertmovil.services.beneficiario.BeneficiarioService;
import com.sidert.sidertmovil.services.permiso.IPermisoService;
import com.sidert.sidertmovil.services.solicitud.solicitudgpo.SolicitudGpoService;
import com.sidert.sidertmovil.utils.Constants;
import com.sidert.sidertmovil.utils.DatosCompartidos;
import com.sidert.sidertmovil.utils.ManagerInterface;
import com.sidert.sidertmovil.utils.Miscellaneous;
import com.sidert.sidertmovil.utils.NameFragments;
import com.sidert.sidertmovil.utils.Popups;
import com.sidert.sidertmovil.utils.RetrofitClient;
import com.sidert.sidertmovil.utils.Servicios_Sincronizado;
import com.sidert.sidertmovil.utils.SessionManager;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

import static com.sidert.sidertmovil.utils.Constants.CONTROLLER_API;;
import static com.sidert.sidertmovil.utils.Constants.CONTROLLER_SOLICITUDES;
import static com.sidert.sidertmovil.utils.Constants.ES_RENOVACION;
import static com.sidert.sidertmovil.utils.Constants.ID_SOLICITUD;
import static com.sidert.sidertmovil.utils.Constants.REQUEST_CODE_ADD_INTEGRANTE;
import static com.sidert.sidertmovil.utils.Constants.TBL_CONYUGE_INTEGRANTE_REN;
import static com.sidert.sidertmovil.utils.Constants.TBL_CREDITO_GPO_REN;
import static com.sidert.sidertmovil.utils.Constants.TBL_CROQUIS_GPO_REN;
import static com.sidert.sidertmovil.utils.Constants.TBL_DATOS_BENEFICIARIO;
import static com.sidert.sidertmovil.utils.Constants.TBL_DOCUMENTOS_INTEGRANTE_REN;
import static com.sidert.sidertmovil.utils.Constants.TBL_DOMICILIO_INTEGRANTE_REN;
import static com.sidert.sidertmovil.utils.Constants.TBL_INTEGRANTES_GPO_REN;
import static com.sidert.sidertmovil.utils.Constants.TBL_NEGOCIO_INTEGRANTE_REN;
import static com.sidert.sidertmovil.utils.Constants.TBL_OTROS_DATOS_INTEGRANTE_REN;
import static com.sidert.sidertmovil.utils.Constants.TBL_POLITICAS_PLD_INTEGRANTE_REN;
import static com.sidert.sidertmovil.utils.Constants.TBL_SOLICITUDES_REN;
import static com.sidert.sidertmovil.utils.Constants.TBL_TELEFONOS_INTEGRANTE_REN;
import static com.sidert.sidertmovil.utils.Constants.question;
import static com.sidert.sidertmovil.utils.Constants.warning;

import org.json.JSONArray;
import org.json.JSONObject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**Clase para mostrar los datos del credito grupal y de integrantes*/
public class RenovacionCreditoGpo extends AppCompatActivity implements dialog_renovacion_gpo.OnCompleteListener {

    private Context ctx;

    private Toolbar TBmain;

    private DBhelper dBhelper;
    private SQLiteDatabase db;

    private RecyclerView rvIntegrantes;
    private FloatingActionButton fabAgregar;

    private adapter_originacion adapter;

    private String nombre_gpo;
    private String plazo;
    private String periodicidad;
    private String fecha_desembolso;
    private String dia_desembolso;
    private String hora_visita;
    private String observaciones;
    private String fecha_vencimiento;
    private boolean is_edit = false;

    private TextView tvInfoCredito;

    private long id_solicitud = 0;
    private long id_credito = 0;
    private boolean is_new = false;

    private Paint p = new Paint();

    private final int MENU_INDEX_DEVMODE = 3;
    private final int MENU_INDEX_DESBLOQUEAR = 2;
    private final int MENU_INDEX_UPDATE_ESTATUS = 1;
    private final int MENU_INDEX_ENVIAR = 0;
    private boolean modoSuperUsuario = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_renovacion_credito_gpo);

        ctx = this;

        dBhelper = new DBhelper(ctx);
        db = dBhelper.getWritableDatabase();

        TBmain = findViewById(R.id.TBmain);
        setSupportActionBar(TBmain);

        tvInfoCredito = findViewById(R.id.tvInfoCredito);
        rvIntegrantes = findViewById(R.id.rvIntegrantes);
        fabAgregar = findViewById(R.id.fabAgregar);

        rvIntegrantes.setLayoutManager(new LinearLayoutManager(ctx));
        rvIntegrantes.setHasFixedSize(false);

        tvInfoCredito.setOnClickListener(tvInfoCredito_OnClick);
        fabAgregar.setOnClickListener(fabAgregar_OnClick);

        //Intent intent = new Intent(ctx, RenovarIntegrante.class);

        /**Validacion para saber si tendra que abrir el dialog de datos del credito*/
        if (getIntent().getBooleanExtra("is_new",true)) {
            is_edit = true;
            is_new = true;
            nombre_gpo = getIntent().getStringExtra("nombre");
            id_solicitud = Long.parseLong(getIntent().getStringExtra("id_solicitud"));
            TBmain.setSubtitle(nombre_gpo);
            /**Funcion para abrir un dialogFragment para completar los datos del credito*/
            openInfoOriginacion();
        }
        else{/**En caso de que ya habian guardado los datos del credito*/
            is_new = false;
            /**Obtiene datos que se mandaron entre clases para variable globañ*/
            id_solicitud = Long.parseLong(getIntent().getStringExtra("id_solicitud"));

            /**Obtiene el id de los datos del credito porque con ese ID se relacionan los integrantes*/
            Cursor row = dBhelper.getRecords(TBL_CREDITO_GPO_REN, " WHERE id_solicitud = ?", "", new String[]{String.valueOf(id_solicitud)});
            row.moveToFirst();
            id_credito = Long.parseLong(row.getString(0));
            String sdato1 = row.getString(1);
            TBmain.setSubtitle(row.getString(2));
            row.close();
            /**Funcion para obtener los integrantes*/

            initComponents(sdato1);
        }
        DatosCompartidos.getInstance().setId_solicitud(id_solicitud);
        DatosCompartidos.getInstance().setCredito_id(id_credito);
       // Intent intent = new Intent(ctx, RenovarIntegrante.class);
       // intent.putExtra("id_solicitud", String.valueOf(id_solicitud));
       // startActivity(intent);

    }


    /**Evento para abrir un dialogFragment para mostrar los datos del credito */
    private View.OnClickListener tvInfoCredito_OnClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            openInfoOriginacion();
        }
    };

    /**Evento para agregar un nuevo integrante*/
    private View.OnClickListener fabAgregar_OnClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            /**Se realiza un consulta para obtener el total de integrantes que hay registrados*/
            Cursor row = dBhelper.customSelect(TBL_INTEGRANTES_GPO_REN, "COUNT (cargo)", " WHERE id_credito = ?", "", new String[]{String.valueOf(id_credito)});
            row.moveToFirst();
            /**Si el total de registrados es menor a 40 puede agregar un intengrante mas*/
            if (row.getInt(0) < 40) {
                Intent i_renovar_integrante = new Intent(ctx, RenovarIntegrante.class);
                i_renovar_integrante.putExtra("id_credito", String.valueOf(id_credito));
                i_renovar_integrante.putExtra("periodicidad", Miscellaneous.GetPeriodicidad(periodicidad));
                startActivityForResult(i_renovar_integrante, REQUEST_CODE_ADD_INTEGRANTE);
            }
            else
                Toast.makeText(ctx, "Ha superado el límite de integrantes por grupo", Toast.LENGTH_SHORT).show();
        }
    };

    /**Funcion para abrir un dialogFrament para mostrar o editar datos del credito*/
    private void openInfoOriginacion() {
        dialog_renovacion_gpo renovacion_gpo = new dialog_renovacion_gpo();
        renovacion_gpo.setCancelable(false);
        Bundle b = new Bundle();
        /**Valida si ya no tiene que editar y solo es para visualizar los datos*/
        if (!is_new) {
            b.putBoolean("is_edit", is_edit);
            b.putString("nombre", nombre_gpo);
            b.putString("plazo", plazo);
            b.putString("periodicidad", periodicidad);
            b.putString("fecha_desembolso", fecha_desembolso);
            b.putString("dia_desembolso", dia_desembolso);
            b.putString("hora_visita", hora_visita);
            b.putString("observaciones", observaciones);
            b.putString("id_solicitud", String.valueOf(id_solicitud));
            renovacion_gpo.setArguments(b);
        }
        else {/**Datos para saber que tiene que agregar los datos del credito*/
            b.putBoolean("is_edit", true);
            b.putString("nombre", nombre_gpo);
            b.putString("id_solicitud", String.valueOf(id_solicitud));
        }
        renovacion_gpo.setArguments(b);
        renovacion_gpo.show(getSupportFragmentManager(), NameFragments.DIALOGORIGINACIONGPO);
    }

    /**Funcion para obtener datos del credito*/
    private void initComponents(String idSolicitud){
        String sql = "" +
                "SELECT c.*, s.estatus " +
                "FROM " + TBL_CREDITO_GPO_REN + " AS c " +
                "INNER JOIN " + TBL_SOLICITUDES_REN + " AS s ON c.id_solicitud = s.id_solicitud " +
                "WHERE c.id_solicitud = ?";
        Cursor row = db.rawQuery(sql, new String[]{idSolicitud});
        //Cursor row = dBhelper.getRecords(TBL_CREDITO_GPO, " WHERE id_solicitud = ?", "",new String[]{idSolicitud});
        row .moveToFirst();
        id_credito = row.getInt(0);
        nombre_gpo = row.getString(2);
        plazo = row.getString(3);
        periodicidad = row.getString(4);
        fecha_desembolso = row.getString(5);
        dia_desembolso = row.getString(6);
        hora_visita = row.getString(7);
        observaciones = row.getString(9);

        is_edit = row.getInt(12) <= 1; /**Valida si el estatus del credito esta en estatus parcial*/
        deshabilitarCampos();

        row.close();

        /**Obtiene los integrantes relacionados con el id del credito*/
        Cursor row_integrantes = dBhelper.getRecords(TBL_INTEGRANTES_GPO_REN, " WHERE id_credito = ?", " ORDER BY nombre ASC", new String[]{String.valueOf(id_credito)});
        if (row_integrantes.getCount() > 0){
            row_integrantes.moveToFirst();
            ArrayList<HashMap<Integer,String>> data = new ArrayList<>();
            for(int i = 0; i < row_integrantes.getCount(); i++){
                HashMap<Integer, String> item = new HashMap();
                Log.e("id"+i, row_integrantes.getString(21));
                item.put(0,row_integrantes.getString(0));
                String nombre = row_integrantes.getString(3) + " " +row_integrantes.getString(4) + " " + row_integrantes.getString(5);
                item.put(1, nombre.trim().toUpperCase());
                item.put(2, "2");
                item.put(3, row_integrantes.getString(21));
                item.put(4, "");
                item.put(5, "");
                item.put(6, row_integrantes.getString(1));
                item.put(7, row_integrantes.getString(20));
                item.put(8, row_integrantes.getString(2));
                data.add(item);
                row_integrantes.moveToNext();
            }

            /**Agrega los integrantes al adaptador*/
            adapter = new adapter_originacion(ctx, data, new adapter_originacion.Event() {
                @Override
                public void FichaOnClick(HashMap<Integer, String> item) {
                    Intent i_integrante = new Intent(ctx, RenovarIntegrante.class);
                    i_integrante.putExtra("is_new", false);
                    i_integrante.putExtra("id_integrante", item.get(0));
                    i_integrante.putExtra("id_credito", item.get(6));
                    i_integrante.putExtra("periodicidad", Miscellaneous.GetPeriodicidad(periodicidad));
                    i_integrante.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(i_integrante);
                }
            });

            rvIntegrantes.setAdapter(adapter);
            initSwipe();
        }
        row_integrantes.close();

    }

    /**Funcion para mostrar u ocultar le menu*/
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_enviar_datos, menu);
        //if (!is_edit) {
            menu.getItem(MENU_INDEX_ENVIAR).setVisible(is_edit);
        //}

        SolicitudRenDao solicitudRenDao = new SolicitudRenDao(ctx);
        SolicitudRen solicitudRen = solicitudRenDao.findByIdSolicitud(Integer.valueOf(String.valueOf(id_solicitud)));

        if (TBmain.getMenu().size() > 1) {
            if (solicitudRen != null && solicitudRen.getEstatus() > 1) {
                TBmain.getMenu().getItem(MENU_INDEX_UPDATE_ESTATUS).setVisible(true);
            }
            else
            {
                TBmain.getMenu().getItem(MENU_INDEX_UPDATE_ESTATUS).setVisible(false);
            }
        }

        menu.getItem(MENU_INDEX_DEVMODE).setVisible(modoSuperUsuario);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;
            case R.id.devmode:
                enviarJSONObjects();
                //senDataBeneficiarioRen(id_solicitud);
                break;
            case R.id.desbloquear:
                if (modoSuperUsuario) {
                    desactivarModoSuper();
                    deshabilitarSolicitud();
                }
                else activarModoSuper();
                break;
            case R.id.updateEstatus:
                obtenerEstatusSolicitud();
                break;
            case R.id.enviar:
                sendSolicitud();
                //senDataBeneficiarioRen(id_solicitud);
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    /**Funcion mostrar un mensaje dinamico*/
    private void Mensaje(String mess){
        final AlertDialog solicitud;
        solicitud = Popups.showDialogMessage(ctx, warning,
                mess, R.string.accept, new Popups.DialogMessage() {
                    @Override
                    public void OnClickListener(AlertDialog dialog) {
                        dialog.dismiss();
                    }
                });
        solicitud.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        solicitud.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        solicitud.show();
    }

    @Override
    protected void onResume() {
        super.onResume();
        initComponents(String.valueOf(id_solicitud));
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
                    BorrarSolicitud(adapter.getItem(position).get(0), position);
                } else {
                    BorrarSolicitud(adapter.getItem(position).get(0), position);
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
        helper.attachToRecyclerView(rvIntegrantes);
    }

    private void BorrarSolicitud (final String id_integrante, final int position){
        AlertDialog borrar_soli_dlg = Popups.showDialogConfirm(ctx, question,
            R.string.borrar_solicitud, R.string.yes, new Popups.DialogMessage() {
                @Override
                public void OnClickListener(AlertDialog dialog) {
                    AlertDialog confirm_borrar_soli_dlg = Popups.showDialogConfirm(ctx, question,
                            R.string.confirm_borrar_solicitud, R.string.yes, new Popups.DialogMessage() {
                                @Override
                                public void OnClickListener(AlertDialog dialog) {

                                    db.delete(TBL_INTEGRANTES_GPO_REN, "id = ?", new String[]{id_integrante});
                                    db.delete(TBL_TELEFONOS_INTEGRANTE_REN, "id_integrante = ?", new String[]{id_integrante});
                                    db.delete(TBL_DOMICILIO_INTEGRANTE_REN, "id_integrante = ?", new String[]{id_integrante});
                                    db.delete(TBL_NEGOCIO_INTEGRANTE_REN, "id_integrante = ?", new String[]{id_integrante});
                                    db.delete(TBL_CONYUGE_INTEGRANTE_REN, "id_integrante = ?", new String[]{id_integrante});
                                    db.delete(TBL_OTROS_DATOS_INTEGRANTE_REN, "id_integrante = ?", new String[]{id_integrante});
                                    db.delete(TBL_CROQUIS_GPO_REN, "id_integrante = ?", new String[]{id_integrante});
                                    db.delete(TBL_POLITICAS_PLD_INTEGRANTE_REN, "id_integrante = ?", new String[]{id_integrante});
                                    db.delete(TBL_DOCUMENTOS_INTEGRANTE_REN, "id_integrante = ?", new String[]{id_integrante});

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

    /**Funcion que recibe los datos del credito del dialogFragment*/
    @Override
    public void onComplete(String plazo, String periodicidad, String fecha, String dia, String hora, String observaciones, Boolean isEdit) {
        if (plazo != null && periodicidad != null) {/**Si el usuario guardo los datos del credito podrá continuar con el registro de integrantes*/
            is_new = false;

            this.plazo = plazo;
            this.periodicidad = periodicidad;
            this.fecha_desembolso = fecha;
            this.dia_desembolso = dia;
            this.hora_visita = hora;
            this.observaciones = observaciones;
            is_edit = isEdit;

        }
        else if (plazo == null ) {/**En caso de no guardar los datos del credito se cierra la vista*/
            finish();
        }
    }

    private void activarModoSuper()
    {
        final AlertDialog loadingDesbloqueo = Popups.showLoadingDialog(ctx, R.string.please_wait, R.string.loading_info);
        loadingDesbloqueo.show();

        SessionManager session = new SessionManager(ctx);
        IPermisoService permisoService = new RetrofitClient().newInstance(ctx).create(IPermisoService.class);
        Call<PermisoResponse> call = permisoService.isSuperEnabled("Bearer " + session.getUser().get(7));

        call.enqueue(new Callback<PermisoResponse>() {
            @Override
            public void onResponse(Call<PermisoResponse> call, Response<PermisoResponse> response) {
                PermisoResponse permisoResponse;

                switch (response.code()) {
                    case 200:
                        permisoResponse = response.body();
                        loadingDesbloqueo.dismiss();
                        if (permisoResponse.getData() != null) {
                            modoSuperUsuario = true;
                            TBmain.getMenu().getItem(MENU_INDEX_DESBLOQUEAR).setIcon(ContextCompat.getDrawable(ctx, R.drawable.ic_baseline_lock_open_24_white));
                            TBmain.getMenu().getItem(MENU_INDEX_DEVMODE).setVisible(modoSuperUsuario);

                            if (habilitarSolicitud()) {
                                habilitarCampos();
                                Toast.makeText(ctx, "Modo super usuario activado", Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(ctx, "¡La solicitud no pudo habilitarse en modo super usuario!", Toast.LENGTH_SHORT).show();
                            }

                        } else {
                            Toast.makeText(ctx, permisoResponse.getMensaje(), Toast.LENGTH_SHORT).show();
                        }
                        break;
                    default:
                        loadingDesbloqueo.dismiss();
                        Toast.makeText(ctx, response.message(), Toast.LENGTH_SHORT).show();
                        break;
                }
            }

            @Override
            public void onFailure(Call<PermisoResponse> call, Throwable t) {
                loadingDesbloqueo.dismiss();
                Log.e("AQUI ERROR", t.getMessage());
                Toast.makeText(ctx, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void desactivarModoSuper()
    {
        modoSuperUsuario = false;
        deshabilitarCampos();
        TBmain.getMenu().getItem(MENU_INDEX_DESBLOQUEAR).setIcon(ContextCompat.getDrawable(ctx, R.drawable.ic_baseline_lock_24_white));
        TBmain.getMenu().getItem(MENU_INDEX_DEVMODE).setVisible(modoSuperUsuario);
    }

    private void habilitarCampos()
    {
        TBmain.getMenu().getItem(MENU_INDEX_ENVIAR).setVisible(true);
        TBmain.getMenu().getItem(MENU_INDEX_UPDATE_ESTATUS).setVisible(false);
    }

    private void deshabilitarCampos()
    {
        SolicitudRenDao solicitudRenDao = new SolicitudRenDao(ctx);
        SolicitudRen solicitudRen = solicitudRenDao.findByIdSolicitud(Integer.valueOf(String.valueOf(id_solicitud)));

        if (solicitudRen != null && solicitudRen.getEstatus() > 1) {
            if (TBmain.getMenu().size() > 1)
                TBmain.getMenu().getItem(MENU_INDEX_UPDATE_ESTATUS).setVisible(true);
        }
        else {
            if (TBmain.getMenu().size() > 1)
                TBmain.getMenu().getItem(MENU_INDEX_UPDATE_ESTATUS).setVisible(false);
        }

        if (!is_edit) {
            if(TBmain.getMenu().size() > 0) TBmain.getMenu().getItem(MENU_INDEX_ENVIAR).setVisible(false);
            fabAgregar.hide();
        }
    }

    private boolean habilitarSolicitud()
    {
        boolean solicitudActivada = false;

        if (id_solicitud <= 0) return solicitudActivada;

        SolicitudRenDao solicitudRenDao = new SolicitudRenDao(ctx);
        SolicitudRen solicitudRen = solicitudRenDao.findByIdSolicitud(Integer.valueOf(String.valueOf(id_solicitud)));

        if (solicitudRen != null) {
            CreditoGpoRenDao creditoGpoRenDao = new CreditoGpoRenDao(ctx);
            CreditoGpoRen creditoGpoRen = creditoGpoRenDao.findByIdSolicitud(solicitudRen.getIdSolicitud());

            if(creditoGpoRen != null)
            {
                IntegranteGpoRenDao integranteGpoRenDao = new IntegranteGpoRenDao(ctx);
                List<IntegranteGpoRen> integranteGpoRenList = integranteGpoRenDao.findAllByIdCredito(creditoGpoRen.getId());

                if(integranteGpoRenList.size() > 0)
                {
                    for(IntegranteGpoRen iGpoRen : integranteGpoRenList)
                    {
                        iGpoRen.setEstatusCompletado(0);
                        iGpoRen.setEstatusRechazo(0);
                        integranteGpoRenDao.updateEstatus(iGpoRen);
                        integranteGpoRenDao.saveEstatus(iGpoRen);
                    }
                }

                solicitudRen.setEstatus(0);
                solicitudRenDao.updateEstatus(solicitudRen);
                solicitudActivada = true;
            }
        }

        return solicitudActivada;
    }

    private boolean deshabilitarSolicitud()
    {
        TBmain.getMenu().getItem(MENU_INDEX_ENVIAR).setVisible(false);
        boolean solicitudBloqueada = false;

        if (id_solicitud <= 0) return solicitudBloqueada;

        SolicitudRenDao solicitudRenDao = new SolicitudRenDao(ctx);
        SolicitudRen solicitudRen = solicitudRenDao.findByIdSolicitud(Integer.valueOf(String.valueOf(id_solicitud)));

        if (solicitudRen != null) {
            CreditoGpoRenDao creditoGpoRenDao = new CreditoGpoRenDao(ctx);
            CreditoGpoRen creditoGpoRen = creditoGpoRenDao.findByIdSolicitud(solicitudRen.getIdSolicitud());

            if(creditoGpoRen != null)
            {
                IntegranteGpoRenDao integranteGpoRenDao = new IntegranteGpoRenDao(ctx);
                List<IntegranteGpoRen> integranteGpoRenList = integranteGpoRenDao.findAllByIdCredito(creditoGpoRen.getId());

                if(integranteGpoRenList.size() > 0)
                {
                    for(IntegranteGpoRen iGpoRen : integranteGpoRenList)
                    {
                        iGpoRen.setEstatusCompletado(2);
                        iGpoRen.setEstatusRechazo(0);
                        integranteGpoRenDao.updateEstatus(iGpoRen);
                        integranteGpoRenDao.saveEstatus(iGpoRen);
                    }
                }

                solicitudRen.setEstatus(2);
                solicitudRenDao.updateEstatus(solicitudRen);
                solicitudBloqueada = true;
            }
        }

        return solicitudBloqueada;
    }

    private void enviarJSONObjects()
    {
        JSONObject json_solicitud = new JSONObject();
        SolicitudRenDao solicitudRenDao = new SolicitudRenDao(ctx);
        SolicitudRen solicitudRen = solicitudRenDao.findByIdSolicitud(Integer.valueOf(String.valueOf(id_solicitud)));

        if(solicitudRen != null)
        {
            try {
                json_solicitud.put(SolicitudRen.TBL, solicitudRen);

                CreditoGpoRenDao creditoGpoRenDao = new CreditoGpoRenDao(ctx);
                CreditoGpoRen creditoGpoRen = creditoGpoRenDao.findByIdSolicitud(solicitudRen.getIdSolicitud());

                if(creditoGpoRen != null)
                {
                    json_solicitud.put(CreditoGpoRen.TBL, creditoGpoRen);

                    IntegranteGpoRenDao integranteGpoRenDao = new IntegranteGpoRenDao(ctx);
                    List<IntegranteGpoRen> integranteGpoRenList = integranteGpoRenDao.findAllByIdCredito(creditoGpoRen.getId());

                    JSONArray json_integrantes = new JSONArray();

                    if(integranteGpoRenList.size() > 0)
                    {
                        for(IntegranteGpoRen iGpoRen : integranteGpoRenList)
                        {
                            JSONObject json_integrante = new JSONObject();
                            json_integrante.put(IntegranteGpoRen.TBL, iGpoRen);

                            TelefonoIntegranteRenDao telefonoIntegranteRenDao = new TelefonoIntegranteRenDao(ctx);
                            TelefonoIntegranteRen telefonoIntegranteRen = telefonoIntegranteRenDao.findByIdIntegrante(iGpoRen.getId());
                            if(telefonoIntegranteRen != null) json_integrante.put(TelefonoIntegranteRen.TBL, telefonoIntegranteRen);

                            DomicilioIntegranteRenDao domicilioIntegranteRenDao = new DomicilioIntegranteRenDao(ctx);
                            DomicilioIntegranteRen domicilioIntegranteRen = domicilioIntegranteRenDao.findByIdIntegrante(Long.valueOf(iGpoRen.getId()));
                            if(domicilioIntegranteRen != null) json_integrante.put(DomicilioIntegranteRen.TBL, domicilioIntegranteRen);

                            NegocioIntegranteRenDao negocioIntegranteRenDao = new NegocioIntegranteRenDao(ctx);
                            NegocioIntegranteRen negocioIntegranteRen = negocioIntegranteRenDao.findByIdIntegrante(iGpoRen.getId());
                            if(negocioIntegranteRen != null) json_integrante.put(NegocioIntegranteRen.TBL, negocioIntegranteRen);

                            ConyugueIntegranteRenDao conyugueIntegranteRenDao = new ConyugueIntegranteRenDao(ctx);
                            ConyugueIntegranteRen conyugueIntegranteRen = conyugueIntegranteRenDao.findByIdIntegrante(iGpoRen.getId());
                            if(conyugueIntegranteRen != null) json_integrante.put(ConyugueIntegranteRen.TBL, conyugueIntegranteRenDao);

                            OtrosDatosIntegranteRenDao otrosDatosIntegranteRenDao = new OtrosDatosIntegranteRenDao(ctx);
                            OtrosDatosIntegranteRen otrosDatosIntegranteRen = otrosDatosIntegranteRenDao.findByIdIntegrante(iGpoRen.getId());
                            if(otrosDatosIntegranteRen != null) json_integrante.put(OtrosDatosIntegranteRen.TBL, otrosDatosIntegranteRen);

                            CroquisIntegranteRenDao croquisDao = new CroquisIntegranteRenDao(ctx);
                            CroquisIntegranteRen croquis = croquisDao.findByIdIntegrante(iGpoRen.getId());
                            if(croquis != null) json_integrante.put(CroquisIntegranteRen.TBL, croquis);

                            PoliticaPldIntegranteRenDao politicaPldIntegranteRenDao = new PoliticaPldIntegranteRenDao(ctx);
                            PoliticaPldIntegranteRen politicaPldIntegranteRen = politicaPldIntegranteRenDao.findByIdIntegrante(iGpoRen.getId());
                            if(politicaPldIntegranteRen != null) json_integrante.put(politicaPldIntegranteRen.TBL, politicaPldIntegranteRen);

                            DocumentoIntegranteRenDao documentoIntegranteRenDao = new DocumentoIntegranteRenDao(ctx);
                            DocumentoIntegranteRen documentoIntegranteRen = documentoIntegranteRenDao.findByIdIntegrante(iGpoRen.getId());
                            if(documentoIntegranteRen != null) json_integrante.put(DocumentoIntegranteRen.TBL, documentoIntegranteRen);

                            json_integrantes.put(json_integrante);
                        }

                        json_solicitud.put("integrantes", json_integrantes);
                    }
                }
            }
            catch (Exception e) {
                e.printStackTrace();
                Toast.makeText(ctx, e.getMessage(), Toast.LENGTH_SHORT).show();
            }

            SessionManager session = new SessionManager(ctx);

            Log.e("AQUI", new Gson().toJson(json_solicitud));

            final AlertDialog loadingSendData = Popups.showLoadingDialog(ctx, R.string.please_wait, R.string.loading_info);
            loadingSendData.show();

            SolicitudGpoService solicitudGpoService = new RetrofitClient().newInstance(ctx).create(SolicitudGpoService.class);
            Call<ApiResponse> call = solicitudGpoService.jsonRenovacionGpo("Bearer " + session.getUser().get(7), new Gson().toJson(json_solicitud));

            call.enqueue(new Callback<ApiResponse>() {
                @Override
                public void onResponse(Call<ApiResponse> call, Response<ApiResponse> response) {
                    ApiResponse apiResponse;
                    Log.e("AQUI OTRO", "code: " + response.code());
                    switch (response.code()) {
                        case 200:
                            apiResponse = response.body();
                            loadingSendData.dismiss();

                            if (apiResponse.getError() == null) {
                                Toast.makeText(ctx, "¡Solicitud compartida!", Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(ctx, apiResponse.getMensaje(), Toast.LENGTH_SHORT).show();
                            }
                            break;
                        default:
                            loadingSendData.dismiss();
                            Toast.makeText(ctx, response.toString(), Toast.LENGTH_SHORT).show();
                            break;
                    }
                }

                @Override
                public void onFailure(Call<ApiResponse> call, Throwable t) {
                    loadingSendData.dismiss();
                    t.printStackTrace();
                    Log.e("AQUI ERROR", t.getMessage());
                    Toast.makeText(ctx, t.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

    private void sendSolicitud()
    {
        if(esSolicitudValida())
        {
            ContentValues cv = new ContentValues();
            cv.put("estatus_completado", 1);

            db.update(TBL_CREDITO_GPO_REN, cv, "id = ?", new String[]{String.valueOf(id_credito)});

            ContentValues cv_solicitud = new ContentValues();
            cv_solicitud.put("estatus", 1);
            cv_solicitud.put("fecha_termino", Miscellaneous.ObtenerFecha("timestamp"));

            db.update(TBL_SOLICITUDES_REN, cv_solicitud, "id_solicitud = ?" , new String[]{String.valueOf(id_solicitud)});

            dialog_sending_solicitud_grupal dialogSendSI = new dialog_sending_solicitud_grupal();
            Bundle b = new Bundle();
            b.putLong(ID_SOLICITUD, id_solicitud);
            b.putBoolean(ES_RENOVACION, true);
            dialogSendSI.setArguments(b);
            dialogSendSI.setCancelable(false);
            dialogSendSI.show(getSupportFragmentManager(), NameFragments.DIALOGSENDINGSOLICITUDGRUPAL);
        }
    }

    private boolean esSolicitudValida()
    {
        boolean flag = false;

        Cursor row_credito = dBhelper.getRecords(TBL_INTEGRANTES_GPO_REN, " WHERE id_credito = ?", "", new String[]{String.valueOf(id_credito)});

        if (row_credito.getCount() > 3){
            Cursor row_cargo = dBhelper.customSelect(TBL_INTEGRANTES_GPO_REN, "DISTINCT (cargo)", " WHERE id_credito = ? AND cargo <> 4", "", new String[]{String.valueOf(id_credito)});

            if (row_cargo.getCount() == 3){
                Cursor row_reunion = dBhelper.customSelect(TBL_OTROS_DATOS_INTEGRANTE_REN + " AS o", "casa_reunion", " INNER JOIN " + TBL_CREDITO_GPO_REN + " AS c ON c.id = i.id_credito INNER JOIN "+TBL_INTEGRANTES_GPO_REN + " AS i ON i.id = o.id_integrante WHERE c.id = ? AND o.casa_reunion = 1", "", new String[]{String.valueOf(id_credito)});
                Cursor row_total = dBhelper.customSelect(TBL_INTEGRANTES_GPO_REN , "SUM (CASE WHEN estatus_completado = 1 THEN 1 ELSE 0 END) AS completadas, SUM (CASE WHEN estatus_completado = 0 THEN 1 ELSE 0 END) AS pendientes", " WHERE id_credito = ?"," GROUP BY id_credito", new String[]{String.valueOf(id_credito)});
                row_total.moveToFirst();

                if (row_total.getInt(1) == 0){
                    flag = true;
                }
                else
                    Mensaje("Existen integrantes pedientes por guardar");
            }
            else
                Mensaje("Falta elegir al comité del grupo");
        }
        else
            Mensaje("No cuenta con la cantidad de integrantes para formar un grupo");

        return flag;
    }

    private void obtenerEstatusSolicitud()
    {
        final AlertDialog loadingEstatus = Popups.showLoadingDialog(ctx, R.string.please_wait, R.string.loading_info);
        loadingEstatus.show();

        SolicitudRenDao solicitudRenDao = new SolicitudRenDao(ctx);
        SolicitudRen solicitudRen = solicitudRenDao.findByIdSolicitud(Integer.valueOf(String.valueOf(id_solicitud)));

        if(solicitudRen != null && solicitudRen.getEstatus() > 1)
        {
            SessionManager session = new SessionManager(ctx);
            SolicitudGpoService solicitudGpoService = new RetrofitClient().newInstance(ctx).create(SolicitudGpoService.class);
            Call<List<SolicitudDetalleEstatusGpo>> call = solicitudGpoService.showEstatusSolicitudes("Bearer " + session.getUser().get(7));

            call.enqueue(new Callback<List<SolicitudDetalleEstatusGpo>>() {
                @Override
                public void onResponse(Call<List<SolicitudDetalleEstatusGpo>> call, Response<List<SolicitudDetalleEstatusGpo>> response) {
                    switch (response.code()) {
                        case 200:
                            List<SolicitudDetalleEstatusGpo> solicitudes = response.body();

                            for (SolicitudDetalleEstatusGpo se : solicitudes) {
                                if (se.getTipoSolicitud() != 1) {
                                    CreditoGpoRenDao creditoDao = new CreditoGpoRenDao(ctx);
                                    IntegranteGpoRenDao integranteDao = new IntegranteGpoRenDao(ctx);

                                    CreditoGpoRen credito = null;
                                    IntegranteGpoRen integrante = null;
                                    SolicitudRen solicitudTemp = null;

                                    integrante = integranteDao.findByIdSolicitudIntegrante(se.getIdSolicitudIntegrante());

                                    if(integrante != null) credito = creditoDao.findById(integrante.getIdCredito());
                                    if(credito != null) solicitudTemp = solicitudRenDao.findByIdSolicitud(credito.getIdSolicitud());

                                    if(solicitudTemp != null ){
                                        solicitudTemp.setIdOriginacion(se.getId());
                                        solicitudRenDao.updateEstatus(solicitudTemp);
                                    }

                                    if(solicitudTemp != null && Integer.compare(solicitudTemp.getIdSolicitud(), solicitudRen.getIdSolicitud()) == 0) {
                                        String comentario = "";

                                        if (se.getSolicitudEstadoIdIntegrante() == 1) {
                                            comentario = "EN REVISIÓN";
                                        } else if (se.getSolicitudEstadoIdIntegrante() == 3) {
                                            comentario = "VALIDADO";
                                        } else {
                                            //comentario = se.getComentarioAdmin();
                                        }

                                        Log.e("AQUI REN", String.valueOf(se.getId()));

                                        if(se.getSolicitudEstadoIdSolicitud() == 2) solicitudRen.setEstatus(5);
                                        if(se.getSolicitudEstadoIdSolicitud() == 3) solicitudRen.setEstatus(3);

                                        integrante.setComentarioRechazo(comentario);
                                        integranteDao.updateEstatus(integrante);

                                        solicitudRen.setIdOriginacion(se.getId());
                                        solicitudRenDao.updateEstatus(solicitudRen);
                                    }
                                }
                            }
                            obtenerRechazo(loadingEstatus, solicitudRen);
                            break;
                        default:
                            Log.e("AQUI ", response.message());
                            obtenerRechazo(loadingEstatus, solicitudRen);
                            break;
                    }
                }

                @Override
                public void onFailure(Call<List<SolicitudDetalleEstatusGpo>> call, Throwable t) {
                    obtenerRechazo(loadingEstatus, solicitudRen);
                    Log.e("Error", "failAGF" + t.getMessage());
                }
            });
        }
    }

    private void obtenerRechazo(AlertDialog alert, SolicitudRen solicitud)
    {
        SessionManager session = new SessionManager(ctx);
        SolicitudRenDao solicitudRenDao = new SolicitudRenDao(ctx);

        ManagerInterface api = new RetrofitClient().generalRF(CONTROLLER_SOLICITUDES, ctx).create(ManagerInterface.class);
        Call<List<MSolicitudRechazoGpo>> call = api.getSolicitudRechazoGpo("Bearer "+ session.getUser().get(7));

        call.enqueue(new Callback<List<MSolicitudRechazoGpo>>() {
            @Override
            public void onResponse(Call<List<MSolicitudRechazoGpo>> call, Response<List<MSolicitudRechazoGpo>> response) {

                switch (response.code()) {
                    case 200:
                        Log.e("AQUI RECHAZADO", response.body().toString());
                        List<MSolicitudRechazoGpo> solicitudes = response.body();
                        if (solicitudes.size() > 0) {
                            CreditoGpoRenDao creditoDao = new CreditoGpoRenDao(ctx);
                            IntegranteGpoRenDao integranteDao = new IntegranteGpoRenDao(ctx);

                            for (MSolicitudRechazoGpo item : solicitudes) {
                                ContentValues cv;
                                String sql = "";
                                Cursor row = null;
                                CreditoGpoRen credito = null;
                                IntegranteGpoRen integrante = null;
                                SolicitudRen solicitudTemp = null;

                                integrante = integranteDao.findByIdSolicitudIntegrante(item.getIdSolicitudIntegrante());

                                if(integrante != null) credito = creditoDao.findById(integrante.getIdCredito());
                                if(credito != null) solicitudTemp = solicitudRenDao.findByIdSolicitud(credito.getIdSolicitud());

                                if (item.getTipoSolicitud() != 1 && solicitudTemp != null && Integer.compare(solicitudTemp.getIdSolicitud(), solicitud.getIdSolicitud()) == 0) {
                                    sql = "SELECT " +
                                            "i.id AS id_integrante, " +
                                            "tel.id_telefonico, " +
                                            "dom.id_domicilio, " +
                                            "neg.id_negocio, " +
                                            "con.id_conyuge, " +
                                            "otr.id_otro, " +
                                            "cro.id AS id_croquis, " +
                                            "pol.id_politica, " +
                                            "doc.id_documento, " +
                                            "sol.id_solicitud " +
                                            "FROM " + TBL_INTEGRANTES_GPO_REN + " AS i " +
                                            "JOIN " + TBL_CREDITO_GPO_REN + " AS cre ON i.id_credito = cre.id " +
                                            "JOIN " + TBL_SOLICITUDES_REN + " AS sol ON cre.id_solicitud = sol.id_solicitud " +
                                            "JOIN " + TBL_TELEFONOS_INTEGRANTE_REN + " AS tel ON i.id = tel.id_integrante " +
                                            "JOIN " + TBL_DOMICILIO_INTEGRANTE_REN + " AS dom ON i.id = doc.id_integrante " +
                                            "JOIN " + TBL_NEGOCIO_INTEGRANTE_REN + " AS neg ON i.id = neg.id_integrante " +
                                            "JOIN " + TBL_CONYUGE_INTEGRANTE_REN + " AS con ON i.id = con.id_integrante " +
                                            "JOIN " + TBL_OTROS_DATOS_INTEGRANTE_REN + " AS otr ON i.id = otr.id_integrante " +
                                            "JOIN " + TBL_CROQUIS_GPO_REN + " AS cro ON i.id = cro.id_integrante " +
                                            "JOIN " + TBL_POLITICAS_PLD_INTEGRANTE_REN + " AS pol ON i.id = pol.id_integrante " +
                                            "JOIN " + TBL_DOCUMENTOS_INTEGRANTE_REN + " AS doc ON i.id = doc.id_integrante " +
                                            "WHERE i.id_solicitud_integrante = ? AND i.estatus_completado >= 2";

                                    row = db.rawQuery(sql, new String[]{String.valueOf(item.getIdSolicitudIntegrante())});

                                    if (row.getCount() > 0) { //Existe algun registro de renovacion
                                        row.moveToFirst();
                                        if (item.getSolicitudEstadoIdIntegrante() == 4) { //Es rechazo parcial
                                            cv = new ContentValues();
                                            cv.put("estatus_completado", 0);
                                            cv.put("comentario_rechazo", item.getComentarioAdmin());
                                            db.update(TBL_INTEGRANTES_GPO_REN, cv, "id = ?", new String[]{row.getString(0)});

                                            cv = new ContentValues();
                                            cv.put("estatus_completado", 0);
                                            db.update(TBL_TELEFONOS_INTEGRANTE_REN, cv, "id_telefonico = ?", new String[]{row.getString(1)});

                                            cv = new ContentValues();
                                            cv.put("estatus_completado", 0);
                                            db.update(TBL_DOMICILIO_INTEGRANTE_REN, cv, "id_domicilio = ?", new String[]{row.getString(2)});

                                            cv = new ContentValues();
                                            cv.put("estatus_completado", 0);
                                            db.update(TBL_NEGOCIO_INTEGRANTE_REN, cv, "id_negocio = ?", new String[]{row.getString(3)});

                                            cv = new ContentValues();
                                            cv.put("estatus_completado", 0);
                                            db.update(TBL_CONYUGE_INTEGRANTE_REN, cv, "id_conyuge = ?", new String[]{row.getString(4)});

                                            cv = new ContentValues();
                                            cv.put("estatus_completado", 0);
                                            db.update(TBL_OTROS_DATOS_INTEGRANTE_REN, cv, "id_otro = ?", new String[]{row.getString(5)});

                                            cv = new ContentValues();
                                            cv.put("estatus_completado", 0);
                                            db.update(TBL_CROQUIS_GPO_REN, cv, "id = ?", new String[]{row.getString(6)});

                                            cv = new ContentValues();
                                            cv.put("estatus_completado", 0);
                                            db.update(TBL_POLITICAS_PLD_INTEGRANTE_REN, cv, "id_politica = ?", new String[]{row.getString(7)});

                                            cv = new ContentValues();
                                            cv.put("estatus_completado", 0);
                                            db.update(TBL_DOCUMENTOS_INTEGRANTE_REN, cv, "id_documento = ?", new String[]{row.getString(8)});

                                            cv = new ContentValues();
                                            cv.put("estatus", 0);
                                            cv.put("id_originacion", String.valueOf(item.getId()));
                                            cv.put("fecha_termino", "");
                                            cv.put("fecha_envio", "");
                                            cv.put("fecha_guardado", "");
                                            db.update(TBL_SOLICITUDES_REN, cv, "id_solicitud = ?", new String[]{row.getString(9)});
                                        } else {//Es rechazo de solcitud completo
                                            cv = new ContentValues();
                                            //cv.put("estatus_completado", 0);
                                            cv.put("comentario_rechazo", item.getComentarioAdmin());
                                            db.update(TBL_INTEGRANTES_GPO_REN, cv, "id = ?", new String[]{row.getString(0)});

                                            cv = new ContentValues();//cv.put("estatus", 0);

                                            if (item.getSolicitudEstadoIdSolicitud() == 2)
                                                cv.put("estatus", 5);

                                            cv.put("id_originacion", String.valueOf(item.getId()));
                                            //cv.put("fecha_termino", "");
                                            //cv.put("fecha_envio", "");
                                            //cv.put("fecha_guardado", "");
                                            db.update(TBL_SOLICITUDES_REN, cv, "id_solicitud = ?", new String[]{row.getString(9)});
                                        }
                                    }
                                }
                            }
                        }
                        alert.dismiss();
                        finish();
                        break;
                    default:
                        try {
                            Log.e("ERROR " + response.code(), response.errorBody().string());
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        Log.e("ERROR " + response.code(), response.message());
                        alert.dismiss();
                        break;
                }
            }

            @Override
            public void onFailure(Call<List<MSolicitudRechazoGpo>> call, Throwable t)
            {
                Log.e("ERROR ", t.getMessage());
                alert.dismiss();
            }
        });
    }


}
