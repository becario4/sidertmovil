package com.sidert.sidertmovil.v2.domain.daos;

import com.sidert.sidertmovil.v2.domain.entities.PrestamosGpo;

import java.util.List;
import java.util.Optional;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;
import androidx.room.Delete;

@Dao
public interface PrestamosGpoDao {

    @Query("SELECT * FROM tbl_prestamos_gpo_t")
    List<PrestamosGpo> getAll();

    @Query("SELECT * FROM tbl_prestamos_gpo_t t0 WHERE t0._id = :id")
    Optional<PrestamosGpo> findById(Long id);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(List<PrestamosGpo> entities);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    Long insert(PrestamosGpo entity);

    @Update()
    void update(PrestamosGpo entity);

    @Delete()
    void delete(PrestamosGpo entity);


}

