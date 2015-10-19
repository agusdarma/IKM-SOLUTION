package com.jakarta.software.web.data.param;

public class SystemSettingParamVO extends ParamPagingVO{
	
	@Override
	protected String getPrimaryKey() {
		return "updatedOn";
	}
	
//	@Override
//	protected String getAliasTable() {
//		return "";
//	}
}
