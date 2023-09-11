package com.sidert.sidertmovil.v2.domain.daos;

import com.sidert.sidertmovil.v2.domain.entities.CroquisGpoRen;

import java.util.List;
import java.util.Optional;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;
import androidx.room.Delete;

@Dao
public interface CroquisGpoRenDao {

    @Query("SELECT * FROM tbl_croquis_gpo_ren")
    List<CroquisGpoRen> getAll();

    @Query("SELECT * FROM tbl_croquis_gpo_ren t0 WHERE t0.id = :id")
    Optional<CroquisGpoRen> findById(Long id);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(List<CroquisGpoRen> entities);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    Long insert(CroquisGpoRen entity);

    @Update()
    void update(CroquisGpoRen entity);

    @Delete()
    void delete(CroquisGpoRen entity);


}

