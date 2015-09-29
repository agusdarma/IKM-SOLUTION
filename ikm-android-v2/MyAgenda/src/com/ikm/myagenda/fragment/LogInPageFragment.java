package com.ikm.myagenda.fragment;

import java.util.ArrayList;
import java.util.List;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

import com.ikm.myagenda.LogInPageActivity;
import com.ikm.myagenda.R;
import com.ikm.myagenda.RegisterPageActivity;
import com.ikm.myagenda.adapter.SubcategoryAdapter;

public class LogInPageFragment extends Fragment implements OnItemClickListener {

	private ListView mListView;
	private List<String> mLogInPages;

	public static LogInPageFragment newInstance() {
		return new LogInPageFragment();
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		mLogInPages = new ArrayList<String>();
		mLogInPages.add(LogInPageActivity.LIGHT);
		mLogInPages.add(LogInPageActivity.DARK);
		mLogInPages.add(LogInPageActivity.TRAVEL);
		mLogInPages.add(LogInPageActivity.SOCIAL);
		mLogInPages.add(RegisterPageActivity.REGISTER_TRAVEL);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View rootView = inflater.inflate(
				R.layout.fragment_login_page_and_loaders, container, false);

		mListView = (ListView) rootView.findViewById(R.id.list_view);
		mListView
				.setAdapter(new SubcategoryAdapter(getActivity(), mLogInPages));
		mListView.setOnItemClickListener(this);

		return rootView;
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		if (position != 4) {
			Intent intent = new Intent(getActivity(), LogInPageActivity.class);
			intent.putExtra(LogInPageActivity.LOGIN_PAGE_AND_LOADERS_CATEGORY,
					mLogInPages.get(position));
			startActivity(intent);
		} else {
			Intent intent = new Intent(getActivity(),
					RegisterPageActivity.class);
			startActivity(intent);
		}
	}
}
