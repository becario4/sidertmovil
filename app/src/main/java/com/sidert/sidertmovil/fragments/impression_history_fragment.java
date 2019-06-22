package com.sidert.sidertmovil.fragments;


import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.Toast;

import com.sidert.sidertmovil.Home;
import com.sidert.sidertmovil.R;
import com.sidert.sidertmovil.adapters.ImpressionsAdapter;
import com.sidert.sidertmovil.database.DBhelper;
import com.sidert.sidertmovil.models.ImpressionsFields;
import com.sidert.sidertmovil.utils.Constants;

import java.util.ArrayList;


public class impression_history_fragment extends Fragment {

    private Context ctx;
    private Home boostrap;

    private RadioButton rbRecovery;
    private RadioButton rbCollect;
    private RadioButton rbWallet;

    private RecyclerView rvHistory;

    private ImpressionsAdapter adapter;

    private DBhelper dbHelper;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_impression_history, container, false);

        ctx     = getContext();
        boostrap    = (Home) getActivity();

        rbRecovery  = view.findViewById(R.id.rbRecovery);
        rbCollect   = view.findViewById(R.id.rbCollect);
        rbWallet    = view.findViewById(R.id.rbWalletExpire);

        rvHistory   = view.findViewById(R.id.rvLogImp);

        rvHistory.setLayoutManager(new LinearLayoutManager(ctx));
        rvHistory.setHasFixedSize(false);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        boostrap.setTitle("Historial de impresiones");
        dbHelper = new DBhelper(ctx);


        rbRecovery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FillAdapter(Constants.LOG_ASESSOR,1);
            }
        });

        rbCollect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FillAdapter(Constants.LOG_ASESSOR,3);
            }
        });

        rbWallet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FillAdapter(Constants.LOG_MANAGER, 2);
            }
        });
    }

    private void FillAdapter (String table, int id){
        rvHistory.setAdapter(null);
        Cursor row;

        row = dbHelper.getDataImpresionsLog(table,""," ORDER BY folio DESC",null);

        if (row.getCount() > 0){

            ArrayList<ImpressionsFields> obj = new ArrayList<>();
            row.moveToFirst();
            for (int i = 0; i < row.getCount(); i++){
                if (id == 1){
                    if (row.getString(3).contains("ri") || row.getString(3).contains("rg")){
                    Log.v("Recuperacion",row.getString(5));
                        obj.add(new
                                ImpressionsFields(row.getString(3),
                                row.getString(2),
                                row.getString(5),
                                row.getString(1),
                                row.getString(4),
                                row.getString(9),
                                row.getString(7),
                                row.getString(8)));
                    }
                }
                else if (id == 2){
                    if (row.getString(3).contains("cvi") || row.getString(3).contains("cvg")){
                        obj.add(new
                                ImpressionsFields(row.getString(3),
                                row.getString(2),
                                row.getString(5),
                                row.getString(1),
                                row.getString(4),
                                row.getString(9),
                                row.getString(7),
                                row.getString(8)));
                    }
                }
                else{
                    if (row.getString(3).contains("cvi") || row.getString(3).contains("cvg")){
                        obj.add(new
                                ImpressionsFields(row.getString(3),
                                row.getString(2),
                                row.getString(5),
                                row.getString(1),
                                row.getString(4),
                                row.getString(9),
                                row.getString(7),
                                row.getString(8)));
                    }
                }

                row.moveToNext();
            }
            if (obj.size() == 0) {
                Toast.makeText(ctx,"Sin contenido", Toast.LENGTH_SHORT).show();
            }

            adapter =  new ImpressionsAdapter(ctx,obj);
            rvHistory.setAdapter(adapter);
        }
        else {
            Toast.makeText(ctx,"Sin contenido", Toast.LENGTH_SHORT).show();
        }
    }
}
