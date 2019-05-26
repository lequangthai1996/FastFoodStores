package fastfood.service.impl;

import com.google.common.base.Function;
import fastfood.build.PaymentVOBuilder;
import fastfood.domain.OrderVO;
import fastfood.domain.PaymentVO;
import fastfood.entity.PaymentEntity;
import fastfood.repository.PaymentRepository;
import fastfood.service.PaymentService;
import fastfood.utils.PageConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
public class PaymentServiceImpl implements PaymentService {
    @Autowired
    private PaymentRepository paymentRepository;
    private OrderServiceImpl orderService;
    @Override
    public Page<PaymentVO> getAll(int page, int size, String sort) {
        Page<PaymentEntity> payments;
        if (sort != null && !sort.equals("") && (sort.charAt(0) == ' ' || sort.charAt(0) == '-')) {
            String direction = sort.substring(0,1);
            String keySort = sort.substring(1,sort.length());
            payments = paymentRepository.findAll(PageRequest.of(page,size,
                    direction.equals("-") ? Sort.Direction.DESC : Sort.Direction.ASC, keySort));
        } else {
            payments = paymentRepository.findAll(PageRequest.of(page, size));
        }


        return PageConverter.convert(payments).using(new Function<PaymentEntity, PaymentVO>() {
            @Override
            public PaymentVO apply(PaymentEntity source) {
                return convertVO(source);
            }
        });
    }
    public PaymentVO convertVO(PaymentEntity payment) {
        OrderVO orderVO = new OrderVO();
        orderVO.setId(payment.getOrder().getId());
        PaymentVO paymentVO = PaymentVOBuilder.aPaymentVO().withId(payment.getId())
                .withPayerEmail(payment.getPayEmail())
                .withTransactionAmount(payment.getTransactionAmount())
                .withTransactionAt(payment.getTransactionTime())
                .withOrder(orderVO).build();
        return paymentVO;
    }
}