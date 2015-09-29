package com.ikm.myagenda.adapter;

import java.util.ArrayList;

import se.emilsjolander.stickylistheaders.StickyListHeadersAdapter;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.ikm.myagenda.R;
import com.ikm.myagenda.data.SubtitleVO;
import com.ikm.myagenda.model.DummyModel;
import com.ikm.myagenda.util.ImageUtil;
import com.nhaarman.listviewanimations.ArrayAdapter;

public class MyStickyListHeadersSubtitleAdapter extends ArrayAdapter<String>
		implements StickyListHeadersAdapter, OnClickListener {

	private final Context mContext;
	private LayoutInflater mInflater;
	private ArrayList<SubtitleVO> mSubtitleModelList;
	private String header;

	public MyStickyListHeadersSubtitleAdapter(final Context context, ArrayList<SubtitleVO> dummyModelList) {
		mContext = context;
		mSubtitleModelList = dummyModelList;
		mInflater = (LayoutInflater) mContext
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		for (int i = 0; i < mSubtitleModelList.size(); i++) {
			add("Row number " + i);
		}
	}

	@Override
	public long getItemId(final int position) {
		return getItem(position).hashCode();
	}

	@Override
	public boolean hasStableIds() {
		return true;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		final ViewHolder holder;
		if (convertView == null) {
			convertView = mInflater.inflate(
					R.layout.list_item_sticky_header_social, parent, false);
			holder = new ViewHolder();
			holder.image = (ImageView) convertView
					.findViewById(R.id.list_item_sticky_header_social_image);
			holder.name = (TextView) convertView
					.findViewById(R.id.list_item_sticky_header_social_name);
			holder.likeIcon = (TextView) convertView
					.findViewById(R.id.list_item_sticky_header_social_icon_like);
			holder.bookmarkIcon = (TextView) convertView
					.findViewById(R.id.list_item_sticky_header_social_icon_bookmark);
			holder.shareIcon = (TextView) convertView
					.findViewById(R.id.list_item_sticky_header_social_icon_share);
			holder.text = (TextView) convertView
					.findViewById(R.id.list_item_sticky_header_social_text);
			holder.image.setOnClickListener(this);
			holder.name.setOnClickListener(this);
			holder.likeIcon.setOnClickListener(this);
			holder.bookmarkIcon.setOnClickListener(this);
			holder.shareIcon.setOnClickListener(this);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}

		// TODO Change image URL
//		ImageUtil.displayRoundImage(holder.image,
//				"http://pengaja.com/uiapptemplate/avatars/0.jpg", null);
		ImageUtil.displayRoundImage(holder.image,
				mSubtitleModelList.get(position % 5).getImageURL(), null);
		header = mSubtitleModelList.get(position).getmHeaderSubtitle();
		holder.name.setText(mSubtitleModelList.get(position).getmTitleMovie());
		holder.text.setText(mSubtitleModelList.get(position).getmSubFileName());
		holder.image.setTag(position);
		holder.name.setTag(position);
		holder.likeIcon.setTag(position);
		holder.shareIcon.setTag(position);
		holder.bookmarkIcon.setTag(position);
		return convertView;
	}

	private static class ViewHolder {
		public ImageView image;
		public/* Roboto */TextView name;
		public/* Material */TextView likeIcon;
		public/* Material */TextView bookmarkIcon;
		public/* Material */TextView shareIcon;
		public/* Roboto */TextView text;
	}

	private static class HeaderViewHolder {
		public/* Roboto */TextView day;
		public/* Roboto */TextView date;
	}

	@Override
	public View getHeaderView(final int position, final View convertView,
			final ViewGroup parent) {
		View view = (View) convertView;
		final HeaderViewHolder holder;
		if (view == null) {
			view = (View) LayoutInflater.from(mContext).inflate(
					R.layout.list_header_social, parent, false);
			holder = new HeaderViewHolder();
			holder.day = (TextView) view
					.findViewById(R.id.list_header_social_day);
			holder.date = (TextView) view
					.findViewById(R.id.list_header_social_date);
			view.setTag(holder);
		} else {
			holder = (HeaderViewHolder) view.getTag();
		}

		holder.day.setText(mSubtitleModelList.get(position).getmSubDateUpload());
		holder.date.setText(mSubtitleModelList.get(position).getmSubDateUpload());
		// holder.name.setText("Header " + getHeaderId(position));

		return view;
	}

	@Override
	public long getHeaderId(final int position) {
		long id = 0;
		if(!mSubtitleModelList.get(position).getmHeaderSubtitle().equalsIgnoreCase(header)){
			id = 1;
		}
		return id;
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		int position = (Integer) v.getTag();
		switch (v.getId()) {
		case R.id.list_item_sticky_header_social_image:
			Toast.makeText(mContext, "User image: " + position,
					Toast.LENGTH_SHORT).show();
			break;
		case R.id.list_item_sticky_header_social_icon_like:
			Toast.makeText(mContext, "Like icon: " + position,
					Toast.LENGTH_SHORT).show();
			break;
		case R.id.list_item_sticky_header_social_icon_bookmark:
			Toast.makeText(mContext, "Bookmark icon: " + position,
					Toast.LENGTH_SHORT).show();
			break;
		case R.id.list_item_sticky_header_social_icon_share:
			Toast.makeText(mContext, "Share icon: " + position,
					Toast.LENGTH_SHORT).show();
			break;
		case R.id.list_item_sticky_header_social_name:
			Toast.makeText(mContext, "Username: " + position,
					Toast.LENGTH_SHORT).show();
			break;
		}
	}
}