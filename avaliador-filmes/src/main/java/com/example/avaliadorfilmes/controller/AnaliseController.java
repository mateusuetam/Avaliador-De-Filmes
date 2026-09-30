package com.example.avaliadorfilmes.controller;

import com.example.avaliadorfilmes.model.Analise;
import com.example.avaliadorfilmes.model.Filme;
import com.example.avaliadorfilmes.repository.AnaliseRepository;
import com.example.avaliadorfilmes.repository.FilmeRepository;
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

    private final AnaliseRepository analiseRepository;
    private final FilmeRepository filmeRepository;

    public AnaliseController(AnaliseRepository analiseRepository, FilmeRepository filmeRepository) {
        this.analiseRepository = analiseRepository;
        this.filmeRepository = filmeRepository;
    }

    @GetMapping
    public String listarAnalises(Model model) {
        model.addAttribute("analises", analiseRepository.findAll());
        return "analises";
    }

    @GetMapping("/nova")
    public String novaAnalise(@RequestParam Long filmeId, Model model) {

        Filme filme = filmeRepository.findById(filmeId).orElse(null);

        if (filme == null) {
            return "redirect:/filmes";
        }

        model.addAttribute("filme", filme);
        model.addAttribute("analise", new Analise());

        return "analise-form";
    }

    @PostMapping
    public String cadastrarAnalise(@RequestParam Long filmeId, @ModelAttribute Analise analise) {

        Filme filme = filmeRepository.findById(filmeId).orElse(null);

        if (filme == null) {
            return "redirect:/filmes";
        }

        analise.setId(null);
        analise.setFilme(filme);

        analiseRepository.save(analise);

        return "redirect:/analises";
    }
}
