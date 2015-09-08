package com.myproject.parking.trx.logic;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LogicFactory {
	private static final Logger LOG = LoggerFactory.getLogger(LogicFactory.class);
	
	private Map<String, BaseQueryLogic> logic;

	
	public Map<String, BaseQueryLogic> getLogic() {
		return logic;
	}

	public void setLogic(Map<String, BaseQueryLogic> logic) {
		this.logic = logic;
		LOG.info("logic :"+ logic);
	}
}
