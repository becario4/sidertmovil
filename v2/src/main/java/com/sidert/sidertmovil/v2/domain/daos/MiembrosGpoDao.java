package com.sidert.sidertmovil.v2.domain.daos;

import com.sidert.sidertmovil.v2.domain.entities.MiembrosGpo;

import java.util.List;
import java.util.Optional;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;
import androidx.room.Delete;

@Dao
public interface MiembrosGpoDao {

    @Query("SELECT * FROM tbl_miembros_gpo_t")
    List<MiembrosGpo> getAll();

    @Query("SELECT * FROM tbl_miembros_gpo_t t0 WHERE t0._id = :id")
    Optional<MiembrosGpo> findById(Long id);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(List<MiembrosGpo> entities);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    Long insert(MiembrosGpo entity);

    @Update()
    void update(MiembrosGpo entity);

    @Delete()
    void delete(MiembrosGpo entity);


}

