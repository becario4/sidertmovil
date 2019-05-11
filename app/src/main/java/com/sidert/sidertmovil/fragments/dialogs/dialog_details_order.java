package com.sidert.sidertmovil.fragments.dialogs;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ImageButton;
import android.widget.TextView;

import com.sidert.sidertmovil.R;
import com.sidert.sidertmovil.activities.GroupCollection;
import com.sidert.sidertmovil.activities.GroupExpiredWallet;
import com.sidert.sidertmovil.activities.GroupRecovery;
import com.sidert.sidertmovil.activities.IndividualCollection;
import com.sidert.sidertmovil.activities.IndividualExpiredWallet;
import com.sidert.sidertmovil.activities.IndividualRecovery;
import com.sidert.sidertmovil.utils.Constants;

public class dialog_details_order extends DialogFragment {

    private ImageButton ibOrderDetails, ibClose;
    private  TextView tvNameClient, tvAddress, tvLoanNumber, tvLoanAmount, tvPaymentDate, tvPhone;
    private Context ctx;
    private int type;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.details_order_popup,container,false);
        ctx                 = getContext();
        ibOrderDetails      = view.findViewById(R.id.ibDetails);
        ibClose             = view.findViewById(R.id.ibClose);
        getDialog().getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        getDialog().getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        type = getArguments().getInt(Constants.type);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        ibOrderDetails.setOnClickListener(ibOrderDetails_OnClick);
        ibClose.setOnClickListener(ibClose_OnClick);
    }

    private View.OnClickListener ibOrderDetails_OnClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent intent_order = null;
            switch (type){
                case 0:
                    intent_order = new Intent(ctx, IndividualRecovery.class);
                    startActivity(intent_order);
                    break;
                case 1:
                    intent_order = new Intent(ctx, GroupRecovery.class);
                    startActivity(intent_order);
                    break;
                case 2:
                    intent_order = new Intent(ctx, IndividualCollection.class);
                    startActivity(intent_order);
                    break;
                case 3:
                    intent_order = new Intent(ctx, GroupCollection.class);
                    startActivity(intent_order);
                    break;
                case 4:
                    intent_order = new Intent(ctx, IndividualExpiredWallet.class);
                    startActivity(intent_order);
                    break;
                case 5:
                    intent_order = new Intent(ctx, GroupExpiredWallet.class);
                    startActivity(intent_order);
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
}
