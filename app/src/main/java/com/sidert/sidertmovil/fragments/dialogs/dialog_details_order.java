package com.sidert.sidertmovil.fragments.dialogs;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ImageButton;
import android.widget.TextView;

import com.sidert.sidertmovil.R;
import com.sidert.sidertmovil.activities.CarteraVencidaGrupal;
import com.sidert.sidertmovil.activities.CarteraVencidaIndividual;
import com.sidert.sidertmovil.activities.CobranzaGrupal;
import com.sidert.sidertmovil.activities.CobranzaIndividual;
import com.sidert.sidertmovil.activities.RecuperacionGrupal;
import com.sidert.sidertmovil.activities.RecuperacionIndividual;
import com.sidert.sidertmovil.database.DBhelper;
import com.sidert.sidertmovil.models.ModeloGrupal;
import com.sidert.sidertmovil.models.ModeloIndividual;
import com.sidert.sidertmovil.utils.Constants;
import com.sidert.sidertmovil.utils.Miscellaneous;
import com.sidert.sidertmovil.utils.Popups;

import org.json.JSONException;
import org.json.JSONObject;

public class dialog_details_order extends DialogFragment {

    private ImageButton ibOrderDetails, ibClose;
    private  TextView tvExternalID,tvNombre, tvDireccion, tvNumeroPrestamo, tvMontoPrestamo, tvFechaPagoEstablecida, tvTelefono;
    private Context ctx;
    ModeloIndividual ind;
    ModeloGrupal gpo;
    private AlertDialog loading;
    private String external_id = "";
    private String formulario = "";
    private DBhelper dBhelper ;
    private SQLiteDatabase db;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.details_order_popup,container,false);
        ctx                     = getContext();
        dBhelper = new DBhelper(ctx);
        db = dBhelper.getWritableDatabase();

        tvExternalID            = view.findViewById(R.id.tvExternalID);
        tvNombre                = view.findViewById(R.id.tvNombre);
        tvDireccion             = view.findViewById(R.id.tvDireccion);
        tvNumeroPrestamo        = view.findViewById(R.id.tvNumeroPrestamo);
        tvMontoPrestamo         = view.findViewById(R.id.tvMontoPrestamo);
        tvFechaPagoEstablecida  = view.findViewById(R.id.tvFechaPagoEstablecida);
        tvTelefono              = view.findViewById(R.id.tvTelefono);
        ibOrderDetails          = view.findViewById(R.id.ibDetails);
        ibClose                 = view.findViewById(R.id.ibClose);
        getDialog().getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        getDialog().getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));

        external_id = getArguments().getString(Constants.ORDER_ID);
        formulario = getArguments().getString(Constants.TYPE);
        /*if (getArguments().containsKey(Constants.INDIVIDUAL))
            ind = (ModeloIndividual) getArguments().getSerializable(Constants.INDIVIDUAL);
        else
            gpo = (ModeloGrupal) getArguments().getSerializable(Constants.GRUPO);*/

        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        Cursor row = dBhelper.getRecords(Constants.FICHAS_T, " WHERE external_id = '"+external_id+"'", "", null);
        row.moveToFirst();


        tvExternalID.setText(external_id);
        tvNombre.setText(row.getString(8));
        try {
            JSONObject json = new JSONObject(row.getString(7));
            tvNumeroPrestamo.setText(json.getString(Constants.NUMERO_DE_PRESTAMO));
            tvMontoPrestamo.setText(Miscellaneous.moneyFormat(json.getString(Constants.MONTO_PRESTAMO)));
            tvFechaPagoEstablecida.setText(json.getString(Constants.FECHA_PAGO_ESTABLECIDA));

        } catch (JSONException e) {
            e.printStackTrace();
        }

        if (Miscellaneous.getIndorGpo(row.getString(2)) == 1){
            try {
                JSONObject jsonCliente = new JSONObject(row.getString(10));
                tvDireccion.setText(jsonCliente.getJSONObject(Constants.DIRECCION).getString(Constants.CALLE)+", "+
                        jsonCliente.getJSONObject(Constants.DIRECCION).getString(Constants.COLONIA)+", "+
                        jsonCliente.getJSONObject(Constants.DIRECCION).getString(Constants.MUNICIPIO));
                tvTelefono.setText(jsonCliente.getString(Constants.TEL_CELULAR));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        else {
            try {
                JSONObject jsonTesorera = new JSONObject(row.getString(13));
                tvDireccion.setText(jsonTesorera.getJSONObject(Constants.DIRECCION).getString(Constants.ADDRESS));
                tvTelefono.setText(jsonTesorera.getString(Constants.TELEFONO_CELULAR));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }


        ibOrderDetails.setOnClickListener(ibOrderDetails_OnClick);
        ibClose.setOnClickListener(ibClose_OnClick);
    }

    private View.OnClickListener ibOrderDetails_OnClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent intent_order = null;
            loading = Popups.showLoadingDialog(ctx,R.string.please_wait, R.string.loading_info);
            loading.show();

            switch (formulario){
                case Constants.COBRANZA_IND:
                case Constants.RECUPERACION_IND:
                    intent_order = new Intent(ctx, RecuperacionIndividual.class);
                    intent_order.putExtra(Constants.ORDER_ID, external_id);
                    startActivityForResult(intent_order, Constants.REQUEST_CODE_ACTIVITY);
                    break;
                case Constants.CARTERA_VENCIDA_IND:
                    intent_order = new Intent(ctx, CarteraVencidaIndividual.class);
                    intent_order.putExtra(Constants.INDIVIDUAL, ind);
                    startActivityForResult(intent_order, Constants.REQUEST_CODE_ACTIVITY);
                    break;
                case Constants.RECUPERACION_GPO:
                case Constants.COBRANZA_GPO:
                    intent_order = new Intent(ctx, RecuperacionGrupal.class);
                    intent_order.putExtra(Constants.ORDER_ID, external_id);
                    startActivityForResult(intent_order, Constants.REQUEST_CODE_ACTIVITY);
                    break;
                case Constants.CARTERA_VENCIDA_GPO:
                    intent_order = new Intent(ctx, CarteraVencidaGrupal.class);
                    intent_order.putExtra(Constants.GRUPO, gpo);
                    startActivityForResult(intent_order, Constants.REQUEST_CODE_ACTIVITY);
                    break;
            }

        }
    };

    private View.OnClickListener ibClose_OnClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            getDialog().dismiss();
        }
    };

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode){
            case Constants.REQUEST_CODE_ACTIVITY:
                if (resultCode == Activity.RESULT_OK){
                    if (data != null){
                        loading.dismiss();
                        getDialog().dismiss();
                    }
                }
                break;
        }
    }
}
