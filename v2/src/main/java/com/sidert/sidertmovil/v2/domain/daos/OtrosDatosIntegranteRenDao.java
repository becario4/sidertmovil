package com.sidert.sidertmovil.v2.domain.daos;

import com.sidert.sidertmovil.v2.domain.entities.OtrosDatosIntegranteRen;

import java.util.List;
import java.util.Optional;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;
import androidx.room.Delete;

@Dao
public interface OtrosDatosIntegranteRenDao {

    @Query("SELECT * FROM tbl_otros_datos_integrante_ren")
    List<OtrosDatosIntegranteRen> getAll();

    @Query("SELECT * FROM tbl_otros_datos_integrante_ren t0 WHERE t0.id_otro = :id")
    Optional<OtrosDatosIntegranteRen> findById(Long id);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(List<OtrosDatosIntegranteRen> entities);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    Long insert(OtrosDatosIntegranteRen entity);

    @Update()
    void update(OtrosDatosIntegranteRen entity);

    @Delete()
    void delete(OtrosDatosIntegranteRen entity);


}

