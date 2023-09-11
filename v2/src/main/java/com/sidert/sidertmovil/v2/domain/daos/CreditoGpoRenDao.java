package com.sidert.sidertmovil.v2.domain.daos;

import com.sidert.sidertmovil.v2.domain.entities.CreditoGpoRen;

import java.util.List;
import java.util.Optional;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;
import androidx.room.Delete;

@Dao
public interface CreditoGpoRenDao {

    @Query("SELECT * FROM tbl_credito_gpo_ren")
    List<CreditoGpoRen> getAll();

    @Query("SELECT * FROM tbl_credito_gpo_ren t0 WHERE t0.id = :id")
    Optional<CreditoGpoRen> findById(Long id);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(List<CreditoGpoRen> entities);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    Long insert(CreditoGpoRen entity);

    @Update()
    void update(CreditoGpoRen entity);

    @Delete()
    void delete(CreditoGpoRen entity);


}

