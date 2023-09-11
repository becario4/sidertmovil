package com.sidert.sidertmovil.v2.domain.daos;

import com.sidert.sidertmovil.v2.domain.entities.PrestamosToRenovar;
import com.sidert.sidertmovil.v2.domain.entities.SolicitudesRen;

import java.util.List;
import java.util.Optional;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;
import androidx.room.Delete;

@Dao
public interface PrestamosToRenovarDao {

    @Query("SELECT * FROM tbl_prestamos_to_renovar")
    List<PrestamosToRenovar> getAll();

    @Query("SELECT * FROM tbl_prestamos_to_renovar t0 WHERE t0._id = :id")
    Optional<PrestamosToRenovar> findById(Long id);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(List<PrestamosToRenovar> entities);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    Long insert(PrestamosToRenovar entity);

    @Update()
    void update(PrestamosToRenovar entity);

    @Delete()
    void delete(PrestamosToRenovar entity);

    @Query("SELECT * FROM tbl_prestamos_to_renovar t0 WHERE trim(t0.cliente_nombre) = trim(:clienteNombre) ORDER BY t0.fecha_vencimiento LIMIT 1 OFFSET :index ")
    Optional<PrestamosToRenovar> findByClienteNombreAndPosition(String clienteNombre, int index);

    @Query("SELECT * FROM tbl_prestamos_to_renovar t0 WHERE t0.asesor_id = :asesorId and t0.prestamo_id = :prestamoId and t0.cliente_id = :clienteId LIMIT 1")
    Optional<PrestamosToRenovar> findByAsesorIdAndPrestamoIdAndClienteId(String asesorId, Integer prestamoId, String clienteId);

    @Query("SELECT * FROM tbl_prestamos_to_renovar t0 WHERE t0.asesor_id = :asesorId and t0.grupo_id = :grupoId and t0.cliente_id = :clienteId LIMIT 1")
    Optional<PrestamosToRenovar> findByAsesorIdAndGrupoIdAndClienteId(String asesorId, Integer grupoId, String clienteId);

    @Query("SELECT * FROM tbl_prestamos_to_renovar t0 WHERE t0.descargado = :estatus LIMIT 1")
    List<PrestamosToRenovar> findByDescargoStatus(int estatus);

    @Query("UPDATE tbl_prestamos_to_renovar SET descargado = 1 WHERE grupo_id = :grupoPorRenovarId")
    void updateDownloadStatusByGrupoId(Integer grupoPorRenovarId);

    @Query("UPDATE tbl_prestamos_to_renovar SET descargado = 1 WHERE prestamo_id = :prestamoToRenovarRemoteId and cliente_id = :prestamoToRenovarClienteId")
    void updateDownloadStatusByPrestamoIdAndClienteId(Integer prestamoToRenovarRemoteId, Integer prestamoToRenovarClienteId);

}

