package com.sidert.sidertmovil.fragments.dialogs;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.NumberPicker;

import com.sidert.sidertmovil.R;
import com.sidert.sidertmovil.activities.SolicitudCreditoInd;
import com.sidert.sidertmovil.utils.Constants;


public class dialog_time_picker extends DialogFragment {

    final String[] valuesHour= {"08", "09", "10","11", "12", "13", "14","15","16", "17", "18", "19", "20","21", "22"};
    final String[] valuesMinute= {"00","15", "30", "45"};
    private NumberPicker npHour;
    private NumberPicker npMinute;
    private Button btnAceptar;

    private int identifier;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.popup_time_picker, container, false);

        npHour      = view.findViewById(R.id.npHour);
        npMinute    = view.findViewById(R.id.npMinute);
        btnAceptar  = view.findViewById(R.id.btnAceptar);

        npHour.setMinValue(0);
        npMinute.setMinValue(0);
        npHour.setMaxValue(valuesHour.length-1);
        npMinute.setMaxValue(valuesMinute.length-1);
        npHour.setWrapSelectorWheel(true);
        npMinute.setWrapSelectorWheel(true);
        npHour.setDisplayedValues(valuesHour);
        npMinute.setDisplayedValues(valuesMinute);

        identifier      = getArguments().getInt(Constants.IDENTIFIER);

        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        btnAceptar.setOnClickListener(btnAceptar_OnClick);
    }

    private View.OnClickListener btnAceptar_OnClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {

            switch (identifier){
                case 1:
                    SolicitudCreditoInd registerActivityClie = (SolicitudCreditoInd) getActivity();
                    registerActivityClie.setTimer(valuesHour[npHour.getValue()]+":"+valuesMinute[npMinute.getValue()], "HoraVisita");
                    break;
                case 2:
                    SolicitudCreditoInd registerActivityAval = (SolicitudCreditoInd) getActivity();
                    registerActivityAval.setTimer(valuesHour[npHour.getValue()]+":"+valuesMinute[npMinute.getValue()], "HoraVisitaAval");
                    break;
                case 3:
                    Intent i = new Intent();
                    i.putExtra("hora", valuesHour[npHour.getValue()]+":"+valuesMinute[npMinute.getValue()]);
                    getTargetFragment().onActivityResult(159,753,i);
                    break;
            }
            dismiss();
        }
    };

}
