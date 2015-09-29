package com.ikm.myagenda.fragment;

import java.util.ArrayList;
import java.util.List;

import se.emilsjolander.stickylistheaders.StickyListHeadersListView;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

import com.ikm.myagenda.CategoriesListViewActivity;
import com.ikm.myagenda.HomeActivity;
import com.ikm.myagenda.R;
import com.ikm.myagenda.StickyListHeadersActivity;
import com.ikm.myagenda.adapter.MyStickyListHeadersAdapter;
import com.ikm.myagenda.adapter.MyStickyListHeadersSocialAdapter;
import com.ikm.myagenda.adapter.MyStickyListHeadersSubtitleAdapter;
import com.ikm.myagenda.adapter.MyStickyListHeadersTravelAdapter;
import com.ikm.myagenda.util.DummyContent;
import com.nhaarman.listviewanimations.appearance.StickyListHeadersAdapterDecorator;
import com.nhaarman.listviewanimations.appearance.simple.AlphaInAnimationAdapter;
import com.nhaarman.listviewanimations.util.StickyListHeadersListViewWrapper;

public class HomeFragment extends Fragment implements OnItemClickListener {
	
	public static final String LIST_VIEW_OPTION_STICKI_LIST_HEADERS = "Sticky list headers";
//	private ListView mListView;
//	private List<String> mListViews;

	public static HomeFragment newInstance() {
		return new HomeFragment();
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
//		mListViews = new ArrayList<String>();
//		mListViews.add(CategoriesListViewActivity.LIST_VIEW_OPTION_EXPANDABLE);
//		mListViews
//				.add(CategoriesListViewActivity.LIST_VIEW_OPTION_DRAG_AND_DROP);
//		mListViews
//				.add(CategoriesListViewActivity.LIST_VIEW_OPTION_SWIPE_TO_DISSMISS);
//		mListViews
//				.add(CategoriesListViewActivity.LIST_VIEW_OPTION_APPEARANCE_ANIMIATIONS);
//		mListViews
//				.add(CategoriesListViewActivity.LIST_VIEW_OPTION_STICKI_LIST_HEADERS);
//		mListViews
//				.add(CategoriesListViewActivity.LIST_VIEW_OPTION_GOOGLE_CARDS);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.fragment_list_views,
				container, false);
		StickyListHeadersListView listView = (StickyListHeadersListView) rootView.findViewById(R.id.activity_stickylistheaders_listview);
		listView.setFitsSystemWindows(true);

		AlphaInAnimationAdapter animationAdapter;
		
//		MyStickyListHeadersSocialAdapter adapterSocial = new MyStickyListHeadersSocialAdapter(
//				this.getActivity().getBaseContext(), DummyContent.getDummyModelList());
		MyStickyListHeadersSubtitleAdapter adapterSubtitle = new MyStickyListHeadersSubtitleAdapter(
				this.getActivity().getBaseContext(), DummyContent.getDummySubtitleList());
		animationAdapter = new AlphaInAnimationAdapter(adapterSubtitle);
		

		StickyListHeadersAdapterDecorator stickyListHeadersAdapterDecorator = new StickyListHeadersAdapterDecorator(
				animationAdapter);
		stickyListHeadersAdapterDecorator
				.setListViewWrapper(new StickyListHeadersListViewWrapper(
						listView));
		assert animationAdapter.getViewAnimator() != null;
		animationAdapter.getViewAnimator().setInitialDelayMillis(500);
		assert stickyListHeadersAdapterDecorator.getViewAnimator() != null;
		stickyListHeadersAdapterDecorator.getViewAnimator()
				.setInitialDelayMillis(500);
		listView.setAdapter(stickyListHeadersAdapterDecorator);
//		mListView = (ListView) rootView.findViewById(R.id.list_view);
//		mListView.setAdapter(new SubcategoryAdapter(getActivity(), mListViews));
//		mListView.setOnItemClickListener(this);
//		Intent intent = null;
//		intent = new Intent(this.getActivity(), HomeActivity.class);
//		intent.putExtra(LIST_VIEW_OPTION_STICKI_LIST_HEADERS,HomeActivity.STICKY_LIST_HEADERS_SOCIAL);
//		startActivity(intent);
		return rootView;
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
//		Intent intent = new Intent(getActivity(),
//				CategoriesListViewActivity.class);
//		;
//		if (position == 5) {
//			intent.putExtra(
//					CategoriesListViewActivity.LIST_VIEW_CATEGORY_GOOGLE_CARDS,
//					mListViews.get(position));
//		} else if (position == 4) {
//			intent.putExtra(
//					CategoriesListViewActivity.LIST_VIEW_CATEGORY_STICKY_HEADER,
//					mListViews.get(position));
//		} else if (position == 0) {
//			intent.putExtra(
//					CategoriesListViewActivity.LIST_VIEW_CATEGORY_EXPANDABLES,
//					mListViews.get(position));
//		} else if (position == 1) {
//			intent.putExtra(
//					CategoriesListViewActivity.LIST_VIEW_CATEGORY_DRAG_AND_DROP,
//					mListViews.get(position));
//		} else if (position == 2) {
//			intent.putExtra(
//					CategoriesListViewActivity.LIST_VIEW_CATEGORY_SWIPE_TO_DISSMISS,
//					mListViews.get(position));
//		} else if (position == 3) {
//			intent.putExtra(
//					CategoriesListViewActivity.LIST_VIEW_CATEGORY_APPEARANCE_ANIMATIONS,
//					mListViews.get(position));
//		} else {
//		}
//		startActivity(intent);
	}
}
