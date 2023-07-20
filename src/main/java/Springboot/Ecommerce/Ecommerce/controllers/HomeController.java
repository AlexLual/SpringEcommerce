package Springboot.Ecommerce.Ecommerce.controllers;

import Springboot.Ecommerce.Ecommerce.models.DetalleOrden;
import Springboot.Ecommerce.Ecommerce.models.Orden;
import Springboot.Ecommerce.Ecommerce.models.Producto;
import Springboot.Ecommerce.Ecommerce.models.Usuarios;
import Springboot.Ecommerce.Ecommerce.services.IDetalleOrdenService;
import Springboot.Ecommerce.Ecommerce.services.IOrdenService;
import Springboot.Ecommerce.Ecommerce.services.IUsuarioService;
import Springboot.Ecommerce.Ecommerce.services.ProductoService;
import jakarta.servlet.http.HttpSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/")
public class HomeController {
    private final Logger log = LoggerFactory.getLogger(HomeController.class);
    @Autowired
    private ProductoService productoService;
    @Autowired
    private IUsuarioService iUsuarioService;
    @Autowired
    private IDetalleOrdenService iDetalleOrdenService;
    @Autowired
    private IOrdenService iOrdenService;

    //Para al,macenar detalles order
    List<DetalleOrden> detalles = new ArrayList<DetalleOrden>();
//Dator orden
    Orden orden = new Orden();

    @GetMapping("")
    public String home(Model model, HttpSession session) {
        log.info("Session del usuario: {}", session.getAttribute("idusuario"));
        model.addAttribute("productos", productoService.findAll());
        //Sesion
        model.addAttribute("sesion", session.getAttribute("idusuario"));



        return "usuario/home";
    }

    @GetMapping("productohome/{id}")
    public String poductHome(@PathVariable Integer id, Model model) {
        log.info("Id product enviado como parametro {}", id);
        Producto producto = new Producto();
        Optional<Producto> productoOptional = productoService.get(id);
        producto = productoOptional.get();

        model.addAttribute("producto", producto);


        return "usuario/productohome";
    }

    @PostMapping("/cart")
    public String addCart(@RequestParam Integer id, @RequestParam Integer cantidad, Model model) {
        DetalleOrden detalleOrden = new DetalleOrden();
        Producto producto = new Producto();
        double sumaTotal = 0;
        Optional<Producto> optionalProducto = productoService.get(id);
        log.info("Producto añadido: {}", optionalProducto.get());
        log.info("cantidad: {}", cantidad);
        producto = optionalProducto.get();

        detalleOrden.setCantidad(cantidad);
        detalleOrden.setPrecio(producto.getPrecio());
        detalleOrden.setNombre(producto.getNombre());
        detalleOrden.setTotal(producto.getPrecio() * cantidad);
        detalleOrden.setProducto(producto);

        //Validaion de que producto no se agregue mas de una vez.
        Integer idProducto = producto.getId();
        boolean ingresado = detalles.stream().anyMatch(p -> p.getProducto().getId() == idProducto);
        if (!ingresado) {
            detalles.add(detalleOrden);
        }

        sumaTotal = detalles.stream().mapToDouble(dt -> dt.getTotal()).sum();
        orden.setTotal(sumaTotal);
        model.addAttribute("cart", detalles);
        model.addAttribute("orden", orden);
        return "usuario/carrito";
    }

    //quitar carro
    @GetMapping("/delete/cart/{id}")
    public String deleteProductCart(@PathVariable Integer id, Model model) {
        List<DetalleOrden> ordenesnuevas = new ArrayList<DetalleOrden>();
        for (DetalleOrden detalleOrden : detalles) {
            if (detalleOrden.getProducto().getId() != id) {
                ordenesnuevas.add(detalleOrden);
            }
        }
        //poner la nueva lista con los productos restantes
        detalles = ordenesnuevas;
        double sumaTotal = 0;

        sumaTotal = detalles.stream().mapToDouble(dt -> dt.getTotal()).sum();
        orden.setTotal(sumaTotal);
        model.addAttribute("cart", detalles);
        model.addAttribute("orden", orden);


        return "usuario/carrito";
    }

    @GetMapping("/getCart")
    public String getCard(Model model, HttpSession session){
        model.addAttribute("cart", detalles);
        model.addAttribute("orden", orden);
        //Session
        model.addAttribute("sesion", session.getAttribute("idusuario"));

        return "/usuario/carrito";
    }
@GetMapping("/order")
    public String order(Model model, HttpSession session){

    Usuarios usuarios= iUsuarioService.findById(Integer.parseInt(session.getAttribute("idusuario").toString())).get();

    model.addAttribute("cart", detalles);
    model.addAttribute("orden", orden);
    model.addAttribute("usuario", usuarios);

        return "usuario/resumenorden";
}


//Guardar de la orden
    //-HttpSession reemplaza guarda id de usuario, previamente iniciado en 1
@GetMapping("/saveorder")
    public String saverOrder(HttpSession session){
    Date fechaCreacion = new Date();
    orden.setFechaCreacion(fechaCreacion);
    orden.setNumero(iOrdenService.generarNumeroOrden());

    //Usuario
    Usuarios usuarios= iUsuarioService.findById(Integer.parseInt(session.getAttribute("idusuario").toString())).get();
    orden.setUsuarios(usuarios);
    iOrdenService.save(orden);


    //Guardar detalle
    for (DetalleOrden dt: detalles){
        dt.setOrden(orden);
        iDetalleOrdenService.save(dt);
    }
    //Limpiar lista
    orden =new Orden();
    detalles.clear();



        return "redirect:/ ";
}
//Buscar producto o filtrar.
@PostMapping("/search")
public String searchProduct(@RequestParam String nombre, Model model){
        log.info("Nombre del producto:{}", nombre);
        List<Producto> productos= productoService.findAll().stream().filter(p->p.getNombre().contains(nombre)).collect(Collectors.toList());
        model.addAttribute("productos", productos);
        return "usuario/home";
}

}
