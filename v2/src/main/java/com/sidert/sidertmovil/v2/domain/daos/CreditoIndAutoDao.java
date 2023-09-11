package com.sidert.sidertmovil.v2.domain.daos;

import com.sidert.sidertmovil.v2.domain.entities.CreditoIndAuto;

import java.util.List;
import java.util.Optional;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;
import androidx.room.Delete;

@Dao
public interface CreditoIndAutoDao {

    @Query("SELECT * FROM tbl_credito_ind_auto")
    List<CreditoIndAuto> getAll();

    @Query("SELECT * FROM tbl_credito_ind_auto t0 WHERE t0.id_credito = :id")
    Optional<CreditoIndAuto> findById(Long id);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(List<CreditoIndAuto> entities);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    Long insert(CreditoIndAuto entity);

    @Update()
    void update(CreditoIndAuto entity);

    @Delete()
    void delete(CreditoIndAuto entity);


}

