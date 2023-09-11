package com.sidert.sidertmovil.v2.domain.daos;

import com.sidert.sidertmovil.v2.domain.entities.CreditoGpo;

import java.util.List;
import java.util.Optional;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;
import androidx.room.Delete;

@Dao
public interface CreditoGpoDao {

    @Query("SELECT * FROM tbl_credito_gpo")
    List<CreditoGpo> getAll();

    @Query("SELECT * FROM tbl_credito_gpo t0 WHERE t0.id = :id")
    Optional<CreditoGpo> findById(Long id);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(List<CreditoGpo> entities);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    Long insert(CreditoGpo entity);

    @Update()
    void update(CreditoGpo entity);

    @Delete()
    void delete(CreditoGpo entity);


}

