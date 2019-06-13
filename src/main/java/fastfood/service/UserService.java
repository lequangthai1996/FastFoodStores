package fastfood.service;

import fastfood.domain.*;
import fastfood.entity.UserEntity;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface UserService {
    UserResponse getUserByUserName(String username);

    UserResponse addUser(LoginDTO loginDTO);

    UserResponse addSale(LoginDTO loginDTO);

    UserResponse addADMIN(LoginDTO loginDTO);

    List<UserEntity> getAllUser();

    UserResponse getUserDetail(Long userID);

    UserResponse updateUserInfo(UserInfoDTO userInfoDTO, MultipartFile avatar) throws Exception;
}
