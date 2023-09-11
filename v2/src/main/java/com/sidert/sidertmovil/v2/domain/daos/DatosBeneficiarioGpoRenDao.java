package com.sidert.sidertmovil.v2.domain.daos;

import com.sidert.sidertmovil.v2.domain.entities.DatosBeneficiarioGpoRen;

import java.util.List;
import java.util.Optional;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;
import androidx.room.Delete;

@Dao
public interface DatosBeneficiarioGpoRenDao {

    @Query("SELECT * FROM tbl_datos_beneficiario_gpo_ren")
    List<DatosBeneficiarioGpoRen> getAll();

    @Query("SELECT * FROM tbl_datos_beneficiario_gpo_ren t0 WHERE t0.idBeneficiario = :id")
    Optional<DatosBeneficiarioGpoRen> findById(Long id);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(List<DatosBeneficiarioGpoRen> entities);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    Long insert(DatosBeneficiarioGpoRen entity);

    @Update()
    void update(DatosBeneficiarioGpoRen entity);

    @Delete()
    void delete(DatosBeneficiarioGpoRen entity);


}

