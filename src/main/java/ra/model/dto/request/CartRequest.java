package ra.model.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.userdetails.User;
import ra.model.entity.CartItem;
import ra.model.entity.Users;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Data // = set get
@NoArgsConstructor
@AllArgsConstructor
public class CartRequest {
    @Min(value = 0, message = "Import price must be greater than 0")
    private int quantity;
    @NotNull(message = "Must be not empty")
    private Long productId;
}
