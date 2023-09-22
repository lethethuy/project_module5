package ra.model.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;

@Data // = set get
@NoArgsConstructor
@AllArgsConstructor
public class BrandRequest {
    @NotEmpty(message = "Must be not empty")
    private String name;
}
