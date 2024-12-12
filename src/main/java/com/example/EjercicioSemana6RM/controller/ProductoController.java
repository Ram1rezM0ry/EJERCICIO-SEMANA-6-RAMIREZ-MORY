package com.example.EjercicioSemana6RM.controller;

import com.example.EjercicioSemana6RM.model.Producto;
import com.example.EjercicioSemana6RM.repository.ProductoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Controller
public class ProductoController {

    @Autowired
    private ProductoRepository productoRepository;

    @PostMapping("/submitProducto")
    public String procesarFormulario(@ModelAttribute Producto producto, Model model) {
        productoRepository.save(producto);
        model.addAttribute("producto", producto);
        return "redirect:/listarProductos";
    }

    @GetMapping("/submitProducto")
    public String mostrarFormulario(Model model) {
        model.addAttribute("producto", new Producto());
        return "formularioProducto"; // Nombre de la plantilla Thymeleaf
    }

    @GetMapping("/listarProductos")
    public String listarProductos(Model model) {
        List<Producto> productos = productoRepository.findAll();
        model.addAttribute("productos", productos);
        model.addAttribute("producto", new Producto()); // Agregar el objeto producto vacío
        return "listarProductos";
    }

    @GetMapping("/editarProducto/{id}")
    public String editarProducto(@PathVariable("id") Long id, Model model) {
        Producto producto = productoRepository.findById(id).orElse(null);
        if (producto != null) {              
            model.addAttribute("producto", producto);
        return "formularioProducto";
    }
    return "redirect:/listarProductos";
}


    @PostMapping("/submitEdicionProducto")
    public String submitEdicionProducto(@ModelAttribute Producto producto, Model model) {
        if (producto.getId() != null) {
            Producto productoExistente = productoRepository.findById(producto.getId()).orElse(null);
            if (productoExistente != null) {
                productoExistente.setNombre(producto.getNombre());
                productoExistente.setCategoria(producto.getCategoria());
                productoExistente.setFechaVencimiento(producto.getFechaVencimiento());
                System.out.println("Fecha recibida: " + producto.getFechaVencimiento());
                productoExistente.setStockMinimo(producto.getStockMinimo());
                productoExistente.setStockMaximo(producto.getStockMaximo());
                productoExistente.setMarca(producto.getMarca());
                productoExistente.setPeso(producto.getPeso());
                productoRepository.save(productoExistente);
                model.addAttribute("producto", productoExistente);
            }
        }
        return "redirect:/listarProductos";
    }

    @GetMapping("/eliminarProducto/{id}")
    public String eliminarProducto(@PathVariable("id") Long id) {
        productoRepository.deleteById(id);
        return "redirect:/listarProductos";
    }
}