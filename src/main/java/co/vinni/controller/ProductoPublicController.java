package co.vinni.controller;

import co.vinni.model.Producto;
import co.vinni.service.ProductoService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/productos")
public class ProductoPublicController {

    private final ProductoService productoService;

    public ProductoPublicController(ProductoService productoService) {
        this.productoService = productoService;
    }

    // ✅ Mostrar formulario de creación de producto (similar a /clientes/form)
    @GetMapping("/form")
    public String mostrarFormulario(Model model) {
        model.addAttribute("producto", new Producto());
        return "crear_producto_publico"; // Vista específica para endpoint público
    }

    // ✅ Procesar formulario de producto
    @PostMapping("/crear")
    public String crearProducto(Producto producto) {
        productoService.guardar(producto);
        return "redirect:/productos/form?success=true";
    }

    // ✅ Listar todos los productos (opcional)
    @GetMapping("/todos")
    public String listarTodos(Model model) {
        model.addAttribute("productos", productoService.listar());
        return "lista_productos";
    }
}