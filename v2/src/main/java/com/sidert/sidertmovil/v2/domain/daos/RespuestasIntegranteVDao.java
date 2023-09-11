package com.sidert.sidertmovil.v2.domain.daos;

import com.sidert.sidertmovil.v2.domain.entities.RespuestasIntegranteV;

import java.util.List;
import java.util.Optional;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;
import androidx.room.Delete;

@Dao
public interface RespuestasIntegranteVDao {

    @Query("SELECT * FROM respuestas_integrante_v_t")
    List<RespuestasIntegranteV> getAll();

    @Query("SELECT * FROM respuestas_integrante_v_t t0 WHERE t0._id = :id")
    Optional<RespuestasIntegranteV> findById(Long id);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(List<RespuestasIntegranteV> entities);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    Long insert(RespuestasIntegranteV entity);

    @Update()
    void update(RespuestasIntegranteV entity);

    @Delete()
    void delete(RespuestasIntegranteV entity);


}

