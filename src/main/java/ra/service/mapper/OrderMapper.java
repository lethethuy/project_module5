package ra.service.mapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ra.exception.NotFoundException;
import ra.model.dto.request.OrderRequest;
import ra.model.dto.response.OrderDetailResponse;
import ra.model.dto.response.OrderResponse;
import ra.model.entity.*;
import ra.repository.*;
import ra.security.user_principle.UserDetailService;
import ra.service.IGenericMapper;
import ra.service.impl.shipping.ShippingService;

import java.text.NumberFormat;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Component
public class OrderMapper implements IGenericMapper<Orders, OrderRequest, OrderResponse> {
    @Autowired
    private UserDetailService userDetailService;
    @Autowired
    private IOrderRepository orderRepository;
    @Autowired
    private IUserRepository userRepository;
    @Autowired
    private IPaymentRepository paymentRepository;
    @Autowired
    private IShippingRepository shippingRepository;
    @Autowired
    private IOrderDetailRepository orderDetailRepository;
    @Autowired
    private ShippingService shippingService;

    @Override
    public Orders toEntity(OrderRequest orderRequest) throws NotFoundException {
        Orders order = new Orders();
        order.setUser(userDetailService.getCurrentUser());
        order.setReceiver(orderRequest.getReceiver());
        order.setAddress(orderRequest.getAddress());
        order.setTel(orderRequest.getTel());
        order.setNote(orderRequest.getNote());
        order.setOrderDate(LocalDateTime.now());
        order.setPayment(paymentRepository.findById(orderRequest.getPaymentId()).get());
        order.setShipping(shippingRepository.findById(orderRequest.getShippingId()).get());
        order.setStatus(OrderStatus.PENDING);
        order.setActive(true);
        double total = 0;
        List<CartItem> cartItems = order.getUser().getCartItems();
        if (cartItems.isEmpty()) {
            throw new NotFoundException("Empty cart");
        }
        for (CartItem cartItem : cartItems) {
            OrderDetail orderDetail = new OrderDetail();
            orderDetail.setProduct(cartItem.getProducts());
            orderDetail.setQuantity(cartItem.getQuantity());
            orderDetail.setPrice(orderDetail.getProduct().getPrice());
            orderDetail.setAmount(orderDetail.getProduct().getPrice() * orderDetail.getQuantity());
            orderDetail.setOrder(order);
            order.getItems().add(orderDetail);
            total += orderDetail.getAmount();
        }
        order.setTotal(total);
        return order;
    }

    @Override
    public OrderResponse toResponse(Orders order) {
        OrderResponse orderResponse = new OrderResponse();
        orderResponse.setId(order.getId());
        orderResponse.setOwner(order.getUser().getFullName());
        orderResponse.setReceiver(order.getReceiver());
        orderResponse.setAddress(order.getAddress());
        orderResponse.setTel(order.getTel());
        orderResponse.setNote(order.getNote());
        orderResponse.setTotal(NumberFormat.getInstance().format(order.getTotal()) + "₫");
        orderResponse.setOrderDate(order.getOrderDate().toString());
        orderResponse.setStatus(order.getStatus().name());
        orderResponse.setPayment(order.getPayment().getType().toString());
        orderResponse.setShipping(order.getShipping().getType().toString());
        List<OrderDetailResponse> orderDetailResponses = new ArrayList<>();
        for (OrderDetail item : order.getItems()) {
            OrderDetailResponse orderDetailResponse = new OrderDetailResponse();
            orderDetailResponse.setProductId(item.getProduct().getId());
            orderDetailResponse.setProductName(item.getProduct().getName());
            orderDetailResponse.setQuantity(item.getQuantity());
            orderDetailResponse.setPrice(NumberFormat.getInstance().format(item.getProduct().getPrice()) + "₫");
            orderDetailResponse.setAmount(NumberFormat.getInstance().format(item.getAmount()) + "₫");
            orderDetailResponses.add(orderDetailResponse);
        }
        orderResponse.setItems(orderDetailResponses);
        return orderResponse;
    }
}