package com.sidert.sidertmovil.v2.domain.daos;

import com.sidert.sidertmovil.v2.domain.entities.ReferenciaInd;

import java.util.List;
import java.util.Optional;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;
import androidx.room.Delete;

@Dao
public interface ReferenciaIndDao {

    @Query("SELECT * FROM tbl_referencia_ind")
    List<ReferenciaInd> getAll();

    @Query("SELECT * FROM tbl_referencia_ind t0 WHERE t0.id_referencia = :id")
    Optional<ReferenciaInd> findById(Long id);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(List<ReferenciaInd> entities);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    Long insert(ReferenciaInd entity);

    @Update()
    void update(ReferenciaInd entity);

    @Delete()
    void delete(ReferenciaInd entity);


}

