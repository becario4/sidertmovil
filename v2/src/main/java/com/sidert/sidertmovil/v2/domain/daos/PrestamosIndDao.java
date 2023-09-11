package com.sidert.sidertmovil.v2.domain.daos;

import com.sidert.sidertmovil.v2.domain.entities.PrestamosInd;

import java.util.List;
import java.util.Optional;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;
import androidx.room.Delete;

@Dao
public interface PrestamosIndDao {

    @Query("SELECT * FROM tbl_prestamos_ind_t")
    List<PrestamosInd> getAll();

    @Query("SELECT * FROM tbl_prestamos_ind_t t0 WHERE t0._id = :id")
    Optional<PrestamosInd> findById(Long id);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(List<PrestamosInd> entities);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    Long insert(PrestamosInd entity);

    @Update()
    void update(PrestamosInd entity);

    @Delete()
    void delete(PrestamosInd entity);


}

