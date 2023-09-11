package com.sidert.sidertmovil.v2.domain.daos;

import com.sidert.sidertmovil.v2.domain.entities.DireccionesRen;

import java.util.List;
import java.util.Optional;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;
import androidx.room.Delete;

@Dao
public interface DireccionesRenDao {

    @Query("SELECT * FROM tbl_direcciones_ren")
    List<DireccionesRen> getAll();

    @Query("SELECT * FROM tbl_direcciones_ren t0 WHERE t0.id_direccion = :id")
    Optional<DireccionesRen> findById(Long id);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(List<DireccionesRen> entities);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    Long insert(DireccionesRen entity);

    @Update()
    void update(DireccionesRen entity);

    @Delete()
    void delete(DireccionesRen entity);


}

