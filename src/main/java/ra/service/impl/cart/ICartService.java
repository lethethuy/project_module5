package ra.service.impl.cart;

import ra.exception.NotFoundException;
import ra.model.dto.request.CartRequest;
import ra.model.dto.request.OrderRequest;
import ra.model.dto.response.CartResponse;
import ra.model.dto.response.OrderResponse;
import ra.service.IGenericService;

import java.util.List;

public interface ICartService extends IGenericService<CartRequest, CartResponse> {
    List<CartResponse> findAllByUserId(Long id);
    OrderResponse checkout(OrderRequest orderRequest) throws NotFoundException;

}
