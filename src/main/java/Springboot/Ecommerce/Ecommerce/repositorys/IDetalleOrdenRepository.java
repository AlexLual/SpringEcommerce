package Springboot.Ecommerce.Ecommerce.repositorys;

import Springboot.Ecommerce.Ecommerce.models.DetalleOrden;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IDetalleOrdenRepository extends JpaRepository<DetalleOrden, Integer> {
}
