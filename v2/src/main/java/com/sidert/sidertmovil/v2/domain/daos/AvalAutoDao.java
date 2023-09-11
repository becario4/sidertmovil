package com.sidert.sidertmovil.v2.domain.daos;

import com.sidert.sidertmovil.v2.domain.entities.AvalAuto;

import java.util.List;
import java.util.Optional;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;
import androidx.room.Delete;

@Dao
public interface AvalAutoDao {

    @Query("SELECT * FROM tbl_aval_auto")
    List<AvalAuto> getAll();

    @Query("SELECT * FROM tbl_aval_auto t0 WHERE t0.id_aval = :id")
    Optional<AvalAuto> findById(Long id);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(List<AvalAuto> entities);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    Long insert(AvalAuto entity);

    @Update()
    void update(AvalAuto entity);

    @Delete()
    void delete(AvalAuto entity);


}

