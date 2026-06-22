package comms.folder.comms_service.service;

import comms.folder.comms_service.client.UserClient;
import comms.folder.comms_service.client.OrdenClient;
import comms.folder.comms_service.model.Comms;
import comms.folder.comms_service.repository.CommsRepository;
import feign.FeignException;
import comms.folder.comms_service.dto.CommsDTO;
import comms.folder.comms_service.dto.CommsCreateDTO;
import comms.folder.comms_service.dto.UserDTO;
import comms.folder.comms_service.dto.OrdenDTO;
import comms.folder.comms_service.exception.RecursoNoEncontradoException;
import comms.folder.comms_service.exception.ServicioNoDisponibleException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CommsService {
    
    private static final Logger log = LoggerFactory.getLogger(CommsService.class);

    private final CommsRepository commsRepository;
    private final OrdenClient ordenClient;
    private final UserClient userClient;

    public CommsService(CommsRepository commsRepository, OrdenClient ordenClient, UserClient userClient) {
        this.commsRepository = commsRepository;
        this.ordenClient = ordenClient;
        this.userClient = userClient;
    }

    private void validarExistenciaUsuario(Long userId) {
        log.info("Validando existencia para usuario con id={}", userId);
        try {
            UserDTO user = userClient.buscarUserPorId(userId);
            log.info("Usuario confirmado correctamente: ID={}, NombreCompleto='{}'", user.getId(), user.getNombreCompleto());
        } catch (FeignException.NotFound e) {
            log.warn("Servicio User respondió: El ID={} no existe", userId);
            throw new RecursoNoEncontradoException("No se puede procesar la solicitud: El usuario especificado no existe.");
        } catch (FeignException e) {
            log.error("Error al consultar servicio User: {}", e.getMessage());
            throw new ServicioNoDisponibleException("Servicio de User no disponible para solicitar el usuario.");
        }
    }

    private void validarExistenciaOrden(Long orderId) {
        log.info("Validando existencia de la orden con ID={}", orderId);
        try {
            OrdenDTO orden = ordenClient.buscarOrdenPorId(orderId);
            log.info("Orden validada con éxito. ID={}, Estado='{}'", orden.getId(), orden.getEstado());
        } catch (FeignException.NotFound e) {
            log.warn("Servicio de Ordenes respondió: La orden ID={} no existe", orderId);
            throw new RecursoNoEncontradoException("No se puede procesar la solicitud: La orden especificada no existe");
        } catch (FeignException e) {
            log.error("Error de comunicación con servicio de Ordenes: {}", e.getMessage());
            throw new ServicioNoDisponibleException("Servicio de Ordenes no disponible momentáneamente. Intente más tarde.");
        }
    }

    public CommsDTO crearComms(CommsCreateDTO request) {
        
        validarExistenciaUsuario(request.getUserId());
        validarExistenciaOrden(request.getOrderId());

        Comms nuevo = new Comms();
        nuevo.setUserId(request.getUserId());
        nuevo.setOrderId(request.getOrderId());
        nuevo.setCorreo(request.getCorreo());
        nuevo.setMensaje(request.getMensaje());
        
        Comms guardadoNuevo = commsRepository.save(nuevo);
        log.info("Notificación enviada y registrada correctamente con ID={}", guardadoNuevo.getId());
        return convertirADTO(guardadoNuevo);

    }


    // Listar todos los Mensajes
    public List<CommsDTO> listarTodas() {
        log.info("Solicitando listado completo de todos los mensajes");
        return commsRepository.findAll().stream()
                .map(this::convertirADTO)
                .collect(Collectors.toList());
    }
    // Buscar Mensaje por id
    public CommsDTO findDtoById(Long id) {
            return commsRepository.findById(id)
            .map(mensaje -> convertirADTO(mensaje))
            .orElseThrow(() -> new RecursoNoEncontradoException("Mensaje no encontrado"));

    }

    // Buscar Mensaje por id de orden
    public List<CommsDTO> obtenerPorOrderId(Long orderId) {
        log.info("Solicitando listado de Mensajes para la orden ID={}", orderId);
        return commsRepository.findByOrderId(orderId).stream()
                .map(this::convertirADTO)
                .collect(Collectors.toList());
    }

    // Buscar Mensaje por id de user
    public List<CommsDTO> obtenerPorUserId(Long userId) {
        log.info("Solicitando listado de Mensajes para el user ID={}", userId);
        return commsRepository.findByUserId(userId).stream()
                .map(this::convertirADTO)
                .collect(Collectors.toList());
    }

    // Eliminar Pago por id
    public boolean eliminarMensaje(Long id) {
        log.info("Intentando eliminar mensaje ID={}", id);
        if (commsRepository.existsById(id)) {
            commsRepository.deleteById(id);
            log.info("Mensaje ID={} eliminado con éxito", id);
            return true;
        }
        log.warn("No se pudo eliminar: Mensaje ID={} no existe", id);
        return false;
    }

    // Actualizar Mensaje por id
    public CommsDTO actualizarMensaje(Long id, CommsCreateDTO dto) {
        Comms comms = commsRepository.findById(id)
                .orElseThrow(() -> {
                    log.warn("Intento fallido de actualizar mensaje: ID={} no existe", id);
                    return new RecursoNoEncontradoException("No existe el mensaje con ID: " + id);
                });

        validarExistenciaUsuario(dto.getUserId());
        validarExistenciaOrden(dto.getOrderId());

        comms.setUserId(dto.getUserId());
        comms.setOrderId(dto.getOrderId());
        comms.setCorreo(dto.getCorreo());
        comms.setMensaje(dto.getMensaje());

        Comms actualizada = commsRepository.save(comms);
        log.info("Mensaje ID={} actualizado con éxito", id);
        return convertirADTO(actualizada);
    }


        //METODOS DE APOYO-RAPIDEZ
    
    private CommsDTO convertirADTO(Comms comms) {
        return new CommsDTO(
            comms.getId(),
            comms.getUserId(),
            comms.getOrderId(),
            comms.getCorreo(),
            comms.getMensaje()
        );
    }


}