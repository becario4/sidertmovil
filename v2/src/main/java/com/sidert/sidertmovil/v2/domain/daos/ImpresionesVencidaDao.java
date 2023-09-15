package com.sidert.sidertmovil.v2.domain.daos;

import com.sidert.sidertmovil.v2.domain.entities.ImpresionesVencida;

import java.util.List;
import java.util.Optional;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;
import androidx.room.Delete;

@Dao
public interface ImpresionesVencidaDao {

    @Query("SELECT * FROM tbl_impresiones_vencida_t")
    List<ImpresionesVencida> getAll();

    @Query("SELECT * FROM tbl_impresiones_vencida_t t0 WHERE t0._id = :id")
    Optional<ImpresionesVencida> findById(Long id);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(List<ImpresionesVencida> entities);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    Long insert(ImpresionesVencida entity);

    @Update()
    void update(ImpresionesVencida entity);

    @Delete()
    void delete(ImpresionesVencida entity);


}
