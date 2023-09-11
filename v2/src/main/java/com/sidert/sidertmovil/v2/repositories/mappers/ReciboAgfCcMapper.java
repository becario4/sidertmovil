package com.sidert.sidertmovil.v2.repositories.mappers;


import com.sidert.sidertmovil.v2.domain.entities.RecibosAgfCc;
import com.sidert.sidertmovil.v2.remote.dtos.ReciboResponse;

import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper()
public interface ReciboAgfCcMapper {

    RecibosAgfCc remoteToLocal(ReciboResponse response);

    void updateLocalWithRemote(ReciboResponse remoto, @MappingTarget RecibosAgfCc local);

}
