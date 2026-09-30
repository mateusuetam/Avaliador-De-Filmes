package com.example.avaliadorfilmes.repository;

import com.example.avaliadorfilmes.model.Filme;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FilmeRepository extends JpaRepository<Filme, Long> {
}
