package Springboot.Ecommerce.Ecommerce.repositorys;

import Springboot.Ecommerce.Ecommerce.models.Producto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IProductoRepository extends JpaRepository<Producto,Integer> {
}
