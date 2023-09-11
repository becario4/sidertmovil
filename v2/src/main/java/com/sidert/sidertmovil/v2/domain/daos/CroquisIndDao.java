package com.sidert.sidertmovil.v2.domain.daos;

import com.sidert.sidertmovil.v2.domain.entities.CroquisInd;

import java.util.List;
import java.util.Optional;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;
import androidx.room.Delete;

@Dao
public interface CroquisIndDao {

    @Query("SELECT * FROM tbl_croquis_ind")
    List<CroquisInd> getAll();

    @Query("SELECT * FROM tbl_croquis_ind t0 WHERE t0.id = :id")
    Optional<CroquisInd> findById(Long id);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(List<CroquisInd> entities);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    Long insert(CroquisInd entity);

    @Update()
    void update(CroquisInd entity);

    @Delete()
    void delete(CroquisInd entity);


}

