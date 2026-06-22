package auth.folder.auth_service.model;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import org.junit.jupiter.api.Test;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import java.util.Set;
import org.junit.jupiter.api.DisplayName;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;

@DisplayName("Pruebas Unitarias, Capa de Modelo (Auth)")
public class AuthTest {

    private Validator validator;

    @BeforeEach
    public void setUp() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        this.validator = factory.getValidator();
    }

    @Test
    @DisplayName("Verificar funcionamiento de getters y setters")
    public void debeVerificarGettersYSetters() {
        
        // Arrange
        Auth auth = new Auth();

        auth.setId(1L);
        auth.setNombre("Vicente Araya");
        auth.setCorreo("vicente@gmail.com");
        auth.setContraseña("contraseña123");

        // Assert
        assertEquals(1L, auth.getId());
        assertEquals("Vicente Araya", auth.getNombre());
        assertEquals("vicente@gmail.com", auth.getCorreo());
        assertEquals("contraseña123", auth.getContraseña());
    }

    @Test
    @DisplayName("Debe verificar el constructor vacío")
    public void debeVerificarConstrutorVacio() {
        
        // Arrange & Act
        Auth auth = new Auth();

        // Assert
        assertNull(auth.getId());
        assertNull(auth.getNombre());
        assertNull(auth.getCorreo());
        assertNull(auth.getContraseña());
    }

    @Test
    @DisplayName("Debe verificar el constructor completo")
    public void debeVerificarConstrutorCompleto() {
        // Arrange & Act
        Auth auth = new Auth(1L, "Vicente Araya", "vicente@gmail.com", "contraseña123");

        // Assert
        assertEquals(1L, auth.getId());
        assertEquals("Vicente Araya", auth.getNombre());
        assertEquals("vicente@gmail.com", auth.getCorreo());
        assertEquals("contraseña123", auth.getContraseña());
    }

    @Test
    @DisplayName("Debe fallar si la contraseña tiene menos de 4 caracteres")
    public void debeFallarSiContraseñaEsCorta() {
        // Arrange (Contraseña de solo 3 caracteres)
        Auth auth = new Auth(1L, "Vicente Araya", "vicente@example.com", "123");

        // Act
        Set<ConstraintViolation<Auth>> violations = validator.validate(auth);

        // Assert
        assertFalse(violations.isEmpty());
        
        boolean tieneMensajeEsperado = violations.stream()
                .anyMatch(v -> v.getMessage().equals("Minimo 4 caracteres"));
                
        assertTrue(tieneMensajeEsperado);
    }
}
