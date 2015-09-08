package com.myproject.parking.lib.service;

import java.io.IOException;

import org.apache.commons.mail.EmailException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.myproject.parking.lib.entity.UserData;
import com.myproject.parking.lib.mapper.UserDataMapper;
import com.myproject.parking.lib.utils.Constants;
import com.myproject.parking.lib.utils.EmailSender;

@Service
public class UserDataService {
	private static final Logger LOG = LoggerFactory.getLogger(UserDataService.class);
	
	@Autowired
	private UserDataMapper userDataMapper;
	
	@Autowired
	private EmailSender emailSender;
	
	@Transactional(rollbackFor=Exception.class)
	public void processingRegistrationUser(UserData user) throws ParkingEngineException {
		LOG.debug("Checking phoneNo:[{}] and email:[{}] for Registration", user.getPhoneNo(), user.getEmail());
		UserData userData = userDataMapper.findUserDataByEmailAndPhoneNo(user.getEmail(), user.getPhoneNo());
		if (userData != null) {
			String msg = String.format("User with phoneNo:[%s] and email:[%s] has been registered", user.getPhoneNo(), user.getEmail());
			LOG.warn(msg);
			throw new ParkingEngineException(ParkingEngineException.ENGINE_USER_HAS_BEEN_REGISTERED);
		}else{
			LOG.debug("Sending Email Confirmation Registration..");
			try {
				composeEmailMsg(user);
			} catch (Exception e) {
				throw new ParkingEngineException(
						ParkingEngineException.FAILED_SENDING_EMAIL, e.getMessage());
			}
			LOG.debug("Registration User: {}", user);
			userDataMapper.createUserData(user);
		}
	}
	
	private void composeEmailMsg(UserData user) throws ParkingEngineException, EmailException, IOException{
//		boolean sendingEmail = false;
		String emailTo= user.getEmail();
		String emailSubject= Constants.APP_NAME+": Register Account";
		String txtMessage= "";
		String link = Constants.ENVIRONMENT_LIVE+"/userActivate?actKey="+ user.getActivateKey() + "&email=" + user.getEmail() + "&noHp=" + user.getPhoneNo();
		
		StringBuilder sb = new StringBuilder();
		sb.append("Dear Mr/Mrs ").append(user.getName()).append(",");
		sb.append("\n").append("\n");
		sb.append("Your account has been successfully registered.").append("\n");
		sb.append("Please click link below to activate your account.").append("\n");
		sb.append(link).append("\n");
		sb.append("Thank you for using our application.").append("\n");
		sb.append("\n");
		sb.append("Regards,");
		sb.append("\n").append("\n");
		sb.append("Administrator");
		txtMessage = sb.toString();
		
		
		String html = ""
				+ "<!DOCTYPE html PUBLIC \"-//W3C//DTD XHTML 1.0 Transitional//EN\" \"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd\"> "
				+ "<html xmlns=\"http://www.w3.org/1999/xhtml\" style=\"font-family: 'Helvetica Neue', Helvetica, Arial, sans-serif; box-sizing: border-box; font-size: 14px; margin: 0;\"> "
				+ "<head> "
				+ "<meta name=\"viewport\" content=\"width=device-width\" /> "
				+ "<meta http-equiv=\"Content-Type\" content=\"text/html; charset=UTF-8\" /> "
				+ "<title>Premium Parking Solution : Registration User</title> "
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
				+ "{background-color: #f6f6f6; "
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
				+ "				<table class=\"main\" width=\"100%\" cellpadding=\"0\" cellspacing=\"0\" itemprop=\"action\" itemscope itemtype=\"http://schema.org/ConfirmAction\" style=\"font-family: 'Helvetica Neue',Helvetica,Arial,sans-serif; box-sizing: border-box; font-size: 14px; border-radius: 3px; background-color: #fff; margin: 0; border: 1px solid #e9e9e9;\" bgcolor=\"#fff\"><tr style=\"font-family: 'Helvetica Neue',Helvetica,Arial,sans-serif; box-sizing: border-box; font-size: 14px; margin: 0;\"> "
				+ "				<td class=\"content-wrap\" style=\"font-family: 'Helvetica Neue',Helvetica,Arial,sans-serif; box-sizing: border-box; font-size: 14px; vertical-align: top; margin: 0; padding: 20px;\" valign=\"top\"> "
				+ "							<meta itemprop=\"name\" content=\"Confirm Email\" style=\"font-family: 'Helvetica Neue',Helvetica,Arial,sans-serif; box-sizing: border-box; font-size: 14px; margin: 0;\" /><table width=\"100%\" cellpadding=\"0\" cellspacing=\"0\" style=\"font-family: 'Helvetica Neue',Helvetica,Arial,sans-serif; box-sizing: border-box; font-size: 14px; margin: 0;\"><tr style=\"font-family: 'Helvetica Neue',Helvetica,Arial,sans-serif; box-sizing: border-box; font-size: 14px; margin: 0;\"><td class=\"content-block\" style=\"font-family: 'Helvetica Neue',Helvetica,Arial,sans-serif; box-sizing: border-box; font-size: 14px; vertical-align: top; margin: 0; padding: 0 0 20px;\" valign=\"top\"> "
				+ "										Dear Mr/Mrs " + user.getName()
				+ "									</td> "
				+ "								</tr><tr style=\"font-family: 'Helvetica Neue',Helvetica,Arial,sans-serif; box-sizing: border-box; font-size: 14px; margin: 0;\"><td class=\"content-block\" style=\"font-family: 'Helvetica Neue',Helvetica,Arial,sans-serif; box-sizing: border-box; font-size: 14px; vertical-align: top; margin: 0; padding: 0 0 20px;\" valign=\"top\"> "
				+ "										Please confirm your email address by clicking the link below. "
				+ "									</td> "
				+ "								</tr><tr style=\"font-family: 'Helvetica Neue',Helvetica,Arial,sans-serif; box-sizing: border-box; font-size: 14px; margin: 0;\"><td class=\"content-block\" style=\"font-family: 'Helvetica Neue',Helvetica,Arial,sans-serif; box-sizing: border-box; font-size: 14px; vertical-align: top; margin: 0; padding: 0 0 20px;\" valign=\"top\"> "
				+ "										We may need to send you critical information about our service and it is important that we have an accurate email address. "
				+ "									</td> "
				+ "								</tr><tr style=\"font-family: 'Helvetica Neue',Helvetica,Arial,sans-serif; box-sizing: border-box; font-size: 14px; margin: 0;\"><td class=\"content-block\" itemprop=\"handler\" itemscope itemtype=\"http://schema.org/HttpActionHandler\" style=\"font-family: 'Helvetica Neue',Helvetica,Arial,sans-serif; box-sizing: border-box; font-size: 14px; vertical-align: top; margin: 0; padding: 0 0 20px;\" valign=\"top\"> "
				+ "										<a href="+link+" class=\"btn-primary\" itemprop=\"url\" style=\"font-family: 'Helvetica Neue',Helvetica,Arial,sans-serif; box-sizing: border-box; font-size: 14px; color: #FFF; text-decoration: none; line-height: 2em; font-weight: bold; text-align: center; cursor: pointer; display: inline-block; border-radius: 5px; text-transform: capitalize; background-color: #348eda; margin: 0; border-color: #348eda; border-style: solid; border-width: 10px 20px;\">Confirm email address</a> "
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
		
//		sendingEmail = emailSender.sendSimpleMail("", emailTo, emailSubject, txtMessage);
//		if(sendingEmail == false){
//			String msg = "Failed Sending Email to: [" + emailTo + "]";
//			LOG.warn(msg);
//			throw new ParkingEngineException(ParkingEngineException.FAILED_SENDING_EMAIL);
//		}
	
	}
}
