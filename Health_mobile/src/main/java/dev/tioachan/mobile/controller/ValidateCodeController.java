package dev.tioachan.mobile.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import dev.tioachan.constant.MessageConstant;
import dev.tioachan.entity.Result;
import dev.tioachan.exception.CustomerException;
import dev.tioachan.service.ValidateCodeService;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/validateCode")
@CrossOrigin("*")
public class ValidateCodeController {

	@Reference
	private ValidateCodeService validateCodeService;

	//用户在线体检预约发送验证码
	@RequestMapping("/send4Order")
	public Result send4Order(String telephone){
		try {
			validateCodeService.sendReservationCode(telephone);
		}catch (CustomerException exception){
			return new Result(false, exception.getMsgDes());
		}
		return new Result(true,MessageConstant.SEND_VALIDATECODE_SUCCESS);
	}
}
