package com.sidert.sidertmovil.fragments;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

import com.orhanobut.dialogplus.DialogPlus;
import com.orhanobut.dialogplus.OnClickListener;
import com.orhanobut.dialogplus.ViewHolder;
import com.sidert.sidertmovil.Home;
import com.sidert.sidertmovil.R;
import com.sidert.sidertmovil.models.MSoporte;
import com.sidert.sidertmovil.activities.SolicitudTicket;
import com.sidert.sidertmovil.adapters.adapter_soporte;
import com.sidert.sidertmovil.database.DBhelper;
import com.sidert.sidertmovil.fragments.dialogs.dialog_date_picker;
import com.sidert.sidertmovil.utils.SessionManager;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

import static com.sidert.sidertmovil.utils.Constants.DATE;
import static com.sidert.sidertmovil.utils.Constants.DATE_CURRENT;
import static com.sidert.sidertmovil.utils.Constants.DAY_CURRENT;
import static com.sidert.sidertmovil.utils.Constants.FECHAS_POST;
import static com.sidert.sidertmovil.utils.Constants.FORMAT_TIMESTAMP;
import static com.sidert.sidertmovil.utils.Constants.ID;
import static com.sidert.sidertmovil.utils.Constants.IDENTIFIER;
import static com.sidert.sidertmovil.utils.Constants.MONTH_CURRENT;
import static com.sidert.sidertmovil.utils.Constants.REQUEST_CODE_SOPORTE;
import static com.sidert.sidertmovil.utils.Constants.TBL_SOPORTE;
import static com.sidert.sidertmovil.utils.Constants.TICKETS;
import static com.sidert.sidertmovil.utils.Constants.YEAR_CURRENT;
import static com.sidert.sidertmovil.utils.NameFragments.DIALOGDATEPICKER;


public class mesa_ayuda_fragment extends Fragment {

    public String[] _estatus;

    private Context ctx;
    private Home parent;

    private RecyclerView rvTickets;
    private FloatingActionButton fabAdd;

    private adapter_soporte adapter;

    private DBhelper dBhelper;
    private SQLiteDatabase db;
    private String formatString = String.format("%%0%dd", 7);

    private TextView tvFecha;
    private TextView tvEstatus;
    private TextView tvCategoria;
    private CheckBox cbEnviados;
    private CheckBox cbPendientes;
    private Button btnFiltrar;
    private Button btnBorrar;

    private Calendar myCalendar;
    private SimpleDateFormat sdf = new SimpleDateFormat(FORMAT_TIMESTAMP);

    private SessionManager session;

    private ArrayAdapter<String> arrayAdapter;

    private String fecha = "";
    private String estatus = "";
    private String categoria = "";
    private boolean enviados = false;
    private boolean pendientes = false;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_mesa_ayuda, container, false);

        ctx = getContext();
        parent  = (Home) getActivity();

        session = new SessionManager(ctx);

        dBhelper = new DBhelper(ctx);
        db = dBhelper.getWritableDatabase();

        rvTickets = v.findViewById(R.id.rvTickets);
        fabAdd      = v.findViewById(R.id.fbAgregar);

        rvTickets.setLayoutManager(new LinearLayoutManager(ctx));
        rvTickets.setHasFixedSize(false);

        setHasOptionsMenu(true);

        myCalendar = Calendar.getInstance();

        _estatus = getResources().getStringArray(R.array.estatus_ticket);

        arrayAdapter = new ArrayAdapter<String>(ctx, android.R.layout.select_dialog_item);
        String sql = "SELECT * FROM " + TICKETS + " WHERE estatus = ? OR estatus = ?";
        Cursor row = db.rawQuery(sql, new String[]{"true","1"});

        if (row.getCount() > 0){
            row.moveToFirst();
            for (int i = 0; i < row.getCount(); i++){
                arrayAdapter.add(row.getString(2));
                row.moveToNext();
            }
        }
        row.close();

        return v;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        fabAdd.setOnClickListener(fabAdd_OnClick);
    }

    private void GetTickets( String where){
        String sql = "SELECT s.*, t.nombre FROM " + TBL_SOPORTE + " AS s INNER JOIN " + TICKETS + " AS t ON s.categoria_id = t.ticket_id"+where+ " ORDER BY fecha_solicitud DESC";
        final Cursor row = db.rawQuery(sql, null);

        ArrayList<MSoporte> _soporte = new ArrayList<>();
        if (row.getCount() > 0){
            row.moveToFirst();
            for(int i = 0; i < row.getCount(); i++){
                MSoporte item = new MSoporte();
                item.setFolioSolicitud("#"+String.format(formatString, row.getLong(9)));
                item.setEstatusSolicitud(row.getString(14));
                item.setCategoria(row.getString(16));
                item.setComentario(row.getString(8));
                item.setFechaSolicitud(row.getString(12));
                item.setFechaEnvio(row.getString(13));
                _soporte.add(item);
                row.moveToNext();

            }
        }
        row.close();

        adapter = new adapter_soporte(ctx, _soporte, new adapter_soporte.Event() {
            @Override
            public void Soporte(MSoporte item) {
                String tipo = (session.getUser().get(5).contains("ROLE_GESTOR"))?"GESTOR"+session.getUser().get(0):"ASESOR"+session.getUser().get(0);
                String mensaje = "Este es un mensaje de solicitud de soporte del *"+tipo+"* \n *Fecha Solicitud:* "+item.getFechaSolicitud()+"\n *Categoria:* " + item.getCategoria() +"\n*Comentario:* _" + item.getComentario() +"_";
                Intent waIntent = new Intent(Intent.ACTION_SEND);
                waIntent.setType("text/plain");
                waIntent.setPackage("com.whatsapp");
                if (waIntent != null) {
                    waIntent.putExtra(
                            Intent.EXTRA_TEXT,
                            mensaje);
                    startActivity(Intent.createChooser(waIntent, "Share with"));
                } else
                    Toast.makeText(ctx, "WhatsApp not Installed", Toast.LENGTH_SHORT)
                            .show();
            }
        });
        rvTickets.setAdapter(adapter);

    }

    private View.OnClickListener fabAdd_OnClick = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Intent i_solicitud = new Intent(parent, SolicitudTicket.class);
            startActivityForResult(i_solicitud, REQUEST_CODE_SOPORTE);
        }
    };

    private void Filtros (){
        DialogPlus filtros_dg = DialogPlus.newDialog(parent)
                .setContentHolder(new ViewHolder(R.layout.sheet_dialog_filtros_tickets))
                .setGravity(Gravity.TOP)
                .setPadding(20,10,20,10)
                .setOnClickListener(new OnClickListener() {
                    @Override
                    public void onClick(DialogPlus dialog, View view) {
                        String where = "";

                        InputMethodManager imm = (InputMethodManager) parent.getSystemService(Context.INPUT_METHOD_SERVICE);
                        switch (view.getId()) {
                            case R.id.btnFiltrar:
                                if (!tvFecha.getText().toString().trim().isEmpty()){
                                    fecha = tvFecha.getText().toString().trim();
                                    where = " AND fecha_solicitud LIKE '%"+fecha+"%'";
                                }
                                else
                                    fecha = "";

                                if (!tvEstatus.getText().toString().trim().isEmpty()){
                                    estatus = tvEstatus.getText().toString().trim();
                                    where = " AND estatus_ticket LIKE '%"+estatus+"%'";
                                }
                                else
                                    estatus = "";

                                if (!tvCategoria.getText().toString().trim().isEmpty()){
                                    categoria = tvCategoria.getText().toString().trim();
                                    String sql = "SELECT ticket_id FROM " + TICKETS + " WHERE nombre = ?";
                                    Cursor row = db.rawQuery(sql, new String[]{categoria});
                                    row.moveToFirst();
                                    where = " AND estatus_ticket = " + row.getInt(0) + "";
                                    row.close();
                                }
                                else
                                    categoria = "";

                                if (cbEnviados.isChecked() && cbPendientes.isChecked()){
                                    enviados = true;
                                    pendientes = true;
                                    where += " AND estatus_envio IN (1, 2)";
                                }
                                else if (cbEnviados.isChecked()){
                                    enviados = true;
                                    pendientes = false;
                                    where += " AND estatus_envio = 1";
                                }
                                else if (cbPendientes.isChecked()){
                                    enviados = false;
                                    pendientes = true;
                                    where += " AND estatus_envio = 0";
                                }
                                else{
                                    enviados = false;
                                    pendientes = false;
                                }

                                imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
                                Log.e("where",where);
                                if (where.length() > 4)
                                    GetTickets(" WHERE" + where.substring(4));
                                else
                                    GetTickets("");
                                dialog.dismiss();

                                break;
                            case R.id.btnBorrar:
                                tvFecha.setText("");
                                tvEstatus.setText("");
                                tvCategoria.setText("");
                                cbEnviados.setChecked(false);
                                cbPendientes.setChecked(false);
                                fecha = "";
                                estatus = "";
                                categoria = "";
                                enviados = false;
                                pendientes = false;



                                imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
                                GetTickets("");
                                //dialog.dismiss();

                                break;
                        }
                        //setupBadge();

                    }
                })
                .setExpanded(true, 900)
                .create();

        tvFecha = filtros_dg.getHolderView().findViewById(R.id.tvFecha);
        tvEstatus = filtros_dg.getHolderView().findViewById(R.id.tvEstatus);
        tvCategoria = filtros_dg.getHolderView().findViewById(R.id.tvCategoria);
        cbEnviados = filtros_dg.getHolderView().findViewById(R.id.cbEnviadas);
        cbPendientes = filtros_dg.getHolderView().findViewById(R.id.cbPendientes);
        btnFiltrar = filtros_dg.getHolderView().findViewById(R.id.btnFiltrar);
        btnBorrar = filtros_dg.getHolderView().findViewById(R.id.btnBorrar);

        tvFecha.setText(fecha);
        tvEstatus.setText(estatus);
        tvCategoria.setText(categoria);
        cbEnviados.setChecked(enviados);
        cbPendientes.setChecked(pendientes);


        tvFecha.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog_date_picker dialogDatePicker = new dialog_date_picker();

                Bundle b = new Bundle();

                b.putInt(YEAR_CURRENT, myCalendar.get(Calendar.YEAR));
                b.putInt(MONTH_CURRENT, myCalendar.get(Calendar.MONTH));
                b.putInt(DAY_CURRENT, myCalendar.get(Calendar.DAY_OF_MONTH));
                b.putString(DATE_CURRENT, sdf.format(myCalendar.getTime()));
                b.putInt(IDENTIFIER, 14);
                b.putBoolean(FECHAS_POST, false);
                dialogDatePicker.setArguments(b);
                dialogDatePicker.setTargetFragment(mesa_ayuda_fragment.this,123);
                dialogDatePicker.show(parent.getSupportFragmentManager(), DIALOGDATEPICKER);
            }
        });

        tvEstatus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(ctx);
                builder.setTitle(R.string.selected_option)
                        .setItems(R.array.estatus_ticket, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int position) {
                                tvEstatus.setText(_estatus[position]);
                            }
                        });
                builder.create();
                builder.show();
            }
        });

        tvCategoria.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builderSingle = new AlertDialog.Builder(ctx);
                builderSingle.setTitle("Seleccione una opción");
                builderSingle.setAdapter(arrayAdapter, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        tvCategoria.setText(arrayAdapter.getItem(which));
                        dialog.dismiss();
                    }
                });
                builderSingle.show();
            }
        });

        filtros_dg.show();
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        menu.clear();
        onResume();
        inflater.inflate(R.menu.menu_filtro, menu);
        menu.getItem(1).setVisible(false);

        final MenuItem menuItem = menu.findItem(R.id.nvFiltro);
        View actionView = MenuItemCompat.getActionView(menuItem);
        TextView tvContFiltros = actionView.findViewById(R.id.filtro_bagde);
        tvContFiltros.setVisibility(View.GONE);
        actionView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onOptionsItemSelected(menuItem);
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.nvFiltro:
                Filtros();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.e("RequesCode", "Code: "+requestCode);
        switch (requestCode){
            case REQUEST_CODE_SOPORTE:
                Log.e("ResultCode", "Code: "+resultCode);
                if (resultCode == parent.RESULT_OK){
                    Log.e("Data", String.valueOf(data != null));
                    if (data != null){
                        String sql = "SELECT s.fecha_solicitud, t.nombre, s.comentario FROM " + TBL_SOPORTE + " AS s INNER JOIN " + TICKETS + " AS T ON s.categoria_id = t.ticket_id WHERE s._id = ?";
                        Cursor row = db.rawQuery(sql,  new String[]{String.valueOf(data.getLongExtra(ID,0))});

                        if (row.getCount() > 0) {
                            row.moveToFirst();
                            String tipo = (session.getUser().get(5).contains("ROLE_GESTOR"))?"GESTOR"+session.getUser().get(0):"ASESOR"+session.getUser().get(0);
                            String mensaje = "Este es un mensaje de solicitud de soporte del *"+tipo+"* \n *Fecha Solicitud:* "+ row.getString(0)+ "\n *Categoria:* " + row.getString(1) +"\n*Comentario:* _" + row.getString(2) +"_";
                            Intent waIntent = new Intent(Intent.ACTION_SEND);
                            waIntent.setType("text/plain");
                            waIntent.setPackage("com.whatsapp");
                            if (waIntent != null) {
                                waIntent.putExtra(
                                        Intent.EXTRA_TEXT,
                                        mensaje);
                                startActivity(Intent.createChooser(waIntent, "Share with"));
                            } else
                                Toast.makeText(ctx, "WhatsApp not Installed", Toast.LENGTH_SHORT)
                                        .show();
                        }
                    }
                }
                break;
            case 123:
                if (resultCode == 321){
                    if (data != null){
                        tvFecha.setError(null);
                        tvFecha.setText(data.getStringExtra(DATE));
                    }
                }
                break;
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        GetTickets("");
    }
}
