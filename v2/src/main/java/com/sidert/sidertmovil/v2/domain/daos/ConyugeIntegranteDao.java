package com.sidert.sidertmovil.v2.domain.daos;

import com.sidert.sidertmovil.v2.domain.entities.ConyugeIntegrante;

import java.util.List;
import java.util.Optional;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;
import androidx.room.Delete;

@Dao
public interface ConyugeIntegranteDao {

    @Query("SELECT * FROM tbl_conyuge_integrante")
    List<ConyugeIntegrante> getAll();

    @Query("SELECT * FROM tbl_conyuge_integrante t0 WHERE t0.id_conyuge = :id")
    Optional<ConyugeIntegrante> findById(Long id);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(List<ConyugeIntegrante> entities);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    Long insert(ConyugeIntegrante entity);

    @Update()
    void update(ConyugeIntegrante entity);

    @Delete()
    void delete(ConyugeIntegrante entity);


}

