package com.example.backend.rest;

import org.junit.jupiter.api.Test;
import com.example.backend.model.Opravilo;
import com.example.backend.model.Uporabnik;
import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;
public class OpraviloTest {
    @Test
    void testOpraviloFields() {
        // Create a test user
        Uporabnik uporabnik = new Uporabnik();
        uporabnik.setId(1L);
        uporabnik.setName("Test User");
        uporabnik.setEmail("test@example.com");

        // Create a test task
        Opravilo opravilo = new Opravilo();
        opravilo.setId(1L);
        opravilo.setAktivnost("Test Activity");
        opravilo.setOpis("This is a test description.");
        opravilo.setOpravljeno(false);
        opravilo.setDatumCas(LocalDateTime.of(2024, 12, 1, 10, 30));
        opravilo.setReminderMethod("email");
        opravilo.setUporabnik(uporabnik);

        // Verify the fields
        assertThat(opravilo.getId()).isEqualTo(1L);
        assertThat(opravilo.getAktivnost()).isEqualTo("Test Activity");
        assertThat(opravilo.getOpis()).isEqualTo("This is a test description.");
        assertThat(opravilo.isOpravljeno()).isFalse();
        assertThat(opravilo.getDatumCas()).isEqualTo(LocalDateTime.of(2024, 12, 1, 10, 30));
        assertThat(opravilo.getReminderMethod()).isEqualTo("email");
        assertThat(opravilo.getUporabnik()).isEqualTo(uporabnik);
    }

    @Test
    void testToStringAndLombokFeatures() {
        // Create a test task using the all-args constructor
        Uporabnik uporabnik = new Uporabnik(1L, "Test User", "test@example.com", "123456789", null);
        Opravilo opravilo = new Opravilo(1L, "Test Activity", "This is a test description.",
                true, LocalDateTime.of(2024, 12, 1, 10, 30), "email", uporabnik);

        // Verify Lombok-generated methods
        assertThat(opravilo.toString()).contains("Test Activity", "Test User", "email");
        assertThat(opravilo.equals(opravilo)).isTrue(); // Same instance
        assertThat(opravilo.hashCode()).isNotZero(); // Hashcode should be non-zero
    }
}
