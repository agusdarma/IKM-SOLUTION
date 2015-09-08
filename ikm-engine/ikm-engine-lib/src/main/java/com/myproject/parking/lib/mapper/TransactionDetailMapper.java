package com.myproject.parking.lib.mapper;

import org.apache.ibatis.annotations.Param;

import com.myproject.parking.lib.entity.TransactionDetailVO;

public interface TransactionDetailMapper {
	public void createTransactionDetail(TransactionDetailVO transactionDetailVO);
	public void updateMallSlotStatus(@Param("idSlot") int idSlot);
}
