package fastfood.service;

import fastfood.domain.LoginDTO;
import fastfood.domain.UserResponse;
import sun.rmi.runtime.Log;

public interface UserService {
    UserResponse getUserByUserName(String username);

    UserResponse addUser(LoginDTO loginDTO);

    UserResponse addSale(LoginDTO loginDTO);

    UserResponse addADMIN(LoginDTO loginDTO);
}
