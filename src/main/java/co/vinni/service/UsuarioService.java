package co.vinni.service;

import co.vinni.model.Usuario;
import co.vinni.repositories.UsuarioRepository;
import org.springframework.stereotype.Service;

@Service
public class UsuarioService {

    private final UsuarioRepository usuarioRepo;

    public UsuarioService(UsuarioRepository usuarioRepo) {
        this.usuarioRepo = usuarioRepo;
    }

    public Usuario validarLogin(String username, String password) {
        Usuario user = usuarioRepo.findByUsername(username);
        if (user != null && user.getPassword().equals(password)) {
            return user;
        }
        return null;
    }

    // Método opcional para crear el admin si no existe
    public void crearAdminPorDefecto() {
        if (usuarioRepo.findByUsername("admin") == null) {
            Usuario admin = new Usuario("admin", "123", "ADMIN");
            usuarioRepo.save(admin);
        }
    }
}
