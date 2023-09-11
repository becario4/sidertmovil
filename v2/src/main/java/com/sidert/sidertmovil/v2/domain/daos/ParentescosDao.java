package com.sidert.sidertmovil.v2.domain.daos;

import com.sidert.sidertmovil.v2.domain.entities.Parentescos;

import java.util.List;
import java.util.Optional;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;
import androidx.room.Delete;

@Dao
public interface ParentescosDao {

    @Query("SELECT * FROM tbl_parentescos")
    List<Parentescos> getAll();

    @Query("SELECT * FROM tbl_parentescos t0 WHERE t0._id = :id")
    Optional<Parentescos> findById(Long id);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(List<Parentescos> entities);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    Long insert(Parentescos entity);

    @Update()
    void update(Parentescos entity);

    @Delete()
    void delete(Parentescos entity);


}

