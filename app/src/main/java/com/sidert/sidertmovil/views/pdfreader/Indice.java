package com.sidert.sidertmovil.views.pdfreader;

public class Indice {
    private Integer pagina;
    private String titulo;

    public Indice(Integer pagina, String titulo){
        this.pagina = pagina;
        this.titulo = titulo;
    }

    public Integer getPagina() {
        return pagina;
    }

    public void setPagina(Integer pagina) {
        this.pagina = pagina;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }
}
