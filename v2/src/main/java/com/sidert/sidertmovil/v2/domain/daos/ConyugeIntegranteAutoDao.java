package com.sidert.sidertmovil.v2.domain.daos;

import com.sidert.sidertmovil.v2.domain.entities.ConyugeIntegranteAuto;

import java.util.List;
import java.util.Optional;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;
import androidx.room.Delete;

@Dao
public interface ConyugeIntegranteAutoDao {

    @Query("SELECT * FROM tbl_conyuge_integrante_auto")
    List<ConyugeIntegranteAuto> getAll();

    @Query("SELECT * FROM tbl_conyuge_integrante_auto t0 WHERE t0.id_conyuge = :id")
    Optional<ConyugeIntegranteAuto> findById(Long id);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(List<ConyugeIntegranteAuto> entities);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    Long insert(ConyugeIntegranteAuto entity);

    @Update()
    void update(ConyugeIntegranteAuto entity);

    @Delete()
    void delete(ConyugeIntegranteAuto entity);


}

