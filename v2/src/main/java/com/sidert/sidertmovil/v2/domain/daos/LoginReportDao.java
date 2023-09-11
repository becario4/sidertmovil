package com.sidert.sidertmovil.v2.domain.daos;

import com.sidert.sidertmovil.v2.domain.entities.LoginReport;

import java.util.List;
import java.util.Optional;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;
import androidx.room.Delete;

@Dao
public interface LoginReportDao {

    @Query("SELECT * FROM login_report_t")
    List<LoginReport> getAll();

    @Query("SELECT * FROM login_report_t t0 WHERE t0._id = :id")
    Optional<LoginReport> findById(Long id);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(List<LoginReport> entities);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    Long insert(LoginReport entity);

    @Update()
    void update(LoginReport entity);

    @Delete()
    void delete(LoginReport entity);

    @Query("SELECT * FROM login_report_t t0 ORDER BY t0._id desc limit 1")
    Optional<LoginReport> findOneOrderByCreateAtDesc();
}

