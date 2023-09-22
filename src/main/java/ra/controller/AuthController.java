package ra.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.web.bind.annotation.*;
import ra.exception.LoginException;
import ra.exception.RegisterException;
import ra.model.dto.request.LoginRequestDto;
import ra.model.dto.request.RegisterRequestDto;
import ra.model.dto.response.JwtResponse;
import ra.security.jwt.JwtProvider;
import ra.security.user_principle.UserPrinciple;
import ra.service.impl.user.IUserService;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;


@RestController
@RequestMapping("/api/v1/")
@CrossOrigin("*")
public class AuthController {
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private JwtProvider jwtProvider;
    @Autowired
    private IUserService userService;

    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody @Valid RegisterRequestDto registerRequestDto) throws RegisterException {
        userService.save(registerRequestDto);
        return new ResponseEntity<>("SUSSES", HttpStatus.CREATED);
    }

    @PostMapping("/login")
    public ResponseEntity<JwtResponse> login(@RequestBody @Valid LoginRequestDto loginRequestDto) throws LoginException {
        Authentication authentication = null;
        try {
            System.out.println(loginRequestDto.getUsername());
            System.out.println(loginRequestDto.getPassword());
            authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                    loginRequestDto.getUsername(),loginRequestDto.getPassword()));
        }catch (AuthenticationException e) {
            e.printStackTrace();
            throw new LoginException("Username or password is incorrect!");
        }

        // xác thực thành công người dùng và lưu thông tin đăng nhập vào securitityContext
        UserPrinciple userPrinciple = (UserPrinciple) authentication.getPrincipal();

        if (!userPrinciple.isStatus()){
            throw new LoginException("Your account has been locked");
        }
        String token = jwtProvider.generateToken(userPrinciple);

        List<String> roles = userPrinciple.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority).collect(Collectors.toList());
        return ResponseEntity.ok(JwtResponse.builder().token(token)
                .fullName(userPrinciple.getFullName())
                .username(userPrinciple.getUsername())
                .roles(roles)
                .type("Bearer")
                .status(userPrinciple.isStatus()).build());
    }

}
