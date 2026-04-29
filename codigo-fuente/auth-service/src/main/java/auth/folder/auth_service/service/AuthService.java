package auth.folder.auth_service.service;

import auth.folder.auth_service.model.Auth;
import auth.folder.auth_service.repository.AuthRepository;

import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class AuthService {
    private final AuthRepository authRepository;

    public AuthService(AuthRepository authRepository) {
        this.authRepository = authRepository;
    }

    public Auth AuthRegister(Auth auth) {
        return authRepository.save(auth);
    }

    // Listar todos los Usuarios
    public List<Auth> listarAuth() {
        return authRepository.findAll();
    }

    // Buscar Usuario por id
    public Optional<Auth> buscarPorId(Long id) {
        return authRepository.findById(id);
    }

    // Buscar Usuario por Nombre
    public List<Auth> buscarPorNombre(String nombre) {
        return authRepository.findByNombre(nombre);
    }


    // Buscar Usuario por Correo
    public List<Auth> buscarPorCorreo(String correo){
        return authRepository.findByCorreo(correo);
    }
    

    // Eliminar Usuario por id
    public boolean eliminarAuth(Long id) {
        if (authRepository.existsById(id)) {
            authRepository.deleteById(id);
            return true;
        }
        return false;
    }

    // Actualizar Usuario por id
    public Auth actualizarAuth(Long id, Auth authActualizado) {
        Optional<Auth> authExistenteOpt = authRepository.findById(id);
        if (authExistenteOpt.isPresent()) {
            Auth authExistente = authExistenteOpt.get();
            authExistente.setNombre(authActualizado.getNombre());
            authExistente.setCorreo(authActualizado.getCorreo());
            authExistente.setContraseña(authActualizado.getContraseña());
            return authRepository.save(authExistente);
        }
        return null;
    }

}
