package com.sidert.sidertmovil.v2.domain.daos;

import com.sidert.sidertmovil.v2.domain.entities.TrackerAsesor;

import java.util.List;
import java.util.Optional;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;
import androidx.room.Delete;

@Dao
public interface TrackerAsesorDao {

    @Query("SELECT * FROM tbl_tracker_asesor_t")
    List<TrackerAsesor> getAll();

    @Query("SELECT * FROM tbl_tracker_asesor_t t0 WHERE t0._id = :id")
    Optional<TrackerAsesor> findById(Long id);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(List<TrackerAsesor> entities);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    Long insert(TrackerAsesor entity);

    @Update()
    void update(TrackerAsesor entity);

    @Delete()
    void delete(TrackerAsesor entity);


}

