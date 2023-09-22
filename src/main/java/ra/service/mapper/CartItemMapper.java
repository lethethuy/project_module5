package ra.service.mapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ra.model.dto.request.CartRequest;
import ra.model.dto.response.CartResponse;
import ra.model.entity.CartItem;
import ra.repository.IProductRepository;
import ra.security.user_principle.UserDetailService;
import ra.service.IGenericMapper;
import java.text.NumberFormat;


@Component
public class CartItemMapper implements IGenericMapper<CartItem, CartRequest, CartResponse> {
    @Autowired
    private UserDetailService userDetailService;
    @Autowired
    private IProductRepository productRepository;

    @Override
    public CartItem toEntity(CartRequest cartRequest) {
        return CartItem.builder()
                .users(userDetailService.getCurrentUser())
                .products(productRepository.findById(cartRequest.getProductId()).get())
                .quantity(cartRequest.getQuantity())
                .build();
    }

    @Override
    public CartResponse toResponse(CartItem cartItem) {
        return CartResponse.builder()
                .id(cartItem.getId())
                .productName(cartItem.getProducts().getName())
                .productPrice(NumberFormat.getInstance().format(cartItem.getProducts().getPrice())+"₫")
                .quantity(cartItem.getQuantity())
                .amount(NumberFormat.getInstance().format(cartItem.getProducts().getPrice()*cartItem.getQuantity())+"₫")
                .build();
    }
}
