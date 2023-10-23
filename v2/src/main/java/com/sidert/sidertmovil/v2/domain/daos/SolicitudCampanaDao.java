package com.sidert.sidertmovil.v2.domain.daos;

import com.sidert.sidertmovil.v2.domain.entities.SolicitudCampana;

import java.util.List;
import java.util.Optional;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

@Dao
public interface SolicitudCampanaDao {

    @Query("SELECT * FROM solicitud_campana")
    List<SolicitudCampana> getAll();

    @Query("SELECT * FROM solicitud_campana t0 WHERE t0._id = :id")
    Optional<SolicitudCampana> findById(Long id);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(List<SolicitudCampana> entities);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    Long insert(SolicitudCampana entity);

    @Update()
    void update(SolicitudCampana entity);

    @Delete()
    void delete(SolicitudCampana entity);

}
