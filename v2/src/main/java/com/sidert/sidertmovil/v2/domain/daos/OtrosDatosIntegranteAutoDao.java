package com.sidert.sidertmovil.v2.domain.daos;

import com.sidert.sidertmovil.v2.domain.entities.OtrosDatosIntegranteAuto;

import java.util.List;
import java.util.Optional;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;
import androidx.room.Delete;

@Dao
public interface OtrosDatosIntegranteAutoDao {

    @Query("SELECT * FROM tbl_otros_datos_integrante_auto")
    List<OtrosDatosIntegranteAuto> getAll();

    @Query("SELECT * FROM tbl_otros_datos_integrante_auto t0 WHERE t0.id_otro = :id")
    Optional<OtrosDatosIntegranteAuto> findById(Long id);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(List<OtrosDatosIntegranteAuto> entities);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    Long insert(OtrosDatosIntegranteAuto entity);

    @Update()
    void update(OtrosDatosIntegranteAuto entity);

    @Delete()
    void delete(OtrosDatosIntegranteAuto entity);


}

