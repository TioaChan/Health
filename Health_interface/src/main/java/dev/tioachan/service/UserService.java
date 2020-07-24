package dev.tioachan.service;


import dev.tioachan.domain.User;

public interface UserService {
	User findByUsername(String s);
}
