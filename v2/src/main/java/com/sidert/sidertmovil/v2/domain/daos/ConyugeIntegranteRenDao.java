package com.sidert.sidertmovil.v2.domain.daos;

import com.sidert.sidertmovil.v2.domain.entities.ConyugeIntegranteRen;

import java.util.List;
import java.util.Optional;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;
import androidx.room.Delete;

@Dao
public interface ConyugeIntegranteRenDao {

    @Query("SELECT * FROM tbl_conyuge_integrante_ren")
    List<ConyugeIntegranteRen> getAll();

    @Query("SELECT * FROM tbl_conyuge_integrante_ren t0 WHERE t0.id_conyuge = :id")
    Optional<ConyugeIntegranteRen> findById(Long id);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(List<ConyugeIntegranteRen> entities);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    Long insert(ConyugeIntegranteRen entity);

    @Update()
    void update(ConyugeIntegranteRen entity);

    @Delete()
    void delete(ConyugeIntegranteRen entity);


}

