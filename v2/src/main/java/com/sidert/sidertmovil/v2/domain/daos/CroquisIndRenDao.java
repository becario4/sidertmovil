package com.sidert.sidertmovil.v2.domain.daos;

import com.sidert.sidertmovil.v2.domain.entities.CroquisIndRen;

import java.util.List;
import java.util.Optional;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;
import androidx.room.Delete;

@Dao
public interface CroquisIndRenDao {

    @Query("SELECT * FROM tbl_croquis_ind_ren")
    List<CroquisIndRen> getAll();

    @Query("SELECT * FROM tbl_croquis_ind_ren t0 WHERE t0.id = :id")
    Optional<CroquisIndRen> findById(Long id);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(List<CroquisIndRen> entities);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    Long insert(CroquisIndRen entity);

    @Update()
    void update(CroquisIndRen entity);

    @Delete()
    void delete(CroquisIndRen entity);


}

