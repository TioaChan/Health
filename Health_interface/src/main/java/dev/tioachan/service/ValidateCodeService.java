package dev.tioachan.service;

import dev.tioachan.exception.CustomerException;

public interface ValidateCodeService {
	void sendReservationCode(String telephone) throws CustomerException;

	boolean validateReservationCode(String telephone, String validateCode) throws CustomerException;
}
