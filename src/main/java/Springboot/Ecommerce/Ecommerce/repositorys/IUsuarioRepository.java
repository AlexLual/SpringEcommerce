package Springboot.Ecommerce.Ecommerce.repositorys;

import Springboot.Ecommerce.Ecommerce.models.Usuarios;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IUsuarioRepository extends JpaRepository<Usuarios, Integer> {
}
