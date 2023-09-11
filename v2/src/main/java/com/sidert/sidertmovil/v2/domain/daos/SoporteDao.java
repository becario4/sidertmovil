package com.sidert.sidertmovil.v2.domain.daos;

import com.sidert.sidertmovil.v2.domain.entities.Soporte;

import java.util.List;
import java.util.Optional;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;
import androidx.room.Delete;

@Dao
public interface SoporteDao {

    @Query("SELECT * FROM tbl_soporte")
    List<Soporte> getAll();

    @Query("SELECT * FROM tbl_soporte t0 WHERE t0._id = :id")
    Optional<Soporte> findById(Long id);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(List<Soporte> entities);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    Long insert(Soporte entity);

    @Update()
    void update(Soporte entity);

    @Delete()
    void delete(Soporte entity);


}

