package com.sidert.sidertmovil.v2.domain.daos;

import com.sidert.sidertmovil.v2.domain.entities.DocumentosIntegrante;

import java.util.List;
import java.util.Optional;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;
import androidx.room.Delete;

@Dao
public interface DocumentosIntegranteDao {

    @Query("SELECT * FROM documentos_integrante")
    List<DocumentosIntegrante> getAll();

    @Query("SELECT * FROM documentos_integrante t0 WHERE t0.id_documento = :id")
    Optional<DocumentosIntegrante> findById(Long id);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(List<DocumentosIntegrante> entities);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    Long insert(DocumentosIntegrante entity);

    @Update()
    void update(DocumentosIntegrante entity);

    @Delete()
    void delete(DocumentosIntegrante entity);


}

