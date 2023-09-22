package ra.service.impl.order;

import ra.exception.BadRequestException;
import ra.exception.NotFoundException;
import ra.model.dto.request.OrderRequest;
import ra.model.dto.response.OrderResponse;
import ra.model.entity.OrderStatus;
import ra.service.IGenericService;

import java.util.List;

public interface IOrderService extends IGenericService<OrderRequest, OrderResponse> {
    List<OrderResponse> findAllByUserId(Long id);

    OrderResponse findByUserId(Long userId) throws NotFoundException;
    List<OrderResponse> findByStatus(String status) throws NotFoundException;
    OrderResponse findByOrderDate(String orderDate) throws NotFoundException;
    OrderResponse updateStatus(Long orderId, String statusCode) throws NotFoundException, BadRequestException;

    OrderResponse cancel(Long id) throws NotFoundException;}
