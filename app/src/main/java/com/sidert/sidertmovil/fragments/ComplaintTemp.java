package com.sidert.sidertmovil.fragments;


import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.sidert.sidertmovil.Home;
import com.sidert.sidertmovil.R;
import com.sidert.sidertmovil.fragments.dialogs.dialog_mailbox;
import com.sidert.sidertmovil.utils.NameFragments;


public class ComplaintTemp extends Fragment {

    private Context ctx;
    private Button btnComplaint;
    private Home boostrap;

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
            dialog_mailbox complaint = new dialog_mailbox();
            complaint.show(boostrap.getSupportFragmentManager(), NameFragments.DIALOGMAILBOX);

        }
    };

}
