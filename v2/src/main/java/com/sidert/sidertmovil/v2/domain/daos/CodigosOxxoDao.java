package com.sidert.sidertmovil.v2.domain.daos;

import com.sidert.sidertmovil.v2.domain.entities.CodigosOxxo;

import java.util.List;
import java.util.Optional;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;
import androidx.room.Delete;

@Dao
public interface CodigosOxxoDao {

    @Query("SELECT * FROM tbl_codigos_oxxo")
    List<CodigosOxxo> getAll();

    @Query("SELECT * FROM tbl_codigos_oxxo t0 WHERE t0._id = :id")
    Optional<CodigosOxxo> findById(Long id);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(List<CodigosOxxo> entities);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    Long insert(CodigosOxxo entity);

    @Update()
    void update(CodigosOxxo entity);

    @Delete()
    void delete(CodigosOxxo entity);


}

