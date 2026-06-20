package auth.folder.auth_service.service;

import auth.folder.auth_service.model.Auth;
import auth.folder.auth_service.repository.AuthRepository;
import auth.folder.auth_service.dto.AuthCreateDTO;
import auth.folder.auth_service.dto.AuthDTO;
import auth.folder.auth_service.exception.RecursoNoEncontradoException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class AuthService {

    private static final Logger log = LoggerFactory.getLogger(AuthService.class);
    private final AuthRepository authRepository;

    public AuthService(AuthRepository authRepository) {
        this.authRepository = authRepository;
    }

    //Crear Usuario
    public AuthDTO crearAuthUsuario(AuthCreateDTO dto) {

        // 1. Convertir DTO de entrada a entidad
        Auth a = new Auth();
        a.setNombre(dto.getNombre());
        a.setCorreo(dto.getCorreo());
        a.setContraseña(dto.getContraseña());

        // 2. Persistir en la base de datos
        Auth guardado = authRepository.save(a);

        // 3. Convertir la entidad guardada a DTO de salida (ya con id)
        return convertirADTO(guardado);
}

    // Listar todos los Auth
    public List<AuthDTO> listarAuth() {
        log.info("Solicitando listado completo de auths");
        return authRepository.findAll().stream()
            .map(this::convertirADTO)
            .collect(Collectors.toList());
    }

    //Buscar por Id
    public AuthDTO buscarAuthPorId(Long id) {
        return authRepository.findById(id)
            .map(auth -> convertirADTO(auth))
            .orElseThrow(() -> new RecursoNoEncontradoException("Auth no encontrado"));

    }
  

    // Eliminar Usuario por id
    public boolean eliminarAuth(Long id) {
        log.info("Intentando eliminar auth ID={}", id);
        if (authRepository.existsById(id)) {
            authRepository.deleteById(id);
            log.info("Usuario ID={} eliminada con éxito", id);
            return true;
        }

        log.warn("No se pudo eliminar: Auth ID={} no existe", id);
        return false;
    }


    // Actualizar Usuario por id
    public AuthDTO actualizarAuth(Long id, AuthCreateDTO dto) {
        log.info("Actualizando Auth id={}", id);
        return authRepository.findById(id)
                .map(authExistente -> {
                    authExistente.setNombre(dto.getNombre());
                    authExistente.setCorreo(dto.getCorreo());
                    authExistente.setContraseña(dto.getContraseña());
                    Auth actualizado = authRepository.save(authExistente);
                    return convertirADTO(actualizado);
                })
                .orElseThrow(() -> new RecursoNoEncontradoException("Auth no encontrado"));
    }


    //METODOS DE APOYO-RAPIDEZ
    
    private AuthDTO convertirADTO(Auth auth) {
        return new AuthDTO(
            auth.getId(),
            auth.getNombre(),
            auth.getCorreo());
    }

    
}

