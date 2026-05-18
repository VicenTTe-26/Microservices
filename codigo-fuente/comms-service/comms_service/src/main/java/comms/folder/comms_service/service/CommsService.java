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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CommsService {
    
    private static final Logger log = LoggerFactory.getLogger(CommsService.class);

    private final CommsRepository commsRepository;

    @Autowired
    private OrdenClient ordenClient;
    
    @Autowired
    private UserClient userClient;

    public CommsService(CommsRepository commsRepository) {
        this.commsRepository = commsRepository;
    }

    public CommsDTO crearComms(CommsCreateDTO request) {
        log.info("Validando usuario con id={}", request.getUserId());
        log.info("Verificando existencia para usuario con id={}", request.getUserId());

        // Llamada al microservicio de User
        UserDTO user;
        try {
            user = userClient.buscarUserPorId(request.getUserId());
            log.info("Usuario confirmado correctamente: ID={}, NombreCompleto='{}'", user.getId(), user.getNombreCompleto());
        } catch (FeignException.NotFound e) {
            log.warn("Servicio User respondió: El  ID={} no existe", request.getUserId());
            throw new RecursoNoEncontradoException("No se puede crear el mensaje: El usuario especificado no existe.");
        } catch (FeignException e) {
            log.error("Error al consultar servicio User: {}", e.getMessage());
            throw new ServicioNoDisponibleException("Servicio de User no disponible para solicitar el usuario.");
        } 


        log.info("Iniciando proceso de notificación para la orden ID={}", request.getOrderId());
        log.info("Verificando existencia de la orden con ID={}", request.getOrderId());
        
        // Llamada al microservicio Orden
        OrdenDTO orden;
        try {
            orden = ordenClient.buscarOrdenPorId(request.getOrderId());
            log.info("Orden validada con éxito. ID={}, Estado='{}', UserID={}", orden.getId(), orden.getEstado(), orden.getIdUsuario());
        } catch (FeignException.NotFound e) {
            log.warn("Servicio de Ordenes respondió: La orden ID={} no existe", request.getOrderId());
            throw new RecursoNoEncontradoException("No se puede enviar la notificación: La orden especificada no existe");
        } catch (FeignException e) {
            log.error("Error de comunicación con servicio de Ordenes: {}", e.getMessage());
            throw new ServicioNoDisponibleException("Servicio de Ordenes no disponible momentáneamente. Intente más tarde.");
        }

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
        Comms c = commsRepository.findById(id)
            .orElseThrow(() -> new RecursoNoEncontradoException("Mensaje no encontrado"));

        return convertirADTO(c);
    }

    // Buscar Mensaje por id de orden
    public List<CommsDTO> obtenerPorOrderId(Long orderId) {
        log.info("Solicitando listado de Mensajes para la orden ID={}", orderId);
        return commsRepository.findByOrderId(orderId).stream()
                .map(this::convertirADTO)
                .collect(Collectors.toList());
    }

    // Buscar Mensaje por id de orden
    public List<CommsDTO> obtenerPorUserId(Long userId) {
        log.info("Solicitando listado de Mensajes para el user ID={}", userId);
        return commsRepository.findByUserId(userId).stream()
                .map(this::convertirADTO)
                .collect(Collectors.toList());
    }

    // Eliminar Pago por id
    public boolean eliminarMensaje(Long id) {
        log.info("Intentando eliminar usuario ID={}", id);
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
        return commsRepository.findById(id)
                .map(comms -> {
                    comms.setUserId(dto.getUserId());
                    comms.setOrderId(dto.getOrderId());
                    comms.setCorreo(dto.getCorreo());
                    Comms actualizada = commsRepository.save(comms);
                    log.info("Mensaje ID={} actualizado con éxito", id);
                    return convertirADTO(actualizada);
                })
                .orElseThrow(() -> {
                    log.warn("Intento fallido de actualizar mensaje: ID={} no existe", id);
                    return new RecursoNoEncontradoException("No existe el mensaje con ID: " + id);
                });
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