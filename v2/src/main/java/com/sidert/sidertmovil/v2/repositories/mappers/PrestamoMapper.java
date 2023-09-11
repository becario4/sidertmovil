package com.sidert.sidertmovil.v2.repositories.mappers;


import com.sidert.sidertmovil.v2.domain.entities.Prestamos;
import com.sidert.sidertmovil.v2.remote.dtos.PrestamoResponse;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper()
public interface PrestamoMapper {

    @Mapping(target = "estadoNacimiento", source = "estadoNacimientoId")
    Prestamos remoteToLocal(PrestamoResponse prestamo);

    @Mapping(target = "estadoNacimiento", source = "estadoNacimientoId")
    void updateLocalWithRemote(PrestamoResponse remoto, @MappingTarget Prestamos local);

}
