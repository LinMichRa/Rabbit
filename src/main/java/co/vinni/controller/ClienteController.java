package co.vinni.controller;

import co.vinni.model.CarritoItem;
import co.vinni.model.Cliente;
import co.vinni.model.Producto;
import co.vinni.service.ClienteService;
import co.vinni.service.ProductoService;
import jakarta.servlet.http.HttpSession;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/clientes")
public class ClienteController {

    private final ClienteService clienteService;
    private final ProductoService productoService;

    // ✅ Constructor ÚNICO con ambos servicios
    public ClienteController(ClienteService clienteService, ProductoService productoService) {
        this.clienteService = clienteService;
        this.productoService = productoService;
    }

    // ✅ Mostrar formulario de registro de cliente
    @GetMapping("/form")
    public String mostrarFormulario(Model model) {
        model.addAttribute("cliente", new Cliente());
        return "registro"; // Asegúrate que la vista exista
    }

    // ✅ Procesar formulario
    @PostMapping("/registro")
    public String registrarDesdeWeb(Cliente cliente) {
        clienteService.registrar(cliente);
        return "redirect:/clientes/form?success=true";
    }

    // ✅ Mostrar catálogo de productos
    @GetMapping("/catalogo")
    public String verCatalogo(Model model, HttpSession session) {
        model.addAttribute("productos", productoService.listar());
        List<CarritoItem> carrito = (List<CarritoItem>) session.getAttribute("carrito");
        if (carrito == null) carrito = new ArrayList<>();
        model.addAttribute("carrito", carrito);
        return "catalogo";
    }
    @PostMapping("/carrito/quitar")
    public String quitarDelCarrito(@RequestParam Long idProducto, HttpSession session) {
        List<CarritoItem> carrito = (List<CarritoItem>) session.getAttribute("carrito");
        if (carrito != null) {
            carrito.removeIf(item -> item.getIdProducto().equals(idProducto));
            session.setAttribute("carrito", carrito);
        }
        return "redirect:/clientes/catalogo";
    }

    @PostMapping("/carrito/pagar")
    public String pagarCarrito(HttpSession session) {
        session.removeAttribute("carrito");
        // Aquí puedes agregar lógica de pago real
        return "redirect:/clientes/catalogo?pagado=true";
    }

    // ✅ Perfil del cliente
    @GetMapping("/perfil")
    public String perfilCliente() {
        return "perfil-cliente"; // Reemplazar luego con datos reales
    }

    @GetMapping("/carrito")
    public String verCarrito(Model model, HttpSession session) {
        List<CarritoItem> carrito = (List<CarritoItem>) session.getAttribute("carrito");
        if (carrito == null) {
            carrito = new ArrayList<>();
        }
        model.addAttribute("carrito", carrito);
        return "carrito";
    }

    @PostMapping("/carrito/agregar")
    public String agregarAlCarrito(@RequestParam Long idProducto, HttpSession session) {
        Producto producto = productoService.obtenerPorId(idProducto);
        if (producto == null) {
            return "redirect:/clientes/catalogo?error=notfound";
        }

        // Obtener carrito desde sesión
        List<CarritoItem> carrito = (List<CarritoItem>) session.getAttribute("carrito");
        if (carrito == null) {
            carrito = new ArrayList<>();
        }

        boolean existe = false;
        for (CarritoItem item : carrito) {
            if (item.getIdProducto().equals(idProducto)) {
                item.setCantidad(item.getCantidad() + 1);
                existe = true;
                break;
            }
        }

        if (!existe) {
            CarritoItem nuevo = new CarritoItem(
                producto.getId(),
                producto.getNombre(),
                producto.getPrecio(),
                1
            );
            carrito.add(nuevo);
        }

        session.setAttribute("carrito", carrito);
        return "redirect:/clientes/carrito";
    }

    @GetMapping("/checkout")
public String checkout(Model model, HttpSession session) {
    List<CarritoItem> carrito = (List<CarritoItem>) session.getAttribute("carrito");

    if (carrito == null || carrito.isEmpty()) {
        return "redirect:/clientes/carrito?empty=true";
    }

    double total = carrito.stream()
            .mapToDouble(item -> item.getPrecio() * item.getCantidad())
            .sum();

    model.addAttribute("carrito", carrito);
    model.addAttribute("total", total);
    return "checkout";
}

    @PostMapping("/procesar-pago")
    public String procesarPago(HttpSession session) {
        session.removeAttribute("carrito");
        return "redirect:/clientes/confirmacion";
    }

    @GetMapping("/confirmacion")
    public String confirmacion() {
        return "confirmacion";
    }

    
}
