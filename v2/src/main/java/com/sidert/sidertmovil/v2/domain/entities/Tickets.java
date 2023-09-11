package com.sidert.sidertmovil.v2.domain.entities;

import java.io.Serializable;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "tickets")
public final class Tickets implements Serializable {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "_id")
    private Long id;
    @ColumnInfo(name = "ticket_id")
    private Integer ticketId;
    @ColumnInfo(name = "nombre")
    private String nombre;
    @ColumnInfo(name = "prioridad")
    private Integer prioridad;
    @ColumnInfo(name = "estatus")
    private String estatus;


// Getters & Setters

    public Long getId(){
        return this.id;
    }

    public void setId(Long id){
        this.id = id;
    }
    public Integer getTicketId(){
        return this.ticketId;
    }

    public void setTicketId(Integer ticketId){
        this.ticketId = ticketId;
    }
    public String getNombre(){
        return this.nombre;
    }

    public void setNombre(String nombre){
        this.nombre = nombre;
    }
    public Integer getPrioridad(){
        return this.prioridad;
    }

    public void setPrioridad(Integer prioridad){
        this.prioridad = prioridad;
    }
    public String getEstatus(){
        return this.estatus;
    }

    public void setEstatus(String estatus){
        this.estatus = estatus;
    }

}