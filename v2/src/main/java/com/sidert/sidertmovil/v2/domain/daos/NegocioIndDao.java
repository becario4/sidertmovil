package com.sidert.sidertmovil.v2.domain.daos;

import com.sidert.sidertmovil.v2.domain.entities.NegocioInd;

import java.util.List;
import java.util.Optional;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;
import androidx.room.Delete;

@Dao
public interface NegocioIndDao {

    @Query("SELECT * FROM tbl_negocio_ind")
    List<NegocioInd> getAll();

    @Query("SELECT * FROM tbl_negocio_ind t0 WHERE t0.id_negocio = :id")
    Optional<NegocioInd> findById(Long id);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(List<NegocioInd> entities);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    Long insert(NegocioInd entity);

    @Update()
    void update(NegocioInd entity);

    @Delete()
    void delete(NegocioInd entity);


}

