package com.sidert.sidertmovil.v2.domain.daos;

import com.sidert.sidertmovil.v2.domain.entities.PlazosPrestamos;

import java.util.List;
import java.util.Optional;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;
import androidx.room.Delete;

@Dao
public interface PlazosPrestamosDao {

    @Query("SELECT * FROM tbl_plazos_prestamos")
    List<PlazosPrestamos> getAll();

    @Query("SELECT * FROM tbl_plazos_prestamos t0 WHERE t0._id = :id")
    Optional<PlazosPrestamos> findById(Long id);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(List<PlazosPrestamos> entities);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    Long insert(PlazosPrestamos entity);

    @Update()
    void update(PlazosPrestamos entity);

    @Delete()
    void delete(PlazosPrestamos entity);


}

