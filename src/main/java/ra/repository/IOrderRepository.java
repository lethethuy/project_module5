package ra.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ra.model.entity.OrderStatus;
import ra.model.entity.Orders;

import java.util.List;
import java.util.Optional;

public interface IOrderRepository extends JpaRepository<Orders,Long> {
    Optional<Orders> findByUserId(Long userId);
    List<Orders> findAllByStatus(OrderStatus orderStatus);
    Optional<Orders> findByOrderDate(String orderDate);
    List<Orders> findAllByUserId(Long userId);
}
