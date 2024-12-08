package com.example.backend.repo;
import com.example.backend.model.Priloga;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PrilogaRepo extends JpaRepository<Priloga, Long> {
    // Lahko dodate dodatne metode, če so potrebne
}
