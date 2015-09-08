package com.myproject.parking.lib.data;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;

public abstract class ParamPagingVO implements java.io.Serializable {
	private static final long serialVersionUID = 1L;
	private int rowStart;
	private int rowEnd;
	private List<ParamOrderVO> orders;
	
//	protected abstract String getRealFieldName(String fieldName);
	protected abstract String getPrimaryKey();
	protected abstract String getAliasTable();
	
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
	
	public int getRowCount() {
		int count = rowEnd - rowStart;
		if (count < 0) count = 0;
		return count;
	}

	public void clearOrder() {
		getOrders().clear();
	}
	public void addOrder(ParamOrderVO order) {
		for (ParamOrderVO o: getOrders()) {
			if (o.getParamField().equals(order.getParamField())) {
				o.setAsc(order.isAsc());
				return;
			}
		}
		getOrders().add(order);
	}
	public void removeOrder(String paramField) {
		for (ParamOrderVO o: getOrders()) {
			if (o.getParamField().equals(paramField)) {
				getOrders().remove(o);
				return;
			}
		}
	}
	public void addOrder(String name, boolean isAsc) {
		ParamOrderVO order = new ParamOrderVO();
		order.setAsc(isAsc);
		order.setParamField(name);
		addOrder(order);
	}
	public void removeAllOrder() {
		getOrders().clear();
	}
	public List<ParamOrderVO> getOrders() {
		if (orders == null)
			orders = new ArrayList<ParamOrderVO>();
		return orders;
	}
	
	public String getParamOrder() {
		if (orders == null || orders.size() == 0) {
			return "order by " + getPrimaryKey() + " asc";
		}
		StringBuilder sb = new StringBuilder();
		sb.append("order by ");
		for (ParamOrderVO order: orders) {
			// paramField is in camelCase, convert it to under_score
			String field = order.getParamField();
			String norm = getRealFieldName(field); 
			sb.append(norm);
			if (order.isAsc())
				sb.append(" asc");
			else
				sb.append(" desc");
			sb.append(",");
		}
		sb.deleteCharAt(sb.length() -1);
		return sb.toString();
	}
	
	protected String getRealFieldName(String fieldName) {
		// get real field name
		String s = fieldName;
		// if (fieldName.endsWith("Display"))
		// s = fieldName.substring(0, fieldName.length() - "Display".length());
		// else if (fieldName.equals("updated_on") ||
		// fieldName.equals("created_on"))
		// s = "r." + fieldName;
		// convert camelCase to under_score
		return s;// CommonUtils.convertCamelCaseToUnderScore(s);
	}
	
	@Override
	public String toString() {
		return ReflectionToStringBuilder.toString(this);
	}

}
