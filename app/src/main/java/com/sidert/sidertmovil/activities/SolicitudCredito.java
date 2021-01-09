package com.sidert.sidertmovil.activities;

import android.app.ProgressDialog;
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
import android.support.v7.widget.helper.ItemTouchHelper;
import android.text.Html;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.sidert.sidertmovil.R;
import com.sidert.sidertmovil.adapters.adapter_originacion;
import com.sidert.sidertmovil.database.DBhelper;
import com.sidert.sidertmovil.utils.Constants;
import com.sidert.sidertmovil.utils.Popups;

import java.util.ArrayList;
import java.util.HashMap;
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
    private WebView wv;

    private LinearLayout llGpo;
    private LinearLayout llInd;

    private DBhelper dBhelper;
    private SQLiteDatabase db;

    private Paint p = new Paint();

    private boolean fabExpanded = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_solicitud_credito);
        ctx = getApplicationContext();
        context = this;

        dBhelper = new DBhelper(ctx);
        db = dBhelper.getWritableDatabase();

        rvOriginacion = findViewById(R.id.rvOriginacion);

        rvOriginacion.setLayoutManager(new LinearLayoutManager(ctx));
        rvOriginacion.setHasFixedSize(false);

        //initComponents();

        fbAgregar = findViewById(R.id.fbAgregar);
        llGpo = findViewById(R.id.llGpo);
        llInd = findViewById(R.id.llInd);

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

        //Change settings icon to 'X' icon
        fbAgregar.setImageResource(R.drawable.ic_close_black);
        fabExpanded = true;
    }

    private void initComponents(){
        Cursor row = dBhelper.getRecords(TBL_SOLICITUDES, "", "", null);

        if (row.getCount() > 0){
            row.moveToFirst();
            ArrayList<HashMap<Integer,String>> data = new ArrayList<>();
            for(int i = 0; i < row.getCount(); i++){
                HashMap<Integer, String> item = new HashMap();
                Log.e("id_solicitud", row.getString(4)+" "+row.getString(0) + " nombre: "+row.getString(5)+ " "+row.getString(11));
                item.put(0,row.getString(0));           //ID solicitud
                item.put(1, row.getString(5).trim().toUpperCase()); //Nombre
                item.put(2, row.getString(3));          //Tipo solicitud
                item.put(3, row.getString(11));         //Estatus
                item.put(4, row.getString(7));          //Fecha Termino
                item.put(5, row.getString(10));         //Fecha Envio
                item.put(6, row.getString(4));          //id_originacion

                data.add(item);
                row.moveToNext();
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

    @Override
    protected void onResume() {
        super.onResume();
        initComponents();
        llGpo.setEnabled(true);
        llInd.setEnabled(true);

    }
}
