package com.sidert.sidertmovil.v2.domain.daos;

import com.sidert.sidertmovil.v2.domain.entities.ConsultaCc;

import java.util.List;
import java.util.Optional;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;
import androidx.room.Delete;

@Dao
public interface ConsultaCcDao {

    @Query("SELECT * FROM tbl_consulta_cc")
    List<ConsultaCc> getAll();

    @Query("SELECT * FROM tbl_consulta_cc t0 WHERE t0._id = :id")
    Optional<ConsultaCc> findById(Long id);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(List<ConsultaCc> entities);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    Long insert(ConsultaCc entity);

    @Update()
    void update(ConsultaCc entity);

    @Delete()
    void delete(ConsultaCc entity);


}

