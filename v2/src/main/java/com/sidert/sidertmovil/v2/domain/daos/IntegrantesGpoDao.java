package com.sidert.sidertmovil.v2.domain.daos;

import com.sidert.sidertmovil.v2.domain.entities.IntegrantesGpo;

import java.util.List;
import java.util.Optional;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;
import androidx.room.Delete;

@Dao
public interface IntegrantesGpoDao {

    @Query("SELECT * FROM tbl_integrantes_gpo")
    List<IntegrantesGpo> getAll();

    @Query("SELECT * FROM tbl_integrantes_gpo t0 WHERE t0.id = :id")
    Optional<IntegrantesGpo> findById(Long id);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(List<IntegrantesGpo> entities);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    Long insert(IntegrantesGpo entity);

    @Update()
    void update(IntegrantesGpo entity);

    @Delete()
    void delete(IntegrantesGpo entity);


}

