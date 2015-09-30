package com.ikm.myagenda.model;

public class DummyModel {
	
	public static final int DRAWER_ITEM_ID_MYAGENDA = 10;
	public static final int DRAWER_ITEM_ID_INBOX = 11;
	public static final int DRAWER_ITEM_ID_ADD_AGENDA = 12;
	
	private int mId;
	private String mImageURL;
	private String mText;
	private int mIconRes;

	public DummyModel() {
	}

	public DummyModel(int id, String imageURL, String text, int iconRes) {
		mId = id;
		mImageURL = imageURL;
		mText = text;
		mIconRes = iconRes;
	}

	public int getId() {
		return mId;
	}

	public void setId(int id) {
		mId = id;
	}

	public String getImageURL() {
		return mImageURL;
	}

	public void setImageURL(String imageURL) {
		mImageURL = imageURL;
	}

	public String getText() {
		return mText;
	}

	public void setText(String text) {
		mText = text;
	}

	public int getIconRes() {
		return mIconRes;
	}

	public void setIconRes(int iconRes) {
		mIconRes = iconRes;
	}

	@Override
	public String toString() {
		return mText;
	}
}
