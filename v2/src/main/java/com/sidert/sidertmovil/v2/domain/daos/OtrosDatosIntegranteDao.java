package com.sidert.sidertmovil.v2.domain.daos;

import com.sidert.sidertmovil.v2.domain.entities.OtrosDatosIntegrante;

import java.util.List;
import java.util.Optional;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;
import androidx.room.Delete;

@Dao
public interface OtrosDatosIntegranteDao {

    @Query("SELECT * FROM tbl_otros_datos_integrante")
    List<OtrosDatosIntegrante> getAll();

    @Query("SELECT * FROM tbl_otros_datos_integrante t0 WHERE t0.id_otro = :id")
    Optional<OtrosDatosIntegrante> findById(Long id);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(List<OtrosDatosIntegrante> entities);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    Long insert(OtrosDatosIntegrante entity);

    @Update()
    void update(OtrosDatosIntegrante entity);

    @Delete()
    void delete(OtrosDatosIntegrante entity);


}

