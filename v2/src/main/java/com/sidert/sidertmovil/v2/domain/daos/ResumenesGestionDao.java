package com.sidert.sidertmovil.v2.domain.daos;

import com.sidert.sidertmovil.v2.domain.entities.ResumenesGestion;

import java.util.List;
import java.util.Optional;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;
import androidx.room.Delete;

@Dao
public interface ResumenesGestionDao {

    @Query("SELECT * FROM tbl_resumenes_gestion")
    List<ResumenesGestion> getAll();

    @Query("SELECT * FROM tbl_resumenes_gestion t0 WHERE t0._id = :id")
    Optional<ResumenesGestion> findById(Long id);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(List<ResumenesGestion> entities);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    Long insert(ResumenesGestion entity);

    @Update()
    void update(ResumenesGestion entity);

    @Delete()
    void delete(ResumenesGestion entity);


}

