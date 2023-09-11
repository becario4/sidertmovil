package com.sidert.sidertmovil.views.renovacion;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
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
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.orhanobut.dialogplus.DialogPlus;
import com.orhanobut.dialogplus.OnClickListener;
import com.orhanobut.dialogplus.ViewHolder;
import com.sidert.sidertmovil.Home;
import com.sidert.sidertmovil.R;
import com.sidert.sidertmovil.activities.DescargaDatos;
import com.sidert.sidertmovil.adapters.adapter_renovacion;
import com.sidert.sidertmovil.database.DBhelper;
import com.sidert.sidertmovil.models.solicitudes.PrestamoToRenovar;
import com.sidert.sidertmovil.models.solicitudes.PrestamoToRenovarDao;
import com.sidert.sidertmovil.models.solicitudes.SolicitudRen;
import com.sidert.sidertmovil.models.solicitudes.SolicitudRenDao;
import com.sidert.sidertmovil.models.solicitudes.solicitudind.renovacion.ClienteRen;
import com.sidert.sidertmovil.models.solicitudes.solicitudind.renovacion.ClienteRenDao;
import com.sidert.sidertmovil.utils.Constants;
import com.sidert.sidertmovil.utils.Miscellaneous;
import com.sidert.sidertmovil.utils.Popups;
import com.sidert.sidertmovil.utils.SessionManager;
import com.sidert.sidertmovil.views.apoyogastosfunerarios.ResumenActivity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

import static com.sidert.sidertmovil.utils.Constants.TBL_AVAL_IND;
import static com.sidert.sidertmovil.utils.Constants.TBL_AVAL_IND_REN;
import static com.sidert.sidertmovil.utils.Constants.TBL_CLIENTE_IND;
import static com.sidert.sidertmovil.utils.Constants.TBL_CLIENTE_IND_REN;
import static com.sidert.sidertmovil.utils.Constants.TBL_CONYUGE_IND;
import static com.sidert.sidertmovil.utils.Constants.TBL_CONYUGE_IND_REN;
import static com.sidert.sidertmovil.utils.Constants.TBL_CONYUGE_INTEGRANTE;
import static com.sidert.sidertmovil.utils.Constants.TBL_CONYUGE_INTEGRANTE_REN;
import static com.sidert.sidertmovil.utils.Constants.TBL_CREDITO_GPO;
import static com.sidert.sidertmovil.utils.Constants.TBL_CREDITO_GPO_REN;
import static com.sidert.sidertmovil.utils.Constants.TBL_CREDITO_IND;
import static com.sidert.sidertmovil.utils.Constants.TBL_CREDITO_IND_REN;
import static com.sidert.sidertmovil.utils.Constants.TBL_CROQUIS_GPO;
import static com.sidert.sidertmovil.utils.Constants.TBL_CROQUIS_GPO_REN;
import static com.sidert.sidertmovil.utils.Constants.TBL_CROQUIS_IND;
import static com.sidert.sidertmovil.utils.Constants.TBL_CROQUIS_IND_REN;
import static com.sidert.sidertmovil.utils.Constants.TBL_DOCUMENTOS;
import static com.sidert.sidertmovil.utils.Constants.TBL_DOCUMENTOS_INTEGRANTE;
import static com.sidert.sidertmovil.utils.Constants.TBL_DOCUMENTOS_INTEGRANTE_REN;
import static com.sidert.sidertmovil.utils.Constants.TBL_DOCUMENTOS_REN;
import static com.sidert.sidertmovil.utils.Constants.TBL_DOMICILIO_INTEGRANTE;
import static com.sidert.sidertmovil.utils.Constants.TBL_DOMICILIO_INTEGRANTE_REN;
import static com.sidert.sidertmovil.utils.Constants.TBL_ECONOMICOS_IND;
import static com.sidert.sidertmovil.utils.Constants.TBL_ECONOMICOS_IND_REN;
import static com.sidert.sidertmovil.utils.Constants.TBL_INTEGRANTES_GPO;
import static com.sidert.sidertmovil.utils.Constants.TBL_INTEGRANTES_GPO_REN;
import static com.sidert.sidertmovil.utils.Constants.TBL_NEGOCIO_IND;
import static com.sidert.sidertmovil.utils.Constants.TBL_NEGOCIO_IND_REN;
import static com.sidert.sidertmovil.utils.Constants.TBL_NEGOCIO_INTEGRANTE;
import static com.sidert.sidertmovil.utils.Constants.TBL_NEGOCIO_INTEGRANTE_REN;
import static com.sidert.sidertmovil.utils.Constants.TBL_OTROS_DATOS_INTEGRANTE;
import static com.sidert.sidertmovil.utils.Constants.TBL_OTROS_DATOS_INTEGRANTE_REN;
import static com.sidert.sidertmovil.utils.Constants.TBL_POLITICAS_PLD_IND;
import static com.sidert.sidertmovil.utils.Constants.TBL_POLITICAS_PLD_IND_REN;
import static com.sidert.sidertmovil.utils.Constants.TBL_POLITICAS_PLD_INTEGRANTE;
import static com.sidert.sidertmovil.utils.Constants.TBL_POLITICAS_PLD_INTEGRANTE_REN;
import static com.sidert.sidertmovil.utils.Constants.TBL_PRESTAMOS_TO_RENOVAR;
import static com.sidert.sidertmovil.utils.Constants.TBL_REFERENCIA_IND;
import static com.sidert.sidertmovil.utils.Constants.TBL_REFERENCIA_IND_REN;
import static com.sidert.sidertmovil.utils.Constants.TBL_SOLICITUDES;
import static com.sidert.sidertmovil.utils.Constants.TBL_SOLICITUDES_REN;
import static com.sidert.sidertmovil.utils.Constants.TBL_TELEFONOS_INTEGRANTE;
import static com.sidert.sidertmovil.utils.Constants.TBL_TELEFONOS_INTEGRANTE_REN;

import org.json.JSONException;

/**
 * Clase donde se visualizaran todos los créditos por renovar de individual y grupal
 */
public class RenovacionCredito extends AppCompatActivity {

    private Context ctx;
    private adapter_renovacion adapter;
    private RecyclerView rvRenovacion;
    private Toolbar tbMain;
    private Paint p = new Paint();
    private RadioButton rbDisponibles;
    private RadioButton rbEnProceso;
    private RadioButton rbCompletados;
    private DBhelper dBhelper;
    private SQLiteDatabase db;
    private SessionManager session;
    private int seccion = 1;
    private int cont_disponibles = 0;
    private int cont_proceso = 0;
    private int cont_completados = 0;
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
        setContentView(R.layout.activity_renovacion_credito);

        ctx = this;

        tbMain = findViewById(R.id.TBmain);
        session = SessionManager.getInstance(ctx);

        dBhelper = DBhelper.getInstance(ctx);
        db = dBhelper.getWritableDatabase();

        rvRenovacion = findViewById(R.id.rvRenovacion);
        rbDisponibles = findViewById(R.id.rbDisponibles);
        rbEnProceso = findViewById(R.id.rbEnProceso);
        rbCompletados = findViewById(R.id.rbCompletados);

        rvRenovacion.setLayoutManager(new LinearLayoutManager(ctx));
        rvRenovacion.setHasFixedSize(false);

        setSupportActionBar(tbMain);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        setTitle("Renovación de Crédito");

        invalidateOptionsMenu();

        rbDisponibles.setChecked(true);


        rbDisponibles.setOnClickListener(v -> {
            seccion = 1;
            setupBadge();
            GetClientes();
            getDisponibles();
        });

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
    }

    private String tipoUsuarioRol(SessionManager sen) {
        String tipoUser = " ";
        Home tipoRol = new Home();
        try {
            tipoUser = tipoRol.getTipoRolA(sen);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return tipoUser;
    }

    private void initSwipe() {
        ItemTouchHelper.SimpleCallback simpleCallback = new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.RIGHT | ItemTouchHelper.LEFT) {
            @Override
            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
                int position = viewHolder.getAdapterPosition();
                if (direction == ItemTouchHelper.RIGHT) {
                    BorrarSolicitud(Integer.parseInt(adapter.getItem(position).get(2)), adapter.getItem(position).get(0), position);
                } else {
                    BorrarSolicitud(Integer.parseInt(adapter.getItem(position).get(2)), adapter.getItem(position).get(0), position);
                }
            }

            @Override
            public void onChildDraw(Canvas c, RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive) {
                Bitmap icon;
                if (actionState == ItemTouchHelper.ACTION_STATE_SWIPE) {
                    View itemView = viewHolder.itemView;
                    float height = (float) itemView.getBottom() - (float) itemView.getTop();
                    float width = height / 4;
                    if (dX > 0) {
                        p.setColor(Color.RED);
                        RectF background = new RectF((float) itemView.getLeft(), (float) itemView.getTop(), dX, (float) itemView.getBottom());
                        c.drawRect(background, p);
                        icon = BitmapFactory.decodeResource(getResources(), R.drawable.ic_delete_forever);
                        RectF icon_dest = new RectF((float) itemView.getLeft() + width, (float) itemView.getTop() + width, (float) itemView.getLeft() + 3 * width, (float) itemView.getBottom() - width);
                        c.drawBitmap(icon, null, icon_dest, p);
                    } else {
                        p.setColor(Color.RED);
                        RectF background = new RectF((float) itemView.getRight() + dX, (float) itemView.getTop(), (float) itemView.getRight(), (float) itemView.getBottom());
                        c.drawRect(background, p);
                        icon = BitmapFactory.decodeResource(getResources(), R.drawable.ic_delete_forever);
                        RectF icon_dest = new RectF((float) itemView.getRight() - 3 * width, (float) itemView.getTop() + width, (float) itemView.getRight() - width, (float) itemView.getBottom() - width);
                        c.drawBitmap(icon, null, icon_dest, p);
                    }
                }
                super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);
            }
        };
        ItemTouchHelper helper = new ItemTouchHelper(simpleCallback);
        helper.attachToRecyclerView(rvRenovacion);
    }

    private void BorrarSolicitud(final int tipo_solicitud, final String id_solcitud, final int position) {
        AlertDialog borrar_soli_dlg = Popups.showDialogConfirm(ctx, Constants.question,
                R.string.borrar_solicitud, R.string.yes, new Popups.DialogMessage() {
                    @Override
                    public void OnClickListener(AlertDialog dialog) {
                        AlertDialog confirm_borrar_soli_dlg = Popups.showDialogConfirm(ctx, Constants.question,
                                R.string.confirm_borrar_solicitud, R.string.yes, new Popups.DialogMessage() {
                                    @Override
                                    public void OnClickListener(AlertDialog dialog) {
                                        if (tipo_solicitud == 1) {
                                            db.delete(TBL_SOLICITUDES_REN, "id_solicitud = ?", new String[]{id_solcitud});
                                            db.delete(TBL_CREDITO_IND_REN, "id_solicitud = ?", new String[]{id_solcitud});
                                            db.delete(TBL_CLIENTE_IND_REN, "id_solicitud = ?", new String[]{id_solcitud});
                                            db.delete(TBL_CONYUGE_IND_REN, "id_solicitud = ?", new String[]{id_solcitud});
                                            db.delete(TBL_ECONOMICOS_IND_REN, "id_solicitud = ?", new String[]{id_solcitud});
                                            db.delete(TBL_NEGOCIO_IND_REN, "id_solicitud = ?", new String[]{id_solcitud});
                                            db.delete(TBL_AVAL_IND_REN, "id_solicitud = ?", new String[]{id_solcitud});
                                            db.delete(TBL_REFERENCIA_IND_REN, "id_solicitud = ?", new String[]{id_solcitud});
                                            db.delete(TBL_CROQUIS_IND_REN, "id_solicitud = ?", new String[]{id_solcitud});
                                            db.delete(TBL_POLITICAS_PLD_IND_REN, "id_solicitud = ?", new String[]{id_solcitud});
                                            db.delete(TBL_DOCUMENTOS_REN, "id_solicitud = ?", new String[]{id_solcitud});
                                            db.delete(TBL_PRESTAMOS_TO_RENOVAR, "_id=?", new String[]{id_solcitud});
                                        } else {
                                            Cursor row_credito = dBhelper.getRecords(TBL_CREDITO_GPO_REN, " WHERE id_solicitud = ?", "", new String[]{id_solcitud});
                                            row_credito.moveToFirst();
                                            Cursor row_integrantes = dBhelper.getRecords(TBL_INTEGRANTES_GPO_REN, " WHERE id_credito = ?", "", new String[]{row_credito.getString(0)});
                                            row_integrantes.moveToFirst();
                                            for (int i = 0; i < row_integrantes.getCount(); i++) {
                                                db.delete(TBL_TELEFONOS_INTEGRANTE_REN, "id_integrante = ?", new String[]{row_integrantes.getString(0)});
                                                db.delete(TBL_DOMICILIO_INTEGRANTE_REN, "id_integrante = ?", new String[]{row_integrantes.getString(0)});
                                                db.delete(TBL_NEGOCIO_INTEGRANTE_REN, "id_integrante = ?", new String[]{row_integrantes.getString(0)});
                                                db.delete(TBL_CONYUGE_INTEGRANTE_REN, "id_integrante = ?", new String[]{row_integrantes.getString(0)});
                                                db.delete(TBL_OTROS_DATOS_INTEGRANTE_REN, "id_integrante = ?", new String[]{row_integrantes.getString(0)});
                                                db.delete(TBL_CROQUIS_GPO_REN, "id_integrante = ?", new String[]{row_integrantes.getString(0)});
                                                db.delete(TBL_POLITICAS_PLD_INTEGRANTE_REN, "id_integrante = ?", new String[]{row_integrantes.getString(0)});
                                                db.delete(TBL_DOCUMENTOS_INTEGRANTE_REN, "id_integrante = ?", new String[]{row_integrantes.getString(0)});
                                                db.delete(TBL_PRESTAMOS_TO_RENOVAR, "_id=?", new String[]{row_integrantes.getString(0)});
                                                row_integrantes.moveToNext();
                                            }
                                            row_integrantes.close();

                                            db.delete(TBL_INTEGRANTES_GPO_REN, "id_credito = ?", new String[]{row_credito.getString(0)});
                                            db.delete(TBL_CREDITO_GPO_REN, "id = ?", new String[]{row_credito.getString(0)});
                                            row_credito.close();
                                            db.delete(TBL_SOLICITUDES_REN, "id_solicitud = ?", new String[]{id_solcitud});
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

    private void GetClientes() {
        SolicitudRenDao solicitudRenDao = new SolicitudRenDao(ctx);
        List<String> nombres = solicitudRenDao.showNombres(0);

        if (nombres.size() > 0) {
            dataNombre = new String[nombres.size()];

            for (int i = 0; i < nombres.size(); i++) {
                dataNombre[i] = nombres.get(i);
            }
        } else {
            dataNombre = new String[1];
            dataNombre[0] = "";
        }

        adapterNombre = new ArrayAdapter<>(ctx, R.layout.custom_list_item, R.id.text_view_list_item, dataNombre);
    }

    private void GetClientesEnProceso() {
        SolicitudRenDao solicitudRenDao = new SolicitudRenDao(ctx);
        List<String> nombres = solicitudRenDao.showNombres(1);

        if (nombres.size() > 0) {
            dataNombre = new String[nombres.size()];

            for (int i = 0; i < nombres.size(); i++) {
                dataNombre[i] = nombres.get(i);
            }
        } else {
            dataNombre = new String[1];
            dataNombre[0] = "";
        }

        adapterNombre = new ArrayAdapter<>(ctx, R.layout.custom_list_item, R.id.text_view_list_item, dataNombre);
    }

    private void GetClientesCompletados() {
        SolicitudRenDao solicitudRenDao = new SolicitudRenDao(ctx);
        List<String> nombres = solicitudRenDao.showNombres(3);

        if (nombres.size() > 0) {
            dataNombre = new String[nombres.size()];

            for (int i = 0; i < nombres.size(); i++) {
                dataNombre[i] = nombres.get(i);
            }
        } else {
            dataNombre = new String[1];
            dataNombre[0] = "";
        }

        adapterNombre = new ArrayAdapter<>(ctx, R.layout.custom_list_item, R.id.text_view_list_item, dataNombre);
    }

    private void getDisponibles() {
        SolicitudRenDao solicitudRenDao = new SolicitudRenDao(ctx);

        String banderaIndividuales = session.getFiltrosDisponiblesRen().get(0);
        String banderaGrupales = session.getFiltrosDisponiblesRen().get(1);
        String nombre = session.getFiltrosDisponiblesRen().get(2);
        String banderaMenor45 = session.getFiltrosDisponiblesRen().get(3);

        List<SolicitudRen> solicitudesRen = solicitudRenDao.findAllByFilters(
                banderaMenor45,
                banderaGrupales,
                banderaIndividuales,
                nombre,
                0
        );

        rvRenovacion.setAdapter(null);

        if (solicitudesRen.size() > 0) {
            fillList(solicitudesRen);
        }
    }

    private void getEnProceso() {
        SolicitudRenDao solicitudRenDao = new SolicitudRenDao(ctx);

        String banderaIndividuales = session.getFiltrosEnProcesoRen().get(0);
        String banderaGrupales = session.getFiltrosEnProcesoRen().get(1);
        String nombre = session.getFiltrosEnProcesoRen().get(2);
        String banderaMenor45 = session.getFiltrosEnProcesoRen().get(3);

        List<SolicitudRen> solicitudesRen = solicitudRenDao.findAllByFilters(
                banderaMenor45,
                banderaGrupales,
                banderaIndividuales,
                nombre,
                1
        );

        rvRenovacion.setAdapter(null);

        if (solicitudesRen.size() > 0) {
            fillList(solicitudesRen);
        }
    }

    private void getCompletados() {
        SolicitudRenDao solicitudRenDao = new SolicitudRenDao(ctx);

        String banderaIndividuales = session.getFiltrosCompletadosRen().get(0);
        String banderaGrupales = session.getFiltrosCompletadosRen().get(1);
        String nombre = session.getFiltrosCompletadosRen().get(2);
        String banderaMenor45 = session.getFiltrosCompletadosRen().get(3);

        List<SolicitudRen> solicitudesRen = solicitudRenDao.findAllByFilters(
                banderaMenor45,
                banderaGrupales,
                banderaIndividuales,
                nombre,
                3
        );

        rvRenovacion.setAdapter(null);

        if (solicitudesRen.size() > 0) {
            fillList(solicitudesRen);
        }
    }

    private void fillList(List<SolicitudRen> solicitudesRen) {
        ClienteRenDao clienteDao = new ClienteRenDao(ctx);

        ArrayList<HashMap<Integer, String>> data = new ArrayList<>();
        for (SolicitudRen solicitud : solicitudesRen) {
            HashMap<Integer, String> item = new HashMap();

            ClienteRen cliente = null;

            if (solicitud.getTipoSolicitud() == 1)
                cliente = clienteDao.findByIdSolicitud(solicitud.getIdSolicitud());

            item.put(0, String.valueOf(solicitud.getIdSolicitud()));
            item.put(1, solicitud.getNombre().trim().toUpperCase());
            item.put(2, String.valueOf(solicitud.getTipoSolicitud()));
            item.put(3, String.valueOf(solicitud.getEstatus()));
            item.put(4, solicitud.getFechaTermino());
            item.put(5, solicitud.getFechaGuardado());
            item.put(6, "");//pendiente

            if (cliente != null) item.put(7, cliente.getComentarioRechazo());//pendiente

            PrestamoToRenovarDao prestamoToRenovarDao = new PrestamoToRenovarDao(ctx);
            //PrestamoToRenovar prestamoToRenovar = prestamoToRenovarDao.findByClienteNombre(solicitud.getNombre().trim().toUpperCase());
            PrestamoToRenovar prestamoToRenovar = prestamoToRenovarDao.findByPrestamoId(solicitud.getPrestamoId());

            item.put(8, (prestamoToRenovar != null) ? prestamoToRenovar.getFechaVencimiento() : "");

            data.add(item);
        }

        adapter = new adapter_renovacion(ctx, data, (adapter_renovacion.Event) item -> {
            Intent i_renovacion;
            ProgressDialog dialog = ProgressDialog.show(RenovacionCredito.this, "", "Cargando la información por favor espere...", true);
            dialog.setCancelable(false);
            dialog.show();

            switch (Integer.parseInt(item.get(2))) {
                case 1:
                    i_renovacion = new Intent(ctx, RenovacionCreditoInd.class);
                    i_renovacion.putExtra("is_new", false);
                    i_renovacion.putExtra("id_solicitud", item.get(0));
                    i_renovacion.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(i_renovacion);
                    dialog.dismiss();
                    break;
                case 2:
                    Cursor row = dBhelper.getRecords(TBL_CREDITO_GPO_REN, " WHERE id_solicitud = ?", "", new String[]{item.get(0)});
                    row.moveToFirst();

                    boolean completed = false;
                    if (row.getInt(8) == 0)
                        completed = true;

                    i_renovacion = new Intent(ctx, RenovacionCreditoGpo.class);
                    i_renovacion.putExtra("is_new", completed);/**Bandera para validar si mostrara datos del credito o ya podrá continuar con los integrantes*/
                    i_renovacion.putExtra("id_solicitud", item.get(0));
                    i_renovacion.putExtra("nombre", item.get(1));/**Nombre del grupo*/
                    i_renovacion.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(i_renovacion);
                    dialog.dismiss();
                    //Intent intel = new Intent(ctx,RenovarIntegrante.class);
                    //intel.putExtra("id_solicitud", item.get(0));
                    //startActivity(intel);
                    row.close();
                    break;
            }
        });

        rvRenovacion.setAdapter(adapter);


        initSwipe();

        String resultado = tipoUsuarioRol(session);
        if (resultado.contains("ROLE_SUPER")) {
            initSwipe();
            Toast.makeText(ctx, "SE PUEDEN ELIMNAR RENOVACIONES IND Y GPO", Toast.LENGTH_SHORT).show();
        }

    }

    @SuppressLint("ClickableViewAccessibility")
    private void Filtros() {

        View decorateView = this.getWindow().getDecorView();
        int view = 0;
        int sizeH = (int) (decorateView.getHeight() / 2.0);

        switch (seccion) {
            case 1:
                view = R.layout.sheet_dialog_filtros_disponibles_ren;
                break;
            case 2:
                view = R.layout.sheet_dialog_filtros_proceso_ren;
                break;
            case 3:
                view = R.layout.sheet_dialog_filtros_completados_ren;
                break;
        }

        DialogPlus filtros_dg = DialogPlus.newDialog(ctx)
                .setContentHolder(new ViewHolder(view))
                .setGravity(Gravity.TOP)
                .setPadding(20, 40, 20, 10)
                .setOnClickListener(new OnClickListener() {
                    @Override
                    public void onClick(DialogPlus dialog, View view) {
                        cont_disponibles = 0;
                        cont_proceso = 0;
                        cont_completados = 0;
                        HashMap<String, String> filtros = new HashMap<>();
                        InputMethodManager imm = (InputMethodManager) ctx.getSystemService(Context.INPUT_METHOD_SERVICE);

                        int id = view.getId();
                        if (id == R.id.btnFiltrar) {
                            switch (seccion) {
                                case 1:
                                    if (!aetNombre.getText().toString().trim().isEmpty()) {
                                        filtros.put("nombre_renovacion", aetNombre.getText().toString().trim());
                                        cont_disponibles += 1;
                                    } else filtros.put("nombre_renovacion", "");

                                    if (cbInd.isChecked() && cbGpo.isChecked()) {
                                        filtros.put("tipo_renovacion_ind", "1");
                                        filtros.put("tipo_renovacion_gpo", "1");
                                        cont_disponibles += 2;
                                    } else if (cbInd.isChecked()) {
                                        filtros.put("tipo_renovacion_ind", "1");
                                        filtros.put("tipo_renovacion_gpo", "0");
                                        cont_disponibles += 1;
                                    } else if (cbGpo.isChecked()) {
                                        filtros.put("tipo_renovacion_ind", "0");
                                        filtros.put("tipo_renovacion_gpo", "1");
                                        cont_disponibles += 1;
                                    } else {
                                        filtros.put("tipo_renovacion_ind", "0");
                                        filtros.put("tipo_renovacion_gpo", "0");
                                    }

                                    if (cbMenor45.isChecked()) {
                                        filtros.put("menor45_renovacion", "1");
                                        cont_disponibles += 1;
                                    } else filtros.put("menor45_renovacion", "0");

                                    filtros.put("contador_renovacion", String.valueOf(cont_disponibles));
                                    session.setFiltrosDisponiblesRen(filtros);

                                    imm.hideSoftInputFromWindow(view.getWindowToken(), 0);

                                    getDisponibles();

                                    break;
                                case 2:
                                    if (!aetNombre.getText().toString().trim().isEmpty()) {
                                        filtros.put("nombre_renovacion_pro", aetNombre.getText().toString().trim());
                                        cont_proceso += 1;
                                    } else filtros.put("nombre_renovacion_pro", "");

                                    if (cbInd.isChecked() && cbGpo.isChecked()) {
                                        filtros.put("tipo_renovacion_ind_pro", "1");
                                        filtros.put("tipo_renovacion_gpo_pro", "1");
                                        cont_proceso += 2;
                                    } else if (cbInd.isChecked()) {
                                        filtros.put("tipo_renovacion_ind_pro", "1");
                                        filtros.put("tipo_renovacion_gpo_pro", "0");
                                        cont_proceso += 1;
                                    } else if (cbGpo.isChecked()) {
                                        filtros.put("tipo_renovacion_ind_pro", "0");
                                        filtros.put("tipo_renovacion_gpo_pro", "1");
                                        cont_proceso += 1;
                                    } else {
                                        filtros.put("tipo_renovacion_ind_pro", "0");
                                        filtros.put("tipo_renovacion_gpo_pro", "0");
                                    }

                                    if (cbMenor45.isChecked()) {
                                        filtros.put("menor45_renovacion_pro", "1");
                                        cont_proceso += 1;
                                    } else filtros.put("menor45_renovacion_pro", "0");

                                    filtros.put("contador_renovacion_pro", String.valueOf(cont_proceso));
                                    session.setFiltrosEnProcesoRen(filtros);

                                    imm.hideSoftInputFromWindow(view.getWindowToken(), 0);

                                    getEnProceso();

                                    break;
                                case 3:
                                    if (!aetNombre.getText().toString().trim().isEmpty()) {
                                        filtros.put("nombre_renovacion_comp", aetNombre.getText().toString().trim());
                                        cont_completados += 1;
                                    } else filtros.put("nombre_renovacion_comp", "");

                                    if (cbInd.isChecked() && cbGpo.isChecked()) {
                                        filtros.put("tipo_renovacion_ind_comp", "1");
                                        filtros.put("tipo_renovacion_gpo_comp", "1");
                                        cont_completados += 2;
                                    } else if (cbInd.isChecked()) {
                                        filtros.put("tipo_renovacion_ind_comp", "1");
                                        filtros.put("tipo_renovacion_gpo_comp", "0");
                                        cont_completados += 1;
                                    } else if (cbGpo.isChecked()) {
                                        filtros.put("tipo_renovacion_ind_comp", "0");
                                        filtros.put("tipo_renovacion_gpo_comp", "1");
                                        cont_completados += 1;
                                    } else {
                                        filtros.put("tipo_renovacion_ind_comp", "0");
                                        filtros.put("tipo_renovacion_gpo_comp", "0");
                                    }

                                    if (cbMenor45.isChecked()) {
                                        filtros.put("menor45_renovacion_comp", "1");
                                        cont_completados += 1;
                                    } else filtros.put("menor45_renovacion_comp", "0");

                                    filtros.put("contador_renovacion_comp", String.valueOf(cont_completados));
                                    session.setFiltrosCompletadosRen(filtros);

                                    imm.hideSoftInputFromWindow(view.getWindowToken(), 0);

                                    getCompletados();

                                    break;
                            }
                            dialog.dismiss();
                        } else if (id == R.id.btnBorrar) {
                            switch (seccion) {
                                case 1:
                                    cbInd.setChecked(false);
                                    cbGpo.setChecked(false);
                                    cbMenor45.setChecked(false);
                                    aetNombre.setText("");
                                    aetNombre.setAdapter(adapterNombre);

                                    cont_disponibles = 0;
                                    filtros = new HashMap<>();
                                    filtros.put("nombre_renovacion", "");
                                    filtros.put("tipo_renovacion_ind", "0");
                                    filtros.put("tipo_renovacion_gpo", "0");
                                    filtros.put("menor45_renovacion", "0");
                                    filtros.put("contador_renovacion", "0");
                                    session.setFiltrosDisponiblesRen(filtros);

                                    imm.hideSoftInputFromWindow(view.getWindowToken(), 0);

                                    getDisponibles();

                                    break;
                                case 2:
                                    cbInd.setChecked(false);
                                    cbGpo.setChecked(false);
                                    cbMenor45.setChecked(false);
                                    aetNombre.setText("");
                                    aetNombre.setAdapter(adapterNombre);

                                    cont_proceso = 0;
                                    filtros = new HashMap<>();
                                    filtros.put("nombre_renovacion_pro", "");
                                    filtros.put("tipo_renovacion_ind_pro", "0");
                                    filtros.put("tipo_renovacion_gpo_pro", "0");
                                    filtros.put("menor45_renovacion_pro", "0");
                                    filtros.put("contador_renovacion_pro", "0");
                                    session.setFiltrosEnProcesoRen(filtros);

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
                                    filtros.put("nombre_renovacion_comp", "");
                                    filtros.put("tipo_renovacion_ind_comp", "0");
                                    filtros.put("tipo_renovacion_gpo_comp", "0");
                                    filtros.put("menor45_renovacion_comp", "0");
                                    filtros.put("contador_renovacion_comp", "0");
                                    session.setFiltrosCompletadosRen(filtros);

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

        switch (seccion) {
            case 1:
                aetNombre = filtros_dg.getHolderView().findViewById(R.id.aetNombre);
                cbInd = filtros_dg.getHolderView().findViewById(R.id.cbInd);
                cbGpo = filtros_dg.getHolderView().findViewById(R.id.cbGpo);
                cbMenor45 = filtros_dg.getHolderView().findViewById(R.id.cbMenor45);
                //cbMenor45.setChecked(true);

                aetNombre.setAdapter(adapterNombre);

                aetNombre.setOnTouchListener((v, event) -> {
                    aetNombre.showDropDown();
                    return false;
                });

                if (!session.getFiltrosDisponiblesRen().get(2).isEmpty()) {
                    aetNombre.setText(session.getFiltrosDisponiblesRen().get(2));
                }

                if (session.getFiltrosDisponiblesRen().get(1).equals("1")) {
                    cbGpo.setChecked(true);
                }

                if (session.getFiltrosDisponiblesRen().get(0).equals("1")) {
                    cbInd.setChecked(true);
                }

                if (session.getFiltrosDisponiblesRen().get(3).equals("1")) {
                    cbMenor45.setChecked(true);
                }

                break;
            case 2:
                aetNombre = filtros_dg.getHolderView().findViewById(R.id.aetNombre);
                cbInd = filtros_dg.getHolderView().findViewById(R.id.cbInd);
                cbGpo = filtros_dg.getHolderView().findViewById(R.id.cbGpo);
                cbMenor45 = filtros_dg.getHolderView().findViewById(R.id.cbMenor45);
                //cbMenor45.setChecked(true);

                aetNombre.setAdapter(adapterNombre);

                aetNombre.setOnTouchListener((v, event) -> {
                    aetNombre.showDropDown();
                    return false;
                });

                if (!session.getFiltrosEnProcesoRen().get(2).isEmpty()) {
                    aetNombre.setText(session.getFiltrosEnProcesoRen().get(2));
                }

                if (session.getFiltrosEnProcesoRen().get(1).equals("1")) {
                    cbGpo.setChecked(true);
                }

                if (session.getFiltrosEnProcesoRen().get(0).equals("1")) {
                    cbInd.setChecked(true);
                }

                if (session.getFiltrosEnProcesoRen().get(3).equals("1")) {
                    cbMenor45.setChecked(true);
                }

                break;
            case 3:
                aetNombre = filtros_dg.getHolderView().findViewById(R.id.aetNombre);
                cbInd = filtros_dg.getHolderView().findViewById(R.id.cbInd);
                cbGpo = filtros_dg.getHolderView().findViewById(R.id.cbGpo);
                cbMenor45 = filtros_dg.getHolderView().findViewById(R.id.cbMenor45);
                //cbMenor45.setChecked(true);

                aetNombre.setAdapter(adapterNombre);

                aetNombre.setOnTouchListener((v, event) -> {
                    aetNombre.showDropDown();
                    return false;
                });

                if (!session.getFiltrosCompletadosRen().get(2).isEmpty()) {
                    aetNombre.setText(session.getFiltrosCompletadosRen().get(2));
                }

                if (session.getFiltrosCompletadosRen().get(1).equals("1")) {
                    cbGpo.setChecked(true);
                }

                if (session.getFiltrosCompletadosRen().get(0).equals("1")) {
                    cbInd.setChecked(true);
                }

                if (session.getFiltrosCompletadosRen().get(3).equals("1")) {
                    cbMenor45.setChecked(true);
                }

                break;
        }

        filtros_dg.show();

    }

    private void setupBadge() {
        switch (seccion) {
            case 2:
                if (tvContFiltros != null) {
                    tvContFiltros.setText(String.valueOf(session.getFiltrosEnProcesoRen().get(4)));
                    tvContFiltros.setVisibility(View.VISIBLE);
                }
                break;
            case 3:
                if (tvContFiltros != null) {
                    tvContFiltros.setText(String.valueOf(session.getFiltrosCompletadosRen().get(4)));
                    tvContFiltros.setVisibility(View.VISIBLE);
                }
                break;
            default:
                if (tvContFiltros != null) {
                    tvContFiltros.setText(String.valueOf(session.getFiltrosDisponiblesRen().get(4)));
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

        switch (seccion) {
            case 1:
                rbDisponibles.setChecked(true);
                setupBadge();
                GetClientes();
                getDisponibles();
                break;
            case 2:
                rbEnProceso.setChecked(true);
                setupBadge();
                GetClientesEnProceso();
                getEnProceso();
                break;
            default:
                rbCompletados.setChecked(true);
                setupBadge();
                GetClientesCompletados();
                getCompletados();
                break;
        }

    }
}
