package com.example.backend.repo;
import com.example.backend.model.Priloga;
import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface PrilogaRepo extends JpaRepository<Priloga, Long> {

    @Query("SELECT p FROM Priloga p WHERE p.opravilo.id = :opraviloId")
    List<Priloga> findAllByOpraviloId(Long opraviloId);

    // Lahko dodate dodatne metode, če so potrebne
}
