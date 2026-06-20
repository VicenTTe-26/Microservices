package payment.folder.payment_service.service;

import payment.folder.payment_service.client.OrdenClient;
import payment.folder.payment_service.model.Payment;
import payment.folder.payment_service.repository.PaymentRepository;
import feign.FeignException;
import payment.folder.payment_service.dto.PaymentDTO;
import payment.folder.payment_service.dto.PaymentCreateDTO;
import payment.folder.payment_service.dto.OrdenDTO;
import payment.folder.payment_service.exception.RecursoNoEncontradoException;
import payment.folder.payment_service.exception.ServicioNoDisponibleException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class PaymentService {
    
    private static final Logger log = LoggerFactory.getLogger(PaymentService.class);

    private final PaymentRepository paymentRepository;
    private OrdenClient ordenClient; 

    public PaymentService(PaymentRepository paymentRepository, OrdenClient ordenClient) {
        this.paymentRepository = paymentRepository;
        this.ordenClient = ordenClient;
    }

    public PaymentDTO crearPago(PaymentCreateDTO request) {
        log.info("Validando Pago con id={}", request.getOrderId());
        log.info("Verificando existencia para la orden con id={}", request.getOrderId());

        // Llamada al microservicio de Order
        OrdenDTO orden;
        try {
            orden = ordenClient.buscarOrdenPorId(request.getOrderId());
            log.info("Pago confirmado por la Orden: '{}'", orden.getId());
        } catch (FeignException.NotFound e) {
            log.warn("Servicio Orden respondió: La orden ID={} no existe", request.getOrderId());
            throw new RecursoNoEncontradoException("No se puede crear el pago: La orden especificado no existe.");
        } catch (FeignException e) {
            log.error("Error al consultar servicio Order: {}", e.getMessage());
            throw new ServicioNoDisponibleException("Servicio de Orden no disponible para verificar el id.");
        } 
        


        Payment nuevo = new Payment();
        nuevo.setOrderId(request.getOrderId());
        nuevo.setTotalAmount(request.getTotalAmount());
        nuevo.setMetodoPago(request.getMetodoPago());;
        
        Payment guardadoNuevo = paymentRepository.save(nuevo);
        log.info("Nuevo pago registrado con el ID={}", guardadoNuevo.getId());
        return convertirADTO(guardadoNuevo);

    }


    // Listar todos los Pagos
    public List<PaymentDTO> listarTodas() {
        log.info("Solicitando listado completo de todos los pagos");
        return paymentRepository.findAll().stream()
                .map(this::convertirADTO)
                .collect(Collectors.toList());
    }
    // Buscar Pago por id
    public PaymentDTO findDtoById(Long id) {
        return paymentRepository.findById(id)
            .map(payment -> convertirADTO(payment))
            .orElseThrow(() -> new RecursoNoEncontradoException("Pago no encontrado"));

    }

    // Buscar Pago por id de orden
        public List<PaymentDTO> obtenerPorOrdenId(Long orderId) {
        log.info("Solicitando listado de Pagos para el order ID={}", orderId);
        return paymentRepository.findByOrderId(orderId).stream()
                .map(this::convertirADTO)
                .collect(Collectors.toList());
    }

    // Eliminar Pago por id
    public boolean eliminarPago(Long id) {
        log.info("Intentando eliminar pago ID={}", id);
        if (paymentRepository.existsById(id)) {
            paymentRepository.deleteById(id);
            log.info("Pago ID={} eliminado con éxito", id);
            return true;
        }
        log.warn("No se pudo eliminar: Pago ID={} no existe", id);
        return false;
    }

    // Actualizar Pago por id
   public PaymentDTO actualizar(Long id, PaymentCreateDTO dto) {
        log.info("Actualizando Pago id={}", id);
        Payment p = paymentRepository.findById(id)
                .orElseThrow(() -> new RecursoNoEncontradoException("Pago no encontrado: " + id));
        p.setOrderId(dto.getOrderId());
        p.setTotalAmount(dto.getTotalAmount());
        p.setMetodoPago(dto.getMetodoPago());
        return convertirADTO(paymentRepository.save(p));
    }


        //METODOS DE APOYO-RAPIDEZ
    
    private PaymentDTO convertirADTO(Payment payment) {
        return new PaymentDTO(
            payment.getId(),
            payment.getOrderId(),
            payment.getTotalAmount(),
            payment.getMetodoPago()
        );
    }

}

