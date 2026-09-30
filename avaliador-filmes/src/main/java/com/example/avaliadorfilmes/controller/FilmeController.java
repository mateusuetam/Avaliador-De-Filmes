package com.example.avaliadorfilmes.controller;

import com.example.avaliadorfilmes.model.Filme;
import com.example.avaliadorfilmes.repository.FilmeRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/filmes")
public class FilmeController {

    private final FilmeRepository filmeRepository;

    public FilmeController(FilmeRepository filmeRepository) {
        this.filmeRepository = filmeRepository;
    }

    @GetMapping
    public String listarFilmes(Model model) {
        model.addAttribute("filmes", filmeRepository.findAll());

        return "filmes";
    }

    @GetMapping("/novo")
    public String novoFilme(Model model) {
        model.addAttribute("filme", new Filme());

        return "filme-form";
    }

    @PostMapping
    public String cadastrarFilme(@ModelAttribute Filme filme) {
        filme.setId(null);
        filmeRepository.save(filme);

        return "redirect:/filmes";
    }
}
