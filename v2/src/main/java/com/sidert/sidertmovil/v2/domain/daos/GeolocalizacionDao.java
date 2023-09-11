package com.sidert.sidertmovil.v2.domain.daos;

import com.sidert.sidertmovil.v2.domain.entities.Geolocalizacion;

import java.util.List;
import java.util.Optional;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;
import androidx.room.Delete;

@Dao
public interface GeolocalizacionDao {

    @Query("SELECT * FROM geolocalizacion_t")
    List<Geolocalizacion> getAll();

    @Query("SELECT * FROM geolocalizacion_t t0 WHERE t0._id = :id")
    Optional<Geolocalizacion> findById(Long id);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(List<Geolocalizacion> entities);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    Long insert(Geolocalizacion entity);

    @Update()
    void update(Geolocalizacion entity);

    @Delete()
    void delete(Geolocalizacion entity);


}

