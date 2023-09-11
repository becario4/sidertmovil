package com.sidert.sidertmovil.v2.domain.daos;

import com.sidert.sidertmovil.v2.domain.entities.DatosBeneficiarioIndRen;

import java.util.List;
import java.util.Optional;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;
import androidx.room.Delete;

@Dao
public interface DatosBeneficiarioIndRenDao {

    @Query("SELECT * FROM tbl_datos_beneficiario_ind_ren")
    List<DatosBeneficiarioIndRen> getAll();

    @Query("SELECT * FROM tbl_datos_beneficiario_ind_ren t0 WHERE t0.idBeneficiario = :id")
    Optional<DatosBeneficiarioIndRen> findById(Long id);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(List<DatosBeneficiarioIndRen> entities);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    Long insert(DatosBeneficiarioIndRen entity);

    @Update()
    void update(DatosBeneficiarioIndRen entity);

    @Delete()
    void delete(DatosBeneficiarioIndRen entity);


}

