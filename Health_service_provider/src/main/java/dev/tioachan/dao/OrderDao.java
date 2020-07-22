package dev.tioachan.dao;

import dev.tioachan.domain.Order;

import java.util.List;

public interface OrderDao {
	List<Order> findByCondition(Order order);

	void add(Order order);
}
