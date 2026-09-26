package com.example.avaliadorfilmes.model;

public class Analise {

    private Long id;
    private Filme filme;
    private Integer nota;
    private String comentario;

    public Analise() {
    }

    public Analise(Long id, Filme filme, Integer nota, String comentario) {
        this.id = id;
        this.filme = filme;
        this.nota = nota;
        this.comentario = comentario;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Filme getFilme() {
        return filme;
    }

    public void setFilme(Filme filme) {
        this.filme = filme;
    }

    public Integer getNota() {
        return nota;
    }

    public void setNota(Integer nota) {
        this.nota = nota;
    }

    public String getComentario() {
        return comentario;
    }

    public void setComentario(String comentario) {
        this.comentario = comentario;
    }
}
