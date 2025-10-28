package co.vinni.service;

import co.vinni.Producer;
import co.vinni.model.Producto;
import co.vinni.repositories.ProductoRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductoService {

    private final ProductoRepository productoRepository;
    private static final String QUEUE = "ProductosQueue";

    public ProductoService(ProductoRepository productoRepository) {
        this.productoRepository = productoRepository;
    }

    public List<Producto> listar() {
        return productoRepository.findAll();
    }

    public void guardar(Producto producto) {
        // 1. Guardar en base de datos
        Producto productoGuardado = productoRepository.save(producto);

        // 2. Enviar a RabbitMQ solo si es un producto nuevo (recién creado)
        if (producto.getId() == null || productoGuardado.getId() != null) {
            try {
                ObjectMapper mapper = new ObjectMapper();
                // Registrar módulos para soporte completo de tipos de datos
                mapper.findAndRegisterModules();
                String jsonMsg = mapper.writeValueAsString(productoGuardado);
                Producer.sendMsg(jsonMsg, QUEUE, "Act1");
                System.out.println("Mensaje de producto enviado a Rabbit: " + jsonMsg);
            } catch (Exception e) {
                System.err.println("Error enviando mensaje de producto a RabbitMQ: " + e.getMessage());
                e.printStackTrace();
            }
        }
    }

    public Producto obtenerPorId(Long id) {
        return productoRepository.findById(id).orElse(null);
    }

    public void eliminar(Long id) {
        productoRepository.deleteById(id);
    }
}
