package com.sidert.sidertmovil.fragments;

import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
//import android.support.v4.app.Fragment;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;

import com.google.gson.Gson;
import com.sidert.sidertmovil.Home;
import com.sidert.sidertmovil.R;
import com.sidert.sidertmovil.adapters.autorizaciones_adapter;
import com.sidert.sidertmovil.fragments.dialogs.dialog_info_consulta_cc;
import com.sidert.sidertmovil.models.MAutorizarCC;
import com.sidert.sidertmovil.models.MRespuestaCC;
import com.sidert.sidertmovil.utils.Constants;
import com.sidert.sidertmovil.utils.ManagerInterface;
import com.sidert.sidertmovil.utils.Miscellaneous;
import com.sidert.sidertmovil.utils.NameFragments;
import com.sidert.sidertmovil.utils.Popups;
import com.sidert.sidertmovil.utils.RetrofitClient;
import com.sidert.sidertmovil.utils.Servicios_Sincronizado;
import com.sidert.sidertmovil.utils.SessionManager;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.sidert.sidertmovil.utils.Constants.CONTROLLER_API;


public class autorizaciones_cc_fragment extends Fragment {

    private Context ctx;
    private Home boostrap;

    private RecyclerView rvAutorizaCC;

    private autorizaciones_adapter adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_autorizaciones_cc, container, false);

        ctx     = getContext();

        boostrap = (Home) getActivity();

        rvAutorizaCC = v.findViewById(R.id.rvAutorizaCC);

        rvAutorizaCC.setLayoutManager(new LinearLayoutManager(ctx));
        rvAutorizaCC.setHasFixedSize(false);

        boostrap.setTitle("Consultas CC");

        boostrap.invalidateOptionsMenu();

        GetAutorizaciones();
        return v;
    }

    private void GetAutorizaciones(){
        final AlertDialog loading = Popups.showLoadingDialog(ctx, R.string.please_wait, R.string.loading_info);

        loading.show();

        final SessionManager session = SessionManager.getInstance(ctx);
        ManagerInterface api = RetrofitClient.generalRF(CONTROLLER_API, ctx).create(ManagerInterface.class);

        Call<List<MAutorizarCC>> call = api.getAutorizarCC(
                "Bearer "+ session.getUser().get(7),
                "2020-11-16",
                "2021-01-14",
                "8");

        call.enqueue(new Callback<List<MAutorizarCC>>() {
            @Override
            public void onResponse(Call<List<MAutorizarCC>> call, Response<List<MAutorizarCC>> response) {
                Log.e("ResAutorizaCode", "code"+response.code());
                switch (response.code()){
                    case 200:
                        List<MAutorizarCC> consultasCC = response.body();

                        adapter = new autorizaciones_adapter(ctx, consultasCC, new autorizaciones_adapter.Event() {
                            @Override
                            public void infoClick(MAutorizarCC item) {
                                if (!item.getRespuesta().isEmpty()){
                                    dialog_info_consulta_cc dlg_info = new dialog_info_consulta_cc();

                                    Bundle b = new Bundle();
                                    b.putString("file_pdf", item.getPdf());
                                    b.putString("info", item.getRespuesta());
                                    b.putString("monto_solicitado", String.valueOf(item.getMontoSolicitado()));
                                    dlg_info.setArguments(b);
                                    dlg_info.show(boostrap.getSupportFragmentManager(), NameFragments.DIALOGINFOAUTORIZARCC);
                                }
                                else{

                                    MRespuestaCC respuesta = new Gson().fromJson(item.getError(), MRespuestaCC.class);

                                    AlertDialog error_connect = Popups.showDialogMessage(ctx, Constants.warning,
                                            respuesta.getErrores().get(0).getCodigo()+"\n"+respuesta.getErrores().get(0).getMensaje(), R.string.accept, new Popups.DialogMessage() {
                                                @Override
                                                public void OnClickListener(AlertDialog dialog) {
                                                    dialog.dismiss();
                                                }
                                            });
                                    error_connect.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
                                    error_connect.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
                                    error_connect.show();
                                }
                            }
                        });

                        rvAutorizaCC.setAdapter(adapter);
                        /*for (MAutorizarCC item : consultasCC){
                            Gson g = new Gson();
                            MRespuestaCC respuesta;

                            if (!item.getRespuesta().isEmpty())
                                MRespuestaCC respuesta = new Gson().fromJson(item.getRespuesta(), MRespuestaCC.class);
                            else
                                respuesta = new Gson().fromJson(item.getError(), MRespuestaCC.class);

                            Log.e("Created_At", Miscellaneous.validStr(item.getCreatedAt()));
                        }*/
                        break;
                }
                loading.dismiss();
            }

            @Override
            public void onFailure(Call<List<MAutorizarCC>> call, Throwable t) {
                loading.dismiss();
            }
        });
    }
}
