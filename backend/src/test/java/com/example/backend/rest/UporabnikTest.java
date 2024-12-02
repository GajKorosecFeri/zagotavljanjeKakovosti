package com.example.backend.rest;

import com.example.backend.model.Uporabnik;
import com.example.backend.model.Opravilo;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class UporabnikTest {

    @Test
    void testUporabnikFields() {
        // Create a list of tasks (opravila)
        List<Opravilo> opravila = new ArrayList<>();
        opravila.add(new Opravilo(1L, "Task 1", "Description 1", false, null, "email", null));
        opravila.add(new Opravilo(2L, "Task 2", "Description 2", true, null, "sms", null));

        // Create a user (uporabnik)
        Uporabnik uporabnik = new Uporabnik();
        uporabnik.setId(1L);
        uporabnik.setName("Test User");
        uporabnik.setEmail("test@example.com");
        uporabnik.setPhoneNumber("123456789");
        uporabnik.setOpravila(opravila);

        // Verify the fields
        assertThat(uporabnik.getId()).isEqualTo(1L);
        assertThat(uporabnik.getName()).isEqualTo("Test User");
        assertThat(uporabnik.getEmail()).isEqualTo("test@example.com");
        assertThat(uporabnik.getPhoneNumber()).isEqualTo("123456789");
        assertThat(uporabnik.getOpravila()).hasSize(2);
    }

    @Test
    void testOpravilaAssociation() {
        // Create a user
        Uporabnik uporabnik = new Uporabnik(1L, "Test User", "test@example.com", "123456789", new ArrayList<>());

        // Create and associate tasks (opravila)
        Opravilo opravilo1 = new Opravilo(1L, "Task 1", "Description 1", false, null, "email", uporabnik);
        Opravilo opravilo2 = new Opravilo(2L, "Task 2", "Description 2", true, null, "sms", uporabnik);

        uporabnik.getOpravila().add(opravilo1);
        uporabnik.getOpravila().add(opravilo2);

        // Verify the association
        assertThat(uporabnik.getOpravila()).contains(opravilo1, opravilo2);
        assertThat(opravilo1.getUporabnik()).isEqualTo(uporabnik);
        assertThat(opravilo2.getUporabnik()).isEqualTo(uporabnik);
    }

    @Test
    void testToStringAndLombokFeatures() {
        // Create a user with the all-args constructor
        List<Opravilo> opravila = new ArrayList<>();
        opravila.add(new Opravilo(1L, "Task 1", "Description 1", false, null, "email", null));
        Uporabnik uporabnik = new Uporabnik(1L, "Test User", "test@example.com", "123456789", opravila);

        // Verify Lombok-generated methods
        assertThat(uporabnik.toString()).contains("Test User", "test@example.com");
        assertThat(uporabnik.equals(uporabnik)).isTrue(); // Same instance
        assertThat(uporabnik.hashCode()).isNotZero(); // Hashcode should be non-zero
    }
}

