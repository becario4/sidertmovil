package com.sidert.sidertmovil.activities;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
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
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.MultiAutoCompleteTextView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TimePicker;
import android.widget.Toast;

import com.sidert.sidertmovil.R;
import com.sidert.sidertmovil.adapters.adapter_originacion;
import com.sidert.sidertmovil.database.DBhelper;
import com.sidert.sidertmovil.utils.Constants;
import com.sidert.sidertmovil.utils.Miscellaneous;
import com.sidert.sidertmovil.utils.Popups;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Objects;

import moe.feng.common.stepperview.VerticalStepperItemView;

public class SolicitudCredito extends AppCompatActivity {

    private Context ctx;
    private Context context;

    private FloatingActionButton fbAgregar;

    private boolean FAB_Status = false;

    private adapter_originacion adapter;
    private RecyclerView rvOriginacion;

    FloatingActionButton fabGrupal;
    FloatingActionButton fabIndividual;
    private DBhelper dBhelper;
    private SQLiteDatabase db;

    private Paint p = new Paint();

    //Animations
    Animation show_fab_ind;
    Animation hide_fab_ind;
    Animation show_fab_gpo;
    Animation hide_fab_gpo;


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

        initComponents();

        fbAgregar = findViewById(R.id.fbAgregar);
        fabGrupal = findViewById(R.id.fabGrupal);
        fabIndividual = findViewById(R.id.fabIndividual);

        //Animations
        show_fab_ind = AnimationUtils.loadAnimation(ctx, R.anim.fab1_show);
        hide_fab_ind = AnimationUtils.loadAnimation(ctx, R.anim.fab1_hide);
        show_fab_gpo = AnimationUtils.loadAnimation(ctx, R.anim.fab3_show);
        hide_fab_gpo = AnimationUtils.loadAnimation(ctx, R.anim.fab3_hide);

        fbAgregar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (FAB_Status == false) {
                    //Display FAB menu
                    expandFAB();
                    FAB_Status = true;
                } else {
                    //Close FAB menu
                    hideFAB();
                    FAB_Status = false;
                }
            }
        });

        fabGrupal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i_solicitud_gpo = new Intent(ctx, SolicitudCreditoGpo.class);
                i_solicitud_gpo.putExtra("is_new",true);
                startActivity(i_solicitud_gpo);
            }
        });

        fabIndividual.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i_solicitud_ind = new Intent(ctx, SolicitudCreditoInd.class);
                i_solicitud_ind.putExtra("is_new", true);
                startActivity(i_solicitud_ind);
            }
        });
    }

    private void expandFAB() {

        //Floating Action Button 1
        FrameLayout.LayoutParams layoutParams = (FrameLayout.LayoutParams) fabIndividual.getLayoutParams();
        layoutParams.rightMargin += (int) (fabIndividual.getWidth() * 1.7);
        layoutParams.bottomMargin += (int) (fabIndividual.getHeight() * 0.25);
        fabIndividual.setLayoutParams(layoutParams);
        fabIndividual.startAnimation(show_fab_ind);
        fabIndividual.setClickable(true);

        //Floating Action Button 3
        FrameLayout.LayoutParams layoutParams3 = (FrameLayout.LayoutParams) fabGrupal.getLayoutParams();
        layoutParams3.rightMargin += (int) (fabGrupal.getWidth() * 0.25);
        layoutParams3.bottomMargin += (int) (fabGrupal.getHeight() * 1.7);
        fabGrupal.setLayoutParams(layoutParams3);
        fabGrupal.startAnimation(show_fab_gpo);
        fabGrupal.setClickable(true);
    }

    private void hideFAB() {

        //Floating Action Button 1
        FrameLayout.LayoutParams layoutParams = (FrameLayout.LayoutParams) fabIndividual.getLayoutParams();
        layoutParams.rightMargin -= (int) (fabIndividual.getWidth() * 1.7);
        layoutParams.bottomMargin -= (int) (fabIndividual.getHeight() * 0.25);
        fabIndividual.setLayoutParams(layoutParams);
        fabIndividual.startAnimation(hide_fab_ind);
        fabIndividual.setClickable(false);


        //Floating Action Button 3
        FrameLayout.LayoutParams layoutParams3 = (FrameLayout.LayoutParams) fabGrupal.getLayoutParams();
        layoutParams3.rightMargin -= (int) (fabGrupal.getWidth() * 0.25);
        layoutParams3.bottomMargin -= (int) (fabGrupal.getHeight() * 1.7);
        fabGrupal.setLayoutParams(layoutParams3);
        fabGrupal.startAnimation(hide_fab_gpo);
        fabGrupal.setClickable(false);
    }

    private void initComponents(){
        Cursor row = null;
        if (Constants.ENVIROMENT)
            row = dBhelper.getRecords(Constants.SOLICITUDES_T, "", "", null);
        else
            row = dBhelper.getRecords(Constants.SOLICITUDES_T, "", "", null);

        if (row.getCount() > 0){
            row.moveToFirst();
            ArrayList<HashMap<Integer,String>> data = new ArrayList<>();
            for(int i = 0; i < row.getCount(); i++){
                HashMap<Integer, String> item = new HashMap();
                item.put(0,row.getString(0));
                String nombre = row.getString(5) + " " +row.getString(6) + " " + row.getString(7);
                item.put(1, nombre.trim().toUpperCase());
                data.add(item);
                row.moveToNext();
            }

            adapter = new adapter_originacion(ctx, data, new adapter_originacion.Event() {
                @Override
                public void FichaOnClick(HashMap<Integer, String> item) {
                    Intent i_solicitud_ind = new Intent(ctx, SolicitudCreditoInd.class);
                    i_solicitud_ind.putExtra("is_new", false);
                    Log.e("id_Solicitud",item.get(0));
                    i_solicitud_ind.putExtra("id_solicitud", item.get(0));
                    startActivity(i_solicitud_ind);
                }
            });

            rvOriginacion.setAdapter(adapter);
            initSwipe();
        }
    }

    private void BorrarSolicitud (final String id_Solcitud, final int position){
        AlertDialog borrar_soli_dlg = Popups.showDialogConfirm(context, Constants.question,
                R.string.borrar_solicitud, R.string.yes, new Popups.DialogMessage() {
                    @Override
                    public void OnClickListener(AlertDialog dialog) {
                        AlertDialog confirm_borrar_soli_dlg = Popups.showDialogConfirm(context, Constants.question,
                                R.string.confirm_borrar_solicitud, R.string.yes, new Popups.DialogMessage() {
                                    @Override
                                    public void OnClickListener(AlertDialog dialog) {
                                        db.delete(Constants.SOLICITUDES_T, "id_solicitud = ?", new String[]{id_Solcitud});
                                        db.delete(Constants.DATOS_CREDITO_IND_T, "id_solicitud = ?", new String[]{id_Solcitud});
                                        db.delete(Constants.DATOS_CLIENTE_IND_T, "id_solicitud = ?", new String[]{id_Solcitud});
                                        db.delete(Constants.DATOS_CONYUGE_IND_T, "id_solicitud = ?", new String[]{id_Solcitud});
                                        db.delete(Constants.DATOS_ECONOMICOS_IND_T, "id_solicitud = ?", new String[]{id_Solcitud});
                                        db.delete(Constants.DATOS_NEGOCIO_IND_T, "id_solicitud = ?", new String[]{id_Solcitud});
                                        db.delete(Constants.DATOS_AVAL_IND_T, "id_solicitud = ?", new String[]{id_Solcitud});
                                        db.delete(Constants.DATOS_REFERENCIA_IND_T, "id_solicitud = ?", new String[]{id_Solcitud});
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
        helper.attachToRecyclerView(rvOriginacion);
    }

    @Override
    protected void onResume() {
        super.onResume();
        initComponents();

    }
}
