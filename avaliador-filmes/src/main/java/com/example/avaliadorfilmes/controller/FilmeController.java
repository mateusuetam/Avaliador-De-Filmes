package com.example.avaliadorfilmes.controller;

import com.example.avaliadorfilmes.model.Filme;
import com.example.avaliadorfilmes.service.MemoriaService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/filmes")
public class FilmeController {

    private final MemoriaService memoriaService;

    public FilmeController(MemoriaService memoriaService) {
        this.memoriaService = memoriaService;
    }

    @GetMapping
    public String listarFilmes(Model model) {
        model.addAttribute("filmes", memoriaService.listarFilmes());
        return "filmes";
    }

    @GetMapping("/novo")
    public String novoFilme(Model model) {
        model.addAttribute("filme", new Filme());
        return "filme-form";
    }

    @PostMapping
    public String cadastrarFilme(@ModelAttribute Filme filme) {
        memoriaService.adicionarFilme(filme);
        return "redirect:/filmes";
    }
}
