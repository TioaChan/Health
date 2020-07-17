package dev.tioachan.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import dev.tioachan.dao.OrderSettingDao;
import dev.tioachan.domain.OrderSetting;
import dev.tioachan.service.OrderSettingService;
import org.apache.commons.lang3.time.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.text.ParseException;
import java.util.ConcurrentModificationException;
import java.util.Date;
import java.util.List;

@Service(interfaceClass = OrderSettingService.class)
@Transactional
public class OrderSettingServiceImpl implements OrderSettingService {
	@Autowired
	private OrderSettingDao orderSettingDao;

	@Override
	public void add(List<OrderSetting> data) {
		if(data != null && data.size() > 0){
			for (OrderSetting orderSetting : data) {
				//判断当前日期是否已经进行了预约设置
				long countByOrderDate = orderSettingDao.findCountByOrderDate(orderSetting.getOrderDate());
				if(countByOrderDate > 0){
					//已经进行了预约设置，执行更新操作
					orderSettingDao.editNumberByOrderDate(orderSetting);
				}else{
					//没有进行预约设置，执行插入操作
					orderSettingDao.add(orderSetting);
				}
			}
		}
		else{
			//抛出异常，或者返回错误对象
		}
	}

	@Override
	public List<OrderSetting> getOrderSettingByMonth(String month) {
		// 2020-7
		List<OrderSetting> list=null;
		try {
			Date startDay = DateUtils.parseDate(month+"-01", "yyyy-MM-dd");
			Date endDay = DateUtils.addMonths(startDay, 1);
			list = orderSettingDao.getOrderSettingByMonth(startDay,endDay);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return list;
	}
}
