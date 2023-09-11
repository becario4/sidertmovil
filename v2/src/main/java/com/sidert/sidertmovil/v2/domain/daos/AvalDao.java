package com.sidert.sidertmovil.v2.domain.daos;

import com.sidert.sidertmovil.v2.domain.entities.Aval;

import java.util.List;
import java.util.Optional;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;
import androidx.room.Delete;

@Dao
public interface AvalDao {

    @Query("SELECT * FROM tbl_aval_t")
    List<Aval> getAll();

    @Query("SELECT * FROM tbl_aval_t t0 WHERE t0._id = :id")
    Optional<Aval> findById(Long id);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(List<Aval> entities);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    Long insert(Aval entity);

    @Update()
    void update(Aval entity);

    @Delete()
    void delete(Aval entity);


}

