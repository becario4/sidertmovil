package com.sidert.sidertmovil.models.solicitudes.solicitudgpo.originacion;

import android.content.Context;

import com.sidert.sidertmovil.models.BaseDao;

public class DocumentoIntegranteDao extends BaseDao {

    public DocumentoIntegranteDao(Context ctx) {
        super(ctx);
    }

    public DocumentoIntegrante findByIdIntegrante(Integer idIntegrante)
    {
        DocumentoIntegrante documentoIntegrante = new DocumentoIntegrante();
        find( findByIdIntegrante(DocumentoIntegrante.TBL, idIntegrante), documentoIntegrante );
        return documentoIntegrante;
    }
}
