package ra.model.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.util.List;
@Data
@NoArgsConstructor
@AllArgsConstructor
public class RegisterRequestDto {
    @NotBlank(message = "Not Blank")
    private String fullName;
    @Size(min=8,max=100,message = "Username must have between 8 and 100 characters")
    private String username;
    @Pattern(regexp ="^[A-Za-z0-9+_.-]+@(.+)$",message = "Email invalidate")
    private String email;
    @Pattern(regexp ="^(?=.*[A-Za-z])(?=.*\\d).{8,}$",message = "Must be greater than 8 characters with 1 uppercase letter, 1 lowercase letter and 1 number")
    private String password;
    private List<String> roles;
}
