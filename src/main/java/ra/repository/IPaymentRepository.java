package ra.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ra.model.entity.Payment;
import ra.model.entity.PaymentType;

import java.util.Optional;

public interface IPaymentRepository extends JpaRepository<Payment,Long> {
    Optional<Payment> findByType(PaymentType paymentType);
}
