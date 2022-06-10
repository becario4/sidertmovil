package com.sidert.sidertmovil.fragments.view_pager;

import android.annotation.SuppressLint;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.drawable.ColorDrawable;
//import android.graphics.drawable.RotateDrawable;
import android.os.Bundle;
/*import androidx.annotation.Nullable;
import android.support.v4.app.Fragment;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;*/
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.CheckBox;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.orhanobut.dialogplus.DialogPlus;
import com.orhanobut.dialogplus.OnClickListener;
import com.orhanobut.dialogplus.ViewHolder;
import com.sidert.sidertmovil.AlarmaManager.AlarmaTrackerReciver;
import com.sidert.sidertmovil.Home;
import com.sidert.sidertmovil.R;
import com.sidert.sidertmovil.activities.MisCierresDeDia;
import com.sidert.sidertmovil.activities.PrestamosClientes;
import com.sidert.sidertmovil.activities.ResumenCartera;
import com.sidert.sidertmovil.adapters.adapter_fichas_pendientes;
import com.sidert.sidertmovil.database.DBhelper;
import com.sidert.sidertmovil.fragments.orders_fragment;
import com.sidert.sidertmovil.models.MCarteraGnral;
import com.sidert.sidertmovil.utils.Constants;
import com.sidert.sidertmovil.utils.Miscellaneous;
import com.sidert.sidertmovil.utils.NetworkStatus;
import com.sidert.sidertmovil.utils.Popups;
import com.sidert.sidertmovil.utils.Servicios_Sincronizado;
import com.sidert.sidertmovil.utils.SessionManager;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

import static com.sidert.sidertmovil.utils.Constants.CANCEL_TRACKER_ID;
import static com.sidert.sidertmovil.utils.Constants.ID_CARTERA;
import static com.sidert.sidertmovil.utils.Constants.TBL_CARTERA_GPO;
import static com.sidert.sidertmovil.utils.Constants.TBL_CARTERA_GPO_T;
import static com.sidert.sidertmovil.utils.Constants.TBL_CARTERA_IND;
import static com.sidert.sidertmovil.utils.Constants.TBL_CARTERA_IND_T;
import static com.sidert.sidertmovil.utils.Constants.TBL_CIERRE_DIA_T;
import static com.sidert.sidertmovil.utils.Constants.TBL_PRESTAMOS_GPO_T;
import static com.sidert.sidertmovil.utils.Constants.TBL_PRESTAMOS_IND_T;
import static com.sidert.sidertmovil.utils.Constants.TBL_RESPUESTAS_GPO_T;
import static com.sidert.sidertmovil.utils.Constants.TBL_RESPUESTAS_IND_T;
import static com.sidert.sidertmovil.utils.Constants.TBL_RESPUESTAS_IND_V_T;
import static com.sidert.sidertmovil.utils.Constants.TBL_RESPUESTAS_INTEGRANTE_T;
import static com.sidert.sidertmovil.utils.Constants.TIPO;

/**Clase para ver la cartera que tiene asignado el asesor/gestor*/
public class fichas_pendientes_fragment extends Fragment {

    private Context ctx;
    private Home boostrap;
    private RecyclerView rvFichas;
    private adapter_fichas_pendientes adapter;

    private List<MCarteraGnral> _m_carteraGral;
    private SQLiteDatabase db;
    private DBhelper dBhelper;

    private SessionManager session;

    private TextView tvNoInfo;
    public TextView tvContFiltros;
    public TextView tvContCierre;
    private AutoCompleteTextView aetNombre;
    private AutoCompleteTextView aetDia;
    private AutoCompleteTextView aetColonia;
    private Spinner spAsesor;
    private CheckBox cbInd;
    private CheckBox cbGpo;
    private CheckBox cbParcial;
    private int cont_filtros = 0;

    private String[] dataAsesor;
    private ArrayAdapter<String> adapterNombre;
    private ArrayAdapter<String> adapterDia;
    private ArrayAdapter<String> adapterColonia;
    private ArrayAdapter<String> adapterAsesor;

    private orders_fragment parent;

    List<String> asesor;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_fichas_pendientes, container, false);
        ctx = getContext();
        boostrap = (Home) getActivity();

        parent = (orders_fragment) getParentFragment();

        session = new SessionManager(ctx);

        dBhelper = new DBhelper(ctx);
        db = dBhelper.getWritableDatabase();

        tvNoInfo = view.findViewById(R.id.tvNoInfo);
        rvFichas = view.findViewById(R.id.rvFichas);

        rvFichas.setLayoutManager(new LinearLayoutManager(ctx));
        //rvFichas.setHasFixedSize(false);

        _m_carteraGral = new ArrayList<>();

        /**Funcion para obtener el listado de asesores que
         * tiene asignado en la cartera por si se estan compartiendo cartera*/
        GetAsesores();

        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        setHasOptionsMenu(true);

        /**Adaptador para colocar el listado de cartera */
        adapter = new adapter_fichas_pendientes(ctx, _m_carteraGral, new adapter_fichas_pendientes.Event() {
            @Override
            public void FichaOnClick(MCarteraGnral item) {
                /**Evento al dar tap en cualquier ficha para visualizar los prestamos*/
                Intent i_prestamos = new Intent(boostrap, PrestamosClientes.class);
                i_prestamos.putExtra(ID_CARTERA, item.getId_cliente());
                i_prestamos.putExtra(TIPO, item.getTipo());
                i_prestamos.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(i_prestamos);
            }

            @Override
            public void IsRutaOnClick(MCarteraGnral item, boolean is_ruta, int pos) {
                /**Evento para Agregar o Remover de Ruta*/
                ContentValues cv = new ContentValues();
                String tbl = "";
                int ruta = 0;
                if (is_ruta)
                    ruta = 1;

                cv.put("is_ruta", ruta);

                switch (item.getTipo()) {
                    case "INDIVIDUAL":
                        tbl = TBL_CARTERA_IND_T;
                        break;
                    case "GRUPAL":
                        tbl = TBL_CARTERA_GPO_T;
                        break;
                }
                db.update(tbl, cv, "id_cartera = ?", new String[]{item.getId_cliente()});
            }
        });
        //GetCartera("");

        rvFichas.setAdapter(adapter);

    }

    /**
     * Funcion para obtener la cartera asignada, recibe como parametro
     * un String que es el condicional para filtrar
     * ejemplo where = " WHERE nombre LIKE '%ALEJANDRO%' AND dia LIKE '%JUEVES%'"
     */
    private void GetCartera(String where) {
        Cursor row;
        _m_carteraGral = new ArrayList<>();

        /**Se prepara la consulta para obtener la cartera de las tablas de TBL_CARTERA_IND_T, TBL_CARTERA_GPO_T
         * y JOIN con TBL_PRESTAMOS_IND_T y TBL_PRESTAMOS_GPO_T y
         * JOIN con TBL_RESPUESTAS_IND_T, TBL_RESPUESTAS_GPO_T, TBL_RESPUESTAS_IND_V_T, TBL_RESPUESTAS_INTEGRANTE_T
         * para saber si tiene en estatus parcial*/
        String query = "SELECT * FROM (SELECT id_cartera,nombre, direccion,is_ruta, ruta_obligado,dia,'' as tesorera,asesor_nombre,'INDIVIDUAL' AS tipo, colonia, COALESCE(pi.tipo_cartera,'NO ENCONTRADO'), COALESCE(ri.estatus, -1) AS parcial, ci.dias_atraso FROM " + TBL_CARTERA_IND_T + " AS ci LEFT JOIN " + TBL_PRESTAMOS_IND_T + " AS pi ON ci.id_cartera = pi.id_cliente LEFT JOIN " + TBL_RESPUESTAS_IND_T + " AS ri ON pi.id_prestamo = ri.id_prestamo WHERE ri._id = (SELECT ri2._id FROM " + TBL_RESPUESTAS_IND_T + " AS ri2 WHERE ri2.id_prestamo = pi.id_prestamo ORDER BY ri2._id DESC LIMIT 1) OR ri._id is null AND pi.tipo_cartera IN ('VIGENTE', 'COBRANZA') AND ci.estatus = '1' UNION SELECT id_cartera,nombre, direccion,is_ruta, ruta_obligado,dia,tesorera,asesor_nombre,'GRUPAL' AS tipo, colonia, COALESCE(pg.tipo_cartera,'NO ENCONTRADO'), COALESCE(rg.estatus, -1) AS parcial, cg.dias_atraso FROM " + TBL_CARTERA_GPO_T + " AS cg LEFT JOIN " + TBL_PRESTAMOS_GPO_T + " AS pg ON cg.id_cartera = pg.id_grupo LEFT JOIN " + TBL_RESPUESTAS_GPO_T + " AS rg ON pg.id_prestamo = rg.id_prestamo WHERE rg._id = (SELECT rg2._id FROM " + TBL_RESPUESTAS_GPO_T + " AS rg2 WHERE rg2.id_prestamo = pg.id_prestamo ORDER by rg2._id DESC LIMIT 1) OR rg._id IS null AND pg.tipo_cartera IN ('VIGENTE', 'COBRANZA') AND cg.estatus = '1' UNION SELECT cvi.id_cartera, cvi.nombre, cvi.direccion, cvi.is_ruta, cvi.ruta_obligado, cvi.dia, '' as tesorera, cvi.asesor_nombre, 'INDIVIDUAL' AS tipo, cvi.colonia, COALESCE(pvi.tipo_cartera,'NO ENCONTRADO'), COALESCE(rvi.estatus, -1) AS parcial, cvi.dias_atraso FROM " + TBL_CARTERA_IND_T + " AS cvi LEFT JOIN " + TBL_PRESTAMOS_IND_T + " AS pvi ON cvi.id_cartera = pvi.id_cliente LEFT JOIN " + TBL_RESPUESTAS_IND_V_T + " AS rvi ON pvi.id_prestamo = rvi.id_prestamo WHERE rvi._id = (SELECT rvi2._id FROM " + TBL_RESPUESTAS_IND_V_T + " AS rvi2 WHERE rvi2.id_prestamo = pvi.id_prestamo ORDER BY rvi2._id DESC LIMIT 1) OR rvi._id is null AND pvi.tipo_cartera IN ('VENCIDA') AND cvi.estatus = '1' UNION SELECT cvg.id_cartera,cvg.nombre, cvg.direccion,cvg.is_ruta, cvg.ruta_obligado,cvg.dia,cvg.tesorera,cvg.asesor_nombre,'GRUPAL' AS tipo, cvg.colonia, COALESCE(pvg.tipo_cartera,'NO ENCONTRADO'), COALESCE(rvg.estatus, -1) AS parcial, cvg.dias_atraso FROM " + TBL_CARTERA_GPO_T + " AS cvg LEFT JOIN " + TBL_PRESTAMOS_GPO_T + " AS pvg ON cvg.id_cartera = pvg.id_grupo LEFT JOIN " + TBL_RESPUESTAS_INTEGRANTE_T + " AS rvg ON pvg.id_prestamo = rvg.id_prestamo WHERE rvg._id = (SELECT rvg2._id FROM " + TBL_RESPUESTAS_INTEGRANTE_T + " AS rvg2 WHERE rvg2.id_prestamo = pvg.id_prestamo ORDER by rvg2._id DESC LIMIT 1) OR rvg._id IS null AND pvg.tipo_cartera IN ('VENCIDA') AND cvg.estatus = '1') AS cartera " + where + " GROUP BY id_cartera";

        Log.e("QUERYCartera", query);
        row = db.rawQuery(query, null);

        /**Coloca el total de registros obtenidos en el titulo de Cartera*/
        parent.SetUpBagde(0, row.getCount());

        /**Arrays para almacenar los nombre de los cliente, dias de la semana y colonias para filtros*/
        String[] dataNombre;
        String[] dataDia;
        String[] dataColonia;
        Log.e("QueryCount", String.valueOf(row.getCount()));
        if (row.getCount() > 0) {
            row.moveToFirst();
            List<String> nombre = new ArrayList<>();
            List<String> dia = new ArrayList<>();
            List<String> colonia = new ArrayList<>();

            /**Se recorre de la cartera obtenida */
            for (int i = 0; i < row.getCount(); i++) {
                nombre.add(row.getString(1));
                dia.add(row.getString(5));
                colonia.add(row.getString(9));
                MCarteraGnral mCarteraGeneral = new MCarteraGnral();
                mCarteraGeneral.setId_cliente(row.getString(0));
                mCarteraGeneral.setTipo(row.getString(8));
                mCarteraGeneral.setNombre(row.getString(1));
                mCarteraGeneral.setTesorera(row.getString(6));
                mCarteraGeneral.setDireccion(row.getString(2));
                mCarteraGeneral.setDiaSemana(row.getString(5));
                mCarteraGeneral.setIs_ruta(row.getInt(3) == 1);
                mCarteraGeneral.setIs_obligatorio(row.getInt(4) == 1);
                mCarteraGeneral.setTipoPrestamo(row.getString(10));
                mCarteraGeneral.setParcial(row.getInt(11));
                //
                mCarteraGeneral.setDiasMora(Miscellaneous.Rango(row.getInt(12)));
                /**Agrega el objecto para despues ser colocado en la lista*/
                _m_carteraGral.add(mCarteraGeneral);
                row.moveToNext();
            }

            /**Remueve los datos repetidos en nombre del cliente, colonias, dias de la semana
             * esto se ocupa para filtros*/
            dataNombre = Miscellaneous.RemoverRepetidos(nombre);
            dataColonia = Miscellaneous.RemoverRepetidos(colonia);
            dataDia = Miscellaneous.RemoverRepetidos(dia);

            adapterNombre = new ArrayAdapter<>(ctx,
                    R.layout.custom_list_item, R.id.text_view_list_item, dataNombre);

            adapterDia = new ArrayAdapter<>(ctx,
                    R.layout.custom_list_item, R.id.text_view_list_item, dataDia);

            adapterColonia = new ArrayAdapter<>(ctx,
                    R.layout.custom_list_item, R.id.text_view_list_item, dataColonia);

        } else {
            /**Por si no encontró resultados de la cartera se inicializan los arrays con vacio esto para filtros*/
            dataNombre = new String[1];
            dataNombre[0] = "";
            adapterNombre = new ArrayAdapter<>(ctx,
                    R.layout.custom_list_item, R.id.text_view_list_item, dataNombre);

            dataDia = new String[1];
            dataDia[0] = "";
            adapterDia = new ArrayAdapter<>(ctx,
                    R.layout.custom_list_item, R.id.text_view_list_item, dataDia);

            dataColonia = new String[1];
            dataColonia[0] = "";
            adapterColonia = new ArrayAdapter<>(ctx,
                    R.layout.custom_list_item, R.id.text_view_list_item, dataColonia);
        }
        row.close();

        if (_m_carteraGral.size() > 0) {
            /**Actualiza la lista de la cartera*/
            adapter.UpdateData(_m_carteraGral);
            rvFichas.setVisibility(View.VISIBLE);
            tvNoInfo.setVisibility(View.GONE);
        } else {
            /**Muestra leyenda de que no se encontró información*/
            rvFichas.setVisibility(View.GONE);
            tvNoInfo.setVisibility(View.VISIBLE);
        }
    }

    /**Se carga los menus Filtros, Informacion, Sincronizar*/
    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        menu.clear();
        onResume();
        inflater.inflate(R.menu.menu_cartera, menu);

        /**Si tiene ROLE_GESTOR mostrar menu de Cierre de dia*/
        if (session.getUser().get(5).contains("ROLE_GESTOR"))
            menu.getItem(0).setVisible(true);

        /**Variables para el menu de filtro y Contador de filtros realizado*/
        final MenuItem menuItem = menu.findItem(R.id.nvFiltro);
        View actionView = menuItem.getActionView();
        tvContFiltros = actionView.findViewById(R.id.filtro_bagde);
        actionView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onOptionsItemSelected(menuItem);
            }
        });

        /**Variables para el menu de cierre de dia y Contador de cuantos cierre de dia tiene*/
        final MenuItem menuItemCierre = menu.findItem(R.id.nvCierreDia);
        View actionViewCierre = menuItemCierre.getActionView();
        tvContCierre = actionViewCierre.findViewById(R.id.filtro_bagde);
        actionViewCierre.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onOptionsItemSelected(menuItemCierre);
            }
        });

        setupBadge();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.nvFiltro:/**Cuando seleccioan el menu Filtro*/
                Filtros();
                return true;
            case R.id.nvInfo:/**Cuando seleccioan el menu Información*/
                Intent i_resumen = new Intent(boostrap, ResumenCartera.class);
                startActivity(i_resumen);
                return true;
            case R.id.nvSynchronized:/**Cuando seleccioan el menu Sincronizar*/
                /**Valida que tenga conexión a internet*/
                if (NetworkStatus.haveNetworkConnection(ctx)) {
                    Servicios_Sincronizado ss = new Servicios_Sincronizado();
                    /**Ejecuta funcion para enviar solo gestiones contestadas*/
                    ss.SaveRespuestaGestion(boostrap, true);
                }
                else{
                    /**Mensaje cuando tratan de sincronizar y no tienen internet*/
                    final AlertDialog error_network = Popups.showDialogMessage(boostrap, Constants.not_network,
                            R.string.not_network, R.string.accept, new Popups.DialogMessage() {
                                @Override
                                public void OnClickListener(AlertDialog dialog) {
                                    dialog.dismiss();
                                }
                            });
                    Objects.requireNonNull(error_network.getWindow()).requestFeature(Window.FEATURE_NO_TITLE);
                    error_network.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
                    error_network.show();
                }
                return true;
            case R.id.nvCierreDia:/**Cuando seleccioan el menu Cierre de dia*/
                Intent i_cierre_dia = new Intent(ctx, MisCierresDeDia.class);
                startActivity(i_cierre_dia);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    /**Funcion para mostrar la vista de filtros*/
    @SuppressLint("ClickableViewAccessibility")
    private void Filtros (){
        /**Se crea un nuevo dialogo con su configuracion de mostrar arriba y el padding*/
        DialogPlus filtros_dg = DialogPlus.newDialog(boostrap)
                .setContentHolder(new ViewHolder(R.layout.sheet_dialog_filtros_cartera))
                .setGravity(Gravity.TOP)
                .setPadding(20,10,20,10)
                .setOnClickListener(new OnClickListener() {
                    @Override
                    public void onClick(DialogPlus dialog, View view) {
                        String where = "";
                        cont_filtros = 0;
                        HashMap<String, String> filtros = new HashMap<>();
                        InputMethodManager imm = (InputMethodManager)boostrap.getSystemService(Context.INPUT_METHOD_SERVICE);
                        /**Se valida que boton fue selecionado si Borrar filtros o Filtrar*/
                        switch (view.getId()) {
                            case R.id.btnFiltrar:/**Seleccionaron Filtrar*/
                                /**Se valida si el campo de nombre contiene algun valor*/
                                if (!aetNombre.getText().toString().trim().isEmpty()){
                                    /**va agregando en el map el valor del campo nombre para guardarlo despues en variable de sesion*/
                                    filtros.put("nombre_cartera_p",aetNombre.getText().toString().trim());
                                    /**Aumenta el contador */
                                    cont_filtros += 1;
                                    /**Va creando el condicional para filtrar en la obtencion de cartera por nombre cliente/grupo*/
                                    where = " AND nombre LIKE '%"+aetNombre.getText().toString().trim()+"%'";
                                }
                                /**Cuando esta vacio el campo solo guarda en el map vacio en nombre*/
                                else filtros.put("nombre_cartera_p","");

                                /**Se valida si el campo de Dia de la semana contiene algun valor*/
                                if (!aetDia.getText().toString().trim().isEmpty()) {
                                    /**va agregando en el map el valor del campo dia para guardarlo despues en variable de sesion*/
                                    filtros.put("dia_semana_p",aetDia.getText().toString().trim());
                                    /**Aumenta el contador */
                                    cont_filtros += 1;
                                    /**Va agregando el condicional para filtrar en la obtencion de cartera por dia*/
                                    where += " AND dia LIKE '%" + aetDia.getText().toString().trim() + "%'";
                                }
                                /**Cuando esta vacio el campo solo guarda en el map vacio en dia semana*/
                                else filtros.put("dia_semana_p","");

                                /**Se valida si el campo de Colonia contiene algun valor*/
                                if (!aetColonia.getText().toString().trim().isEmpty()) {
                                    /**va agregando en el map el valor del campo colonia para guardarlo despues en variable de sesion*/
                                    filtros.put("colonia_cartera_p",aetColonia.getText().toString().trim());
                                    /**Aumenta el contador */
                                    cont_filtros += 1;
                                    /**Va agregando el condicional para filtrar en la obtencion de cartera por colonia*/
                                    where += " AND colonia LIKE '%" + aetColonia.getText().toString().trim() + "%'";
                                }
                                /**Cuando esta vacio el campo solo guarda en el map vacio en colonia*/
                                else filtros.put("colonia_cartera_p","");

                                /**Si el valor del selector (spinner) es diferente de vacio*/
                                if (!spAsesor.getSelectedItem().toString().trim().isEmpty()) {
                                    /**va agregando en el map el valor del campo asesor para guardarlo despues en variable de sesion*/
                                    filtros.put("asesor_cartera_p",spAsesor.getSelectedItemPosition()+"");
                                    /**Aumenta el contador */
                                    cont_filtros += 1;
                                    /**Va agregando el condicional para filtrar en la obtencion de cartera por asesor*/
                                    where += " AND asesor_nombre LIKE '%" + spAsesor.getSelectedItem().toString().trim() + "%'";
                                }
                                /**Cuando esta vacio el campo solo guarda en el map vacio en asesor*/
                                else filtros.put("asesor_cartera_p","0");

                                /**Valida si los checklist de individual y grupal esten seleccionados*/
                                if (cbInd.isChecked() && cbGpo.isChecked()){
                                    /**va agregando en el map el valor del campo tipo cartera para guardarlo despues en variable de sesion*/
                                    filtros.put("tipo_cartera_ind_p","1");
                                    filtros.put("tipo_cartera_gpo_p","1");
                                    /**Aumenta el contador aqui en 2 porque son 2 filtros*/
                                    cont_filtros += 2;
                                    /**Va agregando el condicional para filtrar en la obtencion de cartera por tipo*/
                                    where += " AND tipo IN ('INDIVIDUAL','GRUPAL')";
                                }
                                /**Valida si solo el checklist de individual esta seleccionado*/
                                else if (cbInd.isChecked()){
                                    /**va agregando en el map el valor del campo tipo cartera para guardarlo despues en variable de sesion*/
                                    filtros.put("tipo_cartera_ind_p","1");
                                    filtros.put("tipo_cartera_gpo_p","0");
                                    /**Aumenta el contador*/
                                    cont_filtros += 1;
                                    /**Va agregando el condicional para filtrar en la obtencion de cartera por tipo*/
                                    where += " AND tipo = 'INDIVIDUAL'";
                                }
                                /**Valida si solo el checklist de grupal esta seleccionado*/
                                else if (cbGpo.isChecked()){
                                    /**va agregando en el map el valor del campo tipo cartera para guardarlo despues en variable de sesion*/
                                    filtros.put("tipo_cartera_ind_p","0");
                                    filtros.put("tipo_cartera_gpo_p","1");
                                    /**Aumenta el contador*/
                                    cont_filtros += 1;
                                    /**Va agregando el condicional para filtrar en la obtencion de cartera por tipo*/
                                    where += " AND tipo = 'GRUPAL'";
                                }else {
                                    /**Cuando no ha seleccionado ningun checklist solo guarda en el map vacio en tipos*/
                                    filtros.put("tipo_cartera_ind_p","0");
                                    filtros.put("tipo_cartera_gpo_p","0");
                                }

                                /**Valida si el check de parciales esta seleccioanado para saber si tiene gestiones parciales*/
                                if (cbParcial.isChecked()){
                                    /**va agregando en el map el valor del campo parcial cartera para guardarlo despues en variable de sesion*/
                                    filtros.put("parcial_cartera_p","1");
                                    /**Aumenta el contador*/
                                    cont_filtros += 1;
                                    /**Va agregando el condicional para filtrar en la obtencion de cartera por gestiones parciales*/
                                    where += " AND parcial = '0'";
                                }else {
                                    /**Cuando no ha seleccionado el checklist solo guarda en el map vacio en parcial*/
                                    filtros.put("parcial_cartera_p","0");
                                    //where += " AND parcial IN ('-1', '0', '1', '2')";
                                }

                                /**Agrega el contador al map para ser guardado en variable de sesion*/
                                filtros.put("contador_cartera_p", String.valueOf(cont_filtros));
                                /**Colocar el map de filtros en variable de sesion*/
                                session.setFiltrosCartera(filtros);

                                /**Oculta el teclado virtual*/
                                imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
                                Log.e("where",where);
                                /**Si la variable where (condicional) su tamaño es mayor a 4 le antepone la palabra WHERE para la consulta*/
                                if (where.length() > 4)
                                    GetCartera("WHERE" + where.substring(4));
                                else {
                                    /**No seleccionó ningun filtro se hace la consulta sin condiciones*/
                                    GetCartera("");
                                }
                                /**cierra la ventana de filtros*/
                                dialog.dismiss();

                                break;
                            case R.id.btnBorrarFiltros:/**cuando selecciona borrar filtros*/
                                /**Limpia todas las variables*/
                                cbInd.setChecked(false);
                                cbGpo.setChecked(false);
                                cbParcial.setChecked(false);
                                aetNombre.setText("");
                                aetDia.setText("");
                                aetColonia.setText("");
                                spAsesor.setSelection(0);
                                cont_filtros = 0;
                                /**Crea el map para variables de sesion en filtros de cartera*/
                                filtros = new HashMap<>();
                                filtros.put("nombre_cartera_p","");
                                filtros.put("dia_semana_p","");
                                filtros.put("colonia_carteta_p","");
                                filtros.put("asesor_cartera_p","0");
                                filtros.put("tipo_cartera_ind_p","0");
                                filtros.put("tipo_cartera_gpo_p","0");
                                filtros.put("contador_cartera_p", "0");
                                filtros.put("parcial_cartera_p", "0");
                                /**Coloca el map en variables de sesion*/
                                session.setFiltrosCartera(filtros);

                                /**Oculta el teclado virtual*/
                                imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
                                /**Obtiene la cartera de la fichas sin ningun filtro*/
                                GetCartera("");


                                aetNombre.setAdapter(adapterNombre);
                                aetDia.setAdapter(adapterDia);
                                aetColonia.setAdapter(adapterColonia);
                                spAsesor.setAdapter(adapterAsesor);
                                //dialog.dismiss();

                                break;
                        }
                        setupBadge();

                    }
                })
                .setExpanded(true, 1000)
                .create();
        /**Declaracion de variables con la vista XML*/
        aetNombre   = filtros_dg.getHolderView().findViewById(R.id.aetNombre);
        aetDia      = filtros_dg.getHolderView().findViewById(R.id.aetDia);
        aetColonia  = filtros_dg.getHolderView().findViewById(R.id.aetColonia);
        spAsesor    = filtros_dg.getHolderView().findViewById(R.id.spAsesor);
        cbInd       = filtros_dg.getHolderView().findViewById(R.id.cbInd);
        cbGpo       = filtros_dg.getHolderView().findViewById(R.id.cbGpo);
        cbParcial   = filtros_dg.getHolderView().findViewById(R.id.cbParciales);

        aetNombre.setAdapter(adapterNombre);
        aetDia.setAdapter(adapterDia);
        aetColonia.setAdapter(adapterColonia);
        spAsesor.setAdapter(adapterAsesor);

        /**Crea el evento de dropDown en el campo de dia al dar tap sobre el campo*/
        aetDia.setOnTouchListener(new View.OnTouchListener() {
            @SuppressLint("ClickableViewAccessibility")
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                aetDia.showDropDown();
                return false;
            }
        });

        /**Crea el evento de dropDown en el campo de nombre al dar tap sobre el campo*/
        aetNombre.setOnTouchListener(new View.OnTouchListener() {
            @SuppressLint("ClickableViewAccessibility")
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                aetNombre.showDropDown();
                return false;
            }
        });

        /**Crea el evento de dropDown en el campo de colonia al dar tap sobre el campo*/
        aetColonia.setOnTouchListener(new View.OnTouchListener() {
            @SuppressLint("ClickableViewAccessibility")
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                aetColonia.showDropDown();
                return false;
            }
        });


        /**Se obtienen los filtros guardados en variables de sesion*/
        if (!session.getFiltrosCartera().get(2).isEmpty())
            aetNombre.setText(session.getFiltrosCartera().get(2));
        if (!session.getFiltrosCartera().get(3).isEmpty())
            aetDia.setText(session.getFiltrosCartera().get(3));
        if (!session.getFiltrosCartera().get(4).isEmpty())
            aetColonia.setText(session.getFiltrosCartera().get(4));
        if (!session.getFiltrosCartera().get(5).isEmpty())
            spAsesor.setSelection(Integer.parseInt(session.getFiltrosCartera().get(5)));
        if (session.getFiltrosCartera().get(0).equals("1"))
            cbInd.setChecked(true);
        if (session.getFiltrosCartera().get(1).equals("1"))
            cbGpo.setChecked(true);
        if (session.getFiltrosCartera().get(7).equals("1"))
            cbParcial.setChecked(true);
        /**----------------------------------------------------------------------------------*/

        /**Muestra la ventana de filtros*/
        filtros_dg.show();
    }

    /**Funcion para colocar el contador en los menus de filtros y cierre de dia*/
    private void setupBadge() {
        Log.v("contador",session.getFiltrosCartera().get(6));
        if (tvContFiltros != null) {
            Log.e("tvcontador", "visible");
            tvContFiltros.setText(String.valueOf(session.getFiltrosCartera().get(6)));
            tvContFiltros.setVisibility(View.VISIBLE);
        }

        if (tvContCierre != null){
            Cursor row = dBhelper.getRecords(TBL_CIERRE_DIA_T, " WHERE estatus = 0", "", null);
            if (row.getCount() > 0){
                Log.e("Cierre", row.getCount()+" zzz");
                tvContCierre.setText(String.valueOf(row.getCount()));
                tvContCierre.setVisibility(View.VISIBLE);
            }

        }

    }

    /**Funcion para obtener el listado de asesores en la cartera esto es para cuando se comparte cartera
     * y puedan filtrar por asesor*/
    private void GetAsesores (){
        /**Se prepara la consulta a la tablas de TBL_CARTERA_IND_T y TBL_CARTERA_GPO_T que esten en estatus 1*/
        String sql = "SELECT * FROM (SELECT ci.asesor_nombre FROM "+TBL_CARTERA_IND_T + " AS ci WHERE ci.estatus = ? UNION SELECT cg.asesor_nombre FROM " + TBL_CARTERA_GPO_T + " AS cg WHERE cg.estatus = ?) AS asesores ORDER BY asesor_nombre ASC";
        Cursor row = db.rawQuery(sql, new String[]{"1","1"});
        asesor = new ArrayList<>();
        asesor.add("");
        if (row.getCount() > 0){
            row.moveToFirst();
            dataAsesor = new String[row.getCount()];
            for(int i = 0; i < row.getCount(); i++){
                asesor.add(row.getString(0));
                row.moveToNext();
            }
            dataAsesor = Miscellaneous.RemoverRepetidos(asesor);

            adapterAsesor = new ArrayAdapter<>(ctx,
                    R.layout.custom_list_item, R.id.text_view_list_item, dataAsesor);
        }
        else {
            dataAsesor = new String[1];
            dataAsesor[0] = "";
            adapterAsesor = new ArrayAdapter<>(ctx,
                    R.layout.custom_list_item, R.id.text_view_list_item, dataAsesor);
        }
        row.close();
        Log.e("SizeASesores", asesor.size()+" aws");
    }

    /**Metodo que se ejecuta en automatico cada vez que entra a la vista y comienza a obtener
     * los filtros de variables de sesion para continue con el filtro que habia dejado anteriormente*/
    @Override
    public void onResume() {
        super.onResume();
        setupBadge();
        String where = "";

        /**Valida si coloco algun nombre en el filtro*/
        if (!session.getFiltrosCartera().get(2).isEmpty())
            where = " AND nombre LIKE '%" + session.getFiltrosCartera().get(2) + "%'";

        /**Valida si coloco algun dia de la semana en el filtro*/
        if (!session.getFiltrosCartera().get(3).isEmpty()) {
            where += " AND dia LIKE '%" + session.getFiltrosCartera().get(3) + "%'";
        }

        /**Valida si coloco alguna colonia en el filtro*/
        if (!session.getFiltrosCartera().get(4).isEmpty()) {
            where += " AND colonia LIKE '%" + session.getFiltrosCartera().get(4) + "%'";
        }

        //Log.e("SizeAsesires"," tamaño:" + asesor.size());
        /**Valida si coloco algun asesor y que el listado de asesores sea mayor que 0*/
        if (!session.getFiltrosCartera().get(5).isEmpty() && Integer.parseInt(session.getFiltrosCartera().get(5)) > 0 && asesor.size() > 0) {
            where += " AND asesor_nombre LIKE '%" + asesor.get(Integer.parseInt(session.getFiltrosCartera().get(5))) + "%'";
        }

        /**Valida si selecciono grupal e individual en el filtro*/
        if (session.getFiltrosCartera().get(0).equals("1") && session.getFiltrosCartera().get(1).equals("1")){
            where += " AND tipo IN ('INDIVIDUAL','GRUPAL')";
        }
        /**Valida si solo selecciono individual en el filtro*/
        else if (session.getFiltrosCartera().get(0).equals("1")){
            where += " AND tipo = 'INDIVIDUAL' ";
        }
        /**Valida si solo selecciono grupal en el filtro*/
        else if (session.getFiltrosCartera().get(1).equals("1")){
            where += " AND tipo = 'GRUPAL'";
        }
        /**Valida si solo selecciono gestiones parciales en el filtro*/
        if (session.getFiltrosCartera().get(7).equals("1")){
            where += " AND parcial = '0'";
        }

        /**Valida si el tamaño del condicional "where" es mayor que cero para anteponer la
         * palabra reservada WHERE*/
        if (where.length() > 4)
            GetCartera("WHERE" + where.substring(4));
        else
            GetCartera("");
    }
}



