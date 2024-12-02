package com.example.backend.rest;

import com.example.backend.model.Uporabnik;
import com.example.backend.repo.UporabnikRepo;
import com.example.backend.rest.OpraviloController;
import com.example.backend.model.Opravilo;
import com.example.backend.repo.OpraviloRepo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class) // Use MockitoExtension to initialize mocks
@TestInstance(TestInstance.Lifecycle.PER_CLASS) // Ensure single test class instance
public class UporabnikControllerTest {// za  TestInstance(TestInstance.Lifecycle.PER_CLASS) bypassa da se vsakic naredi nova instanca test classa za vsakic ko se pozene test?


    @Mock
    private UporabnikRepo uporabnikRepo; // Mocked repository

    @InjectMocks
    private UporabnikController uporabnikController; // Controller with mocks injected

    private Uporabnik testUser;
    private Uporabnik testUser2;

    @BeforeEach
    void setUp() {
        // Create a test user
        testUser = new Uporabnik();
        testUser.setId(1L);
        testUser.setName("John Doe");
        testUser.setEmail("johndoe@example.com");

        testUser2 = new Uporabnik();
        testUser2.setId(1L);
        testUser2.setName("Uporabnik 2");
        testUser2.setEmail("test2@example.com");
    }


    @Test
    void testGetAllUporabniki() {


        when(uporabnikRepo.findAll()).thenReturn(List.of(testUser, testUser2)); //inicializira izhod array size 2 v repoju instead of database

        ResponseEntity<List<Uporabnik>> result = uporabnikController.getAllUporabniki(); // restult

        assertNotNull(result);
        assertEquals(2, result.getBody().size()); // Assert that two users are returned
        verify(uporabnikRepo, times(1)).findAll();

    }


    @Test
    void testCreateUporabnik() {
        when(uporabnikRepo.save(any(Uporabnik.class))).thenReturn(testUser);
        ResponseEntity<Uporabnik> result = uporabnikController.createUporabnik((testUser));

        assertNotNull(result);
        assertEquals(testUser.getName(), result.getBody().getName()); // Assert that names match
        assertEquals(200, result.getStatusCodeValue()); // Assert 200 OK response
        verify(uporabnikRepo, times(1)).save(testUser);

    }

    @Test
    void testGetUporabnikById_Success() {

        when(uporabnikRepo.findById(2L)).thenReturn(Optional.of(testUser2));

        ResponseEntity<Uporabnik> result = uporabnikController.getUporabnikById(2L);

        assertNotNull(result);
        assertEquals(testUser2, result.getBody());
        verify(uporabnikRepo, times(1)).findById(2L);
    }


    @Test
    void testGetUporabnikById_NotFound() {
        when(uporabnikRepo.findById(1L)).thenReturn(Optional.empty());

        ResponseEntity<Uporabnik> result = uporabnikController.getUporabnikById(1L);

        assertNotNull(result);
        assertEquals(404, result.getStatusCodeValue()); // Assert 404 Not Found
        verify(uporabnikRepo, times(1)).findById(1L);
    }


    @Test
    void testDeleteUporabnik_Success() {

        when(uporabnikRepo.existsById(1L)).thenReturn(true);

        ResponseEntity<Void> result = uporabnikController.deleteUporabnik(1L);

        assertNotNull(result);
        assertEquals(204, result.getStatusCodeValue()); // Assert 204 No Content
        verify(uporabnikRepo, times(1)).deleteById(1L); //checks if the delete statement was called exactly once with the argument 1L
    }

    @Test
    void testDeleteUporabnik_NotFound() {

        when(uporabnikRepo.existsById(2L)).thenReturn(false);

        ResponseEntity<Void> result = uporabnikController.deleteUporabnik(2L);

        assertNotNull(result);
        assertEquals(404, result.getStatusCodeValue()); // Assert 404 Not Found
        verify(uporabnikRepo, times(0)).deleteById(2L);
    }


















}
