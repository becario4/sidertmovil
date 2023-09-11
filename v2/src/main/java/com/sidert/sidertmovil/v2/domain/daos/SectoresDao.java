package com.sidert.sidertmovil.v2.domain.daos;

import com.sidert.sidertmovil.v2.domain.entities.Sectores;

import java.util.List;
import java.util.Optional;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;
import androidx.room.Delete;

@Dao
public interface SectoresDao {

    @Query("SELECT * FROM sectores")
    List<Sectores> getAll();

    @Query("SELECT * FROM sectores t0 WHERE t0._id = :id")
    Optional<Sectores> findById(Long id);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(List<Sectores> entities);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    Long insert(Sectores entity);

    @Update()
    void update(Sectores entity);

    @Delete()
    void delete(Sectores entity);


}

