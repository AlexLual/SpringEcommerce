package Springboot.Ecommerce.Ecommerce.services;

import Springboot.Ecommerce.Ecommerce.models.Usuarios;

import java.util.Optional;

public interface IUsuarioService {
    Optional<Usuarios> findById(Integer id);
    Usuarios save (Usuarios usuarios);
}
