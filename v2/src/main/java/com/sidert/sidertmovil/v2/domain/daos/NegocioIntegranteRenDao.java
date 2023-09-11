package com.sidert.sidertmovil.v2.domain.daos;

import com.sidert.sidertmovil.v2.domain.entities.NegocioIntegranteRen;

import java.util.List;
import java.util.Optional;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;
import androidx.room.Delete;

@Dao
public interface NegocioIntegranteRenDao {

    @Query("SELECT * FROM tbl_negocio_integrante_ren")
    List<NegocioIntegranteRen> getAll();

    @Query("SELECT * FROM tbl_negocio_integrante_ren t0 WHERE t0.id_negocio = :id")
    Optional<NegocioIntegranteRen> findById(Long id);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(List<NegocioIntegranteRen> entities);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    Long insert(NegocioIntegranteRen entity);

    @Update()
    void update(NegocioIntegranteRen entity);

    @Delete()
    void delete(NegocioIntegranteRen entity);


}

