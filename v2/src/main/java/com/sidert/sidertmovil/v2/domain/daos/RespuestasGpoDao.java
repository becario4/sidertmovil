package com.sidert.sidertmovil.v2.domain.daos;

import com.sidert.sidertmovil.v2.domain.entities.RespuestasGpo;

import java.util.List;
import java.util.Optional;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;
import androidx.room.Delete;

@Dao
public interface RespuestasGpoDao {

    @Query("SELECT * FROM respuestas_gpo_t")
    List<RespuestasGpo> getAll();

    @Query("SELECT * FROM respuestas_gpo_t t0 WHERE t0._id = :id")
    Optional<RespuestasGpo> findById(Long id);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(List<RespuestasGpo> entities);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    Long insert(RespuestasGpo entity);

    @Update()
    void update(RespuestasGpo entity);

    @Delete()
    void delete(RespuestasGpo entity);


}

