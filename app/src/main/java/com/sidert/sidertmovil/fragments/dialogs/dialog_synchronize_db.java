package com.sidert.sidertmovil.fragments.dialogs;


import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.sidert.sidertmovil.R;
import com.sidert.sidertmovil.database.DBhelper;
import com.sidert.sidertmovil.models.AsesorID;
import com.sidert.sidertmovil.models.SynchronizeBD;
import com.sidert.sidertmovil.utils.Constants;
import com.sidert.sidertmovil.utils.ManagerInterface;
import com.sidert.sidertmovil.utils.Miscellaneous;
import com.sidert.sidertmovil.utils.NetworkStatus;
import com.sidert.sidertmovil.utils.Popups;
import com.sidert.sidertmovil.utils.RetrofitClient;
import com.sidert.sidertmovil.utils.Validator;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class dialog_synchronize_db extends DialogFragment {

    private Context ctx;
    private ImageView ivClose;
    private EditText etIdAsessor;
    private Button btnSynchronize;
    private Validator validator;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.popup_synchronize_db, container, false);
        ctx             = getContext();

        ivClose         = view.findViewById(R.id.ivClose);
        etIdAsessor     = view.findViewById(R.id.etIdAsesor);
        btnSynchronize  = view.findViewById(R.id.btnSynchronize);

        getDialog().getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        getDialog().getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        setCancelable(false);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        validator = new Validator();

        ivClose.setOnClickListener(ivClose_OnClick);
        btnSynchronize.setOnClickListener(btnSynchronize_OnClick);
    }

    private View.OnClickListener ivClose_OnClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            getDialog().dismiss();
        }
    };

    private View.OnClickListener btnSynchronize_OnClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (!validator.validate(etIdAsessor,new String[] {validator.REQUIRED})){
                DownloadImpressions();
            }
        }
    };

    public void DownloadImpressions(){
        if (NetworkStatus.haveNetworkConnection(ctx)){
            final AlertDialog loading = Popups.showLoadingDialog(ctx,R.string.please_wait, R.string.loading_info);
            loading.show();

            ManagerInterface api = new RetrofitClient().generalRF().create(ManagerInterface.class);

            AsesorID obj = new AsesorID(etIdAsessor.getText().toString().trim());

            Call<List<SynchronizeBD>> call = api.getImpressions(obj);

            call.enqueue(new Callback<List<SynchronizeBD>>() {
                @Override
                public void onResponse(Call<List<SynchronizeBD>> call, Response<List<SynchronizeBD>> response) {
                    List<SynchronizeBD> result = response.body();
                    if (result.size() > 0){
                        DBhelper dBhelper = new DBhelper(ctx);
                        SQLiteDatabase db = dBhelper.getWritableDatabase();
                        Cursor row;
                        for (int i = 0; i < result.size(); i++){
                            String externalID = result.get(i).getExternalid();
                            String table = Miscellaneous.getTableLog(externalID);
                            String where = " WHERE external_id = ? AND folio = ? AND type_impression = ? ";
                            String order = "";
                            String[] args =  new String[] {result.get(i).getExternalid(), result.get(i).getFolio(), result.get(i).getTipo()};
                            row = dBhelper.getRecords(table, where, order, args);
                            Log.v("CountSelect",row.getCount()+"");
                            if (row.getCount() == 0){
                                HashMap<Integer, String> params = new HashMap<>();
                                params.put(0, result.get(i).getFolio());
                                params.put(1, result.get(i).getAsesorid());
                                params.put(2, result.get(i).getExternalid());
                                params.put(3, String.valueOf(result.get(i).getMontoRealizado()));
                                params.put(4, result.get(i).getTipo());
                                params.put(5, String.valueOf(result.get(i).getErrores()));
                                params.put(6, result.get(i).getGeneratedAt());
                                params.put(7, result.get(i).getSendedAt());
                                params.put(8, "0");
                                dBhelper.saveRecordsImpressionsLog(db,table, params);
                            }
                        }
                        Toast.makeText(ctx, "Base de datos sincronizada", Toast.LENGTH_SHORT).show();
                    }
                    else {
                        Toast.makeText(ctx, "No se encontró información", Toast.LENGTH_SHORT).show();
                    }
                    etIdAsessor.setText("");
                    loading.dismiss();
                                    }
                @Override
                public void onFailure(Call<List<SynchronizeBD>> call, Throwable t) {
                    loading.dismiss();
                }
            });


        }
        else{
            final AlertDialog errorInternet = Popups.showDialogMessage(ctx, Constants.not_network,
                    R.string.not_network, R.string.accept, new Popups.DialogMessage() {
                @Override
                public void OnClickListener(AlertDialog dialog) {
                    dialog.dismiss();
                }
            });
            errorInternet.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
            errorInternet.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
            errorInternet.show();
        }
    }
}
