package ra.model.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OrderResponse {
    private Long id;
    private String owner;
    private String receiver;
    private String address;
    private String tel;
    private String note;
    private String total;
    private String orderDate;
    private String shippingDate;
    private String status;
    private String payment;
    private String shipping;
    private List<OrderDetailResponse> items;
}
