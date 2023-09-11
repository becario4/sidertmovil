package com.sidert.sidertmovil.v2.domain.daos;

import com.sidert.sidertmovil.v2.domain.entities.ClienteIndAuto;

import java.util.List;
import java.util.Optional;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;
import androidx.room.Delete;

@Dao
public interface ClienteIndAutoDao {

    @Query("SELECT * FROM tbl_cliente_ind_auto")
    List<ClienteIndAuto> getAll();

    @Query("SELECT * FROM tbl_cliente_ind_auto t0 WHERE t0.id_cliente = :id")
    Optional<ClienteIndAuto> findById(Long id);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(List<ClienteIndAuto> entities);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    Long insert(ClienteIndAuto entity);

    @Update()
    void update(ClienteIndAuto entity);

    @Delete()
    void delete(ClienteIndAuto entity);


}

