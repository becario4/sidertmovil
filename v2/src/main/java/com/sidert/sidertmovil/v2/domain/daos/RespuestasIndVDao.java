package com.sidert.sidertmovil.v2.domain.daos;

import com.sidert.sidertmovil.v2.domain.entities.RespuestasIndV;

import java.util.List;
import java.util.Optional;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;
import androidx.room.Delete;

@Dao
public interface RespuestasIndVDao {

    @Query("SELECT * FROM respuestas_ind_v_t")
    List<RespuestasIndV> getAll();

    @Query("SELECT * FROM respuestas_ind_v_t t0 WHERE t0._id = :id")
    Optional<RespuestasIndV> findById(Long id);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(List<RespuestasIndV> entities);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    Long insert(RespuestasIndV entity);

    @Update()
    void update(RespuestasIndV entity);

    @Delete()
    void delete(RespuestasIndV entity);


}

