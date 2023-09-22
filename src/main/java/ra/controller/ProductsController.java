package ra.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import ra.exception.NotFoundException;
import ra.model.dto.request.ProductsRequest;
import ra.model.dto.response.ProductResponse;
import ra.service.impl.product.IProductService;


import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/v1/products")
public class ProductsController {
    @Autowired
    private IProductService productService;
//    @GetMapping
//    public ResponseEntity<List<ProductResponse>> findALl(){
//        return new ResponseEntity<>(productService.findAll(), HttpStatus.OK);
//    }
    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ProductResponse> findById(@PathVariable Long id ) throws NotFoundException {
        return  new ResponseEntity<>(productService.findById(id),HttpStatus.OK);
    }
    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ProductResponse> create(@RequestBody @Valid ProductsRequest productsRequest) throws NotFoundException {
        return new ResponseEntity<>(productService.save(productsRequest),HttpStatus.CREATED);
    }
    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ProductResponse> update(@RequestBody @Valid ProductsRequest productsRequest,@PathVariable Long id) throws NotFoundException {
        return new ResponseEntity<>(productService.update(productsRequest,id),HttpStatus.CREATED);
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<ProductResponse> deleteById(@PathVariable Long id ) throws NotFoundException {
        return  new ResponseEntity<>(productService.deleteById(id),HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<ProductResponse>> getPaginatedProducts(
            @RequestParam(defaultValue = "") String name,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "2") int size,
            @RequestParam(defaultValue = "name") String sort,
            @RequestParam(defaultValue = "asc") String by) {


        Pageable pageable = PageRequest.of(page, size, Sort.Direction.fromString(by), sort);
        List<ProductResponse> products = (List<ProductResponse>) productService.getProducts(name, pageable);
        return new ResponseEntity<>(products, HttpStatus.OK);

    }

    // phan trang and search
//    @GetMapping("/search")
//    public ResponseEntity<?> list(@RequestParam(defaultValue = "") String name, @RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "2") int size, @RequestParam(defaultValue = "name") String sort, @RequestParam(defaultValue = "asc") String by){
//        return new ResponseEntity<>(productService.findAll(name,page,size,sort,by),HttpStatus.OK);
//    }
}
