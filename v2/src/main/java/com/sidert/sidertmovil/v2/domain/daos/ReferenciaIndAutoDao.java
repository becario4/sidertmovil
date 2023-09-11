package com.sidert.sidertmovil.v2.domain.daos;

import com.sidert.sidertmovil.v2.domain.entities.ReferenciaIndAuto;

import java.util.List;
import java.util.Optional;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;
import androidx.room.Delete;

@Dao
public interface ReferenciaIndAutoDao {

    @Query("SELECT * FROM tbl_referencia_ind_auto")
    List<ReferenciaIndAuto> getAll();

    @Query("SELECT * FROM tbl_referencia_ind_auto t0 WHERE t0.id_referencia = :id")
    Optional<ReferenciaIndAuto> findById(Long id);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(List<ReferenciaIndAuto> entities);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    Long insert(ReferenciaIndAuto entity);

    @Update()
    void update(ReferenciaIndAuto entity);

    @Delete()
    void delete(ReferenciaIndAuto entity);


}

