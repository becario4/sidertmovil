package com.sidert.sidertmovil.models.solicitudes.solicitudgpo.renovacion;

import android.content.Context;

import com.sidert.sidertmovil.models.BaseDao;

public class DocumentoIntegranteRenDao extends BaseDao {

    public DocumentoIntegranteRenDao(Context ctx) {
        super(ctx);
    }

    public DocumentoIntegranteRen findByIdIntegrante(Integer idIntegrante)
    {
        DocumentoIntegranteRen documentoIntegranteRen = new DocumentoIntegranteRen();
        find( findByIdIntegrante(DocumentoIntegranteRen.TBL, idIntegrante), documentoIntegranteRen );
        return documentoIntegranteRen;
    }

}
