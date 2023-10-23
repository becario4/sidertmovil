package com.sidert.sidertmovil.fragments.dialogs;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.widget.DatePicker;
import android.widget.TextView;

import com.sidert.sidertmovil.utils.Miscellaneous;
import com.sidert.sidertmovil.views.originacion.AgregarIntegrante;
import com.sidert.sidertmovil.views.originacion.SolicitudCreditoInd;
import com.sidert.sidertmovil.views.renovacion.RenovacionCreditoInd;
import com.sidert.sidertmovil.views.renovacion.RenovarIntegrante;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;

public class DialogSelectorFecha extends DialogFragment {

    private Activity actividad;
    private TextView textView;
    private final Calendar calendario;
    private final int aux;
    private final int tipoActividad;
    private String tipoCampo = "";

    public DialogSelectorFecha(Activity actividad, TextView textView, int aux, int tipoActividad) {
        this.actividad = actividad;
        this.textView = textView;
        this.aux = aux;
        this.tipoActividad = tipoActividad;
        this.calendario = Calendar.getInstance();
        //this.identificador = identificador1;
    }

    public void recuperarFecha(String fechaCliente) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd", Locale.ROOT);
        try {
            Date fecha = format.parse(fechaCliente);
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(fecha);
            int year = calendar.get(Calendar.YEAR);
            int month = calendar.get(Calendar.MONTH);
            int day = calendar.get(Calendar.DAY_OF_MONTH);
            if (aux == 1) {
                tipoCampo = "fechaNacCli";
            } else if (aux == 2) {
                tipoCampo = "fechaNacAval";
            } else if (aux == 3) {
                tipoCampo = "fechaNacRef";
            } else if (aux == 4) {
                tipoCampo = "fechaNacCli";
            }

            DatePickerDialog datePickerDialog = new DatePickerDialog(actividad, (datePicker, year1, month1, day1) -> {
                String monthFormatted = String.format(Locale.ROOT, "%02d", month1 + 1);
                String dayFormatted = String.format(Locale.ROOT, "%02d", day1);
                String selectDate = year1 + "-" + monthFormatted + "-" + dayFormatted;
                actualizarFecha(selectDate, tipoCampo, tipoActividad);
            }, year, month, day);

            datePickerDialog.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public void showDatePickerDialog() {

        if (!actividad.isFinishing()) {
            int year = calendario.get(Calendar.YEAR);
            int month = calendario.get(Calendar.MONTH);
            int day = calendario.get(Calendar.DAY_OF_MONTH);

            DatePickerDialog datePickerDialog = new DatePickerDialog(actividad, (datePicker, year1, month1, day1) -> {

                String monthFormatted = ((month1 + 1) < 10) ? "0" + (month1 + 1) : String.valueOf(month1);
                String dayFormmated = (day1 < 10) ? "0" + day1 : String.valueOf(day1);

                //String selectDate = year + "-" + (month + 1) + "-" + day;
                String selectDate = year1 + "-" + monthFormatted + "-" + dayFormmated;

                if (aux == 1) {
                    tipoCampo = "fechaNacCli";
                    if (Integer.parseInt(Miscellaneous.GetEdad2(selectDate)) > 17 && Integer.parseInt(Miscellaneous.GetEdad2(selectDate)) < 75) {
                        SolicitudCreditoInd registerActividad = (SolicitudCreditoInd) actividad;
                        if (!textView.toString().isEmpty()) {
                            actualizarFecha(selectDate, tipoCampo, tipoActividad);
                        } else {
                            registerActividad.mostrarEdad(selectDate, tipoCampo);
                        }
                    } else {
                        new AlertDialog.Builder(actividad)
                                .setMessage("Solo personas mayores de 18 y menores de 75 años")
                                .setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {
                                        dialogInterface.dismiss();
                                    }
                                }).show();

                    }
                } else if (aux == 2) {
                    tipoCampo = "fechaNacAval";
                    if (Integer.parseInt(Miscellaneous.GetEdad2(selectDate)) > 17 && Integer.parseInt(Miscellaneous.GetEdad2(selectDate)) < 75) {
                        SolicitudCreditoInd registerActividad = (SolicitudCreditoInd) actividad;
                        actualizarFecha(selectDate, tipoCampo, tipoActividad);
                        registerActividad.mostrarEdad(selectDate, tipoCampo);

                    } else {
                        new AlertDialog.Builder(actividad)
                                .setMessage("Solo personas mayores de 18 y menores de 75 años")
                                .setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {
                                        dialogInterface.dismiss();
                                    }
                                }).show();
                    }

                } else if (aux == 3) {
                    tipoCampo = "fechaNacRef";
                    if (Integer.parseInt(Miscellaneous.GetEdad2(selectDate)) > 17 && Integer.parseInt(Miscellaneous.GetEdad2(selectDate)) < 75) {
                        SolicitudCreditoInd registerActividad = (SolicitudCreditoInd) actividad;
                        registerActividad.mostrarEdad(selectDate, tipoCampo);
                        actualizarFecha(selectDate, tipoCampo, tipoActividad);
                    } else {
                        new AlertDialog.Builder(actividad)
                                .setMessage("Solo personas mayores de 18 y menores de 75 años")
                                .setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {
                                        dialogInterface.dismiss();
                                    }
                                }).show();
                    }
                } else if (aux == 4) {
                    tipoCampo = "fechaNacCli";
                    if (Integer.parseInt(Miscellaneous.GetEdad2(selectDate)) > 17 && Integer.parseInt(Miscellaneous.GetEdad2(selectDate)) < 75) {
                        //SolicitudCreditoInd registerActividad = (SolicitudCreditoInd) actividad;
                        AgregarIntegrante agregarIntegrante = (AgregarIntegrante) actividad;
                        agregarIntegrante.mostrarEdadGpo(selectDate, tipoCampo);
                        actualizarFecha(selectDate, tipoCampo, tipoActividad);
                    } else {
                        new AlertDialog.Builder(actividad)
                                .setMessage("Solo personas mayores de 18 y menores de 75")
                                .setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {
                                        dialogInterface.dismiss();
                                    }
                                }).show();
                    }
                } else if (aux == 5) {

                    tipoCampo = "fechaNacCli";
                    if (Integer.parseInt(Miscellaneous.GetEdad2(selectDate)) > 17 && Integer.parseInt(Miscellaneous.GetEdad2(selectDate)) < 75) {
                        RenovacionCreditoInd renovacionCreditoInd = (RenovacionCreditoInd) actividad;
                        renovacionCreditoInd.mostrarEdad(selectDate, tipoCampo);
                        actualizarFecha(selectDate, tipoCampo, tipoActividad);

                    } else {
                        new AlertDialog.Builder(actividad)
                                .setMessage("Solo personas mayores de 18 y menores de 75")
                                .setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {
                                        dialogInterface.dismiss();
                                    }
                                }).show();
                    }
                } else if (aux == 6) {
                    tipoCampo = "fechaNacAval";
                    if (Integer.parseInt(Miscellaneous.GetEdad2(selectDate)) > 17 && Integer.parseInt(Miscellaneous.GetEdad2(selectDate)) < 75) {
                        RenovacionCreditoInd renovacionCreditoInd = (RenovacionCreditoInd) actividad;
                        renovacionCreditoInd.mostrarEdad(selectDate, tipoCampo);
                        actualizarFecha(selectDate, tipoCampo, tipoActividad);

                    } else {
                        new AlertDialog.Builder(actividad)
                                .setMessage("Solo personas mayores de 18 y menores de 75 años")
                                .setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {
                                        dialogInterface.dismiss();
                                    }
                                }).show();
                    }
                } else if (aux == 7) {
                    tipoCampo = "fechaNacRef";
                    if (Integer.parseInt(Miscellaneous.GetEdad2(selectDate)) > 17 && Integer.parseInt(Miscellaneous.GetEdad2(selectDate)) < 75) {
                        RenovacionCreditoInd renovacionCreditoInd = (RenovacionCreditoInd) actividad;
                        renovacionCreditoInd.mostrarEdad(selectDate, tipoCampo);
                        actualizarFecha(selectDate, tipoCampo, tipoActividad);
                    } else {
                        new AlertDialog.Builder(actividad)
                                .setMessage("Solo personas mayores de 18 y menores de 75 años")
                                .setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {
                                        dialogInterface.dismiss();
                                    }
                                }).show();
                    }
                } else if (aux == 8) {
                    tipoCampo = "fechaNacCli";
                    if (Integer.parseInt(Miscellaneous.GetEdad2(selectDate)) > 17 && Integer.parseInt(Miscellaneous.GetEdad2(selectDate)) < 75) {
                        RenovarIntegrante renovarIntegrante = (RenovarIntegrante) actividad;
                        renovarIntegrante.mostrarEdadGpo(selectDate, tipoCampo);
                        actualizarFecha(selectDate, tipoCampo, tipoActividad);
                    } else {
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

            }, year, month, day);
            datePickerDialog.show();
        }
    }

    public void actualizarFecha(String fechaSelect, String campo, Integer dato) {

        if (dato == 1) {
            SolicitudCreditoInd registerActividad = (SolicitudCreditoInd) actividad;
            registerActividad.mostrarEdad(fechaSelect, campo);
        } else if (dato == 2) {
            AgregarIntegrante agregarIntegrante = (AgregarIntegrante) actividad;
            agregarIntegrante.mostrarEdadGpo(fechaSelect, campo);
        } else if (dato == 3) {
            RenovacionCreditoInd renovacionCreditoInd = (RenovacionCreditoInd) actividad;
            renovacionCreditoInd.mostrarEdad(fechaSelect, campo);
        } else if (dato == 4) {
            RenovarIntegrante renovarIntegrante = (RenovarIntegrante) actividad;
            renovarIntegrante.mostrarEdadGpo(fechaSelect, campo);
        }
        textView.setText(fechaSelect);
    }
}
