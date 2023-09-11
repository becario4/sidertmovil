package com.sidert.sidertmovil.v2.domain.daos;

import com.sidert.sidertmovil.v2.domain.entities.PoliticasPldIntegranteRen;

import java.util.List;
import java.util.Optional;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;
import androidx.room.Delete;

@Dao
public interface PoliticasPldIntegranteRenDao {

    @Query("SELECT * FROM tbl_politicas_pld_integrante_ren")
    List<PoliticasPldIntegranteRen> getAll();

    @Query("SELECT * FROM tbl_politicas_pld_integrante_ren t0 WHERE t0.id_politica = :id")
    Optional<PoliticasPldIntegranteRen> findById(Long id);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(List<PoliticasPldIntegranteRen> entities);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    Long insert(PoliticasPldIntegranteRen entity);

    @Update()
    void update(PoliticasPldIntegranteRen entity);

    @Delete()
    void delete(PoliticasPldIntegranteRen entity);


}

