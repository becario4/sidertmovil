package com.sidert.sidertmovil.v2.domain.daos;

import com.sidert.sidertmovil.v2.domain.entities.PoliticasPldIndRen;

import java.util.List;
import java.util.Optional;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;
import androidx.room.Delete;

@Dao
public interface PoliticasPldIndRenDao {

    @Query("SELECT * FROM tbl_politicas_pld_ind_ren")
    List<PoliticasPldIndRen> getAll();

    @Query("SELECT * FROM tbl_politicas_pld_ind_ren t0 WHERE t0.id = :id")
    Optional<PoliticasPldIndRen> findById(Long id);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(List<PoliticasPldIndRen> entities);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    Long insert(PoliticasPldIndRen entity);

    @Update()
    void update(PoliticasPldIndRen entity);

    @Delete()
    void delete(PoliticasPldIndRen entity);


}

