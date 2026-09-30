package com.example.avaliadorfilmes.controller;

import com.example.avaliadorfilmes.dto.AnaliseRequest;
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
@RequestMapping("/api/analises")
public class AnaliseRestController {

    private final AnaliseRepository analiseRepository;
    private final FilmeRepository filmeRepository;

    public AnaliseRestController(AnaliseRepository analiseRepository, FilmeRepository filmeRepository) {
        this.analiseRepository = analiseRepository;
        this.filmeRepository = filmeRepository;
    }

    @GetMapping
    public List<Analise> listarAnalises() {
        return analiseRepository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Analise> buscarAnalise(@PathVariable Long id) {
        return analiseRepository.findById(id).map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<?> cadastrarAnalise(@RequestBody AnaliseRequest dados) {

        Filme filme = filmeRepository.findById(dados.getFilmeId()).orElse(null);

        if (filme == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Filme não encontrado.");
        }

        if (dados.getNota() == null || dados.getNota() < 0 || dados.getNota() > 10) {
            return ResponseEntity.badRequest().body("A nota deve estar entre 0 e 10.");
        }

        Analise analise = new Analise();

        analise.setFilme(filme);
        analise.setNota(dados.getNota());
        analise.setComentario(dados.getComentario());

        Analise salva = analiseRepository.save(analise);

        return ResponseEntity.status(HttpStatus.CREATED).body(salva);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> atualizarAnalise(@PathVariable Long id, @RequestBody AnaliseRequest dados) {

        Analise analise = analiseRepository.findById(id).orElse(null);

        if (analise == null) {
            return ResponseEntity.notFound().build();
        }

        Filme filme = filmeRepository.findById(dados.getFilmeId()).orElse(null);

        if (filme == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Filme não encontrado.");
        }

        if (dados.getNota() == null || dados.getNota() < 0 || dados.getNota() > 10) {
            return ResponseEntity.badRequest().body("A nota deve estar entre 0 e 10.");
        }

        analise.setFilme(filme);
        analise.setNota(dados.getNota());
        analise.setComentario(dados.getComentario());

        Analise atualizada = analiseRepository.save(analise);

        return ResponseEntity.ok(atualizada);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> excluirAnalise(@PathVariable Long id) {

        if (!analiseRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }

        analiseRepository.deleteById(id);

        return ResponseEntity.noContent().build();
    }
}
