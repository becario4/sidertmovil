package com.sidert.sidertmovil.v2.domain.daos;

import com.sidert.sidertmovil.v2.domain.entities.DatosEntregaCartera;

import java.util.List;
import java.util.Optional;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;
import androidx.room.Delete;

@Dao
public interface DatosEntregaCarteraDao {

    @Query("SELECT * FROM tbl_datos_entrega_cartera")
    List<DatosEntregaCartera> getAll();

    @Query("SELECT * FROM tbl_datos_entrega_cartera t0 WHERE t0._id = :id")
    Optional<DatosEntregaCartera> findById(Long id);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(List<DatosEntregaCartera> entities);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    Long insert(DatosEntregaCartera entity);

    @Update()
    void update(DatosEntregaCartera entity);

    @Delete()
    void delete(DatosEntregaCartera entity);


}

