package com.sidert.sidertmovil.v2.domain.daos;

import com.sidert.sidertmovil.v2.domain.entities.NivelesEstudios;

import java.util.List;
import java.util.Optional;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;
import androidx.room.Delete;

@Dao
public interface NivelesEstudiosDao {

    @Query("SELECT * FROM tbl_niveles_estudios")
    List<NivelesEstudios> getAll();

    @Query("SELECT * FROM tbl_niveles_estudios t0 WHERE t0._id = :id")
    Optional<NivelesEstudios> findById(Long id);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(List<NivelesEstudios> entities);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    Long insert(NivelesEstudios entity);

    @Update()
    void update(NivelesEstudios entity);

    @Delete()
    void delete(NivelesEstudios entity);


}

