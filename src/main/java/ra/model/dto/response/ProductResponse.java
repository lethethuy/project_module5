package ra.model.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProductResponse {
 private Long id;
 private String name;
 private double price;
 private String description;
 private int stock;
 private String category;
 private String brand;
 private boolean status;
}
