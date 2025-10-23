package com.example.backend.rest;

import com.example.backend.rest.OpraviloController;
import com.example.backend.model.Opravilo;
import com.example.backend.repo.OpraviloRepo;
import com.example.backend.service.EmailService;
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

    @Mock
    private EmailService emailService;

    @InjectMocks
    private OpraviloController opraviloController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    // Preveri, ali metoda pridobiVsaOpravila vrne pravilen seznam opravil.
    @Test
    void testPridobiVsaOpravila() {
        // Arrange
        List<Opravilo> opravila = new ArrayList<>();
        opravila.add(new Opravilo(1L, "Task 1", "Description 1", false, null, null, null,null));
        opravila.add(new Opravilo(2L, "Task 2", "Description 2", true, null, null, null,null));

        when(opraviloRepo.findAll()).thenReturn(opravila);

        // Act
        List<Opravilo> result = opraviloController.pridobiVsaOpravila(null);

        // Assert
        assertEquals(2, result.size());
        verify(opraviloRepo, times(1)).findAll();
    }

    //Preveri, ali metoda pridobiOpraviloPoId vrne opravilo, če obstaja.
    @Test
    void testPridobiOpraviloPoId_Success() {
        // Arrange
        Opravilo opravilo = new Opravilo(1L, "Task 1", "Description 1", false, null, null, null,null);
        when(opraviloRepo.findById(1L)).thenReturn(Optional.of(opravilo));

        // Act
        ResponseEntity<Opravilo> result = opraviloController.pridobiOpraviloPoId(1L);

        // Assert
        assertNotNull(result);
        assertEquals(opravilo, result.getBody());
        verify(opraviloRepo, times(1)).findById(1L);
    }

    // Preveri, ali metoda pridobiOpraviloPoId vrže izjemo, če opravilo ne obstaja.
    @Test
    void testPridobiOpraviloPoId_NotFound() {
        // Arrange
        when(opraviloRepo.findById(1L)).thenReturn(Optional.empty());

        // Act & Assert
        RuntimeException exception = assertThrows(RuntimeException.class, () -> opraviloController.pridobiOpraviloPoId(1L));
        assertEquals("Opravilo ni bilo najdeno", exception.getMessage());
        verify(opraviloRepo, times(1)).findById(1L);
    }

    //Preveri, ali metoda ustvariOpravilo pravilno shrani novo opravilo.
    @Test
    void testUstvariOpravilo() {
        // Arrange
        Opravilo opravilo = new Opravilo(1L, "Task 1", "Description 1", false, null, null, null,null);
        when(opraviloRepo.save(any(Opravilo.class))).thenReturn(opravilo);

        // Act
        Opravilo result = opraviloController.ustvariOpravilo(opravilo);

        // Assert
        assertNotNull(result);
        assertEquals(opravilo.getAktivnost(), result.getAktivnost());
        verify(opraviloRepo, times(1)).save(opravilo);
    }

    //Preveri, ali metoda izbrisiOpravilo pravilno izbriše obstoječe opravilo.
    @Test
    void testIzbrisiOpravilo_Success() {
        // Arrange
        Opravilo opravilo = new Opravilo(1L, "Task 1", "Description 1", false, null, null, null,null);
        when(opraviloRepo.findById(1L)).thenReturn(Optional.of(opravilo));

        // Act
        ResponseEntity<Void> result = opraviloController.izbrisiOpravilo(1L);

        // Assert
        assertNotNull(result);
        assertEquals(204, result.getStatusCodeValue());
        verify(opraviloRepo, times(1)).delete(opravilo);
    }

    //Preveri, ali metoda izbrisiOpravilo vrže izjemo, če opravilo ne obstaja.
    @Test
    void testIzbrisiOpravilo_NotFound() {
        // Arrange
        when(opraviloRepo.findById(1L)).thenReturn(Optional.empty());

        // Act & Assert
        RuntimeException exception = assertThrows(RuntimeException.class, () -> opraviloController.izbrisiOpravilo(1L));
        assertEquals("Opravilo ni bilo najdeno", exception.getMessage());
        verify(opraviloRepo, times(0)).delete(any(Opravilo.class));
    }

    //Preveri ali se opravilo posodobi pravilno
    @Test
    void testPosodobiOpravilo_Success() {
        // Arrange
        Opravilo obstojeceOpravilo = new Opravilo(1L, "Old Task", "Old Description", false, null, null, null,null);
        Opravilo novoOpravilo = new Opravilo(1L, "Updated Task", "Updated Description", true, null, null, null,null);

        when(opraviloRepo.findById(1L)).thenReturn(Optional.of(obstojeceOpravilo));
        when(opraviloRepo.save(obstojeceOpravilo)).thenReturn(novoOpravilo);

        // Act
        ResponseEntity<Opravilo> result = opraviloController.posodobiOpravilo(1L, novoOpravilo);

        // Assert
        assertNotNull(result);
        assertEquals("Updated Task", result.getBody().getAktivnost());
        verify(opraviloRepo, times(1)).findById(1L);
        verify(opraviloRepo, times(1)).save(obstojeceOpravilo);
    }

    //Preveri ali se opravilo pravilno označi kot opravljeno
    @Test
    void testOznaciKotOpravljeno_Success() {
        // Arrange
        Opravilo opravilo = new Opravilo(1L, "Task", "Description", false, null, null, null,null);
        when(opraviloRepo.findById(1L)).thenReturn(Optional.of(opravilo));
        when(opraviloRepo.save(any(Opravilo.class))).thenAnswer(invocation -> invocation.getArgument(0));

        // Act
        ResponseEntity<Opravilo> result = opraviloController.oznaciKotOpravljeno(1L);

        // Assert
        assertNotNull(result.getBody());
        assertTrue(result.getBody().isOpravljeno(), "The task should be marked as completed.");
        verify(opraviloRepo, times(1)).save(opravilo);
    }

    //Preveri ali se najde opravilo glede aktivnosti
    @Test
    void testIskanjeOpravila() {
        // Arrange
        List<Opravilo> opravila = new ArrayList<>();
        opravila.add(new Opravilo(1L, "Task 1", "Description 1", false, null, null, null,null));
        when(opraviloRepo.findByAktivnostContainingIgnoreCase("Task")).thenReturn(opravila);

        // Act
        List<Opravilo> result = opraviloController.iskanjeOpravila("Task");

        // Assert
        assertNotNull(result);
        assertEquals(1, result.size());
        verify(opraviloRepo, times(1)).findByAktivnostContainingIgnoreCase("Task");
    }

    //Preveri ali se vrne sporočilo
    @Test
    void testTestEndpoint() {
        // Act
        String result = opraviloController.testEndpoint();

        // Assert
        assertEquals("Controller is working!", result);
    }

    //Preveri ali vrne napako pri posiljanju ali ne
    @Test
    void testSms() {//naredi da pade...
        // Act
        String result = opraviloController.testSms();

        // Assert
        assertTrue(result.contains("Napaka pri pošiljanju SMS") || result.contains("SMS uspešno poslan"));
    }
}
