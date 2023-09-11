package com.sidert.sidertmovil.v2.domain.daos;

import com.sidert.sidertmovil.v2.domain.entities.DomicilioIntegranteRen;

import java.util.List;
import java.util.Optional;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;
import androidx.room.Delete;

@Dao
public interface DomicilioIntegranteRenDao {

    @Query("SELECT * FROM tbl_domicilio_integrante_ren")
    List<DomicilioIntegranteRen> getAll();

    @Query("SELECT * FROM tbl_domicilio_integrante_ren t0 WHERE t0.id_domicilio = :id")
    Optional<DomicilioIntegranteRen> findById(Long id);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(List<DomicilioIntegranteRen> entities);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    Long insert(DomicilioIntegranteRen entity);

    @Update()
    void update(DomicilioIntegranteRen entity);

    @Delete()
    void delete(DomicilioIntegranteRen entity);


}

