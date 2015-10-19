package com.jakarta.software.web.interceptor;

import java.util.LinkedList;
import java.util.List;

import org.jmesa.view.editor.CellEditor;

public class TableJMesaExportVO {
	private String caption;
	private List<TableJMesaProperties> props = new LinkedList<TableJMesaExportVO.TableJMesaProperties>();
	
	public String getCaption() {
		return caption;
	}
	public void setCaption(String caption) {
		this.caption = caption;
	}
	
	public String getTitleFor(String column) {
		TableJMesaProperties prop = getPropertyFor(column);
		if (prop == null || prop.title == null) return null;		
		return prop.title;
	}
	public CellEditor getCellEditorFor(String column) {
		TableJMesaProperties prop = getPropertyFor(column);
		if (prop == null || prop.editor == null) return null;		
		return prop.editor;
	}
	public String getStyleFor(String column) {
		TableJMesaProperties prop = getPropertyFor(column);
		if (prop == null || prop.style == null) return null;		
		return prop.style;
	}
	public String[] getColumns() {
		String[] cols = new String[props.size()];
		int i = 0;
		for (TableJMesaProperties prop: props) {
			cols[i++] = prop.column;
		}
		return cols;
	}
	public void setColumnTitle(String column, String title) {
		setColumnTitle(column, title, null, null);
	}
	public void setColumnTitle(String column, String title, CellEditor editor) {
		setColumnTitle(column, title, editor, null);
	}
	public void setColumnTitle(String column, String title, CellEditor editor, String style) {
		TableJMesaProperties prop = new TableJMesaProperties();
		prop.column = column;
		prop.title = title;
		prop.editor = editor;
		prop.style = style;
		props.add(prop);
	}
	
	public boolean isEmpty() {
		return caption == null || props.size() == 0;
	}
	
	private TableJMesaProperties getPropertyFor(String column) {
		for (TableJMesaProperties prop: props) {
			if (prop.column == column)
				return prop;
		}
		return null;
	}
	
	private class TableJMesaProperties {
		String column;
		String title;
		CellEditor editor;
		String style;
	}
}

