package ra.model.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductsRequest {
    @NotEmpty(message = "Name ID must be not empty")
    private String name;
    @NotNull
    private double price;
    @NotEmpty(message = "Description must be not empty")
    private String description;
    @NotNull
    private int stock;
    @NotNull(message = "Category must be not empty")
    private Long category;
    @NotNull(message = "Brand must be not empty")
    private Long brand;
    private boolean status;
    Pageable firstPageWithTwoElements = PageRequest.of(0, 2);
}
