package com.example.avaliadorfilmes.controller;

import com.example.avaliadorfilmes.model.Analise;
import com.example.avaliadorfilmes.model.Filme;
import com.example.avaliadorfilmes.repository.AnaliseRepository;
import com.example.avaliadorfilmes.repository.FilmeRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;

@RestController
@RequestMapping("/api/filmes")
public class FilmeRestController {

    private final FilmeRepository filmeRepository;
    private final AnaliseRepository analiseRepository;

    public FilmeRestController(FilmeRepository filmeRepository, AnaliseRepository analiseRepository) {
        this.filmeRepository = filmeRepository;
        this.analiseRepository = analiseRepository;
    }

    @GetMapping
    public List<Filme> listarFilmes() {
        return filmeRepository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Filme> buscarFilme(@PathVariable Long id) {
        return filmeRepository.findById(id).map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Filme> cadastrarFilme(@RequestBody Filme filme) {
        filme.setId(null);
        Filme salvo = filmeRepository.save(filme);
        return ResponseEntity.status(HttpStatus.CREATED).body(salvo);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Filme> atualizarFilme(@PathVariable Long id, @RequestBody Filme dados) {

        return filmeRepository.findById(id).map(filme -> {
            filme.setTitulo(dados.getTitulo());
            filme.setDiretor(dados.getDiretor());
            filme.setAnoLancamento(dados.getAnoLancamento());
            filme.setAssistido(dados.getAssistido());

            Filme atualizado = filmeRepository.save(filme);

            return ResponseEntity.ok(atualizado);
        }).orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> excluirFilme(@PathVariable Long id) {

        if (!filmeRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }

        List<Analise> analises = analiseRepository.findByFilmeId(id);

        analiseRepository.deleteAll(analises);
        filmeRepository.deleteById(id);

        return ResponseEntity.noContent().build();
    }
}
