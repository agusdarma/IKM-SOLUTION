package com.jakarta.software.web.mapper;

import java.util.List;

import com.jakarta.software.web.data.TrxLogBrowserVO;
import com.jakarta.software.web.data.param.TrxLogBrowserParamVO;

public interface TrxLogBrowserMapper {
	public int countTrxLogBrowserByParam(TrxLogBrowserParamVO paramVO);
	public List<TrxLogBrowserVO> findTrxLogBrowserByParam(TrxLogBrowserParamVO paramVO);
	public List<TrxLogBrowserVO> findTrxLogBrowserByParamNoPaging(TrxLogBrowserParamVO paramVO);
	public TrxLogBrowserVO findTrxLogBrowserDetailBySyslogno(String syslogno);
}
