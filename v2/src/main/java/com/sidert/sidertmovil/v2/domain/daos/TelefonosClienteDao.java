package com.sidert.sidertmovil.v2.domain.daos;

import com.sidert.sidertmovil.v2.domain.entities.TelefonosCliente;

import java.util.List;
import java.util.Optional;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;
import androidx.room.Delete;

@Dao
public interface TelefonosClienteDao {

    @Query("SELECT * FROM tbl_telefonos_cliente")
    List<TelefonosCliente> getAll();

    @Query("SELECT * FROM tbl_telefonos_cliente t0 WHERE t0._id = :id")
    Optional<TelefonosCliente> findById(Long id);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(List<TelefonosCliente> entities);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    Long insert(TelefonosCliente entity);

    @Update()
    void update(TelefonosCliente entity);

    @Delete()
    void delete(TelefonosCliente entity);


}

