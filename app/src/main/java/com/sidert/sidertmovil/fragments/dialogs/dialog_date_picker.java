package com.sidert.sidertmovil.fragments.dialogs;


import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
/*import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.appcompat.app.AlertDialog;*/
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.NumberPicker;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;

import com.sidert.sidertmovil.R;
import com.sidert.sidertmovil.activities.AgregarIntegrante;
import com.sidert.sidertmovil.activities.FormularioCC;
import com.sidert.sidertmovil.activities.GenerarCurp;
import com.sidert.sidertmovil.views.renovacion.RenovacionCreditoInd;
import com.sidert.sidertmovil.views.renovacion.RenovarIntegrante;
import com.sidert.sidertmovil.activities.ReporteInicioSesion;
import com.sidert.sidertmovil.activities.SolicitudCreditoInd;
import com.sidert.sidertmovil.activities.TrackerAsesor;
import com.sidert.sidertmovil.activities.VencidaIntegrante;
import com.sidert.sidertmovil.utils.Constants;
import com.sidert.sidertmovil.utils.Miscellaneous;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import static com.sidert.sidertmovil.utils.Constants.DATE;


public class dialog_date_picker extends DialogFragment {

//IDENTIFIER 8
    String[] valuesDays;
    String[] valuesMonths;
    String[] valuesYear;
    private NumberPicker npDay;
    private NumberPicker npMonth;
    private NumberPicker npYear;
    private Button btnAceptar;
    private int year_current = 0;
    private int month_current = 0;
    private int day_current = 0;
    private Calendar myCalendar;
    private String date_current = "";
    private boolean fechas_post = false;
    private SimpleDateFormat sdf = new SimpleDateFormat(Constants.FORMAT_DATE_GNRAL);
    private boolean ban;
    private int identifer = 0;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.popup_date_picker, container, false);

        npDay      = view.findViewById(R.id.npDay);
        npMonth    = view.findViewById(R.id.npMonth);
        npYear     = view.findViewById(R.id.npYear);
        btnAceptar = view.findViewById(R.id.btnAceptar);

        npDay.setWrapSelectorWheel(true);
        npMonth.setWrapSelectorWheel(true);
        npYear.setWrapSelectorWheel(true);

        year_current    = getArguments().getInt(Constants.YEAR_CURRENT);
        month_current   = getArguments().getInt(Constants.MONTH_CURRENT);
        day_current     = getArguments().getInt(Constants.DAY_CURRENT);
        date_current    = getArguments().getString(Constants.DATE_CURRENT);
        fechas_post      = getArguments().getBoolean(Constants.FECHAS_POST);
        identifer       = getArguments().getInt(Constants.IDENTIFIER);
        ban = true;

        Log.e("year_current", String.valueOf(year_current));
        valuesMonths = new String[]{"ENERO",
                                    "FEBRERO",
                                    "MARZO",
                                    "ABRIL",
                                    "MAYO",
                                    "JUNIO",
                                    "JULIO",
                                    "AGOSTO",
                                    "SEPTIEMBRE",
                                    "OCTUBRE",
                                    "NOVIEMBRE",
                                    "DICIEMBRE"};

        getDialog().getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        myCalendar = Calendar.getInstance();

        int i = myCalendar.get(Calendar.YEAR)-81;
        valuesYear = new String[83];
        int j = 0;
        while (i<(myCalendar.get(Calendar.YEAR)+2)){
            valuesYear[j] = ""+i;
            i++;
            j++;
        }

        int pos = 2;
        for (int x = 82; x < valuesYear.length; x--){
            Log.e("ValX: ",String.valueOf(x));
            Log.e("Year: ",valuesYear[x]);

            if (valuesYear[x].equals(String.valueOf(year_current))){
                pos = Math.abs(83 - x);
                break;
            }
        }

        //----------  AÑO  -----------------------
        npYear.setMinValue(0);
        npYear.setMaxValue(valuesYear.length-1);
        npYear.setDisplayedValues(valuesYear);
        npYear.setValue(valuesYear.length-pos);
        npYear.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
                getDaysOfMonth(Integer.parseInt(valuesYear[newVal]),npMonth.getValue());
            }
        });

        //----------  MES  -----------------------
        npMonth.setMinValue(0);
        npMonth.setMaxValue(valuesMonths.length-1);
        npMonth.setDisplayedValues(valuesMonths);
        npMonth.setValue(month_current);

        npMonth.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
                getDaysOfMonth(Integer.parseInt(valuesYear[npYear.getValue()]),newVal);
            }
        });

        getDaysOfMonth(year_current,month_current);
        btnAceptar.setOnClickListener(btnAceptar_OnClick);
    }

    private View.OnClickListener btnAceptar_OnClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            myCalendar.set(Calendar.YEAR,Integer.parseInt(valuesYear[npYear.getValue()]));
            myCalendar.set(Calendar.MONTH,npMonth.getValue());
            myCalendar.set(Calendar.DAY_OF_MONTH,Integer.parseInt(valuesDays[npDay.getValue()]));
            sdf.setTimeZone(myCalendar.getTimeZone());
            try {
                Date date1 = sdf.parse(date_current);
                Date date2 = sdf.parse(sdf.format(myCalendar.getTime()));
                if (fechas_post){
                    if (date2.after(date1) || date2.equals(date1)) {
                        if (identifer == 1) {
                            Calendar c = Calendar.getInstance();
                            String[] fechaDes = sdf.format(myCalendar.getTime()).split("-");
                            c.set(Integer.valueOf(fechaDes[0]), (Integer.valueOf(fechaDes[1]) - 1), Integer.valueOf(fechaDes[2]));
                            int nD=c.get(Calendar.DAY_OF_WEEK);
                            if (nD != 1) {
                                SolicitudCreditoInd registerActivity = (SolicitudCreditoInd) getActivity();
                                registerActivity.setDate(sdf.format(myCalendar.getTime()), "desembolso");
                                dismiss();
                            }
                            else{
                                AlertDialog success = new AlertDialog.Builder(getContext())
                                        .setMessage("No se puede seleccionar domingo para día de desembolso")
                                        .setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialogInterface, int i) {
                                                dialogInterface.dismiss();
                                            }
                                        }).show();
                            }
                        }
                        else if(identifer == 2){
                            Intent i = new Intent();
                            i.putExtra("fecha",sdf.format(myCalendar.getTime()));
                            getTargetFragment().onActivityResult(123,321,i);
                            dismiss();
                        }
                        else if(identifer == 10){
                            Intent i = new Intent();
                            i.putExtra(DATE,sdf.format(myCalendar.getTime()));
                            getTargetFragment().onActivityResult(213, 312,i);
                            dismiss();
                        }
                        else if(identifer == 12){
                            VencidaIntegrante integrante = (VencidaIntegrante) getActivity();
                            integrante.setDate(sdf.format(myCalendar.getTime()), "fecha_promesa");
                            dismiss();
                        }
                        else if(identifer == 13){
                            Calendar c = Calendar.getInstance();
                            String[] fechaDes = sdf.format(myCalendar.getTime()).split("-");
                            c.set(Integer.valueOf(fechaDes[0]), (Integer.valueOf(fechaDes[1]) - 1), Integer.valueOf(fechaDes[2]));
                            int nD=c.get(Calendar.DAY_OF_WEEK);
                            if (nD != 1) {
                                RenovacionCreditoInd registerActivity = (RenovacionCreditoInd) getActivity();
                                registerActivity.setDate(sdf.format(myCalendar.getTime()), "desembolso");
                                dismiss();
                            }
                            else{
                                AlertDialog success = new AlertDialog.Builder(getContext())
                                        .setMessage("No se puede seleccionar domingo para día de desembolso")
                                        .setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialogInterface, int i) {
                                                dialogInterface.dismiss();
                                            }
                                        }).show();
                            }
                        }
                        else {
                            Calendar c = Calendar.getInstance();
                            String[] fechaDes = sdf.format(myCalendar.getTime()).split("-");
                            c.set(Integer.valueOf(fechaDes[0]), (Integer.valueOf(fechaDes[1]) - 1), Integer.valueOf(fechaDes[2]));
                            int nD=c.get(Calendar.DAY_OF_WEEK);
                            if (nD != 1) {
                                Intent i = new Intent();
                                i.putExtra(DATE, sdf.format(myCalendar.getTime()));
                                getTargetFragment().onActivityResult(123, 321, i);
                                dismiss();
                            }
                            else{
                                AlertDialog success = new AlertDialog.Builder(getContext())
                                        .setMessage("No se puede seleccionar domingo para día de desembolso")
                                        .setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialogInterface, int i) {
                                                dialogInterface.dismiss();
                                            }
                                        }).show();
                            }
                        }

                    } else {
                        AlertDialog success = new AlertDialog.Builder(getContext())
                                .setMessage("No se pueden seleccionar fechas anteriores")
                                .setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {
                                        dialogInterface.dismiss();
                                    }
                                }).show();
                    }

                }
                else {
                    if (date2.before(date1) || date2.equals(date1)) {
                        if (identifer == 2){
                            if (Integer.parseInt(Miscellaneous.GetEdad(sdf.format(myCalendar.getTime()))) > 17 && Integer.parseInt(Miscellaneous.GetEdad(sdf.format(myCalendar.getTime()))) < 76){
                                SolicitudCreditoInd registerActivity = (SolicitudCreditoInd) getActivity();
                                registerActivity.setDate(sdf.format(myCalendar.getTime()),"fechaNacCli");
                                dismiss();
                            }
                            else{
                                new AlertDialog.Builder(getContext())
                                        .setMessage("Solo personas mayores de 18 años.")
                                        .setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialogInterface, int i) {
                                                dialogInterface.dismiss();
                                            }
                                        }).show();
                            }

                        }
                        else if (identifer == 3){
                            if (Integer.parseInt(Miscellaneous.GetEdad(sdf.format(myCalendar.getTime()))) > 17 && Integer.parseInt(Miscellaneous.GetEdad(sdf.format(myCalendar.getTime()))) < 76) {
                                SolicitudCreditoInd registerActivity = (SolicitudCreditoInd) getActivity();
                                registerActivity.setDate(sdf.format(myCalendar.getTime()), "fechaNacAval");
                                dismiss();
                            }
                            else{
                                new AlertDialog.Builder(getContext())
                                        .setMessage("Solo personas mayores de 18 años.")
                                        .setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialogInterface, int i) {
                                                dialogInterface.dismiss();
                                            }
                                        }).show();
                            }
                        }
                        else if (identifer == 4){
                            if (Integer.parseInt(Miscellaneous.GetEdad(sdf.format(myCalendar.getTime()))) > 17 && Integer.parseInt(Miscellaneous.GetEdad(sdf.format(myCalendar.getTime()))) < 76) {
                                AgregarIntegrante integranteActivity = (AgregarIntegrante) getActivity();
                                integranteActivity.setDate(sdf.format(myCalendar.getTime()), "fechaNacCli");
                                dismiss();
                            }
                            else{
                                new AlertDialog.Builder(getContext())
                                        .setMessage("Solo personas mayores de 18 años.")
                                        .setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialogInterface, int i) {
                                                dialogInterface.dismiss();
                                            }
                                        }).show();
                            }
                        }
                        else if(identifer == 5){
                            Intent i = new Intent();
                            i.putExtra(DATE,sdf.format(myCalendar.getTime()));
                            getTargetFragment().onActivityResult(123,321,i);
                            dismiss();
                        }
                        else if(identifer == 6){
                            Intent i = new Intent();
                            i.putExtra(DATE,sdf.format(myCalendar.getTime()));
                            getTargetFragment().onActivityResult(812,321,i);
                            dismiss();
                        }
                        else if(identifer == 7){
                            Intent i = new Intent();
                            i.putExtra(DATE,sdf.format(myCalendar.getTime()));
                            getTargetFragment().onActivityResult(123,321,i);
                            dismiss();
                        }
                        else if(identifer == 8){
                            Intent i = new Intent();
                            i.putExtra(DATE,sdf.format(myCalendar.getTime()));
                            getTargetFragment().onActivityResult(812,321,i);
                            dismiss();
                        }
                        else if(identifer == 9){
                            TrackerAsesor tracker = (TrackerAsesor) getActivity();
                            tracker.setDate(sdf.format(myCalendar.getTime()));
                            dismiss();
                        }
                        else if(identifer == 10){
                            VencidaIntegrante integrante = (VencidaIntegrante) getActivity();
                            integrante.setDate(sdf.format(myCalendar.getTime()), "fecha_pago");
                            dismiss();
                        }
                        else if(identifer == 11){
                            VencidaIntegrante integrante = (VencidaIntegrante) getActivity();
                            integrante.setDate(sdf.format(myCalendar.getTime()), "fecha_defuncion");
                            dismiss();
                        }
                        else if(identifer == 12){
                            ReporteInicioSesion inicioSesion = (ReporteInicioSesion) getActivity();
                            inicioSesion.setDate(sdf.format(myCalendar.getTime()));
                            dismiss();
                        }
                        else if(identifer == 13){
                            GenerarCurp generarCurp = (GenerarCurp) getActivity();
                            generarCurp.setDate(sdf.format(myCalendar.getTime()));
                            dismiss();
                        }
                        else if(identifer == 14){
                            Intent i = new Intent();
                            i.putExtra(DATE,sdf.format(myCalendar.getTime()));
                            getTargetFragment().onActivityResult(123,321,i);
                            dismiss();
                        }
                        else if (identifer == 15){
                            if (Integer.parseInt(Miscellaneous.GetEdad(sdf.format(myCalendar.getTime()))) > 17 && Integer.parseInt(Miscellaneous.GetEdad(sdf.format(myCalendar.getTime()))) < 76) {
                                SolicitudCreditoInd registerActivity = (SolicitudCreditoInd) getActivity();
                                registerActivity.setDate(sdf.format(myCalendar.getTime()), "fechaNacRef");
                                dismiss();
                            }
                            else{
                                new AlertDialog.Builder(getContext())
                                        .setMessage("Solo personas mayores de 18 años.")
                                        .setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialogInterface, int i) {
                                                dialogInterface.dismiss();
                                            }
                                        }).show();
                            }
                        }
                        else if (identifer == 16){
                            if (Integer.parseInt(Miscellaneous.GetEdad(sdf.format(myCalendar.getTime()))) > 17 && Integer.parseInt(Miscellaneous.GetEdad(sdf.format(myCalendar.getTime()))) < 76) {
                                RenovacionCreditoInd registerActivity = (RenovacionCreditoInd) getActivity();
                                registerActivity.setDate(sdf.format(myCalendar.getTime()), "fechaNacAval");
                                dismiss();
                            }
                            else{
                                new AlertDialog.Builder(getContext())
                                        .setMessage("Solo personas mayores de 18 años.")
                                        .setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialogInterface, int i) {
                                                dialogInterface.dismiss();
                                            }
                                        }).show();
                            }
                        }
                        else if (identifer == 17){
                            if (Integer.parseInt(Miscellaneous.GetEdad(sdf.format(myCalendar.getTime()))) > 17 && Integer.parseInt(Miscellaneous.GetEdad(sdf.format(myCalendar.getTime()))) < 76) {
                                RenovacionCreditoInd registerActivity = (RenovacionCreditoInd) getActivity();
                                registerActivity.setDate(sdf.format(myCalendar.getTime()), "fechaNacRef");
                                dismiss();
                            }
                            else{
                                new AlertDialog.Builder(getContext())
                                        .setMessage("Solo personas mayores de 18 años.")
                                        .setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialogInterface, int i) {
                                                dialogInterface.dismiss();
                                            }
                                        }).show();
                            }
                        }
                        else if (identifer == 18){
                            if (Integer.parseInt(Miscellaneous.GetEdad(sdf.format(myCalendar.getTime()))) > 17 && Integer.parseInt(Miscellaneous.GetEdad(sdf.format(myCalendar.getTime()))) < 76) {
                                RenovarIntegrante integranteActivity = (RenovarIntegrante) getActivity();
                                integranteActivity.setDate(sdf.format(myCalendar.getTime()), "fechaNacCli");
                                dismiss();
                            }
                            else{
                                new AlertDialog.Builder(getContext())
                                        .setMessage("Solo personas mayores de 18 años.")
                                        .setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialogInterface, int i) {
                                                dialogInterface.dismiss();
                                            }
                                        }).show();
                            }
                        }
                        else if (identifer == 19){

                            if (Integer.parseInt(Miscellaneous.GetEdad(sdf.format(myCalendar.getTime()))) > 17 && Integer.parseInt(Miscellaneous.GetEdad(sdf.format(myCalendar.getTime()))) < 90) {
                                FormularioCC consultarCCActivity = (FormularioCC) getActivity();
                                consultarCCActivity.setDate(sdf.format(myCalendar.getTime()));
                                dismiss();
                            }
                            else{
                                new AlertDialog.Builder(getContext())
                                        .setMessage("Solo personas mayores de 18 años.")
                                        .setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialogInterface, int i) {
                                                dialogInterface.dismiss();
                                            }
                                        }).show();
                            }
                        }
                        else {
                            Intent i = new Intent();
                            i.putExtra(DATE,sdf.format(myCalendar.getTime()));
                            getTargetFragment().onActivityResult(123,321,i);
                            dismiss();
                        }
                    } else {
                        AlertDialog success = new AlertDialog.Builder(getContext())
                                .setMessage("No se pueden seleccionar fechas posteriores")
                                .setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {
                                        dialogInterface.dismiss();
                                    }
                                }).show();
                    }

                }
            } catch (ParseException e) {
                e.printStackTrace();
            }

        }
    };

    private void getDaysOfMonth (int year, int month){
        GregorianCalendar gCal = new GregorianCalendar(year, month, 1);
        int daysInMonth = gCal.getActualMaximum(Calendar.DAY_OF_MONTH);

        valuesDays = new String[daysInMonth];
        for (int i = 0; i < daysInMonth; i++){
            valuesDays[i] = gCal.get(Calendar.DAY_OF_MONTH)+"";
            gCal.add(Calendar.DAY_OF_YEAR,1);
        }

        npDay.setDisplayedValues(null);
        npDay.setMaxValue(valuesDays.length-1);
        npDay.setMinValue(0);
        npDay.setDisplayedValues(valuesDays);
        if (ban){
            npDay.setValue(day_current-1);
            ban = false;
        } else {
            npDay.setValue(0);
        }

    }
}
