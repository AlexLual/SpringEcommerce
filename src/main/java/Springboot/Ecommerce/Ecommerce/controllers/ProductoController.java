package Springboot.Ecommerce.Ecommerce.controllers;

import Springboot.Ecommerce.Ecommerce.models.Producto;
import Springboot.Ecommerce.Ecommerce.models.Usuarios;
import Springboot.Ecommerce.Ecommerce.services.IUsuarioService;
import Springboot.Ecommerce.Ecommerce.services.ProductoService;
import Springboot.Ecommerce.Ecommerce.services.UploadFileService;
import jakarta.servlet.http.HttpSession;
import org.slf4j.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Optional;


@Controller
@RequestMapping("/productos")
public class ProductoController {
    private static final Logger LOGGER = LoggerFactory.getLogger(ProductoController.class);
    @Autowired
private ProductoService productoService;

@Autowired
private UploadFileService upload;
@Autowired
private IUsuarioService iUsuarioService;

    @GetMapping("")
    public String show(Model model){
        model.addAttribute("productos",productoService.findAll());
        return "productos/show";
    }
@GetMapping("/create")
    public String create(){
        return "productos/create";
    }


    @PostMapping("/save")
    public String save(Producto producto, @RequestParam("img") MultipartFile file, HttpSession session) throws IOException {
        LOGGER.info("Este es el objecto producto{}",producto);
        Usuarios u = iUsuarioService.findById(Integer.parseInt(session.getAttribute("idusuario").toString() )).get();
        producto.setUsuarios(u);
//imagen
        if (producto.getId()==null){
            String nombreImagen= upload.saveImages(file);
            producto.setImagen(nombreImagen);
        }else {

        }

        productoService.save(producto);
        return "redirect:/productos";
    }
@GetMapping("/edit/{id}")
    public String edit(@PathVariable Integer id, Model model){
        Producto producto = new Producto();
    Optional<Producto> optionalProducto=productoService.get(id);
   producto=optionalProducto.get();
   LOGGER.info("producto buscado: {}", producto);
   model.addAttribute("producto", producto);


        return "productos/edit";
    }
    @PostMapping("/update")
public String update(Producto producto, @RequestParam("img") MultipartFile file) throws IOException {
        Producto p = new Producto();
        p=productoService.get(producto.getId()).get();
        if (file.isEmpty()){
            producto.setImagen(p.getImagen());
        }else {//Cuando se edita tambien la imagen
            //Eliminar cuando no sea la imagen por default
            if(!p.getImagen().equals("default.jpg")){
                upload.deleteImages(p.getImagen());
            }
            String nombreImagen= upload.saveImages(file);
            producto.setImagen(nombreImagen);
        }
        producto.setUsuarios(p.getUsuarios());
        productoService.update(producto);
        return "redirect:/productos";
}
@GetMapping("/delete/{id}")
public String delete(@PathVariable Integer id){
        Producto p = new Producto();
        p = productoService.get(id).get();
        //Eliminar cuando no sea la imagen por default
    if(!p.getImagen().equals("default.jpg")){
        upload.deleteImages(p.getImagen());
    }
        productoService.delete(id);
        return "redirect:/productos";
}

}
