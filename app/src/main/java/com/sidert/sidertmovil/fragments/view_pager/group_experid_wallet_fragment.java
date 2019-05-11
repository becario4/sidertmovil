package com.sidert.sidertmovil.fragments.view_pager;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.sidert.sidertmovil.R;
import com.sidert.sidertmovil.activities.GroupMembersInfo;


public class group_experid_wallet_fragment extends Fragment {

    private Button btnGroupMembers;
    private Context ctx;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_group_experid_wallet, container, false);
        ctx = getContext();
        btnGroupMembers = view.findViewById(R.id.btnGroupMembers);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        btnGroupMembers.setOnClickListener(btnGroupMembers_OnClick);
    }

    private View.OnClickListener btnGroupMembers_OnClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent members_info = new Intent(ctx, GroupMembersInfo.class);
            startActivity(members_info);
        }
    };
}
