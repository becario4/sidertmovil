package com.sidert.sidertmovil.v2.domain.daos;

import com.sidert.sidertmovil.v2.domain.entities.Localidades;

import java.util.List;
import java.util.Optional;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;
import androidx.room.Delete;

@Dao
public interface LocalidadesDao {

    @Query("SELECT * FROM localidades")
    List<Localidades> getAll();

    @Query("SELECT * FROM localidades t0 WHERE t0._id = :id")
    Optional<Localidades> findById(Long id);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(List<Localidades> entities);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    Long insert(Localidades entity);

    @Update()
    void update(Localidades entity);

    @Delete()
    void delete(Localidades entity);


}

