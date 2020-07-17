package dev.tioachan.service;

import dev.tioachan.domain.OrderSetting;

import java.util.List;

public interface OrderSettingService {
	void add(List<OrderSetting> data);

	List<OrderSetting> getOrderSettingByMonth(String month);
}
