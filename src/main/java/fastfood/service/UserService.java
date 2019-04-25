package fastfood.service;

import fastfood.domain.LoginDTO;
import fastfood.domain.UserResponse;
import fastfood.entity.UserEntity;

import java.util.List;

public interface UserService {
    UserResponse getUserByUserName(String username);

    UserResponse addUser(LoginDTO loginDTO);

    UserResponse addSale(LoginDTO loginDTO);

    UserResponse addADMIN(LoginDTO loginDTO);

    List<UserEntity> getAllUser();
}
