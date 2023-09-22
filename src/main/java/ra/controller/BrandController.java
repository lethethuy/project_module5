package ra.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import ra.exception.NotFoundException;
import ra.model.dto.request.BrandRequest;

import ra.model.dto.response.BrandResponse;

import ra.service.impl.brand.IBrandService;


import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/v1/brand")
@PreAuthorize("hasAnyRole('ADMIN')")
public class BrandController {
    @Autowired
    private IBrandService brandService;

    @GetMapping
    public ResponseEntity<List<BrandResponse>> findALl() {
        return new ResponseEntity<>(brandService.findAll(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<BrandResponse> findById(@PathVariable Long id) throws NotFoundException {
        return new ResponseEntity<>(brandService.findById(id), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<BrandResponse> create(@RequestBody @Valid BrandRequest brandRequest) throws NotFoundException {
        return new ResponseEntity<>(brandService.save(brandRequest), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<BrandResponse> update(@RequestBody @Valid BrandRequest brandRequest, @PathVariable Long id) throws NotFoundException {
        return new ResponseEntity<>(brandService.update(brandRequest, id), HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<BrandResponse> deleteById(@PathVariable Long id) throws NotFoundException {
        return new ResponseEntity<>(brandService.deleteById(id), HttpStatus.OK);
    }

}