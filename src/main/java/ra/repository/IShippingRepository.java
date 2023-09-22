package ra.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ra.model.entity.Shipping;
import ra.model.entity.ShippingType;

import java.util.Optional;

public interface IShippingRepository extends JpaRepository<Shipping,Long> {
        Optional<Shipping> findByType(ShippingType shippingType);
}
