package com.sidert.sidertmovil.v2.domain.daos;

import com.sidert.sidertmovil.v2.domain.entities.ClienteIndRen;

import java.util.List;
import java.util.Optional;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;
import androidx.room.Delete;

@Dao
public interface ClienteIndRenDao {

    @Query("SELECT * FROM tbl_cliente_ind_ren")
    List<ClienteIndRen> getAll();

    @Query("SELECT * FROM tbl_cliente_ind_ren t0 WHERE t0.id_cliente = :id")
    Optional<ClienteIndRen> findById(Long id);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(List<ClienteIndRen> entities);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    Long insert(ClienteIndRen entity);

    @Update()
    void update(ClienteIndRen entity);

    @Delete()
    void delete(ClienteIndRen entity);


}

