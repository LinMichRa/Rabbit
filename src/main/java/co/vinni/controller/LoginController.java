package co.vinni.controller;

import co.vinni.model.Cliente;
import co.vinni.model.Usuario;
import co.vinni.service.ClienteService;
import co.vinni.service.UsuarioService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class LoginController {

    private final UsuarioService usuarioService;
    private final ClienteService clienteService;

    public LoginController(UsuarioService usuarioService, ClienteService clienteService) {
        this.usuarioService = usuarioService;
        this.clienteService = clienteService;
    }

    @GetMapping("/login")
    public String formLogin() {
        return "login";
    }

    @PostMapping("/login")
    public String login(@RequestParam String username,
                        @RequestParam String password,
                        Model model) {

        Usuario user = usuarioService.validarLogin(username, password);
        if (user != null) {
            if ("ADMIN".equals(user.getRol())) {
                return "redirect:/admin/panel";
            } else {
                return "redirect:/cliente/catalogo";
            }
        }

        Cliente cliente = clienteService.validarLogin(username, password);
        if (cliente != null) {
            return "redirect:/clientes/catalogo";
        }

        model.addAttribute("error", "Credenciales inválidas");
        return "login";
    }
}
