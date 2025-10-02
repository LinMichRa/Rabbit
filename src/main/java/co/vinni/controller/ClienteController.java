package co.vinni.controller;

import co.vinni.model.Cliente;
import co.vinni.service.ClienteService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/clientes")
public class ClienteController {

    private final ClienteService clienteService;

    public ClienteController(ClienteService clienteService) {
        this.clienteService = clienteService;
    }

    @PostMapping("/registro")
    public String registrar(@RequestBody Cliente cliente) {
        clienteService.registrar(cliente);
        return "Cliente registrado con éxito";
    }
}

