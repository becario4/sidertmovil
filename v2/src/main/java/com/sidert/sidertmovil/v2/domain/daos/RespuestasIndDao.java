package com.sidert.sidertmovil.v2.domain.daos;

import com.sidert.sidertmovil.v2.domain.entities.RespuestasInd;

import java.util.List;
import java.util.Optional;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;
import androidx.room.Delete;

@Dao
public interface RespuestasIndDao {

    @Query("SELECT * FROM respuestas_ind_t")
    List<RespuestasInd> getAll();

    @Query("SELECT * FROM respuestas_ind_t t0 WHERE t0._id = :id")
    Optional<RespuestasInd> findById(Long id);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(List<RespuestasInd> entities);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    Long insert(RespuestasInd entity);

    @Update()
    void update(RespuestasInd entity);

    @Delete()
    void delete(RespuestasInd entity);


}

