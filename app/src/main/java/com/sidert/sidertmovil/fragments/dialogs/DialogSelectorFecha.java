package com.sidert.sidertmovil.fragments.dialogs;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;
import androidx.lifecycle.viewmodel.CreationExtras;

import com.sidert.sidertmovil.MainActivity;
import com.sidert.sidertmovil.activities.FormularioCC;
import com.sidert.sidertmovil.utils.Constants;
import com.sidert.sidertmovil.utils.DatosCompartidos;
import com.sidert.sidertmovil.utils.Miscellaneous;
import com.sidert.sidertmovil.views.originacion.AgregarIntegrante;
import com.sidert.sidertmovil.views.originacion.SolicitudCreditoInd;
import com.sidert.sidertmovil.views.renovacion.RenovacionCreditoInd;
import com.sidert.sidertmovil.views.renovacion.RenovarIntegrante;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class DialogSelectorFecha extends DialogFragment{

    private Activity actividad;
    private TextView textView;
    private  Calendar calendario = Calendar.getInstance();
    private Date fecha;
    private Context ctx;
    private SimpleDateFormat sdf = new SimpleDateFormat(Constants.FORMAT_DATE_GNRAL);
    private int aux = 0;
    private String tipoCampo = "";
    private int tipoActividad = 0;


    public DialogSelectorFecha(Activity actividad, TextView textView, int variable, int tipoActividad){
        this.actividad = actividad;
        this.textView = textView;
        this.aux = variable;
        this.tipoActividad = tipoActividad;
        //this.identificador = identificador1;
    }

    public void recuperarFecha(String fechaCliente) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        try {
            fecha = format.parse(fechaCliente);

            Calendar calendar = Calendar.getInstance();
            calendar.setTime(fecha);
            int year = calendar.get(Calendar.YEAR);
            int month = calendar.get(Calendar.MONTH);
            int day = calendar.get(Calendar.DAY_OF_MONTH);
            if(aux == 1){
                tipoCampo = "fechaNacCli";
            }else if(aux == 2){
                tipoCampo = "fechaNacAval";
            }else if(aux == 3){
                tipoCampo = "fechaNacRef";
            }else if(aux == 4){
                tipoCampo = "fechaNacCli";
            }

            DatePickerDialog datePickerDialog = new DatePickerDialog(actividad, new DatePickerDialog.OnDateSetListener() {
                @Override
                public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                    //actualizarFecha(year + "-" + (month + 1) + "-" + day,tipoCampo);

                    String dayFormatted = String.format("%02d", day);

                    // Eliminar cero al principio del mes si está presente
                    String monthFormatted = String.format("%02d", month + 1);

                    String selectDate = year + "-" + monthFormatted + "-" + dayFormatted;
                    //String selectDate = year + "-" + month + "-" + day;

                    actualizarFecha(selectDate,tipoCampo,tipoActividad);
                }
            }, year, month, day);

            datePickerDialog.show();
        } catch (Exception e) {
            e.printStackTrace();
            return;
        }
    }


    public void showDatePickerDialog(){

        if(!((Activity)actividad).isFinishing()){
            int year = calendario.get(Calendar.YEAR);
            int month = calendario.get(Calendar.MONTH);
            int day = calendario.get(Calendar.DAY_OF_MONTH);

            DatePickerDialog datePickerDialog = new DatePickerDialog(actividad, new DatePickerDialog.OnDateSetListener() {
                @Override
                public void onDateSet(DatePicker datePicker, int year, int month, int day) {

                    String dayFormmated = (day<10) ? "0" + day :String.valueOf(day);

                    String monthFormatted = ((month+1)<10) ? "0" + (month + 1) : String.valueOf(month);

                    //String selectDate = year + "-" + (month + 1) + "-" + day;
                    String selectDate =  year + "-" + monthFormatted + "-" +  dayFormmated;

                    if(aux == 1){
                        tipoCampo = "fechaNacCli";
                        if (Integer.parseInt(Miscellaneous.GetEdad2(selectDate)) > 17 && Integer.parseInt(Miscellaneous.GetEdad2(selectDate)) < 75) {
                            SolicitudCreditoInd registerActividad = (SolicitudCreditoInd) actividad;
                            if(!textView.toString().isEmpty()){
                                actualizarFecha(selectDate,tipoCampo,tipoActividad);
                            }else{
                                registerActividad.mostrarEdad(selectDate, tipoCampo);
                            }
                        }
                        else{
                            new AlertDialog.Builder(actividad)
                                    .setMessage("Solo personas mayores de 18 y menores de 75 años")
                                    .setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialogInterface, int i) {
                                            dialogInterface.dismiss();
                                        }
                                    }).show();

                        }
                    }else if(aux == 2){
                        tipoCampo = "fechaNacAval";
                        if(Integer.parseInt(Miscellaneous.GetEdad2(selectDate))>17 && Integer.parseInt(Miscellaneous.GetEdad2(selectDate))<75){
                            SolicitudCreditoInd registerActividad = (SolicitudCreditoInd) actividad;
                            actualizarFecha(selectDate,tipoCampo,tipoActividad);
                            registerActividad.mostrarEdad(selectDate,tipoCampo);

                        }else{
                            new AlertDialog.Builder(actividad)
                                    .setMessage("Solo personas mayores de 18 y menores de 75 años")
                                    .setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialogInterface, int i) {
                                            dialogInterface.dismiss();
                                        }
                                    }).show();
                        }

                    }else if(aux == 3){
                        tipoCampo = "fechaNacRef";
                        if(Integer.parseInt(Miscellaneous.GetEdad2(selectDate)) > 17 && Integer.parseInt(Miscellaneous.GetEdad2(selectDate))<75){
                            SolicitudCreditoInd registerActividad = (SolicitudCreditoInd) actividad;
                            registerActividad.mostrarEdad(selectDate,tipoCampo);
                            actualizarFecha(selectDate,tipoCampo,tipoActividad);
                        }else{
                            new AlertDialog.Builder(actividad)
                                    .setMessage("Solo personas mayores de 18 y menores de 75 años")
                                    .setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialogInterface, int i) {
                                            dialogInterface.dismiss();
                                        }
                                    }).show();
                        }
                    }else if(aux == 4){
                        tipoCampo = "fechaNacCli";
                        if(Integer.parseInt(Miscellaneous.GetEdad2(selectDate))>17 && Integer.parseInt(Miscellaneous.GetEdad2(selectDate))<75){
                            //SolicitudCreditoInd registerActividad = (SolicitudCreditoInd) actividad;
                            AgregarIntegrante agregarIntegrante = (AgregarIntegrante) actividad;
                            agregarIntegrante.mostrarEdadGpo(selectDate,tipoCampo);
                            actualizarFecha(selectDate,tipoCampo,tipoActividad);
                        }else{
                            new AlertDialog.Builder(actividad)
                                    .setMessage("Solo personas mayores de 18 y menores de 75")
                                    .setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialogInterface, int i) {
                                            dialogInterface.dismiss();
                                        }
                                    }).show();
                        }
                    }else if(aux == 5){

                        tipoCampo = "fechaNacCli";
                        if(Integer.parseInt(Miscellaneous.GetEdad2(selectDate))>17 && Integer.parseInt(Miscellaneous.GetEdad2(selectDate))<75){
                            RenovacionCreditoInd renovacionCreditoInd = (RenovacionCreditoInd) actividad;
                            renovacionCreditoInd.mostrarEdad(selectDate,tipoCampo);
                            actualizarFecha(selectDate,tipoCampo,tipoActividad);

                        }else{
                            new AlertDialog.Builder(actividad)
                                    .setMessage("Solo personas mayores de 18 y menores de 75")
                                    .setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialogInterface, int i) {
                                            dialogInterface.dismiss();
                                        }
                                    }).show();
                        }
                    }else if(aux == 6){
                        tipoCampo = "fechaNacAval";
                        if(Integer.parseInt(Miscellaneous.GetEdad2(selectDate))>17 && Integer.parseInt(Miscellaneous.GetEdad2(selectDate))<75){
                            RenovacionCreditoInd renovacionCreditoInd = (RenovacionCreditoInd) actividad;
                            renovacionCreditoInd.mostrarEdad(selectDate,tipoCampo);
                            actualizarFecha(selectDate,tipoCampo,tipoActividad);

                        }else{
                            new AlertDialog.Builder(actividad)
                                    .setMessage("Solo personas mayores de 18 y menores de 75 años")
                                    .setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialogInterface, int i) {
                                            dialogInterface.dismiss();
                                        }
                                    }).show();
                        }
                    }else if(aux == 7){
                        tipoCampo = "fechaNacRef";
                        if(Integer.parseInt(Miscellaneous.GetEdad2(selectDate)) > 17 && Integer.parseInt(Miscellaneous.GetEdad2(selectDate))<75){
                            RenovacionCreditoInd renovacionCreditoInd = (RenovacionCreditoInd) actividad;
                            renovacionCreditoInd.mostrarEdad(selectDate,tipoCampo);
                            actualizarFecha(selectDate,tipoCampo,tipoActividad);
                        }else{
                            new AlertDialog.Builder(actividad)
                                    .setMessage("Solo personas mayores de 18 y menores de 75 años")
                                    .setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialogInterface, int i) {
                                            dialogInterface.dismiss();
                                        }
                                    }).show();
                        }
                    }else if(aux == 8){
                        tipoCampo = "fechaNacCli";
                        if(Integer.parseInt(Miscellaneous.GetEdad2(selectDate))>17 && Integer.parseInt(Miscellaneous.GetEdad2(selectDate))<75){
                            RenovarIntegrante renovarIntegrante = (RenovarIntegrante) actividad;
                            renovarIntegrante.mostrarEdadGpo(selectDate,tipoCampo);
                            actualizarFecha(selectDate,tipoCampo,tipoActividad);
                        }else{
                            new AlertDialog.Builder(actividad)
                                    .setMessage("Solo personas mayores de 18 y menores de 75")
                                    .setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialogInterface, int i) {
                                            dialogInterface.dismiss();
                                        }
                                    }).show();
                        }
                    }

                }
            }, year, month, day);
            datePickerDialog.show();
        }
    }

    public void actualizarFecha(String fechaSelect,String campo, Integer dato){

        if(dato == 1){
            SolicitudCreditoInd registerActividad = (SolicitudCreditoInd) actividad;
            registerActividad.mostrarEdad(fechaSelect,campo);
        }else if(dato == 2){
            AgregarIntegrante agregarIntegrante = (AgregarIntegrante) actividad;
            agregarIntegrante.mostrarEdadGpo(fechaSelect,campo);
        }else if(dato == 3){
            RenovacionCreditoInd renovacionCreditoInd = (RenovacionCreditoInd) actividad;
            renovacionCreditoInd.mostrarEdad(fechaSelect,campo);
        }else if(dato == 4){
            RenovarIntegrante renovarIntegrante = (RenovarIntegrante) actividad;
            renovarIntegrante.mostrarEdadGpo(fechaSelect,campo);
        }
        textView.setText(fechaSelect);
    }
}
