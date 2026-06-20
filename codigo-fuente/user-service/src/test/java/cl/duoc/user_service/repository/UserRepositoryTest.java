package cl.duoc.user_service.repository;

import cl.duoc.user_service.model.User;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@DisplayName("Pruebas de Integración con BD en Memoria - Capa de Repositorio (UserRepository)")
public class UserRepositoryTest {
    
    @Autowired
    private UserRepository userRepository;

    @Test
    @DisplayName("Debe probar save y findById")
    void debeProbarSaveYFindById() {

        // Arrange
        User nuevoUser = new User(null, 200L, "María López", "98.765.432-1", "20/02/2000", "+56987654321", "Pasaje Central 456");
        User guardada = userRepository.save(nuevoUser);

        // Act
        Optional<User> encontrada = userRepository.findById(guardada.getId());

        // Assert
        assertTrue(encontrada.isPresent());
        assertEquals("María López", encontrada.get().getNombreCompleto());
    }

    @Test
    @DisplayName("Debe probar findAll")
    void debeProbarFindAll() {
        User user = new User(null, 101L, "Maria Laura Acevedo", "12.234.567-8", "30/01/2008", "+56923456789", "Pasaje Roma 891");
        userRepository.save(user);

        List<User> resultados = userRepository.findAll();
        assertFalse(resultados.isEmpty());
    }
}
