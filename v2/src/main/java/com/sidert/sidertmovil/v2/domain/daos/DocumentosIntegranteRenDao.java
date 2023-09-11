package com.sidert.sidertmovil.v2.domain.daos;

import com.sidert.sidertmovil.v2.domain.entities.DocumentosIntegranteRen;

import java.util.List;
import java.util.Optional;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;
import androidx.room.Delete;

@Dao
public interface DocumentosIntegranteRenDao {

    @Query("SELECT * FROM tbl_documentos_integrante_ren")
    List<DocumentosIntegranteRen> getAll();

    @Query("SELECT * FROM tbl_documentos_integrante_ren t0 WHERE t0.id_documento = :id")
    Optional<DocumentosIntegranteRen> findById(Long id);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(List<DocumentosIntegranteRen> entities);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    Long insert(DocumentosIntegranteRen entity);

    @Update()
    void update(DocumentosIntegranteRen entity);

    @Delete()
    void delete(DocumentosIntegranteRen entity);


}

