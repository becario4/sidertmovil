package com.sidert.sidertmovil.v2.domain.daos;

import com.sidert.sidertmovil.v2.domain.entities.SolicitudesRen;

import java.util.List;
import java.util.Optional;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;
import androidx.room.Delete;

@Dao
public interface SolicitudesRenDao {

    @Query("SELECT * FROM tbl_solicitudes_ren")
    List<SolicitudesRen> getAll();

    @Query("SELECT * FROM tbl_solicitudes_ren t0 WHERE t0.id_solicitud = :id")
    Optional<SolicitudesRen> findById(Long id);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(List<SolicitudesRen> entities);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    Long insert(SolicitudesRen entity);

    @Update()
    void update(SolicitudesRen entity);

    @Delete()
    void delete(SolicitudesRen entity);

    @Query("SELECT * FROM tbl_solicitudes_ren t0 WHERE t0.nombre = :clienteNombre and t0.prestamo_id = :prestamoId")
    List<SolicitudesRen> findByNombreAndPrestamoId(String clienteNombre, Integer prestamoId);
}

