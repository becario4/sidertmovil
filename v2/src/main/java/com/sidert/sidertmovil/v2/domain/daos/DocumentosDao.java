package com.sidert.sidertmovil.v2.domain.daos;

import com.sidert.sidertmovil.v2.domain.entities.Documentos;

import java.util.List;
import java.util.Optional;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;
import androidx.room.Delete;

@Dao
public interface DocumentosDao {

    @Query("SELECT * FROM tbl_documentos")
    List<Documentos> getAll();

    @Query("SELECT * FROM tbl_documentos t0 WHERE t0.id_documento = :id")
    Optional<Documentos> findById(Long id);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(List<Documentos> entities);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    Long insert(Documentos entity);

    @Update()
    void update(Documentos entity);

    @Delete()
    void delete(Documentos entity);


}

