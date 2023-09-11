package com.sidert.sidertmovil.v2.domain.daos;

import com.sidert.sidertmovil.v2.domain.entities.ReimpresionVigente;

import java.util.List;
import java.util.Optional;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;
import androidx.room.Delete;

@Dao
public interface ReimpresionVigenteDao {

    @Query("SELECT * FROM tbl_reimpresion_vigente_t")
    List<ReimpresionVigente> getAll();

    @Query("SELECT * FROM tbl_reimpresion_vigente_t t0 WHERE t0._id = :id")
    Optional<ReimpresionVigente> findById(Long id);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(List<ReimpresionVigente> entities);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    Long insert(ReimpresionVigente entity);

    @Update()
    void update(ReimpresionVigente entity);

    @Delete()
    void delete(ReimpresionVigente entity);


}

