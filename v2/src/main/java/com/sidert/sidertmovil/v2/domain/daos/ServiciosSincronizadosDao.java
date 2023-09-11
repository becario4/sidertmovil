package com.sidert.sidertmovil.v2.domain.daos;

import com.sidert.sidertmovil.v2.domain.entities.ServiciosSincronizados;

import java.util.List;
import java.util.Optional;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;
import androidx.room.Delete;

@Dao
public interface ServiciosSincronizadosDao {

    @Query("SELECT * FROM tbl_servicios_sincronizados")
    List<ServiciosSincronizados> getAll();

    @Query("SELECT * FROM tbl_servicios_sincronizados t0 WHERE t0._id = :id")
    Optional<ServiciosSincronizados> findById(Long id);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(List<ServiciosSincronizados> entities);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    Long insert(ServiciosSincronizados entity);

    @Update()
    void update(ServiciosSincronizados entity);

    @Delete()
    void delete(ServiciosSincronizados entity);


}

