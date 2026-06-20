package cl.duoc.user_service.service;

import cl.duoc.user_service.client.AuthClient;
import cl.duoc.user_service.model.User;
import cl.duoc.user_service.repository.UserRepository;
import cl.duoc.user_service.dto.UserDTO;
import cl.duoc.user_service.dto.UserCreateDTO;
import cl.duoc.user_service.dto.AuthDTO;
import cl.duoc.user_service.exception.RecursoNoEncontradoException;
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
@DisplayName("Pruebas Unitarias - Capa de Servicio (UserService)")
public class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private AuthClient authClient;

    @InjectMocks
    private UserService userService;

    @Test
    @DisplayName("Debe crear un usuario")
    void debeCrearUsuario() {
        UserCreateDTO dto = new UserCreateDTO(5L, "Alfonso Rojas", "33.333.333-3", "09/08/1990", "+56944444444", "Av. Las Palmas 1234");
        AuthDTO authSimulado = new AuthDTO();
        authSimulado.setId(5L);
        User user = new User(2L, 5L, "Alfonso Rojas", "33.333.333-3", "09/08/1990", "+56944444444", "Av. Las Palmas 1234");

        // Mocks para saltar las validaciones de clientes externos del servicio
        when(authClient.buscarAuthPorId(5L)).thenReturn(new AuthDTO());
        when(userRepository.save(any(User.class))).thenReturn(user);

        UserDTO resultado = userService.crearUsuario(dto);
        assertNotNull(resultado);
        assertEquals(2L, resultado.getId());
    }

    @Test
    @DisplayName("Debe listar todos los usuarios")
    void debeListarUsers() {
            User user = new User(2L, 5L, "Alfonso Rojas", "33.333.333-3", "09/08/1990", "+56944444444", "Av. Las Palmas 1234");
        when(userRepository.findAll()).thenReturn(Collections.singletonList(user));

        List<UserDTO> resultado = userService.listarTodas();
        assertFalse(resultado.isEmpty());
        assertEquals(1, resultado.size());
    }

    @Test
    @DisplayName("Debe buscar una usuario por ID")
    void debeBuscarPorId() {
        User user = new User(2L, 5L, "Alfonso Rojas", "33.333.333-3", "09/08/1990", "+56944444444", "Av. Las Palmas 1234");
        when(userRepository.findById(2L)).thenReturn(Optional.of(user));

        UserDTO resultado = userService.findDtoById(2L);
        assertNotNull(resultado);
        assertEquals("Alfonso Rojas", resultado.getNombreCompleto());
        assertEquals("33.333.333-3", resultado.getRut());
    }

    @Test
    @DisplayName("Debe actualizar un usuario")
    void debeActualizarUsuario() {
        UserCreateDTO dto = new UserCreateDTO(5L, "Maria Del Carmen", "33.333.333-3", "09/08/1990", "+56944444444", "Av. Las Palmas 1234");
        User userExistente = new User(2L, 5L, "Alfonso Rojas", "33.333.333-3", "09/08/1990", "+56944444444", "Av. Las Palmas 1234");
        User userActualizado = new User(2L, 5L, "Maria Del Carmen", "33.333.333-3", "09/08/1990", "+56944444444", "Av. Las Palmas 1234");
        
        // Simular que encuentra la reseña original antes de editarla
        when(userRepository.findById(2L)).thenReturn(Optional.of(userExistente));
        when(userRepository.save(any(User.class))).thenReturn(userActualizado);

        UserDTO resultado = userService.actualizar(2L, dto);
        assertNotNull(resultado);
        assertEquals("Maria Del Carmen", resultado.getNombreCompleto());
    }

    @Test
    @DisplayName("Debe eliminar un usuario")
    void debeEliminarUsuario() {
        when(userRepository.existsById(2L)).thenReturn(true);
        Mockito.doNothing().when(userRepository).deleteById(2L);

        boolean eliminado = userService.eliminarUser(2L);
        assertTrue(eliminado);
    }

    @Test
    @DisplayName("Regla de Negocio: Debe lanzar excepción si el ID no existe")
    void debeLanzarExcepcionCuandoIdNoExiste() {
        when(userRepository.findById(999L)).thenReturn(Optional.empty());

        assertThrows(RecursoNoEncontradoException.class, () -> {
            userService.findDtoById(999L);
        });
    }
}
