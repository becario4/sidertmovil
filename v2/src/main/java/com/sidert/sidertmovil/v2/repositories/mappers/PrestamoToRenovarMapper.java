package com.sidert.sidertmovil.v2.repositories.mappers;

import com.sidert.sidertmovil.models.MPrestamosRenovar;
import com.sidert.sidertmovil.v2.domain.entities.PrestamosToRenovar;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import javax.inject.Inject;
import javax.inject.Named;

@Mapper()
public abstract class PrestamoToRenovarMapper {

    protected String asesorId;

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "asesorId", expression = "java(this.asesorId)")
    @Mapping(target = "prestamoId", source = "prestamoId")
    @Mapping(target = "clienteId", source = "clienteId")
    @Mapping(target = "clienteNombre", source = "nombre")
    @Mapping(target = "noPrestamo", expression = "java(prestamosRenovar.getNoPrestamo().trim())")
    @Mapping(target = "fechaVencimiento", source = "fechaVencimiento")
    @Mapping(target = "numPagos", source = "numPagos")
    @Mapping(target = "descargado", expression = "java(0)")
    @Mapping(target = "tipoPrestamo", expression = "java(prestamosRenovar.getTipoPrestamo().equals(\"INDIVIDUAL\") ? 1 : 2)")
    @Mapping(target = "grupoId", source = "grupoId")
    public abstract PrestamosToRenovar remoteToLocal(MPrestamosRenovar prestamosRenovar);

    public String getAsesorId() {
        return asesorId;
    }

    @Inject
    public void setAsesorId(@Named("asesorId") String asesorId) {
        this.asesorId = asesorId;
    }
}
