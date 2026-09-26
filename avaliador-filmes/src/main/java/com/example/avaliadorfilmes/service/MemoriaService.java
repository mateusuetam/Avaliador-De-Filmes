package com.example.avaliadorfilmes.service;

import com.example.avaliadorfilmes.model.Analise;
import com.example.avaliadorfilmes.model.Filme;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class MemoriaService {

    private final List<Filme> filmes = new ArrayList<>();
    private final List<Analise> analises = new ArrayList<>();

    private long proximoFilmeId = 1;
    private long proximaAnaliseId = 1;

    public List<Filme> listarFilmes() {
        return new ArrayList<>(filmes);
    }

    public Filme buscarFilmePorId(Long id) {
        return filmes.stream().filter(filme -> filme.getId().equals(id)).findFirst().orElse(null);
    }

    public Filme adicionarFilme(Filme filme) {
        filme.setId(proximoFilmeId++);
        filmes.add(filme);
        return filme;
    }

    public List<Analise> listarAnalises() {
        return new ArrayList<>(analises);
    }

    public Analise adicionarAnalise(Analise analise) {
        analise.setId(proximaAnaliseId++);
        analises.add(analise);
        return analise;
    }
}
