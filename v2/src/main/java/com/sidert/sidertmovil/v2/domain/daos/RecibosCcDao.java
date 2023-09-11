package com.sidert.sidertmovil.v2.domain.daos;

import com.sidert.sidertmovil.v2.domain.entities.RecibosCc;

import java.util.List;
import java.util.Optional;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;
import androidx.room.Delete;

@Dao
public interface RecibosCcDao {

    @Query("SELECT * FROM tbl_recibos_cc")
    List<RecibosCc> getAll();

    @Query("SELECT * FROM tbl_recibos_cc t0 WHERE t0._id = :id")
    Optional<RecibosCc> findById(Long id);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(List<RecibosCc> entities);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    Long insert(RecibosCc entity);

    @Update()
    void update(RecibosCc entity);

    @Delete()
    void delete(RecibosCc entity);


    @Query("SELECT * FROM tbl_recibos_cc t0 WHERE t0.curp = :curp and t0.tipo_impresion = :tipoImpresion")
    Optional<RecibosCc> findByCurpAndTipoImpresion(String curp, String tipoImpresion);
}

