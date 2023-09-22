package ra.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ra.model.entity.Brand;
import ra.model.entity.CartItem;

import java.util.List;
import java.util.Optional;
@Repository
public interface ICartRepository  extends JpaRepository<CartItem,Long> {
    List<CartItem> findAllByUsersId(Long id);
    Optional<CartItem> findByProductsId(Long id);
    Optional<CartItem> findByUsersId(Long id);
    Optional<CartItem> findByUsersIdAndProductsId(Long userId,Long productId);

}
