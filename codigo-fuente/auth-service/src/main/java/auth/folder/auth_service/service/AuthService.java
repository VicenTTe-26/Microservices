package auth.folder.auth_service.service;

import auth.folder.auth_service.model.Auth;
import auth.folder.auth_service.repository.AuthRepository;
import auth.folder.auth_service.dto.AuthCreateDTO;
import auth.folder.auth_service.dto.AuthDTO;

import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class AuthService {
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

    // Listar todos los Usuarios
    public List<AuthDTO> listarAuth() {
        return authRepository.findAll().stream()
            .map(this::convertirADTO)
            .collect(Collectors.toList());
    }

    //Buscar por Id
    public AuthDTO buscarAuthPorId(Long id) {
        Auth a = authRepository.findById(id)
            .orElseThrow(() -> new RecursoNoEncontradoException("Usuario no encontrado"));

        return convertirADTO(a);
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
    public AuthDTO actualizarAuth(Long id, AuthCreateDTO dto) {
        return authRepository.findById(id)
                .map(authExistente -> {
                    authExistente.setNombre(dto.getNombre());
                    authExistente.setCorreo(dto.getCorreo());
                    authExistente.setContraseña(dto.getContraseña());
                    Auth actualizado = authRepository.save(authExistente);
                    return convertirADTO(actualizado);
                })
                .orElseThrow(() -> new RecursoNoEncontradoException("Usuario no encontrado"));
    }


    //METODOS DE APOYO-RAPIDEZ
    
    private AuthDTO convertirADTO(Auth auth) {
        return new AuthDTO(
            auth.getId(),
            auth.getNombre(),
            auth.getCorreo());
    }

    
    // Excepción personalizada interna
public class RecursoNoEncontradoException extends RuntimeException {
    public RecursoNoEncontradoException(String mensaje) {
        super(mensaje);
    }
}

}
