package com.sidert.sidertmovil.v2.repositories.mappers;

import com.sidert.sidertmovil.v2.domain.entities.*;
import com.sidert.sidertmovil.models.MCatalogo;
import com.sidert.sidertmovil.models.MPlazos;
import com.sidert.sidertmovil.models.MTickets;
import com.sidert.sidertmovil.models.ModeloOcupaciones;
import com.sidert.sidertmovil.models.ModeloSectores;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper()
public interface CatalogoMapper {

    @Mapping(target = "sectorNombre", source = "nombre")
    @Mapping(target = "sectorId", source = "id")
    Sectores sectorRemoteToLocal(ModeloSectores modeloSectores);

    @Mapping(target = "ocupacionNombre", source = "nombre")
    @Mapping(target = "ocupacionId", source = "id")
    @Mapping(target = "ocupacionClave", source = "clave")
    Ocupaciones ocupacionRemoteToLocal(ModeloOcupaciones modeloOcupaciones);

    @Mapping(source = "id", target = "remoteId")
    IdentificacionTipo indentificacionRemoteToLocal(MCatalogo mCatalogo);

    @Mapping(source = "id", target = "remoteId")
    ViviendaTipos viviendaTipoRemoteToLocal(MCatalogo mCatalogo);

    @Mapping(source = "id", target = "remoteId")
    MediosContacto medioContactoRemoteToLocal(MCatalogo mCatalogo);

    @Mapping(source = "id", target = "remoteId")
    DestinosCredito destinoCreditoRemoteToLocal(MCatalogo mCatalogo);

    @Mapping(source = "id", target = "remoteId")
    EstadosCiviles estadoCivilRemoteToLocal(MCatalogo mCatalogo);

    @Mapping(source = "id", target = "remoteId")
    NivelesEstudios nivelEstudioRemoteToLocal(MCatalogo mCatalogo);

    @Mapping(source = "id", target = "remoteId")
    MediosPagoOri medioPagoOriginacionRemoteToLocal(MCatalogo mCatalogo);

    @Mapping(source = "id", target = "remoteId")
    Parentescos parentescoRemoteToLocal(MCatalogo mCatalogo);

    @Mapping(target = "ticketId", source = "id")
    Tickets categoriaTicketRemoteToLocal(MTickets mTickets);

    @Mapping(target = "numeroPlazos", source = "numeroMeses")
    @Mapping(target = "idPlazoPrestamo", source = "id")
    @Mapping(target = "estatus", expression = "java(mPlazos.getEstatus() ? 1 : 2)")
    PlazosPrestamos plazoRemoteToLocal(MPlazos mPlazos);

}
