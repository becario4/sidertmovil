package com.sidert.sidertmovil.models.solicitudes.solicitudind.originacion;

import static com.sidert.sidertmovil.utils.Constants.TBL_CLIENTE_IND;
import static com.sidert.sidertmovil.utils.Constants.TBL_DATOS_BENEFICIARIO;
import static com.sidert.sidertmovil.utils.Constants.TBL_PRESTAMOS_IND_T;
import static com.sidert.sidertmovil.utils.Constants.TBL_SOLICITUDES;
import static com.sidert.sidertmovil.utils.Constants.TBL_TRACKER_ASESOR_T;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.sidert.sidertmovil.database.DBhelper;
import com.sidert.sidertmovil.database.SidertTables;
import com.sidert.sidertmovil.models.BaseDao;

import java.util.ArrayList;
import java.util.List;

public class BeneficiarioDao  extends BaseDao {

    private static DBhelper dBhelper1;
    private static SQLiteDatabase db1;

    public BeneficiarioDao(Context ctx){super(ctx);}

    public Beneficiario findByIdSolicitud(Integer idSolicitud){
        Beneficiario beneficiario = new Beneficiario();
        find(findByIdSolicitud(Beneficiario.TBL, idSolicitud), beneficiario);
        return beneficiario;
    }

    public static int obtenerSerieAsesor(Context ctx){

        dBhelper1 = new DBhelper(ctx);
        db1 = dBhelper1.getWritableDatabase();

        int serie_id = 0;

        String sql = " SELECT serie_id " + " FROM " + TBL_TRACKER_ASESOR_T + " WHERE _id=1";

        Cursor row = db1.rawQuery(sql,null);

        if(row.getCount()>0){
            row.moveToFirst();
            serie_id = row.getInt(0);
            row.close();
        }
        return serie_id;
    }

    public static int obtenerClienteInd(int id_solicitud,Context ctx){
        dBhelper1 = new DBhelper(ctx);
        db1 = dBhelper1.getWritableDatabase();
        int id_cliente = 0;

        String sql = " SELECT id_cliente FROM " + TBL_CLIENTE_IND + " WHERE id_solicitud=?";

        Cursor row = db1.rawQuery( sql, new String[]{String.valueOf(id_solicitud)});

        if(row != null && row.getCount()>0){
            row.moveToFirst();
            id_cliente = row.getInt(0);
            row.close();
        }
        return id_cliente;
    }


    public static boolean validarBeneficiarioInd(int id_solicitud,Context ctx){
        dBhelper1 = new DBhelper(ctx);
        db1 = dBhelper1.getWritableDatabase();
        boolean estatus = false;
        int dato = 0;

        String sql = " SELECT COUNT(*) FROM " + TBL_DATOS_BENEFICIARIO + " WHERE id_solicitud=?";

        Cursor row = db1.rawQuery(sql,new String[]{String.valueOf(id_solicitud)});

        if(row.getCount()>0){
            row.moveToFirst();
            dato = row.getInt(0);
            row.close();
        }

        if(dato >= 1){
            estatus = true;
        }
        return estatus;
    }

    public static int obtenerIdOriginacion(int id_solicitud, Context ctx){
        dBhelper1 = new DBhelper(ctx);
        db1 = dBhelper1.getWritableDatabase();

        int id_originacion = 0;

        String sql = " SELECT id_originacion FROM " + TBL_SOLICITUDES + " WHERE id_solicitud = ?";

        Cursor row = db1.rawQuery(sql, new String[]{String.valueOf(id_solicitud)});

        if(row.getCount()>0){
            row.moveToFirst();
            id_originacion = row.getInt(0);
            row.close();
        }
        return id_originacion;
    }

    public Beneficiario getBeneficiario(int id_solicitud, int id_originacion, Context ctx){
        dBhelper1 = new DBhelper(ctx);
        db1 = dBhelper1.getWritableDatabase();

        Beneficiario beneficiario = null;

        String sql = " SELECT * FROM " + TBL_DATOS_BENEFICIARIO + " WHERE id_solicitud=? AND id_originacion=?";

        Cursor row = db1.rawQuery(sql, new String[]{String.valueOf(id_solicitud),String.valueOf(id_originacion)});

        if(row.getCount()>0){
            row.moveToFirst();

            beneficiario = new Beneficiario();

            Fill(row, beneficiario);
        }

        row.close();

        return beneficiario;
    }

    private void Fill(Cursor row, Beneficiario beneficiario){
        beneficiario.setIdBeneficiario(row.getInt(0));
        beneficiario.setIdSolicitud(row.getInt(1));
        beneficiario.setId_originacion(row.getInt(2));
        beneficiario.setCliente_id(row.getInt(3));
        beneficiario.setGrupo_id(row.getInt(4));
        beneficiario.setNombre(row.getString(5));
        beneficiario.setPaterno(row.getString(6));
        beneficiario.setMaterno(row.getString(7));
        beneficiario.setParentesco(row.getString(8));
        beneficiario.setSerieId(row.getInt(9));

    }




}
