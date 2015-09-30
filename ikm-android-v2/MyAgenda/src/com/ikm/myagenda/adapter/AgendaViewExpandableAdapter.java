package com.ikm.myagenda.adapter;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.ikm.myagenda.R;
import com.ikm.myagenda.data.AgendaHeader;
import com.ikm.myagenda.data.AgendaItems;
import com.ikm.myagenda.util.ImageUtil;
import com.ikm.myagenda.view.AnimatedExpandableListView.AnimatedExpandableListAdapter;

public class AgendaViewExpandableAdapter extends AnimatedExpandableListAdapter {
	private static final String TAG = AgendaViewExpandableAdapter.class.getSimpleName();

	private LayoutInflater inflater;

	protected List<AgendaHeader> items;

	public AgendaViewExpandableAdapter(Context context,List<AgendaHeader> items) {
		inflater = LayoutInflater.from(context);
		this.items = items;
	}

	public void setData(List<AgendaHeader> items) {
		this.items = items;
	}

	@Override
	public AgendaItems getChild(int groupPosition, int childPosition) {
		return items.get(groupPosition).getItemsDetail().get(childPosition);
	}

	@Override
	public long getChildId(int groupPosition, int childPosition) {
		return childPosition;
	}
	
	private static class ChildHolder {
		TextView title;
		ImageView image;
	}

	private static class GroupHolder {
		TextView title;
		TextView icon;
	}

	@Override
	public View getRealChildView(int groupPosition, int childPosition,
			boolean isLastChild, View convertView, ViewGroup parent) {
		ChildHolder holder;
		AgendaItems item = getChild(groupPosition, childPosition);
		if (convertView == null) {
			holder = new ChildHolder();
			convertView = inflater.inflate(
					R.layout.list_item_expandable_social_child, parent,
					false);
			holder.title = (TextView) convertView
					.findViewById(R.id.expandable_item_social_child_name);
			holder.image = (ImageView) convertView
					.findViewById(R.id.expandable_item_social_child_image);
			convertView.setTag(holder);
		} else {
			holder = (ChildHolder) convertView.getTag();
		}

		holder.title.setText(item.getTitle());
		ImageUtil.displayRoundImage(holder.image,
				"http://pengaja.com/uiapptemplate/newphotos/profileimages/0.jpg", null);

		return convertView;
	}

	@Override
	public int getRealChildrenCount(int groupPosition) {
		return items.get(groupPosition).getItemsDetail().size();
	}

	@Override
	public AgendaHeader getGroup(int groupPosition) {
		return items.get(groupPosition);
	}

	@Override
	public int getGroupCount() {
		return items.size();
	}

	@Override
	public long getGroupId(int groupPosition) {
		return groupPosition;
	}

	@Override
	public View getGroupView(int groupPosition, boolean isExpanded,
			View convertView, ViewGroup parent) {
		GroupHolder holder;
		AgendaHeader item = getGroup(groupPosition);
		if (convertView == null) {
			holder = new GroupHolder();
			convertView = inflater.inflate(
					R.layout.list_item_expandable_social, parent, false);
			holder.title = (TextView) convertView
					.findViewById(R.id.expandable_item_social_name);
			holder.icon = (TextView) convertView
					.findViewById(R.id.expandable_item_social_icon);
			convertView.setTag(holder);
		} else {
			holder = (GroupHolder) convertView.getTag();
		}

		holder.title.setText(item.getTitle());
		holder.icon.setText(item.getIcon());

		return convertView;
	}

	@Override
	public boolean hasStableIds() {
		return true;
	}

	@Override
	public boolean isChildSelectable(int arg0, int arg1) {
		return true;
	}

}