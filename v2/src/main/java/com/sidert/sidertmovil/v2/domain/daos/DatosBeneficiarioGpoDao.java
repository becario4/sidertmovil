package com.sidert.sidertmovil.v2.domain.daos;

import com.sidert.sidertmovil.v2.domain.entities.DatosBeneficiarioGpo;

import java.util.List;
import java.util.Optional;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;
import androidx.room.Delete;

@Dao
public interface DatosBeneficiarioGpoDao {

    @Query("SELECT * FROM tbl_datos_beneficiario_gpo")
    List<DatosBeneficiarioGpo> getAll();

    @Query("SELECT * FROM tbl_datos_beneficiario_gpo t0 WHERE t0.idBeneficiario = :id")
    Optional<DatosBeneficiarioGpo> findById(Long id);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(List<DatosBeneficiarioGpo> entities);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    Long insert(DatosBeneficiarioGpo entity);

    @Update()
    void update(DatosBeneficiarioGpo entity);

    @Delete()
    void delete(DatosBeneficiarioGpo entity);


}

