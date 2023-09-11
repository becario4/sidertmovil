package com.sidert.sidertmovil.v2.domain.daos;

import com.sidert.sidertmovil.v2.domain.entities.IntegrantesGpoAuto;

import java.util.List;
import java.util.Optional;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;
import androidx.room.Delete;

@Dao
public interface IntegrantesGpoAutoDao {

    @Query("SELECT * FROM tbl_integrantes_gpo_auto")
    List<IntegrantesGpoAuto> getAll();

    @Query("SELECT * FROM tbl_integrantes_gpo_auto t0 WHERE t0.id = :id")
    Optional<IntegrantesGpoAuto> findById(Long id);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(List<IntegrantesGpoAuto> entities);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    Long insert(IntegrantesGpoAuto entity);

    @Update()
    void update(IntegrantesGpoAuto entity);

    @Delete()
    void delete(IntegrantesGpoAuto entity);


}

