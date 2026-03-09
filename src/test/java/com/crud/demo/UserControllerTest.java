package com.crud.demo;

import com.crud.demo.controller.UserController;
import com.crud.demo.dto.UserRequestDTO;
import com.crud.demo.dto.UserResponseDTO;
import com.crud.demo.infra.ResourceNotFoundException;
import com.crud.demo.service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.hamcrest.Matchers.endsWith;
import static org.mockito.ArgumentMatchers.eq;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(UserController.class)
public class UserControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @MockitoBean
    private UserService service;

    private ObjectMapper objectMapper;
    private UserResponseDTO responseDTO;
    private UserRequestDTO requestDTO;

    @BeforeEach
    void setUp() {
        objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());

        responseDTO = new UserResponseDTO(
            1L,
            "Tom",
            "Hanks",
            "tom@example.com",
            LocalDateTime.now()
        );

        requestDTO = new UserRequestDTO(
           "Tom",
           "Hanks",
           "tom@example.com",
           "password123"
        );
    }

    @Test
    @DisplayName("GET /users - Should return status 200 OK and user list")
    void findAllWhenUsersExistReturnsOkAndUserList() throws Exception {
        Mockito.when(service.findAll()).thenReturn(List.of(responseDTO));

        mockMvc.perform(get("/users")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1L))
                .andExpect(jsonPath("$[0].firstName").value("Tom"));
    }

    @Test
    @DisplayName("GET /users/{id} - Should return 200 OK and the user when id exists")
    void findByIdWhenIdExistsReturnsOkAndUser() throws Exception {
        Mockito.when(service.findById(1L)).thenReturn(responseDTO);

        mockMvc.perform(get("/users/{id}", 1L)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L));
    }

    @Test
    @DisplayName("POST /users - Should return 201 Created and header Location with valid data")
    void insertWithValidDataReturnsCreated() throws Exception {
        Mockito.when(service.insert(any(UserRequestDTO.class))).thenReturn(responseDTO);

        String jsonBody = objectMapper.writeValueAsString(requestDTO);

        mockMvc.perform(post("/users")
                .content(jsonBody)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                    .andExpect(status().isCreated())
                    .andExpect(header().string("Location", endsWith("/users/1")))
                    .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                    .andExpect(jsonPath("$.id").value(1L))
                    .andExpect(jsonPath("$.firstName").value("Tom"))
                    .andExpect(jsonPath("$.lastName").value("Hanks"))
                    .andExpect(jsonPath("$.email").value("tom@example.com"));
    }

    @Test
    @DisplayName("DELETE /users/{id} - Should return status 204 No Content when delete is successful")
    void deleteWhenIdExistsReturnNoContent() throws Exception {
        Mockito.doNothing().when(service).delete(1L);

        mockMvc.perform(delete("/users/{id}", 1L))
                .andExpect(status().isNoContent());
    }

    @Test
    @DisplayName("PUT /users/{id} - Should return status 200 OK and the updated user with the valid data")
    void updateWithValidDataReturnsOkAndUpdatedUser() throws Exception {
        Mockito.when(service.update(eq(1L), any())).thenReturn(responseDTO);

        String jsonBody = objectMapper.writeValueAsString(requestDTO);

        mockMvc.perform(put("/users/{id}", 1L)
                .content(jsonBody)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L));
    }

    @Test
    @DisplayName("GET /users/{id} - Should return status 404 Not Found when ID does not exists")
    void findByIdWhenIdDoesNotExistAndReturnsNotFound() throws Exception {
        Mockito.when(service.findById(99L)).thenThrow(new ResourceNotFoundException("Usuário não encontrado"));

        mockMvc.perform(get("/users/{id}", 99L)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    @DisplayName("DELETE /users/{id} - Should return status 404 Not Found when ID does not exists")
    void delete_WhenIdDoesNotExist_ReturnsNotFound() throws Exception {
        Mockito.doThrow(new ResourceNotFoundException("Usuário não encontrado")).when(service).delete(99L);

        mockMvc.perform(delete("/users/{id}", 99L))
                .andExpect(status().isNotFound());
    }
}
