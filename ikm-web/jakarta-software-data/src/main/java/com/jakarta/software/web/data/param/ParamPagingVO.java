package com.jakarta.software.web.data.param;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;

import com.jakarta.software.web.data.WebConstants;

public abstract class ParamPagingVO {
	private int rowStart;
	private int rowEnd;
	private int rowPerPage;
	private int rowStartMysql;
	public int getRowStartMysql() {
		rowStartMysql = rowStart-1;
		return rowStartMysql;
	}

	public void setRowStartMysql(int rowStartMysql) {
		this.rowStartMysql = rowStartMysql;
	}
	private String sortVariable;
	private String sortOrder;
	private List<ParamOrderVO> orders;
		
	protected abstract String getPrimaryKey();

//	protected abstract String getAliasTable();

	public int getRowStart() {
		return rowStart;
	}

	public String getSortVariable() {
		return sortVariable;
	}

	public void setSortVariable(String sortVariable) {
		this.sortVariable = sortVariable;
	}

	public String getSortOrder() {
		return sortOrder;
	}

	public void setSortOrder(String sortOrder) {
		this.sortOrder = sortOrder;
	}

	public int getRowPerPage() {
		if(rowPerPage==0)
		{
			rowPerPage=WebConstants.DEFAULT_ROW_PER_PAGE;
		}
		return rowPerPage;
	}

	public void setRowPerPage(int rowPerPage) {
		this.rowPerPage = rowPerPage;
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
		if (count < 0)
			count = 0;
		return count;
	}

	public void clearOrder() {
		getOrders().clear();
	}

	public void addOrder(ParamOrderVO order) {
		for (ParamOrderVO o : getOrders()) {
			if (o.getParamField().equals(order.getParamField())) {
				o.setAsc(order.isAsc());
				return;
			}
		}
		getOrders().add(order);
	}

	public void removeOrder(String paramField) {
		for (ParamOrderVO o : getOrders()) {
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
			return "order by " + getPrimaryKey() + " desc";
		}
		StringBuilder sb = new StringBuilder();
		sb.append("order by ");
		for (ParamOrderVO order : orders) {
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
		sb.deleteCharAt(sb.length() - 1);
		return sb.toString();
	}

//	public String getParamOrderJmesaCamelCaseToUnderScore() {
//		if (orders == null || orders.size() == 0) {
//			if (!getPrimaryKey().equalsIgnoreCase("moName")
//					&& !getPrimaryKey().equalsIgnoreCase("appsId")
//					&& !getPrimaryKey().equalsIgnoreCase("dateConfirmAwc")
//					&& !getPrimaryKey().equalsIgnoreCase("lastLogin")) {
//				return getAliasTable()
//						+ getRealFieldNameCamelCaseToUnderScore(getPrimaryKey())
//						+ " desc";
//			} else {
//				return getRealFieldNameCamelCaseToUnderScore(getPrimaryKey())
//						+ " desc";
//			}
//
//		}
//		StringBuilder sb = new StringBuilder();
//		// sb.append("order by ");
//		for (ParamOrderVO order : orders) {
//			// paramField is in camelCase, convert it to under_score
//			String field = order.getParamField();
//			String norm = "";
//			if (field.equalsIgnoreCase("STATUSDISPLAY")
//					|| field.equalsIgnoreCase("createdByDisplay")
//					|| field.equalsIgnoreCase("updatedByDisplay")
//					|| field.equalsIgnoreCase("regionName")
//					|| field.equalsIgnoreCase("areaName")
//					|| field.equalsIgnoreCase("product")
//					|| field.equalsIgnoreCase("productTypeName")
//					|| field.equalsIgnoreCase("updatedByDisplay")
//					|| field.equalsIgnoreCase("brandName")
//					|| field.equalsIgnoreCase("modelName")
//					|| field.equalsIgnoreCase("deviationCodeDisplay")
//					|| field.equalsIgnoreCase("positionCodeDisplay")
//					|| field.equalsIgnoreCase("operatorDisplay")
//					|| field.equalsIgnoreCase("conditionDisplay")
//					|| field.equalsIgnoreCase("branchCodeDisplay")
//					|| field.equalsIgnoreCase("replacementName")
//					|| field.equalsIgnoreCase("userCode")
//					|| field.equalsIgnoreCase("regionDisplay")
//					|| field.equalsIgnoreCase("branchDisplay")
//					|| field.equalsIgnoreCase("productTypeDisplay")
//					|| field.equalsIgnoreCase("custName")
//					|| field.equalsIgnoreCase("branchName")
//					|| field.equalsIgnoreCase("marketingName")
//					|| field.equalsIgnoreCase("repeatOrder")
//					|| field.equalsIgnoreCase("appsDate")
//					|| field.equalsIgnoreCase("lastState")
//					|| field.equalsIgnoreCase("status")
//					|| field.equalsIgnoreCase("actionBy")
//					|| field.equalsIgnoreCase("actionOn")
//					|| field.equalsIgnoreCase("moName")
//					|| field.equalsIgnoreCase("appsId")
//					|| field.equalsIgnoreCase("rejectBy")
//					|| field.equalsIgnoreCase("description")
//					|| field.equalsIgnoreCase("dateProcessPending")
//					|| field.equalsIgnoreCase("dateApplicationCA")
//					|| field.equalsIgnoreCase("aging")
//					|| field.equalsIgnoreCase("caName")
//					|| field.equalsIgnoreCase("dateAppIn")
//					|| field.equalsIgnoreCase("dateAppOut")
//					|| field.equalsIgnoreCase("caDecision")
//					|| field.equalsIgnoreCase("descAwc")
//					|| field.equalsIgnoreCase("dateProcessAwc")
//					|| field.equalsIgnoreCase("processBy")
//					|| field.equalsIgnoreCase("dateConfirmAwc")
//					|| field.equalsIgnoreCase("confirmBy")
//					|| field.equalsIgnoreCase("lastLogin")
//					|| field.equalsIgnoreCase("approvalBy")
//					|| field.equalsIgnoreCase("rejectBy")) {
//				norm = getRealFieldNameCamelCaseToUnderScore(field);
//			} else {
//				norm = getAliasTable()
//						+ getRealFieldNameCamelCaseToUnderScore(field);
//			}
//			//
//			sb.append(norm);
//			if (order.isAsc())
//				sb.append(" asc");
//			else
//				sb.append(" desc");
//			sb.append(",");
//		}
//		sb.deleteCharAt(sb.length() - 1);
//		return sb.toString();
//	}

//	public String getParamOrderJmesa() {
//		if (orders == null || orders.size() == 0) {
//			return getAliasTable() + getPrimaryKey() + " desc";
//		}
//		StringBuilder sb = new StringBuilder();
//		// sb.append("order by ");
//		for (ParamOrderVO order : orders) {
//			// paramField is in camelCase, convert it to under_score
//			String field = order.getParamField();
//			String norm = getAliasTable() + getRealFieldName(field);
//			sb.append(norm);
//			if (order.isAsc())
//				sb.append(" asc");
//			else
//				sb.append(" desc");
//			sb.append(",");
//		}
//		sb.deleteCharAt(sb.length() - 1);
//		return sb.toString();
//	}

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

//	protected String getRealFieldNameCamelCaseToUnderScore(String fieldName) {
//		// get real field name
//		String s = fieldName;
//		// if (fieldName.endsWith("Display"))
//		// s = fieldName.substring(0, fieldName.length() - "Display".length());
//		// else
//		if (fieldName.equals("updated_on") || fieldName.equals("created_on"))
//			s = "r." + fieldName;
//		else if (fieldName.equalsIgnoreCase("statusDisplay")
//				|| fieldName.equalsIgnoreCase("product")
//				|| fieldName.equalsIgnoreCase("productTypeName")
//				|| fieldName.equalsIgnoreCase("operatorDisplay")
//				|| fieldName.equalsIgnoreCase("conditionDisplay")
//				|| fieldName.equalsIgnoreCase("productTypeDisplay"))
//			s = "ld.lookup_desc";
//		else if (fieldName.equalsIgnoreCase("createdByDisplay")
//				|| fieldName.equalsIgnoreCase("replacementName"))
//			// s = "ud2.user_name";
//			s = "ud1.user_name";
//		else if (fieldName.equalsIgnoreCase("updatedByDisplay"))
//			// s = "ud3.user_name";
//			s = "ud2.user_name";
//		else if (fieldName.equalsIgnoreCase("regionName")
//				|| fieldName.equalsIgnoreCase("regionDisplay"))
//			s = "rd.region_name";
//		else if (fieldName.equalsIgnoreCase("areaName"))
//			s = "ad.area_name";
//		else if (fieldName.equalsIgnoreCase("brandName"))
//			s = "bd.brand_name";
//		else if (fieldName.equalsIgnoreCase("modelName"))
//			s = "md.model_name";
//		else if (fieldName.equalsIgnoreCase("deviationCodeDisplay"))
//			s = "d.deviation_code";
//		else if (fieldName.equalsIgnoreCase("positionCodeDisplay")
//				|| fieldName.equalsIgnoreCase("lastState"))
//			s = "lvd.position_code";
//		else if (fieldName.equalsIgnoreCase("branchCodeDisplay"))
//			s = "b.branch_code";
//		else if (fieldName.equalsIgnoreCase("userCode"))
//			s = "ud.user_code";
//		else if (fieldName.equalsIgnoreCase("branchDisplay"))
//			s = "b.branch_name";
//		else if (fieldName.equalsIgnoreCase("custName"))
//			s = "asg.cust_name";
//		else if (fieldName.equalsIgnoreCase("branchName")) {
//			if (getAliasTable().equals("bd."))
//				s = "bd.branch_name";
//			else
//				s = "asg.branch_name";
//		} else if (fieldName.equalsIgnoreCase("marketingName")
//				|| fieldName.equalsIgnoreCase("moName"))
//			s = "asg.mo_name";
//		else if (fieldName.equalsIgnoreCase("repeatOrder"))
//			s = "apd.repeat_order";
//		else if (fieldName.equalsIgnoreCase("appsDate"))
//			s = "apd.apps_date";
//		else if (fieldName.equalsIgnoreCase("status"))
//			s = "d.status_apps";
//		else if (fieldName.equalsIgnoreCase("actionBy")
//				|| fieldName.equalsIgnoreCase("rejectBy")
//				|| fieldName.equalsIgnoreCase("caName")
//				|| fieldName.equalsIgnoreCase("processBy")
//				|| fieldName.equalsIgnoreCase("confirmBy")
//				|| fieldName.equalsIgnoreCase("approvalBy"))
//			s = "ud.user_name";
//		else if (fieldName.equalsIgnoreCase("actionOn"))
//			s = "d.action_on";
//		else if (fieldName.equalsIgnoreCase("appsId"))
//			s = "asg.apps_id";
//		else if (fieldName.equalsIgnoreCase("description")
//				|| fieldName.equalsIgnoreCase("descAwc")) {
//			if (getAliasTable().equals("d."))
//				s = "d.description";
//			else
//				s = "td.desc_status_apps";
//		}
//
//		else if (fieldName.equalsIgnoreCase("dateProcessPending")
//				|| fieldName.equalsIgnoreCase("dateApplicationCA")
//				|| fieldName.equalsIgnoreCase("aging")
//				|| fieldName.equalsIgnoreCase("dateAppOut")
//				|| fieldName.equalsIgnoreCase("dateConfirmAwc")
//				|| fieldName.equalsIgnoreCase("dateProcessAwc")
//				|| fieldName.equalsIgnoreCase("dateConfirmAwc"))
//			s = "td.action_on";
//		else if (fieldName.equalsIgnoreCase("dateAppIn"))
//			s = "th.created_on";
//		else if (fieldName.equalsIgnoreCase("caDecision"))
//			s = "td.desc_status_apps";
//		else if (fieldName.equalsIgnoreCase("lastLogin"))
//			s = "ud.last_login_data";
//		else if (fieldName.equalsIgnoreCase("appDate"))
//			s = "created_on";
//		// convert camelCase to under_score
//		return CommonUtils.convertCamelCaseToUnderScore(s);
//	}

	@Override
	public String toString() {
		return ReflectionToStringBuilder.toString(this);
	}

}
