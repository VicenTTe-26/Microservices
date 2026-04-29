package cl.duoc.user_service.service;

import cl.duoc.user_service.model.User;
import cl.duoc.user_service.repository.UserRepository;

import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class UserService {
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User registrarUser(User user) {
        return userRepository.save(user);
    }

    // Listar todos los Usuarios
    public List<User> listarUsuarios() {
        return userRepository.findAll();
    }

    // Buscar Usuario por id
    public Optional<User> buscarPorId(Long id) {
        return userRepository.findById(id);
    }

    // Buscar Usuario por Nombre Completo
    public List<User> buscarPorNombre(String nombreCompleto) {
        return userRepository.findByNombreCompleto(nombreCompleto);
    }

    // Buscar Usuario por Rut
    public List<User> buscarPorRut(String rut) {
        return userRepository.findByRut(rut);
    }

    // Buscar Usuario por Correo
    public List<User> buscarPorCorreo(String correo){
        return userRepository.findByCorreo(correo);
    }
    

    // Buscar Usuario por Número de Teléfono
    public List<User> buscarPorTelefono(String numeroTelefono) {
        return userRepository.findByNumeroTelefono(numeroTelefono);
    }


    // Eliminar Usuario por id
    public boolean eliminarUsuario(Long id) {
        if (userRepository.existsById(id)) {
            userRepository.deleteById(id);
            return true;
        }
        return false;
    }

    // Actualizar Usuario por id
    public User actualizarUsuario(Long id, User userActualizado) {
        Optional<User> userExistenteOpt = userRepository.findById(id);
        if (userExistenteOpt.isPresent()) {
            User userExistente = userExistenteOpt.get();
            userExistente.setNombreCompleto(userActualizado.getNombreCompleto());
            userExistente.setRut(userActualizado.getRut());
            userExistente.setFechaNacimiento(userActualizado.getFechaNacimiento());
            userExistente.setNumeroTelefono(userActualizado.getNumeroTelefono());
            userExistente.setCorreo(userActualizado.getCorreo());
            userExistente.setDireccion(userActualizado.getDireccion());
            return userRepository.save(userExistente);
        }
        return null;
    }

}