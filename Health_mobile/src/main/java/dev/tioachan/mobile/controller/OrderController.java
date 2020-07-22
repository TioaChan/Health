package dev.tioachan.mobile.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import dev.tioachan.constant.MessageConstant;
import dev.tioachan.domain.OrderForm;
import dev.tioachan.entity.Result;
import dev.tioachan.exception.CustomerException;
import dev.tioachan.service.OrderService;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/order")
@CrossOrigin("*")
public class OrderController {

	@Reference
	private OrderService orderService;

	@RequestMapping("/submit")
	public Result submit(@RequestBody OrderForm orderForm){
		Integer orderId=null;
		try {
			orderForm.setOrderType("微信预约");
			orderId=orderService.submit(orderForm);
		}catch (CustomerException customerException){
			customerException.printStackTrace();
			return new Result(false, customerException.getMsgDes());
		}
		return new Result(true, MessageConstant.ORDER_SUCCESS,orderId);
	}
}
