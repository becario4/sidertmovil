package com.sidert.sidertmovil.v2.domain.daos;

import com.sidert.sidertmovil.v2.domain.entities.EconomicosIndAuto;

import java.util.List;
import java.util.Optional;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;
import androidx.room.Delete;

@Dao
public interface EconomicosIndAutoDao {

    @Query("SELECT * FROM tbl_economicos_ind_auto")
    List<EconomicosIndAuto> getAll();

    @Query("SELECT * FROM tbl_economicos_ind_auto t0 WHERE t0.id_economico = :id")
    Optional<EconomicosIndAuto> findById(Long id);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(List<EconomicosIndAuto> entities);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    Long insert(EconomicosIndAuto entity);

    @Update()
    void update(EconomicosIndAuto entity);

    @Delete()
    void delete(EconomicosIndAuto entity);


}
