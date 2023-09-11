package com.sidert.sidertmovil.v2.domain.daos;

import com.sidert.sidertmovil.v2.domain.entities.IntegrantesGpoRen;

import java.util.List;
import java.util.Optional;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;
import androidx.room.Delete;

@Dao
public interface IntegrantesGpoRenDao {

    @Query("SELECT * FROM tbl_integrantes_gpo_ren")
    List<IntegrantesGpoRen> getAll();

    @Query("SELECT * FROM tbl_integrantes_gpo_ren t0 WHERE t0.id = :id")
    Optional<IntegrantesGpoRen> findById(Long id);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(List<IntegrantesGpoRen> entities);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    Long insert(IntegrantesGpoRen entity);

    @Update()
    void update(IntegrantesGpoRen entity);

    @Delete()
    void delete(IntegrantesGpoRen entity);


}

