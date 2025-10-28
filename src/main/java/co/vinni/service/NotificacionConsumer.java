package co.vinni.service;

import com.rabbitmq.client.*;
import org.springframework.stereotype.Service;

import jakarta.annotation.PostConstruct;
import java.nio.charset.StandardCharsets;

@Service
public class NotificacionConsumer {

    private static final String QUEUE_CLIENTES = "Act1";
    private static final String QUEUE_PRODUCTOS = "ProductosQueue";
    private static final String QUEUE_ORDENES = "OrdenesQueue";
    private static final String HOST = "127.0.0.1";

    @PostConstruct
    public void iniciarConsumidores() {
        // Consumer para clientes
        iniciarConsumidorClientes();
        
        // Consumer para productos
        iniciarConsumidorProductos();
        
        // Consumer para órdenes de compra
        iniciarConsumidorOrdenes();
    }

    private void iniciarConsumidorClientes() {
        new Thread(() -> {
            try {
                ConnectionFactory factory = new ConnectionFactory();
                factory.setHost(HOST);
                factory.setVirtualHost("Act1");

                Connection connection = factory.newConnection();
                Channel channel = connection.createChannel();

                channel.queueDeclare(QUEUE_CLIENTES, true, false, false, null);
                System.out.println(" [*] Esperando mensajes de CLIENTES en la cola: " + QUEUE_CLIENTES);

                channel.basicConsume(QUEUE_CLIENTES, false, (consumerTag, delivery) -> {
                    String message = new String(delivery.getBody(), StandardCharsets.UTF_8);
                    System.out.println(" [x] CLIENTE Recibido: " + message);
                    enviarCorreoCliente(message);
                    // Confirmación manual
                    channel.basicAck(delivery.getEnvelope().getDeliveryTag(), false);
                }, consumerTag -> {});

            } catch (Exception e) {
                System.err.println("Error en consumer de clientes: " + e.getMessage());
                e.printStackTrace();
            }
        }).start();
    }

    private void iniciarConsumidorProductos() {
        new Thread(() -> {
            try {
                ConnectionFactory factory = new ConnectionFactory();
                factory.setHost(HOST);
                factory.setVirtualHost("Act1");

                Connection connection = factory.newConnection();
                Channel channel = connection.createChannel();

                channel.queueDeclare(QUEUE_PRODUCTOS, true, false, false, null);
                System.out.println(" [*] Esperando mensajes de PRODUCTOS en la cola: " + QUEUE_PRODUCTOS);

                channel.basicConsume(QUEUE_PRODUCTOS, false, (consumerTag, delivery) -> {
                    String message = new String(delivery.getBody(), StandardCharsets.UTF_8);
                    System.out.println(" [x] PRODUCTO Recibido: " + message);
                    enviarCorreoProducto(message);
                    // Confirmación manual
                    channel.basicAck(delivery.getEnvelope().getDeliveryTag(), false);
                }, consumerTag -> {});

            } catch (Exception e) {
                System.err.println("Error en consumer de productos: " + e.getMessage());
                e.printStackTrace();
            }
        }).start();
    }

    private void iniciarConsumidorOrdenes() {
        new Thread(() -> {
            try {
                ConnectionFactory factory = new ConnectionFactory();
                factory.setHost(HOST);
                factory.setVirtualHost("Act1");

                Connection connection = factory.newConnection();
                Channel channel = connection.createChannel();

                channel.queueDeclare(QUEUE_ORDENES, true, false, false, null);
                System.out.println(" [*] Esperando mensajes de ÓRDENES DE COMPRA en la cola: " + QUEUE_ORDENES);

                channel.basicConsume(QUEUE_ORDENES, false, (consumerTag, delivery) -> {
                    String message = new String(delivery.getBody(), StandardCharsets.UTF_8);
                    System.out.println(" [x] ORDEN DE COMPRA Recibida: " + message);
                    procesarOrdenCompra(message);
                    // Confirmación manual
                    channel.basicAck(delivery.getEnvelope().getDeliveryTag(), false);
                }, consumerTag -> {});

            } catch (Exception e) {
                System.err.println("Error en consumer de órdenes: " + e.getMessage());
                e.printStackTrace();
            }
        }).start();
    }

    private void enviarCorreoCliente(String datos) {
        System.out.println("📧 Simulando envío de correo para CLIENTE con datos: " + datos);
        // Aquí luego puedes implementar JavaMailSender si quieres real
    }

    private void enviarCorreoProducto(String datos) {
        System.out.println("📧 Simulando envío de correo para PRODUCTO con datos: " + datos);
        // Aquí luego puedes implementar JavaMailSender si quieres real
    }

    private void procesarOrdenCompra(String datos) {
        System.out.println("🛒 Procesando ORDEN DE COMPRA: " + datos);
        System.out.println("📧 Enviando confirmación de compra por email...");
        System.out.println("📦 Iniciando proceso de envío...");
        System.out.println("💳 Procesando pago...");
        // Aquí luego puedes implementar JavaMailSender, integración con APIs de pago, etc.
    }
}
