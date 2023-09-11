package com.sidert.sidertmovil.v2.domain.daos;

import com.sidert.sidertmovil.v2.domain.entities.DatosCreditoCampanaGpo;

import java.util.List;
import java.util.Optional;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;
import androidx.room.Delete;

@Dao
public interface DatosCreditoCampanaGpoDao {

    @Query("SELECT * FROM tbl_datos_credito_campana_gpo")
    List<DatosCreditoCampanaGpo> getAll();

    @Query("SELECT * FROM tbl_datos_credito_campana_gpo t0 WHERE t0._id = :id")
    Optional<DatosCreditoCampanaGpo> findById(Long id);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(List<DatosCreditoCampanaGpo> entities);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    Long insert(DatosCreditoCampanaGpo entity);

    @Update()
    void update(DatosCreditoCampanaGpo entity);

    @Delete()
    void delete(DatosCreditoCampanaGpo entity);


}

