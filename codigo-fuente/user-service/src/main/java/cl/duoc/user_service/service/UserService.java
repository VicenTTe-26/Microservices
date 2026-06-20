package cl.duoc.user_service.service;

import cl.duoc.user_service.client.AuthClient;
import cl.duoc.user_service.model.User;
import cl.duoc.user_service.repository.UserRepository;
import feign.FeignException;
import cl.duoc.user_service.dto.UserDTO;
import cl.duoc.user_service.dto.UserCreateDTO;
import cl.duoc.user_service.dto.AuthDTO;
import cl.duoc.user_service.exception.RecursoNoEncontradoException;
import cl.duoc.user_service.exception.ServicioNoDisponibleException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserService {
    
    private static final Logger log = LoggerFactory.getLogger(UserService.class);

    private final UserRepository userRepository;
    private final AuthClient authClient; 

    public UserService(UserRepository userRepository, AuthClient authClient) {
        this.userRepository = userRepository;
        this.authClient = authClient;
    }

    public UserDTO crearUsuario(UserCreateDTO request) {
        log.info("Validando usuario con id={}", request.getIdAuth());
        log.info("Verificando existencia para usuario con id={}", request.getIdAuth());

        // Llamada al microservicio de Auth
        AuthDTO auth;
        try {
            auth = authClient.buscarAuthPorId(request.getIdAuth());
            log.info("Usuario confirmado por Auth: '{}'", auth.getNombre());
        } catch (FeignException.NotFound e) {
            log.warn("Servicio Auth respondió: El Auth ID={} no existe", request.getIdAuth());
            throw new RecursoNoEncontradoException("No se puede crear el usuario: El auth especificado no existe.");
        } catch (FeignException e) {
            log.error("Error al consultar servicio Auth: {}", e.getMessage());
            throw new ServicioNoDisponibleException("Servicio de Auth no disponible para verificar el usuario.");
        } 


        User nuevo = new User();
        nuevo.setIdAuth(request.getIdAuth());
        nuevo.setNombreCompleto(request.getNombreCompleto());
        nuevo.setRut(request.getRut());
        nuevo.setFechaNacimiento(request.getFechaNacimiento());
        nuevo.setNumeroTelefono(request.getNumeroTelefono());
        nuevo.setDireccion(request.getDireccion());
        
        User guardadoNuevo = userRepository.save(nuevo);
        log.info("Nuevo usuario registrado con el ID={}", guardadoNuevo.getId());
        return convertirADTO(guardadoNuevo);

    }


    // Listar todos los Usuarios
    public List<UserDTO> listarTodas() {
        log.info("Solicitando listado completo de todos los usuarios");
        return userRepository.findAll().stream()
                .map(this::convertirADTO)
                .collect(Collectors.toList());
    }
    // Buscar Usuario por id
    public UserDTO findDtoById(Long id) {
        return userRepository.findById(id)
            .map(user -> convertirADTO(user))
            .orElseThrow(() -> new RecursoNoEncontradoException("Usuario no encontrado"));

        
    }

    // Buscar Usuario por id de auth
        public List<UserDTO> obtenerPorAuthId(Long idAuth) {
        log.info("Solicitando listado de Usuario para el auth ID={}", idAuth);
        return userRepository.findByIdAuth(idAuth).stream()
                .map(this::convertirADTO)
                .collect(Collectors.toList());
    }

    // Eliminar User por id
    public boolean eliminarUser(Long id) {
        log.info("Intentando eliminar usuario ID={}", id);
        if (userRepository.existsById(id)) {
            userRepository.deleteById(id);
            log.info("Usuario ID={} eliminada con éxito", id);
            return true;
        }
        
        log.warn("No se pudo eliminar: Usuario ID={} no existe", id);
        return false;
    }

    // Actualizar Usuario por id
   public UserDTO actualizar(Long id, UserCreateDTO dto) {
        log.info("Actualizando Usuario id={}", id);
        User u = userRepository.findById(id)
                .orElseThrow(() -> new RecursoNoEncontradoException("Usuario no encontrado: " + id));
        u.setIdAuth(dto.getIdAuth());
        u.setNombreCompleto(dto.getNombreCompleto());
        u.setRut(dto.getRut());
        u.setFechaNacimiento(dto.getFechaNacimiento());
        u.setNumeroTelefono(dto.getNumeroTelefono());
        u.setDireccion(dto.getDireccion());
        return convertirADTO(userRepository.save(u));
    }


        //METODOS DE APOYO-RAPIDEZ
    
    private UserDTO convertirADTO(User user) {
        return new UserDTO(
            user.getId(),
            user.getIdAuth(),
            user.getNombreCompleto(),
            user.getRut(),
            user.getFechaNacimiento(),
            user.getNumeroTelefono(),
            user.getDireccion()
        );
    }


}