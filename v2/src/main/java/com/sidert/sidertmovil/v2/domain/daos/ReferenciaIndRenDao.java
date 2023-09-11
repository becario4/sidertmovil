package com.sidert.sidertmovil.v2.domain.daos;

import com.sidert.sidertmovil.v2.domain.entities.ReferenciaIndRen;

import java.util.List;
import java.util.Optional;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;
import androidx.room.Delete;

@Dao
public interface ReferenciaIndRenDao {

    @Query("SELECT * FROM tbl_referencia_ind_ren")
    List<ReferenciaIndRen> getAll();

    @Query("SELECT * FROM tbl_referencia_ind_ren t0 WHERE t0.id_referencia = :id")
    Optional<ReferenciaIndRen> findById(Long id);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(List<ReferenciaIndRen> entities);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    Long insert(ReferenciaIndRen entity);

    @Update()
    void update(ReferenciaIndRen entity);

    @Delete()
    void delete(ReferenciaIndRen entity);


}

