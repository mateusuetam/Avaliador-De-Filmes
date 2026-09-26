package com.example.avaliadorfilmes.controller;

import com.example.avaliadorfilmes.model.Analise;
import com.example.avaliadorfilmes.model.Filme;
import com.example.avaliadorfilmes.service.MemoriaService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/analises")
public class AnaliseController {

    private final MemoriaService memoriaService;

    public AnaliseController(MemoriaService memoriaService) {
        this.memoriaService = memoriaService;
    }

    @GetMapping
    public String listarAnalises(Model model) {
        model.addAttribute("analises", memoriaService.listarAnalises());
        return "analises";
    }

    @GetMapping("/nova")
    public String novaAnalise(@RequestParam Long filmeId, Model model) {

        Filme filme = memoriaService.buscarFilmePorId(filmeId);

        if (filme == null) {
            return "redirect:/filmes";
        }

        model.addAttribute("filme", filme);
        model.addAttribute("analise", new Analise());

        return "analise-form";
    }

    @PostMapping
    public String cadastrarAnalise(@RequestParam Long filmeId, @ModelAttribute Analise analise) {

        Filme filme = memoriaService.buscarFilmePorId(filmeId);

        if (filme == null) {
            return "redirect:/filmes";
        }

        analise.setFilme(filme);
        memoriaService.adicionarAnalise(analise);

        return "redirect:/analises";
    }
}
