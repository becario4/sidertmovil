package com.sidert.sidertmovil.v2.domain.daos;

import com.sidert.sidertmovil.v2.domain.entities.AvalRen;

import java.util.List;
import java.util.Optional;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;
import androidx.room.Delete;

@Dao
public interface AvalRenDao {

    @Query("SELECT * FROM tbl_aval_ren")
    List<AvalRen> getAll();

    @Query("SELECT * FROM tbl_aval_ren t0 WHERE t0.id_aval = :id")
    Optional<AvalRen> findById(Long id);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(List<AvalRen> entities);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    Long insert(AvalRen entity);

    @Update()
    void update(AvalRen entity);

    @Delete()
    void delete(AvalRen entity);


}

