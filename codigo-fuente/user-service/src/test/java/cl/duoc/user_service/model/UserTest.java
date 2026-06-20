package cl.duoc.user_service.model;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Pruebas Unitarias, Capa de Modelo (User)")
public class UserTest {

    @Test
    @DisplayName("Verificar funcionamiento de getters y setters")
    public void debeVerificarGettersYSetters() {
        // Arrange
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
}