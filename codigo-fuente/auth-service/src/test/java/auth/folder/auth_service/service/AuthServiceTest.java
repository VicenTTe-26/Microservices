package auth.folder.auth_service.service;

import auth.folder.auth_service.model.Auth;
import auth.folder.auth_service.repository.AuthRepository;
import auth.folder.auth_service.dto.AuthDTO;
import auth.folder.auth_service.dto.AuthCreateDTO;
import auth.folder.auth_service.exception.RecursoNoEncontradoException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@DisplayName("Pruebas Unitarias - Capa de Servicio (AuthService)")
public class AuthServiceTest {

    @Mock
    private AuthRepository authRepository;

    @InjectMocks
    private AuthService authService;

    @Test
    @DisplayName("Debe crear un auth")
    void debeCrearAuth() {
        
        // Given
        AuthCreateDTO dto = new AuthCreateDTO("Juan Perez", "juanperez@gmail.com", "contraseña123");
        Auth authGuardado = new Auth(1L, "Juan Perez", "juanperez@gmail.com", "contraseña123");
        when(authRepository.save(any(Auth.class))).thenReturn(authGuardado);

        // When
        AuthDTO resultado = authService.crearAuth(dto);

        // Then
        assertNotNull(resultado);
        assertEquals(1L, resultado.getId());
        assertEquals("Juan Perez", resultado.getNombre());
        assertEquals("juanperez@gmail.com", resultado.getCorreo());
    }

    @Test
    @DisplayName("Debe listar todos los auths")
    void debeListarAuths() {

        // Given
        Auth auth = new Auth(1L, "Juan Perez", "juanperez@gmail.com", "contraseña123");
        when(authRepository.findAll()).thenReturn(Collections.singletonList(auth));

        // When
        List<AuthDTO> resultado = authService.listarAuth();

        // Then
        assertFalse(resultado.isEmpty());
        assertEquals(1, resultado.size());

    }

    @Test
    @DisplayName("Debe buscar un auth por ID")
    void debeBuscarPorId() {
        
        // Given
        Auth auth = new Auth(1L, "Juan Perez", "juanperez@gmail.com", "contraseña123");
        when(authRepository.findById(1L)).thenReturn(Optional.of(auth));

        // When
        AuthDTO resultado = authService.buscarAuthPorId(1L);

        // Then
        assertNotNull(resultado);
        assertEquals("Juan Perez", resultado.getNombre());
        assertEquals("juanperez@gmail.com", resultado.getCorreo());
    }

    @Test
    @DisplayName("Debe actualizar un auth")
    void debeActualizarAuth() {
        
        // Given
        AuthCreateDTO dto = new AuthCreateDTO("Juan Modificado", "juanmodifi@gmail.com", "nuevacontraseña");
        Auth authExistente = new Auth(1L, "Juan Perez", "juanperez@gmail.com", "contraseña123");
        Auth authActualizado = new Auth(1L, "Juan Modificado", "juanmodifi@gmail.com", "nuevacontraseña123");

        when(authRepository.findById(1L)).thenReturn(Optional.of(authExistente));
        when(authRepository.save(any(Auth.class))).thenReturn(authActualizado);

        // When
        AuthDTO resultado = authService.actualizarAuth(1L, dto);

        // Then
        assertNotNull(resultado);
        assertEquals("Juan Modificado", resultado.getNombre());
        assertEquals("juanmodifi@gmail.com", resultado.getCorreo());
    }


    @Test
    @DisplayName("Debe eliminar un auth existente")
    void debeEliminarAuth() {

        // Given
        when(authRepository.existsById(2L)).thenReturn(true);
        Mockito.doNothing().when(authRepository).deleteById(2L);

        // When
        boolean eliminado = authService.eliminarAuth(2L);

        // Then
        assertTrue(eliminado);
    }

    @Test
    @DisplayName("Regla de Negocio: Debe lanzar excepción si el ID no existe al buscar")
    void debeLanzarExcepcionCuandoIdNoExiste() {
        
        // Given
        when(authRepository.findById(999L)).thenReturn(Optional.empty());

        // When Y Then
        assertThrows(RecursoNoEncontradoException.class, () -> {
            authService.buscarAuthPorId(999L);
        });
    }

    @Test
    @DisplayName("Regla de Negocio: Debe lanzar excepción si el ID no existe")
    void debeLanzarExcepcionCuandoIdNoExisteAlActualizar() {
        
        // Given
        AuthCreateDTO dto = new AuthCreateDTO("Juan Modificado", "juanmodifi@gmail.com", "nuevacontraseña123");
        when(authRepository.findById(999L)).thenReturn(Optional.empty());

        // When Y Then
        assertThrows(RecursoNoEncontradoException.class, () -> {
            authService.actualizarAuth(999L, dto);
        });
    }
}
