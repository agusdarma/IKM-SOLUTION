package com.jakarta.software.web.mapper;

import java.util.List;

import com.jakarta.software.web.data.CifReportVO;
import com.jakarta.software.web.data.MerchantVO;
import com.jakarta.software.web.data.TerminatedCifReportVO;
import com.jakarta.software.web.data.TransactionReportVO;
import com.jakarta.software.web.data.param.ReportParamVO;

public interface ReportMapper {
	
	public List<MerchantVO> findMerchantReportByParam(ReportParamVO paramVO);
	
	public List<MerchantVO> findMerchantReportByParamNoPaging(ReportParamVO paramVO);
	
	public int countMerchantReportByParam(ReportParamVO paramVO);

	public MerchantVO findMerchantReportById(int id);
	
	public List<MerchantVO> findHistoryMerchantById(int id);
	
	public int countCifByParam(ReportParamVO paramVO);
	public List<CifReportVO> findCifByParam(ReportParamVO paramVO);
	public List<CifReportVO> findCifByParamNoPaging(ReportParamVO paramVO);
	
	public int countAirtimeRefillByParam(ReportParamVO paramVO);
	public List<TransactionReportVO> findAirtimeRefillByParam(ReportParamVO paramVO);
	public List<TransactionReportVO> findAirtimeRefillByParamNoPaging(ReportParamVO paramVO);
	
	public int countMatmPurchaseByParam(ReportParamVO paramVO);
	public List<TransactionReportVO> findMatmPurchaseByParam(ReportParamVO paramVO);
	public List<TransactionReportVO> findMatmPurchaseByParamNoPaging(ReportParamVO paramVO);
	
	public int countFundTransferByParam(ReportParamVO paramVO);
	public List<TransactionReportVO> findFundTransferByParam(ReportParamVO paramVO);
	public List<TransactionReportVO> findFundTransferByParamNoPaging(ReportParamVO paramVO);

	public int countBillPaymentByParam(ReportParamVO paramVO);
	public List<TransactionReportVO> findBillPaymentByParam(ReportParamVO paramVO);
	public List<TransactionReportVO> findBillPaymentByParamNoPaging(ReportParamVO paramVO);
	
	public int countBlastSmsByParam(ReportParamVO paramVO);
	public List<TransactionReportVO> findBlastSmsByParam(ReportParamVO paramVO);
	public List<TransactionReportVO> findBlastSmsByParamNoPaging(ReportParamVO paramVO);

	public int countTerminatedCifByParam(ReportParamVO paramVO);
	public List<TerminatedCifReportVO> findTerminatedCifByParam(ReportParamVO paramVO);
	public List<TerminatedCifReportVO> findTerminatedCifByParamNoPaging(ReportParamVO paramVO);
	public List<TerminatedCifReportVO> findTerminatedCifDetail(int cifId);

	public int countAuditTrailAccountByParam(ReportParamVO paramVO);
	public List<CifReportVO> findAuditTrailAccountByParam(ReportParamVO paramVO);
	public List<CifReportVO> findAuditTrailAccountByParamNoPaging(ReportParamVO paramVO);

	public int countSummaryTransactionByParam(ReportParamVO paramVO);
	public List<TransactionReportVO> findSummaryTransactionByParam(ReportParamVO paramVO);
	public List<TransactionReportVO> findSummaryTransactionByParamNoPaging(ReportParamVO paramVO);
	
	
}
