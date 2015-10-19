package com.jakarta.software.web.service;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jakarta.software.web.data.CifReportVO;
import com.jakarta.software.web.data.MerchantVO;
import com.jakarta.software.web.data.TerminatedCifReportVO;
import com.jakarta.software.web.data.TransactionReportVO;
import com.jakarta.software.web.data.param.MerchantParamVO;
import com.jakarta.software.web.data.param.ReportParamVO;
import com.jakarta.software.web.mapper.ReportMapper;

@Service
public class ReportService {
	private static final Logger LOGGER = LoggerFactory.getLogger(ReportService.class);
	
	@Autowired
	private ReportMapper reportMapper;
	
	public int countMerchantReportByParam(ReportParamVO paramVO) {
		LOGGER.debug("Processing -> countMerchantReportByParam: [{}]", paramVO);
		try {
			int count = reportMapper.countMerchantReportByParam(paramVO);
			return count;
		} catch (Exception e) {
			LOGGER.error("Error: " +e, e);
			return 0 ;
		}
	}
	
	public List<MerchantVO> findMerchantReportByParam(ReportParamVO paramVO) {
		LOGGER.debug("Processing -> findMerchantReportByParam: [{}]", paramVO);
		try {
			List<MerchantVO> listMerchant = reportMapper.findMerchantReportByParam(paramVO);
			return listMerchant;
		} catch (Exception e) {
			LOGGER.error("ERROR: " + e, e);
			return new ArrayList<MerchantVO>();
		}
	}
	
	public MerchantVO findMerchantById(int id) {
		LOGGER.debug("Processing -> findMerchantById: [{}]", id);
		try {
			MerchantVO merchant = reportMapper.findMerchantReportById(id);
			return merchant;
		} catch (Exception e) {
			LOGGER.error("ERROR: " + e, e);
			return new MerchantVO();
		}
	}
	
	public List<MerchantVO> findHistoryMerchantById(int id) {
		LOGGER.debug("Processing -> findHistoryMerchantById: [{}]", id);
		try {
			List<MerchantVO> listMerchant = reportMapper.findHistoryMerchantById(id);
			return listMerchant;
		} catch (Exception e) {
			LOGGER.error("ERROR: " + e, e);
			return new ArrayList<MerchantVO>();
		}
	}
	
	public List<MerchantVO> findMerchantReportByParamNoPaging(ReportParamVO paramVO) {
		LOGGER.debug("Processing -> findMerchantReportByParam: [{}]", paramVO);
		try {
			List<MerchantVO> listMerchant = reportMapper.findMerchantReportByParam(paramVO);
			return listMerchant;
		} catch (Exception e) {
			LOGGER.error("ERROR: " + e, e);
			return new ArrayList<MerchantVO>();
		}
	}
	
	public int countCifReportByParam(ReportParamVO paramVO) {
		LOGGER.debug("Processing -> countCifReportByParam: [{}]", paramVO);
		try {
			int count = reportMapper.countCifByParam(paramVO);
			return count;
		} catch (Exception e) {
			LOGGER.error("Error: " +e, e);
			return 0 ;
		}
	}
	
	public List<CifReportVO> findCifReportByParam(ReportParamVO paramVO) {
		LOGGER.debug("Processing -> findCifReportByParam: [{}]", paramVO);
		try {
			List<CifReportVO> listCif = reportMapper.findCifByParam(paramVO);
			return listCif;
		} catch (Exception e) {
			LOGGER.error("ERROR: " + e, e);
			return new ArrayList<CifReportVO>();
		}
	}
	
	public List<CifReportVO> findCifByParamNoPaging(ReportParamVO paramVO) {
		LOGGER.debug("Processing -> findCifByParamNoPaging: [{}]", paramVO);
		try {
			List<CifReportVO> listCif = reportMapper.findCifByParamNoPaging(paramVO);
			return listCif;
		} catch (Exception e) {
			LOGGER.error("ERROR: " + e, e);
			return new ArrayList<CifReportVO>();
		}
	}
	
	public int countAirtimeRefillReportByParam(ReportParamVO paramVO) {
		LOGGER.debug("Processing -> countAirtimeRefillReportByParam: [{}]", paramVO);
		try {
			int count = reportMapper.countAirtimeRefillByParam(paramVO);
			return count;
		} catch (Exception e) {
			LOGGER.error("Error: " +e, e);
			return 0 ;
		}
	}
	
	public List<TransactionReportVO> findAirtimeRefillByParam(ReportParamVO paramVO) {
		LOGGER.debug("Processing -> findAirtimeRefillReportByParam: [{}]", paramVO);
		try {
			List<TransactionReportVO> listTrx = reportMapper.findAirtimeRefillByParam(paramVO);
			return listTrx;
		} catch (Exception e) {
			LOGGER.error("ERROR: " + e, e);
			return new ArrayList<TransactionReportVO>();
		}
	}
	
	public List<TransactionReportVO> findAirtimeRefillByParamNoPaging(ReportParamVO paramVO) {
		LOGGER.debug("Processing -> findAirtimeRefillByParamNoPaging: [{}]", paramVO);
		try {
			List<TransactionReportVO> listTrx = reportMapper.findAirtimeRefillByParamNoPaging(paramVO);
			return listTrx;
		} catch (Exception e) {
			LOGGER.error("ERROR: " + e, e);
			return new ArrayList<TransactionReportVO>();
		}
	}
	
	public int countFundTransferReportByParam(ReportParamVO paramVO) {
		LOGGER.debug("Processing -> countFundTransferReportByParam: [{}]", paramVO);
		try {
			int count = reportMapper.countFundTransferByParam(paramVO);
			return count;
		} catch (Exception e) {
			LOGGER.error("Error: " +e, e);
			return 0 ;
		}
	}
	
	public List<TransactionReportVO> findFundTransferByParam(ReportParamVO paramVO) {
		LOGGER.debug("Processing -> findFundTransferByParam: [{}]", paramVO);
		try {
			List<TransactionReportVO> listTrx = reportMapper.findFundTransferByParam(paramVO);
			return listTrx;
		} catch (Exception e) {
			LOGGER.error("ERROR: " + e, e);
			return new ArrayList<TransactionReportVO>();
		}
	}
	
	public List<TransactionReportVO> findFundTransferByParamNoPaging(ReportParamVO paramVO) {
		LOGGER.debug("Processing -> findFundTransferByParamNoPaging: [{}]", paramVO);
		try {
			List<TransactionReportVO> listTrx = reportMapper.findFundTransferByParamNoPaging(paramVO);
			return listTrx;
		} catch (Exception e) {
			LOGGER.error("ERROR: " + e, e);
			return new ArrayList<TransactionReportVO>();
		}
	}
	
	public int countBillPaymentReportByParam(ReportParamVO paramVO) {
		LOGGER.debug("Processing -> countBillPaymentReportByParam: [{}]", paramVO);
		try {
			int count = reportMapper.countBillPaymentByParam(paramVO);
			return count;
		} catch (Exception e) {
			LOGGER.error("Error: " +e, e);
			return 0 ;
		}
	}
	
	public List<TransactionReportVO> findBillPaymentByParam(ReportParamVO paramVO) {
		LOGGER.debug("Processing -> findBillPaymentByParam: [{}]", paramVO);
		try {
			List<TransactionReportVO> listTrx = reportMapper.findBillPaymentByParam(paramVO);
			return listTrx;
		} catch (Exception e) {
			LOGGER.error("ERROR: " + e, e);
			return new ArrayList<TransactionReportVO>();
		}
	}
	
	public List<TransactionReportVO> findBillPaymentByParamNoPaging(ReportParamVO paramVO) {
		LOGGER.debug("Processing -> findBillPaymentByParamNoPaging: [{}]", paramVO);
		try {
			List<TransactionReportVO> listTrx = reportMapper.findBillPaymentByParamNoPaging(paramVO);
			return listTrx;
		} catch (Exception e) {
			LOGGER.error("ERROR: " + e, e);
			return new ArrayList<TransactionReportVO>();
		}
	}
	
	public int countMatmReportByParam(ReportParamVO paramVO) {
		LOGGER.debug("Processing -> countMatmReportByParam: [{}]", paramVO);
		try {
			int count = reportMapper.countMatmPurchaseByParam(paramVO);
			return count;
		} catch (Exception e) {
			LOGGER.error("Error: " +e, e);
			return 0 ;
		}
	}
	
	public List<TransactionReportVO> findMatmByParam(ReportParamVO paramVO) {
		LOGGER.debug("Processing -> findMatmByParam: [{}]", paramVO);
		try {
			List<TransactionReportVO> listTrx = reportMapper.findMatmPurchaseByParam(paramVO);
			return listTrx;
		} catch (Exception e) {
			LOGGER.error("ERROR: " + e, e);
			return new ArrayList<TransactionReportVO>();
		}
	}
	
	public List<TransactionReportVO> findMatmByParamNoPaging(ReportParamVO paramVO) {
		LOGGER.debug("Processing -> findMatmByParamNoPaging: [{}]", paramVO);
		try {
			List<TransactionReportVO> listTrx = reportMapper.findMatmPurchaseByParamNoPaging(paramVO);
			return listTrx;
		} catch (Exception e) {
			LOGGER.error("ERROR: " + e, e);
			return new ArrayList<TransactionReportVO>();
		}
	}
	
	public int countBlastSmsByParam(ReportParamVO paramVO) {
		LOGGER.debug("Processing -> countBlastSmsByParam: [{}]", paramVO);
		try {
			int count = reportMapper.countBlastSmsByParam(paramVO);
			return count;
		} catch (Exception e) {
			LOGGER.error("Error: " +e, e);
			return 0 ;
		}
	}
	
	public List<TransactionReportVO> findBlastSmsByParam(ReportParamVO paramVO) {
		LOGGER.debug("Processing -> findBlastSmsByParam: [{}]", paramVO);
		try {
			List<TransactionReportVO> listTrx = reportMapper.findBlastSmsByParam(paramVO);
			return listTrx;
		} catch (Exception e) {
			LOGGER.error("ERROR: " + e, e);
			return new ArrayList<TransactionReportVO>();
		}
	}
	
	public List<TransactionReportVO> findBlastSmsByParamNoPaging(ReportParamVO paramVO) {
		LOGGER.debug("Processing -> findBlastSmsByParamNoPaging: [{}]", paramVO);
		try {
			List<TransactionReportVO> listTrx = reportMapper.findBlastSmsByParamNoPaging(paramVO);
			return listTrx;
		} catch (Exception e) {
			LOGGER.error("ERROR: " + e, e);
			return new ArrayList<TransactionReportVO>();
		}
	}
	
	public int countTerminatedCifReportByParam(ReportParamVO paramVO) {
		LOGGER.debug("Processing -> countTerminatedCifReportByParam: [{}]", paramVO);
		try {
			int count = reportMapper.countTerminatedCifByParam(paramVO);
			return count;
		} catch (Exception e) {
			LOGGER.error("Error: " +e, e);
			return 0 ;
		}
	}
	
	public List<TerminatedCifReportVO> findTerminatedCifByParam(ReportParamVO paramVO) {
		LOGGER.debug("Processing -> findTerminatedCifByParam: [{}]", paramVO);
		try {
			List<TerminatedCifReportVO> listTrx = reportMapper.findTerminatedCifByParam(paramVO);
			return listTrx;
		} catch (Exception e) {
			LOGGER.error("ERROR: " + e, e);
			return new ArrayList<TerminatedCifReportVO>();
		}
	}
	
	public List<TerminatedCifReportVO> findTerminatedCifByParamNoPaging(ReportParamVO paramVO) {
		LOGGER.debug("Processing -> findTerminatedCifByParamNoPaging: [{}]", paramVO);
		try {
			List<TerminatedCifReportVO> listTrx = reportMapper.findTerminatedCifByParamNoPaging(paramVO);
			return listTrx;
		} catch (Exception e) {
			LOGGER.error("ERROR: " + e, e);
			return new ArrayList<TerminatedCifReportVO>();
		}
	}
	
	public List<TerminatedCifReportVO> findTerminatedCifDetail(int cifId) {
		LOGGER.debug("Processing -> findTerminatedCifDetail with cifId :" + cifId);
		try {
			List<TerminatedCifReportVO> listTrx = reportMapper.findTerminatedCifDetail(cifId);
			return listTrx;
		} catch (Exception e) {
			LOGGER.error("ERROR: " + e, e);
			return new ArrayList<TerminatedCifReportVO>();
		}
	}
	
	public int countAuditTrailAccountReportByParam(ReportParamVO paramVO) {
		LOGGER.debug("Processing -> countAuditTrailAccountReportByParam: [{}]", paramVO);
		try {
			int count = reportMapper.countTerminatedCifByParam(paramVO);
			return count;
		} catch (Exception e) {
			LOGGER.error("Error: " +e, e);
			return 0 ;
		}
	}
	
	public List<CifReportVO> findAuditTrailAccountByParam(ReportParamVO paramVO) {
		LOGGER.debug("Processing -> findAuditTrailAccountByParam: [{}]", paramVO);
		try {
			List<CifReportVO> listAuditTrailAccount = reportMapper.findAuditTrailAccountByParam(paramVO);
			return listAuditTrailAccount;
		} catch (Exception e) {
			LOGGER.error("ERROR: " + e, e);
			return new ArrayList<CifReportVO>();
		}
	}
	
	public List<CifReportVO> findAuditTrailByParamNoPaging(ReportParamVO paramVO) {
		LOGGER.debug("Processing -> findAuditTrailByParamNoPaging: [{}]", paramVO);
		try {
			List<CifReportVO> listAuditTrailAccount = reportMapper.findAuditTrailAccountByParamNoPaging(paramVO);
			return listAuditTrailAccount;
		} catch (Exception e) {
			LOGGER.error("ERROR: " + e, e);
			return new ArrayList<CifReportVO>();
		}
	}
	
	public int countSummaryTransactionReportByParam(ReportParamVO paramVO) {
		LOGGER.debug("Processing -> countSummaryTransactionReportByParam: [{}]", paramVO);
		try {
			int count = reportMapper.countTerminatedCifByParam(paramVO);
			return count;
		} catch (Exception e) {
			LOGGER.error("Error: " +e, e);
			return 0 ;
		}
	}
	
	public List<TransactionReportVO> findSummaryTransactionByParam(ReportParamVO paramVO) {
		LOGGER.debug("Processing -> findSummaryTransactionByParam: [{}]", paramVO);
		try {
			List<TransactionReportVO> listSummaryTransaction = reportMapper.findSummaryTransactionByParam(paramVO);
			return listSummaryTransaction;
		} catch (Exception e) {
			LOGGER.error("ERROR: " + e, e);
			return new ArrayList<TransactionReportVO>();
		}
	}
	
	public List<TransactionReportVO> findSummaryTransactionByParamNoPaging(ReportParamVO paramVO) {
		LOGGER.debug("Processing -> findSummaryTransactionByParamNoPaging: [{}]", paramVO);
		try {
			List<TransactionReportVO> listSummaryTransaction = reportMapper.findSummaryTransactionByParamNoPaging(paramVO);
			return listSummaryTransaction;
		} catch (Exception e) {
			LOGGER.error("ERROR: " + e, e);
			return new ArrayList<TransactionReportVO>();
		}
	}
}
