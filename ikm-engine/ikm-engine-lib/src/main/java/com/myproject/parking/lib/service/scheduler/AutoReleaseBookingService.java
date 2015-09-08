package com.myproject.parking.lib.service.scheduler;

import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.mail.EmailException;
import org.codehaus.jackson.map.DeserializationConfig;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.SerializationConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.myproject.parking.lib.data.BookingVO;
import com.myproject.parking.lib.data.EmailNotifVO;
import com.myproject.parking.lib.entity.Booking;
import com.myproject.parking.lib.mapper.BookingMapper;
import com.myproject.parking.lib.service.AppsTimeService;
import com.myproject.parking.lib.utils.CommonUtil;
import com.myproject.parking.lib.utils.Constants;
import com.myproject.parking.lib.utils.EmailSender;

@Service
public class AutoReleaseBookingService {
	private static final Logger LOG = LoggerFactory.getLogger(AutoReleaseBookingService.class);
	
	@Autowired
	private BookingMapper bookingMapper;
	
	@Autowired
	private AppsTimeService timeService;
	
	@Autowired
	private EmailSender emailSender;
	
	private HashMap<String,String> emailNotif;
	
	private ObjectMapper mapper;
	
	@Transactional(rollbackFor=Exception.class)
	public void release(){
		if(mapper == null){
			mapper = new ObjectMapper();
			mapper.configure(DeserializationConfig.Feature.FAIL_ON_UNKNOWN_PROPERTIES, false);
			mapper.configure(SerializationConfig.Feature.WRITE_DATES_AS_TIMESTAMPS, false);
		}
		
		
		/**
		 * Release parkiran jika tidak dibayar
		 */
		if(emailNotif == null)
			emailNotif = new HashMap<String, String>();
		LOG.info("release on process");	
		List<Booking> listBookingNotPay = bookingMapper.findBookingNotPay();
		LOG.debug("release on process with listBookingNotPay " + listBookingNotPay.size());	
		for (Booking booking : listBookingNotPay) {
			
				if(isExpiredPay(booking.getBookingDate())){
					bookingMapper.updateMallSlotStatusAvailable(booking.getIdSlot());
					booking.setBookingStatus(Constants.STATUS_AUTO_RELEASE_AFTER_BOOKING);
					bookingMapper.updateBookingStatus(booking);
				}					
			
			
		}
		/**
		 * Release parkiran jika sudah lewat dari 2 jam
		 */
		List<Booking> listBookingNotCheckIn = null;
		listBookingNotCheckIn = bookingMapper.findBookingNotCheckIn();
		LOG.debug("release on process with listBookingNotCheckIn " + listBookingNotCheckIn.size());	
		for (Booking booking : listBookingNotCheckIn) {
			
				if(isExpiredCheckIn(booking.getBookingDate())){
					bookingMapper.updateMallSlotStatusAvailable(booking.getIdSlot());
					booking.setBookingStatus(Constants.STATUS_AUTO_RELEASE_AFTER_PAY);
					bookingMapper.updateBookingStatus(booking);
					try {
						sendNotificationExpired(booking, "sudah expired");
						LOG.debug("sendNotificationExpired success");	
					} catch (EmailException e) {
						LOG.error("[EmailException] sendNotificationExpired failed " + e.getMessage());	
					} catch (IOException e) {
						LOG.error("[IOException] sendNotificationExpired failed " + e.getMessage());	
					}
				}		
				
				try {
					long minutes = checkMinutesBeforeExpired(booking.getBookingDate());
					if(minutes >= 105 && minutes < 120){
						/**
						 * Jika kurang 15 menit dari 2 jam maka akan di notif email
						 * sebelum kirim email cek dahulu apakah sudah pernah dikirim apa belum
						 */
						long diff = 120-minutes;
						String notif = emailNotif.get(booking.getEmail()+booking.getBookingCode());
						if(StringUtils.isEmpty(notif)){
							// belum pernah dikirim email
							sendNotificationExpired(booking, "akan expired dalam " + diff + " menit");
							EmailNotifVO emailNotifVO = new EmailNotifVO();
							emailNotifVO.setBookingCode(booking.getBookingCode());
							emailNotifVO.setIsSentEmail(1);
							emailNotif.put(booking.getEmail()+booking.getBookingCode(), mapper.writeValueAsString(emailNotifVO));
						}else{
							// cek apakah dengan kode booking yg sama atau tidak
							// jika tidak sama maka kirim notif lagi
							EmailNotifVO emailNotifVO = mapper.readValue(notif, EmailNotifVO.class);
							if(!emailNotifVO.getBookingCode().equalsIgnoreCase(booking.getBookingCode())){
								sendNotificationExpired(booking, "akan expired dalam " + diff + " menit");
								emailNotifVO = new EmailNotifVO();
								emailNotifVO.setBookingCode(booking.getBookingCode());
								emailNotifVO.setIsSentEmail(1);
								emailNotif.put(booking.getEmail()+booking.getBookingCode(), mapper.writeValueAsString(emailNotifVO));
							}
						}
					}
				} catch (EmailException e) {
					LOG.error("[EmailException] sendNotificationExpired failed " + e.getMessage());	
				} catch (IOException e) {
					LOG.error("[IOException] sendNotificationExpired failed " + e.getMessage());	
				} catch (Exception e) {
					LOG.error("[Exception] sendNotificationExpired failed " + e.getMessage());	
				}
				
			
		}
		LOG.info("release done");
	}
	
	/**
	 * Jika sudah 2 jam maka customer akan di beri notif ke email
	 * Jika sudah 15 menit sebelum 2 jam maka customer akan di beri notif ke email
	 * 
	 * @param booking
	 * @param expired
	 * @throws EmailException
	 * @throws IOException
	 */
	private void sendNotificationExpired(Booking booking,String expired)throws EmailException, IOException {
		String emailTo = booking.getEmail();
//		expired = "sudah expired";
		String emailSubject = Constants.APP_NAME+" : Pemberitahuan Pesanan Anda "+ expired;
		String txtMessage = "";
		
		StringBuilder sb = new StringBuilder();
		sb.append("Dear Mr/Mrs ").append(booking.getName()).append(",");
		sb.append("\n").append("\n");
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
				+ "<title>Informasi pesanan parkir anda sudah expired</title> "
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
				+ "				<table class=\"main\" width=\"100%\" cellpadding=\"0\" cellspacing=\"0\" itemprop=\"action\" itemscope itemtype=\"http://schema.org/ConfirmAction\" style=\"font-family: 'Helvetica Neue',Helvetica,Arial,sans-serif; box-sizing: border-box; font-size: 14px; border-radius: 3px; background-color: #fff; margin: 0; border: 1px solid #e9e9e9;\" bgcolor=\"#fff\"><tr style=\"font-family: 'Helvetica Neue',Helvetica,Arial,sans-serif; box-sizing: border-box; font-size: 14px; margin: 0;\"><td class=\"content-wrap\" style=\"font-family: 'Helvetica Neue',Helvetica,Arial,sans-serif; box-sizing: border-box; font-size: 14px; vertical-align: top; margin: 0; padding: 20px;\" valign=\"top\"> "
				+ "							<meta itemprop=\"name\" content=\"Confirm Email\" style=\"font-family: 'Helvetica Neue',Helvetica,Arial,sans-serif; box-sizing: border-box; font-size: 14px; margin: 0;\" /><table width=\"100%\" cellpadding=\"0\" cellspacing=\"0\" style=\"font-family: 'Helvetica Neue',Helvetica,Arial,sans-serif; box-sizing: border-box; font-size: 14px; margin: 0;\"><tr style=\"font-family: 'Helvetica Neue',Helvetica,Arial,sans-serif; box-sizing: border-box; font-size: 14px; margin: 0;\"><td class=\"content-block\" style=\"font-family: 'Helvetica Neue',Helvetica,Arial,sans-serif; box-sizing: border-box; font-size: 14px; vertical-align: top; margin: 0; padding: 0 0 0px;\" valign=\"top\"> "
				+ "										Yth Bapak/Ibu " + booking.getName()
				+ "									</td> "
				+ "								</tr><tr style=\"font-family: 'Helvetica Neue',Helvetica,Arial,sans-serif; box-sizing: border-box; font-size: 14px; margin: 0;\"><td class=\"content-block\" style=\"font-family: 'Helvetica Neue',Helvetica,Arial,sans-serif; box-sizing: border-box; font-size: 14px; vertical-align: top; margin: 0; padding: 0 0 20px;\" valign=\"top\"> "
				+ "										Terima kasih telah menggunakan Premium Parkir Solution untuk memesan valet parkir, kami beritahukan bahwa pesanan anda " + expired
				+ "									</td> "
				+ "								</tr><tr style=\"font-family: 'Helvetica Neue',Helvetica,Arial,sans-serif; box-sizing: border-box; font-size: 14px; margin: 0;\"><td class=\"content-block\" style=\"font-family: 'Helvetica Neue',Helvetica,Arial,sans-serif; box-sizing: border-box; font-size: 14px; vertical-align: top; margin: 0; padding: 0 0 20px;\" valign=\"top\"> "
				+ "										Berikut adalah informasi pemesanan anda: "
				+ "									</td> "
				+ "								</tr><tr style=\"font-family: 'Helvetica Neue',Helvetica,Arial,sans-serif; box-sizing: border-box; font-size: 14px; margin: 0;\"><td class=\"content-block\" style=\"font-family: 'Helvetica Neue',Helvetica,Arial,sans-serif; box-sizing: border-box; font-size: 14px; vertical-align: top; margin: 0; padding: 0 0 20px;\" valign=\"top\"> "
				+ "										<b>Nama customer</b> 			: " + booking.getName()
				+ "									</td> "
				+ "								</tr><tr style=\"font-family: 'Helvetica Neue',Helvetica,Arial,sans-serif; box-sizing: border-box; font-size: 14px; margin: 0;\"><td class=\"content-block\" style=\"font-family: 'Helvetica Neue',Helvetica,Arial,sans-serif; box-sizing: border-box; font-size: 14px; vertical-align: top; margin: 0; padding: 0 0 20px;\" valign=\"top\"> "
				+ "										<b>Booking Id</b>				: " + booking.getBookingId()
				+ "									</td> "
				+ "								</tr><tr style=\"font-family: 'Helvetica Neue',Helvetica,Arial,sans-serif; box-sizing: border-box; font-size: 14px; margin: 0;\"><td class=\"content-block\" style=\"font-family: 'Helvetica Neue',Helvetica,Arial,sans-serif; box-sizing: border-box; font-size: 14px; vertical-align: top; margin: 0; padding: 0 0 20px;\" valign=\"top\"> "
				+ "										<b>Booking Code</b>			: " + booking.getBookingCode()
				+ "									</td> "
				+ "								</tr><tr style=\"font-family: 'Helvetica Neue',Helvetica,Arial,sans-serif; box-sizing: border-box; font-size: 14px; margin: 0;\"><td class=\"content-block\" style=\"font-family: 'Helvetica Neue',Helvetica,Arial,sans-serif; box-sizing: border-box; font-size: 14px; vertical-align: top; margin: 0; padding: 0 0 20px;\" valign=\"top\"> "
				+ "										<b>Mall Name</b>				: " + booking.getMallName()
				+ "									</td> "
				+ "								</tr><tr style=\"font-family: 'Helvetica Neue',Helvetica,Arial,sans-serif; box-sizing: border-box; font-size: 14px; margin: 0;\"><td class=\"content-block\" style=\"font-family: 'Helvetica Neue',Helvetica,Arial,sans-serif; box-sizing: border-box; font-size: 14px; vertical-align: top; margin: 0; padding: 0 0 20px;\" valign=\"top\"> "
				+ "										<b>Waktu Transaksi</b>			: " + CommonUtil.displayDateTime(booking.getBookingDate())
				+ "									</td> "
				+ "								</tr></table></td> "
				+ "					</tr></table><div class=\"footer\" style=\"font-family: 'Helvetica Neue',Helvetica,Arial,sans-serif; box-sizing: border-box; font-size: 14px; width: 100%; clear: both; color: #999; margin: 0; padding: 20px;\"> "
				+ "					<table width=\"100%\" style=\"font-family: 'Helvetica Neue',Helvetica,Arial,sans-serif; box-sizing: border-box; font-size: 14px; margin: 0;\"><tr style=\"font-family: 'Helvetica Neue',Helvetica,Arial,sans-serif; box-sizing: border-box; font-size: 14px; margin: 0;\"><td class=\"aligncenter content-block\" style=\"font-family: 'Helvetica Neue',Helvetica,Arial,sans-serif; box-sizing: border-box; font-size: 12px; vertical-align: top; color: #999; text-align: center; margin: 0; padding: 0 0 20px;\" align=\"center\" valign=\"top\">Follow <a href=\"http://google.com\" style=\"font-family: 'Helvetica Neue',Helvetica,Arial,sans-serif; box-sizing: border-box; font-size: 12px; color: #999; text-decoration: underline; margin: 0;\">@premium_parking_solution</a> on Twitter.</td> "
				+ "						</tr></table></div></div> "
				+ "		</td> "
				+ "		<td style=\"font-family: 'Helvetica Neue',Helvetica,Arial,sans-serif; box-sizing: border-box; font-size: 14px; vertical-align: top; margin: 0;\" valign=\"top\"></td> "
				+ "	</tr></table></body> "
				+ "</html>";


		LOG.debug("sendNotificationExpired to " + emailTo);
		emailSender.sendHTMLMailNew("", "",
				new String[] { emailTo }, new String[] { booking.getName() },
				emailSubject, html, txtMessage, null);

	}
	
	private boolean isExpiredPay(Date bookDate){
		boolean hasil = false;
		Date now = timeService.getCurrentTime();
		LOG.info("isExpiredPay with param : " + " Booking Id Date: " + bookDate + " current time : " + now + " Setting : " + Constants.EXPIRED_PAY_IN_MINUTES + " minutes" );
		long minutes = CommonUtil.dateDifferentInMinutes(bookDate, now);
		LOG.info("different minutes : " + minutes);
		if(minutes>=Constants.EXPIRED_PAY_IN_MINUTES){
			LOG.warn("Expired Booking Id with setting : " + Constants.EXPIRED_PAY_IN_MINUTES + " minutes.");
			hasil = true;
		}		
		LOG.info("isExpiredPay Done with param : " + " Booking Id Date: " + bookDate + " current time : " + now );
		return hasil;
	}
	
	private boolean isExpiredCheckIn(Date bookDate){
		boolean hasil = false;
		Date now = timeService.getCurrentTime();
		LOG.info("isExpiredCheckIn with param : " + " BookingCodeDate: " + bookDate + " current time : " + now + " Setting : " + Constants.EXPIRED_BOOKING_CODE_IN_HOURS + " hours" );
		long hours = CommonUtil.dateDifferentInHours(bookDate, now);
		LOG.info("different hours : " + hours);
		if(hours>=Constants.EXPIRED_BOOKING_CODE_IN_HOURS){
			LOG.warn("Expired Booking Code with setting : " + Constants.EXPIRED_BOOKING_CODE_IN_HOURS + " hours.");
			hasil = true;
		}		
		LOG.info("isExpiredCheckIn Done with param : " + " BookingCodeDate: " + bookDate + " current time : " + now );
		return hasil;
	}
	
	private long checkMinutesBeforeExpired(Date bookDate){
		Date now = timeService.getCurrentTime();
		long minutes = CommonUtil.dateDifferentInMinutesOnly(bookDate, now);
		LOG.info("different minutes : " + minutes);
		return minutes;
	}
}
