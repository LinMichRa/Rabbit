package co.vinni.service;

import com.rabbitmq.client.*;

import jakarta.annotation.PostConstruct;
import java.nio.charset.StandardCharsets;

public class NotificacionConsumer {

    private static final String QUEUE = "Act1";
    private static final String HOST = "127.0.0.1";

    @PostConstruct
    public void iniciarConsumidor() {
        new Thread(() -> {
            try {
                ConnectionFactory factory = new ConnectionFactory();
                factory.setHost(HOST);
                factory.setVirtualHost("Act1");


                Connection connection = factory.newConnection();
                Channel channel = connection.createChannel();

                channel.queueDeclare(QUEUE, true, false, false, null);
                System.out.println(" [*] Esperando mensajes en la cola: " + QUEUE);

                DeliverCallback deliverCallback = (consumerTag, delivery) -> {
                    String message = new String(delivery.getBody(), StandardCharsets.UTF_8);
                    System.out.println(" [x] Recibido: " + message);

                    // Aquí simulas el envío de correo
                    enviarCorreo(message);
                };

                channel.basicConsume(QUEUE, false, (consumerTag, delivery) -> {
                    String message = new String(delivery.getBody(), StandardCharsets.UTF_8);
                    System.out.println(" [x] Recibido: " + message);
                    enviarCorreo(message);
                    // Confirmación manual
                    channel.basicAck(delivery.getEnvelope().getDeliveryTag(), false);
                }, consumerTag -> {});

            } catch (Exception e) {
                e.printStackTrace();
            }
        }).start();
    }

    private void enviarCorreo(String datos) {
        System.out.println("Simulando envío de correo con datos: " + datos);
        // Aquí luego puedes implementar JavaMailSender si quieres real
    }
}
