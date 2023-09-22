package ra.service.mapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import ra.model.dto.request.ProductsRequest;
import ra.model.dto.response.ProductResponse;
import ra.model.entity.Brand;
import ra.model.entity.Category;
import ra.model.entity.Products;
import ra.repository.IBrandRepository;
import ra.repository.ICategoryRepository;
import ra.service.IGenericMapper;
import java.util.Optional;

@Component
public class ProductMapper implements IGenericMapper<Products, ProductsRequest, ProductResponse> {
    @Autowired
    private ICategoryRepository categoryRepository;
    @Autowired
    private IBrandRepository brandRepository;

    @Override
    public Products toEntity(ProductsRequest productsRequest) {
        // Lấy danh sách danh mục từ danh sách ID
        Optional<Category> categories = categoryRepository.findById(productsRequest.getCategory());
        Optional<Brand> brand = brandRepository.findById(productsRequest.getCategory());

        // Xây dựng đối tượng Products bằng cách sử dụng thông tin từ ProductRequest và các đối tượng đã lấy
        Products product = Products.builder()
                .name(productsRequest.getName())
                .price(productsRequest.getPrice())
                .description(productsRequest.getDescription())
                .category(categories.get())
                .brand(brand.get())
                .stock(productsRequest.getStock())
                .status(true)
                .build();
        return product;
    }

    @Override
    public ProductResponse toResponse(Products products) {
        Optional<Category> c = categoryRepository.findById(products.getCategory().getId());
        Optional<Brand> b = brandRepository.findById(products.getBrand().getId());
        return ProductResponse.builder()
                .id(products.getId())
                .name(products.getName())
                .price(products.getPrice())
                .description(products.getDescription())
                .category(c.get().getName())
                .brand(b.get().getName())
                .stock(products.getStock())
                .status(products.isStatus())
                .build();
    }
}
