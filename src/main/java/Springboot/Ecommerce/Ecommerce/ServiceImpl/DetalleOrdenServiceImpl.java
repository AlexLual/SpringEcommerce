package Springboot.Ecommerce.Ecommerce.ServiceImpl;

import Springboot.Ecommerce.Ecommerce.models.DetalleOrden;
import Springboot.Ecommerce.Ecommerce.repositorys.IDetalleOrdenRepository;
import Springboot.Ecommerce.Ecommerce.services.IDetalleOrdenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DetalleOrdenServiceImpl implements IDetalleOrdenService {
   @Autowired
   private IDetalleOrdenRepository iDetalleOrdenRepository;
    @Override
    public DetalleOrden save(DetalleOrden detalleOrden) {
        return iDetalleOrdenRepository.save(detalleOrden);
    }
}
