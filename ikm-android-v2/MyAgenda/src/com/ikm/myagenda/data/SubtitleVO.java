package com.ikm.myagenda.data;

public class SubtitleVO {

	private int mId;
	private String mImageURL;
	private String mSubFileName;
	private String mTitleMovie;
	private String mHeaderSubtitle;
	private String mSubDateUpload;
	private int mIconRes;

	public SubtitleVO() {
	}

	public SubtitleVO(int id, String imageURL, String subFileName,String titleMovie,String headerSubtitle, int iconRes) {
		mId = id;
		mImageURL = imageURL;
		mSubFileName = subFileName;
		mTitleMovie = titleMovie;
		mHeaderSubtitle = headerSubtitle;
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

	

	public int getIconRes() {
		return mIconRes;
	}

	public void setIconRes(int iconRes) {
		mIconRes = iconRes;
	}

	@Override
	public String toString() {
		return mSubFileName;
	}

	public String getmSubFileName() {
		return mSubFileName;
	}

	public void setmSubFileName(String mSubFileName) {
		this.mSubFileName = mSubFileName;
	}

	public String getmTitleMovie() {
		return mTitleMovie;
	}

	public void setmTitleMovie(String mTitleMovie) {
		this.mTitleMovie = mTitleMovie;
	}

	public String getmHeaderSubtitle() {
		return mHeaderSubtitle;
	}

	public void setmHeaderSubtitle(String mHeaderSubtitle) {
		this.mHeaderSubtitle = mHeaderSubtitle;
	}

	public String getmSubDateUpload() {
		return mSubDateUpload;
	}

	public void setmSubDateUpload(String mSubDateUpload) {
		this.mSubDateUpload = mSubDateUpload;
	}
}
