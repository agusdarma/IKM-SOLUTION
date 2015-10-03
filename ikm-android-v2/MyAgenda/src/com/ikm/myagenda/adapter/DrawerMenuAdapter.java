package com.ikm.myagenda.adapter;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.ikm.myagenda.R;
import com.ikm.myagenda.data.Constants;
import com.ikm.myagenda.model.DummyModel;
import com.ikm.myagenda.util.DummyContent;
import com.ikm.myagenda.util.SharedPreferencesUtils;

public class DrawerMenuAdapter extends BaseAdapter {

	private List<DummyModel> mDrawerItems;
	private LayoutInflater mInflater;
	private Context ctx;

	public DrawerMenuAdapter(Context context,String tipeLogin) {
		this.ctx = context;
		mInflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		if(Constants.PARENTS.equalsIgnoreCase(tipeLogin)){
			mDrawerItems = DummyContent.getParentMenuList();
		}else if(Constants.TEACHER.equalsIgnoreCase(tipeLogin)){
			mDrawerItems = DummyContent.getTeacherMenuList();
		}
		
	}

	@Override
	public int getCount() {
		return mDrawerItems.size();
	}

	@Override
	public Object getItem(int position) {
		return mDrawerItems.get(position-1);
	}

	@Override
	public long getItemId(int position) {
		return mDrawerItems.get(position).getId();
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		final ViewHolder holder;
		if (convertView == null) {
			convertView = mInflater.inflate(
					R.layout.list_view_item_navigation_drawer_social, parent,
					false);
			holder = new ViewHolder();
			holder.icon = (TextView) convertView
					.findViewById(R.id.icon_social_navigation_item);
			holder.numberOfNotification = (TextView) convertView
					.findViewById(R.id.number_of_notification);			
			holder.title = (TextView) convertView.findViewById(R.id.title);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}

		DummyModel item = mDrawerItems.get(position);
		holder.numberOfNotification.setText("0");
		holder.numberOfNotification.setVisibility(View.GONE);
		if(Constants.MENU_INBOX.equalsIgnoreCase(item.getText())){
			holder.numberOfNotification.setVisibility(View.VISIBLE);
			holder.numberOfNotification.setText(Integer.toString(SharedPreferencesUtils.getNumberNotification(ctx)));			
		}		
		holder.icon.setText(item.getIconRes());
		holder.title.setText(item.getText());

		return convertView;
	}

	private static class ViewHolder {
		public TextView icon;
		public TextView numberOfNotification;
		public/* Roboto */TextView title;
	}
}
