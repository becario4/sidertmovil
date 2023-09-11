package com.sidert.sidertmovil.v2.domain.daos;

import com.sidert.sidertmovil.v2.domain.entities.DestinosCredito;

import java.util.List;
import java.util.Optional;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;
import androidx.room.Delete;

@Dao
public interface DestinosCreditoDao {

    @Query("SELECT * FROM tbl_destinos_credito")
    List<DestinosCredito> getAll();

    @Query("SELECT * FROM tbl_destinos_credito t0 WHERE t0._id = :id")
    Optional<DestinosCredito> findById(Long id);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(List<DestinosCredito> entities);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    Long insert(DestinosCredito entity);

    @Update()
    void update(DestinosCredito entity);

    @Delete()
    void delete(DestinosCredito entity);


}

