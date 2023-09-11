package com.sidert.sidertmovil.v2.domain.daos;

import com.sidert.sidertmovil.v2.domain.entities.CatalogoCampanas;

import java.util.List;
import java.util.Optional;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;
import androidx.room.Delete;

@Dao
public interface CatalogoCampanasDao {

    @Query("SELECT * FROM tbl_catalogo_campanas")
    List<CatalogoCampanas> getAll();

    @Query("SELECT * FROM tbl_catalogo_campanas t0 WHERE t0._id = :id")
    Optional<CatalogoCampanas> findById(Long id);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(List<CatalogoCampanas> entities);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    Long insert(CatalogoCampanas entity);

    @Update()
    void update(CatalogoCampanas entity);

    @Delete()
    void delete(CatalogoCampanas entity);


}

