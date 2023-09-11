package com.sidert.sidertmovil.v2.domain.daos;

import com.sidert.sidertmovil.v2.domain.entities.DatosCreditoCampana;

import java.util.List;
import java.util.Optional;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;
import androidx.room.Delete;

@Dao
public interface DatosCreditoCampanaDao {

    @Query("SELECT * FROM tbl_datos_credito_campana")
    List<DatosCreditoCampana> getAll();

    @Query("SELECT * FROM tbl_datos_credito_campana t0 WHERE t0._id = :id")
    Optional<DatosCreditoCampana> findById(Long id);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(List<DatosCreditoCampana> entities);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    Long insert(DatosCreditoCampana entity);

    @Update()
    void update(DatosCreditoCampana entity);

    @Delete()
    void delete(DatosCreditoCampana entity);


}

