package com.sidert.sidertmovil.v2.domain.daos;

import com.sidert.sidertmovil.v2.domain.entities.CierreDia;

import java.util.List;
import java.util.Optional;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;
import androidx.room.Delete;

@Dao
public interface CierreDiaDao {

    @Query("SELECT * FROM tbl_cierre_dia_t")
    List<CierreDia> getAll();

    @Query("SELECT * FROM tbl_cierre_dia_t t0 WHERE t0._id = :id")
    Optional<CierreDia> findById(Long id);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(List<CierreDia> entities);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    Long insert(CierreDia entity);

    @Update()
    void update(CierreDia entity);

    @Delete()
    void delete(CierreDia entity);


}

