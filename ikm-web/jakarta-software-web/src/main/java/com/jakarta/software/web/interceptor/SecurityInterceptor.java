package com.jakarta.software.web.interceptor;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.jakarta.software.web.action.BaseAction;
import com.jakarta.software.web.action.security.ChangePasswordAction;
import com.jakarta.software.web.data.UserDataLoginVO;
import com.jakarta.software.web.data.WebConstants;
import com.jakarta.software.web.mapper.UserDataMapper;
import com.jakarta.software.web.service.MmbsWebException;
import com.jakarta.software.web.service.SettingService;
import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.interceptor.AbstractInterceptor;

public class SecurityInterceptor extends AbstractInterceptor {
	private static final long serialVersionUID = 1L;

	private static final Logger LOG = LoggerFactory.getLogger(SecurityInterceptor.class);

	@Autowired
	private UserDataMapper userDataMapper;

	@Autowired
	private SettingService settingService;

	@Override
	public String intercept(ActionInvocation invocation) throws Exception {
		Map<String, Object> session = invocation.getInvocationContext().getSession();
		UserDataLoginVO userData = (UserDataLoginVO) session.get(BaseAction.LOGIN_KEY);

		if (userData == null || userData.getId() == 0) {
			LOG.debug("Session is expired or no session");
			return BaseAction.SESSION_EXPIRED;
		}
		Object o = invocation.getAction();

		if (o instanceof ModuleCheckable) {
			int menuId = ((ModuleCheckable) o).getMenuId();
			if (userData.getLevelVO() == null) {
				LOG.debug("No rank for user: {}", new String[] { "" + userData });
				return BaseAction.MODULE_ACCESS_DENIED;
			}
			if (!userData.getLevelVO().isMenuAllowed(menuId)) {
				LOG.debug("User does not have access to menu: {}", new String[] { "" + menuId });
				if (o instanceof ActionSupport) {
					ActionSupport a = (ActionSupport) o;
					a.addActionError(a.getText("err.accessDenied"));

				}
				return BaseAction.MODULE_ACCESS_DENIED;
			}
		}
		if (o instanceof BaseAction) {
			try {
				((BaseAction) o).checkNotif(userData);
			} catch (Exception e) {
				((BaseAction) o).handleException(e);
			}
		}
		if (userDataMapper.checkSessionLogin(userData.getId(), userData.getSessionId()) == 0) {
			LOG.debug("User Session is over: " + userData);
			if (o instanceof ActionSupport) {
				ActionSupport a = (ActionSupport) o;
				a.addActionError(a.getText("err.needLoginAgain"));
			}
			return BaseAction.SESSION_EXPIRED;
		}

		if (WebConstants.NEED_CHANGE_PASS.equalsIgnoreCase(userData.getNeedChangePass())) {
			String actionName = o.getClass().getName();
			String changePassword = ChangePasswordAction.class.getName();
			if (!actionName.equals(changePassword)) {
				if (o instanceof ActionSupport) {
					ActionSupport a = (ActionSupport) o;
					// a.addActionError(a.getText("err.needChangePassword"));
					a.addActionError("Harap lakukan perubahan Password dahulu");
				}
				return BaseAction.FORCE_CHANGE_PASSWORD; // CHANGE_PASSWORD
			}
		}
		return invocation.invoke();
	}

}
