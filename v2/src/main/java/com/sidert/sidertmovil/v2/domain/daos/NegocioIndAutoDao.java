package com.sidert.sidertmovil.v2.domain.daos;

import com.sidert.sidertmovil.v2.domain.entities.NegocioIndAuto;

import java.util.List;
import java.util.Optional;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;
import androidx.room.Delete;

@Dao
public interface NegocioIndAutoDao {

    @Query("SELECT * FROM tbl_negocio_ind_auto")
    List<NegocioIndAuto> getAll();

    @Query("SELECT * FROM tbl_negocio_ind_auto t0 WHERE t0.id_negocio = :id")
    Optional<NegocioIndAuto> findById(Long id);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(List<NegocioIndAuto> entities);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    Long insert(NegocioIndAuto entity);

    @Update()
    void update(NegocioIndAuto entity);

    @Delete()
    void delete(NegocioIndAuto entity);


}

