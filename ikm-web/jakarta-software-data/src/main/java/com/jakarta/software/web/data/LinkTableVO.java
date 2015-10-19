package com.jakarta.software.web.data;

public class LinkTableVO {

	private String link;
	private String linkLocation;
	private String[] linkKey;
	private String[] linkValue;
	
	public LinkTableVO(String link, String linkLocation, String[] linkKey, String[] linkValue) {
		this.link = link;
		this.linkLocation = linkLocation;
		this.linkKey = linkKey;
		this.linkValue = linkValue;
	}
	
	public String getLink() {
		return link;
	}
	public void setLink(String link) {
		this.link = link;
	}
	public String getLinkLocation() {
		return linkLocation;
	}
	public void setLinkLocation(String linkLocation) {
		this.linkLocation = linkLocation;
	}
	public String[] getLinkKey() {
		return linkKey;
	}
	public void setLinkKey(String[] linkKey) {
		this.linkKey = linkKey;
	}
	public String[] getLinkValue() {
		return linkValue;
	}
	public void setLinkValue(String[] linkValue) {
		this.linkValue = linkValue;
	}

		
}
