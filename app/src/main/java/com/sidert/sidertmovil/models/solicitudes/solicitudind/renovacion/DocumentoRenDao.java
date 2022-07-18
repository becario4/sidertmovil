package com.sidert.sidertmovil.models.solicitudes.solicitudind.renovacion;

import android.content.Context;

import com.sidert.sidertmovil.models.BaseDao;

public class DocumentoRenDao extends BaseDao {

    public DocumentoRenDao(Context ctx) {
        super(ctx);
    }

    public DocumentoRen findByIdSolicitud(Integer idSolicitud)
    {
        DocumentoRen documentoRen = new DocumentoRen();
        find( findByIdSolicitud(DocumentoRen.TBL, idSolicitud), documentoRen );
        return documentoRen;
    }

}