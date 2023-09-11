package com.sidert.sidertmovil.v2.domain.daos;

import com.sidert.sidertmovil.v2.domain.entities.Estados;

import java.util.List;
import java.util.Optional;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;
import androidx.room.Delete;

@Dao
public interface EstadosDao {

    @Query("SELECT * FROM estados")
    List<Estados> getAll();

    @Query("SELECT * FROM estados t0 WHERE t0._id = :id")
    Optional<Estados> findById(Long id);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(List<Estados> entities);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    Long insert(Estados entity);

    @Update()
    void update(Estados entity);

    @Delete()
    void delete(Estados entity);


}

