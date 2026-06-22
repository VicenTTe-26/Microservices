package auth.folder.auth_service.controller;

import auth.folder.auth_service.service.AuthService;
import auth.folder.auth_service.dto.AuthCreateDTO;
import auth.folder.auth_service.dto.AuthDTO;
import auth.folder.auth_service.exception.GlobalExceptionHandler;
import auth.folder.auth_service.exception.RecursoNoEncontradoException;
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

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
@DisplayName("Pruebas de Integración - Capa de Controlador (AuthController)")
public class AuthControllerTest {

    private MockMvc mockMvc;

    @Mock
    private AuthService authService;

    @InjectMocks
    private AuthController authController;

    private final ObjectMapper objectMapper = new ObjectMapper();

    @BeforeEach
    void setUp() {
        this.mockMvc = MockMvcBuilders.standaloneSetup(authController)
                .setControllerAdvice(new GlobalExceptionHandler())
                .build();
    }

    @Test
    @DisplayName("POST -> Debe responder HTTP 201 Created")
    void debeRetornar201() throws Exception {
        
        // Arrange
        AuthCreateDTO entrada = new AuthCreateDTO("Vicente Araya", "vicente@gmail.com", "contraseña123");
        AuthDTO salida = new AuthDTO(1L, "Vicente Araya", "vicente@gmail.com");

        Mockito.when(authService.crearAuth(Mockito.any(AuthCreateDTO.class))).thenReturn(salida);

        // Act & Assert
        mockMvc.perform(post("/api/v2/auth")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(entrada)))
                .andExpect(status().isCreated());
    }

    @Test
    @DisplayName("GET -> Debe retornar HTTP 200 OK")
    void debeRetornar200() throws Exception {
        // Arrange
        Mockito.when(authService.listarAuth()).thenReturn(Collections.emptyList());

        // Act & Assert
        mockMvc.perform(get("/api/v2/auth"))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("GET -> Debe retornar HTTP 404 Not Found")
    void debeRetornar404() throws Exception {
        // Arrange
        Mockito.when(authService.buscarAuthPorId(999L))
               .thenThrow(new RecursoNoEncontradoException("Auth no encontrado"));

        // Act & Assert
        mockMvc.perform(get("/api/v2/auth/{id}", 999L))
                .andExpect(status().isNotFound());
    }

    @Test
    @DisplayName("POST -> Debe retornar HTTP 400 Bad Request ante payload inválido")
    void debeRetornar400() throws Exception {
        // Arrange
        AuthCreateDTO entradaInvalida = new AuthCreateDTO();

        // Act & Assert
        mockMvc.perform(post("/api/v2/auth")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(entradaInvalida)))
                .andExpect(status().isBadRequest());
    }
}
