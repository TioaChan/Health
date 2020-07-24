package dev.tioachan.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import dev.tioachan.domain.User;
import dev.tioachan.service.UserService;

@Service(interfaceClass = UserService.class)
public class UserServiceImpl implements UserService {
	@Override
	public User findByUsername(String s) {
		return null;
	}
}
