package com.sidert.sidertmovil.fragments.view_pager;


import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;

import com.sidert.sidertmovil.R;
import com.sidert.sidertmovil.activities.IndividualRecovery;


public class ind_recovery_fragment extends Fragment {

    private Button btnCallHome;
    private Button btnCallCell;
    private Button btnCallEndorsement;

    private final int REQUEST_PERMISSION_CALL = 12312;

    private IndividualRecovery boostrap;
    private Context ctx;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_ind_recovery, container, false);
        ctx                     = getContext();
        boostrap                = (IndividualRecovery) getActivity();
        btnCallHome             = view.findViewById(R.id.btnCallHome);
        btnCallCell             = view.findViewById(R.id.btnCallCell);
        btnCallEndorsement      = view.findViewById(R.id.btnCallEndorsement);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        btnCallHome.setOnClickListener(btnCallHome_onClick);
        btnCallCell.setOnClickListener(btnCallCell_onClick);
        btnCallEndorsement.setOnClickListener(btnCallEndorsement_onClick);
    }

    private View.OnClickListener btnCallHome_onClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (ContextCompat.checkSelfPermission(ctx, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.CALL_PHONE, Manifest.permission.CALL_PHONE}, REQUEST_PERMISSION_CALL);
            } else {
                Intent intent = new Intent(Intent.ACTION_CALL);
                intent.setData(Uri.parse("tel:" + "2351074275"));
                startActivity(intent);
            }
        }
    };

    private View.OnClickListener btnCallCell_onClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (ContextCompat.checkSelfPermission(ctx, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.CALL_PHONE, Manifest.permission.CALL_PHONE}, REQUEST_PERMISSION_CALL);
            } else {
                Intent intent = new Intent(Intent.ACTION_CALL);
                intent.setData(Uri.parse("tel:" + "2351074275"));
                startActivity(intent);
            }
        }
    };

    private View.OnClickListener btnCallEndorsement_onClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (ContextCompat.checkSelfPermission(ctx, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.CALL_PHONE, Manifest.permission.CALL_PHONE}, REQUEST_PERMISSION_CALL);
            } else {
                Intent intent = new Intent(Intent.ACTION_CALL);
                intent.setData(Uri.parse("tel:" + "2351074275"));
                startActivity(intent);
            }
        }
    };
}
