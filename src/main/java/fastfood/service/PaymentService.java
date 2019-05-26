package fastfood.service;

import fastfood.domain.PaymentVO;
import org.springframework.data.domain.Page;

public interface PaymentService {
    Page<PaymentVO> getAll(int page, int size, String sort);
}