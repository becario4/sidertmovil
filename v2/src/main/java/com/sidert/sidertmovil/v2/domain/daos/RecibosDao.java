package com.sidert.sidertmovil.v2.domain.daos;

import com.sidert.sidertmovil.v2.domain.entities.Recibos;

import java.util.List;
import java.util.Optional;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;
import androidx.room.Delete;

@Dao
public interface RecibosDao {

    @Query("SELECT * FROM tbl_recibos")
    List<Recibos> getAll();

    @Query("SELECT * FROM tbl_recibos t0 WHERE t0._id = :id")
    Optional<Recibos> findById(Long id);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(List<Recibos> entities);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    Long insert(Recibos entity);

    @Update()
    void update(Recibos entity);

    @Delete()
    void delete(Recibos entity);


    @Query("SELECT t0.* FROM tbl_recibos t0 INNER JOIN tbl_prestamos p0 ON t0.prestamo_id = p0._id WHERE p0.grupo_id = :grupoId and p0.num_solicitud = :numSolicitud ORDER BY t0._id desc limit 1")
    Optional<Recibos> findByGrupoIdAndNumSolicitud(Integer grupoId, Integer numSolicitud);

    @Query("SELECT t0.* FROM tbl_recibos t0 INNER JOIN tbl_prestamos p0 ON t0.prestamo_id = p0._id WHERE p0.cliente_id = :clienteId and p0.num_solicitud = :numSolicitud ORDER BY t0._id desc limit 1")
    Optional<Recibos> findByClienteIdAndNumSolicitud(String clienteId, Integer numSolicitud);
}

