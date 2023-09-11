package com.sidert.sidertmovil.v2.domain.daos;

import com.sidert.sidertmovil.v2.domain.entities.Ocupaciones;

import java.util.List;
import java.util.Optional;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;
import androidx.room.Delete;

@Dao
public interface OcupacionesDao {

    @Query("SELECT * FROM ocupaciones")
    List<Ocupaciones> getAll();

    @Query("SELECT * FROM ocupaciones t0 WHERE t0._id = :id")
    Optional<Ocupaciones> findById(Long id);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(List<Ocupaciones> entities);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    Long insert(Ocupaciones entity);

    @Update()
    void update(Ocupaciones entity);

    @Delete()
    void delete(Ocupaciones entity);


}

