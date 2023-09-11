package com.sidert.sidertmovil.v2.domain.daos;

import com.sidert.sidertmovil.v2.domain.entities.DatosBeneficiarioInd;

import java.util.List;
import java.util.Optional;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;
import androidx.room.Delete;

@Dao
public interface DatosBeneficiarioIndDao {

    @Query("SELECT * FROM tbl_datos_beneficiario_ind")
    List<DatosBeneficiarioInd> getAll();

    @Query("SELECT * FROM tbl_datos_beneficiario_ind t0 WHERE t0.idBeneficiario = :id")
    Optional<DatosBeneficiarioInd> findById(Long id);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(List<DatosBeneficiarioInd> entities);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    Long insert(DatosBeneficiarioInd entity);

    @Update()
    void update(DatosBeneficiarioInd entity);

    @Delete()
    void delete(DatosBeneficiarioInd entity);


}

