package com.sidert.sidertmovil.v2.domain.daos;

import com.sidert.sidertmovil.v2.domain.entities.ViviendaTipos;

import java.util.List;
import java.util.Optional;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;
import androidx.room.Delete;

@Dao
public interface ViviendaTiposDao {

    @Query("SELECT * FROM tbl_vivienda_tipos")
    List<ViviendaTipos> getAll();

    @Query("SELECT * FROM tbl_vivienda_tipos t0 WHERE t0._id = :id")
    Optional<ViviendaTipos> findById(Long id);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(List<ViviendaTipos> entities);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    Long insert(ViviendaTipos entity);

    @Update()
    void update(ViviendaTipos entity);

    @Delete()
    void delete(ViviendaTipos entity);


}

