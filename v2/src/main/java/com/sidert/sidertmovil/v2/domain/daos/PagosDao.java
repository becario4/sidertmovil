package com.sidert.sidertmovil.v2.domain.daos;

import com.sidert.sidertmovil.v2.domain.entities.Pagos;

import java.util.List;
import java.util.Optional;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;
import androidx.room.Delete;

@Dao
public interface PagosDao {

    @Query("SELECT * FROM tbl_pagos_t")
    List<Pagos> getAll();

    @Query("SELECT * FROM tbl_pagos_t t0 WHERE t0._id = :id")
    Optional<Pagos> findById(Long id);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(List<Pagos> entities);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    Long insert(Pagos entity);

    @Update()
    void update(Pagos entity);

    @Delete()
    void delete(Pagos entity);


}

