package com.sidert.sidertmovil.v2.domain.daos;

import com.sidert.sidertmovil.v2.domain.entities.ClienteInd;

import java.util.List;
import java.util.Optional;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;
import androidx.room.Delete;

@Dao
public interface ClienteIndDao {

    @Query("SELECT * FROM tbl_cliente_ind")
    List<ClienteInd> getAll();

    @Query("SELECT * FROM tbl_cliente_ind t0 WHERE t0.id_cliente = :id")
    Optional<ClienteInd> findById(Long id);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(List<ClienteInd> entities);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    Long insert(ClienteInd entity);

    @Update()
    void update(ClienteInd entity);

    @Delete()
    void delete(ClienteInd entity);


}

