package logistic.folder.logistic_service.service;

import logistic.folder.logistic_service.client.OrdenClient;
import logistic.folder.logistic_service.model.Envio;
import logistic.folder.logistic_service.repository.LogisticRepository;
import feign.FeignException;
import logistic.folder.logistic_service.dto.OrdenDTO;
import logistic.folder.logistic_service.dto.LogisticDTO;
import logistic.folder.logistic_service.dto.LogisticCreateDTO;
import logistic.folder.logistic_service.exception.RecursoNoEncontradoException;
import logistic.folder.logistic_service.exception.ServicioNoDisponibleException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class LogisticService {
    
    private static final Logger log = LoggerFactory.getLogger(LogisticService.class);

    private final LogisticRepository logisticRepository;
    private final OrdenClient ordenClient; 

    public LogisticService(LogisticRepository logisticRepository, OrdenClient ordenClient) {
        this.logisticRepository = logisticRepository;
        this.ordenClient = ordenClient;
    }

    private void validarExistenciaOrden(Long orderId) {
        log.info("Validando existencia para orden con id={}", orderId);
        try {
            OrdenDTO orden = ordenClient.buscarOrdenPorId(orderId);
            log.info("Orden confirmada por Order Service: '{}'", orden.getId());
        } catch (FeignException.NotFound e) {
            log.warn("Servicio Orden respondió: La orden ID={} no existe", orderId);
            throw new RecursoNoEncontradoException("La orden especificada no existe.");
        } catch (FeignException e) {
            log.error("Error al consultar servicio Order: {}", e.getMessage());
            throw new ServicioNoDisponibleException("Servicio de Orden no disponible para verificar la id.");
        }
    }

    public LogisticDTO crearEnvio(LogisticCreateDTO request) {
        log.info("Iniciando creación de envío para orden con id={}", request.getOrderId());
        
        validarExistenciaOrden(request.getOrderId());

        Envio nuevo = new Envio();
        nuevo.setOrderId(request.getOrderId());
        nuevo.setDestinatarioNombre(request.getDestinatarioNombre());
        nuevo.setDireccionCompleta(request.getDireccionCompleta());
        nuevo.setProveedorEnvio(request.getProveedorEnvio());
        nuevo.setEstadoEnvio(request.getEstadoEnvio());
        nuevo.setFechaEstimadaEntrega(request.getFechaEstimadaEntrega());
        
        Envio guardadoNuevo = logisticRepository.save(nuevo);
        log.info("Nuevo Envio registrado con el ID={}", guardadoNuevo.getId());
        return convertirADTO(guardadoNuevo);

    }


    // Listar todos los Envios
    public List<LogisticDTO> listarTodas() {
        log.info("Solicitando listado completo de todos los envios");
        return logisticRepository.findAll().stream()
                .map(this::convertirADTO)
                .collect(Collectors.toList());
    }
    // Buscar Envio por id
    public LogisticDTO findDtoById(Long id) {
        return logisticRepository.findById(id)
            .map(envio -> convertirADTO(envio))
            .orElseThrow(() -> new RecursoNoEncontradoException("Envio no encontrado"));

        
    }

    // Buscar Envio por id de orden
        public List<LogisticDTO> obtenerPorOrderId(Long orderId) {
        log.info("Solicitando listado de Envios para la orden ID={}", orderId);
        return logisticRepository.findByOrderId(orderId).stream()
                .map(this::convertirADTO)
                .collect(Collectors.toList());
    }

    // Eliminar Envio por id
    public boolean eliminarEnvio(Long id) {
        log.info("Intentando eliminar envio ID={}", id);
        if (logisticRepository.existsById(id)) {
            logisticRepository.deleteById(id);
            log.info("Envio ID={} eliminado con éxito", id);
            return true;
        }
        log.warn("No se pudo eliminar: Envio ID={} no existe", id);
        return false;
    }

    // Actualizar Envio por id
   public LogisticDTO actualizar(Long id, LogisticCreateDTO dto) {
        log.info("Actualizando Envio id={}", id);
        Envio e = logisticRepository.findById(id)
                .orElseThrow(() -> new RecursoNoEncontradoException("Envio no encontrado: " + id));

        validarExistenciaOrden(dto.getOrderId());

        e.setOrderId(dto.getOrderId());
        e.setDestinatarioNombre(dto.getDestinatarioNombre());
        e.setDireccionCompleta(dto.getDireccionCompleta());
        e.setProveedorEnvio(dto.getProveedorEnvio());
        e.setEstadoEnvio(dto.getEstadoEnvio());
        e.setFechaEstimadaEntrega(dto.getFechaEstimadaEntrega());
        return convertirADTO(logisticRepository.save(e));
    }


        //METODOS DE APOYO-RAPIDEZ
    
    private LogisticDTO convertirADTO(Envio envio) {
        return new LogisticDTO(
            envio.getId(),
            envio.getOrderId(),
            envio.getDestinatarioNombre(),
            envio.getDireccionCompleta(),
            envio.getProveedorEnvio(),
            envio.getEstadoEnvio(),
            envio.getFechaEstimadaEntrega()
        );
    }


}
