package com.sidert.sidertmovil.v2.domain.daos;

import com.sidert.sidertmovil.v2.domain.entities.ArqueoCaja;

import java.util.List;
import java.util.Optional;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;
import androidx.room.Delete;

@Dao
public interface ArqueoCajaDao {

    @Query("SELECT * FROM tbl_arqueo_caja_t")
    List<ArqueoCaja> getAll();

    @Query("SELECT * FROM tbl_arqueo_caja_t t0 WHERE t0._id = :id")
    Optional<ArqueoCaja> findById(Long id);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(List<ArqueoCaja> entities);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    Long insert(ArqueoCaja entity);

    @Update()
    void update(ArqueoCaja entity);

    @Delete()
    void delete(ArqueoCaja entity);


}

