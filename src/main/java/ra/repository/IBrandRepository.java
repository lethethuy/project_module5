package ra.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ra.model.entity.Brand;
import ra.model.entity.Products;

public interface IBrandRepository extends JpaRepository<Brand,Long> {
}
