package ra.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;;
import org.springframework.web.bind.annotation.*;
import ra.exception.BadRequestException;
import ra.exception.LoginException;
import ra.exception.RegisterException;
import ra.model.dto.request.ChangePasswordRequest;
import ra.model.dto.request.LoginRequestDto;
import ra.model.dto.request.RegisterRequestDto;
import ra.model.dto.response.JwtResponse;
import ra.model.entity.Users;
import ra.security.jwt.JwtProvider;
import ra.security.user_principle.UserDetailService;
import ra.security.user_principle.UserPrinciple;
import ra.service.impl.user.IUserService;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/user")
public class UserController {
    @Autowired
    private IUserService userService;
    @Autowired
    private UserDetailService userDetailService;

    @PutMapping("/change-password")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<String> changePassword(@Valid @RequestBody ChangePasswordRequest changePasswordRequest) throws BadRequestException {
        Users user = userDetailService.getCurrentUser();
        userService.changePassword(user.getId(), changePasswordRequest.getOldPass(), changePasswordRequest.getNewPass(), changePasswordRequest.getReNewPass());
        return new ResponseEntity<>("Change password successfully", HttpStatus.OK);
    }

    @PutMapping("/changeStatus/{id}")
    @PreAuthorize("hasRole('ADMIN')")

    public ResponseEntity<?> changeStatus(@PathVariable Long id) {
        Optional<Users> userOptional = Optional.ofNullable(userService.findById(id));
        if (!userOptional.isPresent()) {
            return new ResponseEntity<>("User's id not found", HttpStatus.NOT_FOUND);
        }
        if (id == 1) {
            return new ResponseEntity<>("You can't change status of admin!", HttpStatus.BAD_REQUEST);
        }
        userService.changeStatus(userOptional.get());
        return new ResponseEntity<>("Change status successfully.", HttpStatus.OK);
    }
}
