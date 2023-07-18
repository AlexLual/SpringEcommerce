package Springboot.Ecommerce.Ecommerce.repositorys;

import Springboot.Ecommerce.Ecommerce.models.Orden;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IOrdenRepository extends JpaRepository<Orden, Integer> {
}
