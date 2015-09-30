package com.ikm.myagenda.data;

import java.util.ArrayList;
import java.util.List;

import com.ikm.myagenda.R;

public class AgendaHeader implements java.io.Serializable {
	private static final long serialVersionUID = 1L;

	private String title;
	private int icon = R.string.material_icon_friends;
	private List<AgendaItems> itemsDetail = new ArrayList<AgendaItems>();

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public int getIcon() {
		return icon;
	}

	public void setIcon(int icon) {
		this.icon = icon;
	}

	public List<AgendaItems> getItemsDetail() {
		return itemsDetail;
	}

	public void setItemsDetail(List<AgendaItems> itemsDetail) {
		this.itemsDetail = itemsDetail;
	}

	

}
