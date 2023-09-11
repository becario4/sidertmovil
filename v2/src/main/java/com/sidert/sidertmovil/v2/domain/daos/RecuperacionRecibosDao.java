package com.sidert.sidertmovil.v2.domain.daos;

import com.sidert.sidertmovil.v2.domain.entities.RecuperacionRecibos;

import java.util.List;
import java.util.Optional;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;
import androidx.room.Delete;

@Dao
public interface RecuperacionRecibosDao {

    @Query("SELECT * FROM tbl_recuperacion_recibos")
    List<RecuperacionRecibos> getAll();

    @Query("SELECT * FROM tbl_recuperacion_recibos t0 WHERE t0._id = :id")
    Optional<RecuperacionRecibos> findById(Long id);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(List<RecuperacionRecibos> entities);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    Long insert(RecuperacionRecibos entity);

    @Update()
    void update(RecuperacionRecibos entity);

    @Delete()
    void delete(RecuperacionRecibos entity);


}

