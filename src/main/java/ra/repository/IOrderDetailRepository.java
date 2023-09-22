package ra.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ra.model.entity.OrderDetail;

public interface IOrderDetailRepository extends JpaRepository<OrderDetail,Long> {
}
