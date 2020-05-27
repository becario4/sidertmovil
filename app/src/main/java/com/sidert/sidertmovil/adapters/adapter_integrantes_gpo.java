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
import android.widget.Toast;

import com.sidert.sidertmovil.R;

import com.sidert.sidertmovil.models.MIntegrante;
import com.sidert.sidertmovil.models.MIntegrantePago;
import com.sidert.sidertmovil.models.ModeloGrupal;
import com.sidert.sidertmovil.utils.Miscellaneous;

import java.util.List;

public class adapter_integrantes_gpo extends RecyclerView.Adapter<adapter_integrantes_gpo.ViewHolder> {

    private Context ctx;
    public List<MIntegrantePago> data;

    public adapter_integrantes_gpo(Context ctx, List<MIntegrantePago> data) {
        this.ctx = ctx;
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

        final MIntegrantePago integrante = data.get(pos);
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

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvNombre        = itemView.findViewById(R.id.tvNombre);
            tvPagoSemanal   = itemView.findViewById(R.id.tvPagoSemanal);
            etPagoRealizado = itemView.findViewById(R.id.etPagoRealizado);
            etPagoSolidario = itemView.findViewById(R.id.etPagoSolidario);
            etPagoAdelanto  = itemView.findViewById(R.id.etPagoAdelanto);
            cbPagoCompleto  = itemView.findViewById(R.id.cbPagoCompleto);

            etPagoRealizado.clearFocus();
            etPagoRealizado.setFocusableInTouchMode(true);

        }

        public void bind (final MIntegrantePago item, final int position) {

            tvNombre.setText(item.getNombre());
            tvPagoSemanal.setText(Miscellaneous.moneyFormat(String.valueOf(item.getMontoRequerido())));
            etPagoRealizado.setText(String.valueOf(item.getPagoRealizado()));
            etPagoSolidario.setText(String.valueOf((item.getSolidario() != null)?redondearDecimales(Double.parseDouble(item.getSolidario())):redondearDecimales((double)0)));
            etPagoAdelanto.setText(String.valueOf((item.getAdelanto() != null)?redondearDecimales(Double.parseDouble(item.getAdelanto())):redondearDecimales((double)0)));

            cbPagoCompleto.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    if (!data.get(position).isPagoRequerido()){
                        etPagoRealizado.requestFocus();
                        etPagoRealizado.setText(String.valueOf(redondearDecimales(Double.parseDouble(item.getMontoRequerido()))));
                        etPagoSolidario.setText(String.valueOf(redondearDecimales((double)0)));
                        etPagoAdelanto.setText(String.valueOf(redondearDecimales((double)0)));
                    }else {
                        etPagoRealizado.requestFocus();
                        etPagoRealizado.setText(String.valueOf(redondearDecimales((double)0)));
                        etPagoSolidario.setText(String.valueOf(redondearDecimales((double)0)));
                        etPagoAdelanto.setText(String.valueOf(redondearDecimales((double)0)));
                    }
                    data.get(position).setPagoRequerido(!item.isPagoRequerido());
                }
            });
            /*cbPagoCompleto.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    data.get(position).setPagoRequerido(isChecked);
                    if (isChecked){
                        etPagoRealizado.requestFocus();
                        etPagoRealizado.setText(String.valueOf(redondearDecimales(Double.parseDouble(item.getMontoRequerido()))));
                        etPagoSolidario.setText(String.valueOf(redondearDecimales((double)0)));
                        etPagoAdelanto.setText(String.valueOf(redondearDecimales((double)0)));
                    }else {
                        etPagoRealizado.requestFocus();
                        etPagoRealizado.setText(String.valueOf(redondearDecimales((double)0)));
                        etPagoSolidario.setText(String.valueOf(redondearDecimales((double)0)));
                        etPagoAdelanto.setText(String.valueOf(redondearDecimales((double)0)));
                    }
                }
            });*/

            cbPagoCompleto.setChecked(data.get(position).isPagoRequerido());

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
                        data.get(position).setPagoRealizado(s.toString());
                    }
                    else {
                        data.get(position).setPagoRealizado("0");
                    }

                    if(etPagoRealizado.hasFocus()) {
                        if (s.length() > 0) {
                            try{
                                if (Double.parseDouble(s.toString()) > Double.parseDouble(item.getMontoRequerido())) {
                                    etPagoSolidario.setText(String.valueOf(redondearDecimales(Double.parseDouble(s.toString()) - Double.parseDouble(item.getMontoRequerido()))));
                                } else {
                                    etPagoSolidario.setText(String.valueOf(redondearDecimales((double) 0)));
                                }
                            }
                            catch (NumberFormatException e){
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
                        data.get(position).setSolidario(s.toString());
                    }
                    else{
                        data.get(position).setSolidario("0");
                    }

                    if(etPagoSolidario.hasFocus()) {
                        if (s.length() > 0) {

                                if (Double.parseDouble(s.toString()) + Double.parseDouble(item.getMontoRequerido()) > Double.parseDouble(etPagoRealizado.getText().toString())) {
                                    etPagoSolidario.setText(String.valueOf(redondearDecimales(Double.parseDouble(etPagoRealizado.getText().toString().trim()) - Double.parseDouble(item.getMontoRequerido()))));
                                    etPagoAdelanto.setText(String.valueOf(redondearDecimales((double) 0)));
                                } else if (Double.parseDouble(s.toString()) + Double.parseDouble(item.getMontoRequerido()) < Double.parseDouble(etPagoRealizado.getText().toString())) {
                                    etPagoAdelanto.setText(String.valueOf(redondearDecimales(Double.parseDouble(etPagoRealizado.getText().toString()) - Double.parseDouble(item.getMontoRequerido()) - Double.parseDouble(s.toString()))));
                                }


                        } else {
                            if (Double.parseDouble(etPagoRealizado.getText().toString()) > Double.parseDouble(item.getMontoRequerido())){
                                etPagoSolidario.setText(String.valueOf(redondearDecimales((double) 0)));
                                etPagoAdelanto.setText(String.valueOf(redondearDecimales(Double.parseDouble(etPagoRealizado.getText().toString()) - Double.parseDouble(item.getMontoRequerido()))));
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
                            data.get(position).setAdelanto(s.toString());

                        } else {
                            data.get(position).setAdelanto("0");
                        }
                }
            });

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
                    data.get(index).setPagoRealizado(String.valueOf(Math.ceil(Double.parseDouble(data.get(index).getMontoRequerido()))));
                    data.get(index).setPagoRequerido(isSelectedAll);
                }
            }
            notifyDataSetChanged();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
