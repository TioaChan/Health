package dev.tioachan.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import dev.tioachan.constant.MessageConstant;
import dev.tioachan.constant.RedisMessageConstant;
import dev.tioachan.exception.CustomerException;
import dev.tioachan.service.ValidateCodeService;
import dev.tioachan.util.ValidateCodeUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import redis.clients.jedis.JedisPool;

@Service(interfaceClass = ValidateCodeService.class)
@Transactional
public class ValidateCodeServiceImpl implements ValidateCodeService {
	@Autowired
	private JedisPool jedisPool;

	@Override
	public void sendReservationCode(String telephone) throws CustomerException {
		String key = RedisMessageConstant.SENDTYPE_ORDER + telephone;
		//随机生成4位数字验证码
		String validateCode = ValidateCodeUtils.generateValidateCode(6).toString();

		Long ttl = jedisPool.getResource().ttl(key);
		if(ttl > 240){
//			return new Result(false, "一分钟内不能重复点击");
			throw new CustomerException(CustomerException.INVALID_CODE,CustomerException.SMS_CODE_STILL_ALIVE);
		}

		//给用户发送验证码
		try{
//			SMSUtils.sendShortMessage(SMSUtils.VALIDATE_CODE,telephone,validateCode);
			System.out.println(validateCode);
		}catch (Exception e){
			e.printStackTrace();
			jedisPool.getResource().del(key);
//			return new Result(false, MessageConstant.SEND_VALIDATECODE_FAIL);
			throw new CustomerException(CustomerException.INVALID_CODE,MessageConstant.SEND_VALIDATECODE_FAIL);
		}
		//将验证码保存到redis（5分钟）
		jedisPool.getResource().setex(key,300,validateCode);
	}

	@Override
	public boolean validateReservationCode(String telephone, String validateCode) throws CustomerException {
		String key = RedisMessageConstant.SENDTYPE_ORDER + telephone;
		String code = jedisPool.getResource().get(key);
		if (code ==null){
			throw new CustomerException(CustomerException.INVALID_CODE,CustomerException.SMS_CODE_INVALID);
		}
		return code.equals(validateCode);
	}
}
