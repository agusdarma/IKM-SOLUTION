package com.myproject.parking.lib.mapper;

import org.apache.ibatis.annotations.Param;

import com.myproject.parking.lib.entity.TransactionVO;

public interface TransactionMapper {
	public void createTransaction(TransactionVO transactionVO);
	public void updateTransactionStatus(@Param("PaymentTransactionId") String PaymentTransactionId
			,@Param("PaymentFdsStatus") String PaymentFdsStatus
			,@Param("PaymentStatus") String PaymentStatus,@Param("orderId") String orderId);
	public void updateEmailNotification(@Param("emailNotification") int emailNotification,@Param("emailNotificationReason") String emailNotificationReason,
			@Param("orderId") String orderId);
    
}
