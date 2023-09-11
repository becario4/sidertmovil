package com.sidert.sidertmovil.v2.domain.daos;

import com.sidert.sidertmovil.v2.domain.entities.Prestamos;

import java.util.List;
import java.util.Optional;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;
import androidx.room.Delete;

@Dao
public interface PrestamosDao {

    @Query("SELECT * FROM tbl_prestamos")
    List<Prestamos> getAll();

    @Query("SELECT * FROM tbl_prestamos t0 WHERE t0._id = :id")
    Optional<Prestamos> findById(Long id);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(List<Prestamos> entities);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    Long insert(Prestamos entity);

    @Update()
    void update(Prestamos entity);

    @Delete()
    void delete(Prestamos entity);

    @Query("SELECT * FROM tbl_prestamos t0 WHERE t0.grupo_id = :grupoId and t0.num_solicitud = :numSolicitud")
    Optional<Prestamos> findByGrupoIdAndNumSolicitud(Integer grupoId, Integer numSolicitud);

    @Query("SELECT * FROM tbl_prestamos t0 WHERE t0.cliente_id = :clienteId and t0.num_solicitud = :numSolicitud")
    Optional<Prestamos> findByClienteIdAndNumSolicitud(String clienteId, Integer numSolicitud);
}

