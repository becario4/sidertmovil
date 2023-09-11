package com.sidert.sidertmovil.v2.domain.daos;

import com.sidert.sidertmovil.v2.domain.entities.TelefonosIntegranteRen;

import java.util.List;
import java.util.Optional;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;
import androidx.room.Delete;

@Dao
public interface TelefonosIntegranteRenDao {

    @Query("SELECT * FROM tbl_telefonos_integrante_ren")
    List<TelefonosIntegranteRen> getAll();

    @Query("SELECT * FROM tbl_telefonos_integrante_ren t0 WHERE t0.id_telefonico = :id")
    Optional<TelefonosIntegranteRen> findById(Long id);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(List<TelefonosIntegranteRen> entities);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    Long insert(TelefonosIntegranteRen entity);

    @Update()
    void update(TelefonosIntegranteRen entity);

    @Delete()
    void delete(TelefonosIntegranteRen entity);


}

