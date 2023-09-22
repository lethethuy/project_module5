package ra.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import ra.model.entity.Products;

import java.util.List;

public interface IProductRepository extends JpaRepository<Products,Long> {
    Page<Products> findAllByNameContainingIgnoreCase(String name, Pageable pageable);}
