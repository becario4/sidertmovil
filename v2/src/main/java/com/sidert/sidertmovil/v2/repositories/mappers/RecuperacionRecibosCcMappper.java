package com.sidert.sidertmovil.v2.repositories.mappers;

import com.sidert.sidertmovil.models.circulocredito.CirculoCredito;
import com.sidert.sidertmovil.utils.Miscellaneous;
import com.sidert.sidertmovil.v2.domain.entities.RecibosCc;
import com.sidert.sidertmovil.v2.domain.entities.RecuperacionRecibosCc;

import org.mapstruct.Mapper;


@Mapper(
        imports = {
                com.sidert.sidertmovil.utils.Miscellaneous.class
        }
)
public abstract class RecuperacionRecibosCcMappper {

    public RecuperacionRecibosCc remoteToLocalRecuperacion(CirculoCredito response) {
        RecuperacionRecibosCc gestionCc = new RecuperacionRecibosCc();
        gestionCc.setTipoCredito((response.getProducto().equals("CREDITO INDIVIDUAL")) ? 1 : 2);
        gestionCc.setNombreUno(response.getClienteGrupo());
        gestionCc.setCurp(response.getCurp());
        gestionCc.setNombreDos(response.getAvalRepresentante());
        gestionCc.setIntegrantes(response.getIntegrantes());
        gestionCc.setMonto(response.getMonto());
        gestionCc.setMedioPago(Miscellaneous.GetMedioPago(response.getMedioPagoId()));

        String folioManual = response.processFolioManual();
        String[] folio = response.processFolio();

        if (folioManual != null) {
            gestionCc.setImprimirRecibo("NO");
            gestionCc.setFolio(Integer.parseInt(folioManual));
        }

        if (folio != null) {
            gestionCc.setImprimirRecibo("SI");
            gestionCc.setFolio(Integer.parseInt(folio[2]));
        }

        if (folioManual == null && folio == null) {
            gestionCc.setImprimirRecibo("NO");
            gestionCc.setFolio(0);
        }

        gestionCc.setEvidencia("");
        gestionCc.setTipoImagen("");
        gestionCc.setFechaTermino("");
        gestionCc.setFechaEnvio(response.getFechaEnvio().substring(0, 19).replace("T", " "));
        gestionCc.setEstatus(2);
        gestionCc.setCostoConsulta("");

        double costoConsulta = Double.parseDouble(gestionCc.getMonto());
        int integrantes = gestionCc.getIntegrantes() == null ? 0 : gestionCc.getIntegrantes();

        if (integrantes > 0 && costoConsulta > 0) {
            costoConsulta = costoConsulta / gestionCc.getIntegrantes();

            if (costoConsulta > 20) {
                gestionCc.setCostoConsulta("22.50");
            } else {
                gestionCc.setCostoConsulta("17.50");
            }
        }

        if (integrantes == 0) {
            if (costoConsulta > 20) {
                gestionCc.setCostoConsulta("22.50");
            } else {
                gestionCc.setCostoConsulta("17.50");
            }
        }

        return gestionCc;
    }

    public RecibosCc remoteToLocalRecuperacionLast(CirculoCredito response) {
        String[] folio = response.processFolio();
        RecibosCc lastReciboCC = new RecibosCc();
        lastReciboCC.setTipoCredito((response.getProducto().equals("CREDITO INDIVIDUAL")) ? 1 : 2);
        lastReciboCC.setNombreUno(response.getClienteGrupo());
        lastReciboCC.setCurp(response.getCurp());
        lastReciboCC.setNombreDos(response.getAvalRepresentante());
        lastReciboCC.setTotalIntegrantes(response.getIntegrantes());
        lastReciboCC.setMonto(response.getMonto());
        lastReciboCC.setTipoImpresion(response.getTipoImpresion());
        lastReciboCC.setFolio(Integer.parseInt(folio[2]));
        lastReciboCC.setFechaImpresion(response.getFechaImpreso().substring(0, 19).replace("T", " "));
        lastReciboCC.setFechaEnvio(response.getFechaEnvio().substring(0, 19).replace("T", " "));
        lastReciboCC.setEstatus(1);
        return lastReciboCC;
    }

}
