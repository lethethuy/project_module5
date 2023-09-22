package ra.service.impl.product;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import ra.model.dto.request.ProductsRequest;
import ra.model.dto.response.ProductResponse;
import ra.model.entity.Products;
import ra.repository.IProductRepository;
import ra.service.mapper.ProductMapper;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@Service
public class ProductService implements IProductService {
    @Autowired
    private IProductRepository productRepository;
    @Autowired
    private ProductMapper productMapper;

    @Override
    public List<ProductResponse> findAll() {
        return productRepository.findAll().stream().map(p -> productMapper.toResponse(p)).collect(Collectors.toList());
    }


    @Override
    public ProductResponse findById(Long id) {
        Optional<Products> p = productRepository.findById(id);
        if (p.isPresent()) {
            return productMapper.toResponse(p.get());
        }
        return null;
    }

    @Override
    public ProductResponse save(ProductsRequest productsRequest) {
        Products products = productMapper.toEntity(productsRequest);
        return productMapper.toResponse(productRepository.save(products));
    }

    @Override
    public ProductResponse update(ProductsRequest productsRequest, Long id) {
        Products p = productMapper.toEntity(productsRequest);
        p.setId(id);
        return productMapper.toResponse(productRepository.save(p));
    }

    @Override
    public ProductResponse deleteById(Long id) {
        Optional<Products> p = productRepository.findById(id);
        if (p.isPresent()) {
            productRepository.deleteById(id);
            return productMapper.toResponse(p.get());
        }
        return null;
    }

    @Override
    public Page<Products> findAll2(String name, int page, int size, String sort, String by) {
        return null;
    }

    @Override
    public List<ProductResponse> getProducts(String name, Pageable pageable) {
        Page<Products> products;
        if (name.isEmpty()) {
            products = productRepository.findAll(pageable);
        } else {
            products = productRepository.findAllByNameContainingIgnoreCase(name, pageable);
        }

        return products.map(productMapper::toResponse).getContent();
    }

//    @Override
//    public Page<Products> findAll(String name, int page, int size, String sort, String by) {
////        pageable = PageRequest.of(pageable.getPageNumber(),3);
//        Sort s ;
//        if(by.equals("desc")){
//            s = Sort.by(sort).descending();
//        }else {
//            s =Sort.by(sort).ascending();
//        }
//        return productRepository.findAllByNameContaining(name,PageRequest.of(page,size,s));
//    }
}
