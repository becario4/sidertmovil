package com.sidert.sidertmovil.v2.domain.daos;

import com.sidert.sidertmovil.v2.domain.entities.Tickets;

import java.util.List;
import java.util.Optional;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;
import androidx.room.Delete;

@Dao
public interface TicketsDao {

    @Query("SELECT * FROM tickets")
    List<Tickets> getAll();

    @Query("SELECT * FROM tickets t0 WHERE t0._id = :id")
    Optional<Tickets> findById(Long id);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(List<Tickets> entities);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    Long insert(Tickets entity);

    @Update()
    void update(Tickets entity);

    @Delete()
    void delete(Tickets entity);


}

