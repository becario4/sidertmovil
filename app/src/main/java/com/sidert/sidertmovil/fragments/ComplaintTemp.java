package com.sidert.sidertmovil.fragments;


import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.sidert.sidertmovil.Home;
import com.sidert.sidertmovil.R;
import com.sidert.sidertmovil.fragments.dialogs.dialog_mailbox;
import com.sidert.sidertmovil.utils.NameFragments;

import static com.sidert.sidertmovil.activities.Signature.verifyStoragePermissions;


public class ComplaintTemp extends Fragment {

    private Context ctx;
    private Button btnComplaint;
    private Home boostrap;
    private final static int WRPERMS = 5564;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_complaint_temp, container, false);
        ctx = getContext();
        boostrap    = (Home) getActivity();
        btnComplaint = view.findViewById(R.id.btnComplaint);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        btnComplaint.setOnClickListener(btnComplaint_OnClick);
    }

    private View.OnClickListener btnComplaint_OnClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (ContextCompat.checkSelfPermission(getContext(), android.Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(getActivity(), new String[]{android.Manifest.permission.READ_EXTERNAL_STORAGE}, WRPERMS);
            } else {
                dialog_mailbox complaint = new dialog_mailbox();
                complaint.show(boostrap.getSupportFragmentManager(), NameFragments.DIALOGMAILBOX);
            }
        }
    };

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == WRPERMS) {
            dialog_mailbox complaint = new dialog_mailbox();
            complaint.show(boostrap.getSupportFragmentManager(), NameFragments.DIALOGMAILBOX);
        }
    }
}
