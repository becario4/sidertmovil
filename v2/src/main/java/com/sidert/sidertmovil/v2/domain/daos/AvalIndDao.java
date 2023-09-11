package com.sidert.sidertmovil.v2.domain.daos;

import com.sidert.sidertmovil.v2.domain.entities.AvalInd;

import java.util.List;
import java.util.Optional;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;
import androidx.room.Delete;

@Dao
public interface AvalIndDao {

    @Query("SELECT * FROM tbl_aval_ind")
    List<AvalInd> getAll();

    @Query("SELECT * FROM tbl_aval_ind t0 WHERE t0.id_aval = :id")
    Optional<AvalInd> findById(Long id);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(List<AvalInd> entities);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    Long insert(AvalInd entity);

    @Update()
    void update(AvalInd entity);

    @Delete()
    void delete(AvalInd entity);


}

