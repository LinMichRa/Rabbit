package co.vinni.service;

import co.vinni.Producer;
import co.vinni.model.Cliente;
import co.vinni.repositories.ClienteRepository;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;


@Service
public class ClienteService {

    private final ClienteRepository clienteRepository;
    private static final String QUEUE = "Act1";

    public ClienteService(ClienteRepository clienteRepository) {
        this.clienteRepository = clienteRepository;
    }

    public void registrar(Cliente cliente) {
        // 1. Guardar en base de datos
        clienteRepository.save(cliente);

        // 2. Enviar a RabbitMQ
        try {
            ObjectMapper mapper = new ObjectMapper();
            String jsonMsg = mapper.writeValueAsString(cliente);
            Producer.sendMsg(jsonMsg, QUEUE, "Act1");
            System.out.println("Mensaje enviado a Rabbit: " + jsonMsg);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public Cliente validarLogin(String usernameOrEmail, String password) {

        Cliente cliente = clienteRepository.findByEmailAndPassword(usernameOrEmail, password);

        if (cliente == null) {
            cliente = clienteRepository.findByUsernameAndPassword(usernameOrEmail, password);
        }

        return cliente;
    }
}

