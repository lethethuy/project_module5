package ra.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import ra.exception.BadRequestException;
import ra.exception.NotFoundException;
import ra.model.dto.request.CartRequest;
import ra.model.dto.request.OrderRequest;
import ra.model.dto.response.CartResponse;
import ra.model.entity.Users;
import ra.security.user_principle.UserDetailService;
import ra.service.impl.cart.ICartService;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/v1/cart")
@PreAuthorize("hasAnyRole('USER')")
public class CartItemController {
    @Autowired
    private ICartService cartItemService;
    @Autowired
    private UserDetailService userDetailService;
    @PostMapping("/checkout")
    public ResponseEntity<String> checkout(
            @Valid
            @RequestBody OrderRequest orderRequest) throws NotFoundException {
        cartItemService.checkout(orderRequest);
        return new ResponseEntity<>("Checkout successfully",HttpStatus.CREATED);

    }

    @PostMapping("")

    public ResponseEntity<CartResponse> add(
            @Valid
            @RequestBody CartRequest cartItemRequest) throws NotFoundException, BadRequestException {

        return new ResponseEntity<>(cartItemService.save(cartItemRequest), HttpStatus.CREATED);

    }

    @GetMapping("")
    public ResponseEntity<List<CartResponse>> getAll() {
        Users user = userDetailService.getCurrentUser();
        return new ResponseEntity<>(cartItemService.findAllByUserId(user.getId()), HttpStatus.OK);
    }


    @GetMapping("/{id}")
    public ResponseEntity<CartResponse> getOne(@PathVariable Long id) throws NotFoundException {
        return new ResponseEntity<>(cartItemService.findById(id), HttpStatus.OK);

    }

    @PutMapping("/{id}")
    public ResponseEntity<CartResponse> edit(
            @Valid
            @RequestBody CartRequest cartItemRequest,
            @PathVariable Long id) throws NotFoundException {
        return new ResponseEntity<>(cartItemService.update(cartItemRequest, id), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<CartResponse> delete(@PathVariable Long id) throws NotFoundException {
        return new ResponseEntity<>(cartItemService.deleteById(id), HttpStatus.OK);
    }
}