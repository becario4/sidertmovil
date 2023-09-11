package com.sidert.sidertmovil.v2.domain.daos;

import com.sidert.sidertmovil.v2.domain.entities.CroquisGpo;

import java.util.List;
import java.util.Optional;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;
import androidx.room.Delete;

@Dao
public interface CroquisGpoDao {

    @Query("SELECT * FROM tbl_croquis_gpo")
    List<CroquisGpo> getAll();

    @Query("SELECT * FROM tbl_croquis_gpo t0 WHERE t0.id = :id")
    Optional<CroquisGpo> findById(Long id);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(List<CroquisGpo> entities);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    Long insert(CroquisGpo entity);

    @Update()
    void update(CroquisGpo entity);

    @Delete()
    void delete(CroquisGpo entity);


}

