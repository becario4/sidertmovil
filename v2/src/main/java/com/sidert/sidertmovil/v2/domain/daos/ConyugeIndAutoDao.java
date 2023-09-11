package com.sidert.sidertmovil.v2.domain.daos;

import com.sidert.sidertmovil.v2.domain.entities.ConyugeIndAuto;

import java.util.List;
import java.util.Optional;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;
import androidx.room.Delete;

@Dao
public interface ConyugeIndAutoDao {

    @Query("SELECT * FROM tbl_conyuge_ind_auto")
    List<ConyugeIndAuto> getAll();

    @Query("SELECT * FROM tbl_conyuge_ind_auto t0 WHERE t0.id_conyuge = :id")
    Optional<ConyugeIndAuto> findById(Long id);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(List<ConyugeIndAuto> entities);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    Long insert(ConyugeIndAuto entity);

    @Update()
    void update(ConyugeIndAuto entity);

    @Delete()
    void delete(ConyugeIndAuto entity);


}

