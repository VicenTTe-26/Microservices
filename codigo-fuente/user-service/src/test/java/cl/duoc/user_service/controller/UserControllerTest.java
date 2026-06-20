package cl.duoc.user_service.controller;

import cl.duoc.user_service.service.UserService;
import cl.duoc.user_service.dto.UserDTO;
import cl.duoc.user_service.dto.UserCreateDTO;
import cl.duoc.user_service.exception.GlobalExceptionHandler;
import cl.duoc.user_service.exception.RecursoNoEncontradoException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Collections;
import java.util.Objects;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
@DisplayName("Pruebas de Integración - Capa de Controlador (UserController)")
public class UserControllerTest {

    private MockMvc mockMvc;

    @Mock
    private UserService userService;

    @InjectMocks
    private UserController userController;

    private final ObjectMapper objectMapper = new ObjectMapper();
    
    @BeforeEach
    void setUp() {

        this.mockMvc = MockMvcBuilders.standaloneSetup(userController)
                .setControllerAdvice(new GlobalExceptionHandler())
                .build();
    }

    @Test
    @DisplayName("POST -> Debe responder HTTP 201 Created")
    void debeRetornar201() throws Exception {
        UserCreateDTO entrada = new UserCreateDTO(5L, "Alfonso Rojas", "33.333.333-3", "09/08/1990", "+56944444444", "Av. Las Palmas 1234");
        UserDTO salida = new UserDTO(100L, 5L, "Alfonso Rojas", "33.333.333-3", "09/08/1990", "+56944444444", "Av. Las Palmas 1234");

        Mockito.when(userService.crearUsuario(Mockito.any(UserCreateDTO.class))).thenReturn(salida);

        mockMvc.perform(post("/api/v2/users")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(entrada)))
                .andExpect(status().isCreated());
    }

    @Test
    @DisplayName("GET -> Debe retornar HTTP 200 OK")
    void debeRetornar200() throws Exception {
        Mockito.when(userService.listarTodas()).thenReturn(Collections.emptyList());

        mockMvc.perform(get("/api/v2/users"))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("GET -> Debe retornar HTTP 404 Not Found")
    void debeRetornar404() throws Exception {
        Mockito.when(userService.findDtoById(999L))
               .thenThrow(new RecursoNoEncontradoException("No encontrado"));

        mockMvc.perform(get("/api/v2/users/{id}", 999L))
                .andExpect(status().isNotFound());
    }

    @Test
    @DisplayName("POST -> Debe retornar HTTP 400 Bad Request ante payload inválido")
    void debeRetornar400() throws Exception {
        UserCreateDTO entradaInvalida = new UserCreateDTO();

        mockMvc.perform(post("/api/v2/users")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(entradaInvalida)))
                .andExpect(status().isBadRequest());
    }
}
