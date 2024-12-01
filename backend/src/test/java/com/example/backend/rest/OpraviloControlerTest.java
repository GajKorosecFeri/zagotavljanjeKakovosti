package com.example.backend.rest;

import com.example.backend.rest.OpraviloController;
import com.example.backend.model.Opravilo;
import com.example.backend.repo.OpraviloRepo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class OpraviloControlerTest {
    @Mock
    private OpraviloRepo opraviloRepo;

    @InjectMocks
    private OpraviloController opraviloController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testPridobiVsaOpravila() {
        // Arrange
        List<Opravilo> opravila = new ArrayList<>();
        opravila.add(new Opravilo(1L, "Task 1", "Description 1", false, null, null, null));
        opravila.add(new Opravilo(2L, "Task 2", "Description 2", true, null, null, null));

        when(opraviloRepo.findAll()).thenReturn(opravila);

        // Act
        List<Opravilo> result = opraviloController.pridobiVsaOpravila(null);

        // Assert
        assertEquals(2, result.size());
        verify(opraviloRepo, times(1)).findAll();
    }

    @Test
    void testPridobiOpraviloPoId_Success() {
        // Arrange
        Opravilo opravilo = new Opravilo(1L, "Task 1", "Description 1", false, null, null, null);
        when(opraviloRepo.findById(1L)).thenReturn(Optional.of(opravilo));

        // Act
        ResponseEntity<Opravilo> result = opraviloController.pridobiOpraviloPoId(1L);

        // Assert
        assertNotNull(result);
        assertEquals(opravilo, result.getBody());
        verify(opraviloRepo, times(1)).findById(1L);
    }

    @Test
    void testPridobiOpraviloPoId_NotFound() {
        // Arrange
        when(opraviloRepo.findById(1L)).thenReturn(Optional.empty());

        // Act & Assert
        RuntimeException exception = assertThrows(RuntimeException.class, () -> opraviloController.pridobiOpraviloPoId(1L));
        assertEquals("Opravilo ni bilo najdeno", exception.getMessage());
        verify(opraviloRepo, times(1)).findById(1L);
    }

    @Test
    void testUstvariOpravilo() {
        // Arrange
        Opravilo opravilo = new Opravilo(1L, "Task 1", "Description 1", false, null, null, null);
        when(opraviloRepo.save(any(Opravilo.class))).thenReturn(opravilo);

        // Act
        Opravilo result = opraviloController.ustvariOpravilo(opravilo);

        // Assert
        assertNotNull(result);
        assertEquals(opravilo.getAktivnost(), result.getAktivnost());
        verify(opraviloRepo, times(1)).save(opravilo);
    }

    @Test
    void testIzbrisiOpravilo_Success() {
        // Arrange
        Opravilo opravilo = new Opravilo(1L, "Task 1", "Description 1", false, null, null, null);
        when(opraviloRepo.findById(1L)).thenReturn(Optional.of(opravilo));

        // Act
        ResponseEntity<Void> result = opraviloController.izbrisiOpravilo(1L);

        // Assert
        assertNotNull(result);
        assertEquals(204, result.getStatusCodeValue());
        verify(opraviloRepo, times(1)).delete(opravilo);
    }

    @Test
    void testIzbrisiOpravilo_NotFound() {
        // Arrange
        when(opraviloRepo.findById(1L)).thenReturn(Optional.empty());

        // Act & Assert
        RuntimeException exception = assertThrows(RuntimeException.class, () -> opraviloController.izbrisiOpravilo(1L));
        assertEquals("Opravilo ni bilo najdeno", exception.getMessage());
        verify(opraviloRepo, times(0)).delete(any(Opravilo.class));
    }
}
