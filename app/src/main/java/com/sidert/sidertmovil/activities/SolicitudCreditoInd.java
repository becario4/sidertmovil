package com.sidert.sidertmovil.activities;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.MultiAutoCompleteTextView;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TimePicker;
import android.widget.Toast;

import com.sidert.sidertmovil.R;
import com.sidert.sidertmovil.utils.Constants;
import com.sidert.sidertmovil.utils.Miscellaneous;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import moe.feng.common.stepperview.VerticalStepperItemView;

public class SolicitudCreditoInd extends AppCompatActivity {

    private Context ctx;
    private Context context;

    private VerticalStepperItemView mSteppers[] = new VerticalStepperItemView[9];

    private FloatingActionButton btnContinuar0;
    private FloatingActionButton btnContinuar1;
    private FloatingActionButton btnContinuar2;
    private FloatingActionButton btnContinuar3;
    private FloatingActionButton btnContinuar4;
    private FloatingActionButton btnContinuar5;
    private FloatingActionButton btnContinuar6;
    private FloatingActionButton btnContinuar7;
    private FloatingActionButton btnContinuar8;

    private FloatingActionButton btnRegresar1;
    private FloatingActionButton btnRegresar2;
    private FloatingActionButton btnRegresar3;
    private FloatingActionButton btnRegresar4;
    private FloatingActionButton btnRegresar5;
    private FloatingActionButton btnRegresar6;
    private FloatingActionButton btnRegresar7;
    private FloatingActionButton btnRegresar8;

    private EditText etMontoPrestamo;
    private MultiAutoCompleteTextView etCantidadLetra;

    private EditText etNombre;
    private EditText etApPaterno;
    private EditText etApMaterno;
    private EditText etFechaNac;
    private EditText etEdad;
    private RadioGroup rgSexo;
    private Spinner spEstadoNac;
    private EditText etCurp;
    private EditText etRfc;

    private SimpleDateFormat sdf = new SimpleDateFormat(Constants.FORMAT_DATE_GNRAL);
    private Calendar myCalendar;
    private Date minDate;

    private EditText etHoraVisita;
    private EditText etHorarioLoc;
    private TimePickerDialog picker;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_solicitud_credito_ind);
        ctx = getApplicationContext();
        context = this;

        initComponents();

        mSteppers[0] = findViewById(R.id.stepper_0);
        mSteppers[1] = findViewById(R.id.stepper_1);
        mSteppers[2] = findViewById(R.id.stepper_2);
        mSteppers[3] = findViewById(R.id.stepper_3);
        mSteppers[4] = findViewById(R.id.stepper_4);
        mSteppers[5] = findViewById(R.id.stepper_5);
        mSteppers[6] = findViewById(R.id.stepper_6);
        mSteppers[7] = findViewById(R.id.stepper_7);
        mSteppers[8] = findViewById(R.id.stepper_8);

        btnContinuar0 = findViewById(R.id.btnContinuar0);
        btnContinuar1 = findViewById(R.id.btnContinuar1);
        btnContinuar2 = findViewById(R.id.btnContinuar2);
        btnContinuar3 = findViewById(R.id.btnContinuar3);
        btnContinuar4 = findViewById(R.id.btnContinuar4);
        btnContinuar5 = findViewById(R.id.btnContinuar5);
        btnContinuar6 = findViewById(R.id.btnContinuar6);
        btnContinuar7 = findViewById(R.id.btnContinuar7);
        btnContinuar8 = findViewById(R.id.btnContinuar8);

        btnRegresar1 = findViewById(R.id.btnRegresar1);
        btnRegresar2 = findViewById(R.id.btnRegresar2);
        btnRegresar3 = findViewById(R.id.btnRegresar3);
        btnRegresar4 = findViewById(R.id.btnRegresar4);
        btnRegresar5 = findViewById(R.id.btnRegresar5);
        btnRegresar6 = findViewById(R.id.btnRegresar6);
        btnRegresar7 = findViewById(R.id.btnRegresar7);
        btnRegresar8 = findViewById(R.id.btnRegresar8);

        VerticalStepperItemView.bindSteppers(mSteppers);

        etMontoPrestamo = findViewById(R.id.etMontoPrestamo);
        etCantidadLetra = findViewById(R.id.etCantidadLetra);
        etHoraVisita    = findViewById(R.id.etHoraVisita);

        etNombre    = findViewById(R.id.etNombre);
        etApPaterno = findViewById(R.id.etApPaterno);
        etApMaterno = findViewById(R.id.etApMaterno);
        etFechaNac  = findViewById(R.id.etFechaNac);
        etEdad      = findViewById(R.id.etEdad);
        rgSexo      = findViewById(R.id.rgSexo);
        spEstadoNac = findViewById(R.id.spEstadoNac);
        etCurp      = findViewById(R.id.etCurp);
        etRfc       = findViewById(R.id.etRfc);

        btnContinuar0.setOnClickListener(btnContinuar0_OnClick);
        btnContinuar1.setOnClickListener(btnContinuar1_OnClick);
        btnContinuar2.setOnClickListener(btnContinuar2_OnClick);
        btnContinuar3.setOnClickListener(btnContinuar3_OnClick);
        btnContinuar4.setOnClickListener(btnContinuar4_OnClick);
        btnContinuar5.setOnClickListener(btnContinuar5_OnClick);
        btnContinuar6.setOnClickListener(btnContinuar6_OnClick);
        btnContinuar7.setOnClickListener(btnContinuar7_OnClick);
        btnContinuar8.setOnClickListener(btnContinuar8_OnClick);

        btnRegresar1.setOnClickListener(btnRegresar1_OnClick);
        btnRegresar2.setOnClickListener(btnRegresar2_OnClick);
        btnRegresar3.setOnClickListener(btnRegresar3_OnClick);
        btnRegresar4.setOnClickListener(btnRegresar4_OnClick);
        btnRegresar5.setOnClickListener(btnRegresar5_OnClick);
        btnRegresar6.setOnClickListener(btnRegresar6_OnClick);
        btnRegresar7.setOnClickListener(btnRegresar7_OnClick);
        btnRegresar8.setOnClickListener(btnRegresar8_OnClick);


        etHorarioLoc = findViewById(R.id.etHorarioLoc);

        etHoraVisita.setOnClickListener(etHoraVisita_OnClick);
        etHorarioLoc.setOnClickListener(etHorarioLoc_OnClick);

        etFechaNac.setOnClickListener(etFechaNac_OnClick);

        etMontoPrestamo.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.length()> 0){
                    etCantidadLetra.setText(Miscellaneous.cantidadLetra(s.toString()).toUpperCase() + " PESOS MEXICANOS ");
                }
                else{
                    etCantidadLetra.setText("");
                }
            }
        });
    }

    private View.OnClickListener etFechaNac_OnClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            DatePickerDialog dpd = new DatePickerDialog(context, new DatePickerDialog.OnDateSetListener() {
                @Override
                public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {

                    myCalendar.set(Calendar.YEAR,year);
                    myCalendar.set(Calendar.MONTH,month);
                    myCalendar.set(Calendar.DAY_OF_MONTH,dayOfMonth);
                    setDatePicked(etFechaNac);

                }
            },myCalendar.get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),myCalendar.get(Calendar.DAY_OF_MONTH));
            dpd.getDatePicker().setMaxDate(new Date().getTime());
            dpd.getDatePicker().setMinDate(minDate.getTime());
            dpd.show();
        }
    };

    //Continuar
    private View.OnClickListener btnContinuar0_OnClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            mSteppers[0].nextStep();
        }
    };
    private View.OnClickListener btnContinuar1_OnClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            mSteppers[1].nextStep();
            String apPaterno = etApPaterno.getText().toString().trim().toUpperCase();
            String apMaterno = etApMaterno.getText().toString().trim().toUpperCase();
            String nombre = etNombre.getText().toString().trim().toUpperCase();
            String fecha = etFechaNac.getText().toString().trim().toUpperCase();
            String[] sFechaNac = fecha.split("-");

            //etRfc.setText(apPaterno.substring(0,2)+((apMaterno.length() > 0)?apMaterno.substring(0,1):"X")+nombre.substring(0,1)+sFechaNac[0].substring(2,4)+sFechaNac[1]+sFechaNac[2]);
            //etCurp.setText(etRfc.getText()+((rgSexo.getCheckedRadioButtonId()==R.id.rbHombre)?"H":"M")+Miscellaneous.clvEstado(spEstadoNac.getSelectedItemPosition())+Miscellaneous.segundaConsonante(etApPaterno.getText().toString().trim().toUpperCase())+Miscellaneous.segundaConsonante(etApMaterno.getText().toString().trim().toUpperCase())+Miscellaneous.segundaConsonante(etNombre.getText().toString().trim().toUpperCase()));
        }
    };
    private View.OnClickListener btnContinuar2_OnClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            mSteppers[2].nextStep();
        }
    };
    private View.OnClickListener btnContinuar3_OnClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            mSteppers[3].nextStep();
        }
    };
    private View.OnClickListener btnContinuar4_OnClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            mSteppers[4].nextStep();
        }
    };
    private View.OnClickListener btnContinuar5_OnClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            mSteppers[5].nextStep();
        }
    };
    private View.OnClickListener btnContinuar6_OnClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            mSteppers[6].nextStep();
        }
    };
    private View.OnClickListener btnContinuar7_OnClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            mSteppers[7].nextStep();
        }
    };
    private View.OnClickListener btnContinuar8_OnClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Toast.makeText(ctx, "Termina proceso", Toast.LENGTH_SHORT).show();
        }
    };

    //Regresar
    private View.OnClickListener btnRegresar1_OnClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            mSteppers[1].prevStep();
        }
    };
    private View.OnClickListener btnRegresar2_OnClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            mSteppers[2].prevStep();
        }
    };
    private View.OnClickListener btnRegresar3_OnClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            mSteppers[3].prevStep();
        }
    };
    private View.OnClickListener btnRegresar4_OnClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            mSteppers[4].prevStep();
        }
    };
    private View.OnClickListener btnRegresar5_OnClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            mSteppers[5].prevStep();
        }
    };
    private View.OnClickListener btnRegresar6_OnClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            mSteppers[6].prevStep();
        }
    };
    private View.OnClickListener btnRegresar7_OnClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            mSteppers[7].prevStep();
        }
    };
    private View.OnClickListener btnRegresar8_OnClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            mSteppers[8].prevStep();
        }
    };

    private View.OnClickListener etHoraVisita_OnClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            final Calendar cldr = Calendar.getInstance();
            int hour = cldr.get(Calendar.HOUR_OF_DAY);
            int minutes = cldr.get(Calendar.MINUTE);
            // time picker dialog
            picker = new TimePickerDialog(context,
                    new TimePickerDialog.OnTimeSetListener() {
                        @Override
                        public void onTimeSet(TimePicker tp, int sHour, int sMinute) {
                            etHoraVisita.setText(sHour + ":" + sMinute);
                        }
                    }, hour, minutes, true);
            picker.show();
        }
    };

    private View.OnClickListener etHorarioLoc_OnClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            final Calendar cldr = Calendar.getInstance();
            int hour = cldr.get(Calendar.HOUR_OF_DAY);
            int minutes = cldr.get(Calendar.MINUTE);
            // time picker dialog
            picker = new TimePickerDialog(context,
                    new TimePickerDialog.OnTimeSetListener() {
                        @Override
                        public void onTimeSet(TimePicker tp, int sHour, int sMinute) {
                            etHorarioLoc.setText(sHour + ":" + sMinute);
                        }
                    }, hour, minutes, true);
            picker.show();
        }
    };

    private void setDatePicked(EditText et){
        sdf.setTimeZone(myCalendar.getTimeZone());
        et.setError(null);
        et.setText(sdf.format(myCalendar.getTime()));
        etEdad.setText(Miscellaneous.GetEdad(sdf.format(myCalendar.getTime())));
    }

    private void initComponents(){
        myCalendar = Calendar.getInstance();
        try {
            minDate = sdf.parse("1955-01-01");
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }
}
