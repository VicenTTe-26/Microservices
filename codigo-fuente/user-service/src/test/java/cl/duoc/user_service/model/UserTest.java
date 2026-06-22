package cl.duoc.user_service.model;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import org.junit.jupiter.api.Test;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import java.util.Set;
import org.junit.jupiter.api.DisplayName;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;

@DisplayName("Pruebas Unitarias, Capa de Modelo (User)")
public class UserTest {

    private Validator validator;

    @BeforeEach
    public void setUp() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    @DisplayName("Verificar funcionamiento de getters y setters")
    public void debeVerificarGettersYSetters() {
        
        // Arrange Y Act
        User user = new User();

        user.setId(1L);
        user.setIdAuth(100L);
        user.setNombreCompleto("Vicente Araya Salazar");
        user.setRut("11.111.111-1");
        user.setFechaNacimiento("22/02/2002");
        user.setNumeroTelefono("+56933333333");
        user.setDireccion("Psje Juan Perez");

        // Assert
        assertEquals(1L, user.getId());
        assertEquals(100L, user.getIdAuth());
        assertEquals("Vicente Araya Salazar", user.getNombreCompleto());
        assertEquals("11.111.111-1", user.getRut());
        assertEquals("22/02/2002", user.getFechaNacimiento());
        assertEquals("+56933333333", user.getNumeroTelefono());
        assertEquals("Psje Juan Perez", user.getDireccion());
    }

    @Test
    @DisplayName("Debe verificar el constructor vacío")
    public void debeVerificarConstrutorVacio() {

        User user = new User();

        assertNull(user.getId());
        assertNull(user.getNombreCompleto());
    }

    @Test
    @DisplayName("Debe verificar el constructor completo")
    public void debeVerificarConstrutorCompleto() {
       
        User user = new User(1L, 100L, "Vicente Araya Salazar", "11.111.111-1", "22/02/2002", "+56933333333", "Psje Juan Perez");

        
        assertEquals(1L, user.getId());
        assertEquals(100L, user.getIdAuth());
        assertEquals("Vicente Araya Salazar", user.getNombreCompleto());
        assertEquals("11.111.111-1", user.getRut());
        assertEquals("22/02/2002", user.getFechaNacimiento());
        assertEquals("+56933333333", user.getNumeroTelefono());
        assertEquals("Psje Juan Perez", user.getDireccion());
    }

    @Test
    @DisplayName("Debe fallar si los campos @NotBlank están vacíos")
    public void debeFallarCuandoCamposObligatoriosEstanVacios() {

        // Arrange: Instanciamos un usuario válido pero le dejamos campos vacíos a proposito
        User user = new User(1L, 100L, "", "", "22/02/2002", "+56933333333", "Psje Juan Perez");

        // Act
        Set<ConstraintViolation<User>> violations = validator.validate(user);

        // Assert
        assertFalse(violations.isEmpty());
        
        // Verifica el error del RUT
        boolean tieneErrorRut = violations.stream()
                .anyMatch(v -> v.getMessage().equals("El RUT es obligatorio"));
        assertTrue(tieneErrorRut);

        // Verifica el error del Nombre
        boolean tieneErrorNombre = violations.stream()
                .anyMatch(v -> v.getMessage().equals("El nombre es obligatorio"));
        assertTrue(tieneErrorNombre);
}
}