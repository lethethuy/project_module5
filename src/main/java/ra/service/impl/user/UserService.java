package ra.service.impl.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ra.exception.BadRequestException;
import ra.exception.RegisterException;
import ra.model.dto.request.RegisterRequestDto;
import ra.model.entity.Role;
import ra.model.entity.RoleName;
import ra.model.entity.Users;
import ra.repository.IUserRepository;
import ra.service.impl.role.IRoleService;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class UserService implements IUserService {
    @Autowired
    private IUserRepository userRepository;
    @Autowired
    private IRoleService roleService;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public List<Users> findAll() {
        return userRepository.findAll();
    }

    @Override
    public Users findById(Long id) {
        Optional<Users> usersOptional = userRepository.findById(id);
        // Neu dung Users thi co the tra ve null
        // Neu dung Optional thi khong tra lai loi NullPointException
        if (usersOptional.isPresent()) {
            return usersOptional.get();
        }
        return null;
    }


    @Override
    public Users save(Users users) {
        return null;
    }

    @Override
    public Users update(Long id) {
        return null;
    }

    @Override
    public Users delete(Long id) {
        return null;
    }

    @Override
    public Optional<Users> findByUserName(String username) {
        return userRepository.findByUsername(username);
    }

    @Override
    public Users save(RegisterRequestDto user) throws RegisterException {
        if (userRepository.existsByUsername(user.getUsername())) {
            throw new RegisterException("User is exits");
        }
        if (userRepository.existsByEmail(user.getEmail())) {
            throw new RegisterException("Email is exits");
        }
        Set<Role> roles = new HashSet<>();
        if (user.getRoles() == null || user.getRoles().isEmpty()) {
            roles.add(roleService.findByRoleName(RoleName.ROLE_USER));
        } else {
            user.getRoles().stream().forEach(
                    role -> {
                        switch (role) {
                            case "admin":
                                roles.add(roleService.findByRoleName(RoleName.ROLE_ADMIN));
                            case "seller":
                                roles.add(roleService.findByRoleName(RoleName.ROLE_SELLER));
                            case "user":
                                roles.add(roleService.findByRoleName(RoleName.ROLE_USER));
                        }
                    }
            );
        }
        Users users = Users.builder()
                .fullName(user.getFullName())
                .username(user.getUsername())
                .password(passwordEncoder.encode(user.getPassword()))
                .email(user.getEmail())
                .roles(roles).
                status(true).build();
        return userRepository.save(users);
    }

    @Override
    public void changePassword(Long userId, String oldPass, String rePassword, String newPass) throws BadRequestException {
        Users user = userRepository.findById(userId).get();
        String userPassword = user.getPassword();
        System.out.println(userPassword);

        if (!(passwordEncoder.matches(oldPass, userPassword))) {
            throw new BadRequestException("Old password is incorrect");
        } else if (newPass.equals(oldPass)) {
            throw new BadRequestException("New password is the same old password.");
        } else if (!newPass.equals(rePassword)) {
            throw new BadRequestException("Re-password is not the same new password");
        }
        user.setPassword(passwordEncoder.encode(newPass));

        userRepository.save(user);
    }

    @Override
    public void changeStatus(Users user) {
        user.setStatus(!user.isStatus());
        userRepository.save(user);
    }
}
