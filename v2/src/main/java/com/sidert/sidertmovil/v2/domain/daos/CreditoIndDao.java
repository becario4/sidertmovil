package com.sidert.sidertmovil.v2.domain.daos;

import com.sidert.sidertmovil.v2.domain.entities.CreditoInd;

import java.util.List;
import java.util.Optional;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;
import androidx.room.Delete;

@Dao
public interface CreditoIndDao {

    @Query("SELECT * FROM tbl_credito_ind")
    List<CreditoInd> getAll();

    @Query("SELECT * FROM tbl_credito_ind t0 WHERE t0.id_credito = :id")
    Optional<CreditoInd> findById(Long id);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(List<CreditoInd> entities);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    Long insert(CreditoInd entity);

    @Update()
    void update(CreditoInd entity);

    @Delete()
    void delete(CreditoInd entity);


}

