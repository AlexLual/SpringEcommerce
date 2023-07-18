package Springboot.Ecommerce.Ecommerce.ServiceImpl;

import Springboot.Ecommerce.Ecommerce.models.Orden;
import Springboot.Ecommerce.Ecommerce.repositorys.IOrdenRepository;
import Springboot.Ecommerce.Ecommerce.services.IOrdenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OrdenServiceimpl implements IOrdenService {
  @Autowired
  private IOrdenRepository iOrdenRepository;

    @Override
    public Orden save(Orden orden) {
        return iOrdenRepository.save(orden) ;
    }
}
