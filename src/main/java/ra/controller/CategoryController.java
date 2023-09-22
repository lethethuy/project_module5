package ra.controller;


import jdk.jshell.spi.ExecutionControl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import ra.exception.NotFoundException;
import ra.model.dto.request.CategoryRequest;
import ra.model.dto.response.CategoryResponse;
import ra.service.impl.category.ICategoryService;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/v1/categories")
@PreAuthorize("hasAnyRole('ADMIN')")
public class CategoryController {
    @Autowired
    private ICategoryService categoryService;
    @GetMapping
    public ResponseEntity<List<CategoryResponse>> findALl(){
        return new ResponseEntity<>(categoryService.findAll(), HttpStatus.OK);
    }
    @GetMapping("/{id}")
    public ResponseEntity<CategoryResponse> findById(@PathVariable Long id ) throws NotFoundException {
        return  new ResponseEntity<>(categoryService.findById(id),HttpStatus.OK);
    }
    @PostMapping
    public ResponseEntity<CategoryResponse> create(@RequestBody @Valid CategoryRequest categoryRequest) throws NotFoundException {
        return new ResponseEntity<>(categoryService.save(categoryRequest),HttpStatus.CREATED);
    }
    @PutMapping("/{id}")
    public ResponseEntity<CategoryResponse> update(@RequestBody @Valid CategoryRequest categoryRequest,@PathVariable Long id) throws NotFoundException {
        return new ResponseEntity<>(categoryService.update(categoryRequest,id),HttpStatus.CREATED);
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<CategoryResponse> deleteById(@PathVariable Long id ) throws NotFoundException {
        return  new ResponseEntity<>(categoryService.deleteById(id),HttpStatus.OK);
    }

}