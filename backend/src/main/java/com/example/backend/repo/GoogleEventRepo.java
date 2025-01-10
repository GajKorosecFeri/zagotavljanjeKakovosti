package com.example.backend.repo;

import com.example.backend.model.GoogleEvent;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GoogleEventRepo extends JpaRepository<GoogleEvent, Long> {
}
