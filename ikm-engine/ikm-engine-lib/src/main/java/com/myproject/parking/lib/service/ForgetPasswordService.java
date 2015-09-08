package com.myproject.parking.lib.service;

import java.io.IOException;

import org.apache.commons.mail.EmailException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ResourceLoaderAware;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Service;

import com.myproject.parking.lib.entity.UserData;
import com.myproject.parking.lib.mapper.UserDataMapper;
import com.myproject.parking.lib.utils.CipherUtil;
import com.myproject.parking.lib.utils.CommonUtil;
import com.myproject.parking.lib.utils.Constants;
import com.myproject.parking.lib.utils.EmailSender;

@Service
public class ForgetPasswordService implements ResourceLoaderAware {
	private static final Logger LOG = LoggerFactory
			.getLogger(ForgetPasswordService.class);

	@Autowired
	private UserDataMapper userDataMapper;

	@Autowired
	private AppsTimeService timeService;

	@Autowired
	private EmailSender emailSender;

	private ResourceLoader resourceLoader;

	public void forgetPassword(String email) throws ParkingEngineException {
		LOG.info("forgetPassword with param : " + " EMAIL: " + email);
		UserData user = userDataMapper.findUserDataByEmail(email);
		if (user == null) {
			LOG.error("Can't find User");
			throw new ParkingEngineException(
					ParkingEngineException.ENGINE_USER_NOT_FOUND);
		}
		if (Constants.BLOCKED == user.getStatus()) {
			LOG.error("User already blocked");
			throw new ParkingEngineException(
					ParkingEngineException.ENGINE_USER_BLOCKED);
		}
		if (Constants.PENDING == user.getStatus()) {
			LOG.error("User not active");
			throw new ParkingEngineException(
					ParkingEngineException.ENGINE_USER_NOT_ACTIVE);
		}
		// update password user
		// update updated on
		// kirim ke email
		String password = CommonUtil
				.generateAlphaNumeric(Constants.LENGTH_GENERATE_FORGET_PASSWORD);
		String passwordHash = CipherUtil.passwordDigest(user.getEmail(),
				password);
		// kirim ke email user
		try {
			composeEmailMsg(user, password);
		} catch (Exception e) {
			throw new ParkingEngineException(
					ParkingEngineException.FAILED_SENDING_EMAIL, e.getMessage());
		}

		userDataMapper.updatePasswordUser(email, passwordHash,
				timeService.getCurrentTime());

		LOG.info("forgetPassword done with param : " + " EMAIL: " + email);
	}

	private void composeEmailMsg(UserData user, String password)
			throws EmailException, IOException {
		String emailTo = user.getEmail();
		String emailSubject = Constants.APP_NAME+" : Forget Password";
		String txtMessage = "";

		StringBuilder sb = new StringBuilder();
		sb.append("Dear Mr/Mrs ").append(user.getName()).append(",");
		sb.append("\n").append("\n");
		sb.append("Your Password: " + password).append("\n");
		sb.append("Please change password after login.").append("\n");
		sb.append("Thank you for using our application.").append("\n");
		sb.append("\n");
		sb.append("Regards,");
		sb.append("\n").append("\n");
		sb.append("Administrator");
		txtMessage = sb.toString();

//		Resource img1 = resourceLoader
//				.getResource("url:http://www.google.com/intl/en_ALL/images/logo.gif");
//		Resource img2 = resourceLoader
//				.getResource("url:http://www.google.com/intl/en_ALL/images/logo.gif");
		// String html =
		// "<html><body><table><tr><td>Image 1</td><td>Image 2</td></tr><tr><td>"
		// +
		// "<img src='${0}'/></td><td><img src='${1}'/></td></tr></table></body></html>";

		// emailSender.sendHTMLMailNew("agusdk2011@gmail.com", "ADK",
		// new String[] { emailTo }, new String[] { "Guy Bashan" },
		// emailSubject, html2, message, new Resource[] { img1, img2 });

		String html = ""
				+ "<!DOCTYPE html PUBLIC \"-//W3C//DTD XHTML 1.0 Transitional//EN\" \"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd\"> "
				+ "<html xmlns=\"http://www.w3.org/1999/xhtml\" style=\"font-family: 'Helvetica Neue', Helvetica, Arial, sans-serif; box-sizing: border-box; font-size: 14px; margin: 0;\"> "
				+ "<head> "
				+ "<meta name=\"viewport\" content=\"width=device-width\" /> "
				+ "<meta http-equiv=\"Content-Type\" content=\"text/html; charset=UTF-8\" /> "
				+ "<title>Forget Password</title> "
				+ " "
				+ " "
				+ "<style type=\"text/css\"> "
				+ "img "
				+ "{max-width: 100%; "
				+ "} "
				+ "body "
				+ "{-webkit-font-smoothing: antialiased; -webkit-text-size-adjust: none; width: 100% !important; height: 100%; line-height: 1.6em; "
				+ "} "
				+ "body "
				+ "{background-color: #CCFFCC; "
				+ "} "
				+ "@media only screen and (max-width: 640px) "
				+ "{  body { "
				+ "    padding: 0 !important; "
				+ "  } "
				+ "  h1 "
				+ "{    font-weight: 800 !important; margin: 20px 0 5px !important; "
				+ "  } "
				+ "  h2 "
				+ "{    font-weight: 800 !important; margin: 20px 0 5px !important; "
				+ "  } "
				+ "  h3 "
				+ "{    font-weight: 800 !important; margin: 20px 0 5px !important; "
				+ "  } "
				+ "  h4 "
				+ "{    font-weight: 800 !important; margin: 20px 0 5px !important; "
				+ "  } "
				+ "  h1 "
				+ "{    font-size: 22px !important; "
				+ "  } "
				+ "  h2 "
				+ "{    font-size: 18px !important; "
				+ "  } "
				+ "  h3 "
				+ "{    font-size: 16px !important; "
				+ "  } "
				+ "  .container "
				+ "{    padding: 0 !important; width: 100% !important; "
				+ "  } "
				+ "  .content "
				+ "{    padding: 0 !important; "
				+ "  } "
				+ "  .content-wrap "
				+ "{    padding: 10px !important; "
				+ "  } "
				+ "  .invoice "
				+ "{    width: 100% !important; "
				+ "  } "
				+ "} "
				+ "</style> "
				+ "</head> "
				+ " "
				+ "<body itemscope itemtype=\"http://schema.org/EmailMessage\" style=\"font-family: 'Helvetica Neue',Helvetica,Arial,sans-serif; box-sizing: border-box; font-size: 14px; -webkit-font-smoothing: antialiased; -webkit-text-size-adjust: none; width: 100% !important; height: 100%; line-height: 1.6em; background-color: #f6f6f6; margin: 0;\" bgcolor=\"#f6f6f6\"> "
				+ " "
				+ "<table class=\"body-wrap\" style=\"font-family: 'Helvetica Neue',Helvetica,Arial,sans-serif; box-sizing: border-box; font-size: 14px; width: 100%; background-color: #f6f6f6; margin: 0;\" bgcolor=\"#f6f6f6\"><tr style=\"font-family: 'Helvetica Neue',Helvetica,Arial,sans-serif; box-sizing: border-box; font-size: 14px; margin: 0;\"><td style=\"font-family: 'Helvetica Neue',Helvetica,Arial,sans-serif; box-sizing: border-box; font-size: 14px; vertical-align: top; margin: 0;\" valign=\"top\"></td> "
				+ "		<td class=\"container\" width=\"600\" style=\"font-family: 'Helvetica Neue',Helvetica,Arial,sans-serif; box-sizing: border-box; font-size: 14px; vertical-align: top; display: block !important; max-width: 600px !important; clear: both !important; margin: 0 auto;\" valign=\"top\"> "
				+ "			<div class=\"content\" style=\"font-family: 'Helvetica Neue',Helvetica,Arial,sans-serif; box-sizing: border-box; font-size: 14px; max-width: 600px; display: block; margin: 0 auto; padding: 20px;\"> "
				+ "				<table class=\"main\" width=\"100%\" cellpadding=\"0\" cellspacing=\"0\" itemprop=\"action\" itemscope itemtype=\"http://schema.org/ConfirmAction\" style=\"font-family: 'Helvetica Neue',Helvetica,Arial,sans-serif; box-sizing: border-box; font-size: 14px; border-radius: 3px; background-color: #fff; margin: 0; border: 1px solid #e9e9e9;\" bgcolor=\"#fff\"><tr style=\"font-family: 'Helvetica Neue',Helvetica,Arial,sans-serif; box-sizing: border-box; font-size: 14px; margin: 0;\"><td class=\"content-wrap\" style=\"font-family: 'Helvetica Neue',Helvetica,Arial,sans-serif; box-sizing: border-box; font-size: 14px; vertical-align: top; margin: 0; padding: 20px;\" valign=\"top\"> "
				+ "							<meta itemprop=\"name\" content=\"Confirm Email\" style=\"font-family: 'Helvetica Neue',Helvetica,Arial,sans-serif; box-sizing: border-box; font-size: 14px; margin: 0;\" /><table width=\"100%\" cellpadding=\"0\" cellspacing=\"0\" style=\"font-family: 'Helvetica Neue',Helvetica,Arial,sans-serif; box-sizing: border-box; font-size: 14px; margin: 0;\"><tr style=\"font-family: 'Helvetica Neue',Helvetica,Arial,sans-serif; box-sizing: border-box; font-size: 14px; margin: 0;\"><td class=\"content-block\" style=\"font-family: 'Helvetica Neue',Helvetica,Arial,sans-serif; box-sizing: border-box; font-size: 14px; vertical-align: top; margin: 0; padding: 0 0 20px;\" valign=\"top\"> "
				+ "										Dear Mr/Mrs " + user.getName()
				+ "									</td> "
				+ "								</tr><tr style=\"font-family: 'Helvetica Neue',Helvetica,Arial,sans-serif; box-sizing: border-box; font-size: 14px; margin: 0;\"><td class=\"content-block\" style=\"font-family: 'Helvetica Neue',Helvetica,Arial,sans-serif; box-sizing: border-box; font-size: 14px; vertical-align: top; margin: 0; padding: 0 0 0px;\" valign=\"top\"> "
				+ "										Your New Password: " + password
				+ "									</td> "
				+ "								</tr><tr style=\"font-family: 'Helvetica Neue',Helvetica,Arial,sans-serif; box-sizing: border-box; font-size: 14px; margin: 0;\"><td class=\"content-block\" style=\"font-family: 'Helvetica Neue',Helvetica,Arial,sans-serif; box-sizing: border-box; font-size: 14px; vertical-align: top; margin: 0; padding: 0 0 20px;\" valign=\"top\"> "
				+ "										Please change password after login, Thank you for using our application. "
				+ "									</td> "
				+ "								</tr><tr style=\"font-family: 'Helvetica Neue',Helvetica,Arial,sans-serif; box-sizing: border-box; font-size: 14px; margin: 0;\"><td class=\"content-block\" style=\"font-family: 'Helvetica Neue',Helvetica,Arial,sans-serif; box-sizing: border-box; font-size: 14px; vertical-align: top; margin: 0; padding: 0 0 0px;\" valign=\"top\"> "
				+ "										Regards, "
				+ "									</td> "
				+ "								</tr><tr style=\"font-family: 'Helvetica Neue',Helvetica,Arial,sans-serif; box-sizing: border-box; font-size: 14px; margin: 0;\"><td class=\"content-block\" style=\"font-family: 'Helvetica Neue',Helvetica,Arial,sans-serif; box-sizing: border-box; font-size: 14px; vertical-align: top; margin: 0; padding: 0 0 20px;\" valign=\"top\"> "
				+ "										Administrator "
				+ "									</td> "
				+ "								</tr></table></td> "
				+ "					</tr></table><div class=\"footer\" style=\"font-family: 'Helvetica Neue',Helvetica,Arial,sans-serif; box-sizing: border-box; font-size: 14px; width: 100%; clear: both; color: #999; margin: 0; padding: 20px;\"> "
				+ "					<table width=\"100%\" style=\"font-family: 'Helvetica Neue',Helvetica,Arial,sans-serif; box-sizing: border-box; font-size: 14px; margin: 0;\"><tr style=\"font-family: 'Helvetica Neue',Helvetica,Arial,sans-serif; box-sizing: border-box; font-size: 14px; margin: 0;\"><td class=\"aligncenter content-block\" style=\"font-family: 'Helvetica Neue',Helvetica,Arial,sans-serif; box-sizing: border-box; font-size: 12px; vertical-align: top; color: #999; text-align: center; margin: 0; padding: 0 0 20px;\" align=\"center\" valign=\"top\">Follow <a href=\"http://google.com\" style=\"font-family: 'Helvetica Neue',Helvetica,Arial,sans-serif; box-sizing: border-box; font-size: 12px; color: #999; text-decoration: underline; margin: 0;\">@premium_parking_solution</a> on Twitter.</td> "
				+ "						</tr></table></div></div> "
				+ "		</td> "
				+ "		<td style=\"font-family: 'Helvetica Neue',Helvetica,Arial,sans-serif; box-sizing: border-box; font-size: 14px; vertical-align: top; margin: 0;\" valign=\"top\"></td> "
				+ "	</tr></table></body> "
				+ "</html>";


		emailSender.sendHTMLMailNew("", "",
				new String[] { emailTo }, new String[] { user.getName() },
				emailSubject, html, txtMessage, null);

	}

	@Override
	public void setResourceLoader(ResourceLoader resourceLoader) {
		this.resourceLoader = resourceLoader;

	}
}
