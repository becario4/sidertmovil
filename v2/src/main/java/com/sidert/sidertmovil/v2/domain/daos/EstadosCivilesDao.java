package com.sidert.sidertmovil.v2.domain.daos;

import com.sidert.sidertmovil.v2.domain.entities.EstadosCiviles;

import java.util.List;
import java.util.Optional;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;
import androidx.room.Delete;

@Dao
public interface EstadosCivilesDao {

    @Query("SELECT * FROM tbl_estados_civiles")
    List<EstadosCiviles> getAll();

    @Query("SELECT * FROM tbl_estados_civiles t0 WHERE t0._id = :id")
    Optional<EstadosCiviles> findById(Long id);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(List<EstadosCiviles> entities);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    Long insert(EstadosCiviles entity);

    @Update()
    void update(EstadosCiviles entity);

    @Delete()
    void delete(EstadosCiviles entity);


}

