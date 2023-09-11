package com.sidert.sidertmovil.v2.domain.daos;

import com.sidert.sidertmovil.v2.domain.entities.TelefonosIntegranteAuto;

import java.util.List;
import java.util.Optional;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;
import androidx.room.Delete;

@Dao
public interface TelefonosIntegranteAutoDao {

    @Query("SELECT * FROM tbl_telefonos_integrante_auto")
    List<TelefonosIntegranteAuto> getAll();

    @Query("SELECT * FROM tbl_telefonos_integrante_auto t0 WHERE t0.id_telefonico = :id")
    Optional<TelefonosIntegranteAuto> findById(Long id);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(List<TelefonosIntegranteAuto> entities);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    Long insert(TelefonosIntegranteAuto entity);

    @Update()
    void update(TelefonosIntegranteAuto entity);

    @Delete()
    void delete(TelefonosIntegranteAuto entity);


}

