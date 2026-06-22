package auth.folder.auth_service.repository;

import auth.folder.auth_service.model.Auth;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@DisplayName("Pruebas de Integración con BD en Memoria - Capa de Repositorio (AuthRepository)")
public class AuthRepositoryTest {

    @Autowired
    private AuthRepository authRepository;

    @Test
    @DisplayName("Debe probar save y findById")
    void debeProbarSaveYFindById() {
        
        // Arrange
        // Pasamos null en el ID ya que la base de datos se encarga de autogenerarlo
        Auth nuevoAuth = new Auth(null, "Carlos Muñoz", "carlos@gmail.com", "contraseña123");
        Auth guardado = authRepository.save(nuevoAuth);

        // Act
        Optional<Auth> encontrado = authRepository.findById(guardado.getId());

        // Assert
        assertTrue(encontrado.isPresent());
        assertEquals("Carlos Muñoz", encontrado.get().getNombre());
        assertEquals("carlos@gmail.com", encontrado.get().getCorreo());
    }

    @Test
    @DisplayName("Debe probar findAll")
    void debeProbarFindAll() {
        
        // Arrange
        Auth auth = new Auth(null, "Maria Laura Acevedo", "amariaacevedo@gmail.com", "contraseña123");
        authRepository.save(auth);

        // Act
        List<Auth> resultados = authRepository.findAll();

        // Assert
        assertFalse(resultados.isEmpty());
        assertEquals(1, resultados.size());
    }
}
