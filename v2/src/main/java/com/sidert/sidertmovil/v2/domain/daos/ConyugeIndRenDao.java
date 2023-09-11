package com.sidert.sidertmovil.v2.domain.daos;

import com.sidert.sidertmovil.v2.domain.entities.ConyugeIndRen;

import java.util.List;
import java.util.Optional;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;
import androidx.room.Delete;

@Dao
public interface ConyugeIndRenDao {

    @Query("SELECT * FROM tbl_conyuge_ind_ren")
    List<ConyugeIndRen> getAll();

    @Query("SELECT * FROM tbl_conyuge_ind_ren t0 WHERE t0.id_conyuge = :id")
    Optional<ConyugeIndRen> findById(Long id);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(List<ConyugeIndRen> entities);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    Long insert(ConyugeIndRen entity);

    @Update()
    void update(ConyugeIndRen entity);

    @Delete()
    void delete(ConyugeIndRen entity);


}

