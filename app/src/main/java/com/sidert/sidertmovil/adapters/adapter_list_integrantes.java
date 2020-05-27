package com.sidert.sidertmovil.adapters;

import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TextView;

import com.sidert.sidertmovil.R;
import com.sidert.sidertmovil.models.ModeloGrupal;

import java.util.ArrayList;
import java.util.List;

import static com.sidert.sidertmovil.adapters.adapter_integrantes_gpo.redondearDecimales;

public class adapter_list_integrantes extends ArrayAdapter<ModeloGrupal.IntegrantesDelGrupo> implements View.OnClickListener {

    private List<ModeloGrupal.IntegrantesDelGrupo> dataSet;
    Context mContext;

    // View lookup cache
    private static class ViewHolder {
        private TextView tvNombre;
        private TextView tvPagoSemanal;
        private EditText etPagoRealizado;
        private EditText etPagoSolidario;
        private EditText etPagoAdelanto;
        private CheckBox cbPagoCompleto;
    }

    public adapter_list_integrantes(ArrayList<ModeloGrupal.IntegrantesDelGrupo> data, Context context) {
        super(context, R.layout.item_cliente_pago_demo, data);
        this.dataSet = data;
        this.mContext=context;
    }

    @Override
    public void onClick(View v) {

        int position=(Integer) v.getTag();
        Object object= getItem(position);
        ModeloGrupal.IntegrantesDelGrupo dataModel=(ModeloGrupal.IntegrantesDelGrupo)object;

    }

    private int lastPosition = -1;

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        // Get the data item for this position
        final ModeloGrupal.IntegrantesDelGrupo dataModel = getItem(position);
        // Check if an existing view is being reused, otherwise inflate the view
        final ViewHolder viewHolder; // view lookup cache stored in tag

        final View result;

        if (convertView == null) {

            viewHolder = new ViewHolder();
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.item_cliente_pago_demo, parent, false);
            viewHolder.tvNombre        = convertView.findViewById(R.id.tvNombre);
            viewHolder.tvPagoSemanal   = convertView.findViewById(R.id.tvPagoSemanal);
            viewHolder.etPagoRealizado = convertView.findViewById(R.id.etPagoRealizado);
            viewHolder.etPagoSolidario = convertView.findViewById(R.id.etPagoSolidario);
            viewHolder.etPagoAdelanto  = convertView.findViewById(R.id.etPagoAdelanto);
            viewHolder.cbPagoCompleto  = convertView.findViewById(R.id.cbPagoCompleto);

            result=convertView;

            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
            result=convertView;
        }

        lastPosition = position;


        viewHolder.tvNombre.setText(dataModel.getNombre());
        viewHolder.tvPagoSemanal.setText(dataModel.getPagoSemanalInt().toString());

        viewHolder.etPagoRealizado.setText(String.valueOf(dataModel.getPagoRealizado()));
        viewHolder.etPagoSolidario.setText(String.valueOf((dataModel.getPagoSolidario() != null)?redondearDecimales(dataModel.getPagoSolidario()):redondearDecimales((double)0)));
        viewHolder.etPagoAdelanto.setText(String.valueOf((dataModel.getPagoAdelanto() != null)?redondearDecimales(dataModel.getPagoAdelanto()):redondearDecimales((double)0)));


        /*viewHolder.cbPagoCompleto.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                dataSet.get(position).setPagoCompleto(isChecked);
                if (isChecked){
                    viewHolder.etPagoRealizado.requestFocus();
                    viewHolder.etPagoRealizado.setText(String.valueOf(redondearDecimales(dataModel.getPagoSemanalInt())));
                    viewHolder.etPagoSolidario.setText(String.valueOf(redondearDecimales((double)0)));
                    viewHolder.etPagoAdelanto.setText(String.valueOf(redondearDecimales((double)0)));
                }else {
                    viewHolder.etPagoRealizado.requestFocus();
                    viewHolder.etPagoRealizado.setText(String.valueOf(redondearDecimales((double)0)));
                    viewHolder.etPagoSolidario.setText(String.valueOf(redondearDecimales((double)0)));
                    viewHolder.etPagoAdelanto.setText(String.valueOf(redondearDecimales((double)0)));
                }
            }
        });*/

        viewHolder.cbPagoCompleto.setChecked(dataSet.get(position).isPagoCompleto());

        viewHolder.etPagoRealizado.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.length() > 0){
                    dataSet.get(position).setPagoRealizado(Double.parseDouble(s.toString()));
                }
                else {
                    dataSet.get(position).setPagoRealizado((double) 0);
                }

                if(viewHolder.etPagoRealizado.hasFocus()) {
                    if (s.length() > 0) {
                        if (Double.parseDouble(s.toString()) > dataModel.getPagoSemanalInt()) {
                            viewHolder.etPagoSolidario.setText(String.valueOf(redondearDecimales(Double.parseDouble(s.toString()) - dataModel.getPagoSemanalInt())));
                        } else {
                            viewHolder.etPagoSolidario.setText(String.valueOf(redondearDecimales((double) 0)));
                        }

                    } else {
                        viewHolder.etPagoRealizado.setText(String.valueOf(redondearDecimales((double) 0)));
                        viewHolder.etPagoSolidario.setText(String.valueOf(redondearDecimales((double) 0)));
                    }
                }
            }
        });

        viewHolder.etPagoSolidario.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {

                if (s.length() > 0){
                    dataSet.get(position).setPagoSolidario(Double.parseDouble(s.toString()));
                }
                else{
                    dataSet.get(position).setPagoSolidario((double) 0);
                }

                if(viewHolder.etPagoSolidario.hasFocus()) {
                    if (s.length() > 0) {
                        if (Double.parseDouble(s.toString()) + dataModel.getPagoSemanalInt() > Double.parseDouble(viewHolder.etPagoRealizado.getText().toString())) {
                            viewHolder.etPagoSolidario.setText(String.valueOf(redondearDecimales(Double.parseDouble(viewHolder.etPagoRealizado.getText().toString().trim()) - dataModel.getPagoSemanalInt())));
                            viewHolder.etPagoAdelanto.setText(String.valueOf(redondearDecimales((double) 0)));
                        } else if (Double.parseDouble(s.toString()) + dataModel.getPagoSemanalInt() < Double.parseDouble(viewHolder.etPagoRealizado.getText().toString())) {
                            viewHolder.etPagoAdelanto.setText(String.valueOf(redondearDecimales(Double.parseDouble(viewHolder.etPagoRealizado.getText().toString()) - dataModel.getPagoSemanalInt() - Double.parseDouble(s.toString()))));
                        }
                    } else {
                        if (Double.parseDouble(viewHolder.etPagoRealizado.getText().toString()) > dataModel.getPagoSemanalInt()){
                            viewHolder.etPagoSolidario.setText(String.valueOf(redondearDecimales((double) 0)));
                            viewHolder.etPagoAdelanto.setText(String.valueOf(redondearDecimales(Double.parseDouble(viewHolder.etPagoRealizado.getText().toString()) - dataModel.getPagoSemanalInt())));
                        }else {
                            viewHolder.etPagoSolidario.setText(String.valueOf(redondearDecimales((double) 0)));
                            viewHolder.etPagoAdelanto.setText(String.valueOf(redondearDecimales((double) 0)));
                        }
                    }
                }

            }
        });

        viewHolder.etPagoAdelanto.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {

                if (s.length() > 0) {
                    dataSet.get(position).setPagoAdelanto(Double.parseDouble(s.toString()));

                } else {
                    dataSet.get(position).setPagoAdelanto((double) 0);
                }

            }
        });

        // Return the completed view to render on screen
        return convertView;
    }

}
