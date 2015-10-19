package com.jakarta.software.web.data;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;

public class ViewPagingParamVO {
	// used for paging
	private int page;
	private int lastPage;
	private int viewCount;  // defines how many will be displayed / viewed
	private int rowStart;
	private int rowEnd;
	private int rowCount;  // defines how many total / all rows

	public int getPage() {
		return page;
	}
	public void setPage(int page) {
		this.page = page;
	}
	
	public int getRowStart() {
		return rowStart - 1;
	}
//	public void setRowStart(int rowStart) {
//		this.rowStart = rowStart;
//	}

	public int getRowEnd() {
		return rowEnd;
	}
//	public void setRowEnd(int rowEnd) {
//		this.rowEnd = rowEnd;
//	}

	public int getRowCount() {
		return rowCount;
	}
	public void setRowCount(int rowCount) {
		this.rowCount = rowCount;
	}
	public int getLastPage() {
		return lastPage;
	}
//	public void setLastPage(int lastPage) {
//		this.lastPage = lastPage;
//	}
	public int getViewCount() {
		return viewCount;
	}
	public void setViewCount(int viewCount) {
		this.viewCount = viewCount;
	}
	
	/**
	 * Must be called after setting page and rowCount
	 */
	public void calculate() {
		//TODO: should we use default if page / viewCount is not defined ?
		if (page == 0) page = 1;
		if (viewCount == 0) viewCount = 15;
		rowStart = ((page - 1) * viewCount);
		rowEnd = (page * viewCount) - 1;
		if (rowEnd >= rowCount) rowEnd = rowCount - 1;
		lastPage = (int) Math.floor(rowCount / viewCount);
		if (rowCount % viewCount > 0) lastPage++;
		rowStart++;
		rowEnd++;
	}
	
	public int[] getPages() {
		int[] pages = new int[lastPage];
		for (int i=0; i < lastPage; i++)
			pages[i] = i + 1;
		return pages;
	}
	
	@Override
	public String toString() {
		return ReflectionToStringBuilder.toString(this);
	}
}
