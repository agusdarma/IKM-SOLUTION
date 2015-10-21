package com.jakarta.software.web.interceptor;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.jakarta.software.web.action.BaseAction;
import com.jakarta.software.web.data.RespLoginVO;
import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.AbstractInterceptor;

public class SecurityInterceptorAgenda extends AbstractInterceptor {
	private static final long serialVersionUID = 1L;

	private static final Logger LOG = LoggerFactory.getLogger(SecurityInterceptorAgenda.class);

	@Override
	public String intercept(ActionInvocation invocation) throws Exception {
		Map<String, Object> session = invocation.getInvocationContext().getSession();
		RespLoginVO userData = (RespLoginVO) session.get(BaseAction.LOGIN_KEY);

		if (userData == null || userData.getId() == 0) {
			LOG.debug("Session is expired or no session");
			return BaseAction.SESSION_EXPIRED;
		}
		return invocation.invoke();
	}

}
