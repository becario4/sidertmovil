package com.sidert.sidertmovil.v2.domain.daos;

import com.sidert.sidertmovil.v2.domain.entities.CroquisGpoAuto;

import java.util.List;
import java.util.Optional;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;
import androidx.room.Delete;

@Dao
public interface CroquisGpoAutoDao {

    @Query("SELECT * FROM tbl_croquis_gpo_auto")
    List<CroquisGpoAuto> getAll();

    @Query("SELECT * FROM tbl_croquis_gpo_auto t0 WHERE t0.id = :id")
    Optional<CroquisGpoAuto> findById(Long id);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(List<CroquisGpoAuto> entities);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    Long insert(CroquisGpoAuto entity);

    @Update()
    void update(CroquisGpoAuto entity);

    @Delete()
    void delete(CroquisGpoAuto entity);


}

