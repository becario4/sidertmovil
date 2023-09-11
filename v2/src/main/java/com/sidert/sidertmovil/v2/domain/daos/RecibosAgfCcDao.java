package com.sidert.sidertmovil.v2.domain.daos;

import com.sidert.sidertmovil.v2.domain.entities.RecibosAgfCc;

import java.util.List;
import java.util.Optional;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;
import androidx.room.Delete;

@Dao
public interface RecibosAgfCcDao {

    @Query("SELECT * FROM tbl_recibos_agf_cc")
    List<RecibosAgfCc> getAll();

    @Query("SELECT * FROM tbl_recibos_agf_cc t0 WHERE t0._id = :id")
    Optional<RecibosAgfCc> findById(Long id);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(List<RecibosAgfCc> entities);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    Long insert(RecibosAgfCc entity);

    @Update()
    void update(RecibosAgfCc entity);

    @Delete()
    void delete(RecibosAgfCc entity);


    @Query("SELECT t0.* FROM tbl_recibos_agf_cc t0 WHERE t0.grupo_id = :grupoId and t0.num_solicitud = :numSolicitud and t0.tipo_impresion = :tipoImpresion ORDER BY t0.fecha_impresion DESC LIMIT 1")
    Optional<RecibosAgfCc> findByGrupoIdAndNumSolicitudAndTipoImpresion(String grupoId, String numSolicitud, String tipoImpresion);

    @Query("SELECT t0.* FROM tbl_recibos_agf_cc t0 WHERE t0.nombre = :nombre and t0.num_solicitud = :numSolicitud and t0.tipo_impresion = :tipoImpresion ORDER BY t0.fecha_impresion DESC LIMIT 1")
    Optional<RecibosAgfCc> findByNombreAndNumSolicitudAndTipoImpresion(String nombre, String numSolicitud, String tipoImpresion);
}

