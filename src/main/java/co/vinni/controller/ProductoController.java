package co.vinni.controller;

import co.vinni.model.Producto;
import co.vinni.service.ProductoService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/admin/productos")
public class ProductoController {

    private final ProductoService productoService;

    public ProductoController(ProductoService productoService) {
        this.productoService = productoService;
    }

    @GetMapping
    public String listarProductos(Model model) {
        model.addAttribute("productos", productoService.listar());
        return "lista_productos";
    }

    @GetMapping("/nuevo")
    public String formularioNuevoProducto(Model model) {
        model.addAttribute("producto", new Producto());
        return "nuevo_producto";
    }

    @PostMapping("/guardar")
    public String guardarProducto(Producto producto) {
        productoService.guardar(producto);
        return "redirect:/admin/productos";
    }

    @GetMapping("/editar/{id}")
    public String editarProducto(@PathVariable Long id, Model model) {
        Producto producto = productoService.obtenerPorId(id);
        model.addAttribute("producto", producto);
        return "editar_producto";
    }

    @PostMapping("/actualizar/{id}")
    public String actualizarProducto(@PathVariable Long id, @ModelAttribute Producto producto) {
        Producto existente = productoService.obtenerPorId(id);
        if (existente != null) {
            existente.setNombre(producto.getNombre());
            existente.setPrecio(producto.getPrecio());
            existente.setDescripcion(producto.getDescripcion());
            productoService.guardar(existente);
        }
        return "redirect:/admin/productos";
    }

    @GetMapping("/eliminar/{id}")
    public String eliminarProducto(@PathVariable Long id) {
        productoService.eliminar(id);
        return "redirect:/admin/productos";
    }
}
