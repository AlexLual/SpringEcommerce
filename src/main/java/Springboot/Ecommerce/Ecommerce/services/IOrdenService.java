package Springboot.Ecommerce.Ecommerce.services;

import Springboot.Ecommerce.Ecommerce.models.Orden;

import java.util.List;

public interface IOrdenService {
    List<Orden> findAll();

    Orden save(Orden orden);
}
