package ra.service.impl.order;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ra.exception.BadRequestException;
import ra.exception.NotFoundException;
import ra.model.dto.request.OrderRequest;
import ra.model.dto.response.OrderResponse;
import ra.model.entity.*;
import ra.repository.ICartRepository;
import ra.repository.IOrderDetailRepository;
import ra.repository.IOrderRepository;
import ra.repository.IProductRepository;
import ra.service.impl.cart.CartService;
import ra.service.impl.shipping.IShippingService;
import ra.service.mapper.OrderMapper;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class OrderService implements IOrderService {
    @Autowired
    private ICartRepository cartItemRepository;
    @Autowired
    private CartService cartItemService;
    @Autowired
    private IOrderRepository orderRepository;
    @Autowired
    private IOrderDetailRepository orderDetailRepository;
    @Autowired
    private IShippingService shippingService;
    @Autowired
    private OrderMapper orderMapper;
    @Autowired
    private IProductRepository productRepository;

    @Override
    public List<OrderResponse> findAll() {
        List<Orders> orders = orderRepository.findAll();
        return orders.stream()
                .map(order -> orderMapper.toResponse(order))
                .collect(Collectors.toList());
    }

    @Override
    public List<OrderResponse> findAllByUserId(Long id) {
        List<Orders> orders = orderRepository
                .findAllByUserId(id);
        return orders.stream()
                .map(orders1 -> orderMapper.toResponse(orders1))
                .collect(Collectors.toList());

    }


    @Override
    public OrderResponse findByUserId(Long userId) throws NotFoundException {
        Optional<Orders> orderOptional = orderRepository.findByUserId(userId);
        if (!orderOptional.isPresent()) {
            throw new NotFoundException("Order of user's id " + userId + " not found.");
        }
        return orderMapper.toResponse(orderOptional.get());
    }

    @Override
    public List<OrderResponse> findByStatus(String status) throws NotFoundException {
        OrderStatus orderStatus = null;
        switch (status) {
            case "PENDING":
                orderStatus = OrderStatus.PENDING;
                break;
            case "ACCEPTED":
                orderStatus = OrderStatus.ACCEPTED;
                break;
            case "DELIVERED":
                orderStatus = OrderStatus.DELIVERED;
                break;
            case "SHIPPING":
                orderStatus = OrderStatus.SHIPPING;
                break;
            case "CANCEL":
                orderStatus = OrderStatus.CANCEL;
                break;
            default:
        }

        return orderRepository.findAllByStatus(orderStatus).stream().map(o -> orderMapper.toResponse(o)).collect(Collectors.toList());
    }

    @Override
    public OrderResponse findByOrderDate(String orderDate) throws NotFoundException {
        Optional<Orders> orderOptional = orderRepository.findByOrderDate(orderDate);
        if (!orderOptional.isPresent()) {
            throw new NotFoundException("Order of date " + orderDate + " not found.");
        }
        return orderMapper.toResponse(orderOptional.get());
    }


    @Override
    public OrderResponse findById(Long id) throws NotFoundException {
        Optional<Orders> orderOptional = orderRepository.findById(id);
        if (!orderOptional.isPresent()) {
            throw new NotFoundException("Order's id " + id + " not found.");
        }
        return orderMapper.toResponse(orderOptional.get());
    }

    @Override
    public OrderResponse save(OrderRequest orderRequest) throws NotFoundException {
        return null;
    }

    @Override
    public OrderResponse update(OrderRequest orderRequest, Long id) throws NotFoundException {
        return null;
    }

    @Override
    public OrderResponse deleteById(Long id) throws NotFoundException {
        return null;
    }

    @Override
    public OrderResponse updateStatus(Long orderId, String statusCode) throws NotFoundException, BadRequestException {
        Optional<Orders> orderOptional = orderRepository.findById(orderId);
        if (!orderOptional.isPresent()) {
            throw new NotFoundException("Order's id " + orderId + " not found.");
        }
        Orders order = orderOptional.get();
        order.setId(orderId);
        switch (statusCode) {
            case "accepted":
                order.setStatus(OrderStatus.ACCEPTED);
                break;
            case "shipping":
                order.setStatus(OrderStatus.SHIPPING);
                break;
            case "delivered":
                order.setStatus(OrderStatus.DELIVERED);
                break;
            case "cancel":
                order.setStatus(OrderStatus.CANCEL);
                break;
            default:
                throw new BadRequestException("Status's value not true");
        }
        if (order.getStatus() == OrderStatus.CANCEL) {
            for (OrderDetail item : order.getItems()) {
                Products product = productRepository.findById(item.getProduct().getId()).get();
                product.setStock(product.getStock() + item.getQuantity());
                productRepository.save(product);
            }

        }
        return orderMapper.toResponse(orderRepository.save(order));
    }

    @Override
    public OrderResponse cancel(Long id) throws NotFoundException {
        Optional<Orders> orderOptional = orderRepository.findById(id);
        if (!orderOptional.isPresent()) {
            throw new NotFoundException("Order's id " + id + " not found.");
        }
        orderOptional.get().setActive(false);
        orderOptional.get().setStatus(OrderStatus.CANCEL);
        return orderMapper.toResponse(orderRepository.save(orderOptional.get()));
    }


}
