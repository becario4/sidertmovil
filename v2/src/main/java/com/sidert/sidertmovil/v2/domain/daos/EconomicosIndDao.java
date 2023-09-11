package com.sidert.sidertmovil.v2.domain.daos;

import com.sidert.sidertmovil.v2.domain.entities.EconomicosInd;

import java.util.List;
import java.util.Optional;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;
import androidx.room.Delete;

@Dao
public interface EconomicosIndDao {

    @Query("SELECT * FROM tbl_economicos_ind")
    List<EconomicosInd> getAll();

    @Query("SELECT * FROM tbl_economicos_ind t0 WHERE t0.id_economico = :id")
    Optional<EconomicosInd> findById(Long id);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(List<EconomicosInd> entities);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    Long insert(EconomicosInd entity);

    @Update()
    void update(EconomicosInd entity);

    @Delete()
    void delete(EconomicosInd entity);


}

