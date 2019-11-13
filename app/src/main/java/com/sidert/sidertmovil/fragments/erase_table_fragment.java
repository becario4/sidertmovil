package com.sidert.sidertmovil.fragments;


import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.sidert.sidertmovil.Home;
import com.sidert.sidertmovil.R;
import com.sidert.sidertmovil.database.DBhelper;
import com.sidert.sidertmovil.database.SidertTables;
import com.sidert.sidertmovil.utils.Constants;

import java.util.HashMap;

public class erase_table_fragment extends Fragment {

    private Context ctx;
    private Button btnImpresiones;
    private Button btnFichas;
    private Home root;

    private DBhelper dBhelper;
    private SQLiteDatabase db;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_erase_table, container, false);
        ctx = getContext();
        root = (Home) getActivity();

        dBhelper = new DBhelper(ctx);
        db = dBhelper.getWritableDatabase();
        btnFichas = view.findViewById(R.id.btnFichas);
        btnImpresiones  = view.findViewById(R.id.btnImpresiones);

        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        btnImpresiones.setOnClickListener(btnImpresiones_OnClick);
        btnFichas.setOnClickListener(btnFichas_OnClick);
    }

    private View.OnClickListener btnImpresiones_OnClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            db.execSQL("DELETE FROM " + SidertTables.SidertEntry.TABLE_NAME_LOG_ASESSOR);
            db.execSQL("DELETE FROM " + SidertTables.SidertEntry.TABLE_NAME_LOG_MANAGER);
        }
    };

    private View.OnClickListener btnFichas_OnClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            db.execSQL("DELETE FROM " + SidertTables.SidertEntry.TABLE_FICHAS_T);
        }
    };
}
