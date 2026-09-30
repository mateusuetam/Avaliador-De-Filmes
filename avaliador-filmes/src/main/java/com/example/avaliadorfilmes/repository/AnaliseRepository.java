package com.example.avaliadorfilmes.repository;

import com.example.avaliadorfilmes.model.Analise;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface AnaliseRepository extends JpaRepository<Analise, Long> {

    List<Analise> findByFilmeId(Long filmeId);
}
