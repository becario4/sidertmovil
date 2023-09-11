package com.sidert.sidertmovil.v2.domain.daos;

import com.sidert.sidertmovil.v2.domain.entities.ReporteSesiones;

import java.util.List;
import java.util.Optional;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;
import androidx.room.Delete;

@Dao
public interface ReporteSesionesDao {

    @Query("SELECT * FROM tbl_reporte_sesiones")
    List<ReporteSesiones> getAll();

    @Query("SELECT * FROM tbl_reporte_sesiones t0 WHERE t0._id = :id")
    Optional<ReporteSesiones> findById(Long id);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(List<ReporteSesiones> entities);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    Long insert(ReporteSesiones entity);

    @Update()
    void update(ReporteSesiones entity);

    @Delete()
    void delete(ReporteSesiones entity);


}

