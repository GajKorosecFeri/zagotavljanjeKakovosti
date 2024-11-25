package com.example.backend.repo;

import com.example.backend.model.Opravilo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface OpraviloRepo extends JpaRepository<Opravilo, Long> {
        List<Opravilo> findByAktivnostContainingIgnoreCase(String aktivnost);

        List<Opravilo> findBydatumCas(LocalDateTime cas);

        List<Opravilo> findByUporabnikId(Long uporabnikId);

        //Poišče opravila, kjer je metoda opomnika določena in čas v določenem intervalu
        List<Opravilo> findByReminderMethodAndDatumCasBetween(
                String reminderMethod, LocalDateTime start, LocalDateTime end
        );


}
