package fastfood.service;

import fastfood.domain.RegisterSalerAccountDTO;
import fastfood.domain.RegisterUserDTO;
import fastfood.domain.ResponseCommonAPI;
import org.springframework.web.multipart.MultipartFile;

public interface GuessService {
    ResponseCommonAPI registerSalerAccount(RegisterSalerAccountDTO registerSalerAccountDTO, MultipartFile[] files) throws Exception;

    ResponseCommonAPI registerUserAccount(RegisterUserDTO registerUserDTO) throws Exception;
}
