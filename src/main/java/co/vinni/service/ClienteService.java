package co.vinni.service;

import co.vinni.Producer;
import co.vinni.model.Cliente;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

@Service
public class ClienteService {

    private static final String QUEUE = "Act1";

    public void registrar(Cliente cliente) {
        try {
            // Convertimos el objeto a JSON para enviar
            ObjectMapper mapper = new ObjectMapper();
            String jsonMsg = mapper.writeValueAsString(cliente);

            // Llamamos a tu Producer actual
            Producer.sendMsg(jsonMsg, QUEUE, "Act1");

            System.out.println("Mensaje enviado a Rabbit: " + jsonMsg);

        } catch (IOException | TimeoutException e) {
            e.printStackTrace();
        }
    }
}
