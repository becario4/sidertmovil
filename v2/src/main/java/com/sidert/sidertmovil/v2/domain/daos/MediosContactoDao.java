package com.sidert.sidertmovil.v2.domain.daos;

import com.sidert.sidertmovil.v2.domain.entities.MediosContacto;

import java.util.List;
import java.util.Optional;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;
import androidx.room.Delete;

@Dao
public interface MediosContactoDao {

    @Query("SELECT * FROM tbl_medios_contacto")
    List<MediosContacto> getAll();

    @Query("SELECT * FROM tbl_medios_contacto t0 WHERE t0._id = :id")
    Optional<MediosContacto> findById(Long id);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(List<MediosContacto> entities);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    Long insert(MediosContacto entity);

    @Update()
    void update(MediosContacto entity);

    @Delete()
    void delete(MediosContacto entity);


}

