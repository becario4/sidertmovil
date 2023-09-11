package com.sidert.sidertmovil.v2.domain.daos;

import com.sidert.sidertmovil.v2.domain.entities.Municipios;

import java.util.List;
import java.util.Optional;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;
import androidx.room.Delete;

@Dao
public interface MunicipiosDao {

    @Query("SELECT * FROM municipios")
    List<Municipios> getAll();

    @Query("SELECT * FROM municipios t0 WHERE t0._id = :id")
    Optional<Municipios> findById(Long id);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(List<Municipios> entities);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    Long insert(Municipios entity);

    @Update()
    void update(Municipios entity);

    @Delete()
    void delete(Municipios entity);


}

