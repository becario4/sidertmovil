package com.sidert.sidertmovil.v2.domain.daos;

import com.sidert.sidertmovil.v2.domain.entities.RecuperacionRecibosCc;

import java.util.List;
import java.util.Optional;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;
import androidx.room.Delete;

@Dao
public interface RecuperacionRecibosCcDao {

    @Query("SELECT * FROM tbl_recuperacion_recibos_cc")
    List<RecuperacionRecibosCc> getAll();

    @Query("SELECT * FROM tbl_recuperacion_recibos_cc t0 WHERE t0._id = :id")
    Optional<RecuperacionRecibosCc> findById(Long id);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(List<RecuperacionRecibosCc> entities);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    Long insert(RecuperacionRecibosCc entity);

    @Update()
    void update(RecuperacionRecibosCc entity);

    @Delete()
    void delete(RecuperacionRecibosCc entity);

    @Query("SELECT * FROM tbl_recuperacion_recibos_cc t0 WHERE t0.curp = :curp")
    Optional<RecuperacionRecibosCc> findByCurp(String curp);
}

