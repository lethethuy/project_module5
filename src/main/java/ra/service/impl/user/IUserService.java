package ra.service.impl.user;

import ra.exception.BadRequestException;
import ra.exception.RegisterException;
import ra.model.dto.request.RegisterRequestDto;
import ra.model.entity.Users;

import java.util.List;
import java.util.Optional;

public interface IUserService {
    List<Users> findAll();
    Users findById(Long id);
    Users save(Users users);
    Users update(Long id);
    Users delete(Long id);
    Optional<Users> findByUserName(String username);
    Users save(RegisterRequestDto user) throws RegisterException;

    void changePassword(Long userId, String oldPass, String rePassword, String newPass) throws BadRequestException;
    void changeStatus(Users user);

}
