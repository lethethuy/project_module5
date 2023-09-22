package ra.model.dto.response;

import jdk.tools.jlink.internal.plugins.StripNativeCommandsPlugin;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CartResponse {
private Long id;
private int quantity;
private String productName;
private String productPrice;
private String amount;
}
