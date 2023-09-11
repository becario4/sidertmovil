package com.sidert.sidertmovil.v2.domain.daos;

import com.sidert.sidertmovil.v2.domain.entities.Amortizaciones;

import java.util.List;
import java.util.Optional;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;
import androidx.room.Delete;

@Dao
public interface AmortizacionesDao {

    @Query("SELECT * FROM tbl_amortizaciones_t")
    List<Amortizaciones> getAll();

    @Query("SELECT * FROM tbl_amortizaciones_t t0 WHERE t0._id = :id")
    Optional<Amortizaciones> findById(Long id);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(List<Amortizaciones> entities);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    Long insert(Amortizaciones entity);

    @Update()
    void update(Amortizaciones entity);

    @Delete()
    void delete(Amortizaciones entity);


}

