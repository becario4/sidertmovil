package com.sidert.sidertmovil.v2.domain.daos;

import com.sidert.sidertmovil.v2.domain.entities.Geo;

import java.util.List;
import java.util.Optional;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;
import androidx.room.Delete;

@Dao
public interface GeoDao {

    @Query("SELECT * FROM geo")
    List<Geo> getAll();

    @Query("SELECT * FROM geo t0 WHERE t0._id = :id")
    Optional<Geo> findById(Long id);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(List<Geo> entities);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    Long insert(Geo entity);

    @Update()
    void update(Geo entity);

    @Delete()
    void delete(Geo entity);


}

