package com.sidert.sidertmovil.activities;

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
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.text.Editable;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.github.amlcurran.showcaseview.ShowcaseView;
import com.github.amlcurran.showcaseview.targets.ActionViewTarget;
import com.sidert.sidertmovil.R;
import com.sidert.sidertmovil.adapters.adapter_originacion;
import com.sidert.sidertmovil.database.DBhelper;
import com.sidert.sidertmovil.fragments.dialogs.dialog_originacion_gpo;
import com.sidert.sidertmovil.utils.Miscellaneous;
import com.sidert.sidertmovil.utils.NameFragments;
import com.sidert.sidertmovil.utils.Popups;
import com.sidert.sidertmovil.utils.Servicios_Sincronizado;

import org.json.JSONException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Objects;

import static com.sidert.sidertmovil.utils.Constants.CONYUGE_INTEGRANTE;
import static com.sidert.sidertmovil.utils.Constants.CONYUGE_INTEGRANTE_T;
import static com.sidert.sidertmovil.utils.Constants.DATOS_CREDITO_GPO;
import static com.sidert.sidertmovil.utils.Constants.DATOS_CREDITO_GPO_T;
import static com.sidert.sidertmovil.utils.Constants.DATOS_INTEGRANTES_GPO;
import static com.sidert.sidertmovil.utils.Constants.DATOS_INTEGRANTES_GPO_T;
import static com.sidert.sidertmovil.utils.Constants.DOCUMENTOS_INTEGRANTE;
import static com.sidert.sidertmovil.utils.Constants.DOCUMENTOS_INTEGRANTE_T;
import static com.sidert.sidertmovil.utils.Constants.DOMICILIO_INTEGRANTE;
import static com.sidert.sidertmovil.utils.Constants.DOMICILIO_INTEGRANTE_T;
import static com.sidert.sidertmovil.utils.Constants.ENVIROMENT;
import static com.sidert.sidertmovil.utils.Constants.INTEGRANTES_GRUPO;
import static com.sidert.sidertmovil.utils.Constants.NEGOCIO_INTEGRANTE;
import static com.sidert.sidertmovil.utils.Constants.NEGOCIO_INTEGRANTE_T;
import static com.sidert.sidertmovil.utils.Constants.OTROS_DATOS_INTEGRANTE;
import static com.sidert.sidertmovil.utils.Constants.OTROS_DATOS_INTEGRANTE_T;
import static com.sidert.sidertmovil.utils.Constants.REQUEST_CODE_ADD_INTEGRANTE;
import static com.sidert.sidertmovil.utils.Constants.SOLICITUDES;
import static com.sidert.sidertmovil.utils.Constants.SOLICITUDES_T;
import static com.sidert.sidertmovil.utils.Constants.TELEFONOS_INTEGRANTE;
import static com.sidert.sidertmovil.utils.Constants.TELEFONOS_INTEGRANTE_T;
import static com.sidert.sidertmovil.utils.Constants.question;

public class SolicitudCreditoGpo extends AppCompatActivity implements dialog_originacion_gpo.OnCompleteListener {

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
    private boolean is_edit = false;

    private TextView tvInfoCredito;

    private long id_solicitud = 0;
    private long id_credito = 0;
    private boolean is_new = false;

    private Paint p = new Paint();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_solicitud_credito_gpo);

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

        if (getIntent().getBooleanExtra("is_new",true)) {
            is_edit = true;
            is_new = true;
            openInfoOriginacion();
        }
        else{
            is_new = false;
            id_solicitud = Long.parseLong(getIntent().getStringExtra("id_solicitud"));

            Cursor row = dBhelper.getRecords(DATOS_CREDITO_GPO_T, " WHERE id_solicitud = " + id_solicitud, "", null);
            row.moveToFirst();
            id_credito = Long.parseLong(row.getString(0));
            initComponents(row.getString(0));
        }
    }

    private View.OnClickListener tvInfoCredito_OnClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            openInfoOriginacion();
        }
    };

    private View.OnClickListener fabAgregar_OnClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Cursor row = dBhelper.customSelect(DATOS_INTEGRANTES_GPO_T, "COUNT (cargo)", " WHERE id_credito = ?", "", new String[]{String.valueOf(id_credito)});
            row.moveToFirst();
            if (row.getInt(0) < 40) {
                Intent i_agregar_integrante = new Intent(ctx, AgregarIntegrante.class);
                Log.e("id_credito", "credito"+id_credito);
                i_agregar_integrante.putExtra("id_credito", String.valueOf(id_credito));
                startActivityForResult(i_agregar_integrante, REQUEST_CODE_ADD_INTEGRANTE);
            }
            else
                Toast.makeText(ctx, "Ha superado el límite de integrantes por grupo", Toast.LENGTH_SHORT).show();
        }
    };

    private void openInfoOriginacion() {
        dialog_originacion_gpo originacion_gpo = new dialog_originacion_gpo();
        originacion_gpo.setCancelable(false);
        Bundle b = new Bundle();
        Log.e("is_edit", String.valueOf(is_edit));
        if (!is_edit) {
            b.putBoolean("is_edit", false);
            b.putString("nombre", nombre_gpo);
            b.putString("plazo", plazo);
            b.putString("periodicidad", periodicidad);
            b.putString("fecha_desembolso", fecha_desembolso);
            b.putString("dia_desembolso", dia_desembolso);
            b.putString("hora_visita", hora_visita);
            originacion_gpo.setArguments(b);
        }
        else
            b.putBoolean("is_edit", true);
        originacion_gpo.setArguments(b);
        originacion_gpo.show(getSupportFragmentManager(), NameFragments.DIALOGORIGINACIONGPO);
    }

    private void initComponents(String idSolicitud){
        Cursor row = dBhelper.getOriginacionCreditoGpo(idSolicitud);
        Log.e("count", "xxxxx"+row.getCount());
        row .moveToFirst();
        id_credito = row.getInt(12);
        nombre_gpo = row.getString(13);
        plazo = row.getString(14);
        periodicidad = row.getString(15);
        fecha_desembolso = row.getString(16);
        dia_desembolso = row.getString(17);
        hora_visita = row.getString(18);
        is_edit = false;

        row.close();

        Cursor row_integrantes = dBhelper.getRecords(DATOS_INTEGRANTES_GPO_T, " WHERE id_credito = ?", " ORDER BY nombre ASC", new String[]{String.valueOf(id_credito)});
        if (row_integrantes.getCount() > 0){
            row_integrantes.moveToFirst();
            ArrayList<HashMap<Integer,String>> data = new ArrayList<>();
            for(int i = 0; i < row_integrantes.getCount(); i++){
                HashMap<Integer, String> item = new HashMap();
                item.put(0,row_integrantes.getString(0));
                String nombre = row_integrantes.getString(3) + " " +row_integrantes.getString(4) + " " + row_integrantes.getString(5);
                item.put(1, nombre.trim().toUpperCase());
                item.put(2, row_integrantes.getString(1));
                item.put(3, "2");
                item.put(4, row_integrantes.getString(20));
                data.add(item);
                row_integrantes.moveToNext();
            }

            adapter = new adapter_originacion(ctx, data, new adapter_originacion.Event() {
                @Override
                public void FichaOnClick(HashMap<Integer, String> item) {
                    Intent i_integrante = new Intent(ctx, AgregarIntegrante.class);
                    i_integrante.putExtra("is_new", false);
                    i_integrante.putExtra("id_integrante", item.get(0));
                    i_integrante.putExtra("id_credito", item.get(2));
                    startActivity(i_integrante);
                }
            });

            rvIntegrantes.setAdapter(adapter);
            initSwipe();

        }
        row_integrantes.close();
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
                                        if (ENVIROMENT) {
                                            db.delete(DATOS_INTEGRANTES_GPO_T, "id = ?", new String[]{id_integrante});
                                            db.delete(TELEFONOS_INTEGRANTE_T, "id_integrante = ?", new String[]{id_integrante});
                                            db.delete(DOMICILIO_INTEGRANTE_T, "id_integrante = ?", new String[]{id_integrante});
                                            db.delete(NEGOCIO_INTEGRANTE_T, "id_integrante = ?", new String[]{id_integrante});
                                            db.delete(CONYUGE_INTEGRANTE_T, "id_integrante = ?", new String[]{id_integrante});
                                            db.delete(OTROS_DATOS_INTEGRANTE_T, "id_integrante = ?", new String[]{id_integrante});
                                            db.delete(DOCUMENTOS_INTEGRANTE_T, "id_integrante = ?", new String[]{id_integrante});
                                        }
                                        else{
                                            db.delete(DATOS_INTEGRANTES_GPO, "id = ?", new String[]{id_integrante});
                                            db.delete(TELEFONOS_INTEGRANTE, "id_integrante = ?", new String[]{id_integrante});
                                            db.delete(DOMICILIO_INTEGRANTE, "id_integrante = ?", new String[]{id_integrante});
                                            db.delete(NEGOCIO_INTEGRANTE, "id_integrante = ?", new String[]{id_integrante});
                                            db.delete(CONYUGE_INTEGRANTE, "id_integrante = ?", new String[]{id_integrante});
                                            db.delete(OTROS_DATOS_INTEGRANTE, "id_integrante = ?", new String[]{id_integrante});
                                            db.delete(DOCUMENTOS_INTEGRANTE, "id_integrante = ?", new String[]{id_integrante});
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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_enviar_datos, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                break;
            case R.id.enviar:
                Intent i_camera = new Intent(SolicitudCreditoGpo.this, CameraActivity.class);
                startActivity(i_camera);
                Cursor row_credito = null;
                if (ENVIROMENT)
                    row_credito = dBhelper.getRecords(DATOS_INTEGRANTES_GPO, " WHERE id_credito = ?", "", new String[]{String.valueOf(id_credito)});
                else
                    row_credito = dBhelper.getRecords(DATOS_INTEGRANTES_GPO_T, " WHERE id_credito = ?", "", new String[]{String.valueOf(id_credito)});

                if (row_credito.getCount() > 7){
                    Cursor row_cargo;
                    if (ENVIROMENT)
                        row_cargo = dBhelper.customSelect(DATOS_INTEGRANTES_GPO, "DISTINCT (cargo)", " WHERE id_credito = ? AND cargo <> 4", "", new String[]{String.valueOf(id_credito)});
                    else
                        row_cargo = dBhelper.customSelect(DATOS_INTEGRANTES_GPO_T, "DISTINCT (cargo)", " WHERE id_credito = ? AND cargo <> 4", "", new String[]{String.valueOf(id_credito)});

                    if (row_cargo.getCount() == 3){
                        Cursor row_reunion;
                        if (ENVIROMENT)
                            row_reunion = dBhelper.customSelect(OTROS_DATOS_INTEGRANTE + " AS o", "casa_reunion", " INNER JOIN " + DATOS_CREDITO_GPO + " AS c ON c.id = i.id_credito INNER JOIN "+DATOS_INTEGRANTES_GPO + " AS i ON i.id = o.id_integrante WHERE c.id = ? AND casa_reunion = 1", "", new String[]{String.valueOf(id_credito)});
                        else
                            row_reunion = dBhelper.customSelect(OTROS_DATOS_INTEGRANTE_T + " AS o", "casa_reunion", " INNER JOIN " + DATOS_CREDITO_GPO_T + " AS c ON c.id = i.id_credito INNER JOIN "+DATOS_INTEGRANTES_GPO_T + " AS i ON i.id = o.id_integrante WHERE c.id = ? AND o.casa_reunion = 1", "", new String[]{String.valueOf(id_credito)});

                        Cursor row_total = dBhelper.customSelect(DATOS_INTEGRANTES_GPO_T , "SUM (CASE WHEN estatus_completado = 1 THEN 1 ELSE 0 END) AS completadas, SUM (CASE WHEN estatus_completado = 0 THEN 1 ELSE 0 END) AS pendientes", " WHERE id_credito = ?"," GROUP BY id_credito", new String[]{String.valueOf(id_credito)});
                        row_total.moveToFirst();
                        if (row_total.getInt(1) > 0){
                            ContentValues cv = new ContentValues();
                            cv.put("estatus_completado", 1);
                            if (ENVIROMENT)
                                db.update(DATOS_CREDITO_GPO, cv, "id = ?", new String[]{String.valueOf(id_credito)});
                            else
                                db.update(DATOS_CREDITO_GPO_T, cv, "id = ?", new String[]{String.valueOf(id_credito)});

                            ContentValues cv_solicitud = new ContentValues();
                            cv_solicitud.put("estatus", 0);
                            cv_solicitud.put("fecha_termino", Miscellaneous.ObtenerFecha("timestamp"));

                            Servicios_Sincronizado s = new Servicios_Sincronizado();
                            s.SendOriginacionInd(ctx,false);

                            if (ENVIROMENT)
                                db.update(SOLICITUDES, cv_solicitud, "id_solicitud = ?", new String[]{String.valueOf(id_solicitud)});
                            else
                                db.update(SOLICITUDES_T, cv_solicitud, "id_solicitud = ?" , new String[]{String.valueOf(id_solicitud)});
                        }
                        else{
                            Toast.makeText(ctx, "Existen integrantes pedientes por enviar", Toast.LENGTH_SHORT).show();
                        }

                    }
                    else{
                        Toast.makeText(ctx, "Falta elegir al comité del grupo", Toast.LENGTH_SHORT).show();
                    }
                }
                else {
                    Toast.makeText(ctx, "No cuenta con la cantidad de integrantes para formar un grupo", Toast.LENGTH_SHORT).show();
                }

                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onComplete(long id_solicitud, long id_credito, String nombre, String plazo, String periodicidad, String fecha, String dia, String hora) {
        if (id_solicitud > 0 && id_credito > 0) {
            is_new = false;
            this.id_solicitud = id_solicitud;
            this.id_credito = id_credito;
            nombre_gpo = nombre;
            this.plazo = plazo;
            this.periodicidad = periodicidad;
            this.fecha_desembolso = fecha;
            this.dia_desembolso = dia;
            this.hora_visita = hora;
            is_edit = false;
        }
        else if (nombre == null )
            finish();



    }

    @Override
    protected void onResume() {
        super.onResume();
        if (!is_new)
            initComponents(String.valueOf(id_solicitud));
    }
}
