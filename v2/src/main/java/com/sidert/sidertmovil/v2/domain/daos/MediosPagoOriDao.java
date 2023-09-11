package com.sidert.sidertmovil.v2.domain.daos;

import com.sidert.sidertmovil.v2.domain.entities.MediosPagoOri;

import java.util.List;
import java.util.Optional;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;
import androidx.room.Delete;

@Dao
public interface MediosPagoOriDao {

    @Query("SELECT * FROM tbl_medios_pago_ori")
    List<MediosPagoOri> getAll();

    @Query("SELECT * FROM tbl_medios_pago_ori t0 WHERE t0._id = :id")
    Optional<MediosPagoOri> findById(Long id);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(List<MediosPagoOri> entities);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    Long insert(MediosPagoOri entity);

    @Update()
    void update(MediosPagoOri entity);

    @Delete()
    void delete(MediosPagoOri entity);


}

