package com.sidert.sidertmovil.v2.domain.daos;

import com.sidert.sidertmovil.v2.domain.entities.Sincronizado;

import java.util.List;
import java.util.Optional;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;
import androidx.room.Delete;

@Dao
public interface SincronizadoDao {

    @Query("SELECT * FROM sincronizado_t")
    List<Sincronizado> getAll();

    @Query("SELECT * FROM sincronizado_t t0 WHERE t0._id = :id")
    Optional<Sincronizado> findById(Long id);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(List<Sincronizado> entities);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    Long insert(Sincronizado entity);

    @Update()
    void update(Sincronizado entity);

    @Delete()
    void delete(Sincronizado entity);


}

