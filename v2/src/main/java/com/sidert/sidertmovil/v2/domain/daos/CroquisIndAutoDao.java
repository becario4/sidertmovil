package com.sidert.sidertmovil.v2.domain.daos;

import com.sidert.sidertmovil.v2.domain.entities.CroquisIndAuto;

import java.util.List;
import java.util.Optional;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;
import androidx.room.Delete;

@Dao
public interface CroquisIndAutoDao {

    @Query("SELECT * FROM tbl_croquis_ind_auto")
    List<CroquisIndAuto> getAll();

    @Query("SELECT * FROM tbl_croquis_ind_auto t0 WHERE t0.id = :id")
    Optional<CroquisIndAuto> findById(Long id);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(List<CroquisIndAuto> entities);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    Long insert(CroquisIndAuto entity);

    @Update()
    void update(CroquisIndAuto entity);

    @Delete()
    void delete(CroquisIndAuto entity);


}

