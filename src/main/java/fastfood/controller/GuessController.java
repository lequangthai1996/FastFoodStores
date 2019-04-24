package fastfood.controller;

import fastfood.config.TokenProvider;
import fastfood.domain.ErrorCustom;
import fastfood.domain.RegisterSalerAccountDTO;
import fastfood.domain.ResponseCommonAPI;
import fastfood.service.GuessService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/register")
public class GuessController {

    @Autowired
    private TokenProvider tokenProvider;

    @Autowired
    private GuessService guessService;

    @PostMapping(value = "/saler")
    public ResponseEntity<ResponseCommonAPI> registerAccountSaler(RegisterSalerAccountDTO registerSalerAccountDTO, @RequestParam("files") MultipartFile[] files) {
        ResponseCommonAPI res = new ResponseCommonAPI();

        List<ErrorCustom> listErrors = registerSalerAccountDTO.validateData();
        if(!CollectionUtils.isEmpty(listErrors)) {
            res.setSuccess(false);
            res.setError(listErrors);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(res);
        }

        try {
            res = guessService.registerSalerAccount(registerSalerAccountDTO, files);
            if(res.getSuccess()) {
                return ResponseEntity.ok(res);
            } else {
                return ResponseEntity.badRequest().body(res);
            }
        } catch (Exception e) {
            res.setSuccess(false);
            res.setMessage(e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(res);
        }
    }

}
