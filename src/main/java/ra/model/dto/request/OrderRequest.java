package ra.model.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class OrderRequest {
    @NotEmpty(message = "Receiver must be not empty")
    private String receiver;
    @NotEmpty(message = "Address must be not empty")
    private String address;
    @NotEmpty(message = "Tel must be not empty")
    private String tel;
    @NotEmpty(message = "Note must be not empty")
    private String note;
    @NotNull(message = "Payment ID must be not empty")
    private Long paymentId;
    @NotNull(message = "Payment ID must be not empty")
    private Long shippingId;

}
