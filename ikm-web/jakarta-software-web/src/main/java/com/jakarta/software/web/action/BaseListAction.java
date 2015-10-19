package com.jakarta.software.web.action;

import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;

import com.jakarta.software.web.data.WebConstants;
import com.jakarta.software.web.data.param.ParamPagingVO;
import com.jakarta.software.web.service.WebSearchResultService;

public abstract class BaseListAction extends BaseAction {
	private static final long serialVersionUID = 1L;
	
	@Autowired
	protected WebSearchResultService webSearchResultService;

	protected ParamPagingVO paramVO;
	
	protected int currentPage;
	
	protected int tableActivity;
	protected int rowModifier;
	protected String sortVariable;
	protected int sortActivity;
	
	public abstract ParamPagingVO getParamVO();
	
	protected void prepareParamVO(ParamPagingVO newPagingVO, String sessionName, String defaultSortVariable, String defaultSortOrder)
	{
		if(paramVO==null)
		{
			paramVO = newPagingVO;
		}
		paramVO.setSortVariable(defaultSortVariable);
		paramVO.setSortOrder(defaultSortOrder);

		if(tableActivity==1)//navigate
		{
			paramVO = newPagingVO;
			Object obj = session.get(sessionName);
			paramVO = (ParamPagingVO) obj;
			paramVO.setRowPerPage(rowModifier);
			if(sortActivity==1)
			{
				if(sortVariable.equals(paramVO.getSortVariable()))
				{
					if(paramVO.getSortOrder().equals(WebConstants.SORT_ORDER_ASC))
					{
						paramVO.setSortOrder(WebConstants.SORT_ORDER_DESC);
					}
					else
					{
						paramVO.setSortOrder(WebConstants.SORT_ORDER_ASC);
					}
				}
				else
				{
					paramVO.setSortOrder(WebConstants.SORT_ORDER_ASC);
				}
				paramVO.setSortVariable(sortVariable);				
			}
		}
		HashMap<String, Integer> rows=webSearchResultService.calculatePaging(getCurrentPage(), paramVO.getRowPerPage());
		paramVO.setRowStart(rows.get("rowStart"));
		paramVO.setRowEnd(rows.get("rowEnd"));
		session.put(sessionName, paramVO);
	}
	
//	public ParamPagingVO getParamVO() {
//		return paramVO;
//	}
//	public void setParamVO(ParamPagingVO paramVO) {
//		this.paramVO = paramVO;
//	}

	public int getTableActivity() {
		return tableActivity;
	}
	public void setTableActivity(int tableActivity) {
		this.tableActivity = tableActivity;
	}

	public int getRowModifier() {
		return rowModifier;
	}
	public void setRowModifier(int rowModifier) {
		this.rowModifier = rowModifier;
	}
	
	public String getSortVariable() {
		return sortVariable;
	}
	public void setSortVariable(String sortVariable) {
		this.sortVariable = sortVariable;
	}

	public int getSortActivity() {
		return sortActivity;
	}
	public void setSortActivity(int sortActivity) {
		this.sortActivity = sortActivity;
	}

	public int getCurrentPage() {
		if(currentPage==0)
		{
			currentPage=1;
		}
		return currentPage;
	}
	public void setCurrentPage(int currentPage) {
		this.currentPage = currentPage;
	}
	
}
