package com.sidert.sidertmovil.v2.domain.daos;

import com.sidert.sidertmovil.v2.domain.entities.ConyugeInd;

import java.util.List;
import java.util.Optional;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;
import androidx.room.Delete;

@Dao
public interface ConyugeIndDao {

    @Query("SELECT * FROM tbl_conyuge_ind")
    List<ConyugeInd> getAll();

    @Query("SELECT * FROM tbl_conyuge_ind t0 WHERE t0.id_conyuge = :id")
    Optional<ConyugeInd> findById(Long id);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(List<ConyugeInd> entities);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    Long insert(ConyugeInd entity);

    @Update()
    void update(ConyugeInd entity);

    @Delete()
    void delete(ConyugeInd entity);


}

