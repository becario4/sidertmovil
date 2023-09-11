package com.sidert.sidertmovil.v2.domain.daos;

import com.sidert.sidertmovil.v2.domain.entities.CatColonias;

import java.util.List;
import java.util.Optional;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;
import androidx.room.Delete;

@Dao
public interface CatColoniasDao {

    @Query("SELECT * FROM cat_colonias")
    List<CatColonias> getAll();

    @Query("SELECT * FROM cat_colonias t0 WHERE t0._id = :id")
    Optional<CatColonias> findById(Long id);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(List<CatColonias> entities);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    Long insert(CatColonias entity);

    @Update()
    void update(CatColonias entity);

    @Delete()
    void delete(CatColonias entity);


}

