package ra.service.impl.cart;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Service;
import ra.exception.NotFoundException;
import ra.model.dto.request.CartRequest;
import ra.model.dto.request.CategoryRequest;
import ra.model.dto.request.OrderRequest;
import ra.model.dto.response.CartResponse;
import ra.model.dto.response.CategoryResponse;
import ra.model.dto.response.OrderResponse;
import ra.model.entity.*;
import ra.repository.ICartRepository;
import ra.repository.IOrderRepository;
import ra.repository.IProductRepository;
import ra.security.user_principle.UserDetailService;
import ra.service.impl.category.ICategoryService;
import ra.service.mapper.CartItemMapper;
import ra.service.mapper.OrderMapper;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CartService implements ICartService {
    @Autowired
    private UserDetailService userDetailService;
    @Autowired
    private IProductRepository productRepository;

    @Autowired
    private ICartRepository cartRepository;
    @Autowired
    private CartItemMapper cartItemMapper;
    @Autowired
    private OrderMapper orderMapper;
    @Autowired
    private IOrderRepository orderRepository;


    @Override
    public List<CartResponse> findAll() {
        List<CartItem> cartItems = cartRepository.findAll();
        return cartItems.stream()
                .map(cartItem -> cartItemMapper.toResponse(cartItem))
                .collect(Collectors.toList());
    }

    @Override
    public CartResponse findById(Long id) throws NotFoundException {
        Optional<CartItem> cartItemOptional = cartRepository.findById(id);
        if(!cartItemOptional.isPresent()){
            throw new NotFoundException("Cart item's id "+id+" not found.");

        }
        return cartItemMapper.toResponse(cartItemOptional.get());


    }

    @Override
    public CartResponse save(CartRequest cartRequest) throws NotFoundException {
        Users currentUser = userDetailService.getCurrentUser();
        Long productId = cartRequest.getProductId();

        Optional<CartItem> existingCartItemOptional = cartRepository.findByUsersIdAndProductsId(currentUser.getId(), productId);

        if (existingCartItemOptional.isPresent()) {
            // Nếu đã tồn tại một mục CartItem với cùng product
            CartItem existingCartItem = existingCartItemOptional.get();
            int newQuantity = existingCartItem.getQuantity() + cartRequest.getQuantity();
            existingCartItem.setQuantity(newQuantity);
            return cartItemMapper.toResponse(cartRepository.save(existingCartItem));
        } else {
            // Nếu chưa tồn tại mục CartItem với cùng product, thêm mới
            Optional<Products> productOptional = productRepository.findById(productId);
            if (!productOptional.isPresent()) {
                throw new NotFoundException("Product's id " + productId + " not found.");
            } else {
                CartItem cartItem = cartItemMapper.toEntity(cartRequest);
                cartItem.setUsers(currentUser);
                return cartItemMapper.toResponse(cartRepository.save(cartItem));
            }
        }

    }

    @Override
    public CartResponse update(CartRequest cartRequest, Long id) throws NotFoundException {
        Users currentUser = userDetailService.getCurrentUser();
        Optional<CartItem> cartItemOptional = cartRepository.findById(id);
        if (!cartItemOptional.isPresent()) {
            throw new NotFoundException("Cart item's id "+id+" not found.");
        }

        if(cartItemOptional.get().getUsers().getId().equals(currentUser.getId())) {
            cartRequest.setProductId(cartItemOptional.get().getProducts().getId());
            CartItem cartItem = cartItemMapper.toEntity(cartRequest);
            cartItem.setId(id);
            return cartItemMapper.toResponse(cartRepository.save(cartItem));
        } else {
            throw new NotFoundException("Cart item's id "+id+" is not yours.");
        }
    }

    @Override
    public CartResponse deleteById(Long id) throws NotFoundException {
        Users currentUser = userDetailService.getCurrentUser();
        Optional<CartItem> cartItemOptional = cartRepository.findById(id);
        if(!cartItemOptional.isPresent()){
            throw new NotFoundException("Cart item's id "+id+" not found.");
        }
        if(cartItemOptional.get().getUsers().getId().equals(currentUser.getId())) {
            cartRepository.deleteById(id);
            return cartItemMapper.toResponse(cartItemOptional.get());
        } else {
            throw new NotFoundException("Cart item's id "+id+" is not yours.");
        }
    }
    @Override
    public List<CartResponse> findAllByUserId(Long id) {
        List<CartItem> cartItems = cartRepository.findAllByUsersId(id);
        return cartItems.stream()
                .map(cartItem -> cartItemMapper.toResponse(cartItem))
                .collect(Collectors.toList());
    }

    public void clearCart(){
        List<CartItem> cartItems = cartRepository.findAllByUsersId(userDetailService.getCurrentUser().getId());
        cartItems.clear();

    }
    public OrderResponse checkout(OrderRequest orderRequest) throws NotFoundException {
        List<CartItem> cartItem = cartRepository.findAllByUsersId(userDetailService.getCurrentUser().getId());
        Orders order = orderMapper.toEntity(orderRequest);
        orderRepository.save(order);
        cartRepository.deleteAll(cartItem);
        return orderMapper.toResponse(order);
    }


}

