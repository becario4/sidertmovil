package com.sidert.sidertmovil.v2.domain.daos;

import com.sidert.sidertmovil.v2.domain.entities.DatosCreditoCampanaGpoRen;

import java.util.List;
import java.util.Optional;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;
import androidx.room.Delete;

@Dao
public interface DatosCreditoCampanaGpoRenDao {

    @Query("SELECT * FROM tbl_datos_credito_campana_gpo_ren")
    List<DatosCreditoCampanaGpoRen> getAll();

    @Query("SELECT * FROM tbl_datos_credito_campana_gpo_ren t0 WHERE t0._id = :id")
    Optional<DatosCreditoCampanaGpoRen> findById(Long id);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(List<DatosCreditoCampanaGpoRen> entities);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    Long insert(DatosCreditoCampanaGpoRen entity);

    @Update()
    void update(DatosCreditoCampanaGpoRen entity);

    @Delete()
    void delete(DatosCreditoCampanaGpoRen entity);


}

