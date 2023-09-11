package com.sidert.sidertmovil.v2.domain.daos;

import com.sidert.sidertmovil.v2.domain.entities.EconomicosIndRen;

import java.util.List;
import java.util.Optional;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;
import androidx.room.Delete;

@Dao
public interface EconomicosIndRenDao {

    @Query("SELECT * FROM tbl_economicos_ind_ren")
    List<EconomicosIndRen> getAll();

    @Query("SELECT * FROM tbl_economicos_ind_ren t0 WHERE t0.id_economico = :id")
    Optional<EconomicosIndRen> findById(Long id);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(List<EconomicosIndRen> entities);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    Long insert(EconomicosIndRen entity);

    @Update()
    void update(EconomicosIndRen entity);

    @Delete()
    void delete(EconomicosIndRen entity);


}

