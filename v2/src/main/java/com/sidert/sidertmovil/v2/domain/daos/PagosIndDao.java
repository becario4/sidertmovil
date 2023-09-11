package com.sidert.sidertmovil.v2.domain.daos;

import com.sidert.sidertmovil.v2.domain.entities.PagosInd;

import java.util.List;
import java.util.Optional;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;
import androidx.room.Delete;

@Dao
public interface PagosIndDao {

    @Query("SELECT * FROM tbl_pagos_ind_t")
    List<PagosInd> getAll();

    @Query("SELECT * FROM tbl_pagos_ind_t t0 WHERE t0._id = :id")
    Optional<PagosInd> findById(Long id);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(List<PagosInd> entities);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    Long insert(PagosInd entity);

    @Update()
    void update(PagosInd entity);

    @Delete()
    void delete(PagosInd entity);


}

