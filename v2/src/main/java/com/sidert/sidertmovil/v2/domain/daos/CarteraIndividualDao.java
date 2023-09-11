package com.sidert.sidertmovil.v2.domain.daos;

import com.sidert.sidertmovil.v2.domain.entities.CarteraIndividual;

import java.util.List;
import java.util.Optional;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;
import androidx.room.Delete;

@Dao
public interface CarteraIndividualDao {

    @Query("SELECT * FROM cartera_individual_t")
    List<CarteraIndividual> getAll();

    @Query("SELECT * FROM cartera_individual_t t0 WHERE t0._id = :id")
    Optional<CarteraIndividual> findById(Long id);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(List<CarteraIndividual> entities);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    Long insert(CarteraIndividual entity);

    @Update()
    void update(CarteraIndividual entity);

    @Delete()
    void delete(CarteraIndividual entity);


}

