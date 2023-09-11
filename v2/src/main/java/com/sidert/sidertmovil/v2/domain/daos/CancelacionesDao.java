package com.sidert.sidertmovil.v2.domain.daos;

import com.sidert.sidertmovil.v2.domain.entities.Cancelaciones;

import java.util.List;
import java.util.Optional;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;
import androidx.room.Delete;

@Dao
public interface CancelacionesDao {

    @Query("SELECT * FROM tbl_cancelaciones")
    List<Cancelaciones> getAll();

    @Query("SELECT * FROM tbl_cancelaciones t0 WHERE t0._id = :id")
    Optional<Cancelaciones> findById(Long id);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(List<Cancelaciones> entities);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    Long insert(Cancelaciones entity);

    @Update()
    void update(Cancelaciones entity);

    @Delete()
    void delete(Cancelaciones entity);


}

