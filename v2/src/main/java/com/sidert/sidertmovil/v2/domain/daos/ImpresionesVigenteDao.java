package com.sidert.sidertmovil.v2.domain.daos;

import com.sidert.sidertmovil.v2.domain.entities.ImpresionesVigente;

import java.util.List;
import java.util.Optional;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;
import androidx.room.Delete;

@Dao
public interface ImpresionesVigenteDao {

    @Query("SELECT * FROM tbl_impresiones_vigente_t")
    List<ImpresionesVigente> getAll();

    @Query("SELECT * FROM tbl_impresiones_vigente_t t0 WHERE t0._id = :id")
    Optional<ImpresionesVigente> findById(Long id);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(List<ImpresionesVigente> entities);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    Long insert(ImpresionesVigente entity);

    @Update()
    void update(ImpresionesVigente entity);

    @Delete()
    void delete(ImpresionesVigente entity);


}

