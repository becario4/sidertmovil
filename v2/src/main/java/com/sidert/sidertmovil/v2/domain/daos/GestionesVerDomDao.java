package com.sidert.sidertmovil.v2.domain.daos;

import com.sidert.sidertmovil.v2.domain.entities.GestionesVerDom;

import java.util.List;
import java.util.Optional;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;
import androidx.room.Delete;

@Dao
public interface GestionesVerDomDao {

    @Query("SELECT * FROM tbl_gestiones_ver_dom")
    List<GestionesVerDom> getAll();

    @Query("SELECT * FROM tbl_gestiones_ver_dom t0 WHERE t0._id = :id")
    Optional<GestionesVerDom> findById(Long id);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(List<GestionesVerDom> entities);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    Long insert(GestionesVerDom entity);

    @Update()
    void update(GestionesVerDom entity);

    @Delete()
    void delete(GestionesVerDom entity);


}

