package com.sidert.sidertmovil.v2.domain.daos;

import com.sidert.sidertmovil.v2.domain.entities.Fichas;

import java.util.List;
import java.util.Optional;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;
import androidx.room.Delete;

@Dao
public interface FichasDao {

    @Query("SELECT * FROM fichas_t")
    List<Fichas> getAll();

    @Query("SELECT * FROM fichas_t t0 WHERE t0._id = :id")
    Optional<Fichas> findById(Long id);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(List<Fichas> entities);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    Long insert(Fichas entity);

    @Update()
    void update(Fichas entity);

    @Delete()
    void delete(Fichas entity);


}

