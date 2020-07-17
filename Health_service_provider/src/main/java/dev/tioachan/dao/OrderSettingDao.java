package dev.tioachan.dao;

import dev.tioachan.domain.OrderSetting;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface OrderSettingDao {
	long findCountByOrderDate(Date orderDate);

	void editNumberByOrderDate(OrderSetting orderSetting);

	void add(OrderSetting orderSetting);

	List<OrderSetting> getOrderSettingByMonth(@Param("startDay") Date startDay,@Param("endDay") Date endDay);
}
