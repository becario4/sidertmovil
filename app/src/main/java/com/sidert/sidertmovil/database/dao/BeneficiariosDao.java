package com.sidert.sidertmovil.database.dao;

import com.sidert.sidertmovil.database.entities.Beneficiario;

import java.util.List;
import java.util.Optional;

public interface BeneficiariosDao {

    List<Beneficiario> getAll();

    Optional<Beneficiario> findById(Long id);

    Long insert(Beneficiario entity);

    void update(Beneficiario entity);

    void delete(Beneficiario entity);

    Optional<Beneficiario> findBySolicitudId(Integer solicitudId, Integer integranteId, String tipoSolicitud);

}
