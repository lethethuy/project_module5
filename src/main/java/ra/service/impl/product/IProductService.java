package ra.service.impl.product;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import ra.model.dto.request.ProductsRequest;
import ra.model.dto.response.ProductResponse;
import ra.model.entity.Products;
import ra.service.IGenericService;

import java.util.List;

public interface IProductService extends IGenericService<ProductsRequest, ProductResponse> {
    Page<Products> findAll2(String name, int page, int size, String sort, String by);

    List<ProductResponse> getProducts(String name, Pageable pageable);
}
