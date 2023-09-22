package ra.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import ra.exception.BadRequestException;
import ra.exception.NotFoundException;
import ra.model.dto.request.OrderRequest;
import ra.model.dto.response.OrderResponse;
import ra.model.entity.OrderStatus;
import ra.service.impl.order.IOrderService;

import javax.validation.Valid;
import java.util.List;
@RestController
@RequestMapping("/api/v1/orders")
public class OrderController {
    @Autowired
    private IOrderService orderService;
    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<OrderResponse>> findALl(){
        return new ResponseEntity<>(orderService.findAll(), HttpStatus.OK);
    }
    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<OrderResponse> findById(@PathVariable Long id ) throws NotFoundException {
        return  new ResponseEntity<>(orderService.findById(id),HttpStatus.OK);
    }

    @GetMapping("/filter/{status}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<OrderResponse>> findByStatus(@PathVariable String status ) throws NotFoundException {
        return  new ResponseEntity<>(orderService.findByStatus(status),HttpStatus.OK);
    }

    @GetMapping("/user/{id}")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<OrderResponse> findByUserId(@PathVariable Long id) throws NotFoundException {
        return new ResponseEntity<>(orderService.findByUserId(id),HttpStatus.OK);
    }


    @PostMapping
    public ResponseEntity<OrderResponse> create(@RequestBody @Valid OrderRequest orderRequestDTO) throws NotFoundException {
        return new ResponseEntity<>(orderService.save(orderRequestDTO),HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<OrderResponse> deleteById(@PathVariable Long id ) throws NotFoundException {
        return  new ResponseEntity<>(orderService.deleteById(id),HttpStatus.OK);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<OrderResponse> updateStatus(
            @Valid
            @PathVariable Long id,
            @RequestParam (name = "status") String statusCode) throws NotFoundException, BadRequestException, BadRequestException {
        return new ResponseEntity<>(orderService.updateStatus(id,statusCode), HttpStatus.OK);
    }
}
