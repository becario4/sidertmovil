package com.sidert.sidertmovil.v2.domain.daos;

import com.sidert.sidertmovil.v2.domain.entities.CentroCosto;

import java.util.List;
import java.util.Optional;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;
import androidx.room.Delete;

@Dao
public interface CentroCostoDao {

    @Query("SELECT * FROM tbl_centro_costo")
    List<CentroCosto> getAll();

    @Query("SELECT * FROM tbl_centro_costo t0 WHERE t0.id = :id")
    Optional<CentroCosto> findById(Long id);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(List<CentroCosto> entities);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    Long insert(CentroCosto entity);

    @Update()
    void update(CentroCosto entity);

    @Delete()
    void delete(CentroCosto entity);


}

