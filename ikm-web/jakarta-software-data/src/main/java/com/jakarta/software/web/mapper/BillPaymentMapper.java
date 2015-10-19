package com.jakarta.software.web.mapper;

import java.util.List;

import com.jakarta.software.web.data.BillPaymentVO;
import com.jakarta.software.web.data.param.BillPaymentParamVO;
import com.jakarta.software.web.entity.BillPayment;

public interface BillPaymentMapper {
	public int countBillPaymentByParam(BillPaymentParamVO paramVO);
	public List<BillPaymentVO> findBillPaymentByParam(BillPaymentParamVO paramVO);
	public BillPaymentVO findBillPaymentById(int id);
	public BillPayment findBillPaymentByBillerNo(String bpCode);
	public void insertBillPayment(BillPayment billPayment);
	public void updateBillPayment(BillPayment billPayment);
	public List<BillPayment> selectAllBillPayment();
}
