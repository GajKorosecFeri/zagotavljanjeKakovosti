package com.example.backend.rest;


import com.example.backend.model.Opravilo;
import com.example.backend.model.Priloga;
import com.example.backend.model.Uporabnik;
import com.example.backend.repo.OpraviloRepo;
import com.example.backend.repo.PrilogaRepo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.repository.support.Repositories;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockMultipartFile;
import org.testng.Assert;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class PrilogaControllerTest {

    @InjectMocks
    private PrilogaController prilogaController;

    @Mock
    private OpraviloRepo opraviloRepo;

    @Mock
    private  PrilogaRepo prilogaRepo;

    private Opravilo testOpravilo;



    @BeforeEach
    void setUp(){
        MockitoAnnotations.openMocks(this);
        MockitoAnnotations.openMocks(this);
        testOpravilo = new Opravilo();
        testOpravilo.setId(1L);
        testOpravilo.setAktivnost("Test Aktivnost");
        testOpravilo.setOpis("Opis opravila");
        testOpravilo.setOpravljeno(false);
        testOpravilo.setDatumCas(LocalDateTime.now());
        testOpravilo.setReminderMethod("EMAIL");


        Uporabnik uporabnik = new Uporabnik();
        uporabnik.setId(1L);
        testOpravilo.setUporabnik(uporabnik);

    }



    @Test
    void testDodajPrilogoSuccess(){
        MockMultipartFile file = new MockMultipartFile("priloga", "testfile.txt", "text/plain", "test content".getBytes());


        when(opraviloRepo.findById(1L)).thenReturn(Optional.of(testOpravilo));
        ResponseEntity<String> response = prilogaController.dodajPrilogo(1L, file);
        assertEquals(200, response.getStatusCodeValue(), "HTTP status should be 200");
        assertEquals("Priloga uspešno dodana.", response.getBody());


        verify(opraviloRepo, times(1)).findById(1L);
        verify(prilogaRepo, times(1)).save(any(Priloga.class)); //verufija ali se klice save metoda

    }


    @Test
    void testDodajPrilogoOpraviloNotFound(){
        MockMultipartFile file = new MockMultipartFile("priloga", "testfile.txt", "text/plain", "test content".getBytes());

        when(opraviloRepo.findById(1L)).thenReturn(Optional.empty());
        ResponseEntity<String> response = prilogaController.dodajPrilogo(1L,file);

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        assertEquals("Napaka pri nalaganju priloge: " + "Opravilo ne obstaja", response.getBody());

        verify(opraviloRepo, times(1)).findById(1L);
        verify(prilogaRepo, never()).save(any(Priloga.class));

    }


}
