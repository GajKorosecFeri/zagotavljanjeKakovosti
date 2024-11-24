package com.example.backend.repo;

import com.example.backend.model.Uporabnik;
import org.springframework.data.jpa.repository.JpaRepository;

public interface    UporabnikRepo extends JpaRepository<Uporabnik, Long> {
}
