package com.sidert.sidertmovil.fragments.dialogs;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import androidx.annotation.Nullable;
//import androidx.fragment.app.DialogFragment;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.sidert.sidertmovil.R;
import com.sidert.sidertmovil.activities.VistaGestion;
import com.sidert.sidertmovil.database.DBhelper;
import com.sidert.sidertmovil.models.MSolicitudCancelacion;
import com.sidert.sidertmovil.utils.Constants;
import com.sidert.sidertmovil.utils.ManagerInterface;
import com.sidert.sidertmovil.utils.Miscellaneous;
import com.sidert.sidertmovil.utils.Popups;
import com.sidert.sidertmovil.utils.RetrofitClient;
import com.sidert.sidertmovil.utils.SessionManager;
import com.sidert.sidertmovil.utils.Validator;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Objects;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.sidert.sidertmovil.utils.Constants.CONTROLLER_MOVIL;
import static com.sidert.sidertmovil.utils.Constants.ID_RESPUESTA;
import static com.sidert.sidertmovil.utils.Constants.TBL_CANCELACIONES;
import static com.sidert.sidertmovil.utils.Constants.TBL_PRESTAMOS_GPO_T;
import static com.sidert.sidertmovil.utils.Constants.TBL_PRESTAMOS_IND_T;
import static com.sidert.sidertmovil.utils.Constants.TBL_RESPUESTAS_GPO_T;
import static com.sidert.sidertmovil.utils.Constants.TBL_RESPUESTAS_IND_T;
import static com.sidert.sidertmovil.utils.Constants.TBL_RESPUESTAS_IND_V_T;
import static com.sidert.sidertmovil.utils.Constants.TBL_RESPUESTAS_INTEGRANTE_T;
import static com.sidert.sidertmovil.utils.Constants.TIMESTAMP;
import static com.sidert.sidertmovil.utils.Constants.TIPO_GESTION;
import static com.sidert.sidertmovil.utils.Constants.TIPO_PRESTAMO;


public class dialog_cancel_gestion extends DialogFragment {

    private Context ctx;

    private EditText etComentario;
    private Button btnEnviar;

    private ImageView ivClose;

    private String idRespuesta = "";
    private String tipoGestion = "0";
    private String tipoPrestamo = "";

    private DBhelper dBhelper;
    private SQLiteDatabase db;

    private SessionManager session;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.popup_cancel_gestion, container, false);

        ctx          = getContext();

        session = SessionManager.getInstance(ctx);

        dBhelper = DBhelper.getInstance(ctx);
        db = dBhelper.getWritableDatabase();

        etComentario = v.findViewById(R.id.etComentario);
        btnEnviar    = v.findViewById(R.id.btnEnviar);

        ivClose      = v.findViewById(R.id.ivClose);

        idRespuesta = getArguments().getString(ID_RESPUESTA);
        tipoGestion = getArguments().getString(TIPO_GESTION);
        tipoPrestamo = getArguments().getString(TIPO_PRESTAMO);

        getDialog().getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        getDialog().getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));

        return v;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        btnEnviar.setOnClickListener(btnEnviar_OnClick);
        ivClose.setOnClickListener(ivClose_OnClick);
    }

    private View.OnClickListener btnEnviar_OnClick = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Validator validator = new Validator();
            if (!validator.validate(etComentario, new String[]{validator.REQUIRED})){
                AlertDialog guardar_info_dlg = Popups.showDialogConfirm(ctx, Constants.question,
                        R.string.mess_solicitud_cancel_gestion, R.string.yes, new Popups.DialogMessage() {
                            @Override
                            public void OnClickListener(AlertDialog dialog) {
                                HashMap<Integer, String> params = new HashMap<>();
                                params.put(0, idRespuesta);
                                params.put(1, "");
                                params.put(2, tipoGestion);
                                params.put(3, tipoPrestamo);
                                params.put(4, etComentario.getText().toString().trim().toUpperCase());
                                params.put(5, "");
                                params.put(6, "");
                                params.put(7, Miscellaneous.ObtenerFecha(TIMESTAMP));
                                params.put(8, "");

                                SendSolicitudCancelacion(dBhelper.saveCancelGestiones(db, params));
                                dialog.dismiss();
                                VistaGestion vGestion = (VistaGestion) getActivity();
                                vGestion.solicitudCancel();
                                getDialog().dismiss();
                            }
                        }, R.string.no, new Popups.DialogMessage() {
                            @Override
                            public void OnClickListener(AlertDialog dialog) {
                                dialog.dismiss();
                            }
                        });
                Objects.requireNonNull(guardar_info_dlg.getWindow()).requestFeature(Window.FEATURE_NO_TITLE);
                guardar_info_dlg.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
                guardar_info_dlg.show();
            }
        }
    };

    private void SendSolicitudCancelacion(final Long id){
        String sql = "";
        if (tipoGestion.equals("1") && tipoPrestamo.equals("VIGENTE"))
            sql = "SELECT i.fecha_inicio, i.fecha_fin, i.id_prestamo, p.num_solicitud, 'VIGENTE' AS tipo, 1 AS tipo_gestion FROM " + TBL_RESPUESTAS_IND_T + " AS i INNER JOIN " + TBL_PRESTAMOS_IND_T + " AS p ON p.id_prestamo = i.id_prestamo WHERE i._id = ?";
        else if(tipoGestion.equals("2") && tipoPrestamo.equals("VIGENTE"))
            sql = "SELECT g.fecha_inicio, g.fecha_fin, g.id_prestamo, p.num_solicitud, 'VIGENTE' AS tipo, 2 AS tipo_gestion FROM " + TBL_RESPUESTAS_GPO_T + " AS g INNER JOIN " + TBL_PRESTAMOS_GPO_T + " AS p ON p.id_prestamo = g.id_prestamo WHERE g._id = ?";
        else if(tipoGestion.equals("1") && tipoPrestamo.equals("VENCIDA"))
            sql = "SELECT i.fecha_inicio, i.fecha_fin, i.id_prestamo, p.num_solicitud, 'VENCIDA' AS tipo, 1 AS tipo_gestion FROM " + TBL_RESPUESTAS_IND_V_T + " AS i INNER JOIN " + TBL_PRESTAMOS_IND_T + " AS p ON p.id_prestamo = i.id_prestamo WHERE i._id = ?";
        else if(tipoGestion.equals("2") && tipoPrestamo.equals("VENCIDA"))
            sql = "SELECT g.fecha_inicio, g.fecha_fin, g.id_prestamo, p.num_solicitud, 'VENCIDA' AS tipo, 2 AS tipo_gestion FROM " + TBL_RESPUESTAS_INTEGRANTE_T + " AS g INNER JOIN " + TBL_PRESTAMOS_GPO_T + " AS p ON p.id_prestamo = g.id_prestamo WHERE g._id = ?";
        Cursor row = db.rawQuery(sql, new String[]{idRespuesta});

        if (row.getCount() > 0){
            row.moveToFirst();
            try {

                JSONObject item = new JSONObject();
                item.put("fecha_inicio_gestion", row.getString(0));
                item.put("fecha_fin_gestion", row.getString(1));
                item.put("tipo_gestion", row.getInt(5));

                RequestBody idPrestamoBody = RequestBody.create(MultipartBody.FORM, row.getString(2));
                RequestBody numSolicitudBody = RequestBody.create(MultipartBody.FORM, row.getString(3));
                RequestBody respuestaBody = RequestBody.create(MultipartBody.FORM, item.toString());
                RequestBody tipoBody = RequestBody.create(MultipartBody.FORM, row.getString(4));
                RequestBody comentarioBody = RequestBody.create(MultipartBody.FORM, etComentario.getText().toString().trim().toUpperCase());
                RequestBody fechaSoliBody = RequestBody.create(MultipartBody.FORM, Miscellaneous.ObtenerFecha(TIMESTAMP));

                ManagerInterface api = RetrofitClient.generalRF(CONTROLLER_MOVIL, ctx).create(ManagerInterface.class);

                Call<MSolicitudCancelacion> call = api.solicitudCancelar("Bearer "+ session.getUser().get(7),
                        idPrestamoBody,
                        numSolicitudBody,
                        respuestaBody,
                        tipoBody,
                        comentarioBody,
                        fechaSoliBody);

                call.enqueue(new Callback<MSolicitudCancelacion>() {
                    @Override
                    public void onResponse(Call<MSolicitudCancelacion> call, Response<MSolicitudCancelacion> response) {
                        MSolicitudCancelacion resp = response.body();
                        switch (response.code()){
                            case 200:
                                ContentValues cv = new ContentValues();
                                cv.put("id_solicitud", resp.getIdCancelacion());
                                cv.put("estatus", "PENDIENTE");
                                db.update(TBL_CANCELACIONES, cv, "_id = ?", new String[]{String.valueOf(id)});
                                break;
                        }
                    }

                    @Override
                    public void onFailure(Call<MSolicitudCancelacion> call, Throwable t) {

                    }
                });



            } catch (JSONException e) {
                e.printStackTrace();
            }

        }
        row.close();
    }

    private View.OnClickListener ivClose_OnClick = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            getDialog().dismiss();
        }
    };
}
