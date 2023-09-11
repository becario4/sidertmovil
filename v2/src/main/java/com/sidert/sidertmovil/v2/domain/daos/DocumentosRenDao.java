package com.sidert.sidertmovil.v2.domain.daos;

import com.sidert.sidertmovil.v2.domain.entities.DocumentosRen;

import java.util.List;
import java.util.Optional;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;
import androidx.room.Delete;

@Dao
public interface DocumentosRenDao {

    @Query("SELECT * FROM tbl_documentos_ren")
    List<DocumentosRen> getAll();

    @Query("SELECT * FROM tbl_documentos_ren t0 WHERE t0.id_documento = :id")
    Optional<DocumentosRen> findById(Long id);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(List<DocumentosRen> entities);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    Long insert(DocumentosRen entity);

    @Update()
    void update(DocumentosRen entity);

    @Delete()
    void delete(DocumentosRen entity);


}

