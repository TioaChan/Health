package dev.tioachan.service;

import dev.tioachan.domain.OrderForm;
import dev.tioachan.exception.CustomerException;

public interface OrderService {

	Integer submit(OrderForm orderForm) throws CustomerException;
}
