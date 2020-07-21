package dev.tioachan.mobile.controller;

import dev.tioachan.constant.MessageConstant;
import dev.tioachan.constant.RedisMessageConstant;
import dev.tioachan.entity.Result;
import dev.tioachan.util.SMSUtils;
import dev.tioachan.util.ValidateCodeUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import redis.clients.jedis.JedisPool;

@RestController
@RequestMapping("/validateCode")
public class ValidateCodeController {
	@Autowired
	private JedisPool jedisPool;

	//用户在线体检预约发送验证码
	@RequestMapping("/send4Order")
	public Result send4Order(String telephone){
		String key = telephone + RedisMessageConstant.SENDTYPE_ORDER;
		//随机生成4位数字验证码
		String validateCode = ValidateCodeUtils.generateValidateCode(6).toString();
		Long ttl = jedisPool.getResource().ttl(key);
		if(ttl > 240){
			return new Result(false, "一分钟内不能重复点击");
		}
		//给用户发送验证码
		try{
//			SMSUtils.sendShortMessage(SMSUtils.VALIDATE_CODE,telephone,validateCode);
			System.out.println(validateCode);
		}catch (Exception e){
			e.printStackTrace();
			jedisPool.getResource().del(key);
			return new Result(false, MessageConstant.SEND_VALIDATECODE_FAIL);
		}
		//将验证码保存到redis（5分钟）
		jedisPool.getResource().setex(key,300,validateCode);
		return new Result(true,MessageConstant.SEND_VALIDATECODE_SUCCESS);
	}
}
