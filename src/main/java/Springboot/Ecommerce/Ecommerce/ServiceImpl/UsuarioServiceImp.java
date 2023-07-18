package Springboot.Ecommerce.Ecommerce.ServiceImpl;

import Springboot.Ecommerce.Ecommerce.models.Usuarios;
import Springboot.Ecommerce.Ecommerce.repositorys.IUsuarioRepository;
import Springboot.Ecommerce.Ecommerce.services.IUsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
@Service
public class UsuarioServiceImp implements IUsuarioService {
   @Autowired
   private IUsuarioRepository usuarioRepository;

    @Override
    public Optional<Usuarios> findById(Integer id) {
        return usuarioRepository.findById(id);
    }
}