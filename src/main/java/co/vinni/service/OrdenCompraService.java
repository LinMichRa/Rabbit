package co.vinni.service;

import co.vinni.Producer;
import co.vinni.model.CarritoItem;
import co.vinni.model.OrdenCompra;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

@Service
public class OrdenCompraService {

    private static final String QUEUE = "OrdenesQueue";
    private final AtomicLong contadorId = new AtomicLong(1);

    public OrdenCompra procesarOrden(List<CarritoItem> items, String nombreTitular, String numeroTarjeta) {
        // 1. Calcular total
        double total = items.stream()
                .mapToDouble(item -> item.getPrecio() * item.getCantidad())
                .sum();

        // 2. Crear la orden
        OrdenCompra orden = new OrdenCompra(items, total, nombreTitular, numeroTarjeta);
        orden.setId(contadorId.getAndIncrement());

        // 3. Enviar a RabbitMQ
        try {
            ObjectMapper mapper = new ObjectMapper();
            // Registrar el módulo JSR310 para soporte de LocalDateTime
            mapper.findAndRegisterModules();
            String jsonMsg = mapper.writeValueAsString(orden);
            Producer.sendMsg(jsonMsg, QUEUE, "Act1");
            System.out.println("🛒 Orden de compra enviada a Rabbit: " + jsonMsg);
        } catch (Exception e) {
            System.err.println("❌ Error enviando orden a RabbitMQ: " + e.getMessage());
            e.printStackTrace();
        }

        return orden;
    }

    public double calcularTotal(List<CarritoItem> items) {
        return items.stream()
                .mapToDouble(item -> item.getPrecio() * item.getCantidad())
                .sum();
    }
}