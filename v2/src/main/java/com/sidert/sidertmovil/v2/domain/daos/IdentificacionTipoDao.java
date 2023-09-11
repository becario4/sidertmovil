package com.sidert.sidertmovil.v2.domain.daos;

import com.sidert.sidertmovil.v2.domain.entities.IdentificacionTipo;

import java.util.List;
import java.util.Optional;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;
import androidx.room.Delete;

@Dao
public interface IdentificacionTipoDao {

    @Query("SELECT * FROM tbl_identificacion_tipo")
    List<IdentificacionTipo> getAll();

    @Query("SELECT * FROM tbl_identificacion_tipo t0 WHERE t0._id = :id")
    Optional<IdentificacionTipo> findById(Long id);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(List<IdentificacionTipo> entities);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    Long insert(IdentificacionTipo entity);

    @Update()
    void update(IdentificacionTipo entity);

    @Delete()
    void delete(IdentificacionTipo entity);


}

