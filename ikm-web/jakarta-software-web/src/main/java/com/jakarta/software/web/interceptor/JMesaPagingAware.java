package com.jakarta.software.web.interceptor;

import org.jmesa.model.PageItems;

public interface JMesaPagingAware extends PageItems {
	
	// get table id for JMesa Table, indicates that table is being processed
	public String getTableId();
	
	// set table export jmesa
	public void setTableJMesaExport(TableJMesaExportVO exportVO);
	
	// set flag to indicate that jmesa is exporting data
	public void setExport(boolean isExport);
	
}
