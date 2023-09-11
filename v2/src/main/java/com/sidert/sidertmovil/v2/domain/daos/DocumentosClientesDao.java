package com.sidert.sidertmovil.v2.domain.daos;

import com.sidert.sidertmovil.v2.domain.entities.DocumentosClientes;

import java.util.List;
import java.util.Optional;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;
import androidx.room.Delete;

@Dao
public interface DocumentosClientesDao {

    @Query("SELECT * FROM tbl_documentos_clientes")
    List<DocumentosClientes> getAll();

    @Query("SELECT * FROM tbl_documentos_clientes t0 WHERE t0.id = :id")
    Optional<DocumentosClientes> findById(Long id);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(List<DocumentosClientes> entities);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    Long insert(DocumentosClientes entity);

    @Update()
    void update(DocumentosClientes entity);

    @Delete()
    void delete(DocumentosClientes entity);


}

