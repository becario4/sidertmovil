package com.sidert.sidertmovil.v2.utils;

import java.io.Serializable;

public final class PrestamoIdTipo implements Serializable {
    private final int id;
    private final int tipo;

    public PrestamoIdTipo(int id, int tipo) {
        this.id = id;
        this.tipo = tipo;
    }

    public int getId() {
        return id;
    }

    public int getTipo() {
        return tipo;
    }
}