package com.sidert.sidertmovil.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.sidert.sidertmovil.R;

import com.sidert.sidertmovil.models.ModeloGrupal;
import com.sidert.sidertmovil.utils.Miscellaneous;

import java.util.List;

public class adapter_integrantes_gpo extends RecyclerView.Adapter<adapter_integrantes_gpo.ViewHolder> {

    private Context ctx;
    public List<ModeloGrupal.IntegrantesDelGrupo> data;
    private boolean isEditable;

    public adapter_integrantes_gpo(Context ctx, boolean isEditable, List<ModeloGrupal.IntegrantesDelGrupo> data) {
        this.ctx = ctx;
        this.isEditable = isEditable;
        this.data = data;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(ctx).inflate(R.layout.item_cliente_pago_demo, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, final int pos) {

        final ModeloGrupal.IntegrantesDelGrupo integrante = data.get(pos);
        Log.v("NotifyData", "----- "+integrante.getPagoSemanalInt().toString() + " --------");
        holder.bind(integrante, pos);

    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private TextView tvNombre;
        private TextView tvPagoSemanal;
        private EditText etPagoRealizado;
        private EditText etPagoSolidario;
        private EditText etPagoAdelanto;
        private CheckBox cbPagoCompleto;

        //private ImageView ibEditar, ibGuardar;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvNombre        = itemView.findViewById(R.id.tvNombre);
            tvPagoSemanal   = itemView.findViewById(R.id.tvPagoSemanal);
            etPagoRealizado = itemView.findViewById(R.id.etPagoRealizado);
            etPagoSolidario = itemView.findViewById(R.id.etPagoSolidario);
            etPagoAdelanto  = itemView.findViewById(R.id.etPagoAdelanto);
            cbPagoCompleto  = itemView.findViewById(R.id.cbPagoCompleto);

            etPagoRealizado.requestFocus();

            //ibEditar        = itemView.findViewById(R.id.ibEditar);
            //ibGuardar       = itemView.findViewById(R.id.ibGuardar);

        }

        public void bind (final ModeloGrupal.IntegrantesDelGrupo item, final int position) {

            Log.v("-","==================================================================");
            Log.v("Realizado",item.getPagoRealizado()+"");
            Log.v("Solidario",item.getPagoSolidario()+"");
            Log.v("Adelanto",item.getPagoAdelanto()+"");
            Log.v("-","==================================================================");
            tvNombre.setText(item.getNombre());
            tvPagoSemanal.setText(Miscellaneous.moneyFormat(String.valueOf(item.getPagoSemanalInt())));
            etPagoRealizado.setText(String.valueOf(item.getPagoRealizado()));
            etPagoSolidario.setText(String.valueOf((item.getPagoSolidario() != null)?redondearDecimales(item.getPagoSolidario()):redondearDecimales((double)0)));
            etPagoAdelanto.setText(String.valueOf((item.getPagoAdelanto() != null)?redondearDecimales(item.getPagoAdelanto()):redondearDecimales((double)0)));

            cbPagoCompleto.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    //ibGuardar.setVisibility(View.VISIBLE);
                    data.get(position).setPagoCompleto(isChecked);
                    if (isChecked){
                        etPagoRealizado.requestFocus();
                        etPagoRealizado.setText(String.valueOf(redondearDecimales(item.getPagoSemanalInt())));
                        etPagoSolidario.setText(String.valueOf(redondearDecimales((double)0)));
                        etPagoAdelanto.setText(String.valueOf(redondearDecimales((double)0)));
                    }else {
                        etPagoRealizado.requestFocus();
                        etPagoRealizado.setText(String.valueOf(redondearDecimales((double)0)));
                        etPagoSolidario.setText(String.valueOf(redondearDecimales((double)0)));
                        etPagoAdelanto.setText(String.valueOf(redondearDecimales((double)0)));
                    }
                }
            });

            cbPagoCompleto.setChecked(data.get(position).isPagoCompleto());

            etPagoRealizado.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {

                }

                @Override
                public void afterTextChanged(Editable s) {
                    if (s.length() > 0){
                        data.get(position).setPagoRealizado(Double.parseDouble(s.toString()));
                    }
                    else {
                        data.get(position).setPagoRealizado((double) 0);
                    }

                    if(etPagoRealizado.hasFocus()) {
                        if (s.length() > 0) {
                            if (Double.parseDouble(s.toString()) > item.getPagoSemanalInt()) {
                                etPagoSolidario.setText(String.valueOf(redondearDecimales(Double.parseDouble(s.toString()) - item.getPagoSemanalInt())));
                            } else {
                                etPagoSolidario.setText(String.valueOf(redondearDecimales((double) 0)));
                            }

                        } else {
                            etPagoRealizado.setText(String.valueOf(redondearDecimales((double) 0)));
                            etPagoSolidario.setText(String.valueOf(redondearDecimales((double) 0)));
                        }
                    }
                }
            });

            etPagoSolidario.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {

                }

                @Override
                public void afterTextChanged(Editable s) {

                    if (s.length() > 0){
                        data.get(position).setPagoSolidario(Double.parseDouble(s.toString()));
                    }
                    else{
                        data.get(position).setPagoSolidario((double) 0);
                    }

                    if(etPagoSolidario.hasFocus()) {
                        if (s.length() > 0) {
                            if (Double.parseDouble(s.toString()) + item.getPagoSemanalInt() > Double.parseDouble(etPagoRealizado.getText().toString())) {
                                etPagoSolidario.setText(String.valueOf(redondearDecimales(Double.parseDouble(etPagoRealizado.getText().toString().trim()) - item.getPagoSemanalInt())));
                                etPagoAdelanto.setText(String.valueOf(redondearDecimales((double) 0)));
                            } else if (Double.parseDouble(s.toString()) + item.getPagoSemanalInt() < Double.parseDouble(etPagoRealizado.getText().toString())) {
                                etPagoAdelanto.setText(String.valueOf(redondearDecimales(Double.parseDouble(etPagoRealizado.getText().toString()) - item.getPagoSemanalInt() - Double.parseDouble(s.toString()))));
                            }
                        } else {
                            if (Double.parseDouble(etPagoRealizado.getText().toString()) > item.getPagoSemanalInt()){
                                etPagoSolidario.setText(String.valueOf(redondearDecimales((double) 0)));
                                etPagoAdelanto.setText(String.valueOf(redondearDecimales(Double.parseDouble(etPagoRealizado.getText().toString()) - item.getPagoSemanalInt())));
                            }else {
                                etPagoSolidario.setText(String.valueOf(redondearDecimales((double) 0)));
                                etPagoAdelanto.setText(String.valueOf(redondearDecimales((double) 0)));
                            }
                        }
                    }

                }
            });

            etPagoAdelanto.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {

                }

                @Override
                public void afterTextChanged(Editable s) {

                        if (s.length() > 0) {
                            data.get(position).setPagoAdelanto(Double.parseDouble(s.toString()));

                        } else {
                            data.get(position).setPagoAdelanto((double) 0);
                        }

                }
            });

            if (isEditable){
                etPagoRealizado.setEnabled(true);
                etPagoSolidario.setEnabled(true);
                cbPagoCompleto.setEnabled(true);
            }
            else {
                etPagoRealizado.setEnabled(false);
                etPagoSolidario.setEnabled(false);
                cbPagoCompleto.setEnabled(false);
            }
        }
    }

    public static double redondearDecimales(double valorInicial) {
        double parteEntera, resultado;
        resultado = valorInicial;
        parteEntera = Math.floor(resultado);
        resultado=(resultado-parteEntera)*Math.pow(10, 2);
        resultado=Math.round(resultado);
        resultado=(resultado/Math.pow(10, 2))+parteEntera;
        return resultado;
    }

    public void selectAllItem(boolean isSelectedAll) {
        try {
            if (data != null) {
                for (int index = 0; index < data.size(); index++) {
                    data.get(index).setPagoRealizado(data.get(index).getPagoSemanalInt());
                    data.get(index).setPagoCompleto(isSelectedAll);
                }
            }
            notifyDataSetChanged();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
