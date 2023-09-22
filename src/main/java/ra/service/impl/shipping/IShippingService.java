package ra.service.impl.shipping;

import ra.exception.NotFoundException;
import ra.model.entity.Shipping;
import ra.model.entity.ShippingType;

public interface IShippingService {
    Shipping findByType(ShippingType shippingType) throws NotFoundException;
}
