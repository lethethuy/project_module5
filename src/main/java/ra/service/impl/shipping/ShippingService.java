package ra.service.impl.shipping;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ra.exception.NotFoundException;
import ra.model.entity.Shipping;
import ra.model.entity.ShippingType;
import ra.repository.IShippingRepository;

import java.util.Optional;

@Service
public class ShippingService implements IShippingService {
    @Autowired
    private IShippingRepository shippingRepository;
    @Override
    public Shipping findByType(ShippingType shippingType) throws NotFoundException {
        Optional<Shipping> shippingOptional = shippingRepository.findByType(shippingType);
        if(!shippingOptional.isPresent()){
            throw new NotFoundException(shippingType+" not found.");
        }
        return shippingOptional.get();
    }

    public Shipping findById(Long id) throws NotFoundException {
        Optional<Shipping> shippingOptional = shippingRepository.findById(id);
        if(!shippingOptional.isPresent()){
            throw new NotFoundException("Shipping' s id "+id+" not found.");
        }
        return shippingOptional.get();
    }
}
