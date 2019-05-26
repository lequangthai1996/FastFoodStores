package fastfood.controller;

import com.paypal.api.payments.Links;
import com.paypal.api.payments.Payment;
import com.paypal.base.rest.PayPalRESTException;
import fastfood.config.PaypalPaymentIntent;
import fastfood.config.PaypalPaymentMethod;
import fastfood.domain.PaymentVO;
import fastfood.entity.PaymentEntity;
import fastfood.repository.PaymentRepository;
import fastfood.service.PaymentService;
import fastfood.service.impl.PaypalService;
import fastfood.utils.URLUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
@Controller
@RequestMapping("/payments")
public class PaymentController {
	
	public static final String PAYPAL_SUCCESS_URL = "pay/success";
	public static final String PAYPAL_CANCEL_URL = "pay/cancel";
	
	private Logger log = LoggerFactory.getLogger(getClass());
	@Autowired
	private PaymentRepository paymentRepository;

	@Autowired
	private PaymentService paymentService;

	@Autowired
	private PaypalService paypalService;
	
	@RequestMapping(method = RequestMethod.GET)
	@ResponseBody
	public Page<PaymentVO> index(@RequestParam("page") int page,
								 @RequestParam("size") int size,
								 @RequestParam("sort") String sort) {
		return paymentService.getAll(page,size,sort);
	}

	@RequestMapping(method = RequestMethod.POST, value = "/create")
	@ResponseBody
	public PaymentEntity create(@RequestBody PaymentEntity payment){
		PaymentEntity newPayment = new PaymentEntity();
		newPayment.setOrder(payment.getOrder());
		newPayment.setPayEmail(payment.getPayEmail());
		newPayment.setTransactionAmount(payment.getTransactionAmount());
		newPayment.setTransactionTime(payment.getTransactionTime());
		return paymentRepository.save(newPayment);
	}

	@RequestMapping(method = RequestMethod.POST, value = "pay")
	public String pay(HttpServletRequest request){
		String cancelUrl = URLUtils.getBaseURl(request) + "/" + PAYPAL_CANCEL_URL;
		String successUrl = URLUtils.getBaseURl(request) + "/" + PAYPAL_SUCCESS_URL;
		try {
			Payment payment = paypalService.createPayment(
					4.00, 
					"USD", 
					PaypalPaymentMethod.paypal,
					PaypalPaymentIntent.sale,
					"payment description", 
					cancelUrl, 
					successUrl);
			for(Links links : payment.getLinks()){
				if(links.getRel().equals("approval_url")){
					return "redirect:" + links.getHref();
				}
			}
		} catch (PayPalRESTException e) {
			log.error(e.getMessage());
		}
		return "redirect:/";
	}

	@RequestMapping(method = RequestMethod.GET, value = PAYPAL_CANCEL_URL)
	public String cancelPay(){
		return "cancel";
	}

	@RequestMapping(method = RequestMethod.GET, value = PAYPAL_SUCCESS_URL)
	public String successPay(@RequestParam("paymentId") String paymentId, @RequestParam("PayerID") String payerId){
		try {
			Payment payment = paypalService.executePayment(paymentId, payerId);
			if(payment.getState().equals("approved")){
				return "success";
			}
		} catch (PayPalRESTException e) {
			log.error(e.getMessage());
		}
		return "redirect:/";
	}
	
}
