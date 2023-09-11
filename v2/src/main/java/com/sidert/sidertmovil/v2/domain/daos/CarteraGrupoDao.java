package com.sidert.sidertmovil.v2.domain.daos;

import com.sidert.sidertmovil.v2.domain.entities.CarteraGrupo;

import java.util.List;
import java.util.Optional;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;
import androidx.room.Delete;

@Dao
public interface CarteraGrupoDao {

    @Query("SELECT * FROM cartera_grupo_t")
    List<CarteraGrupo> getAll();

    @Query("SELECT * FROM cartera_grupo_t t0 WHERE t0._id = :id")
    Optional<CarteraGrupo> findById(Long id);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(List<CarteraGrupo> entities);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    Long insert(CarteraGrupo entity);

    @Update()
    void update(CarteraGrupo entity);

    @Delete()
    void delete(CarteraGrupo entity);


}

