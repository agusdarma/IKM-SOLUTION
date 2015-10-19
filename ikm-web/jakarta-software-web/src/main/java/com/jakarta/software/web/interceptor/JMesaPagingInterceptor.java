package com.jakarta.software.web.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.struts2.StrutsStatics;
import org.jmesa.limit.ExportType;
import org.jmesa.model.Struts2TableModel;
import org.jmesa.model.TableModel;
import org.jmesa.model.TableModelUtils;
import org.jmesa.view.component.Column;
import org.jmesa.view.component.Row;
import org.jmesa.view.component.Table;
import org.jmesa.view.editor.CellEditor;
import org.jmesa.view.html.component.HtmlColumn;
import org.jmesa.view.html.component.HtmlRow;
import org.jmesa.view.html.component.HtmlTable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.AbstractInterceptor;

public class JMesaPagingInterceptor extends AbstractInterceptor {
	private static final long serialVersionUID = 1L;
	
	private static Logger LOGGER = LoggerFactory.getLogger(JMesaPagingInterceptor.class);
	
	@Override
	public String intercept(ActionInvocation invocation) throws Exception {
		final Object action = invocation.getAction();
        final ActionContext context = invocation.getInvocationContext();

        if (action instanceof JMesaPagingAware) {
        	JMesaPagingAware limitAware = (JMesaPagingAware) action;
        	
        	if (StringUtils.isEmpty(limitAware.getTableId())) 
        		return invocation.invoke();
        	
        	LOGGER.debug("JMesaLimit Interceptor for Table Id: {}", limitAware.getTableId());
        	HttpServletRequest request = (HttpServletRequest) context.get(StrutsStatics.HTTP_REQUEST);
            HttpServletResponse response = (HttpServletResponse) context.get(StrutsStatics.HTTP_RESPONSE);

            TableModel tableModel = new Struts2TableModel(limitAware.getTableId(), request, response);
            if (tableModel.isExporting()) {
            	limitAware.setExport(false);
            	LOGGER.debug("Exporting JMesa Table in {} with Id: {}", 
            			limitAware.getClass().getName(), limitAware.getTableId());
            	TableJMesaExportVO exportVO = new TableJMesaExportVO();
            	limitAware.setTableJMesaExport(exportVO);
            	if (!exportVO.isEmpty()) {
            		Table table = null;
            		if (tableModel.getExportType() == ExportType.PDF) {
            			table = new HtmlTable().caption(exportVO.getCaption());
            			HtmlRow row = new HtmlRow();
            			table.setRow(row);
            			String[] columns = exportVO.getColumns();
            			for (String column: columns) {
                			HtmlColumn col = new HtmlColumn(column).title(exportVO.getTitleFor(column));
                			CellEditor editor = exportVO.getCellEditorFor(column);
                			if (editor != null)
                				col = col.cellEditor(editor);
                		    row.addColumn(col);
            			}  // end for -> looping column
            		} else {
            			table = new Table().caption(exportVO.getCaption());
            			Row row = new Row();
            			table.setRow(row);
            			String[] columns = exportVO.getColumns();
            			for (String column: columns) {
                			Column col = new Column(column).title(exportVO.getTitleFor(column));
                			CellEditor editor = exportVO.getCellEditorFor(column);
                			if (editor != null)
                				col = col.cellEditor(editor);
                		    row.addColumn(col);
            			}  // end for -> looping column
            		}
            		tableModel.setItems(limitAware);
                	tableModel.setTable(table);
	            	tableModel.render();
            	} else {
            		LOGGER.warn("No Table for Exporting JMESA for Id: {}", limitAware.getTableId());
            	}
            	// output has been sent to browser, so tell STRUTS that no further processing is required
            	return null;
            } else {
            	limitAware.setExport(false);
//            	Collection<?> items = 
            	TableModelUtils.getItems(limitAware.getTableId(), request, limitAware);
            	//limitAware.setDataJMesa(items);
            }

        }  // end if JMesaLimitAware
        
		return invocation.invoke();
	}

}
