package com.sidert.sidertmovil.activities;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.sidert.sidertmovil.R;
import com.sidert.sidertmovil.adapters.adapter_group_members_info;
import com.sidert.sidertmovil.fragments.dialogs.dialog_ine_photo;
import com.sidert.sidertmovil.utils.Constants;
import com.sidert.sidertmovil.utils.NameFragments;

public class GroupMembersInfo extends AppCompatActivity {

    private Context ctx;
    private Toolbar TBmain;
    private RecyclerView rvMembersInfo;
    private adapter_group_members_info adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_group_members_info);
        ctx             = getApplicationContext();
        TBmain          = findViewById(R.id.TBmain);
        rvMembersInfo   = findViewById(R.id.rvMembersInfo);

        setSupportActionBar(TBmain);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        setTitle(getApplicationContext().getString(R.string.members_group));

        rvMembersInfo.setLayoutManager(new LinearLayoutManager(ctx));
        rvMembersInfo.setHasFixedSize(false);

        adapter = new adapter_group_members_info(ctx, new adapter_group_members_info.Event() {
            @Override
            public void IneOnClick(String ine) {
                dialog_ine_photo ine_photo = new dialog_ine_photo();
                Bundle b = new Bundle();
                b.putString(Constants.client_code, ine);
                ine_photo.setArguments(b);
                ine_photo.show(getSupportFragmentManager(), NameFragments.DIALOGINEPHOTO);
            }
        });
        rvMembersInfo.setAdapter(adapter);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

}
