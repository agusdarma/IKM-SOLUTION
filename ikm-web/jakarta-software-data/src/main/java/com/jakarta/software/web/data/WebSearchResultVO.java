package com.jakarta.software.web.data;

import java.util.List;

public class WebSearchResultVO {

	private String title; //0 success sisanya reject
	private String header;
	private String body; //0 insert sisanya update
	private List<LinkTableVO> linkTable;
	private String bodyContent; //isinya json karena entity itu beda2 ga mungkin gw taruh sini semuanya
	private String sortVariable;
	private String chosenSortVariable;
	private String footerPage;
	private String footerRecord;
	private String panelRowPerPage;
	private int currentPage;
	private int rowPerPage;
	private int totalRow;
	private int totalPage;
	private int rowStart;
	private int rowEnd;
	private int panelPosition;
	private int sortOrder;
	private boolean useExport;
	
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getHeader() {
		return header;
	}
	public void setHeader(String header) {
		this.header = header;
	}
	public String getBody() {
		return body;
	}
	public void setBody(String body) {
		this.body = body;
	}
	public String getBodyContent() {
		return bodyContent;
	}
	public void setBodyContent(String bodyContent) {
		this.bodyContent = bodyContent;
	}
	public int getCurrentPage() {
		return currentPage;
	}
	public void setCurrentPage(int currentPage) {
		this.currentPage = currentPage;
	}
	public int getRowPerPage() {
		return rowPerPage;
	}
	public void setRowPerPage(int rowPerPage) {
		this.rowPerPage = rowPerPage;
	}
	public int getTotalRow() {
		return totalRow;
	}
	public void setTotalRow(int totalRow) {
		this.totalRow = totalRow;
	}
	public int getTotalPage() {
		return totalPage;
	}
	public void setTotalPage(int totalPage) {
		this.totalPage = totalPage;
	}
	public int getRowStart() {
		return rowStart;
	}
	public void setRowStart(int rowStart) {
		this.rowStart = rowStart;
	}
	public int getRowEnd() {
		return rowEnd;
	}
	public void setRowEnd(int rowEnd) {
		this.rowEnd = rowEnd;
	}
	public String getFooterPage() {
		return footerPage;
	}
	public void setFooterPage(String footerPage) {
		this.footerPage = footerPage;
	}
	public String getFooterRecord() {
		return footerRecord;
	}
	public void setFooterRecord(String footerRecord) {
		this.footerRecord = footerRecord;
	}
	public int getPanelPosition() {
		return panelPosition;
	}
	public void setPanelPosition(int panelPosition) {
		this.panelPosition = panelPosition;
	}
	public String getPanelRowPerPage() {
		return panelRowPerPage;
	}
	public void setPanelRowPerPage(String panelRowPerPage) {
		this.panelRowPerPage = panelRowPerPage;
	}
	public List<LinkTableVO> getLinkTable() {
		return linkTable;
	}
	public void setLinkTable(List<LinkTableVO> linkTable) {
		this.linkTable = linkTable;
	}
	public String getSortVariable() {
		return sortVariable;
	}
	public void setSortVariable(String sortVariable) {
		this.sortVariable = sortVariable;
	}
	public int getSortOrder() {
		return sortOrder;
	}
	public void setSortOrder(int sortOrder) {
		this.sortOrder = sortOrder;
	}
	public String getChosenSortVariable() {
		return chosenSortVariable;
	}
	public void setChosenSortVariable(String chosenSortVariable) {
		this.chosenSortVariable = chosenSortVariable;
	}
	public boolean isUseExport() {
		return useExport;
	}
	public void setUseExport(boolean useExport) {
		this.useExport = useExport;
	}
	
}
