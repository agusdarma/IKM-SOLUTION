package com.myproject.parking.lib.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class CheckUserService {
	private static final Logger LOG = LoggerFactory.getLogger(CheckUserService.class);
	
	public void isValidUser(String email,String sessionId) throws ParkingEngineException {
		// check email dan sessionid di table user
			// jika tidak ada throw ParkingEngineException.ENGINE_USER_NOT_FOUND
			// jika ada check apakah sessionid sudah expired atau belum
				// jika sudah expired return ENGINE_SESSION_ID_EXPIRED
				// jika belum expired user ini valid untuk transaksi return result true
		/**
		 * only for testing
		 */
//		throw new ParkingEngineException(ParkingEngineException.ENGINE_USER_NOT_FOUND);
	}
}
