package com.sidert.sidertmovil.v2.domain.daos;

import com.sidert.sidertmovil.v2.domain.entities.CreditoGpoAuto;

import java.util.List;
import java.util.Optional;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;
import androidx.room.Delete;

@Dao
public interface CreditoGpoAutoDao {

    @Query("SELECT * FROM tbl_credito_gpo_auto")
    List<CreditoGpoAuto> getAll();

    @Query("SELECT * FROM tbl_credito_gpo_auto t0 WHERE t0.id = :id")
    Optional<CreditoGpoAuto> findById(Long id);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(List<CreditoGpoAuto> entities);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    Long insert(CreditoGpoAuto entity);

    @Update()
    void update(CreditoGpoAuto entity);

    @Delete()
    void delete(CreditoGpoAuto entity);


}

