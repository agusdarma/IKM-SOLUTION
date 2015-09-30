package com.ikm.myagenda.fragment;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;
import android.widget.ExpandableListView.OnGroupClickListener;
import android.widget.ImageView;
import android.widget.TextView;

import com.ikm.myagenda.R;
import com.ikm.myagenda.adapter.AgendaViewExpandableAdapter;
import com.ikm.myagenda.adapter.AgendaViewVO;
import com.ikm.myagenda.data.AgendaHeader;
import com.ikm.myagenda.data.AgendaItems;
import com.ikm.myagenda.util.ImageUtil;
import com.ikm.myagenda.view.AnimatedExpandableListView;
import com.ikm.myagenda.view.AnimatedExpandableListView.AnimatedExpandableListAdapter;

public class MyAgendaFragment extends Fragment {
	private static final String TAG = MyAgendaFragment.class.getSimpleName();
	private AnimatedExpandableListView listView;
	private AgendaViewExpandableAdapter adapter;
//	private ReqListAgendaTask reqListAgendaTask = null;
    private List<AgendaViewVO> data;
	private ImageView mImage;
	private TextView mName;
	private TextView mPlace;
	private Context ctx;

	public static MyAgendaFragment newInstance() {
		return new MyAgendaFragment();
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.fragment_myagenda_list_views,
				container, false);
		ctx = this.getActivity().getBaseContext();
		mImage = (ImageView) rootView.findViewById(R.id.expandable_lv_social_image);
		mName = (TextView) rootView.findViewById(R.id.expandable_lv_social_name);
		mPlace = (TextView) rootView.findViewById(R.id.expandable_lv_social_place);
		
//		ImageUtil.displayRoundImage(mImage,
//				"http://pengaja.com/uiapptemplate/newphotos/profileimages/2.jpg", null);
		mName.setText("Agenda Michael");
		mPlace.setText("Sekolah Dian Harapan");
		

		List<AgendaHeader> items = new ArrayList<AgendaHeader>();
		items = fillData(items);

		adapter = new AgendaViewExpandableAdapter(ctx,items);
		adapter.setData(items);

		listView = (AnimatedExpandableListView) rootView.findViewById(R.id.expandable_lv_social_list_view);
		listView.setAdapter(adapter);

		// In order to show animations, we need to use a custom click handler
		// for our ExpandableListView.
		listView.setOnGroupClickListener(new OnGroupClickListener() {

			@Override
			public boolean onGroupClick(ExpandableListView parent, View v,
					int groupPosition, long id) {
				// We call collapseGroupWithAnimation(int) and
				// expandGroupWithAnimation(int) to animate group
				// expansion/collapse.
				if (listView.isGroupExpanded(groupPosition)) {
					listView.collapseGroupWithAnimation(groupPosition);
				} else {
					listView.expandGroupWithAnimation(groupPosition);
				}
				return true;
			}
		});
		
		return rootView;
	}
	
	private List<AgendaHeader> fillData(List<AgendaHeader> items) {
		AgendaHeader item = new AgendaHeader();
		item.setTitle("Friends");
		AgendaItems child;
		child = new AgendaItems();
		child.setTitle("John Doe");
		item.getItemsDetail().add(child);

		child = new AgendaItems();
		child.setTitle("Jane Doe");
		item.getItemsDetail().add(child);

		child = new AgendaItems();
		child.setTitle("John Doe");
		item.getItemsDetail().add(child);

		child = new AgendaItems();
		child.setTitle("Jane Doe");
		item.getItemsDetail().add(child);

		items.add(item);

		item = new AgendaHeader();
		item.setTitle("Enemies");
		
		child = new AgendaItems();
		child.setTitle("John Doe");
		item.getItemsDetail().add(child);

		child = new AgendaItems();
		child.setTitle("John Doe");
		item.getItemsDetail().add(child);

		child = new AgendaItems();
		child.setTitle("John Doe");
		item.getItemsDetail().add(child);

		child = new AgendaItems();
		child.setTitle("John Doe");
		item.getItemsDetail().add(child);

		child = new AgendaItems();
		child.setTitle("John Doe");
		item.getItemsDetail().add(child);

		child = new AgendaItems();
		child.setTitle("John Doe");
		item.getItemsDetail().add(child);

		items.add(item);
		
		item = new AgendaHeader();
		item.setTitle("Neutral");
		
		child = new AgendaItems();
		child.setTitle("John Doe");
		item.getItemsDetail().add(child);

		child = new AgendaItems();
		child.setTitle("John Doe");
		item.getItemsDetail().add(child);

		child = new AgendaItems();
		child.setTitle("John Doe");
		item.getItemsDetail().add(child);

		child = new AgendaItems();
		child.setTitle("John Doe");
		item.getItemsDetail().add(child);

		child = new AgendaItems();
		child.setTitle("John Doe");
		item.getItemsDetail().add(child);

		items.add(item);

		return items;
	}
	
	private static class GroupItem {
		String title;
		int icon = R.string.material_icon_friends;
		List<ChildItem> items = new ArrayList<ChildItem>();
	}

	private static class ChildItem {
		String title;
	}

	private static class ChildHolder {
		TextView title;
		ImageView image;
	}

	private static class GroupHolder {
		TextView title;
		TextView icon;
	}
	
	private class ExampleAdapter extends AnimatedExpandableListAdapter {
		private LayoutInflater inflater;

		private List<GroupItem> items;

		public ExampleAdapter(Context context) {
			inflater = LayoutInflater.from(context);
		}

		public void setData(List<GroupItem> items) {
			this.items = items;
		}

		@Override
		public ChildItem getChild(int groupPosition, int childPosition) {
			return items.get(groupPosition).items.get(childPosition);
		}

		@Override
		public long getChildId(int groupPosition, int childPosition) {
			return childPosition;
		}

		@Override
		public View getRealChildView(int groupPosition, int childPosition,
				boolean isLastChild, View convertView, ViewGroup parent) {
			ChildHolder holder;
			ChildItem item = getChild(groupPosition, childPosition);
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

			holder.title.setText(item.title);
			ImageUtil.displayRoundImage(holder.image,
					"http://pengaja.com/uiapptemplate/newphotos/profileimages/0.jpg", null);

			return convertView;
		}

		@Override
		public int getRealChildrenCount(int groupPosition) {
			return items.get(groupPosition).items.size();
		}

		@Override
		public GroupItem getGroup(int groupPosition) {
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
			GroupItem item = getGroup(groupPosition);
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

			holder.title.setText(item.title);
			holder.icon.setText(item.icon);

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
}
