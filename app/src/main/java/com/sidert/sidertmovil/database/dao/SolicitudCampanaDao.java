package com.sidert.sidertmovil.database.dao;

import com.sidert.sidertmovil.database.entities.SolicitudCampana;

import java.util.List;
import java.util.Optional;

public interface SolicitudCampanaDao {

    List<SolicitudCampana> getAll();

    Optional<SolicitudCampana> findById(Long id);

    void insertAll(List<SolicitudCampana> entities);

    Long insert(SolicitudCampana entity);

    void update(SolicitudCampana entity);

    void delete(SolicitudCampana entity);

    Optional<SolicitudCampana> findBySolicitudId(Integer solicitudId, Integer integranteId, String tipoSolicitud);


}
