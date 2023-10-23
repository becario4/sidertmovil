package com.sidert.sidertmovil.v2.domain.daos;

import com.sidert.sidertmovil.v2.domain.entities.Beneficiario;

import java.util.List;
import java.util.Optional;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

@Dao
public interface BeneficiariosDao {

    @Query("SELECT * FROM beneficiario")
    List<Beneficiario> getAll();

    @Query("SELECT * FROM beneficiario t0 WHERE t0._id = :id")
    Optional<Beneficiario> findById(Long id);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(List<Beneficiario> entities);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    Long insert(Beneficiario entity);

    @Update()
    void update(Beneficiario entity);

    @Delete()
    void delete(Beneficiario entity);

}
