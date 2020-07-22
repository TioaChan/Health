package dev.tioachan.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import dev.tioachan.constant.MessageConstant;
import dev.tioachan.constant.RedisMessageConstant;
import dev.tioachan.dao.MemberDao;
import dev.tioachan.dao.OrderDao;
import dev.tioachan.dao.OrderSettingDao;
import dev.tioachan.domain.Member;
import dev.tioachan.domain.Order;
import dev.tioachan.domain.OrderForm;
import dev.tioachan.domain.OrderSetting;
import dev.tioachan.exception.CustomerException;
import dev.tioachan.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import redis.clients.jedis.JedisPool;

import java.util.Date;
import java.util.List;

@Service(interfaceClass = OrderService.class)
@Transactional
public class OrderServiceImpl implements OrderService {
	@Autowired
	private JedisPool jedisPool;

	@Autowired
	private MemberDao memberDao;

	@Autowired
	private OrderDao orderDao;

	@Autowired
	private OrderSettingDao orderSettingDao;

	@Override
	public Integer submit(OrderForm orderForm) throws CustomerException {
		try {
			//检查验证码是否匹配
			String key = RedisMessageConstant.SENDTYPE_ORDER + orderForm.getTelephone();
			String code = jedisPool.getResource().get(key);
			if (code ==null){
				throw new CustomerException(CustomerException.INVALID_CODE,CustomerException.SMS_CODE_INVALID);
			}
			if (!code.equals(orderForm.getValidateCode())){
				//验证码不匹配
				throw new CustomerException(CustomerException.INVALID_CODE,CustomerException.SMS_CODE_INVALID);
			}


			//检查当天是否满预约
			Date orderDate = orderForm.getOrderDate();
			OrderSetting orderSetting= orderSettingDao.findByOrderDate(orderDate);
			if(orderSetting == null){
				//指定日期没有进行预约设置，无法完成体检预约
				throw new CustomerException(CustomerException.INVALID_CODE, MessageConstant.SELECTED_DATE_CANNOT_ORDER);
			}
			if(orderSetting.getReservations() >= orderSetting.getNumber()){
				//已经约满，无法预约
				throw new CustomerException(CustomerException.INVALID_CODE, MessageConstant.ORDER_FULL);
			}


			//检查是否为注册用户
			Member currentMember= memberDao.findByPhone(orderForm.getTelephone());
			if (currentMember==null){
				//自动注册为会员，填充id
				currentMember=new Member();
				currentMember.setName(orderForm.getName());
				currentMember.setPhoneNumber(orderForm.getTelephone());
				currentMember.setIdCard(orderForm.getIdCard());
				currentMember.setSex(orderForm.getSex().toString());
				currentMember.setRegTime(new Date());
				memberDao.add(currentMember);//自动完成会员注册
			}else {
				//检查用户是否重复预约（同一用户同一时间同一套餐）
				Order order = new Order();
				order.setMemberId(currentMember.getId());
				order.setOrderDate(orderForm.getOrderDate());
				order.setSetmealId(orderForm.getSetmealId());
				List<Order> orderList =orderDao.findByCondition(order);
				if(orderList != null && orderList.size() > 0){
					//重复预约，无法完成再次预约
					throw new CustomerException(CustomerException.INVALID_CODE, MessageConstant.HAS_ORDERED);
				}
			}

			//成功预约
			Order order = new Order();
			order.setMemberId(currentMember.getId());//设置会员ID
			order.setOrderDate(orderForm.getOrderDate());//预约日期
			order.setOrderType(orderForm.getOrderType());//预约类型
			order.setOrderStatus(Order.ORDERSTATUS_NO);//到诊状态
			order.setSetmealId(orderForm.getSetmealId());//套餐ID
			orderDao.add(order);
			orderSetting.setReservations(orderSetting.getReservations() + 1);//设置已预约人数+1
			orderSettingDao.editReservationsByOrderDate(orderSetting);

			//返回id
			return order.getId();
		}catch (CustomerException e){
			throw e;
		}
	}

}
