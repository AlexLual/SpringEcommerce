package Springboot.Ecommerce.Ecommerce.controllers;

import Springboot.Ecommerce.Ecommerce.models.Usuarios;
import Springboot.Ecommerce.Ecommerce.services.IUsuarioService;
import jakarta.servlet.http.HttpSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Optional;


@Controller
@RequestMapping("/usuario")
public class UsuarioController {
    private final Logger logger= LoggerFactory.getLogger(UsuarioController.class);
    @Autowired
    private IUsuarioService iUsuarioService;

// /usuario/registro
    @GetMapping("/registro")
    public String create(){
        return "usuario/registro";
    }
    @PostMapping("/save")
    public String save(Usuarios usuarios){
        logger.info("Usuario registro: {}", usuarios);
        usuarios.setTipo("USER");
        iUsuarioService.save(usuarios);

        return "redirect:/";
    }
//Aqui empieza el login para usuario
@GetMapping("/login")
public String login(){
        return "usuario/login";
}
@PostMapping("/acceder")
public String acceder(Usuarios usuarios, HttpSession session){
        logger.info("Accesos :{} ",usuarios);
    Optional<Usuarios> user=iUsuarioService.findByEmail(usuarios.getEmail());
   // logger.info("Usuario de db: {}",user.get());
    if (user.isPresent()){
        session.setAttribute("idusuario",user.get().getId());
        if (user.get().getTipo().equals("ADMIN")){
            return "redirect:/administrador";
        }else {
            return "redirect:/";
        }
    }else {
        logger.info("usuario no existe");
    }



        return "redirect:/";
}
@GetMapping("/compras")
    public String obtenerCompras(Model model, HttpSession session){
        model.addAttribute("sesion", session.getAttribute("idusuario"));

        return "usuario/compras";
}


}
